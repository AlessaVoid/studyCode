package com.boco.SYS.service.impl;

import com.boco.PM.service.IFdOperService;
import com.boco.PM.service.IWebRoleInfoService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.entity.WebRoleInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.SYS.mapper.WebOperInfoMapper;
import com.boco.SYS.mapper.WebOperRoleMapper;
import com.boco.SYS.mapper.WebRoleInfoMapper;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.Pager;
import com.boco.SYS.util.StringUtil;
import com.boco.SYS.util.WebContextUtil;
import org.activiti.engine.*;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipInputStream;

@Service
public class WorkFlowService implements IWorkFlowService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private IWebRoleInfoService webRoleInfoService;
    @Autowired
    private IFdOperService fdOperService;
    @Autowired
    private WebOperRoleMapper webOperRoleMapper;
    @Autowired
    private WebOperInfoMapper webOperInfoMapper;
    @Autowired
    private FdOrganMapper fdOrganMapper;
    @Autowired
    private WebRoleInfoMapper webRoleInfoMapper;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void deployNewProcess(CommonsMultipartFile file) throws Exception {
        try {
            String fileName = file.getOriginalFilename();
            InputStream fileInputStream = file.getInputStream();
            String extension = FilenameUtils.getExtension(fileName);
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                repositoryService.createDeployment().addZipInputStream(zip).deploy();
            } else {
                repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            }

        } catch (Exception e) {
            //w112-部署流程失败!
            throw new SystemException("w112", e);
        }
    }

    @Override
    public long getProcessDefinitionCount() throws Exception {
        return repositoryService.createProcessDefinitionQuery().count();
    }

    @Override
    public List<Map<String, Object>> getProcessDefinitionPage(Pager pager) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionKey()
                .desc()
                .orderByProcessDefinitionVersion()
                .desc()
                .listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        for (ProcessDefinition processDefinition : processDefinitionList) {
            map = new HashMap<String, Object>();
            map.put("id", processDefinition.getId());
            map.put("name", processDefinition.getName());
            map.put("key", processDefinition.getKey());
            map.put("version", processDefinition.getVersion());
            map.put("xml", processDefinition.getResourceName());
            map.put("image", processDefinition.getDiagramResourceName());
            map.put("deploymentId", processDefinition.getDeploymentId());
            map.put("status", processDefinition.isSuspended() + "");
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(processDefinition.getDeploymentId()).singleResult();
            map.put("deploymentTime", df.format(deployment.getDeploymentTime()));
            rows.add(map);
        }
        return rows;
    }

    @Override
    public void deleteProcess(String deploymentId) throws Exception {
        // 普通删除，如果当前规则下有正在执行的流程，则抛异常
        repositoryService.deleteDeployment(deploymentId);
        // 级联删除,会删除和当前规则相关的所有信息，包括历史
        //repositoryService.deleteDeployment(deploymentId, true);
    }

    @Override
    public InputStream getResourceAsStream(String processDefinitionId, String resourceType) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        if (processDefinition == null) {
            //流程定义[{0}]不存在!
            throw new SystemException("w114", processDefinitionId);
        }
        String resourceName = "";
        if ("image".equals(resourceType)) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if ("xml".equals(resourceType)) {
            resourceName = processDefinition.getResourceName();
        } else {
            //资源类型不正确!
            throw new SystemException("w115");
        }
        return repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
    }

    @Override
    public Map<String, Object> getCurrentActivitiCoordinate(String taskId) throws Exception {
        ActivityImpl activity = getActivityImplByTaskId(taskId);
        Map<String, Object> coordinate = new HashMap<String, Object>();
        coordinate.put("x", activity.getX());
        coordinate.put("y", activity.getY());
        coordinate.put("width", activity.getWidth());
        coordinate.put("height", activity.getHeight());
        coordinate.put("documentation", activity.getProperties().get("documentation"));
        return coordinate;
    }

    @Override
    public Map<String, Object> getCurrentActivitiCoordinateByProcessInstanceId(String processInstanceId) throws Exception {
        ActivityImpl activity = getActivityImplByProcessInstanceId(processInstanceId);
        Map<String, Object> coordinate = new HashMap<String, Object>();
        coordinate.put("x", activity.getX());
        coordinate.put("y", activity.getY());
        coordinate.put("width", activity.getWidth());
        coordinate.put("height", activity.getHeight());
        coordinate.put("documentation", activity.getProperties().get("documentation"));
        return coordinate;
    }

    @Override
    public void activateProcessDefinitionById(String processDefinitionId) throws Exception {
        repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
    }

    @Override
    public void suspendProcessDefinitionById(String processDefinitionId) throws Exception {
        repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
    }

    @Override
    public ProcessInstance startProcess(String processDefinitionKey, Map<String, Object> variables) throws Exception {
        return runtimeService.startProcessInstanceByKey(processDefinitionKey, (String) variables.get("businessKey"), variables);
    }

    @Override
    public long getPersonalTaskCount(String processDefinitionKey, String assignee) throws Exception {
        //如果传入流程定义key，就查某个流程的任务，否则查所有流程的任务
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            return taskService.createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskAssignee(assignee)
                    .count();
        } else {
            return taskService
                    .createTaskQuery()
                    .taskAssignee(assignee)
                    .count();
        }
    }

    @Override
    public List<Map<String, Object>> getPersonalTaskPage(String processDefinitionKey, Pager pager) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        List<Task> taskList = new ArrayList<Task>();
        //如果传入流程定义key，就查某个流程的任务，否则查所有流程的任务
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            taskList = taskService
                    .createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .active()//仅查激活状态的任务，挂起的不查
                    .orderByTaskCreateTime()
                    .desc()
                    .listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        } else {
            taskList = taskService
                    .createTaskQuery()
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .active()//仅查激活状态的任务，挂起的不查
                    .orderByTaskCreateTime()
                    .desc()
                    .listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        }
        for (Task task : taskList) {
            map = new HashMap<String, Object>();
            map.put("taskId", task.getId());
            map.put("name", task.getName());
            map.put("createTime", task.getCreateTime());
            map.put("assignee", task.getAssignee());
            map.put("description", task.getDescription());
            map.put("processInstanceId", task.getProcessInstanceId());
            rows.add(map);
        }
        return rows;
    }

    @Override
    public String getPersonalTaskActiveCount(String processDefinitionKey) throws Exception {
        List<Task> taskList = new ArrayList<Task>();
        //如果传入流程定义key，就查某个流程的任务，否则查所有流程的任务
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            taskList = taskService
                    .createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .active()//仅查激活状态的任务，挂起的不查
                    .orderByTaskCreateTime()
                    .desc()
                    .list();
        } else {
            taskList = taskService
                    .createTaskQuery()
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .active()//仅查激活状态的任务，挂起的不查
                    .orderByTaskCreateTime()
                    .desc()
                    .list();
        }
        return String.valueOf(new BigDecimal(taskList.size()));
    }

    @Override
    public ProcessDefinition getProcessDefinitionByTaskId(String taskId) throws Exception {
        Task task = getTaskById(taskId);
        String processDefinitionId = task.getProcessDefinitionId();
        ProcessDefinition processDefinition = getProcessDefinitionById(processDefinitionId);
        return processDefinition;
    }

    @Override
    public ProcessInstance getProcessInstanceByTaskId(String taskId) throws Exception {
        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(getTaskById(taskId).getProcessInstanceId())
                .singleResult();
        if (processInstance == null) {
            //流程实例未找到!
            throw new SystemException("w115");
        }
        return processInstance;
    }

    @Override
    public Task getTaskByProcessInstanceId(String processInstanceId) throws Exception {
        return taskService.createTaskQuery()
                .processInstanceId(processInstanceId).singleResult();
    }

    @Override
    public Task getTaskById(String taskId) throws Exception {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    @Override
    public ProcessDefinition getProcessDefinitionById(String processDefinitionId) throws Exception {
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
    }

    @Override
    public ProcessInstance getProcessInstanceById(String processInstanceId) throws Exception {
        return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

    @Override
    public String getTaskFormKeyByTaskId(String taskId) throws Exception {
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        return taskFormData.getFormKey();
    }

    @Override
    public String getBusinessKeyByTaskId(String taskId) throws Exception {
        return (String) taskService.getVariable(taskId, "businessKey");
    }

    @Override
    public Map<String, Object> getTaskVariables(String taskId) throws Exception {
        return taskService.getVariables(taskId);
    }

    @Override
    public void completeTask(String taskId, String comment, Map<String, Object> varMap) throws Exception {
        ActivityImpl activity = getActivityImplByTaskId(taskId);
        String documentation = (String) activity.getProperties().get("documentation");
        String remark = "";
//        if (StringUtil.isBlack(comment)) {
//            if (StringUtils.isNotBlank(documentation)) {
//                remark += "" + documentation;
//                if (!documentation.contains("编辑")) {
//                    if (null != varMap) {
//                        if ("0".equals((String) varMap.get("isAgree"))) {//不同意
//                            remark += "审批驳回；";
//                        } else if ("1".equals((String) varMap.get("isAgree"))) {
//                            if ("1".equals((String) varMap.get("isReject"))) {
//                                remark += "重新提交审批；";
//                            }else {
//                                remark += "审批通过；";
//                            }
//                        }
//                    }
//                }
//            }
//            remark += " 未批注";
//        } else {
        if (!comment.contains("流程启动")) {
            if (StringUtils.isNotBlank(documentation)) {
                remark += "" + documentation;
                if (!documentation.contains("编辑")) {
                    if (null != varMap) {
                        if ("0".equals((String) varMap.get("isAgree"))) {//不同意
                            remark += "审批驳回；";
                        } else if ("1".equals((String) varMap.get("isAgree"))) {
                            if ("1".equals((String) varMap.get("isReject"))) {
                                remark += "重新提交审批；";
                            } else {
                                remark += "审批通过；";
                            }
                        }
                    }
                    if ("null".equals(comment)) {
                        remark += " 未批注";
                    } else {
                        remark += " " + comment;
                    }
                }
            } else {
                if (null != varMap) {
                    if ("0".equals((String) varMap.get("isAgree"))) {//不同意
                        remark += "审批驳回；";
                    } else if ("1".equals((String) varMap.get("isAgree"))) {
                        if ("1".equals((String) varMap.get("isReject"))) {
                            remark += "重新提交审批；";
                        } else {
                            remark += "审批通过；";
                        }

                    }
                }
                if ("".equals(comment.trim())) {

                } else {
                    remark += " " + comment;
                }
            }
        } else {
            remark += comment;
        }
        String str = "";
        if ((varMap != null) && (varMap.get("organName") != null)) {
            str = varMap.get("organName").toString();
        }
        Authentication.setAuthenticatedUserId(str + WebContextUtil.getSessionUser().getOpername());
        taskService.addComment(taskId, getTaskById(taskId).getProcessInstanceId(), remark);
        taskService.complete(taskId, varMap);
    }

    /**
     * TODO 根据任务ID获取活动节点.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                               2016年3月14日    	    杨滔    新建
     *                                                                                                                                                                                                                                                                               </pre>
     */
    public ActivityImpl getActivityImplByTaskId(String taskId) throws Exception {
        Task task = getTaskById(taskId);
        //1.获取当前活动节点方法改为下面代码，上面的方式在子流程中获取不到活动节点ID
        String excId = task.getExecutionId();
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
        String activityId = execution.getActivityId();
        //2.获取流程定义,活动节点(ActivityImpl存在内存中,所以不能用Query对象查询)
        ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
        //3.通过活动的节点ID获取节点对象
        ActivityImpl activity = pd.findActivity(activityId);
        return activity;
    }

    public ActivityImpl getActivityImplByProcessInstanceId(String processInstanceId) throws Exception {
        Task task = getTaskByProcessInstanceId(processInstanceId);
        //1.获取当前活动节点方法改为下面代码，上面的方式在子流程中获取不到活动节点ID
        String excId = task.getExecutionId();
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
        String activityId = execution.getActivityId();
        //2.获取流程定义,活动节点(ActivityImpl存在内存中,所以不能用Query对象查询)
        ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
        //3.通过活动的节点ID获取节点对象
        ActivityImpl activity = pd.findActivity(activityId);
        return activity;
    }

    @Override
    public List<String> getOutGoingName(String taskId) throws Exception {
        List<String> list = new ArrayList<String>();
        ActivityImpl act = getActivityImplByTaskId(taskId);
        List<PvmTransition> outGoings = act.getOutgoingTransitions();
        for (PvmTransition pvmTransition : outGoings) {
            String name = (String) pvmTransition.getProperty("name");
            if (StringUtils.isNotBlank(name)) {
                list.add(name);
            }
        }
        if (list.isEmpty()) {
            list.add("通过");
        }
        return list;
    }

    @Override
    public List<Comment> getTaskComments(String taskId) throws Exception {
        List<Comment> comments = new ArrayList<Comment>();
        //获取任务对象
        Task task = getTaskById(taskId);
        //获取流程实例对象
        String pid = task.getProcessInstanceId();
        //通过流程实例ID，获取所有历史任务
        List<HistoricTaskInstance> hti = historyService
                .createHistoricTaskInstanceQuery()
                .processInstanceId(pid)
                .orderByHistoricTaskInstanceEndTime()
                .desc().
                        list();
        List<Comment> commentsResever = new ArrayList<>();
        for (HistoricTaskInstance historicTaskInstance : hti) {
            List<Comment> list = taskService.getTaskComments(historicTaskInstance.getId());
            commentsResever.addAll(list);
        }
        //使批注升序排列
        for (int i = 0; i < commentsResever.size(); i++) {
            comments.add(commentsResever.get(Integer.valueOf(commentsResever.size() - 1 - i)));
        }

        /**
         *添加当前待审批信息批注
         */
        CommentEntity comment = new CommentEntity();
        String assignee = task.getAssignee();
        String opercode = "";
        if (assignee.indexOf(":") > 0) {
            opercode = assignee.substring(0, assignee.indexOf(":"));
        } else {
            opercode = assignee;
        }
        FdOper fdOper = new FdOper();
        fdOper.setOpercode(opercode);
        List<FdOper> fdOpers = fdOperService.selectByAttr(fdOper);
        fdOper = fdOpers.get(0);
        //查询角色
        List<String> roleIdList = webOperRoleMapper.selectOwnRoleByOperCode(fdOper.getOpercode());
        String roleName = "";
        for (String roleId : roleIdList) {
            WebRoleInfo webRoleInfo = webRoleInfoMapper.selectByPK(roleId);
            if (Objects.nonNull(webRoleInfo)) {
                roleName += webRoleInfo.getRoleName() + ",";
            }
        }
        String msg = "待审批";
        if (roleName.contains("录入") || roleName.contains("系统管理员")) {
            msg = "待提交";
        }
        WebOperInfo webOperInfo = new WebOperInfo();
        webOperInfo.setOperCode(fdOper.getOpercode());
        List<WebOperInfo> webOperInfoList = webOperInfoMapper.selectByAttr(webOperInfo);
        FdOrgan fdOrgan = fdOrganMapper.selectByPK(webOperInfoList.get(0).getOrganCode());
        String str = "";
        if ("11005293".equals(webOperInfoList.get(0).getOrganCode())) {
            str = "总行-" + fdOper.getOpername();
        } else {
            str = fdOrgan.getOrganname() + "-" + fdOper.getOpername();
        }
        comment.setUserId(str);
        comment.setFullMessage(msg);
        comments.add(comment);
        return comments;
    }

    public List<Comment> getTaskCommentsByProcessInstanceId(String processInstanceId) throws Exception {
        List<Comment> comments = new ArrayList<Comment>();
        //获取任务对象
        Task task = getTaskByProcessInstanceId(processInstanceId);
        //获取流程实例对象
        String pid = task.getProcessInstanceId();
        //通过流程实例ID，获取所有历史任务
        List<HistoricTaskInstance> hti = historyService
                .createHistoricTaskInstanceQuery()
                .processInstanceId(pid)
                .orderByHistoricTaskInstanceEndTime()
                .desc().
                        list();
        for (HistoricTaskInstance historicTaskInstance : hti) {
            List<Comment> list = taskService.getTaskComments(historicTaskInstance.getId());
            comments.addAll(list);
        }
        /**
         *添加当前待审批信息批注
         */
        CommentEntity comment = new CommentEntity();
        String assignee = task.getAssignee();
        String opercode = "";
        if (assignee.indexOf(":") > 0) {
            opercode = assignee.substring(0, assignee.indexOf(":"));
        } else {
            opercode = assignee;
        }
        FdOper fdOper = new FdOper();
        fdOper.setOpercode(opercode);
        List<FdOper> fdOpers = fdOperService.selectByAttr(fdOper);
        fdOper = fdOpers.get(0);
        comment.setUserId(fdOper.getOpername());
        comment.setFullMessage("待审批");
        comments.add(comment);
        return comments;
    }

    @Override
    public long getRunningProcessInstaceCount(String processDefinitionKey) throws Exception {
        return runtimeService
                .createProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .active()
                .count();
    }

    @Override
    public List<Map<String, Object>> getRunningProcessInstacePage(String processDefinitionKey, Pager pager) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        ProcessInstanceQuery query = runtimeService
                .createProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .active()
                .orderByProcessInstanceId()
                .desc();
        List<ProcessInstance> list = query.listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        for (ProcessInstance processInstance : list) {
            map = new HashMap<String, Object>();
            map.put("pid", processInstance.getId());
            map.put("name", processInstance.getName());
            map.put("deploymentId", processInstance.getDeploymentId());
            map.put("status", processInstance.isSuspended());
            rows.add(map);
        }
        return rows;
    }

    @Override
    public String getActivityNameByTaskId(String taskId) throws Exception {
        ActivityImpl activityImpl = getActivityImplByTaskId(taskId);
        String activityName = (String) activityImpl.getProperty("name");
        return activityName;
    }

    @Override
    public List<Map<String, Object>> getSubProcessInstacePage(String processDefinitionKey, Pager pager) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        HistoricProcessInstanceQuery query = historyService
                .createHistoricProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .startedBy(WebContextUtil.getSessionUser().getOpercode())
                .orderByProcessInstanceEndTime()
                .desc();
        List<HistoricProcessInstance> list = query.listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        for (HistoricProcessInstance historicProcessInstance : list) {
            map = new HashMap<String, Object>();
            map.put("processDefinitionId", historicProcessInstance.getProcessDefinitionId());
            map.put("processInstanceId", historicProcessInstance.getId());
            map.put("pdid", historicProcessInstance.getProcessDefinitionId());
            map.put("startTime", historicProcessInstance.getStartTime());
            map.put("endTime", historicProcessInstance.getEndTime());
            map.put("duration", historicProcessInstance.getDurationInMillis());
            map.put("vars", historicProcessInstance.getProcessVariables());
            map.put("taskId", getTaskByPid(historicProcessInstance.getId()) != null ? getTaskByPid(historicProcessInstance.getId()).getId() : "");
            rows.add(map);
        }
        return rows;
    }

    @Override
    public List<Map<String, Object>> getFinishedProcessInstacePage(String processDefinitionKey, String assign, Pager pager) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        HistoricProcessInstanceQuery query = historyService
                .createHistoricProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .involvedUser(assign)
                .orderByProcessInstanceEndTime()
                .desc();
        List<HistoricProcessInstance> list = query.listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        for (HistoricProcessInstance historicProcessInstance : list) {
            map = new HashMap<String, Object>();
            map.put("processDefinitionId", historicProcessInstance.getProcessDefinitionId());
            map.put("processInstanceId", historicProcessInstance.getId());
            map.put("pdid", historicProcessInstance.getProcessDefinitionId());
            map.put("startTime", historicProcessInstance.getStartTime());
            map.put("endTime", historicProcessInstance.getEndTime());
            map.put("duration", historicProcessInstance.getDurationInMillis());
            map.put("vars", historicProcessInstance.getProcessVariables());
            map.put("taskId", getTaskByPid(historicProcessInstance.getId()) != null ? getTaskByPid(historicProcessInstance.getId()).getId() : "");
            rows.add(map);
        }
        return rows;
    }

    /**
     * TODO 通过实例ID查任务.
     *
     * @param processInstanceId
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                           修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                           2016年3月15日    	    杨滔    新建
     *                                                                                                                                                                                                                                           </pre>
     */
    @Override
    public Task getTaskByPid(String processInstanceId) throws Exception {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    }

    @Override
    public List<Map<String, Object>> getPersonalTaskByName(String taskName, String processDefinitionKey, Pager pager) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        List<Task> taskList;
        //如果传入流程定义key，就查某个流程的任务，否则查所有流程的任务
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            taskList = taskService
                    .createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .taskName(taskName)
                    .active()//仅查激活状态的任务，挂起的不查
                    .orderByTaskCreateTime()
                    .desc()
                    .listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        } else {
            taskList = taskService
                    .createTaskQuery()
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .taskName(taskName)
                    .active()//仅查激活状态的任务，挂起的不查
                    .orderByTaskCreateTime()
                    .desc()
                    .listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        }
        for (Task task : taskList) {
            map = new HashMap<String, Object>();
            map.put("taskId", task.getId());
            map.put("name", task.getName());
            map.put("createTime", task.getCreateTime());
            map.put("assignee", task.getAssignee());
            map.put("description", task.getDescription());
            map.put("processInstanceId", task.getProcessInstanceId());
            rows.add(map);
        }
        return rows;
    }

    @Override
    public long getPersonalTaskCountByName(String taskName, String processDefinitionKey, String assignee) throws Exception {
        //如果传入流程定义key，就查某个流程的任务，否则查所有流程的任务
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            return taskService.createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskAssignee(assignee)
                    .taskName(taskName)
                    .active()//仅查激活状态的任务，挂起的不查
                    .count();
        } else {
            return taskService
                    .createTaskQuery()
                    .taskAssignee(assignee)
                    .taskName(taskName)
                    .active()//仅查激活状态的任务，挂起的不查
                    .count();
        }
    }

    /**
     * TODO 通过在经办人字段后拼接不同的字符串来指定不同种类的业务类型.
     * 查询符合条件的列表信息
     *
     * @param processDefinitionKey
     * @param assignee
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                               2016年4月5日    	  杜旭    新建
     *                                                                                                                                                                                                                                                                               </pre>
     */
    @Override
    public List<Map<String, Object>> getPersonalTaskPageByType(String processDefinitionKey, String assignee, Pager pager) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        List<Task> taskList = new ArrayList<Task>();
        //如果传入流程定义key，就查某个流程的任务，否则查所有流程的任务
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            taskList = taskService
                    .createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskAssignee(assignee)
                    .active()//仅查激活状态的任务，挂起的不查
                    .orderByTaskCreateTime()
                    .desc()
                    .listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        } else {
            taskList = taskService
                    .createTaskQuery()
                    .taskAssignee(assignee)
                    .active()//仅查激活状态的任务，挂起的不查
                    .orderByTaskCreateTime()
                    .desc()
                    .listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        }
        for (Task task : taskList) {
            map = new HashMap<String, Object>();
            map.put("taskId", task.getId());
            map.put("name", task.getName());
            map.put("createTime", task.getCreateTime());
            map.put("assignee", task.getAssignee());
            map.put("description", task.getDescription());
            map.put("processInstanceId", task.getProcessInstanceId());
            rows.add(map);
        }
        return rows;
    }

    /**
     * TODO 通过在经办人字段后拼接不同的字符串来指定不同种类的业务类型.
     * 查询符合条件的记录数
     *
     * @param processDefinitionKey
     * @param assignee
     * @param
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                               2016年4月5日    	  杜旭    新建
     *                                                                                                                                                                                                                                                                               </pre>
     */
    @Override
    public long getPersonalTaskCountByType(String processDefinitionKey, String assignee) throws Exception {
        //如果传入流程定义key，就查某个流程的任务，否则查所有流程的任务
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            return taskService.createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskAssignee(assignee)
                    .active()//仅查激活状态的任务，挂起的不查
                    .count();
        } else {
            return taskService
                    .createTaskQuery()
                    .taskAssignee(assignee)
                    .active()//仅查激活状态的任务，挂起的不查
                    .count();
        }
    }

    /**
     * TODO 查询当前人的组任务.
     *
     *
     * <pre>
     * 修改日期        修改人    修改原因
     * 2016年4月6日    	  杜旭    新建
     * </pre>
     */
    @Override
    public List<Map<String, Object>> findPersonalGroupTask(String processDefinitionKey, String candidateUser, Pager pager) {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        List<Task> taskList = new ArrayList<Task>();
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            taskList = taskService//与正在执行的任务管理相关的Service
                    .createTaskQuery()//创建任务查询对象
                    .processDefinitionKey(processDefinitionKey)
                    /**查询条件（where部分）*/
                    .taskCandidateUser(candidateUser)//组任务的办理人查询
                    /**排序*/
                    .orderByTaskCreateTime().asc()//使用创建时间的升序排列
                    /**返回结果集*/
                    .listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        } else {
            taskList = taskService//与正在执行的任务管理相关的Service
                    .createTaskQuery()//创建任务查询对象
                    /**查询条件（where部分）*/
                    .taskCandidateUser(candidateUser)//组任务的办理人查询
                    /**排序*/
                    .orderByTaskCreateTime().asc()//使用创建时间的升序排列
                    /**返回结果集*/
                    .listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        }
        for (Task task : taskList) {
            map = new HashMap<String, Object>();
            map.put("taskId", task.getId());
            map.put("name", task.getName());
            map.put("createTime", task.getCreateTime());
            map.put("assignee", task.getAssignee());
            map.put("description", task.getDescription());
            map.put("processInstanceId", task.getProcessInstanceId());
            rows.add(map);
        }
        return rows;
    }

    /**
     * TODO 查询当前人的组任务
     *
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                               2016年4月6日    	  杜旭    新建
     *                                                                                                                                                                                                                                                                               </pre>
     */
    @Override
    public long findPersonalGroupTaskCount(String processDefinitionKey, String candidateUser) throws Exception {
        //如果传入流程定义key，就查某个流程的任务，否则查所有流程的任务
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            return taskService.createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskCandidateUser(candidateUser)//组任务的办理人查询
                    .active()//仅查激活状态的任务，挂起的不查
                    .count();
        } else {
            return taskService
                    .createTaskQuery()
                    .taskCandidateUser(candidateUser)//组任务的办理人查询
                    .active()//仅查激活状态的任务，挂起的不查
                    .count();
        }
    }

    /**
     * TODO 拾取任务，将组任务分给个人任务，指定任务的办理人字段
     *
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                               2016年4月6日    	  杜旭    新建
     *                                                                                                                                                                                                                                                                               </pre>
     */
    public void claim(String taskId, String assignee) throws Exception {
        //将组任务分配给个人任务
        taskService.claim(taskId, assignee);
    }

    /**
     * TODO 通过指定的流程定key获取最新的流程定义.
     *
     * @param processDefinitionKey
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                               2016年4月6日    	  杜旭    新建
     *                                                                                                                                                                                                                                                                               </pre>
     */
    @Override
    public List<ProcessDefinition> getProcessDefinitionList(String processDefinitionKey) throws Exception {
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
        return processDefinitionList;
    }

    @Override
    public List<Comment> getInstanceComments(String processInstanceId) throws Exception {
        List<Comment> comments = new ArrayList<Comment>();
        //通过流程实例ID，获取所有历史任务
        List<HistoricTaskInstance> hti = historyService
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceEndTime()
                .desc().
                        list();

        List<Comment> commentsResever = new ArrayList<>();
        for (HistoricTaskInstance historicTaskInstance : hti) {
            List<Comment> list = taskService.getTaskComments(historicTaskInstance.getId());
            commentsResever.addAll(list);
        }
        //使批注升序排列
        for (int i = 0; i < commentsResever.size(); i++) {
            comments.add(commentsResever.get(Integer.valueOf(commentsResever.size() - 1 - i)));
        }
        /**
         *添加当前待审批信息批注
         */
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        CommentEntity comment = new CommentEntity();
        if (list.size() > 0) {
            if (list.size() > 1) {
                String userId = "";
                for (int i = 0; i < list.size(); i++) {
                    Task t = list.get(i);
                    String assignee = t.getAssignee();
                    String opercode = "";
                    if (assignee.indexOf(":") > 0) {
                        opercode = assignee.substring(0, assignee.indexOf(":"));
                    } else {
                        opercode = assignee;
                    }
                    FdOper fdOper = new FdOper();
                    fdOper.setOpercode(opercode);
                    List<FdOper> fdOpers = fdOperService.selectByAttr(fdOper);
                    fdOper = fdOpers.get(0);
                    userId = userId + "," + fdOper.getOpername();
                }
                comment.setUserId(userId.substring(1));
                comment.setFullMessage("待签收");
            } else {
                String assignee = list.get(0).getAssignee();
                String opercode = "";
                if (assignee.indexOf(":") > 0) {
                    opercode = assignee.substring(0, assignee.indexOf(":"));
                } else {
                    opercode = assignee;
                }
                FdOper fdOper = new FdOper();
                fdOper.setOpercode(opercode);
                List<FdOper> fdOpers = fdOperService.selectByAttr(fdOper);
                fdOper = fdOpers.get(0);
                //查询角色
                List<String> roleIdList = webOperRoleMapper.selectOwnRoleByOperCode(fdOper.getOpercode());
                String roleName = "";
                for (String roleId : roleIdList) {
                    WebRoleInfo webRoleInfo = webRoleInfoMapper.selectByPK(roleId);
                    if (Objects.nonNull(webRoleInfo)) {
                        roleName += webRoleInfo.getRoleName() + ",";
                    }
                }
                String msg = "待审批";
                if (roleName.contains("录入") || roleName.contains("系统管理员")) {
                    msg = "待提交";
                }
                WebOperInfo webOperInfo = new WebOperInfo();
                webOperInfo.setOperCode(fdOper.getOpercode());
                List<WebOperInfo> webOperInfoList = webOperInfoMapper.selectByAttr(webOperInfo);
                FdOrgan fdOrgan = fdOrganMapper.selectByPK(webOperInfoList.get(0).getOrganCode());
                String str = "";
                if ("11005293".equals(webOperInfoList.get(0).getOrganCode())) {
                    str = "总行-" + fdOper.getOpername();
                } else {
                    str = fdOrgan.getOrganname() + "-" + fdOper.getOpername();
                }
                comment.setUserId(str);
                comment.setFullMessage(msg);
            }
            comments.add(comment);
        } else {
            // 修改最后的审批信息
            Comment com = comments.get(comments.size() - 1);
            comments.remove(comments.size() - 1);

            CommentEntity commentLast = new CommentEntity();
            commentLast.setUserId(com.getUserId());
            commentLast.setFullMessage("审批通过已上报" + com.getFullMessage().substring(5));
            commentLast.setType(com.getType());
            commentLast.setTime(com.getTime());

            comments.add(commentLast);
        }
        return comments;
    }

    /**
     * TODO 获取当前人已完成任务的记录数.
     *
     * @param
     * @param
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年4月16日    	  杜旭    新建
     * </pre>
     */
    @Override
    public Long getFinishedProcessInstaceCountByType(String processDefinitionKey, String assign) {
        return historyService
                .createHistoricProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .involvedUser(assign)
                .count();
    }

    /**
     * TODO 查看当前人为发起人的记录数.
     *
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                               2016年4月16日    	  杜旭    新建
     *                                                                                                                                                                                                                                                                               </pre>
     */
    @Override
    public Long getStartedByCountByType(String processDefinitionKey, String assign) {
        return historyService
                .createHistoricProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .startedBy(assign)
                .count();
    }

    /**
     * TODO 与任务id绑定流程变量.
     *
     * @param taskId
     * @param taskVariables
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                               2016年5月10日    	  杜旭    新建
     *                                                                                                                                                                                                                                                                               </pre>
     */
    @Override
    public void setTaskVariables(String taskId, Map<String, Object> taskVariables) throws Exception {
        taskService.setVariablesLocal(taskId, taskVariables);
    }

    /**
     * TODO 与任务id绑定流程变量.
     *
     * @param taskId
     * @param taskVariables
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                               2016年5月10日    	  杜旭    新建
     *                                                                                                                                                                                                                                                                               </pre>
     */
    @Override
    public void setVariablesByPID(String taskId, Map<String, Object> taskVariables) throws Exception {
        taskService.setVariables(taskId, taskVariables);
    }

    /**
     * TODO 通过任务id查询已完成任务.
     *
     * @param taskId
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年5月10日    	  杜旭    新建
     * </pre>
     */
    @Override
    public List<HistoricVariableInstance> getHiVariablesByTaskId(String taskId) {
        return historyService.createHistoricVariableInstanceQuery().taskId(taskId).list();
    }

    /**
     * TODO 历史流程实例查询.
     *
     * @param
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年5月10日    	  杜旭    新建
     * </pre>
     */
    @Override
    public HistoricProcessInstance getProcessInstance(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

    @Override
    public List<Map<String, Object>> getPersonalTask(String processDefinitionKey) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        List<Task> taskList = new ArrayList<Task>();
        //如果传入流程定义key，就查某个流程的任务，否则查所有流程的任务
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            taskList = taskService
                    .createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .active()//仅查激活状态的任务，挂起的不查
                    .orderByTaskCreateTime()
                    .desc().list();
        } else {
            taskList = taskService
                    .createTaskQuery()
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .active()//仅查激活状态的任务，挂起的不查
                    .orderByTaskCreateTime()
                    .desc().list();
        }
        for (Task task : taskList) {
            map = new HashMap<String, Object>();
            map.put("taskId", task.getId());
            map.put("name", task.getName());
            map.put("createTime", task.getCreateTime());
            map.put("assignee", task.getAssignee());
            map.put("description", task.getDescription());
            map.put("processInstanceId", task.getProcessInstanceId());
            rows.add(map);
        }
        return rows;
    }

    @Override
    public List<Map<String, Object>> getSubProcessInstace(String processDefinitionKey) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        List<HistoricProcessInstance> list = historyService
                .createHistoricProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .startedBy(WebContextUtil.getSessionUser().getOpercode())
                .orderByProcessInstanceEndTime()
                .desc().list();
        for (HistoricProcessInstance historicProcessInstance : list) {
            map = new HashMap<String, Object>();
            map.put("processDefinitionId", historicProcessInstance.getProcessDefinitionId());
            map.put("processInstanceId", historicProcessInstance.getId());
            map.put("pdid", historicProcessInstance.getProcessDefinitionId());
            map.put("startTime", historicProcessInstance.getStartTime());
            map.put("endTime", historicProcessInstance.getEndTime());
            map.put("duration", historicProcessInstance.getDurationInMillis());
            map.put("vars", historicProcessInstance.getProcessVariables());
            map.put("taskId", getTaskByPid(historicProcessInstance.getId()) != null ? getTaskByPid(historicProcessInstance.getId()).getId() : "");
            rows.add(map);
        }
        return rows;
    }

    @Override
    public List<Map<String, Object>> getFinishedProcessInstace(String processDefinitionKey) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        List<HistoricProcessInstance> list = historyService
                .createHistoricProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .involvedUser(WebContextUtil.getSessionUser().getOpercode())
                .orderByProcessInstanceEndTime()
                .desc()
                .list();
        for (HistoricProcessInstance historicProcessInstance : list) {
            map = new HashMap<String, Object>();
            map.put("processDefinitionId", historicProcessInstance.getProcessDefinitionId());
            map.put("processInstanceId", historicProcessInstance.getId());
            map.put("pdid", historicProcessInstance.getProcessDefinitionId());
            map.put("startTime", historicProcessInstance.getStartTime());
            map.put("endTime", historicProcessInstance.getEndTime());
            map.put("duration", historicProcessInstance.getDurationInMillis());
            map.put("vars", historicProcessInstance.getProcessVariables());
            map.put("taskId", getTaskByPid(historicProcessInstance.getId()) != null ? getTaskByPid(historicProcessInstance.getId()).getId() : "");
            rows.add(map);
        }
        return rows;
    }

    @Override
    public Map<String, Object> getTaskIdByProcDefId(String procDefId) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(procDefId);
        List<ActivityImpl> activitiesList = processDefinition.getActivities();
        for (int i = 0; i < activitiesList.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            ActivityImpl activityImpl = activitiesList.get(i);
            //去掉网关和结束活动节点
            if (activityImpl.getId().startsWith("excl") || activityImpl.getId().startsWith("end")) {
                continue;
            } else {
                map.put("key", activityImpl.getProperty("name"));
                map.put("value", activityImpl.getId());
                list.add(map);
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("list", list);
        return resultMap;
    }

    @Override
    public Map<String, Object> getTaskRole() throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        WebRoleInfo webRoleInfo = new WebRoleInfo();
        List<WebRoleInfo> listTemp = webRoleInfoService.selectByAttr(webRoleInfo);
        for (WebRoleInfo info : listTemp) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("key", info.getRoleName());
            map.put("value", info.getRoleCode());
            list.add(map);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("list", list);
        return resultMap;
    }

    /**
     * TODO 通过流程实例id和任务名称模糊查询历史任务表.
     *
     * @param processInstanceId
     * @param taskName
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年7月14日    	    杜旭    新建
     * </pre>
     */
    @Override
    public List<HistoricTaskInstance> getHistoricTaskInstance(String processInstanceId, String taskName) {
        List<HistoricTaskInstance> list = new ArrayList<HistoricTaskInstance>();
        List<HistoricTaskInstance> hti = historyService
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceEndTime()
                .asc()
                .list();
        for (HistoricTaskInstance historicTaskInstance : hti) {
            if (historicTaskInstance.getName().contains(taskName)) {
                list.add(historicTaskInstance);
            }
        }
        return list;
    }

    /**
     * TODO 通过任务id查询批注信息表，获取任务id对应的批注信息.
     *
     * @param
     * @param
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年7月14日    	    杜旭    新建
     * </pre>
     */
    @Override
    public List<Comment> getHistoricTaskComments(String taskId) throws Exception {
        List<Comment> comments = taskService.getTaskComments(taskId);
        //遍历所有历史任务，通过taskId获取对象批注
        return comments;
    }

    @Override
    public void addTaskComment(String taskId, String type, String comment, Map<String, Object> varMap) throws Exception {
        ActivityImpl activity = getActivityImplByTaskId(taskId);
        String documentation = (String) activity.getProperties().get("documentation");
        String remark = "";
        if (StringUtil.isBlack(comment)) {
            if (StringUtils.isNotBlank(documentation)) {
                remark += "" + documentation;
                if (!documentation.contains("编辑")) {
                    if (null != varMap) {
                        if ("0".equals((String) varMap.get("isAgree"))) {//不同意
                            remark += "审批驳回；";
                        } else if ("1".equals((String) varMap.get("isAgree"))) {
                            remark += "审批通过；";
                        } else {
                            remark += "重新提交审批；";
                        }
                    }
                }
            }
            remark += " 未批注";
        } else {
            if (!comment.contains("流程启动")) {
                if (StringUtils.isNotBlank(documentation)) {
                    remark += "" + documentation;
                    if (!documentation.contains("编辑")) {
                        if (null != varMap) {
                            if ("0".equals((String) varMap.get("isAgree"))) {//不同意
                                remark += "审批驳回；";
                            } else if ("1".equals((String) varMap.get("isAgree"))) {
                                remark += "审批通过；";
                            } else {
                                remark += "重新提交审批；";
                            }
                        }
                        if ("null".equals(comment)) {
                            remark += " 未批注";
                        } else {
                            remark += " " + comment;
                        }
                    }
                } else {
                    if (null != varMap) {
                        if ("0".equals((String) varMap.get("isAgree"))) {//不同意
                            remark += "审批驳回；";
                        } else if ("1".equals((String) varMap.get("isAgree"))) {
                            remark += "审批通过；";
                        } else {
                            remark += "重新提交审批；";
                        }
                    }
                    remark += " " + comment;
                }
            } else {
                remark += comment;
            }
        }
        Authentication.setAuthenticatedUserId(WebContextUtil.getSessionUser().getOpername());
        taskService.addComment(taskId, getTaskById(taskId).getProcessInstanceId(), type, remark);
    }

    @Override
    public List<Comment> getTaskComments(String taskId, String type) throws Exception {
        List<Comment> comments = new ArrayList<Comment>();
        //获取任务对象
        Task task = getTaskById(taskId);
        //获取流程实例对象
        String pid = task.getProcessInstanceId();
        //通过流程实例ID，获取所有历史任务
        List<HistoricTaskInstance> hti = historyService
                .createHistoricTaskInstanceQuery()
                .processInstanceId(pid)
                .orderByHistoricTaskInstanceEndTime()
                .desc().
                        list();
        for (HistoricTaskInstance historicTaskInstance : hti) {
            List<Comment> list = taskService.getTaskComments(historicTaskInstance.getId(), type);
            comments.addAll(list);
        }
        /**
         *添加当前待审批信息批注
         */
        if ("comment".equals(type)) {
            CommentEntity comment = new CommentEntity();
            String assignee = task.getAssignee();
            String opercode = "";
            if (assignee.indexOf(":") > 0) {
                opercode = assignee.substring(0, assignee.indexOf(":"));
            } else {
                opercode = assignee;
            }
            FdOper fdOper = new FdOper();
            fdOper.setOpercode(opercode);
            List<FdOper> fdOpers = fdOperService.selectByAttr(fdOper);
            fdOper = fdOpers.get(0);
            comment.setUserId(fdOper.getOpername());
            comment.setFullMessage("待审批");
            comments.add(comment);
        }
        return comments;
    }

    @Override
    public List<Comment> getInstanceComments(String processInstanceId, String type) throws Exception {
        List<Comment> comments = new ArrayList<Comment>();
        //通过流程实例ID，获取所有历史任务
        List<HistoricTaskInstance> hti = historyService
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceEndTime()
                .desc().
                        list();
        for (HistoricTaskInstance historicTaskInstance : hti) {
            List<Comment> list = taskService.getTaskComments(historicTaskInstance.getId(), type);
            comments.addAll(list);
        }
        /**
         *添加当前待审批信息批注
         */
        if ("comment".equals(type)) {
            List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
            CommentEntity comment = new CommentEntity();
            if (list.size() > 0) {
                if (list.size() > 1) {
                    String userId = "";
                    for (int i = 0; i < list.size(); i++) {
                        Task t = list.get(i);
                        String assignee = t.getAssignee();
                        String opercode = "";
                        if (assignee.indexOf(":") > 0) {
                            opercode = assignee.substring(0, assignee.indexOf(":"));
                        } else {
                            opercode = assignee;
                        }
                        FdOper fdOper = new FdOper();
                        fdOper.setOpercode(opercode);
                        List<FdOper> fdOpers = fdOperService.selectByAttr(fdOper);
                        fdOper = fdOpers.get(0);
                        userId = userId + "," + fdOper.getOpername();
                    }
                    comment.setUserId(userId.substring(1));
                    comment.setFullMessage("待签收");
                } else {
                    String assignee = list.get(0).getAssignee();
                    String opercode = "";
                    if (assignee.indexOf(":") > 0) {
                        opercode = assignee.substring(0, assignee.indexOf(":"));
                    } else {
                        opercode = assignee;
                    }
                    FdOper fdOper = new FdOper();
                    fdOper.setOpercode(opercode);
                    List<FdOper> fdOpers = fdOperService.selectByAttr(fdOper);
                    fdOper = fdOpers.get(0);
                    comment.setUserId(fdOper.getOpername());
                    comment.setFullMessage("待审批");
                }
                comments.add(comment);
            }
        }
        return comments;
    }

    @Override
    public boolean getLastUserType(ActivityImpl activityImpl) throws Exception {
        boolean lastUserType = false;
        //获取当前活动完成之后节点的名称
        List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
        if (pvmList != null && pvmList.size() > 0) {
            for (PvmTransition pvm : pvmList) {
                PvmActivity act = pvm.getDestination();
                //如果是网关的话，通过网关获取下一个节点的名称
                if ("Exclusive Gateway".equals(act.getProperty("name"))) {
                    List<PvmTransition> actList = act.getOutgoingTransitions();
                    if (actList != null && actList.size() > 0) {
                        for (PvmTransition gwt : actList) {
                            PvmActivity gw = gwt.getDestination();
                            if ("End".equals(gw.getProperty("name"))) {
                                lastUserType = true;
                            }
                        }
                    }
                }
            }
        }
        return lastUserType;
    }

    @Override
    public List<TaskInfo> getProcessListByOrganCode(String organCode) {
        return null;
    }


    /**
     * 获取下一个用户任务用户组信息
     *
     * @param taskId 任务Id信息
     * @return 下一个用户任务用户组信息
     * @throws Exception
     */
    @Override
    public String getNextTaskAssigneeKey(String taskId, String elString) throws Exception {
        ProcessDefinitionEntity processDefinitionEntity = null;
        String id = null;
        TaskDefinition task = null;
        String processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
        String definitionId = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getProcessDefinitionId();

        processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(definitionId);

        ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

//当前流程节点Id信息
        String activitiId = execution.getActivityId();

//获取流程所有节点信息
        List<ActivityImpl> activitiList = processDefinitionEntity.getActivities();
//遍历所有节点信息
        for (ActivityImpl activityImpl : activitiList) {
            id = activityImpl.getId();

            // 找到当前节点信息
            if (activitiId.equals(id)) {

//获取下一个节点信息
                task = nextTaskDefinition(activityImpl, activityImpl.getId(), elString, processInstanceId);

                break;
            }
        }
        if (task == null) {
            return null;
        }
        return task.getKey();
    }

    /**
     * 下一个任务节点信息,
     * <p>
     * 如果下一个节点为用户任务则直接返回,
     * <p>
     * 如果下一个节点为排他网关, 获取排他网关Id信息, 根据排他网关Id信息和execution获取流程实例排他网关Id为key的变量值,
     * 根据变量值分别执行排他网关后线路中的el表达式, 并找到el表达式通过的线路后的用户任务信息
     *
     * @param ActivityImpl activityImpl     流程节点信息
     * @param String       activityId     当前流程节点Id信息
     * @param String       elString       排他网关顺序流线段判断条件, 例如排他网关顺序留线段判断条件为${money>1000}, 若满足流程启动时设置variables中的money>1000, 则流程流向该顺序流信息
     * @param String       processInstanceId      流程实例Id信息
     * @return
     */
    private TaskDefinition nextTaskDefinition(ActivityImpl activityImpl, String activityId, String elString, String processInstanceId) {

        PvmActivity ac = null;

        Object s = null;

//如果遍历节点为用户任务并且节点不是当前节点信息
        if ("userTask".equals(activityImpl.getProperty("type")) && !activityId.equals(activityImpl.getId())) {
//获取该节点下一个节点信息
            TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activityImpl.getActivityBehavior()).getTaskDefinition();
            return taskDefinition;
        } else {
//获取节点所有流向线路信息
            List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
            List<PvmTransition> outTransitionsTemp = null;
            for (PvmTransition tr : outTransitions) {
                ac = tr.getDestination(); //获取线路的终点节点
                //如果流向线路为排他网关
                if ("exclusiveGateway".equals(ac.getProperty("type"))) {
                    outTransitionsTemp = ac.getOutgoingTransitions();

//如果网关路线判断条件为空信息
                    if (StringUtils.isEmpty(elString)) {
                        //获取流程启动时设置的网关判断条件信息
                        elString = getGatewayCondition(ac.getId(), processInstanceId);
                    }

//如果排他网关只有一条线路信息
                    if (outTransitionsTemp.size() == 1) {
                        return nextTaskDefinition((ActivityImpl) outTransitionsTemp.get(0).getDestination(), activityId, elString, processInstanceId);
                    } else if (outTransitionsTemp.size() > 1) {  //如果排他网关有多条线路信息
                        for (PvmTransition tr1 : outTransitionsTemp) {
                            s = tr1.getProperty("conditionText");  //获取排他网关线路判断条件信息
//判断el表达式是否成立
                            if (isCondition(ac.getId(), StringUtils.trim(s.toString()), elString)) {
                                return nextTaskDefinition((ActivityImpl) tr1.getDestination(), activityId, elString, processInstanceId);
                            }
                        }
                    }
                } else if ("userTask".equals(ac.getProperty("type"))) {
                    return ((UserTaskActivityBehavior) ((ActivityImpl) ac).getActivityBehavior()).getTaskDefinition();
                } else {
                }
            }
            return null;
        }
    }

    /**
     * 查询流程启动时设置排他网关判断条件信息
     *
     * @param String gatewayId  排他网关Id信息, 流程启动时设置网关路线判断条件key为网关Id信息
     * @param String processInstanceId  流程实例Id信息
     * @return
     */
    public String getGatewayCondition(String gatewayId, String processInstanceId) {
        Execution execution = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).singleResult();
        return runtimeService.getVariable(execution.getId(), gatewayId).toString();
    }

    /**
     * 根据key和value判断el表达式是否通过信息
     *
     * @param String key    el表达式key信息
     * @param String el     el表达式信息
     * @param String value  el表达式传入值信息
     * @return
     */
    public boolean isCondition(String key, String el, String value) {
        if (el.equals(value)) {
            return true;
        }
        return false;
//        ExpressionFactory factory = new ExpressionFactoryImpl();
//        SimpleContext context = new SimpleContext();
//        context.setVariable(key, factory.createValueExpression(value, String.class));
//        ValueExpression e = factory.createValueExpression(context, el, boolean.class);
//        return (Boolean) e.getValue(context);
    }


}
