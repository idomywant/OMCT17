package com.ifi.omct17.Classes.FBO.HK20110000Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("HK20110000Request_Content")
public class Content {
	public String CUSTCOD;
	public String CUSTNAME;
	public String TXSER;
	public String TXTYPE;
	public String PRODCOD;
	public String RETRY;
	public String TXDATE;
	public String CRCNT;
	public String CRCCY;
	public String CRAMT;
	public String FLRMKS;
	public String PAYMENTDET1;
	public String PAYMENTDET2;
	public String PAYMENTDET3;
	public String PACKNO;
	@Autowired @Qualifier("HK20110000Request_DRINFO")
	public DRINFO DRINFO;
	@Autowired @Qualifier("HK20110000Request_CRINFO")
	public CRINFO CRINFO;
}
