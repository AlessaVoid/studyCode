package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbOrganRateParam;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * �������ֲ��������ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbOrganRateParamMapper extends GenericMapper<TbOrganRateParam, String>{

    /**
     * @Author: Liujinbo
     * @Description:  ���ֲ����б�
     * @Date: 2020/2/10

     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> selectByType();
}