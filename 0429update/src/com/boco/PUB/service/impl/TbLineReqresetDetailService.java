package com.boco.PUB.service.impl;

import java.util.*;

import com.boco.PUB.service.ITbLineReqresetDetailService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.mapper.LineProductMapper;
import com.boco.SYS.mapper.TbLineReqresetDetailMapper;
import com.boco.SYS.mapper.WebOperLineMapper;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbLineReqresetDetail;
import com.boco.SYS.base.GenericService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * �������������¼�����ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbLineReqresetDetailService extends GenericService<TbLineReqresetDetail, Integer> implements ITbLineReqresetDetailService {
    //���Զ��巽��ʱʹ��
    //@Autowired
    //���Զ��巽��ʱʹ��
    @Autowired
    private TbLineReqresetDetailMapper tbLineReqresetDetailMapper;
    @Autowired
    WebOperLineMapper webOperLineMapper;
    @Autowired
    LineProductMapper lineProductMapper;

    /**
     * ����������������.
     *
     * @param tbLineReqresetDetail
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> showLineReqResetMonth(TbLineReqresetDetail tbLineReqresetDetail) {
        List<Map<String, String>> list = tbLineReqresetDetailMapper.showLineReqResetMonth(tbLineReqresetDetail);
        return list;
    }

    @Override
    public List<TbLineReqresetDetail> getAll(TbLineReqresetDetail tbLineReqresetDetail, FdOper fdOper) throws Exception {
        List<TbLineReqresetDetail> tbLineReqDetails = new ArrayList<>();
        List<TbLineReqresetDetail> list = tbLineReqresetDetailMapper.selectByAttr(tbLineReqresetDetail);
        WebOperLineDO webOperLineDO = new WebOperLineDO().setStatus(1);
        webOperLineDO.setOperCode(fdOper.getOpercode());
        List<WebOperLineDO> webOperLineDOList = webOperLineMapper.getAllWebOperLineByOperCode(webOperLineDO);
        if (list != null && list.size() > 0) {
            for (WebOperLineDO operLineDO : webOperLineDOList) {
                ProductLineInfoDO lineInfoDO = lineProductMapper.getProductLineInfoByLineId(operLineDO.getLineId());
                if (Objects.nonNull(lineInfoDO)) {
                    String lineId = lineInfoDO.getLineId();
                    for (TbLineReqresetDetail tb : list) {
                        if (lineId.equals(tb.getLineCode())) {
                            tbLineReqDetails.add(tb);
                        }
                    }
                }
            }
        }
        return tbLineReqDetails;
    }

    @Override
    public List<TbLineReqresetDetail> selectAll(TbLineReqresetDetail tbLineReqresetDetail, FdOper fdOper) {
        List<TbLineReqresetDetail> tbLineReqDetails = new ArrayList<>();
        try {
            List<TbLineReqresetDetail> list = tbLineReqresetDetailMapper.selectByAttr(tbLineReqresetDetail);
            WebOperLineDO webOperLineDO = new WebOperLineDO().setStatus(1);
            webOperLineDO.setOperCode(fdOper.getOpercode());
            List<WebOperLineDO> webOperLineDOList = webOperLineMapper.getAllWebOperLineByOperCode(webOperLineDO);
            if (list != null && list.size() > 0) {
                for (WebOperLineDO operLineDO : webOperLineDOList) {
                    ProductLineInfoDO lineInfoDO = lineProductMapper.getProductLineInfoByLineId(operLineDO.getLineId());
                    if (Objects.nonNull(lineInfoDO)) {
                        String lineId = lineInfoDO.getLineId();
                        for (TbLineReqresetDetail tb : list) {
                            if (lineId.equals(tb.getLineCode())) {
                                tbLineReqDetails.add(tb);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tbLineReqDetails;
    }

}