package com.boco.SYS.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebMenuInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.WebMenuInfoMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.service.IWebMenuInfoService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * �˵���ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class WebMenuInfoService extends GenericService<WebMenuInfo,java.lang.String> implements IWebMenuInfoService{
	//���Զ��巽��ʱʹ��
	@Autowired
	private WebMenuInfoMapper mapper;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	private BocoUtils bocoUtils = new BocoUtils();
	
	public WebMenuInfo selectByPKInfo(WebMenuInfo webMenuInfo) throws DataAccessException{
		return this.mapper.selectByPKInfo(webMenuInfo);
	}
	/**
	 * 
	 *
	 * TODO �����˵�.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��22��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json insertWebMenuInfo(WebMenuInfo webMenuInfo,FdOper fdOper) throws Exception {
		String id = bocoUtils.getUUID();
		webMenuInfo.setId(id);
		//��������У�����
		boolean check = checkInsertData(webMenuInfo);
		//��֤ʧ��
		if(check == false){
			return this.json;
		}
		//��֤�ɹ�
		Map map = getMenuNoOrder(webMenuInfo);
		String menuNo = String.valueOf(map.get("menuNo"));
		String orderBy = String.valueOf(map.get("orderBy"));
			
		//����ǰ�ť��url���������ƴ��
		if("2".equals(webMenuInfo.getMenuType())){
			String iconClass = webMenuInfo.getMenuIcon().replace(".gif", "");
			String method = getMethod(iconClass);
			String url = "";
			if(StringUtils.isNotEmpty(webMenuInfo.getMenuUrl())){
				url = "{text : '"+webMenuInfo.getMenuName()+"', click : " + webMenuInfo.getMenuUrl() + ", iconClass : 'icon_"+ iconClass +"'}";
			}else{
				url = "{text : '"+webMenuInfo.getMenuName()+"', click : " + method + ", iconClass : 'icon_"+ iconClass +"'}";
			}
			webMenuInfo.setMenuUrl(url);
			webMenuInfo.setIsParent("0");
		}
		
		if(!StringUtils.isNotEmpty(webMenuInfo.getMenuNo())){
			webMenuInfo.setMenuNo(menuNo);
		}
		if(!StringUtils.isNotEmpty(webMenuInfo.getOrderNo())){
			webMenuInfo.setOrderNo(orderBy);
		}
		if("1".equals(webMenuInfo.getIsParent())){
			if(!StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
				webMenuInfo.setUpMenuNo("0");
			}
		}
		webMenuInfo.setMenuIcon("/libs/icons/"+webMenuInfo.getMenuIcon());
		webMenuInfo.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
		webMenuInfo.setLatestModifyTime(BocoUtils.getNowTime());
		int count = insertEntity(webMenuInfo);
		//�������ݿ�ʧ��
		if(count != 1){
			throw new SystemException("����ʧ��!");
		}
		return this.json.returnMsg("true", "�����ɹ�!");
	}
	/**
	 * 
	 *
	 * TODO ��ȡ��ť����.
	 *
	 * @param iconClass
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��15��    	  ����    �½�
	 * </pre>
	 */
	public String getMethod(String iconClass){
		String method = "";
		if(iconClass.equals("add")){
			method = "onInsert";
		}else if(iconClass.equals("list")){
			method = "onInfo";
		}else if(iconClass.equals("edit")){
			method = "onEdit";
		}else if(iconClass.equals("delete")){
			method = "onDelete";
		}else if(iconClass.equals("import")){
			method = "onImport";
		}else if(iconClass.equals("export")){
			method = "onExport";
		}
		return method;
	}
	/**
	 * 
	 *
	 * TODO ����ť�����Ӳ˵�������˵��������������ʱ�Զ���ȡ�˵������������.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��15��    	  ����    �½�
	 * </pre>
	 */
	public Map getMenuNoOrder(WebMenuInfo webMenuInfo){
		BigDecimal orderBy = null;
		String menuNo = "";
		Map map = new HashMap();
		//��ȡ�ϼ��˵��µ�����Ӳ˵��ı�ź�����
		//�˵�����Ϊ�յ�ʱ��
		if(!StringUtils.isNotEmpty(webMenuInfo.getMenuNo())){
			//�ϼ��˵����벻Ϊ�գ��Ӳ˵��Ͱ�ť ��ȡ�˵������������
			if(StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
				map = mapper.selectMenuInfo(webMenuInfo.getUpMenuNo());
				if(map != null && map.size() != 0){
					String menu = String.valueOf(map.get("MENUNO"));
					if(StringUtils.isNotEmpty(menu)){
						if(menu.length() != 0){
							String[] tempMenuNos = menu.split("-");
							for(int i = 0; i < tempMenuNos.length; i++){
								if(i == tempMenuNos.length -1){
									String tempMenuNo = tempMenuNos[tempMenuNos.length-1];
									int countMenuNo = Integer.parseInt(tempMenuNo) + 1;
									if(countMenuNo < 10){
										menuNo += "0" + String.valueOf(countMenuNo);
									}else{
										menuNo += countMenuNo;
									}
								}else{
									menuNo += tempMenuNos[i] + "-";
								}
							}
						}
					}
					orderBy = new BigDecimal(String.valueOf(map.get("ORDERBY")));
					orderBy = orderBy.add(new BigDecimal("1"));
				}else{
					menuNo = webMenuInfo.getUpMenuNo() + "-01";
					WebMenuInfo webMenu = new WebMenuInfo();
					webMenu.setMenuNo(webMenuInfo.getUpMenuNo());
					List<WebMenuInfo> webMenuList = mapper.selectByAttr(webMenu);
					if(webMenuList != null && webMenuList.size() != 0){
						Map menuMap = mapper.selectMenuInfo(webMenuList.get(0).getUpMenuNo());
						if(menuMap != null && menuMap.size() != 0){
							orderBy = new BigDecimal(String.valueOf(menuMap.get("ORDERBY")));
							orderBy = orderBy.add(new BigDecimal("1"));
						}
					}
				}
			}
		}
		//�����ţ���ȡ������
		if(!StringUtils.isNotEmpty(webMenuInfo.getOrderNo())){
			if(!StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
				String orderNo = mapper.selectMenuOrder();
				orderBy = new BigDecimal(orderNo);
				orderBy = orderBy.add(new BigDecimal("1"));
			}
		}
		
		Map resultMap = new HashMap();
		resultMap.put("menuNo", menuNo);
		resultMap.put("orderBy", orderBy);
		
		return resultMap;
	}
	/**
	 * 
	 *
	 * TODO �޸Ĳ˵�.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��22��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json updateWebMenuInfo(WebMenuInfo webMenuInfo, FdOper fdOper) throws Exception {
		checkUpdateData(webMenuInfo);
		
		Map map = getMenuNoOrder(webMenuInfo);
		String menuNo = String.valueOf(map.get("menuNo"));
		String orderBy = String.valueOf(map.get("orderBy"));
		//����ǰ�ť��url���������ƴ��
		if("2".equals(webMenuInfo.getMenuType())){
			String iconClass = webMenuInfo.getMenuIcon().replace(".gif", "");
			String method = getMethod(iconClass);
			String url = "";
			if(StringUtils.isNotEmpty(webMenuInfo.getMenuUrl())){
				url = "{text : '"+webMenuInfo.getMenuName()+"', click : " + webMenuInfo.getMenuUrl() + ", iconClass : 'icon_"+ iconClass +"'}";
			}else{
				url = "{text : '"+webMenuInfo.getMenuName()+"', click : " + method + ", iconClass : 'icon_"+ iconClass +"'}";
			}
			webMenuInfo.setMenuUrl(url);
			webMenuInfo.setIsParent("0");
		}
		if(!StringUtils.isNotEmpty(webMenuInfo.getMenuNo())){
			webMenuInfo.setMenuNo(menuNo);
		}
		if(!StringUtils.isNotEmpty(webMenuInfo.getOrderNo())){
			webMenuInfo.setOrderNo(orderBy);
		}
		if("1".equals(webMenuInfo.getIsParent())){
			if(!StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
				webMenuInfo.setUpMenuNo("0");
			}
		}
		webMenuInfo.setMenuIcon("/libs/icons/"+webMenuInfo.getMenuIcon());
		webMenuInfo.setLatestModifyDate(fdBusinessDateService.getCommonDateDetails());
		webMenuInfo.setLatestModifyTime(BocoUtils.getNowTime());
		int count = updateByPK(webMenuInfo);
		//�������ݿ�ʧ��
		if(count != 1){
			throw new SystemException("�޸�ʧ��!");
		}
		return this.json.returnMsg("true", "�޸ĳɹ�!");
	}
	/**
	 * 
	 *
	 * TODO �˵�ɾ��.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��22��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json deleteWebMenuInfo(WebMenuInfo webMenuInfo) throws Exception {
		//�����޸�У�����
		boolean check = checkDeleteData(webMenuInfo);
		//��֤ʧ��
		if(check == false){
			return this.json;
		}
		//��֤�ɹ�
		webMenuInfo.setMenuStatus("2");
		int count = updateByPK(webMenuInfo);
		//�޸����ݿ�ʧ��
		if(count != 1){
			throw new SystemException("ɾ��ʧ��!");
		}
		return this.json.returnMsg("true", "ɾ���ɹ�!");
	}
	
	/**
	 * 
	 *
	 * TODO �˵�������֤����.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��22��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkInsertData(WebMenuInfo webMenuInfo) throws Exception{
		//��֤����Ĳ˵���Ϣ�����ݿ����Ƿ��Ѵ���
		WebMenuInfo exist = new WebMenuInfo();
		exist.setId(webMenuInfo.getId());
		int existCount = countByAttr(exist);
		if(existCount > 0){
			throw new SystemException("�˵���Ϣ�Ѵ��ڣ�����������!");
		}
		//������ڵ㣬�˵�����������룬������ӽڵ㣬���Բ�����˵����룬�ϼ��˵������������
		//���ڵ�
		if("1".equals(webMenuInfo.getIsParent())){
			if(!StringUtils.isNotEmpty(webMenuInfo.getMenuNo())){
				String errInfo = getErrorInfo("w314");
				throw new SystemException(errInfo);
			}
		}else{//�ӽڵ�
			if(!StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
				throw new SystemException(getErrorInfo("w315"));
			}
		}
		//��֤����Ĳ˵������Ƿ����
		if("1".equals(webMenuInfo.getMenuType())){
			WebMenuInfo existMenuName= new WebMenuInfo();
			existMenuName.setMenuName(webMenuInfo.getMenuName());
			int menuNameCount = countByAttr(existMenuName);
			if(menuNameCount > 0){
				throw new SystemException("�˵������Ѵ��ڣ�����������!");
			}
		}else if("2".equals(webMenuInfo.getMenuType())){
			WebMenuInfo existMenuName= new WebMenuInfo();
			existMenuName.setMenuName(webMenuInfo.getMenuName());
			existMenuName.setUpMenuNo(webMenuInfo.getUpMenuNo());
			int menuNameCount = countByAttr(existMenuName);
			if(menuNameCount > 0){
				throw new SystemException("�˵������Ѵ��ڣ�����������!");
			}
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO ɾ��У�����.
	 *
	 * @param webMenuInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��22��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	private boolean checkDeleteData(WebMenuInfo webMenuInfo) throws Exception {
		//��֤�ò˵����Ƿ����Ӳ˵�
		WebMenuInfo exist = new WebMenuInfo();
		exist.setUpMenuNo(webMenuInfo.getMenuNo());
		//�˵�����Ϊ�˵�
		exist.setMenuType("1");
		int existCount = countByAttr(exist);
		if(existCount > 0){
			throw new SystemException("�ò˵������Ӳ˵����޷�ɾ��!");
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO ��ѯĳ����ɫ�Ĺ��ܼ���.
	 *
	 * @param roleCode ��ɫ����
	 * @param menuType ��ѯ�˵���ť
	 * @param parentId ���ڵ�
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��2��    	    ����    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectRoleFuns(String opercode, String menuType, String parentId) throws RuntimeException{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("menuStatus", "1");//״̬:1-����
		map.put("menuType", menuType);//���ͣ�1-�˵�,2-��ť��3-����
		map.put("opercode", opercode);
		//���ӹ���
		if(StringUtils.isNotBlank(parentId)){
			map.put("upMenuNo", parentId);
		}
		return mapper.selectRoleFuns(map);
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯĳ���û��Ĳ˵���.
	 *
	 * @param opercode
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��2��    	    ����    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectRolesMenus(String opercode) throws RuntimeException{
		//�ò˵������Map��key����ֹ�����ɫ�й�ͬȨ�޳����ظ��˵������
		return this.selectRoleFuns(opercode, "1", null);//��˵�
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯĳ���û���ĳ���˵��µİ�ť��.
	 *
	 * @param opercode
	 * @param parentId
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��2��    	    ����    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectRolesBtns(String opercode, String parentId) throws RuntimeException{
		return this.selectRoleFuns(opercode, "2", parentId);//�鰴ť
	}
	/**
	 * 
	 *
	 * TODO ��ѯ�˵���Ŷ�Ӧ�������¼��˵���Ϣ��������ť��Ϣ.
	 *
	 * @param upMenuNo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��3��    	  ����    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectByNo(Map<String,Object> map){
		return mapper.selectByNo(map);
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
	@Override
	public List<WebMenuInfo> selectMenuByRole(List<String> roleCode) {
		return mapper.selectMenuByRole(roleCode);
	}
	/**
	 * 
	 *
	 * TODO ����ɫ�ַ�ֱ�Ӵ���sql�н��в�ѯ.
	 *
	 * @param map
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	  ����    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectMenuByAttr(String roleCodes) throws DataAccessException{
		return this.selectMenuByAttr(roleCodes, "1", null);//��˵�
	}
	/**
	 * 
	 *
	 * TODO ����ɫ�ַ�ֱ�Ӵ���sql�н��в�ѯ.
	 *
	 * @param map
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	  ����    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectBtnByAttr(String roleCodes, String parentId) throws DataAccessException{
		return this.selectMenuByAttr(roleCodes, "2", parentId);//��˵�
	}
	/**
	 * 
	 *
	 * TODO ���ݽ�ɫ��ȡ�˵���Ϣ.
	 *
	 * @param roleCodes
	 * @param menuType
	 * @param parentId
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	  ����    �½�
	 * </pre>
	 */
	private List<WebMenuInfo> selectMenuByAttr(String roleCodes,String menuType, String parentId) throws RuntimeException{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("menuStatus", "1");//״̬:1-����
		map.put("menuType", menuType);//���ͣ�1-�˵�
		map.put("roleCodes", roleCodes);
		//���ӹ���
		if(StringUtils.isNotBlank(parentId)){
			map.put("upMenuNo", parentId);
		}
		return mapper.selectMenuByAttr(map);
	}
	/**
	 * 
	 *
	 * TODO ��ȡ��ݲ˵�.
	 *
	 * @param roleCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	public List<WebMenuInfo> selectShortMenus(String opercode){
		return mapper.selectShortMenus(opercode);
	}
	/**
	 * 
	 *
	 * TODO �޸�У�����.
	 *
	 * @param webMenuInfo
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��15��    	  ����    �½�
	 * </pre>
	 */
	public void checkUpdateData(WebMenuInfo webMenuInfo){
		//������ڵ㣬�˵�����������룬������ӽڵ㣬���Բ�����˵����룬�ϼ��˵������������
		//���ڵ�
		if("1".equals(webMenuInfo.getIsParent())){
			if(!StringUtils.isNotEmpty(webMenuInfo.getMenuNo())){
				throw new SystemException("������˵�����");
			}
		}else{//�ӽڵ�
			if(!StringUtils.isNotEmpty(webMenuInfo.getUpMenuNo())){
				throw new SystemException("�������ϼ��˵�����");
			}
		}
	}
	
	/**
	 * 
	 *
	 * TODO ���ݹ�Ա��ɫ��ѯ��Ա���Բ鿴�ı���.
	 *
	 * @param roleCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��10��    	  ������    �½�
	 * </pre>
	 */
	@Override
	public List<WebMenuInfo> selectReportMenuByRole(List<String> roleCode) {
		// TODO Auto-generated method stub
		return  mapper.selectReportMenuByRole(roleCode);
	}
}