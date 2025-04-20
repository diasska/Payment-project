package com.example.paymentproject.model.entity.enums;

import lombok.Getter;

@Getter
public enum RefundStatus {
     COMPLETED,
     FAILED;

    public static RefundStatus fromString(String string) {
        for (RefundStatus refundStatus : RefundStatus.values()) {
            if (refundStatus.name().equalsIgnoreCase(string)) {
                return refundStatus;
            }
        }
        throw  new IllegalArgumentException("Invalid RefundStatus: " + string);
    }

}
