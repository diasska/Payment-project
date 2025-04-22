package com.example.paymentproject.mapper;

import com.example.paymentproject.model.dto.CancelPaymentTransactionResponse;
import com.example.paymentproject.model.entity.Refund;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefundMapper {
    Refund toEntity(Refund refund);

    CancelPaymentTransactionResponse toResponse(CancelPaymentTransactionResponse response);
}
