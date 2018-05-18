package com.ygy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableEurekaClient
@EnableScheduling
@SpringBootApplication
@MapperScan("com.ygy.mapper")
public class DemoApplication {

	public static void main(String[] args) {
 		SpringApplication.run(DemoApplication.class, args);
	}
}
