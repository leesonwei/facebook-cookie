package com.anywhere.facebookcookie.command;

import com.anywhere.facebookcookie.config.CookieProperties;
import com.anywhere.facebookcookie.service.CookieService;
import com.anywhere.facebookcookie.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lizong
 * @createTime 2021-02-02
 * @Description TODO
 * @since 1.0.0
 */
@Slf4j
@Component
public class CookieCommand implements CommandLineRunner {
    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    private CookieProperties cookieProperties;

    @Override
    public void run(String... args){
        for (int i = 0; i < cookieProperties.getThreadNum(); i++) {
            newThread();
        }
    }

    public void newThread() {
        beanFactory.getBean(CookieService.class).getCookie();
        try {
            TimeUnit.MILLISECONDS.sleep(cookieProperties.getIntervalMs());
        } catch (InterruptedException e) {
            log.info(e.getMessage(), e);
        }
    }
}
