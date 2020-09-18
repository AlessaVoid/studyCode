package com.boco.TONY.biz.weboper.service;

import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.biz.weboper.POJO.DTO.WebOperLineDTO;

/**
 * 柜员条线业务逻辑层接口
 * @author tony
 * @describe IWebOperLineService
 * @date 2019-09-24
 */
public interface IWebOperLineService {

    /**插入柜员和条线的关系*/
    PlainResult<String> insertWebOperLine(String operCode, String lineIds);

    /**更新柜员和条线的关系*/
    PlainResult<String> updateWebOperLine(String operCode, String lineIds);

    /**删除柜员和条线的关系*/
    PlainResult<String> deleteWebOperLine(String operCode, String lineIds);

    /**删除柜员角色下所有的条线*/
    PlainResult<String> deleteAllWebOperLineByOperCode(String operCode);

    /**获取所有条线,通过操作码*/
   ListResult<WebOperLineDTO> getAllWebOperLineByOperCode(String operCode, int status);
}
