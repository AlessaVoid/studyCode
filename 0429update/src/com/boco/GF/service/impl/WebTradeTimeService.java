package com.boco.GF.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.boco.SYS.service.IFdBusinessDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.GF.service.IWebTradeTimeService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebTradeTime;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.WebTradeTimeMapper;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebTradeTimeҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class WebTradeTimeService extends GenericService<WebTradeTime,java.lang.String> implements IWebTradeTimeService{

	@Autowired
	private WebTradeTimeMapper webTradeTimeMapper;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	
	/**
	 * 
	 *
	 * TODO �޸Ľ���ʱ��ڵ���Ϣ.
	 *
	 * @param webTradeTime
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��1��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json updateWebTradeTime(WebTradeTime webTradeTime,FdOper fdOper) throws Exception {
		//У��ʧ��
		checkUpdateData(webTradeTime);
		//У��ɹ�
		webTradeTime.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
		webTradeTime.setLatestModifyTime(BocoUtils.getNowTime());
		webTradeTime.setLatestOperCode(fdOper.getOpercode());
		int count = updateByPK(webTradeTime);
		if(count != 1){
			throw new SystemException("�޸Ľ���ʱ��ڵ���Ϣʧ��");
		}
		return json.returnMsg("true", "�޸ĳɹ�");
	}
	/**
	 * 
	 *
	 * TODO �޸�У�����.
	 *
	 * @param webTradeTime
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��1��    	  ����    �½�
	 * </pre>
	 */
	public void checkUpdateData(WebTradeTime webTradeTime){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String currentDate = fdBusinessDateService.getCommonDateDetails() + " " + sdf.format(new Date());//��ǰ����
		String beginTime = webTradeTime.getBeginTime().replace(":", "");
		if(beginTime.compareTo(currentDate.replace(":", "")) < 0){
			//��ʼʱ��Ӧ���ڵ��ڵ�ǰʱ��
			//{0}Ӧ���ڵ���{1}
			throw new SystemException("w3B4","��ʼʱ��","��ǰʱ��,��ǰʱ�䣺"+currentDate);
		}
		String endTime = webTradeTime.getEndTime().replace(":", "");
		if(endTime.compareTo(currentDate.replace(":", "")) < 0){
			//��ֹʱ��Ӧ���ڵ��ڵ�ǰʱ��
			//{0}Ӧ���ڵ���{1}
			throw new SystemException("w3B4","��ֹʱ��","��ǰʱ��,��ǰʱ�䣺"+currentDate);
		}
		//��֤��ֹʱ����ڵ��ڿ�ʼʱ��
		if(webTradeTime.getBeginTime() != null && !"".equals(webTradeTime.getBeginTime())
				&& webTradeTime.getEndTime() != null && !"".equals(webTradeTime.getEndTime())){
			if(webTradeTime.getEndTime().compareTo(webTradeTime.getBeginTime()) < 0){
				//��ʼʱ��ӦС�ڵ�����ֹʱ��
				//{0}ӦС�ڵ���{1}
				throw new SystemException("w3B7","��ʼʱ��","��ֹʱ��");
			}
		}
	}
	@Override
	public void reSetTradeTime(WebTradeTime webTradeTime) {
		webTradeTimeMapper.reSetTradeTime(webTradeTime.getMenuCode());
	}
}