package com.ifi.omct17.Handlers.FBO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ctc.wstx.shaded.msv_core.grammar.xmlschema.Field;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ifi.omct17.Classes.Common.ValidateException;
//import com.ifi.omct17.Classes.FBO.HK20110000Req;
//import com.ifi.omct17.Classes.FBO.HK20110000Req;
//import com.ifi.omct17.Classes.FBO.HK20110000Req.Tx;

import com.ifi.omct17.Classes.FBO.HK20110000Request.HK20110000Req;
import com.ifi.omct17.Classes.FBO.HK20110000Response.HK20110000Rsp;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentRsp;
//import com.ifi.omct17.Classes.FBO.HK20110000Request.Tx;
//import com.ifi.omct17.Interfaces.IProcessService;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest.SinglepaymentReq;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest.TxnUdfDetDto;

public class HK20110000Handler {

	Logger logger = LogManager.getLogger(this.getClass());

	HK20110000Req tx;
	
	SinglepaymentReq spReq = new SinglepaymentReq();
	
	//SinglepaymentRsp singlepaymentRsp = new SinglepaymentRsp();

	public HK20110000Rsp Prcedure(String requestString) throws ValidateException, Exception {

		// Step 1. pass request
		PassRequest(requestString);

		// Step 2. Validate Request Value
		ValidRequest();
		
		// step 3. Mapping Field
		FieldMapping();

		return null;
	}


	// Step 1. pass request
	private void PassRequest(String xmlString) throws JsonMappingException, JsonProcessingException {
		XmlMapper xmlMapper = new XmlMapper();

		tx = xmlMapper.readValue(xmlString, HK20110000Req.class);
		logger.info("Finish Pass Tx");
	}

	// Step 2. Validate Request Value
	private void ValidRequest() throws ValidateException {

		if (!tx.TxHead.HMSGID.trim().equalsIgnoreCase("P")) {
			throw new ValidateException("HMSGID value Error");
		}

	}
	
	// step 3. Mapping Field
	private void FieldMapping() {
		spReq.header.entityId = "ENTITY_ID1";
		spReq.header.userId = "UPLOAD";
		spReq.header.functionId = "PMDPNSPS";
		spReq.header.action = "NEW";
		spReq.header.channel = "REST";
		spReq.header.source = "FBO";
		spReq.header.moduleId = "PM";
		spReq.header.hostCode = "TFBHKB";
		spReq.header.msgId = "FOB3202404112999";
		spReq.header.userBranch = "950";
		
		spReq.txnDet.transferType = "B";
		spReq.txnDet.sourceCode = "FBO";
		spReq.txnDet.customerNo = "";
		spReq.txnDet.bookDate = "2024-03-15";
		spReq.txnDet.credttm = "2024-03-15";
		
		spReq.txnDet.custcrdtrfinitPmtinfDto.dbtrnm = "";
		spReq.txnDet.custcrdtrfinitPmtinfDto.dbtradrline1 = "";
		spReq.txnDet.custcrdtrfinitPmtinfDto.dbtradrline2 = "";
		spReq.txnDet.custcrdtrfinitPmtinfDto.dbtradrline3 = "";
		spReq.txnDet.custcrdtrfinitPmtinfDto.reqdexctndt = "";
		spReq.txnDet.custcrdtrfinitPmtinfDto.dbtraccothrid = "";
		spReq.txnDet.custcrdtrfinitPmtinfDto.dbtraccccy = "";
		spReq.txnDet.custcrdtrfinitPmtinfDto.svclvlcd = "FNB0";
		
		spReq.txnDet.custcrdtrfinitCdttxinfDto.cdtrnm = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.cdtradrline1 = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.cdtradrline2 = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.cdtrctry = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.cdtraccothrid = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.instdamt = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.instdamtccy = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtbicfi = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtnm = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtadrline1 = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtadrline2 = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtctry = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.rmtinfustrd1 = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.rmtinfustrd2 = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.rmtinfustrd3 = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.rmtinfustrd4 = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.endtoendid = "";
		spReq.txnDet.custcrdtrfinitCdttxinfDto.instrid = "";
		
		spReq.txnDet.custcrdtrfinitChgDto.add(0, null);
		spReq.txnDet.custcrdtrfinitChgDto.add(0, null);
		
		spReq.txnDet.custcrdtrfinitAddinfDto.instdrmbrsntagtbicfi = "";
		spReq.txnDet.custcrdtrfinitAddinfDto.creditNostro = "";
		spReq.txnDet.custcrdtrfinitAddinfDto.recvr = "";
		spReq.txnDet.custcrdtrfinitAddinfDto.suppressCoverMessage = "";
		spReq.txnDet.custcrdtrfinitAddinfDto.processWithoutCover = "";
		
		spReq.txnDet.misdetailsDto.misGroup = "";
		spReq.txnDet.misdetailsDto.txnmiscode1 = "";
		spReq.txnDet.misdetailsDto.txnMis1 = "";
		spReq.txnDet.misdetailsDto.txnmiscode2 = "";
		spReq.txnDet.misdetailsDto.txnMis2 = "";
		
		spReq.txnDet.txnUdfDetDto.add(new TxnUdfDetDto());
		
	}

}
