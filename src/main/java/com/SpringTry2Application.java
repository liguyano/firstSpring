package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SpringTry2Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringTry2Application.class, args);
    }

}
