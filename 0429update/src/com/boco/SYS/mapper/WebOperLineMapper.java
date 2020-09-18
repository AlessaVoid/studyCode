package com.boco.SYS.mapper;

import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import com.boco.TONY.biz.weboper.exception.OperLineException;

import java.util.List;

/**
 * ��Ա-����
 *
 * @author tony
 * @describe WebOperLineMapper
 * @date 2019-09-24
 */
public interface WebOperLineMapper {

    /**
     * �����Ա-���ߵĹ�ϵ
     */
    void insertWebOperLine(WebOperLineDO webOperLineDO) throws OperLineException;

    /**
     * ���¹�Ա-���ߵĹ�ϵ
     */
    void updateWebOperLine(WebOperLineDO webOperLineDO) throws OperLineException;

    /**
     * ɾ����Ա-���ߵĹ�ϵ
     */
    void deleteWebOperLine(WebOperLineDO webOperLineDO) throws OperLineException;

    /**
     * ɾ����Ա�����е�����
     *
     * @param operCode ��Ա��
     * @throws OperLineException
     */
    void deleteAllWebOperLineByOperCode(String operCode) throws OperLineException;

    /**
     * ���ݹ�Ա�Ų�ѯ����������
     *
     * @param webOperLineDO ��Ա��Ϣ
     * @return WebOperLineDOList
     * @throws OperLineException �쳣
     */
    List<WebOperLineDO> getAllWebOperLineByOperCode(WebOperLineDO webOperLineDO) throws OperLineException;

    /**
     * ����Ա�����Ƿ��Ѿ�����,��ֹ�����洢,���¼�¼�ظ�
     *
     * @param webOperLineDO ��Ա����DO
     * @return WebOperLineDO
     * @throws OperLineException ��Ա�����쳣
     */
    WebOperLineDO checkWebOperLineIsExist(WebOperLineDO webOperLineDO) throws OperLineException;

    /**
     *
     * ����opercodeɾ��
     * @param opercode
     * @return
     * @throws OperLineException
     */


    void deleteWebOperLineByOpercode(String opercode)throws OperLineException;
}
