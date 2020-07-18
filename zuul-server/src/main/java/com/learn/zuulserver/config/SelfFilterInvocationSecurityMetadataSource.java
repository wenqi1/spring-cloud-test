package com.learn.zuulserver.config;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

@Component
public class SelfFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private HashMap<String, Collection<ConfigAttribute>> map =null;

    public void loadResourceDefine(){
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        // 从数据库获取所有资源各自访问需要的权限(这里只是模拟，未查数据库)
        array = new ArrayList<>();
        cfg = new SecurityConfig("ROLE_ADMIN");
        // 此处添加的信息将会作为SelfAccessDecisionManager类decide()的第三个参数。
        array.add(cfg);
        // 用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
        map.put("/admin/test", array);

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if(map == null){
            loadResourceDefine();
        }

        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for(Iterator<String> item = map.keySet().iterator(); item.hasNext(); ) {
            resUrl = item.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if(matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
