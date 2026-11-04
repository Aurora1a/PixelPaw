package com.pixelpaw.user.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.err.println("=======> SaTokenConfig 拦截器已注册 <=======");
        // 注意：必须显式传 auth lambda，无参 new SaInterceptor() 不会自动校验登录
        registry.addInterceptor(new SaInterceptor(handler -> {
            SaRouter.match("/**")                                   // 拦全部
                    .notMatch(
                            "/auth/register",   // 放行注册
                            "/auth/login",      // 放行登录
                            "/actuator/**"      // 放行健康检查
                    )
                    .check(r -> StpUtil.checkLogin());             // 其余一律要登录
        })).addPathPatterns("/**");
    }
}