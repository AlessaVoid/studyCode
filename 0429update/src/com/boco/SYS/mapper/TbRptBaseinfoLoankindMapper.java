package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.entity.TbRptBaseinfoLoankind;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * 新报表，机构贷种维度数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface TbRptBaseinfoLoankindMapper extends GenericMapper<TbRptBaseinfoLoankind,HashMap<String,Object>>{
    /**
     * 查询当前登录机构及其下属机构的数据
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectByDateAndOrgan(HashMap<String, Object> param);

    /**
     * 汇总数据到一分机构维度
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectByDateAndComb(HashMap<String, Object> param);

    /**
     * 汇总到区域维度
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectAreaData(HashMap<String, Object> param);

    /**
     * 查询除一分外的其他重点行
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectOtherKeyOrgan(HashMap<String, Object> param);

    /**
     * 获取一分机构数据
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectLevelOtherOrganDataByDateAndComb(HashMap<String, Object> param);

    /**
     * 一分登陆 查询机构数据
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectOrganDataByDateAndOrgan(HashMap<String, Object> param);

    /**
     * 流量表 - 总行登录查询一分机构的数据
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectDataFlowLevelOneOrgan(HashMap<String, Object> param);

    /**
     * 流量表 - 总行登录查询一分机构未来的数据
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectDataFlowLevelOneOrganFuture(HashMap<String, Object> param);
    /**
     * 流量表 - 一分登录查询二分机构的数据
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectDataFlowLevelOtherOrgan(HashMap<String, Object> param);

    /**
     * 流量表 - 一分登录查询二分机构未来的数据
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectDataFlowLevelOtherOrganFuture(HashMap<String, Object> param);

    /**
     * 流量表 - 区域数据
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectDataFlowAreaData(HashMap<String, Object> param);

    /**
     * 流量表 - 区域未来数据
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectDataFlowAreaDataFuture(HashMap<String, Object> param);

    /**
     * 期限表 -总行登录查询一分机构的数据
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectTimeLimitLevelOneOrgan(HashMap<String, Object> param);

    /**
     * 期限表 - 一分登录查询二分机构的数据
     * @param param
     * @return
     */
    List<TbRptBaseinfoLoankind> selectTimeLimitLevelOtherOrgan(HashMap<String, Object> param);
}