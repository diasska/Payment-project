package com.example.paymentproject.service.handler;

import com.example.paymentproject.model.dto.CancelPaymentTransactionRequest;
import com.example.paymentproject.service.BankAccountService;
import com.example.paymentproject.service.PaymentTransactionService;
import com.example.paymentproject.service.PaymentTransactionValidator;
import com.example.paymentproject.service.RefundService;
import com.example.paymentproject.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelPaymentTransactionCommandHandler implements PaymentTransactionCommandHandler {

    private final JsonConverter jsonConverter;
    private final PaymentTransactionValidator paymentTransactionValidator;
    private final PaymentTransactionService paymentTransactionService;
    private final BankAccountService bankAccountService;
    private final RefundService refundService;


    @Override
    public void process(String requestId, String message) {
        var request = jsonConverter.toObject(message, CancelPaymentTransactionRequest.class);
        paymentTransactionValidator.validateCancelPaymentTransactionRequest(request);
        var sourceTransaction = paymentTransactionService.findById(request.getTransactionId()).get();

        var sourceBankAccount = sourceTransaction.getSourceBankAccount();
        sourceBankAccount.setBalance(
                sourceBankAccount.getBalance().add(request.getRefundedAmount())
        );

        if (sourceTransaction.getDestinationBankAccount() != null) {

            var destinationBankAccount = sourceTransaction.getDestinationBankAccount();
            destinationBankAccount.setBalance(
                    destinationBankAccount.getBalance().subtract(request.getRefundedAmount())
            );
        }

    }
}
