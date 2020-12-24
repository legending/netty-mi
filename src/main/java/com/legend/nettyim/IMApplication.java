package com.legend.nettyim;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.legend.nettyim.mapper")
public class IMApplication {
    private static final Logger log = LoggerFactory.getLogger(IMApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(IMApplication.class, args);

    }
}
