package com.boco.SYS.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebTaskRoleInfoNew;
/**
 * 
 * 
 * WebTaskRoleInfoNew���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebTaskRoleInfoNewMapper extends GenericMapper<WebTaskRoleInfoNew,HashMap<String,Object>>{

	public void deleteByProcDefId(@Param(value="procDefId")String procDefId);
}