package com.boco.AL.service.impl;


import com.boco.AL.service.ITbPunishParamService;
import com.boco.SYS.mapper.TbPunishParamMapper;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbPunishParam;
import com.boco.SYS.base.GenericService;

import java.util.List;
import java.util.Map;

/**
 * 罚息参数表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class TbPunishParamService extends GenericService<TbPunishParam, Integer> implements ITbPunishParamService {


    @Autowired
    private TbPunishParamMapper tbPunishParamMapper;

    /**
     * 查询tbPunishParam
     *
     * @param ppId
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public TbPunishParam selectByPpId(Integer ppId) throws Exception {
        return tbPunishParamMapper.selectByPK(ppId);
    }

    /**
     * 部署tbPunishParam规则
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public Json deploy() throws Exception {

        //TODO 涉及到规则引擎部分  1.拿到所有规则 2.拼装字符串 3.封装到规则实体类 4.扔到规则引擎中5.留痕


        return json.returnMsg("true", "部署成功!");
    }

    /**
     * 新增tbPunishParam
     *
     * @param tbPunishParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public Json insertTbPunishParam(TbPunishParam tbPunishParam) throws Exception {

        //调用新增校验规则


        int count = insertEntity(tbPunishParam);
        //插入数据库失败
        if (count == 1) {
            //"新增成功!"
            return this.json.returnMsg("true", getErrorInfo("w456"));
        }
        //新增失败
        return this.json.returnMsg("false", getErrorInfo("w446"));

    }

    /**
     * 修改tbPunishParam
     *
     * @param tbPunishParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public Json updateTbPunishParam(TbPunishParam tbPunishParam) throws Exception {
        //调用新增校验规则
        boolean check = true; //TODO 校验阈值逻辑问题 （1-6 和4-8 交叉问题）

        if (check == false) {
            return this.json;
        }
        tbPunishParam.setUpdatetime(BocoUtils.getTime());
        int count = updateByPK(tbPunishParam);
        //更新数据库失败
        if (count == 1) {
            //"修改成功!"
            return this.json.returnMsg("true", getErrorInfo("w448"));
        }
        //修改失败
        return this.json.returnMsg("false", getErrorInfo("w447"));

    }

    /**
     *  联想输入参数编号.
     *
     * @param tbPunishParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectPpId(TbPunishParam tbPunishParam) {
        List<Map<String, Integer>> list = tbPunishParamMapper.selectPpId(tbPunishParam);
        return list;
    }

    /**
     *  联想输入参数名称.
     *
     * @param tbPunishParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectPpName(TbPunishParam tbPunishParam) {
        List<Map<String, String>> list = tbPunishParamMapper.selectPpName(tbPunishParam);
        return list;
    }

    /**
     *  联想输入机构编号.
     *
     * @param tbPunishParam
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectPpOrgan(TbPunishParam tbPunishParam) {
        List<Map<String, String>> list = tbPunishParamMapper.selectPpOrgan(tbPunishParam);
        return list;
    }
    //有自定义方法时使用
    //@Autowired
    //private TbPunishParamMapper mapper;
}