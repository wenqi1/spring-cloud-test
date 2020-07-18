package com.learn.zuulserver.feignService;

import com.learn.zuulserver.entity.Result;
import com.learn.zuulserver.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
@RequestMapping("userInfo")
@Component
public interface UserServiceFeign {

    @PostMapping
    public Result addUserInfo(UserInfo userInfo);

    @GetMapping
    public Result queryUserInfo(@RequestParam(value = "phone") String phone);
}
