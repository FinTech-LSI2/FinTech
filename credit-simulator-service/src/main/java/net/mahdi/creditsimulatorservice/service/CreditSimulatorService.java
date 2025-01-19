package net.mahdi.creditsimulatorservice.service;

import net.mahdi.creditsimulatorservice.models.CreditSimulation;
import org.springframework.stereotype.Service;

@Service
public class CreditSimulatorService {

    public double calculateMensualite(CreditSimulation simulation) {
        double tauxMensuel = simulation.getTaux() / 12 / 100;
        double dureeEnMois = simulation.getDuree() * 12;
        double mensualite = (simulation.getMontant() * tauxMensuel * Math.pow(1 + tauxMensuel, dureeEnMois))
                / (Math.pow(1 + tauxMensuel, dureeEnMois) - 1);

        return Math.round(mensualite * 100.0) / 100.0;
    }}