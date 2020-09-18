package com.boco.RE.service.impl;

import com.boco.RE.excel.importutil.ExcelImporter;
import com.boco.RE.excel.importutil.ImportedExcelData;
import com.boco.RE.service.ITbPsbcRmbLoanSumService;
import com.boco.RE.util.Constant;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbPsbcRmbLoanSum;
import com.boco.SYS.mapper.TbPsbcRmbLoanSumMapper;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 人民币存贷款日报汇总表业务服务层接口（邮储分产品）
 * @Author zhuhongjiang
 * @Date 2020/1/8 下午2:58
 **/
@Service
public class TbPsbcRmbLoanSumService extends GenericService<TbPsbcRmbLoanSum, String> implements ITbPsbcRmbLoanSumService {

	private static final Logger log = LoggerFactory.getLogger(TbPsbcRmbLoanSumService.class);

	@Autowired
	private TbPsbcRmbLoanSumMapper psbcRmbLoanSumMapper;

	/**
	 * 上传
	 * @param file
	 * @param resultMap
	 * @return
	 */
	@Transactional
	@Override
	public Map<String, Object> upload(List<Map<String, Object>> loanTypeList, String statisticsDayParam, MultipartFile file, String projectPath, String operCode, Map<String, Object> resultMap) {
		String rulePath = projectPath + Constant.RULE_PATH_PSBC_RMB_LOAN_SUM;
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


		//统计日期
		String statisticsDayStr = (String)sheetHeadMap.get("statisticsDay");
		String statisticsDay;
		try{
			statisticsDay = Constant.sdf2.format(Constant.sdf3.parse(statisticsDayStr));
			if(!statisticsDayParam.equals(statisticsDay)){
				resultMap.put("status", "0001");
				resultMap.put("message", "上传失败，选择的统计日期，与文件中日期不一致！");
				return resultMap;
			}
		}catch (Exception e){
			resultMap.put("status", "0001");
			resultMap.put("message", "上传失败，统计日期解析失败，参考模板格式！");
			return resultMap;
		}

		List<TbPsbcRmbLoanSum> importListInsert = new ArrayList<>();
		List<TbPsbcRmbLoanSum> importListUpdate = new ArrayList<>();

		//遍历行数据
		for(Map<String, Object> sheetDetailMap : sheetDetailData){
			//贷款类型
			String loanTypeStr = (String)sheetDetailMap.get("loanType");
			String loanType = getLoanType(loanTypeStr, loanTypeList);
			if(StringUtils.isEmpty(loanType)){
				resultMap.put("status", "0001");
				resultMap.put("message", "上传失败，贷款类型【"+loanTypeStr+"】解析失败，参考模板格式！");
				return resultMap;
			}

			//excel信息 - 余额（万元存储）
			Double balance = (Double)sheetDetailMap.get("balance");
			balance = balance == null ? null : new BigDecimal(String.valueOf(balance)).multiply(new BigDecimal("10000")).doubleValue();

			//excel信息 - 日增（万元存储）
			Double dayAdd = (Double)sheetDetailMap.get("dayAdd");
			dayAdd = dayAdd == null ? null : new BigDecimal(String.valueOf(dayAdd)).multiply(new BigDecimal("10000")).doubleValue();

			//excel信息 - 月增（万元存储）
			Double monthlyAdd = (Double)sheetDetailMap.get("monthlyAdd");
			monthlyAdd = monthlyAdd == null ? null : new BigDecimal(String.valueOf(monthlyAdd)).multiply(new BigDecimal("10000")).doubleValue();

			//excel信息 - 季增（万元存储）
			Double quarterAdd = (Double)sheetDetailMap.get("quarterAdd");
			quarterAdd = quarterAdd == null ? null : new BigDecimal(String.valueOf(quarterAdd)).multiply(new BigDecimal("10000")).doubleValue();

			//excel信息 - 年增（万元存储）
			Double yearAdd = (Double)sheetDetailMap.get("yearAdd");
			yearAdd = yearAdd == null ? null : new BigDecimal(String.valueOf(yearAdd)).multiply(new BigDecimal("10000")).doubleValue();

			//excel信息 - 年增幅
			Double annualGrowthRate = percentToDouble((String)sheetDetailMap.get("annualGrowthRate"));


			if(balance == null && dayAdd == null && monthlyAdd == null && quarterAdd== null && yearAdd == null && annualGrowthRate == null){
				continue;
			}

			//校验是否存在
			TbPsbcRmbLoanSum record = new TbPsbcRmbLoanSum();
			record.setStatisticsDay(statisticsDay);
			record.setLoanType(loanType);
			List<TbPsbcRmbLoanSum> existList = psbcRmbLoanSumMapper.selectByAttr(record);

			if(existList == null || existList.isEmpty()){
				record.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
				record.setBalance(balance);
				record.setDayAdd(dayAdd);
				record.setMonthlyAdd(monthlyAdd);
				record.setQuarterAdd(quarterAdd);
				record.setYearAdd(yearAdd);
				record.setAnnualGrowthRate(annualGrowthRate);
				record.setCreatedBy(operCode);
				record.setCreatedTime(Constant.sdf3.format(new Date()));
				importListInsert.add(record);
			}else{
				record = existList.get(0);
				record.setBalance(balance);
				record.setDayAdd(dayAdd);
				record.setMonthlyAdd(monthlyAdd);
				record.setQuarterAdd(quarterAdd);
				record.setYearAdd(yearAdd);
				record.setAnnualGrowthRate(annualGrowthRate);
				record.setUpdatedBy(operCode);
				record.setUpdatedTime(Constant.sdf3.format(new Date()));
				importListUpdate.add(record);
			}
		}

		if(!importListInsert.isEmpty()){
			psbcRmbLoanSumMapper.insertBatch(importListInsert);
		}
		if(!importListUpdate.isEmpty()){
			for(TbPsbcRmbLoanSum record : importListUpdate){
				psbcRmbLoanSumMapper.updateByPK(record);
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
	public Map<String, Object> checkStatisticsDay(String statisticsDay, Map<String, Object> resultMap) {
		TbPsbcRmbLoanSum record = new TbPsbcRmbLoanSum();
		record.setStatisticsDay(statisticsDay);
		List<TbPsbcRmbLoanSum> existList = psbcRmbLoanSumMapper.selectByAttr(record);

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
	 * 百分比转Double
	 * @param percentStr
	 * @return
	 */
	public Double percentToDouble(String percentStr){
		Double d = null;
		if(StringUtils.isNotEmpty(percentStr)){
			percentStr = percentStr.replaceAll("%", "");
			if(StringUtils.isNotEmpty(percentStr)){
				d = Double.valueOf(percentStr);
			}
		}
		return d;
	}

	/**
	 * 获取贷款类型
	 * @param loanTypeStr
	 * @return
	 */
	public String getLoanType(String loanTypeStr, List<Map<String, Object>> loanTypeList){
		String loanType = null;
		try{
			if(StringUtils.isNotEmpty(loanTypeStr)){
				loanTypeStr = loanTypeStr.trim();
				for(Map<String, Object> loanTypeMap : loanTypeList){
					if(loanTypeMap.get("alias").equals(loanTypeStr)){
						loanType = String.valueOf(loanTypeMap.get("code"));
						break;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return loanType;
	}
}