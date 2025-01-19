package org.fstt.transactionclient.DTOs;



import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("PAIEMENT")
public class PaiementDTO extends TransactionDTO {
    private String numFacture ;
}
