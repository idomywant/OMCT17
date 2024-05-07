package com.ifi.omct17.Classes.Flexcube.SinglepaymentResponse;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
@Component("SinglepaymentResponse_SinglepaymentRsp")
public class SinglepaymentRsp{
    public String txnrefno;
    public String sourceCode;
    public String endtoendid;
    public String instrid;
    @JsonProperty("MSGSTATUS") 
    public String mSGSTATUS;
    @JsonProperty("TXNID") 
    public String tXNID;
    public ArrayList<Resp> resp = new ArrayList<>();
}
