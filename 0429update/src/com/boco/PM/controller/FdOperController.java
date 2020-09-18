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
 * FdOperAction控制层
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
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

    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "操作员维护", funCode = "PM-01", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PM/fdOper/fdOperList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "操作员维护", funCode = "PM-01-04", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.INFO)
    public String infoUI(FdOper fdOper) throws Exception {
        setAttribute("fdOper", fdOperService.selectByPK(MapUtil.beanToMap(fdOper)));
        return basePath + "/PM/fdOper/fdOperInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "操作员维护", funCode = "PM-01-01", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/PM/fdOper/fdOperEdit";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "操作员维护", funCode = "PM-01-02", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.INFO)
    public String updateUI(FdOper fdOper) throws Exception {
        setAttribute("fdOper", fdOperService.selectByPK(MapUtil.beanToMap(fdOper)));
        return basePath + "/PM/fdOper/fdOperEdit";
    }

    @RequestMapping("qzSignoutUI")
    @SystemLog(tradeName = "柜员强制签退", funCode = "PM-15", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.INFO)
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
    @SystemLog(tradeName = "柜员密码修改", funCode = "PM-13", funName = "加载柜员密码修改页面", accessType = AccessType.READ, level = Debug.INFO)
    public String operUpdatePwdUI(FdOper fdOper) throws Exception {
        return basePath + "/PM/fdOper/FdOperUpdatePwd";
    }

    @RequestMapping("operRePwdUI")
    @SystemLog(tradeName = "柜员密码重置", funCode = "PM-12", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.INFO)
    public String operRePwdUI(FdOper fdOper) throws Exception {
        return basePath + "/PM/fdOper/FdOperRePwd";
    }

    @RequestMapping("operReOrganUI")
    @SystemLog(tradeName = "柜员机构修改", funCode = "PM-13", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.INFO)
    public String operReOrganUI(FdOper fdOper) throws Exception {
        return basePath + "/PM/fdOper/FdOperReOrgan";
    }

    /**
     * TODO 查询FD_OPER分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "操作员维护", funCode = "PM-01", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findPage(FdOper fdOper) throws Exception {
        setPageParam();
        List<FdOper> list = fdOperService.selectByAttr(fdOper);
        return pageData(list);
    }

    /**
     * TODO 新增FD_OPER.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "操作员维护", funCode = "PM-01-01", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(FdOper fdOper) throws Exception {
        fdOperService.insertEntity(fdOper);
        return this.json.returnMsg("true", "新增成功!").toJson();
    }

    /**
     * TODO 修改FD_OPER.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "操作员维护", funCode = "PM-01-02", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(FdOper fdOper) throws Exception {
        fdOperService.updateByPK(fdOper);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }

    /**
     * TODO 删除FD_OPER
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "操作员维护", funCode = "PM-01-03", funName = "删除", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(FdOper fdOper) throws Exception {
        fdOperService.deleteByPK(MapUtil.beanToMap(fdOper));
        return this.json.returnMsg("true", "删除成功!").toJson();
    }

    /**
     * TODO 根据请求参数查询操作员信息，返回用于操作员生产下拉框的json串.
     *
     * @param request 查询串格式为 funno=**** 例如：funno=FD-01
     * @return json串格式为{"list":[{"value":"操作员A的编号","key":"操作员A"},{"value":"操作员B的编号"
     * ,"key":"操作员B"}]}
     * @throws Exception <pre>
     *                                                                                                                                                                   修改日期        修改人    修改原因
     *                                                                                                                                                                   2016年3月7日    	  杜旭    新建
     *                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "getAppUserList")
    @SystemLog(tradeName = "操作员维护", funCode = "system", funName = "获取复核人员信息", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getAppUserList(HttpServletRequest request, HttpSession session) throws Exception {
        String funCode = request.getParameter("funCode");
        Map<String, List<Map<String, String>>> result = fdOperService.getAppUserList(funCode, session);
        return JsonUtils.toJson(result);
    }

    /**
     * @param request
     * @param session
     * @return 获取总行复核人员
     * @throws Exception
     */
    @RequestMapping(value = "getHeadOfficeAppUserList")
    @SystemLog(tradeName = "操作员维护", funCode = "system", funName = "获取总行复核人员信息", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getHeadOfficeAppUserList(HttpServletRequest request, HttpSession session) throws Exception {
        String funCode = request.getParameter("funCode");
        Map<String, List<Map<String, String>>> result = fdOperService.getHeadOfficeAppUserList(funCode, session);
        return JsonUtils.toJson(result);
    }

    /**
     * TODO 复杂流程获取审批人员信息.
     *
     * @param request
     * @param session
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                   修改日期        修改人    修改原因
     *                                                                                                                                                                   2016年4月5日    	  杜旭    新建
     *                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "getAppUserListByRole")
    @SystemLog(tradeName = "操作员维护", funCode = "system", funName = "获取复核人员信息", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getAppUserListByRole(HttpServletRequest request, HttpSession session) throws Exception {
        String funCode = request.getParameter("funCode");
        String roleCode = request.getParameter("roleCode");
        Map<String, List<Map<String, String>>> result = fdOperService.getAppUserListByRole(funCode, roleCode, getSessionOrgan().getThiscode());
        return JsonUtils.toJson(result);
    }

    /**
     * TODO 根据人员编号查询人员信息，机构信息，角色信息.
     *
     * @param request
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                   修改日期        修改人    修改原因
     *                                                                                                                                                                   2016年3月8日    	  杜旭    新建
     *                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "getUserInfo")
    @SystemLog(tradeName = "操作员维护", funCode = "system", funName = "根据人员编号查询柜员和机构信息", accessType = AccessType.READ, level = Debug.DEBUG)
    @ResponseBody
    public String getUserInfo(HttpServletRequest request) throws Exception {
        String userNo = request.getParameter("userNo");
        Map map = fdOperService.getUserInfo(userNo);
        String json = JsonUtils.toJson(map);
        return json;
    }

    /**
     * 生成新的柜员号
     *
     * @param request
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                   修改日期        修改人    修改原因
     *                                                                                                                                                                   2016年3月8日    	  杜旭    新建
     *                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "newOpercode")
    @SystemLog(tradeName = "操作员维护", funCode = "system", funName = "生成新的柜员号", accessType = AccessType.READ, level = Debug.DEBUG)
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
     * TODO 密码修改
     *
     * @param fdOper
     * @param newPwd
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                   修改日期        修改人    修改原因
     *                                                                                                                                                                   2016年10月26日    	   谷立羊  新建
     *                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "operUpdatePwd")
    @SystemLog(tradeName = "操作员维护", funCode = "PM-13", funName = "用户密码修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String operUpdatePwd() throws Exception {
        FdOper sessionUser = getSessionUser();
        String operpassword = getParameter("operpassword");
        String password = getParameter("password");
        //将原密码和新密码进行加密
//		MD5 md5 = new MD5();
//		String salt = "dhjdfu34i34u34-zmew8732dfhjd-"+sessionUser.getOpercode()+"dfhjdf8347sdhxcye-ehjcbeww34";
//		operpassword = md5.get32MD5ofStr(operpassword+"{"+salt+"}");
//		password = md5.get32MD5ofStr(password+"{"+salt+"}");
        if (StringUtil.isEquals(password, operpassword)) {
            this.json.returnMsg("false", "新密码不能与原密码重复");
            return json.toJson();
        }
        if (!StringUtil.isEquals(sessionUser.getOperpassword(), operpassword)) {
            this.json.returnMsg("false", "原密码输入错误");
        } else {
            //获取相应信息
            Map resultMap = fdOperService.operUpdatePwd(sessionUser, password);
            String respcodeInfo = (String) resultMap.get("RESPINFO");
            if ("00000000".equals(resultMap.get("RESPCODE")) || "000000".equals(resultMap.get("RESPCODE"))) {
                this.json.returnMsg("true", "密码修改成功");
                //修改session里面的密码
                sessionUser.setOperpassword(password);
                getSession().setAttribute("sessionUser", sessionUser);
            } else {
                this.json.returnMsg("false", respcodeInfo);
            }
        }
        return json.toJson();
    }

    /**
     * TODO 机构修改
     *
     * @param fdOper
     * @param reOrgan
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                   修改日期        修改人    修改原因
     *                                                                                                                                                                   2016年10月26日    	   谷立羊  新建
     *                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "operUpdateOrgan")
    @SystemLog(tradeName = "操作员维护", funCode = "PM-30", funName = "用户机构修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String operUpdateOrgan(HttpSession session) throws Exception {
        String operCode = getParameter("userNo");
        String newOperCode = getParameter("newOperCode");
        FdOper fdOper = new FdOper();
        fdOper.setOpercode(operCode);
        String newOrganCode = getParameter("newOrganCode");
        if (fdOrganService.selectByOrganCode(newOrganCode).size() == 0) {
            this.json.returnMsg("false", "该机构不存在");
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
                    this.json.returnMsg("false", "人员同步表中已存在该柜员");
                }
                if (webOperInfoList.size() == 0) {
                    webOperInfo.setOperName(newFdOper.getOpername());
                    FdOper fdOper1 = (FdOper) session.getAttribute("sessionUser");
                    webOperInfo.setLatestOperCode(fdOper1.getOpercode());
                    //当前日期、当前时间
                    String latestModifyDate = fdBusinessDateService.getCommonDateDetails();
                    webOperInfo.setLatestModifyDate(latestModifyDate);
                    webOperInfo.setLatestModifyTime(BocoUtils.getNowTime());
                    insert = webOperInfoService.insert(webOperInfo);
                } else {
                    this.json.returnMsg("false", "人员信息表中已存在该柜员");
                }
                String respcodeInfoInsertResult = (String) resultMap.get("RESPINFO");
                if (("00000000".equals(insertResultMap.get("RESPCODE")) || "000000".equals(insertResultMap.get("RESPCODE"))) && insert.getSuccess().equals("true")) {
                    this.json.returnMsg("true", "人员转岗成功!");
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
     * TODO 密码重置
     *
     * @param fdOper
     * @param newPwd
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                   修改日期        修改人    修改原因
     *                                                                                                                                                                   2016年10月26日    	   谷立羊  新建
     *                                                                                                                                                                   </pre>
     */
    @RequestMapping(value = "operRePwd")
    @SystemLog(tradeName = "操作员维护", funCode = "PM-12", funName = "用户密码重置", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String operRePwd(FdOper fdOper) throws Exception {
        FdOper sessionUser = getSessionUser();

//        if (!sessionUser.getOperdegree().contains("001")&&!sessionUser.getOperdegree().contains("010")) {
//            return this.json.returnMsg("false", "非高级管理员不得重置密码！").toJson();
//        }
        if (sessionUser.getOrgancode().equals("11005293")) {
            Map operInfo = fdOperService.getUserInfo(fdOper.getOpercode());
            if (operInfo.isEmpty()) {
                return this.json.returnMsg("false", "不存在此柜员！").toJson();
            }
            Map resultMap = fdOperService.operRePwd(sessionUser, fdOper);
            //获取相应信息
            String respcodeInfo = (String) resultMap.get("RESPINFO");
            if ("00000000".equals(resultMap.get("RESPCODE")) || "000000".equals(resultMap.get("RESPCODE"))) {
                this.json.returnMsg("true", "用户密码重置成功");
                getSession().setAttribute("sessionUser", sessionUser);
            } else {
                this.json.returnMsg("false", respcodeInfo);
            }
        } else {
            Map operInfo = fdOperService.getUserInfo(fdOper.getOpercode());
            if (operInfo.isEmpty()) {
                return this.json.returnMsg("false", "不存在此柜员！").toJson();
            }
            if (!operInfo.get("organCode").equals(sessionUser.getOrgancode())) {
                return this.json.returnMsg("false", "只能重置本机构下柜员密码！").toJson();
            }
            Map resultMap = fdOperService.operRePwd(sessionUser, fdOper);
            //获取相应信息
            String respcodeInfo = (String) resultMap.get("RESPINFO");
            if ("00000000".equals(resultMap.get("RESPCODE")) || "000000".equals(resultMap.get("RESPCODE"))) {
                this.json.returnMsg("true", "用户密码重置成功");
                getSession().setAttribute("sessionUser", sessionUser);
            } else {
                this.json.returnMsg("false", respcodeInfo);
            }
        }
        return json.toJson();
    }
}
