package net.mahdi.demandescreditservice.entities;
import jakarta.persistence.*;
import net.mahdi.demandescreditservice.enums.LoanApplicationStatus;
import net.mahdi.demandescreditservice.enums.LoanIntent;
import net.mahdi.demandescreditservice.enums.PersonHomeOwnership;


@Entity
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name ;

    private String cin ;

    private int personAge;

    private double personIncome;

    private int personEmpLength;

    private double loanAmnt;



    private String cbPersonDefaultOnFile;

    private int cbPersonCredHistLength;
    private  String email;

    @Enumerated(EnumType.STRING)
    private LoanApplicationStatus status = LoanApplicationStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private LoanIntent loanIntent;

    @Enumerated(EnumType.STRING)
    private PersonHomeOwnership personHomeOwnership;

    public LoanApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(LoanApplicationStatus status) {
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPersonAge() {
        return personAge;
    }

    public void setPersonAge(int personAge) {
        this.personAge = personAge;
    }

    public double getPersonIncome() {
        return personIncome;
    }

    public void setPersonIncome(double personIncome) {
        this.personIncome = personIncome;
    }

    public PersonHomeOwnership getPersonHomeOwnership() {
        return personHomeOwnership;
    }

    public void setPersonHomeOwnership(PersonHomeOwnership personHomeOwnership) {
        this.personHomeOwnership = personHomeOwnership;
    }

    public int getPersonEmpLength() {
        return personEmpLength;
    }

    public void setPersonEmpLength(int personEmpLength) {
        this.personEmpLength = personEmpLength;
    }

    public LoanIntent getLoanIntent() {
        return loanIntent;
    }

    public void setLoanIntent(LoanIntent loanIntent) {
        this.loanIntent = loanIntent;
    }

    public double getLoanAmnt() {
        return loanAmnt;
    }

    public void setLoanAmnt(double loanAmnt) {
        this.loanAmnt = loanAmnt;
    }



    public String getCbPersonDefaultOnFile() {
        return cbPersonDefaultOnFile;
    }

    public void setCbPersonDefaultOnFile(String cbPersonDefaultOnFile) {
        this.cbPersonDefaultOnFile = cbPersonDefaultOnFile;
    }

    public int getCbPersonCredHistLength() {
        return cbPersonCredHistLength;
    }

    public void setCbPersonCredHistLength(int cbPersonCredHistLength) {
        this.cbPersonCredHistLength = cbPersonCredHistLength;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public  String getEmail(){
        return email;
    }
}
