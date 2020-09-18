package com.boco.SYS.mapper;

import com.boco.TONY.biz.weboper.POJO.DO.RoleUpdateInfo;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperRoleDO;
import com.boco.SYS.entity.FdOper;

import java.util.List;
import java.util.Map;

/**
 * 柜员-角色权限表
 *
 * @author tony
 * @describe WebOperRoleMapper
 * @date 2019-09-07
 */
public interface WebOperRoleMapper {
    /**插入柜员-角色关系*/
    void insertOperRole(WebOperRoleDO webOperRoleDO);

    /**更新柜员-角色关系*/
    void updateOperRole(WebOperRoleDO WebOperRoleDO);

    /** 查询柜员-角色*/
    List<String> selectOwnRoleByOperCode(String operCode);

    /**用于判断是否已经存在角色记录*/
    RoleUpdateInfo selectRoleByOperCodeAndRoleId(WebOperRoleDO webOperRoleDO);

    /**用于判断是否已经存在角色记录*/
    List<String> selectRoleCodeListByRoleId(String roleId);

    /** 根据机构号，角色码查询柜员信息 **/
    List<FdOper> getOperByRolecode(Map<String, String> map);

    void deleteWebOperPoleByOpercode(String opercode);
}
