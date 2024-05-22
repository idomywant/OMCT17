package com.ifi.omct17.Classes.FBO.HK20110000Request;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Tx")
@Component("HK20110000Request_HK20110000Req")
public class HK20110000Req { 
	public FMPConnectionString FMPConnectionString;
	public TxHead TxHead;
	public TxBody TxBody;
}
