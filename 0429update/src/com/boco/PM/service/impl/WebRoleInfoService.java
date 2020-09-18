package com.boco.PM.service.impl;

import com.boco.PM.service.IWebRoleInfoService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebRoleFun;
import com.boco.SYS.entity.WebRoleInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.*;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * WebRoleInfoҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class WebRoleInfoService extends GenericService<WebRoleInfo, String> implements IWebRoleInfoService {
    @Autowired
    private FdOperMapper fdOperMapper;
    @Autowired
    private WebRoleFunMapper webRoleFunMapper;
    @Autowired
    private WebRoleInfoMapper webRoleInfoMapper;
    @Autowired
    private WebSublicenseInfoMapper webSublicenseInfoMapper;
    @Autowired
    private IFdBusinessDateService fdBusinessDateService;
    @Autowired
    private WebOperRoleMapper webOperRoleMapper;

    private Json json = new Json();

    /**
     * TODO ������ɫ��Ϣ.
     *
     * @param webRoleInfo
     * @param fdOper
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��23��    	  ����    �½�
     * </pre>
     * @throws Exception
     */
    @Override
    public Json InsertWebRoleInfo(WebRoleInfo webRoleInfo, FdOper fdOper, String funCodes) throws Exception {
        checkInsertData(webRoleInfo);
        //�����ɫ��Ϣ
        webRoleInfo.setLatestOperCode(fdOper.getOpercode());
        webRoleInfo.setLatestModifyDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        webRoleInfo.setLatestModifyTime(BocoUtils.getNowTime());
        int count = insertEntity(webRoleInfo);
        if (count != 1) {
            throw new SystemException("������ɫ��Ϣʧ��");
        }
        //�����ɫ���Ӧ��Ȩ����Ϣ
        String[] funs = funCodes.split("\\,");
        int funsCount = funs.length;
        int insertCount = insertBatchRoleFun(webRoleInfo.getRoleCode(), funCodes, fdOper.getOpercode());
        if (insertCount != funsCount) {
            throw new SystemException("����Ȩ����Ϣʧ��");
        }
        return this.json.returnMsg("true", "�����ɹ�");
    }

    /**
     * TODO �޸Ľ�ɫ��Ϣ.
     *
     * @param webRoleInfo
     * @param fdOper
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��23��    	  ����    �½�
     * </pre>
     * @throws Exception
     */
    public Json updateWebRoleInfo(WebRoleInfo webRoleInfo, FdOper fdOper, String funCodes) throws Exception {
        checkUpdateData(webRoleInfo);
        //�޸Ľ�ɫ��Ϣ
        webRoleInfo.setLatestOperCode(fdOper.getOpercode());
        webRoleInfo.setLatestModifyDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        webRoleInfo.setLatestModifyTime(BocoUtils.getNowTime());
        int count = updateByPK(webRoleInfo);
        if (count != 1) {
            throw new SystemException("�޸Ľ�ɫ��Ϣʧ��");
        }
        //ɾ����ɫ���ܱ��иý�ɫ��Ӧ��Ȩ����Ϣ
        webRoleFunMapper.deleteFunsByRole(webRoleInfo.getRoleCode());
        //�����ɫ���Ӧ��Ȩ����Ϣ
        String[] funs = funCodes.split("\\,");
        int funsCount = funs.length;
        int insertCount = insertBatchRoleFun(webRoleInfo.getRoleCode(), funCodes, fdOper.getOpercode());
        if (insertCount != funsCount) {
            throw new SystemException("����Ȩ����Ϣʧ��");
        }
        return this.json.returnMsg("true", "�޸ĳɹ�");
    }

    /**
     * TODO ɾ����ɫ��Ϣ.
     *
     * @param webRoleInfo
     * @param fdOper
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��23��    	  ����    �½�
     * </pre>
     * @throws Exception
     */
    public Json deleteWebRoleInfo(WebRoleInfo webRoleInfo) throws Exception {
        checkDeleteData(webRoleInfo);
        //��������ɾ����ɫ
        int count = deleteByPK(webRoleInfo.getRoleCode());
        if (count != 1) {
            throw new SystemException("ɾ��ʧ��");
        }
        //ɾ����ɫ���Ӧ��Ȩ����Ϣ
        webRoleFunMapper.deleteFunsByRole(webRoleInfo.getRoleCode());
        return this.json.returnMsg("true", "ɾ���ɹ�");
    }

    /**
     * TODO ��������Ȩ�޼�.
     *
     * @param roleCode
     * @param funCodes
     * @param opercode
     * @return
     * @throws RuntimeException <pre>
     *                                                                                                     �޸�����        �޸���    �޸�ԭ��
     *                                                                                                     2016��2��2��    	    ����    �½�
     *                                                                                                     </pre>
     */
    public int insertBatchRoleFun(String roleCode, String funCodes, String opercode) throws RuntimeException {
        String[] funCodeArr = funCodes.split(",");
        WebRoleFun roleFun = null;
        List<WebRoleFun> list = new ArrayList<WebRoleFun>();
        String currentDate = fdBusinessDateService.getCommonDateDetails();
        for (String funCode : funCodeArr) {
            roleFun = new WebRoleFun();
            roleFun.setFunCode(funCode);
            roleFun.setRoleCode(roleCode);
            roleFun.setLatestOperCode(opercode);
            roleFun.setLatestModifyDate(currentDate);
            roleFun.setLatestModifyTime(BocoUtils.getNowTime1());
            list.add(roleFun);
        }
        return webRoleFunMapper.insertBatch(list);
    }

    /**
     * TODO ����У�����.
     *
     * @param webRoleInfo
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��23��    	  ����    �½�
     * </pre>
     * @throws Exception
     */
    public boolean checkInsertData(WebRoleInfo webRoleInfo) throws Exception {
        String req = "[\u4e00-\u9fa5]*+[a-zA-Z]*+";
        if (Pattern.matches(req, webRoleInfo.getRoleName()) == false) {
            //��ɫ���ư����Ƿ��ַ������������Ļ�Ӣ��
            throw new SystemException("w417");
        }

        //��֤��ɫ���Ƶĳ���
        if (BocoUtils.getStrLength(webRoleInfo.getRoleName(), 2) > 200) {
            //��ɫ���Ƶĳ��Ȳ��ܳ���100!
            throw new SystemException("w418");
        }
        //��֤��ɫ�����Ƿ����
        WebRoleInfo existRoleCode = new WebRoleInfo();
        existRoleCode.setRoleCode(webRoleInfo.getRoleCode());
        int roleCodeCount = countByAttr(existRoleCode);
        if (roleCodeCount > 0) {
            //��ɫ�����Ѵ��ڣ�����������!
            throw new SystemException("w419");
        }
        //��֤ͬ���������½�ɫ�����Ƿ����
        WebRoleInfo existRoleName = new WebRoleInfo();
        existRoleName.setRoleName(webRoleInfo.getRoleName());
        existRoleName.setOrganLevel(webRoleInfo.getOrganLevel());
        int roleNameCount = countByAttr(existRoleName);
        if (roleNameCount > 0) {
            //�ü����£���ɫ�����Ѵ��ڣ�����������!
            throw new SystemException("w420");
        }
        return true;
    }

    /**
     * TODO �޸�У�����.
     *
     * @param webRoleInfo
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��23��    	  ����    �½�
     * </pre>
     * @throws Exception
     */
    private boolean checkUpdateData(WebRoleInfo webRoleInfo) throws Exception {
        String req = "[\u4e00-\u9fa5]*+[a-zA-Z]*+";
        if (Pattern.matches(req, webRoleInfo.getRoleName()) == false) {
            //��ɫ���ư����Ƿ��ַ������������Ļ�Ӣ��
            throw new SystemException("w417");
        }
        //��֤��ɫ���Ƶĳ���
        if (BocoUtils.getStrLength(webRoleInfo.getRoleName(), 2) > 200) {
            //��ɫ���Ƶĳ��Ȳ��ܳ���100!
            throw new SystemException("w418");
        }
        //��֤ͬ�����¼����ɫ�����Ƿ����
        WebRoleInfo existRoleName = new WebRoleInfo();
        existRoleName.setRoleName(webRoleInfo.getRoleName());
        existRoleName.setOrganLevel(webRoleInfo.getOrganLevel());
        List<WebRoleInfo> roles = selectByAttr(existRoleName);
        if (roles.size() != 0) {
            for (WebRoleInfo role : roles) {
                if (!(role.getRoleCode().equals(webRoleInfo.getRoleCode()) && role.getOrganLevel().equals(webRoleInfo.getOrganLevel()))) {
                    //�ü����£���ɫ�����Ѵ��ڣ�����������!
                    throw new SystemException("w420");
                }
            }
        }
        return true;
    }

    /**
     * TODO ɾ��У�����.
     *
     * @param webRoleInfo
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��23��    	  ����    �½�
     * </pre>
     */
    private boolean checkDeleteData(WebRoleInfo webRoleInfo) {
        //��֤�ý�ɫ���Ƿ��й�Ա����
        FdOper fdOper = new FdOper();
        fdOper.setOperdegree(webRoleInfo.getRoleCode());
        List<String> roleInfoList = webOperRoleMapper.selectRoleCodeListByRoleId(webRoleInfo.getRoleCode());
        if (CollectionUtils.isNotEmpty(roleInfoList)) {
            //�ý�ɫ�´��ڹ�Ա���޷�ɾ��!
            throw new SystemException("w421");
        }
        //��֤�ý�ɫ�Ƿ����δ�ջ�״̬��ת��Ȩ��Ϣ
        return true;
    }

    /**
     * TODO ��ɫ����ģ����ѯ��¼.
     *
     * @param webRoleInfo
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��3��8��    	  ����    �½�
     * </pre>
     */
    public List<WebRoleInfo> selectByLike(WebRoleInfo webRoleInfo) {
        return webRoleInfoMapper.selectByLike(webRoleInfo);
    }
    public List<WebRoleInfo> selectByLikeOrder(WebRoleInfo webRoleInfo) {
         return webRoleInfoMapper.selectByLikeOrder(webRoleInfo);
    }
    @Override
    public List<WebRoleInfo> selectByOrganLevel(String organLevel,String operCode) {
        return webRoleInfoMapper.selectByOrganLevel(organLevel,operCode);
    }


    /**
     * ���������ɫ����
     */
    @Override
    public List<Map<String, String>> selectRoleCode(WebRoleInfo webRoleInfo) {
        List<Map<String, String>> list = webRoleInfoMapper.selectRoleCode(webRoleInfo);
        return list;
    }

    /**
     * ���������ɫ����
     */
    @Override
    public List<Map<String, String>> selectRoleName(WebRoleInfo webRoleInfo) {
        List<Map<String, String>> list = webRoleInfoMapper.selectRoleName(webRoleInfo);
        return list;
    }

    /**
     * TODO ���ݽ�ɫ���뼯��ѯ��ɫ����.
     *
     * @param roleCode
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��5��26��    	  ����    �½�
     * </pre>
     */
    @Override
    public String findNameByProdCodes(String roleCode) {
        String roleName = "", roleCodes = "";
        if (StringUtils.isNotEmpty(roleCode)) {
            for (int i = 0; i < roleCode.length(); i = i + 3) {
                roleCodes += "," + roleCode.substring(i, i + 3);
            }
            if (roleCodes.length() > 0) {
                roleCodes = roleCodes.substring(1);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("roleCodes", roleCodes.split(","));
            List<WebRoleInfo> list = webRoleInfoMapper.selectByRoleCodes(map);
            if (list.size() > 0) {
                for (WebRoleInfo info : list) {
                    roleName += "," + info.getRoleName();
                }
            }
            if (roleName.length() > 0) {
                roleName = roleName.substring(1);
            }
        }
        return roleName;
    }

    @Override
    public String findNameByRoleCode(String roleCode) {
        String roleName = "";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleCodes", roleCode.split(","));
        List<WebRoleInfo> list = webRoleInfoMapper.selectByRoleCodes(map);
        if (list.size() > 0) {
            for (WebRoleInfo info : list) {
                roleName += "," + info.getRoleName();
            }
        }
        if (roleName.length() > 0) {
            roleName = roleName.substring(1);
        }
        return roleName;
    }
}