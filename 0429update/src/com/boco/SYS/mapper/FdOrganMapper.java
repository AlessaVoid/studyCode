package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.FdOrgan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * FdOrgan���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface FdOrganMapper extends GenericMapper<FdOrgan, java.lang.String> {
    /**
     * TODO ��ȡһ�ֻ���.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��3��4��    	  ����    �½�
     * </pre>
     */
    public List<FdOrgan> selectProvOrgan();

    String selectOrganName(String thiscode);

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
     * TODO ��ȡ��Ʒ���ۻ���
     *
     * @param areaCodes
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��10��9��    	   ������  �½�
     * </pre>
     */
    public List<FdOrgan> selectByAreaCodes(String[] organs);

    /**
     * ����,��������
     *
     * @param fdOrgan ����
     * @return
     */
    public List<String> selectByName(FdOrgan fdOrgan);

    /**
     * ����,��������
     *
     * @param fdOrgan ����
     * @return
     */
    public List<String> selectByThisCode(FdOrgan fdOrgan);

    /**
     * ���ݻ����ţ���ѯ�¼����л���
     * @param uporgan
     * @return
     */
    List<Map<String, Object>> selectByUporgan(String uporgan);

    List<FdOrgan> selectList(Map<String, Object> map);


    //��ѯ��ǰ����
    List<Map<String, Object>> selectByOrganCode(String thiscode);

    List<String> selectByLikeThisCode(FdOrgan fdOrgan);

    List<String> selectByLikeThisCodeZX(FdOrgan fdOrgan);

    List<String> selectByLikeName(FdOrgan fdOrgan);
    List<String> selectByLikeNameZX(String thiscode);

    String selectUporganByThisCodeList(String thiscode);//����thiscodelist��ѯuporgan

    String selectOrganNameBycode(String thiscode);

    List<String> selectthiscodeByLikeOrganname(String uporgan);//ͨ��organnameģ����ѯ��Ӧ��thiscode

    List<Map<String, Object>> selectByLikeOrganname(@Param("organname") String organname);

    //ȥ�������У����л������ʴ�
    List<FdOrgan> selectByAttr2(FdOrgan fdOrgan);
}