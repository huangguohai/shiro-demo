package com.imooc.config;

import com.imooc.service.UserService;
import com.imooc.vm.LoginInfo;
import com.imooc.vm.Permission;
import com.imooc.vm.Role;
import com.imooc.vm.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现AuthorizingRealm接口用户用户认证
 * 用于判断用户角色权限
 * 黄国海
 * 20190303
 */
public class MyShiroRealm extends AuthorizingRealm {

    {
        super.setName("MyShiroRealm");
    }

    // 用于用户查询 实际使用时，查询数据（或从缓存中查询）
    @Autowired
    private UserService userService;

    /**
     * 角色权限和对应权限添加
     * 做授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("...做授权");
        // 获取登录用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        // 查询用户名称
        User user = userService.findUserByUsername(username);

        // 添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            // 添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            for (Permission permission : role.getPermissions()) {
                // 添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 做认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("...做认证");
        // 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        // 获取用户信息
        String username = authenticationToken.getPrincipal().toString();
        LoginInfo user = userService.findLoginByUsername(username);
        if (user == null) {
            // 这里返回后会报出对应异常
            return null;
        } else {
            // 这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(), getName());
//            authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("huang")); 加密
            return simpleAuthenticationInfo;
        }
    }
}
