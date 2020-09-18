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
 * Action控制层
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbatLoanProd/")
public class TbatLoanProdController extends BaseController{
	@Autowired
	private TbatLoanProdService tbatLoanProdService;
	
	//访问列表、详细信息、新增、修改页面
	@RequestMapping("listUI")
	@SystemLog(tradeName="交易名称",funCode="SYS-08",funName="加载列表页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PUB/tbatLoanProd/tbatLoanProdList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="交易名称",funCode="SYS-08",funName="加载详细页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String infoUI(TbatLoanProd tbatLoanProd) throws Exception {
		setAttribute("entity", tbatLoanProdService.selectByPK(MapUtil.beanToMap(tbatLoanProd)));
		return basePath + "/PUB/tbatLoanProd/tbatLoanProdInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="交易名称",funCode="SYS-08", funName="加载新增页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/PUB/tbatLoanProd/tbatLoanProdAdd";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="交易名称",funCode="SYS-08", funName="加载修改页面",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(TbatLoanProd tbatLoanProd) throws Exception {
		setAttribute("entity", tbatLoanProdService.selectByPK(MapUtil.beanToMap(tbatLoanProd)));
		return basePath + "/PUB/tbatLoanProd/tbatLoanProdEdit";
	}
	
	/**
	 * 
	 *
	 * TODO 查询t_bat_loan_prod分页数据
	 * 
	 * @return 
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="交易名称",funCode="SYS-08",funName="加载列表数据",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String findPage(TbatLoanProd tbatLoanProd) throws Exception {
		setPageParam();
		// List<TbatLoanProd> list = tbatLoanProdService.selectByAttr(tbatLoanProd);
		List<TbatLoanProd> list = tbatLoanProdService.selectAll(tbatLoanProd);
		return pageData(list);
	}
	
	/**
	 * 
	 *
	 * TODO 新增t_bat_loan_prod.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="交易名称",funCode="SYS-08",funName="新增",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(TbatLoanProd tbatLoanProd, HttpSession session) throws Exception{
		tbatLoanProdService.insertEntity(tbatLoanProd);
		return this.json.returnMsg("true", "新增成功!").toJson();
	}
	
	/**
	 * 
	 *
	 * TODO 修改t_bat_loan_prod.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月18日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="交易名称",funCode="SYS-08",funName="修改",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(TbatLoanProd tbatLoanProd, HttpSession session) throws Exception{
		tbatLoanProdService.updateByPK(tbatLoanProd);
		return this.json.returnMsg("true", "修改成功!").toJson();
	}

	/**
	 * 
	 *
	 * TODO 删除t_bat_loan_prod
	 *  
	 *  @return
	 *
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2014-10-29      杨滔      批量新建
	 * </pre>
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="交易名称",funCode="SYS-08",funName="删除",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(TbatLoanProd tbatLoanProd, HttpSession session) throws Exception {
		tbatLoanProdService.deleteByPK(MapUtil.beanToMap(tbatLoanProd));
		return this.json.returnMsg("true", "删除成功!").toJson();
	}
}