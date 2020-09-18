package com.boco.SYS.mapper;

import java.util.List;
import java.util.Map;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.ActRuTask;
/**
 * 
 * 
 * ActRuTask���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface ActRuTaskMapper extends GenericMapper<ActRuTask,java.lang.String>{
	/**
	 * 
	 *
	 * TODO ��Ʒ���������ĳ����Ա��ĳ���׶Σ�Ҫ��������Ϣ�б�.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��4��    	  ����    �½�
	 * </pre>
	 */
	public List<ActRuTask> getActRuTaskDesign(Map map);
	/**
	 * 
	 *
	 * TODO ���ڰ���������ĳ����Ա��ĳ���׶Σ�Ҫ��������Ϣ�б�.
	 *
	 * @param processDefinitionKey
	 * @param assignee
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��3��    	  ����    �½�
	 * </pre>
	 */
	public List<Map<String,String>> getActRuTaskSchedule(Map map);
}