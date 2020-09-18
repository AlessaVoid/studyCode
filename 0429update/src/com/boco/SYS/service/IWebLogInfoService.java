package com.boco.SYS.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebDeptInfo;
import com.boco.SYS.entity.WebLogInfo;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.util.TreeNode;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * ҵ����־��ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebLogInfoService extends IGenericService<WebLogInfo,java.lang.String>{
	/**
	 * 
	 *
	 * TODO ��̬���ݲ��ű�Ų�ѯϵͳ��־��Ϣ.
	 *
	 * @param roleCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	  ����    �½�
	 * </pre>
	 */

	/**
	 * 
	 *
	 * TODO ��װ��ѯ�����������쵼��.
	 * ѡ��һ�����ţ����Բ�ѯ��������ż��¼��µĲ���Ա��ϵͳ��־��Ϣ
	 *
	 * @param webLogInfo
	 * @param webOperInfoDTO
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��9��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public HashMap getSelectMap(WebLogInfo webLogInfo,WebOperInfo webOperInfoDTO) throws Exception;
	/**
	 * 
	 *
	 * TODO У�����Ա����.
	 *
	 * @param webLogInfo
	 * @param fdOper
	 * @param operDept
	 * @param upOperDept
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��9��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkOperDept(WebLogInfo webLogInfo,FdOper fdOper,String operDept,String upOperDept) throws Exception;
	/**
	 * 
	 *
	 * TODO У�����Ա����.
	 *
	 * @param webLogInfo
	 * @param fdOper
	 * @param selectDeptCode
	 * @param selectUpDeptCode
	 * @param operDept
	 * @param upOperDept
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��9��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkOperCode(WebLogInfo webLogInfo,FdOper fdOper,String operDept,String upOperDept) throws Exception;
	/**
	 * 
	 *
	 * TODO ��ѯ�Ƿ��ǵ�ǰ���ŵ��¼�����.
	 *
	 * @param deptInfo
	 * @param deptCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkDept(WebDeptInfo deptInfo,String deptCode) throws Exception;
	/**
	 * 
	 *
	 * TODO ϵͳ��־��ѯ��ȡ����������Ϣ.
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	  ����    �½�
	 * </pre>
	 */
	public List<TreeNode> getWebDeptInfo() throws Exception;
}