package com.boco.GF.service.impl;

import com.boco.GF.service.IActHiTaskinstService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.ActHiTaskinst;
import com.boco.SYS.mapper.ActHiTaskinstMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ActHiTaskinstService
 * @Description TODO
 * @Author daice
 * @Date 2019/11/16 ÏÂÎç5:39
 * @Version 2.0
 **/

@Service
public class ActHiTaskinstService extends GenericService<ActHiTaskinst, String> implements IActHiTaskinstService {

    @Autowired
    private ActHiTaskinstMapper actHiTaskinstMapper;


}
