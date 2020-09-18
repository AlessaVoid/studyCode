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
 * FdOperҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
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
     * TODO ��Աǩ��.
     *
     * @param map
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��24��    	  ����    �½�
     * </pre>
     */
    @Override
    public boolean operSignIn(FdOper fdOper) {
        String operCode = fdOper.getOpercode();
        String password = fdOper.getOperpassword();
        //��¼��ʱ����Ҫ���ݸ������Ϣ
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
     * TODO ��Աǩ��.
     *
     * @param map
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��24��    	  ����    �½�
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
     * TODO ��Աǩ��.
     *
     * @param map
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��24��    	  ����    �½�
     * </pre>
     */
    @Override
    public Map operQzSignout(FdOper fdOper, String doOperCode) {
        Fml32DTO fml32DTO = new Fml32DTO();
        Map sendMap = fml32DTO.getQzSignoutSendMap();
        //��Ա��
        sendMap.put("TELLER_CODE", fdOper.getOpercode());
        //����ʶ���� md5���ܵĹ�Ա����
		/*MD5 md5 = new MD5();
		String salt = "dhjdfu34i34u34-zmew8732dfhjd-"+fdOper.getOpercode()+"dfhjdf8347sdhxcye-ehjcbeww34";
		String password = md5.get32MD5ofStr(fdOper.getOperpassword()+"{"+salt+"}");*/
        //��¼ʱ�Ѿ�����
        String password = fdOper.getOperpassword();
        sendMap.put("PINDATA", password);
        //���׻�������
        sendMap.put("TRANBRANCH", fdOper.getOrgancode());
        //�������
        FdExternsys clearFdExternsys = this.getFdExternsys("9970003");
        sendMap.put("CLEARDATE", clearFdExternsys.getExterndate());
        //ϵͳ���ٺ�
        Integer unifyserialQD = fdSeqUnifyserialQDMapper.findFdSeqUnifyserialQD();
//		sendMap.put("SYS_TRACE_NO","99370000000"+ fdPublicparaService.GetTransDate() + String.valueOf(unifyserialQD));
        DataProcess dp = new DataProcess();
        sendMap.put("SYS_TRACE_NO", "99370000000" + dp.getFormatDate("yyyyMMdd") + String.valueOf(unifyserialQD));
        //��������Ա��
        sendMap.put("CHG_TELLER_NO", doOperCode);
        String servername = "QDGLPT_612080";
        FdExternsys fdExternsys = this.getFdExternsys("99700041");//����ַ
        FdExternsys fdExternsys1 = this.getFdExternsys("99700042");//���õ�ַ
        ChannelService channelBiz = new ChannelService();
        String returnMsg = channelBiz.send2Tux(fdExternsys.getIpaddr(), fdExternsys.getPort(), fdExternsys1.getIpaddr(), fdExternsys1.getPort(), sendMap, servername);
        return this.getRetMsgMap(returnMsg);
    }

    /**
     * �����޸�
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
                returnMsgMap.put("RESPINFO", "ԭ���벻��ȷ");
                return new HashMap();
            }
            String newPassword = PasswordEncryptHelper.encrypt(fdOper.getOpercode(), password);
            fdOper.setOperpassword(newPassword);
            fdOperMapper.updateOperPassword(fdOper);
            returnMsgMap.put("RESPCODE", "00000000");
            returnMsgMap.put("RESPINFO", "��������ɹ�");
            return returnMsgMap;
        } catch (Exception e) {
            returnMsgMap.put("RESPINFO", "��������ʧ��");
            return returnMsgMap;
        }


    }

    /**
     * �����޸�
     */
    @Override
    public Map operUpdateOrgan(FdOper fdOper, String newOrganCode) {
        Map<String, String> returnMsgMap = new HashMap<>();
        String orginOrgancode = null;
        try {
            orginOrgancode = fdOperMapper.selectOpersOrgan(fdOper.getOpercode());
        } catch (Exception e) {
            returnMsgMap.put("RESPINFO", "���»���ʧ��");
        }
        try {
            if (orginOrgancode.equals(newOrganCode)) {
                returnMsgMap.put("RESPINFO", "�»�����ԭ����һ��");
            }
        }
        catch (Exception e) {
            returnMsgMap.put("RESPINFO", "���»���ʧ��");
        }
        return returnMsgMap;
    }

    /**
     * ��Աת��
     */
    @Override
    public Map insertNewOper(FdOper fdOper) {
        Map<String, String> returnMsgMap = new HashMap<>();
        try {
            fdOperMapper.insertEntity(fdOper);
            returnMsgMap.put("RESPCODE", "00000000");
            returnMsgMap.put("RESPINFO", "��Աת�ڳɹ�");
        } catch (DataAccessException e) {
            returnMsgMap.put("RESPINFO", "��Աת��ʧ��");
        }
        return returnMsgMap;
    }

    /**
     * ��������
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
            returnMsgMap.put("RESPINFO", "��������ɹ�");
            return returnMsgMap;
        } catch (Exception e) {
            returnMsgMap.put("RESPINFO", "��������ʧ��");
            return returnMsgMap;
        }
    }

    /**
     * TODO �����������ƽ̨��ip��˿�.
     *
     * @param externsysCode
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��24��    	  ����    �½�
     * </pre>
     */
    public FdExternsys getFdExternsys(String externsysCode) {
        //�����������ƽ̨��ip��˿�
        //������9937000
        //���ƽ̨��9970003
        FdExternsys fdExternsys = fdExternsysMapper.selectByPK(externsysCode);
        return fdExternsys;
    }

    /**
     * TODO ������õ���Ӧ����.
     *
     * @param returnMsg
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��24��    	  ����    �½�
     * </pre>
     */
    public Map getRetMsgMap(String returnMsg) {
        Map<String, Object> returnMsgMap = new HashMap<String, Object>();
        if (StringUtils.isBlank(returnMsg)) {
            returnMsgMap.put("RESPINFO", "δ������Ϣ");
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
     * TODO ���ɫ���ܱ������ѯ��Ȩ�û������б�����.
     *
     * @param query
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             2016��3��7��    	  ����    �½�
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             </pre>
     */
    public List<Map<String, String>> getPowerList(Map<String, String> query) throws Exception {
        return fdOperMapper.getPowerList(query);
    }

    /**
     * * TODO �������������ѯ����Ա��Ϣ���������ڲ���Ա�����������json��.
     *
     * @param request ��ѯ����ʽΪ funno=**** ���磺funno=FD-01
     * @return json����ʽΪ{"list":[{"value":"����ԱA�ı��","key":"����ԱA"},{"value":"����ԱB�ı��"
     * ,"key":"����ԱB"}]}
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             2016��3��14��    	  ����    �½�
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
     * ��ȡ���и�����Ա
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
     * TODO �������̣� ���ɫ���ܱ������ѯ��Ȩ�����б�����.
     *
     * @param funCode
     * @param session
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             2016��4��5��    	  ����    �½�
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             </pre>
     */
    public Map<String, List<Map<String, String>>> getAppUserListByRole(String funCode, String roleCode, String organcode) throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();
        Map<String, String> tempMap = new HashMap<String, String>();
        if (funCode != null && !funCode.equals("")) {
            WebRoleInfo webRoleInfo = webRoleInfoService.selectByPK(roleCode.substring(0, 3));
            Map<String, Object> query = new HashMap<String, Object>();
            //�����¼����ԱΪ������Ա����ѯ��ɫʱ����ӱ���������
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
     * TODO ������Ա��Ų�ѯ��Ա��Ϣ��������Ϣ����ɫ��Ϣ.
     *
     * @param request
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             2016��3��14��    	  ����    �½�
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             </pre>
     */
    public Map getUserInfo(String userNo) throws Exception {
        String roleName = "", role = "";
        FdOper fdOper = new FdOper();
        fdOper.setOpercode(userNo);
        Map map = new HashMap();
        List<FdOper> operList = fdOperMapper.selectByAttr(fdOper);
        if (operList.size() == 1) {
            //��ȡ����Ա����
            map.put("operName", operList.get(0).getOpername());
            //��ȡ����Ա���ڻ����Ļ������뼰��������
            map.put("password", operList.get(0).getOperpassword());
            FdOrgan fdOrgan = new FdOrgan();
            fdOrgan.setThiscode(operList.get(0).getOrgancode());
            List<FdOrgan> organList = fdOrganMapper.selectByAttr(fdOrgan);
            map.put("organName", organList.get(0).getOrganname());
            map.put("organCode", organList.get(0).getThiscode());
            //��ȡ��ɫ1����fd_Oper���ȡ��Ա��ɫ
//            List<String> roles = new ArrayList<>();
//            if (StringUtils.isNotEmpty(operList.get(0).getOperdegree())) {
//                role = operList.get(0).getOperdegree();
//            }
//            //��ȡ��ɫ2����ת��Ȩ���ȡת����ǰ����Ա��û���ջصĽ�ɫ��
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
//            //ͨ����ɫ�����ȡ��ɫ����
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