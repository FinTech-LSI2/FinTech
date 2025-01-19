package net.mahdi.demandescreditservice.DTOs;

import net.mahdi.demandescreditservice.enums.LoanIntent;
import net.mahdi.demandescreditservice.enums.PersonHomeOwnership;

public class LoanApplicationDTO {


    private String cin;
    private String name;
    private int personAge;
    private double personIncome;
    private PersonHomeOwnership personHomeOwnership;
    private int personEmpLength;
    private LoanIntent loanIntent;
    private double loanAmnt;
    private double loanPercentIncome;
    private String cbPersonDefaultOnFile;
    private int cbPersonCredHistLength;
    private String email;


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

    // Getters and Setters
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

    public double getLoanPercentIncome() {
        return loanPercentIncome;
    }

    public void setLoanPercentIncome(double loanPercentIncome) {
        this.loanPercentIncome = loanPercentIncome;
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
     public  void setEmail(String email){
        this.email = email;

     }
     public String getEmail(){
        return email;
     }
}
