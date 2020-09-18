package com.boco.TONY.biz.product.controller;

import com.boco.TONY.biz.loancomb.POJO.DO.productbase.ProductDO;
import com.boco.TONY.biz.loancomb.POJO.DTO.productbase.ProductDTO;
import com.boco.TONY.biz.product.service.IWebProductService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.TONY.common.ListResult;
import com.boco.SYS.global.Dic;
import com.boco.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 信贷产品维护业务控制层
 * @author tony
 * @describe WebProductController
 * @date 2019-09-17
 */
@Controller
@RequestMapping("/webProduct")
public class WebProductController extends BaseController {
    @Autowired
    IWebProductService productService;

    @RequestMapping("/listUI")
    @SystemLog(tradeName = "产品组合查询页", accessType = Dic.AccessType.READ, funCode = "PM-26", funName = "查询产品组合信息", level = Dic.Debug.DEBUG)
    public String listProductPage() {
        return basePath + "/PM/webProductInfo/webProductInfoList";
    }

    @RequestMapping("/listAll")
    @ResponseBody
    @SystemLog(tradeName = "查询所有产品组合信息", accessType = Dic.AccessType.READ, funCode = "PM-26", funName = "查询所有产品组合信息", level = Dic.Debug.DEBUG)
    public String listAllProductInfo() throws Exception {
        String productCode = getParameter("productCode");
        String productName = getParameter("productName");
        String productLevel = getParameter("productLevel");
        String productSystemId = getParameter("productSystemId");
        String productStatus = getParameter("productStatus");
        ProductDO productDO = new ProductDO();
        productDO.setProductCode(productCode);
        if (StringUtils.isNotBlank(productLevel)) {
            productDO.setProductLevel(Integer.parseInt(productLevel));
        }
        productDO.setProductSystemId(productSystemId);
        productDO.setProductName(productName);
        productDO.setProductStatus(productStatus);
        setPageParam();
        List<ProductDO> list = productService.getAllProductComb2(productDO);
        return pageData(list);
    }

    @RequestMapping("/listAllAvailableProduct")
    @ResponseBody
    @SystemLog(tradeName = "查询所有产品组合信息", accessType = Dic.AccessType.READ, funCode = "PM-26", funName = "查询所有产品组合信息", level = Dic.Debug.DEBUG)
    public String listAllAvailableProductInfo() throws Exception {
        ListResult<ProductDTO> serviceResult = productService.getAllProductComb(null, null, null, null);
        setPageParam();
        return pageData(serviceResult.getData());
    }

    @RequestMapping(value = "selectProductName")
    @SystemLog(tradeName = "联想产品名称", funCode = "PM-07", funName = "联想输入", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody
    String selectProductName(HttpServletRequest request) throws Exception {
        String productName = request.getParameter("productName").replace("'","");
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<String> productNameList = productService.selectProductName(productName);
        List<Map<String, String>> list = new ArrayList<>();
        for (String data : productNameList) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        return JsonUtils.toJson(resultMap);
    }

    @RequestMapping(value = "selectProductCode")
    @SystemLog(tradeName = "联想产品名称", funCode = "PM-07", funName = "联想输入", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody
    String selectProductCode(HttpServletRequest request) throws Exception {
        String productCode = request.getParameter("productCode").replace("'","");
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<String> productCodeList = productService.selectProductCode(productCode);
        List<Map<String, String>> list = new ArrayList<>();
        for (String data : productCodeList) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        return JsonUtils.toJson(resultMap);
    }

    @RequestMapping(value = "selectSystemId")
    @SystemLog(tradeName = "联想所属业务系统", funCode = "PM-07", funName = "联想输入", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody
    String selectSystemId(HttpServletRequest request) throws Exception {
        String productName = request.getParameter("productSystemId").replace("'","");
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<String> systemIdList = productService.selectSystemId(productName);
        List<Map<String, String>> list = new ArrayList<>();
        for (String data : systemIdList) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        return JsonUtils.toJson(resultMap);
    }
}
