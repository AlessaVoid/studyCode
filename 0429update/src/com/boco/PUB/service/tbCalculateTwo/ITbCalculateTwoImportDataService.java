package com.boco.PUB.service.tbCalculateTwo;

import java.util.HashMap;
import java.util.Map;

import com.boco.SYS.entity.TbCalculateTwoImportData;
import com.boco.SYS.base.IGenericService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 
 * 测算二  历史数据导入表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface ITbCalculateTwoImportDataService extends IGenericService<TbCalculateTwoImportData, String>{
    Map<String, String> enterDataByMonth(MultipartFile file, String operCode, HttpServletRequest request);
}