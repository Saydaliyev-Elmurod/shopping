package com.example.shopping.util;

import com.example.shopping.enums.ProfileRole;
import com.example.shopping.exp.MethodNotAllowedException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
    private static final String secretKey = "dasda143mazgi";

    public static String encode(String email, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("email", email);
        jwtBuilder.claim("role", role);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("Kunuz test portali");
        return jwtBuilder.compact();
    }

    public static String encodeToUpdateEmail(String email, Integer pId) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("email", email);
        jwtBuilder.claim("id", pId);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("Kunuz test portali");
        return jwtBuilder.compact();
    }

    public static String encode(String text) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);
        jwtBuilder.claim("email", text);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("Kunuz test portali");
        return jwtBuilder.compact();
    }

    public static JwtDto decode(String token) {

        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);
        Jws<Claims> jws = jwtParser.parseClaimsJws(token);
        Claims claims = jws.getBody();
        String email = (String) claims.get("email");
        String role = (String) claims.get("role");
        ProfileRole profileRole = ProfileRole.valueOf(role);
        return new JwtDto(email, profileRole);

    }
    public static JwtDto decodeToUpdateEmail(String token) {
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);
        Jws<Claims> jws = jwtParser.parseClaimsJws(token);
        Claims claims = jws.getBody();
        String email = (String) claims.get("email");
        Integer pId = (Integer) claims.get("id");
        return new JwtDto(email, pId);
    }

    public static String decodeEmailVerification(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);
            Jws<Claims> jws = jwtParser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            return (String) claims.get("email");
        } catch (JwtException e) {
            e.printStackTrace();
        }
        throw new MethodNotAllowedException("Jwt exception");
    }

    public static JwtDto getJwtDto(String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        return JwtUtil.decode(jwt);
    }

    public static JwtDto getJwtDto(String authorization, ProfileRole... roleList) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDto jwtDTO = JwtUtil.decode(jwt);
        boolean roleFound = false;
        for (ProfileRole role : roleList) {
            if (jwtDTO.getRole().equals(role)) {
                roleFound = true;
                break;
            }
        }
        if (!roleFound) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return jwtDTO;
    }

    public static void checkForRequiredRole(HttpServletRequest request, ProfileRole... roleList) {
        ProfileRole jwtRole = (ProfileRole) request.getAttribute("role");
        boolean roleFound = false;
        for (ProfileRole role : roleList) {
            if (jwtRole.equals(role)) {
                roleFound = true;
                break;
            }
        }
        if (!roleFound) {
            throw new MethodNotAllowedException("Method not allowed");
        }
    }
}
