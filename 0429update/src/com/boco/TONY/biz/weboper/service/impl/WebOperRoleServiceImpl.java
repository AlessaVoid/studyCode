package com.boco.TONY.biz.weboper.service.impl;

import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.biz.weboper.POJO.DO.RoleUpdateInfo;
import com.boco.TONY.enums.RoleUpdateStateEnum;
import com.boco.TONY.biz.weboper.POJO.DO.WebOperRoleDO;
import com.boco.TONY.biz.weboper.POJO.VO.WebOperRoleVO;
import com.boco.TONY.biz.line.POJO.DTO.ProductLineInfoDTO;
import com.boco.SYS.mapper.WebOperRoleMapper;
import com.boco.TONY.biz.weboper.service.IWebOperRoleService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/**
 * 柜员角色业务逻辑层接口实现
 * @author tony
 * @describe WebOperRoleServiceImpl
 * @date 2019-09-07
 */
@Service
public class WebOperRoleServiceImpl implements IWebOperRoleService {
    @Autowired
    WebOperRoleMapper webOperRoleMapper;

    @Autowired

    private static final String COMMA = ",";
    private static final String COLON = ":";

    /**
     * 更新柜员拥有的角色集合[单条] 前端ajax同步更新
     */
    @Override

    public PlainResult<String> updateOperAndRoleRelation(WebOperRoleVO webOperRoleVO) {
        Preconditions.checkArgument(null != webOperRoleVO
                && null != webOperRoleVO.getModifyOper() && null != webOperRoleVO.getRoleId());
        PlainResult<String> result = new PlainResult<>();
        String mixRoleInfo = webOperRoleVO.getRoleId();
        String[] infos = mixRoleInfo.split(COMMA);
        Date currentTime = localDateTime2Date(LocalDateTime.now());
        WebOperRoleDO webOperRoleDO = new WebOperRoleDO();
        webOperRoleDO.setUpdateTime(currentTime);
        webOperRoleDO.setOperCode(webOperRoleVO.getOperCode());
        webOperRoleDO.setModifyOper(webOperRoleVO.getModifyOper());

        for (String info : infos) { //read role info
            String[] infoArr = info.split(COLON);
            String roleId = infoArr[0];
            int state = Integer.parseInt(infoArr[1]);
            Preconditions.checkArgument(state == RoleUpdateStateEnum.ROLE_UP.state
                    || state == RoleUpdateStateEnum.ROLE_DOWN.state, "State is not in (ROLE_UP,ROLE_DOWN)");
            webOperRoleDO.setRoleId(roleId);
            webOperRoleDO.setState(state);
            RoleUpdateInfo isRoleUpdateInfo = webOperRoleMapper.selectRoleByOperCodeAndRoleId(webOperRoleDO);

            try {
                if (null == isRoleUpdateInfo) {//no record exists
                    webOperRoleDO.setCreateTime(currentTime);
                    webOperRoleMapper.insertOperRole(webOperRoleDO);
                } else {
                    webOperRoleMapper.updateOperRole(webOperRoleDO);
                }
            } catch (Exception e) {
                return result.setCode(500).setMessage("inner error 500").setSuccess(false).build();
            }
        }
        return result.success("success", "update role_oper success");
    }

    /**
     * 订单任务/国际/注意时间格式使用TimeStamp,保证时区时间一致性
     */
    private synchronized Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 查询柜员拥有的角色集合
     */
    @Override
    public List<String> selectOwnRoleByOperCode(String operCode) {
        //mybatis no NPE
        return webOperRoleMapper.selectOwnRoleByOperCode(operCode);
    }

    @Override
    public ListResult<String> selectOwnRoleByOperCodeForListResult(String operCode) {
        List<String> roleList = selectOwnRoleByOperCode(operCode);
        ListResult<String> result = new ListResult<>();
        result.setCode(200).setData(roleList).setMessage("success").setSuccess(true);
        return result;
    }

    @Override
    public ListResult<ProductLineInfoDTO> selectProductLineInfo(String operCode) {

        return null;
    }
}
