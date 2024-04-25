package com.ifi.omct17;


import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class helloController {
Logger logger = LogManager.getLogger(helloController.class.getName());
	@GetMapping("Hello")
	public String Hello() {
		Date dt=new Date();
		logger.error("This is a line of log"+dt.getTime());
		logger.info("This is a line of log"+dt.getTime());
		return "Hello Kyle";
	}
}
