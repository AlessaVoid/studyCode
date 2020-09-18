package com.boco.PM.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebRoleInfo;
import com.boco.SYS.util.Json;

import java.util.List;
import java.util.Map;

/**
 * WebRoleInfoҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebRoleInfoService extends IGenericService<WebRoleInfo, String> {
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
    public Json InsertWebRoleInfo(WebRoleInfo webRoleInfo, FdOper fdOper, String funCodes) throws Exception;

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
    public Json updateWebRoleInfo(WebRoleInfo webRoleInfo, FdOper fdOper, String funCodes) throws Exception;

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
    public Json deleteWebRoleInfo(WebRoleInfo webRoleInfo) throws Exception;

    /**
     * TODO ��ɫ����ģ����ѯ��¼.
     *
     * @param webRoleInfo
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��3��8��    	  ����    �½�
     * </pre>
     */
    public List<WebRoleInfo> selectByLike(WebRoleInfo webRoleInfo);
    public List<WebRoleInfo> selectByLikeOrder(WebRoleInfo webRoleInfo);
    public List<WebRoleInfo> selectByOrganLevel(String organLevel, String operCode);

    /**
     * TODO ���������ɫ���.
     *
     * @param webRoleInfo
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��4��11��    	    �غ���   �½�
     * </pre>
     */
    public List<Map<String, String>> selectRoleCode(WebRoleInfo webRoleInfo);

    /**
     * TODO ���������ɫ����.
     *
     * @param webRoleInfo
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��4��11��    	    �غ���   �½�
     * </pre>
     */
    public List<Map<String, String>> selectRoleName(WebRoleInfo webRoleInfo);

    /**
     * TODO ���ݽ�ɫ���뼯��ѯ��ɫ����.
     *
     * @param roleCode
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��5��26��    	  ����    �½�
     * </pre>
     */
    public String findNameByProdCodes(String roleCode);

    /**
     * TODO ���ݽ�ɫ���뼯��ѯ��ɫ����.
     *
     * @param roleCode
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��8��17��    	   ������  �½�
     * </pre>
     */
    public String findNameByRoleCode(String roleCode);
}