package com.boco.PUB.controller.tbSystemCtrlStatus;

import com.boco.PUB.service.tbSystemCtrlStatus.TbSystemCtrlStatusService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbSystemCtrlStatus;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @param
 * @Description 系统开关管理
 * @Author liujinbo
 * @Date 2020/3/23
 * @Return
 */
@Controller
@RequestMapping(value = "/tbSystemCtrlStatus/")
public class TbSystemCtrlStatusController extends BaseController {
    @Autowired
    private TbSystemCtrlStatusService tbSystemCtrlStatusService;

    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "交易名称", funCode = "SYS-05", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbSystemCtrlStatus/tbSystemCtrlStatusIndex";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "SYS-05", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbSystemCtrlStatus tbSystemCtrlStatus) throws Exception {
        TbSystemCtrlStatus tbSystemCtrlStatus1 = tbSystemCtrlStatusService.selectByPK(tbSystemCtrlStatus.getSystemid());
        setAttribute("tbSystemCtrlStatus", tbSystemCtrlStatus1);
        setAttribute("status", Integer.valueOf(tbSystemCtrlStatus1.getSystemStatus()));
        return basePath + "/PUB/tbSystemCtrlStatus/tbSystemCtrlStatusUpdate";
    }


    /*加载列表页数据*/
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "SYS-05", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbSystemCtrlStatus tbSystemCtrlStatus) throws Exception {
        setPageParam();
        tbSystemCtrlStatus.setSortColumn("systemid");
        List<Map<String,Object>> list = tbSystemCtrlStatusService.selectTbSystemCtrlStatus(tbSystemCtrlStatus);
        return pageData(list);
    }


    /*修改数据*/
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "交易名称", funCode = "SYS-05", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(TbSystemCtrlStatus tbSystemCtrlStatus) throws Exception {
        tbSystemCtrlStatusService.updateByPK(tbSystemCtrlStatus);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }


}