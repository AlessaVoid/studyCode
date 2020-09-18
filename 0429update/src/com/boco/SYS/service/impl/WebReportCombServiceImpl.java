package com.boco.SYS.service.impl;

import com.boco.SYS.entity.TbCombDetail;
import com.boco.SYS.entity.TbReportComb;
import com.boco.SYS.mapper.ProductCombMapper;
import com.boco.SYS.mapper.ReportCombDetailMapper;
import com.boco.SYS.mapper.ReportCombMapper;
import com.boco.SYS.service.IWebReportCombService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.TreeNode;
import com.boco.TONY.biz.line.POJO.DO.ProductLineCombDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombStatusDO;
import com.boco.TONY.biz.loancomb.POJO.DO.productbase.ProductDO;
import com.boco.TONY.biz.loancomb.POJO.DO.productbase.ProductStatusDO;
import com.boco.TONY.biz.loancomb.POJO.DTO.combbase.LoanCombDTOV2;
import com.boco.TONY.biz.loancomb.exception.LoanCombDetailException;
import com.boco.TONY.biz.loancomb.exception.LoanCombException;
import com.boco.TONY.biz.product.exception.LoanProductException;
import com.boco.TONY.biz.product.service.IWebProductService;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.constants.CombLevelConstants;
import com.boco.TONY.enums.CombDetailStatusEnum;
import com.boco.TONY.enums.CombStatusEnum;
import com.boco.TONY.enums.ProductStatusEnum;
import com.boco.TONY.utils.IDGeneratorUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author txn
 * @describe WebReportCombServiceImpl
 * @date 2019-09-17
 */

@Service
public class WebReportCombServiceImpl implements IWebReportCombService {
    @Autowired
    ReportCombMapper reportCombMapper;
    @Autowired
    ProductCombMapper productCombMapper;
    @Autowired
    ReportCombDetailMapper reportCombDetailMapper;
    @Autowired
    IWebProductService webProductService;
    private static final String COMA_PRODUCT_ID = ",";
    private static final int DELTA = 1;


    /**
     * 通过贷种Level查询贷种组合
     *
     * @param combLevel 贷种级别
     * @param isLine    是否是条线
     * @return TreeNodeList
     */
    @Override
    public ListResult<TreeNode> getLoanCombInfoByLevel(int combLevel, boolean isLine) {
        Preconditions.checkArgument(combLevel == CombLevelConstants.COMPOSE_LEVEL_0 || combLevel == CombLevelConstants.COMPOSE_LEVEL_1
                || combLevel == CombLevelConstants.COMPOSE_LEVEL_2 || combLevel == CombLevelConstants.COMPOSE_LEVEL_3);
        List<TbReportComb> loanComposeList;
        ListResult<TreeNode> result = new ListResult<>();

        try {

            switch (combLevel) {
                case CombLevelConstants.COMPOSE_LEVEL_0:
                case CombLevelConstants.COMPOSE_LEVEL_1:
                case CombLevelConstants.COMPOSE_LEVEL_2:
                    loanComposeList = reportCombMapper.getOrganLoanProductInfoByLevel(combLevel + DELTA);
                    return result.success(buildTreeFromCombList(transferDO2DTO(loanComposeList), false), "load loan compose success.");

                case CombLevelConstants.COMPOSE_LEVEL_3:
                    List<ProductDO> productList;
                    productList = productCombMapper.getAllAvailableCombProduct();
                    List<TreeNode> productTreeNodes = buildTreeFromProductList(productList, false);
                    return result.success(productTreeNodes, "load loan product success.");
                default:
                    return result.error(HttpServletResponse.SC_NOT_FOUND, "unknown compose level,please check it");
            }
        } catch (LoanProductException | LoanCombException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "inner error.load loan compose failed");
        }
    }

    @Override
    public ListResult<TreeNode> getLoanCombInfoByLevelAndOrganCode(int combLevel, String organCode) throws LoanCombException {
        Preconditions.checkArgument(combLevel == CombLevelConstants.COMPOSE_LEVEL_0 || combLevel == CombLevelConstants.COMPOSE_LEVEL_1
                || combLevel == CombLevelConstants.COMPOSE_LEVEL_2 || combLevel == CombLevelConstants.COMPOSE_LEVEL_3);
        List<TbReportComb> loanComposeList;
        ListResult<TreeNode> result = new ListResult<>();
        ProductLineCombDO productLineCombDO = new ProductLineCombDO();
        productLineCombDO.setCombLevel(combLevel);
        productLineCombDO.setOrganCode(organCode);
        try {
            loanComposeList = reportCombMapper.getLineLoanProductInfoByLevelAndOrganCode(productLineCombDO);
            return result.success(buildTreeFromCombList(transferDO2DTO(loanComposeList), false), "load loan comb success.");
        } catch (LoanCombException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "load loan comb failed");
        }

    }

    /**
     * 贷种名称
     *
     * @param combName 贷种名称
     * @return
     */
    @Override
    public List<String> selectCombName(String combName) {
        return reportCombMapper.selectCombName(combName);
    }

    /**
     * 贷种编码
     *
     * @param combCode 贷种编码
     * @return
     */
    @Override
    public List<String> selectCombCode(String combCode) {
        return reportCombMapper.selectCombCode(combCode);
    }

    @Override
    public int countCombListSize() {
        return reportCombMapper.countCombListSize();
    }

    @Override
    public PlainResult<String> checkCombInfo(String combCode, String combName) throws LoanCombException {
        PlainResult<String> result = new PlainResult<>();
        TbReportComb combNameRecord = reportCombMapper.selectExactlyCombName(combName);
        TbReportComb combCodeRecord = reportCombMapper.getLoanCombInfoByCombCode(combCode);
        if (Objects.nonNull(combCodeRecord)) {
            return result.error(1, "贷种编码已存在");
        }
        if (Objects.nonNull(combNameRecord)) {

            return result.error(2, "贷种名称已存在");
        }
        return result.success("check pass", "检查通过");
    }

    @Override
    public List<Map<String, Object>> selectComb(Map<String, Object> combMap) {
        return reportCombMapper.selectComb(combMap);
    }

    @Override
    public List<Map<String, Object>> selectCombOfBind(Map<String, Object> combMap) {
        return reportCombMapper.selectCombOfBind(combMap);
    }

    @Override
    public List<Map<String, Object>> selectCombByOpercode(HashMap<String, Object> map) {
        return reportCombMapper.selectCombByOpercode(map);
    }

    @Override
    public List<Map<String, Object>> selectCombBycombcode(HashMap<String, Object> map) {
        return reportCombMapper.selectCombBycombcode(map);
    }

    /**
     * 通过贷种Level查询贷种组合
     *
     * @param combLevel 贷种级别
     * @return TreeNodeList
     */
    @Override
    public ListResult<TbReportComb> getLoanCombInfoByLevel2(int combLevel) {
        Preconditions.checkArgument(combLevel == CombLevelConstants.COMPOSE_LEVEL_0 || combLevel == CombLevelConstants.COMPOSE_LEVEL_1
                || combLevel == CombLevelConstants.COMPOSE_LEVEL_2 || combLevel == CombLevelConstants.COMPOSE_LEVEL_3);
        ListResult<TbReportComb> result = new ListResult<>();
        try {
            List<TbReportComb> loanCombList = reportCombMapper.getOrganLoanProductInfoByLevel(combLevel + DELTA);
            return result.success(transferDO2DTO(loanCombList), "load loan compose success.");
        } catch (LoanCombException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "inner error.load loan compose failed");
        }
    }


    /***
     * 新增贷种组合信息
     * @param combCode 贷种编码
     * @param combName 贷种名称
     * @param combLevel 贷种级别
     * @param combCreator 贷种创建人
     * @param productIds 选中的下级产品id列表
     * @return PlainResult
     */
    @Override
    public PlainResult<String> insertLoanCombInfo(String combCode, String combName, int combLevel, String combCreator, String productIds) throws LoanCombException {
        Preconditions.checkArgument(null != combCode && null != combName && 0L < combLevel && null != combCreator && null != productIds);

        PlainResult<String> result = new PlainResult<>();

        try {
            String combId = IDGeneratorUtils.getSequence();
            reportCombMapper.insertLoanCombInfo(buildLoanComposeDO(combId, combCode, combName, combLevel, CombStatusEnum.COMB_FREE.status, combCreator, combCreator));
            if (StringUtils.isBlank(productIds)) {
                return result.success("success", "create loan compose success");
            }
            String[] productIdList = transferStr2ProductIdList(productIds);
            switch (combLevel) {//首先前提展示的都是未被组合的贷种产品
                case CombLevelConstants.COMPOSE_LEVEL_1:
                case CombLevelConstants.COMPOSE_LEVEL_2:
                    for (String productId : productIdList) {
                        LoanCombStatusDO loanComposeStatusDO = new LoanCombStatusDO()
                                .setCombCode(productId).setCombStatus(CombStatusEnum.COMB_TAKEN.status);
                        // TODO: 19-9-20 先行更新下级贷种状态,再插入明细,保证数据一致性
                        reportCombMapper.updateLoanCombStatus(loanComposeStatusDO);
                        reportCombDetailMapper.insertLoanComposeChildProduct(
                                buildTbCombDetail(productId, combCode, CombDetailStatusEnum.COMB_DETAIL_NORMAL.status));
                    }
                    return result.success("success", "create loan compose success");
                case CombLevelConstants.COMPOSE_LEVEL_3:
                    for (String productId : productIdList) {
                        ProductStatusDO productStatusDO = new ProductStatusDO()
                                .setProductId(productId).setProductStatus(ProductStatusEnum.PRODUCT_TAKEN.status);
                        productCombMapper.updateCombProductStatus(productStatusDO);
                        TbCombDetail TbCombDetail = buildTbCombDetail(productId, combCode, CombDetailStatusEnum.COMB_DETAIL_NORMAL.status);
                        ProductDO productInfo = productCombMapper.getSelectedCombProduct(productId);
                        TbCombDetail.setProdSysId(productInfo.getProductSystemId());
                        reportCombDetailMapper.insertLoanComposeChildProduct(TbCombDetail);
                    }
                    return result.success("success", "create loan compose success");
                default:
                    return result.error(HttpServletResponse.SC_NOT_FOUND, "comb level is not exist");
            }
        } catch (LoanProductException | LoanCombException | LoanCombDetailException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "load loan compose failed");
        }

    }


    /**
     * 获取所有的组合产品列表
     *
     * @return LoanComposeDTO2List
     */
    @Override
    public Map<String, Object> getAllLoanCombInfo3(String combCode, String combName, String combLevel, int pageNum, int pageSize) {
        Map<String, Object> results = new Hashtable<>();
        try {
            TbReportComb loanCombDO = new TbReportComb();
            loanCombDO.setCombCode(combCode);
            loanCombDO.setCombName(combName);
            if (Objects.nonNull(combLevel) && StringUtils.isNotBlank(combLevel)) {
                loanCombDO.setCombLevel(Integer.parseInt(combLevel));
            }
            PageHelper.startPage(pageNum, pageSize, true, true, true);
            List<TbReportComb> loanCombInfoList = reportCombMapper.getAllLoanCombInfoList(loanCombDO);
            //返回页面的分页数据
            if (CollectionUtils.isEmpty(loanCombInfoList)) {
                results.put("pager.pageNo", 1);
                results.put("pager.totalRows", 0);
                results.put("rows", new ArrayList<>());
            }
            PageInfo<TbReportComb> page = new PageInfo<>(loanCombInfoList);
            results.put("pager.pageNo", page.getPageNum());
            results.put("pager.totalRows", page.getTotal());
            List<LoanCombDTOV2> loanCombDTO2List = transferDO2DTO2(loanCombInfoList);
            List<LoanCombDTOV2> loanCombDTO2ListRes = new ArrayList<>();
            for (LoanCombDTOV2 loanCombDTOV2 : loanCombDTO2List) {
                int level = loanCombDTOV2.getCombLevel();
                String combCode1 = loanCombDTOV2.getCombCode();
                List<String> childrenCombList;
                if (level != 3) {
                    try {
                        childrenCombList = reportCombDetailMapper.getSelectedLoanComposeInfoByCombCode(combCode1);
                        String childCombStr = "";
                        for (String childCombCode : childrenCombList) {
                            TbReportComb childComb = reportCombMapper.getLoanCombInfoByCombCode(childCombCode);
                            if (Objects.nonNull(childComb)) {
                                childCombStr += childComb.getCombName() + ",";
                            }
                        }
                        if (StringUtils.isBlank(childCombStr)) {
                            childCombStr = "";
                        } else {
                            childCombStr = childCombStr.substring(0, childCombStr.length() - 1);
                        }
                        loanCombDTOV2.setCombChildren(childCombStr);
                        loanCombDTO2ListRes.add(loanCombDTOV2);
                    } catch (LoanCombDetailException | LoanCombException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        childrenCombList = reportCombDetailMapper.getSelectedLoanComposeInfoByCombCode(combCode1);
                        String childCombStr = "";
                        for (String childCombCode : childrenCombList) {
                            String prodName = reportCombMapper.getProdInfoByProdCode(childCombCode);
                            if (prodName != null || !"null".equalsIgnoreCase(String.valueOf(prodName))) {
                                childCombStr += prodName + ",";
                            }
                        }
                        if (StringUtils.isBlank(childCombStr)) {
                            childCombStr = "";
                        } else {
                            childCombStr = childCombStr.substring(0, childCombStr.length() - 1);
                        }
                        loanCombDTOV2.setCombChildren(childCombStr);
                        loanCombDTO2ListRes.add(loanCombDTOV2);
                    } catch (LoanCombDetailException | LoanCombException e) {
                        e.printStackTrace();
                    }
                }
            }
            results.put("rows", loanCombDTO2ListRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 通过combCode查找贷种组合
     *
     * @param combCode 贷种组合编码
     * @return LoanComposeDTOList
     */
    @Override
    public PlainResult<TbReportComb> getLoanCombInfoByCombCode(String combCode) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(combCode));

        PlainResult<TbReportComb> result = new PlainResult<>();
        try {
            TbReportComb loanComposeDO = reportCombMapper.getLoanCombInfoByCombCode(combCode);
            TbReportComb loanComposeDTO = buildLoanCombDTO(loanComposeDO);
            return result.success(loanComposeDTO, "load loan by id compose success");
        } catch (LoanCombException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "load loan by id compose failed");
        }
    }

    /**
     * 获取子贷种产品或者下级贷种详情
     *
     * @param parentCombCode 父贷种组合
     * @param combLevel      贷种组合级别
     * @return TreeNodeList
     */
    @Override
    public ListResult<TreeNode> getLoanCombDetailInfoByCombCode(String parentCombCode, int combLevel) {
        ListResult<TreeNode> result = new ListResult<>();
        List<TreeNode> treeNodes = new ArrayList<>();
        try {
            List<String> combCodeList = reportCombDetailMapper.getSelectedLoanComposeInfoByCombCode(parentCombCode);
            List<TbReportComb> loanComposeDTOList = new ArrayList<>();
            switch (combLevel) {
                case CombLevelConstants.COMPOSE_LEVEL_1:
                case CombLevelConstants.COMPOSE_LEVEL_2:
                    for (String combCode : combCodeList) {
                        TbReportComb loanComposeDO = reportCombMapper.getLoanCombInfoByCombCode(combCode);
                        // TODO: 19-9-21 console NPE
                        if (!Objects.isNull(loanComposeDO)) {
                            TbReportComb loanComposeDTO = buildLoanCombDTO(loanComposeDO);
                            loanComposeDTOList.add(loanComposeDTO);
                        } else {
                            return result.success(new ArrayList<>(), "no comb product provided");
                        }
                    }
                    List<TreeNode> combTreeNodes = buildTreeFromCombList(loanComposeDTOList, true);
                    return result.success(combTreeNodes, "load child nodes success.");
                case CombLevelConstants.COMPOSE_LEVEL_3:
                    //获取产品列表
                    List<ProductDO> productDOList = webProductService.getSelectedCombProduct(combCodeList);
                    List<TreeNode> productTreeNodes = buildTreeFromProductList(productDOList, true);
                    return result.success(productTreeNodes, "load child nodes success.");
                default:
                    return result.success(treeNodes, "can not match the comb level");
            }
        } catch (LoanCombDetailException | LoanCombException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "load child nodes failed.");
        }


    }

    /**
     * 通过贷种组合编码删除贷种组合
     *
     * @param combCode  贷种组合编码
     * @param combLevel 贷种组合级别
     * @return PlainResult
     */
    @Override
    public PlainResult<String> deleteLoanCombInfoByCombCode(String combCode, int combLevel) {
        Preconditions.checkArgument(null != combCode && 0L < combLevel);
        PlainResult<String> result = new PlainResult<>();
        try {
            reportCombMapper.deleteLoanCombByCombCode(combCode);
            List<String> productCodeList = reportCombDetailMapper.getSelectedLoanComposeInfoByCombCode(combCode);
            for (String childProductCode : productCodeList) {
                TbCombDetail loanComposeDetailDO = new TbCombDetail();
                loanComposeDetailDO.setProdCode(childProductCode);
                loanComposeDetailDO.setCombCode(combCode);
                loanComposeDetailDO.setStatus(CombDetailStatusEnum.COMB_DETAIL_DELETE.status);
                reportCombDetailMapper.updateLoanComposeProductInfo(loanComposeDetailDO);
                if (loanComposeDetailDO.getStatus().equals(CombDetailStatusEnum.COMB_DETAIL_DELETE.status)) {
                    reportCombDetailMapper.deleteLoanComposeProductInfo(loanComposeDetailDO);
                }
            }
            //释放当前贷种组合占用的产品
            switch (combLevel) {
                case CombLevelConstants.COMPOSE_LEVEL_1:
                case CombLevelConstants.COMPOSE_LEVEL_2:
                    for (String productId : productCodeList) {
                        LoanCombStatusDO loanComposeStatusDO = new LoanCombStatusDO().setCombCode(productId).setCombStatus(CombStatusEnum.COMB_FREE.status);
                        reportCombMapper.updateLoanCombStatus(loanComposeStatusDO);
                    }
                    return result.success("success", "delete loan comb product success");
                case CombLevelConstants.COMPOSE_LEVEL_3:
                    for (String productId : productCodeList) {
                        ProductStatusDO productStatusDO = new ProductStatusDO().setProductId(productId).setProductStatus(ProductStatusEnum.PRODUCT_FREE.status);
                        productCombMapper.updateCombProductStatus(productStatusDO);
                    }
                    return result.success("success", "delete loan comb product success");
                default:
                    return result.error(HttpServletResponse.SC_NOT_FOUND, "unknown request,please check it.");
            }

        } catch (LoanProductException | LoanCombDetailException | LoanCombException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "load loan compose failed");
        }
    }

    /**
     * 更新贷种组合信息
     *
     * @param combCode    贷种组合编码
     * @param combLevel   贷种组合级别
     * @param combName    贷种组合名称
     * @param productIds  贷种组合关联产品列表
     * @param combUpdater 贷种组合更新人
     * @return PlainResult
     */
    @Override
    public PlainResult<String> updateLoanCombInfo(String combCode, int combLevel, String combName, String productIds, String combUpdater) {
        Preconditions.checkArgument(null != combCode && null != productIds);

        final PlainResult<String> result = new PlainResult<>();
        String[] productIdList = transferStr2ProductIdList(productIds);
        List<String> existProductCodeList = new ArrayList<>(); //待重置产品表
        List<String> canceledProductCodeList = new ArrayList<>(); //待重置产品表
        List<String> newNoFilterProductList = Arrays.asList(productIdList); //更新产品表
        List<String> newProductList = newNoFilterProductList.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        try {
            TbReportComb loanCombDO = new TbReportComb();
            loanCombDO.setCombCode(combCode);
            loanCombDO.setCombName(combName);
            loanCombDO.setCombLevel(combLevel);
//            loanCombDO.setCombUpdateOper(combUpdater);
//            loanCombDO.setCombUpdateTime(BocoUtils.getTime());
            reportCombMapper.updateLoanCombInfo(loanCombDO);
            List<String> oldProductIdList = reportCombDetailMapper.getLoanComposeInfoByCombCode(combCode); //老产品表
            for (String productId : oldProductIdList) {
                if (newProductList.contains(productId)) {
                    existProductCodeList.add(productId);
                } else {
                    canceledProductCodeList.add(productId);
                }
            }
            switch (combLevel) {
                case CombLevelConstants.COMPOSE_LEVEL_1:
                case CombLevelConstants.COMPOSE_LEVEL_2:
                    for (String productCode : newProductList) {
                        if (canceledProductCodeList.contains(productCode)) {
                            continue;
                        }
                        TbCombDetail loanComposeDetailDO =
                                buildTbCombDetail(productCode, combCode, CombDetailStatusEnum.COMB_DETAIL_NORMAL.status);
                        //新添加的下级贷种
                        if (!existProductCodeList.contains(productCode)) {
                            reportCombDetailMapper.insertLoanComposeChildProduct(loanComposeDetailDO);
                        } else {
                            reportCombDetailMapper.updateLoanComposeProductInfo(loanComposeDetailDO);
                        }
                        LoanCombStatusDO loanCombStatusDO = new LoanCombStatusDO();
                        loanCombStatusDO.setCombCode(productCode);
                        loanCombStatusDO.setCombStatus(CombStatusEnum.COMB_TAKEN.status);
                        reportCombMapper.updateLoanCombStatus(loanCombStatusDO);

                    }
                    boolean isCancel = doCancel(canceledProductCodeList, combCode);//放行未组合产品
                    if (isCancel) {
                        return result.success("update", "update loan comb product success");
                    } else {
                        return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "cancel loan comb product success");
                    }
                case CombLevelConstants.COMPOSE_LEVEL_3:
                    for (String productCode : newProductList) { //产品从其他产品线同步,不需要手动录入
                        if (canceledProductCodeList.contains(productCode)) {
                            continue;
                        }
                        if (!existProductCodeList.contains(productCode)) {
                            TbCombDetail loanComposeDetailDO = buildTbCombDetail(productCode, combCode, CombDetailStatusEnum.COMB_DETAIL_NORMAL.status);
                            ProductDO selectedCombProduct = productCombMapper.getSelectedCombProduct(productCode);
                            loanComposeDetailDO.setProdSysId(selectedCombProduct.getProductSystemId());
                            reportCombDetailMapper.insertLoanComposeChildProduct(loanComposeDetailDO);
                            ProductStatusDO productStatusDO = new ProductStatusDO()
                                    .setProductId(productCode).setProductStatus(ProductStatusEnum.PRODUCT_TAKEN.status);
                            productCombMapper.updateCombProductStatus(productStatusDO);
                        }
                    }
                    for (String productId : canceledProductCodeList) {
                        ProductStatusDO productStatusDO = new ProductStatusDO()
                                .setProductId(productId).setProductStatus(ProductStatusEnum.PRODUCT_FREE.status);
                        TbCombDetail loanComposeDetailDO = new TbCombDetail();
//                        loanComposeDetailDO.setStatus(CombDetailStatusEnum.COMB_DETAIL_DELETE.status);
                        loanComposeDetailDO.setCombCode(combCode);
                        loanComposeDetailDO.setProdCode(productId);
                        reportCombDetailMapper.deleteLoanComposeProductInfo(loanComposeDetailDO);
                        productCombMapper.updateCombProductStatus(productStatusDO);
                    }
                    return result.success("success", "update loan comb product success");
            }
        } catch (LoanCombDetailException | LoanProductException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update loan compose |product failed");
        } catch (LoanCombException e) {
            e.printStackTrace();
        }
        return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update loan compose |product failed");
    }

    /**
     * 取消贷种组合关联产品
     *
     * @param canceledProductIdList 取消的贷种组合列表
     */
    private boolean doCancel(List<String> canceledProductIdList, String combCode) {
        try {
            for (String productId : canceledProductIdList) {
                reportCombMapper.updateLoanCombStatus(buildLoanComposeStatusDO(productId, CombStatusEnum.COMB_FREE.status));
                TbCombDetail TbCombDetail = new TbCombDetail();
                TbCombDetail.setCombCode(combCode);
                TbCombDetail.setProdCode(productId);
                TbCombDetail.setStatus(CombDetailStatusEnum.COMB_DETAIL_DELETE.status);
                reportCombDetailMapper.updateLoanComposeProductInfo(TbCombDetail);
                if (TbCombDetail.getStatus().equals(CombDetailStatusEnum.COMB_DETAIL_DELETE.status)) {
                    reportCombDetailMapper.deleteLoanComposeProductInfo(TbCombDetail);
                }
            }
            return true;
        } catch (LoanCombException | LoanCombDetailException e) {
            return false;
        }
    }

    /**
     * 通过贷种名称查询贷种信息[模糊查询]
     *
     * @param name 查询名称
     * @return LoanCombDTOList
     */
    @Override
    public ListResult<TbReportComb> getLoanCombInfoByName(String name) {
        ListResult<TbReportComb> result = new ListResult<>();
        try {
            List<TbReportComb> loanComposeDOList = reportCombMapper.getLoanCombInfoByName(name);
            List<TbReportComb> loanComposeDTOList = transferDO2DTO(loanComposeDOList);
            return result.success(loanComposeDTOList, "load loan compose by name success");
        } catch (LoanCombException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update loan compose failed");
        }
    }

    /**
     * productIds转换成productId数组
     *
     * @param productIds 产品列表信息
     * @return 产品列表数组
     */
    private String[] transferStr2ProductIdList(String productIds) {
        return productIds.split(COMA_PRODUCT_ID);
    }

    /**
     * 构造前段的树节点
     *
     * @param loanCombDTOList 贷种组合列表
     * @param checked         是否勾选
     * @return TreeNodeList
     */
    public static List<TreeNode> buildTreeFromCombList(List<TbReportComb> loanCombDTOList, boolean checked) {
        List<TreeNode> treeNodes = new ArrayList<>();
        for (TbReportComb product : loanCombDTOList) {
            TreeNode treeNode = new TreeNode().setId(product.getCombCode())
                    .setName(product.getCombName() + "(" + product.getCombCode() + ")").setIsParent(false).setOpen(true);
            if (checked) {
                treeNode.setChecked(true);
            } else {
                treeNode.setChecked(false);
            }
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }

    /**
     * 构造产品树
     *
     * @param productList 产品列表
     * @param checked     是否勾选
     * @return TreeNodeList
     */
    private List<TreeNode> buildTreeFromProductList(List<ProductDO> productList, boolean checked) {
        List<TreeNode> treeNodes = new ArrayList<>();
        // TODO: 19-9-19 产品可能需要分类扩展预留字段
        for (ProductDO product : productList) {
            TreeNode treeNode = new TreeNode()
                    .setId(product.getProductCode())
                    .setName(product.getProductName() + "(" + product.getProductCode() + ")")
                    .setIsParent(true)
                    .setOpen(true);
            if (checked) {
                treeNode.setChecked(true);
            } else {
                treeNode.setChecked(false);
            }
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }

    /**
     * 构建LoanComposeDTO
     *
     * @param loanCombDO 贷种组合DO
     * @return TbReportComb
     */
    private TbReportComb buildLoanCombDTO(TbReportComb loanCombDO) {


        TbReportComb ss = new TbReportComb();
        ss.setCombId(loanCombDO.getCombId());
        ss.setCombCreateOper(loanCombDO.getCombCreateOper());
//        ss.setCombCreateTime(loanCombDO.getCombCreateTime());
        ss.setCombUpdateOper(loanCombDO.getCombUpdateOper());
//        ss.setCombUpdateTime(loanCombDO.getCombUpdateTime());
        ss.setCombName(loanCombDO.getCombName());
        ss.setCombCode(loanCombDO.getCombCode());
        ss.setCombStatus(loanCombDO.getCombStatus());
        ss.setCombLevel(loanCombDO.getCombLevel());

        return ss;
    }

    /**
     * 构建LoanComposeDetailDO
     *
     * @param productId  产品ID
     * @param combCode   产品编号
     * @param combStatus 产品状态
     * @return TbCombDetail
     */
    private TbCombDetail buildTbCombDetail(String productId, String combCode, int combStatus) {
        TbCombDetail tbCombDetail = new TbCombDetail();
        tbCombDetail.setStatus(combStatus);
        tbCombDetail.setProdCode(productId);
        tbCombDetail.setCombCode(combCode);
        tbCombDetail.setCreateTime(LocalDateTime.now());
        return tbCombDetail;
    }

    /**
     * 构建composeStatus
     *
     * @param productId  产品列表
     * @param combStatus 产品状态
     * @return LoanCombStatusDO
     */
    private LoanCombStatusDO buildLoanComposeStatusDO(String productId, int combStatus) {
        return new LoanCombStatusDO()
                .setCombCode(productId)
                .setCombStatus(combStatus);
    }

    /**
     * List<LoanComposeDO> -> List<LoanComposeDTO>
     *
     * @param loanCombDOList 贷种组合
     * @return 贷种组合DTO
     */
    private List<TbReportComb> transferDO2DTO(List<TbReportComb> loanCombDOList) {
        List<TbReportComb> loanComposeDTOList = new ArrayList<>();
        for (TbReportComb loanComposeDO : loanCombDOList) {
            TbReportComb loanComposeDTO = new TbReportComb();
            loanComposeDTO.setCombId(loanComposeDO.getCombId());
            loanComposeDTO.setCombCode(loanComposeDO.getCombCode());
            loanComposeDTO.setCombName(loanComposeDO.getCombName());
            loanComposeDTO.setCombUpdateOper(loanComposeDO.getCombUpdateOper());
//            loanComposeDTO.setCombCreateTime(loanComposeDO.getCombCreateTime());
            loanComposeDTO.setCombCreateOper(loanComposeDO.getCombCreateOper());
            loanComposeDTO.setCombStatus(loanComposeDO.getCombStatus());
            loanComposeDTO.setCombLevel(loanComposeDO.getCombLevel());
//            loanComposeDTO.setCombUpdateTime(loanComposeDO.getCombUpdateTime());
            loanComposeDTOList.add(loanComposeDTO);
        }
        return loanComposeDTOList;
    }

    /**
     * 转换DO转换成DTO
     *
     * @param loanCombDOList 贷种组合列表
     * @return LoanCombDTOV2List
     */
    private List<LoanCombDTOV2> transferDO2DTO2(List<TbReportComb> loanCombDOList) {
        List<LoanCombDTOV2> loanComposeDTO2List = new ArrayList<>();
        for (TbReportComb loanComposeDO : loanCombDOList) {
            LoanCombDTOV2 loanComposeDTO = new LoanCombDTOV2();
            loanComposeDTO.setCombId(loanComposeDO.getCombId());
            loanComposeDTO.setCombCode(loanComposeDO.getCombCode());
            loanComposeDTO.setCombName(loanComposeDO.getCombName());
            loanComposeDTO.setCombUpdater(loanComposeDO.getCombUpdateOper());
            loanComposeDTO.setCombCreateTime(loanComposeDO.getCombCreateTime());
            loanComposeDTO.setCombCreator(loanComposeDO.getCombCreateOper());
            loanComposeDTO.setCombStatus(loanComposeDO.getCombStatus());
            loanComposeDTO.setCombLevel(loanComposeDO.getCombLevel());
            loanComposeDTO.setCombUpdateTime(loanComposeDO.getCombUpdateTime());
            loanComposeDTO2List.add(loanComposeDTO);
        }

        return loanComposeDTO2List;
    }


    /**
     * 构建LoanComposeDO
     *
     * @param combId      贷种ID
     * @param combCode    贷种编码
     * @param combName    贷种名称
     * @param combLevel   贷种级别
     * @param combStatus  贷种状态
     * @param combCreator 贷种创建者
     * @param combUpdater 贷种更新者
     * @return 贷种组合信息
     */
    private TbReportComb buildLoanComposeDO(String combId, String combCode, String combName
            , int combLevel, int combStatus, String combCreator, String combUpdater) {
        LocalDateTime now = LocalDateTime.now();

        TbReportComb ss = new TbReportComb();
        ss.setCombId(combId);
        ss.setCombCode(combCode);
        ss.setCombName(combName);
        ss.setCombLevel(combLevel);
        ss.setCombStatus(combStatus);
//        ss.setCombCreateOper(combCreator);
//        ss.setCombCreateTime(now.toString());
//        ss.setCombCreateOper(combUpdater);
//        ss.setCombUpdateTime(now.toString());

        return ss;
    }


}