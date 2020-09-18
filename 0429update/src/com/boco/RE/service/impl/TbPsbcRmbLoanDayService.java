package com.boco.RE.service.impl;

import com.boco.RE.excel.importutil.ExcelImporter;
import com.boco.RE.excel.importutil.ImportedExcelData;
import com.boco.RE.service.ITbPsbcRmbLoanDayService;
import com.boco.RE.util.Constant;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbPsbcRmbLoanDay;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.SYS.mapper.TbPsbcRmbLoanDayMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 人民币贷款日报表业务服务层实现类（邮储总分行）
 * @Author zhuhongjiang
 * @Date 2020/1/8 下午3:07
 **/
@Service
public class TbPsbcRmbLoanDayService extends GenericService<TbPsbcRmbLoanDay, String> implements ITbPsbcRmbLoanDayService {

	private static final Logger log = LoggerFactory.getLogger(TbPsbcRmbLoanDayService.class);

	@Autowired
	private TbPsbcRmbLoanDayMapper psbcRmbLoanDayMapper;
	@Autowired
	private FdOrganMapper fdOrganMapper;

	/**
	 * 分页查询
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectListByPage(Map<String, Object> paramMap) {
		return psbcRmbLoanDayMapper.selectListByPage(paramMap);
	}

	/**
	 * 上传
	 * @param file
	 * @param resultMap
	 * @return
	 */
	@Transactional
	@Override
	public Map<String, Object> upload(List<Map<String, Object>> loanTypeList, String statisticsDayParam, String type, MultipartFile file, String projectPath, String operCode, Map<String, Object> resultMap) {
		String rulePath = projectPath + getRulePath(type);
		ExcelImporter importer = new ExcelImporter(rulePath, file);
		boolean result = importer.importExcel();
		if(!result){
			resultMap.put("errorList", importer.getErrorList());
			resultMap.put("status", "0001");
			resultMap.put("message", "上传失败！");
			return resultMap;
		}

		//模板规则验证通过
		ImportedExcelData importedData = importer.getImportedExcelData();
		Map<String, Object> sheetHeadMap = importedData.getHeadDataOfSheet(0);
		List<Map<String, Object>> sheetDetailData = importedData.getDetailDataOfSheet(0);

		List<TbPsbcRmbLoanDay> importListInsert = new ArrayList<>();
		List<TbPsbcRmbLoanDay> importListUpdate = new ArrayList<>();

		//遍历贷款类型
		for(Map<String, Object> loanTypeMap : loanTypeList){
			String loanType = String.valueOf(loanTypeMap.get("code"));
			String text = String.valueOf(loanTypeMap.get("name"));

			//特殊处理 - 住房按揭贷款 (与消费贷款共用 统计日期、机构、组别)
			String lastLoanType = null;
			if(Constant.GXDK_GRDK_XFDK_ZFAJDK.equals(loanType)){
				lastLoanType = Constant.GXDK_GRDK_XFDK;
			}

			//统计日期
			String statisticsDayStr = (String)sheetHeadMap.get((lastLoanType==null?loanType:lastLoanType)  + "_statisticsDay");
			String statisticsDay;
			try{
				statisticsDay = Constant.sdf2.format(Constant.sdf3.parse(statisticsDayStr));
				if(!statisticsDayParam.equals(statisticsDay)){
					resultMap.put("status", "0001");
					resultMap.put("message", "上传失败，选择的统计日期，与文件中【"+text+"】日期不一致！");
					return resultMap;
				}
			}catch (Exception e){
				resultMap.put("status", "0001");
				resultMap.put("message", "上传失败，"+text+"-统计日期【"+statisticsDayStr+"】解析失败，参考模板格式！");
				return resultMap;
			}

			//遍历行数据
			for(Map<String, Object> sheetDetailMap : sheetDetailData){
				//地区
				String area = (String)sheetDetailMap.get((lastLoanType==null?loanType:lastLoanType) + "_organcode");
				String organCode = getOrganCode(area);
				if(StringUtils.isEmpty(organCode)){
					resultMap.put("status", "0001");
					resultMap.put("message", "上传失败，"+text+"-地区名称【"+area+"】解析失败，参考模板格式！");
					return resultMap;
				}

				//excel信息 - 组别
				String grepcode = (String)sheetDetailMap.get((lastLoanType==null?loanType:lastLoanType) + "_grepcode");

				//excel信息 - 余额（万元存储）
				Double balance = (Double)sheetDetailMap.get(loanType + "_balance");
				balance = balance == null ? null : new BigDecimal(String.valueOf(balance)).multiply(new BigDecimal("10000")).doubleValue();

				//excel信息 - 日增（万元存储）
				Double dayAdd = (Double)sheetDetailMap.get(loanType + "_dayAdd");
				dayAdd = dayAdd == null ? null : new BigDecimal(String.valueOf(dayAdd)).multiply(new BigDecimal("10000")).doubleValue();

				//excel信息 - 月增（万元存储）
				Double monthlyAdd = (Double)sheetDetailMap.get(loanType + "_monthlyAdd");
				monthlyAdd = monthlyAdd == null ? null : new BigDecimal(String.valueOf(monthlyAdd)).multiply(new BigDecimal("10000")).doubleValue();

				//excel信息 - 季增（万元存储）
				Double quarterAdd = (Double)sheetDetailMap.get(loanType + "_quarterAdd");
				quarterAdd = quarterAdd == null ? null : new BigDecimal(String.valueOf(quarterAdd)).multiply(new BigDecimal("10000")).doubleValue();

				//excel信息 - 年增（万元存储）
				Double yearAdd = (Double)sheetDetailMap.get(loanType + "_yearAdd");
				yearAdd = yearAdd == null ? null : new BigDecimal(String.valueOf(yearAdd)).multiply(new BigDecimal("10000")).doubleValue();

				//excel信息 - 年增排名
				Integer yearRank = (Integer)sheetDetailMap.get(loanType + "_yearRank");


				if(balance == null && dayAdd == null && monthlyAdd == null && quarterAdd == null && yearAdd == null && yearRank == null){
					continue;
				}

				//校验是否存在
				TbPsbcRmbLoanDay record = new TbPsbcRmbLoanDay();
				record.setStatisticsDay(statisticsDay);
				record.setOrgancode(organCode);
				record.setLoanType(loanType);
				List<TbPsbcRmbLoanDay> existList = psbcRmbLoanDayMapper.selectByAttr(record);

				if(existList == null || existList.isEmpty()){
					record.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
					record.setGrepcode(grepcode);
					record.setBalance(balance);
					record.setDayAdd(dayAdd);
					record.setMonthlyAdd(monthlyAdd);
					record.setQuarterAdd(quarterAdd);
					record.setYearAdd(yearAdd);
					record.setYearRank(yearRank);
					record.setCreatedBy(operCode);
					record.setCreatedTime(Constant.sdf3.format(new Date()));
					importListInsert.add(record);
				}else{
					record = existList.get(0);
					record.setGrepcode(grepcode);
					record.setBalance(balance);
					record.setDayAdd(dayAdd);
					record.setMonthlyAdd(monthlyAdd);
					record.setQuarterAdd(quarterAdd);
					record.setYearAdd(yearAdd);
					record.setYearRank(yearRank);
					record.setUpdatedBy(operCode);
					record.setUpdatedTime(Constant.sdf3.format(new Date()));
					importListUpdate.add(record);
				}
			}
		}

		if(!importListInsert.isEmpty()){
			psbcRmbLoanDayMapper.insertBatch(importListInsert);
		}
		if(!importListUpdate.isEmpty()){
			for(TbPsbcRmbLoanDay record : importListUpdate){
				psbcRmbLoanDayMapper.updateByPK(record);
			}
		}

		resultMap.put("status", "0000");
		resultMap.put("message", "上传成功，新增【"+importListInsert.size()+"】条，修改【"+importListUpdate.size()+"】条！");
		return resultMap;
	}

	/**
	 * 校验统计日期
	 * @param statisticsDay
	 * @param resultMap
	 * @return
	 */
	@Override
	public Map<String, Object> checkStatisticsDay(String type, String statisticsDay, Map<String, Object> resultMap) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("type", type);
		paramMap.put("statisticsDay", statisticsDay);
		List<Map<String, Object>> existList = psbcRmbLoanDayMapper.selectForCheckOnly(paramMap);

		if(existList != null && !existList.isEmpty()){
			resultMap.put("isExist", true);
		}else{
			resultMap.put("isExist", false);
		}
		resultMap.put("status", "0000");
		resultMap.put("message", "查询成功");
		return resultMap;
	}

	/**
	 * 获取机构编码
	 * @param area
	 * @return
	 */
	public String getOrganCode(String area){
		String organCode = null;
		try{
			if("全国".equals(area)){
				organCode = Constant.ORGAN_CODE_QG;
			}else if("总行".equals(area)){
				organCode = Constant.ORGAN_CODE_ZH;
			}else{
				List<Map<String, Object>> list = fdOrganMapper.selectByLikeOrganname(area);
				if(list != null && !list.isEmpty()){
					organCode = String.valueOf(list.get(0).get("thiscode"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return organCode;
	}

	/**
	 * 根据类型获取不同模板
	 * @param type
	 * @return
	 */
	public String getRulePath(String type){
		String rulePath = null;
		if(Constant.UPLOAD_TYPE_4.equals(type)){
			rulePath = Constant.RULE_PATH_PSBC_RMB_LOANDAY;
		}else if(Constant.UPLOAD_TYPE_5.equals(type)){
			rulePath = Constant.RULE_PATH_PSBC_RMB_LOANDAY_GR;
		}else if(Constant.UPLOAD_TYPE_6.equals(type)){
			rulePath = Constant.RULE_PATH_PSBC_RMB_LOANDAY_GS;
		}
		return rulePath;
	}
}