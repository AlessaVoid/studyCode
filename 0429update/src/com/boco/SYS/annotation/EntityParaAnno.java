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
 *  ʵ��������ע��
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2016��3��9��    	    ������   �½�
 * </pre>
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented 
public @interface EntityParaAnno {
	String zhName();//����������
	String dicType() default "";//�ֵ������
}
