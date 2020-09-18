package com.boco.RE.excel.importutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ���浼���EXCEL��Ϣ����
 * @Author zhuhongjiang
 * @Date 2020/1/7 ����2:56
 **/
public class ImportedExcelData {
	private List sheetNameList = new ArrayList(); //SHEET���Ƽ���,List ����ΪSHEET����
	private List headDataList = new ArrayList(); //ͷ��Ϣ���� ,List ����ΪMap����
	private List detailDataList = new ArrayList(); //����Ϣ���� ,List����ΪList����
	
	public ImportedExcelData(){
	
	}
	
	/**
	 * ����˵��:��ȡ�����SHEET����
	 * @return
	 */
	public int getImportedSheetCount(){
		int headSheetCount = 0;
		int detailSheetCount = 0;
		//������ЩSHEET�е�����������������(1:��ͷ����;2����ͷÿ��;3��ûͷ����),��ȡ�������
		if(this.headDataList != null && this.headDataList.size() > 0){
			headSheetCount = this.headDataList.size();
		}
		if(this.detailDataList != null && this.detailDataList.size() > 0){
			detailSheetCount = this.detailDataList.size();
		}
		if(headSheetCount > detailSheetCount){
			return headSheetCount;
		}else{
			return detailSheetCount;
		}
	}
	
	/**
	 * ����˵��: ��ȡָ��λ�õ�SHEET����
	 * @param sheetNum sheetλ�ñ��
	 * @return SHEET����
	 */
	public String getNameOfSheet(int sheetNum){
		if(this.sheetNameList != null && this.sheetNameList.size() > sheetNum){
			return (String)this.sheetNameList.get(sheetNum);
		}
		else{
			return null;
		}
	}
	
	/**
	 * ����˵��:��ȡ�����ָ��λ�õ�SHEET���ͷ��Ϣ
	 * ͷ��Ϣ: key("headData")--, value(Map)
	 * @param sheetNum ָ��λ��
	 * @return
	 */
	public Map getHeadDataOfSheet(int sheetNum){
		if(this.headDataList != null && this.headDataList.size() > sheetNum){
			return (Map)this.headDataList.get(sheetNum);
		}
		else{
			return null;
		}
	}
	
	/**
	 * ����˵��:��ȡ�����ָ��λ�õ�SHEET�������Ϣ
	 * ����Ϣ��key("detailData")--, value(List)
	 * @param sheetNum  ָ��λ��
	 * @return
	 */
	public List getDetailDataOfSheet(int sheetNum){
		if(this.detailDataList != null && this.detailDataList.size() > sheetNum){
			return (List)this.detailDataList.get(sheetNum);
		}
		else{
			return null;
		}
	}

	/**
	 * ����˵��:��ȡ�����ָ��λ�õ�SHEET�����Ϣ(ͷ��Ϣ,����Ϣ)
	 *    ͷ��Ϣ: key("headData")--, value(Map)
	 *    ����Ϣ��key("detailData")--, value(List)
	 * @param sheetNum ָ��λ��
	 * @return
	 */
	public Map getHeadAndDetailDataOfSheet(int sheetNum){
		if(this.headDataList != null && this.headDataList.size() > sheetNum 
				&& this.detailDataList != null && this.detailDataList.size() > sheetNum){
			Map sheetData = new HashMap();
			sheetData.put("headData", this.headDataList.get(sheetNum));
			sheetData.put("detailData", this.detailDataList.get(sheetNum));
			return sheetData;
		}
		else{
			return null;
		}
	}
	
	/**
	 * ����˵��:�������б������SHEET����
	 * @param sheetName
	 * @return
	 */
	public String addNameOfSheet(String sheetName){
		if(this.sheetNameList == null){
			this.sheetNameList = new ArrayList();
		}
		this.sheetNameList.add(sheetName);
		return sheetName;
	}
	
	/**
	 * ����˵��: �������б������ָ��λ�õ�SHEET����
	 * @param sheetNum ָ��λ��
	 * @param sheetName sheet����
	 * @return ��ӳɹ���sheet����
	 */
	public String setNameOfSheet(int sheetNum, String sheetName){
		if(this.sheetNameList == null){
			this.sheetNameList = new ArrayList();
		}
		this.sheetNameList.set(sheetNum, sheetName);
		return sheetName;
	}
	
	/**
	 * ����˵��: ��ȡ�ɹ���ͷ��Ϣ���ݴ���importedExcelData����
	 * ͷ��Ϣ:List<Map> 
	 * @param headDataMap ͷ��Ϣ����
	 * @return
	 */
	public Map addHeadDataOfSheet(Map headDataMap){
		if(this.headDataList == null){
			this.headDataList = new ArrayList();
		}
		this.headDataList.add(headDataMap);
		return headDataMap;
	}
	
	/**
	 * 
	 * ����˵��: ��ȡ�ɹ�������Ϣ���ݴ���importedExcelData����
	 * @param detailDataList ����Ϣ����
	 * @return
	 */
	public List addDetailDataOfSheet(List detailDataList){
		if(this.detailDataList == null){
			this.detailDataList = new ArrayList();
		}
		this.detailDataList.add(detailDataList);
		return detailDataList;
	}
}
