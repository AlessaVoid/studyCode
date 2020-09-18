
package com.boco.SYS.controller;

import com.boco.GF.service.IWebTaskRoleInfoNewService;
import com.boco.GF.service.IWebTaskRoleInfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.WebTaskRoleInfo;
import com.boco.SYS.entity.WebTaskRoleInfoNew;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Pager;
import com.boco.util.JsonUtils;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping("/workflow/")
public class WorkFlowController extends BaseController {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IWebTaskRoleInfoService webTaskRoleInfoService;
    @Autowired
    private IWebTaskRoleInfoNewService webTaskRoleInfoNewService;

    /**
     * TODO 流程定义列表页面.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年1月20日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping("processDefinitionListUI")
    @SystemLog(tradeName = "流程管理", funCode = "ACT-01", funName = "加载流程定义列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String processDefinitionListUI() throws Exception {
        authButtons();
        return basePath + "/workflow/processDefinitionList";
    }


    /**
     * TODO 流程定义列表数据
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年1月20日    	    杨滔    新建
     * </pre>
     * @throws Exception
     */
    @RequestMapping("findProcessDefinitionPage")
    @SystemLog(tradeName = "流程管理", funCode = "ACT-01", funName = "加载流程定义列表数据", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findProcessDefinitionPage(Pager pager) throws Exception {
        List<Map<String, Object>> rows = workFlowService.getProcessDefinitionPage(pager);
        Map<String, Object> results = new Hashtable<String, Object>();
        results.put("pager.pageNo", pager.getPageNo());
        results.put("pager.totalRows", workFlowService.getProcessDefinitionCount());
        results.put("rows", rows);
        return JsonUtils.toJson(results);
    }

    /**
     * TODO 部署页面.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年1月20日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping("deployUI")
    @SystemLog(tradeName = "流程管理", funCode = "ACT-01-01", funName = "加载流程部署列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String deployUI() throws Exception {
        return basePath + "/workflow/deploy";
    }

    /**
     * TODO 跳转任务节点角色信息
     *
     * @return
     * @throws Exception <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2016年6月13日    	   谷立羊  新建
     *                                     </pre>
     */
    @RequestMapping("taskRoleConfigUI")
    @SystemLog(tradeName = "流程管理", funCode = "ACT-01-01", funName = "加载流程节点角色列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String taskRoleConfigUI() throws Exception {
        String procDefId = getParameter("procDefId");
        List<WebTaskRoleInfo> list = webTaskRoleInfoService.selectByProcDefId(procDefId);
        setAttribute("gridData", pageData(list));
        setAttribute("procDefId", procDefId);
        return basePath + "/workflow/taskRoleConfigList";
    }

    /**
     * TODO 维护驳回节点控制
     *
     * @return
     * @throws Exception <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2016年8月8日    	   谷立羊  新建
     *                                     </pre>
     */
    @RequestMapping("taskRoleConfigNewUI")
    @SystemLog(tradeName = "流程管理", funCode = "ACT-01-06", funName = "加载流程节点角色列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String taskRoleConfigNewUI() throws Exception {
        String procDefId = getParameter("procDefId");
        List<WebTaskRoleInfoNew> list = webTaskRoleInfoNewService.selectByProcDefId(procDefId);
        setAttribute("gridData", pageData(list));
        setAttribute("procDefId", procDefId);
        return basePath + "/workflow/taskRoleConfigNewList";
    }

    /**
     * TODO 部署流程.
     *
     * @param file
     * @return
     * @throws IOException <pre>
     *                                         修改日期        修改人    修改原因
     *                                         2016年2月22日    	    杨滔    新建
     *                                         </pre>
     */
    @RequestMapping("deploy")
    @SystemLog(tradeName = "流程管理", funCode = "ACT-01-01", funName = "部署流程", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String deploy(@RequestParam("file") CommonsMultipartFile file, String type) throws Exception {
        workFlowService.deployNewProcess(file);
        return this.json.returnMsg("true", "部署成功!").toJson();
    }

    /**
     * TODO 删除流程
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping("delete")
    @SystemLog(tradeName = "流程管理", funCode = "ACT-01-02", funName = "删除流程定义", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(String deploymentId) throws Exception {
        workFlowService.deleteProcess(deploymentId);
        return this.json.returnMsg("true", "删除成功!").toJson();
    }

    /**
     * TODO 通过流程定义查看流程资源.
     *
     * @param processDefinitionId
     * @param resourceType
     * @param response
     * @throws Exception <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2016年2月18日    	    杨滔    新建
     *                                     </pre>
     */
    @RequestMapping("showResource")
    @SystemLog(tradeName = "流程管理", funCode = "system", funName = "通过流程定义查看流程资源", accessType = AccessType.READ, level = Debug.DEBUG)
    public void loadByDeployment(String processDefinitionId, String resourceType, HttpServletResponse response) throws Exception {
        InputStream resourceAsStream = workFlowService.getResourceAsStream(processDefinitionId, resourceType);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * TODO 通过任务查看流程资源.
     *
     * @param resourceType
     * @param processInstanceId
     * @param taskId
     * @param response
     * @throws Exception <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2016年2月22日    	    杨滔    新建
     *                                     </pre>
     */
    @RequestMapping(value = "loadImgByProcessInstance")
    @SystemLog(tradeName = "流程管理", funCode = "system", funName = "通过任务查看流程资源", accessType = AccessType.READ, level = Debug.DEBUG)
    public void loadByProcessInstance(String resourceType, String processInstanceId, String taskId,
                                      HttpServletResponse response)
            throws Exception {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
        InputStream resourceAsStream = workFlowService.getResourceAsStream(processDefinition.getId(), resourceType);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * TODO 挂起/激活流程.
     *
     * @param type
     * @param processDefinitionId
     * @return
     * @throws IOException <pre>
     *                                         修改日期        修改人    修改原因
     *                                         2016年2月22日    	    杨滔    新建
     *                                         </pre>
     */
    @RequestMapping("changeStatus")
    @SystemLog(tradeName = "流程管理", funCode = "system", funName = "挂起/激活流程定义", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String changeStatus(String type, String processDefinitionId) throws Exception {
        if (type.equals("active")) {
            workFlowService.activateProcessDefinitionById(processDefinitionId);
            return this.json.returnMsg("true", "流程[" + processDefinitionId + "]激活成功!").toJson();
        } else if (type.equals("suspend")) {
            workFlowService.suspendProcessDefinitionById(processDefinitionId);
            return this.json.returnMsg("true", "流程[" + processDefinitionId + "]挂起成功!").toJson();
        } else {
            return this.json.returnMsg("false", "请求参数类型错误!").toJson();
        }
    }

    /**
     * TODO 任务列表页面.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年1月20日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping("taskListUI")
    @SystemLog(tradeName = "流程管理", funCode = "system", funName = "加载个人任务列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String taskListUI() throws Exception {
        authButtons();
        setAttribute("processDefinitionKey", getParameter("processDefinitionKey"));
        return basePath + "/workflow/taskList";
    }

    /**
     * TODO 加载当前流程图页面.
     *
     * @return
     * @throws Exception <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2016年2月24日    	    杨滔    新建
     *                                     </pre>
     */
    @RequestMapping("imageUI")
    @SystemLog(tradeName = "流程管理", funCode = "system", funName = "加载当前流程图页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String imageUI() throws Exception {
        try {
            String taskId = getParameter("taskId");
            String processInstanceId = getParameter("processInstanceId");
            setAttribute("taskId", taskId);
            setAttribute("processInstanceId", processInstanceId);
            //当前活动节点坐标
            Map<String, Object> coordinate = workFlowService.getCurrentActivitiCoordinate(taskId);
            //任务批注
            List<Comment> comments = workFlowService.getTaskComments(taskId);


            setAttribute("comments", BocoUtils.translateComments(comments,""));
            setAttribute("coordinate", coordinate);
        } catch (Exception e) {
            e.printStackTrace();
            setAttribute("isNew","false");
        }
        return basePath + "/workflow/image";
    }


    @RequestMapping("imageUIForPlan")
    @SystemLog(tradeName = "流程管理", funCode = "system", funName = "加载当前流程图页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String imageUIForPlan() throws Exception {
        try {
            String taskId = getParameter("taskId");
            String processInstanceId = getParameter("processInstanceId");
            setAttribute("taskId", taskId);
            setAttribute("processInstanceId", processInstanceId);
            //当前活动节点坐标
            Map<String, Object> coordinate = workFlowService.getCurrentActivitiCoordinate(taskId);
            //任务批注
            List<Comment> comments = workFlowService.getTaskComments(taskId);


            setAttribute("comments", BocoUtils.translateComments(comments,""));
            setAttribute("coordinate", coordinate);
        } catch (Exception e) {
            e.printStackTrace();
            setAttribute("isNew","false");
        }
        return basePath + "/workflow/imagePlan";
    }


    /**
     * TODO 加载当前流程图页面.
     *
     * @return
     * @throws Exception <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2016年2月24日    	    杨滔    新建
     *                                     </pre>
     */
    @RequestMapping("imageAndCommentUI")
    @SystemLog(tradeName = "流程管理", funCode = "system", funName = "加载当前流程图页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String imageAndCommentUI() throws Exception {
        String taskId = getParameter("taskId");
        String processInstanceId = getParameter("processInstanceId");
        setAttribute("taskId", taskId);
        setAttribute("processInstanceId", processInstanceId);
        //当前活动节点坐标
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

         setAttribute("comments",  BocoUtils.translateComments(comments,""));
        Map<String, Object> coordinate = workFlowService.getCurrentActivitiCoordinate(taskId);
        setAttribute("coordinate", coordinate);
        return basePath + "/workflow/imageAndComment";
    }



//    @RequestMapping("imageSubmitUI")
//    @SystemLog(tradeName = "流程管理", funCode = "system", funName = "加载当前流程图页面", accessType = AccessType.READ, level = Debug.DEBUG)
//    public String imageSubmitUI() throws Exception {
//        String processInstanceId = getParameter("processInstanceId");
//        //当前活动节点坐标
//        Map<String, Object> coordinate = workFlowService.getCurrentActivitiCoordinateByProcessInstanceId(processInstanceId);
//        Task task = workFlowService.getTaskByProcessInstanceId(processInstanceId);
//        setAttribute("taskId", task.getId());
//        setAttribute("processInstanceId", processInstanceId);
//        setAttribute("coordinate", coordinate);
//        return basePath + "/workflow/image";
//    }

    /**
     * TODO 任务列表数据.
     *
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2016年2月22日    	    杨滔    新建
     *                                     </pre>
     */
    @RequestMapping("findTaskPage")
    @SystemLog(tradeName = "流程管理", funCode = "system", funName = "加载个人任务列表数据", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findTaskPage(Pager pager) throws Exception {
        String assignee = getSessionUser().getOpercode();////登录用户
        String processDefinitionKey = getParameter("processDefinitionKey");//从菜单传来的流程定义Key
        List<Map<String, Object>> rows = workFlowService.getPersonalTaskPage(processDefinitionKey, pager);
        Map<String, Object> results = new Hashtable<String, Object>();
        results.put("pager.pageNo", pager.getPageNo());
        results.put("pager.totalRows", workFlowService.getPersonalTaskCount(processDefinitionKey, assignee));
        results.put("rows", rows);
        return JsonUtils.toJson(results);
    }

    /**
     * TODO 查看业务表单.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2016年2月22日    	    杨滔    新建
     *                                     </pre>
     */
    @RequestMapping("viewTaskForm")
    @SystemLog(tradeName = "流程管理", funCode = "system", funName = "根据FormKey获取任务表单", accessType = AccessType.READ, level = Debug.DEBUG)
    public String viewTaskForm(String taskId) throws Exception {
        //通过taskId获取fromKey
        String formKey = workFlowService.getTaskFormKeyByTaskId(taskId);
        if (StringUtils.isBlank(formKey)) {
            throw new SystemException("流程节点FormKey未配置!");
        }
        //通过taskId获取业务key
        String businessKey = workFlowService.getBusinessKeyByTaskId(taskId);
        if (StringUtils.isBlank(businessKey)) {
            throw new SystemException("单据标识为空!");
        }
        //拼接业务表单地址
        String formUrl = formKey + "?businessKey=" + businessKey + "&taskId=" + taskId;
        return "redirect:" + formUrl;
    }

    /**
     * TODO 节点审批.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2016年2月22日    	    杨滔    新建
     *                                     </pre>
     */
    @RequestMapping("completeTask")
    @SystemLog(tradeName = "流程管理", funCode = "system", funName = "节点审批", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String completeTask(String taskId, String comment, String isAgree, String assignee) throws Exception {
        Map<String, Object> varMap = new HashMap<String, Object>();
        varMap.put("isAgree", getParameter("isAgree"));
        varMap.put("assignee", getParameter("assignee"));
        workFlowService.completeTask(taskId, comment, varMap);
        return this.json.returnMsg("true", "审批成功!").toJson();
    }

    /**
     * TODO 获取部署流程活动节点.
     *
     * @param
     * @return
     * @throws Exception <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2016年6月14日    	   谷立羊  新建
     *                                     </pre>
     */
    @RequestMapping("getTaskIdByProcDefId")
    @SystemLog(tradeName = "获取部署流程活动节点", funCode = "ACT-01-05", funName = "获取部署流程活动节点", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getTaskIdByProcDefId(String procDefId) throws Exception {
        Map<String, Object> resultMap = workFlowService.getTaskIdByProcDefId(procDefId);
        return JsonUtils.toJson(resultMap);
    }

    /**
     * TODO获取产品审批后所有状态
     *
     * @return
     * @throws Exception <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2016年9月6日    	   谷立羊  新建
     *                                     </pre>
     */
    @RequestMapping("getProdStatus")
    @SystemLog(tradeName = "获取限额平台审批所有状态", funCode = "ACT-01-05", funName = "获取产品所有状态", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getAppStatus() throws Exception {
//        List<Map<String, Object>> list = DicCache.getGroupByCode("D_PROD_STATUS");
        List<Map<String, Object>> list = DicCache.getGroupByCode("D_REQ_STATUS");
        List<Map<String, Object>> listTemp = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> mapTemp : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("key", mapTemp.get("DICT_VALUE_IN"));
            map.put("value", mapTemp.get("DICT_KEY_IN"));
            listTemp.add(map);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("list", listTemp);
        return JsonUtils.toJson(resultMap);
    }

    /**
     * TODO 查询角色信息
     *
     * @return
     * @throws Exception <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2016年6月14日    	   谷立羊  新建
     *                                     </pre>
     */
    @RequestMapping("getTaskRole")
    @SystemLog(tradeName = "获取部署流程活动节点", funCode = "ACT-01-05", funName = "获取角色信息", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getTaskRole() throws Exception {
        Map<String, Object> resultMap = workFlowService.getTaskRole();
        return JsonUtils.toJson(resultMap);
    }

    /**
     * TODO初始化流程配置.
     *
     * @param processDefinitionId
     * @param key
     * @return
     * @throws Exception <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2016年10月15日    	   谷立羊  新建
     *                                     </pre>
     */
    @RequestMapping("nodeConfig")
    @SystemLog(tradeName = "初始化流程配置", funCode = "ACT-01-07", funName = "初始化流程配置", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String nodeConfig(String processDefinitionId, String key) throws Exception {
        String type = "01";
        if (key.equals("ProdResearch")) {
            type = "01";
        } else if (key.equals("ProdDesign")) {
            type = "02";
        } else if (key.equals("ProdChange")) {
            type = "03";
        } else if (key.equals("ProdCapital")) {
            type = "04";
        }
        webTaskRoleInfoService.nodeConfig(processDefinitionId, type);
        webTaskRoleInfoNewService.nodeConfig(processDefinitionId, type);
        return json.returnMsg("true", "初始化已完成！").toJson();
    }
}
