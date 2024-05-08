package com.ifi.omct17.Services.Singlepayment;



import java.util.Collections;

import javax.management.loading.PrivateClassLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest.SinglepaymentReq;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentResponse.SinglepaymentRsp;


@Component
public class SinglepaymentService {

	@Value("${service.singlepayment}")
	private String address;
	
	Logger logger = LogManager.getLogger(this.getClass());
	
	public SinglepaymentRsp SinglepaymentService(SinglepaymentReq singlepaymentReq) throws Exception {
		
		
		SinglepaymentRsp result = null;
		
		try {
		    
			HttpHeaders headers = new HttpHeaders();
			
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			HttpEntity<String> entity = new HttpEntity<>("body", headers);
			
			RestTemplate restTemplate = new RestTemplate();
		    
			String reString = restTemplate.postForObject(address, entity, String.class);
			
			//https://stackoverflow.com/questions/19238715/how-to-set-an-accept-header-on-spring-resttemplate-request
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			result = objectMapper.readValue(reString, SinglepaymentRsp.class);
			
			logger.info(reString);
			
		} catch (Exception e) {
			throw new Exception("SinglepaymentService() Error" + e.getMessage());
		}
		
		return result;
	}
}
