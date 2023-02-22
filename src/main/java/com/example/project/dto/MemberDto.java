package com.example.project.dto;

import lombok.*;

@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberDto {
    private String memberId;
    private String password;
    private String name;
    private String phone;
    private String email;
}