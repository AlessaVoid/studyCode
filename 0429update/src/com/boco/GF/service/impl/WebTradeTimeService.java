package com.boco.GF.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.boco.SYS.service.IFdBusinessDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.GF.service.IWebTradeTimeService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebTradeTime;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.WebTradeTimeMapper;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebTradeTime业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class WebTradeTimeService extends GenericService<WebTradeTime,java.lang.String> implements IWebTradeTimeService{

	@Autowired
	private WebTradeTimeMapper webTradeTimeMapper;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	
	/**
	 * 
	 *
	 * TODO 修改交易时间节点信息.
	 *
	 * @param webTradeTime
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月1日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json updateWebTradeTime(WebTradeTime webTradeTime,FdOper fdOper) throws Exception {
		//校验失败
		checkUpdateData(webTradeTime);
		//校验成功
		webTradeTime.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
		webTradeTime.setLatestModifyTime(BocoUtils.getNowTime());
		webTradeTime.setLatestOperCode(fdOper.getOpercode());
		int count = updateByPK(webTradeTime);
		if(count != 1){
			throw new SystemException("修改交易时间节点信息失败");
		}
		return json.returnMsg("true", "修改成功");
	}
	/**
	 * 
	 *
	 * TODO 修改校验规则.
	 *
	 * @param webTradeTime
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月1日    	  杜旭    新建
	 * </pre>
	 */
	public void checkUpdateData(WebTradeTime webTradeTime){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String currentDate = fdBusinessDateService.getCommonDateDetails() + " " + sdf.format(new Date());//当前日期
		String beginTime = webTradeTime.getBeginTime().replace(":", "");
		if(beginTime.compareTo(currentDate.replace(":", "")) < 0){
			//开始时间应大于等于当前时间
			//{0}应大于等于{1}
			throw new SystemException("w3B4","开始时间","当前时间,当前时间："+currentDate);
		}
		String endTime = webTradeTime.getEndTime().replace(":", "");
		if(endTime.compareTo(currentDate.replace(":", "")) < 0){
			//终止时间应大于等于当前时间
			//{0}应大于等于{1}
			throw new SystemException("w3B4","终止时间","当前时间,当前时间："+currentDate);
		}
		//验证终止时间大于等于开始时间
		if(webTradeTime.getBeginTime() != null && !"".equals(webTradeTime.getBeginTime())
				&& webTradeTime.getEndTime() != null && !"".equals(webTradeTime.getEndTime())){
			if(webTradeTime.getEndTime().compareTo(webTradeTime.getBeginTime()) < 0){
				//开始时间应小于等于终止时间
				//{0}应小于等于{1}
				throw new SystemException("w3B7","开始时间","终止时间");
			}
		}
	}
	@Override
	public void reSetTradeTime(WebTradeTime webTradeTime) {
		webTradeTimeMapper.reSetTradeTime(webTradeTime.getMenuCode());
	}
}