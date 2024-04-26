package com.ifi.omct17.Classes.Flexcube;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SinglepaymentRsp {
	// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
	// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
	/* ObjectMapper om = new ObjectMapper();
	Root root = om.readValue(myJsonString, Root.class); */
	public class Resp{
	    public String respCode;
	    public String respDesc;
	}

	public class Root{
	    public String txnrefno;
	    public String sourceCode;
	    public String endtoendid;
	    public String instrid;
	    @JsonProperty("MSGSTATUS") 
	    public String mSGSTATUS;
	    @JsonProperty("TXNID") 
	    public String tXNID;
	    public ArrayList<Resp> resp;
	}
}
