package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebApproveModel;
/**
 * 
 * 
 * WebApproveModel数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebApproveModelMapper extends GenericMapper<WebApproveModel,java.lang.String>{
	/**
	 *  查询常见意见是否已存在
	 *
	 * TODO 请输入你的方法说明.
	 *
	 * @param appCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月9日    	  杜旭    新建
	 * </pre>
	 */
	public Integer countByExist(WebApproveModel webApproveModel);
}