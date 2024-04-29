package com.ifi.omct17.Controllers.FBO;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HK20110002Controller {

	@PostMapping(value = "/HK20110002", 
			  consumes = MediaType.APPLICATION_JSON_VALUE, 
			  produces = MediaType.APPLICATION_JSON_VALUE)
	public void HK20110002(HttpEntity<String> httpEntity)
	{}
}
