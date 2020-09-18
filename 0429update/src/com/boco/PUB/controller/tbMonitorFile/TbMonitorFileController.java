package com.boco.PUB.controller.tbMonitorFile;
import java.util.List;

import com.boco.PUB.service.tbMonitorFile.TbMonitorFileService;
import com.boco.SYS.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import com.boco.SYS.entity.TbMonitorFile;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.util.JsonUtils;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.util.MapUtil;
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
@RequestMapping(value = "/tbMonitorFile/")
public class TbMonitorFileController extends BaseController{
	@Autowired
	private TbMonitorFileService tbMonitorFileService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="交易名称",funCode="未填写",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PUB/tbMonitorFile/tbMonitorFileIndex";
	}

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="交易名称",funCode="未填写",funName="加载列表数据",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String findPage(TbMonitorFile tbMonitorFile) throws Exception {
			String sortColumn = "file_date desc";
		String sort = getParameter("sort");
		String direction = getParameter("direction");
		if (sort != null) {
			sort = StringUtil.toUnderLine(sort) + " " + direction;
			sortColumn =  sort;
		}

		tbMonitorFile.setSortColumn(sortColumn);
		setPageParam();
		// List<TbMonitorFile> list = tbMonitorFileService.selectByAttr(tbMonitorFile);
		List<TbMonitorFile> list = tbMonitorFileService.selectTbMonitorFile(tbMonitorFile);
		return pageData(list);
	}
	

}