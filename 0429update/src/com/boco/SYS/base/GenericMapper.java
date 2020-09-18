package com.boco.SYS.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.FdOrgan;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

/**
 * 
 * 
 *  �ṩ����������ɾ���ġ���ӿ�.
 *  T �����ʵ������
 *  K ʵ�������
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2015��11��2��    	    ����    �½�
 * </pre>
 */
public interface GenericMapper <T,PK extends Serializable>{
	/**
	 * 
	 *
	 * TODO ����һ��ʵ��.
	 *
	 * @param t
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��2��    	    ����    �½�
	 * </pre>
	 */
	public int insertEntity(T t) throws DataAccessException;
	
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
	public int insertBatch(List<T> list) throws DataAccessException;
	
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
	public int deleteByPK(PK pk) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO ������������ɾ��.
	 *
	 * @param list
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��30��    	    ����    �½�
	 * </pre>
	 */
	public int deleteBatchByPKList(List<PK> list) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO �Զ���where����ɾ��������Ϊwhere��������ַ�������"id='1' and name='a'".
	 *
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��4��    	    ����    �½�
	 * </pre>
	 */
	public int deleteByWhere(@Param(value = "whereStr") String whereStr) throws DataAccessException;
	
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
	public int updateByPK(T t) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO �����޸�,����map:����=��ֵ��keyList={1,2,3...}.
	 *
	 * @param map
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��30��    	    ����    �½�
	 * </pre>
	 */
	public int updateBatch(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO �Զ���where�����޸ģ�����Ϊwhere��������ַ�������"id='1' and name='a'".
	 *
	 * @param whereStr
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��4��    	    ����    �½�
	 * </pre>
	 */
	public int updateByWhere(@Param(value = "whereStr") String whereStr) throws DataAccessException;
	
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
	public T selectByPK(PK pk) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO ���ݷ�����ΨһԼ����ѯ��¼,û��ΨһԼ��ʱ��ѯ���м�¼.
	 *
	 * @param k
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��5��    	    ����    �½�
	 * </pre>
	 */
	public List<T> selectByUQ(T t) throws DataAccessException;
	
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
	public List<T> selectByAttr(T t) throws DataAccessException;

	//���ݻ���ID��ѯ��������
	String selectOrganNameZX (String thiscode);

	public List<T> selectByType(T t) throws DataAccessException;

	public List<T> selectDistinctTbwarn(T t) throws DataAccessException;

	public List<T> select(T t) throws DataAccessException;

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
	public List<T> selectByLikeAttr(T t) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO �Զ���where������ѯ������Ϊwhere��������ַ�������"id='1' and name='a'".
	 *
	 * @param whereStr
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��5��    	    ����    �½�
	 * </pre>
	 */
	public List<T> selectByWhere(@Param(value = "whereStr") String whereStr) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO ����������ѯ��¼��.
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��5��    	    ����    �½�
	 * </pre>
	 */
	public int countByAttr(T t) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO ����ģ��������ѯ��¼��.
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��5��    	    ����    �½�
	 * </pre>
	 */
	public int countByLikeAttr(T t) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO �Զ���where������ѯ��¼��������Ϊwhere��������ַ�������"id='1' and name='a'".
	 *
	 * @param whereStr
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��7��    	    ����    �½�
	 * </pre>
	 */
	public int countByWhere(@Param(value = "whereStr") String whereStr) throws DataAccessException;

    List<FdOrgan> selectByLevel(Map<String, Object> map);


	T selectByPKOper(PK pk);
}
