package net.mahdi.clientservice.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import net.mahdi.clientservice.enums.StatusCompte;
import net.mahdi.clientservice.enums.TypeCompte;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comptes")
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String rib;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private Date createdDate = new Date();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCompte status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeCompte typeCompte;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}