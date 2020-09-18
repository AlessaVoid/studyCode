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
     * ����������Ϣ
     */
    void insertProductLine(ProductLineInfoDO productLineInfoDO) throws LineProductException;

    /**
     * ����������Ϣ
     */
    void updateProductLineInfo(ProductLineInfoDO productLineInfoDO) throws LineProductException;

    /**
     * ɾ��������Ϣ
     */
    void deleteProductLineInfo(String lineId) throws LineProductException;

    /**
     * ��ѯ������ɾ����������Ϣ
     */
    List<ProductLineInfoDO> getAllDeadProductLineInfo() throws LineProductException;

    /**
     * ��ȡ���пɱ���Ȩ��������Ϣ
     */
    List<ProductLineInfoDO> getAllAliveProductLineInfoByOrganCode(ProductLineInfoDO productLineInfoDO) throws LineProductException;

    List<String> getAllAliveProductLineInfoByOrganCode3(ProductLineInfoDO productLineInfoDO) throws LineProductException;


    /**
     * ��ѯ����������Ϣ,����ǰ��չʾ
     */
    List<ProductLineInfoDO> getAllProductLineInfo() throws LineProductException;

    /**
     * ��������ID��ѯ������Ϣ
     */
    List<ProductLineInfoDO> getProductLineByLineId(String lineId) throws LineProductException;

    /**
     * ͨ������ģ����ѯ������Ϣ
     */
    List<ProductLineInfoDO> getProductLineByName(String lineName) throws LineProductException;
    /**
     * ͨ������ģ����ѯ������Ϣ
     * @return
     */
    List<String> selectLineNameByOrgan(String lineName, FdOrgan sessionOrgan);
    /**
     * ͨ������ID��ѯ������Ϣ
     */
    ProductLineInfoDO getProductLineInfoByLineId(String lineId);


    /**
     * �����������ƻ�ȡ������Ϣ
     *
     * @param lineName ��������
     * @return
     */
    List<String> selectLineName(String lineName);

    /**
     * �������߱����ȡ������Ϣ
     *
     * @param lineCode ���߱���
     * @return
     */
    List<String> selectLineCode(String lineCode);

    /**
     * �����ܼ�¼��
     * @return
     */
    int countLineListSize();
}
