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
 * Action���Ʋ�
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbMonitorFile/")
public class TbMonitorFileController extends BaseController{
	@Autowired
	private TbMonitorFileService tbMonitorFileService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="��������",funCode="δ��д",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PUB/tbMonitorFile/tbMonitorFileIndex";
	}

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="��������",funCode="δ��д",funName="�����б�����",accessType=AccessType.READ,level=Debug.DEBUG)
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