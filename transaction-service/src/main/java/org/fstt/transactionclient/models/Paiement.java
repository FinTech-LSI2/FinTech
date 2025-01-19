package org.fstt.transactionclient.models;



import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("PAIEMENT")
public class Paiement extends Transaction {
    private String numFacture ;
}
