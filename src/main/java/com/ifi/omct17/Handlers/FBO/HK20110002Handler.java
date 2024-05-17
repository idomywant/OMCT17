package com.ifi.omct17.Handlers.FBO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ifi.omct17.Classes.Common.ValidateException;
import com.ifi.omct17.Classes.FBO.HK20110002Request.HK20110002Req;
import com.ifi.omct17.Classes.FBO.HK20110002Response.HK20110002Rsp;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest.CustcrdtrfinitChgDto;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest.SinglepaymentReq;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest.TxnUdfDetDto;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentResponse.Resp;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentResponse.SinglepaymentRsp;
import com.ifi.omct17.Services.OBPM.Singlepayment.SinglepaymentService;

@Component
public class HK20110002Handler {

	@Autowired SinglepaymentService singlepaymentService;
	
	Logger logger = LogManager.getLogger(this.getClass());

	SinglepaymentReq singlepaymentReq;
	
	SinglepaymentRsp singlepaymentRsp;

	HK20110002Rsp hk20110002Rsp;

	HK20110002Req hk20110002Req = new HK20110002Req();

	/**
	 * procedure the logic process
	 * @param reqString - RESTful XML body
	 * @param iniHK20110002Rsp - response reference object
	 * @param iniSinglepaymentReq - singlepayment request object
	 * */
	public void Procedure(String reqString, HK20110002Rsp iniHK20110002Rsp,
			SinglepaymentReq iniSinglepaymentReq) throws Exception {
		// Step 0. Initial Objects
		this.hk20110002Rsp = iniHK20110002Rsp;

		this.singlepaymentReq = iniSinglepaymentReq;

		// Step 1. request message passer to HK20110002Req object
		PassRequest(reqString);

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
	 * When Exception occur
	 * */ 
	public void ExceptionResponse(Exception e) {
		if (e.getClass() == ValidateException.class) {
			this.hk20110002Rsp.TxHead.HERRID = "F077";
			this.hk20110002Rsp.TxBody.TxXML.ResponseHeader.ErrorCode = e.toString();
			this.hk20110002Rsp.TxBody.TxXML.ResponseHeader.ErrorMsg = ((ValidateException)e).validErrorMessage;
		}else if (e.getClass() == Exception.class) {
			this.hk20110002Rsp.TxHead.HERRID = "F078";
			this.hk20110002Rsp.TxBody.TxXML.ResponseHeader.ErrorCode = e.toString();
			this.hk20110002Rsp.TxBody.TxXML.ResponseHeader.ErrorMsg = e.getMessage();
		}

	}
	
	



	/**
	 * Step 1. request message passer to HK20110002Req object
	 * */ 
	private void PassRequest(String reqString) throws JsonMappingException, JsonProcessingException {
		XmlMapper xmlMapper = new XmlMapper();

		hk20110002Req = xmlMapper.readValue(reqString, HK20110002Req.class);

		logger.info("Finish Pass Tx");
	}

	/**
	 * Step 2. Validate Request object Value
	 * */ 
	private void ValidRequest() {
		// TODO Auto-generated method stub

	}

	/**
	 * Step 3. Request Field Mapping FBO request object to Singlepayment request object
	 * */ 
	private void RequestFieldMapping() throws Exception {
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

	/**
	 * Step 4. send single payment message to OBPM service
	 * */ 
	private void LaunchSinglePaymentService() throws Exception {
		
		singlepaymentRsp = singlepaymentService.SinglepaymentService(this.singlepaymentReq);
		
		logger.info(singlepaymentRsp.toString());
	}

	/**
	 * Step 5. Response Field Mapping Singlepayment boject to HK20110002Rsp object
	 * */ 
	private void ResponseFieldMapping() {
		
		//TxHead
		this.hk20110002Rsp.TxHead.HMSGID = "P";
		this.hk20110002Rsp.TxHead.HERRID = "9999"; // Unknow error.
		this.hk20110002Rsp.TxHead.HSYDAY = this.hk20110002Req.TxHead.HSYDAY;
		this.hk20110002Rsp.TxHead.HSYTIME = this.hk20110002Req.TxHead.HSYTIME;
		this.hk20110002Rsp.TxHead.HWSID = this.hk20110002Req.TxHead.HWSID;
		this.hk20110002Rsp.TxHead.HSTANO = this.hk20110002Req.TxHead.HSTANO;
		this.hk20110002Rsp.TxHead.HDTLEN = "Unknow";
		this.hk20110002Rsp.TxHead.HDRVQ1 = "Unknow";
		this.hk20110002Rsp.TxHead.HSYCVD = "Unknow";
		this.hk20110002Rsp.TxHead.HTXTID = this.hk20110002Req.TxHead.HTXTID;
		this.hk20110002Rsp.TxHead.HRETRN = this.hk20110002Req.TxHead.HRETRN;
		
		this.hk20110002Rsp.TxBody.TxXML.ResponseHeader.FuncId = this.hk20110002Req.TxBody.TxXML.RequestHeader.FuncId;
		this.hk20110002Rsp.TxBody.TxXML.ResponseHeader.TerminalId = this.hk20110002Req.TxBody.TxXML.RequestHeader.TerminalId;
		this.hk20110002Rsp.TxBody.TxXML.ResponseHeader.AskSNO = this.hk20110002Req.TxBody.TxXML.RequestHeader.AskSNO;
		// this function is determine the transaction is success or fail
		this.SinglepaymentRspResulMapping();
		
		// OBPM  response success or fail different response is vie singlepaymentRes this part is common information
		//this.hk20110002Rsp.TxBody.TxXML.Content.DRINFO.DUPDSER = singlepaymentRsp.txnrefno;
		//this.hk20110002Rsp.TxBody.TxXML.Content.CRINFO.CUPDSER = singlepaymentRsp.txnrefno;
		this.hk20110002Rsp.TxBody.TxXML.Content.CRINFO.CRSER = this.GetInstridBySpecRule(singlepaymentRsp.instrid);
		this.hk20110002Rsp.TxBody.TxXML.Content.TXSER = this.GetInstridBySpecRule(singlepaymentRsp.instrid);
		
		
	}
	
	/**
	 * Get Instrid by spec Rule FBO.OBPM Service v6.7
	 * */ 
	private String GetInstridBySpecRule(String sInstrid)
	{
		return "715" + (singlepaymentRsp.instrid.substring(0, 7)) + "0" + singlepaymentRsp.instrid.substring(7, 16);
	}
	
	/**
	 * determine the singlepayment response is success or failed
	 * */ 
	private void SinglepaymentRspResulMapping() {
		// Singlepayment service response success
		if(singlepaymentRsp.resp.size() == 1 && ((Resp)singlepaymentRsp.resp.get(0)).respCode.trim().equalsIgnoreCase("PM-TXP-001"))
		{
			this.hk20110002Rsp.TxHead.HERRID = "0000";
			//success TxHead
			this.hk20110002Rsp.TxBody.TxXML.ResponseHeader.ErrorCode = "000000";
			this.hk20110002Rsp.TxBody.TxXML.ResponseHeader.ErrorMsg = "";
			
			//Success TxBody
			this.hk20110002Rsp.TxBody.TxXML.Content.CRAMT = this.hk20110002Req.TxBody.TxXML.Content.CRAMT;
			
			this.hk20110002Rsp.TxBody.TxXML.Content.PRODCOD = this.hk20110002Req.TxBody.TxXML.Content.PRODCOD;
			
			this.hk20110002Rsp.TxBody.TxXML.Content.DRINFO.DERRCOD = "000000";
			this.hk20110002Rsp.TxBody.TxXML.Content.DRINFO.DERRMSG = "";
			this.hk20110002Rsp.TxBody.TxXML.Content.DRINFO.DUPDSER = singlepaymentRsp.txnrefno;
			this.hk20110002Rsp.TxBody.TxXML.Content.DRINFO.DRBANK = this.hk20110002Req.TxBody.TxXML.Content.DRINFO.DRBANK;
			this.hk20110002Rsp.TxBody.TxXML.Content.DRINFO.DRACCT = this.hk20110002Req.TxBody.TxXML.Content.DRINFO.DRACCT;
			this.hk20110002Rsp.TxBody.TxXML.Content.DRINFO.DRCCY = this.hk20110002Req.TxBody.TxXML.Content.DRINFO.DRCCY;
			this.hk20110002Rsp.TxBody.TxXML.Content.DRINFO.DRAMT = this.hk20110002Req.TxBody.TxXML.Content.DRINFO.DRAMT;
			this.hk20110002Rsp.TxBody.TxXML.Content.DRINFO.DCHINF1.DRCAMT1 = "00000000000000000";
			this.hk20110002Rsp.TxBody.TxXML.Content.DRINFO.DCHINF2.DRCAMT2 = "00000000000000000";
			
			this.hk20110002Rsp.TxBody.TxXML.Content.CRINFO.CERRCOD = "000000";
			this.hk20110002Rsp.TxBody.TxXML.Content.CRINFO.CERRMSG = "";
			this.hk20110002Rsp.TxBody.TxXML.Content.CRINFO.CUPDSER = singlepaymentRsp.txnrefno;
			this.hk20110002Rsp.TxBody.TxXML.Content.CRINFO.CRSER = this.GetInstridBySpecRule(singlepaymentRsp.instrid);
		}else {
			this.hk20110002Rsp.TxHead.HERRID = "F073";
			this.hk20110002Rsp.TxBody.TxXML.ResponseHeader.ErrorCode = ((Resp)singlepaymentRsp.resp.get(0)).respCode.trim();
			this.hk20110002Rsp.TxBody.TxXML.ResponseHeader.ErrorMsg = ((Resp)singlepaymentRsp.resp.get(0)).respDesc.trim();
		}
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
	
	/**
	 * 3.1
	 * Get Message ID from documentv6.5
	 * */
	private String GetMsgID() {
		String fboMsgID = hk20110002Req.TxHead.HTXTID + hk20110002Req.TxHead.HSYDAY + hk20110002Req.TxHead.HSYTIME
				+ hk20110002Req.TxBody.TxXML.Content.TXSER;
		return fboMsgID;
	}

	/**
	 * 3.2
	 * Get current date time format is yyyy-MM-dd
	 * @return yyyy-MM-dd current date time
	 */
	private String GetDtNow() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.now().format(formatter);
	}
	
	/**
	 * 3.3 use product code to case which intrmyagt1bicfi field
	 * @param pRODCOD
	 * */
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
	
	/**
	 * 3.4 Get TXSER 16 serial
	 * */
	private String GetTXSER_16(String TXSER) throws Exception
	{
		String returnResult = "";
		try
		{
			returnResult = TXSER.substring(3, 10) + TXSER.substring(11, 20);
		}
		catch(Exception ex)
		{
			throw new Exception("Function GetTXSER_16 field mapping Error: \n" + ex.getMessage());
		}
		return returnResult;
	}
	
	/**
	 * 3.5 Get TXSER 16 serial
	 * FTORCOMM2 FTORCABLE2
	 * */
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
	
	/**
	 * 3.6 check TxnUdfDetDto
	 * */
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
	
	/**
	 * 3.7 get RECVR by product code
	 * */
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
	
	/**
	 * Get SuppressCoverMsgFlag
	 * @return flag value Y or N
	 * */
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
