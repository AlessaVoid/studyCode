package com.boco.PUB.service;


import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.entity.TbReqList;

import java.util.List;
import java.util.Map;

/**
 * 下发信贷需求报送要求表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface ITbReqListService extends IGenericService<TbReqList, Integer> {


    /**
     * 联想输入报送要求下发编号.
     *
     * @param tbReqList
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, Integer>> selectReqId(TbReqList tbReqList);

    /**
     * 联想输入报送要求下发编号.
     *
     * @param tbReqList
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, Integer>> showReqId(TbReqList tbReqList);

    /**
     * 联想输入所属周期---批次页面.
     *
     * @param tbReqList
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> selectReqMonth(TbReqList tbReqList);
    /**
     * 联想输入下发机构页面.
     *
     * @param tbReqList
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> selectReqOrgan(TbReqList tbReqList);

    /**
     * 联想输入所属周期---详情页面.
     *
     * @param tbReqList
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> showReqMonth(TbReqList tbReqList);


    /**
     * 更新下发状态
     *
     * @param id
     * @param state
     * @return
     */
    int updateReqState(Integer id, int state);


    /**
     * 查询reqTo为0的记录
     *
     * @param reqTo,organCode
     * @return
     */
    List<TbReqList> selectByReqTo(Integer reqTo, String organCode,List<TbReqDetail> tbReqDetailList);

    /**
     * 查询reqTo为0的记录
     *
     * @param
     * @return
     */
    List<TbReqList> getMonth();


    /**
     * 信贷需求审批之后 校验填报完成情况
     *
     * @param reqId
     */
    public void checkChildOrganNum( Integer reqId);


}