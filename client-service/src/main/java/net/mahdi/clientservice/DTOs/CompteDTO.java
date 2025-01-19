package net.mahdi.clientservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mahdi.clientservice.enums.StatusCompte;
import net.mahdi.clientservice.enums.TypeCompte;


public class CompteDTO {
    private Double balance;
    private StatusCompte status;
    private TypeCompte typeCompte;
    private Long idClient;

    // Champs spécifiques aux types de comptes
    private Double decouvert = 0.0; // Par défaut pour CompteCourant
    private Double interetRate = 0.0; // Par défaut pour CompteEpargne

    // Getters et Setters
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public StatusCompte getStatus() {
        return status;
    }

    public void setStatus(StatusCompte status) {
        this.status = status;
    }

    public TypeCompte getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(TypeCompte typeCompte) {
        this.typeCompte = typeCompte;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Double getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(Double decouvert) {
        this.decouvert = decouvert;
    }

    public Double getInteretRate() {
        return interetRate;
    }

    public void setInteretRate(Double interetRate) {
        this.interetRate = interetRate;
    }
}
