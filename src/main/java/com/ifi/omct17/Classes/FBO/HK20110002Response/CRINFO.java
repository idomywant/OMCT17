package com.ifi.omct17.Classes.FBO.HK20110002Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("HK20110002Response_CRINFO")
public class CRINFO { 
	public int CERRCOD;
	public Object CERRMSG;
	public double CUPDSER;
	public String CRSER;
	public Object CRFILL1;
	public Object CRFILL2;
	public int SWRTIND;
	@Autowired @Qualifier("HK20110002Response_CCHINF1") public CCHINF1 CCHINF1;
	@Autowired @Qualifier("HK20110002Response_CCHINF2") public CCHINF2 CCHINF2;
}
