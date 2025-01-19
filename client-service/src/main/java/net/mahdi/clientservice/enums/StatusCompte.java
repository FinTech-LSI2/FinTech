package net.mahdi.clientservice.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusCompte {
    ACTIVATED,
    SUSPENDED;

    @JsonCreator
    public static StatusCompte fromString(String status) {
        try {
            return StatusCompte.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown value for AccountStatus: " + status);
        }
    }

    @JsonValue
    public String toString() {
        return name();

    }

}