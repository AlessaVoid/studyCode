package com.boco.PUB.service.impl;


import com.boco.PUB.service.ITbReqresetListService;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.SYS.mapper.TbLineReqresetDetailMapper;
import com.boco.SYS.mapper.TbReqresetDetailMapper;
import com.boco.SYS.mapper.TbReqresetListMapper;
import com.boco.SYS.util.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.base.GenericService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TbReqresetListҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbReqresetListService extends GenericService<TbReqresetList, Integer> implements ITbReqresetListService {
    @Autowired
    private TbReqresetListMapper tbReqresetListMapper;


    @Autowired
    private TbReqresetDetailMapper tbReqresetDetailMapper;


    @Autowired
    private TbLineReqresetDetailMapper tbLineReqresetDetailMapper;

    /**
     * �������뱨��Ҫ����.
     *
     * @param tbReqresetList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectReqId(TbReqresetList tbReqresetList) {
        List<Map<String, Integer>> list = tbReqresetListMapper.selectReqId(tbReqresetList);
        return list;
    }

    /**
     * �������뱨��Ҫ����.
     *
     * @param tbReqresetList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> showReqId(TbReqresetList tbReqresetList) {
        List<Map<String, Integer>> list = tbReqresetListMapper.showReqId(tbReqresetList);
        return list;
    }

    /**
     * ����������������.
     *
     * @param tbReqresetList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectReqMonth(TbReqresetList tbReqresetList) {
        List<Map<String, String>> list = tbReqresetListMapper.selectReqMonth(tbReqresetList);
        return list;
    }

    /**
     * ����������������.
     *
     * @param tbReqresetList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> showReqMonth(TbReqresetList tbReqresetList) {
        List<Map<String, String>> list = tbReqresetListMapper.showReqMonth(tbReqresetList);
        return list;
    }

    /**
     * ���������·�����ҳ��.
     *
     * @param tbReqresetList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectReqresetOrgan(TbReqresetList tbReqresetList) {
        List<Map<String, String>> list = tbReqresetListMapper.selectReqresetOrgan(tbReqresetList);
        return list;
    }

    /**
     * �����·�״̬
     *
     * @param id,state
     * @return
     */
    @Override
    public int updateReqState(Integer id, int state) {
        TbReqresetList tbReqresetList = new TbReqresetList();
        tbReqresetList.setReqresetId(id);
        tbReqresetList.setReqresetState(state);
        return tbReqresetListMapper.updateReqState(tbReqresetList);

    }

    /**
     * ��reqToΪ������ѯ��¼
     *
     * @param reqTo, organCode
     * @return
     */
    @Override
    public List<TbReqresetList> selectByReqTo(Integer reqTo, String organCode) {
        TbReqresetList tbReqresetList = new TbReqresetList();
        tbReqresetList.setReqresetOrgan(organCode);
        tbReqresetList.setReqresetTo(reqTo);
        return tbReqresetListMapper.selectByAttr(tbReqresetList);
    }

    /**
     * ��ѯreqToΪ0�ļ�¼
     *
     * @param tbReqresetList
     * @return
     */
    @Override
    public List<TbReqresetList> checkApprovedList(TbReqresetList tbReqresetList) {
        return tbReqresetListMapper.checkApprovedList(tbReqresetList);
    }

    /**
     * ��ȡ���ߵ����������
     *
     * @param month
     * @param organCode
     * @return
     */
    @Override
    public Map<String, BigDecimal> getLineReqResetDetailList(String month, String organCode) {
        TbReqresetList searchTbList = new TbReqresetList();
        searchTbList.setReqresetMonth(month);
        searchTbList.setReqresetType(1);
        searchTbList.setReqresetOrgan(organCode);
        List<TbReqresetList> tbReqresetLists = tbReqresetListMapper.selectByAttr(searchTbList);
        Map<String, BigDecimal> dataMap = new HashMap<>();
        if (tbReqresetLists != null && tbReqresetLists.size() > 0) {
            TbReqresetList tbReqresetList = tbReqresetLists.get(0);
            int reqresetId = tbReqresetList.getReqresetId();
            TbLineReqresetDetail searchTbLine = new TbLineReqresetDetail();
            searchTbLine.setLineResetrefId(reqresetId);
            searchTbLine.setLineState(16);
            List<TbLineReqresetDetail> tbReqresetDetails = tbLineReqresetDetailMapper.selectByAttr(searchTbLine);
            if (tbReqresetDetails != null && tbReqresetDetails.size() > 0) {
                for (TbLineReqresetDetail tempTb : tbReqresetDetails) {
                    String combStr = tempTb.getLineCombCode();
                    String[] split = combStr.split(",");
                    String[] NumArr = tempTb.getLineNum().split(",");
                    for (int i = 0; i < split.length; i++) {
                        String keyStr = tempTb.getLineOrgan() + "_" + split[i];
                        BigDecimal value = BigDecimalUtil.getSafeCount(NumArr[i]);
                        dataMap.put(keyStr, value);
                    }
                }
            }
        }
        return dataMap;

    }


}