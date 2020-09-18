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
 * ����Controller.
 *
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2015��11��4��    	    ����    �½�
 * </pre>
 */
public class BaseController {
    //��־�������
    protected Logger logger = Logger.getLogger(super.getClass());
    //ҳ���·��
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
    FdOrgan fdOrgan = null;// ��¼������Ϣ
    WebOperInfo operInfo = null;// ��¼��Ա������Ϣ
    protected Json json = getJson();


    /**
     * TODO ��ȡҳ�����.
     *
     * @param key
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��1��    	    ����    �½�
     * </pre>
     */
    protected String getParameter(String key) throws Exception {
        return getRequest().getParameter(key);
    }

    /**
     * TODO ����Request��ֵ.
     *
     * @param key
     * @param value <pre>
     *              �޸�����        �޸���    �޸�ԭ��
     *              2016��2��1��    	    ����    �½�
     *              </pre>
     */
    protected void setAttribute(String key, Object value) throws Exception {
        getRequest().setAttribute(key, value);
    }

    /**
     * TODO ��ȡHttpServletRequest.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��1��    	    ����    �½�
     * </pre>
     */
    protected HttpServletRequest getRequest() throws Exception {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * TODO ��ȡHttpServletResponse.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��22��    	    ����    �½�
     * </pre>
     */
    protected HttpServletResponse getResponse() throws Exception {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * TODO ��ȡSession.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��1��    	    ����    �½�
     * </pre>
     */
    protected HttpSession getSession() throws Exception {
        return getRequest().getSession();
    }

    protected String getCurrentDate() throws Exception {
        return fdBusinessDateService.getCommonDateDetails();
    }

    /**
     * TODO ��ҳ��ķ�ҳ������װ��PageHelper���(�����ŵĵ�һ��select�����ᱻ��ҳ).
     *
     *
     * <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��5��    	    ����    �½�
     * </pre>
     */
    protected void setPageParam() throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //��ʼҳ��
        int pageNum = Integer.valueOf(request.getParameter("pageNo"));
        //ÿҳ��ʾ����
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));
        //�����ֶ�
        String orderBy = request.getParameter("sort") + " " + request.getParameter("direction");
        //�Ƿ��������Ĭ�ϲ�ѯ����count
        boolean count = true;
        //���ú���ʱ�����pageNum<1���ѯ��һҳ�����pageNum>pages���ѯ���һҳ
        //���ú���ʱ�����pageNum<1��pageNum>pages�᷵�ؿ�����
        boolean reasonable = true;
        //����Ϊtrueʱ�����pageSize=0����RowBounds.limit = 0�ͻ��ѯ��ȫ���Ľ��
        //���൱��û��ִ�з�ҳ��ѯ�����Ƿ��ؽ����Ȼ��Page���ͣ�
        boolean pageSizeZero = true;
        //PageHelper.startPage(pageNum, pageSize, orderBy);
        PageHelper.startPage(pageNum, pageSize, count, reasonable, pageSizeZero);
    }

    /**
     * TODO ��PageHelper��������ݷ�װ��ҳ��ʶ��ķ�ҳ����.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��5��    	    ����    �½�
     * </pre>
     */
    protected String pageData(List<?> list) throws Exception {
        //����ҳ��ķ�ҳ����
        Map<String, Object> results = new Hashtable<String, Object>();
        if (list == null) {
            results.put("pager.pageNo", 1);
            results.put("pager.totalRows", 0);
            results.put("rows", new ArrayList<>());
            return JsonUtils.toJson(results);
        }
        //��PageInfo�Խ�����а�װ
        PageInfo<?> page = new PageInfo(list);
        results.put("pager.pageNo", page.getPageNum());
        results.put("pager.totalRows", page.getTotal());
        results.put("rows", list);
        return JsonUtils.toJson(results);
    }

    /**
     * TODO ��PageHelper��������ݷ�װ��ҳ��ʶ��ķ�ҳ����.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��5��    	    ����    �½�
     * </pre>
     */
    protected String pageDataPage(List<?> list, Pager pager) throws Exception {
        //����ҳ��ķ�ҳ����
        Map<String, Object> results = new Hashtable<String, Object>();
        if (list == null) {
            results.put("pager.pageNo", 1);
            results.put("pager.totalRows", 0);
            results.put("rows", new ArrayList<Object>());
            return JsonUtils.toJson(results);
        }
        //��PageInfo�Խ�����а�װ
        PageInfo<?> page = new PageInfo(list);
        results.put("pager.pageNo", page.getPageNum());
        results.put("pager.totalRows", pager.getTotalPages());
        results.put("rows", list);
        return JsonUtils.toJson(results);
    }

    /**
     * TODO ��ȡ��¼�˻�.
     *
     * @param
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��9��    	    ����    �½�
     * </pre>
     * @throws Exception
     */
    public FdOper getSessionUser() throws Exception {
        FdOper oper = null;
        try {
            oper = (FdOper) getRequest().getSession().getAttribute("sessionUser");
        } catch (Exception e) {
            //��ȡSession��Ϣʧ��!
            throw new SystemException("w113", e);
        }
        return oper;
    }

    /**
     * TODO ��ȡ��¼������Ϣ.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��20��    	    ����    �½�
     * </pre>
     * @throws Exception
     */
    public FdOrgan getSessionOrgan() throws Exception {
        FdOrgan organ = null;
        try {
            organ = (FdOrgan) getRequest().getSession().getAttribute("sessionOrgan");
        } catch (Exception e) {
            //��ȡSession��Ϣʧ��!
            throw new SystemException("w113", e);
        }
        return organ;
    }

    /**
     * TODO ��ѯĳ������Աӵ��Ȩ�޵İ�ť.
     *
     * @param  <pre>
     *                �޸�����        �޸���    �޸�ԭ��
     *                2015��11��17��    	    ����    �½�
     *                </pre>
     */
    //@SystemLog(tradeName="ϵͳ����",funCode="system" ,funName="����ҳ�水ť",logfunType=AccessType.READ,level=Debug.INFO)
    protected void authButtons() throws Exception {
        logger.debug("=========================����ҳ�水ť��ʼ=========================");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String menuNo = getParameter("menuNo");
        List<WebMenuInfo> btnList = webMenuInfoService.selectBtnByAttr(getSessionUser().getOperdegrees(), menuNo);
        String btnHtml = "";
        if (btnList.size() > 0) {
            for (WebMenuInfo menu : btnList) {
                if (!menu.getMenuName().contains("����")) {
                    btnHtml += "{line : true}," + menu.getMenuUrl() + ",";
                }
            }
        }
        btnHtml += "{line : true}";
        request.setAttribute("btnList", btnHtml);
        logger.debug("=========================����ҳ�水ť����=========================");
    }

    /**
     * TODO ���ݴ������ȡ������Ϣ.
     *
     * @return
     * @throws Exception <pre>
     *                   �޸�����        �޸���    �޸�ԭ��
     *                   2016��3��11��    	     ����    �½�
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
            return "û���ҵ���Ӧ������Ϣ�������룺" + errorCode;
        }
    }

    /**
     * TODO ���ݴ������ȡ������Ϣ.
     *
     * @return
     * @throws Exception <pre>
     *                   �޸�����        �޸���    �޸�ԭ��
     *                   2016��3��11��    �غ���    �½�
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
            return "û���ҵ���Ӧ������Ϣ�������룺" + errorCode;
        }
    }

    /**
     * TODO ��ȡjson����.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��4��12��    	     ����    �½�
     * </pre>
     */
    public Json getJson() {

        return Json.getInstance();
    }

    /**
     * TODO ��ȡ��¼�û�������Ϣ.
     *
     * @return
     * @throws Exception <pre>
     *                   �޸�����        �޸���    �޸�ԭ��
     *                   2016��11��10��    	   ������  �½�
     *                   </pre>
     */
    public WebOperInfo getSessionOperInfo() throws Exception {
        WebOperInfo operInfo = null;
        try {
            operInfo = (WebOperInfo) getRequest().getSession().getAttribute("sessionOperInfo");
        } catch (Exception e) {
            //��ȡSession��Ϣʧ��!
            throw new SystemException("w113", e);
        }
        return operInfo;
    }

    /**
     * TODO ������־��
     *
     * @param targetName
     * @param methodName
     * @param consume    ��ʱ
     * @param retult     ���
     * @throws Exception <pre>
     *                   �޸�����        �޸���    �޸�ԭ��
     *                   2016��11��10��    	   ������  �½�
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
            logger.error("��ȡ������־ע�����:", e);
            throw new SystemException("w104", e);
        }
        WebLogInfo sysLog = new WebLogInfo();
        fdOrgan = getSessionOrgan();
        operInfo = getSessionOperInfo();
        String ip = IPUtil.getReqestIpAddr();
        if (StringUtils.isBlank(ip)) {
            ip = "��ȡʧ��";
        }
        sysLog.setThreadCode(Thread.currentThread().getName());
        sysLog.setTradeDate(BocoUtils.getNowDate());
        sysLog.setTradeTime(BocoUtils.getNowTime());
        sysLog.setOrganCode(fdOper == null ? "��ȡʧ��" : fdOper.getOrgancode() == null ? "---" : fdOper.getOrgancode());
        sysLog.setOrganName(fdOrgan == null ? "��ȡʧ��" : fdOrgan.getOrganname() == null ? "---" : fdOrgan.getOrganname());
        sysLog.setOperCode(fdOper == null ? "��ȡʧ��" : fdOper.getOpercode() == null ? "---" : fdOper.getOpercode());
        sysLog.setOperName(fdOper == null ? "��ȡʧ��" : fdOper.getOpername() == null ? "---" : fdOper.getOpername());
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
            //��¼��־����!
            throw new SystemException("w102", ex);
        }
    }
}
