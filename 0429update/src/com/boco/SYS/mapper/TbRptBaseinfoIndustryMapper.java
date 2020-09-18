package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbRptBaseinfoIndustry;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * �±���������ҵά�����ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbRptBaseinfoIndustryMapper extends GenericMapper<TbRptBaseinfoIndustry,HashMap<String,Object>>{
    /**
     * ��ѯ��ҵ
     * @return
     */
    List<Map<String, Object>> selectIndustry();

    /**
     * ��ҵ��-���е�¼��ѯһ������
     * @param param
     * @return
     */
    List<TbRptBaseinfoIndustry> selectLevelOneOrganData(HashMap<String, Object> param);

    /**
     * ��ҵ��-һ�ֵ�¼�鿴��������
     * @param param
     * @return
     */
    List<TbRptBaseinfoIndustry> selectLevelOhterOrganData(HashMap<String, Object> param);
}