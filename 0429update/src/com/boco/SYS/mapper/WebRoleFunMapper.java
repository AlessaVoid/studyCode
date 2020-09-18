package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebRoleFun;
import com.boco.SYS.entity.WebShortMenuInfo;
/**
 * 
 * 
 * ��ɫ���ܶ��ձ����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebRoleFunMapper extends GenericMapper<WebRoleFun,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO ��ѯĳ�������Ĺ���Ȩ�޼�.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��1��    	    ����    �½�
	 * </pre>
	 */
	public List<WebRoleFun> selectOperFuns(String opercode) throws DataAccessException;
	/**
	 * 
	 *
	 * TODO  ��ѯĳ�������Ĺ���Ȩ�޼�.
	 *
	 * @param opercode
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	  ����    �½�
	 * </pre>
	 */
	public List<WebRoleFun> selectOperRoleFun(@Param(value = "operdegrees")String operdegrees) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO ���ݽ�ɫ����ɾ�����ܼ�.
	 *
	 * @param roleCode
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��2��    	    ����    �½�
	 * </pre>
	 */
	public int deleteFunsByRole(String roleCode) throws DataAccessException;
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