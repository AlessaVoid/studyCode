package com.boco.SYS.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebPublicPromptTable;
/**
 * 
 * 
 * ������ʾ��||������ʾ�����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebPublicPromptTableMapper extends GenericMapper<WebPublicPromptTable,java.lang.String>{
	/**
	 * 
	 *
	 * TODO ��ѯ������Ϣ
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��11��11��    	   ������  �½�
	 * </pre>
	 */
	public List<WebPublicPromptTable> selectPromptMsg() throws DataAccessException;
}