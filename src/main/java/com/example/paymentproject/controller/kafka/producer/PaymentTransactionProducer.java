package com.example.paymentproject.controller.kafka.producer;

import com.example.paymentproject.model.entity.PaymentTransaction;
import com.example.paymentproject.model.entity.enums.PaymentTransactionCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentTransactionProducer {
    public final static String RESULT_TOPIC = "payment-command-result";

    private final static String PAYMENT_TRANSACTION_COMMAND_TYPE_HEADER = "command";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendCommandResult(String topic, String requestId, String message, PaymentTransactionCommand command) {
        var kafkamessage = buildMessage(topic,requestId,message, command);

        kafkaTemplate.send(
             kafkamessage
        );
        log.info("Successfully sent command result: {}", kafkamessage);

    }


    public Message<String> buildMessage(String topic, String requestId, String message, PaymentTransactionCommand command){
        return MessageBuilder.withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.KEY, requestId)
                .setHeader(PAYMENT_TRANSACTION_COMMAND_TYPE_HEADER, command.toString())
                .build();
    }

}
