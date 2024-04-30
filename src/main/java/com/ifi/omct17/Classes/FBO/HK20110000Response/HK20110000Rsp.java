
package com.ifi.omct17.Classes.FBO.HK20110000Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Tx")
@Component
public class HK20110000Rsp { 
	@Autowired @Qualifier("HK20110000Response_TxHead") public TxHead TxHead;
	@Autowired @Qualifier("HK20110000Response_TxBody") public TxBody TxBody;
}
