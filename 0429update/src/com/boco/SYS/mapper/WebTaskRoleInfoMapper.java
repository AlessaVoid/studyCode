package com.boco.SYS.mapper;

import java.util.HashMap;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebTaskRoleInfo;
/**
 * 
 * 
 * ����ڵ��ɫ��Ӧ��Ϣ��||����ڵ��ɫ��Ӧ��Ϣ�����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebTaskRoleInfoMapper extends GenericMapper<WebTaskRoleInfo,HashMap<String,Object>>{
 /**
  * 
  *
  * TODO ͨ�����̶���id��������
  *
  * @param procDefId
  *
  * <pre>
  * �޸�����        �޸���    �޸�ԭ��
  * 2016��6��13��    	   ������  �½�
  * </pre>
  */
	void deleteByProcDefId(String procDefId);
}