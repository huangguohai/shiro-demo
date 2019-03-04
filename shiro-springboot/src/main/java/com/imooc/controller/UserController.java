package com.imooc.controller;

import com.imooc.service.UserService;
import com.imooc.vm.LoginInfo;
import com.imooc.vm.Result;
import com.imooc.vm.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户操作接口
 * 黄国海
 * 2019-2-26
 */
@CrossOrigin
@RestController
//@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 测试接口
     *
     * @return
     */
    @GetMapping("/test")
    public String test() {
        return "get 测试成功！";
    }

    @PostMapping("/test")
    public String testPost(LoginInfo user){
        return "post 测试成功！用户名：" + user.getUsername()+",密码："+user.getPassword();
    }

    /**
     * 用户登录，返回用户信息：用户名，姓名，角色，权限（菜单、按钮等）
     *
     * @param loginInfo
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginInfo loginInfo) {
        System.out.println(loginInfo.getUsername()+"：登录请求");

        // 添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginInfo.getUsername(), loginInfo.getPassword());

        try {
            subject.login(token);
            // 获取用户信息
            User user = userService.findUserByUsername(loginInfo.getUsername());
            user.setToken(token);
            return Result.success(user);
        } catch (AuthenticationException e) {
            return Result.failure( e.getMessage());
        }
//        return Result.success(token);
    }

    /**
     * 登出
     * @return
     */
    @GetMapping("/api/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return "用户已登出系统";

    }


    @RequiresRoles("admin")
    @GetMapping("/testrole")
    public String  testRole(){
        return "testRole success!";
    }

    @RequiresRoles("user")
    @GetMapping("/testPerms")
    public String  testPerms(){
        return "testPerms success!";
    }

    @GetMapping("/testPerms1")
    public String  testPerms1(){
        return "testPerms1 success!";
    }

}
