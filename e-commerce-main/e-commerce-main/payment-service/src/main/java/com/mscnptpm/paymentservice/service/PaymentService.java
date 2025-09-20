package com.mscnptpm.paymentservice.service;

import com.mscnptpm.paymentservice.dto.request.PaymentRequest;
import com.mscnptpm.paymentservice.dto.response.PaymentResponse;
import com.mscnptpm.paymentservice.entity.Payment;
import com.mscnptpm.paymentservice.exception.ResourceNotFoundException;
import com.mscnptpm.paymentservice.mapper.PaymentMapper;
import com.mscnptpm.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    public PaymentResponse create(PaymentRequest req) {
        Payment entity = mapper.toEntity(req);
        entity.setStatus("AUTHORIZED"); // demo
        entity.setCreatedAt(Instant.now());
        return mapper.toDTO(repository.save(entity));
    }

    public List<PaymentResponse> getAll() {
        return mapper.toDTOs(repository.findAll());
    }

    public PaymentResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + id));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Payment not found with id " + id);
        }
        repository.deleteById(id);
    }
}
