package com.example.project.service;

import com.example.project.entity.Order;
import com.example.project.mapper.OrderMapper;
import com.example.project.repository.OrderRepository;
import com.example.project.util.CreateOrderNumber;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Log4j2
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderMapper orderMapper;

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

        return orderRepository.findById(orderIdx).orElse(null);
    }

    public Map getOrderList() {

        return null;
    }

    public Map<String, Object> orderListMybatis(Map<String, Object> param) {

        return orderMapper.orderList(param);

    }
}
