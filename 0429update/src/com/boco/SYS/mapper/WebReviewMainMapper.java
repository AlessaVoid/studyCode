package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.entity.WebReviewMain;
import com.boco.SYS.entity.WebRoleFun;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * WebReviewMain���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebReviewMainMapper extends GenericMapper<WebReviewMain,java.lang.String>{
	/**
	 * 
	 *
	 * TODO ��ȡ��Ӧ����.
	 *
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��26��    	  ����    �½�
	 * </pre>
	 */
	public Integer getPubSQ() throws RuntimeException;
	/**
	 * 
	 *
	 * TODO ���븴��֮ǰ�ж�����Ĳ����Ƿ��Ѵ���δ������ɵĸ��˼�¼.
	 *
	 * @param webReviewMain
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��26��    	  ����    �½�
	 * </pre>
	 */
	public List<Map<String,String>> checkRep(WebReviewMain webReviewMain) throws RuntimeException;
}