package com.boco.SYS.monitor.util;

import java.lang.reflect.Field;

/**
 * 类反射工具
 * @Author zhuhongjiang
 * @Date 2020/1/6 上午2:44
 **/
public class ClassUtil {

    /**
     * 初始化java对象
     * @Author zhuhongjiang
     * @Date 2020/1/6 上午2:44
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
