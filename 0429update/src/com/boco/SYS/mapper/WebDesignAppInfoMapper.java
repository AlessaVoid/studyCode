package com.boco.SYS.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.boco.SYS.entity.WebDesignAppInfo;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * �ñ�洢��Ʋ�Ʒ���������Ϣ��¼���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebDesignAppInfoMapper extends GenericMapper<WebDesignAppInfo,java.lang.String>{
	/**
	 * 
	 *
	 * TODO ���ݵ��ڱ�Ų�ѯ��Ʒ
	 *
	 * @param scheduleCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��9��19��    	   ������  �½�
	 * </pre>
	 */
	List<WebDesignAppInfo> selectByScheduleCode(@Param(value="scheduleCode")String scheduleCode);
}