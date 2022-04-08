package com.itheima.dao;

import com.itheima.pojo.Permission;

import java.util.Set;

/**
 * @author 侯孟珂
 * @date 2022/4/7-18:40
 */
public interface PermissionDao {
     public Set<Permission> findByRoleId(Integer roleId);
}
