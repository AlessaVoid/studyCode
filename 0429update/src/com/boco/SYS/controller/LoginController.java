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
 * 登录Controller.
 *
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年12月2日    	    杨滔    新建
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
     * TODO 加载登录页.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月30日    	    杨滔    新建
     * </pre>
     */
    @SystemLog(tradeName = "系统功能", funCode = "system", funName = "加载登录页面", accessType = AccessType.READ, level = Debug.DEBUG)
    @RequestMapping(value = "toLogin", method = RequestMethod.GET)
    public String login() {
        return "/system/login/login";
    }

    @SystemLog(tradeName = "系统功能", funCode = "system", funName = "加载登录页面", accessType = AccessType.READ, level = Debug.DEBUG)
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
                    resultMap.put("msg", "注销成功");
                    resultMap.put("localIp", IPUtil.getLocalHostIpAddr());
                    logger.info("本机(" + IPUtil.getLocalHostIpAddr() + ")注销成功");
                    return json.setBean(resultMap).toJson();
                } else {
                    logger.info("本机(" + IPUtil.getLocalHostIpAddr() + ")不存在此用户");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultMap.put("success", "false");
        resultMap.put("msg", "注销失败");
        resultMap.put("localIp", IPUtil.getLocalHostIpAddr());

        return json.setBean(resultMap).toJson();
    }

    /**
     * TODO 登录处理.
     * 20160307   修改人： 杜旭    提出人：代策   修改内容：柜员没有本系统角色，没有转授权本系统角色时不可以登录
     * 20160521  修改人：杨滔 在查询每个表的时候加上try..catch语句，将错误信息返回到登录页面，
     * 解决登录的时候如果查询出错了，页面一直停留在“正在登录中...”，从页面上看不出已出现异常，修改原来不明确的提示
     * 20190129 czh
     * roleCodes += "'" + roleStr + "'||','||";改为roleCodes += "'" + roleStr + "',";
     *
     * @param fdOper 柜员号
     * @return 　JSON
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @SystemLog(tradeName = "系统功能", funCode = "system", funName = "登录系统", accessType = AccessType.WRITE, level = Debug.INFO)
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
                return json.returnMsg("false", "柜员信息表无匹配记录!").toJson();
            }
        } catch (Exception e) {
            logger.error("查询柜员表出错:", e);
            return json.returnMsg("false", "查询柜员表出错!").toJson();
        }
        List<FdOrgan> fdOrganList;
        try {
            FdOrgan fdOrgan = new FdOrgan();
            fdOrgan.setThiscode(organ);
            fdOrganList = fdOrganService.selectByAttr(fdOrgan);
            if (fdOrganList.size() != 1) {
                return json.returnMsg("false", "机构信息表无匹配记录!").toJson();
            }
        } catch (Exception e) {
            logger.error("查询机构表出错:", e);
            return json.returnMsg("false", "查询机构表出错!").toJson();
        }
        try {
            HashMap<String, Object> pk = new HashMap<>();
            pk.put("organCode", fdOrganList.get(0).getThiscode());
            pk.put("operCode", operList.get(0).getOpercode());
            WebOperInfo webOperInfo = webOperInfoService.selectByPK(pk);
            session.setAttribute("sessionOperInfo", webOperInfo);//session
        } catch (Exception e) {
            logger.error("查询人员信息表出错:", e);
            return json.returnMsg("false", "查询人员信息表出错!").toJson();
        }
        boolean loginSuccess = fdOperService.operSignIn(fdOper);
        if (!loginSuccess) {
            end = System.currentTimeMillis();
            consume = end - start;
            try {
                insertLog(fdOper, LoginController.class.toString().substring(6)
                        , "login", consume + "", "登录失败,密码不正确");
            } catch (Exception e) {
                logger.error("插入日志表出错:", e);
                return json.returnMsg("false", "插入日志表出错!").toJson();
            }
            return json.returnMsg("false", "用户密码不匹配!").toJson();
        }
        List<String> roleList = webOperRoleService.selectOwnRoleByOperCode(fdOper.getOpercode());
        if (CollectionUtils.isEmpty(roleList)) {
            return json.returnMsg("false", "角色信息无匹配记录!").toJson();
        }
        StringBuilder role = new StringBuilder();
        for (String listRole : roleList) {
            role.append(listRole);
        }
        if (StringUtils.isBlank(role)) {
            return json.returnMsg("false", "角色信息无匹配记录!").toJson();
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
            json.returnMsg("true", "登录成功，正在加载首页...");
        } catch (Exception e) {
            logger.error("查询角色信息表出错:", e);
            return json.returnMsg("false", "查询角色信息表出错!").toJson();
        }
        return json.toJson();
    }

    /**
     * TODO 柜员签退.
     *
     * @param session SESSION
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   2016年2月24日    	  杜旭    新建
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "/exitSignout")
    @SystemLog(tradeName = "系统功能", funCode = "system", funName = "柜员签退", accessType = AccessType.WRITE, level = Debug.INFO)
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
     * TODO 柜员强制签退.
     *
     * @param request HTTP REQUEST
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   2016年2月24日    	  杜旭    新建
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "/qzExitSignout", method = RequestMethod.POST)
    @SystemLog(tradeName = "系统功能", funCode = "system", funName = "柜员强制签退", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String qzExitSignOut(HttpServletRequest request) throws Exception {
        long start = System.currentTimeMillis(), end, consume;
        FdOper fdOper = getSessionUser();
        String doOperCode = request.getParameter("opercode1");
        if (StringUtils.isEmpty(doOperCode)) {
            return json.returnMsg("false", "请输入被操作柜员号").toJson();
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
            logger.error("查询柜员表出错:", e);
            return json.returnMsg("false", "查询柜员表出错!").toJson();
        }
        if (listFdOper.size() != 1 || listDoFdOper.size() != 1) {
            return json.returnMsg("false", "机构信息表无匹配记录!").toJson();
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
            logger.info(IPUtil.getLocalHostIpAddr() + "移除session成功");
        } else {
            logger.info("本机(" + IPUtil.getLocalHostIpAddr() + ")不存在此用户,请求已转发");
            GfDict gfDict = new GfDict();
            gfDict.setDictNo("REMOTE_SERVER_IP");
            List<GfDict> gfDictList = gfDictService.selectByAttr(gfDict);
            logger.info("查询字典值数量" + gfDictList.size());
            for (GfDict gfDict2 : gfDictList) {
                logger.info("查询字典值" + gfDict2.getDictValueIn());
            }
            int serverPort = request.getServerPort();
            logger.info("请求端口号" + serverPort);
            if (gfDictList.size() > 0) {
                logger.info("for之前");
                for (GfDict gfDictTemp : gfDictList) {
                    String dictIp = gfDictTemp.getDictValueIn().split(":")[0];
                    String dictPort = gfDictTemp.getDictValueIn().split(":")[1];
                    logger.info(dictIp + ":" + dictPort + "||" + serverPort);
                    if (IPUtil.getLocalHostIpAddr().equals(dictIp) && Integer.valueOf(dictPort) == serverPort) {
                        logger.info(IPUtil.getLocalHostIpAddr() + ":" + serverPort);
                    } else {
                        logger.info("远程注销访问地址" + "http://" + gfDictTemp.getDictValueIn() + "/web/qiantui.htm");
                        String sg = HttpRequestClient.sendGet("http://" + gfDictTemp.getDictValueIn() + "/web/qiantui.htm", "operCode=" + doOperCode);
                        logger.info("远程注销返回信息" + sg);
                        JSONObject jn = new JSONObject(sg);
                        JSONObject bean = (JSONObject) jn.get("bean");
                        boolean successFalg = Boolean.valueOf((String) bean.get("success"));
                        if (successFalg) {
                            logger.info(bean.get("localIp") + "完成注销");
                        } else {
                            logger.info("注销失败");
                        }
                    }
                }
            }
        }
        return json.returnMsg("true", "强制签退操作成功").toJson();
    }

    /**
     * TODO 获取角色名称.
     *
     * @param roleList 角色名称列表
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月24日    	  杜旭    新建
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
     * TODO 加载首页.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年12月18日    	    杨滔    新建
     * </pre>
     * @throws Exception EX
     */
    @RequestMapping(value = "menu")
    @SystemLog(tradeName = "系统功能", funCode = "system", funName = "加载首页", accessType = AccessType.READ, level = Debug.DEBUG)
    public String index(HttpSession session, HttpServletRequest request) throws Exception {
        FdOper onlineUser = (FdOper) session.getAttribute("sessionUser");
        //获取登录人菜单集
        List<WebMenuInfo> webMenuInfos = webMenuInfoService.selectMenuByAttr(onlineUser.getOperdegrees());
        //获取当前登录操作员设置的快捷菜单
        List<WebMenuInfo> shortMenus = webMenuInfoService.selectShortMenus(onlineUser.getOpercode());
        String dataArr = JsonUtils.toJson(webMenuInfos);
        String shortMenuDataArr = JsonUtils.toJson(shortMenus);
        request.getSession().setAttribute("menus", dataArr);
        request.getSession().setAttribute("shortMenus", shortMenuDataArr);
        setAttribute("msg", PromptMsgCache.msg);
        //获取通知总数
        request.setAttribute("InformNum", 1);
        //获取待办信息总数
        request.setAttribute("AppNum", 2);
        //获取公告总数
        request.setAttribute("NoticeNum", 3);
        //系统时间
        request.setAttribute("sysTime", DataProcess.StringFormatStringDate(BocoUtils.getNowDate(), "yyyyMMdd", "yyyy/MM/dd"));
        return "/system/layout/index";
    }

    /**
     * TODO 超时页面.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年12月25日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "timeout")
    @SystemLog(tradeName = "系统功能", funCode = "system", funName = "超时页面跳转", accessType = AccessType.READ, level = Debug.INFO)
    public String timeout() {
        return "/exception/timeout/timeout";
    }
}