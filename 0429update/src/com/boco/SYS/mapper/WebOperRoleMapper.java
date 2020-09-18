package com.boco.SYS.mapper;

import com.boco.TONY.biz.weboper.POJO.DO.RoleUpdateInfo;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperRoleDO;
import com.boco.SYS.entity.FdOper;

import java.util.List;
import java.util.Map;

/**
 * ��Ա-��ɫȨ�ޱ�
 *
 * @author tony
 * @describe WebOperRoleMapper
 * @date 2019-09-07
 */
public interface WebOperRoleMapper {
    /**�����Ա-��ɫ��ϵ*/
    void insertOperRole(WebOperRoleDO webOperRoleDO);

    /**���¹�Ա-��ɫ��ϵ*/
    void updateOperRole(WebOperRoleDO WebOperRoleDO);

    /** ��ѯ��Ա-��ɫ*/
    List<String> selectOwnRoleByOperCode(String operCode);

    /**�����ж��Ƿ��Ѿ����ڽ�ɫ��¼*/
    RoleUpdateInfo selectRoleByOperCodeAndRoleId(WebOperRoleDO webOperRoleDO);

    /**�����ж��Ƿ��Ѿ����ڽ�ɫ��¼*/
    List<String> selectRoleCodeListByRoleId(String roleId);

    /** ���ݻ����ţ���ɫ���ѯ��Ա��Ϣ **/
    List<FdOper> getOperByRolecode(Map<String, String> map);

    void deleteWebOperPoleByOpercode(String opercode);
}
