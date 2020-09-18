package com.boco.PUB.controller.tbCalculateThree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.boco.PUB.service.tbCalculateThree.ITbCalculateThreeProportionService;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.utils.IDGeneratorUtils;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.boco.SYS.entity.TbCalculateThreeProportion;

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
@RequestMapping(value = "/tbCalculateThreeProportion/")
public class TbCalculateThreeProportionController extends BaseController {
    @Autowired
    private ITbCalculateThreeProportionService tbCalculateThreeProportionService;
    @Autowired
    LoanCombMapper loanCombMapper;

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI() throws Exception {

        List<Map<String, String>> combAmountNameList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("name", "排序");
        map1.put("code", "order");
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name", "满足比例");
        map2.put("code", "proportion");
        combAmountNameList.add(map1);
        combAmountNameList.add(map2);
        setAttribute("combAmountNameList", combAmountNameList);

        List<Map<String, String>> combList = new ArrayList<>();
        List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
        for (LoanCombDO loanCombDO : loanCombDOS) {
            Map<String, String> combMap = new HashMap<>(2);
            combMap.put("combCode", loanCombDO.getCombCode());
            combMap.put("combName", loanCombDO.getCombName());
            combList.add(combMap);
        }
        setAttribute("combList", combList);

        return basePath + "/PUB/tbCalculateThree/tbCalculateThreeProportion/tbCalculateThreeProportionEdit";
    }


    /**
     * 详情页数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getDetailList")
    @SystemLog(tradeName = "信贷需求", funCode = "PUB", funName = "信贷计划详情数据", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody
    String creditPlanDetailData() throws Exception {
        Map<String, Object> resultMap = new HashMap<>(16);

        List<TbCalculateThreeProportion> list = tbCalculateThreeProportionService.selectByAttr(new TbCalculateThreeProportion());

        resultMap.put("tbReqDetailList", list);

        return JSON.toJSONString(resultMap);
    }


    /**
     * TODO 修改tb_calculate_three_proportion.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "交易名称", funCode = "未填写", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {

        try {
            String commonProportion = request.getParameter("commonProportion");

            List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());
            List<TbCalculateThreeProportion> list = new ArrayList<>(4);
            for (LoanCombDO tempComb : loanCombDOS) {
                TbCalculateThreeProportion tbCalculateThreeProportion = new TbCalculateThreeProportion();
                String combCode = tempComb.getCombCode();
                String order = request.getParameter(combCode + "_order");
                String proportion = request.getParameter(combCode + "_proportion");
                tbCalculateThreeProportion.setId(IDGeneratorUtils.getSequence());
                tbCalculateThreeProportion.setCode(combCode);
                tbCalculateThreeProportion.setName(tempComb.getCombName());
                tbCalculateThreeProportion.setOrderNum(Integer.valueOf(order));
                tbCalculateThreeProportion.setProportion(BigDecimalUtil.getSafeCount(proportion));
                tbCalculateThreeProportion.setCommonProportion(BigDecimalUtil.getSafeCount(commonProportion));
                list.add(tbCalculateThreeProportion);
            }
            tbCalculateThreeProportionService.deleteAll();
            tbCalculateThreeProportionService.insertBatch(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.json.returnMsg("true", "修改成功!").toJson();
    }

}