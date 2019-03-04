package com.imooc.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试认证类
 * 黄国海
 * 2019-2-26
 */
public class AuthenticationTest2 {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    /**
     * 认证之前，先加一个用户
     */
    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("huangguohai", "123456","admin","user");
    }

    /**
     * 测试授权过程
     */
    @Test
    public void testAuthentication() {
        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // 2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("huangguohai", "123456");
        subject.login(token); // 登录

        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        // 认证完之后，检查权限
        subject.checkRoles("admin","user");
    }

}
