package com.boco.PM.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.PM.service.IWebApproveModelService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebApproveModel;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.WebApproveModelMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebApproveModelҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class WebApproveModelService extends GenericService<WebApproveModel,java.lang.String> implements IWebApproveModelService{
	@Autowired
	private WebApproveModelMapper webApproveModelMapper;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	/**
	 * 
	 *
	 * TODO ���������������ģ����Ϣ.
	 *
	 * @param webApproveModel
	 * @param fdOper
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��9��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public Json insertWebApproveMode(WebApproveModel webApproveModel,FdOper fdOper) throws Exception{
		boolean check = checkInsertData(webApproveModel);
		if(check == false){
			return json;
		}
		//У��ɹ�����������
		String appCode = BocoUtils.getUUID();
		webApproveModel.setAppCode(appCode);
		webApproveModel.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
		webApproveModel.setLatestModifyTime(BocoUtils.getNowTime());
		webApproveModel.setLatestOperCode(fdOper.getOpercode());
		int count = insertEntity(webApproveModel);
		if(count != 1){
			throw new SystemException(getErrorInfo("w301"));
		}
		return json.returnMsg("true", "�����ɹ�");
	}
	/**
	 * 
	 *
	 * TODO �޸ĳ����������ģ����Ϣ.
	 *
	 * @param webApproveModel
	 * @param fdOper
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��9��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json updateWebApproveMode(WebApproveModel webApproveModel,FdOper fdOper) throws Exception {
		boolean check = checkUpdateData(webApproveModel);
		if(check == false){
			return json;
		}
		//У��ɹ�
		webApproveModel.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
		webApproveModel.setLatestModifyTime(BocoUtils.getNowTime());
		webApproveModel.setLatestOperCode(fdOper.getOpercode());
		updateByPK(webApproveModel);
		return json.returnMsg("true", "�޸ĳɹ�");
	}
	/**
	 * 
	 *
	 * TODO ����У�����.
	 *
	 * @param webApproveModel
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��9��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkInsertData(WebApproveModel webApproveModel) throws Exception {
		//��֤��������ĳ���
		if(StringUtils.isNotEmpty(webApproveModel.getAppAdvice())){
			if(BocoUtils.getStrLength(webApproveModel.getAppAdvice(), 2) > 200){
				json.returnMsg("false", getErrorInfo("w302"));
				return false;
			}
		}
		//��֤��������Ƿ��Ѵ���
		int count = webApproveModelMapper.countByAttr(webApproveModel);
		if(count > 0){
			throw new SystemException("w303");
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO �޸�У�����.
	 *
	 * @param webApproveModel
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��9��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkUpdateData(WebApproveModel webApproveModel) throws Exception {
		//��֤��������ĳ���
		if(StringUtils.isNotEmpty(webApproveModel.getAppAdvice())){
			if(BocoUtils.getStrLength(webApproveModel.getAppAdvice(), 2) > 200){
				json.returnMsg("false", getErrorInfo("w302"));
				return false;
			}
		}
		//��֤��������Ƿ��Ѵ���
		WebApproveModel exist = new WebApproveModel();
		exist.setAppCode(webApproveModel.getAppCode());
		exist.setAppAdvice(webApproveModel.getAppAdvice());
		int existCount = webApproveModelMapper.countByExist(exist);
		if(existCount > 0){
			json.returnMsg("false", getErrorInfo("w303"));
			return false;
		}
		return true;
	}

}