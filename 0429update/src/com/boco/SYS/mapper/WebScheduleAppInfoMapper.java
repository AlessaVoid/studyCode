package com.boco.SYS.mapper;

import java.util.List;
import java.util.Map;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebScheduleAppInfo;
/**
 * 
 * 
 * WebScheduleAppInfo���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebScheduleAppInfoMapper extends GenericMapper<WebScheduleAppInfo,java.lang.String>{
	/**
	 * 
	 *
	 * TODO ��ѯ����������Ϣ.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��15��    	  ����    �½�
	 * </pre>
	 */
	public List<WebScheduleAppInfo> selectByScheduleAudit(Map map);
	/**
	 * 
	 *
	 * TODO ����������¼��.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��15��    	  ����    �½�
	 * </pre>
	 */
	public int countByScheduleAudit(Map map);
	/**
	 * 
	 *
	 * TODO ��ѯ������������Ϣ.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��15��    	  ����    �½�
	 * </pre>
	 */
	public List<WebScheduleAppInfo> selectByScheduleAudited(Map map);
	/**
	 * 
	 *
	 * TODO ������������¼��.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��15��    	  ����    �½�
	 * </pre>
	 */
	public int countByScheduleAudited(Map map);
	/**
	 * 
	 *
	 * TODO ��ѯ�������ύ��Ϣ.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��15��    	  ����    �½�
	 * </pre>
	 */
	public List<WebScheduleAppInfo> selectByScheduleSubmit(Map map);
	/**
	 * 
	 *
	 * TODO �������ύ��¼��.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��15��    	  ����    �½�
	 * </pre>
	 */
	public int countByScheduleSubmit(Map map);
	/**
	 * 
	 *
	 * TODO ���ݵ��ڱ�ż���ѯ����.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��25��    	  ����    �½�
	 * </pre>
	 */
	public List<WebScheduleAppInfo> selectByScheduleCode(Map map);
	/**
	 * 
	 *
	 * TODO ��ѯ���ڴ�ǩ����Ϣ.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��25��    	  ����    �½�
	 * </pre>
	 */
	public List<WebScheduleAppInfo> selectByScheduleRev(Map map);
	/**
	 * 
	 *
	 * TODO ���ڴ�ǩ�ռ�¼��.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��25��    	  ����    �½�
	 * </pre>
	 */
	public int countByScheduleRev(Map map);
	/**
	 * 
	 *
	 * TODO ��ѯ���5������
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��9��19��    	   ������  �½�
	 * </pre>
	 */
	public List<WebScheduleAppInfo> selectPreSchedule();
}