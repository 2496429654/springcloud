package com.ccy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://blog.csdn.net/apei830/article/details/78722180
 */
@SpringBootApplication
public class CcyMainApplicationTest {
    private static Log log = LogFactory.getLog(CcyMainApplicationTest.class);


    public static void main(String[] args) {
        log.info("---------------开始启动----------------------------");
        SpringApplication application = new SpringApplication(CcyMainApplicationTest.class);
        //关闭spring字体设置
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
        log.info("---------------启动结束----------------------------");
    }

}