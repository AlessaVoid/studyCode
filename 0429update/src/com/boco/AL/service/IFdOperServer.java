package com.boco.AL.service;

import com.boco.SYS.entity.FdOper;

import java.util.List;

/**
 * @Author daice
 * @Description //柜员
 * @Date 下午6:57 2019/11/14
 * @Param
 * @return
 **/
public interface IFdOperServer {

    /**
     * @Author daice
     * @Description //根据机构号，柜员角色，查询柜员信息
     * @Date 下午6:58 2019/11/14
     * @Param [organcode, rolecode]
     * @return com.boco.SYS.base.ListResult<com.boco.SYS.entity.FdOper>
     **/
    public List<FdOper> getOperByRolecode(String organcode, String rolecode,String opercode);
}
