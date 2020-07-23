package com.es.phoneshop.web.controller.pages;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional
@ContextConfiguration(locations = {"classpath:applicationContext.xml",
        "classpath:dispatcher-servlet.xml",
        "file:src/main/webapp/WEB-INF/spring-security.xml"})
@WebAppConfiguration
public @interface IntegrationTest {
}
