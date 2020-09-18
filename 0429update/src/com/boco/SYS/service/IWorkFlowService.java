package com.boco.SYS.service;

import com.boco.SYS.util.Pager;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IWorkFlowService {
    /**
     * TODO 部署新流程.
     *
     * @param processFile
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月21日    	    杨滔    新建
     *                                                       </pre>
     */
    public void deployNewProcess(CommonsMultipartFile file) throws Exception;

    /**
     * TODO 获取所有流程定义列表.
     *
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月21日    	    杨滔    新建
     *                                                       </pre>
     */
    public long getProcessDefinitionCount() throws Exception;

    /**
     * TODO 获取分页流程定义列表.
     *
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月22日    	    杨滔    新建
     *                                                       </pre>
     */
    public List<Map<String, Object>> getProcessDefinitionPage(Pager pager) throws Exception;

    /**
     * TODO 删除流程.
     *
     * @param deploymentId
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月21日    	    杨滔    新建
     *                                                       </pre>
     */
    public void deleteProcess(String deploymentId) throws Exception;

    /**
     * TODO 返回资源文件流.
     *
     * @param processDefinitionId
     * @param resourceName        image,xml
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月21日    	    杨滔    新建
     *                                                       </pre>
     */
    public InputStream getResourceAsStream(String processDefinitionId, String resourceType) throws Exception;

    /**
     * TODO 获取当前任务节点坐标.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月22日    	    杨滔    新建
     *                                                       </pre>
     */
    public Map<String, Object> getCurrentActivitiCoordinate(String taskId) throws Exception;

    public Map<String, Object> getCurrentActivitiCoordinateByProcessInstanceId(String processInstanceId) throws Exception;

    /**
     * TODO 激活任务.
     *
     * @param processDefinitionId
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月22日    	    杨滔    新建
     *                                                       </pre>
     */
    public void activateProcessDefinitionById(String processDefinitionId) throws Exception;

    /**
     * TODO 挂起任务.
     *
     * @param processDefinitionId
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月22日    	    杨滔    新建
     *                                                       </pre>
     */
    public void suspendProcessDefinitionById(String processDefinitionId) throws Exception;

    /**
     * TODO 启动流程.
     *
     * @param processDefinitionKey
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月21日    	    杨滔    新建
     *                                                       </pre>
     */
    public ProcessInstance startProcess(String processDefinitionKey, Map<String, Object> variables) throws Exception;

    /**
     * TODO 查看个人任务.
     *
     * @param processDefinitionKey 流程key
     * @param assignee
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月21日    	    杨滔    新建
     *                                                       </pre>
     */
    public long getPersonalTaskCount(String processDefinitionKey, String assignee) throws Exception;

    /**
     * TODO 查看个人任务分页数据.
     *
     * @param processDefinitionKey 流程key
     * @param assignee
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月22日    	    杨滔    新建
     *                                                       </pre>
     */
    public List<Map<String, Object>> getPersonalTaskPage(String processDefinitionKey, Pager pager) throws Exception;

    /**
     * TODO 通过任务Id获取流程定义.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月22日    	    杨滔    新建
     *                                                       </pre>
     */
    public ProcessDefinition getProcessDefinitionByTaskId(String taskId) throws Exception;

    /**
     * TODO 通过任务Id获取流程实例.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月22日    	    杨滔    新建
     *                                                       </pre>
     */
    public ProcessInstance getProcessInstanceByTaskId(String taskId) throws Exception;

    public Task getTaskByProcessInstanceId(String processInstanceId) throws Exception;

    /**
     * TODO 通过Id获取任务.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月22日    	    杨滔    新建
     *                                                       </pre>
     */
    public Task getTaskById(String taskId) throws Exception;

    /**
     * TODO 通过ID获取流程定义.
     *
     * @param processDefinitionId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月22日    	    杨滔    新建
     *                                                       </pre>
     */
    public ProcessDefinition getProcessDefinitionById(String processDefinitionId) throws Exception;

    /**
     * TODO 通过流程实例Id获取流程实例对象.
     *
     * @param processInstanceId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月22日    	    杨滔    新建
     *                                                       </pre>
     */
    public ProcessInstance getProcessInstanceById(String processInstanceId) throws Exception;

    /**
     * TODO 通过任务ID获取FormKey
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月22日    	    杨滔    新建
     *                                                       </pre>
     */
    public String getTaskFormKeyByTaskId(String taskId) throws Exception;

    /**
     * TODO 通过任务ID获取业务KEY.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月22日    	    杨滔    新建
     *                                                       </pre>
     */
    public String getBusinessKeyByTaskId(String taskId) throws Exception;

    /**
     * TODO 查询任务流程.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年3月15日    	    杨滔    新建
     *                                                       </pre>
     */
    public Map<String, Object> getTaskVariables(String taskId) throws Exception;

    /**
     * TODO 完成审批.
     *
     * @param taskId
     * @param comment 批注
     * @param varMap  流程变量
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月22日    	    杨滔    新建
     *                                                       </pre>
     */
    public void completeTask(String taskId, String comment, Map<String, Object> varMap) throws Exception;

    /**
     * TODO 添加自定义批注
     *
     * @param taskId
     * @param comment
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年9月20日    	   谷立羊  新建
     *                                                       </pre>
     */
    public void addTaskComment(String taskId, String type, String comment, Map<String, Object> varMap) throws Exception;

    /**
     * TODO 获取任务所有的出口名称.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月23日    	    杨滔    新建
     *                                                       </pre>
     */
    public List<String> getOutGoingName(String taskId) throws Exception;

    /**
     * TODO 请输入你的方法说明.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月23日    	    杨滔    新建
     *                                                       </pre>
     */
    public List<Comment> getTaskComments(String taskId) throws Exception;

    public List<Comment> getTaskCommentsByProcessInstanceId(String taskId) throws Exception;

    /**
     * TODO 根据任务id和批注类型获取批注
     *
     * @param taskId
     * @param type
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年9月20日    	   谷立羊  新建
     *                                                       </pre>
     */
    public List<Comment> getTaskComments(String taskId, String type) throws Exception;

    /**
     * TODO 通过流程实例id查询批注信息
     *
     * @param processInstanceId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年4月7日    	    谷立羊   新建
     *                                                       </pre>
     */
    public List<Comment> getInstanceComments(String processInstanceId) throws Exception;

    /**
     * TODO 通过流程实例id和批注类型查询批注信息
     *
     * @param processInstanceId
     * @param type
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年4月7日    	    谷立羊   新建
     *                                                       </pre>
     */
    public List<Comment> getInstanceComments(String processInstanceId, String type) throws Exception;

    /**
     * TODO 根据流程定义key查询运行中的实例数.
     *
     * @param processDefinitionKey
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月24日    	    杨滔    新建
     *                                                       </pre>
     */
    public long getRunningProcessInstaceCount(String processDefinitionKey) throws Exception;

    /**
     * TODO 根据流程定义key查询运行中的实例.
     *
     * @param processDefinitionKey
     * @param pager
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月24日    	    杨滔    新建
     * </pre>
     */
    public List<Map<String, Object>> getRunningProcessInstacePage(String processDefinitionKey, Pager pager) throws Exception;

    /**
     * TODO 根据任务Id获取当前活动节点名称.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年2月25日    	    杨滔    新建
     *                                                       </pre>
     */
    public String getActivityNameByTaskId(String taskId) throws Exception;

    /**
     * TODO 查询已提交历史.
     *
     * @param processDefinitionKey
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年3月14日    	    杨滔    新建
     *                                                       </pre>
     */
    public List<Map<String, Object>> getSubProcessInstacePage(String processDefinitionKey, Pager pager) throws Exception;

    /**
     * TODO 查询已审批历史.
     *
     * @param processDefinitionKey
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年3月14日    	    杨滔    新建
     *                                                       </pre>
     */
    public List<Map<String, Object>> getFinishedProcessInstacePage(String processDefinitionKey, String assign, Pager pager) throws Exception;

    /**
     * TODO 查看个人任务的记录数 激活状态.
     *
     * @param processDefinitionKey
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年3月29日    	  杜旭    新建
     *                                                       </pre>
     */
    public String getPersonalTaskActiveCount(String processDefinitionKey) throws Exception;

    List<Map<String, Object>> getPersonalTaskByName(String taskName,
                                                    String processDefinitionKey, Pager pager) throws Exception;

    /**
     * TODO 根据任务名称获取正在运行的记录数(激活).
     *
     * @param taskName
     * @param processDefinitionKey
     * @param assignee
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年3月29日    	  杜旭    新建
     *                                                       </pre>
     */
    long getPersonalTaskCountByName(String taskName, String processDefinitionKey, String assignee) throws Exception;

    /**
     * TODO 根据任务ID获取活动节点.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年3月14日    	    杨滔    新建
     *                                                       </pre>
     */
    public ActivityImpl getActivityImplByTaskId(String taskId) throws Exception;

    /**
     * TODO 通过在经办人字段后拼接不同的字符串来指定不同种类的业务类型.
     *
     * @param processDefinitionKey
     * @param assignee
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年4月5日    	  杜旭    新建
     *                                                       </pre>
     */
    List<Map<String, Object>> getPersonalTaskPageByType(String processDefinitionKey, String assignee, Pager pager) throws Exception;

    /**
     * TODO 通过在经办人字段后拼接不同的字符串来指定不同种类的业务类型.
     * 查询符合条件的记录数
     *
     * @param processDefinitionKey
     * @param assignee
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年4月5日    	  杜旭    新建
     *                                                       </pre>
     */
    long getPersonalTaskCountByType(String processDefinitionKey, String assignee) throws Exception;

    /**
     * TODO 查询当前人的组任务.
     *
     *
     * <pre>
     * 修改日期        修改人    修改原因
     * 2016年4月6日    	  杜旭    新建
     * </pre>
     */
    public List<Map<String, Object>> findPersonalGroupTask(String processDefinitionKey, String candidateUser, Pager pager);

    /**
     * TODO 查询当前人的组任务
     *
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年4月6日    	  杜旭    新建
     *                                                       </pre>
     */
    public long findPersonalGroupTaskCount(String processDefinitionKey, String candidateUser) throws Exception;

    /**
     * TODO 拾取任务，将组任务分给个人任务，指定任务的办理人字段
     *
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年4月6日    	  杜旭    新建
     *                                                       </pre>
     */
    public void claim(String taskId, String assignee) throws Exception;

    /**
     * TODO 通过指定的流程定义key,获取最新的流程定义.
     *
     * @param processDefinitionKey
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年4月6日    	  杜旭    新建
     *                                                       </pre>
     */
    public List<ProcessDefinition> getProcessDefinitionList(String processDefinitionKey) throws Exception;

    /**
     * TODO 获取当前人已完成任务的记录数.
     *
     * @param processdefinitionkeyResearch
     * @param string
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年4月16日    	  杜旭    新建
     * </pre>
     */
    public Long getFinishedProcessInstaceCountByType(String processDefinitionKey, String assign);

    /**
     * TODO 查看当前人为发起人的记录数.
     *
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年4月16日    	  杜旭    新建
     *                                                       </pre>
     */
    public Long getStartedByCountByType(String processDefinitionKey, String assign);

    /**
     * TODO 通过实例ID查任务.
     * 将该方法添加到该接口中
     *
     * @param processInstanceId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年4月29日    	  杜旭    新建
     *                                                       </pre>
     */
    public Task getTaskByPid(String processInstanceId) throws Exception;

    /**
     * TODO 与任务id绑定流程变量.
     *
     * @param taskId
     * @param taskVariables
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年5月10日    	  杜旭    新建
     *                                                       </pre>
     */
    public void setTaskVariables(String taskId, Map<String, Object> taskVariables) throws Exception;

    /**
     * TODO 与任务id绑定流程变量.
     *
     * @param taskId
     * @param taskVariables
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年5月10日    	  杜旭    新建
     *                                                       </pre>
     */
    void setVariablesByPID(String taskId, Map<String, Object> taskVariables) throws Exception;

    /**
     * TODO 通过任务id查询已完成任务的流程变量.
     *
     * @param taskId
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年5月10日    	  杜旭    新建
     * </pre>
     */
    public List<HistoricVariableInstance> getHiVariablesByTaskId(String taskId);

    /**
     * TODO 历史流程实例查询.
     *
     * @param taskId
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年5月10日    	  杜旭    新建
     * </pre>
     */
    public HistoricProcessInstance getProcessInstance(String processInstanceId);

    /**
     * TODO 不分页查询个人待任务
     *
     * @param processdefinitionkey
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年5月19日    	    谷立羊   新建
     *                                                       </pre>
     */
    public List<Map<String, Object>> getPersonalTask(String processdefinitionkey) throws Exception;

    /**
     * TODO不分页查询个人已提交记录
     *
     * @param processdefinitionkey
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年5月19日    	    谷立羊   新建
     *                                                       </pre>
     */
    public List<Map<String, Object>> getSubProcessInstace(String processdefinitionkey) throws Exception;

    /**
     * TODO 不分页查询个人已审批记录
     *
     * @param processdefinitionkey
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年5月19日    	    谷立羊   新建
     *                                                       </pre>
     */
    public List<Map<String, Object>> getFinishedProcessInstace(String processdefinitionkey) throws Exception;

    /**
     * TODO 根据流程id查询活动任务节点
     *
     * @param prodDefId
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年6月14日    	   谷立羊  新建
     *                                                       </pre>
     */
    public Map<String, Object> getTaskIdByProcDefId(String procDefId) throws Exception;

    /**
     * TODO 获取角色信息
     *
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年6月14日    	   谷立羊  新建
     *                                                       </pre>
     */
    public Map<String, Object> getTaskRole() throws Exception;

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
    public List<HistoricTaskInstance> getHistoricTaskInstance(String processInstanceId, String taskName);

    /**
     * TODO 通过任务id查询批注信息表，获取任务id对应的批注信息.
     *
     * @param processInstanceId
     * @param taskName
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年7月14日    	    杜旭    新建
     * </pre>
     */
    public List<Comment> getHistoricTaskComments(String taskId) throws Exception;

    /**
     * TODO 判断是否是最后审批人
     *
     * @param activityImpl
     * @return
     * @throws Exception <pre>
     *                                                       修改日期        修改人    修改原因
     *                                                       2016年12月5日    	   谷立羊  新建
     *                                                       </pre>
     */
    public boolean getLastUserType(ActivityImpl activityImpl) throws Exception;

    public List<TaskInfo> getProcessListByOrganCode(String organCode);

    public String getNextTaskAssigneeKey(String taskId,String elString) throws Exception;
}
