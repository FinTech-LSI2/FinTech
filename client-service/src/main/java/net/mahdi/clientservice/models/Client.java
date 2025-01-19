package net.mahdi.clientservice.models;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(length = 50)
    private String CIN;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroTelephone;

    @Column(nullable = false)
    private LocalDate dateNaissance;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private String natureClient;

    @Column(nullable = false)
    private LocalDate inscriptionDate;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Compte> comptes = new ArrayList<>();

}