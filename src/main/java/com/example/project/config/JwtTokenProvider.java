package com.example.project.config;

import com.example.project.dto.TokenDto;
import com.example.project.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
@Log4j2
public class JwtTokenProvider {
    @Value("${jwt.secretKey}")
    private String secretKey;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private long accessTokenValidTime = 2 * 60 * 60 * 1000L;  //2시간
    private long refreshTokenValidTime = 90 * 24 * 60 * 60 * 1000L; //90일

    private final UserDetailsService userDetailsService;



    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public TokenDto crateToken(String userId, Role roles){
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("ROLE", roles);
        String accessToken = generateToken(claims, accessTokenValidTime);
        String refreshToken = generateToken(claims, refreshTokenValidTime);
        ValueOperations<String, String> token = redisTemplate.opsForValue();
        token.set(userId, refreshToken, Duration.ofMillis(refreshTokenValidTime));
        return new TokenDto("Bearer", accessToken, refreshToken);
    }

    private String generateToken(Claims claims, long validTime){
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다.
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
    public TokenDto regenerationToken(String jwtToken){
        String userId = getUserId(jwtToken);
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        String savedRefreshToken = values.get(userId);
        if (savedRefreshToken !=null){
            Date now = new Date();
            Claims claims = Jwts.claims().setSubject(userId);
            Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
            log.info("==================="+body);
            String accessToken = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
            return new TokenDto("Bearer", accessToken, savedRefreshToken);
        }
        throw new IllegalStateException("오류 발생 입니다.");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    public boolean deleteToken(String jwtToken) {
        try {
            String userId = getUserId(jwtToken);
            String tokenOnly = jwtToken.substring(0, jwtToken.lastIndexOf('.') + 1);
            Date expiration = ((Claims)Jwts.parser().parse(tokenOnly).getBody()).getExpiration();
            Date now = new Date();
            Long expirationPeriod = (expiration.getTime() -  now.getTime()) / 1000;
            ValueOperations<String, String> values = redisTemplate.opsForValue();
            redisTemplate.delete(userId);
            values.set(jwtToken + "_BK", userId, Duration.ofSeconds(expirationPeriod));
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}


