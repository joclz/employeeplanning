package com.cegeka.employeeplanning.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class EmployeeplanningConfig implements WebMvcConfigurer {
    @Autowired
    private Environment env;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        System.out.println("************************************** Aufgerufen *****************************************");

        converters.stream()
                .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                .findFirst()
                .ifPresent(converter -> ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(Charset.forName("utf-8")));
    }

    @PostConstruct
    public void init() {
        System.out.println("spring.profiles.active: " + env.getProperty("spring.profiles.active"));
        System.out.println("spring.datasource.url: " + env.getProperty("spring.datasource.url"));
    }
}
