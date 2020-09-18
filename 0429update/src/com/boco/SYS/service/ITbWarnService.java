package com.boco.SYS.service;

import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbWarn;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * 预警线表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface ITbWarnService extends IGenericService<TbWarn, Integer>{


    /**
     *  联想输入参数编号.
     *
     * @param tbWarn
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, Integer>> selectWarnId(TbWarn tbWarn);

    /**
     *  联想输入参数名称.
     *
     * @param tbWarn
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> selectWarnName(TbWarn tbWarn);
    public List<Map<String, String>> selectWarnNameByOrgan(TbWarn tbWarn);


    /**
     *  联想输入机构编号.
     *
     * @param tbWarn
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    public List<Map<String, String>> selectWarnOrgan(TbWarn tbWarn);


    List<Map<String, String>> selectWarnOrgan1(Map<String, String> map);
}