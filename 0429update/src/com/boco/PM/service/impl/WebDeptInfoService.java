package com.boco.PM.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.PM.service.IWebDeptInfoService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.WebDeptInfo;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.WebDeptInfoMapper;
import com.boco.SYS.mapper.WebOperInfoMapper;
import com.boco.SYS.util.Json;
import com.boco.SYS.util.MapUtil;

/**
 * 
 * 
 * WebDeptInfo业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class WebDeptInfoService extends GenericService<WebDeptInfo,HashMap<String,Object>> implements IWebDeptInfoService{
	
	@Autowired
	private WebDeptInfoMapper deptInfoMapper;
	@Autowired
	private WebDeptInfoMapper webDeptInfoMapper;
	@Autowired
	private WebOperInfoMapper webOperInfoMapper;
	private Json json = new Json();
	/**
	 * 
	 *
	 * TODO 查询列表信息.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月12日    	    秦海舟   新建
	 * </pre>
	 */
	@Override
	public List<WebDeptInfo> select(WebDeptInfo webDeptInfo) {
			return deptInfoMapper.select(webDeptInfo);
	}
	
	/**
	 * 
	 *
	 * TODO 新增.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月12日    	    秦海舟   新建
	 * </pre>
	 */
	@Override
	public Json insert(WebDeptInfo webDeptInfo) throws Exception {
		if("".equals(webDeptInfo.getUpDeptCode())){
			webDeptInfo.setUpDeptCode("0");
		}
		String deptCode = webDeptInfo.getDeptCode();
		String dept = "";
		for(int i=deptCode.length();i<3;i++){
			dept = dept + "0";
		}
		deptCode = dept + deptCode;
		webDeptInfo.setDeptCode(deptCode);
		//调用新增校验规则
		boolean check = checkInsertData(webDeptInfo);
		//验证失败
		if(check == false){
			return this.json ;
		}
		int count = insertEntity(webDeptInfo);
		//插入数据库失败
		if(count != 1){
			//新增失败。
			return this.json.returnMsg("false", getErrorInfo("w446"));
		}//新增成功!  部门代码为："+list.get(0).getDeptCode()
		return this.json.returnMsg("true", getErrorInfo("w445",webDeptInfo.getDeptCode()));
		
		
	}
	/**
	 * 
	 *
	 * TODO 修改.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月12日    	    秦海舟   新建
	 * </pre>
	 */
	@Override
	public Json update(WebDeptInfo webDeptInfo) throws Exception {
		if("".equals(webDeptInfo.getUpDeptCode())){
			webDeptInfo.setUpDeptCode("0");
		}
		//调用新增校验规则
		boolean check = checkUpdateData(webDeptInfo);
		//验证失败
		if(check == false){
			return this.json ;
		}
		int count = updateByPK(webDeptInfo);
		//插入数据库失败
		if(count != 1){
			//修改失败!
			return this.json.returnMsg("false",getErrorInfo("w447"));
		}
		//修改成功！
		return this.json.returnMsg("true",getErrorInfo("w448"));
	}
	/**
	 * 
	 *
	 * TODO 修改验证.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月12日    	    秦海舟   新建
	 * </pre>
	 */
	private boolean checkUpdateData(WebDeptInfo webDeptInfo) throws Exception {
		//验证部门名称是否重复
		WebDeptInfo deptInfo = new WebDeptInfo();
		deptInfo.setUpDeptCode(webDeptInfo.getUpDeptCode());
		deptInfo.setDeptName(webDeptInfo.getDeptName());
		deptInfo.setOrgancode(webDeptInfo.getOrgancode());
		int MenuNoCount = countByAttr(deptInfo);
		if(MenuNoCount > 0){
			//"部门名称已重复，请重新输入!"
			this.json.returnMsg("false", getErrorInfo("w449"));
			return false;
		}
		//上级部门不能等于本级部门
		if(webDeptInfo.getDeptCode()==webDeptInfo.getUpDeptCode()||webDeptInfo.getDeptCode().equals(webDeptInfo.getUpDeptCode())){
			//"上级部门不能等于本级部门，请重新输入!"
			this.json.returnMsg("false", getErrorInfo("w450"));
			return false;
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO 新增验证.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月12日    	    秦海舟   新建
	 * </pre>
	 */
	private boolean checkInsertData(WebDeptInfo webDeptInfo) throws Exception {
		//验证部门名称是否重复
		WebDeptInfo deptInfo = new WebDeptInfo();
		deptInfo.setUpDeptCode(webDeptInfo.getUpDeptCode());
		deptInfo.setDeptName(webDeptInfo.getDeptName());
		deptInfo.setOrgancode(webDeptInfo.getOrgancode());
		int MenuNoCount = countByAttr(deptInfo);
		if(MenuNoCount > 0){
			this.json.returnMsg("false", getErrorInfo("w449"));
			return false;
		}
		
		return true;
	}
	/**
	 * 
	 *
	 * TODO 自增部门编码.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月12日    	    秦海舟   新建
	 * </pre>
	 */
	@Override
	public int max(WebDeptInfo webDeptInfo) {
			return  webDeptInfoMapper.max(webDeptInfo);
	}
	/**
	 * 
	 *
	 * TODO 删除.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月12日    	    秦海舟   新建
	 * </pre>
	 */
	@Override
	public Json delete(WebDeptInfo webDeptInfo) throws Exception {
		//调用删除校验规则
		boolean check = checkDeleteData(webDeptInfo);
		//验证失败
		if(check == false){
			return this.json ;
		}
		int count = deleteByPK(MapUtil.beanToMap(webDeptInfo));
		//插入数据库失败
		if(count != 1){
			//删除失败!"
			return this.json.returnMsg("false", getErrorInfo("w451"));
		}// "删除成功!"
		return this.json.returnMsg("true",getErrorInfo("w452"));
	}
	/**
	 * 
	 *
	 * TODO 删除验证.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月12日    	    秦海舟   新建
	 * </pre>
	 */
	private boolean checkDeleteData(WebDeptInfo webDeptInfo) throws Exception {
		//验证是否存在下级部门
		WebDeptInfo info = new WebDeptInfo();
		info.setUpDeptCode(webDeptInfo.getDeptCode());
		info.setOrgancode(webDeptInfo.getOrgancode());
		int MenuNoCount = countByAttr(info);
		if(MenuNoCount > 0){
			//"存在下级部门，请删除下级部门后在删除此部门!"
			this.json.returnMsg("false",getErrorInfo("w453") );
			return false;
		}
		//验证部门下是否有人员
		WebOperInfo oper = new WebOperInfo();
		oper.setOrganCode(webDeptInfo.getOrgancode());
		int count = webOperInfoMapper.countByAttr(oper);
		if(count > 0){
			// "此部门下存在人员，请删除此部门下的人员后在删除此部门!"
			this.json.returnMsg("false",getErrorInfo("w454"));
			return false;
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO 联想输入框（部门编码）.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月12日    	    秦海舟   新建
	 * </pre>
	 */
	@Override
	public List<Map<String, String>> selectCode(WebDeptInfo webDeptInfo) {
		List<Map<String, String>> list = webDeptInfoMapper.selectCode(webDeptInfo);
		return list;
	}
	/**
	 * 
	 *
	 * TODO 联想输入框（部门名称）.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月12日    	    秦海舟   新建
	 * </pre>
	 */
	@Override
	public List<Map<String, String>> selectName(WebDeptInfo webDeptInfo) {
		List<Map<String, String>> list = webDeptInfoMapper.selectName(webDeptInfo);
		return list;
	}
	/**
	 * 
	 *
	 * TODO 联想输入框（上级部门编码）.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月12日    	    秦海舟   新建
	 * </pre>
	 */
	@Override
	public List<Map<String, String>> selectUpCode(WebDeptInfo webDeptInfo) {
		List<Map<String, String>> list = webDeptInfoMapper.selectUpCode(webDeptInfo);
		return list;
	}
	/**
	 * 
	 *
	 * TODO 上级部门名称菜单树.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月12日    	    秦海舟   新建
	 * </pre>
	 */
	@Override
	public List<WebDeptInfo> selectUpDeptName(WebDeptInfo webDeptInfo) {
		return webDeptInfoMapper.selectUpDeptName(webDeptInfo);
	}
	
}