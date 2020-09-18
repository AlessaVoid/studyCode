package com.boco.TONY.biz.line.service.impl;

import com.boco.SYS.entity.FdOrgan;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LineCombStatusDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDTO;
import com.boco.TONY.enums.CombLineDetailStatusEnum;
import com.boco.TONY.enums.CombLineStatusEnum;
import com.boco.TONY.biz.loancomb.exception.LoanCombException;
import com.boco.TONY.biz.loancomb.service.impl.WebLoanCombServiceImpl;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.biz.line.POJO.DO.ProductLineDetailDO;
import com.boco.TONY.biz.line.POJO.DTO.ProductLineDetailDTO;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.line.POJO.DTO.ProductLineInfoDTO;
import com.boco.TONY.biz.line.exception.LineProductDetailException;
import com.boco.TONY.biz.line.exception.LineProductException;
import com.boco.SYS.mapper.LineProductDetailMapper;
import com.boco.SYS.mapper.LineProductMapper;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.TONY.biz.line.service.IWebLineProductService;
import com.boco.TONY.utils.IDGeneratorUtils;
import com.boco.SYS.util.TreeNode;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * ����ҵ���߼���
 *
 * @author tony
 * @describe WebLineProductServiceImpl
 * @date 2019-09-23
 */
@Service
public class WebLineProductServiceImpl implements IWebLineProductService {

    @Autowired
    LineProductMapper lineProductMapper;

    @Autowired
    LineProductDetailMapper lineProductDetailsMapper;

    @Autowired
    LoanCombMapper loanCombMapper;
    private static final String COMMA = ",";
    //���߰汾
    private static final int LINE_VERSION = 1;

    /**
     * �����Ʒ������Ϣ
     *
     * @param lineName    ��������
     * @param description ��������
     * @param lineCreator ���ߴ�����Ա
     * @param productIds  ��Ʒ�б�
     * @param organCode   ������������
     * @return PlainResult
     */
    @Override
    public PlainResult<String> insertProductLineInfo(String lineName, String description, String lineCreator, String productIds, String organCode) {
        Preconditions.checkState(null != lineName && null != description && null != lineCreator && null != productIds);

        PlainResult<String> result = new PlainResult<>();
        final String lineId = IDGeneratorUtils.getSequence();

        ProductLineInfoDO productLineInfoDO =
                initProductLineInfoDO(lineId, lineName, description, lineCreator, organCode, CombLineStatusEnum.COMB_LINE_FREE.status);

        String[] productIdList = transferStr2ProductId(productIds);
        try {
            lineProductMapper.insertProductLine(productLineInfoDO);
            for (String productId : productIdList) {
                LoanCombDO loanCombDO = loanCombMapper.getLoanCombInfoByCombCode(productId);
                if (Objects.isNull(loanCombDO)) {
                    continue;
                }

                ProductLineDetailDO productLineDetailsDO = new ProductLineDetailDO().setLineId(lineId)
                        .setProductId(productId).setStatus(CombLineDetailStatusEnum.COMB_LINE_DETAIL_NORMAL.status);
                lineProductDetailsMapper.insertProductLineDetail(productLineDetailsDO);
            }
            return result.success("success", "insert product success");
        } catch (LineProductDetailException | LoanCombException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "insert into  product line detail failed ");
        } catch (LineProductException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "insert product line failed.");
        }
    }

    /**
     * ������Ʒ����
     *
     * @param lineId      ���߱��
     * @param lineName    ��������
     * @param description ��������
     * @param lineCreator ���ߴ�����
     * @param organCode   ������������
     * @return ProductLineInfoDO
     */
    private ProductLineInfoDO initProductLineInfoDO(String lineId, String lineName, String description, String lineCreator, String organCode, int lineStatus) {
        return new ProductLineInfoDO()
                .setLineId(lineId)
                .setLineName(lineName)
                .setLineCreator(lineCreator)
                .setCreateTime(LocalDateTime.now())
                .setLineUpdater(lineCreator)
                .setUpdateTime(LocalDateTime.now())
                .setLineStatus(lineStatus)
                .setLineDescription(description)
                .setLineVersion(LINE_VERSION)
                .setOrganCode(organCode)
                .setLineStatus(lineStatus);
    }

    /**
     * ����ǰ�˴��ݵĲ�Ʒ��Ϣ,ת����ѡ�еĲ�Ʒ
     *
     * @param productIds ��ƷID�б��ַ���
     * @return ��ƷID�б�
     */
    private String[] transferStr2ProductId(String productIds) {
        return productIds.split(COMMA);
    }

    /**
     * �������߼���Ʒ��Ϣ
     *
     * @param lineId      ���߱��
     * @param lineName    ��������
     * @param description ��������
     * @param lineUpdater ���߸�����
     * @param productIds  ���²�Ʒ�б�
     * @return PlainResult
     */
    @Override
    public PlainResult<String> updateProductLineInfo(String lineId, String lineName, String description, String lineUpdater, String productIds) {
        String[] productIdList = transferStr2ProductId(productIds);
        Set<String> newLineProductSet = new HashSet<>(Arrays.asList(productIdList));
        PlainResult<String> result = new PlainResult<>();
        try {
            ProductLineInfoDO productLineInfoDO = lineProductMapper.getProductLineInfoByLineId(lineId);
            lineProductMapper.updateProductLineInfo(buildProductLineDO(lineId, lineName, lineUpdater, description, productLineInfoDO.getLineVersion() + 1));
            Set<String> updateLineProductSet = new HashSet<>();
            Set<String> deleteLineProductSet = new HashSet<>();
            List<ProductLineDetailDO> oldLineProductList = lineProductDetailsMapper.getProductLineDetailById(lineId);

            for (ProductLineDetailDO productLineDetailDO : oldLineProductList) {
                String childLineId = productLineDetailDO.getProductId();
                if (!newLineProductSet.contains(childLineId)) {
                    deleteLineProductSet.add(childLineId);
                } else {
                    updateLineProductSet.add(childLineId);
                }
            }
            for (String productLineId : productIdList) {
                if (deleteLineProductSet.contains(productLineId)) {
                    continue;
                }
                if (!updateLineProductSet.contains(productLineId)) {
                    lineProductDetailsMapper.insertProductLineDetail(buildProductLineDetailDO(lineId, CombLineDetailStatusEnum.COMB_LINE_DETAIL_NORMAL.status, productLineId));
                }
            }
            for (String productLineId : deleteLineProductSet) {
                lineProductDetailsMapper.deleteCombProductByProductId(
                        buildProductLineDetailDO(lineId, CombLineDetailStatusEnum.COMB_LINE_DETAIL_DELETE.status, productLineId));
            }
            return result.success("success", "load product info success");
        } catch (LineProductDetailException | LineProductException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "load product line info failed");
        }
    }

    /**
     * ����ProductLineDetailDO
     *
     * @param lineId      ���߱��
     * @param lineName    ��������
     * @param lineUpdater ���߸�����
     * @param description ��������
     * @param version     ���߰汾
     * @return ProductLineInfoDO
     */
    private ProductLineInfoDO buildProductLineDO(String lineId, String lineName, String lineUpdater, String description, int version) {
        return new ProductLineInfoDO()
                .setLineId(lineId)
                .setLineName(lineName)
                .setLineDescription(description)
                .setLineVersion(version)
                .setUpdateTime(LocalDateTime.now())
                .setLineUpdater(lineUpdater);
    }

    /**
     * ����ProductLineDetailDO
     *
     * @param lineId        ���߱��
     * @param lineStatus    ����״̬
     * @param productLineId ��Ʒ���߱��
     * @return ProductLineDetailDO
     */
    private ProductLineDetailDO buildProductLineDetailDO(String lineId, int lineStatus, String productLineId) {
        return new ProductLineDetailDO().setLineId(lineId).setProductId(productLineId).setStatus(lineStatus);
    }

    /**
     * ɾ����Ʒ����
     *
     * @param lineId ���߱��
     * @return PlainResult
     */
    @Override
    public PlainResult<String> deleteProductLineInfo(String lineId) {
        Preconditions.checkState(null != lineId);

        PlainResult<String> result = new PlainResult<>();
        try {
            lineProductMapper.deleteProductLineInfo(lineId);
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "�������ڱ�ʹ��,������ɾ��");
        }
        try {
            // TODO: 19-9-24 fix bug
            List<ProductLineDetailDO> productLineDetailDOList = lineProductDetailsMapper.getProductLineDetailById(lineId);
            for (ProductLineDetailDO productLineDetailDO : productLineDetailDOList) {
                LoanCombDO loanCombDO = loanCombMapper.getLoanCombInfoByCombCode(productLineDetailDO.getProductId());
                if (Objects.isNull(loanCombDO)) {
                    continue;
                }
                LineCombStatusDO lineCombStatusDO = new LineCombStatusDO();
                lineCombStatusDO.setCombCode(productLineDetailDO.getProductId());
                lineCombStatusDO.setLineStatus(CombLineStatusEnum.COMB_LINE_FREE.status);
                lineProductDetailsMapper.deleteCombProductByProductId(buildProductLineDetailDO(lineId, CombLineDetailStatusEnum.COMB_LINE_DETAIL_DELETE.status, productLineDetailDO.getProductId()));

            }
            return result.success("success", "delete line product success");
        } catch (LineProductDetailException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "delete line product failed");
        } catch (LoanCombException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "delete line  product failed");
        }
    }


    /**
     * ǰ�˹����Ʒ��
     *
     * @param loanCombDTOList �������DTO
     * @return TreeNodeList
     */
    private List<TreeNode> buildTreeFromLoanCombDTOList(List<LoanCombDTO> loanCombDTOList) {
        return WebLoanCombServiceImpl.buildTreeFromCombList(loanCombDTOList, true);
    }

    /**
     * ��ȡ����������Ϣ
     *
     * @return ������Ϣ
     */
    @Override
    public ListResult<ProductLineInfoDTO> getProductLineInfoByOrganCode(String lineId, String lineName, String organCode) {
        ListResult<ProductLineInfoDTO> result = new ListResult<>();
        ProductLineInfoDO productLineInfoDO = new ProductLineInfoDO();
        productLineInfoDO.setLineId(lineId);
        productLineInfoDO.setLineName(lineName);
        productLineInfoDO.setOrganCode(organCode);
        try {
            List<ProductLineInfoDO> productLineInfoDOList = lineProductMapper.getAllAliveProductLineInfoByOrganCode(productLineInfoDO);
            List<ProductLineInfoDTO> productLineInfoDTOList = buildProductLineInfoDTOFromList(productLineInfoDOList);
            return result.success(productLineInfoDTOList, "load all product line successful");
        } catch (LineProductException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "inner error.load all product line failed");
        }
    }

    /**
     * ��ȡ����������Ϣ
     *
     * @return ������Ϣ
     */
    @Override
    public List<ProductLineInfoDO> getProductLineInfoByOrganCode2(ProductLineInfoDO productLineInfoDO) {
        try {
            return lineProductMapper.getAllAliveProductLineInfoByOrganCode(productLineInfoDO);
        } catch (LineProductException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * ��ȡ����������Ϣ
     *
     * @return ������Ϣ
     */
    @Override
    public List<String> getProductLineInfoByOrganCode3(ProductLineInfoDO productLineInfoDO) {
        try {
            return lineProductMapper.getAllAliveProductLineInfoByOrganCode3(productLineInfoDO);
        } catch (LineProductException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    /**
     * ͨ�����߱��,��ȡ������Ϣ��ϸ
     *
     * @param lineId ���߱��
     * @return ProductLineInfoDTO
     */
    @Override
    public ProductLineInfoDTO getProductLineInfoByLineId(String lineId) {
        //mapper���᷵�ؿ�ֵ
        ProductLineInfoDO productLineInfoDO = lineProductMapper.getProductLineInfoByLineId(lineId);
        if (Objects.isNull(productLineInfoDO)) {
            return null;
        }
        return buildProductLineInfoDTO(productLineInfoDO);
    }

    /**
     * ����product line dto
     *
     * @param productLineInfoDO ��Ʒ������ϢDO
     * @return ProductLineInfoDTO
     */
    private ProductLineInfoDTO buildProductLineInfoDTO(ProductLineInfoDO productLineInfoDO) {
        return new ProductLineInfoDTO()
                .setLineId(productLineInfoDO.getLineId())
                .setLineName(productLineInfoDO.getLineName())
                .setLineStatus(productLineInfoDO.getLineStatus())
                .setLineVersion(productLineInfoDO.getLineVersion())
                .setLineCreator(productLineInfoDO.getLineCreator())
                .setCreateTime(productLineInfoDO.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setLineDescription(productLineInfoDO.getLineDescription())
                .setUpdateTime(productLineInfoDO.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setLineUpdater(productLineInfoDO.getLineUpdater());
    }

    /**
     * ����ProductLineInfoDTO
     *
     * @param productLineInfoDOList ��Ʒ����DO
     * @return ProductLineInfoDTOList
     */
    public static List<ProductLineInfoDTO> buildProductLineInfoDTOFromList(List<ProductLineInfoDO> productLineInfoDOList) {
        List<ProductLineInfoDTO> productLineInfoDTOList = new ArrayList<>();
        for (ProductLineInfoDO productLineInfoDO : productLineInfoDOList) {
            ProductLineInfoDTO productLineInfoDTO = new ProductLineInfoDTO()
                    .setLineId(productLineInfoDO.getLineId())
                    .setLineName(productLineInfoDO.getLineName())
                    .setLineDescription(productLineInfoDO.getLineDescription())
                    .setLineStatus(productLineInfoDO.getLineStatus())
                    .setLineVersion(productLineInfoDO.getLineVersion())
                    .setLineUpdater(productLineInfoDO.getLineUpdater())
                    .setUpdateTime(productLineInfoDO.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .setCreateTime(productLineInfoDO.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .setLineCreator(productLineInfoDO.getLineCreator())
                    .setOrganCode(productLineInfoDO.getOrganCode());
            productLineInfoDTOList.add(productLineInfoDTO);
        }

        return productLineInfoDTOList;
    }

    /**
     * ��ȡ��Ʒ����ͨ��id
     *
     * @param lineId ���߱��
     * @return LoanCombDTOList
     */
    @Override
    public ListResult<LoanCombDTO> getProductLineById(String lineId) {
        ListResult<LoanCombDTO> result = new ListResult<>();
        //���ȸ������߻�ȡ��ǰ������Ϣ
        try {
            List<ProductLineDetailDO> childCombIds = lineProductDetailsMapper.getProductLineDetailById(lineId);
            List<LoanCombDTO> loanCombDTOList = new ArrayList<>();
            for (ProductLineDetailDO productLineDetailsDO : childCombIds) {
                LoanCombDO loanCombDO = loanCombMapper.getLoanCombInfoByCombCode(productLineDetailsDO.getLineId());
                loanCombDTOList.add(transferLoanComposeDO2LoanCombDTO(loanCombDO));
            }
            return result.success(loanCombDTOList, "load line product by id");
        } catch (LoanCombException | LineProductDetailException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "load all product id");
        }
    }

    /**
     * ת��LoanCombDOΪLoanCombDTO
     *
     * @param loanCombDO �������DO
     * @return LoanCombDTO
     */
    private LoanCombDTO transferLoanComposeDO2LoanCombDTO(LoanCombDO loanCombDO) {
        return new LoanCombDTO()
                .setCombId(loanCombDO.getCombId())
                .setCombCode(loanCombDO.getCombCode())
                .setCombName(loanCombDO.getCombName())
                .setCombLevel(loanCombDO.getCombLevel())
                .setCombStatus(loanCombDO.getCombStatus())
                .setCombCreator(loanCombDO.getCombCreator())
                .setCombCreateTime(loanCombDO.getCombCreateTime())
                .setCombUpdater(loanCombDO.getCombUpdater())
                .setCombUpdateTime(loanCombDO.getCombUpdateTime());
    }

    /**
     * transferLoanCombDOList2LoanComposeDTOList
     *
     * @param loanComposeDOList ��������б�
     * @return LoanCombDTOList
     */
    private List<LoanCombDTO> transferLoanCombDOList2LoanComposeDTOList(List<LoanCombDO> loanComposeDOList) {
        List<LoanCombDTO> loanComposeDTOList = new ArrayList<>();
        for (LoanCombDO loanComposeDO : loanComposeDOList) {
            LoanCombDTO loanComposeDTO = new LoanCombDTO()
                    .setCombId(loanComposeDO.getCombId())
                    .setCombCode(loanComposeDO.getCombCode())
                    .setCombName(loanComposeDO.getCombName())
                    .setCombLevel(loanComposeDO.getCombLevel())
                    .setCombStatus(loanComposeDO.getCombStatus())
                    .setCombCreator(loanComposeDO.getCombCreator())
                    .setCombCreateTime(loanComposeDO.getCombCreateTime())
                    .setCombUpdater(loanComposeDO.getCombUpdater())
                    .setCombUpdateTime(loanComposeDO.getCombUpdateTime());
            loanComposeDTOList.add(loanComposeDTO);
        }
        return loanComposeDTOList;
    }

    /**
     * ͨ�����ƻ�ȡ��Ʒ��Ϣ
     *
     * @param lineName ��������
     */
    @Override
    public void getProductLineInfoByName(String lineName) {

    }

    /**
     * ��ȡ�Ӳ�Ʒ��ϸ
     *
     * @param lineId ���߱���
     * @return ListResult
     */
    @Override
    public ListResult<LoanCombDTO> getProductLineDetailInfoByLineIdWithoutTree(String lineId) {
        Preconditions.checkState(null != lineId);
        ListResult<LoanCombDTO> result = new ListResult<>();
        try {
            List<ProductLineDetailDO> productLineDetailDOList = lineProductDetailsMapper.getProductLineDetailById(lineId);
            List<LoanCombDO> loanComposeDOList = new ArrayList<>();
            for (ProductLineDetailDO productLineDetailDO : productLineDetailDOList) {
                LoanCombDO loanComposeDO = loanCombMapper.getLoanCombInfoByCombCode(productLineDetailDO.getProductId());
                loanComposeDOList.add(loanComposeDO);
            }

            List<LoanCombDTO> loanComposeDTOList = transferLoanCombDOList2LoanComposeDTOList(loanComposeDOList);
            return result.success(loanComposeDTOList, "load product info success");
        } catch (LoanCombException | LineProductDetailException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "load product info faield");
        }
    }

    /**
     * ��ѯ��������
     *
     * @param lineName ��������
     * @return
     */
    @Override
    public List<String> selectLineName(String lineName) {
        return lineProductMapper.selectLineName(lineName);
    }

    /**
     * ��ѯ��������
     *
     * @param lineName ��������
     * @return
     */
    @Override
    public List<String> selectLineNameByOrgan(String lineName, FdOrgan sessionOrgan) {
        return lineProductMapper.selectLineNameByOrgan(lineName,sessionOrgan);
    }

    /**
     * ��ѯ���߱���
     *
     * @param lineId ���߱���
     * @return
     */
    @Override
    public List<String> selectLineCode(String lineId) {
        return lineProductMapper.selectLineCode(lineId);
    }

    @Override
    public int countLineListSize() {
        return lineProductMapper.countLineListSize();
    }

    /**
     * ��ȡ�Ӳ�Ʒ��ϸ
     *
     * @param lineId ���߱��
     * @return TreeNodeList
     */
    @Override
    public ListResult<TreeNode> getProductLineDetailInfoByLineId(String lineId) {
        Preconditions.checkState(null != lineId);
        ListResult<TreeNode> result = new ListResult<>();
        try {
            // TODO: 19-9-24 bug  happen has selected node
            List<ProductLineDetailDO> productLineDetailDOList = lineProductDetailsMapper.getProductLineDetailById(lineId);
            List<LoanCombDO> loanComposeDOList = new ArrayList<>();
            try {
                for (ProductLineDetailDO productLineDetailDO : productLineDetailDOList) {
                    LoanCombDO loanComposeDO = loanCombMapper.getLoanCombInfoByCombCode(productLineDetailDO.getProductId());
                    if (Objects.nonNull(loanComposeDO)) {// TODO: 19-9-24 fix NPE
                        loanComposeDOList.add(loanComposeDO);
                    }
                }

                List<TreeNode> treeNodes = buildTreeFromLoanCombDTOList(transferLoanCombDOList2LoanComposeDTOList(loanComposeDOList));
                return result.success(treeNodes, "load product line detail success");
            } catch (LoanCombException e) {
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "inner code error. load product line detail failed");
            }

        } catch (LineProductDetailException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "inner code error. load product line detail failed");
        }
    }

    /**
     * ����������ϸ���ڵ�
     *
     * @param productLineDetailDTOList ���߲�Ʒ��ϸ
     * @return TreeNodeList
     */
    @SuppressWarnings("unused")
    public List<TreeNode> buildTreeNodeFromProductLineDetailDTO(List<ProductLineDetailDTO> productLineDetailDTOList) {
        List<TreeNode> treeNodeList = new ArrayList<>();
        for (ProductLineDetailDTO productLineDetailDTO : productLineDetailDTOList) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(productLineDetailDTO.getLineId());
            treeNode.setOpen(true);
            treeNode.setChecked(true);
            treeNode.setIsParent(false);
            treeNode.setIcon("icon_item");
            //������Ҫ����ת��
            treeNode.setName(productLineDetailDTO.getCombCode());
            treeNodeList.add(treeNode);
        }
        return treeNodeList;
    }

    /**
     * ����ProductLineDetailDO
     *
     * @param productLineDetailDOList ��Ʒ����
     * @return ProductLineDetailDTOList
     */
    @SuppressWarnings("unused")
    private List<ProductLineDetailDTO> buildProductLineDetailDTOList(List<ProductLineDetailDO> productLineDetailDOList) {
        List<ProductLineDetailDTO> productLineDetailDTOList = new ArrayList<>();
        for (ProductLineDetailDO productLineDetailDO : productLineDetailDOList) {
            ProductLineDetailDTO productLineDetailDTO = new ProductLineDetailDTO().setId(productLineDetailDO.getId()).setLineId(productLineDetailDO.getLineId())
                    .setCombCode(productLineDetailDO.getProductId()).setStatus(productLineDetailDO.getStatus());
            productLineDetailDTOList.add(productLineDetailDTO);
        }
        return productLineDetailDTOList;
    }
}
