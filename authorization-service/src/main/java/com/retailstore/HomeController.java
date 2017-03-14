package com.retailstore;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by master on 2016/12/1.
 */
@RestController
public class HomeController {

        /***
         * 删除网关的cookie ,用于注销
         * @param request
         * @param response
         */
        @RequestMapping(value="/logoutAuth")
        public  String logoutAction(HttpServletRequest request, HttpServletResponse response) {

            //session 注销
            request.getSession().invalidate();

            // 认证中心cookie注销
            Cookie cookie1[] = request.getCookies();
            if (cookie1==null)return "ok";
            for (int i = 0; i < cookie1.length; i++) {
                System.out.println(cookie1[i].getName()+"====del6前的值===>"+cookie1[i].getValue()+"path:"+cookie1[i].getPath()+"domain"+cookie1[i].getDomain());
                if ("JSESSIONID".equals(cookie1[i].getName())){
                    cookie1[i].setMaxAge(0);
                    cookie1[i].setValue("");
                    response.addCookie(  cookie1[i]);
                }
                System.out.println(cookie1[i].getName()+"====del6前的值===>"+cookie1[i].getValue()+"path:"+cookie1[i].getPath()+"domain"+cookie1[i].getDomain());
            }
            return "ok";
        }
    }

