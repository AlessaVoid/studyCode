package com.boco.SYS.base;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.entity.*;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.CommonMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.service.IGfErrInfoService;
import com.boco.SYS.service.IWebLogInfoService;
import com.boco.SYS.service.IWebMenuInfoService;
import com.boco.SYS.util.*;
import com.boco.util.JsonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 基础Controller.
 *
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月4日    	    杨滔    新建
 * </pre>
 */
public class BaseController {
    //日志输出对象
    protected Logger logger = Logger.getLogger(super.getClass());
    //页面根路径
    protected String basePath = "/system";
    @Autowired
    private IWebMenuInfoService webMenuInfoService;
    @Autowired
    private IGfErrInfoService gfErrInfoService;
    @Autowired
    private IWebLogInfoService webLogInfoService;
    @Autowired
    private IFdBusinessDateService fdBusinessDateService;
    @Autowired
    private CommonMapper commonMapper;
    FdOrgan fdOrgan = null;// 登录机构信息
    WebOperInfo operInfo = null;// 登录柜员基本信息
    protected Json json = getJson();


    /**
     * TODO 获取页面变量.
     *
     * @param key
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月1日    	    杨滔    新建
     * </pre>
     */
    protected String getParameter(String key) throws Exception {
        return getRequest().getParameter(key);
    }

    /**
     * TODO 设置Request域值.
     *
     * @param key
     * @param value <pre>
     *              修改日期        修改人    修改原因
     *              2016年2月1日    	    杨滔    新建
     *              </pre>
     */
    protected void setAttribute(String key, Object value) throws Exception {
        getRequest().setAttribute(key, value);
    }

    /**
     * TODO 获取HttpServletRequest.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月1日    	    杨滔    新建
     * </pre>
     */
    protected HttpServletRequest getRequest() throws Exception {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * TODO 获取HttpServletResponse.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月22日    	    杨滔    新建
     * </pre>
     */
    protected HttpServletResponse getResponse() throws Exception {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * TODO 获取Session.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月1日    	    杨滔    新建
     * </pre>
     */
    protected HttpSession getSession() throws Exception {
        return getRequest().getSession();
    }

    protected String getCurrentDate() throws Exception {
        return fdBusinessDateService.getCommonDateDetails();
    }

    /**
     * TODO 将页面的分页参数封装到PageHelper插件(紧跟着的第一个select方法会被分页).
     *
     *
     * <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月5日    	    杨滔    新建
     * </pre>
     */
    protected void setPageParam() throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //起始页码
        int pageNum = Integer.valueOf(request.getParameter("pageNo"));
        //每页显示条数
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));
        //排序字段
        String orderBy = request.getParameter("sort") + " " + request.getParameter("direction");
        //是否查总数，默认查询总数count
        boolean count = true;
        //启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
        //禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据
        boolean reasonable = true;
        //设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果
        //（相当于没有执行分页查询，但是返回结果仍然是Page类型）
        boolean pageSizeZero = true;
        //PageHelper.startPage(pageNum, pageSize, orderBy);
        PageHelper.startPage(pageNum, pageSize, count, reasonable, pageSizeZero);
    }

    /**
     * TODO 将PageHelper插件的数据封装成页面识别的分页数据.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月5日    	    杨滔    新建
     * </pre>
     */
    protected String pageData(List<?> list) throws Exception {
        //返回页面的分页数据
        Map<String, Object> results = new Hashtable<String, Object>();
        if (list == null) {
            results.put("pager.pageNo", 1);
            results.put("pager.totalRows", 0);
            results.put("rows", new ArrayList<>());
            return JsonUtils.toJson(results);
        }
        //用PageInfo对结果进行包装
        PageInfo<?> page = new PageInfo(list);
        results.put("pager.pageNo", page.getPageNum());
        results.put("pager.totalRows", page.getTotal());
        results.put("rows", list);
        return JsonUtils.toJson(results);
    }

    /**
     * TODO 将PageHelper插件的数据封装成页面识别的分页数据.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月5日    	    杨滔    新建
     * </pre>
     */
    protected String pageDataPage(List<?> list, Pager pager) throws Exception {
        //返回页面的分页数据
        Map<String, Object> results = new Hashtable<String, Object>();
        if (list == null) {
            results.put("pager.pageNo", 1);
            results.put("pager.totalRows", 0);
            results.put("rows", new ArrayList<Object>());
            return JsonUtils.toJson(results);
        }
        //用PageInfo对结果进行包装
        PageInfo<?> page = new PageInfo(list);
        results.put("pager.pageNo", page.getPageNum());
        results.put("pager.totalRows", pager.getTotalPages());
        results.put("rows", list);
        return JsonUtils.toJson(results);
    }

    /**
     * TODO 获取登录账户.
     *
     * @param
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月9日    	    杨滔    新建
     * </pre>
     * @throws Exception
     */
    public FdOper getSessionUser() throws Exception {
        FdOper oper = null;
        try {
            oper = (FdOper) getRequest().getSession().getAttribute("sessionUser");
        } catch (Exception e) {
            //获取Session信息失败!
            throw new SystemException("w113", e);
        }
        return oper;
    }

    /**
     * TODO 获取登录机构信息.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月20日    	    杨滔    新建
     * </pre>
     * @throws Exception
     */
    public FdOrgan getSessionOrgan() throws Exception {
        FdOrgan organ = null;
        try {
            organ = (FdOrgan) getRequest().getSession().getAttribute("sessionOrgan");
        } catch (Exception e) {
            //获取Session信息失败!
            throw new SystemException("w113", e);
        }
        return organ;
    }

    /**
     * TODO 查询某个操作员拥有权限的按钮.
     *
     * @param  <pre>
     *                修改日期        修改人    修改原因
     *                2015年11月17日    	    杨滔    新建
     *                </pre>
     */
    //@SystemLog(tradeName="系统功能",funCode="system" ,funName="加载页面按钮",logfunType=AccessType.READ,level=Debug.INFO)
    protected void authButtons() throws Exception {
        logger.debug("=========================加载页面按钮开始=========================");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String menuNo = getParameter("menuNo");
        List<WebMenuInfo> btnList = webMenuInfoService.selectBtnByAttr(getSessionUser().getOperdegrees(), menuNo);
        String btnHtml = "";
        if (btnList.size() > 0) {
            for (WebMenuInfo menu : btnList) {
                if (!menu.getMenuName().contains("复核")) {
                    btnHtml += "{line : true}," + menu.getMenuUrl() + ",";
                }
            }
        }
        btnHtml += "{line : true}";
        request.setAttribute("btnList", btnHtml);
        logger.debug("=========================加载页面按钮结束=========================");
    }

    /**
     * TODO 根据错误码获取错误信息.
     *
     * @return
     * @throws Exception <pre>
     *                   修改日期        修改人    修改原因
     *                   2016年3月11日    	     代策    新建
     *                   </pre>
     */
    public String getErrorInfo(String errorCode) throws Exception {
        GfErrInfo gfErrInfo = new GfErrInfo();
        gfErrInfo.setGfRetCode(errorCode);
        gfErrInfo.setGfSysCode("99370000000");
        gfErrInfo.setOtherSysCode("99370000000");
        gfErrInfo.setOtherRetCode(errorCode);
        GfErrInfo errInfo = gfErrInfoService.selectByPK(MapUtil.beanToMap(gfErrInfo));
        if (errInfo != null) {
            return errInfo.getGfRetInfo();
        } else {
            return "没有找到对应错误信息，错误码：" + errorCode;
        }
    }

    /**
     * TODO 根据错误码获取错误信息.
     *
     * @return
     * @throws Exception <pre>
     *                   修改日期        修改人    修改原因
     *                   2016年3月11日    秦海洲    新建
     *                   </pre>
     */
    public String getErrorInfo(String errorCode, String code) throws Exception {
        GfErrInfo gfErrInfo = new GfErrInfo();
        gfErrInfo.setGfRetCode(errorCode);
        gfErrInfo.setGfSysCode("99370000000");
        gfErrInfo.setOtherSysCode("99370000000");
        gfErrInfo.setOtherRetCode(errorCode);
        GfErrInfo errInfo = gfErrInfoService.selectByPK(MapUtil.beanToMap(gfErrInfo));
        if (errInfo != null) {
            return errInfo.getGfRetInfo().replace("*", code);
        } else {
            return "没有找到对应错误信息，错误码：" + errorCode;
        }
    }

    /**
     * TODO 获取json对象.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年4月12日    	     代策    新建
     * </pre>
     */
    public Json getJson() {

        return Json.getInstance();
    }

    /**
     * TODO 获取登录用户基本信息.
     *
     * @return
     * @throws Exception <pre>
     *                   修改日期        修改人    修改原因
     *                   2016年11月10日    	   谷立羊  新建
     *                   </pre>
     */
    public WebOperInfo getSessionOperInfo() throws Exception {
        WebOperInfo operInfo = null;
        try {
            operInfo = (WebOperInfo) getRequest().getSession().getAttribute("sessionOperInfo");
        } catch (Exception e) {
            //获取Session信息失败!
            throw new SystemException("w113", e);
        }
        return operInfo;
    }

    /**
     * TODO 插入日志表
     *
     * @param targetName
     * @param methodName
     * @param consume    耗时
     * @param retult     结果
     * @throws Exception <pre>
     *                   修改日期        修改人    修改原因
     *                   2016年11月10日    	   谷立羊  新建
     *                   </pre>
     */
    public void insertLog(FdOper fdOper, String targetName, String methodName, String consume, String retult) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("runningMethod", targetName + "." + methodName + "()");
        map.put("arguments", "");
        try {
            @SuppressWarnings("rawtypes")
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    map.put("tradeName", method.getAnnotation(SystemLog.class).tradeName());
                    map.put("funCode", method.getAnnotation(SystemLog.class).funCode());
                    map.put("funName", method.getAnnotation(SystemLog.class).funName());
                    map.put("accessType", method.getAnnotation(SystemLog.class).accessType());
                    map.put("level", method.getAnnotation(SystemLog.class).level());
                }
            }
        } catch (ClassNotFoundException e) {
            logger.error("获取方法日志注解出错:", e);
            throw new SystemException("w104", e);
        }
        WebLogInfo sysLog = new WebLogInfo();
        fdOrgan = getSessionOrgan();
        operInfo = getSessionOperInfo();
        String ip = IPUtil.getReqestIpAddr();
        if (StringUtils.isBlank(ip)) {
            ip = "获取失败";
        }
        sysLog.setThreadCode(Thread.currentThread().getName());
        sysLog.setTradeDate(BocoUtils.getNowDate());
        sysLog.setTradeTime(BocoUtils.getNowTime());
        sysLog.setOrganCode(fdOper == null ? "获取失败" : fdOper.getOrgancode() == null ? "---" : fdOper.getOrgancode());
        sysLog.setOrganName(fdOrgan == null ? "获取失败" : fdOrgan.getOrganname() == null ? "---" : fdOrgan.getOrganname());
        sysLog.setOperCode(fdOper == null ? "获取失败" : fdOper.getOpercode() == null ? "---" : fdOper.getOpercode());
        sysLog.setOperName(fdOper == null ? "获取失败" : fdOper.getOpername() == null ? "---" : fdOper.getOpername());
        sysLog.setModuleName(map.get("tradeName") + "-" + map.get("funName"));
        sysLog.setModuleCode(map.get("funCode") + "");
        sysLog.setRunningMethod(map.get("runningMethod") + "");
        sysLog.setRequestIp(ip);
        sysLog.setRunningResult(retult);
        sysLog.setArgu(map.get("arguments").toString());
        sysLog.setServiceCode(IPUtil.getLocalHostIpAddr());
        sysLog.setTradeConsumingTime(consume + "");
        sysLog.setId(BocoUtils.getNowDate() + BocoUtils.getNowTime() + commonMapper.getNextId());
        try {
            webLogInfoService.insertEntity(sysLog);
        } catch (Exception ex) {
            //记录日志出错!
            throw new SystemException("w102", ex);
        }
    }
}
