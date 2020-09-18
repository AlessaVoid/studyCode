package com.boco.PUB.service.impl;


import com.boco.PUB.service.ITbReqDetailService;
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
 * 信贷需求录入详细表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbReqDetailService extends GenericService<TbReqDetail, Integer> implements ITbReqDetailService {
    //有自定义方法时使用
    //@Autowired
    //private TbReqDetailMapper mapper;

    @Autowired
    private TbReqDetailMapper tbReqDetailMapper;

    @Autowired
    private TbReqListMapper tbReqListMapper;


    /**
     * 联想输入需求编号.
     *
     * @param tbReqDetail
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectReqdId(TbReqDetail tbReqDetail) {
        return tbReqDetailMapper.selectReqdId(tbReqDetail);
    }

    /**
     * 联想输入所属周期.
     *
     * @param tbReqDetail
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectReqdRefId(TbReqDetail tbReqDetail) {
        return tbReqDetailMapper.selectReqdRefId(tbReqDetail);
    }


    /**
     * 根据条件删除
     *
     * @param tbReqDetail
     * @return
     */
    @Override
    public int deleteByAttr(TbReqDetail tbReqDetail){
        return tbReqDetailMapper.deleteByAttr(tbReqDetail);
    }

    /**
     * 联想输入所属周期.
     *
     * @param tbReqDetail
     * @param tbReqList
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<TbReqList> selectTbreqList(TbReqDetail tbReqDetail, TbReqList tbReqList) {

        List<TbReqList> tbReqListList = new ArrayList<>();
        List<TbReqList> list = tbReqListMapper.selectByAttr(tbReqList);
        List<TbReqDetail> tbReqDetailList = tbReqDetailMapper.selectByAttr(tbReqDetail);
        String organCode =tbReqDetail.getReqdOrgan();
        for (TbReqList tbReqList2 : list) {
            String organListStr = tbReqList2.getReqOrganList();
            if (organListStr.indexOf(organCode) != -1) {
                //先统一默认刚下发
                tbReqList2.setReqState(TbReqDetail.STATE_ISSUED);
                for (TbReqDetail tbReqDetail1 : tbReqDetailList) {
                    if (tbReqList2.getReqId().equals(tbReqDetail1.getReqdRefId())) {
                        tbReqList2.setReqState(tbReqDetail1.getReqdState());
                        break;
                    }
                }
                if(tbReqList2.getReqState()<TbReqDetail.STATE_SUBMITED){
                    tbReqListList.add(tbReqList2);
                }
            }
        }

        return  tbReqListList;
    }


}