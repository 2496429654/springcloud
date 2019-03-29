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

    @RequestMapping("querySeriviceName")
    public String querySeriviceName() throws IOException {
        log.info("---"+ymlConfig.getPort());
       // String result=ccyHttpClient.httpGet("http://localhost:9082/queryServiceLineServer/querySeriviceName2");
        String result=restTemplate.getForObject("http://localhost:9082/queryServiceLineServer/querySeriviceName2", String.class);

        return ymlConfig.getPort()+" --- "+result;
    }

}