package com.anywhere.facebookcookie.config;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @author lizong
 * @createTime 2021-02-02
 * @Description TODO
 * @since 1.0.0
 */
@Configuration
public class HttpClientAutoConfiguration {
    CookieProperties cookieProperties;

    @Autowired
    public HttpClientAutoConfiguration(CookieProperties cookieProperties){
        this.cookieProperties = cookieProperties;
    }

    public CloseableHttpClient closeableHttpClient(CookieStore cookieStore){
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        if (cookieProperties.getCustomerProxy()) {
            httpClientBuilder.setProxy(httpHost());
        }
        List<Header> defaultHeaders = Arrays.asList(new BasicHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 FireFox/50.0"));
        httpClientBuilder.setDefaultHeaders(defaultHeaders);
        httpClientBuilder.setDefaultCookieStore(cookieStore);
        CloseableHttpClient httpClient = httpClientBuilder.build();
        return httpClient;
    }


    public HttpGet httpGet(){
        HttpGet httpGet = new HttpGet(cookieProperties.getUrl());
        return httpGet;
    }


    public CookieStore basicCookieStore(){
        CookieStore cookieStore = new CustomerCookieStore();
        return cookieStore;
    }


    public HttpHost httpHost(){
        HttpHost httpHost = null;
        if (cookieProperties.getCustomerProxy()) {
            httpHost = new HttpHost(cookieProperties.getProxyHost(), cookieProperties.getProxyPort());
        }
        return httpHost;
    }
}
