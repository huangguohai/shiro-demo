package com.imooc.test;

import com.imooc.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * 测试自定义Realm
 * 黄国海
 * 2019-2-26
 */
public class CustomRealmTest {
    /**
     * 测试授权过程
     */
    @Test
    public void testAuthentication() {

        CustomRealm customRealm = new CustomRealm();

        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        // 使用加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5"); // 设置加密名称
        matcher.setHashIterations(1); // 设置加密次数

        customRealm.setCredentialsMatcher(matcher);

        // 2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("xiaoming", "123456");
        subject.login(token); // 登录

        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        subject.checkRole("admin"); // 检查角色

        subject.checkPermission("user:delete"); // 检查权限
    }
}
