package com.boco.PM.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.TONY.biz.line.exception.LineProductException;
import com.boco.TONY.biz.weboper.exception.OperLineException;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebOperInfo业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebOperInfoService extends IGenericService<WebOperInfo,HashMap<String,Object>>{

	Map<String, Object> select(WebOperInfo webOperInfo, int pageNum, int pageSize) throws OperLineException, LineProductException;

	Map<String, Object> selectList(Map map, int pageNum, int pageSize) throws OperLineException, LineProductException;

	Json insert(WebOperInfo webOperInfo) throws Exception;

	List<Map<String, String>> selectOperCodeLike(WebOperInfo webOperInfo);

	List<Map<String, String>> selectOperName(WebOperInfo webOperInfo);

	Json update(WebOperInfo webOperInfo) throws Exception;

	public WebOperInfo selectOperCode(WebOperInfo webOperInfo);

	public String selectOperCodeByName(String prodOperName) throws Exception;
}