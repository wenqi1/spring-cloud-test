package com.learn.userservice.service;

import com.learn.userservice.dao.UserInfoDao;
import com.learn.userservice.entity.UserInfo;
import com.learn.userservice.exception.ServiceException;
import com.learn.userservice.util.SnowflakeIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户管理
 *
 * @since 2020/7/11
 */
@Service
public class UserInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoService.class);

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;

    public void addUserInfo(UserInfo userInfo) {
        long id = snowflakeIdGenerator.nextId();
        userInfo.setId(id);
        try {
            userInfoDao.insert(userInfo);
        } catch (Exception e) {
            LOGGER.error("添加用户失败", e);
            throw new ServiceException("1001", e);
        }
    }

    public UserInfo queryUserInfo(UserInfo userInfo) {
        try {
            return userInfoDao.selectOne(userInfo);
        } catch (Exception e) {
            throw new ServiceException("1002", e);
        }
    }
}
