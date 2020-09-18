package com.boco.RE.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPsbcRmbBusi;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 银行业人民币主要业务分地区统计表业务服务层接口（银行业分地区）
 * @Author zhuhongjiang
 * @Date 2020/1/8 下午3:03
 **/
public interface ITbPsbcRmbBusiService extends IGenericService<TbPsbcRmbBusi, String>{

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
    Map<String, Object> upload(List<Map<String, Object>> loanTypeList, String statisticsDayParam, MultipartFile file, String projectPath, String operCode, Map<String, Object> resultMap);

    /**
     * 校验统计日期
     * @param statisticsDay
     * @param resultMap
     * @return
     */
    Map<String, Object> checkStatisticsDay(String statisticsDay, Map<String, Object> resultMap);
}