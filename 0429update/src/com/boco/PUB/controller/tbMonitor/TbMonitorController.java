package com.boco.PUB.controller.tbMonitor;
import java.util.List;
import java.util.Map;

import com.boco.SYS.service.TbMonitorService;
import com.boco.SYS.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import com.boco.SYS.entity.TbMonitor;

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
@RequestMapping(value = "/tbMonitor/")
public class TbMonitorController extends BaseController{
	@Autowired
	private TbMonitorService tbMonitorService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="服务数监控",funCode="SYS-11",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		List<Map<String, Object>> monitorList = tbMonitorService.selectRunCount();
		setAttribute("monitorList", monitorList);
		return basePath + "/PUB/tbMonitor/tbMonitorIndex";
	}

	@RequestMapping("updateUI")
	@SystemLog(tradeName="服务数监控",funCode="SYS-11", funName="加载修改页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(TbMonitor tbMonitor) throws Exception {
		setAttribute("monitor", tbMonitorService.selectByPK(MapUtil.beanToMap(tbMonitor)));
		return basePath + "/PUB/tbMonitor/tbMonitorEdit";
	}

	@RequestMapping(value = "update")
	@SystemLog(tradeName="服务数监控",funCode="SYS-11",funName="修改",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(TbMonitor tbMonitor, HttpSession session) throws Exception{
		tbMonitorService.updateByPK(tbMonitor);
		return this.json.returnMsg("true", "修改成功!").toJson();
	}


	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="服务数监控",funCode="SYS-11",funName="加载列表数据",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String findPage(TbMonitor tbMonitor) throws Exception {
		String sortColumn = "mo_ip";
		String sort = getParameter("sort");
		String direction = getParameter("direction");
		if (sort != null) {
			sort = StringUtil.toUnderLine(sort) + " " + direction;
			sortColumn =  sort;
		}

		tbMonitor.setSortColumn(sortColumn);
		setPageParam();
		List<TbMonitor> list = tbMonitorService.selectByAttr(tbMonitor);
		return pageData(list);
	}
	

}