package com.mscnptpm.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscnptpm.orderservice.dto.OrderDTO;
import com.mscnptpm.orderservice.dto.OrderLineDTO;
import com.mscnptpm.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void createOrder_success() throws Exception {
        OrderDTO input = OrderDTO.builder()
                .userId(Long.valueOf(1))
                .orderLines(List.of(OrderLineDTO.builder()
                        .productId(1L).quantity(2).build()))
                .build();

        OrderDTO output = OrderDTO.builder()
                .id(100L)
                .userId(Long.valueOf(1))
                .status("PENDING")
                .orderLines(input.getOrderLines())
                .build();

        Mockito.when(orderService.createOrder(any(OrderDTO.class))).thenReturn(output);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.userId").value("user123"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void createOrder_validationFail() throws Exception {
        // thiếu userId và quantity = 0
        OrderDTO invalid = OrderDTO.builder()
                .userId(Long.valueOf(1))
                .orderLines(List.of(OrderLineDTO.builder()
                        .productId(null).quantity(0).build()))
                .build();

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.userId").value("userId không được để trống"))
                .andExpect(jsonPath("$.errors['orderLines[0].productId']").value("productId không được để trống"))
                .andExpect(jsonPath("$.errors['orderLines[0].quantity']").value("Số lượng phải >= 1"));
    }
}
