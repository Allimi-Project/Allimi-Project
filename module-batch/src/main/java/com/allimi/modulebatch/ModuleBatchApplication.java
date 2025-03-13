package com.allimi.modulebatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.allimi.modulecore.domain.news")
@ComponentScan(basePackages = {
	"com.allimi.modulebatch",
	"com.allimi.modulecore",
	"com.allimi.modulecore.config",
	"com.allimi.moduleapi"
})
public class ModuleBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleBatchApplication.class, args);
	}

}
