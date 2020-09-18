package com.boco.PUB.controller.tbTransSeq;

import com.boco.PUB.service.tbTransSeq.TbTransSeqService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbTransSeq;
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
 * Action���Ʋ�
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbTransSeq/")
public class TbTransSeqController extends BaseController{
	@Autowired
	private TbTransSeqService tbTransSeqService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="��������",funCode="SYS-08",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PUB/tbTransSeq/tbTransSeqIndex";
	}

	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listTimeUI")
	@SystemLog(tradeName="��������",funCode="SYS-08",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listTimeUI() throws Exception {
		authButtons();
		return basePath + "/PUB/tbTransSeq/tbTransSeqTimeIndex";
	}




	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="��������",funCode="SYS-08",funName="�����б�����",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String findPage(TbTransSeq tbTransSeq) throws Exception {

        String sortColumn = "trans_date desc,trans_time desc";
        String sort = getParameter("sort");
        String direction = getParameter("direction");
        if (sort != null) {
            sort = StringUtil.toUnderLine(sort) + " " + direction;
            sortColumn =  sort;
        }

        tbTransSeq.setSortColumn(sortColumn);
		setPageParam();
		// List<TbTransSeq> list = tbTransSeqService.selectByAttr(tbTransSeq);
		List<TbTransSeq> list = tbTransSeqService.selectTbTransSeq(tbTransSeq);

		return pageData(list);
	}



}