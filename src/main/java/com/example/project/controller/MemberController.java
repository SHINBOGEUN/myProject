package com.example.project.controller;

import com.example.project.config.JwtTokenProvider;
import com.example.project.dto.MemberDto;
import com.example.project.repository.MemberRepository;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MemberController  {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    
    //회원가입
    @PostMapping("/join")
    @ApiOperation(value = "회원을 가입하는 메소드 성공 시 회원 정보를 리턴")
    public String join(@RequestBody MemberDto memberDto){
        return "s";
    }
}
