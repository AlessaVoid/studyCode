package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbPsbcRmbLoanDay;

import java.util.List;
import java.util.Map;
/**
 * 
 * 
 * ����Ҵ����ձ������ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbPsbcRmbLoanDayMapper extends GenericMapper<TbPsbcRmbLoanDay, String>{

    /**
     * ��ҳ��ѯ
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectListByPage(Map<String, Object> paramMap);
    /**
     * ����ͳ������У��Ψһ��
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectForCheckOnly(Map<String, Object> paramMap);
}