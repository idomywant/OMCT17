package com.ifi.omct17.Classes.FBO.HK20110000Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("HK20110000Response_Content")
public class Content { 
	public String CUSTCOD;
	public String TXSER;
	public String TXTYPE;
	public String RETRY;
	public String CRCNT;
	public String CRCCY;
	public String PRODCOD;
	public String CRAMT;
	public String FLRMKS;
	public String PACKNO;
	public String SUCCNT;
	@Autowired @Qualifier("HK20110000Response_DRINFO") public DRINFO DRINFO;
	@Autowired @Qualifier("HK20110000Response_CRINFO") public CRINFO CRINFO;
}
