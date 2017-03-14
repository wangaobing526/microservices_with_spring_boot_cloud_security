package com.retailstore;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SpringBootApplication
@EnableZuulProxy
@EnableHystrix
@EnableEurekaClient
@EnableHystrixDashboard
public class ClientGatewayApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(ClientGatewayApplication.class, args);
    }
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource= new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOrigin("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("OPTIONS");
        corsConfig.addAllowedMethod("HEAD");
        corsConfig.addAllowedMethod("GET");
        corsConfig.addAllowedMethod("PUT");
        corsConfig.addAllowedMethod("POST");
        corsConfig.addAllowedMethod("DELETE");
        corsConfig.addAllowedMethod("PATCH");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfig);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/");
    }

    @RestController
    static class HomeController {
             /***
         * 删除网关的cookie ,用于注销
         * @param request
         * @param response
         */
        @RequestMapping(value="/logoutAction")
        public  String logoutAction(HttpServletRequest request, HttpServletResponse response) {

            HttpSession  session = (HttpSession)request.getSession();
            request.getSession().invalidate();

            //网关cookie注销
            Cookie cookie1[] = request.getCookies();
            for (int i = 0; i < cookie1.length; i++) {
                System.out.println(cookie1[i].getName()+"====del前的值===>"+cookie1[i].getValue()+"path:"+cookie1[i].getPath()+"domain"+cookie1[i].getDomain());
            }
            Cookie getway_cookie = new Cookie("JSESSIONID", "");
            getway_cookie.setPath("/");
            getway_cookie.setMaxAge(0);
            response.addCookie(getway_cookie);
            return "ok";
             }
        }

}
