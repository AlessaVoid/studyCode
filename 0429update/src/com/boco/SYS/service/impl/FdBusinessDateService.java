package com.boco.SYS.service.impl;

import java.util.HashMap;

import com.boco.SYS.mapper.FdBusinessDateMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.FdBusinessDate;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.service.IFdBusinessDateService;

/**
 * 
 * 
 * FdBusinessDate业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      李海波      批量新建
 * </pre>
 */
@Service
public class FdBusinessDateService extends GenericService<FdBusinessDate,HashMap<String,Object>> implements IFdBusinessDateService{
	//有自定义方法时使用
	@Autowired
	private FdBusinessDateMapper mapper;
	@Autowired
	private DicCache dicCache;
	HashMap<String,Object> map = new HashMap<String, Object>();
    /**
     * 普通业务时间
     * @return
     */
    @Override
    public String getCommonDateDetails() {
        map.put("busiTypeCode","02");
        map.put("subBusiType","00");
        return mapper.selectByPK(map).getCurDate();
    }

    /**
     * 货币型产品时间
     * @return
     */
    @Override
    public String getCurrencyDateDetails() {
        map.put("busiTypeCode","02");
        map.put("subBusiType","01");
        return mapper.selectByPK(map).getCurDate();
    }
    /**
     * 开闭市状态标识
     */
    @Override
    public String getBusinessFlag(){
        map.put("busiTypeCode","02");
        map.put("subBusiType","00");
        return mapper.selectByPK(map).getBusiFlag();
    }

	@Override
	public FdBusinessDate getCommonFdBusinessDate() {
		// TODO Auto-generated method stub
        map.put("busiTypeCode","02");
        map.put("subBusiType","00");
		return mapper.selectByPK(map);
	}

	@Override
	public FdBusinessDate getCurrencyFdBusinessDate() {
		// TODO Auto-generated method stub
        map.put("busiTypeCode","02");
        map.put("subBusiType","01");
		return mapper.selectByPK(map);
	}
	/**
	 * TODO 数据字典重置.
	 */
	@Override
	public void RestartParam() {
		dicCache.start();
	}
}