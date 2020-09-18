package com.boco.PM.service.impl;

import com.boco.PM.service.IFdOperService;
import com.boco.PM.service.IWebOperInfoService;
import com.boco.SYS.base.GenericService;
import com.boco.TONY.biz.line.POJO.DO.ProductLineInfoDO;
import com.boco.TONY.biz.line.exception.LineProductException;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperLineDO;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.entity.WebRoleInfo;
import com.boco.TONY.biz.weboper.exception.OperLineException;
import com.boco.SYS.mapper.*;
import com.boco.SYS.util.Json;
import com.boco.SYS.util.MapUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * WebOperInfo业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class WebOperInfoService extends GenericService<WebOperInfo, HashMap<String, Object>> implements IWebOperInfoService {
    @Autowired
    private WebOperInfoMapper webOperInfoMapper;
    @Autowired
    private IFdOperService fdOperService;
    @Autowired
    WebOperLineMapper webOperLineMapper;
    @Autowired
    LineProductMapper lineProductMapper;
    @Autowired
    WebOperRoleMapper webOperRoleMapper;
    @Autowired
    WebRoleInfoMapper webRoleInfoMapper;
    @Autowired
    FdOrganMapper fdOrganMapper;

    private static final int LINE_USABLE = 1;

    /**
     * TODO 查询列表页
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29     秦海洲      批量新建
     * </pre>
     */
    @Override
    public Map<String, Object> select(WebOperInfo webOperInfo, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true, true, true);
        List<WebOperInfo> webOperInfoList = webOperInfoMapper.selectByLikeAttr(webOperInfo);
        String organLevel=webOperInfo.getOrganCode();
        //返回页面的分页数据
        Map<String, Object> results = new Hashtable<>();
        if (CollectionUtils.isEmpty(webOperInfoList)) {
            results.put("pager.pageNo", 1);
            results.put("pager.totalRows", 0);
            results.put("rows", new ArrayList<>());
        }
        PageInfo<WebOperInfo> page = new PageInfo<>(webOperInfoList);
        results.put("pager.pageNo", page.getPageNum());
        results.put("pager.totalRows", page.getTotal());

        WebOperLineDO webOperLineDO = new WebOperLineDO().setStatus(LINE_USABLE);
        List<WebOperInfo> collect = webOperInfoList.stream().peek(item -> {
            List<String> roleIdList = webOperRoleMapper.selectOwnRoleByOperCode(item.getOperCode());
            String roleName = "";
            for (String roleId : roleIdList) {
                WebRoleInfo webRoleInfo = webRoleInfoMapper.selectByPK(roleId);
                if (Objects.nonNull(webRoleInfo)) {
                    roleName += webRoleInfo.getRoleName() + ",";
                }
            }
            webOperLineDO.setOperCode(item.getOperCode());
            try {
                List<WebOperLineDO> webOperLineDOList = webOperLineMapper.getAllWebOperLineByOperCode(webOperLineDO);
                String lineName = "";
                for (WebOperLineDO operLineDO : webOperLineDOList) {
                    ProductLineInfoDO lineInfoDO = lineProductMapper.getProductLineInfoByLineId(operLineDO.getLineId());
                    if (Objects.nonNull(lineInfoDO)) {
                        lineName += lineInfoDO.getLineName() + ",";
                    }
                }
                if (StringUtils.isBlank(lineName)) {
                    lineName = "无";
                } else {
                    lineName = lineName.substring(0, lineName.length() - 1);
                }
                if (StringUtils.isBlank(roleName)) {
                    roleName = "无";
                } else {
                    roleName = roleName.substring(0, roleName.length() - 1);
                }
                item.setRoleName(roleName);
                item.setLineName(lineName);
            } catch (OperLineException e) {
                e.printStackTrace();
            }
        }).collect(Collectors.toList());
        results.put("rows", collect);
        return results;
    }

    @Override
    public Map<String, Object> selectList(Map map, int pageNum, int pageSize) throws OperLineException, LineProductException {
        return null;
    }

    /**
     * TODO 新增
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29     秦海洲      批量新建
     * </pre>
     */
    @Override
    public Json insert(WebOperInfo webOperInfo) throws Exception {
        //调用新增校验规则
        boolean check = checkInsertData(webOperInfo);
        //验证失败
        if (check == false) {
            return this.json;
        }
        FdOper fdOper = new FdOper();
        fdOper.setOrgancode(webOperInfo.getOrganCode());
        fdOper.setOpercode(webOperInfo.getOperCode());
        try {
            fdOper = fdOperService.selectByPK(MapUtil.beanToMap(fdOper));
        } catch (Exception e) {
            return this.json.returnMsg("true", "该人员信息异常，请联系管理人员进行维护");
        }
        if (!"".equals(fdOper.getOpername())) {
            webOperInfo.setOperName(fdOper.getOpername());
        }
        int count = insertEntity(webOperInfo);
        //插入数据库失败
        if (count == 1) {
            //"新增成功!"
            return this.json.returnMsg("true", getErrorInfo("w456"));
        }
        //新增失败
        return this.json.returnMsg("false", getErrorInfo("w446"));

    }


    /**
     * TODO 新增校验
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29     秦海洲      批量新建
     * </pre>
     */
    private boolean checkInsertData(WebOperInfo webOperInfo) throws Exception {
        //判断有没有此柜员
        FdOper fdOper = new FdOper();
        fdOper.setOrgancode(webOperInfo.getOrganCode());
        fdOper.setOpercode(webOperInfo.getOperCode());
        FdOrgan fdOrgan = fdOrganMapper.selectByPK(webOperInfo.getOrganCode());
        if (Objects.isNull(fdOrgan)) {
            this.json.returnMsg("false", getErrorInfo("w423"));
            return false;
        }
        int count = fdOperService.countByAttr(fdOper);

        if (count == 0) {
            //"没有此柜员，请重新输入!"
            this.json.returnMsg("false", getErrorInfo("w455"));
            return false;
        }
        //验证柜员是否已存在
        WebOperInfo operInfo = new WebOperInfo();
        operInfo.setOperCode(webOperInfo.getOperCode());
        operInfo.setOrganCode(webOperInfo.getOrganCode());
        int MenuNoCount = countByAttr(operInfo);
        if (MenuNoCount > 0) {
            //"柜员已存在，请重新输入!"
            this.json.returnMsg("false", getErrorInfo("w457"));
            return false;
        }
//        //验证证件号码是否已存在
//        WebOperInfo certificatecode = new WebOperInfo();
//        certificatecode.setCertificatecode(webOperInfo.getCertificatecode());
//        int certificatecodeCount = countByAttr(certificatecode);
//        if (certificatecodeCount > 0) {
//            //"证件号已存在，请重新输入!"
//            this.json.returnMsg("false", getErrorInfo("w458"));
//            return false;
//        }
        return true;
    }

    /**
     * TODO 联想输入框（柜员号）
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29     秦海洲      批量新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectOperCodeLike(WebOperInfo webOperInfo) {
        List<Map<String, String>> list = webOperInfoMapper.selectOperCodeLike(webOperInfo);
        return list;
    }

    @Override
    public WebOperInfo selectOperCode(WebOperInfo webOperInfo) {
        return webOperInfoMapper.selectOperCode(webOperInfo);
    }

    /**
     * TODO 联想输入框（姓名）
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29     秦海洲      批量新建
     * </pre>
     */
    @Override
    public List<Map<String, String>> selectOperName(WebOperInfo webOperInfo) {
        List<Map<String, String>> list = webOperInfoMapper.selectOperName(webOperInfo);
        return list;
    }

    /**
     * TODO 修改
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29     秦海洲      批量新建
     * </pre>
     */
    @Override
    public Json update(WebOperInfo webOperInfo) throws Exception {
        //调用修改校验规则
        boolean check = checkUpdateData(webOperInfo);
        //验证失败
        if (check == false) {
            return this.json;
        }
        int count = updateByPK(webOperInfo);
        //插入数据库失败
        if (count == 1) {
            //"新增成功!"
            return this.json.returnMsg("true", getErrorInfo("w448"));
        }
        //新增失败
        return this.json.returnMsg("false", getErrorInfo("w447"));
    }

    /**
     * TODO 修改验证.
     *
     * @param webOperInfo
     * @return
     * @throws Exception <pre>
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   修改日期        修改人    修改原因
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   2016年5月13日    	    秦海舟   新建
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   </pre>
     */
    private boolean checkUpdateData(WebOperInfo webOperInfo) throws Exception {
		/*//验证证件号码是否已存在
		WebOperInfo certificatecode = new WebOperInfo();
		certificatecode.setCertificatecode(webOperInfo.getCertificatecode());
		int certificatecodeCount = countByAttr(certificatecode);
		if(certificatecodeCount > 0){
			//"证件号已存在，请重新输入!"
			this.json.returnMsg("false", getErrorInfo("w458"));
			return false;
		}*/
        return true;
    }

    @Override
    public String selectOperCodeByName(String prodOperName) throws Exception {
        return webOperInfoMapper.selectOperCodeByName(prodOperName);
    }
}