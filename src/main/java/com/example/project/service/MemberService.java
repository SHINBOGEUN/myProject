package com.example.project.service;

import com.example.project.config.JwtTokenProvider;
import com.example.project.dto.LoginDTO;
import com.example.project.dto.MemberDto;
import com.example.project.dto.TokenDto;
import com.example.project.entity.Member;
import com.example.project.entity.Role;
import com.example.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public Member insertMember(MemberDto memberDto){
        Member member = new Member();
        member.setMemberId(memberDto.getMemberId());
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        member.setMemberRole(Role.ROLE_USER);
        member.setEmail(memberDto.getEmail());
        member.setName(memberDto.getName());
        member.setUseYn("Y");
        return memberRepository.save(member);
    }

    public TokenDto getToken(LoginDTO loginDTO){
        Member member = memberRepository.findById(loginDTO.getMemberId())
                .orElseThrow(() -> new IllegalStateException("Not found member element"));
        if(!passwordEncoder.matches(loginDTO.getPassword(), member.getPassword())){
            throw new IllegalStateException("Invalid password");
        }
        return jwtTokenProvider.crateToken(member.getMemberId(), member.getMemberRole());

    }
}
