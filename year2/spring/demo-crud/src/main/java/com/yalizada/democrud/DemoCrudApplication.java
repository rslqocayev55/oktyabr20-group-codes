package com.yalizada.democrud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.yalizada.democrud.file.StorageProperties;
import com.yalizada.democrud.file.StorageService;

// Bu annotasiya 3 annotasiyani evez edir, configuration, component scan, auto configuration
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class DemoCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoCrudApplication.class, args);
	}
	// bu metod ilk defe proyekt run olanda ise dusur ve eger file upload qovlugu yoxdursa onu yaradir
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			//storageService.deleteAll();
			storageService.init();
		};
	}
}
