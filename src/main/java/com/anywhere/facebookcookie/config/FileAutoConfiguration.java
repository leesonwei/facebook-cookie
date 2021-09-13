package com.anywhere.facebookcookie.config;

import com.anywhere.facebookcookie.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * @author lizong
 * @createTime 2021-02-04
 * @Description TODO
 * @since 1.0.0
 */
@Slf4j
@Component
public class FileAutoConfiguration {

    @Autowired
    static CookieProperties cookieProperties;

    public static File createFile(){
        cookieProperties = SpringUtils.getBean("cookieProperties");
        String userDir = System.getProperty("user.dir");
        String path = userDir + cookieProperties.getFileName();
        File file = new File(path);
        try {
            FileUtils.forceMkdirParent(file);
            return file;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    public static void writeFile(File file, Cookie cookie){
        try {
            if (cookieProperties.getCookieKey().equals(cookie.getName())) {
                FileUtils.writeLines(file, Collections.singleton(cookie.getValue()), true);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
