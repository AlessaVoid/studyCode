package com.boco.SYS.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebMenuInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.WebMenuInfoMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.service.IWebMenuInfoService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * 菜单表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class WebMenuInfoService extends GenericService<WebMenuInfo,java.lang.String> implements IWebMenuInfoService{
	//有自定义方法时使用
	@Autowired
	private WebMenuInfoMapper mapper;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	private BocoUtils bocoUtils = new BocoUtils();
	
	public WebMenuInfo selectByPKInfo(WebMenuInfo webMenuInfo) throws DataAccessException{
		return this.mapper.selectByPKInfo(webMenuInfo);
	}
	/**
	 * 
	 *
	 * TODO 新增菜单.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月22日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json insertWebMenuInfo(WebMenuInfo webMenuInfo,FdOper fdOper) throws Exception {
		String id = bocoUtils.getUUID();
		webMenuInfo.setId(id);
		//调用新增校验规则
		boolean check = checkInsertData(webMenuInfo);
		//验证失败
		if(check == false){
			return this.json;
		}
		//验证成功
		Map map = getMenuNoOrder(webMenuInfo);
		String menuNo = String.valueOf(map.get("menuNo"));
		String orderBy = String.valueOf(map.get("orderBy"));
			
		//如果是按钮，url按规则进行拼接
		if("2".equals(webMenuInfo.getMenuType())){
			String iconClass = webMenuInfo.getMenuIcon().replace(".gif", "");
			String method = getMethod(iconClass);
			String url = "";
			if(StringUtils.isNotEmpty(webMenuInfo.getMenuUrl())){
				url = "{text : '"+webMenuInfo.getMenuName()+"', click : " + webMenuInfo.getMenuUrl() + ", iconClass : 'icon_"+ iconClass +"'}";
			}else{
				url = "{text : '"+webMenuInfo.getMenuName()+"', click : " + method + ", iconClass : 'icon_"+ iconClass +"'}";
			}
			webMenuInfo.setMenuUrl(url);
			webMenuInfo.setIsParent("0");
		}
		
		if(!StringUtils.isNotEmpty(webMenuInfo.getMenuNo())){
			webMenuInfo.setMenuNo(menuNo);
		}
		if(!StringUtils.isNotEmpty(webMenuInfo.getOrderNo())){
			webMenuInfo.setOrderNo(orderBy);
		}
		if("1".equals(webMenuInfo.getIsParent())){
			if(!StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
				webMenuInfo.setUpMenuNo("0");
			}
		}
		webMenuInfo.setMenuIcon("/libs/icons/"+webMenuInfo.getMenuIcon());
		webMenuInfo.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
		webMenuInfo.setLatestModifyTime(BocoUtils.getNowTime());
		int count = insertEntity(webMenuInfo);
		//插入数据库失败
		if(count != 1){
			throw new SystemException("新增失败!");
		}
		return this.json.returnMsg("true", "新增成功!");
	}
	/**
	 * 
	 *
	 * TODO 获取按钮方法.
	 *
	 * @param iconClass
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月15日    	  杜旭    新建
	 * </pre>
	 */
	public String getMethod(String iconClass){
		String method = "";
		if(iconClass.equals("add")){
			method = "onInsert";
		}else if(iconClass.equals("list")){
			method = "onInfo";
		}else if(iconClass.equals("edit")){
			method = "onEdit";
		}else if(iconClass.equals("delete")){
			method = "onDelete";
		}else if(iconClass.equals("import")){
			method = "onImport";
		}else if(iconClass.equals("export")){
			method = "onExport";
		}
		return method;
	}
	/**
	 * 
	 *
	 * TODO 当按钮或者子菜单不输入菜单编码或者排序编号时自动获取菜单编码和排序编号.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月15日    	  杜旭    新建
	 * </pre>
	 */
	public Map getMenuNoOrder(WebMenuInfo webMenuInfo){
		BigDecimal orderBy = null;
		String menuNo = "";
		Map map = new HashMap();
		//获取上级菜单下的最大子菜单的编号和名称
		//菜单编码为空的时候
		if(!StringUtils.isNotEmpty(webMenuInfo.getMenuNo())){
			//上级菜单编码不为空，子菜单和按钮 获取菜单编码和排序编号
			if(StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
				map = mapper.selectMenuInfo(webMenuInfo.getUpMenuNo());
				if(map != null && map.size() != 0){
					String menu = String.valueOf(map.get("MENUNO"));
					if(StringUtils.isNotEmpty(menu)){
						if(menu.length() != 0){
							String[] tempMenuNos = menu.split("-");
							for(int i = 0; i < tempMenuNos.length; i++){
								if(i == tempMenuNos.length -1){
									String tempMenuNo = tempMenuNos[tempMenuNos.length-1];
									int countMenuNo = Integer.parseInt(tempMenuNo) + 1;
									if(countMenuNo < 10){
										menuNo += "0" + String.valueOf(countMenuNo);
									}else{
										menuNo += countMenuNo;
									}
								}else{
									menuNo += tempMenuNos[i] + "-";
								}
							}
						}
					}
					orderBy = new BigDecimal(String.valueOf(map.get("ORDERBY")));
					orderBy = orderBy.add(new BigDecimal("1"));
				}else{
					menuNo = webMenuInfo.getUpMenuNo() + "-01";
					WebMenuInfo webMenu = new WebMenuInfo();
					webMenu.setMenuNo(webMenuInfo.getUpMenuNo());
					List<WebMenuInfo> webMenuList = mapper.selectByAttr(webMenu);
					if(webMenuList != null && webMenuList.size() != 0){
						Map menuMap = mapper.selectMenuInfo(webMenuList.get(0).getUpMenuNo());
						if(menuMap != null && menuMap.size() != 0){
							orderBy = new BigDecimal(String.valueOf(menuMap.get("ORDERBY")));
							orderBy = orderBy.add(new BigDecimal("1"));
						}
					}
				}
			}
		}
		//排序编号，获取排序编号
		if(!StringUtils.isNotEmpty(webMenuInfo.getOrderNo())){
			if(!StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
				String orderNo = mapper.selectMenuOrder();
				orderBy = new BigDecimal(orderNo);
				orderBy = orderBy.add(new BigDecimal("1"));
			}
		}
		
		Map resultMap = new HashMap();
		resultMap.put("menuNo", menuNo);
		resultMap.put("orderBy", orderBy);
		
		return resultMap;
	}
	/**
	 * 
	 *
	 * TODO 修改菜单.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月22日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json updateWebMenuInfo(WebMenuInfo webMenuInfo, FdOper fdOper) throws Exception {
		checkUpdateData(webMenuInfo);
		
		Map map = getMenuNoOrder(webMenuInfo);
		String menuNo = String.valueOf(map.get("menuNo"));
		String orderBy = String.valueOf(map.get("orderBy"));
		//如果是按钮，url按规则进行拼接
		if("2".equals(webMenuInfo.getMenuType())){
			String iconClass = webMenuInfo.getMenuIcon().replace(".gif", "");
			String method = getMethod(iconClass);
			String url = "";
			if(StringUtils.isNotEmpty(webMenuInfo.getMenuUrl())){
				url = "{text : '"+webMenuInfo.getMenuName()+"', click : " + webMenuInfo.getMenuUrl() + ", iconClass : 'icon_"+ iconClass +"'}";
			}else{
				url = "{text : '"+webMenuInfo.getMenuName()+"', click : " + method + ", iconClass : 'icon_"+ iconClass +"'}";
			}
			webMenuInfo.setMenuUrl(url);
			webMenuInfo.setIsParent("0");
		}
		if(!StringUtils.isNotEmpty(webMenuInfo.getMenuNo())){
			webMenuInfo.setMenuNo(menuNo);
		}
		if(!StringUtils.isNotEmpty(webMenuInfo.getOrderNo())){
			webMenuInfo.setOrderNo(orderBy);
		}
		if("1".equals(webMenuInfo.getIsParent())){
			if(!StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
				webMenuInfo.setUpMenuNo("0");
			}
		}
		webMenuInfo.setMenuIcon("/libs/icons/"+webMenuInfo.getMenuIcon());
		webMenuInfo.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
		webMenuInfo.setLatestModifyTime(BocoUtils.getNowTime());
		int count = updateByPK(webMenuInfo);
		//插入数据库失败
		if(count != 1){
			throw new SystemException("修改失败!");
		}
		return this.json.returnMsg("true", "修改成功!");
	}
	/**
	 * 
	 *
	 * TODO 菜单删除.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月22日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json deleteWebMenuInfo(WebMenuInfo webMenuInfo) throws Exception {
		//调用修改校验规则
		boolean check = checkDeleteData(webMenuInfo);
		//验证失败
		if(check == false){
			return this.json;
		}
		//验证成功
		webMenuInfo.setMenuStatus("2");
		int count = updateByPK(webMenuInfo);
		//修改数据库失败
		if(count != 1){
			throw new SystemException("删除失败!");
		}
		return this.json.returnMsg("true", "删除成功!");
	}
	
	/**
	 * 
	 *
	 * TODO 菜单新增验证规则.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月22日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkInsertData(WebMenuInfo webMenuInfo) throws Exception{
		//验证输入的菜单信息在数据库中是否已存在
		WebMenuInfo exist = new WebMenuInfo();
		exist.setId(webMenuInfo.getId());
		int existCount = countByAttr(exist);
		if(existCount > 0){
			throw new SystemException("菜单信息已存在，请重新输入!");
		}
		//如果父节点，菜单编码必须输入，如果是子节点，可以不输入菜单编码，上级菜单编码必须输入
		//父节点
		if("1".equals(webMenuInfo.getIsParent())){
			if(!StringUtils.isNotEmpty(webMenuInfo.getMenuNo())){
				String errInfo = getErrorInfo("w314");
				throw new SystemException(errInfo);
			}
		}else{//子节点
			if(!StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
				throw new SystemException(getErrorInfo("w315"));
			}
		}
		//验证输入的菜单名称是否存在
		if("1".equals(webMenuInfo.getMenuType())){
			WebMenuInfo existMenuName= new WebMenuInfo();
			existMenuName.setMenuName(webMenuInfo.getMenuName());
			int menuNameCount = countByAttr(existMenuName);
			if(menuNameCount > 0){
				throw new SystemException("菜单名称已存在，请重新输入!");
			}
		}else if("2".equals(webMenuInfo.getMenuType())){
			WebMenuInfo existMenuName= new WebMenuInfo();
			existMenuName.setMenuName(webMenuInfo.getMenuName());
			existMenuName.setUpMenuNo(webMenuInfo.getUpMenuNo());
			int menuNameCount = countByAttr(existMenuName);
			if(menuNameCount > 0){
				throw new SystemException("菜单名称已存在，请重新输入!");
			}
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO 删除校验规则.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月22日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	private boolean checkDeleteData(WebMenuInfo webMenuInfo) throws Exception {
		//验证该菜单下是否有子菜单
		WebMenuInfo exist = new WebMenuInfo();
		exist.setUpMenuNo(webMenuInfo.getMenuNo());
		//菜单类型为菜单
		exist.setMenuType("1");
		int existCount = countByAttr(exist);
		if(existCount > 0){
			throw new SystemException("该菜单下有子菜单，无法删除!");
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO 查询某个角色的功能集合.
	 *
	 * @param roleCode 角色代码
	 * @param menuType 查询菜单或按钮
	 * @param parentId 父节点
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月2日    	    杨滔    新建
	 * </pre>
	 */
	public List<WebMenuInfo> selectRoleFuns(String opercode, String menuType, String parentId) throws RuntimeException{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("menuStatus", "1");//状态:1-正常
		map.put("menuType", menuType);//类型：1-菜单,2-按钮，3-功能
		map.put("opercode", opercode);
		//查子功能
		if(StringUtils.isNotBlank(parentId)){
			map.put("upMenuNo", parentId);
		}
		return mapper.selectRoleFuns(map);
	}
	
	/**
	 * 
	 *
	 * TODO 查询某个用户的菜单集.
	 *
	 * @param opercode
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月2日    	    杨滔    新建
	 * </pre>
	 */
	public List<WebMenuInfo> selectRolesMenus(String opercode) throws RuntimeException{
		//用菜单编号做Map的key，防止多个角色有共同权限出现重复菜单的情况
		return this.selectRoleFuns(opercode, "1", null);//查菜单
	}
	
	/**
	 * 
	 *
	 * TODO 查询某个用户的某个菜单下的按钮集.
	 *
	 * @param opercode
	 * @param parentId
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月2日    	    杨滔    新建
	 * </pre>
	 */
	public List<WebMenuInfo> selectRolesBtns(String opercode, String parentId) throws RuntimeException{
		return this.selectRoleFuns(opercode, "2", parentId);//查按钮
	}
	/**
	 * 
	 *
	 * TODO 查询菜单编号对应的所有下级菜单信息，包括按钮信息.
	 *
	 * @param upMenuNo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月3日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebMenuInfo> selectByNo(Map<String,Object> map){
		return mapper.selectByNo(map);
	}
	/**
	 * 
	 *
	 * TODO 根据柜员角色查询柜员拥有的功能(动态生成where条件中的or).
	 *
	 * @param roleCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 */
	@Override
	public List<WebMenuInfo> selectMenuByRole(List<String> roleCode) {
		return mapper.selectMenuByRole(roleCode);
	}
	/**
	 * 
	 *
	 * TODO 将角色字符直接传到sql中进行查询.
	 *
	 * @param map
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月8日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebMenuInfo> selectMenuByAttr(String roleCodes) throws DataAccessException{
		return this.selectMenuByAttr(roleCodes, "1", null);//查菜单
	}
	/**
	 * 
	 *
	 * TODO 将角色字符直接传到sql中进行查询.
	 *
	 * @param map
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月8日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebMenuInfo> selectBtnByAttr(String roleCodes, String parentId) throws DataAccessException{
		return this.selectMenuByAttr(roleCodes, "2", parentId);//查菜单
	}
	/**
	 * 
	 *
	 * TODO 根据角色获取菜单信息.
	 *
	 * @param roleCodes
	 * @param menuType
	 * @param parentId
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月8日    	  杜旭    新建
	 * </pre>
	 */
	private List<WebMenuInfo> selectMenuByAttr(String roleCodes,String menuType, String parentId) throws RuntimeException{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("menuStatus", "1");//状态:1-正常
		map.put("menuType", menuType);//类型：1-菜单
		map.put("roleCodes", roleCodes);
		//查子功能
		if(StringUtils.isNotBlank(parentId)){
			map.put("upMenuNo", parentId);
		}
		return mapper.selectMenuByAttr(map);
	}
	/**
	 * 
	 *
	 * TODO 获取快捷菜单.
	 *
	 * @param roleCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebMenuInfo> selectShortMenus(String opercode){
		return mapper.selectShortMenus(opercode);
	}
	/**
	 * 
	 *
	 * TODO 修改校验规则.
	 *
	 * @param webMenuInfo
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月15日    	  杜旭    新建
	 * </pre>
	 */
	public void checkUpdateData(WebMenuInfo webMenuInfo){
		//如果父节点，菜单编码必须输入，如果是子节点，可以不输入菜单编码，上级菜单编码必须输入
		//父节点
		if("1".equals(webMenuInfo.getIsParent())){
			if(!StringUtils.isNotEmpty(webMenuInfo.getMenuNo())){
				throw new SystemException("请输入菜单编码");
			}
		}else{//子节点
			if(!StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
				throw new SystemException("请输入上级菜单编码");
			}
		}
	}
	
	/**
	 * 
	 *
	 * TODO 根据柜员角色查询柜员可以查看的报表.
	 *
	 * @param roleCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月10日    	  李沐阳    新建
	 * </pre>
	 */
	@Override
	public List<WebMenuInfo> selectReportMenuByRole(List<String> roleCode) {
		// TODO Auto-generated method stub
		return  mapper.selectReportMenuByRole(roleCode);
	}
}