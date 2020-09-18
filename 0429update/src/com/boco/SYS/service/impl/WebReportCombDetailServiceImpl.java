package com.boco.SYS.service.impl;

import com.boco.SYS.entity.TbCombDetail;
import com.boco.SYS.mapper.ReportCombDetailMapper;
import com.boco.SYS.service.IWebReportCombDetailService;
import com.boco.SYS.util.TreeNode;
import com.boco.TONY.biz.loancomb.POJO.DO.productbase.ProductDO;
import com.boco.TONY.biz.loancomb.exception.LoanCombDetailException;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.enums.CombDetailStatusEnum;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * �����������ҵ���߼���
 *
 * @author txn
 * @describe WebReportCombDetailServiceImpl
 * @date 2019-09-18
 */
@Service
public class WebReportCombDetailServiceImpl implements IWebReportCombDetailService {
    @Autowired
    ReportCombDetailMapper reportComposeDetailMapper;

    /**
     * ��ȡ���ֲ�Ʒ��Ϣ
     *
     * @param combCode ������ϱ���
     * @return ��ȡ�����б�
     */
    @Override
    public List<String> getLoanCombDetailInfoByCombCode(String combCode) {
        Preconditions.checkArgument(combCode != null);
        try {
            return reportComposeDetailMapper.getLoanComposeInfoByCombCode(combCode);
        } catch (LoanCombDetailException e) {
            return new ArrayList<>();
        }
    }

    /**
     * ������ֲ�Ʒ��Ϣ
     *
     * @param combCode  ���ֱ���
     * @param productId ��Ʒ�б�
     * @return PlainResult
     */
    @Override
    public PlainResult<String> insertLoanCombChildProduct(String combCode, String productId) {
        Preconditions.checkState(null != combCode && null != productId);
        PlainResult<String> result = new PlainResult<>();
        try {
            TbCombDetail loanComposeDetailDO = new TbCombDetail();
            loanComposeDetailDO .setCombCode(combCode);
            loanComposeDetailDO.setProdCode(productId);
            loanComposeDetailDO.setStatus(CombDetailStatusEnum.COMB_DETAIL_NORMAL.status);
            reportComposeDetailMapper.insertLoanComposeChildProduct(loanComposeDetailDO);
            return result.success("success", "insert loan compose child node success.");
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "inner error.insert loan compose child node failed");
        }
    }

    /**
     * ���´��ֲ�Ʒ��Ϣ
     *
     * @param combCode  ������ϱ���
     * @param productId ��Ʒ����
     * @return �����Ƿ�ɹ�
     */
    @Override
    public PlainResult<String> updateLoanCombProductDetailInfo(String combCode, String productId) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(combCode) && StringUtils.isNotEmpty(productId));
        PlainResult<String> result = new PlainResult<>();
        TbCombDetail loanComposeDetailDO = new TbCombDetail();
        loanComposeDetailDO.setCombCode(combCode);
        loanComposeDetailDO.setProdCode(productId);
        try {
            reportComposeDetailMapper.updateLoanComposeProductInfo(loanComposeDetailDO);
            return result.success("success", "update loan compose product detail success");
        } catch (LoanCombDetailException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update loan compose product failed");
        }
    }

    /**
     * ����Tree�ڵ��б�
     *
     * @param productList ��Ʒ�б�
     * @return TreeNodeList
     */
    @SuppressWarnings("unused")
    public List<TreeNode> buildTreeFromProductList(List<ProductDO> productList) {
        List<TreeNode> treeNodes = new ArrayList<>();
        // TODO: 19-9-19 ��Ʒ������Ҫ������չԤ���ֶ�
        for (ProductDO product : productList) {
            TreeNode treeNode = new TreeNode()
                    .setId(product.getProductId())
                    .setName(product.getProductName())
                    .setIsParent(true)
                    .setChecked(false)
                    .setOpen(true);
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }
}
