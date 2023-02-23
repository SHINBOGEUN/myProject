package com.example.project.controller;

import com.example.project.config.JwtTokenProvider;
import com.example.project.dto.LoginDTO;
import com.example.project.dto.MemberDto;
import com.example.project.dto.TokenDto;
import com.example.project.entity.Member;
import com.example.project.repository.MemberRepository;
import com.example.project.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Log4j2
@RequestMapping("/project/ex/authentication")
public class MemberController  {
    @Autowired
    private MemberService memberService;

    //회원가입
    @PostMapping("/join")
    @ApiOperation(value = "회원을 가입하는 메소드 성공 시 회원 정보를 리턴")
    public Member signUp(@RequestBody MemberDto memberDto){
        try{
            Member member = memberService.insertMember(memberDto);
            log.info("Message : {}, Member ID : {}", "successfully joined", memberDto.toString());
            return member;
        }catch (Exception e){
            log.error(e);
            return null;
        }
    }
    @PostMapping("/token")
    @ApiOperation(value = "로그인 성공 시 토큰 생성")
    public TokenDto login(@RequestBody LoginDTO loginDTO){
        try {
            return memberService.getToken(loginDTO);
        }catch (Exception e){
            log.error(e);
            return null;
        }
    }

    @GetMapping("/me")
    @ApiOperation(value = "로그인 한 유저의 정보 가져오기")
    public Member loginInfo(HttpServletRequest request){
        try {
            return memberService.getLoginInfo(request);
        }catch (Exception e){
            log.error(e);
            return null;
        }
    }
    @PostMapping("/update")
    @ApiOperation(value = "회원 정보 수정")
    public Member updateMemberInfo(@RequestBody MemberDto memberDto){
        try {
            Member postUpdateMember = memberService.updateMember(memberDto);
            postUpdateMember.setPassword("");
            return postUpdateMember;
        }catch (Exception e){
            log.error(e);
            return null;
        }
    }

}
