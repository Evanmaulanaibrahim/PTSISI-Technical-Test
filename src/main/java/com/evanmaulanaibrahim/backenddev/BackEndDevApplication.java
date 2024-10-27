package com.evanmaulanaibrahim.backenddev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = { "com.evanmaulanaibrahim.backenddev" ,"lib.i18n"})
@EnableJpaAuditing

public class BackEndDevApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndDevApplication.class, args);
    }

}
