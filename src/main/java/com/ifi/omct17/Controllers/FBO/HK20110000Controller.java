package com.ifi.omct17.Controllers.FBO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifi.omct17.Classes.Common.ValidateException;
import com.ifi.omct17.Classes.FBO.HK20110000Request.HK20110000Req;
import com.ifi.omct17.Classes.FBO.HK20110000Response.HK20110000Rsp;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest.SinglepaymentReq;
import com.ifi.omct17.Handlers.FBO.HK20110000Handler;

@RestController
public class HK20110000Controller {

	@Autowired @Qualifier("SinglepaymentRequest_SinglepaymentReq") SinglepaymentReq singlepaymentReq;
	//@PostMapping("/HK20110000")
	//@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	@Autowired HK20110000Req hk20110000Req;
	@Autowired HK20110000Rsp hk20110000Rsp;
	
	@PostMapping(value = "/HK20110000", 
	  consumes = MediaType.APPLICATION_JSON_VALUE, 
	  produces = MediaType.APPLICATION_JSON_VALUE)
	public HK20110000Rsp HK20110000(HttpEntity<String> httpEntity)
	{
		Logger logger = LogManager.getLogger(this.getClass());
		
		//hk20110000Rsp = new HK20110000Rsp();
		
		HK20110000Handler handler = new HK20110000Handler();
		
		try {
			String xmlString = httpEntity.getBody();
			
			handler.Procedure(xmlString,hk20110000Rsp,singlepaymentReq);
		
		} catch (ValidateException e) {
			logger.error(e.validErrorMessage);
		}
		catch (Exception e) {
			
			logger.error(e);
		}
		return hk20110000Rsp;

	}
}
