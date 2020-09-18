package com.boco.PM.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.PM.service.IWebShortMenuInfoService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.WebShortMenuInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.WebShortMenuInfoMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebShortMenuInfoҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class WebShortMenuInfoService extends GenericService<WebShortMenuInfo,HashMap<String,Object>> implements IWebShortMenuInfoService{
	@Autowired
	private WebShortMenuInfoMapper webShortMenuInfoMapper;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	/**
	 * 
	 *
	 * TODO ά����ݲ˵���Ϣ.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	@Override
	public Json updateWebShortMenuInfo(WebShortMenuInfo webShortMenuInfo,String funCode) throws Exception{
		webShortMenuInfoMapper.deleteByOperCode(webShortMenuInfo.getOperCode());
		if(StringUtils.isNotEmpty(funCode)){
			int funCount = funCode.split(",").length;
			insertBatchShortMenu(webShortMenuInfo,funCode);
		}
		return json.returnMsg("true", "����ɹ�");
	}
	/**
	 * 
	 *
	 * TODO ���������ݲ˵���Ϣ.
	 *
	 * @param funCode
	 * @param fdOper
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	public void insertBatchShortMenu(WebShortMenuInfo webShortMenuInfo,String funCode)throws Exception{
		List<WebShortMenuInfo> list = new ArrayList<WebShortMenuInfo>();
		String operCode = webShortMenuInfo.getOperCode();
		if(StringUtils.isNotEmpty(funCode)){
			String[] funCodes = funCode.split(",");
			for(int i =0;i<funCodes.length;i++){
				WebShortMenuInfo shortMenu = new WebShortMenuInfo();
				shortMenu.setOperCode(operCode);
				shortMenu.setMenuCode(funCodes[i]);
				shortMenu.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
				shortMenu.setLatestModifyTime(BocoUtils.getNowTime());
				shortMenu.setLatestOperCode(operCode);
				int count = webShortMenuInfoMapper.insertEntity(shortMenu);
				if(count != 1){
					throw new SystemException("�����ݲ˵���Ϣʧ��");
				}
			}
		}
	}
	/**
	 * 
	 *
	 * TODO ���ݹ�Ա��ɫ��ѯ��Աӵ�еĹ���(��̬����where�����е�or).
	 *
	 * @param roleCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	public List<WebShortMenuInfo> selectByRoleList(List<String> roleCode)throws Exception{
		return this.selectByRoleList(roleCode);
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯ�˵���.
	 *
	 * @param webShortMenuInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��21��    	     ����    �½�
	 * </pre>
	 */
	@Override
	public List<String> selectMenuNoByAttr(WebShortMenuInfo webShortMenuInfo) throws Exception{
		
		return webShortMenuInfoMapper.selectMenuNoByAttr(webShortMenuInfo);
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯ���и��˵�.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��21��    	     ����    �½�
	 * </pre>
	 */
	@Override
	public List<String> selectUpMenuNo() throws Exception{
		
		return webShortMenuInfoMapper.selectUpMenuNo();
	}
}