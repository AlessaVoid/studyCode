package com.boco.PUB.controller.tbCalculateOne;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.boco.PUB.service.tbCalculateOne.ITbCalculateOneRankService;
import com.boco.SYS.util.BocoUtils;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import com.boco.SYS.entity.TbCalculateOneRank;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.base.BaseController;

/**
 * Action控制层
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbCalculateOneRank/")
public class TbCalculateOneRankController extends BaseController {
    @Autowired
    private ITbCalculateOneRankService tbCalculateOneRankService;

    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-31-02", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbCalculateOne/tbCalculateOneRank/tbCalculateOneRankList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-31-02", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbCalculateOneRank tbCalculateOneRank) throws Exception {
        setAttribute("TbCalculateOneRank", tbCalculateOneRankService.selectByPK(tbCalculateOneRank.getId()));
        return basePath + "/PUB/tbCalculateOne/tbCalculateOneRank/tbCalculateOneRankInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-31-02", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/PUB/tbCalculateOne/tbCalculateOneRank/tbCalculateOneRankAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-31-02", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbCalculateOneRank tbCalculateOneRank) throws Exception {
        setAttribute("TbCalculateOneRank", tbCalculateOneRankService.selectByPK(tbCalculateOneRank.getId()));
        return basePath + "/PUB/tbCalculateOne/tbCalculateOneRank/tbCalculateOneRankEdit";
    }

    /**
     * TODO 查询tb_calculate_one_rank分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "交易名称", funCode = "PUB-31-02", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbCalculateOneRank tbCalculateOneRank) throws Exception {
        setPageParam();
        List<TbCalculateOneRank> list = new ArrayList<>();
        try {
            list = tbCalculateOneRankService.selectByAttr(tbCalculateOneRank);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return pageData(list);
    }

    /**
     * TODO 新增tb_calculate_one_rank.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-31-02", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(TbCalculateOneRank tbCalculateOneRank, HttpSession session) throws Exception {
        tbCalculateOneRank.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        tbCalculateOneRank.setCreateTime(BocoUtils.getTime());
        tbCalculateOneRank.setUpdateTime(BocoUtils.getTime());
        tbCalculateOneRank.setCreateOper(getSessionOperInfo().getOperCode());
        tbCalculateOneRank.setUpdateOper(getSessionOperInfo().getOperCode());

        int status = tbCalculateOneRank.getStatus();
        if (status == 1) {
            TbCalculateOneRank tb = new TbCalculateOneRank();
            tb.setStatus(status);
            tb.setType(tbCalculateOneRank.getType());
            List<TbCalculateOneRank> list = tbCalculateOneRankService.selectByAttr(tb);
            for (TbCalculateOneRank tempTb : list) {
                tempTb.setStatus(2);
                tbCalculateOneRankService.updateByPK(tempTb);
            }
        }
        tbCalculateOneRankService.insertEntity(tbCalculateOneRank);
        return this.json.returnMsg("true", "新增成功!").toJson();
    }

    /**
     * TODO 修改tb_calculate_one_rank.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-31-02", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(TbCalculateOneRank tbCalculateOneRank, HttpSession session) throws Exception {
        tbCalculateOneRank.setUpdateTime(BocoUtils.getTime());
        tbCalculateOneRank.setUpdateOper(getSessionOperInfo().getOperCode());
        int status = tbCalculateOneRank.getStatus();
        if (status == 1) {
            TbCalculateOneRank tb = new TbCalculateOneRank();
            tb.setStatus(status);
            tb.setType(tbCalculateOneRank.getType());
            List<TbCalculateOneRank> list = tbCalculateOneRankService.selectByAttr(tb);
            for (TbCalculateOneRank tempTb : list) {
                tempTb.setStatus(2);
                tbCalculateOneRankService.updateByPK(tempTb);
            }
        }

        tbCalculateOneRankService.updateByPK(tbCalculateOneRank);
        return this.json.returnMsg("true", "修改成功!").toJson();
    }

    /**
     * TODO 删除tb_calculate_one_rank
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-31-02", funName = "删除", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbCalculateOneRank tbCalculateOneRank, HttpSession session) throws Exception {
        tbCalculateOneRankService.deleteByPK(tbCalculateOneRank.getId());
        return this.json.returnMsg("true", "删除成功!").toJson();
    }
}