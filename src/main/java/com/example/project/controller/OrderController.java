package com.example.project.controller;

import com.example.project.Message;
import com.example.project.entity.Order;
import com.example.project.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;

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
    @PostMapping("/getOrderOne")
    @ApiOperation("한개 주문 데이터 조회")
    public ResponseEntity getOrderOne(@RequestParam long orderIdx){
        try{
            Order order = orderService.getOrderOne(orderIdx);
            log.info(order.toString());
            if (order == null){
                return ResponseEntity.status(HttpStatus.OK).body(new Message(HttpStatus.NO_CONTENT.value(),"주문 데이터가 없습니다.",null));
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(new Message(HttpStatus.OK.value(),"Success",order));
            }
        }catch (Exception e){
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
}
