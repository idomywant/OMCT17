package com.ifi.omct17.Handlers.FBO;

import com.ifi.omct17.Classes.Common.LogException;
import com.ifi.omct17.Classes.Flexcube.SinglepaymentResponse.Resp;
import com.ifi.omct17.Classes.Flexcube.XBorderinRequest.XBorderinReq;
import com.ifi.omct17.Classes.Flexcube.XBorderinResponse.XBorderinRsp;
import com.ifi.omct17.Services.OBPM.XBorderIn.XBorderinService;
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

    @Autowired
    SinglepaymentService singlepaymentService;

    @Autowired
    XBorderinService xBorderinService;

    Logger logger = LogManager.getLogger(this.getClass());

    SinglepaymentReq singlepaymentReq = new SinglepaymentReq();

    SinglepaymentRsp singlepaymentRsp;

    XBorderinReq xBorderinReq = new XBorderinReq();

    XBorderinRsp xBorderinRsp;

    HK20110000Req hk20110000Req = new HK20110000Req();

    HK20110000Rsp hk20110000Rsp;


    public void Procedure(String requestString, HK20110000Rsp iniHk20110000Rsp, SinglepaymentReq iniSinglepaymentReq) throws ValidateException, Exception {


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
        ConsumeOBPMService();

        // Step 5. Response Field Mapping Singlepayment boject to HK20110002Rsp object
        ResponseFieldMapping();
    }

    /**
     * Step 1. request message passer to HK20110002Req object
     */
    private void PassRequest(String xmlString) throws JsonMappingException, JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();

        this.hk20110000Req = xmlMapper.readValue(xmlString, HK20110000Req.class);
        logger.info("Finish Pass Tx");
    }

    /**
     * Step 2. Validate Request object Value
     */
    private void ValidRequest() throws ValidateException {

        if (!this.hk20110000Req.TxHead.HMSGID.trim().equalsIgnoreCase("P")) {
            throw new ValidateException("HMSGID value Error");
        }

    }

    /**
     * Step 3. Request Field Mapping FBO request object to Singlepayment request object
     */
    private void RequestFieldMapping() throws Exception {
        switch (hk20110000Req.TxBody.TxXML.Content.PRODCOD.trim()){
            case "FIB0":
                this.XBorderInRequestMapping();
                break;
            case "FOB0":
            case "FNB0":
                this.SinglepaymentRequestMapping();
                break;
            default:
                throw new LogException("HK20110000Handler ConsumeOBPMService error PRODUCT CODE is not define.");
        }
    }



    /**
     * 3.1 Product code is FIB0 so consume Xborder serivce.
     * mapping the XborderInRequest object
     * */
    private void XBorderInRequestMapping() {
        this.xBorderinReq.header.userId = "OMC_HK20110000";
        this.xBorderinReq.header.functionId = "PXDITONAL";
        this.xBorderinReq.header.action = "NEW";
        this.xBorderinReq.header.channel = "REST";
        this.xBorderinReq.header.moduleId = "PM";
        this.xBorderinReq.header.hostCode = "TFBHKB";
        this.xBorderinReq.header.entityId = "ENTITY_ID1";
        this.xBorderinReq.header.msgId = GetMsgID();

        this.xBorderinReq.txnDet.sourceCode = "FBO";
        this.xBorderinReq.txnDet.transferType = "C";
    }

    /**
     * 3.2 Product code is FOB0/FNB0 consume Singlepayment service.
     * mapping the Singlepayment Request object
     * */
    private void SinglepaymentRequestMapping() throws Exception {
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

        this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.cdtraccothrid = hk20110000Req.TxBody.TxXML.Content.CRINFO.CRACCT;
        this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.instdamtccy = hk20110000Req.TxBody.TxXML.Content.CRINFO.CRCCY2;
        this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.instdamt = hk20110000Req.TxBody.TxXML.Content.CRINFO.CRAMT2;
        this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.rmtinfustrd1 = hk20110000Req.TxBody.TxXML.Content.PAYMENTDET1;
        this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.rmtinfustrd2 = hk20110000Req.TxBody.TxXML.Content.PAYMENTDET2;
        this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.rmtinfustrd3 = hk20110000Req.TxBody.TxXML.Content.PAYMENTDET3;

        this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.instrid = this.GetTXSER_16(hk20110000Req.TxBody.TxXML.Content.TXSER.trim());
        this.singlepaymentReq.txnDet.custcrdtrfinitCdttxinfDto.endtoendid = this.GetTXSER_16(hk20110000Req.TxBody.TxXML.Content.TXSER.trim());
    }

    /**
     * Step 4. send single payment message to OBPM service
     * This will customize Remittence Rule
     * Product code is FIB0 consume to xborderin Service
     * product code is FOB0/FNB0 consume SinglePayment serivce
     */
    private void ConsumeOBPMService() throws Exception {
        switch (hk20110000Req.TxBody.TxXML.Content.PRODCOD.trim()){
            case "FIB0":
                xBorderinRsp = xBorderinService.XBorderinService(xBorderinReq);
                break;
            case "FOB0":
            case "FNB0":
                singlepaymentRsp = singlepaymentService.SinglepaymentService(singlepaymentReq);
                break;
            default:
                throw new LogException("HK20110000Handler ConsumeOBPMService error PRODUCT CODE is not define.");
        }
    }


    /**
     * Step 5. Response Field Mapping Singlepayment boject to HK20110002Rsp object
     */
    private void ResponseFieldMapping() throws LogException {
        switch (hk20110000Req.TxBody.TxXML.Content.PRODCOD.trim()){
            case "FIB0":
                this.XBorderinResponseMapping();
                break;
            case "FOB0":
            case "FNB0":
                this.SinglepaymentRsponseMapping();
                break;
            default:
                throw new LogException("HK20110000Handler ResponseFieldMapping() PRODUCT CODE is not define.");
        }
    }

    /**
     * Step 5.1 Singlepayment Response Mapping
     * */
    private void XBorderinResponseMapping()
    {

    }

    /**
     * Step 5.2 Singlepayment Response Mapping
     * */
    private void SinglepaymentRsponseMapping()
    {
        if(singlepaymentRsp.resp.size() == 1 && ((Resp)singlepaymentRsp.resp.get(0)).respCode.trim().equalsIgnoreCase("PM-TXP-001"))
        {
            //success TxHead
            this.hk20110000Rsp.TxHead.HMSGID = this.hk20110000Req.TxHead.HMSGID;
            this.hk20110000Rsp.TxHead.HERRID = this.hk20110000Req.TxHead.HERRID;
            this.hk20110000Rsp.TxHead.HSYDAY = this.hk20110000Req.TxHead.HSYDAY;
            this.hk20110000Rsp.TxHead.HSYTIME = this.hk20110000Req.TxHead.HSYTIME;
            this.hk20110000Rsp.TxHead.HWSID = this.hk20110000Req.TxHead.HWSID;
            this.hk20110000Rsp.TxHead.HSTANO = this.hk20110000Req.TxHead.HSTANO;
            this.hk20110000Rsp.TxHead.HDTLEN = this.hk20110000Req.TxHead.HDTLEN;
            this.hk20110000Rsp.TxHead.HREQQ1 = this.hk20110000Req.TxHead.HREQQ1;
            this.hk20110000Rsp.TxHead.HREPQ1 = this.hk20110000Req.TxHead.HREPQ1;
            this.hk20110000Rsp.TxHead.HDRVQ1 = this.hk20110000Req.TxHead.HDRVQ1;
            this.hk20110000Rsp.TxHead.HPVDQ1 = this.hk20110000Req.TxHead.HPVDQ1;
            this.hk20110000Rsp.TxHead.HPVDQ2 = this.hk20110000Req.TxHead.HPVDQ2;
            this.hk20110000Rsp.TxHead.HSYCVD = this.hk20110000Req.TxHead.HSYCVD;
            this.hk20110000Rsp.TxHead.HTLID = this.hk20110000Req.TxHead.HTLID;
            this.hk20110000Rsp.TxHead.HTXTID = this.hk20110000Req.TxHead.HTXTID;
            this.hk20110000Rsp.TxHead.HFMTID = this.hk20110000Req.TxHead.HFMTID;
            this.hk20110000Rsp.TxHead.HRETRN = this.hk20110000Req.TxHead.HRETRN;
            this.hk20110000Rsp.TxHead.HSLGNF = this.hk20110000Req.TxHead.HSLGNF;
            this.hk20110000Rsp.TxHead.HSPSCK = this.hk20110000Req.TxHead.HSPSCK;
            this.hk20110000Rsp.TxHead.HRTNCD = this.hk20110000Req.TxHead.HRTNCD;
            this.hk20110000Rsp.TxHead.HSBTRF = this.hk20110000Req.TxHead.HSBTRF;
            this.hk20110000Rsp.TxHead.HFILL = "";

            //Success ResponseHeader
            this.hk20110000Rsp.TxBody.TxXML.ResponseHeader.FuncId = this.hk20110000Req.TxBody.TxXML.RequestHeader.FuncId;
            this.hk20110000Rsp.TxBody.TxXML.ResponseHeader.ErrorCode = "000000";
            this.hk20110000Rsp.TxBody.TxXML.ResponseHeader.ErrorMsg = "";
            this.hk20110000Rsp.TxBody.TxXML.ResponseHeader.TerminalId = this.hk20110000Req.TxBody.TxXML.RequestHeader.TerminalId;
            this.hk20110000Rsp.TxBody.TxXML.ResponseHeader.AskSNO = this.hk20110000Req.TxBody.TxXML.RequestHeader.AskSNO;

            //Content
            this.hk20110000Rsp.TxBody.TxXML.Content.CUSTCOD = this.hk20110000Req.TxBody.TxXML.Content.CUSTCOD;
            this.hk20110000Rsp.TxBody.TxXML.Content.TXSER = this.hk20110000Req.TxBody.TxXML.Content.TXSER;
            this.hk20110000Rsp.TxBody.TxXML.Content.TXTYPE = this.hk20110000Req.TxBody.TxXML.Content.TXTYPE;
            this.hk20110000Rsp.TxBody.TxXML.Content.PRODCOD = this.hk20110000Req.TxBody.TxXML.Content.PRODCOD;
            this.hk20110000Rsp.TxBody.TxXML.Content.CRAMT = this.hk20110000Req.TxBody.TxXML.Content.CRAMT;
            this.hk20110000Rsp.TxBody.TxXML.Content.FLRMKS = this.hk20110000Req.TxBody.TxXML.Content.FLRMKS;
            this.hk20110000Rsp.TxBody.TxXML.Content.DRINFO.DERRCOD = "000000";
            this.hk20110000Rsp.TxBody.TxXML.Content.DRINFO.DERRMSG = "";
            this.hk20110000Rsp.TxBody.TxXML.Content.DRINFO.DUPDSER = singlepaymentRsp.txnrefno;
            this.hk20110000Rsp.TxBody.TxXML.Content.DRINFO.DRBANK = this.hk20110000Req.TxBody.TxXML.Content.DRINFO.DRBANK;
            this.hk20110000Rsp.TxBody.TxXML.Content.DRINFO.DRACCT = this.hk20110000Req.TxBody.TxXML.Content.DRINFO.DRACCT;
            this.hk20110000Rsp.TxBody.TxXML.Content.DRINFO.DRCCY = this.hk20110000Req.TxBody.TxXML.Content.DRINFO.DRCCY;
            this.hk20110000Rsp.TxBody.TxXML.Content.DRINFO.DRAMT = this.hk20110000Req.TxBody.TxXML.Content.DRINFO.DRAMT;
            this.hk20110000Rsp.TxBody.TxXML.Content.DRINFO.DCHINF1.DRCAMT1 = "00000000000000000";
            this.hk20110000Rsp.TxBody.TxXML.Content.DRINFO.DCHINF2.DRCAMT2 = "00000000000000000";

            this.hk20110000Rsp.TxBody.TxXML.Content.CRINFO.CERRCOD = "000000";
            this.hk20110000Rsp.TxBody.TxXML.Content.CRINFO.CERRMSG = "";
            this.hk20110000Rsp.TxBody.TxXML.Content.CRINFO.CUPDSER = singlepaymentRsp.txnrefno;
            this.hk20110000Rsp.TxBody.TxXML.Content.CRINFO.CRSER = this.GetInstridBySpecRule(singlepaymentRsp.instrid);
        }else {
            this.hk20110000Rsp.TxHead.HERRID = "F073";
            this.hk20110000Rsp.TxBody.TxXML.ResponseHeader.ErrorCode = ((Resp)singlepaymentRsp.resp.get(0)).respCode.trim();
            this.hk20110000Rsp.TxBody.TxXML.ResponseHeader.ErrorMsg = ((Resp)singlepaymentRsp.resp.get(0)).respDesc.trim();
        }

    }



    /**
     * 5.2.1
     * */
    private String GetInstridBySpecRule(String sInstrid)
    {
        return "715" + (singlepaymentRsp.instrid.substring(0, 7)) + "0" + singlepaymentRsp.instrid.substring(7, 16);
    }

    /**
     * 3.1
     * Get Message ID from documentv6.5
     */
    private String GetMsgID() {
        String fboMsgID = hk20110000Req.TxHead.HTXTID + hk20110000Req.TxHead.HSYDAY + hk20110000Req.TxHead.HSYTIME
                + hk20110000Req.TxBody.TxXML.Content.TXSER;
        return fboMsgID;
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


}
