package com.boco.SYS.mapper;

import com.boco.TONY.biz.flownode.POJO.DO.ProcessFlowNodeDO;
import com.boco.TONY.biz.flownode.exception.ProcessFlowNodeException;

import java.util.List;

/**
 * �����ڵ�ά����
 *
 * @author tony
 * @describe SequenceFlowNodeMapper
 * @date 2019-09-25
 */
public interface FnFlowNodeMapper {

    /**
     * ��ȡ�������̽ڵ����ò���
     */
    List<ProcessFlowNodeDO> getAllSequenceNode() throws ProcessFlowNodeException;

    /**
     * ͨ��FnCode��ȡ���̽ڵ�
     */
    Integer getProcessFlowNodeCountByFnCode(String fnCode);

    /**
     * processId�������̽ڵ����
     */
    void updateFlowNodeByFnCode(ProcessFlowNodeDO processFlowNodeDO) throws ProcessFlowNodeException;

    /**
     * ͨ�����̽ڵ�Code��ȡ���̽ڵ�����
     */
    ProcessFlowNodeDO getSequenceNodeByFnCode(String fnCode);


}
