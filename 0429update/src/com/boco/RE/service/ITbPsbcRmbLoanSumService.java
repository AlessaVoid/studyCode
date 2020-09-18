package com.boco.RE.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPsbcRmbLoanSum;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * ����Ҵ�����ձ����ܱ�ҵ������ӿڣ��ʴ��ֲ�Ʒ��
 * @Author zhuhongjiang
 * @Date 2020/1/8 ����2:58
 **/
public interface ITbPsbcRmbLoanSumService extends IGenericService<TbPsbcRmbLoanSum, String>{

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