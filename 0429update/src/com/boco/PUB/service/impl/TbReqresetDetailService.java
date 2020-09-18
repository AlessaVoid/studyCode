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
 * 调整申请报送要求录入详细表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbReqresetDetailService extends GenericService<TbReqresetDetail, Integer> implements ITbReqresetDetailService {
    //有自定义方法时使用
    @Autowired
    private TbReqresetDetailMapper tbReqresetDetailMapper;
    @Autowired
    private TbReqresetListMapper tbReqresetListMapper;


    /**
     * 根据条件删除
     *
     * @param tbReqresetDetail
     * @return
     */
    @Override
    public int deleteByAttr(TbReqresetDetail tbReqresetDetail) {
        return tbReqresetDetailMapper.deleteByAttr(tbReqresetDetail);
    }

    /**
     * 联想输入需求编号.
     *
     * @param tbReqresetDetail
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectReqdId(TbReqresetDetail tbReqresetDetail) {
        return tbReqresetDetailMapper.selectReqdId(tbReqresetDetail);
    }

    /**
     * 联想输入所属周期.
     *
     * @param tbReqresetDetail
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectReqdRefId(TbReqresetDetail tbReqresetDetail) {
        return tbReqresetDetailMapper.selectReqdRefId(tbReqresetDetail);
    }

    /**
     * 根据 月份 和机构号 查询辖内机构的需求调整量
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