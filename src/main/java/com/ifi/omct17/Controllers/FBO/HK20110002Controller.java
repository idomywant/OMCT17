package com.ifi.omct17.Controllers.FBO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest.SinglepaymentReq;



@RestController
public class HK20110002Controller {

	@Autowired @Qualifier("SinglepaymentRequest_SinglepaymentReq") SinglepaymentReq singlepaymentReq;
	
	@PostMapping(value = "/HK20110002", 
			  consumes = MediaType.APPLICATION_JSON_VALUE, 
			  produces = MediaType.APPLICATION_JSON_VALUE)
	public void HK20110002(HttpEntity<String> httpEntity)
	{
		try {
			//HK
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
