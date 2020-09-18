package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebAreaOrganInfo;
/**
 * 
 * 
 * WebAreaOrganInfo���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebAreaOrganInfoMapper extends GenericMapper<WebAreaOrganInfo,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO ��ѯ����������ѡ��Ļ�����Ϣ.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	public List<WebAreaOrganInfo> selectNotEqualOrgan(String areaCode);
	/**
	 * 
	 *
	 * TODO ���ݵ�������ɾ����¼.
	 *
	 * @param areaCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	public Integer deleteByAreaCode(String areaCode);
}