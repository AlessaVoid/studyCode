package com.boco.RE.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 枚举
 * @Author zhuhongjiang
 * @Date 2020/1/8 下午5:17
 **/
public class EnumEntity implements Serializable {
	
	private static final long serialVersionUID = -8484648880179015673L;

	/**
	 * 贷款类型 - 全部
	 * text 类型名称
	 * code 类型编码（excel规则模板应用）
	 */
//	public enum AllLoanTypeEnum {
//		GXDK					("各项贷款",								"gxdk"),
//		GXDK_GRDK				("各项贷款-个人贷款",						"gxdk_grdk"),
//		GXDK_GRDK_XEDK			("各项贷款-个人贷款-小额贷款",				"gxdk_grdk_xedk"),
//		GXDK_GRDK_GRSW			("各项贷款-个人贷款-个人商务",				"gxdk_grdk_grsw"),
//		GXDK_GRDK_GRXYKTZ		("各项贷款-个人贷款-个人信用卡透支",			"gxdk_grdk_grxyktz"),
//		GXDK_GRDK_GRXF			("各项贷款-个人贷款-个人消费",				"gxdk_grdk_grxf"),
//		GXDK_GRDK_GRXF_ZFAJDK	("各项贷款-个人贷款-个人消费-住房按揭贷款",	"gxdk_grdk_grxf_zfajdk"),
//		GXDK_GRDK_GRXF_FZFDK	("各项贷款-个人贷款-个人消费-非住房贷款",		"gxdk_grdk_grxf_fzfdk"),
//		GXDK_GSDK				("各项贷款-公司贷款",						"gxdk_gsdk"),
//		GXDK_GSDK_BGDK			("各项贷款-公司贷款-并购贷款",				"gxdk_gsdk_bgdk"),
//		GXDK_GSDK_PFDK			("各项贷款-公司贷款-批发贷款",				"gxdk_gsdk_pfdk"),
//		GXDK_GSDK_XQYDK			("各项贷款-公司贷款-小企业贷款",				"gxdk_gsdk_xqydk"),
//		GXDK_GSDK_FFT			("各项贷款-公司贷款-福费廷",					"gxdk_gsdk_fft"),
//		GXDK_GSDK_GYL			("各项贷款-公司贷款-供应链",					"gxdk_gsdk_gyl"),
//		GXDK_GSDK_MYRZ			("各项贷款-公司贷款-贸易融资",				"gxdk_gsdk_myrz"),
//		GXDK_GSDK_FRZHTZ		("各项贷款-公司贷款-法人账户透支",			"gxdk_gsdk_frzhtz"),
//		GXDK_GSDK_GYLJMYRZ		("各项贷款-公司贷款-供应链及贸易融资",		"gxdk_gsdk_gyljmyrz"),
//		GXDK_PJRZ				("各项贷款-票据融资",						"gxdk_pjrz"),
//		GXDK_PJRZ_ZHUANT		("各项贷款-票据融资-转贴",					"gxdk_pjrz_pjzhuant"),
//		GXDK_PJRZ_ZHIT			("各项贷款-票据融资-直贴",					"gxdk_pjrz_pjzhit"),
//		GXDK_FCKLJRJGDK			("各项贷款-拆放非存款类金融机构贷款",			"gxdk_fckljrjgdk"),
//		GXDK_JWDK				("各项贷款-境外贷款",						"gxdk_jwdk"),
//		GXDK_GXDK				("各项贷款-各项垫款",						"gxdk_gxdk"),
//		KCXJ					("库存现金",								"kcxj"),
//		MRFS					("买入返售",								"mrfs"),
//		ZQTZ					("债券投资",								"zqtz"),
//		YHYCKL					("银行业存款类金融机构往来",					"yhyckl"),
//		CFFCKL					("存放非存款类金融机构款项",					"cffckl");
//
//		AllLoanTypeEnum(String text,String code){
//			this.text=text;
//			this.code=code;
//		}
//		String code;
//		String text;
//		public String getText() {
//			return text;
//		}
//		public String getCode() {
//			return this.code;
//		}
//	}
//
//
//	/**
//	 * 贷款类型 - 银行业分地区导入
//	 * code 		类型编码（excel规则模板应用）
//	 * text 		类型名称
//	 * importName 	模板导入类型名称
//	 **/
//	public enum PsbcRmbBusiLoanTypeEnum {
//		GXDK				(AllLoanTypeEnum.GXDK.code, 			AllLoanTypeEnum.GXDK.text,			 "各项贷款"),
//		GXDK_JNGRDK			(AllLoanTypeEnum.GXDK_GRDK.code,		AllLoanTypeEnum.GXDK_GRDK.text,	 	 "各项贷款-境内个人贷款"),
//		GXDK_JNGRDK_XFDK	(AllLoanTypeEnum.GXDK_GRDK_GRXF.code, 	AllLoanTypeEnum.GXDK_GRDK_GRXF.text, "各项贷款-境内个人贷款-消费贷款"),
//		GXDK_JNGSDK			(AllLoanTypeEnum.GXDK_GSDK.code,		AllLoanTypeEnum.GXDK_GSDK.text,		 "各项贷款-境内公司贷款"),
//		GXDK_PJRZ			(AllLoanTypeEnum.GXDK_PJRZ.code,		AllLoanTypeEnum.GXDK_PJRZ.text,		 "各项贷款-票据融资"),
//		GXDK_FCKLJRJGDK		(AllLoanTypeEnum.GXDK_FCKLJRJGDK.code,	AllLoanTypeEnum.GXDK_FCKLJRJGDK.text,"各项贷款-非存款类金融机构贷款"),
//		GXDK_JWDK			(AllLoanTypeEnum.GXDK_JWDK.code,		AllLoanTypeEnum.GXDK_JWDK.text,		 "各项贷款-境外贷款");
//
//		PsbcRmbBusiLoanTypeEnum(String code, String text, String importName){
//			this.code=code;
//			this.text=text;
//			this.importName=importName;
//		}
//		String code;
//		String text;
//		String importName;
//		public String getText() {
//			return text;
//		}
//		public void setText(String text) {
//			this.text = text;
//		}
//		public String getCode() {
//			return this.code;
//		}
//		public void setCode(String code) {
//			this.code = code;
//		}
//		public String getImportName() {
//			return this.importName;
//		}
//		public void setImportName(String importName) {
//			this.importName = importName;
//		}
//
//		public 	static Map<String,String> getMap(){
//			Map<String, String> enumMap = new LinkedHashMap<>();
//			for(PsbcRmbBusiLoanTypeEnum one : PsbcRmbBusiLoanTypeEnum.values()){
//				enumMap.put(one.getCode(), one.getImportName());
//			}
//			return enumMap;
//		}
//	}
//
//	/**
//	 * 贷款类型 - 银行业分机构导入
//	 * code 		类型编码（excel规则模板应用）
//	 * text 		类型名称
//	 * importName 	模板导入类型名称
//	 **/
//	public enum BankRmbBusiLoanTypeEnum {
//		GXDK				(AllLoanTypeEnum.GXDK.code, 			AllLoanTypeEnum.GXDK.text,			 "各项贷款"),
//		GXDK_JNGRDK			(AllLoanTypeEnum.GXDK_GRDK.code,		AllLoanTypeEnum.GXDK_GRDK.text,	 	 "各项贷款-境内个人贷款"),
//		GXDK_JNGRDK_XFDK	(AllLoanTypeEnum.GXDK_GRDK_GRXF.code, 	AllLoanTypeEnum.GXDK_GRDK_GRXF.text, "各项贷款-境内个人贷款-消费贷款"),
//		GXDK_JNGSDK			(AllLoanTypeEnum.GXDK_GSDK.code,		AllLoanTypeEnum.GXDK_GSDK.text,		 "各项贷款-境内公司贷款"),
//		GXDK_JNGSDK_BGDK	(AllLoanTypeEnum.GXDK_GSDK_BGDK.code,	AllLoanTypeEnum.GXDK_GSDK_BGDK.text, "各项贷款-境内公司贷款-并购贷款"),
//		GXDK_PJRZ			(AllLoanTypeEnum.GXDK_PJRZ.code,		AllLoanTypeEnum.GXDK_PJRZ.text,		 "各项贷款-票据融资"),
//		GXDK_FCKLJRJGDK		(AllLoanTypeEnum.GXDK_FCKLJRJGDK.code,	AllLoanTypeEnum.GXDK_FCKLJRJGDK.text,"各项贷款-非存款类金融机构贷款"),
//		GXDK_JWDK			(AllLoanTypeEnum.GXDK_JWDK.code,		AllLoanTypeEnum.GXDK_JWDK.text,		 "各项贷款-境外贷款"),
//		GXDK_GXDK			(AllLoanTypeEnum.GXDK_GXDK.code,		AllLoanTypeEnum.GXDK_GXDK.text,		 "各项贷款-各项垫款"),
//		KCXJ				(AllLoanTypeEnum.KCXJ.code,				AllLoanTypeEnum.KCXJ.text,			 "库存现金"),
//		MRFS				(AllLoanTypeEnum.MRFS.code,				AllLoanTypeEnum.MRFS.text,			 "买入返售"),
//		ZQTZ				(AllLoanTypeEnum.ZQTZ.code,				AllLoanTypeEnum.ZQTZ.text,			 "债券投资"),
//		YHYCKL				(AllLoanTypeEnum.YHYCKL.code,			AllLoanTypeEnum.YHYCKL.text,		 "银行业存款类金融机构往来"),
//		CFFCKL				(AllLoanTypeEnum.CFFCKL.code,			AllLoanTypeEnum.CFFCKL.text,		 "存放非存款类金融机构款项");
//
//		BankRmbBusiLoanTypeEnum(String code, String text, String importName){
//			this.code=code;
//			this.text=text;
//			this.importName=importName;
//		}
//		String code;
//		String text;
//		String importName;
//		public String getText() {
//			return text;
//		}
//		public void setText(String text) {
//			this.text = text;
//		}
//		public String getCode() {
//			return this.code;
//		}
//		public void setCode(String code) {
//			this.code = code;
//		}
//		public String getImportName() {
//			return this.importName;
//		}
//		public void setImportName(String importName) {
//			this.importName = importName;
//		}
//
//		public 	static Map<String,String> getMap(){
//			Map<String, String> enumMap = new LinkedHashMap<>();
//			for(BankRmbBusiLoanTypeEnum one : BankRmbBusiLoanTypeEnum.values()){
//				enumMap.put(one.getCode(), one.getImportName());
//			}
//			return enumMap;
//		}
//	}
//
//	/**
//	 * 贷款类型 - 人民币贷款日报汇总导入（邮储分产品）
//	 * code 		类型编码
//	 * text 		类型名称
//	 * importName 	模板导入类型名称（导入映射）
//	 **/
//	public enum PsbcRmbLoanSumLoanTypeEnum {
//		GXDK					(AllLoanTypeEnum.GXDK.code,			 		AllLoanTypeEnum.GXDK.text,					"各项贷款(人行口径)"),
//		GXDK_GRDK				(AllLoanTypeEnum.GXDK_GRDK.code,	 		AllLoanTypeEnum.GXDK_GRDK.text,				"（一）个人贷款"),
//		GXDK_GRDK_XEDK			(AllLoanTypeEnum.GXDK_GRDK_XEDK.code,		AllLoanTypeEnum.GXDK_GRDK_XEDK.text,		"小额贷款"),
//		GXDK_GRDK_GRSW			(AllLoanTypeEnum.GXDK_GRDK_GRSW.code,		AllLoanTypeEnum.GXDK_GRDK_GRSW.text,		"个人商务"),
//		GXDK_GRDK_GRXF			(AllLoanTypeEnum.GXDK_GRDK_GRXF.code,		AllLoanTypeEnum.GXDK_GRDK_GRXF.text,		"个人消费"),
//		GXDK_GRDK_GRXF_ZFAJDK 	(AllLoanTypeEnum.GXDK_GRDK_GRXF_ZFAJDK.code,AllLoanTypeEnum.GXDK_GRDK_GRXF_ZFAJDK.text,	"其中：住房按揭贷款"),
//		GXDK_GRDK_GRXF_FZFDK	(AllLoanTypeEnum.GXDK_GRDK_GRXF_FZFDK.code,	AllLoanTypeEnum.GXDK_GRDK_GRXF_FZFDK.text,	"其中：非住房贷款"),
//		GXDK_GRDK_GRXYKTZ		(AllLoanTypeEnum.GXDK_GRDK_GRXYKTZ.code,	AllLoanTypeEnum.GXDK_GRDK_GRXYKTZ.text,		"个人信用卡透支"),
//		GXDK_GSDK				(AllLoanTypeEnum.GXDK_GSDK.code,			AllLoanTypeEnum.GXDK_GSDK.text,				"（二）公司贷款"),
//		GXDK_GSDK_PFDK			(AllLoanTypeEnum.GXDK_GSDK_PFDK.code,		AllLoanTypeEnum.GXDK_GSDK_PFDK.text,		"批发贷款"),
//		GXDK_GSDK_XQYDK			(AllLoanTypeEnum.GXDK_GSDK_XQYDK.code,		AllLoanTypeEnum.GXDK_GSDK_XQYDK.text,		"小企业贷款"),
//		GXDK_GSDK_FFT			(AllLoanTypeEnum.GXDK_GSDK_FFT.code,		AllLoanTypeEnum.GXDK_GSDK_FFT.text,			"福费廷"),
//		GXDK_GSDK_GYL			(AllLoanTypeEnum.GXDK_GSDK_GYL.code,		AllLoanTypeEnum.GXDK_GSDK_GYL.text,			"供应链"),
//		GXDK_GSDK_MYRZ			(AllLoanTypeEnum.GXDK_GSDK_MYRZ.code,		AllLoanTypeEnum.GXDK_GSDK_MYRZ.text,		"贸易融资"),
//		GXDK_GSDK_FRZHTZ		(AllLoanTypeEnum.GXDK_GSDK_FRZHTZ.code,		AllLoanTypeEnum.GXDK_GSDK_FRZHTZ.text,		"法人账户透支"),
//		GXDK_PJRZ				(AllLoanTypeEnum.GXDK_PJRZ.code,			AllLoanTypeEnum.GXDK_PJRZ.text,				"（三）票据融资"),
//		GXDK_PJRZ_ZHUANT		(AllLoanTypeEnum.GXDK_PJRZ_ZHUANT.code,		AllLoanTypeEnum.GXDK_PJRZ_ZHUANT.text,		"转贴"),
//		GXDK_PJRZ_ZHIT			(AllLoanTypeEnum.GXDK_PJRZ_ZHIT.code,		AllLoanTypeEnum.GXDK_PJRZ_ZHIT.text,		"直贴"),
//		GXDK_CFFCKL				(AllLoanTypeEnum.GXDK_FCKLJRJGDK.code,		AllLoanTypeEnum.GXDK_FCKLJRJGDK.text,		"（四）拆放非存款类金融机构");
//
//		PsbcRmbLoanSumLoanTypeEnum(String code, String text, String importName){
//			this.code=code;
//			this.text=text;
//			this.importName=importName;
//		}
//		String code;
//		String text;
//		String importName;
//		public String getText() {
//			return text;
//		}
//		public void setText(String text) {
//			this.text = text;
//		}
//		public String getCode() {
//			return this.code;
//		}
//		public void setCode(String code) {
//			this.code = code;
//		}
//		public String getImportName() {
//			return this.importName;
//		}
//		public void setImportName(String importName) {
//			this.importName = importName;
//		}
//
//		public 	static Map<String,String> getMap(){
//			Map<String, String> enumMap = new LinkedHashMap<>();
//			for(PsbcRmbLoanSumLoanTypeEnum one : PsbcRmbLoanSumLoanTypeEnum.values()){
//				enumMap.put(one.getCode(), one.getText());
//			}
//			return enumMap;
//		}
//	}
//
//	/**
//	 * 贷款类型 - 人民币贷款日报导入（邮储总分行）
//	 * code 		类型编码
//	 * text 		类型名称
//	 * importName 	模板导入类型名称
//	 * type			模板类型
//	 **/
//	public enum PsbcRmbLoanDayLoanTypeEnum {
//		GXDK					(AllLoanTypeEnum.GXDK.code,					AllLoanTypeEnum.GXDK.text,                 "各项贷款(银监口径)",					Constant.UPLOAD_TYPE_4),
//		GXDK_GRDK				(AllLoanTypeEnum.GXDK_GRDK.code,			AllLoanTypeEnum.GXDK_GRDK.text,            "各项贷款-个人贷款",					Constant.UPLOAD_TYPE_4),
//		GXDK_GRDK_XEDK			(AllLoanTypeEnum.GXDK_GRDK_XEDK.code,		AllLoanTypeEnum.GXDK_GRDK_XEDK.text,       "各项贷款-个人贷款-小额贷款",			Constant.UPLOAD_TYPE_5),
//		GXDK_GRDK_GSDK			(AllLoanTypeEnum.GXDK_GRDK_GRSW.code,		AllLoanTypeEnum.GXDK_GRDK_GRSW.text,	   "各项贷款-个人贷款-个商贷款",			Constant.UPLOAD_TYPE_5),
//		GXDK_GRDK_XFDK			(AllLoanTypeEnum.GXDK_GRDK_GRXF.code,		AllLoanTypeEnum.GXDK_GRDK_GRXF.text,	   "各项贷款-个人贷款-消费贷款",			Constant.UPLOAD_TYPE_5),
//		GXDK_GRDK_XFDK_ZFAJDK	(AllLoanTypeEnum.GXDK_GRDK_GRXF_ZFAJDK.code,AllLoanTypeEnum.GXDK_GRDK_GRXF_ZFAJDK.text,"各项贷款-个人贷款-消费贷款-住房按揭贷款",Constant.UPLOAD_TYPE_5),
//		GXDK_GSDK				(AllLoanTypeEnum.GXDK_GSDK.code,			AllLoanTypeEnum.GXDK_GSDK.text,			   "各项贷款-公司贷款",					Constant.UPLOAD_TYPE_4),
//		GXDK_GSDK_PFDK			(AllLoanTypeEnum.GXDK_GSDK_PFDK.code,		AllLoanTypeEnum.GXDK_GSDK_PFDK.text,       "各项贷款-公司贷款-批发贷款",			Constant.UPLOAD_TYPE_6),
//		GXDK_GSDK_XQYDK			(AllLoanTypeEnum.GXDK_GSDK_XQYDK.code,		AllLoanTypeEnum.GXDK_GSDK_XQYDK.text,      "各项贷款-公司贷款-小企业贷款",			Constant.UPLOAD_TYPE_6),
//		GXDK_GSDK_FFT			(AllLoanTypeEnum.GXDK_GSDK_FFT.code,		AllLoanTypeEnum.GXDK_GSDK_FFT.text,        "各项贷款-公司贷款-福费廷",				Constant.UPLOAD_TYPE_6),
//		GXDK_GSDK_GYLJMYRZ		(AllLoanTypeEnum.GXDK_GSDK_GYLJMYRZ.code,	AllLoanTypeEnum.GXDK_GSDK_GYLJMYRZ.text,   "各项贷款-公司贷款-供应链及贸易融资",		Constant.UPLOAD_TYPE_6),
//		GXDK_PJRZ				(AllLoanTypeEnum.GXDK_PJRZ.code,			AllLoanTypeEnum.GXDK_PJRZ.text,            "各项贷款-票据融资",					Constant.UPLOAD_TYPE_4),
//		GXDK_PJRZ_PJZHUANT		(AllLoanTypeEnum.GXDK_PJRZ_ZHUANT.code,		AllLoanTypeEnum.GXDK_PJRZ_ZHUANT.text,     "各项贷款-票据融资-票据转贴",			Constant.UPLOAD_TYPE_4),
//		GXDK_PJRZ_PJZHIT		(AllLoanTypeEnum.GXDK_PJRZ_ZHIT.code,		AllLoanTypeEnum.GXDK_PJRZ_ZHIT.text,       "各项贷款-票据融资-票据直贴",			Constant.UPLOAD_TYPE_4);
//
//		PsbcRmbLoanDayLoanTypeEnum(String code, String text, String importName, String type){
//			this.code=code;
//			this.text=text;
//			this.importName=importName;
//			this.type=type;
//		}
//		String code;
//		String text;
//		String importName;
//		String type;
//		public String getText() {
//			return text;
//		}
//		public void setText(String text) {
//			this.text = text;
//		}
//		public String getCode() {
//			return this.code;
//		}
//		public void setCode(String code) {
//			this.code = code;
//		}
//		public String getImportName() {
//			return this.importName;
//		}
//		public void setImportName(String importName) {
//			this.importName = importName;
//		}
//		public String getType() {
//			return type;
//		}
//		public void setType(String type) {
//			this.type = type;
//		}
//
//		public 	static Map<String,String> getMap(){
//			Map<String, String> enumMap = new LinkedHashMap<>();
//			for(PsbcRmbLoanDayLoanTypeEnum one : PsbcRmbLoanDayLoanTypeEnum.values()){
//				enumMap.put(one.getCode(), one.getImportName());
//			}
//			return enumMap;
//		}
//	}
//
//	/**
//	 * 银行名称、简称
//	 * @Author zhuhongjiang
//	 * @Date 2020/1/8 下午5:44
//	 **/
//	public enum BankNameEnum {
//		PSBC		("PSBC",	 "邮储"),
//		ICBC		("ICBC",	 "工行"),
//		ABC			("ABC",	 "农行"),
//		BOC			("BOC",	 "中行"),
//		CCB			("CCB",	 "建行"),
//		COMM		("COMM",	 "交行"),
//		CITIC		("CITIC",	 "中信"),
//		CMB			("CMB",	 "招商"),
//		CEB			("CEB",	 "光大"),
//		SPDB		("SPDB",	 "浦发"),
//		GDB			("GDB",	 "广发"),
//		HXBANK		("HXBANK", "华夏"),
//		CIB			("CIB",	 "兴业"),
//		SPABANK		("SPABANK","平安"),
//		CMBC		("CMBC",	 "民生");
//
//		BankNameEnum(String code,String text){
//			this.code=code;
//			this.text=text;
//		}
//		String code;
//		String text;
//		public String getText() {
//			return text;
//		}
//		public void setText(String text) {
//			this.text = text;
//		}
//		public String getCode() {
//			return this.code;
//		}
//		public void setCode(String code) {
//			this.code = code;
//		}
//
//		public static String getCode(String text){
//			String code = null;
//			for(BankNameEnum one : BankNameEnum.values()){
//				if(one.getText().equals(text)){
//					code = one.getCode();
//					break;
//				}
//			}
//			return code;
//		}
//
//		public static Map<String,String> getMap(){
//			Map<String, String> enumMap = new LinkedHashMap<>();
//			for(BankNameEnum one : BankNameEnum.values()){
//				enumMap.put(one.getCode(), one.getText());
//			}
//			return enumMap;
//		}
//	}


	/**
	 * 贷款类型 - 人民币贷款情况报表管理 - 产品结构报表
	 * code 		类型编码
	 * text 		类型名称
	 * alias 	    类型名称（别名）
	 **/
	public enum RMBProMixLoanTypeEnum {
		GXDK					("","",	"各项贷款"),
		GXDK_GRLDK				("","",	"个人类贷款"),
		GXDK_GRLDK_XYK			("","",	"1.信用卡"),
		GXDK_GRLDK_GRJYXDK		("","",	"2.个人经营性贷款"),
		GXDK_GRLDK_ZFDK			("","",	"3.住房贷款"),
		GXDK_GRLDK_QTXFD		("","",	"4.其他消费贷"),
		GXDK_GSLDK				("","",	"公司类贷款"),
		GXDK_GSLDK_XQY			("","",	"1.小企业"),
		GXDK_GSLDK_GSDK			("","",	"2.公司贷款"),
		GXDK_GSLDK_MYRZ			("","",	"3.贸易融资"),
		GXDK_PJFFT				("","",	"票据福费廷"),
		GXDK_PJFFT_ZHIT			("","",	"1.直贴"),
		GXDK_PJFFT_ZHUANT		("","",	"2.转贴"),
		GXDK_PJFFT_FFT			("","",	"3.福费廷"),
		GXDK_CFFY				("","",	"拆放非银");

		RMBProMixLoanTypeEnum(String code, String text, String alias){
			this.code=code;
			this.text=text;
			this.alias=alias;
		}
		String code;
		String text;
		String alias;
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getCode() {
			return this.code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getAlias() {
			return this.alias;
		}
		public void setAlias(String alias) {
			this.alias = alias;
		}

		public 	static List<Map<String,String>> getList(){
			List<Map<String,String>> list = new ArrayList<>();
			for(RMBProMixLoanTypeEnum one : RMBProMixLoanTypeEnum.values()){
				Map<String, String> enumMap = new HashMap<>();
				enumMap.put("code", one.getCode());
				enumMap.put("alias", one.getAlias());
				list.add(enumMap);
			}
			return list;
		}
	}

	/**
	 * 贷款类型 - 人民币贷款情况报表管理 - 机构、区域结构(年)报表 / 机构、区域结构(月)报表
	 * code 		类型编码
	 * text 		类型名称
	 * alias 	    类型名称（别名）
	 **/
	public enum RMBOrganAreaLoanTypeEnum {
		GXDK				("","",	"各项贷款"),
		LXDK				("","",	"两小贷款"),
		QTSTDK				("","",	"其他实体贷款"),
		PJFFT				("","",	"票据福费廷");

		RMBOrganAreaLoanTypeEnum(String code, String text, String alias){
			this.code=code;
			this.text=text;
			this.alias=alias;
		}
		String code;
		String text;
		String alias;
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getCode() {
			return this.code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getAlias() {
			return this.alias;
		}
		public void setAlias(String alias) {
			this.alias = alias;
		}

		public 	static List<Map<String,String>> getList(){
			List<Map<String,String>> list = new ArrayList<>();
			for(RMBOrganAreaLoanTypeEnum one : RMBOrganAreaLoanTypeEnum.values()){
				Map<String, String> enumMap = new HashMap<>();
				enumMap.put("code", one.getCode());
				enumMap.put("alias", one.getAlias());
				list.add(enumMap);
			}
			return list;
		}
	}

	/**
	 * 贷款类型 - 人民币贷款情况报表管理 - 流量结构报表
	 * code 		类型编码
	 * text 		类型名称
	 * alias 	    类型名称（别名）
	 **/
	public enum RMBFlowMixLoanTypeEnum {
		GRJYXDK			("","",	"个人经营性贷款"),
		XQY				("","",	"小企业"),
		ZFAJDK			("","",	"住房按揭贷款"),
		QTXFDK			("","",	"其他消费贷款"),
		GYLYMYRZ		("","",	"供应链与贸易融资"),
		GSDK			("","",	"公司贷款"),
		ZHUANT			("","",	"转贴"),
		ZHIT			("","",	"直贴"),
		FFT				("","",	"福费廷"),
		GRXYKTZ			("","",	"个人信用卡透支"),
		CFFY			("","",	"拆放非银");

		RMBFlowMixLoanTypeEnum(String code, String text, String alias){
			this.code=code;
			this.text=text;
			this.alias=alias;
		}
		String code;
		String text;
		String alias;
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getCode() {
			return this.code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getAlias() {
			return this.alias;
		}
		public void setAlias(String alias) {
			this.alias = alias;
		}

		public 	static List<Map<String,String>> getList(){
			List<Map<String,String>> list = new ArrayList<>();
			for(RMBFlowMixLoanTypeEnum one : RMBFlowMixLoanTypeEnum.values()){
				Map<String, String> enumMap = new HashMap<>();
				enumMap.put("code", one.getCode());
				enumMap.put("alias", one.getAlias());
				list.add(enumMap);
			}
			return list;
		}
	}
}
