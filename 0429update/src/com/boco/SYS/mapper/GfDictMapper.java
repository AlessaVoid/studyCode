package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.entity.GfDict;
import com.boco.SYS.base.GenericMapper;
/**
 * 
 * 
 * GfDict���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface GfDictMapper extends GenericMapper<GfDict,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO ��ȡ��ʶ���.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	public Integer selectOrder(GfDict gfDict);

	public List<Map<String, String>> selectDictNo(GfDict gfDict);

	public List<Map<String, String>> selectDictName(GfDict gfDict);

	public List<Map<String, String>> selectCreateOper(GfDict gfDict);
	public int countBy(GfDict gfDict) throws DataAccessException;

	/**
	 * 
	 */
	/**
	 * 
	 *
	 * TODO ��ѯDICT_KEY_IN,DICT_VAALUE_IN
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��4��    ���ǽ�   �½�
	 * </pre>
	 */
	public List<GfDict> getDictKeyValue();
	/**
	 * 
	 *
	 * TODO ��ѯ���������б�
	 * @param gfDict 
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��16��    	  ������    �½�
	 * </pre>
	 */
	public List<GfDict> selectAreaList(GfDict gfDict);

	public List<GfDict> selectByCodes(String[] channels);
	/**
	 * 
	 *
	 * TODO ͨ���ֵ����ͼ��ֵ�����ֵ��ѯ
	 *
	 * @param map
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��12��    	   ������  �½�
	 * </pre>
	 */
	public List<GfDict> findByValINKeys(Map<String, Object> map);

	public String selectKeyByNoAndVal(String ketIn, String dictNo);
}