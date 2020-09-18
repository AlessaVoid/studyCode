package com.boco.RE.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbBankRmbBusi;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 商业银行人民币业务统计表业务服务层接口（银行业分机构）
 * @Author zhuhongjiang
 * @Date 2020/1/8 下午2:53
 **/
public interface ITbBankRmbBusiService extends IGenericService<TbBankRmbBusi, String>{

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