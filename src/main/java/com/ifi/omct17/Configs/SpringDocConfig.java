package com.ifi.omct17.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@OpenAPIDefinition
@Configuration
public class SpringDocConfig {

	@Bean
	public OpenAPI baseOpenAPI() {
		
		OpenAPI openAPI = new OpenAPI().info(null);
		
		Info info = new Info();
		
		info.title("OMC Java Platform");
		
		info.description("This is build 2024 SpringDoc. release 2024/5/7");
		
		info.version("V1");
		
		License license = new License();
		
		license.name("MIT");
		
		license.url("http://www.jsun.com.tw");
		
		Contact contact = new Contact();
		
		contact.name("Chris");
		
		contact.email("chris.ky.lin@fubon.com");
		
		info.license(license);
		
		info.contact(contact);
		
		openAPI.info(info);
		
		return openAPI;
	}
}
