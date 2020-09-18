package com.boco.TONY.auth.realm;

import com.boco.PM.service.IFdOperService;
import com.boco.TONY.enums.OperStateEnum;
import com.boco.TONY.biz.weboper.service.IWebOperRoleService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色权限控制表realm
 *
 * @author tony
 * @describe OperatorRealm
 * @date 2019-09-11
 */
public class OperatorRealm extends AuthorizingRealm {
    @Autowired
    IWebOperRoleService webOperRoleService;
    @Autowired
    private IFdOperService fdOperService;

    @Override
    public String getName() {
        return "OperatorRealm";
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String operCode = (String) principals.getPrimaryPrincipal();
        //根据用户名查询相关的权限判断权限
        List<String> roles = webOperRoleService.selectOwnRoleByOperCode(operCode);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //赋予用户角色
        Set<String> roleSets = new HashSet<>(roles);
        simpleAuthorizationInfo.setRoles(roleSets);
        return simpleAuthorizationInfo;
    }



    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String operCode = (String) authenticationToken.getPrincipal();
        char[] credentials = (char[]) authenticationToken.getCredentials();
        String password = new String(credentials);
        Map operInfo = null;
        try {
            operInfo = fdOperService.getUserInfo(operCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String operState = (String) operInfo.get("OPERSTATE");
        if (OperStateEnum.OPER_SIGN_OFF.state.equals(operState)) {
            throw new UnsupportedOperationException("oper has sign off");
        }
        if (StringUtils.isEmpty(operCode) || StringUtils.isEmpty(password)) {
            throw new UnknownAccountException("用户名或者密码为空");
        }
        if (!MapUtils.isEmpty(operInfo)) {
            String operPassword = (String) operInfo.get("password");
            if (!password.equals(operPassword)) {
                throw new UnknownAccountException("operator password is failed");
            }
        }
        return new SimpleAuthenticationInfo(operCode, credentials, getName());
    }

    @Override
    protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    protected void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    @Override
    public void setCachingEnabled(boolean cachingEnabled) {
        super.setCachingEnabled(cachingEnabled);
    }

    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    protected void doClearCache(PrincipalCollection principals) {
        super.doClearCache(principals);
    }
}
