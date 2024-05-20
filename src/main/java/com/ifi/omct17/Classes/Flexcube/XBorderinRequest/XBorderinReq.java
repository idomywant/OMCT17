package com.ifi.omct17.Classes.Flexcube.XBorderinRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("XBorderinRequest_XBorderinReq")
public class XBorderinReq{
    @Autowired
    @Qualifier("XBorderinRequest_Header") public Header header;

    @Autowired
    @Qualifier("XBorderinRequest_TxnDet") public TxnDet txnDet;
}
