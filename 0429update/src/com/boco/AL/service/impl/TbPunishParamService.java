package com.boco.AL.service.impl;


import com.boco.AL.service.ITbPunishParamService;
import com.boco.SYS.mapper.TbPunishParamMapper;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbPunishParam;
import com.boco.SYS.base.GenericService;

import java.util.List;
import java.util.Map;

/**
 * ��Ϣ������ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class TbPunishParamService extends GenericService<TbPunishParam, Integer> implements ITbPunishParamService {


    @Autowired
    private TbPunishParamMapper tbPunishParamMapper;

    /**
     * ��ѯtbPunishParam
     *
     * @param ppId
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public TbPunishParam selectByPpId(Integer ppId) throws Exception {
        return tbPunishParamMapper.selectByPK(ppId);
    }

    /**
     * ����tbPunishParam����
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public Json deploy() throws Exception {

        //TODO �漰���������沿��  1.�õ����й��� 2.ƴװ�ַ��� 3.��װ������ʵ���� 4.�ӵ�����������5.����


        return json.returnMsg("true", "����ɹ�!");
    }

    /**
     * ����tbPunishParam
     *
     * @param tbPunishParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public Json insertTbPunishParam(TbPunishParam tbPunishParam) throws Exception {

        //��������У�����


        int count = insertEntity(tbPunishParam);
        //�������ݿ�ʧ��
        if (count == 1) {
            //"�����ɹ�!"
            return this.json.returnMsg("true", getErrorInfo("w456"));
        }
        //����ʧ��
        return this.json.returnMsg("false", getErrorInfo("w446"));

    }

    /**
     * �޸�tbPunishParam
     *
     * @param tbPunishParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public Json updateTbPunishParam(TbPunishParam tbPunishParam) throws Exception {
        //��������У�����
        boolean check = true; //TODO У����ֵ�߼����� ��1-6 ��4-8 �������⣩

        if (check == false) {
            return this.json;
        }
        tbPunishParam.setUpdatetime(BocoUtils.getTime());
        int count = updateByPK(tbPunishParam);
        //�������ݿ�ʧ��
        if (count == 1) {
            //"�޸ĳɹ�!"
            return this.json.returnMsg("true", getErrorInfo("w448"));
        }
        //�޸�ʧ��
        return this.json.returnMsg("false", getErrorInfo("w447"));

    }

    /**
     *  ��������������.
     *
     * @param tbPunishParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, Integer>> selectPpId(TbPunishParam tbPunishParam) {
        List<Map<String, Integer>> list = tbPunishParamMapper.selectPpId(tbPunishParam);
        return list;
    }

    /**
     *  ���������������.
     *
     * @param tbPunishParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectPpName(TbPunishParam tbPunishParam) {
        List<Map<String, String>> list = tbPunishParamMapper.selectPpName(tbPunishParam);
        return list;
    }

    /**
     *  ��������������.
     *
     * @param tbPunishParam
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     *  2019��9��19��    	    txn   �½�
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectPpOrgan(TbPunishParam tbPunishParam) {
        List<Map<String, String>> list = tbPunishParamMapper.selectPpOrgan(tbPunishParam);
        return list;
    }
    //���Զ��巽��ʱʹ��
    //@Autowired
    //private TbPunishParamMapper mapper;
}