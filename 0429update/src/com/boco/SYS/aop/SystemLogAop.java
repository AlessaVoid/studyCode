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
 * 业务日志AOP类.
 *
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月19日    	    杨滔    新建
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
    // 记录开始时间，异常日志时与结束时间算出程序执行时间
    Long start = 0L;
    FdOper fdOper = null;// 登录柜员信息
    FdOrgan fdOrgan = null;// 登录机构信息
    WebOperInfo operInfo = null;// 登录柜员基本信息

    // 业务日志切点
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
     * TODO 业务环绕日志.
     *
     * @param point
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月21日    	    杨滔    新建
     * </pre>
     */
    @Around("logAspect()")
    public Object doController(ProceedingJoinPoint point) throws Throwable {
        Thread.currentThread().setName(BocoUtils.getUUID());
        start = System.currentTimeMillis();
        /**
         * 获取方法注解
         */
        Map<String, Object> map = getMethodDescribe(point);
        if (!("加载登录页面".equals(map.get("funName"))
                || "登录系统".equals(map.get("funName"))
                || "超时页面跳转".equals(map.get("funName")))) {
            // 初始化session用户变量
            initSessionVariables();
        }
        Long start = 0L;
        Long end = 0L;
        Long consume = 0L;
        Object result = null;
        logger.info("######################模块[" + map.get("tradeName") + "]-功能[" + map.get("funName") + "]-编号[" + map.get("funCode") + "]开始######################");

        logger.debug("===========功能[" + map.get("funName") + "]权限校验===========");
        if (!checkPermission(map)) {
            //无此功能权限!
//            throw new SystemException("w101");
        }
        logger.debug("===========功能[" + map.get("funName") + "]权限校验===========");

        /**
         * 执行目标方法
         */
        start = System.currentTimeMillis();
        result = point.proceed();
        end = System.currentTimeMillis();
        consume = end - start;
        if ("登录系统".equals(map.get("funName"))) {
            fdOper = WebContextUtil.getSessionUser();
            fdOrgan = WebContextUtil.getSessionOrgan();
            operInfo = WebContextUtil.getSessionOperInfo();
        }
        WebLogInfo sysLog = buildSystemLog(map);
        sysLog.setTradeConsumingTime(consume + "");

        // 输出log4j日志
        LogUtil.log((Debug) map.get("level"), sysLog);
        insertLog(sysLog, (AccessType) map.get("accessType"));
        logger.info("######################模块[" + map.get("tradeName") + "]-功能[" + map.get("funName") + "]-编号[" + map.get("funCode") + "]结束######################");
        return result;
    }

    /**
     * TODO 初始化Session里保存的用户相关信息.
     *
     *
     * <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月29日    	    杨滔    新建
     * </pre>
     *
     * @throws Exception
     */
    private void initSessionVariables() throws Exception {
        fdOper = WebContextUtil.getSessionUser();
        if (fdOper == null) {
            //未登陆或者会话超时!
            throw new SystemException("w100");
        }
        fdOrgan = WebContextUtil.getSessionOrgan();
        if (fdOrgan == null) {
            //未登陆或者会话超时!
            throw new SystemException("w100");
        }
        operInfo = WebContextUtil.getSessionOperInfo();
    }

    /**
     * TODO 业务异常记录.
     *
     * @param e <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2015年11月5日    	    杨滔    新建
     *                                     </pre>
     */
    @AfterThrowing(pointcut = "logAspect()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e)
            throws Throwable {
        Long eEnd = System.currentTimeMillis();// 程序结束时间
        Long consume = eEnd - start;// 程序耗时
        Map<String, Object> map = getMethodDescribe(joinPoint);
        logger.info("######################模块[" + map.get("tradeName") + "]-功能[" + map.get("funName") + "]-编号[" + map.get("funCode") + "]异常######################");
        WebLogInfo sysLog = buildSystemLog(map);
        sysLog.setEx(e);
        sysLog.setTradeConsumingTime(consume + "");
        //异常信息超过4000时截取
        String msg = "";
        if (e instanceof SystemException) {
            String code = ((SystemException) e).getCode();
            //自定义异常，通过异常代码获取异常信息
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
                    msg = e.getClass().getCanonicalName() + ":" + "没有找到对应错误信息，错误码：[" + pk + "]";
                }
            } else {
                if (errInfo != null) {
                    msg = e.getCause().getClass().getCanonicalName() + ":" + errInfo.getGfRetInfo() + "\n" + e.getCause().getLocalizedMessage();
                } else {
                    msg = e.getCause().getClass().getCanonicalName() + ":" + "没有找到对应错误信息，错误码：[" + pk + "]\n" + e.getCause().getLocalizedMessage();
                }
            }
        } else {
            msg = e.getClass().getCanonicalName() + ":" + e.getLocalizedMessage();
        }
        if (msg != null && msg.length() > 4000) {
            msg = msg.substring(0, 3999);
        }
        sysLog.setRunningResult(msg);
        // 输出log4j日志
        LogUtil.log(Debug.ERROR, sysLog);
        insertLog(sysLog, AccessType.WRITE);
        logger.info("######################模块[" + map.get("tradeName") + "]-功能[" + map.get("funName") + "]-编号[" + map.get("funCode") + "]结束######################");
    }

    /**
     * TODO 判断权限.
     * 20160311  杜旭  修改235行代码，解决当前登录的操作员拥有另外一个操作员转给他的角色，当前操作员无法做交易的问题
     *
     * @param map
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年1月30日    	    杨滔    新建
     * </pre>
     */
    private boolean checkPermission(Map<String, Object> map) throws Exception {
        // 方法未配交易码，无权限
        if (map.get("funCode") == null) {
            return false;
        }
        // system功能码，有权限
        if ("system".equalsIgnoreCase(map.get("funCode").toString())) {
            return true;
        }
        if (fdOper == null) {
            //未登陆或者会话超时!
            throw new SystemException("w100");
        }
        /**
         * 判断角色权限集
         */
        // 循环角色，查询角色拥有的权限集合，如果包含此方法，表示有权限，如果所有角色对应的权限集不包括此方法，无此交易权限
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
     * TODO 插入日志表.
     *
     * @param sysLog
     * @param accessType
     * @throws Exception <pre>
     *                                                                         修改日期        修改人    修改原因
     *                                                                         2016年1月30日    	    杨滔    新建
     *                                                                         </pre>
     */
    private void insertLog(WebLogInfo sysLog, AccessType accessType) {
        if (AccessType.WRITE == accessType) {
            logger.debug("==========记录日志开始==========");
            sysLog.setId(BocoUtils.getNowDate() + BocoUtils.getNowTime() + commonMapper.getNextId());
            try {
                logMapper.insertEntity(sysLog);
            } catch (Exception ex) {
                //记录日志出错!
                throw new SystemException("w102", ex);
            }
            logger.debug("==========记录日志结束==========");
        }
    }

    /**
     * TODO 获取业务日志注解信息.
     *
     * @param joinPoint
     * @return
     * @throws Exception <pre>
     *                                                                         修改日期        修改人    修改原因
     *                                                                         2015年11月21日    	    杨滔    新建
     *                                                                         </pre>
     */
    @SuppressWarnings("rawtypes")
    public Map<String, Object> getMethodDescribe(JoinPoint joinPoint) {
        Map<String, Object> map = new HashMap<String, Object>();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        map.put("runningMethod", targetName + "." + methodName + "()");
        // 获取用户请求方法的参数并序列化为JSON格式字符串
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
                            logger.error("方法未配置日志注解!");
                            throw new SystemException("w103");
                        }
                        map.put("tradeName", method.getAnnotation(SystemLog.class).tradeName());
                        map.put("funCode", method.getAnnotation(SystemLog.class).funCode());
                        map.put("funName", method.getAnnotation(SystemLog.class).funName());
                        map.put("accessType", method.getAnnotation(SystemLog.class).accessType());
                        map.put("level", method.getAnnotation(SystemLog.class).level());
                        map.put("result", "成功");
                        break;
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            logger.error("获取方法日志注解出错:", e);
            throw new SystemException("w104", e);
        }
        return map;
    }

    /**
     * TODO 通过注解信息生成业务日志对象.
     *
     * @param map
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月21日    	    杨滔    新建
     * </pre>
     * @throws UnknownHostException
     */
    private WebLogInfo buildSystemLog(Map<String, Object> map) {
        WebLogInfo sysLog = new WebLogInfo();
        String ip = IPUtil.getReqestIpAddr();
        if (StringUtils.isBlank(ip)) {
            ip = "获取失败";
        }
        sysLog.setThreadCode(Thread.currentThread().getName());
        sysLog.setTradeDate(BocoUtils.getNowDate());
        sysLog.setTradeTime(BocoUtils.getNowTime());
        sysLog.setOrganCode(fdOper == null ? "获取失败" : fdOper.getOrgancode());
        sysLog.setOrganName(fdOrgan == null ? "获取失败" : fdOrgan.getOrganname());
        sysLog.setOperCode(fdOper == null ? "获取失败" : fdOper.getOpercode());
        sysLog.setOperName(fdOper == null ? "获取失败" : fdOper.getOpername());
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
