package org.fstt.transactionclient.models;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("VIREMENT")
public class Virement extends Transaction {

    @Column
    private String destinationRib; // RIB du destinataire
}
