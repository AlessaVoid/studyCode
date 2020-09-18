package com.boco.AL.service;

import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbPunishParam;
import com.boco.SYS.base.IGenericService;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * 罚息参数表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface ITbPunishParamService extends IGenericService<TbPunishParam, Integer>{



    /**
     * 查询tbPunishParam
     *
     * @param ppId
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    TbPunishParam selectByPpId(Integer ppId)throws Exception;


    /**
     * 部署tbPunishParam规则
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */

    Json deploy() throws Exception;


    /**
     * 新增tbPunishParam
     *
     * @param tbPunishParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */

    Json insertTbPunishParam(TbPunishParam tbPunishParam) throws Exception;

    /**
     * 修改tbPunishParam
     *
     * @param tbPunishParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */

    Json updateTbPunishParam(TbPunishParam tbPunishParam) throws Exception;


    /**
     *  联想输入参数编号.
     *
     * @param tbPunishParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, Integer>> selectPpId(TbPunishParam tbPunishParam);

    /**
     *  联想输入参数名称.
     *
     * @param tbPunishParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> selectPpName(TbPunishParam tbPunishParam);



    /**
     *  联想输入机构编号.
     *
     * @param tbPunishParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> selectPpOrgan(TbPunishParam tbPunishParam);



}