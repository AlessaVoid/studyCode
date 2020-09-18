package com.boco.TONY.biz.loancomb.service.impl;

import com.alibaba.fastjson.JSON;
import com.boco.SYS.mapper.LoanCombDetailMapper;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.LoanCombTakenBaseMapper;
import com.boco.SYS.mapper.LoanCombTakenDetailMapper;
import com.boco.TONY.constants.CombTakenTypeConstants;
import com.boco.TONY.biz.loancomb.POJO.DO.combtaken.CombTakenBaseDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combtaken.CombTakenDetailDO;
import com.boco.TONY.biz.loancomb.POJO.DTO.combtaken.CombTakenDetailDTO;
import com.boco.TONY.biz.loancomb.exception.LoanCombException;
import com.boco.TONY.biz.loancomb.service.LoanCombTakenService;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author tony
 * @describe CombTakenServiceImpl
 * @date 2019-10-27
 */
@Service
public class LoanCombTakenServiceImpl implements LoanCombTakenService {
    @Autowired
    LoanCombDetailMapper combDetailMapper;
    @Autowired
    LoanCombMapper loanCombMapper;
    @Autowired
    LoanCombTakenBaseMapper loanCombTakenBaseMapper;
    @Autowired
    LoanCombTakenDetailMapper loanCombTakenDetailMapper;

    /**
     * ���ռ��
     *
     * @param gridData       ռ�ù���
     * @param parentCombCode �����������
     * @param takenType      ����ռ������
     * @return
     * @throws LoanCombException
     */
    @Override
    public PlainResult<String> takeLoanCombInfo(String gridData, String parentCombCode, int takenType) throws LoanCombException {
        Preconditions.checkArgument(StringUtils.isNotBlank(gridData));
        CombTakenBaseDO combTakenBaseDO = new CombTakenBaseDO().setParentComb(parentCombCode).setTakenType(takenType);
        //ͨ��combparent��ȡ����
        CombTakenBaseDO combTakenBaseInfo = loanCombTakenBaseMapper.getCombTakenBaseInfo(parentCombCode);
        loanCombTakenDetailMapper.deleteTakenCombInfo(parentCombCode);
        if (Objects.isNull(combTakenBaseInfo)) {
            loanCombTakenBaseMapper.insertCombTakenBaseInfo(combTakenBaseDO);
        } else {
            loanCombTakenBaseMapper.updateCombTakenBaseInfo(combTakenBaseDO);
        }
        PlainResult<String> result = new PlainResult<>();
        if (takenType == CombTakenTypeConstants.COMB_PART_TAKEN) {
            List<CombTakenDetailDO> combTakenDetailDOList = JSON.parseArray(gridData, CombTakenDetailDO.class);
            combTakenDetailDOList.stream().filter(Objects::nonNull).filter(
                    item -> !StringUtils.isBlank(item.getCombId()) && !StringUtils.isBlank(item.getCombTakenId()))
                    .forEach(item -> {
                                CombTakenDetailDO combTakenDetailDO = new CombTakenDetailDO().setCombId(item.getCombId()).setCombTakenId(item.getCombTakenId()).setCombParentId(parentCombCode);
                                loanCombTakenDetailMapper.insertCombTakenDetailInfo(combTakenDetailDO);
                            }
                    );
        }
        return result.success("comb taken type constants", "update comb taken type success");
    }


    /**
     * ͨ���ϼ�������ϲ�ѯ�¼��������
     *
     * @param parentId ������ϸ�id
     * @return ����������б�
     */
    @Override
    public ListResult<CombTakenDetailDTO> selectLoanCombTakenByParentId(String parentId) {
        ListResult<CombTakenDetailDTO> result = new ListResult<>();
        List<CombTakenDetailDO> combInnerTakenDOList = loanCombTakenDetailMapper.selectCombTakenInfoByParentId(parentId);
        return result.success(buildCombTakenDetailDTOList(combInnerTakenDOList), "select inner taken comb info by parent id");
    }

    /**
     * �����ڲ�takenDTO
     *
     * @param combTakenDetailDOList �����ڲ�taken DOList
     * @return ������������б�(DTO)
     */
    private List<CombTakenDetailDTO> buildCombTakenDetailDTOList(List<CombTakenDetailDO> combTakenDetailDOList) {
        List<CombTakenDetailDTO> combInnerTakenDTOList = new ArrayList<>();
        for (CombTakenDetailDO combTakenDetailDO : combTakenDetailDOList) {
            CombTakenDetailDTO combTakenDetailDTO = new CombTakenDetailDTO();
            BeanUtils.copyProperties(combTakenDetailDO, combTakenDetailDTO);
            combTakenDetailDTO.setCombTakenId(combTakenDetailDO.getCombTakenId());
            combInnerTakenDTOList.add(combTakenDetailDTO);
        }
        return combInnerTakenDTOList;
    }

    public Integer selectInterTakentype() {
        return loanCombTakenDetailMapper.selectInterTakentype();
    }

    /**
     * ͨ��parentype��ѯtakenype
     */
    public Integer getTakenTypeByCombParent(String combCode) {
        Integer i=loanCombTakenDetailMapper.getTakenTypeByCombParent(combCode);
        return i;
    }
}
