package com.boco.SYS.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebMenuInfo;
import com.boco.SYS.entity.WebRoleFun;
/**
 * 
 * 
 * 菜单表数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebMenuInfoMapper extends GenericMapper<WebMenuInfo,java.lang.String>{
	
	/**
	 * 
	 *
	 * TODO 查询柜员菜单.
	 *
	 * @param map
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月2日    	    杨滔    新建
	 * </pre>
	 */
	public List<WebMenuInfo> selectRoleFuns(Map<String,Object> map) throws DataAccessException;
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
	public List<WebMenuInfo> selectMenuByAttr(Map<String,Object> map) throws DataAccessException;
	/**
	 * 
	 *
	 * TODO 查询菜单详细信息.
	 *
	 * @param map
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月2日    	    杨滔    新建
	 * </pre>
	 */
	public WebMenuInfo selectByPKInfo(WebMenuInfo webMenuInfo) throws DataAccessException;
	/**
	 * 
	 *
	 * TODO 根据上级菜单编号查询菜单编号及排序编号.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月2日    	  杜旭    新建
	 * </pre>
	 */
	public Map selectMenuInfo(String upMenuNo);
	/**
	 * 
	 *
	 * TODO 查询菜单编号及排序编号.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月2日    	  杜旭    新建
	 * </pre>
	 */
	public String selectMenuOrder();
	/**
	 * 
	 *
	 * TODO 查询菜单编号对应的所有下级菜单信息，包括按钮信息.
	 *
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
	 * TODO 根据柜员角色查询柜员可以查看的报表
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