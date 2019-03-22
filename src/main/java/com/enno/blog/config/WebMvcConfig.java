package com.enno.blog.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

public class WebMvcConfig {

    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");


    }

}
