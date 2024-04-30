package com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest;

import org.springframework.stereotype.Component;

@Component("SinglepaymentRequest_Header")
public class Header{
    public String userId;
    public String functionId;
    public String action;
    public String channel;
    public String moduleId;
    public String source;
    public String hostCode;
    public String entityId;
    public String msgId;
    public String userBranch;
}
