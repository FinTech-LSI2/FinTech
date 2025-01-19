package net.mahdi.clientservice.DTOs;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ClientDTO {
    private String lastname;
    private String firstname;
    private String CIN;
    private String email;
    private String numeroTelephone;
    private LocalDate dateNaissance;
    private String adresse;
    private String natureClient;
    private String password;
}