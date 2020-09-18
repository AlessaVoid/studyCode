package com.boco.RE.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPsbcRmbBusi;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * ����ҵ�������Ҫҵ��ֵ���ͳ�Ʊ�ҵ������ӿڣ�����ҵ�ֵ�����
 * @Author zhuhongjiang
 * @Date 2020/1/8 ����3:03
 **/
public interface ITbPsbcRmbBusiService extends IGenericService<TbPsbcRmbBusi, String>{

    /**
     * ��ҳ��ѯ
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectListByPage(Map<String, Object> paramMap);

    /**
     * �ϴ�
     * @param file
     * @param resultMap
     * @return
     */
    Map<String, Object> upload(List<Map<String, Object>> loanTypeList, String statisticsDayParam, MultipartFile file, String projectPath, String operCode, Map<String, Object> resultMap);

    /**
     * У��ͳ������
     * @param statisticsDay
     * @param resultMap
     * @return
     */
    Map<String, Object> checkStatisticsDay(String statisticsDay, Map<String, Object> resultMap);
}