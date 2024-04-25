package com.ifi.omct17.Classes.Flexcube;

public class SinglepaymentReq {
	// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
	// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
	/* ObjectMapper om = new ObjectMapper();
	Root root = om.readValue(myJsonString, Root.class); */
	public class CustcrdtrfinitAddinfDto{
	    public String instdrmbrsntagtbicfi;
	    public String creditNostro;
	    public String recvr;
	    public String suppressCoverMessage;
	    public String processWithoutCover;
	}

	public class CustcrdtrfinitCdttxinfDto{
	    public String cdtrnm;
	    public String cdtradrline1;
	    public String cdtradrline2;
	    public String cdtrctry;
	    public String cdtraccothrid;
	    public String instdamt;
	    public String instdamtccy;
	    public String cdtragtbicfi;
	    public String cdtragtnm;
	    public String cdtragtadrline1;
	    public String cdtragtadrline2;
	    public String cdtragtctry;
	    public String rmtinfustrd1;
	    public String rmtinfustrd2;
	    public String rmtinfustrd3;
	    public String rmtinfustrd4;
	    public String endtoendid;
	    public String instrid;
	}

	public class CustcrdtrfinitChgDto{
	    public String amount;
	    public String componentName;
	    public String pricingCcy;
	    public String pricingCode;
	}

	public class CustcrdtrfinitPmtinfDto{
	    public String dbtrnm;
	    public String dbtradrline1;
	    public String dbtradrline2;
	    public String dbtradrline3;
	    public Date reqdexctndt;
	    public String dbtraccothrid;
	    public String dbtraccccy;
	    public String chrgbr;
	    public String svclvlcd;
	}

	public class Header{
	    public String userId;
	    public String functionId;
	    public String action;
	    public String channel;
	    public String moduleId;
	    public String source;
	    public String hostCode;
	    public String entityId;
	    public String msgId;
	    public String userBranch;
	}

	public class MisdetailsDto{
	    public String misGroup;
	    public String txnmiscode1;
	    public String txnMis1;
	    public String txnmiscode2;
	    public String txnMis2;
	}

	public class Root{
	    public Header header;
	    public TxnDet txnDet;
	}

	public class TxnDet{
	    public String transferType;
	    public String sourceCode;
	    public String txnBranch;
	    public String hostCode;
	    public String customerNo;
	    public String bookDate;
	    public String credttm;
	    public CustcrdtrfinitPmtinfDto custcrdtrfinitPmtinfDto;
	    public CustcrdtrfinitCdttxinfDto custcrdtrfinitCdttxinfDto;
	    @JsonProperty("CustcrdtrfinitChgDto") 
	    public ArrayList<CustcrdtrfinitChgDto> custcrdtrfinitChgDto;
	    public CustcrdtrfinitAddinfDto custcrdtrfinitAddinfDto;
	    public MisdetailsDto misdetailsDto;
	    public ArrayList<TxnUdfDetDto> txnUdfDetDto;
	}

	public class TxnUdfDetDto{
	    public String fieldLabel;
	    public String fieldValue;
	}
}
