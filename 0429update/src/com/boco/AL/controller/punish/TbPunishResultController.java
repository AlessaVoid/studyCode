package com.boco.AL.controller.punish;

import com.boco.AL.service.ITbPunishListService;
import com.boco.AL.service.ITbPunishResultService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbPunishList;
import com.boco.SYS.entity.TbPunishResult;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Action控制层
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbTradeManger/tbPunishResult/")
public class TbPunishResultController extends BaseController {
    @Autowired
    private ITbPunishResultService tbPunishResultService;
    @Autowired
    private ITbPunishListService tbPunishListService;

    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "交易名称", funCode = "AL", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbPunishManage/tbPunishResult/List";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "交易名称", funCode = "AL", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbPunishResult tbPunishResult) throws Exception {
        tbPunishResult = tbPunishResultService.selectByPK(tbPunishResult.getPunishId());
        TbPunishList tbPunishList = tbPunishListService.selectByPK(tbPunishResult.getPunishListId());
        setAttribute("TbPunishResult", tbPunishResult);
        setAttribute("tbPunishList", tbPunishList);
        return basePath + "/AL/tbPunishManage/tbPunishResult/Info";
    }

    @RequestMapping("commitUI")
    @SystemLog(tradeName = "交易名称", funCode = "AL", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbPunishResult tbPunishResult) throws Exception {
        tbPunishResult = tbPunishResultService.selectByPK(tbPunishResult.getPunishId());
        TbPunishList tbPunishList = tbPunishListService.selectByPK(tbPunishResult.getPunishListId());
        setAttribute("TbPunishResult", tbPunishResult);
        setAttribute("tbPunishList", tbPunishList);
        return basePath + "/AL/tbPunishManage/tbPunishResult/Edit";
    }

    /**
     * TODO 查询tb_punish_result分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "AL", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbPunishResult tbPunishResult) throws Exception {
        setPageParam();
        tbPunishResult.setOrganCode(getSessionOrgan().getThiscode());
        List<TbPunishResult> list = tbPunishResultService.selectByAttr(tbPunishResult);
        return pageData(list);
    }


    /**
     * TODO 修改tb_punish_result.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "交易名称", funCode = "AL", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(TbPunishResult tbPunishResult, HttpSession session) throws Exception {
        tbPunishResult.setState(TbPunishResult.STATE_APPROVALING);
        tbPunishResultService.updateByPK(tbPunishResult);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }
}