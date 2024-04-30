package com.ifi.omct17.Classes.Flexcube.SinglepaymentRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("SinglepaymentRequest_SinglepaymentReq")
public class SinglepaymentReq{
    @Autowired @Qualifier("SinglepaymentRequest_Header") public Header header;
    @Autowired @Qualifier("SinglepaymentRequest_TxnDet") public TxnDet txnDet;
}
