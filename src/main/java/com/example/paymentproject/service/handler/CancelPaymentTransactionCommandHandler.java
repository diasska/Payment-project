package com.example.paymentproject.service.handler;

import com.example.paymentproject.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelPaymentTransactionCommandHandler implements PaymentTransactionCommandHandler {

    private final JsonConverter jsonConverter;

    @Override
    public void process(String requestId, String message) {

    }
}
