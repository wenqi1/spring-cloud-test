package com.learn.zuulserver.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Iterator;

@Component
public class SelfAccessDecisionManager implements AccessDecisionManager {

    /*判定是否拥有权限的决策方法
     *  authentication 是释CustomUserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
     *  object：包含客户端发起的请求的requset信息，可转换为
     *      HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     *  configAttributes：SelfFilterInvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，
     *      configAttributes中是用户访问的资源所需要的权限
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //configAttributes是否为空，为空则说明访问的资源无需权限
        if(null== configAttributes || configAttributes.size() <=0) {
            return;
        }
        ConfigAttribute c;
        String needRole;
        for(Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext(); ) {
            c = iter.next();
            needRole = c.getAttribute();
            for(GrantedAuthority ga : authentication.getAuthorities()) {
                if(needRole.trim().equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("no right");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }


}
