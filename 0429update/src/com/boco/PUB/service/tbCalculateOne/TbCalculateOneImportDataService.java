package com.boco.PUB.service.tbCalculateOne;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbCalculateOneImportData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 
 * 
 * TbCalculateOneImportData业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbCalculateOneImportDataService extends IGenericService<TbCalculateOneImportData, String>{
    /**
     * @Description 导入测算数据
     * @Author liujinbo
     * @Date 2020/3/10
     * @param file
     * @param operCode
     * @param request
     * @Return java.util.Map<java.lang.String,java.lang.String>
     */
    Map<String, String> enterDataByMonth(MultipartFile file, String operCode, HttpServletRequest request) throws Exception;

}