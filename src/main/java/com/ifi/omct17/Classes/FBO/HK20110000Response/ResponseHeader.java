package com.ifi.omct17.Classes.FBO.HK20110000Response;

import org.springframework.stereotype.Component;

@Component("HK20110000Response_ResponseHeader")
public class ResponseHeader { 
	public int FuncId;
	public int ErrorCode;
	public Object ErrorMsg;
	public int TerminalId;
	public String AskSNO;
}
