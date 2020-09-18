package com.boco.AL.service;

import com.boco.SYS.entity.FdOper;

import java.util.List;

/**
 * @Author daice
 * @Description //��Ա
 * @Date ����6:57 2019/11/14
 * @Param
 * @return
 **/
public interface IFdOperServer {

    /**
     * @Author daice
     * @Description //���ݻ����ţ���Ա��ɫ����ѯ��Ա��Ϣ
     * @Date ����6:58 2019/11/14
     * @Param [organcode, rolecode]
     * @return com.boco.SYS.base.ListResult<com.boco.SYS.entity.FdOper>
     **/
    public List<FdOper> getOperByRolecode(String organcode, String rolecode,String opercode);
}
