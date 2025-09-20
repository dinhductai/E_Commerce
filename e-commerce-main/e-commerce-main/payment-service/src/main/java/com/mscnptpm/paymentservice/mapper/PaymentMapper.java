package com.mscnptpm.paymentservice.mapper;

import com.mscnptpm.paymentservice.dto.request.PaymentRequest;
import com.mscnptpm.paymentservice.dto.response.PaymentResponse;
import com.mscnptpm.paymentservice.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Payment toEntity(PaymentRequest dto);

    PaymentResponse toDTO(Payment entity);

    List<PaymentResponse> toDTOs(List<Payment> entities);
}
