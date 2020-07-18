package com.learn.zuulserver.config;

import com.alibaba.fastjson.JSON;
import com.learn.zuulserver.entity.Result;
import com.learn.zuulserver.entity.UserInfo;
import com.learn.zuulserver.feignService.UserServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class SelfUserDetailsService implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceFeign userServiceFeign;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        SelfUserDetails selfUserDetails = null;
        Result result = userServiceFeign.queryUserInfo(phone);
        if (result.getCode() == 1) {
            UserInfo userInfo = JSON.parseObject(JSON.toJSONString(result.getData()), UserInfo.class);
            selfUserDetails = new SelfUserDetails();
            selfUserDetails.setUsername(phone);
            selfUserDetails.setPassword(passwordEncoder.encode(userInfo.getPassword()));

            Set authoritiesSet = new HashSet();
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
            authoritiesSet.add(authority);
            selfUserDetails.setAuthorities(authoritiesSet);
        }

        return selfUserDetails;
    }
}
