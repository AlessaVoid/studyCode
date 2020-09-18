package com.boco.SYS.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.GfDict;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * GfDictҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IGfDictService extends IGenericService<GfDict,HashMap<String,Object>>{

	Json insert(GfDict gfDict) throws Exception;

	Json update(GfDict gfDict) throws Exception;
	/**
	 * 
	 * 
	 * TODO ��ȡָ������ָ��ֵ���ֵ��ֵ
	 * 
	 * @param organCode
	 * @param operCode
	 * @return
	 * 
	 *         <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2014-12-1    	     �״���    �½�
	 * </pre>
	 */
	public String getValueByName(String accountName, String type);
	
	public String getNameByValue(String value, String type);
	
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
	/**
	 * 
	 *
	 * TODO ģ����ѯӢ����.
	 *
	 * @param gfDict
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��30��    	    �غ���   �½�
	 * </pre>
	 */
	List<Map<String, String>> selectDictNo(GfDict gfDict);
	/**
	 * 
	 *
	 * TODO ģ����ѯ������.
	 *
	 * @param gfDict
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��30��    	    �غ���   �½�
	 * </pre>
	 */
	List<Map<String, String>> selectDictName(GfDict gfDict);
	/**
	 * 
	 *
	 * TODO ģ����ѯ������Ա.
	 *
	 * @param gfDict
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��30��    	    �غ���   �½�
	 * </pre>
	 */
	List<Map<String, String>> selectCreateOper(GfDict gfDict);
	/**
	 * 
	 *
	 * TODO ��ѯDICT_KEY_IN,DICT_VAALUE_IN
	 *
	 * @param gfDict
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��25��    ���ǽ�   �½�
	 * </pre>
	 */
	List<GfDict> getDictKeyValue();
	
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
	List<GfDict> selectAreaList(GfDict gfDict);
	/**
	 * 
	 *
	 * TODO ��ѯ����
	 *
	 * @param channels
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��9��    	   ������  �½�
	 * </pre>
	 */
	List<GfDict> selectByCodes(String[] channels);
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