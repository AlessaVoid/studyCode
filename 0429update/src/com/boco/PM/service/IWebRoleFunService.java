package com.boco.PM.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.entity.WebRoleFun;
import com.boco.SYS.entity.WebShortMenuInfo;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * ��ɫ���ܶ��ձ�ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebRoleFunService extends IGenericService<WebRoleFun,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO ���ý���Ȩ��.
	 *
	 * @param roleCode
	 * @param funCodes
	 * @param updateUser
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��7��    	    ����    �½�
	 * </pre>
	 */
	public void auth(String roleCode, String funCodes,String opercode) throws RuntimeException;
	/**
	 * 
	 *
	 * TODO ���ݹ�Ա��ɫ��ѯ��Աӵ�еĹ���(��̬����where�����е�or).
	 *
	 * @param roleCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	public List<WebRoleFun> selectByRoleList(List<String> roleCode);
}