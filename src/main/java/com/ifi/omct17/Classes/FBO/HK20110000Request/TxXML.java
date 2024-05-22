package com.ifi.omct17.Classes.FBO.HK20110000Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("HK20110000Request_TxXML")
public class TxXML {
	@Autowired @Qualifier("HK20110000Request_RequestHeader")
	public RequestHeader RequestHeader;
	@Autowired @Qualifier("HK20110000Request_Content")
	public Content Content;
}
