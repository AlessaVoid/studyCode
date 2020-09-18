package com.boco.PM.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.PM.service.IWebRoleInfoService;
import com.boco.PM.service.IWebSublicenseInfoService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebRoleInfo;
import com.boco.SYS.entity.WebSublicenseInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.FdOperMapper;
import com.boco.SYS.mapper.WebSublicenseInfoMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;
import com.boco.SYS.util.WebContextUtil;

/**
 * 
 * 
 * WebSublicenseInfo业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class WebSublicenseInfoService extends GenericService<WebSublicenseInfo,java.lang.String> implements IWebSublicenseInfoService{
	@Autowired
	private WebSublicenseInfoMapper webSublicenseInfoMapper;
	@Autowired
	private FdOperMapper fdOperMapper;
	@Autowired
	private IWebRoleInfoService webRoleInfoService;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	private BocoUtils bocoUtils = new BocoUtils();
	private Json json = new Json();
	/**
	 * 
	 *
	 * TODO 维护转授权信息.
	 *
	 * @param webSublicenseInfo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月2日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json outWebSub(WebSublicenseInfo webSublicenseInfo, String roles) throws Exception {
		//校验
		boolean check = checkOutWebSub(webSublicenseInfo,roles);
		//校验未通过
		if(check == false){
			return json;
		}
		if(checkIsOperate(webSublicenseInfo,roles)){
			//校验通过
			//转出柜员拥有的角色
			String role = "";
			//获取转出柜员拥有的角色
			FdOper outFdOper = new FdOper();
			outFdOper.setOpercode(webSublicenseInfo.getOutOper());
			List<FdOper> operRoleList = fdOperMapper.selectByAttr(outFdOper);
			if(operRoleList.size() != 0){
				role = operRoleList.get(0).getOperdegree();
			}
			if(StringUtils.isNotEmpty(webSublicenseInfo.getInOper())){
				//插入转出信息
				if(StringUtils.isNotEmpty(roles)){
					//获得转出的角色代码
					String[] outRole = roles.split(",");
					for(int i=0;i<outRole.length;i++){
						String roleCode = outRole[i];
						//从拥有的角色中去掉转出的角色，用于下面收回角色操作。
						role = role.replace(roleCode, "");
						String id = bocoUtils.getUUID();
						WebSublicenseInfo webSub = new WebSublicenseInfo();
						webSub.setId(id);
						webSub.setOutOper(webSublicenseInfo.getOutOper());
						webSub.setInOper(webSublicenseInfo.getInOper());
						webSub.setOutDate(fdBusinessDateService.getCommonDateDetails());
						webSub.setOutTime(BocoUtils.getNowTime());
						webSub.setIsBack("2");
						webSub.setRoleCode(roleCode);
						int count = insertEntity(webSub);
						if(count != 1){
							throw new SystemException("插入转授权信息失败");
						}
					}
				}
				//修改转出角色状态为收回
				//role：拥有的角色-转出的角色
				if(StringUtils.isNotEmpty(role)){
					for(int i=0;i<role.length();i=i+3){
						String roleCode = role.substring(i,i+3);
						WebSublicenseInfo webSub = new WebSublicenseInfo();
						webSub.setOutOper(webSublicenseInfo.getOutOper());
						webSub.setInOper(webSublicenseInfo.getInOper());
						webSub.setIsBack("2");
						webSub.setRoleCode(roleCode);
						//验证收回的角色在数据库中是否有对应的记录，如果有的话，进行收回操作，没有的话，跳过
						int selectCount = webSublicenseInfoMapper.countByAttr(webSub);
						if(selectCount > 0){
							webSub.setInDate(fdBusinessDateService.getCommonDateDetails());
							webSub.setInTime(BocoUtils.getNowTime());
							webSub.setIsBack("1");
							int updateCount = webSublicenseInfoMapper.updateByAttr(webSub);
							if(updateCount != 1){
								throw new SystemException("修改转授权信息失败");
							}
						}
					}
				}
			}else{//未指定转出柜员，将收回角色进行收回
				if(StringUtils.isNotEmpty(roles)){
					String[] outRole = roles.split(",");
					for(int i=0;i<outRole.length;i++){
						String roleCode = outRole[i];
						//从拥有的角色中去掉转出的角色，用于下面收回角色操作。
						role = role.replace(roleCode, "");
					}
				}
				for(int i=0;i<role.length();i=i+3){
					String roleCode = role.substring(i,i+3);
					WebSublicenseInfo sublicenseInfoAll = new WebSublicenseInfo();
					sublicenseInfoAll.setOutOper(webSublicenseInfo.getOutOper());
					sublicenseInfoAll.setRoleCode(roleCode);
					sublicenseInfoAll.setIsBack("1");
					sublicenseInfoAll.setInDate(fdBusinessDateService.getCommonDateDetails());
					sublicenseInfoAll.setInTime(BocoUtils.getNowTime());
					//收回该柜员转出的当前角色
					webSublicenseInfoMapper.backRoleAll(sublicenseInfoAll);
				}
			}
		}
		return json.returnMsg("true", "保存成功");
	}
	/**
	 * 
	 *
	 * TODO 转授权校验规则.
	 *
	 * @param webSublicenseInfo
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月2日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	private boolean checkOutWebSub(WebSublicenseInfo webSublicenseInfo, String roles) throws Exception {
		//校验是否是转出功能
		//查询当前登录员转出的角色
		//获取页面提交的转出角色
		//如果页面提交的转出角色 当前登录员转出的角色：收回 则转出柜员可以为空
		//如果页面提交的转出角色 >= 当前登录员转出的角色：转出 则转出柜员不能为空
		String rolesTemp = roles;
		WebSublicenseInfo outFdOperWsl = new WebSublicenseInfo();
		outFdOperWsl.setOutOper(WebContextUtil.getSessionUser().getOpercode());
		outFdOperWsl.setIsBack("2");
		List<WebSublicenseInfo> wslList = webSublicenseInfoMapper.selectByAttr(outFdOperWsl);//查询当前柜员已转出未收回的角色集合
		Map<String,Object> outRoleMap = new HashMap<String,Object>();
		if(wslList.size() > 0){//拥有转出角色
			if(rolesTemp.length() > 0){
				rolesTemp = rolesTemp.replace(",", "");
				for(WebSublicenseInfo info : wslList){
					rolesTemp = rolesTemp.replace(info.getRoleCode(), "");
				}
		    	if(rolesTemp.length() > 0){
		    		if(!StringUtils.isNotEmpty(webSublicenseInfo.getInOper())){
						//{0}不能为空
						throw new SystemException("w384","转入柜员");
					}
		    	}
			}
		}else{
			if(rolesTemp.length() > 0){
				if(!StringUtils.isNotEmpty(webSublicenseInfo.getInOper())){
					//{0}不能为空
					throw new SystemException("w384","转入柜员");
				}
			}
		}
		//校验转出的柜员与转入的柜员是否是同一机构的
		if(StringUtils.isNotEmpty(webSublicenseInfo.getOutOper()) && StringUtils.isNotEmpty(webSublicenseInfo.getInOper())){
			//验证转出的柜员与转入的柜员相同
			if(webSublicenseInfo.getOutOper().equals(webSublicenseInfo.getInOper())){
				json.returnMsg("false", "很遗憾，您无法向自己进行转授权操作");
				return false;
			}
			//转出柜员
			FdOper outFdOper = new FdOper();
			outFdOper.setOpercode(webSublicenseInfo.getOutOper());
			//转入柜员
			FdOper inFdOper = new FdOper();
			inFdOper.setOpercode(webSublicenseInfo.getInOper());
			List<FdOper> outList = fdOperMapper.selectByAttr(outFdOper);
			List<FdOper> inList = fdOperMapper.selectByAttr(inFdOper);
			//验证转出柜员
			if(outList.size() == 0){
				json.returnMsg("false", "转出柜员不存在");
				return false;
			}
			//验证转入柜员
			if(inList.size() == 0){
				json.returnMsg("false", "转入柜员不存在");
				return false;
			}
			//验证转入转出柜员是否是同一机构的
			if(outList.size() != 0 && inList.size() != 0){
				outFdOper = outList.get(0);
				inFdOper = inList.get(0);
				if(!outFdOper.getOrgancode().equals(inFdOper.getOrgancode())){
					json.returnMsg("false", "转出柜员与转入柜员不是同一机构，无法进行转授权操作");
					return false;
				}
			}
			//转出角色不为空时
			if(StringUtils.isNotEmpty(roles)){
				String[] outRole = roles.split(",");
				for(int i = 0; i < outRole.length; i++){
					String roleCode = outRole[i];
					WebSublicenseInfo webSub = new WebSublicenseInfo();
					webSub.setOutOper(webSublicenseInfo.getOutOper());
					webSub.setInOper(webSublicenseInfo.getInOper());
					webSub.setRoleCode(roleCode);
					//验证转出的角色，是否转给其他柜员，并未收回的角色信息
					int isNoInOperCount = webSublicenseInfoMapper.countOutRole(webSub);
					if(isNoInOperCount != 0){
						WebRoleInfo roleInfo = webRoleInfoService.selectByPK(roleCode);
						String roleName ="";
						if(roleInfo != null){
							roleName = roleInfo.getRoleName();
						}
						json.returnMsg("false", roleName + "角色已经转给其他柜员，无法进行转出,请先收回该角色，再进行转授权操作");
						return false;
					}
					//验证转出的角色是否已转给当前柜员，并未收回
					webSub.setIsBack("2");
					List<WebSublicenseInfo> list = webSublicenseInfoMapper.selectByAttr(webSub);
					if(list.size() != 0){
						WebRoleInfo roleInfo = webRoleInfoService.selectByPK(roleCode);
						String roleName ="";
						if(roleInfo != null){
							roleName = roleInfo.getRoleName();
						}
						json.returnMsg("false", roleName + "角色已成功转给当前柜员，无法重复进行转授权操作");
						return false;
					}
				}
			}
		}
		
		return true;
	}
	/**
	 * 
	 *
	 * TODO 与角色功能表关联查询授权用户下拉列表数据.
	 *
	 * @param query
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月7日    	  杜旭    新建
	 * </pre>
	 */
	public List<Map<String,String>> getPowerList(Map<String,String> query) throws Exception{
		return webSublicenseInfoMapper.getPowerList(query);
	}
	/**
	 * 
	 *
	 * TODO 判断是否进行处理.
	 *
	 * @param webSublicenseInfo
	 * @param roles
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年6月2日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	private boolean checkIsOperate(WebSublicenseInfo webSublicenseInfo,String roles) throws Exception{
		//由于校验方法已能控制，转入柜员是否输入，因此在此，则可以肯定的获取当前转入柜员
		//如果转入柜员不为空，进行的操作
			//当转出角色集合不为空的时候，则判断当前柜员转给此柜员未收回的角色集合是否与当前转出角色集合相同，如果相同，则不进行处理
			//当转出角色集合为空的时候，则判断当前转出角色是否为空，如果为空，则不进行处理；不为空的时候，则进行转出处理
		//如果转入柜员为空，进行的操作
			//当转出角色集合不为空的时候，则判断当前柜员转给此柜员未收回的角色集合是否与当前转出角色集合相同，如果相同，则不进行处理；如果不相同，则进行对应的处理（收回）
			//当转出角色集合为空的时候，则判断当前转出角色是否为空，如果为空，则不进行处理；如果不为空的时候，则抛出异常
		//综上所述，区别在于，当前柜员的已转出未收回的转出角色集合
		WebSublicenseInfo outFdOperWsl = new WebSublicenseInfo();
		outFdOperWsl.setOutOper(WebContextUtil.getSessionUser().getOpercode());
		if(StringUtils.isNotEmpty(webSublicenseInfo.getInOper())){
			outFdOperWsl.setInOper(webSublicenseInfo.getInOper());
		}
		outFdOperWsl.setIsBack("2");
		//获取对应的转出角色集合
		//当转入柜员不为空的时候，则查询当前柜员此转入柜员未收回的角色集合
		//当转入柜员为空的时候，则查询当前柜员所有已转出未收回的角色集合
		List<WebSublicenseInfo> wslList = webSublicenseInfoMapper.selectByAttr(outFdOperWsl);
		Map<String,Object> outRoleMap = new HashMap<String,Object>();
		//如果当前转出角色和已转出角色集都为空时，不进行处理
		if(roles.length() == 0 && wslList.size() == 0){//当转出角色集合为空的时候，则判断当前转出角色是否为空，则不进行处理
			return false;
		}else{
			//转出角色集合不为空的时候，则判断当前柜员转给此柜员未收回的角色集合是否与当前转出角色集合相同，如果相同，则不进行处理
			if(wslList.size() > 0){//所有已转出未收回的角色集合
				if(roles.length() >0){
					if(wslList.size() == roles.split(",").length){
						roles = roles.replace(",","");
						for(WebSublicenseInfo info : wslList){
							if(roles.contains(info.getRoleCode())){
								roles = roles.replace(info.getRoleCode(), "");
							}
						}
					    //当前转出角色集不为空，除去转出角色，余下的角色数量为0时，表明已转出角色和当前转出角色相同
					    if(roles.length() == 0){
					    	return false;
					    }
					}
				}
			}
		}
		return true;
	}
}