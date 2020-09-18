package com.boco.SYS.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebMenuInfo;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * 菜单表业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebMenuInfoService extends IGenericService<WebMenuInfo,java.lang.String>{
	public WebMenuInfo selectByPKInfo(WebMenuInfo webMenuInfo) throws DataAccessException;
	/**
	 * 
	 *
	 * TODO 查询某个角色的功能集合.
	 *
	 * @param opercode
	 * @param menuType
	 * @param parentId
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月2日    	    杨滔    新建
	 * </pre>
	 */
	public List<WebMenuInfo> selectRoleFuns(String opercode, String menuType, String menuStatus) throws RuntimeException;
	
	/**
	 * 
	 *
	 * TODO 查询某个用户的菜单集.
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
	public List<WebMenuInfo> selectRolesMenus(String opercode) throws RuntimeException;
	
	/**
	 * 
	 *
	 * TODO 查询某个用户的某个菜单下的按钮集.
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
	public List<WebMenuInfo> selectRolesBtns(String opercode, String parentId) throws RuntimeException;
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
	public Json insertWebMenuInfo(WebMenuInfo webMenuInfo,FdOper fdOper) throws Exception;
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
	public Json updateWebMenuInfo(WebMenuInfo webMenuInfo, FdOper fdOper) throws Exception;
	/**
	 * 
	 *
	 * TODO 删除菜单.
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
	public Json deleteWebMenuInfo(WebMenuInfo webMenuInfo) throws Exception;
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
	public List<WebMenuInfo> selectByNo(Map<String,Object> map);
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
	public List<WebMenuInfo> selectMenuByRole(List<String> roleCode);
	/**
	 * 
	 *
	 * TODO 将角色字符直接传到sql中进行查询.
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月8日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebMenuInfo> selectMenuByAttr(String roleCodes) throws DataAccessException;
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
	public List<WebMenuInfo> selectBtnByAttr(String roleCodes, String parentId) throws DataAccessException;
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
	public List<WebMenuInfo> selectShortMenus(String opercode);
	
	
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
	public List<WebMenuInfo> selectReportMenuByRole(List<String> roleCode);
}