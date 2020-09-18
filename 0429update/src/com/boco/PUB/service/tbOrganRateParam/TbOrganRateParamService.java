package com.boco.PUB.service.tbOrganRateParam;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbOrganRateParam;
import com.boco.TONY.common.PlainResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * 机构评分参数表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbOrganRateParamService extends IGenericService<TbOrganRateParam, String>{
    void add();

    /**
     * @Author: Liujinbo
     * @Description: 评分参数列表
     * @Date: 2020/2/10

     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> selectByType();

    /**
     * @Author: Liujinbo
     * @Description:   更新评分参数
     * @Date: 2020/2/10
     * @param request :
     * @param operCode :
     * @return: com.boco.TONY.common.PlainResult<java.lang.String>
     **/
    PlainResult<String> updateOrganRateParam(HttpServletRequest request, String operCode);
}