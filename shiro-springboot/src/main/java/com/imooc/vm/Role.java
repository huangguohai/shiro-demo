package com.imooc.vm;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 角色
 */
@Data
@AllArgsConstructor
public class Role {
    private int id;
    private String roleName;
    private List<Permission> permissions;
    private List<Router> routers;
}
