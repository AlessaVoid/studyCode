package com.boco.PUB.controller.tbLoanInfo;

import com.boco.PUB.service.tbLoanInfo.TbLoanInfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbLoanInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.util.MapUtil;
import com.boco.SYS.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 
 * 
 * Action控制层
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbLoanInfo/")
public class TbLoanInfoController extends BaseController{
	@Autowired
	private TbLoanInfoService tbLoanInfoService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="交易名称",funCode="未填写",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PUB/tbLoanInfo/tbLoanInfoIndex";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="交易名称",funCode="未填写",funName="加载详细页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(TbLoanInfo tbLoanInfo) throws Exception {
		setAttribute("entity", tbLoanInfoService.selectByPK(MapUtil.beanToMap(tbLoanInfo)));
		return basePath + "/PUB/tbLoanInfo/tbLoanInfoDetail";
	}

	

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="交易名称",funCode="未填写",funName="加载列表数据",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String findPage(TbLoanInfo tbLoanInfo) throws Exception {


		String sort = getParameter("sort");
		String direction = getParameter("direction");
		if (sort != null) {
			sort = StringUtil.toUnderLine(sort) + " " + direction;
			tbLoanInfo.setSortColumn(sort);
		}
		setPageParam();
		List<TbLoanInfo> list = tbLoanInfoService.selectByAttr(tbLoanInfo);
		return pageData(list);
	}



}