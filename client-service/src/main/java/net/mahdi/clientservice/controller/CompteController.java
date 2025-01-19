package net.mahdi.clientservice.controller;

import net.mahdi.clientservice.DTOs.CompteDTO;
import net.mahdi.clientservice.exception.CompteNotFoundException;
import net.mahdi.clientservice.models.Compte;
import net.mahdi.clientservice.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/client/comptes")
public class CompteController {

    @Autowired
    private CompteService compteService;

    // Endpoint pour créer un compte

    @PostMapping
    public ResponseEntity<Map<String, String>> createAccount(@RequestBody CompteDTO compteDto) {
        try {
            compteService.createAccount(compteDto);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Compte created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Erreur lors de la création du compte");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint pour récupérer tous les comptes d'épargne
    @GetMapping("/epargne")
    public ResponseEntity<List<Compte>> getComptesEpargnes() {
        List<Compte> comptes = compteService.findComptesEpargnes();
        return new ResponseEntity<>(comptes, HttpStatus.OK);
    }

    // Endpoint pour récupérer tous les comptes courants
    @GetMapping("/courant")
    public ResponseEntity<List<Compte>> getComptesCourants() {
        List<Compte> comptes = compteService.findComptesCourants();
        return new ResponseEntity<>(comptes, HttpStatus.OK);
    }

    // Endpoint pour récupérer un compte par son RIB
    @GetMapping("/{rib}")
    public ResponseEntity<Compte> getCompteByRib(@PathVariable String rib) {
        try {
            Compte compte = compteService.findOne(rib);
            return new ResponseEntity<>(compte, HttpStatus.OK);
        } catch (CompteNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pour activer un compte
    @PutMapping("/activer/{rib}")
    public ResponseEntity<String> activateCompte(@PathVariable String rib) {
        try {
            boolean isActivated = compteService.activateCompte(rib);
            if (isActivated) {
                return new ResponseEntity<>("Compte activé avec succès", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Erreur lors de l'activation du compte", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (CompteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pour suspendre un compte
    @PutMapping("/suspendre/{rib}")
    public ResponseEntity<String> suspendCompte(@PathVariable String rib) {
        try {
            boolean isSuspended = compteService.suspendCompte(rib);
            if (isSuspended) {
                return new ResponseEntity<>("Compte suspendu avec succès", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Erreur lors de la suspension du compte", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (CompteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pour récupérer tous les comptes
    @GetMapping
    public ResponseEntity<List<Compte>> getAllComptes() {
        List<Compte> comptes = compteService.findAllComptes();
        return new ResponseEntity<>(comptes, HttpStatus.OK);
    }
}