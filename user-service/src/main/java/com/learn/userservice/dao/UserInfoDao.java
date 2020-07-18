package com.learn.userservice.dao;

import com.learn.userservice.entity.UserInfo;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * 用户信息
 *
 * @since 2020/7/11
 */
@Component
public interface UserInfoDao extends Mapper<UserInfo> {
}
