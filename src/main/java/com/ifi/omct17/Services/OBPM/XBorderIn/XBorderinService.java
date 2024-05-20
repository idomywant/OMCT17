package com.ifi.omct17.Services.OBPM.XBorderIn;

import com.ifi.omct17.Classes.Common.LogException;
import com.ifi.omct17.Classes.Flexcube.XBorderinRequest.XBorderinReq;
import com.ifi.omct17.Classes.Flexcube.XBorderinResponse.XBorderinRsp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class XBorderinService {
    @Value("${serviceaddr.OBPM.XBorderin}")
    private String address;

    /**
     * This function is consuming OBPM XBorderIn RESTful API
     * @param xBorderinReq this is XBorderinReq object
     * @return XBorderin rsponse object
     * */
    public XBorderinRsp XBorderinService(XBorderinReq xBorderinReq) throws Exception {
        XBorderinRsp xBorderinRsp = null;

        try {
            RestTemplate restTemplate = new RestTemplate();

            xBorderinRsp = restTemplate.getForObject(address, XBorderinRsp.class);
        } catch (Exception e) {
            throw new LogException("XBorderinService() exception:" + e.getMessage());
        }

        return xBorderinRsp;
    }
}
