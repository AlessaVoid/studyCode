package com.boco.PM.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.PM.service.IWebRoleInfoService;
import com.boco.PM.service.IWebSublicenseInfoService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebRoleInfo;
import com.boco.SYS.entity.WebSublicenseInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.FdOperMapper;
import com.boco.SYS.mapper.WebSublicenseInfoMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;
import com.boco.SYS.util.WebContextUtil;

/**
 * 
 * 
 * WebSublicenseInfoҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class WebSublicenseInfoService extends GenericService<WebSublicenseInfo,java.lang.String> implements IWebSublicenseInfoService{
	@Autowired
	private WebSublicenseInfoMapper webSublicenseInfoMapper;
	@Autowired
	private FdOperMapper fdOperMapper;
	@Autowired
	private IWebRoleInfoService webRoleInfoService;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	private BocoUtils bocoUtils = new BocoUtils();
	private Json json = new Json();
	/**
	 * 
	 *
	 * TODO ά��ת��Ȩ��Ϣ.
	 *
	 * @param webSublicenseInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��2��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json outWebSub(WebSublicenseInfo webSublicenseInfo, String roles) throws Exception {
		//У��
		boolean check = checkOutWebSub(webSublicenseInfo,roles);
		//У��δͨ��
		if(check == false){
			return json;
		}
		if(checkIsOperate(webSublicenseInfo,roles)){
			//У��ͨ��
			//ת����Աӵ�еĽ�ɫ
			String role = "";
			//��ȡת����Աӵ�еĽ�ɫ
			FdOper outFdOper = new FdOper();
			outFdOper.setOpercode(webSublicenseInfo.getOutOper());
			List<FdOper> operRoleList = fdOperMapper.selectByAttr(outFdOper);
			if(operRoleList.size() != 0){
				role = operRoleList.get(0).getOperdegree();
			}
			if(StringUtils.isNotEmpty(webSublicenseInfo.getInOper())){
				//����ת����Ϣ
				if(StringUtils.isNotEmpty(roles)){
					//���ת���Ľ�ɫ����
					String[] outRole = roles.split(",");
					for(int i=0;i<outRole.length;i++){
						String roleCode = outRole[i];
						//��ӵ�еĽ�ɫ��ȥ��ת���Ľ�ɫ�����������ջؽ�ɫ������
						role = role.replace(roleCode, "");
						String id = bocoUtils.getUUID();
						WebSublicenseInfo webSub = new WebSublicenseInfo();
						webSub.setId(id);
						webSub.setOutOper(webSublicenseInfo.getOutOper());
						webSub.setInOper(webSublicenseInfo.getInOper());
						webSub.setOutDate(fdBusinessDateService.getCommonDateDetails());
						webSub.setOutTime(BocoUtils.getNowTime());
						webSub.setIsBack("2");
						webSub.setRoleCode(roleCode);
						int count = insertEntity(webSub);
						if(count != 1){
							throw new SystemException("����ת��Ȩ��Ϣʧ��");
						}
					}
				}
				//�޸�ת����ɫ״̬Ϊ�ջ�
				//role��ӵ�еĽ�ɫ-ת���Ľ�ɫ
				if(StringUtils.isNotEmpty(role)){
					for(int i=0;i<role.length();i=i+3){
						String roleCode = role.substring(i,i+3);
						WebSublicenseInfo webSub = new WebSublicenseInfo();
						webSub.setOutOper(webSublicenseInfo.getOutOper());
						webSub.setInOper(webSublicenseInfo.getInOper());
						webSub.setIsBack("2");
						webSub.setRoleCode(roleCode);
						//��֤�ջصĽ�ɫ�����ݿ����Ƿ��ж�Ӧ�ļ�¼������еĻ��������ջز�����û�еĻ�������
						int selectCount = webSublicenseInfoMapper.countByAttr(webSub);
						if(selectCount > 0){
							webSub.setInDate(fdBusinessDateService.getCommonDateDetails());
							webSub.setInTime(BocoUtils.getNowTime());
							webSub.setIsBack("1");
							int updateCount = webSublicenseInfoMapper.updateByAttr(webSub);
							if(updateCount != 1){
								throw new SystemException("�޸�ת��Ȩ��Ϣʧ��");
							}
						}
					}
				}
			}else{//δָ��ת����Ա�����ջؽ�ɫ�����ջ�
				if(StringUtils.isNotEmpty(roles)){
					String[] outRole = roles.split(",");
					for(int i=0;i<outRole.length;i++){
						String roleCode = outRole[i];
						//��ӵ�еĽ�ɫ��ȥ��ת���Ľ�ɫ�����������ջؽ�ɫ������
						role = role.replace(roleCode, "");
					}
				}
				for(int i=0;i<role.length();i=i+3){
					String roleCode = role.substring(i,i+3);
					WebSublicenseInfo sublicenseInfoAll = new WebSublicenseInfo();
					sublicenseInfoAll.setOutOper(webSublicenseInfo.getOutOper());
					sublicenseInfoAll.setRoleCode(roleCode);
					sublicenseInfoAll.setIsBack("1");
					sublicenseInfoAll.setInDate(fdBusinessDateService.getCommonDateDetails());
					sublicenseInfoAll.setInTime(BocoUtils.getNowTime());
					//�ջظù�Աת���ĵ�ǰ��ɫ
					webSublicenseInfoMapper.backRoleAll(sublicenseInfoAll);
				}
			}
		}
		return json.returnMsg("true", "����ɹ�");
	}
	/**
	 * 
	 *
	 * TODO ת��ȨУ�����.
	 *
	 * @param webSublicenseInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��2��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	private boolean checkOutWebSub(WebSublicenseInfo webSublicenseInfo, String roles) throws Exception {
		//У���Ƿ���ת������
		//��ѯ��ǰ��¼Աת���Ľ�ɫ
		//��ȡҳ���ύ��ת����ɫ
		//���ҳ���ύ��ת����ɫ ��ǰ��¼Աת���Ľ�ɫ���ջ� ��ת����Ա����Ϊ��
		//���ҳ���ύ��ת����ɫ >= ��ǰ��¼Աת���Ľ�ɫ��ת�� ��ת����Ա����Ϊ��
		String rolesTemp = roles;
		WebSublicenseInfo outFdOperWsl = new WebSublicenseInfo();
		outFdOperWsl.setOutOper(WebContextUtil.getSessionUser().getOpercode());
		outFdOperWsl.setIsBack("2");
		List<WebSublicenseInfo> wslList = webSublicenseInfoMapper.selectByAttr(outFdOperWsl);//��ѯ��ǰ��Ա��ת��δ�ջصĽ�ɫ����
		Map<String,Object> outRoleMap = new HashMap<String,Object>();
		if(wslList.size() > 0){//ӵ��ת����ɫ
			if(rolesTemp.length() > 0){
				rolesTemp = rolesTemp.replace(",", "");
				for(WebSublicenseInfo info : wslList){
					rolesTemp = rolesTemp.replace(info.getRoleCode(), "");
				}
		    	if(rolesTemp.length() > 0){
		    		if(!StringUtils.isNotEmpty(webSublicenseInfo.getInOper())){
						//{0}����Ϊ��
						throw new SystemException("w384","ת���Ա");
					}
		    	}
			}
		}else{
			if(rolesTemp.length() > 0){
				if(!StringUtils.isNotEmpty(webSublicenseInfo.getInOper())){
					//{0}����Ϊ��
					throw new SystemException("w384","ת���Ա");
				}
			}
		}
		//У��ת���Ĺ�Ա��ת��Ĺ�Ա�Ƿ���ͬһ������
		if(StringUtils.isNotEmpty(webSublicenseInfo.getOutOper()) && StringUtils.isNotEmpty(webSublicenseInfo.getInOper())){
			//��֤ת���Ĺ�Ա��ת��Ĺ�Ա��ͬ
			if(webSublicenseInfo.getOutOper().equals(webSublicenseInfo.getInOper())){
				json.returnMsg("false", "���ź������޷����Լ�����ת��Ȩ����");
				return false;
			}
			//ת����Ա
			FdOper outFdOper = new FdOper();
			outFdOper.setOpercode(webSublicenseInfo.getOutOper());
			//ת���Ա
			FdOper inFdOper = new FdOper();
			inFdOper.setOpercode(webSublicenseInfo.getInOper());
			List<FdOper> outList = fdOperMapper.selectByAttr(outFdOper);
			List<FdOper> inList = fdOperMapper.selectByAttr(inFdOper);
			//��֤ת����Ա
			if(outList.size() == 0){
				json.returnMsg("false", "ת����Ա������");
				return false;
			}
			//��֤ת���Ա
			if(inList.size() == 0){
				json.returnMsg("false", "ת���Ա������");
				return false;
			}
			//��֤ת��ת����Ա�Ƿ���ͬһ������
			if(outList.size() != 0 && inList.size() != 0){
				outFdOper = outList.get(0);
				inFdOper = inList.get(0);
				if(!outFdOper.getOrgancode().equals(inFdOper.getOrgancode())){
					json.returnMsg("false", "ת����Ա��ת���Ա����ͬһ�������޷�����ת��Ȩ����");
					return false;
				}
			}
			//ת����ɫ��Ϊ��ʱ
			if(StringUtils.isNotEmpty(roles)){
				String[] outRole = roles.split(",");
				for(int i = 0; i < outRole.length; i++){
					String roleCode = outRole[i];
					WebSublicenseInfo webSub = new WebSublicenseInfo();
					webSub.setOutOper(webSublicenseInfo.getOutOper());
					webSub.setInOper(webSublicenseInfo.getInOper());
					webSub.setRoleCode(roleCode);
					//��֤ת���Ľ�ɫ���Ƿ�ת��������Ա����δ�ջصĽ�ɫ��Ϣ
					int isNoInOperCount = webSublicenseInfoMapper.countOutRole(webSub);
					if(isNoInOperCount != 0){
						WebRoleInfo roleInfo = webRoleInfoService.selectByPK(roleCode);
						String roleName ="";
						if(roleInfo != null){
							roleName = roleInfo.getRoleName();
						}
						json.returnMsg("false", roleName + "��ɫ�Ѿ�ת��������Ա���޷�����ת��,�����ջظý�ɫ���ٽ���ת��Ȩ����");
						return false;
					}
					//��֤ת���Ľ�ɫ�Ƿ���ת����ǰ��Ա����δ�ջ�
					webSub.setIsBack("2");
					List<WebSublicenseInfo> list = webSublicenseInfoMapper.selectByAttr(webSub);
					if(list.size() != 0){
						WebRoleInfo roleInfo = webRoleInfoService.selectByPK(roleCode);
						String roleName ="";
						if(roleInfo != null){
							roleName = roleInfo.getRoleName();
						}
						json.returnMsg("false", roleName + "��ɫ�ѳɹ�ת����ǰ��Ա���޷��ظ�����ת��Ȩ����");
						return false;
					}
				}
			}
		}
		
		return true;
	}
	/**
	 * 
	 *
	 * TODO ���ɫ���ܱ������ѯ��Ȩ�û������б�����.
	 *
	 * @param query
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��7��    	  ����    �½�
	 * </pre>
	 */
	public List<Map<String,String>> getPowerList(Map<String,String> query) throws Exception{
		return webSublicenseInfoMapper.getPowerList(query);
	}
	/**
	 * 
	 *
	 * TODO �ж��Ƿ���д���.
	 *
	 * @param webSublicenseInfo
	 * @param roles
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��6��2��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	private boolean checkIsOperate(WebSublicenseInfo webSublicenseInfo,String roles) throws Exception{
		//����У�鷽�����ܿ��ƣ�ת���Ա�Ƿ����룬����ڴˣ�����Կ϶��Ļ�ȡ��ǰת���Ա
		//���ת���Ա��Ϊ�գ����еĲ���
			//��ת����ɫ���ϲ�Ϊ�յ�ʱ�����жϵ�ǰ��Աת���˹�Աδ�ջصĽ�ɫ�����Ƿ��뵱ǰת����ɫ������ͬ�������ͬ���򲻽��д���
			//��ת����ɫ����Ϊ�յ�ʱ�����жϵ�ǰת����ɫ�Ƿ�Ϊ�գ����Ϊ�գ��򲻽��д�����Ϊ�յ�ʱ�������ת������
		//���ת���ԱΪ�գ����еĲ���
			//��ת����ɫ���ϲ�Ϊ�յ�ʱ�����жϵ�ǰ��Աת���˹�Աδ�ջصĽ�ɫ�����Ƿ��뵱ǰת����ɫ������ͬ�������ͬ���򲻽��д����������ͬ������ж�Ӧ�Ĵ����ջأ�
			//��ת����ɫ����Ϊ�յ�ʱ�����жϵ�ǰת����ɫ�Ƿ�Ϊ�գ����Ϊ�գ��򲻽��д��������Ϊ�յ�ʱ�����׳��쳣
		//�����������������ڣ���ǰ��Ա����ת��δ�ջص�ת����ɫ����
		WebSublicenseInfo outFdOperWsl = new WebSublicenseInfo();
		outFdOperWsl.setOutOper(WebContextUtil.getSessionUser().getOpercode());
		if(StringUtils.isNotEmpty(webSublicenseInfo.getInOper())){
			outFdOperWsl.setInOper(webSublicenseInfo.getInOper());
		}
		outFdOperWsl.setIsBack("2");
		//��ȡ��Ӧ��ת����ɫ����
		//��ת���Ա��Ϊ�յ�ʱ�����ѯ��ǰ��Ա��ת���Աδ�ջصĽ�ɫ����
		//��ת���ԱΪ�յ�ʱ�����ѯ��ǰ��Ա������ת��δ�ջصĽ�ɫ����
		List<WebSublicenseInfo> wslList = webSublicenseInfoMapper.selectByAttr(outFdOperWsl);
		Map<String,Object> outRoleMap = new HashMap<String,Object>();
		//�����ǰת����ɫ����ת����ɫ����Ϊ��ʱ�������д���
		if(roles.length() == 0 && wslList.size() == 0){//��ת����ɫ����Ϊ�յ�ʱ�����жϵ�ǰת����ɫ�Ƿ�Ϊ�գ��򲻽��д���
			return false;
		}else{
			//ת����ɫ���ϲ�Ϊ�յ�ʱ�����жϵ�ǰ��Աת���˹�Աδ�ջصĽ�ɫ�����Ƿ��뵱ǰת����ɫ������ͬ�������ͬ���򲻽��д���
			if(wslList.size() > 0){//������ת��δ�ջصĽ�ɫ����
				if(roles.length() >0){
					if(wslList.size() == roles.split(",").length){
						roles = roles.replace(",","");
						for(WebSublicenseInfo info : wslList){
							if(roles.contains(info.getRoleCode())){
								roles = roles.replace(info.getRoleCode(), "");
							}
						}
					    //��ǰת����ɫ����Ϊ�գ���ȥת����ɫ�����µĽ�ɫ����Ϊ0ʱ��������ת����ɫ�͵�ǰת����ɫ��ͬ
					    if(roles.length() == 0){
					    	return false;
					    }
					}
				}
			}
		}
		return true;
	}
}