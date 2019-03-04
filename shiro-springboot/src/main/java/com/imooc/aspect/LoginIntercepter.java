package com.imooc.aspect;

import com.imooc.vm.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

//@Component
public class LoginIntercepter extends HandlerInterceptorAdapter {

    private static String[] urls = {"/checkLogin"};

    /**
     * 对静态资源的判断
     *
     * @param requestName
     * @return
     */
    private static boolean checkUrl(String requestName) {
        // 静态资源
        if (requestName == null || requestName.equals("")) return true;
        if (requestName.endsWith(".js") || requestName.endsWith(".css") || requestName.endsWith(".jpg")) return true;

        for (int i = 0; i < urls.length; i++) {
            if (urls[i].equals(requestName)) return true;
        }

        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        // 获取当前请求名称
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String requestName = uri.substring(contextPath.length() + 1, uri.length());

        // 动态获取项目网络地址
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath + "/";

        if (checkUrl(requestName)) return true;

        // 验证用户是否登录
        Object obj = session.getAttribute("users");
        if (obj == null) return false;

        // 某些请求：比如，修改自己的信息、退出登录，是只要求登录就可以访问，而没有纳入权限管理范围
        ServletContext application = session.getServletContext();
        List<String> allUrl = (List<String>) application.getAttribute("allurl");
        if (!allUrl.contains(requestName)) return true;
        // 如果到这里，用户已经登录，并且当前请求不是请求的静态资源，开启权限判断
        User users = (User) obj;
        if (users.getRoles() == null) return false;

        // 得到当前用户所拥有的所有权限信息
//        List<Priviliage>
        return true;
    }
}
