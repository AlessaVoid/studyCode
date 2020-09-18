package com.boco.PM.controller;

import com.boco.PM.service.*;
import com.boco.PM.service.impl.WebOperInfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.FdOperMapper;
import com.boco.SYS.mapper.WebOperInfoMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.service.impl.FdBusinessDateService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;
import com.boco.SYS.util.MapUtil;
import com.boco.SYS.util.StringUtil;
import com.boco.TONY.utils.PasswordEncryptHelper;
import com.boco.util.JsonUtils;
import com.jcraft.jsch.Session;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebEndpoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * FdOperAction���Ʋ�
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/fdOper/")
public class FdOperController extends BaseController {
    @Autowired
    private IFdOperService fdOperService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private IWebRoleInfoService webRoleInfoService;
    @Autowired
    private IWebRoleFunService webRoleFunService;
    @Autowired
    private IWebSublicenseInfoService webSublicenseInfoService;
    @Autowired
    private HashMap<String, Object> sessionMap;
    @Autowired
    private FdOperMapper fdOperMapper;
    @Autowired
    private IWebOperInfoService webOperInfoService;
    @Autowired
    private IFdBusinessDateService fdBusinessDateService;
    @Autowired
    private WebOperInfoMapper webOperInfoMapper;

    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("listUI")
    @SystemLog(tradeName = "����Աά��", funCode = "PM-01", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PM/fdOper/fdOperList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "����Աά��", funCode = "PM-01-04", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.INFO)
    public String infoUI(FdOper fdOper) throws Exception {
        setAttribute("fdOper", fdOperService.selectByPK(MapUtil.beanToMap(fdOper)));
        return basePath + "/PM/fdOper/fdOperInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "����Աά��", funCode = "PM-01-01", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/PM/fdOper/fdOperEdit";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "����Աά��", funCode = "PM-01-02", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.INFO)
    public String updateUI(FdOper fdOper) throws Exception {
        setAttribute("fdOper", fdOperService.selectByPK(MapUtil.beanToMap(fdOper)));
        return basePath + "/PM/fdOper/fdOperEdit";
    }

    @RequestMapping("qzSignoutUI")
    @SystemLog(tradeName = "��Աǿ��ǩ��", funCode = "PM-15", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.INFO)
    public String qzSignoutUI(FdOper fdOper, HttpServletRequest request) throws Exception {
		/*HttpSession session = getSession();
		String FileName = "";
		Object name = session.getAttribute("sessionUser");
		System.out.println(name);
		Enumeration enu = session.getAttributeNames();
		String[] names = session.getValueNames();
		for(int i=0;i<names.length;i++){
			if(names[i].equals("sessionUser")){
				FileName += session.getValue(names[i])+"@";
				System.out.println(FileName);
			}
		}*/
        fdOper = getSessionUser();
        setAttribute("opercode", fdOper.getOpercode());
        setAttribute("opername", fdOper.getOpername());

        return basePath + "/PM/fdOper/fdOperQzSignout";
    }

    @RequestMapping("operUpdatePwdUI")
    @SystemLog(tradeName = "��Ա�����޸�", funCode = "PM-13", funName = "���ع�Ա�����޸�ҳ��", accessType = AccessType.READ, level = Debug.INFO)
    public String operUpdatePwdUI(FdOper fdOper) throws Exception {
        return basePath + "/PM/fdOper/FdOperUpdatePwd";
    }

    @RequestMapping("operRePwdUI")
    @SystemLog(tradeName = "��Ա��������", funCode = "PM-12", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.INFO)
    public String operRePwdUI(FdOper fdOper) throws Exception {
        return basePath + "/PM/fdOper/FdOperRePwd";
    }

    @RequestMapping("operReOrganUI")
    @SystemLog(tradeName = "��Ա�����޸�", funCode = "PM-13", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.INFO)
    public String operReOrganUI(FdOper fdOper) throws Exception {
        return basePath + "/PM/fdOper/FdOperReOrgan";
    }

    /**
     * TODO ��ѯFD_OPER��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "����Աά��", funCode = "PM-01", funName = "�����б�����", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findPage(FdOper fdOper) throws Exception {
        setPageParam();
        List<FdOper> list = fdOperService.selectByAttr(fdOper);
        return pageData(list);
    }

    /**
     * TODO ����FD_OPER.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "����Աά��", funCode = "PM-01-01", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(FdOper fdOper) throws Exception {
        fdOperService.insertEntity(fdOper);
        return this.json.returnMsg("true", "�����ɹ�!").toJson();
    }

    /**
     * TODO �޸�FD_OPER.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "����Աά��", funCode = "PM-01-02", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(FdOper fdOper) throws Exception {
        fdOperService.updateByPK(fdOper);
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }

    /**
     * TODO ɾ��FD_OPER
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "����Աά��", funCode = "PM-01-03", funName = "ɾ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(FdOper fdOper) throws Exception {
        fdOperService.deleteByPK(MapUtil.beanToMap(fdOper));
        return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
    }

    /**
     * TODO �������������ѯ����Ա��Ϣ���������ڲ���Ա�����������json��.
     *
     * @param request ��ѯ����ʽΪ funno=**** ���磺funno=FD-01
     * @return json����ʽΪ{"list":[{"value":"����ԱA�ı��","key":"����ԱA"},{"value":"����ԱB�ı��"
     * ,"key":"����ԱB"}]}
     * @throws Exception <pre>
     *                                                                                                                                                                   �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                   2016��3��7��    	  ����    �½�
     *                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "getAppUserList")
    @SystemLog(tradeName = "����Աά��", funCode = "system", funName = "��ȡ������Ա��Ϣ", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getAppUserList(HttpServletRequest request, HttpSession session) throws Exception {
        String funCode = request.getParameter("funCode");
        Map<String, List<Map<String, String>>> result = fdOperService.getAppUserList(funCode, session);
        return JsonUtils.toJson(result);
    }

    /**
     * @param request
     * @param session
     * @return ��ȡ���и�����Ա
     * @throws Exception
     */
    @RequestMapping(value = "getHeadOfficeAppUserList")
    @SystemLog(tradeName = "����Աά��", funCode = "system", funName = "��ȡ���и�����Ա��Ϣ", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getHeadOfficeAppUserList(HttpServletRequest request, HttpSession session) throws Exception {
        String funCode = request.getParameter("funCode");
        Map<String, List<Map<String, String>>> result = fdOperService.getHeadOfficeAppUserList(funCode, session);
        return JsonUtils.toJson(result);
    }

    /**
     * TODO �������̻�ȡ������Ա��Ϣ.
     *
     * @param request
     * @param session
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                   �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                   2016��4��5��    	  ����    �½�
     *                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "getAppUserListByRole")
    @SystemLog(tradeName = "����Աά��", funCode = "system", funName = "��ȡ������Ա��Ϣ", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getAppUserListByRole(HttpServletRequest request, HttpSession session) throws Exception {
        String funCode = request.getParameter("funCode");
        String roleCode = request.getParameter("roleCode");
        Map<String, List<Map<String, String>>> result = fdOperService.getAppUserListByRole(funCode, roleCode, getSessionOrgan().getThiscode());
        return JsonUtils.toJson(result);
    }

    /**
     * TODO ������Ա��Ų�ѯ��Ա��Ϣ��������Ϣ����ɫ��Ϣ.
     *
     * @param request
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                   �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                   2016��3��8��    	  ����    �½�
     *                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "getUserInfo")
    @SystemLog(tradeName = "����Աά��", funCode = "system", funName = "������Ա��Ų�ѯ��Ա�ͻ�����Ϣ", accessType = AccessType.READ, level = Debug.DEBUG)
    @ResponseBody
    public String getUserInfo(HttpServletRequest request) throws Exception {
        String userNo = request.getParameter("userNo");
        Map map = fdOperService.getUserInfo(userNo);
        String json = JsonUtils.toJson(map);
        return json;
    }

    /**
     * �����µĹ�Ա��
     *
     * @param request
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                   �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                   2016��3��8��    	  ����    �½�
     *                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "newOpercode")
    @SystemLog(tradeName = "����Աά��", funCode = "system", funName = "�����µĹ�Ա��", accessType = AccessType.READ, level = Debug.DEBUG)
    @ResponseBody
    public String newOpercode(HttpServletRequest request) throws Exception {
        String userNo = request.getParameter("userNo");
        String newOrganCode = request.getParameter("newOrganCode");
        FdOper fdOper = new FdOper();
        fdOper.setOpercode(newOrganCode.substring(0, 2));
        List<FdOper> fdOpers = null;
        try {
            fdOpers = fdOperMapper.selectByLikeStartAttr(fdOper);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        int num = 1;
        for (FdOper oper : fdOpers) {
            if (oper.getOpercode().startsWith(newOrganCode.substring(0, 2)) && oper.getOpercode().length() > 11) {
                num++;
            }
        }
        String number = null;
        if (num < 10) {
            number = "00" + num;
        }
        if (9 < num && num < 100) {
            number = "0" + num;
        }
        if (99 < num && num < 1000) {
            number = "" + num;
        }
//        Random random = new Random();
//        String randomNum = random.nextInt(900) + 100 + "";
        Map map = new HashMap();
        String newOperCode = newOrganCode.substring(0, 2) + number + userNo;
        map.put("newOperCode", newOperCode);
        String json = JsonUtils.toJson(map);
        return json;
    }


    /**
     * TODO �����޸�
     *
     * @param fdOper
     * @param newPwd
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                   �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                   2016��10��26��    	   ������  �½�
     *                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "operUpdatePwd")
    @SystemLog(tradeName = "����Աά��", funCode = "PM-13", funName = "�û������޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String operUpdatePwd() throws Exception {
        FdOper sessionUser = getSessionUser();
        String operpassword = getParameter("operpassword");
        String password = getParameter("password");
        //��ԭ�������������м���
//		MD5 md5 = new MD5();
//		String salt = "dhjdfu34i34u34-zmew8732dfhjd-"+sessionUser.getOpercode()+"dfhjdf8347sdhxcye-ehjcbeww34";
//		operpassword = md5.get32MD5ofStr(operpassword+"{"+salt+"}");
//		password = md5.get32MD5ofStr(password+"{"+salt+"}");
        if (StringUtil.isEquals(password, operpassword)) {
            this.json.returnMsg("false", "�����벻����ԭ�����ظ�");
            return json.toJson();
        }
        if (!StringUtil.isEquals(sessionUser.getOperpassword(), operpassword)) {
            this.json.returnMsg("false", "ԭ�����������");
        } else {
            //��ȡ��Ӧ��Ϣ
            Map resultMap = fdOperService.operUpdatePwd(sessionUser, password);
            String respcodeInfo = (String) resultMap.get("RESPINFO");
            if ("00000000".equals(resultMap.get("RESPCODE")) || "000000".equals(resultMap.get("RESPCODE"))) {
                this.json.returnMsg("true", "�����޸ĳɹ�");
                //�޸�session���������
                sessionUser.setOperpassword(password);
                getSession().setAttribute("sessionUser", sessionUser);
            } else {
                this.json.returnMsg("false", respcodeInfo);
            }
        }
        return json.toJson();
    }

    /**
     * TODO �����޸�
     *
     * @param fdOper
     * @param reOrgan
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                   �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                   2016��10��26��    	   ������  �½�
     *                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "operUpdateOrgan")
    @SystemLog(tradeName = "����Աά��", funCode = "PM-30", funName = "�û������޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String operUpdateOrgan(HttpSession session) throws Exception {
        String operCode = getParameter("userNo");
        String newOperCode = getParameter("newOperCode");
        FdOper fdOper = new FdOper();
        fdOper.setOpercode(operCode);
        String newOrganCode = getParameter("newOrganCode");
        if (fdOrganService.selectByOrganCode(newOrganCode).size() == 0) {
            this.json.returnMsg("false", "�û���������");
        } else {
            Map resultMap = fdOperService.operUpdateOrgan(fdOper, newOrganCode);
            String respcodeInfo = (String) resultMap.get("RESPINFO");
            if (respcodeInfo == null) {
                FdOper newFdOper = new FdOper();
                String newPassword = PasswordEncryptHelper.encrypt(newOperCode, "000000");
                newFdOper.setOpercode(newOperCode);
                newFdOper.setOrgancode(newOrganCode);
                newFdOper.setOperpassword(newPassword);
                newFdOper.setOpername(fdOperMapper.selectOperName(operCode));
                List<FdOper> fdOperlist = fdOperMapper.selectByAttr(newFdOper);
                WebOperInfo webOperInfo = new WebOperInfo();
                webOperInfo.setOperCode(newFdOper.getOpercode());
                webOperInfo.setOrganCode(newFdOper.getOrgancode());
                List<WebOperInfo> webOperInfoList = webOperInfoMapper.selectByAttr(webOperInfo);
                Map insertResultMap = new HashMap();
                Json insert = new Json();
                if (fdOperlist.size() == 0) {
                    insertResultMap = fdOperService.insertNewOper(newFdOper);
                } else {
                    this.json.returnMsg("false", "��Աͬ�������Ѵ��ڸù�Ա");
                }
                if (webOperInfoList.size() == 0) {
                    webOperInfo.setOperName(newFdOper.getOpername());
                    FdOper fdOper1 = (FdOper) session.getAttribute("sessionUser");
                    webOperInfo.setLatestOperCode(fdOper1.getOpercode());
                    //��ǰ���ڡ���ǰʱ��
                    String latestModifyDate = fdBusinessDateService.getCommonDateDetails();
                    webOperInfo.setLatestModifyDate(latestModifyDate);
                    webOperInfo.setLatestModifyTime(BocoUtils.getNowTime());
                    insert = webOperInfoService.insert(webOperInfo);
                } else {
                    this.json.returnMsg("false", "��Ա��Ϣ�����Ѵ��ڸù�Ա");
                }
                String respcodeInfoInsertResult = (String) resultMap.get("RESPINFO");
                if (("00000000".equals(insertResultMap.get("RESPCODE")) || "000000".equals(insertResultMap.get("RESPCODE"))) && insert.getSuccess().equals("true")) {
                    this.json.returnMsg("true", "��Աת�ڳɹ�!");
                } else {
                    this.json.returnMsg("false", respcodeInfoInsertResult);
                }
            } else {
                this.json.returnMsg("false", respcodeInfo);
            }
        }
        return json.toJson();
    }

    /**
     * TODO ��������
     *
     * @param fdOper
     * @param newPwd
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                   �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                   2016��10��26��    	   ������  �½�
     *                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "operRePwd")
    @SystemLog(tradeName = "����Աά��", funCode = "PM-12", funName = "�û���������", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String operRePwd(FdOper fdOper) throws Exception {
        FdOper sessionUser = getSessionUser();

//        if (!sessionUser.getOperdegree().contains("001")&&!sessionUser.getOperdegree().contains("010")) {
//            return this.json.returnMsg("false", "�Ǹ߼�����Ա�����������룡").toJson();
//        }
        if (sessionUser.getOrgancode().equals("11005293")) {
            Map operInfo = fdOperService.getUserInfo(fdOper.getOpercode());
            if (operInfo.isEmpty()) {
                return this.json.returnMsg("false", "�����ڴ˹�Ա��").toJson();
            }
            Map resultMap = fdOperService.operRePwd(sessionUser, fdOper);
            //��ȡ��Ӧ��Ϣ
            String respcodeInfo = (String) resultMap.get("RESPINFO");
            if ("00000000".equals(resultMap.get("RESPCODE")) || "000000".equals(resultMap.get("RESPCODE"))) {
                this.json.returnMsg("true", "�û��������óɹ�");
                getSession().setAttribute("sessionUser", sessionUser);
            } else {
                this.json.returnMsg("false", respcodeInfo);
            }
        } else {
            Map operInfo = fdOperService.getUserInfo(fdOper.getOpercode());
            if (operInfo.isEmpty()) {
                return this.json.returnMsg("false", "�����ڴ˹�Ա��").toJson();
            }
            if (!operInfo.get("organCode").equals(sessionUser.getOrgancode())) {
                return this.json.returnMsg("false", "ֻ�����ñ������¹�Ա���룡").toJson();
            }
            Map resultMap = fdOperService.operRePwd(sessionUser, fdOper);
            //��ȡ��Ӧ��Ϣ
            String respcodeInfo = (String) resultMap.get("RESPINFO");
            if ("00000000".equals(resultMap.get("RESPCODE")) || "000000".equals(resultMap.get("RESPCODE"))) {
                this.json.returnMsg("true", "�û��������óɹ�");
                getSession().setAttribute("sessionUser", sessionUser);
            } else {
                this.json.returnMsg("false", respcodeInfo);
            }
        }
        return json.toJson();
    }
}
