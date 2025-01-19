package net.mahdi.clientservice.service;


import net.mahdi.clientservice.DTOs.CompteDTO;
import net.mahdi.clientservice.enums.StatusCompte;
import net.mahdi.clientservice.enums.TypeCompte;
//import net.mahdi.clientservice.events.AccountEvent;
import net.mahdi.clientservice.exception.CompteNotFoundException;
import net.mahdi.clientservice.models.Client;
import net.mahdi.clientservice.models.Compte;
import net.mahdi.clientservice.models.CompteCourant;
import net.mahdi.clientservice.models.CompteEpargne;
import net.mahdi.clientservice.repository.ClientRepository;
import net.mahdi.clientservice.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CompteServiceImpl implements CompteService {

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private ClientRepository clientRepository ;


//    public CompteServiceImpl(KafkaTemplate<String, AccountEvent> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }

    @Override
    public void createAccount(CompteDTO compteDto) {
        try {
            // Récupérer le client à partir de l'ID
            Client client = clientRepository.findById(compteDto.getIdClient())
                    .orElseThrow(() -> new IllegalArgumentException("Client non trouvé avec l'ID : " + compteDto.getIdClient()));

            Compte compte;
            if ("CURRANT".equalsIgnoreCase(String.valueOf(compteDto.getTypeCompte()))) {
                compte = new CompteCourant();
                ((CompteCourant) compte).setDecouvert(compteDto.getDecouvert());
            } else if ("SAVING".equalsIgnoreCase(String.valueOf(compteDto.getTypeCompte()))) {
                compte = new CompteEpargne();
                ((CompteEpargne) compte).setInteretRate(compteDto.getInteretRate());
            } else {
                throw new IllegalArgumentException("Type de compte non valide : " + compteDto.getTypeCompte());
            }

            compte.setBalance(compteDto.getBalance());
            compte.setCreatedDate(new Date());
            compte.setStatus(StatusCompte.ACTIVATED); // Par défaut, activé
            compte.setTypeCompte(compteDto.getTypeCompte());
            compte.setClient(client); // Associer le client au compte

            // Générer le RIB (avant sauvegarde initiale)
            String rib = generateRib(null); // Utilisez null car l'ID n'est pas encore généré
            compte.setRib(rib);

            // Sauvegarder le compte
            compte = compteRepository.save(compte);

            // Générer le RIB après avoir obtenu l'ID du compte
            rib = generateRib(compte.getId());
            compte.setRib(rib);

            // Sauvegarder le compte mis à jour
            compteRepository.save(compte);
        } catch (Exception e) {
            // Log the exception
            System.err.println("Error creating compte: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw the exception to propagate it to the controller
        }
    }


    @Override
    public List<Compte> findComptesEpargnes() {
        return compteRepository.findAllByTypeCompte(TypeCompte.SAVING);
    }

    @Override
    public List<Compte> findComptesCourants() {
        return compteRepository.findAllByTypeCompte(TypeCompte.CURRANT);
    }

    @Override
    public Compte findOne(String rib) {
        return compteRepository.findByRib(rib)
                .orElseThrow(() -> new CompteNotFoundException("Compte non trouvé avec le RIB : " + rib));
    }


    @Override
    public boolean activateCompte(String rib) {
        Optional<Compte> compteOptional = compteRepository.findByRib(rib);
        if (compteOptional.isPresent()) {
            Compte compte = compteOptional.get();
            compte.setStatus(StatusCompte.ACTIVATED);
            compteRepository.save(compte);
            return true;
        }
        throw new CompteNotFoundException("Compte non trouvé pour activation avec le RIB : " + rib);
    }

    @Override
    public boolean suspendCompte(String rib) {
        Optional<Compte> compteOptional = compteRepository.findByRib(rib);
        if (compteOptional.isPresent()) {
            Compte compte = compteOptional.get();
            compte.setStatus(StatusCompte.SUSPENDED);
            compteRepository.save(compte);
            return true;
        }
        throw new CompteNotFoundException("Compte non trouvé pour suspension avec le RIB : " + rib);
    }

    @Override
    public List<Compte> findAllComptes() {
        return compteRepository.findAll();
    }


    public static String generateRib(Long numCompte) {
        final String CODE_BANQUE = "011 ";
        final String CODE_GUICHET = "780 ";
        final String CLE_RIB = " 96";
        String numCompteStr = String.format("%016d", numCompte);
        return CODE_BANQUE + CODE_GUICHET + numCompteStr + CLE_RIB;
    }

//    private final KafkaTemplate<String, AccountEvent> kafkaTemplate;


//    public void publishAccountEvent(AccountEvent accountEvent) {
//        kafkaTemplate.send("accounts-topic", accountEvent);  // Envoie l'événement au topic Kafka
//        System.out.println("Account event published: " + accountEvent);
//    }


    @Override
    public Compte updateBalance(String rib, BigDecimal newBalance) {
        Compte compte = compteRepository.findByRib(rib)
                .orElseThrow(() -> new CompteNotFoundException("Compte introuvable"));
        compte.setBalance(newBalance.doubleValue());
        return compteRepository.save(compte);
    }


}