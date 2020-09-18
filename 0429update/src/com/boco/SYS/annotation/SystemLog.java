package com.boco.SYS.annotation;  
  
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
  
/**
 * 
 * 
 *  日志拦截方法注解类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年12月8日    	    杨滔    新建
 * </pre>
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented  
public @interface SystemLog {
	/**
	 * 
	 *
	 * TODO 交易名称，如理财参数维护.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月1日    	    杨滔    新建
	 * </pre>
	 */
	String tradeName() default "";
	/**
	 * 
	 *
	 * TODO 功能编号.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月1日    	    杨滔    新建
	 * </pre>
	 */
	String funCode() default "";
	/**
	 * 
	 *
	 * TODO 功能名称.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月1日    	    杨滔    新建
	 * </pre>
	 */
	String funName() default "";
	/**
	 * 
	 *
	 * TODO 访问数据库方式，WRITE-写，READ-读.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月1日    	    杨滔    新建
	 * </pre>
	 */
	AccessType accessType() default AccessType.WRITE;
	/**
	 * 
	 *
	 * TODO 输出日志级别
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月1日    	    杨滔    新建
	 * </pre>
	 */
	Debug level() default Debug.INFO;
}  
  
