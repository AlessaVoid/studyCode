package com.boco.RE.service.impl;

import com.boco.RE.excel.importutil.ExcelImporter;
import com.boco.RE.excel.importutil.ImportedExcelData;
import com.boco.RE.service.ITbBankRmbBusiService;
import com.boco.RE.util.Constant;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbBankRmbBusi;
import com.boco.SYS.mapper.TbBankInfoMapper;
import com.boco.SYS.mapper.TbBankRmbBusiMapper;
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
 * 商业银行人民币业务统计表业务服务层接口（银行业分机构）
 * @Author zhuhongjiang
 * @Date 2020/1/8 下午2:53
 **/
@Service
public class TbBankRmbBusiService extends GenericService<TbBankRmbBusi, String> implements ITbBankRmbBusiService {

	private static final Logger log = LoggerFactory.getLogger(TbBankRmbBusiService.class);

	@Autowired
	private TbBankRmbBusiMapper bankRmbBusiMapper;
	@Autowired
	private TbBankInfoMapper tbBankInfoMapper;

	/**
	 * 上传
	 * @param file
	 * @param resultMap
	 * @return
	 */
	@Transactional
	@Override
	public Map<String, Object> upload(List<Map<String, Object>> loanTypeList, String statisticsDayParam, MultipartFile file, String projectPath, String operCode, Map<String, Object> resultMap) {
		String rulePath = projectPath + Constant.RULE_PATH_BANK_RMB_BUSI;
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
			statisticsDay = statisticsDayStr.split("：")[1];
			statisticsDay = Constant.sdf2.format(Constant.sdf1.parse(statisticsDay));
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

		List<TbBankRmbBusi> importListInsert = new ArrayList<>();
		List<TbBankRmbBusi> importListUpdate = new ArrayList<>();

		//遍历行数据
		for(Map<String, Object> sheetDetailMap : sheetDetailData){
			//银行名称
			String bankName = (String)sheetDetailMap.get("bankName");
			String bankShortName = tbBankInfoMapper.selectCodeByName(bankName);
			if(StringUtils.isEmpty(bankShortName)){
				resultMap.put("status", "0001");
				resultMap.put("message", "上传失败，银行名称【"+bankName+"】解析失败，参考模板格式！");
				return resultMap;
			}


			//遍历贷款类型
			for(Map<String, Object> loanTypeMap : loanTypeList){
				String loanType = String.valueOf(loanTypeMap.get("code"));

				//excel信息 - 余额（万元存储）
				Double balance = (Double)sheetDetailMap.get(loanType + "_balance");
				balance = balance == null ? null : new BigDecimal(String.valueOf(balance)).multiply(new BigDecimal("10000")).doubleValue();

				//excel信息 - 排名
				Integer rank = (Integer)sheetDetailMap.get(loanType + "_rank");

				//excel信息 - 当月新增（万元存储）
				Double monthlyAdd = (Double)sheetDetailMap.get(loanType + "_monthlyAdd");
				monthlyAdd = monthlyAdd == null ? null : new BigDecimal(String.valueOf(monthlyAdd)).multiply(new BigDecimal("10000")).doubleValue();

				//excel信息 - 当月排名
				Integer monthlyRank = (Integer)sheetDetailMap.get(loanType + "_monthlyRank");

				//excel信息 - 当年新增（万元存储）
				Double yearAdd = (Double)sheetDetailMap.get(loanType + "_yearAdd");
				yearAdd = yearAdd == null ? null : new BigDecimal(String.valueOf(yearAdd)).multiply(new BigDecimal("10000")).doubleValue();

				//excel信息 - 当年排名
				Integer yearRank = (Integer)sheetDetailMap.get(loanType + "_yearRank");


				if(balance == null && rank == null && monthlyAdd == null && monthlyRank == null && yearAdd == null && yearRank == null){
					continue;
				}

				//校验是否存在
				TbBankRmbBusi record = new TbBankRmbBusi();
				record.setStatisticsDay(statisticsDay);
				record.setBankname(bankShortName);
				record.setLoanType(loanType);
				List<TbBankRmbBusi> existList = bankRmbBusiMapper.selectByAttr(record);

				if(existList == null || existList.isEmpty()){
					record.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
					record.setBalance(balance);
					record.setRank(rank);
					record.setMonthlyAdd(monthlyAdd);
					record.setMonthlyRank(monthlyRank);
					record.setYearAdd(yearAdd);
					record.setYearRank(yearRank);
					record.setCreatedBy(operCode);
					record.setCreatedTime(Constant.sdf3.format(new Date()));
					importListInsert.add(record);
				}else{
					record = existList.get(0);
					record.setBalance(balance);
					record.setRank(rank);
					record.setMonthlyAdd(monthlyAdd);
					record.setMonthlyRank(monthlyRank);
					record.setYearAdd(yearAdd);
					record.setYearRank(yearRank);
					record.setUpdatedBy(operCode);
					record.setUpdatedTime(Constant.sdf3.format(new Date()));
					importListUpdate.add(record);
				}
			}
		}

		if(!importListInsert.isEmpty()){
			bankRmbBusiMapper.insertBatch(importListInsert);
		}
		if(!importListUpdate.isEmpty()){
			for(TbBankRmbBusi record : importListUpdate){
				bankRmbBusiMapper.updateByPK(record);
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
		TbBankRmbBusi record = new TbBankRmbBusi();
		record.setStatisticsDay(statisticsDay);
		List<TbBankRmbBusi> existList = bankRmbBusiMapper.selectByAttr(record);

		if(existList != null && !existList.isEmpty()){
			resultMap.put("isExist", true);
		}else{
			resultMap.put("isExist", false);
		}
		resultMap.put("status", "0000");
		resultMap.put("message", "查询成功");
		return resultMap;
	}
}