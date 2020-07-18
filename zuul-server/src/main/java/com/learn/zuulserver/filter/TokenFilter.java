package com.learn.zuulserver.filter;

import com.learn.zuulserver.config.SelfUserDetails;
import com.learn.zuulserver.util.JWTTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class TokenFilter extends OncePerRequestFilter {

    private RequestMatcher requiresAuthenticationRequestMatcher;

    public TokenFilter() {
        // 拦截header中带Authorization的请求
        this.requiresAuthenticationRequestMatcher = new RequestHeaderRequestMatcher("Authorization");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 判断请求头是否携带token
        if (!requiresAuthentication(httpServletRequest, httpServletResponse)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // 从Authorization头中获取token
        String authInfo = httpServletRequest.getHeader("Authorization");
        String token = authInfo.substring("Bearer ".length());

        Claims claims = JWTTokenUtil.parseToken(token, "secret");
        // 判断token是否过期
        if(claims.getExpiration().after(new Date())){
            // 从token中获取用户名
            String username = claims.getSubject();
            // 从token中获取roles
            List authorities = (List)claims.get("roles");
            HashSet roles = new HashSet();
            for (Object role:authorities) {
                GrantedAuthority grantedAuthority= new SimpleGrantedAuthority((String) role);
                roles.add(grantedAuthority);
            }

            // 创建一个UserDetails
            SelfUserDetails userDetails = new SelfUserDetails();
            userDetails.setUsername(username);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDetails.setPassword(passwordEncoder.encode(username));
            userDetails.setAuthorities(roles);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
            throw new RuntimeException("token已过期");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    protected boolean requiresAuthentication(HttpServletRequest request,
                                             HttpServletResponse response) {
        return requiresAuthenticationRequestMatcher.matches(request);
    }


}