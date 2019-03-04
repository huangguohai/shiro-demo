package com.imooc.vm;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.util.List;

/**
 * 用户不含密码的其他业务信息
 */
@Data
@AllArgsConstructor
public class User {
    private int id;
    private String username;
//    private String password;
    private List<Role> roles;
    private String name; // 姓名
    private String phone; // 联系电话 等
    private UsernamePasswordToken token; // token也记录在用户信息中
}
