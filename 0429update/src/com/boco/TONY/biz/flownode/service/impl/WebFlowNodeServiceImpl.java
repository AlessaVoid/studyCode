package com.boco.TONY.biz.flownode.service.impl;

import com.boco.SYS.mapper.FnFlowNodeMapper;
import com.boco.TONY.biz.flownode.POJO.DO.ProcessFlowNodeDO;
import com.boco.TONY.biz.flownode.POJO.DTO.ProcessFlowNodeDTO;
import com.boco.TONY.biz.flownode.POJO.DTO.ProcessFlowNodeDTOV2;
import com.boco.TONY.biz.flownode.exception.ProcessFlowNodeException;
import com.boco.TONY.biz.flownode.service.IWebFlowNodeService;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.enums.FnNodeTypeEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ���̽ڵ�ά��ҵ���ӿ�ʵ��
 *
 * @author tony
 * @describe WebFlowNodeServiceImpl
 * @date 2019-09-25
 */
@Service
public class WebFlowNodeServiceImpl implements IWebFlowNodeService {
    @Autowired
    private FnFlowNodeMapper sequenceFlowNodeMapper;

    /**
     * ��ȡ���е����̽ڵ�
     *
     * @return �ڵ���Ϣ����
     */
    @Override
    public ListResult<ProcessFlowNodeDTOV2> getAllProcessFlowNodes() {
        ListResult<ProcessFlowNodeDTOV2> result = new ListResult<>();
        try {
            List<ProcessFlowNodeDO> processFlowNodeDOList = sequenceFlowNodeMapper.getAllSequenceNode();
            List<ProcessFlowNodeDTOV2> processFlowNodeDTOV2 = buildProcessFlowNodeDTOV2(processFlowNodeDOList);
            return result.success(processFlowNodeDTOV2, "load flow  node param success");
        } catch (ProcessFlowNodeException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "load  flow node param failed");
        }
    }

    /**
     * �������̽ڵ�DTO
     *
     * @param processFlowNodeDOList ���̽ڵ�DTO
     * @return �ڵ���Ϣ����
     */
    @SuppressWarnings("unused")
    private List<ProcessFlowNodeDTO> buildProcessFlowNodeDTO(List<ProcessFlowNodeDO> processFlowNodeDOList) {
        List<ProcessFlowNodeDTO> processFlowNodeDTOList = new ArrayList<>();
        for (ProcessFlowNodeDO processFlowNodeDO : processFlowNodeDOList) {
            ProcessFlowNodeDTO processFlowNodeDTO =
                    new ProcessFlowNodeDTO()
                            .setFnId(processFlowNodeDO.getFnId())
                            .setFnName(processFlowNodeDO.getFnName())
                            .setFnCode(processFlowNodeDO.getFnCode())
                            .setFnKind(processFlowNodeDO.getFnKind())
                            .setFnCount(processFlowNodeDO.getFnCount())
                            .setFnCreateOper(processFlowNodeDO.getFnCreateOper())
                            .setFnCreateTime(processFlowNodeDO.getFnCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .setFnUpdateOper(processFlowNodeDO.getFnUpdateOper())
                            .setFnUpdateTime(processFlowNodeDO.getFnUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            processFlowNodeDTOList.add(processFlowNodeDTO);
        }
        return processFlowNodeDTOList;

    }

    /**
     * localDateTime׼��
     *
     * @param localDateTime ʱ��
     * @return �ַ�������
     */
    @SuppressWarnings("unused")
    private static String localDateTime2StringDate(LocalDateTime localDateTime) {
        String date = "";
        int year = localDateTime.getYear();
        date += year;
        int month = localDateTime.getMonth().getValue();
        if (month <= 9) {
            date += "0" + month;
        } else {
            date += month;
        }
        int day = localDateTime.getDayOfMonth();
        if (day <= 9) {
            date += "0" + day;
        } else {
            date += day;
        }
        return date;
    }

    /**
     * �������̽ڵ�DTO
     *
     * @param processFlowNodeDOList ���̽ڵ㼯��(DO)
     * @return ���̽ڵ㼯��(DTO)
     */
    private List<ProcessFlowNodeDTOV2> buildProcessFlowNodeDTOV2(List<ProcessFlowNodeDO> processFlowNodeDOList) {
        List<ProcessFlowNodeDTOV2> processFlowNodeDTOList = new ArrayList<>();
        for (ProcessFlowNodeDO processFlowNodeDO : processFlowNodeDOList) {
            ProcessFlowNodeDTOV2 processFlowNodeDTO =
                    new ProcessFlowNodeDTOV2()
                            .setFnId(processFlowNodeDO.getFnId())
                            .setFnName(processFlowNodeDO.getFnName())
                            .setFnCode(processFlowNodeDO.getFnCode())
                            .setFnKind(FnNodeTypeEnums.sourceOf(processFlowNodeDO.getFnKind()))
                            .setFnCount(processFlowNodeDO.getFnCount())
                            .setFnCreateOper(processFlowNodeDO.getFnCreateOper())
                            .setFnCreateTime(processFlowNodeDO.getFnCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .setFnUpdateOper(processFlowNodeDO.getFnUpdateOper())
                            .setFnUpdateTime(processFlowNodeDO.getFnUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            processFlowNodeDTOList.add(processFlowNodeDTO);
        }
        return processFlowNodeDTOList;

    }

    /**
     * �������̽ڵ���Ϣ
     *
     * @param fnCode   ���̱���
     * @param fnCount  ���̽ڵ�����
     * @param operName ��Ա����
     * @return ���½��
     */
    @Override
    public PlainResult<String> updateProcessFlowInfo(String fnCode, int fnCount, String operName) {
        ProcessFlowNodeDO processFlowNodeDO = new ProcessFlowNodeDO().setFnCode(fnCode).setFnCount(fnCount).setFnUpdateTime(LocalDateTime.now()).setFnUpdateOper(operName);
        PlainResult<String> result = new PlainResult<>();
        try {
            sequenceFlowNodeMapper.updateFlowNodeByFnCode(processFlowNodeDO);
            return result.success("success", "update process flow info success");
        } catch (ProcessFlowNodeException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update process flow info failed");
        }
    }

    /**
     * �������̽ڵ�����ȡ���̽ڵ�
     *
     * @param fnCode ���̽ڵ����
     * @return ���̽ڵ���Ϣ
     */
    @Override
    public PlainResult<ProcessFlowNodeDTO> getSequenceNodeByFnCode(String fnCode) {
        ProcessFlowNodeDO flowNodeDO = sequenceFlowNodeMapper.getSequenceNodeByFnCode(fnCode);
        PlainResult<ProcessFlowNodeDTO> result = new PlainResult<>();

        return result.success(buildProcessFlowNodeDTO(flowNodeDO), "load sequence node success");
    }

    /**
     * �������̽ڵ���Ϣ
     *
     * @param processFlowNodeDO ���̽ڵ���Ϣ(DO)
     * @return ���̽ڵ���Ϣ(DTO)
     */
    private ProcessFlowNodeDTO buildProcessFlowNodeDTO(ProcessFlowNodeDO processFlowNodeDO) {
        ProcessFlowNodeDTO processFlowNodeDTO = new ProcessFlowNodeDTO();
        processFlowNodeDTO.setFnCode(processFlowNodeDO.getFnCode())
                .setFnCount(processFlowNodeDO.getFnCount())
                .setFnCreateOper(processFlowNodeDO.getFnCreateOper())
                .setFnUpdateOper(processFlowNodeDO.getFnUpdateOper())
                .setFnCreateTime(processFlowNodeDO.getFnCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setFnUpdateTime(processFlowNodeDO.getFnUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setFnKind(processFlowNodeDO.getFnKind())
                .setFnName(processFlowNodeDO.getFnName());
        return processFlowNodeDTO;
    }

    /**
     * ��ȡ���̽ڵ���Ŀ
     *
     * @param fnCode ���̽ڵ����
     * @return ���̽ڵ���Ŀ
     */
    public Integer getProcessFlowNodeCountByFnCode(String fnCode) {
        Integer fnCount = sequenceFlowNodeMapper.getProcessFlowNodeCountByFnCode(fnCode);
        if (Objects.isNull(fnCount)) {
            return -1;
        }
        return fnCount;
    }

    /**
     * �ƻ������̽ڵ�
     *
     * @param req �Ŵ���ʶ
     * @return ���̽ڵ���Ŀ
     */
    @Override
    public Integer getProcessFlowNodeCountByMonth(int req) {
        return null;
    }

    /**
     * ͨ�����ͻ�ȡ�ڵ�
     *
     * @param type �ڵ�����
     * @return ���̽ڵ���Ŀ
     */
    @Override
    public Integer getProcessFlowNodeCountByFnKind(String type) {
        return null;
    }
}
