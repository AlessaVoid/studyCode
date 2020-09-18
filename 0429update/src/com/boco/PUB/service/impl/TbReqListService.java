package com.boco.PUB.service.impl;

import com.boco.PUB.service.ITbReqListService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.entity.TbReqList;
import com.boco.SYS.mapper.FdOrganMapper;
import com.boco.SYS.mapper.TbReqDetailMapper;
import com.boco.SYS.mapper.TbReqListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * �·��Ŵ�������Ҫ���ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbReqListService extends GenericService<TbReqList, Integer> implements ITbReqListService {
    //���Զ��巽��ʱʹ��

    @Autowired
    private TbReqListMapper tbReqListMapper;


    @Autowired
    private FdOrganMapper fdOrganMapper;


    @Autowired
    private TbReqDetailMapper tbReqDetailMapper;


    /**
     * �������뱨��Ҫ����.
     *
     * @param tbReqList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectReqId(TbReqList tbReqList) {
        List<Map<String, Integer>> list = tbReqListMapper.selectReqId(tbReqList);
        return list;
    }

    /**
     * �������뱨��Ҫ����.
     *
     * @param tbReqList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> showReqId(TbReqList tbReqList) {
        List<Map<String, Integer>> list = tbReqListMapper.showReqId(tbReqList);
        return list;
    }

    /**
     * ����������������.
     *
     * @param tbReqList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectReqMonth(TbReqList tbReqList) {
        List<Map<String, String>> list = tbReqListMapper.selectReqMonth(tbReqList);
        return list;
    }
    /**
     * ����������������.
     *
     * @param tbReqList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectReqOrgan(TbReqList tbReqList) {
        List<Map<String, String>> list = tbReqListMapper.selectReqOrgan(tbReqList);
        return list;
    }

    /**
     * ����������������.
     *
     * @param tbReqList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> showReqMonth(TbReqList tbReqList) {
        List<Map<String, String>> list = tbReqListMapper.showReqMonth(tbReqList);
        return list;
    }

    /**
     * �����·�״̬
     *
     * @param id,state
     * @return
     */
    @Override
    public int updateReqState(Integer id, int state) {
        TbReqList tbReqList = new TbReqList();
        tbReqList.setReqId(id);
        tbReqList.setReqState(state);
        return tbReqListMapper.updateReqState(tbReqList);

    }

    /**
     * ��reqToΪ������ѯ��¼
     *
     * @param reqTo, organCode
     * @return
     */
    @Override
    public List<TbReqList> selectByReqTo(Integer reqTo, String organCode,List<TbReqDetail> tbReqDetailList) {
        TbReqList search = new TbReqList();
        search.setReqOrgan(organCode);
        search.setReqTo(reqTo);

        List<TbReqList> list = tbReqListMapper.selectByAttr(search);

        List<TbReqList> tbReqListList = new ArrayList<>();
        for (TbReqList tbReqList : list) {
            //��ͳһĬ�ϸ��·�
            tbReqList.setReqState(TbReqDetail.STATE_ISSUED);
            for (TbReqDetail tbReqDetail1 : tbReqDetailList) {
                if (tbReqList.getReqId().equals(tbReqDetail1.getReqdRefId())) {
                    tbReqList.setReqState(tbReqDetail1.getReqdState());
                    break;
                }
            }
            tbReqListList.add(tbReqList);
        }
        return  tbReqListList;
    }

    /**
     * ��ѯreqToΪ0�ļ�¼
     *
     * @return
     */
    @Override
    public List<TbReqList> getMonth() {
        return tbReqListMapper.getMonth();
    }

    /**
     * �Ŵ������·�֮�� ���ɸ����ε��¼�����ͳ������
     *
     * @param reqId ����id
     * @return
     */
    @Override
    public void checkChildOrganNum(Integer reqId) {
        TbReqList tbReqList = tbReqListMapper.selectByPK(reqId);
        FdOrgan fdOrgan = fdOrganMapper.selectByPK(tbReqList.getReqOrgan());
        int organLevel = Integer.parseInt(fdOrgan.getOrganlevel());
        //��һ������Ҫ�����
        int organNum = 0;
        FdOrgan fdOrgan1 = new FdOrgan();
        //��֧������-һ��|һ��-����
        if (organLevel == 0) {
            fdOrgan1.setOrganlevel("1");
            fdOrgan1.setCountrycode(fdOrgan.getThiscode());
            organNum = fdOrganMapper.selectByAttr(fdOrgan1).size();
        } else if (organLevel == 1) {
            fdOrgan1.setOrganlevel("2");
            fdOrgan1.setProvincecode(fdOrgan.getThiscode());
            organNum = fdOrganMapper.selectByAttr(fdOrgan1).size();
        }
        //ͳ�������������
        TbReqDetail tbReqDetail = new TbReqDetail();
        tbReqDetail.setReqdRefId(reqId);
        tbReqDetail.setReqdState(TbReqDetail.STATE_APPROVED);
        int reqDetailNum = tbReqDetailMapper.selectByAttr(tbReqDetail).size();

        tbReqList.setReqId(reqId);
        if (reqDetailNum == organNum) {
            tbReqList.setReqState(TbReqList.STATE_COMPLETE);
        } else {
            tbReqList.setReqState(TbReqList.STATE_UNDONE);
        }
        tbReqListMapper.updateByPK(tbReqList);
    }


}