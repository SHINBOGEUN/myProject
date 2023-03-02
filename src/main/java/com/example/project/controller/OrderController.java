package com.example.project.controller;

import com.example.project.entity.Order;
import com.example.project.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/project/ex/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    @ApiOperation("주문 데이터 저장하기")
    public ResponseEntity save(@RequestBody Order order){
        try{
            return new ResponseEntity(orderService.insertOrder(order), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
}
