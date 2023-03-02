package com.example.project.service;

import com.example.project.entity.Order;
import com.example.project.repository.OrderRepository;
import com.example.project.util.CreateOrderNumber;
import lombok.extern.log4j.Log4j2;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Log4j2
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order insertOrder(Order paramOrder){
        String randomNumber = CreateOrderNumber.createOrderNumber();
        Order order = new Order();
        order.setOrderNumber(randomNumber);
        order.setOrderId(paramOrder.getOrderId());
        order.setOrderEmail(paramOrder.getOrderEmail());
        order.setOrderPhone(paramOrder.getOrderPhone());
        order.setOrderName(paramOrder.getOrderName());
        order.setTotalAmount(paramOrder.getTotalAmount());
        order.setIsUse("Y");
        orderRepository.save(order);
        return order;
    }
    public Order getOrderOne(Long orderIdx){
        Order savedOrder = orderRepository.findById(orderIdx).orElse(null);
        return savedOrder;
    }
}
