package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebDeptInfo;
/**
 * 
 * 
 * WebDeptInfo���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebDeptInfoMapper extends GenericMapper<WebDeptInfo,HashMap<String,Object>>{

	List<WebDeptInfo> select(WebDeptInfo webDeptInfo);

	int max(WebDeptInfo webDeptInfo);

	List<Map<String,Object>> count(WebDeptInfo dept);

	List<Map<String, String>> selectCode(WebDeptInfo webDeptInfo);

	List<Map<String, String>> selectName(WebDeptInfo webDeptInfo);

	List<Map<String, String>> selectUpCode(WebDeptInfo webDeptInfo);

	List<WebDeptInfo> selectUpDeptName(WebDeptInfo webDeptInfo);

}