package org.fstt.transactionclient.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data @AllArgsConstructor @NoArgsConstructor
public class Account {
    private String rib;
    private BigDecimal balance;


}