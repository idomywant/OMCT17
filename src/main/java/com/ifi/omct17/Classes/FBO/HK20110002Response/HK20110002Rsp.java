package com.ifi.omct17.Classes.FBO.HK20110002Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@Component
@JacksonXmlRootElement(localName = "Tx")
public class HK20110002Rsp { 
	@Autowired @Qualifier("HK20110002Response_TxHead") public TxHead TxHead;
	@Autowired @Qualifier("HK20110002Response_TxBody") public TxBody TxBody;
}
