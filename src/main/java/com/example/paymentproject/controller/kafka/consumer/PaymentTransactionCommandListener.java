package com.example.paymentproject.controller.kafka.consumer;

import com.example.paymentproject.model.entity.PaymentTransaction;
import com.example.paymentproject.model.entity.enums.PaymentTransactionCommand;
import com.example.paymentproject.service.handler.PaymentTransactionCommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentTransactionCommandListener {

    private final Map<PaymentTransactionCommand, PaymentTransactionCommandHandler> commandHandlers;

    @KafkaListener(topics = "payment-command", containerFactory = "kafkaListenerContainerFactory")
    public void consumePaymentTransaction(ConsumerRecord<String, String> record) {
        var command = getPaymentTransactionCommand(record);
        var handler = commandHandlers.get(command);
        if (handler != null) {
            throw new IllegalArgumentException("Unsupported payment transaction command, record: " + command);
        }

    }

    private PaymentTransactionCommand getPaymentTransactionCommand(ConsumerRecord<String, String> record) {
        var commandHeader =  record.headers().lastHeader("command");
        if (commandHeader != null) {
            return PaymentTransactionCommand.fromString(new String(commandHeader.value()));
        }
        return PaymentTransactionCommand.UNKNOW;
    }

}
