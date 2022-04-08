package com.itheima.service;

import com.itheima.pojo.User;

/**
 * @author 侯孟珂
 * @date 2022/4/7-18:02
 */
public interface UserService {
     public User findByUserName(String username);
}
