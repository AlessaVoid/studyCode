package com.boco.PM.controller;

//import com.boco.AL.service.impl.PunishInterestService;
import com.boco.AL.service.impl.PunishInterestService;
import com.boco.PM.service.IFdOrganService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbTradeParam;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WebDeptInfoAction控制层
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/WebOrganInfo/")
public class WebOrganInfoController extends BaseController {
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private IFdBusinessDateService fdBusinessDateService;
    @Autowired
    private PunishInterestService punishInterestService;
    @Autowired
    private FdOrganMapper fdOrganMapper;

    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "机构管理列表", funCode = "PM-06", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PM/webOrganInfo/webOrganInfoList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "机构管理详细信息", funCode = "PM-06-04", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI() throws Exception {
        String organCode = getParameter("organCode");
        FdOrgan fdOrgan = fdOrganService.selectByPK(organCode);
        setAttribute("fdOrgan", fdOrgan);
        return basePath + "/PM/webOrganInfo/webOrganInfoInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "机构管理新增", funCode = "PM-06-01", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/PM/webOrganInfo/webOrganInfoAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "机构管理修改", funCode = "PM-06-02", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(FdOrgan fdOrgan) throws Exception {
        List<FdOrgan> fdOrganList = fdOrganService.selectByAttr(fdOrgan);
        setAttribute("organInfo", fdOrganList.get(0));
        return basePath + "/PM/webOrganInfo/webOrganInfoEdit";
    }

    /**
     * TODO 查询WEB_DEPT_INFO分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "机构管理列表", funCode = "PM-06", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findPage(FdOrgan fdOrgan) throws Exception {
        setPageParam();
        String organCode = getParameter("thiscode");
        String organName = getParameter("organname");
        fdOrgan.setUporgan(getSessionOrgan().getThiscode());

        fdOrgan.setOrganname(organName);
        fdOrgan.setThiscode(organCode);
        List<FdOrgan> fdOrganList = fdOrganService.selectByLikeAttr(fdOrgan);
        return pageData(fdOrganList);
    }

    @RequestMapping(value = "findList", method = RequestMethod.POST)
    @SystemLog(tradeName = "机构管理", funCode = "system", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String find(HttpServletRequest request, FdOrgan fdOrgan) throws Exception {
        setPageParam();
//        punishInterestService.punishCalculate(1, 1, 1, 1, new BigDecimal("1"), new BigDecimal("1"), new BigDecimal("1"));
        String sort = request.getParameter("sort");
        String direction = request.getParameter("direction");
        if (sort != null) {
            sort = sort + " " + direction;
        }
        String organCode = getParameter("thiscode");
        String organName = getParameter("organname");
        String organlevel = getParameter("organlevel");
        String uporgan =getParameter("uporgan");
        List<String> thiscodeList = null;

        if(uporgan != null && !"".equals(uporgan)){
            thiscodeList = fdOrganMapper.selectthiscodeByLikeOrganname(uporgan);
        }
        if (organlevel == null || "".equals(organlevel)) {
            organlevel = getSessionOrgan().getOrganlevel();
            Map<String, Object> map = new HashMap<>();
            map.put("organcode", organCode);
            map.put("organname", organName);
            map.put("organlevel", organlevel);
            map.put("session_organcode", getSessionOrgan().getThiscode());
            map.put("sort", sort);
            map.put("uporgan",thiscodeList);
            List<FdOrgan> list = fdOrganService.selectList(map);
            for (FdOrgan organ : list) {
                String cityName = fdOrganMapper.selectOrganName(organ.getCitycode());
                organ.setCitycode(cityName);
                String areaName = fdOrganMapper.selectOrganName(organ.getAreacode());
                organ.setAreacode(areaName);
                String upOrganName = fdOrganMapper.selectOrganName(organ.getUporgan());
                organ.setUporgan(upOrganName);
            }
            return pageData(list);
        } else {
            organlevel = getParameter("organlevel");
            String thisorganlevel = getSessionOrgan().getOrganlevel();

            Map<String, Object> map = new HashMap<>();
            map.put("thisorganlevel", thisorganlevel);
            map.put("organcode", organCode);
            map.put("organname", organName);
            map.put("organlevel", organlevel);
            map.put("session_organcode", getSessionOrgan().getThiscode());
            map.put("sort", sort);
            map.put("uporgan",thiscodeList);

            List<FdOrgan> list = fdOrganService.selectByLevel(map);
            for (FdOrgan organ : list) {
                String cityName = fdOrganMapper.selectOrganName(organ.getCitycode());
                organ.setCitycode(cityName);
                String areaName = fdOrganMapper.selectOrganName(organ.getAreacode());
                organ.setAreacode(areaName);
                String upOrganName = fdOrganMapper.selectOrganName(organ.getUporgan());
                organ.setUporgan(upOrganName);
            }
            return pageData(list);
        }
    }

    /**
     * TODO 新增WEB_DEPT_INFO.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "机构管理新增", funCode = "PM-06-01", funName = "新增WebDeptInfo", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(FdOrgan fdOrgan, HttpSession session) throws Exception {
        //最后操作员
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");

        String latestModifyDate = fdBusinessDateService.getCommonDateDetails();
        fdOrgan.setModifyoper(fdOper.getOpercode());
        fdOrgan.setModifyorgan(fdOper.getOrgancode());
        //当前日期、当前时间
        fdOrgan.setModifydate(latestModifyDate);
        fdOrganService.updateByPK(fdOrgan);
        fdOrganService.insertEntity(fdOrgan);
        return this.json.toJson();


    }

    /**
     * TODO 修改WEB_DEPT_INFO.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "机构管理修改", funCode = "PM-06-02", funName = "修改WebDeptInfo", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(FdOrgan fdOrgan, HttpSession session) throws Exception {
        //当前操作员
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        fdOrgan.setModifyoper(fdOper.getOpercode());
        fdOrgan.setModifyorgan(fdOper.getOrgancode());
        //当前日期、当前时间
        String latestModifyDate = fdBusinessDateService.getCommonDateDetails();
        fdOrgan.setModifydate(latestModifyDate);
        fdOrganService.updateByPK(fdOrgan);
        return this.json.toJson();
    }

    /**
     * TODO 删除WEB_DEPT_INFO
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "机构管理删除", funCode = "PM-06-03", funName = "删除WebDeptInfo", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete() throws Exception {
        String organCode = getParameter("organCode");
        fdOrganService.deleteByPK(organCode);
        json.setSuccess("true").setMsg("机构删除成功");
        return this.json.toJson();
    }

    /**
     * TODO 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectCode")
    @SystemLog(tradeName = "机构代码联想输入", funCode = "PM-06", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectCode(HttpServletRequest request) throws Exception {
        String thisCode = getParameter("thiscode").replace("'", "");
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setThiscode(thisCode);
        fdOrgan.setUporgan(getSessionOrgan().getThiscode());
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        if (thisCode.length() > 4) {
            List<String> organCodeList = fdOrganService.selectByThisCode(fdOrgan);
            for (String data : organCodeList) {
                Map<String, String> map = new HashMap<>();
                map.put("key", data);
                map.put("value", data);
                list.add(map);
            }
            resultMap.put("list", list);
        }
        return JsonUtils.toJson(resultMap);
    }

    /**
     * TODO 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      秦海洲      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectName")
    @SystemLog(tradeName = "机构名称联想输入", funCode = "PM-06", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectName() throws Exception {
        String organName = getParameter("organname").replace("'", "");
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setOrganname(organName);
        fdOrgan.setUporgan(getSessionOrgan().getThiscode());
        List<String> organNameList = fdOrganService.selectByName(fdOrgan);
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        for (String data : organNameList) {
            Map<String, String> map = new HashMap<>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        return JsonUtils.toJson(resultMap);
    }

    }
