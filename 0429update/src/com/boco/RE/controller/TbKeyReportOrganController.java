package com.boco.RE.controller;

import com.boco.PM.service.IFdOrganService;
import com.boco.RE.service.TbKeyReportOrganService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.TbKeyReportOrgan;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.FdOperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 城市重点行
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbKeyReportOrgan/")
public class TbKeyReportOrganController extends BaseController {
    @Autowired
    private TbKeyReportOrganService tbKeyReportOrganService;
    @Autowired
    private IFdOrganService fdOrganService;

    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "交易名称", funCode = "RE", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/RE/reportParamManage/keyCity/list";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "交易名称", funCode = "RE", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/RE/reportParamManage/keyCity/add";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "RE", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbKeyReportOrgan tbKeyReportOrgan) throws Exception {
        setAttribute("TbKeyReportOrgan", tbKeyReportOrganService.selectByPK(tbKeyReportOrgan.getOrgancode()));
        return basePath + "/RE/reportParamManage/keyCity/edit";
    }

    /**
     * TODO 查询tb_key_report_organ分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "RE", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbKeyReportOrgan tbKeyReportOrgan) throws Exception {
        List<TbKeyReportOrgan> list = tbKeyReportOrganService.selectByAttr(tbKeyReportOrgan);
        return pageData(list);
    }

    /**
     * TODO 新增tb_key_report_organ.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "交易名称", funCode = "RE", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(TbKeyReportOrgan tbKeyReportOrgan, HttpSession session) throws Exception {

        if ("11005293".equals(tbKeyReportOrgan.getOrgancode())) {
            return this.json.returnMsg("false", "新增失败：请勿添加总行!").toJson();
        }
        List<TbKeyReportOrgan> fdReportOrgans = tbKeyReportOrganService.selectByAttr(tbKeyReportOrgan);
        if (fdReportOrgans != null && fdReportOrgans.size() > 0) {
            return this.json.returnMsg("false", "新增失败：请勿新增重复机构信息!").toJson();
        }

        FdOrgan fdOrgan = fdOrganService.selectByPK(tbKeyReportOrgan.getOrgancode());
        if (fdOrgan != null) {
            if (Integer.parseInt(fdOrgan.getOrganlevel()) > 2) {
                return this.json.returnMsg("false", "新增失败：请选择城市行!").toJson();
            }
            tbKeyReportOrgan.setOrganlevel(fdOrgan.getOrganlevel());
            tbKeyReportOrgan.setUporgan(fdOrgan.getUporgan());
            FdOrgan fdOrganTemp = fdOrganService.selectByPK(tbKeyReportOrgan.getUporgan());
            tbKeyReportOrgan.setUporganname(fdOrganTemp.getOrganname());
        }

        tbKeyReportOrganService.insertEntity(tbKeyReportOrgan);
        return this.json.returnMsg("true", "新增成功!").toJson();
    }

    /**
     * TODO 修改tb_key_report_organ.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "交易名称", funCode = "RE", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(TbKeyReportOrgan tbKeyReportOrgan, HttpSession session) throws Exception {
        tbKeyReportOrganService.updateByPK(tbKeyReportOrgan);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }

    /**
     * TODO 删除tb_key_report_organ
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "交易名称", funCode = "RE", funName = "删除", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbKeyReportOrgan tbKeyReportOrgan, HttpSession session) throws Exception {
        tbKeyReportOrganService.deleteByPK(tbKeyReportOrgan.getOrgancode());
        return this.json.returnMsg("true", "删除成功!").toJson();
    }
}