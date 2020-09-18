package com.boco.SYS.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebMenuInfo;
import com.boco.SYS.entity.WebRoleFun;
/**
 * 
 * 
 * �˵������ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebMenuInfoMapper extends GenericMapper<WebMenuInfo,java.lang.String>{
	
	/**
	 * 
	 *
	 * TODO ��ѯ��Ա�˵�.
	 *
	 * @param map
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��2��    	    ����    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectRoleFuns(Map<String,Object> map) throws DataAccessException;
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
	public List<WebMenuInfo> selectMenuByAttr(Map<String,Object> map) throws DataAccessException;
	/**
	 * 
	 *
	 * TODO ��ѯ�˵���ϸ��Ϣ.
	 *
	 * @param map
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��2��    	    ����    �½�
	 * </pre>
	 */
	public WebMenuInfo selectByPKInfo(WebMenuInfo webMenuInfo) throws DataAccessException;
	/**
	 * 
	 *
	 * TODO �����ϼ��˵���Ų�ѯ�˵���ż�������.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��2��    	  ����    �½�
	 * </pre>
	 */
	public Map selectMenuInfo(String upMenuNo);
	/**
	 * 
	 *
	 * TODO ��ѯ�˵���ż�������.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��2��    	  ����    �½�
	 * </pre>
	 */
	public String selectMenuOrder();
	/**
	 * 
	 *
	 * TODO ��ѯ�˵���Ŷ�Ӧ�������¼��˵���Ϣ��������ť��Ϣ.
	 *
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
	 * TODO ���ݹ�Ա��ɫ��ѯ��Ա���Բ鿴�ı���
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