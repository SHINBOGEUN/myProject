package com.example.project.controller;

import com.example.project.config.JwtTokenProvider;
import com.example.project.dto.LoginDTO;
import com.example.project.dto.MemberDto;
import com.example.project.dto.TokenDto;
import com.example.project.entity.Member;
import com.example.project.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity signUp(@RequestBody MemberDto memberDto){
        try {
            Member member = memberService.insertMember(memberDto);
            return new ResponseEntity(member, HttpStatus.OK);
        }catch (Exception e){
            log.error(e);
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/token")
    @ApiOperation(value = "로그인 성공 시 토큰 생성")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO){
        try {
            return new ResponseEntity(memberService.getToken(loginDTO), HttpStatus.OK);
        }catch (Exception e){
            log.error(e);
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/me")
    @ApiOperation(value = "로그인 한 유저의 정보 가져오기")
    public ResponseEntity loginInfo(HttpServletRequest request){
        try {
            return new ResponseEntity(memberService.getLoginInfo(request), HttpStatus.OK);
        }catch (Exception e){
            log.error(e);
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
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

    @PostMapping("/regeneration")
    @ApiOperation(value = "토큰 재생성")
    public ResponseEntity regenerationToken(HttpServletRequest request){
        try {
            return new ResponseEntity(memberService.getGenerrationToken(request), HttpStatus.OK);
        }catch (Exception e){
            log.error(e);
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/delete")
    @ApiOperation(value = "토큰 삭제")
    public ResponseEntity logout(HttpServletRequest request){
        try {
            return new ResponseEntity(memberService.getLogoutToken(request), HttpStatus.OK);
        }catch (Exception e){
            log.error(e);
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/withdrawal")
    @ApiOperation(value = "회원 탈퇴")
    public ResponseEntity withdrawal(){

        return null;
    }
}
