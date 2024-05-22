package com.ifi.omct17.Classes.FBO.HK20110000Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("HK20110000Request_CRINFO")
public class CRINFO {
	public String CRSER;
	public String CRACCT;
	public String CRCCY2;
	public String CRAMT2;
	public String FILLER2;
	@Autowired @Qualifier("HK20110000Request_CCHINF1")
	public CCHINF1 CCHINF1;
	@Autowired @Qualifier("HK20110000Request_CCHINF2")
	public CCHINF2 CCHINF2;
}
