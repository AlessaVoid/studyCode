package com.boco.SYS.mapper;

import java.util.List;
import java.util.Map;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbReqList;

/**
 * 下发信贷需求报送要求表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbReqListMapper extends GenericMapper<TbReqList, Integer> {


    /**
     * 联想输入查询-id
     *
     * @param tbReqList
     * @return
     */
    List<Map<String, Integer>> selectReqId(TbReqList tbReqList);
    /**
     * 联想输入查询-id
     *
     * @param tbReqList
     * @return
     */
    List<Map<String, Integer>> showReqId(TbReqList tbReqList);

    /**
     * 联想输入查询-所属月份
     *
     * @param tbReqList
     * @return
     */
    List<Map<String, String>> selectReqMonth(TbReqList tbReqList);
    /**
     * 联想输入查询-所属月份
     *
     * @param tbReqList
     * @return
     */
    List<Map<String, String>> selectReqOrgan(TbReqList tbReqList);
    /**
     * 联想输入查询-所属月份
     *
     * @param tbReqList
     * @return
     */
    List<Map<String, String>> showReqMonth(TbReqList tbReqList);


    /**
     * 更新需求 下发状态
     *
     * @param tbReqLis
     * @return
     */
    int updateReqState(TbReqList tbReqLis);

    /**
     * 查询reqTo为0的记录
     *
     * @param
     * @return
     */
    List<TbReqList> getMonth();


}