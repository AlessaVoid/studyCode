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
     * TODO ����������.
     *
     * @param processFile
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��21��    	    ����    �½�
     *                                                       </pre>
     */
    public void deployNewProcess(CommonsMultipartFile file) throws Exception;

    /**
     * TODO ��ȡ�������̶����б�.
     *
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��21��    	    ����    �½�
     *                                                       </pre>
     */
    public long getProcessDefinitionCount() throws Exception;

    /**
     * TODO ��ȡ��ҳ���̶����б�.
     *
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��22��    	    ����    �½�
     *                                                       </pre>
     */
    public List<Map<String, Object>> getProcessDefinitionPage(Pager pager) throws Exception;

    /**
     * TODO ɾ������.
     *
     * @param deploymentId
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��21��    	    ����    �½�
     *                                                       </pre>
     */
    public void deleteProcess(String deploymentId) throws Exception;

    /**
     * TODO ������Դ�ļ���.
     *
     * @param processDefinitionId
     * @param resourceName        image,xml
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��21��    	    ����    �½�
     *                                                       </pre>
     */
    public InputStream getResourceAsStream(String processDefinitionId, String resourceType) throws Exception;

    /**
     * TODO ��ȡ��ǰ����ڵ�����.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��22��    	    ����    �½�
     *                                                       </pre>
     */
    public Map<String, Object> getCurrentActivitiCoordinate(String taskId) throws Exception;

    public Map<String, Object> getCurrentActivitiCoordinateByProcessInstanceId(String processInstanceId) throws Exception;

    /**
     * TODO ��������.
     *
     * @param processDefinitionId
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��22��    	    ����    �½�
     *                                                       </pre>
     */
    public void activateProcessDefinitionById(String processDefinitionId) throws Exception;

    /**
     * TODO ��������.
     *
     * @param processDefinitionId
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��22��    	    ����    �½�
     *                                                       </pre>
     */
    public void suspendProcessDefinitionById(String processDefinitionId) throws Exception;

    /**
     * TODO ��������.
     *
     * @param processDefinitionKey
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��21��    	    ����    �½�
     *                                                       </pre>
     */
    public ProcessInstance startProcess(String processDefinitionKey, Map<String, Object> variables) throws Exception;

    /**
     * TODO �鿴��������.
     *
     * @param processDefinitionKey ����key
     * @param assignee
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��21��    	    ����    �½�
     *                                                       </pre>
     */
    public long getPersonalTaskCount(String processDefinitionKey, String assignee) throws Exception;

    /**
     * TODO �鿴���������ҳ����.
     *
     * @param processDefinitionKey ����key
     * @param assignee
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��22��    	    ����    �½�
     *                                                       </pre>
     */
    public List<Map<String, Object>> getPersonalTaskPage(String processDefinitionKey, Pager pager) throws Exception;

    /**
     * TODO ͨ������Id��ȡ���̶���.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��22��    	    ����    �½�
     *                                                       </pre>
     */
    public ProcessDefinition getProcessDefinitionByTaskId(String taskId) throws Exception;

    /**
     * TODO ͨ������Id��ȡ����ʵ��.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��22��    	    ����    �½�
     *                                                       </pre>
     */
    public ProcessInstance getProcessInstanceByTaskId(String taskId) throws Exception;

    public Task getTaskByProcessInstanceId(String processInstanceId) throws Exception;

    /**
     * TODO ͨ��Id��ȡ����.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��22��    	    ����    �½�
     *                                                       </pre>
     */
    public Task getTaskById(String taskId) throws Exception;

    /**
     * TODO ͨ��ID��ȡ���̶���.
     *
     * @param processDefinitionId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��22��    	    ����    �½�
     *                                                       </pre>
     */
    public ProcessDefinition getProcessDefinitionById(String processDefinitionId) throws Exception;

    /**
     * TODO ͨ������ʵ��Id��ȡ����ʵ������.
     *
     * @param processInstanceId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��22��    	    ����    �½�
     *                                                       </pre>
     */
    public ProcessInstance getProcessInstanceById(String processInstanceId) throws Exception;

    /**
     * TODO ͨ������ID��ȡFormKey
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��22��    	    ����    �½�
     *                                                       </pre>
     */
    public String getTaskFormKeyByTaskId(String taskId) throws Exception;

    /**
     * TODO ͨ������ID��ȡҵ��KEY.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��22��    	    ����    �½�
     *                                                       </pre>
     */
    public String getBusinessKeyByTaskId(String taskId) throws Exception;

    /**
     * TODO ��ѯ��������.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��3��15��    	    ����    �½�
     *                                                       </pre>
     */
    public Map<String, Object> getTaskVariables(String taskId) throws Exception;

    /**
     * TODO �������.
     *
     * @param taskId
     * @param comment ��ע
     * @param varMap  ���̱���
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��22��    	    ����    �½�
     *                                                       </pre>
     */
    public void completeTask(String taskId, String comment, Map<String, Object> varMap) throws Exception;

    /**
     * TODO ����Զ�����ע
     *
     * @param taskId
     * @param comment
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��9��20��    	   ������  �½�
     *                                                       </pre>
     */
    public void addTaskComment(String taskId, String type, String comment, Map<String, Object> varMap) throws Exception;

    /**
     * TODO ��ȡ�������еĳ�������.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��23��    	    ����    �½�
     *                                                       </pre>
     */
    public List<String> getOutGoingName(String taskId) throws Exception;

    /**
     * TODO ��������ķ���˵��.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��23��    	    ����    �½�
     *                                                       </pre>
     */
    public List<Comment> getTaskComments(String taskId) throws Exception;

    public List<Comment> getTaskCommentsByProcessInstanceId(String taskId) throws Exception;

    /**
     * TODO ��������id����ע���ͻ�ȡ��ע
     *
     * @param taskId
     * @param type
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��9��20��    	   ������  �½�
     *                                                       </pre>
     */
    public List<Comment> getTaskComments(String taskId, String type) throws Exception;

    /**
     * TODO ͨ������ʵ��id��ѯ��ע��Ϣ
     *
     * @param processInstanceId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��4��7��    	    ������   �½�
     *                                                       </pre>
     */
    public List<Comment> getInstanceComments(String processInstanceId) throws Exception;

    /**
     * TODO ͨ������ʵ��id����ע���Ͳ�ѯ��ע��Ϣ
     *
     * @param processInstanceId
     * @param type
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��4��7��    	    ������   �½�
     *                                                       </pre>
     */
    public List<Comment> getInstanceComments(String processInstanceId, String type) throws Exception;

    /**
     * TODO �������̶���key��ѯ�����е�ʵ����.
     *
     * @param processDefinitionKey
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��24��    	    ����    �½�
     *                                                       </pre>
     */
    public long getRunningProcessInstaceCount(String processDefinitionKey) throws Exception;

    /**
     * TODO �������̶���key��ѯ�����е�ʵ��.
     *
     * @param processDefinitionKey
     * @param pager
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��24��    	    ����    �½�
     * </pre>
     */
    public List<Map<String, Object>> getRunningProcessInstacePage(String processDefinitionKey, Pager pager) throws Exception;

    /**
     * TODO ��������Id��ȡ��ǰ��ڵ�����.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��2��25��    	    ����    �½�
     *                                                       </pre>
     */
    public String getActivityNameByTaskId(String taskId) throws Exception;

    /**
     * TODO ��ѯ���ύ��ʷ.
     *
     * @param processDefinitionKey
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��3��14��    	    ����    �½�
     *                                                       </pre>
     */
    public List<Map<String, Object>> getSubProcessInstacePage(String processDefinitionKey, Pager pager) throws Exception;

    /**
     * TODO ��ѯ��������ʷ.
     *
     * @param processDefinitionKey
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��3��14��    	    ����    �½�
     *                                                       </pre>
     */
    public List<Map<String, Object>> getFinishedProcessInstacePage(String processDefinitionKey, String assign, Pager pager) throws Exception;

    /**
     * TODO �鿴��������ļ�¼�� ����״̬.
     *
     * @param processDefinitionKey
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��3��29��    	  ����    �½�
     *                                                       </pre>
     */
    public String getPersonalTaskActiveCount(String processDefinitionKey) throws Exception;

    List<Map<String, Object>> getPersonalTaskByName(String taskName,
                                                    String processDefinitionKey, Pager pager) throws Exception;

    /**
     * TODO �����������ƻ�ȡ�������еļ�¼��(����).
     *
     * @param taskName
     * @param processDefinitionKey
     * @param assignee
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��3��29��    	  ����    �½�
     *                                                       </pre>
     */
    long getPersonalTaskCountByName(String taskName, String processDefinitionKey, String assignee) throws Exception;

    /**
     * TODO ��������ID��ȡ��ڵ�.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��3��14��    	    ����    �½�
     *                                                       </pre>
     */
    public ActivityImpl getActivityImplByTaskId(String taskId) throws Exception;

    /**
     * TODO ͨ���ھ������ֶκ�ƴ�Ӳ�ͬ���ַ�����ָ����ͬ�����ҵ������.
     *
     * @param processDefinitionKey
     * @param assignee
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��4��5��    	  ����    �½�
     *                                                       </pre>
     */
    List<Map<String, Object>> getPersonalTaskPageByType(String processDefinitionKey, String assignee, Pager pager) throws Exception;

    /**
     * TODO ͨ���ھ������ֶκ�ƴ�Ӳ�ͬ���ַ�����ָ����ͬ�����ҵ������.
     * ��ѯ���������ļ�¼��
     *
     * @param processDefinitionKey
     * @param assignee
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��4��5��    	  ����    �½�
     *                                                       </pre>
     */
    long getPersonalTaskCountByType(String processDefinitionKey, String assignee) throws Exception;

    /**
     * TODO ��ѯ��ǰ�˵�������.
     *
     *
     * <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��4��6��    	  ����    �½�
     * </pre>
     */
    public List<Map<String, Object>> findPersonalGroupTask(String processDefinitionKey, String candidateUser, Pager pager);

    /**
     * TODO ��ѯ��ǰ�˵�������
     *
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��4��6��    	  ����    �½�
     *                                                       </pre>
     */
    public long findPersonalGroupTaskCount(String processDefinitionKey, String candidateUser) throws Exception;

    /**
     * TODO ʰȡ���񣬽�������ָ���������ָ������İ������ֶ�
     *
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��4��6��    	  ����    �½�
     *                                                       </pre>
     */
    public void claim(String taskId, String assignee) throws Exception;

    /**
     * TODO ͨ��ָ�������̶���key,��ȡ���µ����̶���.
     *
     * @param processDefinitionKey
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��4��6��    	  ����    �½�
     *                                                       </pre>
     */
    public List<ProcessDefinition> getProcessDefinitionList(String processDefinitionKey) throws Exception;

    /**
     * TODO ��ȡ��ǰ�����������ļ�¼��.
     *
     * @param processdefinitionkeyResearch
     * @param string
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��4��16��    	  ����    �½�
     * </pre>
     */
    public Long getFinishedProcessInstaceCountByType(String processDefinitionKey, String assign);

    /**
     * TODO �鿴��ǰ��Ϊ�����˵ļ�¼��.
     *
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��4��16��    	  ����    �½�
     *                                                       </pre>
     */
    public Long getStartedByCountByType(String processDefinitionKey, String assign);

    /**
     * TODO ͨ��ʵ��ID������.
     * ���÷�����ӵ��ýӿ���
     *
     * @param processInstanceId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��4��29��    	  ����    �½�
     *                                                       </pre>
     */
    public Task getTaskByPid(String processInstanceId) throws Exception;

    /**
     * TODO ������id�����̱���.
     *
     * @param taskId
     * @param taskVariables
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��5��10��    	  ����    �½�
     *                                                       </pre>
     */
    public void setTaskVariables(String taskId, Map<String, Object> taskVariables) throws Exception;

    /**
     * TODO ������id�����̱���.
     *
     * @param taskId
     * @param taskVariables
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��5��10��    	  ����    �½�
     *                                                       </pre>
     */
    void setVariablesByPID(String taskId, Map<String, Object> taskVariables) throws Exception;

    /**
     * TODO ͨ������id��ѯ�������������̱���.
     *
     * @param taskId
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��5��10��    	  ����    �½�
     * </pre>
     */
    public List<HistoricVariableInstance> getHiVariablesByTaskId(String taskId);

    /**
     * TODO ��ʷ����ʵ����ѯ.
     *
     * @param taskId
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��5��10��    	  ����    �½�
     * </pre>
     */
    public HistoricProcessInstance getProcessInstance(String processInstanceId);

    /**
     * TODO ����ҳ��ѯ���˴�����
     *
     * @param processdefinitionkey
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��5��19��    	    ������   �½�
     *                                                       </pre>
     */
    public List<Map<String, Object>> getPersonalTask(String processdefinitionkey) throws Exception;

    /**
     * TODO����ҳ��ѯ�������ύ��¼
     *
     * @param processdefinitionkey
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��5��19��    	    ������   �½�
     *                                                       </pre>
     */
    public List<Map<String, Object>> getSubProcessInstace(String processdefinitionkey) throws Exception;

    /**
     * TODO ����ҳ��ѯ������������¼
     *
     * @param processdefinitionkey
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��5��19��    	    ������   �½�
     *                                                       </pre>
     */
    public List<Map<String, Object>> getFinishedProcessInstace(String processdefinitionkey) throws Exception;

    /**
     * TODO ��������id��ѯ�����ڵ�
     *
     * @param prodDefId
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��6��14��    	   ������  �½�
     *                                                       </pre>
     */
    public Map<String, Object> getTaskIdByProcDefId(String procDefId) throws Exception;

    /**
     * TODO ��ȡ��ɫ��Ϣ
     *
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��6��14��    	   ������  �½�
     *                                                       </pre>
     */
    public Map<String, Object> getTaskRole() throws Exception;

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
    public List<HistoricTaskInstance> getHistoricTaskInstance(String processInstanceId, String taskName);

    /**
     * TODO ͨ������id��ѯ��ע��Ϣ����ȡ����id��Ӧ����ע��Ϣ.
     *
     * @param processInstanceId
     * @param taskName
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��7��14��    	    ����    �½�
     * </pre>
     */
    public List<Comment> getHistoricTaskComments(String taskId) throws Exception;

    /**
     * TODO �ж��Ƿ������������
     *
     * @param activityImpl
     * @return
     * @throws Exception <pre>
     *                                                       �޸�����        �޸���    �޸�ԭ��
     *                                                       2016��12��5��    	   ������  �½�
     *                                                       </pre>
     */
    public boolean getLastUserType(ActivityImpl activityImpl) throws Exception;

    public List<TaskInfo> getProcessListByOrganCode(String organCode);

    public String getNextTaskAssigneeKey(String taskId,String elString) throws Exception;
}
