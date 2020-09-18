package com.boco.PM.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.PM.service.IWebAreaOrganInfoService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.GfDict;
import com.boco.SYS.entity.WebAreaOrganInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.WebAreaOrganInfoMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.service.IGfDictService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;
import com.boco.SYS.util.MapUtil;

/**
 * 
 * 
 * WebAreaOrganInfo业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class WebAreaOrganInfoService extends GenericService<WebAreaOrganInfo,HashMap<String,Object>> implements IWebAreaOrganInfoService{
	@Autowired
	private WebAreaOrganInfoMapper webAreaOrganInfoMapper;
	@Autowired
	private IGfDictService gfDictService;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	/**
	 * 
	 *
	 * TODO 新增地区信息.
	 *
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public Json insertWebAreaOrgan(WebAreaOrganInfo webAreaOrganInfo,String organs,FdOper fdOper) throws Exception{
		//验证地区信息
		boolean check = checkInsertData(webAreaOrganInfo,organs);
		//校验失败
		if(check == false){
			return json;
		}
		
		//插入地区信息
		GfDict gfDict = new GfDict();
		gfDict.setDictNo(webAreaOrganInfo.getDictNo());
		gfDict.setDictName(webAreaOrganInfo.getDictName());
		//获取标识序号
		int dictNoOrder = gfDictService.selectOrder(gfDict);
		gfDict.setDictNoOrder(dictNoOrder+1);
		gfDict.setDictKeyIn(webAreaOrganInfo.getAreaCode());
		gfDict.setDictValueIn(webAreaOrganInfo.getAreaName());
		gfDict.setStatus("1");
		gfDict.setCreateOper(fdOper.getOpercode());
		//当前日期、当前时间
		gfDict.setUpdateDate(fdBusinessDateService.getCommonDateDetails());
		gfDict.setUpdateTime(BocoUtils.getNowTime());
		json = gfDictService.insert(gfDict);
		if("false".equals(json.getSuccess())){
			//{0}已存在
			throw new SystemException("w380","地区编号");
		}
		
		//插入地区与机构对应信息
		if(StringUtils.isNotEmpty(organs)){
			insertBatchWebArea(webAreaOrganInfo,organs,fdOper);
		}
		return json.returnMsg("true", "新增成功");
	}
	/**
	 * 
	 *
	 * TODO 修改地区信息.
	 *
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json updateWebAreaOrgan(WebAreaOrganInfo webAreaOrganInfo,String organs, FdOper fdOper) throws Exception {
		//验证地区信息
		boolean check = checkUpdateData(webAreaOrganInfo,organs);
		//校验失败
		if(check == false){
			return json;
		}
		//修改地区信息
		GfDict gfDict = new GfDict();
		gfDict.setDictNo(webAreaOrganInfo.getDictNo());
		gfDict.setDictName(webAreaOrganInfo.getDictName());
		gfDict.setDictKeyIn(webAreaOrganInfo.getAreaCode());
		gfDict.setDictValueIn(webAreaOrganInfo.getAreaName());
		gfDict.setCreateOper(fdOper.getOpercode());
		gfDict.setUpdateDate(fdBusinessDateService.getCommonDateDetails());
		gfDict.setUpdateTime(BocoUtils.getNowTime());
		gfDictService.update(gfDict);
		//删除地区编码对应的地区信息
		webAreaOrganInfoMapper.deleteByAreaCode(webAreaOrganInfo.getAreaCode());
		//新增地区信息
		if(StringUtils.isNotEmpty(organs)){
			insertBatchWebArea(webAreaOrganInfo,organs,fdOper);
		}
		return json.returnMsg("true", "修改成功");
	}
	/**
	 * 
	 *
	 * TODO 删除地区信息.
	 *
	 * @param areaCode
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public void deleteWebAreaOrgan(WebAreaOrganInfo webAreaOrganInfo) throws Exception {
		//修改地区信息
		GfDict gfDict = new GfDict();
		gfDict.setDictNo(webAreaOrganInfo.getDictNo());
		gfDict.setDictName(webAreaOrganInfo.getDictName());
		gfDict.setDictKeyIn(webAreaOrganInfo.getAreaCode());
		gfDictService.deleteByPK(MapUtil.beanToMap(gfDict));
		//删除地区信息
		webAreaOrganInfoMapper.deleteByAreaCode(webAreaOrganInfo.getAreaCode());
	}
	/**
	 * 
	 *
	 * TODO 修改校验规则.
	 *
	 * @param webAreaOrganInfo
	 * @param organs
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	private boolean checkUpdateData(WebAreaOrganInfo webAreaOrganInfo,String organs) throws Exception {
		if(!StringUtils.isNotEmpty(webAreaOrganInfo.getAreaName())){
			//{0}不能为空
			throw new SystemException("w384","地区名称");
		}
		if(BocoUtils.getStrLength(webAreaOrganInfo.getAreaName(), 2) > 256){
			//{0}的长度不能超过{1}
			throw new SystemException("w373","地区名称","256");
		}
		String req = "[\u4e00-\u9fa5]*+[a-zA-Z]*+[0-9]*+";
		if(Pattern.matches(req,webAreaOrganInfo.getAreaName()) == false){
			//{0}包含非法字符
			throw new SystemException("w356","地区名称");
		}
		String hasOrgan = "";
		//获取已被选择的机构信息
		List<WebAreaOrganInfo> organList = webAreaOrganInfoMapper.selectNotEqualOrgan(webAreaOrganInfo.getAreaCode());
		if(organList.size() != 0){
			for(WebAreaOrganInfo areaOrgan:organList){
				hasOrgan += areaOrgan.getProvCode() + ",";
			}
			if(!"".equals(hasOrgan)){
				hasOrgan = hasOrgan.substring(0, hasOrgan.length() - 1);
			}
		}
		//验证所选的机构是否已被其他地区所选择
		if(StringUtils.isNotEmpty(organs) && !"无".equals(organs)){
			String[] organ = organs.split(",");
			for(int i = 0; i < organ.length; i++){
				if(hasOrgan.indexOf(organ[i]) != -1){
					json.returnMsg("false", this.getErrorInfo("w392",organ[i]+","));
					return false;
				}
			}
		}
		//验证地区名称是否重复
		GfDict gfDict = new GfDict();
		gfDict.setDictNo(webAreaOrganInfo.getDictNo());
		gfDict.setDictName(webAreaOrganInfo.getDictName());
		gfDict.setDictValueIn(webAreaOrganInfo.getAreaName());
		int count = gfDictService.countByAttr(gfDict);
		gfDict.setDictValueIn(null);
		gfDict.setDictKeyIn(webAreaOrganInfo.getAreaCode());
		gfDict =  gfDictService.selectByPK(MapUtil.beanToMap(gfDict));
		if(!gfDict.getDictValueIn().equals(webAreaOrganInfo.getAreaName())&&count > 0){
			//地区名称重复，请重新输入！
			json.returnMsg("false", this.getErrorInfo("w4A2"));
			return false;
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO 批量插入地区信息.
	 *
	 * @param webAreaOrganInfo
	 * @param organs
	 * @param fdOper
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 */
	public void insertBatchWebArea(WebAreaOrganInfo webAreaOrganInfo,String organs,FdOper fdOper){
		String[] organ = organs.split(",");
		for(int i = 0; i < organ.length; i++){
			WebAreaOrganInfo area = new WebAreaOrganInfo();
			area.setAreaCode(webAreaOrganInfo.getAreaCode());
			area.setProvCode(organ[i]);
			area.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
			area.setLatestModifyTime(BocoUtils.getNowTime());
			area.setLatestOperCode(fdOper.getOpercode());
			int count = webAreaOrganInfoMapper.insertEntity(area);
			if(count != 1){
				//{0}新增失败
				throw new SystemException("w376","地区信息");
			}
		}
	}
	/**
	 * 
	 *
	 * TODO 新增校验规则.
	 *
	 * @param webAreaOrganInfo
	 * @param organs
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	private boolean checkInsertData(WebAreaOrganInfo webAreaOrganInfo,String organs) throws Exception {
		if(!StringUtils.isNotEmpty(webAreaOrganInfo.getAreaCode())){
			json.returnMsg("false", this.getErrorInfo("w384","地区编码"));
			return false;
		}
		if(!StringUtils.isNotEmpty(webAreaOrganInfo.getAreaName())){
			json.returnMsg("false", this.getErrorInfo("w384","地区名称"));
			return false;
		}
		if(BocoUtils.getStrLength(webAreaOrganInfo.getAreaCode(), 2) > 3){
			//{0}的长度不能超过{1}
			throw new SystemException("w373","地区编码","2");
		}
		if(BocoUtils.getStrLength(webAreaOrganInfo.getAreaName(), 2) > 256){
			//{0}的长度不能超过{1}
			throw new SystemException("w373","地区名称","256");
		}
		String req = "[\u4e00-\u9fa5]*+[a-zA-Z]*+[0-9]*+";
		if(Pattern.matches(req,webAreaOrganInfo.getAreaName()) == false){
			//{0}包含非法字符
			throw new SystemException("w356","地区名称");
		}
		
		WebAreaOrganInfo webAreaOrgan = new WebAreaOrganInfo();
		String hasOrgan = "";
		//获取已被选择的机构信息
		List<WebAreaOrganInfo> organList = webAreaOrganInfoMapper.selectByAttr(webAreaOrgan);
		if(organList.size() != 0){
			for(WebAreaOrganInfo areaOrgan:organList){
				hasOrgan += areaOrgan.getProvCode() + ",";
			}
			if(!"".equals(hasOrgan)){
				hasOrgan = hasOrgan.substring(0, hasOrgan.length() - 1);
			}
		}
		//验证所选的机构是否已被其他地区所选择
		if(StringUtils.isNotEmpty(organs) && !"无".equals(organs)){
			String[] organ = organs.split(",");
			for(int i = 0; i < organ.length; i++){
				if(hasOrgan.indexOf(organ[i]) != -1){
					json.returnMsg("false", this.getErrorInfo("w392",organ[i]+","));
					return false;
				}
			}
		}
		//验证地区名称是否重复
		GfDict gfDict = new GfDict();
		gfDict.setDictNo(webAreaOrganInfo.getDictNo());
		gfDict.setDictName(webAreaOrganInfo.getDictName());
		gfDict.setDictValueIn(webAreaOrganInfo.getAreaName());
		int count = gfDictService.countByAttr(gfDict);
		if(count>0){
			//地区名称重复，请重新输入！
			json.returnMsg("false", this.getErrorInfo("w4A2"));
			return false;
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO 查询其他区域所选择的机构信息.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月4日    	  杜旭    新建
	 * </pre>
	 */
	public List<WebAreaOrganInfo> selectNotEqualOrgan(String areaCode){
		return webAreaOrganInfoMapper.selectNotEqualOrgan(areaCode);
	}
}