package net.mahdi.financeservice.models;

import lombok.Data;

@Data
public class CreditSimulation {
    private double montant;
    private int duree;
    private int differe;
    private double taux;
    private double mensualite;
}