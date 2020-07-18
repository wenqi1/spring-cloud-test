package com.learn.userservice.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.learn.userservice.entity.Result;
import com.learn.userservice.entity.UserInfo;
import com.learn.userservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 *
 * @since 2020/7/11
 */
@RestController
@RequestMapping("userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping
    public Result addUserInfo(UserInfo userInfo) {
        userInfoService.addUserInfo(userInfo);
        return Result.success(null);
    }

    @GetMapping
    public Result queryUserInfo(UserInfo userInfo) {
        UserInfo user = userInfoService.queryUserInfo(userInfo);
        return Result.success(user);
    }
}
