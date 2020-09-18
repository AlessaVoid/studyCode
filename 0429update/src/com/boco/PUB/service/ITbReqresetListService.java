package com.boco.PUB.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbReqresetList;
import com.boco.SYS.base.IGenericService;

/**
 * TbReqresetList业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface ITbReqresetListService extends IGenericService<TbReqresetList, Integer> {

    /**
     * 联想输入报送要求下发编号.
     *
     * @param tbReqresetList
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, Integer>> selectReqId(TbReqresetList tbReqresetList);

    /**
     * 联想输入报送要求下发编号.
     *
     * @param tbReqresetList
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, Integer>> showReqId(TbReqresetList tbReqresetList);

    /**
     * 联想输入所属周期---批次页面.
     *
     * @param tbReqresetList
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> selectReqMonth(TbReqresetList tbReqresetList);

    /**
     * 联想输入所属周期---详情页面.
     *
     * @param tbReqresetList
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> showReqMonth(TbReqresetList tbReqresetList);
    /**
     * 联想输入下发机构页面.
     *
     * @param tbReqresetList
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> selectReqresetOrgan(TbReqresetList tbReqresetList);

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
    List<TbReqresetList> selectByReqTo(Integer reqTo, String organCode);

    /**
     * 查询reqTo为0的记录
     *
     * @param tbReqresetList
     * @return
     */
    List<TbReqresetList> checkApprovedList(TbReqresetList tbReqresetList);



    /**
     *
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     * return : [organCode_combCode:reserNum]
     */
    public Map<String, BigDecimal> getLineReqResetDetailList(String month, String organCode);


}