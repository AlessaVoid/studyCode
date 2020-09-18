package com.boco.PM.service.impl;

import com.boco.PM.service.IFdOperService;
import com.boco.PM.service.IWebRoleInfoService;
import com.boco.SYS.base.GenericService;
import com.boco.TONY.utils.PasswordEncryptHelper;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.DataProcess;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FdOper业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class FdOperService extends GenericService<FdOper, HashMap<String, Object>> implements IFdOperService {
    @Autowired
    private FdExternsysMapper fdExternsysMapper;
    @Autowired
    private FdSeqUnifyserialQDMapper fdSeqUnifyserialQDMapper;
    @Autowired
    private FdOperMapper fdOperMapper;
    @Autowired
    private WebSublicenseInfoMapper webSublicenseInfoMapper;
    @Autowired
    private FdOrganMapper fdOrganMapper;
    @Autowired
    private IWebRoleInfoService webRoleInfoService;

    /**
     * TODO 柜员签到.
     *
     * @param map
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月24日    	  杜旭    新建
     * </pre>
     */
    @Override
    public boolean operSignIn(FdOper fdOper) {
        String operCode = fdOper.getOpercode();
        String password = fdOper.getOperpassword();
        //登录的时候需要传递更多的信息
        Preconditions.checkArgument(null != operCode && null != password, "operCode or password is null");
        try {
            Subject subject = SecurityUtils.getSubject();
            String encryptPassword = PasswordEncryptHelper.encrypt(operCode, password);
            subject.login(new UsernamePasswordToken(operCode, encryptPassword));
            return subject.isAuthenticated();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * TODO 柜员签退.
     *
     * @param map
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月24日    	  杜旭    新建
     * </pre>
     */
    @Override
    public boolean operSignOut(FdOper fdOper) {
        String operCode = fdOper.getOpercode();
        String password = fdOper.getOperpassword();
        Preconditions.checkArgument(null != operCode && null != password, "operCode or password is null");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        Fml32DTO fml32DTO = new Fml32DTO();
        Map sendMap = fml32DTO.getSignoutSendMap();
        sendMap.put("TELLER_CODE", fdOper.getOpercode());
        sendMap.put("PINDATA", password);
        sendMap.put("TRANBRANCH", fdOper.getOrgancode());
        FdExternsys clearFdExternsys = this.getFdExternsys("9970003");
        sendMap.put("CLEARDATE", clearFdExternsys.getExterndate());
        return true;
    }

    /**
     * TODO 柜员签退.
     *
     * @param map
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月24日    	  杜旭    新建
     * </pre>
     */
    @Override
    public Map operQzSignout(FdOper fdOper, String doOperCode) {
        Fml32DTO fml32DTO = new Fml32DTO();
        Map sendMap = fml32DTO.getQzSignoutSendMap();
        //柜员号
        sendMap.put("TELLER_CODE", fdOper.getOpercode());
        //个人识别码 md5加密的柜员密码
		/*MD5 md5 = new MD5();
		String salt = "dhjdfu34i34u34-zmew8732dfhjd-"+fdOper.getOpercode()+"dfhjdf8347sdhxcye-ehjcbeww34";
		String password = md5.get32MD5ofStr(fdOper.getOperpassword()+"{"+salt+"}");*/
        //登录时已经加密
        String password = fdOper.getOperpassword();
        sendMap.put("PINDATA", password);
        //交易机构代码
        sendMap.put("TRANBRANCH", fdOper.getOrgancode());
        //会计日期
        FdExternsys clearFdExternsys = this.getFdExternsys("9970003");
        sendMap.put("CLEARDATE", clearFdExternsys.getExterndate());
        //系统跟踪号
        Integer unifyserialQD = fdSeqUnifyserialQDMapper.findFdSeqUnifyserialQD();
//		sendMap.put("SYS_TRACE_NO","99370000000"+ fdPublicparaService.GetTransDate() + String.valueOf(unifyserialQD));
        DataProcess dp = new DataProcess();
        sendMap.put("SYS_TRACE_NO", "99370000000" + dp.getFormatDate("yyyyMMdd") + String.valueOf(unifyserialQD));
        //被操作柜员号
        sendMap.put("CHG_TELLER_NO", doOperCode);
        String servername = "QDGLPT_612080";
        FdExternsys fdExternsys = this.getFdExternsys("99700041");//主地址
        FdExternsys fdExternsys1 = this.getFdExternsys("99700042");//备用地址
        ChannelService channelBiz = new ChannelService();
        String returnMsg = channelBiz.send2Tux(fdExternsys.getIpaddr(), fdExternsys.getPort(), fdExternsys1.getIpaddr(), fdExternsys1.getPort(), sendMap, servername);
        return this.getRetMsgMap(returnMsg);
    }

    /**
     * 密码修改
     */
    @Override
    public Map operUpdatePwd(FdOper fdOper, String password) {
        Map<String, String> returnMsgMap = new HashMap<>();
        String orginPassword = fdOper.getOperpassword();
        PasswordEncryptHelper.encrypt(fdOper.getOpercode(), orginPassword);
        try {
            Map operInfo = getUserInfo(fdOper.getOpercode());
            String encryptOldPassword = PasswordEncryptHelper.encrypt(fdOper.getOpercode(), orginPassword);
            if (!encryptOldPassword.equals(operInfo.get("password"))) {
                returnMsgMap.put("RESPINFO", "原密码不正确");
                return new HashMap();
            }
            String newPassword = PasswordEncryptHelper.encrypt(fdOper.getOpercode(), password);
            fdOper.setOperpassword(newPassword);
            fdOperMapper.updateOperPassword(fdOper);
            returnMsgMap.put("RESPCODE", "00000000");
            returnMsgMap.put("RESPINFO", "更新密码成功");
            return returnMsgMap;
        } catch (Exception e) {
            returnMsgMap.put("RESPINFO", "更新密码失败");
            return returnMsgMap;
        }


    }

    /**
     * 机构修改
     */
    @Override
    public Map operUpdateOrgan(FdOper fdOper, String newOrganCode) {
        Map<String, String> returnMsgMap = new HashMap<>();
        String orginOrgancode = null;
        try {
            orginOrgancode = fdOperMapper.selectOpersOrgan(fdOper.getOpercode());
        } catch (Exception e) {
            returnMsgMap.put("RESPINFO", "更新机构失败");
        }
        try {
            if (orginOrgancode.equals(newOrganCode)) {
                returnMsgMap.put("RESPINFO", "新机构与原机构一致");
            }
        }
        catch (Exception e) {
            returnMsgMap.put("RESPINFO", "更新机构失败");
        }
        return returnMsgMap;
    }

    /**
     * 柜员转岗
     */
    @Override
    public Map insertNewOper(FdOper fdOper) {
        Map<String, String> returnMsgMap = new HashMap<>();
        try {
            fdOperMapper.insertEntity(fdOper);
            returnMsgMap.put("RESPCODE", "00000000");
            returnMsgMap.put("RESPINFO", "人员转岗成功");
        } catch (DataAccessException e) {
            returnMsgMap.put("RESPINFO", "人员转岗失败");
        }
        return returnMsgMap;
    }

    /**
     * 密码重置
     */
    @Override
    public Map operRePwd(FdOper sessionUser, FdOper fdOper) {
        Map<String, String> returnMsgMap = new HashMap<>();
        try {
            String orignPassword = fdOper.getOperpassword();
            String encryptPassword = PasswordEncryptHelper.encrypt(fdOper.getOpercode(), orignPassword);
            fdOper.setOperpassword(encryptPassword);
            fdOperMapper.resetOperPassword(fdOper);
            returnMsgMap.put("RESPCODE", "00000000");
            returnMsgMap.put("RESPINFO", "重置密码成功");
            return returnMsgMap;
        } catch (Exception e) {
            returnMsgMap.put("RESPINFO", "重置密码失败");
            return returnMsgMap;
        }
    }

    /**
     * TODO 获得渠道管理平台的ip与端口.
     *
     * @param externsysCode
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月24日    	  杜旭    新建
     * </pre>
     */
    public FdExternsys getFdExternsys(String externsysCode) {
        //获得渠道管理平台的ip与端口
        //渠道：9937000
        //会计平台：9970003
        FdExternsys fdExternsys = fdExternsysMapper.selectByPK(externsysCode);
        return fdExternsys;
    }

    /**
     * TODO 解析获得的响应报文.
     *
     * @param returnMsg
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月24日    	  杜旭    新建
     * </pre>
     */
    public Map getRetMsgMap(String returnMsg) {
        Map<String, Object> returnMsgMap = new HashMap<String, Object>();
        if (StringUtils.isBlank(returnMsg)) {
            returnMsgMap.put("RESPINFO", "未返回信息");
            return returnMsgMap;
        }
        String[] strArr = returnMsg.split("\":");
        if (strArr.length == 1) {
            returnMsgMap.put("RESPINFO", returnMsg);
            return returnMsgMap;
        }
        for (String s : strArr) {
            String[] checkStr = s.split("=");
            returnMsgMap.put(checkStr[0].replace("[0]S", ""), checkStr[1].replace("\"", ""));
        }
        return returnMsgMap;
    }

    /**
     * TODO 与角色功能表关联查询授权用户下拉列表数据.
     *
     * @param query
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             2016年3月7日    	  杜旭    新建
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             </pre>
     */
    public List<Map<String, String>> getPowerList(Map<String, String> query) throws Exception {
        return fdOperMapper.getPowerList(query);
    }

    /**
     * * TODO 根据请求参数查询操作员信息，返回用于操作员生产下拉框的json串.
     *
     * @param request 查询串格式为 funno=**** 例如：funno=FD-01
     * @return json串格式为{"list":[{"value":"操作员A的编号","key":"操作员A"},{"value":"操作员B的编号"
     * ,"key":"操作员B"}]}
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             2016年3月14日    	  杜旭    新建
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             </pre>
     */
    public Map<String, List<Map<String, String>>> getAppUserList(String funCode, HttpSession session) throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();
        Map<String, String> tempMap = new HashMap<String, String>();
        if (funCode != null && !funCode.equals("")) {
            Map<String, String> query = new HashMap<String, String>();
            query.put("funCode", funCode);
            query.put("organcode", ((FdOrgan) session.getAttribute("sessionOrgan")).getThiscode());
            List<Map<String, String>> fdOperList = fdOperMapper.getPowerList(query);
            List<Map<String, String>> subInfoList = webSublicenseInfoMapper.getPowerList(query);
            if (fdOperList.size() != 0) {
                for (int i = 0; i < fdOperList.size(); i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    Map<String, String> fdOperMap = fdOperList.get(i);
                    map.put("key", fdOperMap.get("key"));
                    map.put("value", fdOperMap.get("value"));
                    tempMap.put("key", fdOperMap.get("key"));
                    tempMap.put("value", fdOperMap.get("value"));
                    list.add(map);
                }
            }
            if (subInfoList.size() != 0) {
                for (int i = 0; i < subInfoList.size(); i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    Map<String, String> subInfoMap = subInfoList.get(i);
                    if (!tempMap.containsValue(subInfoMap.get("key"))) {
                        map.put("key", subInfoMap.get("key"));
                        map.put("value", subInfoMap.get("value"));
                        tempMap.put("key", subInfoMap.get("key"));
                        tempMap.put("value", subInfoMap.get("value"));
                        list.add(map);
                    }
                }
            }
        }
        result.put("list", list);
        return result;
    }

    /**
     * 获取总行复核人员
     */
    public Map<String, List<Map<String, String>>> getHeadOfficeAppUserList(String funCode, HttpSession session) throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();
        Map<String, String> tempMap = new HashMap<String, String>();
        if (funCode != null && !funCode.equals("")) {
            Map<String, String> query = new HashMap<String, String>();
            query.put("funCode", funCode);
            query.put("organcode", "11005293");
            List<Map<String, String>> fdOperList = fdOperMapper.getPowerList(query);
            List<Map<String, String>> subInfoList = webSublicenseInfoMapper.getPowerList(query);
            if (fdOperList.size() != 0) {
                for (int i = 0; i < fdOperList.size(); i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    Map<String, String> fdOperMap = fdOperList.get(i);
                    map.put("key", fdOperMap.get("key"));
                    map.put("value", fdOperMap.get("value"));
                    tempMap.put("key", fdOperMap.get("key"));
                    tempMap.put("value", fdOperMap.get("value"));
                    list.add(map);
                }
            }
            if (subInfoList.size() != 0) {
                for (int i = 0; i < subInfoList.size(); i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    Map<String, String> subInfoMap = subInfoList.get(i);
                    if (!tempMap.containsValue(subInfoMap.get("key"))) {
                        map.put("key", subInfoMap.get("key"));
                        map.put("value", subInfoMap.get("value"));
                        tempMap.put("key", subInfoMap.get("key"));
                        tempMap.put("value", subInfoMap.get("value"));
                        list.add(map);
                    }
                }
            }
        }
        result.put("list", list);
        return result;
    }

    /**
     * TODO 复杂流程： 与角色功能表关联查询授权下拉列表数据.
     *
     * @param funCode
     * @param session
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             2016年4月5日    	  杜旭    新建
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             </pre>
     */
    public Map<String, List<Map<String, String>>> getAppUserListByRole(String funCode, String roleCode, String organcode) throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();
        Map<String, String> tempMap = new HashMap<String, String>();
        if (funCode != null && !funCode.equals("")) {
            WebRoleInfo webRoleInfo = webRoleInfoService.selectByPK(roleCode.substring(0, 3));
            Map<String, Object> query = new HashMap<String, Object>();
            //如果登录操作员为分行人员，查询角色时，添加本机构代码
            if ("1".equals(webRoleInfo.getOrganLevel())) {
                query.put("organcode", organcode);
            }
            query.put("roleCode", roleCode.split(","));
            query.put("funCode", funCode);
            List<Map<String, String>> fdOperList = fdOperMapper.getPowerListByRole(query);
            List<Map<String, String>> subInfoList = webSublicenseInfoMapper.getPowerListByRole(query);
            if (fdOperList.size() != 0) {
                for (int i = 0; i < fdOperList.size(); i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    Map<String, String> fdOperMap = fdOperList.get(i);
                    map.put("key", fdOperMap.get("key"));
                    map.put("value", fdOperMap.get("value"));
                    tempMap.put("key", fdOperMap.get("key"));
                    tempMap.put("value", fdOperMap.get("value"));
                    list.add(map);
                }
            }
            if (subInfoList.size() != 0) {
                for (int i = 0; i < subInfoList.size(); i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    Map<String, String> subInfoMap = subInfoList.get(i);
                    if (!tempMap.containsValue(subInfoMap.get("key"))) {
                        map.put("key", subInfoMap.get("key"));
                        map.put("value", subInfoMap.get("value"));
                        tempMap.put("key", subInfoMap.get("key"));
                        tempMap.put("value", subInfoMap.get("value"));
                        list.add(map);
                    }
                }
            }
        }
        result.put("list", list);
        return result;
    }

    /**
     * TODO 根据人员编号查询人员信息，机构信息，角色信息.
     *
     * @param request
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             2016年3月14日    	  杜旭    新建
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             </pre>
     */
    public Map getUserInfo(String userNo) throws Exception {
        String roleName = "", role = "";
        FdOper fdOper = new FdOper();
        fdOper.setOpercode(userNo);
        Map map = new HashMap();
        List<FdOper> operList = fdOperMapper.selectByAttr(fdOper);
        if (operList.size() == 1) {
            //获取操作员名称
            map.put("operName", operList.get(0).getOpername());
            //获取操作员所在机构的机构代码及机构名称
            map.put("password", operList.get(0).getOperpassword());
            FdOrgan fdOrgan = new FdOrgan();
            fdOrgan.setThiscode(operList.get(0).getOrgancode());
            List<FdOrgan> organList = fdOrganMapper.selectByAttr(fdOrgan);
            map.put("organName", organList.get(0).getOrganname());
            map.put("organCode", organList.get(0).getThiscode());
            //获取角色1：从fd_Oper表获取柜员角色
//            List<String> roles = new ArrayList<>();
//            if (StringUtils.isNotEmpty(operList.get(0).getOperdegree())) {
//                role = operList.get(0).getOperdegree();
//            }
//            //获取角色2：从转授权表获取转给当前操作员并没有收回的角色集
//            WebSublicenseInfo webSublicenseInfo = new WebSublicenseInfo();
//            webSublicenseInfo.setInOper(userNo);
//            webSublicenseInfo.setIsBack("2");
//            List<WebSublicenseInfo> subInfoList = webSublicenseInfoMapper.selectByAttr(webSublicenseInfo);
//            if (subInfoList.size() != 0) {
//                for (WebSublicenseInfo subInfo : subInfoList) {
//                    if (!role.contains(subInfo.getRoleCode())) {
//                        role += subInfo.getRoleCode();
//                    }
//                }
//            }
//            //通过角色编码获取角色名称
//            for (int i = 0; i < role.length(); i = i + 3) {
//                String roleCode = role.substring(i, i + 3);
//                WebRoleInfo webRoleInfo = webRoleInfoService.selectByPK(roleCode);
//                if (webRoleInfo != null) {
//                    roleName += webRoleInfo.getRoleName() + ",";
//                }
//            }
//            if (roleName.length() != 0) {
//                roleName = roleName.substring(0, roleName.length() - 1);
//            }
            map.put("roleName", roleName);
        }
        return map;
    }
}