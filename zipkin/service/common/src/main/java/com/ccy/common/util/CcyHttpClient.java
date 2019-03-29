package com.ccy.common.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CcyHttpClient {
    private static Log log = LogFactory.getLog(CcyHttpClient.class);

    /***
     * 发送get请求
     * @param url
     * @return
     * @throws IOException
     */
    public String httpGet(String url) throws IOException {

        log.info("MathHttpClient.httpGet----发送请求:"+url);
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = httpClient.newCall(request).execute();
        String result= response.body().string(); // 返回的是string 类型，json的mapper可以直接处理
        log.info("MathHttpClient.httpGet----发送请求:"+url+"  返回结果:"+result);
        return result;
    }

}