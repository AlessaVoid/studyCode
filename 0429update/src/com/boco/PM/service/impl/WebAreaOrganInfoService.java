package com.boco.PM.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.PM.service.IWebAreaOrganInfoService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.GfDict;
import com.boco.SYS.entity.WebAreaOrganInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.WebAreaOrganInfoMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.service.IGfDictService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;
import com.boco.SYS.util.MapUtil;

/**
 * 
 * 
 * WebAreaOrganInfoҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class WebAreaOrganInfoService extends GenericService<WebAreaOrganInfo,HashMap<String,Object>> implements IWebAreaOrganInfoService{
	@Autowired
	private WebAreaOrganInfoMapper webAreaOrganInfoMapper;
	@Autowired
	private IGfDictService gfDictService;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	/**
	 * 
	 *
	 * TODO ����������Ϣ.
	 *
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public Json insertWebAreaOrgan(WebAreaOrganInfo webAreaOrganInfo,String organs,FdOper fdOper) throws Exception{
		//��֤������Ϣ
		boolean check = checkInsertData(webAreaOrganInfo,organs);
		//У��ʧ��
		if(check == false){
			return json;
		}
		
		//���������Ϣ
		GfDict gfDict = new GfDict();
		gfDict.setDictNo(webAreaOrganInfo.getDictNo());
		gfDict.setDictName(webAreaOrganInfo.getDictName());
		//��ȡ��ʶ���
		int dictNoOrder = gfDictService.selectOrder(gfDict);
		gfDict.setDictNoOrder(dictNoOrder+1);
		gfDict.setDictKeyIn(webAreaOrganInfo.getAreaCode());
		gfDict.setDictValueIn(webAreaOrganInfo.getAreaName());
		gfDict.setStatus("1");
		gfDict.setCreateOper(fdOper.getOpercode());
		//��ǰ���ڡ���ǰʱ��
		gfDict.setUpdateDate(fdBusinessDateService.getCommonDateDetails());
		gfDict.setUpdateTime(BocoUtils.getNowTime());
		json = gfDictService.insert(gfDict);
		if("false".equals(json.getSuccess())){
			//{0}�Ѵ���
			throw new SystemException("w380","�������");
		}
		
		//��������������Ӧ��Ϣ
		if(StringUtils.isNotEmpty(organs)){
			insertBatchWebArea(webAreaOrganInfo,organs,fdOper);
		}
		return json.returnMsg("true", "�����ɹ�");
	}
	/**
	 * 
	 *
	 * TODO �޸ĵ�����Ϣ.
	 *
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json updateWebAreaOrgan(WebAreaOrganInfo webAreaOrganInfo,String organs, FdOper fdOper) throws Exception {
		//��֤������Ϣ
		boolean check = checkUpdateData(webAreaOrganInfo,organs);
		//У��ʧ��
		if(check == false){
			return json;
		}
		//�޸ĵ�����Ϣ
		GfDict gfDict = new GfDict();
		gfDict.setDictNo(webAreaOrganInfo.getDictNo());
		gfDict.setDictName(webAreaOrganInfo.getDictName());
		gfDict.setDictKeyIn(webAreaOrganInfo.getAreaCode());
		gfDict.setDictValueIn(webAreaOrganInfo.getAreaName());
		gfDict.setCreateOper(fdOper.getOpercode());
		gfDict.setUpdateDate(fdBusinessDateService.getCommonDateDetails());
		gfDict.setUpdateTime(BocoUtils.getNowTime());
		gfDictService.update(gfDict);
		//ɾ�����������Ӧ�ĵ�����Ϣ
		webAreaOrganInfoMapper.deleteByAreaCode(webAreaOrganInfo.getAreaCode());
		//����������Ϣ
		if(StringUtils.isNotEmpty(organs)){
			insertBatchWebArea(webAreaOrganInfo,organs,fdOper);
		}
		return json.returnMsg("true", "�޸ĳɹ�");
	}
	/**
	 * 
	 *
	 * TODO ɾ��������Ϣ.
	 *
	 * @param areaCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public void deleteWebAreaOrgan(WebAreaOrganInfo webAreaOrganInfo) throws Exception {
		//�޸ĵ�����Ϣ
		GfDict gfDict = new GfDict();
		gfDict.setDictNo(webAreaOrganInfo.getDictNo());
		gfDict.setDictName(webAreaOrganInfo.getDictName());
		gfDict.setDictKeyIn(webAreaOrganInfo.getAreaCode());
		gfDictService.deleteByPK(MapUtil.beanToMap(gfDict));
		//ɾ��������Ϣ
		webAreaOrganInfoMapper.deleteByAreaCode(webAreaOrganInfo.getAreaCode());
	}
	/**
	 * 
	 *
	 * TODO �޸�У�����.
	 *
	 * @param webAreaOrganInfo
	 * @param organs
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	private boolean checkUpdateData(WebAreaOrganInfo webAreaOrganInfo,String organs) throws Exception {
		if(!StringUtils.isNotEmpty(webAreaOrganInfo.getAreaName())){
			//{0}����Ϊ��
			throw new SystemException("w384","��������");
		}
		if(BocoUtils.getStrLength(webAreaOrganInfo.getAreaName(), 2) > 256){
			//{0}�ĳ��Ȳ��ܳ���{1}
			throw new SystemException("w373","��������","256");
		}
		String req = "[\u4e00-\u9fa5]*+[a-zA-Z]*+[0-9]*+";
		if(Pattern.matches(req,webAreaOrganInfo.getAreaName()) == false){
			//{0}�����Ƿ��ַ�
			throw new SystemException("w356","��������");
		}
		String hasOrgan = "";
		//��ȡ�ѱ�ѡ��Ļ�����Ϣ
		List<WebAreaOrganInfo> organList = webAreaOrganInfoMapper.selectNotEqualOrgan(webAreaOrganInfo.getAreaCode());
		if(organList.size() != 0){
			for(WebAreaOrganInfo areaOrgan:organList){
				hasOrgan += areaOrgan.getProvCode() + ",";
			}
			if(!"".equals(hasOrgan)){
				hasOrgan = hasOrgan.substring(0, hasOrgan.length() - 1);
			}
		}
		//��֤��ѡ�Ļ����Ƿ��ѱ�����������ѡ��
		if(StringUtils.isNotEmpty(organs) && !"��".equals(organs)){
			String[] organ = organs.split(",");
			for(int i = 0; i < organ.length; i++){
				if(hasOrgan.indexOf(organ[i]) != -1){
					json.returnMsg("false", this.getErrorInfo("w392",organ[i]+","));
					return false;
				}
			}
		}
		//��֤���������Ƿ��ظ�
		GfDict gfDict = new GfDict();
		gfDict.setDictNo(webAreaOrganInfo.getDictNo());
		gfDict.setDictName(webAreaOrganInfo.getDictName());
		gfDict.setDictValueIn(webAreaOrganInfo.getAreaName());
		int count = gfDictService.countByAttr(gfDict);
		gfDict.setDictValueIn(null);
		gfDict.setDictKeyIn(webAreaOrganInfo.getAreaCode());
		gfDict =  gfDictService.selectByPK(MapUtil.beanToMap(gfDict));
		if(!gfDict.getDictValueIn().equals(webAreaOrganInfo.getAreaName())&&count > 0){
			//���������ظ������������룡
			json.returnMsg("false", this.getErrorInfo("w4A2"));
			return false;
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO �������������Ϣ.
	 *
	 * @param webAreaOrganInfo
	 * @param organs
	 * @param fdOper
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	public void insertBatchWebArea(WebAreaOrganInfo webAreaOrganInfo,String organs,FdOper fdOper){
		String[] organ = organs.split(",");
		for(int i = 0; i < organ.length; i++){
			WebAreaOrganInfo area = new WebAreaOrganInfo();
			area.setAreaCode(webAreaOrganInfo.getAreaCode());
			area.setProvCode(organ[i]);
			area.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
			area.setLatestModifyTime(BocoUtils.getNowTime());
			area.setLatestOperCode(fdOper.getOpercode());
			int count = webAreaOrganInfoMapper.insertEntity(area);
			if(count != 1){
				//{0}����ʧ��
				throw new SystemException("w376","������Ϣ");
			}
		}
	}
	/**
	 * 
	 *
	 * TODO ����У�����.
	 *
	 * @param webAreaOrganInfo
	 * @param organs
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	private boolean checkInsertData(WebAreaOrganInfo webAreaOrganInfo,String organs) throws Exception {
		if(!StringUtils.isNotEmpty(webAreaOrganInfo.getAreaCode())){
			json.returnMsg("false", this.getErrorInfo("w384","��������"));
			return false;
		}
		if(!StringUtils.isNotEmpty(webAreaOrganInfo.getAreaName())){
			json.returnMsg("false", this.getErrorInfo("w384","��������"));
			return false;
		}
		if(BocoUtils.getStrLength(webAreaOrganInfo.getAreaCode(), 2) > 3){
			//{0}�ĳ��Ȳ��ܳ���{1}
			throw new SystemException("w373","��������","2");
		}
		if(BocoUtils.getStrLength(webAreaOrganInfo.getAreaName(), 2) > 256){
			//{0}�ĳ��Ȳ��ܳ���{1}
			throw new SystemException("w373","��������","256");
		}
		String req = "[\u4e00-\u9fa5]*+[a-zA-Z]*+[0-9]*+";
		if(Pattern.matches(req,webAreaOrganInfo.getAreaName()) == false){
			//{0}�����Ƿ��ַ�
			throw new SystemException("w356","��������");
		}
		
		WebAreaOrganInfo webAreaOrgan = new WebAreaOrganInfo();
		String hasOrgan = "";
		//��ȡ�ѱ�ѡ��Ļ�����Ϣ
		List<WebAreaOrganInfo> organList = webAreaOrganInfoMapper.selectByAttr(webAreaOrgan);
		if(organList.size() != 0){
			for(WebAreaOrganInfo areaOrgan:organList){
				hasOrgan += areaOrgan.getProvCode() + ",";
			}
			if(!"".equals(hasOrgan)){
				hasOrgan = hasOrgan.substring(0, hasOrgan.length() - 1);
			}
		}
		//��֤��ѡ�Ļ����Ƿ��ѱ�����������ѡ��
		if(StringUtils.isNotEmpty(organs) && !"��".equals(organs)){
			String[] organ = organs.split(",");
			for(int i = 0; i < organ.length; i++){
				if(hasOrgan.indexOf(organ[i]) != -1){
					json.returnMsg("false", this.getErrorInfo("w392",organ[i]+","));
					return false;
				}
			}
		}
		//��֤���������Ƿ��ظ�
		GfDict gfDict = new GfDict();
		gfDict.setDictNo(webAreaOrganInfo.getDictNo());
		gfDict.setDictName(webAreaOrganInfo.getDictName());
		gfDict.setDictValueIn(webAreaOrganInfo.getAreaName());
		int count = gfDictService.countByAttr(gfDict);
		if(count>0){
			//���������ظ������������룡
			json.returnMsg("false", this.getErrorInfo("w4A2"));
			return false;
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO ��ѯ����������ѡ��Ļ�����Ϣ.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	public List<WebAreaOrganInfo> selectNotEqualOrgan(String areaCode){
		return webAreaOrganInfoMapper.selectNotEqualOrgan(areaCode);
	}
}