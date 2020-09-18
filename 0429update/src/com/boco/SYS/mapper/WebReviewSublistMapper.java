package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.boco.SYS.entity.WebReviewSublist;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * WebReviewSublist���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebReviewSublistMapper extends GenericMapper<WebReviewSublist,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO ���ݸ��˱��ɾ����¼.
	 *
	 * @param opercode
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��26��    	  ����    �½�
	 * </pre>
	 */
	public void deleteByAppNo(String appNo) throws RuntimeException;
	/**
	 * 
	 *
	 * TODO ����orderNo���������ѯ
	 *
	 * @param appNo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��12��18��    	   ������  �½�
	 * </pre>
	 */
	public List<WebReviewSublist> selectAppNo(@Param(value="appNo")String appNo) throws DataAccessException;
}