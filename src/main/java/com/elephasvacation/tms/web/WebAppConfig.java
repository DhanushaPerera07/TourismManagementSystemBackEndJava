package com.elephasvacation.tms.web;

import com.elephasvacation.tms.web.config.BOConfig;
import com.elephasvacation.tms.web.config.DAOConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@ComponentScan
@ComponentScan(basePackages={
        "com.elephasvacation.tms.web.api"
})
@Configuration
@EnableWebMvc
@Import(value = {
        DAOConfig.class,
        BOConfig.class
})
public class WebAppConfig {
}
