package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author 侯孟珂
 * @date 2022/4/7-18:27
 * 用户服务
 */

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findByUserName(String username) {
        User user = userDao.findByUserName(username);
        if (user == null){
            return null;
        }
        Integer userId = user.getId();
        Set<Role> roles = roleDao.findByUserId(userId);
        for (Role role : roles) {

            Integer roleId = role.getId();
            Set<Permission> permissions = permissionDao.findByRoleId(roleId);
            role.setPermissions(permissions);

        }
        user.setRoles(roles);
        return user;
    }
}
