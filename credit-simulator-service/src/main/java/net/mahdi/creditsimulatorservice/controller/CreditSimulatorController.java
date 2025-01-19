package net.mahdi.creditsimulatorservice.controller;


import net.mahdi.creditsimulatorservice.models.CreditSimulation;
import net.mahdi.creditsimulatorservice.service.CreditSimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class CreditSimulatorController {

    @Autowired
    private CreditSimulatorService simulatorService;

    @GetMapping("/simulate")
    public String showSimulator(Model model) {
        model.addAttribute("simulation", new CreditSimulation());
        return "simulator";
    }

    @PostMapping("/simulate")
    public String simulate(@ModelAttribute CreditSimulation simulation, Model model) {
        double mensualite = simulatorService.calculateMensualite(simulation);
        simulation.setMensualite(mensualite);
        model.addAttribute("simulation", simulation);
        return "simulator";
    }
}