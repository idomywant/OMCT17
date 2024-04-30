package com.ifi.omct17.Classes.FBO.HK20110000Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("HK20110000Response_Content")
public class Content { 
	public int CUSTCOD;
	public String TXSER;
	public int TXTYPE;
	public Object RETRY;
	public Object CRCNT;
	public Object CRCCY;
	public String PRODCOD;
	public double CRAMT;
	public String FLRMKS;
	public Object PACKNO;
	public Object SUCCNT;
	@Autowired @Qualifier("HK20110000Response_DRINFO") public DRINFO DRINFO;
	@Autowired @Qualifier("HK20110000Response_CRINFO") public CRINFO CRINFO;
}
