package com.boco.RE.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPsbcRmbLoanDay;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 人民币贷款日报表业务服务层实现类（邮储总分行）
 * @Author zhuhongjiang
 * @Date 2020/1/8 下午3:06
 **/
public interface ITbPsbcRmbLoanDayService extends IGenericService<TbPsbcRmbLoanDay, String>{

    /**
     * 分页查询
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectListByPage(Map<String, Object> paramMap);

    /**
     * 上传
     * @param file
     * @param resultMap
     * @return
     */
    Map<String, Object> upload(List<Map<String, Object>> loanTypeList, String statisticsDayParam, String type, MultipartFile file, String projectPath, String operCode, Map<String, Object> resultMap);

    /**
     * 校验统计日期
     * @param statisticsDay
     * @param resultMap
     * @return
     */
    Map<String, Object> checkStatisticsDay(String type, String statisticsDay, Map<String, Object> resultMap);
}