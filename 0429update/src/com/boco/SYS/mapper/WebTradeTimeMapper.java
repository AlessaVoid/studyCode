package com.boco.SYS.mapper;

import org.apache.ibatis.annotations.Param;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebTradeTime;
/**
 * 
 * 
 * WebTradeTime���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebTradeTimeMapper extends GenericMapper<WebTradeTime,java.lang.String>{

	public void reSetTradeTime(@Param(value="menuCode")String menuCode);
}