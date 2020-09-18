package com.boco.PUB.controller.tbEurekaStatus;

import com.boco.PUB.service.tbEurekaStatus.TbEurekaStatusService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbEurekaStatus;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
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
@RequestMapping(value = "/tbEurekaStatus/")
public class TbEurekaStatusController extends BaseController{
	@Autowired
	private TbEurekaStatusService tbEurekaStatusService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="��������",funCode="δ��д",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PUB/tbEurekaStatus/tbEurekaStatusIndex";
	}

	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="��������",funCode="δ��д",funName="�����б�����",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String findPage(TbEurekaStatus tbEurekaStatus) throws Exception {
		String sortColumn = "";
		String sort = getParameter("sort");
		String direction = getParameter("direction");
		if (sort != null) {
			sort = StringUtil.toUnderLine(sort) + " " + direction;
			sortColumn =  sort;
		}

		tbEurekaStatus.setSortColumn(sortColumn);
		setPageParam();
		List<TbEurekaStatus> list = tbEurekaStatusService.selectByAttr(tbEurekaStatus);
		return pageData(list);
	}
	

}