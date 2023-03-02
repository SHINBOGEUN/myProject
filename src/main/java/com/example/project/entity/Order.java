package com.example.project.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "order_data")
@Data
@NoArgsConstructor
@Entity
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_idx", nullable = false)
    private Long orderIdx;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "order_time")
    @CreationTimestamp
    private LocalDateTime orderTime;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "order_name")
    private String orderName;
    @Column(name = "order_phone")
    private String orderPhone;
    @Column(name = "order_email")
    private String orderEmail;
    @Column(name = "total_amount")
    private Long totalAmount;
    @Column(name = "create_ts")
    @CreationTimestamp
    private LocalDateTime createTime;
    @Column(name = "update_ts")
    @UpdateTimestamp
    private LocalDateTime updateTime;
    @Column(name = "use_yn")
    private String isUse;
}
