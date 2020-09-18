
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
     * TODO ���̶����б�ҳ��.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��1��20��    	    ����    �½�
     * </pre>
     */
    @RequestMapping("processDefinitionListUI")
    @SystemLog(tradeName = "���̹���", funCode = "ACT-01", funName = "�������̶����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String processDefinitionListUI() throws Exception {
        authButtons();
        return basePath + "/workflow/processDefinitionList";
    }


    /**
     * TODO ���̶����б�����
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��1��20��    	    ����    �½�
     * </pre>
     * @throws Exception
     */
    @RequestMapping("findProcessDefinitionPage")
    @SystemLog(tradeName = "���̹���", funCode = "ACT-01", funName = "�������̶����б�����", accessType = AccessType.READ, level = Debug.INFO)
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
     * TODO ����ҳ��.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��1��20��    	    ����    �½�
     * </pre>
     */
    @RequestMapping("deployUI")
    @SystemLog(tradeName = "���̹���", funCode = "ACT-01-01", funName = "�������̲����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String deployUI() throws Exception {
        return basePath + "/workflow/deploy";
    }

    /**
     * TODO ��ת����ڵ��ɫ��Ϣ
     *
     * @return
     * @throws Exception <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2016��6��13��    	   ������  �½�
     *                                     </pre>
     */
    @RequestMapping("taskRoleConfigUI")
    @SystemLog(tradeName = "���̹���", funCode = "ACT-01-01", funName = "�������̽ڵ��ɫ�б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String taskRoleConfigUI() throws Exception {
        String procDefId = getParameter("procDefId");
        List<WebTaskRoleInfo> list = webTaskRoleInfoService.selectByProcDefId(procDefId);
        setAttribute("gridData", pageData(list));
        setAttribute("procDefId", procDefId);
        return basePath + "/workflow/taskRoleConfigList";
    }

    /**
     * TODO ά�����ؽڵ����
     *
     * @return
     * @throws Exception <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2016��8��8��    	   ������  �½�
     *                                     </pre>
     */
    @RequestMapping("taskRoleConfigNewUI")
    @SystemLog(tradeName = "���̹���", funCode = "ACT-01-06", funName = "�������̽ڵ��ɫ�б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String taskRoleConfigNewUI() throws Exception {
        String procDefId = getParameter("procDefId");
        List<WebTaskRoleInfoNew> list = webTaskRoleInfoNewService.selectByProcDefId(procDefId);
        setAttribute("gridData", pageData(list));
        setAttribute("procDefId", procDefId);
        return basePath + "/workflow/taskRoleConfigNewList";
    }

    /**
     * TODO ��������.
     *
     * @param file
     * @return
     * @throws IOException <pre>
     *                                         �޸�����        �޸���    �޸�ԭ��
     *                                         2016��2��22��    	    ����    �½�
     *                                         </pre>
     */
    @RequestMapping("deploy")
    @SystemLog(tradeName = "���̹���", funCode = "ACT-01-01", funName = "��������", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String deploy(@RequestParam("file") CommonsMultipartFile file, String type) throws Exception {
        workFlowService.deployNewProcess(file);
        return this.json.returnMsg("true", "����ɹ�!").toJson();
    }

    /**
     * TODO ɾ������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping("delete")
    @SystemLog(tradeName = "���̹���", funCode = "ACT-01-02", funName = "ɾ�����̶���", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(String deploymentId) throws Exception {
        workFlowService.deleteProcess(deploymentId);
        return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
    }

    /**
     * TODO ͨ�����̶���鿴������Դ.
     *
     * @param processDefinitionId
     * @param resourceType
     * @param response
     * @throws Exception <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2016��2��18��    	    ����    �½�
     *                                     </pre>
     */
    @RequestMapping("showResource")
    @SystemLog(tradeName = "���̹���", funCode = "system", funName = "ͨ�����̶���鿴������Դ", accessType = AccessType.READ, level = Debug.DEBUG)
    public void loadByDeployment(String processDefinitionId, String resourceType, HttpServletResponse response) throws Exception {
        InputStream resourceAsStream = workFlowService.getResourceAsStream(processDefinitionId, resourceType);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * TODO ͨ������鿴������Դ.
     *
     * @param resourceType
     * @param processInstanceId
     * @param taskId
     * @param response
     * @throws Exception <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2016��2��22��    	    ����    �½�
     *                                     </pre>
     */
    @RequestMapping(value = "loadImgByProcessInstance")
    @SystemLog(tradeName = "���̹���", funCode = "system", funName = "ͨ������鿴������Դ", accessType = AccessType.READ, level = Debug.DEBUG)
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
     * TODO ����/��������.
     *
     * @param type
     * @param processDefinitionId
     * @return
     * @throws IOException <pre>
     *                                         �޸�����        �޸���    �޸�ԭ��
     *                                         2016��2��22��    	    ����    �½�
     *                                         </pre>
     */
    @RequestMapping("changeStatus")
    @SystemLog(tradeName = "���̹���", funCode = "system", funName = "����/�������̶���", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String changeStatus(String type, String processDefinitionId) throws Exception {
        if (type.equals("active")) {
            workFlowService.activateProcessDefinitionById(processDefinitionId);
            return this.json.returnMsg("true", "����[" + processDefinitionId + "]����ɹ�!").toJson();
        } else if (type.equals("suspend")) {
            workFlowService.suspendProcessDefinitionById(processDefinitionId);
            return this.json.returnMsg("true", "����[" + processDefinitionId + "]����ɹ�!").toJson();
        } else {
            return this.json.returnMsg("false", "����������ʹ���!").toJson();
        }
    }

    /**
     * TODO �����б�ҳ��.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��1��20��    	    ����    �½�
     * </pre>
     */
    @RequestMapping("taskListUI")
    @SystemLog(tradeName = "���̹���", funCode = "system", funName = "���ظ��������б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String taskListUI() throws Exception {
        authButtons();
        setAttribute("processDefinitionKey", getParameter("processDefinitionKey"));
        return basePath + "/workflow/taskList";
    }

    /**
     * TODO ���ص�ǰ����ͼҳ��.
     *
     * @return
     * @throws Exception <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2016��2��24��    	    ����    �½�
     *                                     </pre>
     */
    @RequestMapping("imageUI")
    @SystemLog(tradeName = "���̹���", funCode = "system", funName = "���ص�ǰ����ͼҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String imageUI() throws Exception {
        try {
            String taskId = getParameter("taskId");
            String processInstanceId = getParameter("processInstanceId");
            setAttribute("taskId", taskId);
            setAttribute("processInstanceId", processInstanceId);
            //��ǰ��ڵ�����
            Map<String, Object> coordinate = workFlowService.getCurrentActivitiCoordinate(taskId);
            //������ע
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
    @SystemLog(tradeName = "���̹���", funCode = "system", funName = "���ص�ǰ����ͼҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String imageUIForPlan() throws Exception {
        try {
            String taskId = getParameter("taskId");
            String processInstanceId = getParameter("processInstanceId");
            setAttribute("taskId", taskId);
            setAttribute("processInstanceId", processInstanceId);
            //��ǰ��ڵ�����
            Map<String, Object> coordinate = workFlowService.getCurrentActivitiCoordinate(taskId);
            //������ע
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
     * TODO ���ص�ǰ����ͼҳ��.
     *
     * @return
     * @throws Exception <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2016��2��24��    	    ����    �½�
     *                                     </pre>
     */
    @RequestMapping("imageAndCommentUI")
    @SystemLog(tradeName = "���̹���", funCode = "system", funName = "���ص�ǰ����ͼҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String imageAndCommentUI() throws Exception {
        String taskId = getParameter("taskId");
        String processInstanceId = getParameter("processInstanceId");
        setAttribute("taskId", taskId);
        setAttribute("processInstanceId", processInstanceId);
        //��ǰ��ڵ�����
        List<Comment> comments = workFlowService.getInstanceComments(processInstanceId);

         setAttribute("comments",  BocoUtils.translateComments(comments,""));
        Map<String, Object> coordinate = workFlowService.getCurrentActivitiCoordinate(taskId);
        setAttribute("coordinate", coordinate);
        return basePath + "/workflow/imageAndComment";
    }



//    @RequestMapping("imageSubmitUI")
//    @SystemLog(tradeName = "���̹���", funCode = "system", funName = "���ص�ǰ����ͼҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
//    public String imageSubmitUI() throws Exception {
//        String processInstanceId = getParameter("processInstanceId");
//        //��ǰ��ڵ�����
//        Map<String, Object> coordinate = workFlowService.getCurrentActivitiCoordinateByProcessInstanceId(processInstanceId);
//        Task task = workFlowService.getTaskByProcessInstanceId(processInstanceId);
//        setAttribute("taskId", task.getId());
//        setAttribute("processInstanceId", processInstanceId);
//        setAttribute("coordinate", coordinate);
//        return basePath + "/workflow/image";
//    }

    /**
     * TODO �����б�����.
     *
     * @param pager
     * @return
     * @throws Exception <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2016��2��22��    	    ����    �½�
     *                                     </pre>
     */
    @RequestMapping("findTaskPage")
    @SystemLog(tradeName = "���̹���", funCode = "system", funName = "���ظ��������б�����", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findTaskPage(Pager pager) throws Exception {
        String assignee = getSessionUser().getOpercode();////��¼�û�
        String processDefinitionKey = getParameter("processDefinitionKey");//�Ӳ˵����������̶���Key
        List<Map<String, Object>> rows = workFlowService.getPersonalTaskPage(processDefinitionKey, pager);
        Map<String, Object> results = new Hashtable<String, Object>();
        results.put("pager.pageNo", pager.getPageNo());
        results.put("pager.totalRows", workFlowService.getPersonalTaskCount(processDefinitionKey, assignee));
        results.put("rows", rows);
        return JsonUtils.toJson(results);
    }

    /**
     * TODO �鿴ҵ���.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2016��2��22��    	    ����    �½�
     *                                     </pre>
     */
    @RequestMapping("viewTaskForm")
    @SystemLog(tradeName = "���̹���", funCode = "system", funName = "����FormKey��ȡ�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public String viewTaskForm(String taskId) throws Exception {
        //ͨ��taskId��ȡfromKey
        String formKey = workFlowService.getTaskFormKeyByTaskId(taskId);
        if (StringUtils.isBlank(formKey)) {
            throw new SystemException("���̽ڵ�FormKeyδ����!");
        }
        //ͨ��taskId��ȡҵ��key
        String businessKey = workFlowService.getBusinessKeyByTaskId(taskId);
        if (StringUtils.isBlank(businessKey)) {
            throw new SystemException("���ݱ�ʶΪ��!");
        }
        //ƴ��ҵ�����ַ
        String formUrl = formKey + "?businessKey=" + businessKey + "&taskId=" + taskId;
        return "redirect:" + formUrl;
    }

    /**
     * TODO �ڵ�����.
     *
     * @param taskId
     * @return
     * @throws Exception <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2016��2��22��    	    ����    �½�
     *                                     </pre>
     */
    @RequestMapping("completeTask")
    @SystemLog(tradeName = "���̹���", funCode = "system", funName = "�ڵ�����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String completeTask(String taskId, String comment, String isAgree, String assignee) throws Exception {
        Map<String, Object> varMap = new HashMap<String, Object>();
        varMap.put("isAgree", getParameter("isAgree"));
        varMap.put("assignee", getParameter("assignee"));
        workFlowService.completeTask(taskId, comment, varMap);
        return this.json.returnMsg("true", "�����ɹ�!").toJson();
    }

    /**
     * TODO ��ȡ�������̻�ڵ�.
     *
     * @param
     * @return
     * @throws Exception <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2016��6��14��    	   ������  �½�
     *                                     </pre>
     */
    @RequestMapping("getTaskIdByProcDefId")
    @SystemLog(tradeName = "��ȡ�������̻�ڵ�", funCode = "ACT-01-05", funName = "��ȡ�������̻�ڵ�", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getTaskIdByProcDefId(String procDefId) throws Exception {
        Map<String, Object> resultMap = workFlowService.getTaskIdByProcDefId(procDefId);
        return JsonUtils.toJson(resultMap);
    }

    /**
     * TODO��ȡ��Ʒ����������״̬
     *
     * @return
     * @throws Exception <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2016��9��6��    	   ������  �½�
     *                                     </pre>
     */
    @RequestMapping("getProdStatus")
    @SystemLog(tradeName = "��ȡ�޶�ƽ̨��������״̬", funCode = "ACT-01-05", funName = "��ȡ��Ʒ����״̬", accessType = AccessType.READ, level = Debug.DEBUG)
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
     * TODO ��ѯ��ɫ��Ϣ
     *
     * @return
     * @throws Exception <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2016��6��14��    	   ������  �½�
     *                                     </pre>
     */
    @RequestMapping("getTaskRole")
    @SystemLog(tradeName = "��ȡ�������̻�ڵ�", funCode = "ACT-01-05", funName = "��ȡ��ɫ��Ϣ", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getTaskRole() throws Exception {
        Map<String, Object> resultMap = workFlowService.getTaskRole();
        return JsonUtils.toJson(resultMap);
    }

    /**
     * TODO��ʼ����������.
     *
     * @param processDefinitionId
     * @param key
     * @return
     * @throws Exception <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2016��10��15��    	   ������  �½�
     *                                     </pre>
     */
    @RequestMapping("nodeConfig")
    @SystemLog(tradeName = "��ʼ����������", funCode = "ACT-01-07", funName = "��ʼ����������", accessType = AccessType.WRITE, level = Debug.INFO)
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
        return json.returnMsg("true", "��ʼ������ɣ�").toJson();
    }
}
