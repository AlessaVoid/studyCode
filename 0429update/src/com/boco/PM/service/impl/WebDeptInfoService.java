package com.boco.PM.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.PM.service.IWebDeptInfoService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.WebDeptInfo;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.WebDeptInfoMapper;
import com.boco.SYS.mapper.WebOperInfoMapper;
import com.boco.SYS.util.Json;
import com.boco.SYS.util.MapUtil;

/**
 * 
 * 
 * WebDeptInfoҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class WebDeptInfoService extends GenericService<WebDeptInfo,HashMap<String,Object>> implements IWebDeptInfoService{
	
	@Autowired
	private WebDeptInfoMapper deptInfoMapper;
	@Autowired
	private WebDeptInfoMapper webDeptInfoMapper;
	@Autowired
	private WebOperInfoMapper webOperInfoMapper;
	private Json json = new Json();
	/**
	 * 
	 *
	 * TODO ��ѯ�б���Ϣ.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��12��    	    �غ���   �½�
	 * </pre>
	 */
	@Override
	public List<WebDeptInfo> select(WebDeptInfo webDeptInfo) {
			return deptInfoMapper.select(webDeptInfo);
	}
	
	/**
	 * 
	 *
	 * TODO ����.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��12��    	    �غ���   �½�
	 * </pre>
	 */
	@Override
	public Json insert(WebDeptInfo webDeptInfo) throws Exception {
		if("".equals(webDeptInfo.getUpDeptCode())){
			webDeptInfo.setUpDeptCode("0");
		}
		String deptCode = webDeptInfo.getDeptCode();
		String dept = "";
		for(int i=deptCode.length();i<3;i++){
			dept = dept + "0";
		}
		deptCode = dept + deptCode;
		webDeptInfo.setDeptCode(deptCode);
		//��������У�����
		boolean check = checkInsertData(webDeptInfo);
		//��֤ʧ��
		if(check == false){
			return this.json ;
		}
		int count = insertEntity(webDeptInfo);
		//�������ݿ�ʧ��
		if(count != 1){
			//����ʧ�ܡ�
			return this.json.returnMsg("false", getErrorInfo("w446"));
		}//�����ɹ�!  ���Ŵ���Ϊ��"+list.get(0).getDeptCode()
		return this.json.returnMsg("true", getErrorInfo("w445",webDeptInfo.getDeptCode()));
		
		
	}
	/**
	 * 
	 *
	 * TODO �޸�.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��12��    	    �غ���   �½�
	 * </pre>
	 */
	@Override
	public Json update(WebDeptInfo webDeptInfo) throws Exception {
		if("".equals(webDeptInfo.getUpDeptCode())){
			webDeptInfo.setUpDeptCode("0");
		}
		//��������У�����
		boolean check = checkUpdateData(webDeptInfo);
		//��֤ʧ��
		if(check == false){
			return this.json ;
		}
		int count = updateByPK(webDeptInfo);
		//�������ݿ�ʧ��
		if(count != 1){
			//�޸�ʧ��!
			return this.json.returnMsg("false",getErrorInfo("w447"));
		}
		//�޸ĳɹ���
		return this.json.returnMsg("true",getErrorInfo("w448"));
	}
	/**
	 * 
	 *
	 * TODO �޸���֤.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��12��    	    �غ���   �½�
	 * </pre>
	 */
	private boolean checkUpdateData(WebDeptInfo webDeptInfo) throws Exception {
		//��֤���������Ƿ��ظ�
		WebDeptInfo deptInfo = new WebDeptInfo();
		deptInfo.setUpDeptCode(webDeptInfo.getUpDeptCode());
		deptInfo.setDeptName(webDeptInfo.getDeptName());
		deptInfo.setOrgancode(webDeptInfo.getOrgancode());
		int MenuNoCount = countByAttr(deptInfo);
		if(MenuNoCount > 0){
			//"�����������ظ�������������!"
			this.json.returnMsg("false", getErrorInfo("w449"));
			return false;
		}
		//�ϼ����Ų��ܵ��ڱ�������
		if(webDeptInfo.getDeptCode()==webDeptInfo.getUpDeptCode()||webDeptInfo.getDeptCode().equals(webDeptInfo.getUpDeptCode())){
			//"�ϼ����Ų��ܵ��ڱ������ţ�����������!"
			this.json.returnMsg("false", getErrorInfo("w450"));
			return false;
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO ������֤.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��12��    	    �غ���   �½�
	 * </pre>
	 */
	private boolean checkInsertData(WebDeptInfo webDeptInfo) throws Exception {
		//��֤���������Ƿ��ظ�
		WebDeptInfo deptInfo = new WebDeptInfo();
		deptInfo.setUpDeptCode(webDeptInfo.getUpDeptCode());
		deptInfo.setDeptName(webDeptInfo.getDeptName());
		deptInfo.setOrgancode(webDeptInfo.getOrgancode());
		int MenuNoCount = countByAttr(deptInfo);
		if(MenuNoCount > 0){
			this.json.returnMsg("false", getErrorInfo("w449"));
			return false;
		}
		
		return true;
	}
	/**
	 * 
	 *
	 * TODO �������ű���.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��12��    	    �غ���   �½�
	 * </pre>
	 */
	@Override
	public int max(WebDeptInfo webDeptInfo) {
			return  webDeptInfoMapper.max(webDeptInfo);
	}
	/**
	 * 
	 *
	 * TODO ɾ��.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��12��    	    �غ���   �½�
	 * </pre>
	 */
	@Override
	public Json delete(WebDeptInfo webDeptInfo) throws Exception {
		//����ɾ��У�����
		boolean check = checkDeleteData(webDeptInfo);
		//��֤ʧ��
		if(check == false){
			return this.json ;
		}
		int count = deleteByPK(MapUtil.beanToMap(webDeptInfo));
		//�������ݿ�ʧ��
		if(count != 1){
			//ɾ��ʧ��!"
			return this.json.returnMsg("false", getErrorInfo("w451"));
		}// "ɾ���ɹ�!"
		return this.json.returnMsg("true",getErrorInfo("w452"));
	}
	/**
	 * 
	 *
	 * TODO ɾ����֤.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��12��    	    �غ���   �½�
	 * </pre>
	 */
	private boolean checkDeleteData(WebDeptInfo webDeptInfo) throws Exception {
		//��֤�Ƿ�����¼�����
		WebDeptInfo info = new WebDeptInfo();
		info.setUpDeptCode(webDeptInfo.getDeptCode());
		info.setOrgancode(webDeptInfo.getOrgancode());
		int MenuNoCount = countByAttr(info);
		if(MenuNoCount > 0){
			//"�����¼����ţ���ɾ���¼����ź���ɾ���˲���!"
			this.json.returnMsg("false",getErrorInfo("w453") );
			return false;
		}
		//��֤�������Ƿ�����Ա
		WebOperInfo oper = new WebOperInfo();
		oper.setOrganCode(webDeptInfo.getOrgancode());
		int count = webOperInfoMapper.countByAttr(oper);
		if(count > 0){
			// "�˲����´�����Ա����ɾ���˲����µ���Ա����ɾ���˲���!"
			this.json.returnMsg("false",getErrorInfo("w454"));
			return false;
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO ��������򣨲��ű��룩.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��12��    	    �غ���   �½�
	 * </pre>
	 */
	@Override
	public List<Map<String, String>> selectCode(WebDeptInfo webDeptInfo) {
		List<Map<String, String>> list = webDeptInfoMapper.selectCode(webDeptInfo);
		return list;
	}
	/**
	 * 
	 *
	 * TODO ��������򣨲������ƣ�.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��12��    	    �غ���   �½�
	 * </pre>
	 */
	@Override
	public List<Map<String, String>> selectName(WebDeptInfo webDeptInfo) {
		List<Map<String, String>> list = webDeptInfoMapper.selectName(webDeptInfo);
		return list;
	}
	/**
	 * 
	 *
	 * TODO ����������ϼ����ű��룩.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��12��    	    �غ���   �½�
	 * </pre>
	 */
	@Override
	public List<Map<String, String>> selectUpCode(WebDeptInfo webDeptInfo) {
		List<Map<String, String>> list = webDeptInfoMapper.selectUpCode(webDeptInfo);
		return list;
	}
	/**
	 * 
	 *
	 * TODO �ϼ��������Ʋ˵���.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��12��    	    �غ���   �½�
	 * </pre>
	 */
	@Override
	public List<WebDeptInfo> selectUpDeptName(WebDeptInfo webDeptInfo) {
		return webDeptInfoMapper.selectUpDeptName(webDeptInfo);
	}
	
}