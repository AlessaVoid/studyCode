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
            //w112-��������ʧ��!
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
        // ��ͨɾ���������ǰ������������ִ�е����̣������쳣
        repositoryService.deleteDeployment(deploymentId);
        // ����ɾ��,��ɾ���͵�ǰ������ص�������Ϣ��������ʷ
        //repositoryService.deleteDeployment(deploymentId, true);
    }

    @Override
    public InputStream getResourceAsStream(String processDefinitionId, String resourceType) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        if (processDefinition == null) {
            //���̶���[{0}]������!
            throw new SystemException("w114", processDefinitionId);
        }
        String resourceName = "";
        if ("image".equals(resourceType)) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if ("xml".equals(resourceType)) {
            resourceName = processDefinition.getResourceName();
        } else {
            //��Դ���Ͳ���ȷ!
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
        //����������̶���key���Ͳ�ĳ�����̵����񣬷�����������̵�����
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
        //����������̶���key���Ͳ�ĳ�����̵����񣬷�����������̵�����
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            taskList = taskService
                    .createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .active()//���鼤��״̬�����񣬹���Ĳ���
                    .orderByTaskCreateTime()
                    .desc()
                    .listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        } else {
            taskList = taskService
                    .createTaskQuery()
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .active()//���鼤��״̬�����񣬹���Ĳ���
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
        //����������̶���key���Ͳ�ĳ�����̵����񣬷�����������̵�����
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            taskList = taskService
                    .createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .active()//���鼤��״̬�����񣬹���Ĳ���
                    .orderByTaskCreateTime()
                    .desc()
                    .list();
        } else {
            taskList = taskService
                    .createTaskQuery()
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .active()//���鼤��״̬�����񣬹���Ĳ���
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
            //����ʵ��δ�ҵ�!
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
//                if (!documentation.contains("�༭")) {
//                    if (null != varMap) {
//                        if ("0".equals((String) varMap.get("isAgree"))) {//��ͬ��
//                            remark += "�������أ�";
//                        } else if ("1".equals((String) varMap.get("isAgree"))) {
//                            if ("1".equals((String) varMap.get("isReject"))) {
//                                remark += "�����ύ������";
//                            }else {
//                                remark += "����ͨ����";
//                            }
//                        }
//                    }
//                }
//            }
//            remark += " δ��ע";
//        } else {
        if (!comment.contains("��������")) {
            if (StringUtils.isNotBlank(documentation)) {
                remark += "" + documentation;
                if (!documentation.contains("�༭")) {
                    if (null != varMap) {
                        if ("0".equals((String) varMap.get("isAgree"))) {//��ͬ��
                            remark += "�������أ�";
                        } else if ("1".equals((String) varMap.get("isAgree"))) {
                            if ("1".equals((String) varMap.get("isReject"))) {
                                remark += "�����ύ������";
                            } else {
                                remark += "����ͨ����";
                            }
                        }
                    }
                    if ("null".equals(comment)) {
                        remark += " δ��ע";
                    } else {
                        remark += " " + comment;
                    }
                }
            } else {
                if (null != varMap) {
                    if ("0".equals((String) varMap.get("isAgree"))) {//��ͬ��
                        remark += "�������أ�";
                    } else if ("1".equals((String) varMap.get("isAgree"))) {
                        if ("1".equals((String) varMap.get("isReject"))) {
                            remark += "�����ύ������";
                        } else {
                            remark += "����ͨ����";
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
     * TODO ��������ID��ȡ��ڵ�.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                               2016��3��14��    	    ����    �½�
     *                                                                                                                                                                                                                                                                               </pre>
     */
    public ActivityImpl getActivityImplByTaskId(String taskId) throws Exception {
        Task task = getTaskById(taskId);
        //1.��ȡ��ǰ��ڵ㷽����Ϊ������룬����ķ�ʽ���������л�ȡ������ڵ�ID
        String excId = task.getExecutionId();
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
        String activityId = execution.getActivityId();
        //2.��ȡ���̶���,��ڵ�(ActivityImpl�����ڴ���,���Բ�����Query�����ѯ)
        ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
        //3.ͨ����Ľڵ�ID��ȡ�ڵ����
        ActivityImpl activity = pd.findActivity(activityId);
        return activity;
    }

    public ActivityImpl getActivityImplByProcessInstanceId(String processInstanceId) throws Exception {
        Task task = getTaskByProcessInstanceId(processInstanceId);
        //1.��ȡ��ǰ��ڵ㷽����Ϊ������룬����ķ�ʽ���������л�ȡ������ڵ�ID
        String excId = task.getExecutionId();
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
        String activityId = execution.getActivityId();
        //2.��ȡ���̶���,��ڵ�(ActivityImpl�����ڴ���,���Բ�����Query�����ѯ)
        ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
        //3.ͨ����Ľڵ�ID��ȡ�ڵ����
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
            list.add("ͨ��");
        }
        return list;
    }

    @Override
    public List<Comment> getTaskComments(String taskId) throws Exception {
        List<Comment> comments = new ArrayList<Comment>();
        //��ȡ�������
        Task task = getTaskById(taskId);
        //��ȡ����ʵ������
        String pid = task.getProcessInstanceId();
        //ͨ������ʵ��ID����ȡ������ʷ����
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
        //ʹ��ע��������
        for (int i = 0; i < commentsResever.size(); i++) {
            comments.add(commentsResever.get(Integer.valueOf(commentsResever.size() - 1 - i)));
        }

        /**
         *��ӵ�ǰ��������Ϣ��ע
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
        //��ѯ��ɫ
        List<String> roleIdList = webOperRoleMapper.selectOwnRoleByOperCode(fdOper.getOpercode());
        String roleName = "";
        for (String roleId : roleIdList) {
            WebRoleInfo webRoleInfo = webRoleInfoMapper.selectByPK(roleId);
            if (Objects.nonNull(webRoleInfo)) {
                roleName += webRoleInfo.getRoleName() + ",";
            }
        }
        String msg = "������";
        if (roleName.contains("¼��") || roleName.contains("ϵͳ����Ա")) {
            msg = "���ύ";
        }
        WebOperInfo webOperInfo = new WebOperInfo();
        webOperInfo.setOperCode(fdOper.getOpercode());
        List<WebOperInfo> webOperInfoList = webOperInfoMapper.selectByAttr(webOperInfo);
        FdOrgan fdOrgan = fdOrganMapper.selectByPK(webOperInfoList.get(0).getOrganCode());
        String str = "";
        if ("11005293".equals(webOperInfoList.get(0).getOrganCode())) {
            str = "����-" + fdOper.getOpername();
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
        //��ȡ�������
        Task task = getTaskByProcessInstanceId(processInstanceId);
        //��ȡ����ʵ������
        String pid = task.getProcessInstanceId();
        //ͨ������ʵ��ID����ȡ������ʷ����
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
         *��ӵ�ǰ��������Ϣ��ע
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
        comment.setFullMessage("������");
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
     * TODO ͨ��ʵ��ID������.
     *
     * @param processInstanceId
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                           �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                           2016��3��15��    	    ����    �½�
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
        //����������̶���key���Ͳ�ĳ�����̵����񣬷�����������̵�����
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            taskList = taskService
                    .createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .taskName(taskName)
                    .active()//���鼤��״̬�����񣬹���Ĳ���
                    .orderByTaskCreateTime()
                    .desc()
                    .listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        } else {
            taskList = taskService
                    .createTaskQuery()
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .taskName(taskName)
                    .active()//���鼤��״̬�����񣬹���Ĳ���
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
        //����������̶���key���Ͳ�ĳ�����̵����񣬷�����������̵�����
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            return taskService.createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskAssignee(assignee)
                    .taskName(taskName)
                    .active()//���鼤��״̬�����񣬹���Ĳ���
                    .count();
        } else {
            return taskService
                    .createTaskQuery()
                    .taskAssignee(assignee)
                    .taskName(taskName)
                    .active()//���鼤��״̬�����񣬹���Ĳ���
                    .count();
        }
    }

    /**
     * TODO ͨ���ھ������ֶκ�ƴ�Ӳ�ͬ���ַ�����ָ����ͬ�����ҵ������.
     * ��ѯ�����������б���Ϣ
     *
     * @param processDefinitionKey
     * @param assignee
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                               2016��4��5��    	  ����    �½�
     *                                                                                                                                                                                                                                                                               </pre>
     */
    @Override
    public List<Map<String, Object>> getPersonalTaskPageByType(String processDefinitionKey, String assignee, Pager pager) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        List<Task> taskList = new ArrayList<Task>();
        //����������̶���key���Ͳ�ĳ�����̵����񣬷�����������̵�����
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            taskList = taskService
                    .createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskAssignee(assignee)
                    .active()//���鼤��״̬�����񣬹���Ĳ���
                    .orderByTaskCreateTime()
                    .desc()
                    .listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        } else {
            taskList = taskService
                    .createTaskQuery()
                    .taskAssignee(assignee)
                    .active()//���鼤��״̬�����񣬹���Ĳ���
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
     * TODO ͨ���ھ������ֶκ�ƴ�Ӳ�ͬ���ַ�����ָ����ͬ�����ҵ������.
     * ��ѯ���������ļ�¼��
     *
     * @param processDefinitionKey
     * @param assignee
     * @param
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                               2016��4��5��    	  ����    �½�
     *                                                                                                                                                                                                                                                                               </pre>
     */
    @Override
    public long getPersonalTaskCountByType(String processDefinitionKey, String assignee) throws Exception {
        //����������̶���key���Ͳ�ĳ�����̵����񣬷�����������̵�����
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            return taskService.createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskAssignee(assignee)
                    .active()//���鼤��״̬�����񣬹���Ĳ���
                    .count();
        } else {
            return taskService
                    .createTaskQuery()
                    .taskAssignee(assignee)
                    .active()//���鼤��״̬�����񣬹���Ĳ���
                    .count();
        }
    }

    /**
     * TODO ��ѯ��ǰ�˵�������.
     *
     *
     * <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��4��6��    	  ����    �½�
     * </pre>
     */
    @Override
    public List<Map<String, Object>> findPersonalGroupTask(String processDefinitionKey, String candidateUser, Pager pager) {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        List<Task> taskList = new ArrayList<Task>();
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            taskList = taskService//������ִ�е����������ص�Service
                    .createTaskQuery()//���������ѯ����
                    .processDefinitionKey(processDefinitionKey)
                    /**��ѯ������where���֣�*/
                    .taskCandidateUser(candidateUser)//������İ����˲�ѯ
                    /**����*/
                    .orderByTaskCreateTime().asc()//ʹ�ô���ʱ�����������
                    /**���ؽ����*/
                    .listPage((pager.getPageNo() - 1) * pager.getPageSize(), pager.getPageSize());
        } else {
            taskList = taskService//������ִ�е����������ص�Service
                    .createTaskQuery()//���������ѯ����
                    /**��ѯ������where���֣�*/
                    .taskCandidateUser(candidateUser)//������İ����˲�ѯ
                    /**����*/
                    .orderByTaskCreateTime().asc()//ʹ�ô���ʱ�����������
                    /**���ؽ����*/
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
     * TODO ��ѯ��ǰ�˵�������
     *
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                               2016��4��6��    	  ����    �½�
     *                                                                                                                                                                                                                                                                               </pre>
     */
    @Override
    public long findPersonalGroupTaskCount(String processDefinitionKey, String candidateUser) throws Exception {
        //����������̶���key���Ͳ�ĳ�����̵����񣬷�����������̵�����
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            return taskService.createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskCandidateUser(candidateUser)//������İ����˲�ѯ
                    .active()//���鼤��״̬�����񣬹���Ĳ���
                    .count();
        } else {
            return taskService
                    .createTaskQuery()
                    .taskCandidateUser(candidateUser)//������İ����˲�ѯ
                    .active()//���鼤��״̬�����񣬹���Ĳ���
                    .count();
        }
    }

    /**
     * TODO ʰȡ���񣬽�������ָ���������ָ������İ������ֶ�
     *
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                               2016��4��6��    	  ����    �½�
     *                                                                                                                                                                                                                                                                               </pre>
     */
    public void claim(String taskId, String assignee) throws Exception {
        //��������������������
        taskService.claim(taskId, assignee);
    }

    /**
     * TODO ͨ��ָ�������̶�key��ȡ���µ����̶���.
     *
     * @param processDefinitionKey
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                               2016��4��6��    	  ����    �½�
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
        //ͨ������ʵ��ID����ȡ������ʷ����
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
        //ʹ��ע��������
        for (int i = 0; i < commentsResever.size(); i++) {
            comments.add(commentsResever.get(Integer.valueOf(commentsResever.size() - 1 - i)));
        }
        /**
         *��ӵ�ǰ��������Ϣ��ע
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
                comment.setFullMessage("��ǩ��");
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
                //��ѯ��ɫ
                List<String> roleIdList = webOperRoleMapper.selectOwnRoleByOperCode(fdOper.getOpercode());
                String roleName = "";
                for (String roleId : roleIdList) {
                    WebRoleInfo webRoleInfo = webRoleInfoMapper.selectByPK(roleId);
                    if (Objects.nonNull(webRoleInfo)) {
                        roleName += webRoleInfo.getRoleName() + ",";
                    }
                }
                String msg = "������";
                if (roleName.contains("¼��") || roleName.contains("ϵͳ����Ա")) {
                    msg = "���ύ";
                }
                WebOperInfo webOperInfo = new WebOperInfo();
                webOperInfo.setOperCode(fdOper.getOpercode());
                List<WebOperInfo> webOperInfoList = webOperInfoMapper.selectByAttr(webOperInfo);
                FdOrgan fdOrgan = fdOrganMapper.selectByPK(webOperInfoList.get(0).getOrganCode());
                String str = "";
                if ("11005293".equals(webOperInfoList.get(0).getOrganCode())) {
                    str = "����-" + fdOper.getOpername();
                } else {
                    str = fdOrgan.getOrganname() + "-" + fdOper.getOpername();
                }
                comment.setUserId(str);
                comment.setFullMessage(msg);
            }
            comments.add(comment);
        } else {
            // �޸�����������Ϣ
            Comment com = comments.get(comments.size() - 1);
            comments.remove(comments.size() - 1);

            CommentEntity commentLast = new CommentEntity();
            commentLast.setUserId(com.getUserId());
            commentLast.setFullMessage("����ͨ�����ϱ�" + com.getFullMessage().substring(5));
            commentLast.setType(com.getType());
            commentLast.setTime(com.getTime());

            comments.add(commentLast);
        }
        return comments;
    }

    /**
     * TODO ��ȡ��ǰ�����������ļ�¼��.
     *
     * @param
     * @param
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��4��16��    	  ����    �½�
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
     * TODO �鿴��ǰ��Ϊ�����˵ļ�¼��.
     *
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                               2016��4��16��    	  ����    �½�
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
     * TODO ������id�����̱���.
     *
     * @param taskId
     * @param taskVariables
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                               2016��5��10��    	  ����    �½�
     *                                                                                                                                                                                                                                                                               </pre>
     */
    @Override
    public void setTaskVariables(String taskId, Map<String, Object> taskVariables) throws Exception {
        taskService.setVariablesLocal(taskId, taskVariables);
    }

    /**
     * TODO ������id�����̱���.
     *
     * @param taskId
     * @param taskVariables
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                               �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                               2016��5��10��    	  ����    �½�
     *                                                                                                                                                                                                                                                                               </pre>
     */
    @Override
    public void setVariablesByPID(String taskId, Map<String, Object> taskVariables) throws Exception {
        taskService.setVariables(taskId, taskVariables);
    }

    /**
     * TODO ͨ������id��ѯ���������.
     *
     * @param taskId
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��5��10��    	  ����    �½�
     * </pre>
     */
    @Override
    public List<HistoricVariableInstance> getHiVariablesByTaskId(String taskId) {
        return historyService.createHistoricVariableInstanceQuery().taskId(taskId).list();
    }

    /**
     * TODO ��ʷ����ʵ����ѯ.
     *
     * @param
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��5��10��    	  ����    �½�
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
        //����������̶���key���Ͳ�ĳ�����̵����񣬷�����������̵�����
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            taskList = taskService
                    .createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .active()//���鼤��״̬�����񣬹���Ĳ���
                    .orderByTaskCreateTime()
                    .desc().list();
        } else {
            taskList = taskService
                    .createTaskQuery()
                    .taskAssignee(WebContextUtil.getSessionUser().getOpercode())
                    .active()//���鼤��״̬�����񣬹���Ĳ���
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
            //ȥ�����غͽ�����ڵ�
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
     * TODO ͨ������ʵ��id����������ģ����ѯ��ʷ�����.
     *
     * @param processInstanceId
     * @param taskName
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��7��14��    	    ����    �½�
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
     * TODO ͨ������id��ѯ��ע��Ϣ����ȡ����id��Ӧ����ע��Ϣ.
     *
     * @param
     * @param
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��7��14��    	    ����    �½�
     * </pre>
     */
    @Override
    public List<Comment> getHistoricTaskComments(String taskId) throws Exception {
        List<Comment> comments = taskService.getTaskComments(taskId);
        //����������ʷ����ͨ��taskId��ȡ������ע
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
                if (!documentation.contains("�༭")) {
                    if (null != varMap) {
                        if ("0".equals((String) varMap.get("isAgree"))) {//��ͬ��
                            remark += "�������أ�";
                        } else if ("1".equals((String) varMap.get("isAgree"))) {
                            remark += "����ͨ����";
                        } else {
                            remark += "�����ύ������";
                        }
                    }
                }
            }
            remark += " δ��ע";
        } else {
            if (!comment.contains("��������")) {
                if (StringUtils.isNotBlank(documentation)) {
                    remark += "" + documentation;
                    if (!documentation.contains("�༭")) {
                        if (null != varMap) {
                            if ("0".equals((String) varMap.get("isAgree"))) {//��ͬ��
                                remark += "�������أ�";
                            } else if ("1".equals((String) varMap.get("isAgree"))) {
                                remark += "����ͨ����";
                            } else {
                                remark += "�����ύ������";
                            }
                        }
                        if ("null".equals(comment)) {
                            remark += " δ��ע";
                        } else {
                            remark += " " + comment;
                        }
                    }
                } else {
                    if (null != varMap) {
                        if ("0".equals((String) varMap.get("isAgree"))) {//��ͬ��
                            remark += "�������أ�";
                        } else if ("1".equals((String) varMap.get("isAgree"))) {
                            remark += "����ͨ����";
                        } else {
                            remark += "�����ύ������";
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
        //��ȡ�������
        Task task = getTaskById(taskId);
        //��ȡ����ʵ������
        String pid = task.getProcessInstanceId();
        //ͨ������ʵ��ID����ȡ������ʷ����
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
         *��ӵ�ǰ��������Ϣ��ע
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
            comment.setFullMessage("������");
            comments.add(comment);
        }
        return comments;
    }

    @Override
    public List<Comment> getInstanceComments(String processInstanceId, String type) throws Exception {
        List<Comment> comments = new ArrayList<Comment>();
        //ͨ������ʵ��ID����ȡ������ʷ����
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
         *��ӵ�ǰ��������Ϣ��ע
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
                    comment.setFullMessage("��ǩ��");
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
                    comment.setFullMessage("������");
                }
                comments.add(comment);
            }
        }
        return comments;
    }

    @Override
    public boolean getLastUserType(ActivityImpl activityImpl) throws Exception {
        boolean lastUserType = false;
        //��ȡ��ǰ����֮��ڵ������
        List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
        if (pvmList != null && pvmList.size() > 0) {
            for (PvmTransition pvm : pvmList) {
                PvmActivity act = pvm.getDestination();
                //��������صĻ���ͨ�����ػ�ȡ��һ���ڵ������
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
     * ��ȡ��һ���û������û�����Ϣ
     *
     * @param taskId ����Id��Ϣ
     * @return ��һ���û������û�����Ϣ
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

//��ǰ���̽ڵ�Id��Ϣ
        String activitiId = execution.getActivityId();

//��ȡ�������нڵ���Ϣ
        List<ActivityImpl> activitiList = processDefinitionEntity.getActivities();
//�������нڵ���Ϣ
        for (ActivityImpl activityImpl : activitiList) {
            id = activityImpl.getId();

            // �ҵ���ǰ�ڵ���Ϣ
            if (activitiId.equals(id)) {

//��ȡ��һ���ڵ���Ϣ
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
     * ��һ������ڵ���Ϣ,
     * <p>
     * �����һ���ڵ�Ϊ�û�������ֱ�ӷ���,
     * <p>
     * �����һ���ڵ�Ϊ��������, ��ȡ��������Id��Ϣ, ������������Id��Ϣ��execution��ȡ����ʵ����������IdΪkey�ı���ֵ,
     * ���ݱ���ֵ�ֱ�ִ���������غ���·�е�el���ʽ, ���ҵ�el���ʽͨ������·����û�������Ϣ
     *
     * @param ActivityImpl activityImpl     ���̽ڵ���Ϣ
     * @param String       activityId     ��ǰ���̽ڵ�Id��Ϣ
     * @param String       elString       ��������˳�����߶��ж�����, ������������˳�����߶��ж�����Ϊ${money>1000}, ��������������ʱ����variables�е�money>1000, �����������˳������Ϣ
     * @param String       processInstanceId      ����ʵ��Id��Ϣ
     * @return
     */
    private TaskDefinition nextTaskDefinition(ActivityImpl activityImpl, String activityId, String elString, String processInstanceId) {

        PvmActivity ac = null;

        Object s = null;

//��������ڵ�Ϊ�û������ҽڵ㲻�ǵ�ǰ�ڵ���Ϣ
        if ("userTask".equals(activityImpl.getProperty("type")) && !activityId.equals(activityImpl.getId())) {
//��ȡ�ýڵ���һ���ڵ���Ϣ
            TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activityImpl.getActivityBehavior()).getTaskDefinition();
            return taskDefinition;
        } else {
//��ȡ�ڵ�����������·��Ϣ
            List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
            List<PvmTransition> outTransitionsTemp = null;
            for (PvmTransition tr : outTransitions) {
                ac = tr.getDestination(); //��ȡ��·���յ�ڵ�
                //���������·Ϊ��������
                if ("exclusiveGateway".equals(ac.getProperty("type"))) {
                    outTransitionsTemp = ac.getOutgoingTransitions();

//�������·���ж�����Ϊ����Ϣ
                    if (StringUtils.isEmpty(elString)) {
                        //��ȡ��������ʱ���õ������ж�������Ϣ
                        elString = getGatewayCondition(ac.getId(), processInstanceId);
                    }

//�����������ֻ��һ����·��Ϣ
                    if (outTransitionsTemp.size() == 1) {
                        return nextTaskDefinition((ActivityImpl) outTransitionsTemp.get(0).getDestination(), activityId, elString, processInstanceId);
                    } else if (outTransitionsTemp.size() > 1) {  //������������ж�����·��Ϣ
                        for (PvmTransition tr1 : outTransitionsTemp) {
                            s = tr1.getProperty("conditionText");  //��ȡ����������·�ж�������Ϣ
//�ж�el���ʽ�Ƿ����
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
     * ��ѯ��������ʱ�������������ж�������Ϣ
     *
     * @param String gatewayId  ��������Id��Ϣ, ��������ʱ��������·���ж�����keyΪ����Id��Ϣ
     * @param String processInstanceId  ����ʵ��Id��Ϣ
     * @return
     */
    public String getGatewayCondition(String gatewayId, String processInstanceId) {
        Execution execution = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).singleResult();
        return runtimeService.getVariable(execution.getId(), gatewayId).toString();
    }

    /**
     * ����key��value�ж�el���ʽ�Ƿ�ͨ����Ϣ
     *
     * @param String key    el���ʽkey��Ϣ
     * @param String el     el���ʽ��Ϣ
     * @param String value  el���ʽ����ֵ��Ϣ
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
