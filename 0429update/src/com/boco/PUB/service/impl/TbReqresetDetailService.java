package com.boco.PUB.service.impl;


import com.boco.PUB.service.ITbReqresetDetailService;
import com.boco.SYS.entity.TbReqresetList;
import com.boco.SYS.mapper.TbReqresetDetailMapper;
import com.boco.SYS.mapper.TbReqresetListMapper;
import com.boco.SYS.util.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbReqresetDetail;
import com.boco.SYS.base.GenericService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �������뱨��Ҫ��¼����ϸ��ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbReqresetDetailService extends GenericService<TbReqresetDetail, Integer> implements ITbReqresetDetailService {
    //���Զ��巽��ʱʹ��
    @Autowired
    private TbReqresetDetailMapper tbReqresetDetailMapper;
    @Autowired
    private TbReqresetListMapper tbReqresetListMapper;


    /**
     * ��������ɾ��
     *
     * @param tbReqresetDetail
     * @return
     */
    @Override
    public int deleteByAttr(TbReqresetDetail tbReqresetDetail) {
        return tbReqresetDetailMapper.deleteByAttr(tbReqresetDetail);
    }

    /**
     * ��������������.
     *
     * @param tbReqresetDetail
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectReqdId(TbReqresetDetail tbReqresetDetail) {
        return tbReqresetDetailMapper.selectReqdId(tbReqresetDetail);
    }

    /**
     * ����������������.
     *
     * @param tbReqresetDetail
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectReqdRefId(TbReqresetDetail tbReqresetDetail) {
        return tbReqresetDetailMapper.selectReqdRefId(tbReqresetDetail);
    }

    /**
     * ���� �·� �ͻ����� ��ѯϽ�ڻ��������������
     * @param month
     * @param organCode
     * @return
     */
    @Override
    public Map<String, BigDecimal> getReqResetDetailList(String month, String organCode) {
        TbReqresetList searchTbList = new TbReqresetList();
        searchTbList.setReqresetMonth(month);
        searchTbList.setReqresetType(0);
        searchTbList.setReqresetOrgan(organCode);
        List<TbReqresetList> tbReqresetLists = tbReqresetListMapper.selectByAttr(searchTbList);
        Map<String, BigDecimal> dataMap = new HashMap<>();
        if (tbReqresetLists != null && tbReqresetLists.size() > 0) {
            TbReqresetList tbReqresetList = tbReqresetLists.get(0);
            int reqresetId = tbReqresetList.getReqresetId();
            TbReqresetDetail searchTbDetail = new TbReqresetDetail();
            searchTbDetail.setReqdresetRefId(reqresetId);
            searchTbDetail.setReqdresetState(16);
            List<TbReqresetDetail> tbReqresetDetails = tbReqresetDetailMapper.selectByAttr(searchTbDetail);
            if (tbReqresetDetails != null && tbReqresetDetails.size() > 0) {
                for (TbReqresetDetail tempTb : tbReqresetDetails) {
                    String organCode_combCode = tempTb.getReqdresetOrgan() + "_" + tempTb.getReqdresetCombCode();
                    dataMap.put(organCode_combCode, BigDecimalUtil.getSafeCount(tempTb.getReqdresetNum()));
                }
            }
        }
        return dataMap;
    }

}