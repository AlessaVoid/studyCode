package com.boco.PUB.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.PUB.service.ITbLineReqDetailService;
import com.boco.SYS.mapper.TbLineReqDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbLineReqDetail;
import com.boco.SYS.base.GenericService;

/**
 * 
 * 
 * 条线需求记录详情表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbLineReqDetailService extends GenericService<TbLineReqDetail, Integer> implements ITbLineReqDetailService {
	//有自定义方法时使用
	@Autowired
	private TbLineReqDetailMapper tbLineReqDetailMapper;



    /**
     * 联想输入所属周期.
     *
     * @param tbLineReqDetail
     * @return <pre>
     * 修改日期        修改人    修改原因
     *  2019年9月19日    	    txn   新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> showLineReqMonth(TbLineReqDetail tbLineReqDetail) {
        List<Map<String, String>> list = tbLineReqDetailMapper.showLineReqMonth(tbLineReqDetail);
        return list;
    }

}