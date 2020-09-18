package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.FdOper;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FdOper���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface FdOperMapper extends GenericMapper<FdOper, HashMap<String, Object>> {
    /**
     * TODO ͨ����ɫ���ҹ�Ա�б�.
     *
     * @param roleCode
     * @return
     * @throws DataAccessException <pre>
     *                                                         �޸�����        �޸���    �޸�ԭ��
     *                                                         2016��2��26��    	    ����    �½�
     *                                                         </pre>
     */
    public List<FdOper> selectOpersByRole(String roleCode) throws DataAccessException;

    String selectOperName(String opercode) throws Exception;

    String selectOpersOrgan(String opercode)throws Exception;

    /**
     * TODO ���ɫ���ܱ������ѯ��Ȩ�û������б�����.
     *
     * @param query
     * @return
     * @throws Exception <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2016��3��7��    	  ����    �½�
     *                                     </pre>
     */
    public List<Map<String, String>> getPowerList(Map<String, String> query) throws Exception;

    /**
     * TODO �������̣� ���ɫ���ܱ������ѯ��Ȩ�����б�����.
     *
     * @param query
     * @return
     * @throws Exception <pre>
     *                                     �޸�����        �޸���    �޸�ԭ��
     *                                     2016��4��5��    	  ����    �½�
     *                                     </pre>
     */
    public List<Map<String, String>> getPowerListByRole(Map<String, Object> query) throws Exception;

    /**
     * �޸��û�����
     */
    void updateOperPassword(FdOper fdOper);

    void resetOperPassword(FdOper fdOper);

    void updateOperOrgan(FdOper fdOper);

    List<FdOper> selectByLikeStartAttr(FdOper fdOper);
}