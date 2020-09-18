package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * WebOperInfo���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebOperInfoMapper extends GenericMapper<WebOperInfo,HashMap<String,Object>>{

	List<WebOperInfo> select(WebOperInfo webOperInfo);

	List<Map<String, String>> selectDeptCode(WebOperInfo webOperInfo);

	List<Map<String, String>> selectOperCodeLike(WebOperInfo webOperInfo);

	List<Map<String, String>> selectOperName(WebOperInfo webOperInfo);
	/**
	 * 
	 *
	 * TODO ���ݲ���Ա�Ų�ѯ
	 *
	 * @param webOperInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��9��22��    	   ������  �½�
	 * </pre>
	 */
	public WebOperInfo selectOperCode(WebOperInfo webOperInfo);

	public String selectOperCodeByName(@Param(value="operName")String operName);
}