package com.boco.PUB.service;


import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.entity.TbReqList;

import java.util.List;
import java.util.Map;

/**
 * 信贷需求录入详细表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface ITbReqDetailService extends IGenericService<TbReqDetail, Integer> {


    /**
     * 联想输入需求编号.
     *
     * @param tbReqDetail
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, Integer>> selectReqdId(TbReqDetail tbReqDetail);

    /**
     * 联想输入所属周期.
     *
     * @param tbReqDetail
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> selectReqdRefId(TbReqDetail tbReqDetail);


    /**
     * 根据条件删除
     * @param tbReqDetail
     * @return
     */

    int deleteByAttr(TbReqDetail tbReqDetail);


    /**
     * 联想输入所属周期.
     *
     * @param tbReqDetail
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
     List<TbReqList> selectTbreqList(TbReqDetail tbReqDetail,TbReqList tbReqList);



}