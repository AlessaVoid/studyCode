package com.boco.SYS.controller;

import com.boco.PM.service.IWebDeptInfoService;
import com.boco.PM.service.IWebOperInfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebDeptInfo;
import com.boco.SYS.entity.WebLogInfo;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.IWebLogInfoService;
import com.boco.SYS.util.TreeNode;
import com.boco.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务日志表Action控制层
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/webLogInfo/")
public class WebLogInfoController extends BaseController {
    @Autowired
    private IWebLogInfoService webLogInfoService;
    @Autowired
    private IWebOperInfoService webOperInfoService;
    @Autowired
    private IWebDeptInfoService webDeptInfoService;

    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "系统日志查询", funCode = "PM-20", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/SYS/webLogInfo/webLogInfoList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "系统日志查询", funCode = "PM-20-01", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.INFO)
    public String infoUI(WebLogInfo webLogInfo) throws Exception {
        setAttribute("webLogInfoDTO", webLogInfoService.selectByPK(webLogInfo.getId()));
        return basePath + "/SYS/webLogInfo/webLogInfoInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "交易名称", funCode = "未填写", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/webLogInfo/webLogInfoEdit";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "未填写", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(WebLogInfo webLogInfo) throws Exception {
        setAttribute("webLogInfo", webLogInfoService.selectByPK(webLogInfo.getId()));
        return basePath + "/webLogInfo/webLogInfoEdit";
    }

    /**
     * TODO 验证柜员是否是领导.
     *
     * @param webLogInfo
     * @return
     * @throws Exception <pre>
     *                                                                                                                               修改日期        修改人    修改原因
     *                                                                                                                               2016年3月8日    	  杜旭    新建
     *                                                                                                                               </pre>
     */
    @RequestMapping("checkIsLeader")
    @SystemLog(tradeName = "系统日志查询", funCode = "PM-20", funName = "验证柜员是否是领导", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String checkIsLeader(WebLogInfo webLogInfo, HttpServletRequest request) throws Exception {
        FdOper fdOper = getSessionUser();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("organCode", fdOper.getOrgancode());
        map.put("operCode", fdOper.getOpercode());
        WebOperInfo webOperInfo = webOperInfoService.selectByPK(map);
        if (webOperInfo == null) {
            json.returnMsg("false", "您没有维护人员信息，无法进行系统日志查询交易");
        }
        return json.toJson();
    }

    /**
     * TODO 查询WEB_LOG_INFO分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "系统日志查询", funCode = "PM-20", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findPage(WebLogInfo webLogInfo, WebOperInfo webOperInfo, HttpSession session) throws Exception {
        FdOper fdOper = getSessionUser();
        setPageParam();
        webLogInfo.setOrganCode(fdOper.getOrgancode());
        webLogInfo.setOperCode(fdOper.getOpercode());
        List<WebLogInfo> list = webLogInfoService.selectByAttr(webLogInfo);
        return pageData(list);
    }

    /**
     * TODO 判断查询数据.
     *
     * @param webLogInfo
     * @param fdOper
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年3月8日    	  杜旭    新建
     * </pre>
     * @throws Exception
     */
    @RequestMapping(value = "checkSelectData", method = RequestMethod.POST)
    @SystemLog(tradeName = "系统日志查询", funCode = "PM-20", funName = "校验查询数据", accessType = AccessType.READ, level = Debug.DEBUG)
    @ResponseBody
    public String checkSelectData(WebLogInfo webLogInfo, FdOper fdOper, HttpSession session) throws Exception {
        fdOper = getSessionUser();
        WebOperInfo webOperInfo = new WebOperInfo();
        HashMap<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("organCode", fdOper.getOrgancode());
        queryMap.put("operCode", fdOper.getOpercode());
        webOperInfo = webOperInfoService.selectByPK(queryMap);
        String operDept = "", upOperDept = "";
        if (webOperInfo != null) {
            HashMap map = new HashMap();
            map.put("organcode", fdOper.getOrgancode());
            WebDeptInfo webDeptInfo = webDeptInfoService.selectByPK(map);
            if (webDeptInfo != null) {
                if (StringUtils.isNotEmpty(webDeptInfo.getUpDeptCode())) {
                    upOperDept = webDeptInfo.getUpDeptCode();
                }
            }
        }

        boolean checkOperDept = webLogInfoService.checkOperDept(webLogInfo, fdOper, operDept, upOperDept);
        if (checkOperDept == false) {
            return json.toJson();
        }

        boolean checkOperCode = webLogInfoService.checkOperCode(webLogInfo, fdOper, operDept, upOperDept);
        if (checkOperCode == false) {
            return json.returnMsg("false", "1").toJson();
        }
        return json.returnMsg("true", "校验成功").toString();
    }


    /**
     * TODO 系统日志查询获取机构部门信息.
     *
     * @param webDeptInfo
     * @return
     * @throws Exception <pre>
     *                                                                                                                               修改日期        修改人    修改原因
     *                                                                                                                               2016年3月8日    	  杜旭    新建
     *                                                                                                                               </pre>
     */
    @RequestMapping(value = "getWebDeptInfo")
    @SystemLog(tradeName = "系统日志查询", funCode = "PM-20", funName = "系统日志查询获取机构部门信息", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getWebDeptInfo(WebLogInfo webLogInfo) throws Exception {
        Map<String, List<TreeNode>> result = new HashMap<String, List<TreeNode>>();
        List<TreeNode> list = webLogInfoService.getWebDeptInfo();
        result.put("treeNodes", list);
        return JsonUtils.toJson(result);
    }

}