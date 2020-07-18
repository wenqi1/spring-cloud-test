package com.learn.zuulserver.filter;

import com.alibaba.fastjson.JSON;
import com.learn.zuulserver.config.SelfUserDetails;
import com.learn.zuulserver.entity.Result;
import com.learn.zuulserver.util.JWTTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    public LoginFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) throws AuthenticationException {
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");

        //封装到token中提交
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);

        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException {
        SelfUserDetails userDetails = (SelfUserDetails) authResult.getPrincipal();
        //用户名
        String username = userDetails.getUsername();
        //用户角色
        Set<? extends GrantedAuthority> authorities = (Set<? extends GrantedAuthority>)userDetails.getAuthorities();
        String[] roleStr = new String[authorities.size()];
        int i = 0;
        for (GrantedAuthority g:authorities) {
            roleStr[i] = g.getAuthority();
            i++;
        }
        Map<String, Object> roles = new HashMap<>();
        roles.put("roles",roleStr);
        //创建JWT
        String jwt = JWTTokenUtil.generateToken(null, username, roles, 60*60*1000, "secret");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(Result.success(jwt)));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(Result.error("", "登入失败")));
    }
}
