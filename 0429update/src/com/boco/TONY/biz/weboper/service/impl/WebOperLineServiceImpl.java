package com.boco.TONY.biz.weboper.service.impl;

import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import com.boco.TONY.biz.weboper.POJO.DTO.WebOperLineDTO;
import com.boco.TONY.biz.weboper.exception.OperLineException;
import com.boco.SYS.mapper.WebOperLineMapper;
import com.boco.TONY.biz.weboper.service.IWebOperLineService;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 柜员条线业务逻辑层接口实现
 * @author tony
 * @describe WebOperLineServiceImpl
 * @date 2019-09-24
 */

@Service
public class WebOperLineServiceImpl implements IWebOperLineService {
    @Autowired
    WebOperLineMapper webOperLineMapper;

    private static final String COMMA = ",";

    /*
    插入一条条线产品
     */
    @Override
    public PlainResult<String> insertWebOperLine(String operCode, String lineIds) {
        Preconditions.checkState(null != operCode && null != lineIds);

        PlainResult<String> result = new PlainResult<>();
        String[] lineIdList = transferStr2LineIdList(lineIds);
        if (lineIdList.length <= 0) {
            throw new IllegalArgumentException("param is not match lineIdList is empty");
        }
        try {
            for (String lineId : lineIdList) {
                webOperLineMapper.insertWebOperLine(buildWebOperLineDO(operCode, lineId));
            }
            return result.success("success", "insert a record of oper line success");
        } catch (OperLineException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "insert web oper line failed");
        }
    }

    /*
    构建柜员角色条线
     */
    private WebOperLineDO buildWebOperLineDO(String operCode, String lineId) {
        WebOperLineDO webOperLineDO = new WebOperLineDO();
        webOperLineDO.setOperCode(operCode);
        webOperLineDO.setLineId(lineId);
        webOperLineDO.setCreateTime(LocalDateTime.now());
        webOperLineDO.setStatus(USABLE_STATUS);
        webOperLineDO.setUpdateTime(LocalDateTime.now());
        return webOperLineDO;
    }

    /*
    字符串转换成List
     */
    private String[] transferStr2LineIdList(String lineIds) {
        return lineIds.split(COMMA);
    }

    private static final int USABLE_STATUS = 1;

    /*
    更新柜员的条线
     */
    @Override
    public PlainResult<String> updateWebOperLine(String operCode, String lineIds) {
        Preconditions.checkState(null != operCode && null != lineIds);

        PlainResult<String> result = new PlainResult<>();
        String[] lineIdArr = transferStr2LineIdList(lineIds);
        List<String> lineIdList = Arrays.asList(lineIdArr).stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        List<String> cancelWebOperLineList = new ArrayList<>();

        try {
            WebOperLineDO webOperLineDO = new WebOperLineDO().setOperCode(operCode).setStatus(USABLE_STATUS);
            List<WebOperLineDO> webOperLineDOList = webOperLineMapper.getAllWebOperLineByOperCode(webOperLineDO);
            if (CollectionUtils.isEmpty(webOperLineDOList)&&StringUtils.isNotBlank(lineIds)) {
                return insertWebOperLine(operCode, lineIds);
            }

            for (WebOperLineDO webChildOperLineDO : webOperLineDOList) {
                String lineId = webChildOperLineDO.getLineId();
                if (!lineIdList.contains(lineId)) {
                    cancelWebOperLineList.add(webChildOperLineDO.getLineId());
                }
            }
            for (String newLineId : lineIdList) {
                if (!cancelWebOperLineList.contains(newLineId)) {
                    WebOperLineDO queryOperLine = new WebOperLineDO();
                    queryOperLine.setLineId(newLineId);
                    queryOperLine.setOperCode(operCode);
                    WebOperLineDO isExist = webOperLineMapper.checkWebOperLineIsExist(queryOperLine);
                    if (Objects.isNull(isExist)) {
                        webOperLineMapper.insertWebOperLine(buildWebOperLineDO(operCode, newLineId));
                    }
                }
            }
            for (String cancelLineId : cancelWebOperLineList) {
                webOperLineMapper.deleteWebOperLine(buildWebOperLineDO(operCode, cancelLineId));
            }
            return result.success("success", "update web oper line success");
        } catch (OperLineException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update web oper line failed");
        }
    }

    /*
    删除柜员下的条线
     */
    @Override
    public PlainResult<String> deleteWebOperLine(String operCode, String lineId) {
        PlainResult<String> result = new PlainResult<>();
        try {
            webOperLineMapper.deleteWebOperLine(buildWebOperLineDO(operCode, lineId));
            return result.success("delete", "delete operCode success");
        } catch (OperLineException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "delete oper line failed");
        }
    }

    /*
    删除柜员下面的所有条线
     */
    @Override
    public PlainResult<String> deleteAllWebOperLineByOperCode(String operCode) {
        PlainResult<String> result = new PlainResult<>();
        try {
            webOperLineMapper.deleteAllWebOperLineByOperCode(operCode);
            return result.success("success", "delete web oper line by oper code success.");
        } catch (OperLineException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "delete web oper line by oper code failed");
        }
    }

    /*
    通过operCode获取所有条线
     */
    @Override
    public ListResult<WebOperLineDTO> getAllWebOperLineByOperCode(String operCode, int status) {
        ListResult<WebOperLineDTO> result = new ListResult<>();
        try {
            List<WebOperLineDO> webOperLineDOList = webOperLineMapper.getAllWebOperLineByOperCode(buildWebOperLineDO(operCode, null).setStatus(status));
            List<WebOperLineDTO> webOperLineDTOList = transferWebOperLineDO2WebOperLineDTO(webOperLineDOList);
            return result.success(webOperLineDTOList, "load all web oper line by oper code success");
        } catch (OperLineException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "load all web oper line by oper code failed");
        }
    }

    /*
    transferWebOperLineDO2WebOperLineDTO
     */
    private List<WebOperLineDTO> transferWebOperLineDO2WebOperLineDTO(List<WebOperLineDO> webOperLineDOList) {
        List<WebOperLineDTO> webOperLineDTOList = new ArrayList<>();
        for (WebOperLineDO webOperLineDO : webOperLineDOList) {
            WebOperLineDTO webOperLineDTO = new WebOperLineDTO()
                    .setLineId(webOperLineDO.getLineId())
                    .setOperCode(webOperLineDO.getOperCode())
                    .setStatus(webOperLineDO.getStatus())
                    .setCreateTime(webOperLineDO.getCreateTime())
                    .setUpdateTime(webOperLineDO.getUpdateTime());
            webOperLineDTOList.add(webOperLineDTO);
        }
        return webOperLineDTOList;
    }
}
