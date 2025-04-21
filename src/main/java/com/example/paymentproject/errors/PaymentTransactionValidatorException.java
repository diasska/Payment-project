package com.example.paymentproject.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PaymentTransactionValidatorException extends RuntimeException {
    private List<String> errors;

}
