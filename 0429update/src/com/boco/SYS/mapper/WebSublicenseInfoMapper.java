package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.WebSublicenseInfo;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * WebSublicenseInfo���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebSublicenseInfoMapper extends GenericMapper<WebSublicenseInfo,java.lang.String>{
	/**
	 * 
	 *
	 * TODO ��ȡת����ɫ�ļ�¼��(ת��Ĺ�Ա���ǵ�ǰת���Ա).
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��2��    	  ����    �½�
	 * </pre>
	 */
	public Integer countOutRole(WebSublicenseInfo webSublicenseInfo);
	/**
	 * 
	 *
	 * TODO ����ת����Ա��ת���Ա����ɫ���롢δ�ջؼ�¼�޸ļ�¼��Ϣ.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��2��    	  ����    �½�
	 * </pre>
	 */
	public Integer updateByAttr(WebSublicenseInfo webSublicenseInfo);
	/**
	 * 
	 *
	 * TODO ���ɫ���ܱ������ѯ��Ȩ�û������б�����.
	 *
	 * @param query
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��7��    	  ����    �½�
	 * </pre>
	 */
	public List<Map<String,String>> getPowerList(Map<String,String> query) throws Exception;
	/**
	 * 
	 *
	 * TODO �������̣����ɫ���ܱ������ѯ��Ȩ�����б�����.
	 *
	 * @param query
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��5��    	  ����    �½�
	 * </pre>
	 */
	public List<Map<String,String>> getPowerListByRole(Map<String, Object> query) throws Exception;
	
	/**
	 * 
	 *
	 * TODO �ջظù�Աת����ָ����ɫ.
	 *
	 * @param webSublicenseInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��11��    	  ����    �½�
	 * </pre>
	 */
	public Integer backRoleAll(WebSublicenseInfo webSublicenseInfo);
}