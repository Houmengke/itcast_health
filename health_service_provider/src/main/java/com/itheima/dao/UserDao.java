package com.itheima.dao;

import com.itheima.pojo.User;

/**
 * @author 侯孟珂
 * @date 2022/4/7-18:30
 */
public interface UserDao {
    public User findByUserName(String username);
}
