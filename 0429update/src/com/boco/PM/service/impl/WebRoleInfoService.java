package com.boco.PM.service.impl;

import com.boco.PM.service.IWebRoleInfoService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebRoleFun;
import com.boco.SYS.entity.WebRoleInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.*;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * WebRoleInfo业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class WebRoleInfoService extends GenericService<WebRoleInfo, String> implements IWebRoleInfoService {
    @Autowired
    private FdOperMapper fdOperMapper;
    @Autowired
    private WebRoleFunMapper webRoleFunMapper;
    @Autowired
    private WebRoleInfoMapper webRoleInfoMapper;
    @Autowired
    private WebSublicenseInfoMapper webSublicenseInfoMapper;
    @Autowired
    private IFdBusinessDateService fdBusinessDateService;
    @Autowired
    private WebOperRoleMapper webOperRoleMapper;

    private Json json = new Json();

    /**
     * TODO 新增角色信息.
     *
     * @param webRoleInfo
     * @param fdOper
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月23日    	  杜旭    新建
     * </pre>
     * @throws Exception
     */
    @Override
    public Json InsertWebRoleInfo(WebRoleInfo webRoleInfo, FdOper fdOper, String funCodes) throws Exception {
        checkInsertData(webRoleInfo);
        //插入角色信息
        webRoleInfo.setLatestOperCode(fdOper.getOpercode());
        webRoleInfo.setLatestModifyDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        webRoleInfo.setLatestModifyTime(BocoUtils.getNowTime());
        int count = insertEntity(webRoleInfo);
        if (count != 1) {
            throw new SystemException("新增角色信息失败");
        }
        //插入角色相对应的权限信息
        String[] funs = funCodes.split("\\,");
        int funsCount = funs.length;
        int insertCount = insertBatchRoleFun(webRoleInfo.getRoleCode(), funCodes, fdOper.getOpercode());
        if (insertCount != funsCount) {
            throw new SystemException("新增权限信息失败");
        }
        return this.json.returnMsg("true", "新增成功");
    }

    /**
     * TODO 修改角色信息.
     *
     * @param webRoleInfo
     * @param fdOper
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月23日    	  杜旭    新建
     * </pre>
     * @throws Exception
     */
    public Json updateWebRoleInfo(WebRoleInfo webRoleInfo, FdOper fdOper, String funCodes) throws Exception {
        checkUpdateData(webRoleInfo);
        //修改角色信息
        webRoleInfo.setLatestOperCode(fdOper.getOpercode());
        webRoleInfo.setLatestModifyDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        webRoleInfo.setLatestModifyTime(BocoUtils.getNowTime());
        int count = updateByPK(webRoleInfo);
        if (count != 1) {
            throw new SystemException("修改角色信息失败");
        }
        //删除角色功能表中该角色对应的权限信息
        webRoleFunMapper.deleteFunsByRole(webRoleInfo.getRoleCode());
        //插入角色相对应的权限信息
        String[] funs = funCodes.split("\\,");
        int funsCount = funs.length;
        int insertCount = insertBatchRoleFun(webRoleInfo.getRoleCode(), funCodes, fdOper.getOpercode());
        if (insertCount != funsCount) {
            throw new SystemException("新增权限信息失败");
        }
        return this.json.returnMsg("true", "修改成功");
    }

    /**
     * TODO 删除角色信息.
     *
     * @param webRoleInfo
     * @param fdOper
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月23日    	  杜旭    新建
     * </pre>
     * @throws Exception
     */
    public Json deleteWebRoleInfo(WebRoleInfo webRoleInfo) throws Exception {
        checkDeleteData(webRoleInfo);
        //根据主键删除角色
        int count = deleteByPK(webRoleInfo.getRoleCode());
        if (count != 1) {
            throw new SystemException("删除失败");
        }
        //删除角色相对应的权限信息
        webRoleFunMapper.deleteFunsByRole(webRoleInfo.getRoleCode());
        return this.json.returnMsg("true", "删除成功");
    }

    /**
     * TODO 批量新增权限集.
     *
     * @param roleCode
     * @param funCodes
     * @param opercode
     * @return
     * @throws RuntimeException <pre>
     *                                                                                                     修改日期        修改人    修改原因
     *                                                                                                     2016年2月2日    	    杨滔    新建
     *                                                                                                     </pre>
     */
    public int insertBatchRoleFun(String roleCode, String funCodes, String opercode) throws RuntimeException {
        String[] funCodeArr = funCodes.split(",");
        WebRoleFun roleFun = null;
        List<WebRoleFun> list = new ArrayList<WebRoleFun>();
        String currentDate = fdBusinessDateService.getCommonDateDetails();
        for (String funCode : funCodeArr) {
            roleFun = new WebRoleFun();
            roleFun.setFunCode(funCode);
            roleFun.setRoleCode(roleCode);
            roleFun.setLatestOperCode(opercode);
            roleFun.setLatestModifyDate(currentDate);
            roleFun.setLatestModifyTime(BocoUtils.getNowTime1());
            list.add(roleFun);
        }
        return webRoleFunMapper.insertBatch(list);
    }

    /**
     * TODO 新增校验规则.
     *
     * @param webRoleInfo
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月23日    	  杜旭    新建
     * </pre>
     * @throws Exception
     */
    public boolean checkInsertData(WebRoleInfo webRoleInfo) throws Exception {
        String req = "[\u4e00-\u9fa5]*+[a-zA-Z]*+";
        if (Pattern.matches(req, webRoleInfo.getRoleName()) == false) {
            //角色名称包含非法字符，请输入中文或英文
            throw new SystemException("w417");
        }

        //验证角色名称的长度
        if (BocoUtils.getStrLength(webRoleInfo.getRoleName(), 2) > 200) {
            //角色名称的长度不能超过100!
            throw new SystemException("w418");
        }
        //验证角色代码是否存在
        WebRoleInfo existRoleCode = new WebRoleInfo();
        existRoleCode.setRoleCode(webRoleInfo.getRoleCode());
        int roleCodeCount = countByAttr(existRoleCode);
        if (roleCodeCount > 0) {
            //角色代码已存在，请重新输入!
            throw new SystemException("w419");
        }
        //验证同机构级别下角色名称是否存在
        WebRoleInfo existRoleName = new WebRoleInfo();
        existRoleName.setRoleName(webRoleInfo.getRoleName());
        existRoleName.setOrganLevel(webRoleInfo.getOrganLevel());
        int roleNameCount = countByAttr(existRoleName);
        if (roleNameCount > 0) {
            //该级别下，角色名称已存在，请重新输入!
            throw new SystemException("w420");
        }
        return true;
    }

    /**
     * TODO 修改校验规则.
     *
     * @param webRoleInfo
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月23日    	  杜旭    新建
     * </pre>
     * @throws Exception
     */
    private boolean checkUpdateData(WebRoleInfo webRoleInfo) throws Exception {
        String req = "[\u4e00-\u9fa5]*+[a-zA-Z]*+";
        if (Pattern.matches(req, webRoleInfo.getRoleName()) == false) {
            //角色名称包含非法字符，请输入中文或英文
            throw new SystemException("w417");
        }
        //验证角色名称的长度
        if (BocoUtils.getStrLength(webRoleInfo.getRoleName(), 2) > 200) {
            //角色名称的长度不能超过100!
            throw new SystemException("w418");
        }
        //验证同机构下级别角色名称是否存在
        WebRoleInfo existRoleName = new WebRoleInfo();
        existRoleName.setRoleName(webRoleInfo.getRoleName());
        existRoleName.setOrganLevel(webRoleInfo.getOrganLevel());
        List<WebRoleInfo> roles = selectByAttr(existRoleName);
        if (roles.size() != 0) {
            for (WebRoleInfo role : roles) {
                if (!(role.getRoleCode().equals(webRoleInfo.getRoleCode()) && role.getOrganLevel().equals(webRoleInfo.getOrganLevel()))) {
                    //该级别下，角色名称已存在，请重新输入!
                    throw new SystemException("w420");
                }
            }
        }
        return true;
    }

    /**
     * TODO 删除校验规则.
     *
     * @param webRoleInfo
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月23日    	  杜旭    新建
     * </pre>
     */
    private boolean checkDeleteData(WebRoleInfo webRoleInfo) {
        //验证该角色下是否有柜员存在
        FdOper fdOper = new FdOper();
        fdOper.setOperdegree(webRoleInfo.getRoleCode());
        List<String> roleInfoList = webOperRoleMapper.selectRoleCodeListByRoleId(webRoleInfo.getRoleCode());
        if (CollectionUtils.isNotEmpty(roleInfoList)) {
            //该角色下存在柜员，无法删除!
            throw new SystemException("w421");
        }
        //验证该角色是否存在未收回状态的转授权信息
        return true;
    }

    /**
     * TODO 角色名称模糊查询记录.
     *
     * @param webRoleInfo
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年3月8日    	  杜旭    新建
     * </pre>
     */
    public List<WebRoleInfo> selectByLike(WebRoleInfo webRoleInfo) {
        return webRoleInfoMapper.selectByLike(webRoleInfo);
    }
    public List<WebRoleInfo> selectByLikeOrder(WebRoleInfo webRoleInfo) {
         return webRoleInfoMapper.selectByLikeOrder(webRoleInfo);
    }
    @Override
    public List<WebRoleInfo> selectByOrganLevel(String organLevel,String operCode) {
        return webRoleInfoMapper.selectByOrganLevel(organLevel,operCode);
    }


    /**
     * 联想输入角色代码
     */
    @Override
    public List<Map<String, String>> selectRoleCode(WebRoleInfo webRoleInfo) {
        List<Map<String, String>> list = webRoleInfoMapper.selectRoleCode(webRoleInfo);
        return list;
    }

    /**
     * 联想输入角色名称
     */
    @Override
    public List<Map<String, String>> selectRoleName(WebRoleInfo webRoleInfo) {
        List<Map<String, String>> list = webRoleInfoMapper.selectRoleName(webRoleInfo);
        return list;
    }

    /**
     * TODO 根据角色编码集查询角色名称.
     *
     * @param roleCode
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年5月26日    	  杜旭    新建
     * </pre>
     */
    @Override
    public String findNameByProdCodes(String roleCode) {
        String roleName = "", roleCodes = "";
        if (StringUtils.isNotEmpty(roleCode)) {
            for (int i = 0; i < roleCode.length(); i = i + 3) {
                roleCodes += "," + roleCode.substring(i, i + 3);
            }
            if (roleCodes.length() > 0) {
                roleCodes = roleCodes.substring(1);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("roleCodes", roleCodes.split(","));
            List<WebRoleInfo> list = webRoleInfoMapper.selectByRoleCodes(map);
            if (list.size() > 0) {
                for (WebRoleInfo info : list) {
                    roleName += "," + info.getRoleName();
                }
            }
            if (roleName.length() > 0) {
                roleName = roleName.substring(1);
            }
        }
        return roleName;
    }

    @Override
    public String findNameByRoleCode(String roleCode) {
        String roleName = "";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleCodes", roleCode.split(","));
        List<WebRoleInfo> list = webRoleInfoMapper.selectByRoleCodes(map);
        if (list.size() > 0) {
            for (WebRoleInfo info : list) {
                roleName += "," + info.getRoleName();
            }
        }
        if (roleName.length() > 0) {
            roleName = roleName.substring(1);
        }
        return roleName;
    }
}