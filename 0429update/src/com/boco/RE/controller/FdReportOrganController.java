package com.boco.RE.controller;

import com.boco.RE.service.FdReportOrganService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdReportOrgan;
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
 * 机构区域
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/fdReportOrgan/")
public class FdReportOrganController extends BaseController {
    @Autowired
    private FdReportOrganService fdReportOrganService;

    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "交易名称", funCode = "RE", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/RE/reportParamManage/organRegion/list";

    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "交易名称", funCode = "RE", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/RE/reportParamManage/organRegion/add";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "RE", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(FdReportOrgan fdReportOrgan) throws Exception {
        setAttribute("FdReportOrgan", fdReportOrganService.selectByPK(fdReportOrgan.getOrgancode()));
        return basePath + "/RE/reportParamManage/organRegion/edit";
    }

    /**
     * TODO 查询fd_report_organ分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "RE", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(FdReportOrgan fdReportOrgan) throws Exception {
        List<FdReportOrgan> list = fdReportOrganService.selectByAttr(fdReportOrgan);
        return pageData(list);
    }

    /**
     * TODO 新增fd_report_organ.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "交易名称", funCode = "RE", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(FdReportOrgan fdReportOrgan, HttpSession session) throws Exception {

        List<FdReportOrgan> fdReportOrgans = fdReportOrganService.selectByAttr(fdReportOrgan);
        if (fdReportOrgans != null && fdReportOrgans.size() > 0) {
            return this.json.returnMsg("false", "新增失败：请勿新增重复机构信息!").toJson();
        }

        fdReportOrganService.insertEntity(fdReportOrgan);
        return this.json.returnMsg("true", "新增成功!").toJson();
    }

    /**
     * TODO 修改fd_report_organ.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "交易名称", funCode = "RE", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(FdReportOrgan fdReportOrgan, HttpSession session) throws Exception {
        fdReportOrganService.updateByPK(fdReportOrgan);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }

    /**
     * TODO 删除fd_report_organ
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "交易名称", funCode = "RE", funName = "删除", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(FdReportOrgan fdReportOrgan, HttpSession session) throws Exception {
        fdReportOrganService.deleteByPK(fdReportOrgan.getOrgancode());
        return this.json.returnMsg("true", "删除成功!").toJson();
    }
}