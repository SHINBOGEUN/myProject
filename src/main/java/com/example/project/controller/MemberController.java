package com.example.project.controller;

import com.example.project.config.JwtTokenProvider;
import com.example.project.dto.MemberDto;
import com.example.project.entity.Member;
import com.example.project.repository.MemberRepository;
import com.example.project.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Log4j2
public class MemberController  {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    //회원가입
    @PostMapping("/join")
    @ApiOperation(value = "회원을 가입하는 메소드 성공 시 회원 정보를 리턴")
    public Member join(@RequestBody MemberDto memberDto){
        try{
            Member member = memberService.insertMember(memberDto);
            log.info("Message : {}, Member ID : {}", "successfully joined", memberDto.getMemberId());
            member.setPassword("");  // 비밀번호 숨김
            return member;
        }catch (Exception e){
            log.error(e);
            return null;
        }
    }
}
