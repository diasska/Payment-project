package com.example.paymentproject.model.dto.enums;

import com.example.paymentproject.model.entity.enums.PaymentTransactionStatus;
import lombok.Getter;

@Getter
public enum CommandResultStatus {
    SUCCESS,
    FAILED;

    public static CommandResultStatus fromString(String string) {
        for (CommandResultStatus commandResultStatus : CommandResultStatus.values()) {
            if (commandResultStatus.name().equalsIgnoreCase(string)) {
                return commandResultStatus;
            }
        }
        throw  new IllegalArgumentException("Invalid CommandResultStatus: " + string);
    }
}
