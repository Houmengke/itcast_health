package com.itheima.dao;

import com.itheima.pojo.Role;

import java.util.Set;

/**
 * @author 侯孟珂
 * @date 2022/4/7-18:35
 */
public interface RoleDao {
     public Set<Role> findByUserId(Integer userId);
}
