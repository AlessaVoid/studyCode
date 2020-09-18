package com.boco.TONY.biz.line.controller;

import com.alibaba.fastjson.JSON;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic;
import com.boco.SYS.util.TreeNode;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.line.POJO.DTO.ProductLineInfoDTO;
import com.boco.TONY.biz.line.service.IWebLineProductService;
import com.boco.TONY.biz.line.service.impl.WebLineProductServiceImpl;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDTO;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.util.JsonUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 部门条线业务控制层
 *
 * @author tony
 * @describe WebLineProductController
 * @date 2019-09-23
 */
@RequestMapping("/webLineProduct")
@Controller
public class WebLineProductController extends BaseController {
    @Autowired
    IWebLineProductService webLineProductService;

    @RequestMapping(value = "/listUI", method = RequestMethod.GET)
    @SystemLog(tradeName = "展示条线管理界面", funCode = "PM-28", funName = "展示条线管理界面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listProductLineIndexUI() throws Exception {
        authButtons();
        return basePath + "/PM/webLineProduct/webLineProductInfoList";
    }

    @RequestMapping(value = "/insertUI", method = RequestMethod.GET)
    @SystemLog(tradeName = "新增条线UI界面", funCode = "PM-28-01", funName = "新增条线UI界面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listProductILineInsertUI() throws Exception {
        authButtons();
        FdOrgan sessionOrgan = getSessionOrgan();
        setAttribute("organCode", sessionOrgan.getThiscode());
        setAttribute("organName", sessionOrgan.getOrganname());
        return basePath + "/PM/webLineProduct/webLineProductInfoAdd";
    }

    @RequestMapping(value = "/updateUI", method = RequestMethod.GET)
    @SystemLog(tradeName = "更新条线UI界面", funCode = "PM-28-02", funName = "更新条线UI界面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listProductLineUpdateUI(@RequestParam String lineId) throws Exception {
        authButtons();
        FdOrgan sessionOrgan = getSessionOrgan();
        setAttribute("organCode", sessionOrgan.getThiscode());
        setAttribute("organName", sessionOrgan.getOrganname());
        ProductLineInfoDTO data = webLineProductService.getProductLineInfoByLineId(lineId);
        setAttribute("webLineProduct", data);
        return basePath + "/PM/webLineProduct/webLineProductInfoEdit";
    }

    @RequestMapping(value = "/infoUI", method = RequestMethod.GET)
    @SystemLog(tradeName = "显示条线详情UI界面", funCode = "PM-28", funName = "显示条线详情UI界面", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listProductLineInfoUI(@RequestParam String lineId) throws Exception {
        ProductLineInfoDTO lineProduct = webLineProductService.getProductLineInfoByLineId(lineId);
        FdOrgan sessionOrgan = getSessionOrgan();
        setAttribute("organCode", sessionOrgan.getThiscode());
        setAttribute("organName", sessionOrgan.getOrganname());
        setAttribute("webLineProduct", lineProduct);
        return basePath + "/PM/webLineProduct/webLineProductInfoInfo";
    }

    @RequestMapping(value = "/listChildLineProductInfo", method = RequestMethod.GET)
    @SystemLog(tradeName = "查询条线下的所有的产品", funCode = "PM-28", funName = "查询条线下的所有的产品", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listChildrenLineProductInfo(String lineId) {
        ListResult<LoanCombDTO> loanComposeDTOListResult = webLineProductService.getProductLineDetailInfoByLineIdWithoutTree(lineId);
        return JSON.toJSONString(loanComposeDTOListResult.getData());
    }

    @ResponseBody
    @RequestMapping("/listAllLineProduct")
    @SystemLog(tradeName = "列出所有条线", funCode = "PM-28", funName = "列出所有条线", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllLineProductInfo() throws Exception {
        authButtons();
        FdOrgan sessionOrgan = getSessionOrgan();
        String lineId = getParameter("lineId");
        String lineName = getParameter("lineName");
        ProductLineInfoDO productLineInfoDO = new ProductLineInfoDO().setLineId(lineId).setLineName(lineName).setOrganCode(sessionOrgan.getThiscode());
        setPageParam();
        List<ProductLineInfoDO> productLineInfoDOList = webLineProductService.getProductLineInfoByOrganCode2(productLineInfoDO);
        Map<String, Object> results = new Hashtable<>();
        if (productLineInfoDOList == null) {
            results.put("pager.pageNo", 1);
            results.put("pager.totalRows", 0);
            results.put("rows", new ArrayList<>());
            return JsonUtils.toJson(results);
        }
        PageInfo<ProductLineInfoDO> page = new PageInfo<>(productLineInfoDOList);
        results.put("pager.pageNo", page.getPageNum());
        results.put("pager.totalRows", page.getTotal());
        List<ProductLineInfoDTO> productLineInfoDTOList = WebLineProductServiceImpl.buildProductLineInfoDTOFromList(productLineInfoDOList);
        results.put("rows", productLineInfoDTOList);
        return JSON.toJSONString(results);
    }

    @ResponseBody
    @RequestMapping(value = "/listAllSelectedProductLine", method = RequestMethod.GET)
    @SystemLog(tradeName = "列出当期条线下的产品", funCode = "PM-28", funName = "列出当期条线下的产品", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String listAllSelectedProductLineInfo(@RequestParam String lineId) throws Exception {
        authButtons();
        ListResult<TreeNode> treeNodeListResult = webLineProductService.getProductLineDetailInfoByLineId(lineId);
        return JSON.toJSONString(treeNodeListResult);
    }

    @ResponseBody
    @RequestMapping(value = "/insertProductLine", method = RequestMethod.POST)
    @SystemLog(tradeName = "插入产品线", funCode = "PM-28", funName = "创建贷种组合", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String insertProductLineInfo(@RequestParam String lineName, @RequestParam String description
            , @RequestParam String productIds) throws Exception {
        authButtons();
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        PlainResult<String> result = webLineProductService.insertProductLineInfo(
                lineName, description, sessionOperInfo.getOperCode(), productIds, sessionOperInfo.getOrganCode());
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/updateLineProductInfo", method = RequestMethod.POST)
    @SystemLog(tradeName = "更新条线信息", funCode = "PM-28", funName = "更新条线信息", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String updateLineProductInfo(@RequestParam String lineId, @RequestParam String lineName, @RequestParam String lineDescription
            , @RequestParam String productIds) throws Exception {
        WebOperInfo sessionOperInfo = getSessionOperInfo();
        authButtons();
        PlainResult<String> result = webLineProductService.updateProductLineInfo(lineId, lineName, lineDescription, sessionOperInfo.getOperCode(), productIds);
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteLineProductInfo", method = RequestMethod.POST)
    @SystemLog(tradeName = "删除条线记录", funCode = "PM-28", funName = "删除条线记录", accessType = Dic.AccessType.WRITE, level = Dic.Debug.DEBUG)
    public String deleteLineProductInfo(@RequestParam String lineId) throws Exception {
        authButtons();
        PlainResult<String> result = webLineProductService.deleteProductLineInfo(lineId);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "selectLineName")
    @SystemLog(tradeName = "联想产品名称", funCode = "PM-07", funName = "联想输入", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody
    String selectLineName(HttpServletRequest request) throws Exception {
        FdOrgan sessionOrgan = getSessionOrgan();
        String lineName = request.getParameter("lineName").replace("'","");
        ProductLineInfoDO productLineInfoDO = new ProductLineInfoDO().setLineName(lineName).setOrganCode(sessionOrgan.getThiscode());
        List<String> lineNameList = webLineProductService.getProductLineInfoByOrganCode3(productLineInfoDO);
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
//        List<String> lineNameList = webLineProductService.selectLineNameByOrgan(lineName,sessionOrgan);
        List<Map<String, String>> list = new ArrayList<>();
        for (String data : lineNameList) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        return JsonUtils.toJson(resultMap);
    }

    @RequestMapping(value = "selectLineCode")
    @SystemLog(tradeName = "联想产品名称", funCode = "PM-07", funName = "联想输入", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public @ResponseBody
    String selectLineCode(HttpServletRequest request) throws Exception {
        String lineId = request.getParameter("lineId").replace("'","");
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<String> lineIdList = webLineProductService.selectLineCode(lineId);
        List<Map<String, String>> list = new ArrayList<>();
        for (String data : lineIdList) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        return JsonUtils.toJson(resultMap);
    }
}
