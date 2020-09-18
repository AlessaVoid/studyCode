package com.boco.SYS.mapper;

import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import com.boco.TONY.biz.weboper.exception.OperLineException;

import java.util.List;

/**
 * 柜员-条线
 *
 * @author tony
 * @describe WebOperLineMapper
 * @date 2019-09-24
 */
public interface WebOperLineMapper {

    /**
     * 插入柜员-条线的关系
     */
    void insertWebOperLine(WebOperLineDO webOperLineDO) throws OperLineException;

    /**
     * 更新柜员-条线的关系
     */
    void updateWebOperLine(WebOperLineDO webOperLineDO) throws OperLineException;

    /**
     * 删除柜员-条线的关系
     */
    void deleteWebOperLine(WebOperLineDO webOperLineDO) throws OperLineException;

    /**
     * 删除柜员下所有的条线
     *
     * @param operCode 柜员号
     * @throws OperLineException
     */
    void deleteAllWebOperLineByOperCode(String operCode) throws OperLineException;

    /**
     * 根据柜员号查询关联的条线
     *
     * @param webOperLineDO 柜员信息
     * @return WebOperLineDOList
     * @throws OperLineException 异常
     */
    List<WebOperLineDO> getAllWebOperLineByOperCode(WebOperLineDO webOperLineDO) throws OperLineException;

    /**
     * 检查柜员条线是否已经存在,防止并发存储,导致记录重复
     *
     * @param webOperLineDO 柜员条线DO
     * @return WebOperLineDO
     * @throws OperLineException 柜员条线异常
     */
    WebOperLineDO checkWebOperLineIsExist(WebOperLineDO webOperLineDO) throws OperLineException;

    /**
     *
     * 根据opercode删除
     * @param opercode
     * @return
     * @throws OperLineException
     */


    void deleteWebOperLineByOpercode(String opercode)throws OperLineException;
}
