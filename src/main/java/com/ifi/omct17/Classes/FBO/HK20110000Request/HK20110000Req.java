package com.ifi.omct17.Classes.FBO.HK20110000Request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Tx")
public class HK20110000Req { 
	public FMPConnectionString FMPConnectionString;
	public TxHead TxHead;
	public TxBody TxBody;
}
