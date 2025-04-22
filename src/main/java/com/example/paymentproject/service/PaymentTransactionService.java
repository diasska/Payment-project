package com.example.paymentproject.service;

import com.example.paymentproject.mapper.PaymentTransactionMapper;
import com.example.paymentproject.model.dto.CreatePaymentTransactionRequest;
import com.example.paymentproject.model.dto.CreatePaymentTransactionResponse;
import com.example.paymentproject.model.entity.PaymentTransaction;
import com.example.paymentproject.repository.PaymentTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentTransactionService {
    private final PaymentTransactionRepository paymentTransactionRepository;


    @Transactional
    public PaymentTransaction save(PaymentTransaction paymentTransaction) {
        return paymentTransactionRepository.save(paymentTransaction);
    }

    @Transactional
    public Optional<PaymentTransaction> findById(Long id) {
        return paymentTransactionRepository.findById(id);
    }
}
