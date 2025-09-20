package com.mscnptpm.inventoryservice.service;

import com.mscnptpm.inventoryservice.dto.request.StockRequest;
import com.mscnptpm.inventoryservice.dto.response.StockResponse;
import com.mscnptpm.inventoryservice.entity.Stock;
import com.mscnptpm.inventoryservice.exception.ResourceNotFoundException;
import com.mscnptpm.inventoryservice.mapper.StockMapper;
import com.mscnptpm.inventoryservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository repository;
    private final StockMapper mapper;

    public StockResponse create(StockRequest req) {
        Stock stock = mapper.toEntity(req);
        return mapper.toDTO(repository.save(stock));
    }

    public List<StockResponse> getAll() {
        return mapper.toDTOs(repository.findAll());
    }

    public StockResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id " + id));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Stock not found with id " + id);
        }
        repository.deleteById(id);
    }
}
