package org.fstt.transactionclient.DTOs;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("VIREMENT")
public class VirementDTO extends TransactionDTO {

    @Column
    private String destinationRib; // RIB du destinataire
}
