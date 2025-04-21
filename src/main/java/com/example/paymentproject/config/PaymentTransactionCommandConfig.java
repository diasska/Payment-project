package com.example.paymentproject.config;

import com.example.paymentproject.model.entity.enums.PaymentTransactionCommand;
import com.example.paymentproject.service.handler.CancelPaymentTransactionCommandHandler;
import com.example.paymentproject.service.handler.CreatePaymentTransactionCommandHandler;
import com.example.paymentproject.service.handler.PaymentTransactionCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaymentTransactionCommandConfig {

    @Bean
    public Map<PaymentTransactionCommand, PaymentTransactionCommandHandler> commandHandlers(CreatePaymentTransactionCommandHandler createPaymentTransactionCommandHandler,
                                                                                            CancelPaymentTransactionCommandHandler cancelPaymentTransactionCommandHandler) {
        Map<PaymentTransactionCommand, PaymentTransactionCommandHandler> commandHandlers = new HashMap<>();
        commandHandlers.put(PaymentTransactionCommand.CREATE, createPaymentTransactionCommandHandler);
        commandHandlers.put(PaymentTransactionCommand.REFUND, cancelPaymentTransactionCommandHandler);

        return commandHandlers;
    }
}
