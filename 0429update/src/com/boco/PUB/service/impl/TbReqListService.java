package com.boco.PUB.service.impl;

import com.boco.PUB.service.ITbReqListService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.entity.TbReqList;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.SYS.mapper.TbReqDetailMapper;
import com.boco.SYS.mapper.TbReqListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 下发信贷需求报送要求表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbReqListService extends GenericService<TbReqList, Integer> implements ITbReqListService {
    //有自定义方法时使用

    @Autowired
    private TbReqListMapper tbReqListMapper;


    @Autowired
    private FdOrganMapper fdOrganMapper;


    @Autowired
    private TbReqDetailMapper tbReqDetailMapper;


    /**
     * 联想输入报送要求编号.
     *
     * @param tbReqList
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectReqId(TbReqList tbReqList) {
        List<Map<String, Integer>> list = tbReqListMapper.selectReqId(tbReqList);
        return list;
    }

    /**
     * 联想输入报送要求编号.
     *
     * @param tbReqList
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> showReqId(TbReqList tbReqList) {
        List<Map<String, Integer>> list = tbReqListMapper.showReqId(tbReqList);
        return list;
    }

    /**
     * 联想输入所属周期.
     *
     * @param tbReqList
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectReqMonth(TbReqList tbReqList) {
        List<Map<String, String>> list = tbReqListMapper.selectReqMonth(tbReqList);
        return list;
    }
    /**
     * 联想输入所属周期.
     *
     * @param tbReqList
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectReqOrgan(TbReqList tbReqList) {
        List<Map<String, String>> list = tbReqListMapper.selectReqOrgan(tbReqList);
        return list;
    }

    /**
     * 联想输入所属周期.
     *
     * @param tbReqList
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> showReqMonth(TbReqList tbReqList) {
        List<Map<String, String>> list = tbReqListMapper.showReqMonth(tbReqList);
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
        TbReqList tbReqList = new TbReqList();
        tbReqList.setReqId(id);
        tbReqList.setReqState(state);
        return tbReqListMapper.updateReqState(tbReqList);

    }

    /**
     * 以reqTo为条件查询记录
     *
     * @param reqTo, organCode
     * @return
     */
    @Override
    public List<TbReqList> selectByReqTo(Integer reqTo, String organCode,List<TbReqDetail> tbReqDetailList) {
        TbReqList search = new TbReqList();
        search.setReqOrgan(organCode);
        search.setReqTo(reqTo);

        List<TbReqList> list = tbReqListMapper.selectByAttr(search);

        List<TbReqList> tbReqListList = new ArrayList<>();
        for (TbReqList tbReqList : list) {
            //先统一默认刚下发
            tbReqList.setReqState(TbReqDetail.STATE_ISSUED);
            for (TbReqDetail tbReqDetail1 : tbReqDetailList) {
                if (tbReqList.getReqId().equals(tbReqDetail1.getReqdRefId())) {
                    tbReqList.setReqState(tbReqDetail1.getReqdState());
                    break;
                }
            }
            tbReqListList.add(tbReqList);
        }
        return  tbReqListList;
    }

    /**
     * 查询reqTo为0的记录
     *
     * @return
     */
    @Override
    public List<TbReqList> getMonth() {
        return tbReqListMapper.getMonth();
    }

    /**
     * 信贷需求下发之后 生成该批次的下级机构统计数组
     *
     * @param reqId 批次id
     * @return
     */
    @Override
    public void checkChildOrganNum(Integer reqId) {
        TbReqList tbReqList = tbReqListMapper.selectByPK(reqId);
        FdOrgan fdOrgan = fdOrganMapper.selectByPK(tbReqList.getReqOrgan());
        int organLevel = Integer.parseInt(fdOrgan.getOrganlevel());
        //下一级机构要填报个数
        int organNum = 0;
        FdOrgan fdOrgan1 = new FdOrgan();
        //暂支持总行-一分|一分-二分
        if (organLevel == 0) {
            fdOrgan1.setOrganlevel("1");
            fdOrgan1.setCountrycode(fdOrgan.getThiscode());
            organNum = fdOrganMapper.selectByAttr(fdOrgan1).size();
        } else if (organLevel == 1) {
            fdOrgan1.setOrganlevel("2");
            fdOrgan1.setProvincecode(fdOrgan.getThiscode());
            organNum = fdOrganMapper.selectByAttr(fdOrgan1).size();
        }
        //统计已填报机构个数
        TbReqDetail tbReqDetail = new TbReqDetail();
        tbReqDetail.setReqdRefId(reqId);
        tbReqDetail.setReqdState(TbReqDetail.STATE_APPROVED);
        int reqDetailNum = tbReqDetailMapper.selectByAttr(tbReqDetail).size();

        tbReqList.setReqId(reqId);
        if (reqDetailNum == organNum) {
            tbReqList.setReqState(TbReqList.STATE_COMPLETE);
        } else {
            tbReqList.setReqState(TbReqList.STATE_UNDONE);
        }
        tbReqListMapper.updateByPK(tbReqList);
    }


}