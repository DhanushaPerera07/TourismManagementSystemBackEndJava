package com.elephasvacation.tms.web;

import com.elephasvacation.tms.web.config.JPAConfig;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

//@ComponentScan(basePackages={
//        "com.elephasvacation.tms.web.api"
//})
@Configuration
@Import(value = {
        JPAConfig.class
})
@EnableAspectJAutoProxy
public class WebRootConfig {
}
