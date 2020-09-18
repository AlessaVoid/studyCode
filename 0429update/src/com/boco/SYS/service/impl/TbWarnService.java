package com.boco.SYS.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.SYS.mapper.TbWarnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbWarn;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.service.ITbWarnService;

/**
 * 
 * 
 * 预警线表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbWarnService extends GenericService<TbWarn, Integer> implements ITbWarnService{

    @Autowired
    private TbWarnMapper tbWarnMapper;
    /**
     * 联想输入参数编号.
     *
     * @param tbWarn
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectWarnId(TbWarn tbWarn) {
        List<Map<String, Integer>> list = tbWarnMapper.selectWarnId(tbWarn);
        return list;
    }

    /**
     * 联想输入参数名称.
     *
     * @param tbWarn
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectWarnName(TbWarn tbWarn) {
        List<Map<String, String>> list = tbWarnMapper.selectWarnName(tbWarn);
        return list;
    }

    @Override
    public List<Map<String, String>> selectWarnNameByOrgan(TbWarn tbWarn) {
        List<Map<String, String>> list = tbWarnMapper.selectWarnNameByOrgan(tbWarn);
        return list;
    }

    /**
     * 联想输入机构编号.
     *
     * @param tbWarn
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectWarnOrgan(TbWarn tbWarn) {
        List<Map<String, String>> list = tbWarnMapper.selectWarnOrgan(tbWarn);
        return list;
    }

    @Override
    public List<Map<String, String>> selectWarnOrgan1(Map<String, String> map) {

        List<Map<String, String>> list = tbWarnMapper.selectWarnOrgan1(map);
        return list;
    }

    //有自定义方法时使用
	//@Autowired
	//private TbWarnMapper mapper;
}