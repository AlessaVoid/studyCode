package com.boco.SYS.controller;

import com.boco.PM.service.IWebReviewMainService;
import com.boco.PM.service.IWebReviewSublistService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.WebReviewMain;
import com.boco.SYS.entity.WebReviewSublist;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Action控制层
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/webReviewMain/")
public class WebReviewMainController extends BaseController {
    @Autowired
    private IWebReviewMainService webReviewMainService;
    @Autowired
    private IWebReviewSublistService webReviewSublistService;


    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "待办列表", funCode = "PM-17", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PM/webReviewMain/webReviewMainList";
    }

    //访问列表、详细信息、新增、修改页面
    @RequestMapping("historyListUI")
    @SystemLog(tradeName = "历史记录", funCode = "PM-16", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String historyListUI() throws Exception {
        authButtons();
        return basePath + "/PM/webReviewMain/webReviewMainHistoryList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "历史记录", funCode = "PM-16-02", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.INFO)
    public String infoUI(WebReviewMain webReviewMain) throws Exception {
        setAttribute("webReviewMainDTO", webReviewMainService.selectByPK(webReviewMain.getAppNo()));
        return basePath + "/PM/webReviewMain/webReviewMainInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "交易名称", funCode = "system", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/webReviewMain/webReviewMainEdit";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "system", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(WebReviewMain webReviewMain) throws Exception {
        setAttribute("webReviewMain", webReviewMainService.selectByPK(webReviewMain.getAppNo()));
        return basePath + "/webReviewMain/webReviewMainEdit";
    }

    /**
     * TODO 查询WEB_REVIEW_MAIN分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "待办列表", funCode = "PM-17", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.INFO)
    public @ResponseBody
    String findPage(WebReviewMain webReviewMain, HttpServletRequest request) throws Exception {
        String opercode = getSessionUser().getOpercode();//操作员代码
        webReviewMain.setRepUserCode(opercode);//取自己复核的列表
        webReviewMain.setRepStatus("1");//取待复核
        webReviewMain.setRevocationFlag("2");//取非撤销状态的申请记录
        webReviewMain.setSortColumn("APP_DATE DESC,APP_TIME DESC");
        setPageParam();
        List<WebReviewMain> list = webReviewMainService.selectByAttr(webReviewMain);
        return pageData(list);
    }

    /**
     * TODO 查询复核历史信息列表.
     *
     * @param
     * @param
     * @param request
     * @return
     * @throws Exception <pre>
     *                   修改日期        修改人    修改原因
     *                   2016年2月25日    	  杜旭    新建
     *                   </pre>
     */
    @RequestMapping(value = "approvalHistoryList", method = RequestMethod.POST)
    @SystemLog(tradeName = "历史记录", funCode = "PM-16", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.INFO)
    @ResponseBody
    public String approvalHistoryList(WebReviewMain webReviewMain, HttpServletRequest request) throws Exception {
        String opercode = getSessionUser().getOpercode();//操作员代码
        String queryType = request.getParameter("queryType") == null ? "" : request.getParameter("queryType");
        if (queryType.equals("0")) {
            webReviewMain.setAppUser(opercode);//取自己申请的列表
        } else if (queryType.equals("1")) {
            webReviewMain.setRepUserCode(opercode);//取自己复核的列表
        } else {
            webReviewMain.setDefaultList(opercode);//默认查询该用户申请和复核的列表
        }
        webReviewMain.setSortColumn("APP_DATE DESC,APP_TIME DESC");
        setPageParam();
        List<WebReviewMain> list = webReviewMainService.selectByAttr(webReviewMain);
        return pageData(list);
    }

    /**
     * TODO 净值维护数据列表.
     *
     * @param request
     * @return
     * @throws Exception <pre>
     *                   修改日期        	修改人    修改原因
     *                   2017年1月3日    张兴帅    新建
     *                   </pre>
     */
    @RequestMapping(value = "NetvalueInfoList")
    @SystemLog(tradeName = "待办列表", funCode = "system", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.INFO)
    public String NetvalueInfoList(HttpServletRequest request) throws Exception {
        return basePath + "/PM//webReviewMain/NetvalueInfoList";
    }

    /**
     * TODO 根据Id查询复核信息表.
     *
     * @param request
     * @return
     * @throws Exception <pre>
     *                   修改日期        修改人    修改原因
     *                   2016年2月25日    	  杜旭    新建
     *                   </pre>
     */
    @RequestMapping(value = "seachWebReviewMain")
    @SystemLog(tradeName = "待办列表", funCode = "system", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.INFO)
    public String seachApproval(HttpServletRequest request) throws Exception {
        String urlType = request.getParameter("urlType");
        WebReviewMain webReviewMain = new WebReviewMain();
        String appNo = request.getParameter("appNo");//获取复核编号
        webReviewMain = webReviewMainService.selectByPK(appNo);//根据编号查找复核信息

        StringBuffer appData = new StringBuffer();
        //pmjnl_approval_sub表里app_data字段多条记录存储一个编号的复核数据，将数据按orderno拼接
        List<WebReviewSublist> list = webReviewSublistService.selectAppNo(webReviewMain.getAppNo());
        for (WebReviewSublist sub : list) {
            appData.append(sub.getAppData());
        }
        if ("[".equals(appData.substring(0, 1))) {
            Map<String, String> appDataMap = new HashMap<String, String>();
            appDataMap.put("gridData", appData.toString());
            request.setAttribute("appDataMap", appDataMap);//添加或删除的数据，或修改后的数据
        } else {
            Map<String, String> appDataMap = JsonUtils.ToMap(appData.toString());//增、删的数据转成Map格式
            request.setAttribute("appDataMap", appDataMap);//添加或删除的数据，或修改后的数据

        }
        Map<String, String> oldDataMap = JsonUtils.ToMap(webReviewMain.getOldData());//如果是修改操作，修改前的数据转成Map格式

        request.setAttribute("oldDataMap", oldDataMap);//修改前的数据
        request.setAttribute("webReviewMain", webReviewMain);//将复核编号传到页面，用作在处理复核页面调用复核处理公共方法
        if ("check".equals(urlType)) {
            return webReviewMain.getAppUrl();//复核页面
        } else {
            //重编辑时把0，1转换成add,update与新增，修改页面值一致，保证到Action类里判断新增修改的方式不变
            if ("0".equals(webReviewMain.getAppType())) {
                request.setAttribute("type", "insert");
            } else if ("1".equals(webReviewMain.getAppType())) {
                request.setAttribute("type", "update");
            }
            return webReviewMain.getReeditUrl();//重新编辑页面
        }
    }

    /**
     * TODO 根据Id查询复核信息表.
     *
     * @param request
     * @return
     * @throws Exception <pre>
     *                   修改日期        修改人    修改原因
     *                   2016年2月25日    	  杜旭    新建
     *                   </pre>
     */
    @RequestMapping(value = "seachWebReviewMainInfo", method = RequestMethod.POST)
    @SystemLog(tradeName = "历史记录", funCode = "PM-16", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public String approvalInfo(HttpServletRequest request) throws Exception {
        WebReviewMain webReviewMain = new WebReviewMain();
        String appNo = request.getParameter("appNo");//获取复核编号
        webReviewMain = webReviewMainService.selectByPK(appNo);//根据编号查找复核
        request.setAttribute("webReviewMain", webReviewMain);//将复核编号传到页面，用作在处理复核页面调用复核处理公共方法
        return "../system/PM/webReviewMain/webReviewMainInfo";
    }

    /**
     * TODO 修改复核状态,撤销或关闭.
     *
     * @param request
     * @return
     * @throws Exception <pre>
     *                   修改日期        修改人    修改原因
     *                   2016年2月25日    	  杜旭    新建
     *                   </pre>
     */
    @RequestMapping(value = "updateWebReviewMainInfo", method = RequestMethod.POST)
    @SystemLog(tradeName = "历史记录", funCode = "PM-16", funName = "加载列表数据", accessType = AccessType.WRITE, level = Debug.INFO)
    @ResponseBody
    public String updateWebReviewMainInfo(HttpServletRequest request) throws Exception {
        String appNo = request.getParameter("appNo");//审批编号
        String type = request.getParameter("type");//修改类型，revocation:撤销;close:关闭
        json = webReviewMainService.updateWebReviewMain(appNo, type);
        return json.toJson();
    }

    /**
     * TODO 新增WEB_REVIEW_MAIN.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "交易名称", funCode = "system", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(WebReviewMain webReviewMain) throws Exception {
        webReviewMainService.insertEntity(webReviewMain);
        return this.json.returnMsg("true", "新增成功!").toJson();
    }

    /**
     * TODO 修改WEB_REVIEW_MAIN.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "交易名称", funCode = "system", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(WebReviewMain webReviewMain) throws Exception {
        webReviewMainService.updateByPK(webReviewMain);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }

    /**
     * TODO 删除WEB_REVIEW_MAIN
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "交易名称", funCode = "system", funName = "删除", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(WebReviewMain webReviewMain) throws Exception {
        webReviewMainService.deleteByPK(webReviewMain.getAppNo());
        return this.json.returnMsg("true", "删除成功!").toJson();
    }

    /**
     * TODO 获取代办信息提示
     *
     * @return
     * @throws Exception <pre>
     *                   修改日期        修改人    修改原因
     *                   2016年12月9日    	   谷立羊  新建
     *                   </pre>
     */
    @RequestMapping(value = "getReviewMsgs")
    @SystemLog(tradeName = "交易名称", funCode = "system", funName = "代办信息提示", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getReviewMsgs() throws Exception {
        List<WebReviewMain> list = webReviewMainService.getReviewMsgs();
        return this.json.returnMsg("true", JsonUtils.toJson(list)).toJson();
    }

    /**
     * TODO 获取净值维护提示信息
     *
     * @return
     * @throws Exception <pre>
     *                   修改日期        修改人   	 修改原因
     *                   2016年12月29日    张兴帅  新建
     *                   </pre>
     */
    @RequestMapping(value = "getLeftReviewMsgs")
    @SystemLog(tradeName = "交易名称", funCode = "system", funName = "净值维护信息提示", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String getLeftReviewMsgs() throws Exception {
//		List<GfProdNetvalueInfo> list = gfProdNetvalueInfoService.getTips();
        return this.json.returnMsg("true", JsonUtils.toJson("")).toJson();
    }

//    /**
//     * TODO 获取汇率维护提示信息
//     *
//     * @return
//     * @throws Exception <pre>
//     *                   修改日期        修改人   	 修改原因
//     *                   2016年12月29日    张兴帅  新建
//     *                   </pre>
//     */
//    @RequestMapping(value = "getExchangeRate")
//    @SystemLog(tradeName = "交易名称", funCode = "system", funName = "汇率维护信息提示", accessType = AccessType.READ, level = Debug.DEBUG)
//    public @ResponseBody
//    String getExchangeRate() throws Exception {
//        List<GfProdBaseInfo> list = webReviewMainService.getExchangeRate();
//        return this.json.returnMsg("true", JsonUtils.toJson(list)).toJson();
//    }
}