package com.shoescms.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CommonConfig {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${photoism.mail.chgpassurl}")
    private String chgPasswordUrl;

    public CommonConfig(@Value("HELLO_WORLD")String test){
        System.out.println("my HELLO_WORLD: " + test);
    }
}
