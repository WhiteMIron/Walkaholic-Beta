package com.example.demo.config;

import lombok.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Profile("prod")
    @Configuration
    public static class ProdMvcConfiguration implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(final ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/static/**")
                    .addResourceLocations("classpath:/static/")
                    .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
        }
    }

    @Profile("local")
    @Configuration
    public static class LocalMvcConfiguration implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(final ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/static/**")
                    .addResourceLocations("file:src/main/resources/static/");

            registry.addResourceHandler("/static/img/**")
                .addResourceLocations("/resources/static/img/");
        }


    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // /ckUpload/** 은 /resources/ckUpload/ 으로 시작하는 uri호출은 /resources/ckUpload/ 경로 하위에 있는 리소스 파일이다 라는 의미입니다.
//        registry.addResourceHandler("/img/**")
//                .addResourceLocations("file:src/main/resources/static/img/","file:src/main/resources/course/");
//
//        //다른 이미지 업로드를 위한 경로
////        registry.addResourceHandler("/img/course/**")
////                .addResourceLocations("/resources/img/course/");
//
////        registry
////                .addResourceHandler(path + "/**") // url 접근 경로
////                .addResourceLocations("file:" + path + "/"); // 디렉토리 경로 (반드시 file: 을 붙여주어야 한다.)

}

