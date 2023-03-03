package com.example.project.controller;

import com.example.project.Message;
import com.example.project.entity.Order;
import com.example.project.service.OrderService;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
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
            log.error("exception msg", e);
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/get_order_one")
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
            log.error("exception msg", e);
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/get_order_list")
    @ApiOperation("주문 리스트 조회")
    public  ResponseEntity getOrderList(@RequestParam String startDate,
                                        @RequestParam String endDate,
                                        @RequestParam(required = false) String orderName) {
        try{
            Map<String, Object> param = new HashMap<>();
            LocalDateTime startDateTime;
            LocalDateTime endDateTime;
            try {
                startDateTime = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(0, 0, 0);
                endDateTime = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(23, 59, 59);
            } catch (Exception ex) {
                log.error("exception msg", ex);
                return ResponseEntity.status(HttpStatus.OK).body(new Message(HttpStatus.BAD_REQUEST.value(), "startDate, endDate 는 필수값이고 yyyyMMdd 형식입니다 ", null));
            }
            param.put("orderName", orderName);
            param.put("startDateTime", startDateTime);
            param.put("endDateTime", endDateTime);
            Map<String, Object> orderList = orderService.orderListMybatis(param);
            return ResponseEntity.status(HttpStatus.OK).body(new Message(HttpStatus.OK,"Success", orderList));
        }catch (Exception e){
            log.error("exception msg", e);
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
