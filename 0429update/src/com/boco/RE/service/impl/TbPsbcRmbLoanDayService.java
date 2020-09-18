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
 * ����Ҵ����ձ���ҵ������ʵ���ࣨ�ʴ��ܷ��У�
 * @Author zhuhongjiang
 * @Date 2020/1/8 ����3:07
 **/
@Service
public class TbPsbcRmbLoanDayService extends GenericService<TbPsbcRmbLoanDay, String> implements ITbPsbcRmbLoanDayService {

	private static final Logger log = LoggerFactory.getLogger(TbPsbcRmbLoanDayService.class);

	@Autowired
	private TbPsbcRmbLoanDayMapper psbcRmbLoanDayMapper;
	@Autowired
	private FdOrganMapper fdOrganMapper;

	/**
	 * ��ҳ��ѯ
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectListByPage(Map<String, Object> paramMap) {
		return psbcRmbLoanDayMapper.selectListByPage(paramMap);
	}

	/**
	 * �ϴ�
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
			resultMap.put("message", "�ϴ�ʧ�ܣ�");
			return resultMap;
		}

		//ģ�������֤ͨ��
		ImportedExcelData importedData = importer.getImportedExcelData();
		Map<String, Object> sheetHeadMap = importedData.getHeadDataOfSheet(0);
		List<Map<String, Object>> sheetDetailData = importedData.getDetailDataOfSheet(0);

		List<TbPsbcRmbLoanDay> importListInsert = new ArrayList<>();
		List<TbPsbcRmbLoanDay> importListUpdate = new ArrayList<>();

		//������������
		for(Map<String, Object> loanTypeMap : loanTypeList){
			String loanType = String.valueOf(loanTypeMap.get("code"));
			String text = String.valueOf(loanTypeMap.get("name"));

			//���⴦�� - ס�����Ҵ��� (�����Ѵ���� ͳ�����ڡ����������)
			String lastLoanType = null;
			if(Constant.GXDK_GRDK_XFDK_ZFAJDK.equals(loanType)){
				lastLoanType = Constant.GXDK_GRDK_XFDK;
			}

			//ͳ������
			String statisticsDayStr = (String)sheetHeadMap.get((lastLoanType==null?loanType:lastLoanType)  + "_statisticsDay");
			String statisticsDay;
			try{
				statisticsDay = Constant.sdf2.format(Constant.sdf3.parse(statisticsDayStr));
				if(!statisticsDayParam.equals(statisticsDay)){
					resultMap.put("status", "0001");
					resultMap.put("message", "�ϴ�ʧ�ܣ�ѡ���ͳ�����ڣ����ļ��С�"+text+"�����ڲ�һ�£�");
					return resultMap;
				}
			}catch (Exception e){
				resultMap.put("status", "0001");
				resultMap.put("message", "�ϴ�ʧ�ܣ�"+text+"-ͳ�����ڡ�"+statisticsDayStr+"������ʧ�ܣ��ο�ģ���ʽ��");
				return resultMap;
			}

			//����������
			for(Map<String, Object> sheetDetailMap : sheetDetailData){
				//����
				String area = (String)sheetDetailMap.get((lastLoanType==null?loanType:lastLoanType) + "_organcode");
				String organCode = getOrganCode(area);
				if(StringUtils.isEmpty(organCode)){
					resultMap.put("status", "0001");
					resultMap.put("message", "�ϴ�ʧ�ܣ�"+text+"-�������ơ�"+area+"������ʧ�ܣ��ο�ģ���ʽ��");
					return resultMap;
				}

				//excel��Ϣ - ���
				String grepcode = (String)sheetDetailMap.get((lastLoanType==null?loanType:lastLoanType) + "_grepcode");

				//excel��Ϣ - ����Ԫ�洢��
				Double balance = (Double)sheetDetailMap.get(loanType + "_balance");
				balance = balance == null ? null : new BigDecimal(String.valueOf(balance)).multiply(new BigDecimal("10000")).doubleValue();

				//excel��Ϣ - ��������Ԫ�洢��
				Double dayAdd = (Double)sheetDetailMap.get(loanType + "_dayAdd");
				dayAdd = dayAdd == null ? null : new BigDecimal(String.valueOf(dayAdd)).multiply(new BigDecimal("10000")).doubleValue();

				//excel��Ϣ - ��������Ԫ�洢��
				Double monthlyAdd = (Double)sheetDetailMap.get(loanType + "_monthlyAdd");
				monthlyAdd = monthlyAdd == null ? null : new BigDecimal(String.valueOf(monthlyAdd)).multiply(new BigDecimal("10000")).doubleValue();

				//excel��Ϣ - ��������Ԫ�洢��
				Double quarterAdd = (Double)sheetDetailMap.get(loanType + "_quarterAdd");
				quarterAdd = quarterAdd == null ? null : new BigDecimal(String.valueOf(quarterAdd)).multiply(new BigDecimal("10000")).doubleValue();

				//excel��Ϣ - ��������Ԫ�洢��
				Double yearAdd = (Double)sheetDetailMap.get(loanType + "_yearAdd");
				yearAdd = yearAdd == null ? null : new BigDecimal(String.valueOf(yearAdd)).multiply(new BigDecimal("10000")).doubleValue();

				//excel��Ϣ - ��������
				Integer yearRank = (Integer)sheetDetailMap.get(loanType + "_yearRank");


				if(balance == null && dayAdd == null && monthlyAdd == null && quarterAdd == null && yearAdd == null && yearRank == null){
					continue;
				}

				//У���Ƿ����
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
		resultMap.put("message", "��ѯ�ɹ�");
		return resultMap;
	}

	/**
	 * ��ȡ��������
	 * @param area
	 * @return
	 */
	public String getOrganCode(String area){
		String organCode = null;
		try{
			if("ȫ��".equals(area)){
				organCode = Constant.ORGAN_CODE_QG;
			}else if("����".equals(area)){
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
	 * �������ͻ�ȡ��ͬģ��
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