package com.boco.PUB.service.tbCalculateOne;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbCalculateOneResult;

/**
 * 
 * 
 * ���� �����ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbCalculateOneResultService extends IGenericService<TbCalculateOneResult,String>{

    /**
     * @Author: Liujinbo
     * @Description:  ���ɲ����� ģʽһ
     * @Date: 2020/2/27

     * @return: void
     **/
    void addCalculateOneResult() throws Exception;
}