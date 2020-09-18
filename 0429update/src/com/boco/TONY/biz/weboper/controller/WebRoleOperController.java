package com.boco.TONY.biz.weboper.controller;

import com.alibaba.fastjson.JSON;
import com.boco.PM.service.IFdOrganService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.TONY.biz.line.POJO.DTO.ProductLineInfoDTO;
import com.boco.TONY.biz.weboper.POJO.DTO.WebOperLineDTO;
import com.boco.TONY.biz.line.service.IWebLineProductService;
import com.boco.TONY.biz.weboper.POJO.VO.WebOperRoleVO;
import com.boco.TONY.biz.weboper.service.IWebOperLineService;
import com.boco.TONY.biz.weboper.service.IWebOperRoleService;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 柜员-角色绑定业务控制层
 * @author tony
 * @describe WebRoleOperController
 * @date 2019-09-07
 */
@RequestMapping("/v1/oper")
@Controller
public class WebRoleOperController extends BaseController {
    @Autowired
    IWebOperRoleService webOperRoleService;
    @Autowired
    IFdOrganService iFdOrganService;
    @Autowired
    IWebOperLineService iWebOperLineService;
    @Autowired
    IWebLineProductService webLineProductService;
    @Autowired
    private FdOrganMapper fdOrganMapper;
    /**
     * 更新角色-柜员信息
     *
     * @param request HttpServletRequest
     */
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    @ResponseBody
    @SystemLog(tradeName = "柜员角色更新", funCode = "PM-07-04", funName = "柜员角色更新", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String updateRoleOperRelationship(HttpServletRequest request, HttpSession session) {

        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        String operCode = request.getParameter("operCode").trim();
        String roleInfo = request.getParameter("roleInfo").trim();
        roleInfo = roleInfo.substring(0, roleInfo.length() - 1);
        WebOperRoleVO webOperRoleVO = new WebOperRoleVO();
        webOperRoleVO.setRoleId(roleInfo);
        webOperRoleVO.setOperCode(operCode);
        webOperRoleVO.setModifyOper(fdOper.getOpercode());
        return JsonUtils.toJson(webOperRoleService.updateOperAndRoleRelation(webOperRoleVO));
    }

    /***
     *柜员角色编辑页
     * @param request HTTP REQUEST
     * @return ModelAndView
     * @throws Exception EX
     */
    @RequestMapping(value = "/role/page", method = RequestMethod.GET)
    @SystemLog(tradeName = "柜员角色编辑页", funCode = "PM-07-04", funName = "柜员角色编辑页", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public ModelAndView showRoleOperRelationshipUpdatePage(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("system/PM/webOperInfo/webOperRoleUpdate");
        String operCode = request.getParameter("operCode");
        String deptCode = request.getParameter("deptCode");
        String organCode = request.getParameter("organCode");
        if (StringUtils.isEmpty(operCode) || StringUtils.isEmpty(deptCode)) {
            throw new IllegalArgumentException("path:[/role/page] operCode or deptCode is null");
        }
        FdOrgan fdOrgan = iFdOrganService.selectByPK(organCode);
        String organLevel = fdOrgan.getOrganlevel();
        if (organLevel.equals("3")) {
            String uporgan = fdOrgan.getUporgan();
            FdOrgan fdUpOrgan = fdOrganMapper.selectByPK(uporgan);
            List<String> specialOrgan = new ArrayList<String>();
            specialOrgan.add("11000013");//北京
            specialOrgan.add("31000017");//上海
            specialOrgan.add("12004390");//天津
            specialOrgan.add("50016751");//重庆
            specialOrgan.add("33000072");//宁波
            specialOrgan.add("35008816");//厦门
            specialOrgan.add("44008995");//深圳
            specialOrgan.add("37000013");//青岛
            specialOrgan.add("21014952");//大连
            if (specialOrgan.contains(uporgan)){
//                    ||fdUpOrgan.getOrganlevel().equalhes("1")) {
                organLevel = "2";
            }
//        } else if (organLevel.equals("4")) {
//            String uporgan = fdOrgan.getUporgan();
//            FdOrgan fdUpOrgan = fdOrganMapper.selectByPK(uporgan);
//            if (fdUpOrgan.getOrganlevel().equals("1")) {
//                organLevel = "2";
//            }
        }
        modelAndView.addObject("operCode", operCode);
        modelAndView.addObject("deptCode", deptCode);
        modelAndView.addObject("organLevel", organLevel);
        return modelAndView;
    }

    /**
     * 查询柜员已拥有角色
     *
     * @param request HTTP REQUEST
     * @return 获取柜员角色列表
     */
    @RequestMapping(value = "/role/me", method = RequestMethod.POST)
    @SystemLog(tradeName = "柜员已拥有角色", funCode = "PM-07-04", funName = "柜员拥有角色", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    @ResponseBody
    public String getOperRoleList(HttpServletRequest request) {
        String operCode = request.getParameter("operCode").trim();
        if (StringUtils.isEmpty(operCode)) {
            throw new IllegalArgumentException("WebRoleOperController|getOperRoleList operCode is null");
        }
        return JsonUtils.toJson(webOperRoleService.selectOwnRoleByOperCodeForListResult(operCode));
    }

    /***
     *柜员角色条线页
     * @param request HTTP REQUEST
     * @return ModelAndView
     */
    @RequestMapping(value = "/line/edit", method = RequestMethod.GET)
    @ResponseBody
    @SystemLog(tradeName = "柜员角色编辑页", funCode = "PM-07-04", funName = "柜员角色编辑页", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public ModelAndView showOperProductLinePage(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("system/PM/webOperInfo/webOperLineUpdate");
        String operCode = request.getParameter("operCode");
        String organCode = request.getParameter("organCode");
        setAttribute("operCode", operCode);
        setAttribute("organCode", organCode);
        modelAndView.addObject("operCode", operCode);
        modelAndView.addObject("organCode", organCode);
        return modelAndView;
    }

    /**
     * 更新柜员-条线信息
     *
     * @param operCode     操作柜员号
     * @param lineInfoList 条线产品
     * @return RS
     */
    @RequestMapping(value = "/line/update", method = RequestMethod.POST)
    @ResponseBody
    @SystemLog(tradeName = "柜员条线编辑页", funCode = "PM-07-06", funName = "柜员条线编辑页", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String updateOperProductLinePage(@RequestParam String operCode, @RequestParam String lineInfoList) {
        PlainResult<String> result = iWebOperLineService.updateWebOperLine(operCode, lineInfoList);
        return JSON.toJSONString(result);
    }

    /**
     * 获取柜员拥有的条线
     *
     * @param operCode 操作柜员号
     * @return RS
     */
    @RequestMapping(value = "/line/product", method = RequestMethod.POST)
    @ResponseBody
    @SystemLog(tradeName = "获取柜员拥有的条线", funCode = "PM-07-06", funName = "获取柜员拥有的条线", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String getOperProductLine(@RequestParam String operCode) {
        ListResult<WebOperLineDTO> result = iWebOperLineService.getAllWebOperLineByOperCode(operCode, 1);
        return JSON.toJSONString(result);
    }

    /**
     * 获取柜员拥有的条线
     *
     * @return RS
     */
    @RequestMapping(value = "/line/all", method = RequestMethod.GET)
    @ResponseBody
    @SystemLog(tradeName = "获取柜员拥有的条线", funCode = "PM-07-06", funName = "获取柜员拥有的条线", accessType = Dic.AccessType.WRITE, level = Dic.Debug.INFO)
    public String getAllProductLine(@RequestParam("organCode") String organCode) throws Exception {
        ListResult<ProductLineInfoDTO> result = webLineProductService.getProductLineInfoByOrganCode(null, null, organCode);
        return JSON.toJSONString(result);
    }
}
