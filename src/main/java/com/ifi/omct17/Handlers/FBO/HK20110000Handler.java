package com.ifi.omct17.Handlers.FBO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ifi.omct17.Classes.Common.ValidateException;
//import com.ifi.omct17.Classes.FBO.HK20110000Req;
//import com.ifi.omct17.Classes.FBO.HK20110000Req;
//import com.ifi.omct17.Classes.FBO.HK20110000Req.Tx;

import com.ifi.omct17.Classes.FBO.HK20110000Request.HK20110000Req;
import com.ifi.omct17.Classes.FBO.HK20110000Response.HK20110000Rsp;
//import com.ifi.omct17.Classes.Flexcube.SinglepaymentRsp;
//import com.ifi.omct17.Classes.FBO.HK20110000Request.Tx;
//import com.ifi.omct17.Interfaces.IProcessService;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest.SinglepaymentReq;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest.TxnUdfDetDto;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentResponse.SinglepaymentRsp;
import com.ifi.omct17.Services.OBPM.Singlepayment.SinglepaymentService;

public class HK20110000Handler {

	@Autowired SinglepaymentService singlepaymentService;
	
	Logger logger = LogManager.getLogger(this.getClass());

	SinglepaymentReq singlepaymentReq = new SinglepaymentReq();
	
	SinglepaymentRsp singlepaymentRsp;
	
	HK20110000Req hk20110000Req = new HK20110000Req();
	
	HK20110000Rsp hk20110000Rsp;
	
	

	public void Procedure(String requestString,HK20110000Rsp iniHk20110000Rsp,SinglepaymentReq iniSinglepaymentReq) throws ValidateException, Exception {


		// Step 0. Initial Objects
		this.hk20110000Rsp = iniHk20110000Rsp;

		this.singlepaymentReq = iniSinglepaymentReq;

		// Step 1. request message passer to HK20110002Req object
		PassRequest(requestString);

		// Step 2. Validate HK20110002Req Request object Value
		ValidRequest();

		// Step 3. Request Field Mapping HK20110002Req to Singlepayment Request object
		RequestFieldMapping();
		
		// Step 4. send single payment message to OBPM service
		LaunchSinglePaymentService();
		
		// Step 5. Response Field Mapping Singlepayment boject to HK20110002Rsp object
		ResponseFieldMapping();	
	}

	/**
	 * Step 1. request message passer to HK20110002Req object
	 * */ 
	private void PassRequest(String xmlString) throws JsonMappingException, JsonProcessingException {
		XmlMapper xmlMapper = new XmlMapper();

		this.hk20110000Req = xmlMapper.readValue(xmlString, HK20110000Req.class);
		logger.info("Finish Pass Tx");
	}

	/**
	 * Step 2. Validate Request object Value
	 * */ 
	private void ValidRequest() throws ValidateException {

		if (!this.hk20110000Req.TxHead.HMSGID.trim().equalsIgnoreCase("P")) {
			throw new ValidateException("HMSGID value Error");
		}

	}
	
	/**
	 * Step 3. Request Field Mapping FBO request object to Singlepayment request object
	 * */ 
	private void RequestFieldMapping() {
		// Singlepayment header mapping
		this.singlepaymentReq.header.entityId = "ENTITY_ID1";
		this.singlepaymentReq.header.userId = "UPLOAD";
		this.singlepaymentReq.header.functionId = "PMDPNSPS";
		this.singlepaymentReq.header.action = "NEW";
		this.singlepaymentReq.header.channel = "REST";
		this.singlepaymentReq.header.source = "FBO";
		this.singlepaymentReq.header.moduleId = "PM";
		this.singlepaymentReq.header.hostCode = "TFBHKB";
		this.singlepaymentReq.header.msgId = GetMsgID();
		this.singlepaymentReq.header.userBranch = "950";
		
		// Singlepayment Body mapping
		this.singlepaymentReq.txnDet.transferType = "B";
		this.singlepaymentReq.txnDet.sourceCode = "FBO";
		this.singlepaymentReq.txnDet.txnBranch = "950";
		this.singlepaymentReq.txnDet.bookDate = hk20110000Req.TxBody.TxXML.Content.TXDATE;
		this.singlepaymentReq.txnDet.credttm = hk20110000Req.TxBody.TxXML.Content.TXDATE;
		this.singlepaymentReq.txnDet.remarks = hk20110000Req.TxBody.TxXML.Content.TXSER;
		
		this.singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.reqdexctndt = hk20110000Req.TxBody.TxXML.Content.TXDATE;
		this.singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.dbtraccothrid = hk20110000Req.TxBody.TxXML.Content.DRINFO.DRACCT;
		this.singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.dbtraccccy = hk20110000Req.TxBody.TxXML.Content.DRINFO.DRCCY;
		this.singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.svclvlcd = hk20110000Req.TxBody.TxXML.Content.PRODCOD;
		
	}

	/**
	 * Step 4. send single payment message to OBPM service
	 * */ 
	private void LaunchSinglePaymentService() {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Step 5. Response Field Mapping Singlepayment boject to HK20110002Rsp object
	 * */ 
	private void ResponseFieldMapping() {
		singlepaymentReq.header.entityId = "ENTITY_ID1";
		singlepaymentReq.header.userId = "UPLOAD";
		singlepaymentReq.header.functionId = "PMDPNSPS";
		singlepaymentReq.header.action = "NEW";
		singlepaymentReq.header.channel = "REST";
		singlepaymentReq.header.source = "FBO";
		singlepaymentReq.header.moduleId = "PM";
		singlepaymentReq.header.hostCode = "TFBHKB";
		singlepaymentReq.header.msgId = "FOB3202404112999";
		singlepaymentReq.header.userBranch = "950";
		
		singlepaymentReq.txnDet.transferType = "B";
		singlepaymentReq.txnDet.sourceCode = "FBO";
		singlepaymentReq.txnDet.customerNo = "";
		singlepaymentReq.txnDet.bookDate = "2024-03-15";
		singlepaymentReq.txnDet.credttm = "2024-03-15";
		
		singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.dbtrnm = "";
		singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.dbtradrline1 = "";
		singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.dbtradrline2 = "";
		singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.dbtradrline3 = "";
		singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.reqdexctndt = "";
		singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.dbtraccothrid = "";
		singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.dbtraccccy = "";
		singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.svclvlcd = "FNB0";
		
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtrnm = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtradrline1 = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtradrline2 = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtrctry = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtraccothrid = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.instdamt = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.instdamtccy = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtbicfi = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtnm = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtadrline1 = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtadrline2 = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtctry = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.rmtinfustrd1 = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.rmtinfustrd2 = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.rmtinfustrd3 = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.rmtinfustrd4 = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.endtoendid = "";
		singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.instrid = "";
		
		singlepaymentReq.txnDet.custcrdtrfinitChgDto.add(0, null);
		singlepaymentReq.txnDet.custcrdtrfinitChgDto.add(0, null);
		
		singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.instdrmbrsntagtbicfi = "";
		singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.creditNostro = "";
		singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.recvr = "";
		singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.suppressCoverMessage = "";
		singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.processWithoutCover = "";
		
		singlepaymentReq.txnDet.misdetailsDto.misGroup = "";
		singlepaymentReq.txnDet.misdetailsDto.txnmiscode1 = "";
		singlepaymentReq.txnDet.misdetailsDto.txnMis1 = "";
		singlepaymentReq.txnDet.misdetailsDto.txnmiscode2 = "";
		singlepaymentReq.txnDet.misdetailsDto.txnMis2 = "";
		
		singlepaymentReq.txnDet.txnUdfDetDto.add(new TxnUdfDetDto());
	}

	
	/**
	 * 3.1
	 * Get Message ID from documentv6.5
	 * */
	private String GetMsgID() {
		String fboMsgID = hk20110000Req.TxHead.HTXTID + hk20110000Req.TxHead.HSYDAY + hk20110000Req.TxHead.HSYTIME
				+ hk20110000Req.TxBody.TxXML.Content.TXSER;
		return fboMsgID;
	}



}
