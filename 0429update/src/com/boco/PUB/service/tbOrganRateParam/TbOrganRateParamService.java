package com.boco.PUB.service.tbOrganRateParam;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbOrganRateParam;
import com.boco.TONY.common.PlainResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * �������ֲ�����ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbOrganRateParamService extends IGenericService<TbOrganRateParam, String>{
    void add();

    /**
     * @Author: Liujinbo
     * @Description: ���ֲ����б�
     * @Date: 2020/2/10

     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> selectByType();

    /**
     * @Author: Liujinbo
     * @Description:   �������ֲ���
     * @Date: 2020/2/10
     * @param request :
     * @param operCode :
     * @return: com.boco.TONY.common.PlainResult<java.lang.String>
     **/
    PlainResult<String> updateOrganRateParam(HttpServletRequest request, String operCode);
}