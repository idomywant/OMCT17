package com.ifi.omct17.Classes.FBO.HK20110002Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("HK20110002Response_TxBody")
public class TxBody { 
	@Autowired @Qualifier("HK20110002Response_TxXML") public TxXML TxXML;
}
