package com.boco.SYS.mapper;

import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbReqresetList;
import com.boco.SYS.base.GenericMapper;

/**
 * TbReqresetList数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbReqresetListMapper extends GenericMapper<TbReqresetList, Integer> {


    /**
     * 联想输入查询-id
     *
     * @param tbReqresetList
     * @return
     */
    List<Map<String, Integer>> selectReqId(TbReqresetList tbReqresetList);

    /**
     * 联想输入查询-id
     *
     * @param tbReqresetList
     * @return
     */
    List<Map<String, Integer>> showReqId(TbReqresetList tbReqresetList);

    /**
     * 联想输入查询-所属月份
     *
     * @param tbReqresetList
     * @return
     */
    List<Map<String, String>> selectReqMonth(TbReqresetList tbReqresetList);

    /**
     * 联想输入查询-所属月份
     *
     * @param tbReqresetList
     * @return
     */
    List<Map<String, String>> showReqMonth(TbReqresetList tbReqresetList);

    /**
     * 联想输入查询-下发机构
     *
     * @param tbReqresetList
     * @return
     */
    List<Map<String, String>> selectReqresetOrgan(TbReqresetList tbReqresetList);


    /**
     * 更新需求 下发状态
     *
     * @param tbReqresetList
     * @return
     */
    int updateReqState(TbReqresetList tbReqresetList);

    List<TbReqresetList> checkApprovedList(TbReqresetList tbReqresetList);
}