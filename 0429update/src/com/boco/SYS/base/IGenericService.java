package com.boco.SYS.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * 
 * 
 *  �����˻�������ɾ�Ĳ����.
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2015��11��3��    	    ����    �½�
 * </pre>
 */
public interface IGenericService<T, PK extends Serializable>{
	/**
	 * 
	 *
	 * TODO ����һ��ʵ��.
	 *
	 * @param t
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��2��    	    ����    �½�
	 * </pre>
	 */
	public int insertEntity(T t) throws Exception;
	
	/**
	 * 
	 *
	 * TODO ��������.
	 *
	 * @param list
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��2��    	    ����    �½�
	 * </pre>
	 */
	public int insertBatch(List<T> list) throws Exception;
	
	/**
	 * 
	 *
	 * TODO ��������ɾһ����¼.
	 *
	 * @param k
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��2��    	    ����    �½�
	 * </pre>
	 */
	public int deleteByPK(PK pk) throws Exception;
	
	/**
	 * 
	 *
	 * TODO ������������ɾ��.
	 *
	 * @param list
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��30��    	    ����    �½�
	 * </pre>
	 */
	public int deleteBatchByPKList(List<PK> list) throws Exception;
	
	/**
	 * 
	 *
	 * TODO �Զ���where����ɾ��������Ϊwhere��������ַ�������"id='1' and name='a'".
	 *
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��4��    	    ����    �½�
	 * </pre>
	 */
	public int deleteByWhere(String whereStr) throws Exception;
	
	/**
	 * 
	 *
	 * TODO ���������޸�һ����¼.
	 *
	 * @param k
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��2��    	    ����    �½�
	 * </pre>
	 */
	public int updateByPK(T t) throws Exception;
	
	/**
	 * 
	 *
	 * TODO �����޸�,����map:����=��ֵ��idList={1,2,3...}.
	 *
	 * @param map
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��30��    	    ����    �½�
	 * </pre>
	 */
	public int updateBatch(Map<String, Object> map) throws Exception;
	
	/**
	 * 
	 *
	 * TODO �Զ���where�����޸ģ�����Ϊwhere��������ַ�������"id='1' and name='a'".
	 *
	 * @param whereStr
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��4��    	    ����    �½�
	 * </pre>
	 */
	public int updateByWhere(String whereStr) throws Exception;
	
	/**
	 * 
	 *
	 * TODO ����������ѯһ����¼.
	 *
	 * @param k
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��2��    	    ����    �½�
	 * </pre>
	 */
	public T selectByPK(PK pk) throws Exception;
	public T selectByPKOper(PK pk) throws Exception;
	/**
	 * 
	 *
	 * TODO ���ݷ�����ΨһԼ����ѯ��¼,û��ΨһԼ��ʱ��ѯ���м�¼.
	 *
	 * @param k
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��5��    	    ����    �½�
	 * </pre>
	 */
	public List<T> selectByUQ(T t) throws Exception;
	
	/**
	 * 
	 *
	 * TODO ����������ѯ��¼.
	 *
	 * @param t
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��2��    	    ����    �½�
	 * </pre>
	 */
	public List<T> selectByAttr(T t) throws Exception;
	String selectOrganNameZX(String thiscode);
	public List<T> selectab(T t) throws Exception;
	public List<T> selectDistinctTbwarn(T t) throws Exception;
	/**
	 * 
	 *
	 * TODO ����ģ��������ѯ��¼.
	 *
	 * @param t
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��2��    	    ����    �½�
	 * </pre>
	 */
	public List<T> selectByLikeAttr(T t) throws Exception;
	
	/**
	 * 
	 *
	 * TODO �Զ���where������ѯ������Ϊwhere��������ַ�������"id='1' and name='a'".
	 *
	 * @param whereStr
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��5��    	    ����    �½�
	 * </pre>
	 */
	public List<T> selectByWhere(String whereStr) throws Exception;
	
	/**
	 * 
	 *
	 * TODO ����������ѯ��¼��.
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��5��    	    ����    �½�
	 * </pre>
	 */
	public int countByAttr(T t) throws Exception;
	
	/**
	 * 
	 *
	 * TODO ����ģ��������ѯ��¼��.
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��5��    	    ����    �½�
	 * </pre>
	 */
	public int countByLikeAttr(T t) throws Exception;
	
	/**
	 * 
	 *
	 * TODO ����Where����ѯ��¼��.
	 *
	 * @param whereStr
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��7��    	    ����    �½�
	 * </pre>
	 */
	public int countByWhere(String whereStr) throws Exception;
	
	/**
	 * 
	 *
	 * TODO ���ݴ������ѯ������Ϣ.
	 *
	 * @param errorCode ������
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��11��    	     ����    �½�
	 * </pre>
	 */
	public String getErrorInfo(String errorCode) throws Exception;
}
