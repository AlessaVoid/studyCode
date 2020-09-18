package com.boco.PM.service.impl;

import com.boco.PM.service.IFdOrganService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.mapper.FdOrganMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FdOrganҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class FdOrganService extends GenericService<FdOrgan, java.lang.String> implements IFdOrganService {
    @Autowired
    private FdOrganMapper fdOrganMapper;


    /**
     * TODO ��ȡһ�ֻ���.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��3��4��    	  ����    �½�
     * </pre>
     */
    public List<FdOrgan> selectProvOrgan() {
        return fdOrganMapper.selectProvOrgan();
    }


    /**
     * TODO ��ѯĳ������ֱ���¼�����
     *
     * @param thiscode
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��4��5��    	  ������    �½�
     * </pre>
     */
    public List<Map<String, String>> findNextOrganCodeList(String thiscode) {
        return fdOrganMapper.findNextOrganCodeList(thiscode);
    }

    /**
     * TODO ��ȡ��������.
     *
     * @param organs
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��3��30��    	  ����    �½�
     * </pre>
     */
    public List<Map<String, String>> selectProvName(String[] organs) {
        return fdOrganMapper.selectProvName(organs);
    }


    @Override
    public List<FdOrgan> selectByAreaCodes(String[] organs) {
        return fdOrganMapper.selectByAreaCodes(organs);
    }

    /**
     * �����������
     *
     * @param name ��������
     * @return
     */
    @Override
    public List<String> selectByName(FdOrgan fdOrgan) {
        List<String> nameList = fdOrganMapper.selectByLikeName(fdOrgan);
        return nameList;
    }
    @Override
    public List<String> selectByNameZX(String thiscode) {
        List<String> nameList = fdOrganMapper.selectByLikeNameZX(thiscode);
        return nameList;
    }


    /**
     * �����ϼ�����
     *
     * @param uporgan  �ϼ�����
     * @return
     */
    @Override
    public String selectUporganByThisCodeList(String thiscode) {
        String uporganList = fdOrganMapper.selectUporganByThisCodeList(thiscode);
        return uporganList;
    }
    /**
     * ��ѯOrganName
     *
     * @param thiscode ��������
     * @return
     */
    @Override
    public String selectOrganName(String thiscode) {
        String OrganName = fdOrganMapper.selectOrganNameBycode(thiscode);
        return OrganName;
    }




    /**
     * �����������
     *
     * @param thisCode ��������
     * @return
     */
    @Override
    public List<String> selectByThisCode(FdOrgan fdOrgan) {
        List<String> codeList = fdOrganMapper.selectByLikeThisCode(fdOrgan);
        return codeList;
    }

    @Override
    public List<String> selectByThisCodeZX(FdOrgan fdOrgan) {
        List<String> codeList = fdOrganMapper.selectByLikeThisCodeZX(fdOrgan);
        return codeList;
    }

    @Override
    public List<FdOrgan> selectList(Map<String, Object> map) {

        List<FdOrgan> list = fdOrganMapper.selectList(map);
        return list;
    }


    @Override
    public List<FdOrgan> selectByLevel(Map<String, Object> map) {
        List<FdOrgan> list = fdOrganMapper.selectByLevel(map);
        return list;
    }


    @Override
    public List<Map<String, Object>> selectByUporgan(String uporgan) {

        return fdOrganMapper.selectByUporgan(uporgan);
    }

    @Override
    public List<Map<String, Object>> selectByOrganCode(String thiscode) {
        List<Map<String, Object>> organList = fdOrganMapper.selectByOrganCode(thiscode);
        //������
        for (Map<String, Object> map : organList) {
            if ("11005293".equals(map.get("thiscode"))) {
                map.put("organname", "����");
                break;
            }
        }
        return organList;
    }

    /*���ݻ����Ų�ѯҪչʾ���¼�����*/
    @Override
    public List<Map<String, Object>> selectOrgan(String thiscode) {

        FdOrgan thisOrgan = fdOrganMapper.selectByPK(thiscode);
        FdOrgan searchOrgan = new FdOrgan();
        searchOrgan.setUporgan(thisOrgan.getThiscode());
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr2(searchOrgan);


        //һ�ֲ�ѯ2�ֻ����ͼӱ���
        if ("1".equals(thisOrgan.getOrganlevel())) {
            FdOrgan searchOrgan_1 = new FdOrgan();
            searchOrgan_1.setProvincecode(thisOrgan.getThiscode());
            searchOrgan_1.setOrganlevel("1");
            List<FdOrgan> organList_1 = fdOrganMapper.selectByAttr(searchOrgan_1);
            FdOrgan fdOrgan = organList_1.get(0);
            fdOrgan.setOrganname(fdOrgan.getOrganname() + "����");
            fdOrganList.add(fdOrgan);
        }

        //���в�ѯ���������Լ�
        String organStr3 = "11005293";
        if (organStr3.contains(thisOrgan.getThiscode())) {
            FdOrgan searchOrgan_1 = new FdOrgan();
            searchOrgan_1.setThiscode(organStr3);
            List<FdOrgan> organList_3 = fdOrganMapper.selectByAttr(searchOrgan_1);
            for (FdOrgan fdOrgan1 : organList_3) {
                fdOrganList.add(fdOrgan1);
            }
        }

        //ת����ʽ
        List<Map<String, Object>> organList = new ArrayList<>();
        for (FdOrgan fdOrgan : fdOrganList) {
            Map<String, Object> organMap = new HashMap<>(2);
            organMap.put("organcode", fdOrgan.getThiscode());
            organMap.put("thiscode", fdOrgan.getThiscode());
            organMap.put("organname", fdOrgan.getOrganname());
            organMap.put("uporgan", fdOrgan.getUporgan());
            organList.add(organMap);
        }

        //������
        for (Map<String, Object> map : organList) {
            if ("11005293".equals(map.get("thiscode"))) {
                map.put("organname", "����");
                break;
            }
        }
        return organList;
    }


}