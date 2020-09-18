package com.boco.GF.controller;

import com.boco.GF.service.IWebTaskRoleInfoNewService;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.WebTaskRoleInfo;
import com.boco.SYS.entity.WebTaskRoleInfoNew;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.util.MapUtil;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Action控制层
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/webTaskRoleInfo/")
public class WebTaskRoleInfoController extends BaseController {
    @Autowired
    private IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    private IWebTaskRoleInfoNewService webTaskRoleInfoNewService;

    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "交易名称", funCode = "未填写", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/webTaskRoleInfo/webTaskRoleInfoList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "交易名称", funCode = "未填写", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(WebTaskRoleInfo webTaskRoleInfo) throws Exception {
        setAttribute("entity", webTaskRoleInfoService.selectByPK(MapUtil.beanToMap(webTaskRoleInfo)));
        return basePath + "/webTaskRoleInfo/webTaskRoleInfoInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "交易名称", funCode = "未填写", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/webTaskRoleInfo/webTaskRoleInfoEdit";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "未填写", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(WebTaskRoleInfo webTaskRoleInfo) throws Exception {
        setAttribute("entity", webTaskRoleInfoService.selectByPK(MapUtil.beanToMap(webTaskRoleInfo)));
        return basePath + "/webTaskRoleInfo/webTaskRoleInfoEdit";
    }

    /**
     * TODO 查询WEB_TASK_ROLE_INFO分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "未填写", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findPage(WebTaskRoleInfo webTaskRoleInfo) throws Exception {
        setPageParam();
        List<WebTaskRoleInfo> list = webTaskRoleInfoService.selectByAttr(webTaskRoleInfo);
        return pageData(list);
    }

    /**
     * TODO 新增WEB_TASK_ROLE_INFO.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "交易名称", funCode = "未填写", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(WebTaskRoleInfo webTaskRoleInfo) throws Exception {
        webTaskRoleInfoService.insertEntity(webTaskRoleInfo);
        return this.json.returnMsg("true", "新增成功!").toJson();
    }

    /**
     * TODO 修改WEB_TASK_ROLE_INFO.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "交易名称", funCode = "未填写", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(WebTaskRoleInfo webTaskRoleInfo) throws Exception {
        webTaskRoleInfoService.updateByPK(webTaskRoleInfo);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }

    /**
     * TODO 批量更新任务节点角色配置信息
     *
     * @return
     * @throws Exception <pre>
     *                                                                                           修改日期        修改人    修改原因
     *                                                                                           2016年6月13日    	   谷立羊  新建
     *                                                                                           </pre>
     */
    @RequestMapping(value = "updateTaskRoleInfo")
    @SystemLog(tradeName = "任务节点角色配置", funCode = "ACT-01-05", funName = "任务节点角色配置", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String updateTaskRoleInfo() throws Exception {
        String gridData = getParameter("gridData");
        String procDefId = getParameter("procDefId");
        List<Map<String, Object>> list = JsonUtils.toList(gridData);
        List<WebTaskRoleInfo> webTaskRoleInfoList = MapUtil.listMapToListBean(list, WebTaskRoleInfo.class);
        webTaskRoleInfoService.updateRoleInfoByBatch(webTaskRoleInfoList, procDefId);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }

    /**
     * TODO 维护流程反向跳转
     *
     * @return
     * @throws Exception <pre>
     *                                                                                           修改日期        修改人    修改原因
     *                                                                                           2016年8月8日    	   谷立羊  新建
     *                                                                                           </pre>
     */
    @RequestMapping(value = "updateTaskRoleInfoNew")
    @SystemLog(tradeName = "任务节点角色配置", funCode = "ACT-01-06", funName = "任务节点角色配置", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String updateTaskRoleInfoNew() throws Exception {
        String gridData = getParameter("gridData");
        String procDefId = getParameter("procDefId");
        List<Map<String, Object>> list = JsonUtils.toList(gridData);
        List<WebTaskRoleInfoNew> webTaskRoleInfoList = MapUtil.listMapToListBean(list, WebTaskRoleInfoNew.class);
        webTaskRoleInfoNewService.updateRoleInfoByBatch(webTaskRoleInfoList, procDefId);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }

    /**
     * TODO 删除WEB_TASK_ROLE_INFO
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "交易名称", funCode = "未填写", funName = "删除", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(WebTaskRoleInfo webTaskRoleInfo) throws Exception {
        webTaskRoleInfoService.deleteByPK(MapUtil.beanToMap(webTaskRoleInfo));
        return this.json.returnMsg("true", "删除成功!").toJson();
    }
}