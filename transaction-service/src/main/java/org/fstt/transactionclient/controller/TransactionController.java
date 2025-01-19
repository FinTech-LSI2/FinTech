package org.fstt.transactionclient.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.fstt.transactionclient.events.Account;
import org.fstt.transactionclient.models.*;
import org.fstt.transactionclient.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



@RestController
@RequestMapping("/api/transactions")
@Transactional
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${kafka.topic.request-account}")
    private String requestAccountTopic;

    @Value("${kafka.topic.response-account}")
    private String responseAccountTopic;

    private final Map<String, Account> accountCache = new ConcurrentHashMap<>();

    @KafkaListener(topics = "${kafka.topic.response-account}", groupId = "transaction-group")
    public void listenAccountResponse(String message) throws JsonProcessingException {
        Account account = objectMapper.readValue(message, Account.class);
        accountCache.put(account.getRib(), account);
    }

    @GetMapping("/clients/{rib}")
    public List<Transaction> getTransactionsByRib(@PathVariable String rib) {
        return transactionService.getTransactionsByRib(rib);
    }

    @PostMapping("/retrait")
    public ResponseEntity<?> makeRetrait(@RequestBody Retrait retrait) throws InterruptedException, JsonProcessingException {
        return processTransaction(retrait, retrait.getAmount().negate());
    }

    @PostMapping("/versement")
    public ResponseEntity<?> makeVersement(@RequestBody Versement versement) throws InterruptedException, JsonProcessingException {
        return processTransaction(versement, versement.getAmount());
    }

    @PostMapping("/paiement")
    public ResponseEntity<?> makePaiement(@RequestBody Paiement paiement) throws InterruptedException, JsonProcessingException {
        return processTransaction(paiement, paiement.getAmount().negate());
    }

    @PostMapping("/virement")
    public ResponseEntity<?> makeVirement(@RequestBody Virement virement) throws InterruptedException, JsonProcessingException {
        // Débit du compte source
        ResponseEntity<?> debitResponse = processTransaction(virement, virement.getAmount().negate(), virement.getRib());
        if (!debitResponse.getStatusCode().is2xxSuccessful()) {
            return debitResponse; // Si le débit échoue, on renvoie l'erreur
        }

        // Crédit du compte destinataire
        ResponseEntity<?> creditResponse = processTransaction(virement, virement.getAmount(), virement.getDestinationRib());
        if (!creditResponse.getStatusCode().is2xxSuccessful()) {
            // Si le crédit échoue, on inverse le débit (rollback)
            processTransaction(virement, virement.getAmount(), virement.getRib()); // Annulation du débit
            return creditResponse; // On renvoie l'erreur pour le crédit
        }

        return ResponseEntity.ok(virement); // Tout s'est bien passé
    }


    private ResponseEntity<?> processTransaction(Transaction transaction, BigDecimal amount) throws InterruptedException, JsonProcessingException {
        return processTransaction(transaction, amount, transaction.getRib());
    }

    private ResponseEntity<?> processTransaction(Transaction transaction, BigDecimal amount, String rib) throws InterruptedException, JsonProcessingException {
        // Publier une requête Kafka pour récupérer les informations du compte
        kafkaTemplate.send(requestAccountTopic, rib);

        // Attendre que la réponse soit reçue
        int attempts = 0;
        while (!accountCache.containsKey(rib) && attempts < 10) {
            Thread.sleep(500); // Attente de 500ms entre les tentatives
            attempts++;
        }

        // Vérifier si l'information a été reçue
        if (!accountCache.containsKey(rib)) {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Impossible de récupérer les informations du compte");
        }

        // Récupérer l'objet compte
        Account account = accountCache.remove(rib);

        // Vérifier le solde suffisant
        if (account.getBalance().add(amount).compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.badRequest().body("Solde insuffisant");
        }

        // Mettre à jour le solde et sauvegarder la transaction
        account.setBalance(account.getBalance().add(amount));
        transactionService.saveTransaction(transaction);

        // Publier les informations mises à jour du compte via Kafka
        //kafkaTemplate.send(responseAccountTopic, objectMapper.writeValueAsString(account));
        kafkaTemplate.send("update-account-topic", objectMapper.writeValueAsString(account));
        return ResponseEntity.ok(transaction);
    }
}