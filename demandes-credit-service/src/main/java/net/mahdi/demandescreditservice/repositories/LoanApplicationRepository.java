package net.mahdi.demandescreditservice.repositories;

import net.mahdi.demandescreditservice.entities.LoanApplication;
import net.mahdi.demandescreditservice.enums.LoanApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    boolean existsByNameAndCinAndEmailAndStatus(String name, String cin, String email, LoanApplicationStatus status);
}
