package com.boco.PUB.service.impl;


import com.boco.PUB.service.ITbReqDetailService;
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
 * �Ŵ�����¼����ϸ��ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbReqDetailService extends GenericService<TbReqDetail, Integer> implements ITbReqDetailService {
    //���Զ��巽��ʱʹ��
    //@Autowired
    //private TbReqDetailMapper mapper;

    @Autowired
    private TbReqDetailMapper tbReqDetailMapper;

    @Autowired
    private TbReqListMapper tbReqListMapper;


    /**
     * ��������������.
     *
     * @param tbReqDetail
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectReqdId(TbReqDetail tbReqDetail) {
        return tbReqDetailMapper.selectReqdId(tbReqDetail);
    }

    /**
     * ����������������.
     *
     * @param tbReqDetail
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectReqdRefId(TbReqDetail tbReqDetail) {
        return tbReqDetailMapper.selectReqdRefId(tbReqDetail);
    }


    /**
     * ��������ɾ��
     *
     * @param tbReqDetail
     * @return
     */
    @Override
    public int deleteByAttr(TbReqDetail tbReqDetail){
        return tbReqDetailMapper.deleteByAttr(tbReqDetail);
    }

    /**
     * ����������������.
     *
     * @param tbReqDetail
     * @param tbReqList
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<TbReqList> selectTbreqList(TbReqDetail tbReqDetail, TbReqList tbReqList) {

        List<TbReqList> tbReqListList = new ArrayList<>();
        List<TbReqList> list = tbReqListMapper.selectByAttr(tbReqList);
        List<TbReqDetail> tbReqDetailList = tbReqDetailMapper.selectByAttr(tbReqDetail);
        String organCode =tbReqDetail.getReqdOrgan();
        for (TbReqList tbReqList2 : list) {
            String organListStr = tbReqList2.getReqOrganList();
            if (organListStr.indexOf(organCode) != -1) {
                //��ͳһĬ�ϸ��·�
                tbReqList2.setReqState(TbReqDetail.STATE_ISSUED);
                for (TbReqDetail tbReqDetail1 : tbReqDetailList) {
                    if (tbReqList2.getReqId().equals(tbReqDetail1.getReqdRefId())) {
                        tbReqList2.setReqState(tbReqDetail1.getReqdState());
                        break;
                    }
                }
                if(tbReqList2.getReqState()<TbReqDetail.STATE_SUBMITED){
                    tbReqListList.add(tbReqList2);
                }
            }
        }

        return  tbReqListList;
    }


}