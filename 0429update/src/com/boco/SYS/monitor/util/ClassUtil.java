package com.boco.SYS.monitor.util;

import java.lang.reflect.Field;

/**
 * �෴�乤��
 * @Author zhuhongjiang
 * @Date 2020/1/6 ����2:44
 **/
public class ClassUtil {

    /**
     * ��ʼ��java����
     * @Author zhuhongjiang
     * @Date 2020/1/6 ����2:44
     **/
    public static void initObject(Object obj) throws IllegalAccessException {
        Class clazz = obj.getClass();
        Field[] fieldArr = clazz.getDeclaredFields();
        for(int i = 0 ; i < fieldArr.length; i++){
            Field field = fieldArr[i];
            field.setAccessible(true);
            String type = field.getType().toString();
            if (type.endsWith("String")) {
                field.set(obj, null);
            }else if(type.endsWith("int") || type.endsWith("Integer")){
                field.set(obj, 0);
            }else if(type.endsWith("long") || type.endsWith("Long")){
                field.set(obj, 0);
            }else if(type.endsWith("double") || type.endsWith("Double")){
                field.set(obj, 0);
            }else if(type.endsWith("Date")){
                field.set(obj, null);
            }
        }
    }
}
