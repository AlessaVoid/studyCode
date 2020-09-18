package com.boco.RE.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPsbcRmbLoanDay;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * ����Ҵ����ձ���ҵ������ʵ���ࣨ�ʴ��ܷ��У�
 * @Author zhuhongjiang
 * @Date 2020/1/8 ����3:06
 **/
public interface ITbPsbcRmbLoanDayService extends IGenericService<TbPsbcRmbLoanDay, String>{

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
    Map<String, Object> upload(List<Map<String, Object>> loanTypeList, String statisticsDayParam, String type, MultipartFile file, String projectPath, String operCode, Map<String, Object> resultMap);

    /**
     * У��ͳ������
     * @param statisticsDay
     * @param resultMap
     * @return
     */
    Map<String, Object> checkStatisticsDay(String type, String statisticsDay, Map<String, Object> resultMap);
}