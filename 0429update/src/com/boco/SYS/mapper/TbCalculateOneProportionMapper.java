package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbCalculateOneProportion;
import org.springframework.dao.DataAccessException;

/**
 * ���� Ȩ�ر����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbCalculateOneProportionMapper extends GenericMapper<TbCalculateOneProportion, String> {

    void deleteAll() throws DataAccessException;

}