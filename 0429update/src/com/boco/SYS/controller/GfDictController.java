package com.boco.SYS.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.GfDict;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.service.IGfDictService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.MapUtil;
import com.boco.util.JsonUtils;
/**
 * 
 * 
 * Action���Ʋ�
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/gfDict/")
public class GfDictController extends BaseController{
	@Autowired
	private IGfDictService gfDictService;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;
	
	//�����б���ϸ��Ϣ���������޸�ҳ��
	@RequestMapping("listUI")
	@SystemLog(tradeName="�ֵ���Ϣ�б�",funCode="PM-08",funName="�����б�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String listUI() throws Exception {
		authButtons();
		return basePath + "/PM/gfDict/gfDictList";
	}
	@RequestMapping("infoUI")
	@SystemLog(tradeName="�ֵ���ϸ��Ϣ",funCode="PM-08-04",funName="������ϸҳ��",accessType=AccessType.READ,level=Debug.INFO)
	public String infoUI(GfDict gfDict) throws Exception {
		gfDict.setDictName(BocoUtils.UTF8String(gfDict.getDictName(), "UTF-8"));
		gfDict.setDictNo(BocoUtils.UTF8String(gfDict.getDictNo(), "UTF-8"));
		gfDict.setDictKeyIn(BocoUtils.UTF8String(gfDict.getDictKeyIn(), "UTF-8"));
		setAttribute("dict", gfDictService.selectByPK(MapUtil.beanToMap(gfDict)));
		return basePath + "/PM/gfDict/gfDictInfo";
	}
	@RequestMapping("insertUI")
	@SystemLog(tradeName="�����ֵ���Ϣ",funCode="PM-08-01", funName="��������ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String insertUI() throws Exception {
		return basePath + "/PM/gfDict/gfDictAdd";
	}
	@RequestMapping("updateUI")
	@SystemLog(tradeName="�޸��ֵ���Ϣ",funCode="PM-08-02", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String updateUI(GfDict gfDict) throws Exception {
		gfDict.setDictName(BocoUtils.UTF8String(gfDict.getDictName(), "UTF-8"));
		setAttribute("dict", gfDictService.selectByPK(MapUtil.beanToMap(gfDict)));
		return basePath + "/PM/gfDict/gfDictEdit";
	}
	@RequestMapping("coypUI")
	@SystemLog(tradeName="�޸��ֵ���Ϣ",funCode="PM-08-05", funName="�����޸�ҳ��",accessType=AccessType.READ,level=Debug.DEBUG)
	public String coypUI(GfDict gfDict) throws Exception {
		gfDict.setDictName(BocoUtils.UTF8String(gfDict.getDictName(), "UTF-8"));
		setAttribute("dict", gfDictService.selectByPK(MapUtil.beanToMap(gfDict)));
		return basePath + "/PM/gfDict/gfDictEdit_copy";
	}
	
	/**
	 * 
	 *
	 * TODO ��ѯGF_DICT��ҳ����
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@SystemLog(tradeName="�ֵ��б���Ϣ",funCode="PM-08",funName="�����б�����",accessType=AccessType.READ,level=Debug.INFO)
	public @ResponseBody String findPage(GfDict gfDict) throws Exception {
		setPageParam();
		List<GfDict> list = gfDictService.selectByAttr(gfDict);
		return pageData(list);
	}
	/**
	 * 
	 *
	 * TODO ����GF_DICT.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "insert")
	@SystemLog(tradeName="�����ֵ���Ϣ",funCode="PM-08-01",funName="����",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String insert(GfDict gfDict) throws Exception{
		 
		//��ǰ���ڡ���ǰʱ��
		gfDict.setUpdateDate(fdBusinessDateService.getCommonDateDetails());
		gfDict.setUpdateTime(BocoUtils.getNowTime());
		this.json = gfDictService.insert(gfDict);
		return this.json.toJson();
	}
	
	/**
	 * 
	 *
	 * TODO �޸�GF_DICT.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��18��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "update")
	@SystemLog(tradeName="�޸��ֵ���Ϣ",funCode="PM-08-02",funName="�޸�",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String update(GfDict gfDict) throws Exception{
		
		//��ǰ���ڡ���ǰʱ��
		gfDict.setUpdateDate(fdBusinessDateService.getCommonDateDetails());
		gfDict.setUpdateTime(BocoUtils.getNowTime());
		
		this.json = gfDictService.update(gfDict);
		return this.json.toJson();
	}

	/**
	 * 
	 *
	 * TODO ɾ��GF_DICT
	 *  
	 *  @return
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      ����      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "delete")
	@SystemLog(tradeName="ɾ���ֵ���Ϣ",funCode="PM-08-03",funName="ɾ��",accessType=AccessType.WRITE,level=Debug.INFO)
	public @ResponseBody String delete(GfDict gfDict) throws Exception {
		gfDictService.deleteByPK(MapUtil.beanToMap(gfDict));
		return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
	}
	/**
	 * 
	 *
	 * TODO ��ȡ�ֵ��������ΪdicType���鲢��װ��ҳ���ѡ���������ݸ�ʽ.
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��8��    	    ������   �½�
	 * </pre>
	 */
	@RequestMapping(value = "getTreeDic")
	@SystemLog(tradeName="��ȡ������ѡ��",funCode="SYSTEM",funName="��ѯ",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String getTreeDic(HttpServletRequest request) throws Exception {
		String dicType=(String) request.getParameter("dicType");
		String  type=request.getParameter("type");
		GfDict gfDict=new GfDict();
		gfDict.setDictNo(dicType);
		List<GfDict> list=gfDictService.selectByAttr(gfDict);
		Map<String, Object> results = new Hashtable<String, Object>();
		List<Map<String,Object>> treelist=new ArrayList<Map<String,Object>>();
		Map<String,Object> treeMap=new HashMap<String,Object>();
		treeMap.put("id", "c");
		treeMap.put("parentId", "p");
		treeMap.put("name", "ȫѡ");
		treelist.add(treeMap);
		for(GfDict info:list){
			treeMap=new HashMap<String,Object>();
			treeMap.put("id", info.getDictKeyIn());
			treeMap.put("parentId", "c");
			treeMap.put("name", info.getDictValueIn());
			if (null!=type) {
				treeMap.put("chkDisabled", "true");
			}
			treelist.add(treeMap);
		}
		results.put("treeNodes", treelist);
		return JsonUtils.toJson(results);
	}
	/**
	 * 
	 *
	 * TODO ���������
	 *  
	 *  @return
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      �غ���      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "selectDictNo")
	@SystemLog(tradeName="����Ӣ������������",funCode="PM-08",funName="��������",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String selectCode(GfDict gfDict,HttpServletRequest request) throws Exception {
		String dictNo = request.getParameter("dictNo").replace("'","");
		if(dictNo!=null){
			gfDict.setDictNo(dictNo);
		}
		Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Set<String> set = new TreeSet<String>();
		List<Map<String, String>> dict = gfDictService.selectDictNo(gfDict);
		for (Map<String, String> dictInfo : dict) {
			String data = dictInfo.get("dict_no");
			set.add(data);
		}
		
		for (String data : set) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("key", data);
			map.put("value", data);
			list.add(map);
		}

		resultMap.put("list", list);
		String json = JsonUtils.toJson(resultMap);
		return json;
	}
	/**
	 * 
	 *
	 * TODO ���������
	 *  
	 *  @return
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      �غ���      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "selectDictName")
	@SystemLog(tradeName="������������������",funCode="PM-08",funName="��������",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String selectDictName(GfDict gfDict,HttpServletRequest request) throws Exception {
		String dictName = request.getParameter("dictName").replace("'","");
		if(dictName!=null){
			gfDict.setDictName(dictName);
		}
		Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Set<String> set = new TreeSet<String>();
		List<Map<String, String>> dict = gfDictService.selectDictName(gfDict);
		for (Map<String, String> dictInfo : dict) {
			String data = dictInfo.get("dict_name");
			set.add(data);
		}
		
		for (String data : set) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("key", data);
			map.put("value", data);
			list.add(map);
		}

		resultMap.put("list", list);
		String json = JsonUtils.toJson(resultMap);
		return json;
	}
	/**
	 * 
	 *
	 * TODO ���������
	 *  
	 *  @return
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2014-10-29      �غ���      �����½�
	 * </pre>
	 */
	@RequestMapping(value = "selectCreateOper")
	@SystemLog(tradeName="������Ա��������",funCode="PM-08",funName="��������",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String selectCreateOper(GfDict gfDict,HttpServletRequest request) throws Exception {
		String createOper = request.getParameter("createOper").replace("'","");
		if(createOper!=null){
			gfDict.setCreateOper(createOper);
		}
		Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Set<String> set = new TreeSet<String>();
		List<Map<String, String>> dict = gfDictService.selectCreateOper(gfDict);
		for (Map<String, String> dictInfo : dict) {
			String data = dictInfo.get("create_oper");
			set.add(data);
		}
		
		for (String data : set) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("key", data);
			map.put("value", data);
			list.add(map);
		}

		resultMap.put("list", list);
		String json = JsonUtils.toJson(resultMap);
		return json;
	}
	/**
	 * 
	 *
	 * TODO ��ȡ��ѯ��Ʒ״̬��Χ
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��18��    	   ������  �½�
	 * </pre>
	 */
	@RequestMapping(value = "getPordStatusTreeDic")
	@SystemLog(tradeName="��ȡ������ѡ��",funCode="SYSTEM",funName="��ѯ",accessType=AccessType.READ,level=Debug.DEBUG)
	public @ResponseBody String getPordStatusTreeDic(HttpServletRequest request) throws Exception {
		String dicType=(String) request.getParameter("dicType");
		String  type=request.getParameter("type");
		String  prodStatus=request.getParameter("prodStatus");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("dicNo", dicType);
		map.put("valKeys", prodStatus.split(","));
		List<GfDict> list=gfDictService.findByValINKeys(map);
		Map<String, Object> results = new Hashtable<String, Object>();
		List<Map<String,Object>> treelist=new ArrayList<Map<String,Object>>();
		Map<String,Object> treeMap=new HashMap<String,Object>();
		treeMap.put("id", "c");
		treeMap.put("parentId", "p");
		treeMap.put("name", "ȫѡ");
		treelist.add(treeMap);
		for(GfDict info:list){
			treeMap=new HashMap<String,Object>();
			treeMap.put("id", info.getDictKeyIn());
			treeMap.put("parentId", "c");
			treeMap.put("name", info.getDictValueIn());
			if (null!=type) {
				treeMap.put("chkDisabled", "true");
			}
			treelist.add(treeMap);
		}
		results.put("treeNodes", treelist);
		return JsonUtils.toJson(results);
	}
}