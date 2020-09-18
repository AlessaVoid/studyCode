package com.boco.PUB.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbReqresetDetail;
import com.boco.SYS.base.IGenericService;
import com.boco.SYS.util.BigDecimalUtil;

/**
 * 
 * 
 * 调整申请报送要求录入详细表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface ITbReqresetDetailService extends IGenericService<TbReqresetDetail, Integer>{

    /**
     * 根据条件删除
     * @param tbReqresetDetail
     * @return
     */

    int deleteByAttr(TbReqresetDetail tbReqresetDetail);

    /**
     * 联想输入需求编号.
     *
     * @param tbReqresetDetail
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, Integer>> selectReqdId(TbReqresetDetail tbReqresetDetail);

    /**
     * 联想输入所属周期.
     *
     * @param tbReqresetDetail
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> selectReqdRefId(TbReqresetDetail tbReqresetDetail);


    /**
     *
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     * return : [organCode_combCode:reserNum]
     */
    public Map<String, BigDecimal> getReqResetDetailList(String month, String organCode);

}