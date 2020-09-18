package com.boco.PUB.service.tbCalculateTwo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbCalculateTwoResult;
import com.boco.SYS.base.IGenericService;

import javax.servlet.http.HttpServletRequest;

/**
 * ����� �����ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface ITbCalculateTwoResultService extends IGenericService<TbCalculateTwoResult, String> {


    public void addCalculateOneResult();

    List<TbCalculateTwoResult> selectMonth();

    void deleleAndInsert(HttpServletRequest request) throws Exception;
}