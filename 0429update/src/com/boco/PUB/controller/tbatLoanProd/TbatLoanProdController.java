package com.boco.PUB.controller.tbatLoanProd;

import com.boco.PUB.service.tbatLoanProd.TbatLoanProdService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbatLoanProd;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
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
@RequestMapping(value = "/tbatLoanProd/")
public class TbatLoanProdController extends BaseController{
	@Autowired
	private TbatLoanProdService tbatLoanProdService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="��������",funCode="SYS-08",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PUB/tbatLoanProd/tbatLoanProdList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="��������",funCode="SYS-08",funName="������ϸҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(TbatLoanProd tbatLoanProd) throws Exception {
		setAttribute("entity", tbatLoanProdService.selectByPK(MapUtil.beanToMap(tbatLoanProd)));
		return basePath + "/PUB/tbatLoanProd/tbatLoanProdInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="��������",funCode="SYS-08", funName="��������ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/PUB/tbatLoanProd/tbatLoanProdAdd";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="��������",funCode="SYS-08", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(TbatLoanProd tbatLoanProd) throws Exception {
		setAttribute("entity", tbatLoanProdService.selectByPK(MapUtil.beanToMap(tbatLoanProd)));
		return basePath + "/PUB/tbatLoanProd/tbatLoanProdEdit";
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯt_bat_loan_prod��ҳ����
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="��������",funCode="SYS-08",funName="�����б�����",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String findPage(TbatLoanProd tbatLoanProd) throws Exception {
		setPageParam();
		// List<TbatLoanProd> list = tbatLoanProdService.selectByAttr(tbatLoanProd);
		List<TbatLoanProd> list = tbatLoanProdService.selectAll(tbatLoanProd);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO ����t_bat_loan_prod.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="��������",funCode="SYS-08",funName="����",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(TbatLoanProd tbatLoanProd, HttpSession session) throws Exception{
		tbatLoanProdService.insertEntity(tbatLoanProd);
		return this.json.returnMsg("true", "�����ɹ�!").toJson();
	}
	
	/**
	 * 
	 *
	 * TODO �޸�t_bat_loan_prod.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="��������",funCode="SYS-08",funName="�޸�",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(TbatLoanProd tbatLoanProd, HttpSession session) throws Exception{
		tbatLoanProdService.updateByPK(tbatLoanProd);
		return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
	}

	/**
	 * 
	 *
	 * TODO ɾ��t_bat_loan_prod
	 *  
	 *  @return
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="��������",funCode="SYS-08",funName="ɾ��",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(TbatLoanProd tbatLoanProd, HttpSession session) throws Exception {
		tbatLoanProdService.deleteByPK(MapUtil.beanToMap(tbatLoanProd));
		return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
	}
}