package com.imooc.vm;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 菜单，路由
 * 20190304
 */
@Data
@AllArgsConstructor
public class Router {
    private String id;
    private String routerName;
    private String desc;
    private String parentId;
}
