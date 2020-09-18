package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebRoleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * WebRoleInfo���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebRoleInfoMapper extends GenericMapper<WebRoleInfo, String> {
    /**
     * TODO ��ɫ����ģ����ѯ��¼.
     *
     * @param webRoleInfo
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��3��8��    	  ����    �½�
     * </pre>
     */
    List<WebRoleInfo> selectByLike(WebRoleInfo webRoleInfo);
    List<WebRoleInfo> selectByLikeOrder(WebRoleInfo webRoleInfo);
    List<WebRoleInfo> selectByOrganLevel(@Param(value = "organLevel") String organLevel, @Param(value = "operCode") String operCode);

    List<WebRoleInfo> selectBySuperAdmin(String organLevel);
    List<WebRoleInfo> selectByOrganLevelZero(@Param(value = "organLevelZero") String organLevelZero);
    List<WebRoleInfo> selectByOrganLevelOne(@Param(value = "organLevelOne") String organLevelOne);
    List<WebRoleInfo> selectByOrganLevelTwo(@Param(value = "organLevelTwo") String organLevelTwo);
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
     * TODO ���ݽ�ɫ���뼯��ѯ����.
     *
     * @param map
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��5��26��    	  ����    �½�
     * </pre>
     */
    public List<WebRoleInfo> selectByRoleCodes(Map map);

}