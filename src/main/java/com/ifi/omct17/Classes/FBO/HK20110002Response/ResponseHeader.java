package com.ifi.omct17.Classes.FBO.HK20110002Response;

import org.springframework.stereotype.Component;

@Component("HK20110002Response_ResponseHeader")
public class ResponseHeader { 
	public int FuncId;
	public int ErrorCode;
	public String ErrorMsg;
	public int TerminalId;
	public String AskSNO;
}
