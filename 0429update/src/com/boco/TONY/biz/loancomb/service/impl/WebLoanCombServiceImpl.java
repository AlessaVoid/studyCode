package com.boco.TONY.biz.loancomb.service.impl;

import com.boco.SYS.entity.TbCombDetail;
import com.boco.SYS.mapper.LoanCombDetailMapper;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.mapper.ProductCombMapper;
import com.boco.SYS.util.TreeNode;
import com.boco.TONY.biz.line.POJO.DO.ProductLineCombDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDTO;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombStatusDO;
import com.boco.TONY.biz.loancomb.POJO.DO.productbase.ProductDO;
import com.boco.TONY.biz.loancomb.POJO.DO.productbase.ProductStatusDO;
import com.boco.TONY.biz.loancomb.POJO.DTO.combbase.LoanCombDTOV2;
import com.boco.TONY.biz.loancomb.exception.LoanCombDetailException;
import com.boco.TONY.biz.loancomb.exception.LoanCombException;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tony
 * @describe WebLoanCombServiceImpl
 * @date 2019-09-17
 */

@Service
public class WebLoanCombServiceImpl implements IWebLoanCombService {
    @Autowired
    LoanCombMapper loanCombMapper;
    @Autowired
    ProductCombMapper productCombMapper;
    @Autowired
    LoanCombDetailMapper loanCombDetailMapper;
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
        List<LoanCombDO> loanComposeList;
        ListResult<TreeNode> result = new ListResult<>();

        try {

            switch (combLevel) {
                case CombLevelConstants.COMPOSE_LEVEL_0:
                case CombLevelConstants.COMPOSE_LEVEL_1:
                case CombLevelConstants.COMPOSE_LEVEL_2:
                    loanComposeList = loanCombMapper.getOrganLoanProductInfoByLevel(combLevel + DELTA);
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
        List<LoanCombDO> loanComposeList;
        ListResult<TreeNode> result = new ListResult<>();
        ProductLineCombDO productLineCombDO = new ProductLineCombDO();
        productLineCombDO.setCombLevel(combLevel);
        productLineCombDO.setOrganCode(organCode);
        try {
            loanComposeList = loanCombMapper.getLineLoanProductInfoByLevelAndOrganCode(productLineCombDO);
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
        return loanCombMapper.selectCombName(combName);
    }

    /**
     * 贷种编码
     *
     * @param combCode 贷种编码
     * @return
     */
    @Override
    public List<String> selectCombCode(String combCode) {
        return loanCombMapper.selectCombCode(combCode);
    }

    @Override
    public int countCombListSize() {
        return loanCombMapper.countCombListSize();
    }

    @Override
    public PlainResult<String> checkCombInfo(String combCode, String combName) throws LoanCombException {
        PlainResult<String> result = new PlainResult<>();
        LoanCombDO combNameRecord = loanCombMapper.selectExactlyCombName(combName);
        LoanCombDO combCodeRecord = loanCombMapper.getLoanCombInfoByCombCode(combCode);
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
        return loanCombMapper.selectComb(combMap);
    }

    @Override
    public List<Map<String, Object>> selectCombOfBind(Map<String, Object> combMap) {
        return loanCombMapper.selectCombOfBind(combMap);
    }

    @Override
    public List<Map<String, Object>> selectCombByOpercode(HashMap<String, Object> map) {
        return loanCombMapper.selectCombByOpercode(map);
    }

    @Override
    public List<Map<String, Object>> selectCombBycombcode(HashMap<String, Object> map) {
        return loanCombMapper.selectCombBycombcode(map);
    }

    /**
     * 通过贷种Level查询贷种组合
     *
     * @param combLevel 贷种级别
     * @return TreeNodeList
     */
    @Override
    public ListResult<LoanCombDTO> getLoanCombInfoByLevel2(int combLevel) {
        Preconditions.checkArgument(combLevel == CombLevelConstants.COMPOSE_LEVEL_0 || combLevel == CombLevelConstants.COMPOSE_LEVEL_1
                || combLevel == CombLevelConstants.COMPOSE_LEVEL_2 || combLevel == CombLevelConstants.COMPOSE_LEVEL_3);
        ListResult<LoanCombDTO> result = new ListResult<>();
        try {
            List<LoanCombDO> loanCombList = loanCombMapper.getOrganLoanProductInfoByLevel(combLevel + DELTA);
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
            loanCombMapper.insertLoanCombInfo(buildLoanComposeDO(combId, combCode, combName, combLevel, CombStatusEnum.COMB_FREE.status, combCreator, combCreator));
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
                        loanCombMapper.updateLoanCombStatus(loanComposeStatusDO);
                        loanCombDetailMapper.insertLoanComposeChildProduct(
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
                        loanCombDetailMapper.insertLoanComposeChildProduct(TbCombDetail);
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
     * 获取所有的组合产品
     *
     * @return LoanComposeDTO2List
     */
    @Override
    public List<LoanCombDTOV2> getAllLoanCombInfo() {
        return getAllLoanCombInfo2(null, null, null);
    }

    /**
     * 通过级别查询所有组合的name和code
     *
     * @param combLevel
     * @return
     */
    public List<LoanCombDO> getCombCodeList(int combLevel) {
        List<LoanCombDO> lcdo = new ArrayList<>();
        try {
            lcdo = loanCombMapper.getCombCodeList(combLevel);
        } catch (LoanCombException e) {
            e.printStackTrace();
        }
        return lcdo;
    }

    /**
     * 获取所有的组合产品列表
     *
     * @return LoanComposeDTO2List
     */
    @Override
    public List<LoanCombDTOV2> getAllLoanCombInfo2(String combCode, String combName, String combLevel) {
        try {
            //将属性封装到bean对象中
            LoanCombDO loanCombDO = new LoanCombDO().setCombCode(combCode).setCombName(combName);
            if (Objects.nonNull(combLevel) && StringUtils.isNotBlank(combLevel)) {
                //comblevel不为空，转化为int设置近bean
                loanCombDO.setCombLevel(Integer.parseInt(combLevel));
            }
            //在tb_comb中查出满足对应属性并且状态不为-1的所有行
            List<LoanCombDO> loanCombInfoList = loanCombMapper.getAllLoanCombInfoList(loanCombDO);
            //将这些封装到list<loancombDTOV2>中
            List<LoanCombDTOV2> loanCombDTO2List = transferDO2DTO2(loanCombInfoList);

            List<LoanCombDTOV2> loanCombDTO2ListRes = new ArrayList<>();
            //循环遍历list
            for (LoanCombDTOV2 loanCombDTOV2 : loanCombDTO2List) {
                //获取list中对应的combcode
                String combCode1 = loanCombDTOV2.getCombCode();
                List<String> childrenCombList;
                try {
                    //在tb_comb_detail表中通过combcode查询prod_code(可能多个)并且状态为1，封装到list中
                    childrenCombList = loanCombDetailMapper.getSelectedLoanComposeInfoByCombCode(combCode1);
                    String childCombStr = "";
                    for (String childCombCode : childrenCombList) {
                        //通过comb_code在tb_comb中查询*
                        LoanCombDO childComb = loanCombMapper.getLoanCombNameByCombCode(childCombCode);
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
            }
            return loanCombDTO2ListRes;
        } catch (Exception e) {
            return new ArrayList<>();
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
            LoanCombDO loanCombDO = new LoanCombDO().setCombCode(combCode).setCombName(combName);
            if (Objects.nonNull(combLevel) && StringUtils.isNotBlank(combLevel)) {
                loanCombDO.setCombLevel(Integer.parseInt(combLevel));
            }
            PageHelper.startPage(pageNum, pageSize, true, true, true);
            List<LoanCombDO> loanCombInfoList = loanCombMapper.getAllLoanCombInfoList(loanCombDO);
            //返回页面的分页数据
            if (CollectionUtils.isEmpty(loanCombInfoList)) {
                results.put("pager.pageNo", 1);
                results.put("pager.totalRows", 0);
                results.put("rows", new ArrayList<>());
            }
            PageInfo<LoanCombDO> page = new PageInfo<>(loanCombInfoList);
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
                        childrenCombList = loanCombDetailMapper.getSelectedLoanComposeInfoByCombCode(combCode1);
                        String childCombStr = "";
                        for (String childCombCode : childrenCombList) {
                            LoanCombDO childComb = loanCombMapper.getLoanCombInfoByCombCode(childCombCode);
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
                        childrenCombList = loanCombDetailMapper.getSelectedLoanComposeInfoByCombCode(combCode1);
                        String childCombStr = "";
                        for (String childCombCode : childrenCombList) {
                           String prodName = loanCombMapper.getProdInfoByProdCode(childCombCode);
//                            prodName==null||"null".equalsIgnoreCase(String.valueOf(prodName))
                            if (prodName!=null||!"null".equalsIgnoreCase(String.valueOf(prodName))) {
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
    public PlainResult<LoanCombDTO> getLoanCombInfoByCombCode(String combCode) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(combCode));

        PlainResult<LoanCombDTO> result = new PlainResult<>();
        try {
            LoanCombDO loanComposeDO = loanCombMapper.getLoanCombInfoByCombCode(combCode);
            LoanCombDTO loanComposeDTO = buildLoanCombDTO(loanComposeDO);
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
            List<String> combCodeList = loanCombDetailMapper.getSelectedLoanComposeInfoByCombCode(parentCombCode);
            List<LoanCombDTO> loanComposeDTOList = new ArrayList<>();
            switch (combLevel) {
                case CombLevelConstants.COMPOSE_LEVEL_1:
                case CombLevelConstants.COMPOSE_LEVEL_2:
                    for (String combCode : combCodeList) {
                        LoanCombDO loanComposeDO = loanCombMapper.getLoanCombInfoByCombCode(combCode);
                        // TODO: 19-9-21 console NPE
                        if (!Objects.isNull(loanComposeDO)) {
                            LoanCombDTO loanComposeDTO = buildLoanCombDTO(loanComposeDO);
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
            loanCombMapper.deleteLoanCombByCombCode(combCode);
            List<String> productCodeList = loanCombDetailMapper.getSelectedLoanComposeInfoByCombCode(combCode);
            for (String childProductCode : productCodeList) {
                TbCombDetail loanComposeDetailDO = new TbCombDetail();
                loanComposeDetailDO.setProdCode(childProductCode);
                loanComposeDetailDO.setCombCode(combCode);
                loanComposeDetailDO.setStatus(CombDetailStatusEnum.COMB_DETAIL_DELETE.status);
                loanCombDetailMapper.updateLoanComposeProductInfo(loanComposeDetailDO);
                if (loanComposeDetailDO.getStatus().equals(CombDetailStatusEnum.COMB_DETAIL_DELETE.status)) {
                    loanCombDetailMapper.deleteLoanComposeProductInfo(loanComposeDetailDO);
                }
            }
            //释放当前贷种组合占用的产品
            switch (combLevel) {
                case CombLevelConstants.COMPOSE_LEVEL_1:
                case CombLevelConstants.COMPOSE_LEVEL_2:
                    for (String productId : productCodeList) {
                        LoanCombStatusDO loanComposeStatusDO = new LoanCombStatusDO().setCombCode(productId).setCombStatus(CombStatusEnum.COMB_FREE.status);
                        loanCombMapper.updateLoanCombStatus(loanComposeStatusDO);
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
            LoanCombDO loanCombDO = new LoanCombDO().setCombCode(combCode).setCombName(combName).setCombLevel(combLevel).setCombUpdater(combUpdater).setCombUpdateTime(LocalDateTime.now());
            loanCombMapper.updateLoanCombInfo(loanCombDO);
            List<String> oldProductIdList = loanCombDetailMapper.getLoanComposeInfoByCombCode(combCode); //老产品表
            for (String productId : oldProductIdList) {
                boolean contains = newProductList.contains(productId);
                if (contains) {
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
                            loanCombDetailMapper.insertLoanComposeChildProduct(loanComposeDetailDO);
                        } else {
                            loanCombDetailMapper.updateLoanComposeProductInfo(loanComposeDetailDO);
                        }
                        LoanCombStatusDO loanCombStatusDO = new LoanCombStatusDO();
                        loanCombStatusDO.setCombCode(productCode);
                        loanCombStatusDO.setCombStatus(CombStatusEnum.COMB_TAKEN.status);
                        loanCombMapper.updateLoanCombStatus(loanCombStatusDO);

                    }
                    boolean isCancel = doCancel(canceledProductCodeList, combCode);//放行未组合产品
                    if (isCancel) {
                        return result.success("update", "update loan comb product success");
                    } else {
                        return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "cancel loan comb product success");
                    }
                case CombLevelConstants.COMPOSE_LEVEL_3:
                    for (String productCode : newProductList) {
                        if (canceledProductCodeList.contains(productCode)) {
                            continue;
                        }
                        if (!existProductCodeList.contains(productCode)) {
                            TbCombDetail loanComposeDetailDO = buildTbCombDetail(productCode, combCode, CombDetailStatusEnum.COMB_DETAIL_NORMAL.status);
                            ProductDO selectedCombProduct = productCombMapper.getSelectedCombProduct(productCode);
                            loanComposeDetailDO.setProdSysId(selectedCombProduct.getProductSystemId());
                            loanCombDetailMapper.insertLoanComposeChildProduct(loanComposeDetailDO);
                            ProductStatusDO productStatusDO = new ProductStatusDO()
                                    .setProductId(productCode).setProductStatus(ProductStatusEnum.PRODUCT_TAKEN.status);
                            productCombMapper.updateCombProductStatus(productStatusDO);
                        }
                    }
                    for (String productId : canceledProductCodeList) {
                        ProductStatusDO productStatusDO = new ProductStatusDO()
                                .setProductId(productId).setProductStatus(ProductStatusEnum.PRODUCT_FREE.status);
                        TbCombDetail loanComposeDetailDO = new TbCombDetail();
                        loanComposeDetailDO.setStatus(CombDetailStatusEnum.COMB_DETAIL_DELETE.status);
                        loanComposeDetailDO.setCombCode(combCode);
                        loanComposeDetailDO.setProdCode(productId);
//                        loanCombDetailMapper.updateLoanComposeProductInfo(loanComposeDetailDO);
                        loanCombDetailMapper.deleteLoanComposeProductInfo(loanComposeDetailDO);
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
                loanCombMapper.updateLoanCombStatus(buildLoanComposeStatusDO(productId, CombStatusEnum.COMB_FREE.status));
                TbCombDetail TbCombDetail = new TbCombDetail();
                TbCombDetail.setCombCode(combCode);
                TbCombDetail.setProdCode(productId);
                TbCombDetail.setStatus(CombDetailStatusEnum.COMB_DETAIL_DELETE.status);
                loanCombDetailMapper.updateLoanComposeProductInfo(TbCombDetail);
                if (TbCombDetail.getStatus().equals(CombDetailStatusEnum.COMB_DETAIL_DELETE.status)) {
                    loanCombDetailMapper.deleteLoanComposeProductInfo(TbCombDetail);
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
    public ListResult<LoanCombDTO> getLoanCombInfoByName(String name) {
        ListResult<LoanCombDTO> result = new ListResult<>();
        try {
            List<LoanCombDO> loanComposeDOList = loanCombMapper.getLoanCombInfoByName(name);
            List<LoanCombDTO> loanComposeDTOList = transferDO2DTO(loanComposeDOList);
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
    public static List<TreeNode> buildTreeFromCombList(List<LoanCombDTO> loanCombDTOList, boolean checked) {
        List<TreeNode> treeNodes = new ArrayList<>();
        for (LoanCombDTO product : loanCombDTOList) {
            TreeNode treeNode = new TreeNode().setId(product.getCombCode())
                    .setName(product.getCombName()+"("+product.getCombCode()+")").setIsParent(false).setOpen(true);
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
                    .setName(product.getProductName()+"("+product.getProductCode()+")")
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
     * @return LoanCombDTO
     */
    private LoanCombDTO buildLoanCombDTO(LoanCombDO loanCombDO) {
        return new LoanCombDTO()
                .setCombId(loanCombDO.getCombId())
                .setCombCreator(loanCombDO.getCombCreator())
                .setCombCreateTime(loanCombDO.getCombCreateTime())
                .setCombUpdater(loanCombDO.getCombUpdater())
                .setCombUpdateTime(loanCombDO.getCombUpdateTime())
                .setCombName(loanCombDO.getCombName())
                .setCombCode(loanCombDO.getCombCode())
                .setCombStatus(loanCombDO.getCombStatus())
                .setCombLevel(loanCombDO.getCombLevel());
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
        return  tbCombDetail;
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
    private List<LoanCombDTO> transferDO2DTO(List<LoanCombDO> loanCombDOList) {
        List<LoanCombDTO> loanComposeDTOList = new ArrayList<>();
        for (LoanCombDO loanComposeDO : loanCombDOList) {
            LoanCombDTO loanComposeDTO = new LoanCombDTO()
                    .setCombId(loanComposeDO.getCombId())
                    .setCombCode(loanComposeDO.getCombCode())
                    .setCombName(loanComposeDO.getCombName())
                    .setCombUpdater(loanComposeDO.getCombUpdater())
                    .setCombCreateTime(loanComposeDO.getCombCreateTime())
                    .setCombCreator(loanComposeDO.getCombCreator())
                    .setCombStatus(loanComposeDO.getCombStatus())
                    .setCombLevel(loanComposeDO.getCombLevel())
                    .setCombUpdateTime(loanComposeDO.getCombUpdateTime());
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
    private List<LoanCombDTOV2> transferDO2DTO2(List<LoanCombDO> loanCombDOList) {
        List<LoanCombDTOV2> loanComposeDTO2List = new ArrayList<>();
        for (LoanCombDO loanComposeDO : loanCombDOList) {
            LoanCombDTOV2 loanComposeDTO = new LoanCombDTOV2()
                    .setCombId(loanComposeDO.getCombId())
                    .setCombCode(loanComposeDO.getCombCode())
                    .setCombName(loanComposeDO.getCombName())
                    .setCombUpdater(loanComposeDO.getCombUpdater())
                    .setCombCreateTime(loanComposeDO.getCombCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .setCombCreator(loanComposeDO.getCombCreator())
                    .setCombStatus(loanComposeDO.getCombStatus())
                    .setCombLevel(loanComposeDO.getCombLevel())
                    .setCombUpdateTime(loanComposeDO.getCombUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            loanComposeDTO2List.add(loanComposeDTO);
        }

        return loanComposeDTO2List;
    }

    /**
     * localDateTime准换
     *
     * @param localDateTime 本地时间
     * @return 贷种时间
     */
    private static String localDateTime2StringDate(LocalDateTime localDateTime) {
        String date = "";
        int year = localDateTime.getYear();
        date += year;
        int month = localDateTime.getMonth().getValue();
        if (month <= 9) {
            date += "0" + month;
        } else {
            date += month;
        }
        int day = localDateTime.getDayOfMonth();
        if (day <= 9) {
            date += "0" + day;
        } else {
            date += day;
        }
        return date;
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
    private LoanCombDO buildLoanComposeDO(String combId, String combCode, String combName
            , int combLevel, int combStatus, String combCreator, String combUpdater) {
        LocalDateTime now = LocalDateTime.now();
        return new LoanCombDO()
                .setCombId(combId)
                .setCombCode(combCode)
                .setCombName(combName)
                .setCombLevel(combLevel)
                .setCombStatus(combStatus)
                .setCombCreator(combCreator)
                .setCombCreateTime(now)
                .setCombUpdater(combUpdater)
                .setCombUpdateTime(now);
    }

    /**
     * List<LoanComposeDTO> -> List<LoanComposeDO>
     *
     * @param loanCombDTOList 貸種組合DTO
     * @return loanCombDOList
     */
    @SuppressWarnings("unused")
    private List<LoanCombDO> transferDTO2DO(List<LoanCombDTO> loanCombDTOList) {
        // TODO: 19-9-20 remain this for the extension of the future
        List<LoanCombDO> loanCombDOList = new ArrayList<>();
        for (LoanCombDTO loanComposeDTO : loanCombDTOList) {
            LoanCombDO loanComposeDO = new LoanCombDO()
                    .setCombId(loanComposeDTO.getCombId())
                    .setCombCode(loanComposeDTO.getCombCode())
                    .setCombCreateTime(loanComposeDTO.getCombCreateTime())
                    .setCombCreator(loanComposeDTO.getCombCreator())
                    .setCombStatus(loanComposeDTO.getCombStatus())
                    .setCombLevel(loanComposeDTO.getCombLevel())
                    .setCombUpdateTime(loanComposeDTO.getCombUpdateTime());
            loanCombDOList.add(loanComposeDO);
        }
        return loanCombDOList;
    }


}