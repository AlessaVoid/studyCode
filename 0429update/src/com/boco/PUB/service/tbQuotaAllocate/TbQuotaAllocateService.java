package com.boco.PUB.service.tbQuotaAllocate;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPlanadjDetail;
import com.boco.SYS.entity.TbQuotaAllocate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * ��ȷ����ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbQuotaAllocateService extends IGenericService<TbQuotaAllocate, String>{
    /**
     * @Description �ƻ���������¶�ȱ�
     * @Author liujinbo
     * @Date 2020/3/26
     * @param tbPlanadjDetailList
     * @param planType �ƻ�����
     * @param planadjId
     * @Return void
     */
    void updatePlanTbQuota(List<TbPlanadjDetail> tbPlanadjDetailList, int planType, String planadjId) throws Exception;


    /**
     * @Description ��ѯ����б�ҳ
     * @Author liujinbo
     * @Date 2020/3/29
     * @param pageNo
     * @param pageSize
     * @param request
     * @param tbQuotaAllocate
     * @Return void
     * @return
     */
    Map<String, Object> getQuotaAllocate(int pageNo, int pageSize, HttpServletRequest request, TbQuotaAllocate tbQuotaAllocate) throws Exception;
}