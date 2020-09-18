package com.boco.SYS.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.PM.service.IFdOperService;
import com.boco.PM.service.IWebDeptInfoService;
import com.boco.PM.service.IWebOperInfoService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebDeptInfo;
import com.boco.SYS.entity.WebLogInfo;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.WebLogInfoMapper;
import com.boco.SYS.service.IWebLogInfoService;
import com.boco.SYS.util.TreeNode;

/**
 * 
 * 
 * ҵ����־��ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class WebLogInfoService extends GenericService<WebLogInfo,java.lang.String> implements IWebLogInfoService{
	@Autowired
	private WebLogInfoMapper webLogInfoMapper;
	@Autowired
	private IWebDeptInfoService webDeptInfoService;
	@Autowired
	private IFdOperService fdOperService;
	@Autowired
	private IWebOperInfoService webOperInfoService;
	/**
	 * 
	 *
	 * TODO ��װ��ѯ�����������쵼��.
	 * ѡ��һ�����ţ����Բ�ѯ��������ż��¼��µĲ���Ա��ϵͳ��־��Ϣ
	 *
	 * @param webLogInfo
	 * @param webOperInfoDTO
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��9��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public HashMap getSelectMap(WebLogInfo webLogInfo,WebOperInfo webOperInfoDTO) throws Exception{
		List<String> operDepts = new ArrayList<String>();
		WebDeptInfo webDeptInfo = new WebDeptInfo();
		List<WebDeptInfo> deptList = webDeptInfoService.selectByAttr(webDeptInfo);
		if(deptList.size() != 0){
			for(WebDeptInfo dept : deptList){
				operDepts.add(dept.getDeptCode());
			}
		}
		HashMap map = new HashMap();
		if(operDepts.size() != 0){
			map.put("deptList", operDepts);
		}
		if(StringUtils.isNotEmpty(webLogInfo.getOperCode())){
			map.put("operCode", webLogInfo.getOperCode());
		}
		if(StringUtils.isNotEmpty(webLogInfo.getOperName())){
			map.put("operName", webLogInfo.getOperName());
		}
		if(StringUtils.isNotEmpty(webLogInfo.getTradeDate())){
			map.put("tradeDate", webLogInfo.getTradeDate());
		}
		return map;
	}
	/**
	 * 
	 *
	 * TODO У�����Ա����.
	 *
	 * @param webLogInfo
	 * @param fdOper
	 * @param operDept
	 * @param upOperDept
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��9��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkOperDept(WebLogInfo webLogInfo,FdOper fdOper,String operDept,String upOperDept) throws Exception{
		return true;
	}
	/**
	 * 
	 *
	 * TODO У�����Ա����.
	 *
	 * @param webLogInfo
	 * @param fdOper
	 * @param selectDeptCode
	 * @param selectUpDeptCode
	 * @param operDept
	 * @param upOperDept
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��9��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkOperCode(WebLogInfo webLogInfo,FdOper fdOper,String operDept,String upOperDept) throws Exception{
		String selectDeptCode="",selectUpDeptCode = "";
		//У�����Ա����
		if(StringUtils.isNotEmpty(webLogInfo.getOperCode())){
			boolean check = false;
			//У��Ҫ��ѯ�Ĳ���Ա�Ƿ���ڣ��Ƿ��뵱ǰ����Ա��ͬһ��������
			FdOper oper = new FdOper();
			oper.setOpercode(webLogInfo.getOperCode());
			List<FdOper> operList = fdOperService.selectByAttr(oper);
			if(operList.size() != 0){
				oper = operList.get(0);
				if(!fdOper.getOrgancode().equals(oper.getOrgancode())){
					json.returnMsg("false", "ֻ�ܲ�ѯ��������Ա��ϵͳ��־��Ϣ");
					return false;
				}
			}else{
				json.returnMsg("false", "��ѯ�Ĺ�Ա�����ڣ�����������");
				return false;
			}
			//Ҫ��ѯ�Ĳ���Ա
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("organCode", fdOper.getOrgancode());
			map.put("operCode", fdOper.getOpercode());
			WebOperInfo selectWebOperInfo = webOperInfoService.selectByPK(map);
			if(selectWebOperInfo == null){
				throw new SystemException("����ѯ�Ĳ���Աû��ά����Ա��Ϣ���޷���ѯ�ò���Ա��ϵͳ��־��Ϣ");
			}else{
				//Ҫ��ѯ�Ĳ���Ա�Ĳ��Ŵ���
				HashMap queryMap = new HashMap();
				queryMap.put("deptCode",selectDeptCode);
				queryMap.put("organcode",fdOper.getOrgancode());
				WebDeptInfo selectDeptInfo = webDeptInfoService.selectByPK(queryMap);
				if(StringUtils.isNotEmpty(selectDeptInfo.getUpDeptCode())){
					selectUpDeptCode = selectDeptInfo.getUpDeptCode();
				}
				//��ǰ����Ա��һ�����ŵ�
				if(!StringUtils.isNotEmpty(upOperDept)){
					//��Ҫ��ѯ�Ĳ���Ա��һ������
					if(!StringUtils.isNotEmpty(selectUpDeptCode)){
						if(operDept.equals(selectDeptCode)){
							check = true;
						}
					}else{//��Ҫ��ѯ�Ĳ���Ա�Ƕ�������
						WebDeptInfo webDeptInfo = new WebDeptInfo();
						webDeptInfo.setUpDeptCode(operDept);
						List<WebDeptInfo> childDept = webDeptInfoService.selectByAttr(webDeptInfo);
						if(childDept.size() != 0){
							for(WebDeptInfo info : childDept){
								if(selectDeptCode.equals(info.getDeptCode())){
									check = true;
								}
							}
						}
					}
				}else{//��ǰ����Ա�Ƕ������ŵ�
					if(selectDeptCode.equals(operDept)){
						check = true;
					}
				}
				
				if(check == false){
					json.returnMsg("false", "��ֻ�ܲ�ѯ�������¼�����Ա��ϵͳ��־��Ϣ");
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO ��ѯ�Ƿ��ǵ�ǰ���ŵ��¼�����.
	 *
	 * @param deptInfo
	 * @param deptCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkDept(WebDeptInfo deptInfo,String deptCode) throws Exception{
		WebDeptInfo childDept = new WebDeptInfo();
		childDept.setUpDeptCode(deptInfo.getDeptCode());
		List<WebDeptInfo> childList = webDeptInfoService.selectByAttr(childDept);
		if(childList.size() != 0){
			for(WebDeptInfo info : childList){
				if(deptCode.equals(info.getDeptCode())){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 
	 *
	 * TODO ϵͳ��־��ѯ��ȡ����������Ϣ.
	 *
	 * @param webDeptInfo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	  ����    �½�
	 * </pre>
	 */
	public List<TreeNode> getWebDeptInfo() throws Exception {
		WebDeptInfo webDeptInfo = new WebDeptInfo();
		List<WebDeptInfo> deptList = webDeptInfoService.selectByAttr(webDeptInfo);
		List<TreeNode> list = new ArrayList<TreeNode>();
		if(deptList.size() != 0){
			for(WebDeptInfo dept : deptList){
				TreeNode treeNode = new TreeNode();
				treeNode.setId(dept.getDeptCode());
				if(!StringUtils.isNotEmpty(dept.getUpDeptCode())){
					dept.setUpDeptCode("0");
				}
				treeNode.setParentId(dept.getUpDeptCode());
				treeNode.setName(dept.getDeptName());
				treeNode.setOpen(true);
				list.add(treeNode);
			}
		}
		return list;
	}
}