package com.example.paymentproject.service.handler;

import com.example.paymentproject.controller.kafka.producer.PaymentTransactionProducer;
import com.example.paymentproject.mapper.PaymentTransactionMapper;
import com.example.paymentproject.model.dto.CreatePaymentTransactionRequest;
import com.example.paymentproject.model.entity.BankAccount;
import com.example.paymentproject.model.entity.enums.PaymentTransactionCommand;
import com.example.paymentproject.model.entity.enums.PaymentTransactionStatus;
import com.example.paymentproject.service.BankAccountService;
import com.example.paymentproject.service.PaymentTransactionService;
import com.example.paymentproject.service.PaymentTransactionValidator;
import com.example.paymentproject.util.JsonConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePaymentTransactionCommandHandler implements PaymentTransactionCommandHandler {

    private final JsonConverter jsonConverter;
    private final PaymentTransactionValidator paymentTransactionValidator;
    private final BankAccountService bankAccountService;
    private final PaymentTransactionMapper paymentTransactionMapper;
    private final PaymentTransactionService paymentTransactionService;
    private final PaymentTransactionProducer paymentTransactionProducer;


    @Override
    @Transactional
    public void process(String requestId, String message) {

        var request = jsonConverter.toObject(message, CreatePaymentTransactionRequest.class);
        paymentTransactionValidator.validateCreatePaymentTransactionRequest(request);

        var sourceBank = bankAccountService.findById(request.getSourceBankAccountId()).get();
        sourceBank.setBalance(
                sourceBank.getBalance().subtract(request.getAmount())
        );
        Optional<BankAccount> destinationBankAccount = Optional.empty();
        if(request.getDestinationBankAccountId() != null) {
            destinationBankAccount = bankAccountService.findById(request.getDestinationBankAccountId());
            destinationBankAccount.get().setBalance(
                    destinationBankAccount.get().getBalance().add(request.getAmount())
            );

        }
        var entity = paymentTransactionMapper.toEntity(request);
        entity.setSourceBankAccount(sourceBank);
        destinationBankAccount.ifPresent(entity::setDestinationBankAccount);
        entity.setPaymentTransactionStatus(PaymentTransactionStatus.SUCCESS);


       var savedEntity = paymentTransactionService.save(entity);
       paymentTransactionProducer.sendCommandResult(PaymentTransactionProducer.RESULT_TOPIC,
               requestId,
               jsonConverter.toJson(savedEntity),
               PaymentTransactionCommand.CREATE);

    }

}
