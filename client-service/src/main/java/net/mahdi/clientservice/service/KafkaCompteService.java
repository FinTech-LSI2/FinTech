package net.mahdi.clientservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.mahdi.clientservice.exception.CompteNotFoundException;
import net.mahdi.clientservice.models.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class KafkaCompteService {

    @Autowired
    private CompteService compteService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.response-account}")
    private String responseAccountTopic;

    @KafkaListener(topics = "${kafka.topic.request-account}", groupId = "compte-service-group")
    public void handleAccountRequest(String rib) {
        try {
            // Récupérer les informations du compte
            Compte compte = compteService.findOne(rib);

            // Sérialiser l'objet Compte en JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String compteJson = objectMapper.writeValueAsString(compte);

            // Publier les informations du compte dans le topic des réponses
            kafkaTemplate.send(responseAccountTopic, compteJson);

        } catch (CompteNotFoundException e) {
            // Publier une erreur dans le topic des réponses (facultatif)
            kafkaTemplate.send(responseAccountTopic, "{\"error\":\"Compte introuvable\"}");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "update-account-topic", groupId = "compte-service-group")
    public void handleAccountUpdate(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Compte account = objectMapper.readValue(message, Compte.class);
        compteService.updateBalance(
                account.getRib(),
                BigDecimal.valueOf(account.getBalance())
        );
    }



}
