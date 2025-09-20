package com.mscnptpm.orderservice.repository;

import com.mscnptpm.orderservice.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
