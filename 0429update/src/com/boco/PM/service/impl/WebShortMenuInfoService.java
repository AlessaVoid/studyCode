package com.boco.PM.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.PM.service.IWebShortMenuInfoService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.WebShortMenuInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.WebShortMenuInfoMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebShortMenuInfo业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class WebShortMenuInfoService extends GenericService<WebShortMenuInfo,HashMap<String,Object>> implements IWebShortMenuInfoService{
	@Autowired
	private WebShortMenuInfoMapper webShortMenuInfoMapper;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	/**
	 * 
	 *
	 * TODO 维护快捷菜单信息.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 */
	@Override
	public Json updateWebShortMenuInfo(WebShortMenuInfo webShortMenuInfo,String funCode) throws Exception{
		webShortMenuInfoMapper.deleteByOperCode(webShortMenuInfo.getOperCode());
		if(StringUtils.isNotEmpty(funCode)){
			int funCount = funCode.split(",").length;
			insertBatchShortMenu(webShortMenuInfo,funCode);
		}
		return json.returnMsg("true", "保存成功");
	}
	/**
	 * 
	 *
	 * TODO 批量插入快捷菜单信息.
	 *
	 * @param funCode
	 * @param fdOper
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 */
	public void insertBatchShortMenu(WebShortMenuInfo webShortMenuInfo,String funCode)throws Exception{
		List<WebShortMenuInfo> list = new ArrayList<WebShortMenuInfo>();
		String operCode = webShortMenuInfo.getOperCode();
		if(StringUtils.isNotEmpty(funCode)){
			String[] funCodes = funCode.split(",");
			for(int i =0;i<funCodes.length;i++){
				WebShortMenuInfo shortMenu = new WebShortMenuInfo();
				shortMenu.setOperCode(operCode);
				shortMenu.setMenuCode(funCodes[i]);
				shortMenu.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
				shortMenu.setLatestModifyTime(BocoUtils.getNowTime());
				shortMenu.setLatestOperCode(operCode);
				int count = webShortMenuInfoMapper.insertEntity(shortMenu);
				if(count != 1){
					throw new SystemException("插入快捷菜单信息失败");
				}
			}
		}
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
	public List<WebShortMenuInfo> selectByRoleList(List<String> roleCode)throws Exception{
		return this.selectByRoleList(roleCode);
	}
	
	/**
	 * 
	 *
	 * TODO 查询菜单码.
	 *
	 * @param webShortMenuInfo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月21日    	     代策    新建
	 * </pre>
	 */
	@Override
	public List<String> selectMenuNoByAttr(WebShortMenuInfo webShortMenuInfo) throws Exception{
		
		return webShortMenuInfoMapper.selectMenuNoByAttr(webShortMenuInfo);
	}
	
	/**
	 * 
	 *
	 * TODO 查询所有父菜单.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月21日    	     代策    新建
	 * </pre>
	 */
	@Override
	public List<String> selectUpMenuNo() throws Exception{
		
		return webShortMenuInfoMapper.selectUpMenuNo();
	}
}