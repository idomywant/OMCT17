package com.ifi.omct17.Classes.FBO.HK20110002Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("HK20110002Response_Content")
public class Content { 
	public String CUSTCOD;
	public String TXSER;
	public String TXTYPE;
	public String RETRY;
	public String CRCNT;
	public String CRCCY;
	public int CRAMT;
	public String FLRMKS;
	public String PACKNO;
	public String SUCCNT;
	public String PRODCOD;
	@Autowired @Qualifier("HK20110002Response_DRINFO") public DRINFO DRINFO;
	@Autowired @Qualifier("HK20110002Response_CRINFO") public CRINFO CRINFO;
	@Autowired @Qualifier("HK20110002Response_SWRTINF") public SWRTINF SWRTINF;
}
