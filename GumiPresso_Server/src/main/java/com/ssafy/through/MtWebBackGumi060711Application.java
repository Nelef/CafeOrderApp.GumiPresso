package com.ssafy.through;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAspectJAutoProxy
@MapperScan(basePackages = "com.ssafy.through.model.repo")
@EnableSwagger2
public class MtWebBackGumi060711Application {

	public static void main(String[] args) {
		SpringApplication.run(MtWebBackGumi060711Application.class, args);
	}

}
