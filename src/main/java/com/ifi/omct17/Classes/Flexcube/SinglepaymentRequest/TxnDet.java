package com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
@Component("SinglepaymentRequest_TxnDet")
public class TxnDet{
    public String transferType;
    public String sourceCode;
    public String txnBranch;
    public String hostCode;
    public String customerNo;
    public String bookDate;
    public String credttm;
    public String remarks;
    @Autowired @Qualifier("SinglepaymentRequest_CustcrdtrfinitPmtinfDto") public CustcrdtrfinitPmtinfDto custcrdtrfinitPmtinfDto;
    @Autowired @Qualifier("SinglepaymentRequest_CustcrdtrfinitCdttxinfDto") public CustcrdtrfinitCdttxinfDto custcrdtrfinitCdttxinfDto;
    @JsonProperty("CustcrdtrfinitChgDto") 
    public ArrayList<CustcrdtrfinitChgDto> custcrdtrfinitChgDto = new ArrayList();
    @Autowired @Qualifier("SinglepaymentRequest_CustcrdtrfinitAddinfDto") public CustcrdtrfinitAddinfDto custcrdtrfinitAddinfDto;
    @Autowired @Qualifier("SinglepaymentRequest_MisdetailsDto") public MisdetailsDto misdetailsDto;
    public ArrayList<TxnUdfDetDto> txnUdfDetDto = new ArrayList<>();
}
