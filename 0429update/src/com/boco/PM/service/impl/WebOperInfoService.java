package com.boco.PM.service.impl;

import com.boco.PM.service.IFdOperService;
import com.boco.PM.service.IWebOperInfoService;
import com.boco.SYS.base.GenericService;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.line.exception.LineProductException;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.entity.WebRoleInfo;
import com.boco.TONY.biz.weboper.exception.OperLineException;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.Json;
import com.boco.SYS.util.MapUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * WebOperInfoҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class WebOperInfoService extends GenericService<WebOperInfo, HashMap<String, Object>> implements IWebOperInfoService {
    @Autowired
    private WebOperInfoMapper webOperInfoMapper;
    @Autowired
    private IFdOperService fdOperService;
    @Autowired
    WebOperLineMapper webOperLineMapper;
    @Autowired
    LineProductMapper lineProductMapper;
    @Autowired
    WebOperRoleMapper webOperRoleMapper;
    @Autowired
    WebRoleInfoMapper webRoleInfoMapper;
    @Autowired
    FdOrganMapper fdOrganMapper;

    private static final int LINE_USABLE = 1;

    /**
     * TODO ��ѯ�б�ҳ
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29     �غ���      �����½�
     * </pre>
     */
    @Override
    public Map<String, Object> select(WebOperInfo webOperInfo, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true, true, true);
        List<WebOperInfo> webOperInfoList = webOperInfoMapper.selectByLikeAttr(webOperInfo);
        String organLevel=webOperInfo.getOrganCode();
        //����ҳ��ķ�ҳ����
        Map<String, Object> results = new Hashtable<>();
        if (CollectionUtils.isEmpty(webOperInfoList)) {
            results.put("pager.pageNo", 1);
            results.put("pager.totalRows", 0);
            results.put("rows", new ArrayList<>());
        }
        PageInfo<WebOperInfo> page = new PageInfo<>(webOperInfoList);
        results.put("pager.pageNo", page.getPageNum());
        results.put("pager.totalRows", page.getTotal());

        WebOperLineDO webOperLineDO = new WebOperLineDO().setStatus(LINE_USABLE);
        List<WebOperInfo> collect = webOperInfoList.stream().peek(item -> {
            List<String> roleIdList = webOperRoleMapper.selectOwnRoleByOperCode(item.getOperCode());
            String roleName = "";
            for (String roleId : roleIdList) {
                WebRoleInfo webRoleInfo = webRoleInfoMapper.selectByPK(roleId);
                if (Objects.nonNull(webRoleInfo)) {
                    roleName += webRoleInfo.getRoleName() + ",";
                }
            }
            webOperLineDO.setOperCode(item.getOperCode());
            try {
                List<WebOperLineDO> webOperLineDOList = webOperLineMapper.getAllWebOperLineByOperCode(webOperLineDO);
                String lineName = "";
                for (WebOperLineDO operLineDO : webOperLineDOList) {
                    ProductLineInfoDO lineInfoDO = lineProductMapper.getProductLineInfoByLineId(operLineDO.getLineId());
                    if (Objects.nonNull(lineInfoDO)) {
                        lineName += lineInfoDO.getLineName() + ",";
                    }
                }
                if (StringUtils.isBlank(lineName)) {
                    lineName = "��";
                } else {
                    lineName = lineName.substring(0, lineName.length() - 1);
                }
                if (StringUtils.isBlank(roleName)) {
                    roleName = "��";
                } else {
                    roleName = roleName.substring(0, roleName.length() - 1);
                }
                item.setRoleName(roleName);
                item.setLineName(lineName);
            } catch (OperLineException e) {
                e.printStackTrace();
            }
        }).collect(Collectors.toList());
        results.put("rows", collect);
        return results;
    }

    @Override
    public Map<String, Object> selectList(Map map, int pageNum, int pageSize) throws OperLineException, LineProductException {
        return null;
    }

    /**
     * TODO ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29     �غ���      �����½�
     * </pre>
     */
    @Override
    public Json insert(WebOperInfo webOperInfo) throws Exception {
        //��������У�����
        boolean check = checkInsertData(webOperInfo);
        //��֤ʧ��
        if (check == false) {
            return this.json;
        }
        FdOper fdOper = new FdOper();
        fdOper.setOrgancode(webOperInfo.getOrganCode());
        fdOper.setOpercode(webOperInfo.getOperCode());
        try {
            fdOper = fdOperService.selectByPK(MapUtil.beanToMap(fdOper));
        } catch (Exception e) {
            return this.json.returnMsg("true", "����Ա��Ϣ�쳣������ϵ������Ա����ά��");
        }
        if (!"".equals(fdOper.getOpername())) {
            webOperInfo.setOperName(fdOper.getOpername());
        }
        int count = insertEntity(webOperInfo);
        //�������ݿ�ʧ��
        if (count == 1) {
            //"�����ɹ�!"
            return this.json.returnMsg("true", getErrorInfo("w456"));
        }
        //����ʧ��
        return this.json.returnMsg("false", getErrorInfo("w446"));

    }


    /**
     * TODO ����У��
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29     �غ���      �����½�
     * </pre>
     */
    private boolean checkInsertData(WebOperInfo webOperInfo) throws Exception {
        //�ж���û�д˹�Ա
        FdOper fdOper = new FdOper();
        fdOper.setOrgancode(webOperInfo.getOrganCode());
        fdOper.setOpercode(webOperInfo.getOperCode());
        FdOrgan fdOrgan = fdOrganMapper.selectByPK(webOperInfo.getOrganCode());
        if (Objects.isNull(fdOrgan)) {
            this.json.returnMsg("false", getErrorInfo("w423"));
            return false;
        }
        int count = fdOperService.countByAttr(fdOper);

        if (count == 0) {
            //"û�д˹�Ա������������!"
            this.json.returnMsg("false", getErrorInfo("w455"));
            return false;
        }
        //��֤��Ա�Ƿ��Ѵ���
        WebOperInfo operInfo = new WebOperInfo();
        operInfo.setOperCode(webOperInfo.getOperCode());
        operInfo.setOrganCode(webOperInfo.getOrganCode());
        int MenuNoCount = countByAttr(operInfo);
        if (MenuNoCount > 0) {
            //"��Ա�Ѵ��ڣ�����������!"
            this.json.returnMsg("false", getErrorInfo("w457"));
            return false;
        }
//        //��֤֤�������Ƿ��Ѵ���
//        WebOperInfo certificatecode = new WebOperInfo();
//        certificatecode.setCertificatecode(webOperInfo.getCertificatecode());
//        int certificatecodeCount = countByAttr(certificatecode);
//        if (certificatecodeCount > 0) {
//            //"֤�����Ѵ��ڣ�����������!"
//            this.json.returnMsg("false", getErrorInfo("w458"));
//            return false;
//        }
        return true;
    }

    /**
     * TODO ��������򣨹�Ա�ţ�
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29     �غ���      �����½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectOperCodeLike(WebOperInfo webOperInfo) {
        List<Map<String, String>> list = webOperInfoMapper.selectOperCodeLike(webOperInfo);
        return list;
    }

    @Override
    public WebOperInfo selectOperCode(WebOperInfo webOperInfo) {
        return webOperInfoMapper.selectOperCode(webOperInfo);
    }

    /**
     * TODO ���������������
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29     �غ���      �����½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectOperName(WebOperInfo webOperInfo) {
        List<Map<String, String>> list = webOperInfoMapper.selectOperName(webOperInfo);
        return list;
    }

    /**
     * TODO �޸�
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29     �غ���      �����½�
     * </pre>
     */
    @Override
    public Json update(WebOperInfo webOperInfo) throws Exception {
        //�����޸�У�����
        boolean check = checkUpdateData(webOperInfo);
        //��֤ʧ��
        if (check == false) {
            return this.json;
        }
        int count = updateByPK(webOperInfo);
        //�������ݿ�ʧ��
        if (count == 1) {
            //"�����ɹ�!"
            return this.json.returnMsg("true", getErrorInfo("w448"));
        }
        //����ʧ��
        return this.json.returnMsg("false", getErrorInfo("w447"));
    }

    /**
     * TODO �޸���֤.
     *
     * @param webOperInfo
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   �޸�����        �޸���    �޸�ԭ��
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   2016��5��13��    	    �غ���   �½�
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   </pre>
     */
    private boolean checkUpdateData(WebOperInfo webOperInfo) throws Exception {
		/*//��֤֤�������Ƿ��Ѵ���
		WebOperInfo certificatecode = new WebOperInfo();
		certificatecode.setCertificatecode(webOperInfo.getCertificatecode());
		int certificatecodeCount = countByAttr(certificatecode);
		if(certificatecodeCount > 0){
			//"֤�����Ѵ��ڣ�����������!"
			this.json.returnMsg("false", getErrorInfo("w458"));
			return false;
		}*/
        return true;
    }

    @Override
    public String selectOperCodeByName(String prodOperName) throws Exception {
        return webOperInfoMapper.selectOperCodeByName(prodOperName);
    }
}