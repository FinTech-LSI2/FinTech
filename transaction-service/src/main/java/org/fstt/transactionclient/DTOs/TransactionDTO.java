package org.fstt.transactionclient.DTOs;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING)
public abstract class TransactionDTO {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String rib;

        @Column(nullable = false)
        private BigDecimal amount;

        @Column(nullable = false)
        private LocalDateTime transactionDate = LocalDateTime.now();

        @Column
        private String description;
}


