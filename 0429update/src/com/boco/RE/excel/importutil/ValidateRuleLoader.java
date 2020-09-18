package com.boco.RE.excel.importutil;

import java.util.Map;
import java.util.List;
import java.io.File;

/**
 * 验证规则接口类
 * @Author zhuhongjiang
 * @Date 2020/1/7 下午2:59
 **/
public interface ValidateRuleLoader {

	boolean loadValidateRules(File ruleFile);

	boolean loadValidateRules(String ruleFilePath);

	TemplateValidateRule getTemplateValidateRule();

	Map getHeadTitleRuleMap();

	Map getDetailTitleRuleMap();

	Map getHeadDataRuleMap();

	Map getDetailDataRuleMap();

	List getErrorList();
	
	List<Map> getHeadTitleRuleList() ;

	List<Map> getHeadDataRuleList() ;

	List<Map> getDetailTitleRuleList() ;

	List<Map> getDetailDataRuleList() ;
}
