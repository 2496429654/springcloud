package com.ccy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CcyMainApplication5 {
    private static Log log = LogFactory.getLog(CcyMainApplication5.class);


    public static void main(String[] args) {
        log.info("---------------开始启动----------------------------");
        SpringApplication application = new SpringApplication(CcyMainApplication5.class);
        //关闭spring字体设置
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
        log.info("---------------启动结束----------------------------");
    }

}