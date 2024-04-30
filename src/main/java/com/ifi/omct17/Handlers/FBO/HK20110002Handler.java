package com.ifi.omct17.Handlers.FBO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ifi.omct17.Classes.FBO.HK20110002Request.HK20110002Req;
import com.ifi.omct17.Classes.FBO.HK20110002Response.HK20110002Rsp;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentReq;



@Component
public class HK20110002Handler {
	
	Logger logger = LogManager.getLogger(this.getClass());

	SinglepaymentReq singlepaymentReq;
	
	HK20110002Rsp hk20110002Rsp;
	
	HK20110002Req hk20110002Req = new HK20110002Req();
	
	public HK20110002Rsp Prcedure(String reqString, HK20110002Rsp iniHK20110002Rsp, SinglepaymentReq iniSinglepaymentReq) throws JsonMappingException, JsonProcessingException
	{
		// Step 0. Initial Objects
		this.hk20110002Rsp = iniHK20110002Rsp;
		
		this.singlepaymentReq = iniSinglepaymentReq;
		
		// Step 1. pass request
		PassRequest(reqString);

		// Step 2. Validate Request Value
		ValidRequest();
		
		// step 3. Mapping Field
		FieldMapping();
		
		return this.hk20110002Rsp;
	}




	private void PassRequest(String reqString) throws JsonMappingException, JsonProcessingException {
		XmlMapper xmlMapper = new XmlMapper();

		hk20110002Req = xmlMapper.readValue(reqString, HK20110002Req.class);
		
		logger.info("Finish Pass Tx");	
	}
	
	private void ValidRequest() {
		// TODO Auto-generated method stub
		
	}
	
	private void FieldMapping() {
		// 
		
		
	}
}
