package com.boco.TONY.biz.flownode.service;

import com.boco.TONY.biz.flownode.POJO.DTO.ProcessFlowNodeDTO;
import com.boco.TONY.biz.flownode.POJO.DTO.ProcessFlowNodeDTOV2;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;

/**
 * 流程节点维护业务层接口
 *
 * @author tony
 * @describe ProcessFlowNodeService
 * @date 2019-09-25
 */
public interface IWebFlowNodeService {

    /**
     * 获取所有的流程节点
     */
    ListResult<ProcessFlowNodeDTOV2> getAllProcessFlowNodes();

    /**
     * 更新流程节点数量
     */
    PlainResult<String> updateProcessFlowInfo(String fnCode, int fnCount, String operName);

    /**
     * 获取流程节点FnCode
     */
    PlainResult<ProcessFlowNodeDTO> getSequenceNodeByFnCode(String fnCode);

    /**
     * 获取流程节点通过FnCode
     */
    Integer getProcessFlowNodeCountByFnCode(String fnCode);

    /**
     * 计划月流程节点
     */
    Integer getProcessFlowNodeCountByMonth(int req);

    /**
     * 通过类型获取节点
     */
    Integer getProcessFlowNodeCountByFnKind(String type);

}
