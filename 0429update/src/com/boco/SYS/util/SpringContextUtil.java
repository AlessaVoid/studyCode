package com.boco.SYS.util;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
 
/**
 * ��ȡspring�������Է��������ж��������bean
 */
@Component
public class SpringContextUtil implements ApplicationContextAware{
 
    private static ApplicationContext   applicationContext;
 
    /**
     * ʵ��ApplicationContextAware�ӿڵĻص����������������Ļ���
     */
    public void setApplicationContext(ApplicationContext applicationContext){
        SpringContextUtil.applicationContext = applicationContext;
    }
 
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
    /**
     * ��ȡ����
     */
    public static Object getBean(String name) throws BeansException{
        return applicationContext.getBean(name);
    }
}