package com.boco.AL.service.impl;

import com.boco.AL.service.IFdOperServer;
import com.boco.PM.service.IFdOperService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebRoleInfo;
import com.boco.SYS.mapper.WebOperRoleMapper;
import com.boco.SYS.mapper.WebRoleInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName FdOperServer
 * @Description TODO
 * @Author daice
 * @Date 2019/11/14 ÏÂÎç6:59
 * @Version 2.0
 **/
@Service
public class FdOperServer implements IFdOperServer {

    @Autowired
    WebOperRoleMapper webOperRoleMapper;
    @Autowired
    WebRoleInfoMapper webRoleInfoMapper;
    @Autowired
    IFdOperService fdOperService;


    @Override
    public List<FdOper> getOperByRolecode(String organcode, String rolecode, String opercode) {

        Map<String, String> map = new HashMap<>();
        map.put("organcode", organcode);
        map.put("rolecode", rolecode);
        map.put("opercode", opercode);
        List<FdOper> list = webOperRoleMapper.getOperByRolecode(map);

        WebRoleInfo webRoleInfo = webRoleInfoMapper.selectByPK(rolecode);
        for (FdOper temp : list) {
            temp.setOperpassword(webRoleInfo.getRoleName());
        }

        return list;
    }


}
