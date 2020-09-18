package com.boco.TONY.biz.product.service.impl;

import com.boco.SYS.mapper.ProductCombMapper;
import com.boco.TONY.biz.loancomb.POJO.DO.productbase.ProductDO;
import com.boco.TONY.biz.loancomb.POJO.DTO.productbase.ProductDTO;
import com.boco.TONY.biz.product.exception.LoanProductException;
import com.boco.TONY.biz.product.service.IWebProductService;
import com.boco.TONY.common.ListResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 信贷产品业务逻辑层实现
 *
 * @author tony
 * @describe WebProductServiceImpl
 * @date 2019-09-17
 */
@Service
public class WebProductServiceImpl implements IWebProductService {
    @Autowired
    ProductCombMapper productMapper;

    /**
     * 查询所有贷种产品
     * notes: state=2　　　表示已经被占用不可以被其他的组合进行占用
     *
     * @param productCode     产品编码
     * @param productName     产品名称
     * @param productLevel    产品级别
     * @param productSystemId 产品系统id
     * @return 产品列表
     */
    @Override
    public ListResult<ProductDTO> getAllProductComb(String productCode, String productName, String productLevel, String productSystemId) {
        ListResult<ProductDTO> result = new ListResult<>();
        ProductDO productDO = new ProductDO();
        productDO.setProductCode(productCode);
        if (StringUtils.isNotBlank(productLevel)) {
            productDO.setProductLevel(Integer.parseInt(productLevel));
        }
        productDO.setProductSystemId(productSystemId);
        productDO.setProductName(productName);
        try {
            List<ProductDO> productInfoList = productMapper.getAllCombProductInfo(productDO);
            List<ProductDTO> productDTOS = transferDO2DTO(productInfoList);
            return result.success(productDTOS, "load product info success");
        } catch (LoanProductException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, " inner error,find  all product compose failed");
        }
    }

    /**
     * 获取所有产品组合
     *
     * @param productDO 产品数据访问
     * @return
     * @throws LoanProductException
     */
    @Override
    public List<ProductDO> getAllProductComb2(ProductDO productDO) throws LoanProductException {
        return productMapper.getAllCombProduct(productDO);
    }

    /**
     * 查询所有未占用产品
     *
     * @return
     */
    @Override
    public ListResult<ProductDTO> listAllAvailableProduct() {
        ListResult<ProductDTO> result = new ListResult<>();
        try {
            List<ProductDO> productDOList = productMapper.getAllAvailableCombProduct();
            List<ProductDTO> productDTOS = buildProductDTO(productDOList);
            return result.success(productDTOS, "list all available product success");
        } catch (LoanProductException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "inner error for list all available product");
        }
    }

    /**
     * 构建ProductDTO
     *
     * @param productDOList
     * @return
     */
    private List<ProductDTO> buildProductDTO(List<ProductDO> productDOList) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (ProductDO productDO : productDOList) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(productDO.getProductId());
            productDTO.setProductName(productDO.getProductName());
            productDTO.setProductCode(productDO.getProductCode());
            productDTO.setProductLevel(productDO.getProductLevel() + "");
            productDTO.setProductSystemId(productDO.getProductSystemId());
            productDTO.setProductStatus(productDO.getProductStatus());
            productDTO.setProductBelong(productDO.getProductBelong());
            productDTO.setProductUpCode(productDO.getProductUpCode());
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    /**
     * 查询所有不可组合的产品
     *
     * @return
     */
    public ListResult<ProductDTO> listAllUnavailableCombProduct() {
        ListResult<ProductDTO> result = new ListResult<>();
        try {
            List<ProductDO> productDOList = productMapper.getAllUnavailableCombProduct();
            List<ProductDTO> productDTOList = buildProductDTO(productDOList);
            return result.success(productDTOList, "get all un available product success");
        } catch (LoanProductException e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "get all unavailable product failed");
        }

    }

    /**
     * 查询选中的产品列表详情
     *
     * @param productCodeList
     * @return
     */
    @Override
    public List<ProductDO> getSelectedCombProduct(List<String> productCodeList) {
        List<ProductDO> productDOList = new ArrayList<>();
        for (String productCode : productCodeList) {
            ProductDO productDO = productMapper.getSelectedCombProduct(productCode);
            productDOList.add(productDO);
        }
        return productDOList;
    }

    /**
     * @param productName 产品名称
     * @return
     */
    @Override
    public List<String> selectProductName(String productName) {
        return productMapper.selectProductName(productName);
    }

    /**
     * 根据产品id选择产品信息
     *
     * @return
     */
    @Override
    public List<String> selectProductCode(String productCode) {
        return productMapper.selectProdCode(productCode);
    }

    /**
     * @param systemId 系统id
     * @return
     */
    @Override
    public List<String> selectSystemId(String systemId) {
        return productMapper.selectSystemId(systemId);
    }

    /**
     * 构建ProductDTO
     *
     * @param productInfoList
     * @return
     */
    private List<ProductDTO> transferDO2DTO(List<ProductDO> productInfoList) {
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (ProductDO productDO : productInfoList) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(productDO.getProductId());
            productDTO.setProductName(productDO.getProductName());
            productDTO.setProductBelong(productDO.getProductBelong());
            productDTO.setProductLevel(productDO.getProductLevel() + "");
            productDTO.setProductSystemId(productDO.getProductSystemId());
            productDTO.setProductStatus(productDO.getProductStatus());
            productDTO.setProductUpCode(productDO.getProductUpCode());
            productDTO.setProductCode(productDO.getProductCode());
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }
}
