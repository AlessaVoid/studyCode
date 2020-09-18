package com.boco.PM.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.boco.PM.service.IWebRoleFunService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.WebRoleFun;
import com.boco.SYS.entity.WebShortMenuInfo;
import com.boco.SYS.mapper.WebRoleFunMapper;

/**
 * 
 * 
 * ��ɫ���ܶ��ձ�ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class WebRoleFunService extends GenericService<WebRoleFun,HashMap<String,Object>> implements IWebRoleFunService{
	//���Զ��巽��ʱʹ��
	@Autowired
	private WebRoleFunMapper mapper;
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
	public void auth(String roleCode, String funCodes,String opercode) throws RuntimeException{
		if("��ѡ��".equals(funCodes)){
			throw new RuntimeException("δѡ����");
		}
		//ɾ��ԭ��¼
		mapper.deleteFunsByRole(roleCode);
		//�����¼�¼
//		insertBatchRoleFun(roleCode, funCodes, opercode);
	}
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
	public List<WebRoleFun> selectByRoleList(List<String> roleCode){
		return mapper.selectByRoleList(roleCode);
	}
}