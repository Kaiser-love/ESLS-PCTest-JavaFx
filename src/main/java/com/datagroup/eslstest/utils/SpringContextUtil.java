
package com.datagroup.eslstest.utils;

import com.datagroup.eslstest.netty.server.ServerChannelHandler;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    private static TextArea resultTextArea;

    public static void printBytes(String comment, byte[] message) {
        resultTextArea.appendText(comment + " ");
        for (byte b : message)
            resultTextArea.appendText(toHex(b) + " ");
        resultTextArea.appendText("\n");
    }
    public static void addString(String comment) {
        resultTextArea.appendText(comment + " ");
        resultTextArea.appendText("\n");
    }
    // 把byte 转化为两位十六进制数
    public static String toHex(byte b) {
        String result = Integer.toHexString(b & 0xFF);
        if (result.length() == 1) {
            result = '0' + result;
        }
        return result;
    }

    public static ServerChannelHandler serverChannelHandler;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextUtil.applicationContext == null) {
            SpringContextUtil.applicationContext = applicationContext;
        }
    }

    // 获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public SpringContextUtil() {
    }

    public static TextArea getResultTextArea() {
        return resultTextArea;
    }

    public static void setResultTextArea(TextArea resultTextArea) {
        SpringContextUtil.resultTextArea = resultTextArea;
    }
}
