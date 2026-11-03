package com.pixelpaw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pixelpaw.**.mapper")
public class PixelPawApplication {
    public static void main(String[] args) {
        SpringApplication.run(PixelPawApplication.class, args);
    }
}
