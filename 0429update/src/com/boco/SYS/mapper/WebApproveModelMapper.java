package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebApproveModel;
/**
 * 
 * 
 * WebApproveModel���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebApproveModelMapper extends GenericMapper<WebApproveModel,java.lang.String>{
	/**
	 *  ��ѯ��������Ƿ��Ѵ���
	 *
	 * TODO ��������ķ���˵��.
	 *
	 * @param appCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��9��    	  ����    �½�
	 * </pre>
	 */
	public Integer countByExist(WebApproveModel webApproveModel);
}