package com.ifi.omct17.Controllers.FBO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifi.omct17.Classes.Common.ValidateException;
import com.ifi.omct17.Classes.FBO.HK20110002Request.HK20110002Req;
import com.ifi.omct17.Classes.FBO.HK20110002Response.HK20110002Rsp;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest.SinglepaymentReq;
import com.ifi.omct17.Handlers.FBO.HK20110000Handler;
import com.ifi.omct17.Handlers.FBO.HK20110002Handler;



@RestController
public class HK20110002Controller {

	@Autowired @Qualifier("SinglepaymentRequest_SinglepaymentReq") SinglepaymentReq singlepaymentReq;
	@Autowired HK20110002Rsp hk20110002Rsp;
	
	@Autowired HK20110002Handler handler;
	
	@PostMapping(value = "/HK20110002", 
			  consumes = MediaType.APPLICATION_JSON_VALUE, 
			  produces = MediaType.APPLICATION_JSON_VALUE)
	public HK20110002Rsp HK20110002(HttpEntity<String> httpEntity)
	//public HK20110002Rsp HK20110002(String httpEntity)
	{
		Logger logger = LogManager.getLogger(this.getClass());
		
		//HK20110002Handler handler = new HK20110002Handler();
		logger.info("Hello 冠宇 info");
		logger.error("Hello 冠宇 error");
		try {
			String xmlString = httpEntity.getBody();
			
			handler.Prcedure(xmlString, hk20110002Rsp, singlepaymentReq);

		} catch (ValidateException e) {
			logger.error(e.validErrorMessage);
		}
		catch (Exception e) {
			
			logger.error(e);
		}
		return hk20110002Rsp;
	}
}
