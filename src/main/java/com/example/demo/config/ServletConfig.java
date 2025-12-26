package com.example.demo.config;

import com.example.demo.servlet.SimpleHealthServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<SimpleHealthServlet> simpleHealthServletRegistration() {
        ServletRegistrationBean<SimpleHealthServlet> registration = 
            new ServletRegistrationBean<>(new SimpleHealthServlet(), "/health");
        registration.setLoadOnStartup(1);
        return registration;
    }
}