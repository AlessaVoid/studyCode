package com.boco.PM.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOrgan;

import java.util.List;
import java.util.Map;

/**
 * FdOrganҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IFdOrganService extends IGenericService<FdOrgan, java.lang.String> {
    /**
     * TODO ��ȡһ�ֻ���.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��3��4��    	  ����    �½�
     * </pre>
     */
    public List<FdOrgan> selectProvOrgan();

    /**
     * TODO ��ѯĳ������ֱ���¼�����
     *
     * @param thiscode
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��4��5��    	  ������    �½�
     * </pre>
     */
    public List<Map<String, String>> findNextOrganCodeList(String thiscode);

    /**
     * TODO ��ȡ��������.
     *
     * @param organs
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��3��30��    	  ����    �½�
     * </pre>
     */
    public List<Map<String, String>> selectProvName(String[] organs);

    /**
     * TODO ��ѯ��Ʒ����
     *
     * @param areaCodes
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��10��9��    	   ������  �½�
     * </pre>
     */
    public List<FdOrgan> selectByAreaCodes(String[] organs);

    /**
     * ͨ����������
     *
     * @param fdOrgan ��������
     * @return
     */
    public List<String> selectByName(FdOrgan fdOrgan);
    public List<String> selectByNameZX(String thiscode);


    /**
     * ͨ���ϼ���������
     *
     * @param fdOrgan �ϼ�����
     * @return
     */
    public String selectUporganByThisCodeList(String thiscode);
    /**
     * ͨ��thiscode��ѯOrganName
     *
     * @param fdOrgan �ϼ�����
     * @return
     */
    public String selectOrganName(String thiscode);

    /**
     * ͨ������������
     *
     * @param fdOrgan ��������
     * @return
     */
    List<String> selectByThisCode(FdOrgan fdOrgan);

    List<String> selectByThisCodeZX(FdOrgan fdOrgan);

    List<FdOrgan> selectList(Map<String,Object> map);


    List<FdOrgan> selectByLevel(Map<String,Object> map);

    /**
     * ���ݻ����ţ���ѯ�¼����л���
     * @param uporgan
     * @return
     */
    List<Map<String, Object>> selectByUporgan(String uporgan);

    //��ѯ��ǰ����
    List<Map<String, Object>> selectByOrganCode(String thiscode);

    /**
     * @Description ���ݻ����Ų�ѯҪչʾ���¼�����
     * @Author liujinbo
     * @Date 2020/3/13
     * @param thiscode
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectOrgan(String thiscode);
}