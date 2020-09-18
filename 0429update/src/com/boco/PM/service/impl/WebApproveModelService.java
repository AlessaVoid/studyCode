package com.boco.PM.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.PM.service.IWebApproveModelService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebApproveModel;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.WebApproveModelMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebApproveModel业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class WebApproveModelService extends GenericService<WebApproveModel,java.lang.String> implements IWebApproveModelService{
	@Autowired
	private WebApproveModelMapper webApproveModelMapper;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	/**
	 * 
	 *
	 * TODO 新增常用审批意见模板信息.
	 *
	 * @param webApproveModel
	 * @param fdOper
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月9日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public Json insertWebApproveMode(WebApproveModel webApproveModel,FdOper fdOper) throws Exception{
		boolean check = checkInsertData(webApproveModel);
		if(check == false){
			return json;
		}
		//校验成功，插入数据
		String appCode = BocoUtils.getUUID();
		webApproveModel.setAppCode(appCode);
		webApproveModel.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
		webApproveModel.setLatestModifyTime(BocoUtils.getNowTime());
		webApproveModel.setLatestOperCode(fdOper.getOpercode());
		int count = insertEntity(webApproveModel);
		if(count != 1){
			throw new SystemException(getErrorInfo("w301"));
		}
		return json.returnMsg("true", "新增成功");
	}
	/**
	 * 
	 *
	 * TODO 修改常用审批意见模板信息.
	 *
	 * @param webApproveModel
	 * @param fdOper
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月9日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json updateWebApproveMode(WebApproveModel webApproveModel,FdOper fdOper) throws Exception {
		boolean check = checkUpdateData(webApproveModel);
		if(check == false){
			return json;
		}
		//校验成功
		webApproveModel.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
		webApproveModel.setLatestModifyTime(BocoUtils.getNowTime());
		webApproveModel.setLatestOperCode(fdOper.getOpercode());
		updateByPK(webApproveModel);
		return json.returnMsg("true", "修改成功");
	}
	/**
	 * 
	 *
	 * TODO 新增校验规则.
	 *
	 * @param webApproveModel
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月9日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkInsertData(WebApproveModel webApproveModel) throws Exception {
		//验证审批意见的长度
		if(StringUtils.isNotEmpty(webApproveModel.getAppAdvice())){
			if(BocoUtils.getStrLength(webApproveModel.getAppAdvice(), 2) > 200){
				json.returnMsg("false", getErrorInfo("w302"));
				return false;
			}
		}
		//验证审批意见是否已存在
		int count = webApproveModelMapper.countByAttr(webApproveModel);
		if(count > 0){
			throw new SystemException("w303");
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO 修改校验规则.
	 *
	 * @param webApproveModel
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月9日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkUpdateData(WebApproveModel webApproveModel) throws Exception {
		//验证审批意见的长度
		if(StringUtils.isNotEmpty(webApproveModel.getAppAdvice())){
			if(BocoUtils.getStrLength(webApproveModel.getAppAdvice(), 2) > 200){
				json.returnMsg("false", getErrorInfo("w302"));
				return false;
			}
		}
		//验证审批意见是否已存在
		WebApproveModel exist = new WebApproveModel();
		exist.setAppCode(webApproveModel.getAppCode());
		exist.setAppAdvice(webApproveModel.getAppAdvice());
		int existCount = webApproveModelMapper.countByExist(exist);
		if(existCount > 0){
			json.returnMsg("false", getErrorInfo("w303"));
			return false;
		}
		return true;
	}

}