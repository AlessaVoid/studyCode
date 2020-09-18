package com.boco.PUB.service.tbCalculateOne;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbCalculateOneProportion;
import org.springframework.dao.DataAccessException;

/**
 * ���� Ȩ�ر�ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface ITbCalculateOneProportionService extends IGenericService<TbCalculateOneProportion, String> {

    void deleteAll() throws DataAccessException;

}