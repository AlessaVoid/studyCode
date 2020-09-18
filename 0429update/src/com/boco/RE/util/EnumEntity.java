package com.boco.RE.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ö��
 * @Author zhuhongjiang
 * @Date 2020/1/8 ����5:17
 **/
public class EnumEntity implements Serializable {
	
	private static final long serialVersionUID = -8484648880179015673L;

	/**
	 * �������� - ȫ��
	 * text ��������
	 * code ���ͱ��루excel����ģ��Ӧ�ã�
	 */
//	public enum AllLoanTypeEnum {
//		GXDK					("�������",								"gxdk"),
//		GXDK_GRDK				("�������-���˴���",						"gxdk_grdk"),
//		GXDK_GRDK_XEDK			("�������-���˴���-С�����",				"gxdk_grdk_xedk"),
//		GXDK_GRDK_GRSW			("�������-���˴���-��������",				"gxdk_grdk_grsw"),
//		GXDK_GRDK_GRXYKTZ		("�������-���˴���-�������ÿ�͸֧",			"gxdk_grdk_grxyktz"),
//		GXDK_GRDK_GRXF			("�������-���˴���-��������",				"gxdk_grdk_grxf"),
//		GXDK_GRDK_GRXF_ZFAJDK	("�������-���˴���-��������-ס�����Ҵ���",	"gxdk_grdk_grxf_zfajdk"),
//		GXDK_GRDK_GRXF_FZFDK	("�������-���˴���-��������-��ס������",		"gxdk_grdk_grxf_fzfdk"),
//		GXDK_GSDK				("�������-��˾����",						"gxdk_gsdk"),
//		GXDK_GSDK_BGDK			("�������-��˾����-��������",				"gxdk_gsdk_bgdk"),
//		GXDK_GSDK_PFDK			("�������-��˾����-��������",				"gxdk_gsdk_pfdk"),
//		GXDK_GSDK_XQYDK			("�������-��˾����-С��ҵ����",				"gxdk_gsdk_xqydk"),
//		GXDK_GSDK_FFT			("�������-��˾����-����͢",					"gxdk_gsdk_fft"),
//		GXDK_GSDK_GYL			("�������-��˾����-��Ӧ��",					"gxdk_gsdk_gyl"),
//		GXDK_GSDK_MYRZ			("�������-��˾����-ó������",				"gxdk_gsdk_myrz"),
//		GXDK_GSDK_FRZHTZ		("�������-��˾����-�����˻�͸֧",			"gxdk_gsdk_frzhtz"),
//		GXDK_GSDK_GYLJMYRZ		("�������-��˾����-��Ӧ����ó������",		"gxdk_gsdk_gyljmyrz"),
//		GXDK_PJRZ				("�������-Ʊ������",						"gxdk_pjrz"),
//		GXDK_PJRZ_ZHUANT		("�������-Ʊ������-ת��",					"gxdk_pjrz_pjzhuant"),
//		GXDK_PJRZ_ZHIT			("�������-Ʊ������-ֱ��",					"gxdk_pjrz_pjzhit"),
//		GXDK_FCKLJRJGDK			("�������-��ŷǴ������ڻ�������",			"gxdk_fckljrjgdk"),
//		GXDK_JWDK				("�������-�������",						"gxdk_jwdk"),
//		GXDK_GXDK				("�������-������",						"gxdk_gxdk"),
//		KCXJ					("����ֽ�",								"kcxj"),
//		MRFS					("���뷵��",								"mrfs"),
//		ZQTZ					("ծȯͶ��",								"zqtz"),
//		YHYCKL					("����ҵ�������ڻ�������",					"yhyckl"),
//		CFFCKL					("��ŷǴ������ڻ�������",					"cffckl");
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
//	 * �������� - ����ҵ�ֵ�������
//	 * code 		���ͱ��루excel����ģ��Ӧ�ã�
//	 * text 		��������
//	 * importName 	ģ�嵼����������
//	 **/
//	public enum PsbcRmbBusiLoanTypeEnum {
//		GXDK				(AllLoanTypeEnum.GXDK.code, 			AllLoanTypeEnum.GXDK.text,			 "�������"),
//		GXDK_JNGRDK			(AllLoanTypeEnum.GXDK_GRDK.code,		AllLoanTypeEnum.GXDK_GRDK.text,	 	 "�������-���ڸ��˴���"),
//		GXDK_JNGRDK_XFDK	(AllLoanTypeEnum.GXDK_GRDK_GRXF.code, 	AllLoanTypeEnum.GXDK_GRDK_GRXF.text, "�������-���ڸ��˴���-���Ѵ���"),
//		GXDK_JNGSDK			(AllLoanTypeEnum.GXDK_GSDK.code,		AllLoanTypeEnum.GXDK_GSDK.text,		 "�������-���ڹ�˾����"),
//		GXDK_PJRZ			(AllLoanTypeEnum.GXDK_PJRZ.code,		AllLoanTypeEnum.GXDK_PJRZ.text,		 "�������-Ʊ������"),
//		GXDK_FCKLJRJGDK		(AllLoanTypeEnum.GXDK_FCKLJRJGDK.code,	AllLoanTypeEnum.GXDK_FCKLJRJGDK.text,"�������-�Ǵ������ڻ�������"),
//		GXDK_JWDK			(AllLoanTypeEnum.GXDK_JWDK.code,		AllLoanTypeEnum.GXDK_JWDK.text,		 "�������-�������");
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
//	 * �������� - ����ҵ�ֻ�������
//	 * code 		���ͱ��루excel����ģ��Ӧ�ã�
//	 * text 		��������
//	 * importName 	ģ�嵼����������
//	 **/
//	public enum BankRmbBusiLoanTypeEnum {
//		GXDK				(AllLoanTypeEnum.GXDK.code, 			AllLoanTypeEnum.GXDK.text,			 "�������"),
//		GXDK_JNGRDK			(AllLoanTypeEnum.GXDK_GRDK.code,		AllLoanTypeEnum.GXDK_GRDK.text,	 	 "�������-���ڸ��˴���"),
//		GXDK_JNGRDK_XFDK	(AllLoanTypeEnum.GXDK_GRDK_GRXF.code, 	AllLoanTypeEnum.GXDK_GRDK_GRXF.text, "�������-���ڸ��˴���-���Ѵ���"),
//		GXDK_JNGSDK			(AllLoanTypeEnum.GXDK_GSDK.code,		AllLoanTypeEnum.GXDK_GSDK.text,		 "�������-���ڹ�˾����"),
//		GXDK_JNGSDK_BGDK	(AllLoanTypeEnum.GXDK_GSDK_BGDK.code,	AllLoanTypeEnum.GXDK_GSDK_BGDK.text, "�������-���ڹ�˾����-��������"),
//		GXDK_PJRZ			(AllLoanTypeEnum.GXDK_PJRZ.code,		AllLoanTypeEnum.GXDK_PJRZ.text,		 "�������-Ʊ������"),
//		GXDK_FCKLJRJGDK		(AllLoanTypeEnum.GXDK_FCKLJRJGDK.code,	AllLoanTypeEnum.GXDK_FCKLJRJGDK.text,"�������-�Ǵ������ڻ�������"),
//		GXDK_JWDK			(AllLoanTypeEnum.GXDK_JWDK.code,		AllLoanTypeEnum.GXDK_JWDK.text,		 "�������-�������"),
//		GXDK_GXDK			(AllLoanTypeEnum.GXDK_GXDK.code,		AllLoanTypeEnum.GXDK_GXDK.text,		 "�������-������"),
//		KCXJ				(AllLoanTypeEnum.KCXJ.code,				AllLoanTypeEnum.KCXJ.text,			 "����ֽ�"),
//		MRFS				(AllLoanTypeEnum.MRFS.code,				AllLoanTypeEnum.MRFS.text,			 "���뷵��"),
//		ZQTZ				(AllLoanTypeEnum.ZQTZ.code,				AllLoanTypeEnum.ZQTZ.text,			 "ծȯͶ��"),
//		YHYCKL				(AllLoanTypeEnum.YHYCKL.code,			AllLoanTypeEnum.YHYCKL.text,		 "����ҵ�������ڻ�������"),
//		CFFCKL				(AllLoanTypeEnum.CFFCKL.code,			AllLoanTypeEnum.CFFCKL.text,		 "��ŷǴ������ڻ�������");
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
//	 * �������� - ����Ҵ����ձ����ܵ��루�ʴ��ֲ�Ʒ��
//	 * code 		���ͱ���
//	 * text 		��������
//	 * importName 	ģ�嵼���������ƣ�����ӳ�䣩
//	 **/
//	public enum PsbcRmbLoanSumLoanTypeEnum {
//		GXDK					(AllLoanTypeEnum.GXDK.code,			 		AllLoanTypeEnum.GXDK.text,					"�������(���пھ�)"),
//		GXDK_GRDK				(AllLoanTypeEnum.GXDK_GRDK.code,	 		AllLoanTypeEnum.GXDK_GRDK.text,				"��һ�����˴���"),
//		GXDK_GRDK_XEDK			(AllLoanTypeEnum.GXDK_GRDK_XEDK.code,		AllLoanTypeEnum.GXDK_GRDK_XEDK.text,		"С�����"),
//		GXDK_GRDK_GRSW			(AllLoanTypeEnum.GXDK_GRDK_GRSW.code,		AllLoanTypeEnum.GXDK_GRDK_GRSW.text,		"��������"),
//		GXDK_GRDK_GRXF			(AllLoanTypeEnum.GXDK_GRDK_GRXF.code,		AllLoanTypeEnum.GXDK_GRDK_GRXF.text,		"��������"),
//		GXDK_GRDK_GRXF_ZFAJDK 	(AllLoanTypeEnum.GXDK_GRDK_GRXF_ZFAJDK.code,AllLoanTypeEnum.GXDK_GRDK_GRXF_ZFAJDK.text,	"���У�ס�����Ҵ���"),
//		GXDK_GRDK_GRXF_FZFDK	(AllLoanTypeEnum.GXDK_GRDK_GRXF_FZFDK.code,	AllLoanTypeEnum.GXDK_GRDK_GRXF_FZFDK.text,	"���У���ס������"),
//		GXDK_GRDK_GRXYKTZ		(AllLoanTypeEnum.GXDK_GRDK_GRXYKTZ.code,	AllLoanTypeEnum.GXDK_GRDK_GRXYKTZ.text,		"�������ÿ�͸֧"),
//		GXDK_GSDK				(AllLoanTypeEnum.GXDK_GSDK.code,			AllLoanTypeEnum.GXDK_GSDK.text,				"��������˾����"),
//		GXDK_GSDK_PFDK			(AllLoanTypeEnum.GXDK_GSDK_PFDK.code,		AllLoanTypeEnum.GXDK_GSDK_PFDK.text,		"��������"),
//		GXDK_GSDK_XQYDK			(AllLoanTypeEnum.GXDK_GSDK_XQYDK.code,		AllLoanTypeEnum.GXDK_GSDK_XQYDK.text,		"С��ҵ����"),
//		GXDK_GSDK_FFT			(AllLoanTypeEnum.GXDK_GSDK_FFT.code,		AllLoanTypeEnum.GXDK_GSDK_FFT.text,			"����͢"),
//		GXDK_GSDK_GYL			(AllLoanTypeEnum.GXDK_GSDK_GYL.code,		AllLoanTypeEnum.GXDK_GSDK_GYL.text,			"��Ӧ��"),
//		GXDK_GSDK_MYRZ			(AllLoanTypeEnum.GXDK_GSDK_MYRZ.code,		AllLoanTypeEnum.GXDK_GSDK_MYRZ.text,		"ó������"),
//		GXDK_GSDK_FRZHTZ		(AllLoanTypeEnum.GXDK_GSDK_FRZHTZ.code,		AllLoanTypeEnum.GXDK_GSDK_FRZHTZ.text,		"�����˻�͸֧"),
//		GXDK_PJRZ				(AllLoanTypeEnum.GXDK_PJRZ.code,			AllLoanTypeEnum.GXDK_PJRZ.text,				"������Ʊ������"),
//		GXDK_PJRZ_ZHUANT		(AllLoanTypeEnum.GXDK_PJRZ_ZHUANT.code,		AllLoanTypeEnum.GXDK_PJRZ_ZHUANT.text,		"ת��"),
//		GXDK_PJRZ_ZHIT			(AllLoanTypeEnum.GXDK_PJRZ_ZHIT.code,		AllLoanTypeEnum.GXDK_PJRZ_ZHIT.text,		"ֱ��"),
//		GXDK_CFFCKL				(AllLoanTypeEnum.GXDK_FCKLJRJGDK.code,		AllLoanTypeEnum.GXDK_FCKLJRJGDK.text,		"���ģ���ŷǴ������ڻ���");
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
//	 * �������� - ����Ҵ����ձ����루�ʴ��ܷ��У�
//	 * code 		���ͱ���
//	 * text 		��������
//	 * importName 	ģ�嵼����������
//	 * type			ģ������
//	 **/
//	public enum PsbcRmbLoanDayLoanTypeEnum {
//		GXDK					(AllLoanTypeEnum.GXDK.code,					AllLoanTypeEnum.GXDK.text,                 "�������(����ھ�)",					Constant.UPLOAD_TYPE_4),
//		GXDK_GRDK				(AllLoanTypeEnum.GXDK_GRDK.code,			AllLoanTypeEnum.GXDK_GRDK.text,            "�������-���˴���",					Constant.UPLOAD_TYPE_4),
//		GXDK_GRDK_XEDK			(AllLoanTypeEnum.GXDK_GRDK_XEDK.code,		AllLoanTypeEnum.GXDK_GRDK_XEDK.text,       "�������-���˴���-С�����",			Constant.UPLOAD_TYPE_5),
//		GXDK_GRDK_GSDK			(AllLoanTypeEnum.GXDK_GRDK_GRSW.code,		AllLoanTypeEnum.GXDK_GRDK_GRSW.text,	   "�������-���˴���-���̴���",			Constant.UPLOAD_TYPE_5),
//		GXDK_GRDK_XFDK			(AllLoanTypeEnum.GXDK_GRDK_GRXF.code,		AllLoanTypeEnum.GXDK_GRDK_GRXF.text,	   "�������-���˴���-���Ѵ���",			Constant.UPLOAD_TYPE_5),
//		GXDK_GRDK_XFDK_ZFAJDK	(AllLoanTypeEnum.GXDK_GRDK_GRXF_ZFAJDK.code,AllLoanTypeEnum.GXDK_GRDK_GRXF_ZFAJDK.text,"�������-���˴���-���Ѵ���-ס�����Ҵ���",Constant.UPLOAD_TYPE_5),
//		GXDK_GSDK				(AllLoanTypeEnum.GXDK_GSDK.code,			AllLoanTypeEnum.GXDK_GSDK.text,			   "�������-��˾����",					Constant.UPLOAD_TYPE_4),
//		GXDK_GSDK_PFDK			(AllLoanTypeEnum.GXDK_GSDK_PFDK.code,		AllLoanTypeEnum.GXDK_GSDK_PFDK.text,       "�������-��˾����-��������",			Constant.UPLOAD_TYPE_6),
//		GXDK_GSDK_XQYDK			(AllLoanTypeEnum.GXDK_GSDK_XQYDK.code,		AllLoanTypeEnum.GXDK_GSDK_XQYDK.text,      "�������-��˾����-С��ҵ����",			Constant.UPLOAD_TYPE_6),
//		GXDK_GSDK_FFT			(AllLoanTypeEnum.GXDK_GSDK_FFT.code,		AllLoanTypeEnum.GXDK_GSDK_FFT.text,        "�������-��˾����-����͢",				Constant.UPLOAD_TYPE_6),
//		GXDK_GSDK_GYLJMYRZ		(AllLoanTypeEnum.GXDK_GSDK_GYLJMYRZ.code,	AllLoanTypeEnum.GXDK_GSDK_GYLJMYRZ.text,   "�������-��˾����-��Ӧ����ó������",		Constant.UPLOAD_TYPE_6),
//		GXDK_PJRZ				(AllLoanTypeEnum.GXDK_PJRZ.code,			AllLoanTypeEnum.GXDK_PJRZ.text,            "�������-Ʊ������",					Constant.UPLOAD_TYPE_4),
//		GXDK_PJRZ_PJZHUANT		(AllLoanTypeEnum.GXDK_PJRZ_ZHUANT.code,		AllLoanTypeEnum.GXDK_PJRZ_ZHUANT.text,     "�������-Ʊ������-Ʊ��ת��",			Constant.UPLOAD_TYPE_4),
//		GXDK_PJRZ_PJZHIT		(AllLoanTypeEnum.GXDK_PJRZ_ZHIT.code,		AllLoanTypeEnum.GXDK_PJRZ_ZHIT.text,       "�������-Ʊ������-Ʊ��ֱ��",			Constant.UPLOAD_TYPE_4);
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
//	 * �������ơ����
//	 * @Author zhuhongjiang
//	 * @Date 2020/1/8 ����5:44
//	 **/
//	public enum BankNameEnum {
//		PSBC		("PSBC",	 "�ʴ�"),
//		ICBC		("ICBC",	 "����"),
//		ABC			("ABC",	 "ũ��"),
//		BOC			("BOC",	 "����"),
//		CCB			("CCB",	 "����"),
//		COMM		("COMM",	 "����"),
//		CITIC		("CITIC",	 "����"),
//		CMB			("CMB",	 "����"),
//		CEB			("CEB",	 "���"),
//		SPDB		("SPDB",	 "�ַ�"),
//		GDB			("GDB",	 "�㷢"),
//		HXBANK		("HXBANK", "����"),
//		CIB			("CIB",	 "��ҵ"),
//		SPABANK		("SPABANK","ƽ��"),
//		CMBC		("CMBC",	 "����");
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
	 * �������� - ����Ҵ������������� - ��Ʒ�ṹ����
	 * code 		���ͱ���
	 * text 		��������
	 * alias 	    �������ƣ�������
	 **/
	public enum RMBProMixLoanTypeEnum {
		GXDK					("","",	"�������"),
		GXDK_GRLDK				("","",	"���������"),
		GXDK_GRLDK_XYK			("","",	"1.���ÿ�"),
		GXDK_GRLDK_GRJYXDK		("","",	"2.���˾�Ӫ�Դ���"),
		GXDK_GRLDK_ZFDK			("","",	"3.ס������"),
		GXDK_GRLDK_QTXFD		("","",	"4.�������Ѵ�"),
		GXDK_GSLDK				("","",	"��˾�����"),
		GXDK_GSLDK_XQY			("","",	"1.С��ҵ"),
		GXDK_GSLDK_GSDK			("","",	"2.��˾����"),
		GXDK_GSLDK_MYRZ			("","",	"3.ó������"),
		GXDK_PJFFT				("","",	"Ʊ�ݸ���͢"),
		GXDK_PJFFT_ZHIT			("","",	"1.ֱ��"),
		GXDK_PJFFT_ZHUANT		("","",	"2.ת��"),
		GXDK_PJFFT_FFT			("","",	"3.����͢"),
		GXDK_CFFY				("","",	"��ŷ���");

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
	 * �������� - ����Ҵ������������� - ����������ṹ(��)���� / ����������ṹ(��)����
	 * code 		���ͱ���
	 * text 		��������
	 * alias 	    �������ƣ�������
	 **/
	public enum RMBOrganAreaLoanTypeEnum {
		GXDK				("","",	"�������"),
		LXDK				("","",	"��С����"),
		QTSTDK				("","",	"����ʵ�����"),
		PJFFT				("","",	"Ʊ�ݸ���͢");

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
	 * �������� - ����Ҵ������������� - �����ṹ����
	 * code 		���ͱ���
	 * text 		��������
	 * alias 	    �������ƣ�������
	 **/
	public enum RMBFlowMixLoanTypeEnum {
		GRJYXDK			("","",	"���˾�Ӫ�Դ���"),
		XQY				("","",	"С��ҵ"),
		ZFAJDK			("","",	"ס�����Ҵ���"),
		QTXFDK			("","",	"�������Ѵ���"),
		GYLYMYRZ		("","",	"��Ӧ����ó������"),
		GSDK			("","",	"��˾����"),
		ZHUANT			("","",	"ת��"),
		ZHIT			("","",	"ֱ��"),
		FFT				("","",	"����͢"),
		GRXYKTZ			("","",	"�������ÿ�͸֧"),
		CFFY			("","",	"��ŷ���");

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
