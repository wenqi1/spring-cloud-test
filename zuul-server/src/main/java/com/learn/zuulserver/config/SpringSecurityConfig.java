package com.learn.zuulserver.config;

import com.learn.zuulserver.filter.LoginFilter;
import com.learn.zuulserver.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SelfAuthenticationEntryPoint selfAuthenticationEntryPoint;

    @Autowired
    private SelfUserDetailsService userDetailsService;

    @Autowired
    private SelfFilterSecurityInterceptor selfFilterSecurityInterceptor;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf验证
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 处理权限不足
                .httpBasic().authenticationEntryPoint(selfAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                // 所有资源都需要登录
                .anyRequest().authenticated()
                .and()
                // 添加一个过滤器所有访问/login的请求交给 JWTLoginFilter来处理
                .addFilterBefore(new LoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(selfFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                // 添加一个过滤器验证其他请求的Token是否合法
                .addFilterBefore(new TokenFilter(),
                        UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
