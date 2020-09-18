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
 * 流程节点维护业务层接口实现
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
     * 获取所有的流程节点
     *
     * @return 节点信息集合
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
     * 构建流程节点DTO
     *
     * @param processFlowNodeDOList 流程节点DTO
     * @return 节点信息集合
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
     * localDateTime准换
     *
     * @param localDateTime 时间
     * @return 字符串日期
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
     * 构建流程节点DTO
     *
     * @param processFlowNodeDOList 流程节点集合(DO)
     * @return 流程节点集合(DTO)
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
     * 更新流程节点信息
     *
     * @param fnCode   流程编码
     * @param fnCount  流程节点数量
     * @param operName 柜员名称
     * @return 更新结果
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
     * 根据流程节点编码获取流程节点
     *
     * @param fnCode 流程节点编码
     * @return 流程节点信息
     */
    @Override
    public PlainResult<ProcessFlowNodeDTO> getSequenceNodeByFnCode(String fnCode) {
        ProcessFlowNodeDO flowNodeDO = sequenceFlowNodeMapper.getSequenceNodeByFnCode(fnCode);
        PlainResult<ProcessFlowNodeDTO> result = new PlainResult<>();

        return result.success(buildProcessFlowNodeDTO(flowNodeDO), "load sequence node success");
    }

    /**
     * 构建流程节点信息
     *
     * @param processFlowNodeDO 流程节点信息(DO)
     * @return 流程节点信息(DTO)
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
     * 获取流程节点数目
     *
     * @param fnCode 流程节点编码
     * @return 流程节点数目
     */
    public Integer getProcessFlowNodeCountByFnCode(String fnCode) {
        Integer fnCount = sequenceFlowNodeMapper.getProcessFlowNodeCountByFnCode(fnCode);
        if (Objects.isNull(fnCount)) {
            return -1;
        }
        return fnCount;
    }

    /**
     * 计划月流程节点
     *
     * @param req 信贷标识
     * @return 流程节点数目
     */
    @Override
    public Integer getProcessFlowNodeCountByMonth(int req) {
        return null;
    }

    /**
     * 通过类型获取节点
     *
     * @param type 节点类型
     * @return 流程节点数目
     */
    @Override
    public Integer getProcessFlowNodeCountByFnKind(String type) {
        return null;
    }
}
