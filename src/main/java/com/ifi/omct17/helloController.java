package com.ifi.omct17;


import java.util.Date;

import com.ifi.omct17.Classes.Common.FJLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class helloController {

	@GetMapping("/Hello")
//@RequestMapping(value = "/Hello", method = RequestMethod.GET)
	public String Hello() {
		//Logger logger = LogManager.getLogger(this.getClass());//helloController.class.getName());
		Date dt=new Date();
		//logger.error("This is a line of log"+dt.getTime());
		FJLogger.Error("This is a line of log Error : "+dt.getTime());
		FJLogger.Info("This is a line of log Info : "+dt.getTime());
		//logger.info("This is a line of log"+dt.getTime());
		return "Hello Kyle";
	}
}
