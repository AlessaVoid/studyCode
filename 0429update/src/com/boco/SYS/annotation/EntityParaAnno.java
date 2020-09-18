package com.boco.SYS.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 
 *  实体类属性注解
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年3月9日    	    谷立羊   新建
 * </pre>
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented 
public @interface EntityParaAnno {
	String zhName();//参数中文名
	String dicType() default "";//字典表类型
}
