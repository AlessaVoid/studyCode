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
 *  ��־���ط���ע����.
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2015��12��8��    	    ����    �½�
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
	 * TODO �������ƣ�����Ʋ���ά��.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��1��    	    ����    �½�
	 * </pre>
	 */
	String tradeName() default "";
	/**
	 * 
	 *
	 * TODO ���ܱ��.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��1��    	    ����    �½�
	 * </pre>
	 */
	String funCode() default "";
	/**
	 * 
	 *
	 * TODO ��������.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��1��    	    ����    �½�
	 * </pre>
	 */
	String funName() default "";
	/**
	 * 
	 *
	 * TODO �������ݿⷽʽ��WRITE-д��READ-��.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��1��    	    ����    �½�
	 * </pre>
	 */
	AccessType accessType() default AccessType.WRITE;
	/**
	 * 
	 *
	 * TODO �����־����
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��1��    	    ����    �½�
	 * </pre>
	 */
	Debug level() default Debug.INFO;
}  
  
