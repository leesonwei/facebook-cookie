package com.anywhere.facebookcookie.service.impl;

import com.anywhere.facebookcookie.command.CookieCommand;
import com.anywhere.facebookcookie.config.CookieProperties;
import com.anywhere.facebookcookie.config.HttpClientAutoConfiguration;
import com.anywhere.facebookcookie.service.CookieService;
import com.anywhere.facebookcookie.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lizong
 * @createTime 2021-02-02
 * @Description TODO
 * @since 1.0.0
 */
@Slf4j
@Service
@Scope("prototype")
public class CookieServiceImpl implements CookieService {
    @Autowired
    CookieProperties cookieProperties;
    @Autowired
    HttpClientAutoConfiguration httpClientAutoConfiguration;
    private boolean flag = true;

    @Async
    @Override
    public void getCookie() {
        while (flag) {
            try {
                CookieStore cookieStore = httpClientAutoConfiguration.basicCookieStore();
                CloseableHttpClient closeableHttpClient = httpClientAutoConfiguration.closeableHttpClient(cookieStore);
                HttpGet httpGet = httpClientAutoConfiguration.httpGet();
                CloseableHttpResponse execute = closeableHttpClient.execute(httpGet);
                log.info(execute.getStatusLine().getStatusCode() + ": " + cookieStore.getCookies().toString());
                TimeUnit.MILLISECONDS.sleep(cookieProperties.getIntervalMs());
            } catch (Exception e) {
                flag = false;
                CookieCommand cookieCommand = SpringUtils.getBean("cookieCommand");
                if (cookieCommand != null) {
                    cookieCommand.newThread();
                }
                log.error(e.getMessage(), e);
            }
        }
    }

}
