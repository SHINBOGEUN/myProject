package com.example.project.config;


import com.example.project.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");  // json 형태로 반환
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); //상태코드 401에러로 set
        response.getWriter().write(objectMapper.writeValueAsString(new Message(HttpStatus.UNAUTHORIZED, "Unauthorized", null)));
    }
}