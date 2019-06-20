package com.sqli.chatUI.config;

public interface JwtConstant {
    String SECRET = "fhamatorSQLI2019";
    String JWT_HEADER_NAME="Authorization";
    long EXPIRATION=864_000_000;
    String HEADER_PREFIX="Bearer ";
}
