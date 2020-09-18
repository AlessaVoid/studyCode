package com.boco.PUB.service.tbQuotaAllocate;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPlanadjDetail;
import com.boco.SYS.entity.TbQuotaAllocate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * 额度分配表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbQuotaAllocateService extends IGenericService<TbQuotaAllocate, String>{
    /**
     * @Description 计划调整后更新额度表
     * @Author liujinbo
     * @Date 2020/3/26
     * @param tbPlanadjDetailList
     * @param planType 计划类型
     * @param planadjId
     * @Return void
     */
    void updatePlanTbQuota(List<TbPlanadjDetail> tbPlanadjDetailList, int planType, String planadjId) throws Exception;


    /**
     * @Description 查询额度列表页
     * @Author liujinbo
     * @Date 2020/3/29
     * @param pageNo
     * @param pageSize
     * @param request
     * @param tbQuotaAllocate
     * @Return void
     * @return
     */
    Map<String, Object> getQuotaAllocate(int pageNo, int pageSize, HttpServletRequest request, TbQuotaAllocate tbQuotaAllocate) throws Exception;
}