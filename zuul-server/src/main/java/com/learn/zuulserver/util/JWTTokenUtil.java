package com.learn.zuulserver.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

public class JWTTokenUtil {

    /**
     * 采用salt加密
     * @param id
     * @param rules   不能为null
     * @param subject
     * @param expirationSeconds   单位为毫秒
     * @param salt
     * @return
     */
    public static String generateToken(String id, String subject,Map rules,int expirationSeconds, String salt) {

        return Jwts.builder()
                .setId(id)
                .setClaims(rules)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds))
                .signWith(SignatureAlgorithm.HS256, salt)
                .compact();
    }

    /**
     * 采用公钥加密
     * @param id
     * @param roles 不能为null
     * @param subject
     * @param expirationSeconds   单位为毫秒
     * @param publicKey
     * @return
     */
    public static String generateToken(String id, String subject, Map roles, int expirationSeconds, PublicKey publicKey) {
        return Jwts.builder()
                .setId(id)
                .setClaims(roles)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds))
                .signWith(SignatureAlgorithm.HS256, publicKey)
                .compact();
    }

    /**
     * 解析用salt加密的Token
     * @param token
     * @param salt
     * @return
     */
    public static Claims parseToken(String token, String salt){
        Claims claims = Jwts.parser()
                .setSigningKey(salt)
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

    /**
     * 解析用公钥加密的Token
     * @param token
     * @param privateKey
     * @return
     */
    public static Claims parseToken(String token, PrivateKey privateKey) {
        HashMap<String, Object> map = new HashMap<>();

        Claims claims = Jwts.parser()
                .setSigningKey(privateKey)
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

}
