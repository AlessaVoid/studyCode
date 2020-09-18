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
 * ����Ҵ�����ձ����ܱ�ҵ������ӿڣ��ʴ��ֲ�Ʒ��
 * @Author zhuhongjiang
 * @Date 2020/1/8 ����2:58
 **/
@Service
public class TbPsbcRmbLoanSumService extends GenericService<TbPsbcRmbLoanSum, String> implements ITbPsbcRmbLoanSumService {

	private static final Logger log = LoggerFactory.getLogger(TbPsbcRmbLoanSumService.class);

	@Autowired
	private TbPsbcRmbLoanSumMapper psbcRmbLoanSumMapper;

	/**
	 * �ϴ�
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
			resultMap.put("message", "�ϴ�ʧ�ܣ�");
			return resultMap;
		}

		//ģ�������֤ͨ��
		ImportedExcelData importedData = importer.getImportedExcelData();
		Map<String, Object> sheetHeadMap = importedData.getHeadDataOfSheet(0);
		List<Map<String, Object>> sheetDetailData = importedData.getDetailDataOfSheet(0);


		//ͳ������
		String statisticsDayStr = (String)sheetHeadMap.get("statisticsDay");
		String statisticsDay;
		try{
			statisticsDay = Constant.sdf2.format(Constant.sdf3.parse(statisticsDayStr));
			if(!statisticsDayParam.equals(statisticsDay)){
				resultMap.put("status", "0001");
				resultMap.put("message", "�ϴ�ʧ�ܣ�ѡ���ͳ�����ڣ����ļ������ڲ�һ�£�");
				return resultMap;
			}
		}catch (Exception e){
			resultMap.put("status", "0001");
			resultMap.put("message", "�ϴ�ʧ�ܣ�ͳ�����ڽ���ʧ�ܣ��ο�ģ���ʽ��");
			return resultMap;
		}

		List<TbPsbcRmbLoanSum> importListInsert = new ArrayList<>();
		List<TbPsbcRmbLoanSum> importListUpdate = new ArrayList<>();

		//����������
		for(Map<String, Object> sheetDetailMap : sheetDetailData){
			//��������
			String loanTypeStr = (String)sheetDetailMap.get("loanType");
			String loanType = getLoanType(loanTypeStr, loanTypeList);
			if(StringUtils.isEmpty(loanType)){
				resultMap.put("status", "0001");
				resultMap.put("message", "�ϴ�ʧ�ܣ��������͡�"+loanTypeStr+"������ʧ�ܣ��ο�ģ���ʽ��");
				return resultMap;
			}

			//excel��Ϣ - ����Ԫ�洢��
			Double balance = (Double)sheetDetailMap.get("balance");
			balance = balance == null ? null : new BigDecimal(String.valueOf(balance)).multiply(new BigDecimal("10000")).doubleValue();

			//excel��Ϣ - ��������Ԫ�洢��
			Double dayAdd = (Double)sheetDetailMap.get("dayAdd");
			dayAdd = dayAdd == null ? null : new BigDecimal(String.valueOf(dayAdd)).multiply(new BigDecimal("10000")).doubleValue();

			//excel��Ϣ - ��������Ԫ�洢��
			Double monthlyAdd = (Double)sheetDetailMap.get("monthlyAdd");
			monthlyAdd = monthlyAdd == null ? null : new BigDecimal(String.valueOf(monthlyAdd)).multiply(new BigDecimal("10000")).doubleValue();

			//excel��Ϣ - ��������Ԫ�洢��
			Double quarterAdd = (Double)sheetDetailMap.get("quarterAdd");
			quarterAdd = quarterAdd == null ? null : new BigDecimal(String.valueOf(quarterAdd)).multiply(new BigDecimal("10000")).doubleValue();

			//excel��Ϣ - ��������Ԫ�洢��
			Double yearAdd = (Double)sheetDetailMap.get("yearAdd");
			yearAdd = yearAdd == null ? null : new BigDecimal(String.valueOf(yearAdd)).multiply(new BigDecimal("10000")).doubleValue();

			//excel��Ϣ - ������
			Double annualGrowthRate = percentToDouble((String)sheetDetailMap.get("annualGrowthRate"));


			if(balance == null && dayAdd == null && monthlyAdd == null && quarterAdd== null && yearAdd == null && annualGrowthRate == null){
				continue;
			}

			//У���Ƿ����
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
		resultMap.put("message", "�ϴ��ɹ���������"+importListInsert.size()+"�������޸ġ�"+importListUpdate.size()+"������");
		return resultMap;
	}

	/**
	 * У��ͳ������
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
		resultMap.put("message", "��ѯ�ɹ�");
		return resultMap;
	}

	/**
	 * �ٷֱ�תDouble
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
	 * ��ȡ��������
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