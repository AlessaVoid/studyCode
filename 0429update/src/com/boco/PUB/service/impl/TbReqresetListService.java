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
 * TbReqresetList业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
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
     * 联想输入报送要求编号.
     *
     * @param tbReqresetList
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectReqId(TbReqresetList tbReqresetList) {
        List<Map<String, Integer>> list = tbReqresetListMapper.selectReqId(tbReqresetList);
        return list;
    }

    /**
     * 联想输入报送要求编号.
     *
     * @param tbReqresetList
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> showReqId(TbReqresetList tbReqresetList) {
        List<Map<String, Integer>> list = tbReqresetListMapper.showReqId(tbReqresetList);
        return list;
    }

    /**
     * 联想输入所属周期.
     *
     * @param tbReqresetList
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectReqMonth(TbReqresetList tbReqresetList) {
        List<Map<String, String>> list = tbReqresetListMapper.selectReqMonth(tbReqresetList);
        return list;
    }

    /**
     * 联想输入所属周期.
     *
     * @param tbReqresetList
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> showReqMonth(TbReqresetList tbReqresetList) {
        List<Map<String, String>> list = tbReqresetListMapper.showReqMonth(tbReqresetList);
        return list;
    }

    /**
     * 联想输入下发机构页面.
     *
     * @param tbReqresetList
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectReqresetOrgan(TbReqresetList tbReqresetList) {
        List<Map<String, String>> list = tbReqresetListMapper.selectReqresetOrgan(tbReqresetList);
        return list;
    }

    /**
     * 更新下发状态
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
     * 以reqTo为条件查询记录
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
     * 查询reqTo为0的记录
     *
     * @param tbReqresetList
     * @return
     */
    @Override
    public List<TbReqresetList> checkApprovedList(TbReqresetList tbReqresetList) {
        return tbReqresetListMapper.checkApprovedList(tbReqresetList);
    }

    /**
     * 获取条线的需求调整量
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