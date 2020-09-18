package com.boco.SYS.aop;

import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseEntity;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.GfErrInfo;
import com.boco.SYS.entity.WebLogInfo;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.entity.WebRoleFun;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.CommonMapper;
import com.boco.SYS.mapper.GfErrInfoMapper;
import com.boco.SYS.mapper.WebLogInfoMapper;
import com.boco.SYS.mapper.WebRoleFunMapper;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.IPUtil;
import com.boco.SYS.util.WebContextUtil;
import com.boco.util.JsonUtils;
import com.github.pagehelper.SqlUtil;

/**
 * ҵ����־AOP��.
 *
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2015��11��19��    	    ����    �½�
 * </pre>
 */
@Aspect
@Component
public class SystemLogAop {
    private static final Logger logger = Logger.getLogger(SystemLogAop.class);
    @Autowired
    private WebLogInfoMapper logMapper;
    @Autowired
    private WebRoleFunMapper webRoleFunMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private GfErrInfoMapper gfErrInfoMapper;
    // ��¼��ʼʱ�䣬�쳣��־ʱ�����ʱ���������ִ��ʱ��
    Long start = 0L;
    FdOper fdOper = null;// ��¼��Ա��Ϣ
    FdOrgan fdOrgan = null;// ��¼������Ϣ
    WebOperInfo operInfo = null;// ��¼��Ա������Ϣ

    // ҵ����־�е�
    @Pointcut("execution(* com.boco.*.controller.*.*(..))")
    public void logAspect() {
    }

    @Before("logAspect()")
    public void before() {
//        System.out.println("before111111");
    }

    @AfterReturning("logAspect()")
    public void after() {

//        System.out.println("after111111");
    }

    /**
     * TODO ҵ������־.
     *
     * @param point
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��21��    	    ����    �½�
     * </pre>
     */
    @Around("logAspect()")
    public Object doController(ProceedingJoinPoint point) throws Throwable {
        Thread.currentThread().setName(BocoUtils.getUUID());
        start = System.currentTimeMillis();
        /**
         * ��ȡ����ע��
         */
        Map<String, Object> map = getMethodDescribe(point);
        if (!("���ص�¼ҳ��".equals(map.get("funName"))
                || "��¼ϵͳ".equals(map.get("funName"))
                || "��ʱҳ����ת".equals(map.get("funName")))) {
            // ��ʼ��session�û�����
            initSessionVariables();
        }
        Long start = 0L;
        Long end = 0L;
        Long consume = 0L;
        Object result = null;
        logger.info("######################ģ��[" + map.get("tradeName") + "]-����[" + map.get("funName") + "]-���[" + map.get("funCode") + "]��ʼ######################");

        logger.debug("===========����[" + map.get("funName") + "]Ȩ��У��===========");
        if (!checkPermission(map)) {
            //�޴˹���Ȩ��!
//            throw new SystemException("w101");
        }
        logger.debug("===========����[" + map.get("funName") + "]Ȩ��У��===========");

        /**
         * ִ��Ŀ�귽��
         */
        start = System.currentTimeMillis();
        result = point.proceed();
        end = System.currentTimeMillis();
        consume = end - start;
        if ("��¼ϵͳ".equals(map.get("funName"))) {
            fdOper = WebContextUtil.getSessionUser();
            fdOrgan = WebContextUtil.getSessionOrgan();
            operInfo = WebContextUtil.getSessionOperInfo();
        }
        WebLogInfo sysLog = buildSystemLog(map);
        sysLog.setTradeConsumingTime(consume + "");

        // ���log4j��־
        LogUtil.log((Debug) map.get("level"), sysLog);
        insertLog(sysLog, (AccessType) map.get("accessType"));
        logger.info("######################ģ��[" + map.get("tradeName") + "]-����[" + map.get("funName") + "]-���[" + map.get("funCode") + "]����######################");
        return result;
    }

    /**
     * TODO ��ʼ��Session�ﱣ����û������Ϣ.
     *
     *
     * <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��29��    	    ����    �½�
     * </pre>
     *
     * @throws Exception
     */
    private void initSessionVariables() throws Exception {
        fdOper = WebContextUtil.getSessionUser();
        if (fdOper == null) {
            //δ��½���߻Ự��ʱ!
            throw new SystemException("w100");
        }
        fdOrgan = WebContextUtil.getSessionOrgan();
        if (fdOrgan == null) {
            //δ��½���߻Ự��ʱ!
            throw new SystemException("w100");
        }
        operInfo = WebContextUtil.getSessionOperInfo();
    }

    /**
     * TODO ҵ���쳣��¼.
     *
     * @param e <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2015��11��5��    	    ����    �½�
     *                                     </pre>
     */
    @AfterThrowing(pointcut = "logAspect()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e)
            throws Throwable {
        Long eEnd = System.currentTimeMillis();// �������ʱ��
        Long consume = eEnd - start;// �����ʱ
        Map<String, Object> map = getMethodDescribe(joinPoint);
        logger.info("######################ģ��[" + map.get("tradeName") + "]-����[" + map.get("funName") + "]-���[" + map.get("funCode") + "]�쳣######################");
        WebLogInfo sysLog = buildSystemLog(map);
        sysLog.setEx(e);
        sysLog.setTradeConsumingTime(consume + "");
        //�쳣��Ϣ����4000ʱ��ȡ
        String msg = "";
        if (e instanceof SystemException) {
            String code = ((SystemException) e).getCode();
            //�Զ����쳣��ͨ���쳣�����ȡ�쳣��Ϣ
            HashMap<String, Object> pk = new HashMap<String, Object>();
            pk.put("gfSysCode", "99370000000");
            pk.put("gfRetCode", code);
            pk.put("otherSysCode", "99370000000");
            pk.put("otherRetCode", code);
            GfErrInfo errInfo = gfErrInfoMapper.selectByPK(pk);
            if (e.getCause() == null) {
                if (errInfo != null) {
                    msg = e.getClass().getCanonicalName() + ":" + errInfo.getGfRetInfo();
                } else {
                    msg = e.getClass().getCanonicalName() + ":" + "û���ҵ���Ӧ������Ϣ�������룺[" + pk + "]";
                }
            } else {
                if (errInfo != null) {
                    msg = e.getCause().getClass().getCanonicalName() + ":" + errInfo.getGfRetInfo() + "\n" + e.getCause().getLocalizedMessage();
                } else {
                    msg = e.getCause().getClass().getCanonicalName() + ":" + "û���ҵ���Ӧ������Ϣ�������룺[" + pk + "]\n" + e.getCause().getLocalizedMessage();
                }
            }
        } else {
            msg = e.getClass().getCanonicalName() + ":" + e.getLocalizedMessage();
        }
        if (msg != null && msg.length() > 4000) {
            msg = msg.substring(0, 3999);
        }
        sysLog.setRunningResult(msg);
        // ���log4j��־
        LogUtil.log(Debug.ERROR, sysLog);
        insertLog(sysLog, AccessType.WRITE);
        logger.info("######################ģ��[" + map.get("tradeName") + "]-����[" + map.get("funName") + "]-���[" + map.get("funCode") + "]����######################");
    }

    /**
     * TODO �ж�Ȩ��.
     * 20160311  ����  �޸�235�д��룬�����ǰ��¼�Ĳ���Աӵ������һ������Աת�����Ľ�ɫ����ǰ����Ա�޷������׵�����
     *
     * @param map
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��1��30��    	    ����    �½�
     * </pre>
     */
    private boolean checkPermission(Map<String, Object> map) throws Exception {
        // ����δ�佻���룬��Ȩ��
        if (map.get("funCode") == null) {
            return false;
        }
        // system�����룬��Ȩ��
        if ("system".equalsIgnoreCase(map.get("funCode").toString())) {
            return true;
        }
        if (fdOper == null) {
            //δ��½���߻Ự��ʱ!
            throw new SystemException("w100");
        }
        /**
         * �жϽ�ɫȨ�޼�
         */
        // ѭ����ɫ����ѯ��ɫӵ�е�Ȩ�޼��ϣ���������˷�������ʾ��Ȩ�ޣ�������н�ɫ��Ӧ��Ȩ�޼��������˷������޴˽���Ȩ��
        SqlUtil.clearLocalPage();
        List<WebRoleFun> funList = webRoleFunMapper.selectOperRoleFun(fdOper.getOperdegrees());
        if (funList == null) {
            return false;
        }
        for (WebRoleFun roleFun : funList) {
            if (StringUtils.equals(roleFun.getFunCode(), map.get("funCode").toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * TODO ������־��.
     *
     * @param sysLog
     * @param accessType
     * @throws Exception <pre>
     *                                                                         �޸�����        �޸���    �޸�ԭ��
     *                                                                         2016��1��30��    	    ����    �½�
     *                                                                         </pre>
     */
    private void insertLog(WebLogInfo sysLog, AccessType accessType) {
        if (AccessType.WRITE == accessType) {
            logger.debug("==========��¼��־��ʼ==========");
            sysLog.setId(BocoUtils.getNowDate() + BocoUtils.getNowTime() + commonMapper.getNextId());
            try {
                logMapper.insertEntity(sysLog);
            } catch (Exception ex) {
                //��¼��־����!
                throw new SystemException("w102", ex);
            }
            logger.debug("==========��¼��־����==========");
        }
    }

    /**
     * TODO ��ȡҵ����־ע����Ϣ.
     *
     * @param joinPoint
     * @return
     * @throws Exception <pre>
     *                                                                         �޸�����        �޸���    �޸�ԭ��
     *                                                                         2015��11��21��    	    ����    �½�
     *                                                                         </pre>
     */
    @SuppressWarnings("rawtypes")
    public Map<String, Object> getMethodDescribe(JoinPoint joinPoint) {
        Map<String, Object> map = new HashMap<String, Object>();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        map.put("runningMethod", targetName + "." + methodName + "()");
        // ��ȡ�û����󷽷��Ĳ��������л�ΪJSON��ʽ�ַ���
        String params = "";
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                if (joinPoint.getArgs()[i] instanceof BaseEntity) {
                    params += JsonUtils.toJson(joinPoint.getArgs()[i]) + ";";
                } else {
                    params += joinPoint.getArgs()[i] + ";";
                }
            }
        }
        map.put("arguments", params);
        try {
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == joinPoint.getArgs().length) {
                        if (method.getAnnotation(SystemLog.class) == null) {
                            logger.error("����δ������־ע��!");
                            throw new SystemException("w103");
                        }
                        map.put("tradeName", method.getAnnotation(SystemLog.class).tradeName());
                        map.put("funCode", method.getAnnotation(SystemLog.class).funCode());
                        map.put("funName", method.getAnnotation(SystemLog.class).funName());
                        map.put("accessType", method.getAnnotation(SystemLog.class).accessType());
                        map.put("level", method.getAnnotation(SystemLog.class).level());
                        map.put("result", "�ɹ�");
                        break;
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            logger.error("��ȡ������־ע�����:", e);
            throw new SystemException("w104", e);
        }
        return map;
    }

    /**
     * TODO ͨ��ע����Ϣ����ҵ����־����.
     *
     * @param map
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��21��    	    ����    �½�
     * </pre>
     * @throws UnknownHostException
     */
    private WebLogInfo buildSystemLog(Map<String, Object> map) {
        WebLogInfo sysLog = new WebLogInfo();
        String ip = IPUtil.getReqestIpAddr();
        if (StringUtils.isBlank(ip)) {
            ip = "��ȡʧ��";
        }
        sysLog.setThreadCode(Thread.currentThread().getName());
        sysLog.setTradeDate(BocoUtils.getNowDate());
        sysLog.setTradeTime(BocoUtils.getNowTime());
        sysLog.setOrganCode(fdOper == null ? "��ȡʧ��" : fdOper.getOrgancode());
        sysLog.setOrganName(fdOrgan == null ? "��ȡʧ��" : fdOrgan.getOrganname());
        sysLog.setOperCode(fdOper == null ? "��ȡʧ��" : fdOper.getOpercode());
        sysLog.setOperName(fdOper == null ? "��ȡʧ��" : fdOper.getOpername());
        sysLog.setModuleName(map.get("tradeName") + "-" + map.get("funName"));
        sysLog.setModuleCode(map.get("funCode") + "");
        sysLog.setRunningMethod(map.get("runningMethod") + "");
        sysLog.setRequestIp(ip);
        sysLog.setRunningResult(map.get("result") + "");
        sysLog.setArgu(map.get("arguments").toString());
        sysLog.setServiceCode(IPUtil.getLocalHostIpAddr());
        return sysLog;
    }
}
