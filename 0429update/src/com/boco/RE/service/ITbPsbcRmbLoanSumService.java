package com.boco.RE.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPsbcRmbLoanSum;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 人民币存贷款日报汇总表业务服务层接口（邮储分产品）
 * @Author zhuhongjiang
 * @Date 2020/1/8 下午2:58
 **/
public interface ITbPsbcRmbLoanSumService extends IGenericService<TbPsbcRmbLoanSum, String>{

    /**
     * 上传
     * @param file
     * @param resultMap
     * @return
     */
    Map<String, Object> upload(List<Map<String, Object>> loanTypeList, String statisticsDayParam, MultipartFile file, String projectPath, String operCode, Map<String, Object> resultMap);

    /**
     * 校验统计日期
     * @param statisticsDay
     * @param resultMap
     * @return
     */
    Map<String, Object> checkStatisticsDay(String statisticsDay, Map<String, Object> resultMap);
}