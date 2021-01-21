package com.cegeka.employeeplanning.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class EmployeeplanningConfig implements WebMvcConfigurer {
    private final Environment env;

    public EmployeeplanningConfig(Environment env) {
        this.env = env;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        System.out.println("************************************** Aufgerufen *****************************************");

        converters.stream()
                .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                .findFirst()
                .ifPresent(converter -> ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8));
    }

    @PostConstruct
    public void init() {
        System.out.println("spring.profiles.active: " + env.getProperty("spring.profiles.active"));
        System.out.println("spring.datasource.url: " + env.getProperty("spring.datasource.url"));
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/employeeplanning").setViewName("forward:/index.html");

        registry.addViewController("/goToStart").setViewName("start");
        registry.addViewController("/goToMitarbeiter").setViewName("mitarbeiter");
        registry.addViewController("/goToMitarbeiterVertrieb").setViewName("mitarbeitervertrieb");
        registry.addViewController("/goToEinsatz").setViewName("einsatz");
        registry.addViewController("/login").setViewName("login");

//        registry.addViewController("/").setViewName("forward:/empty.html");
    }
}
