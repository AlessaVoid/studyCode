package com.boco.SYS.controller;

import com.boco.PM.service.IFdOperService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PM.service.IWebOperInfoService;
import com.boco.PM.service.IWebRoleInfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.cache.PromptMsgCache;
import com.boco.SYS.entity.*;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.FdOperMapper;
import com.boco.SYS.service.IGfDictService;
import com.boco.SYS.service.IWebMenuInfoService;
import com.boco.TONY.biz.weboper.service.IWebOperRoleService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.DataProcess;
import com.boco.SYS.util.HttpRequestClient;
import com.boco.SYS.util.IPUtil;
import com.boco.util.JsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��¼Controller.
 *
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2015��12��2��    	    ����    �½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/")
public class LoginController extends BaseController {
    @Autowired
    private IFdOperService fdOperService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private IWebRoleInfoService webRoleInfoService;
    @Autowired
    private IWebMenuInfoService webMenuInfoService;
    @Autowired
    private IWebOperInfoService webOperInfoService;
    @Autowired
    private HashMap<String, Object> sessionMap;
    @Autowired
    private IGfDictService gfDictService;
    @Autowired
    IWebOperRoleService webOperRoleService;
    @Autowired
    FdOperMapper fdOperMapper;
    @Autowired
    public final static Logger logger = Logger.getLogger(LoginController.class);

    /**
     * TODO ���ص�¼ҳ.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��30��    	    ����    �½�
     * </pre>
     */
    @SystemLog(tradeName = "ϵͳ����", funCode = "system", funName = "���ص�¼ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    @RequestMapping(value = "toLogin", method = RequestMethod.GET)
    public String login() {
        return "/system/login/login";
    }

    @SystemLog(tradeName = "ϵͳ����", funCode = "system", funName = "���ص�¼ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    @RequestMapping(value = "qiantui", method = RequestMethod.GET)
    public @ResponseBody
    String loginOut() throws Exception {
        HashMap<String, Object> resultMap = null;
        try {
            String doOperCode = getParameter("operCode");
            String flagCode = "";
            resultMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : sessionMap.entrySet()) {
                if (entry.getKey().equals(doOperCode)) {
                    HttpSession sessionInfo = (HttpSession) entry.getValue();
                    sessionInfo.removeAttribute("sessionUser");
                    sessionInfo.removeAttribute("sessionOrgan");
                    sessionInfo.removeAttribute("sessionUserRole");
                    sessionInfo.invalidate();
                    flagCode = entry.getKey();
                }
                if (StringUtils.isNotBlank(flagCode)) {
                    sessionMap.remove(flagCode);
                    resultMap.put("success", "true");
                    resultMap.put("msg", "ע���ɹ�");
                    resultMap.put("localIp", IPUtil.getLocalHostIpAddr());
                    logger.info("����(" + IPUtil.getLocalHostIpAddr() + ")ע���ɹ�");
                    return json.setBean(resultMap).toJson();
                } else {
                    logger.info("����(" + IPUtil.getLocalHostIpAddr() + ")�����ڴ��û�");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultMap.put("success", "false");
        resultMap.put("msg", "ע��ʧ��");
        resultMap.put("localIp", IPUtil.getLocalHostIpAddr());

        return json.setBean(resultMap).toJson();
    }

    /**
     * TODO ��¼����.
     * 20160307   �޸��ˣ� ����    ����ˣ�����   �޸����ݣ���Աû�б�ϵͳ��ɫ��û��ת��Ȩ��ϵͳ��ɫʱ�����Ե�¼
     * 20160521  �޸��ˣ����� �ڲ�ѯÿ�����ʱ�����try..catch��䣬��������Ϣ���ص���¼ҳ�棬
     * �����¼��ʱ�������ѯ�����ˣ�ҳ��һֱͣ���ڡ����ڵ�¼��...������ҳ���Ͽ������ѳ����쳣���޸�ԭ������ȷ����ʾ
     * 20190129 czh
     * roleCodes += "'" + roleStr + "'||','||";��ΪroleCodes += "'" + roleStr + "',";
     *
     * @param fdOper ��Ա��
     * @return ��JSON
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @SystemLog(tradeName = "ϵͳ����", funCode = "system", funName = "��¼ϵͳ", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String login(FdOper fdOper, HttpSession session) throws Exception {
        FdOper loginFdOper = new FdOper();
        String opercode = fdOper.getOpercode();
        String organ = fdOperMapper.selectOpersOrgan(opercode);
        loginFdOper.setOrgancode(organ);
        loginFdOper.setOpercode(fdOper.getOpercode());
        List<FdOper> operList;
        long start = System.currentTimeMillis(), end, consume;
        try {
            operList = fdOperService.selectByAttr(loginFdOper);
            if (operList.size() != 1) {
                return json.returnMsg("false", "��Ա��Ϣ����ƥ���¼!").toJson();
            }
        } catch (Exception e) {
            logger.error("��ѯ��Ա�����:", e);
            return json.returnMsg("false", "��ѯ��Ա�����!").toJson();
        }
        List<FdOrgan> fdOrganList;
        try {
            FdOrgan fdOrgan = new FdOrgan();
            fdOrgan.setThiscode(organ);
            fdOrganList = fdOrganService.selectByAttr(fdOrgan);
            if (fdOrganList.size() != 1) {
                return json.returnMsg("false", "������Ϣ����ƥ���¼!").toJson();
            }
        } catch (Exception e) {
            logger.error("��ѯ���������:", e);
            return json.returnMsg("false", "��ѯ���������!").toJson();
        }
        try {
            HashMap<String, Object> pk = new HashMap<>();
            pk.put("organCode", fdOrganList.get(0).getThiscode());
            pk.put("operCode", operList.get(0).getOpercode());
            WebOperInfo webOperInfo = webOperInfoService.selectByPK(pk);
            session.setAttribute("sessionOperInfo", webOperInfo);//session
        } catch (Exception e) {
            logger.error("��ѯ��Ա��Ϣ�����:", e);
            return json.returnMsg("false", "��ѯ��Ա��Ϣ�����!").toJson();
        }
        boolean loginSuccess = fdOperService.operSignIn(fdOper);
        if (!loginSuccess) {
            end = System.currentTimeMillis();
            consume = end - start;
            try {
                insertLog(fdOper, LoginController.class.toString().substring(6)
                        , "login", consume + "", "��¼ʧ��,���벻��ȷ");
            } catch (Exception e) {
                logger.error("������־�����:", e);
                return json.returnMsg("false", "������־�����!").toJson();
            }
            return json.returnMsg("false", "�û����벻ƥ��!").toJson();
        }
        List<String> roleList = webOperRoleService.selectOwnRoleByOperCode(fdOper.getOpercode());
        if (CollectionUtils.isEmpty(roleList)) {
            return json.returnMsg("false", "��ɫ��Ϣ��ƥ���¼!").toJson();
        }
        StringBuilder role = new StringBuilder();
        for (String listRole : roleList) {
            role.append(listRole);
        }
        if (StringUtils.isBlank(role)) {
            return json.returnMsg("false", "��ɫ��Ϣ��ƥ���¼!").toJson();
        }
        List<String> roleListAdapter = new ArrayList<>();
        StringBuilder roleCodes = new StringBuilder();
        for (int i = 0; i < role.length(); i = i + 3) {
            String roleStr = role.substring(i, i + 3);
            roleCodes.append("'").append(roleStr).append("',");
            roleListAdapter.add(roleStr);
        }
        if (StringUtils.isNotEmpty(roleCodes.toString())) {
            roleCodes = new StringBuilder(roleCodes.substring(0, roleCodes.length() - 1));
        }
        session.setAttribute("systemDate", BocoUtils.getNowDate());
        session.setAttribute("sessionOrgan", fdOrganList.get(0));

        FdOper onlineUser = operList.get(0);
        onlineUser.setOperdegree(role.toString());
        onlineUser.setOperpassword(fdOper.getOperpassword());
        onlineUser.setOperdegrees(roleCodes.toString());
        session.setAttribute("sessionUser", onlineUser);

        try {
            String roleName = getName(roleListAdapter);
            session.setAttribute("sessionUserRole", roleName);
            System.out.println(getSessionUser().getOpercode() + "||" + getSessionUser().getOpername() + "||" + getSession());
            sessionMap.put(getSessionUser().getOpercode(), getSession());
            json.returnMsg("true", "��¼�ɹ������ڼ�����ҳ...");
        } catch (Exception e) {
            logger.error("��ѯ��ɫ��Ϣ�����:", e);
            return json.returnMsg("false", "��ѯ��ɫ��Ϣ�����!").toJson();
        }
        return json.toJson();
    }

    /**
     * TODO ��Աǩ��.
     *
     * @param session SESSION
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   2016��2��24��    	  ����    �½�
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "/exitSignout")
    @SystemLog(tradeName = "ϵͳ����", funCode = "system", funName = "��Աǩ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public String exitSignOut(HttpSession session) throws Exception {
        long start = System.currentTimeMillis(), end, consume;
        FdOper fdOper = getSessionUser();
        boolean isSignOut = fdOperService.operSignOut(fdOper);
        if (!isSignOut) {
            end = System.currentTimeMillis();
            consume = end - start;
            insertLog(fdOper, LoginController.class.toString().substring(6)
                    , "exitSignOut", consume + "", "false");
        }
        return "system/login/login";
    }

    /**
     * TODO ��Աǿ��ǩ��.
     *
     * @param request HTTP REQUEST
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   2016��2��24��    	  ����    �½�
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "/qzExitSignout", method = RequestMethod.POST)
    @SystemLog(tradeName = "ϵͳ����", funCode = "system", funName = "��Աǿ��ǩ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String qzExitSignOut(HttpServletRequest request) throws Exception {
        long start = System.currentTimeMillis(), end, consume;
        FdOper fdOper = getSessionUser();
        String doOperCode = request.getParameter("opercode1");
        if (StringUtils.isEmpty(doOperCode)) {
            return json.returnMsg("false", "�����뱻������Ա��").toJson();
        }
        FdOper doFdOper;
        FdOper loginFdOper = new FdOper();
        loginFdOper.setOrgancode(fdOper.getOrgancode());
        loginFdOper.setOpercode(fdOper.getOpercode());
        FdOper loginDFdOper = new FdOper();
        loginDFdOper.setOrgancode(fdOper.getOrgancode());
        loginDFdOper.setOpercode(doOperCode);
        List<FdOper> listFdOper;
        List<FdOper> listDoFdOper;
        try {
            listFdOper = fdOperService.selectByAttr(loginFdOper);
            listDoFdOper = fdOperService.selectByAttr(loginDFdOper);
        } catch (Exception e) {
            logger.error("��ѯ��Ա�����:", e);
            return json.returnMsg("false", "��ѯ��Ա�����!").toJson();
        }
        if (listFdOper.size() != 1 || listDoFdOper.size() != 1) {
            return json.returnMsg("false", "������Ϣ����ƥ���¼!").toJson();
        }
        Map resultMap = fdOperService.operQzSignout(fdOper, doOperCode);
        String respCodeInfo = (String) resultMap.get("RESPINFO");
        if (!"00000000".equals(resultMap.get("RESPCODE")) && !"000000".equals(resultMap.get("RESPCODE"))) {
            end = System.currentTimeMillis();
            consume = end - start;
            insertLog(fdOper, LoginController.class.toString().substring(6), "exitSignout", consume + "", respCodeInfo);
            return json.returnMsg("false", respCodeInfo).toJson();
        }
        String flagCode = "";
        for (Map.Entry<String, Object> entry : sessionMap.entrySet()) {
            System.out.println(entry.getKey() + "||" + entry.getValue());
            if (entry.getKey().equals(doOperCode)) {
                HttpSession sessionF = (HttpSession) entry.getValue();
                doFdOper = (FdOper) sessionF.getAttribute("sessionUser");
                doFdOper.setOpercode(fdOper.getOpercode());
                sessionF.removeAttribute("sessionUser");
                sessionF.removeAttribute("sessionOrgan");
                sessionF.removeAttribute("sessionUserRole");
                sessionF.invalidate();
                flagCode = entry.getKey();
            }
        }
        if (!"".equals(flagCode)) {
            sessionMap.remove(flagCode);
            logger.info(IPUtil.getLocalHostIpAddr() + "�Ƴ�session�ɹ�");
        } else {
            logger.info("����(" + IPUtil.getLocalHostIpAddr() + ")�����ڴ��û�,������ת��");
            GfDict gfDict = new GfDict();
            gfDict.setDictNo("REMOTE_SERVER_IP");
            List<GfDict> gfDictList = gfDictService.selectByAttr(gfDict);
            logger.info("��ѯ�ֵ�ֵ����" + gfDictList.size());
            for (GfDict gfDict2 : gfDictList) {
                logger.info("��ѯ�ֵ�ֵ" + gfDict2.getDictValueIn());
            }
            int serverPort = request.getServerPort();
            logger.info("����˿ں�" + serverPort);
            if (gfDictList.size() > 0) {
                logger.info("for֮ǰ");
                for (GfDict gfDictTemp : gfDictList) {
                    String dictIp = gfDictTemp.getDictValueIn().split(":")[0];
                    String dictPort = gfDictTemp.getDictValueIn().split(":")[1];
                    logger.info(dictIp + ":" + dictPort + "||" + serverPort);
                    if (IPUtil.getLocalHostIpAddr().equals(dictIp) && Integer.valueOf(dictPort) == serverPort) {
                        logger.info(IPUtil.getLocalHostIpAddr() + ":" + serverPort);
                    } else {
                        logger.info("Զ��ע�����ʵ�ַ" + "http://" + gfDictTemp.getDictValueIn() + "/web/qiantui.htm");
                        String sg = HttpRequestClient.sendGet("http://" + gfDictTemp.getDictValueIn() + "/web/qiantui.htm", "operCode=" + doOperCode);
                        logger.info("Զ��ע��������Ϣ" + sg);
                        JSONObject jn = new JSONObject(sg);
                        JSONObject bean = (JSONObject) jn.get("bean");
                        boolean successFalg = Boolean.valueOf((String) bean.get("success"));
                        if (successFalg) {
                            logger.info(bean.get("localIp") + "���ע��");
                        } else {
                            logger.info("ע��ʧ��");
                        }
                    }
                }
            }
        }
        return json.returnMsg("true", "ǿ��ǩ�˲����ɹ�").toJson();
    }

    /**
     * TODO ��ȡ��ɫ����.
     *
     * @param roleList ��ɫ�����б�
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��24��    	  ����    �½�
     * </pre>
     * @throws Exception EX
     */
    public String getName(List<String> roleList) throws Exception {
        StringBuilder roleName = new StringBuilder();
        for (String roleCode : roleList) {
            WebRoleInfo webRoleInfo = webRoleInfoService.selectByPK(roleCode);
            if (webRoleInfo != null) {
                roleName.append(webRoleInfo.getRoleName()).append(",");
            }
        }
        roleName = new StringBuilder(roleName.substring(0, roleName.length() - 1));
        return roleName.toString();
    }

    /**
     * TODO ������ҳ.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��12��18��    	    ����    �½�
     * </pre>
     * @throws Exception EX
     */
    @RequestMapping(value = "menu")
    @SystemLog(tradeName = "ϵͳ����", funCode = "system", funName = "������ҳ", accessType = AccessType.READ, level = Debug.DEBUG)
    public String index(HttpSession session, HttpServletRequest request) throws Exception {
        FdOper onlineUser = (FdOper) session.getAttribute("sessionUser");
        //��ȡ��¼�˲˵���
        List<WebMenuInfo> webMenuInfos = webMenuInfoService.selectMenuByAttr(onlineUser.getOperdegrees());
        //��ȡ��ǰ��¼����Ա���õĿ�ݲ˵�
        List<WebMenuInfo> shortMenus = webMenuInfoService.selectShortMenus(onlineUser.getOpercode());
        String dataArr = JsonUtils.toJson(webMenuInfos);
        String shortMenuDataArr = JsonUtils.toJson(shortMenus);
        request.getSession().setAttribute("menus", dataArr);
        request.getSession().setAttribute("shortMenus", shortMenuDataArr);
        setAttribute("msg", PromptMsgCache.msg);
        //��ȡ֪ͨ����
        request.setAttribute("InformNum", 1);
        //��ȡ������Ϣ����
        request.setAttribute("AppNum", 2);
        //��ȡ��������
        request.setAttribute("NoticeNum", 3);
        //ϵͳʱ��
        request.setAttribute("sysTime", DataProcess.StringFormatStringDate(BocoUtils.getNowDate(), "yyyyMMdd", "yyyy/MM/dd"));
        return "/system/layout/index";
    }

    /**
     * TODO ��ʱҳ��.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��12��25��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "timeout")
    @SystemLog(tradeName = "ϵͳ����", funCode = "system", funName = "��ʱҳ����ת", accessType = AccessType.READ, level = Debug.INFO)
    public String timeout() {
        return "/exception/timeout/timeout";
    }
}