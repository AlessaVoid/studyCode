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
 * Action���Ʋ�
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbMonitor/")
public class TbMonitorController extends BaseController{
	@Autowired
	private TbMonitorService tbMonitorService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="���������",funCode="SYS-11",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		List<Map<String, Object>> monitorList = tbMonitorService.selectRunCount();
		setAttribute("monitorList", monitorList);
		return basePath + "/PUB/tbMonitor/tbMonitorIndex";
	}

	@RequestMapping("updateUI")
	@SystemLog(tradeName="���������",funCode="SYS-11", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(TbMonitor tbMonitor) throws Exception {
		setAttribute("monitor", tbMonitorService.selectByPK(MapUtil.beanToMap(tbMonitor)));
		return basePath + "/PUB/tbMonitor/tbMonitorEdit";
	}

	@RequestMapping(value = "update")
	@SystemLog(tradeName="���������",funCode="SYS-11",funName="�޸�",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(TbMonitor tbMonitor, HttpSession session) throws Exception{
		tbMonitorService.updateByPK(tbMonitor);
		return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
	}


	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="���������",funCode="SYS-11",funName="�����б�����",accessType=AccessType.READ,level=Debug.DEBUG)
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