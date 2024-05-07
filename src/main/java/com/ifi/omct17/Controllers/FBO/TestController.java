package com.ifi.omct17.Controllers.FBO;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	
	@PostMapping(value = "/Testing", 
	  consumes = MediaType.APPLICATION_JSON_VALUE, 
	  produces = MediaType.APPLICATION_JSON_VALUE)
	public String name() {
		String resultString = "{\r\n"
				+ "    \"txnrefno\": \"2411601072587000\",\r\n"
				+ "    \"sourceCode\": \"FBO\",\r\n"
				+ "    \"endtoendid\": \"240110FOB3010012\",\r\n"
				+ "    \"instrid\": \"FOB3202404111449\",\r\n"
				+ "    \"MSGSTATUS\": \"SUCCESS\",\r\n"
				+ "    \"TXNID\": \"2411601972288000\",\r\n"
				+ "    \"resp\": [\r\n"
				+ "        {\r\n"
				+ "            \"respCode\": \"PM-TXP-001\",\r\n"
				+ "            \"respDesc\": \"Transaction is submitted for processing\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return resultString;
	}
}
