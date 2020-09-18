package com.boco.SYS.mapper;

import com.boco.TONY.biz.flownode.POJO.DO.ProcessFlowNodeDO;
import com.boco.TONY.biz.flownode.exception.ProcessFlowNodeException;

import java.util.List;

/**
 * 审批节点维护表
 *
 * @author tony
 * @describe SequenceFlowNodeMapper
 * @date 2019-09-25
 */
public interface FnFlowNodeMapper {

    /**
     * 获取所有流程节点配置参数
     */
    List<ProcessFlowNodeDO> getAllSequenceNode() throws ProcessFlowNodeException;

    /**
     * 通过FnCode获取流程节点
     */
    Integer getProcessFlowNodeCountByFnCode(String fnCode);

    /**
     * processId更新流程节点参数
     */
    void updateFlowNodeByFnCode(ProcessFlowNodeDO processFlowNodeDO) throws ProcessFlowNodeException;

    /**
     * 通过流程节点Code获取流程节点配置
     */
    ProcessFlowNodeDO getSequenceNodeByFnCode(String fnCode);


}
