package com.tinyurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tinyurl.entity.UrlToBeShortenedBean;

@SpringBootApplication
public class URLShortener {
	
	public static void main(String[] args) {
		SpringApplication.run(URLShortener.class, args);

	}
	
	@Configuration
	class AppConfig {
	    @Bean
	    public UrlToBeShortenedBean transferService() {
	        return new UrlToBeShortenedBean();
	    }
	}

}
