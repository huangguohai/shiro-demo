package com.imooc.service;

import com.imooc.vm.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户相关的服务
 */
@Service
public class UserService {

    // 暂时不访问数据库，使用此模拟数据
    Map<String, User> userMap = new HashMap<String, User>();
    Map<String, LoginInfo> loginMap = new HashMap<String, LoginInfo>();

    {
        System.out.println("...模拟的权限配置...");
        // 模拟菜单（动态路由表）
        Router router1 = new Router("M01", "system", "系统管理", "");
        Router router2 = new Router("M0101", "system:config", "参数设置", "M01");
        Router router3 = new Router("M0102", "system:user", "用户管理", "M01");
        Router router4 = new Router("M0103", "system:rights", "角色权限", "M01");
        Router router5 = new Router("M02", "student", "学生管理", "M01");
        Router router6 = new Router("M0201", "student:list", "学生信息", "M02");
        Router router7 = new Router("M0202", "student:score", "学生成绩", "M02");
        Router router8 = new Router("M0203", "student:retake", "重修科目", "M02");


        // 模拟的权限
        Permission permission1 = new Permission(1, "test:add");
        Permission permission2 = new Permission(2, "user:update");
        Permission permission3 = new Permission(3, "user:delete");
        Permission permission4 = new Permission(4, "/api/test/add_test");

        // 管理员的菜单
        List<Router> admin_routers = new ArrayList<Router>();
        admin_routers.add(router1);
        admin_routers.add(router2);
        admin_routers.add(router3);
        admin_routers.add(router4);
        admin_routers.add(router5);
        admin_routers.add(router6);

        // 管理员角色的权限配置
        List<Permission> admin_permissions = new ArrayList<Permission>();
        admin_permissions.add(permission1);
        admin_permissions.add(permission2);
        admin_permissions.add(permission3);
        admin_permissions.add(permission4);

        // 普通用户的菜单
        List<Router> user_routers = new ArrayList<Router>();
        user_routers.add(router5);
        user_routers.add(router6);
        user_routers.add(router7);
        user_routers.add(router8);

        // 普通用户角色的权限配置
        List<Permission> user_permissions = new ArrayList<Permission>();
        user_permissions.add(permission1);
        user_permissions.add(permission2);

        // 假设有几个角色[暂时2个]
        Role role_admin = new Role(1, "admin", admin_permissions, admin_routers);
        Role role_user = new Role(2, "user", user_permissions, user_routers);

        // 2个用户的角色集合（暂时配置成单角色）
        List<Role> huang_roles = new ArrayList<Role>();
        huang_roles.add(role_admin);
//        huang_roles.add(role_user);

        List<Role> xiaoming_roles = new ArrayList<Role>();
        xiaoming_roles.add(role_user);

        // 假设有几个用户
        User user1 = new User(1, "huangguohai", huang_roles, "黄国海", "13908032770", null);
        User user2 = new User(2, "xiaoming", xiaoming_roles, "王小明", "13908032770", null);

        userMap.put("huangguohai", user1);
        userMap.put("xiaoming", user2);

        // 模拟两个登录信息
        LoginInfo adminInfo = new LoginInfo("huangguohai", "123456");
        LoginInfo xiaomingInfo = new LoginInfo("xiaoming", "654321");
        loginMap.put("huangguohai", adminInfo);
        loginMap.put("xiaoming", xiaomingInfo);
    }

    /**
     * 通过用户名查找用户对象
     *
     * @param username
     * @return
     */
    public User findUserByUsername(String username) {
        return userMap.get(username);
    }

    public LoginInfo findLoginByUsername(String username) {
        return loginMap.get(username);
    }
}
