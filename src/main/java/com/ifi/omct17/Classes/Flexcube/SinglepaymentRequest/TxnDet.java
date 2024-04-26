package com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

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
