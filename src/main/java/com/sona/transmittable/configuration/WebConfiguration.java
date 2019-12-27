package com.sona.transmittable.configuration;

import com.sona.transmittable.interceptor.TraceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 *
 * @author renfakai
 * @date 2019/12/27
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 注册轨迹拦截器
     *
     * @param registry 注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceInterceptor()).addPathPatterns("/**/*");
    }
}
