package com.ccy.common.zipkin;

import brave.Tracing;
import brave.context.slf4j.MDCScopeDecorator;
import brave.http.HttpTracing;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import brave.propagation.ThreadLocalCurrentTraceContext;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import brave.spring.webmvc.DelegatingTracingFilter;
import brave.spring.webmvc.SpanCustomizingAsyncHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;



/**
 * https://blog.csdn.net/luanpeng825485697/article/details/85772954
 */

/**
 * traceId：用来确定一个追踪链的16字符长度的字符串，在某个追踪链中保持不变。

 spanId：区域Id，在一个追踪链中spanId可能存在多个，每个spanId用于表明在某个服务中的身份，也是16字符长度的字符串。

 parentId：在跨服务调用者的spanId会传递给被调用者，被调用者会将调用者的spanId作为自己的parentId，然后自己再生成spanId。

 */
@Configuration
@Import({
        TracingClientHttpRequestInterceptor.class,
        SpanCustomizingAsyncHandlerInterceptor.class
})
public class TracingConfiguration extends WebMvcConfigurerAdapter {


    @Bean
    public DelegatingTracingFilter getDelegatingTracingFilter() {
        return new DelegatingTracingFilter();
    }

    @Bean
    Sender sender() {
        return OkHttpSender.create("http://127.0.0.1:9411/api/v2/spans");
    }


    @Bean
    AsyncReporter<Span> spanReporter() {
        return AsyncReporter.create(sender());
    }

    private static String ip="not find ";
    static{
        try {
            InetAddress address = InetAddress.getLocalHost();
            ip=address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /*
    * 标记链路的服务名.ip地址
    */
    @Bean
    Tracing tracing(@Value("${server.context-path}") String serviceName) {
        return Tracing.newBuilder()
                .localServiceName(serviceName)
                //.localIp(ip)
                .propagationFactory(ExtraFieldPropagation.newFactory(B3Propagation.FACTORY, "user-name"))
                .spanReporter(spanReporter()).currentTraceContext(ThreadLocalCurrentTraceContext.newBuilder()
                        .addScopeDecorator(MDCScopeDecorator.create()).build()).build();
    }

    @Bean
    HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }



    @Autowired
    TracingClientHttpRequestInterceptor clientInterceptor;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors =new ArrayList<>(restTemplate.getInterceptors());
        interceptors.add(clientInterceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Autowired
    SpanCustomizingAsyncHandlerInterceptor serverInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(serverInterceptor);
    }
}