package com.boco.PM.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.WebDeptInfo;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebDeptInfoҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebDeptInfoService extends IGenericService<WebDeptInfo,HashMap<String,Object>>{
	List<WebDeptInfo> select(WebDeptInfo webDeptInfo);
	Json insert(WebDeptInfo webDeptInfo) throws Exception;
	//�������ű���
	int max(WebDeptInfo webDeptInfo);
	Json update(WebDeptInfo webDeptInfo) throws Exception;
	Json delete(WebDeptInfo webDeptInfo) throws Exception;
	List<Map<String, String>> selectCode(WebDeptInfo webDeptInfo);
	List<Map<String, String>> selectName(WebDeptInfo webDeptInfo);
	List<Map<String, String>> selectUpCode(WebDeptInfo webDeptInfo);
	List<WebDeptInfo> selectUpDeptName(WebDeptInfo webDeptInfo);

}