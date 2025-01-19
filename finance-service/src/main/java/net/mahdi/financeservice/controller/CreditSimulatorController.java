package net.mahdi.financeservice.controller;



import net.mahdi.financeservice.models.CreditSimulation;
import net.mahdi.financeservice.service.CreditSimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/finance/credit")
public class CreditSimulatorController {

    @Autowired
    private CreditSimulatorService simulatorService;

    @GetMapping("/simulate")
    public ResponseEntity<CreditSimulation> showSimulator() {
        CreditSimulation simulation = new CreditSimulation();
        return ResponseEntity.ok(simulation);
    }

    @PostMapping("/simulate")
    public ResponseEntity<CreditSimulation> simulate(@RequestBody CreditSimulation simulation) {
        double mensualite = simulatorService.calculateMensualite(simulation);
        simulation.setMensualite(mensualite);
        return ResponseEntity.ok(simulation);
    }
}
