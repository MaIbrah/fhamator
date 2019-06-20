package com.sqli.chatUI.config;


import static com.sqli.chatUI.config.JwtConstant.EXPIRATION;
import static com.sqli.chatUI.config.JwtConstant.JWT_HEADER_NAME;
import static com.sqli.chatUI.config.JwtConstant.SECRET;
import static com.sqli.chatUI.config.JwtConstant.HEADER_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqli.chatUI.models.LoginRequest;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        try {
            LoginRequest user = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (IOException e) {
            throw new InvalidUsernamePasswordException("Bad credentials, invalid login or password");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        User user = (User) authResult.getPrincipal();
        List<String> roles = new ArrayList<>();
        authResult.getAuthorities().forEach(a -> roles.add(a.getAuthority()));
        String jwt = JWT.create()
            .withIssuer(request.getRequestURI())
            .withSubject(user.getUsername())
            .withArrayClaim("roles", roles.toArray(new String[roles.size()]))
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
            .sign(Algorithm.HMAC256(SECRET));

        response.addHeader(JWT_HEADER_NAME, HEADER_PREFIX+jwt);
    }

}