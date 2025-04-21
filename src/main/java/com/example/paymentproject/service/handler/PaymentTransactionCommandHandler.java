package com.example.paymentproject.service.handler;

import jakarta.transaction.Transactional;

public interface PaymentTransactionCommandHandler {


    @Transactional
    void process(String requestId, String message);
}
