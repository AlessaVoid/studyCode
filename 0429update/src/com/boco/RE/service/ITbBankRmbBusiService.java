package com.boco.RE.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbBankRmbBusi;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * ��ҵ���������ҵ��ͳ�Ʊ�ҵ������ӿڣ�����ҵ�ֻ�����
 * @Author zhuhongjiang
 * @Date 2020/1/8 ����2:53
 **/
public interface ITbBankRmbBusiService extends IGenericService<TbBankRmbBusi, String>{

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