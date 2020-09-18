package com.boco.SYS.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebMenuInfo;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * �˵���ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebMenuInfoService extends IGenericService<WebMenuInfo,java.lang.String>{
	public WebMenuInfo selectByPKInfo(WebMenuInfo webMenuInfo) throws DataAccessException;
	/**
	 * 
	 *
	 * TODO ��ѯĳ����ɫ�Ĺ��ܼ���.
	 *
	 * @param opercode
	 * @param menuType
	 * @param parentId
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��2��    	    ����    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectRoleFuns(String opercode, String menuType, String menuStatus) throws RuntimeException;
	
	/**
	 * 
	 *
	 * TODO ��ѯĳ���û��Ĳ˵���.
	 *
	 * @param opercode
	 * @param parentId
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��2��    	    ����    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectRolesMenus(String opercode) throws RuntimeException;
	
	/**
	 * 
	 *
	 * TODO ��ѯĳ���û���ĳ���˵��µİ�ť��.
	 *
	 * @param opercode
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��2��    	    ����    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectRolesBtns(String opercode, String parentId) throws RuntimeException;
	/**
	 * 
	 *
	 * TODO �����˵�.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��22��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public Json insertWebMenuInfo(WebMenuInfo webMenuInfo,FdOper fdOper) throws Exception;
	/**
	 * 
	 *
	 * TODO �޸Ĳ˵�.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��22��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public Json updateWebMenuInfo(WebMenuInfo webMenuInfo, FdOper fdOper) throws Exception;
	/**
	 * 
	 *
	 * TODO ɾ���˵�.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��22��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public Json deleteWebMenuInfo(WebMenuInfo webMenuInfo) throws Exception;
	/**
	 * 
	 *
	 * TODO ��ѯ�˵���Ŷ�Ӧ�������¼��˵���Ϣ��������ť��Ϣ.
	 *
	 * @param upMenuNo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��3��    	  ����    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectByNo(Map<String,Object> map);
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
	public List<WebMenuInfo> selectMenuByRole(List<String> roleCode);
	/**
	 * 
	 *
	 * TODO ����ɫ�ַ�ֱ�Ӵ���sql�н��в�ѯ.
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	  ����    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectMenuByAttr(String roleCodes) throws DataAccessException;
	/**
	 * 
	 *
	 * TODO ����ɫ�ַ�ֱ�Ӵ���sql�н��в�ѯ.
	 *
	 * @param map
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	  ����    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectBtnByAttr(String roleCodes, String parentId) throws DataAccessException;
	/**
	 * 
	 *
	 * TODO ��ȡ��ݲ˵�.
	 *
	 * @param roleCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectShortMenus(String opercode);
	
	
	/**
	 * 
	 *
	 * TODO ���ݹ�Ա��ɫ��ѯ��Ա���Բ鿴�ı���.
	 *
	 * @param roleCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��10��    	  ������    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectReportMenuByRole(List<String> roleCode);
}