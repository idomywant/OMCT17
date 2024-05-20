package com.ifi.omct17.Classes.FBO.HK20110000Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("HK20110000Response_DRINFO")
public class DRINFO { 
	public String DERRCOD;
	public String DERRMSG;
	public String DUPDSER;
	public String DRBANK;
	public String DRACCT;
	public String DRCCY;
	public String DRAMT;
	@Autowired @Qualifier("HK20110000Response_DCHINF1") public DCHINF1 DCHINF1;
	@Autowired @Qualifier("HK20110000Response_DCHINF2") public DCHINF2 DCHINF2;
}
