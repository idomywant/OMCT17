package com.ifi.omct17.Handlers.FBO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ifi.omct17.Classes.FBO.HK20110002Request.HK20110002Req;
import com.ifi.omct17.Classes.FBO.HK20110002Response.HK20110002Rsp;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest.CustcrdtrfinitChgDto;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest.SinglepaymentReq;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest.TxnUdfDetDto;

@Component
public class HK20110002Handler {

	Logger logger = LogManager.getLogger(this.getClass());

	SinglepaymentReq singlepaymentReq;

	HK20110002Rsp hk20110002Rsp;

	HK20110002Req hk20110002Req = new HK20110002Req();

	public HK20110002Rsp Prcedure(String reqString, HK20110002Rsp iniHK20110002Rsp,
			SinglepaymentReq iniSinglepaymentReq) throws Exception {
		// Step 0. Initial Objects
		this.hk20110002Rsp = iniHK20110002Rsp;

		this.singlepaymentReq = iniSinglepaymentReq;

		// Step 1. pass request
		PassRequest(reqString);

		// Step 2. Validate Request Value
		ValidRequest();

		// step 3. Mapping Field
		FieldMapping();
		
		// step 4. send OBPM single payment
		LaunchSinglePaymentService();
		
		return this.hk20110002Rsp;
	}

	

	// Step 1.
	private void PassRequest(String reqString) throws JsonMappingException, JsonProcessingException {
		XmlMapper xmlMapper = new XmlMapper();

		hk20110002Req = xmlMapper.readValue(reqString, HK20110002Req.class);

		logger.info("Finish Pass Tx");
	}

	// Step 2.
	private void ValidRequest() {
		// TODO Auto-generated method stub

	}

	// Step 3.
	private void FieldMapping() throws Exception {
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
		this.singlepaymentReq.txnDet.transferType = "C";
		this.singlepaymentReq.txnDet.sourceCode = "FBO";
		this.singlepaymentReq.txnDet.txnBranch = "950";
		this.singlepaymentReq.txnDet.customerNo = hk20110002Req.TxBody.TxXML.Content.CUSTCOD;
		this.singlepaymentReq.txnDet.bookDate = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.WNSDATE;
		this.singlepaymentReq.txnDet.credttm = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.WNSDATE;
		// this.singlepaymentReq.txnDet.remarks =
		// hk20110002Req.TxBody.TxXML.Content.TXSER;
		this.singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.dbtrnm = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.R30CUNM;
		this.singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.dbtradrline1 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.R30CUA1;
		this.singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.dbtradrline2 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.R30CUA2;
		this.singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.dbtradrline3 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.R30CUA3;
		this.singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.reqdexctndt = GetDtNow();
		this.singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.dbtraccothrid = hk20110002Req.TxBody.TxXML.Content.DRINFO.DRACCT
				.trim();
		this.singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.dbtraccccy = hk20110002Req.TxBody.TxXML.Content.DRINFO.DRCCY;
		this.singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.svclvlcd = hk20110002Req.TxBody.TxXML.Content.PRODCOD;
		this.singlepaymentReq.txnDet.custcrdtrfinitPmtinfDto.chrgbr = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BKCHRG;

		// custcrdtrfinitCdttxinfDto
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtrnm = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BENAC;
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtradrline1 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BENADD1;
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtradrline2 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BENADD2;
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtradrline3 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BENADD3;
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtradrline4 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BENADD4;
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtrctry = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BENCNTY;
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtraccothrid = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BENAC;
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.instdamtccy = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.TXCCY;
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.instdamt = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.TXAMT;
		// xchgrate Exchange Rate
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtbicfi = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.ACCBANK;
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtnm = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.ACCNAME;
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtadrline1 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.ACCADR1;
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtadrline2 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.ACCADR2;
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtadrline3 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.ACCADR3;
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtragtctry = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BENCNTY;
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.intrmyagt1bicfi = Get_intrmyagt1bicfi_Code(hk20110002Req.TxBody.TxXML.Content.PRODCOD);
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.instrid = GetTXSER_16(hk20110002Req.TxBody.TxXML.Content.TXSER);
		this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.endtoendid = GetTXSER_16(hk20110002Req.TxBody.TxXML.Content.TXSER);
		
		//custcrdtrfinitChgDto
		this.singlepaymentReq.txnDet.custcrdtrfinitChgDto.addAll(GetCustcrdtrfinitChgDtoArray());
		
		//txnUdfDeb
		this.singlepaymentReq.txnDet.txnUdfDetDto.addAll(GetTxnUdfDetDtoArrayList());
		
		//custcrdtrfinitAddinfDto
		this.singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.instdrmbrsntagtbicfi = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.R30RBCD;
		this.singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.creditNostro = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.R30CBCD;
		this.singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.recvr = GetRECVR();
		this.singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.suppressCoverMessage = GetSuppressCoverMsgFlag();
		// SuppressPaymentMessage
		GetsuppressPaymentMessage();
	}

	// Step 4.
	private void LaunchSinglePaymentService() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(this.singlepaymentReq);
		logger.info(json);
	}




	private void GetsuppressPaymentMessage() {
		if(hk20110002Req.TxBody.TxXML.Content.PRODCOD.trim().equalsIgnoreCase("FOB1") || hk20110002Req.TxBody.TxXML.Content.PRODCOD.trim().equalsIgnoreCase("FOB3") || hk20110002Req.TxBody.TxXML.Content.PRODCOD.trim().equalsIgnoreCase("FOB7"))
		{
			this.singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.sndrToRcvrInfo1 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BTBINF1;
			
			this.singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.sndrToRcvrInfo2 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BTBINF2;
			
			this.singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.sndrToRcvrInfo3 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BTBINF3;
			
			this.singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.sndrToRcvrInfo4 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BTBINF4;
			
			this.singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.sndrToRcvrInfo5 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BTBINF5;
		} 
		else if(hk20110002Req.TxBody.TxXML.Content.PRODCOD.trim().equalsIgnoreCase("FOB2") || hk20110002Req.TxBody.TxXML.Content.PRODCOD.trim().equalsIgnoreCase("FOB4"))
		{
			this.singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.sndrToRcvrInfoCov1 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BTBINF1;
			
			this.singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.sndrToRcvrInfoCov2 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BTBINF2;
			
			this.singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.sndrToRcvrInfoCov3 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BTBINF3;
			
			this.singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.sndrToRcvrInfoCov4 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BTBINF4;
			
			this.singlepaymentReq.txnDet.custcrdtrfinitAddinfDto.sndrToRcvrInfoCov5 = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.BTBINF5;
		}
		
	}

	private String GetMsgID() {
		String fboMsgID = hk20110002Req.TxHead.HTXTID + hk20110002Req.TxHead.HSYDAY + hk20110002Req.TxHead.HSYTIME
				+ hk20110002Req.TxBody.TxXML.Content.TXSER;
		return fboMsgID;
	}

	private String GetDtNow() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.now().format(formatter);
	}
	
	private String Get_intrmyagt1bicfi_Code(String pRODCOD) {
		String returnCode = "";
		switch (pRODCOD.trim()) {
		case "FOB1":
		case "FOB3":
		case "FOB7":
			returnCode = "R30RBCD";
			break;

		default:
			returnCode = "R30IBCD";
		}

		return returnCode;
	}
	
	private String GetTXSER_16(String TXSER) throws Exception
	{
		String returnResult = "";
		try
		{
			returnResult = TXSER.substring(4, 7) + TXSER.substring(12, 9);
		}
		catch(Exception ex)
		{
			throw new Exception("Function GetTXSER_16 field mapping Error: \n" + ex.getMessage());
		}
		return returnResult;
	}
	
	private ArrayList<CustcrdtrfinitChgDto> GetCustcrdtrfinitChgDtoArray() throws Exception
	{
		ArrayList<CustcrdtrfinitChgDto> arrayList = new ArrayList();
		
		try
		{
			CustcrdtrfinitChgDto dto1 = new CustcrdtrfinitChgDto();
			
			dto1.componentName = "FTORCOMM2";
			
			dto1.amount = hk20110002Req.TxBody.TxXML.Content.DRINFO.DCHINF1.DRCAMT1;
			
			dto1.pricingCcy = hk20110002Req.TxBody.TxXML.Content.DRINFO.DCHINF1.DRCCCY1;
			
			arrayList.add(dto1);
			
			CustcrdtrfinitChgDto dto2 = new CustcrdtrfinitChgDto();
			
			dto2.componentName = "FTORCABLE2";
			
			dto2.amount = hk20110002Req.TxBody.TxXML.Content.DRINFO.DCHINF2.DRCAMT2;
			
			dto2.pricingCcy = hk20110002Req.TxBody.TxXML.Content.DRINFO.DCHINF2.DRCCCY2;
			
			arrayList.add(dto2);
		}
		catch(Exception ex)
		{
			throw new Exception("function: GetCustcrdtrfinitChgDtoArray() : " + ex.getMessage());
		}
		
		return arrayList;
	}
	
	private ArrayList<TxnUdfDetDto> GetTxnUdfDetDtoArrayList() throws Exception
	{
		ArrayList<TxnUdfDetDto> arrayList = new ArrayList();
		
		try
		{
			if(hk20110002Req.TxBody.TxXML.Content.PRODCOD.trim().equalsIgnoreCase("FOB7"))
			{
				TxnUdfDetDto dto1 = new TxnUdfDetDto();
			
				dto1.fieldLabel = "NATURE_CNY";
			
				dto1.fieldValue = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.AMLPURPOSE;
				
				arrayList.add(dto1);
			}
		}
		catch(Exception ex)
		{
			throw new Exception("Error Mapping GetTxnUdfDetDtoArrayList:" + ex.getMessage());
		}
		
		return arrayList;
	}
	
	private String GetRECVR() {
		
		String returnString = "";
		
		if(hk20110002Req.TxBody.TxXML.Content.PRODCOD.equalsIgnoreCase("FOB4"))
		{
			returnString = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.ACCBANK;
		}else {
			returnString = hk20110002Req.TxBody.TxXML.Content.CRINFO.SWRTINF.SNDBANK;
		}
		
		return returnString;
	}
	
	private String GetSuppressCoverMsgFlag()
	{
		String returnString = "";
		if(hk20110002Req.TxBody.TxXML.Content.PRODCOD.trim().equalsIgnoreCase("FOB2") || hk20110002Req.TxBody.TxXML.Content.PRODCOD.trim().equalsIgnoreCase("FOB4"))
		{
			returnString = "Y";
		}else {
			returnString = "N";
		}
		return returnString;
	}
}
