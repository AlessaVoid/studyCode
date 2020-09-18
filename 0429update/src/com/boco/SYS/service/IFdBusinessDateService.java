package com.boco.SYS.service;

import java.util.HashMap;

import com.boco.SYS.entity.FdBusinessDate;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * FdBusinessDate业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      李海波     批量新建
 * </pre>
 */
public interface IFdBusinessDateService extends IGenericService<FdBusinessDate,HashMap<String,Object>>{
    /**
     * 普通业务时间
     * @return
     */
    public String getCommonDateDetails();

    /**
     * 货币型产品时间
     * @return
     */
    public String getCurrencyDateDetails();
    /**
     * 获得开闭市状态标识
     * @return
     */
    public String getBusinessFlag();
    /**
     * 获取公共时间对象
     */
    public FdBusinessDate getCommonFdBusinessDate();
    /**
     * 获取货币型产品时间对象
     */
    public FdBusinessDate getCurrencyFdBusinessDate();
    /**
	 * TODO 数据字典重置.
	 *
	 */
	public void RestartParam() ;
}