package com.boco.SYS.mapper;

import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.TbPunishParam;
import com.boco.SYS.base.GenericMapper;

/**
 * 罚息参数表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface TbPunishParamMapper extends GenericMapper<TbPunishParam, Integer> {




    /**
     * TODO 联想输入参数编号.
     *
     * @param tbPunishParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年4月11日    	    秦海舟   新建
     * </pre>
     */
    public List<Map<String, Integer>> selectPpId(TbPunishParam tbPunishParam);

    /**
     * TODO 联想输入参数名称.
     *
     * @param tbPunishParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年4月11日    	    秦海舟   新建
     * </pre>
     */
    public List<Map<String, String>> selectPpName(TbPunishParam tbPunishParam);

    /**
     * TODO 联想输入机构编号.
     *
     * @param tbPunishParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年4月11日    	    秦海舟   新建
     * </pre>
     */
    public List<Map<String, String>> selectPpOrgan(TbPunishParam tbPunishParam);

}