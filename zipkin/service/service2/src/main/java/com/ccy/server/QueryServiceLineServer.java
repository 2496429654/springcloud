package com.ccy.server;

import com.ccy.common.util.CcyHttpClient;
import com.ccy.common.util.YmlConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @descriprtion 查看链路
 */
@RequestMapping("queryServiceLineServer")
@RestController
public class QueryServiceLineServer {
    private static Log log = LogFactory.getLog(IpServer.class);
    @Autowired
    private YmlConfig ymlConfig;
    @Autowired
    private CcyHttpClient ccyHttpClient;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("querySeriviceName2")
    public String querySeriviceName2() throws IOException {
        log.info("---"+ymlConfig.getPort());
        String result=restTemplate.getForObject("http://localhost:9083/queryServiceLineServer/querySeriviceName3",String.class);
        String result2=restTemplate.getForObject("http://localhost:9084/queryServiceLineServer/querySeriviceName4",String.class);

        return ymlConfig.getPort()+" --- "+result+"----"+result2;
    }

}