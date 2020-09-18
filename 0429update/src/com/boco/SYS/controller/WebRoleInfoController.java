package com.boco.SYS.controller;

import com.boco.PM.service.IWebRoleFunService;
import com.boco.PM.service.IWebRoleInfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebMenuInfo;
import com.boco.SYS.entity.WebRoleFun;
import com.boco.SYS.entity.WebRoleInfo;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.FdOperMapper;
import com.boco.SYS.mapper.WebRoleInfoMapper;
import com.boco.SYS.service.IWebMenuInfoService;
import com.boco.TONY.utils.CalendarUtil;
import com.boco.SYS.util.Json;
import com.boco.SYS.util.TreeNode;
import com.boco.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.*;

/**
 * WebRoleInfoAction控制层
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/webRoleInfo/")
public class WebRoleInfoController extends BaseController {
    @Autowired
    private IWebRoleInfoService webRoleInfoService;
    @Autowired
    private IWebMenuInfoService webMenuInfoService;
    @Autowired
    private IWebRoleFunService webRoleFunService;
    @Autowired
    private FdOperMapper fdOperMapper;
    @Autowired
    private WebRoleInfoMapper webRoleInfoMapper;

    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "角色管理", funCode = "PM-02", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PM/webRoleInfo/webRoleInfoList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "角色管理", funCode = "PM-02-04", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.INFO)
    public String infoUI(WebRoleInfo webRoleInfo) throws Exception {
        tradeUI(webRoleInfo, "info");
        WebRoleInfo webRoleInfoRes = webRoleInfoService.selectByPK(webRoleInfo.getRoleCode());
        webRoleInfoRes.setLatestModifyTime(CalendarUtil.getCalendarStringTimeFormat(webRoleInfoRes.getLatestModifyTime()));
        webRoleInfoRes.setLatestModifyDate(CalendarUtil.getCalendarStringDateFormat(webRoleInfoRes.getLatestModifyDate()));
        setAttribute("webRoleInfoDTO", webRoleInfoRes);
        return basePath + "/PM/webRoleInfo/webRoleInfoInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "角色管理", funCode = "PM-02-01", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        tradeUI(new WebRoleInfo(), "insert");
        return basePath + "/PM/webRoleInfo/webRoleInfoAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "角色管理", funCode = "PM-02-02", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(WebRoleInfo webRoleInfo) throws Exception {
        tradeUI(webRoleInfo, "update");
        setAttribute("webRoleInfoDTO", webRoleInfoService.selectByPK(webRoleInfo.getRoleCode()));
        return basePath + "/PM/webRoleInfo/webRoleInfoEdit";
    }

    /**
     * TODO 查询WEB_ROLE_INFO分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "角色管理", funCode = "PM-02", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findPage(HttpServletRequest request, WebRoleInfo webRoleInfo) throws Exception {
        setPageParam();
        //排序功能

        //排序字段
        String sort = request.getParameter("sort");
        //asc desc
        String direction = request.getParameter("direction");
        List<WebRoleInfo> list = new ArrayList<>();
        if (sort != null) {
            if ("roleCode".equals(sort)) {
                sort = "role_code";
            } else if ("roleName".equals(sort)) {
                sort = "role_name";
            } else if ("latestModifyDate".equals(sort)) {
                sort = "latest_modify_date";
            }else if ("latestModifyTime".equals(sort)) {
                sort = "latest_modify_time";
            }else if ("latestOperCode".equals(sort)) {
                sort = "latest_oper_code";
            }
            sort = sort + " " + direction;
            webRoleInfo.setSortColumn(sort);
            list = webRoleInfoService.selectByLikeOrder(webRoleInfo);
        }else {
            list = webRoleInfoService.selectByLike(webRoleInfo);
        }
        for (WebRoleInfo webRoleInfo1:list) {
            String opername = fdOperMapper.selectOperName(webRoleInfo1.getLatestOperCode());
            webRoleInfo1.setLatestOperCode(opername);
        }
        return pageData(list);
    }

    @RequestMapping(value = "findPageByOrganLevel", method = RequestMethod.POST)
    @SystemLog(tradeName = "角色管理", funCode = "PM-02", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findPageByOrganLevel(HttpServletRequest request) throws Exception {
        InputStream inputStream = WebRoleInfoController.class.getClassLoader().getResourceAsStream("resource/ignoreWebRoleInfo.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String organLevel = request.getParameter("organLevel");
        String organLevelZero = properties.getProperty("OrganLevelZero");
        String organLevelOne = properties.getProperty("OrganLevelOne");
        String organLevelTwo = properties.getProperty("OrganLevelTwo");
        FdOper fdOper = getSessionUser();
        String operCode = fdOper.getOpercode();
        List<WebRoleInfo> list = null;
        try {
            if (operCode.equals("20132364701")) {
                list = webRoleInfoMapper.selectBySuperAdmin(organLevel);
            }else {
                if (organLevel.equals("0")) {
                    list=webRoleInfoMapper.selectByOrganLevelZero(organLevelZero);
                } else if (organLevel.equals("1")) {
                    list=webRoleInfoMapper.selectByOrganLevelOne(organLevelOne);
                }else if (organLevel.equals("2")) {
                    list=webRoleInfoMapper.selectByOrganLevelTwo(organLevelTwo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData(list);
    }

    /**
     * TODO 新增WEB_ROLE_INFO.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "角色管理", funCode = "PM-02-01", funName = "新增WebRoleInfo", accessType = AccessType.WRITE, level = Debug.INFO)
    @ResponseBody
    public String insert(WebRoleInfo webRoleInfo, HttpServletRequest request) throws Exception {
        String funCodes = request.getParameter("funCodes");
        FdOper fdOper = getSessionUser();
        Json json = new Json();
        if (funCodes.contains("PM-12") && !fdOper.getOperdegree().contains("001")) {
            return json.returnMsg("false", "不是高级管理员，不能设置密码重置权限！").toJson();
        }
        try {
            json = webRoleInfoService.InsertWebRoleInfo(webRoleInfo, fdOper, funCodes);
        } catch (Exception e) {
            return json.returnMsg("false", "新增角色失败，请更换角色编码后重试").toJson();
        }
        return json.toJson();
    }

    /**
     * TODO 修改WEB_ROLE_INFO.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "角色管理", funCode = "PM-02-02", funName = "修改WebRoleInfo", accessType = AccessType.WRITE, level = Debug.INFO)
    @ResponseBody
    public String update(WebRoleInfo webRoleInfo, HttpServletRequest request) throws Exception {
        String funCodes = request.getParameter("funCodes");
        FdOper fdOper = getSessionUser();
        Json json = new Json();
        if (funCodes.contains("PM-12") && !fdOper.getOperdegree().contains("001")) {
            return json.returnMsg("false", "不是高级管理员，不能设置密码重置权限！").toJson();
        }
        json = webRoleInfoService.updateWebRoleInfo(webRoleInfo, fdOper, funCodes);
        return json.toJson();
    }

    /**
     * TODO 删除WEB_ROLE_INFO
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "deleteWebRoleInfo")
    @SystemLog(tradeName = "角色管理", funCode = "PM-02-03", funName = "删除WebRoleInfo", accessType = AccessType.WRITE, level = Debug.INFO)
    @ResponseBody
    public String deleteWebRoleInfo(WebRoleInfo webRoleInfo) throws Exception {
        Json json = webRoleInfoService.deleteWebRoleInfo(webRoleInfo);
        return json.toJson();
    }

    /**
     * TODO 获取权限配置信息.
     *
     * @param webRoleInfo
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                 修改日期        修改人    修改原因
     *                                                                                                                                                 2016年2月22日    	  杜旭    新建
     *                                                                                                                                                 </pre>
     */
    public void tradeUI(WebRoleInfo webRoleInfo, String method) throws Exception {
        //功能集合
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        //所有功能集合
        WebMenuInfo webMenuInfo = new WebMenuInfo();
        webMenuInfo.setMenuStatus("1");
        List<WebMenuInfo> funList = webMenuInfoService.selectByAttr(webMenuInfo);
        if (method.equals("insert")) {
            //获取功能集合
            for (WebMenuInfo node : funList) {
                TreeNode treeNode = convert(node);
                treeNodes.add(treeNode);
            }
        } else if (method.equals("update") || method.equals("info")) {
            WebRoleFun webRoleFun = new WebRoleFun();
            webRoleFun.setRoleCode(webRoleInfo.getRoleCode());
            //选中角色拥有的功能集合
            List<WebRoleFun> roleFunList = webRoleFunService.selectByAttr(webRoleFun);

            for (WebMenuInfo node : funList) {
                TreeNode treeNode = convert(node);
                for (WebRoleFun roleFun : roleFunList) {
                    if (StringUtils.equals(node.getMenuNo(), roleFun.getFunCode())) {
                        treeNode.setChecked(true);
                    }
                }
                //查看详细时，禁用复选框
                if (method.equals("info")) {
                    treeNode.setChkDisabled(true);
                }
                treeNodes.add(treeNode);
            }
        }
        setAttribute("treeNodes", JsonUtils.toJson(treeNodes));
        setAttribute("roleCode", webRoleInfo.getRoleCode());
    }

    /**
     * TODO 菜单对象转换为QUI树节点对象.
     *
     * @param menu
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年12月7日    	    杨滔    新建
     * </pre>
     * @throws Exception
     */
    public TreeNode convert(WebMenuInfo menu) throws Exception {
        TreeNode node = new TreeNode();
        node.setChecked(false);
        node.setChkDisabled(false);
        node.setClickExpand(true);
        if ("1".equals(menu.getIsParent())) {
            node.setIconClose(getRequest().getContextPath() + "/libs/icons/folderclosed.gif");
            node.setIconOpen(getRequest().getContextPath() + "/libs/icons/folderopen.gif");
        } else {
            node.setIcon(getRequest().getContextPath() + "/libs/icons/" + menu.getMenuIcon());
        }
        node.setId(menu.getMenuNo());
        node.setTarget("frmright");
        node.setName(menu.getMenuName());
        node.setParentId(menu.getUpMenuNo());
        node.setOpen(false);
        node.setExisted(true);
        return node;
    }


    /**
     * TODO 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29     秦海洲      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectRoleCode")
    @SystemLog(tradeName = "角色管理", funCode = "PM-02", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectRoleCode(WebRoleInfo webRoleInfo, HttpServletRequest request) throws Exception {
        String roleCode = getParameter("roleCode").replace("'","");
        if (roleCode != null) {
            webRoleInfo.setRoleCode(roleCode);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Set<String> set = new TreeSet<String>();
        List<Map<String, String>> webOperInfoList = webRoleInfoService.selectRoleCode(webRoleInfo);
        for (Map<String, String> deptInfo : webOperInfoList) {
            String data = deptInfo.get("role_code");
            set.add(data);
        }
        for (String data : set) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        String json = JsonUtils.toJson(resultMap);
        return json;
    }

    /**
     * TODO 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29     秦海洲      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectRoleName")
    @SystemLog(tradeName = "角色管理", funCode = "PM-02", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectRoleName(WebRoleInfo webRoleInfo, HttpServletRequest request) throws Exception {
        String roleName = getParameter("roleName").replace("'","");
        if (roleName != null) {
            webRoleInfo.setRoleName(roleName);
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Set<String> set = new TreeSet<String>();
        List<Map<String, String>> webOperInfoList = webRoleInfoService.selectRoleName(webRoleInfo);
        for (Map<String, String> deptInfo : webOperInfoList) {
            String data = deptInfo.get("role_name");
            set.add(data);
        }
        for (String data : set) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        String json = JsonUtils.toJson(resultMap);
        return json;
    }
}