package com.boco.RE.excel.importutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保存导入的EXCEL信息对象
 * @Author zhuhongjiang
 * @Date 2020/1/7 下午2:56
 **/
public class ImportedExcelData {
	private List sheetNameList = new ArrayList(); //SHEET名称集合,List 内容为SHEET名称
	private List headDataList = new ArrayList(); //头信息数据 ,List 内容为Map对象
	private List detailDataList = new ArrayList(); //行信息数据 ,List内容为List对象
	
	public ImportedExcelData(){
	
	}
	
	/**
	 * 方法说明:获取导入的SHEET数量
	 * @return
	 */
	public int getImportedSheetCount(){
		int headSheetCount = 0;
		int detailSheetCount = 0;
		//由于有些SHEET中导入存在如下三种情况(1:有头有行;2：有头每行;3：没头有行),故取最大集数量
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
	 * 方法说明: 获取指定位置的SHEET名称
	 * @param sheetNum sheet位置编号
	 * @return SHEET名称
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
	 * 方法说明:获取导入的指定位置的SHEET里的头信息
	 * 头信息: key("headData")--, value(Map)
	 * @param sheetNum 指定位置
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
	 * 方法说明:获取导入的指定位置的SHEET里的行信息
	 * 行信息：key("detailData")--, value(List)
	 * @param sheetNum  指定位置
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
	 * 方法说明:获取导入的指定位置的SHEET里的信息(头信息,行信息)
	 *    头信息: key("headData")--, value(Map)
	 *    行信息：key("detailData")--, value(List)
	 * @param sheetNum 指定位置
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
	 * 方法说明:往集合列表里添加SHEET名称
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
	 * 方法说明: 往集合列表里添加指定位置的SHEET名称
	 * @param sheetNum 指定位置
	 * @param sheetName sheet名称
	 * @return 添加成功的sheet名称
	 */
	public String setNameOfSheet(int sheetNum, String sheetName){
		if(this.sheetNameList == null){
			this.sheetNameList = new ArrayList();
		}
		this.sheetNameList.set(sheetNum, sheetName);
		return sheetName;
	}
	
	/**
	 * 方法说明: 读取成功的头信息数据存入importedExcelData对象
	 * 头信息:List<Map> 
	 * @param headDataMap 头信息数据
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
	 * 方法说明: 读取成功的行信息数据存入importedExcelData对象
	 * @param detailDataList 行信息数据
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
