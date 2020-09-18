package com.boco.TONY.biz.flownode.service;

import com.boco.TONY.biz.flownode.POJO.DTO.ProcessFlowNodeDTO;
import com.boco.TONY.biz.flownode.POJO.DTO.ProcessFlowNodeDTOV2;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;

/**
 * ���̽ڵ�ά��ҵ���ӿ�
 *
 * @author tony
 * @describe ProcessFlowNodeService
 * @date 2019-09-25
 */
public interface IWebFlowNodeService {

    /**
     * ��ȡ���е����̽ڵ�
     */
    ListResult<ProcessFlowNodeDTOV2> getAllProcessFlowNodes();

    /**
     * �������̽ڵ�����
     */
    PlainResult<String> updateProcessFlowInfo(String fnCode, int fnCount, String operName);

    /**
     * ��ȡ���̽ڵ�FnCode
     */
    PlainResult<ProcessFlowNodeDTO> getSequenceNodeByFnCode(String fnCode);

    /**
     * ��ȡ���̽ڵ�ͨ��FnCode
     */
    Integer getProcessFlowNodeCountByFnCode(String fnCode);

    /**
     * �ƻ������̽ڵ�
     */
    Integer getProcessFlowNodeCountByMonth(int req);

    /**
     * ͨ�����ͻ�ȡ�ڵ�
     */
    Integer getProcessFlowNodeCountByFnKind(String type);

}
