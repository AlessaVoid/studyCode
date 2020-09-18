package com.boco.RE.service.impl;

import com.boco.RE.excel.importutil.ExcelImporter;
import com.boco.RE.excel.importutil.ImportedExcelData;
import com.boco.RE.service.ITbPsbcRmbBusiService;
import com.boco.RE.util.Constant;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbPsbcRmbBusi;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.SYS.mapper.TbPsbcRmbBusiMapper;
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
 * ����ҵ�������Ҫҵ��ֵ���ͳ�Ʊ�ҵ������ӿڣ�����ҵ�ֵ�����
 * @Author zhuhongjiang
 * @Date 2020/1/8 ����3:03
 **/
@Service
public class TbPsbcRmbBusiService extends GenericService<TbPsbcRmbBusi, String> implements ITbPsbcRmbBusiService {

	private static final Logger log = LoggerFactory.getLogger(TbPsbcRmbBusiService.class);

	@Autowired
	private TbPsbcRmbBusiMapper psbcRmbBusiMapper;
	@Autowired
	private FdOrganMapper fdOrganMapper;


	/**
	 * ��ҳ��ѯ
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectListByPage(Map<String, Object> paramMap) {
		return psbcRmbBusiMapper.selectListByPage(paramMap);
	}

	/**
	 * �ϴ�
	 * @param file
	 * @param resultMap
	 * @return
	 */
	@Transactional
	@Override
	public Map<String, Object> upload(List<Map<String, Object>> loanTypeList, String statisticsDayParam, MultipartFile file, String projectPath, String operCode, Map<String, Object> resultMap) {
		String rulePath = projectPath + Constant.RULE_PATH_PSBC_RMB_BUSI;
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
			statisticsDay = statisticsDayStr.split("��")[1];
			statisticsDay = Constant.sdf2.format(Constant.sdf1.parse(statisticsDay));
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

		List<TbPsbcRmbBusi> importListInsert = new ArrayList<>();
		List<TbPsbcRmbBusi> importListUpdate = new ArrayList<>();

		//����������
		for(Map<String, Object> sheetDetailMap : sheetDetailData){
			//����
			String area = (String)sheetDetailMap.get("area");
			String organCode = getOrganCode(area);
			if(StringUtils.isEmpty(organCode)){
				resultMap.put("status", "0001");
				resultMap.put("message", "�ϴ�ʧ�ܣ��������ơ�"+area+"������ʧ�ܣ��ο�ģ���ʽ��");
				return resultMap;
			}


			//������������
			for(Map<String, Object> loanTypeMap : loanTypeList){
				String loanType = String.valueOf(loanTypeMap.get("code"));

				//excel��Ϣ - ����Ԫ�洢��
				Double balance = (Double)sheetDetailMap.get(loanType + "_balance");
				balance = balance == null ? null : new BigDecimal(String.valueOf(balance)).multiply(new BigDecimal("10000")).doubleValue();

				//excel��Ϣ - ����
				Integer rank = (Integer)sheetDetailMap.get(loanType + "_rank");

				//excel��Ϣ - ������������Ԫ�洢��
				Double monthlyAdd = (Double)sheetDetailMap.get(loanType + "_monthlyAdd");
				monthlyAdd = monthlyAdd == null ? null : new BigDecimal(String.valueOf(monthlyAdd)).multiply(new BigDecimal("10000")).doubleValue();

				//excel��Ϣ - ������������Ԫ�洢��
				Double yearAdd = (Double)sheetDetailMap.get(loanType + "_yearAdd");
				yearAdd = yearAdd == null ? null : new BigDecimal(String.valueOf(yearAdd)).multiply(new BigDecimal("10000")).doubleValue();


				if(balance == null && rank == null && monthlyAdd == null && yearAdd == null){
					continue;
				}

				//У���Ƿ����
				TbPsbcRmbBusi record = new TbPsbcRmbBusi();
				record.setStatisticsDay(statisticsDay);
				record.setArea(organCode);
				record.setLoanType(loanType);
				List<TbPsbcRmbBusi> existList = psbcRmbBusiMapper.selectByAttr(record);

				if(existList == null || existList.isEmpty()){
					record.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
					record.setBalance(balance);
					record.setRank(rank);
					record.setMonthlyAdd(monthlyAdd);
					record.setYearAdd(yearAdd);
					record.setCreatedBy(operCode);
					record.setCreatedTime(Constant.sdf3.format(new Date()));
					importListInsert.add(record);
				}else{
					record = existList.get(0);
					record.setBalance(balance);
					record.setRank(rank);
					record.setMonthlyAdd(monthlyAdd);
					record.setYearAdd(yearAdd);
					record.setUpdatedBy(operCode);
					record.setUpdatedTime(Constant.sdf3.format(new Date()));
					importListUpdate.add(record);
				}
			}
		}

		if(!importListInsert.isEmpty()){
			psbcRmbBusiMapper.insertBatch(importListInsert);
		}
		if(!importListUpdate.isEmpty()){
			for(TbPsbcRmbBusi record : importListUpdate){
				psbcRmbBusiMapper.updateByPK(record);
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
		TbPsbcRmbBusi record = new TbPsbcRmbBusi();
		record.setStatisticsDay(statisticsDay);
		List<TbPsbcRmbBusi> existList = psbcRmbBusiMapper.selectByAttr(record);

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
			}else if("�Ϻ�".equals(area)){
				organCode = Constant.ORGAN_CODE_SHFH;
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
}