package com.ccy.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RequestMapping("ipServer")
@RestController
public class IpServer {
    private static Log log = LogFactory.getLog(IpServer.class);

    @RequestMapping("queryIp")
    public String queryIp() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();//获得本机IP
            String address = addr.getHostName();//获得本机名称
            log.info(ip+"==="+address);
            return ip+"==="+address;
        } catch (UnknownHostException e) {
            return "UnknownHostException";
        }
    }
}