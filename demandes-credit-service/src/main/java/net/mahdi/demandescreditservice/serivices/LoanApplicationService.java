package net.mahdi.demandescreditservice.serivices;

import net.mahdi.demandescreditservice.DTOs.LoanApplicationDTO;
import net.mahdi.demandescreditservice.entities.LoanApplication;
import net.mahdi.demandescreditservice.enums.LoanApplicationStatus;
import net.mahdi.demandescreditservice.repositories.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final String FLASK_API_URL = "http://127.0.0.1:5000/api/prediction";
    private final RestTemplate restTemplate;

    @Autowired
    public LoanApplicationService(LoanApplicationRepository loanApplicationRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.restTemplate = new RestTemplate();
    }


    public LoanApplication createLoanApplication(LoanApplicationDTO loanApplicationDTO) {
        // Vérifier si une demande en attente existe déjà
        boolean exists = loanApplicationRepository.existsByNameAndCinAndEmailAndStatus(
                loanApplicationDTO.getName(),
                loanApplicationDTO.getCin(),
                loanApplicationDTO.getEmail(),
                LoanApplicationStatus.PENDING
        );

        if (exists) {
            throw new IllegalArgumentException("Une demande de crédit en attente existe déjà pour ces informations.");
        }

        // Si aucune demande en attente n'existe, créer une nouvelle demande
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setCin(loanApplicationDTO.getCin());
        loanApplication.setName(loanApplicationDTO.getName());
        loanApplication.setPersonAge(loanApplicationDTO.getPersonAge());
        loanApplication.setPersonIncome(loanApplicationDTO.getPersonIncome());
        loanApplication.setPersonHomeOwnership(loanApplicationDTO.getPersonHomeOwnership());
        loanApplication.setPersonEmpLength(loanApplicationDTO.getPersonEmpLength());
        loanApplication.setLoanIntent(loanApplicationDTO.getLoanIntent());
        loanApplication.setLoanAmnt(loanApplicationDTO.getLoanAmnt());

        loanApplication.setCbPersonDefaultOnFile(loanApplicationDTO.getCbPersonDefaultOnFile());
        loanApplication.setCbPersonCredHistLength(loanApplicationDTO.getCbPersonCredHistLength());
        loanApplication.setEmail(loanApplicationDTO.getEmail());
        loanApplication.setStatus(LoanApplicationStatus.PENDING); // Définir le statut initial comme "PENDING"

        return loanApplicationRepository.save(loanApplication);
    }



    public LoanApplication getLoanApplication(Long id) {
        return loanApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan application not found"));
    }


    public void updateLoanApplication(LoanApplication loanApplication) {
        loanApplicationRepository.save(loanApplication);
    }


    public ResponseEntity<Map<String, String>> simulateLoanApplication(Long id) {
        LoanApplication application = getLoanApplication(id);

        // Prepare data for Flask API
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("person_age", application.getPersonAge());
        requestData.put("person_income", application.getPersonIncome());
        requestData.put("person_home_ownership", application.getPersonHomeOwnership());
        requestData.put("person_emp_length", application.getPersonEmpLength());
        requestData.put("loan_intent", application.getLoanIntent());
        requestData.put("loan_amnt", application.getLoanAmnt());
        requestData.put("cb_person_default_on_file", application.getCbPersonDefaultOnFile());
        requestData.put("cb_person_cred_hist_length", application.getCbPersonCredHistLength());

        try {
            // Call Flask API
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    FLASK_API_URL,
                    requestData,
                    Map.class
            );

            Map<String, String> result = new HashMap<>();
            result.put("simulationResult", response.getBody().get("prediction").toString());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Simulation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    public List<LoanApplication> getAllLoanApplications() {
        return loanApplicationRepository.findAll();
    }
}
