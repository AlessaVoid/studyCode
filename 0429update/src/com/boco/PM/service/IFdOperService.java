package com.boco.PM.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;

/**
 *
 *
 * FdOperҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��).
 *
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2016��2��24��    		����    �½�
 * </pre>
 */
public interface IFdOperService extends IGenericService<FdOper,HashMap<String,Object>>{
	/**
	 *
	 *
	 * TODO ��Աǩ��.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��24��    	  ����    �½�
	 * </pre>
	 */
	public boolean operSignIn(FdOper fdOper);
	/**
	 *
	 *
	 * TODO ��Աǩ��.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��24��    	  ����    �½�
	 * </pre>
	 */
	public boolean operSignOut(FdOper fdOper);
	/**
	 *
	 *
	 * TODO ��Աǿ��ǩ��.
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��24��    	  ����    �½�
	 * </pre>
	 */
	public Map operQzSignout(FdOper fdOper, String doOperCode);
	/**
	 *
	 *
	 * TODO ��Ա�����޸�
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��26��    	   ������  �½�
	 * </pre>
	 */
	public Map operUpdatePwd(FdOper fdOper, String pwd);

	/**
	 * ��Ա�����޸�
	 *
	 */
	Map operUpdateOrgan(FdOper fdOper, String newOrganCode);

	/**
	 * ��Աת��
	 * @param fdOper
	 * @return
	 */
	Map insertNewOper(FdOper fdOper);

	/**
	 *
	 *
	 * TODO ��Ա��������
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��26��    	   ������  �½�
	 * </pre>
	 */
	public Map operRePwd(FdOper sessionUser, FdOper fdOper);
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
	public List<Map<String,String>> getPowerList(Map<String, String> query) throws Exception;
	/**
	 *
	 *
	 *  * TODO �������������ѯ����Ա��Ϣ���������ڲ���Ա�����������json��.
	 *
	 * @param request
	 *            ��ѯ����ʽΪ funno=**** ���磺funno=FD-01
	 * @return
	 *         json����ʽΪ{"list":[{"value":"����ԱA�ı��","key":"����ԱA"},{"value":"����ԱB�ı��"
	 *         ,"key":"����ԱB"}]}
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��14��    	  ����    �½�
	 * </pre>
	 */
	public Map<String,List<Map<String, String>>> getAppUserList(String funCode, HttpSession session) throws Exception;
	/**
	 * ��ȡ���и�����Ա
	 * @param funCode
	 * @param session
	 * @return
	 */
	public Map<String, List<Map<String, String>>> getHeadOfficeAppUserList(String funCode, HttpSession session) throws Exception;
	/**
	 *
	 *
	 * TODO ������Ա��Ų�ѯ��Ա��Ϣ��������Ϣ����ɫ��Ϣ.
	 *
	 * @param userNo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��14��    	  ����    �½�
	 * </pre>
	 */
	public Map getUserInfo(String userNo) throws Exception;
	/**
	 *
	 *
	 * TODO �������̣� ���ɫ���ܱ������ѯ��Ȩ�����б�����.
	 *
	 * @param funCode
	 * @param session
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��5��    	  ����    �½�
	 * </pre>
	 */
	public Map<String,List<Map<String, String>>> getAppUserListByRole(String funCode, String roleCode, String organcode) throws Exception;
}