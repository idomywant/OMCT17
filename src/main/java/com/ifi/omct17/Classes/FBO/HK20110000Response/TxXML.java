package com.ifi.omct17.Classes.FBO.HK20110000Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("HK20110000Response_TxXML")
public class TxXML { 
	@Autowired @Qualifier("HK20110000Response_ResponseHeader") public ResponseHeader ResponseHeader;
	@Autowired @Qualifier("HK20110000Response_Content") public Content Content;
}
