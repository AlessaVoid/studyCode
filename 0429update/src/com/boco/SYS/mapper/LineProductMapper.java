package com.boco.SYS.mapper;

import com.boco.SYS.entity.FdOrgan;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.line.exception.LineProductException;

import java.util.List;

/**
 * @author tony
 * @describe LineProductMapper
 * @date 2019-09-23
 */
public interface LineProductMapper {

    /**
     * 插入条线信息
     */
    void insertProductLine(ProductLineInfoDO productLineInfoDO) throws LineProductException;

    /**
     * 更新条线信息
     */
    void updateProductLineInfo(ProductLineInfoDO productLineInfoDO) throws LineProductException;

    /**
     * 删除条线信息
     */
    void deleteProductLineInfo(String lineId) throws LineProductException;

    /**
     * 查询所有已删除的条线信息
     */
    List<ProductLineInfoDO> getAllDeadProductLineInfo() throws LineProductException;

    /**
     * 获取所有可被授权的条线信息
     */
    List<ProductLineInfoDO> getAllAliveProductLineInfoByOrganCode(ProductLineInfoDO productLineInfoDO) throws LineProductException;

    List<String> getAllAliveProductLineInfoByOrganCode3(ProductLineInfoDO productLineInfoDO) throws LineProductException;


    /**
     * 查询所有条线信息,用于前端展示
     */
    List<ProductLineInfoDO> getAllProductLineInfo() throws LineProductException;

    /**
     * 根据条线ID查询条线信息
     */
    List<ProductLineInfoDO> getProductLineByLineId(String lineId) throws LineProductException;

    /**
     * 通过名字模糊查询条线信息
     */
    List<ProductLineInfoDO> getProductLineByName(String lineName) throws LineProductException;
    /**
     * 通过名字模糊查询条线信息
     * @return
     */
    List<String> selectLineNameByOrgan(String lineName, FdOrgan sessionOrgan);
    /**
     * 通过条线ID查询条线信息
     */
    ProductLineInfoDO getProductLineInfoByLineId(String lineId);


    /**
     * 根据条线名称获取贷种信息
     *
     * @param lineName 条线名称
     * @return
     */
    List<String> selectLineName(String lineName);

    /**
     * 根据条线编码获取贷种信息
     *
     * @param lineCode 条线编码
     * @return
     */
    List<String> selectLineCode(String lineCode);

    /**
     * 条线总记录数
     * @return
     */
    int countLineListSize();
}
