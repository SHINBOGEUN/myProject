package com.example.project.service;

import com.example.project.entity.Order;
import com.example.project.repository.OrderRepository;
import com.example.project.util.CreateOrderNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
}
