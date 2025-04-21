package com.example.paymentproject.model.entity.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum PaymentTransactionCommand {
    CREATE,
    REFUND,
    UNKNOW;

    public static PaymentTransactionCommand fromString(String string) {
        try{
            return PaymentTransactionCommand.valueOf(string);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return UNKNOW;
        }
    }

}
