package com.example.project.service;

import com.example.project.dto.MemberDto;
import com.example.project.entity.Member;
import com.example.project.entity.Role;
import com.example.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;

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

}
