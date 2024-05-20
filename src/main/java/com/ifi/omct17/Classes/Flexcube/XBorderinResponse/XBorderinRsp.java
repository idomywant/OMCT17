package com.ifi.omct17.Classes.Flexcube.XBorderinResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("XBorderinResponse_XBorderinRsp")
public class XBorderinRsp{
    public String txnRefNo;
    public String sourceCode;
    public String externalRefNo;
    public String relatedRefNo;
    @JsonProperty("MSGSTATUS")
    public String mSGSTATUS;
    @JsonProperty("TXNID") 
    public String tXNID;
    public ArrayList<Resp> resp = new ArrayList<>();
}
