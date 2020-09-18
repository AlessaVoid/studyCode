package com.boco.TONY.biz.line.service;

import com.boco.SYS.entity.FdOrgan;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDTO;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.line.POJO.DTO.ProductLineInfoDTO;
import com.boco.SYS.util.TreeNode;

import java.util.List;

/**
 * ���߲�Ʒҵ����
 *
 * @author tony
 * @describe IWebLineProductService
 * @date 2019-09-23
 */
public interface IWebLineProductService {
    /**
     * ����������Ϣ
     */
    PlainResult<String> insertProductLineInfo(String lineName, String description, String creator, String productIds, String organCode);

    /**
     * ����������Ϣ
     */
    PlainResult<String> updateProductLineInfo(String lineId, String lineName, String description, String lineUpdater, String productIds);

    /**
     * ɾ��������Ϣ
     */
    PlainResult<String> deleteProductLineInfo(String lineId);

    /**
     * ��ȡ�������� ����ǰ��չʾ
     */
    ListResult<ProductLineInfoDTO> getProductLineInfoByOrganCode(String lineId, String lineName, String organCode);
    List<ProductLineInfoDO> getProductLineInfoByOrganCode2(ProductLineInfoDO productLineInfoDO);
    List<String> getProductLineInfoByOrganCode3(ProductLineInfoDO productLineInfoDO);

    /**
     * ͨ�����߱����ȡ������Ϣ
     */
    ProductLineInfoDTO getProductLineInfoByLineId(String lineId);

    /**
     * �������߱�ʶ��ѯ����
     */
    ListResult<LoanCombDTO> getProductLineById(String productId);

    /**
     * ģ����ѯ����
     */
    void getProductLineInfoByName(String lineName);

    /**
     * ��ȡ���߲�Ʒ����ͨ�����߱���
     */
    ListResult<TreeNode> getProductLineDetailInfoByLineId(String lineId);

    /**
     * ��������,����չʾ����
     */
    ListResult<LoanCombDTO> getProductLineDetailInfoByLineIdWithoutTree(String lineId);


    /**
     * ����������ƻ�ȡ������Ϣ
     *
     * @param lineName ��������
     * @return
     */
    List<String> selectLineName(String lineName);


    List<String> selectLineNameByOrgan(String lineName, FdOrgan sessionOrgan);

    /**
     * ���ݴ��ֱ����ȡ������Ϣ
     *
     * @param lineName ���߱���
     * @return
     */
    List<String> selectLineCode(String lineName);

    int countLineListSize();

}