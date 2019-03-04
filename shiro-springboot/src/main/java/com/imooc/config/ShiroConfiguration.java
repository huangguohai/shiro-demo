package com.imooc.config;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.shiro.mgt.SecurityManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 目前项目主要使用 自定义的 ShiroRealm 来判断角色和权限
 */
@Configuration
public class ShiroConfiguration {

    /**
     * 将自己的验证方式加入容器
     *
     * @return
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }

    /**
     * 权限管理，配置主要是Realm的管理认证
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件  [目前项目暂时不使用]
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<String, String>();
        // 登录和退出 不需要权限的
//        map.put("/login", "anon");
//        map.put("/logout", "anon");
        map.put("/*", "anon");
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        map.put("/logout", "logout");
        // 以下模拟配置在数据库中的角色权限
        map.put("/api/test/authc_test1","authc"); // 只要是登录后就能访问
        map.put("/api/test/add_test22","roles[admin]"); // 只有角色 teacher 和 student 可以访问


        // 对所有用户认证
//        map.put("/**","authc");
        // 登录
//        shiroFilterFactoryBean.setLoginUrl("/login");
        // 首页
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 错误页面，认证不通过跳转
//        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 加入注解的使用，不加入这个注解不生效
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
