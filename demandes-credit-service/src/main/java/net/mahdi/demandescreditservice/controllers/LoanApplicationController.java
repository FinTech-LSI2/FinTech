package net.mahdi.demandescreditservice.controllers;
import io.swagger.annotations.ApiOperation;


import net.mahdi.demandescreditservice.DTOs.LoanApplicationDTO;
import net.mahdi.demandescreditservice.entities.LoanApplication;
import net.mahdi.demandescreditservice.enums.LoanApplicationStatus;
import net.mahdi.demandescreditservice.serivices.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/loan-applications")
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    @Autowired
    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    // Endpoint pour créer une nouvelle demande de prêt
    @PostMapping
    @ApiOperation("Create a new loan application")
    public ResponseEntity<LoanApplication> createLoanApplication(@RequestBody LoanApplicationDTO loanApplicationDTO) {
        LoanApplication createdLoanApplication = loanApplicationService.createLoanApplication(loanApplicationDTO);
        return new ResponseEntity<>(createdLoanApplication, HttpStatus.CREATED);
    }

    // Endpoint pour récupérer une demande de prêt par ID
    @GetMapping("/{id}")
    public ResponseEntity<LoanApplication> getLoanApplication(@PathVariable Long id) {
        LoanApplication loanApplication = loanApplicationService.getLoanApplication(id);
        return new ResponseEntity<>(loanApplication, HttpStatus.OK);
    }
    @PostMapping("/{id}/simulate")
    public ResponseEntity<Map<String, String>> simulateLoanApplication(@PathVariable Long id) {
        return loanApplicationService.simulateLoanApplication(id);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LoanApplication> rejectLoanApplication(@PathVariable Long id) {
        LoanApplication loanApplication = loanApplicationService.getLoanApplication(id);
        loanApplication.setStatus(LoanApplicationStatus.REJECTED);
        loanApplicationService.updateLoanApplication(loanApplication);
        return new ResponseEntity<>(loanApplication, HttpStatus.OK);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<LoanApplication> acceptLoanApplication(@PathVariable Long id) {
        LoanApplication loanApplication = loanApplicationService.getLoanApplication(id);
        loanApplication.setStatus(LoanApplicationStatus.ACCEPTED);
        loanApplicationService.updateLoanApplication(loanApplication);
        return new ResponseEntity<>(loanApplication, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<LoanApplication>> getAllLoanApplications() {
        List<LoanApplication> loanApplications = loanApplicationService.getAllLoanApplications();
        return new ResponseEntity<>(loanApplications, HttpStatus.OK);
    }
}
