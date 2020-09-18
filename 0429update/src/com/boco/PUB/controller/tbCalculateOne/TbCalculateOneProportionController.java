package com.boco.PUB.controller.tbCalculateOne;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.boco.PUB.service.tbCalculateOne.ITbCalculateOneProportionService;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.utils.IDGeneratorUtils;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.boco.SYS.entity.TbCalculateOneProportion;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.base.BaseController;

/**
 * Action控制层
 * c测算模式一 参数配置
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbCalculateOneProportion/")
public class TbCalculateOneProportionController extends BaseController {
    @Autowired
    private ITbCalculateOneProportionService tbCalculateOneProportionService;


    @RequestMapping("updateUI")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-31-01", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI() throws Exception {
        List<TbCalculateOneProportion> list = tbCalculateOneProportionService.selectByAttr(new TbCalculateOneProportion());
        BigDecimal ratio_1 = BigDecimal.ZERO;
        BigDecimal ratio_2 = BigDecimal.ZERO;
        BigDecimal ratio_4 = BigDecimal.ZERO;
        BigDecimal ratio_8 = BigDecimal.ZERO;
        for (int i = 0; i < list.size(); i++) {
            setAttribute("TbCalculateOneProportion_" + i, list.get(i));
            if ("1".equals(list.get(i).getClassType().toString())) {
                ratio_1 = list.get(i).getRatio();
            } else if ("2".equals(list.get(i).getClassType().toString())) {
                ratio_2 = list.get(i).getRatio();
            } else if ("4".equals(list.get(i).getClassType().toString())) {
                ratio_4 = list.get(i).getRatio();
            } else if ("8".equals(list.get(i).getClassType().toString())) {
                ratio_8 = list.get(i).getRatio();
            }
        }
        setAttribute("ratio_1", ratio_1);
        setAttribute("ratio_2", ratio_2);
        setAttribute("ratio_4", ratio_4);
        setAttribute("ratio_8", ratio_8);


        return basePath + "/PUB/tbCalculateOne/tbCalculateOneProportion/tbCalculateOneProportionEdit";
    }


    /**
     * TODO 修改tb_calculate_one_proportion.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "交易名称", funCode = "PUB-31-01", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {
        try {
            BigDecimal ratio_1 = new BigDecimal(request.getParameter("ratio_1"));
            BigDecimal ratio_2 = new BigDecimal(request.getParameter("ratio_2"));
            BigDecimal ratio_4 = new BigDecimal(request.getParameter("ratio_4"));
            BigDecimal ratio_8 = new BigDecimal(request.getParameter("ratio_8"));

            List<TbCalculateOneProportion> list = new ArrayList<>(28);
            for (int i = 0; i < 28; i++) {
                TbCalculateOneProportion tbCalculateOneProportion = new TbCalculateOneProportion();
                BigDecimal classType = new BigDecimal(request.getParameter("classType_" + i));
                String code = request.getParameter("code_" + i);
                String name = request.getParameter("name_" + i);
                BigDecimal indexType = new BigDecimal(request.getParameter("indexType_" + i));
                BigDecimal sortType = new BigDecimal(request.getParameter("sortType_" + i));
                BigDecimal weight = new BigDecimal(request.getParameter("weight_" + i));
                tbCalculateOneProportion.setId(IDGeneratorUtils.getSequence());
                tbCalculateOneProportion.setClassType(classType);
                tbCalculateOneProportion.setCode(code);
                tbCalculateOneProportion.setName(name);
                tbCalculateOneProportion.setIndexType(indexType);
                tbCalculateOneProportion.setSortType(sortType);
                tbCalculateOneProportion.setWeight(weight);
                if ("1".equals(classType.toString())) {
                    tbCalculateOneProportion.setRatio(ratio_1);
                } else if ("2".equals(classType.toString())) {
                    tbCalculateOneProportion.setRatio(ratio_2);
                } else if ("4".equals(classType.toString())) {
                    tbCalculateOneProportion.setRatio(ratio_4);
                } else if ("8".equals(classType.toString())) {
                    tbCalculateOneProportion.setRatio(ratio_8);
                }
                tbCalculateOneProportion.setCreateTime(BocoUtils.getTime());
                tbCalculateOneProportion.setUpdateTime(BocoUtils.getTime());
                tbCalculateOneProportion.setCreateOper(getSessionOperInfo().getOperCode());
                tbCalculateOneProportion.setUpdateOper(getSessionOperInfo().getOperCode());
                list.add(tbCalculateOneProportion);
            }
            int num1 = 0;
            int num2 = 0;
            int num3 = 0;
            int num4 = 0;
            for (TbCalculateOneProportion tbCalculateOneProportion : list) {
                int classType = tbCalculateOneProportion.getClassType().intValue();
                int indexType = tbCalculateOneProportion.getIndexType().intValue();
                if (classType == 1 && indexType == 1) {
                    num1 += 1;
                } else if (classType == 2 && indexType == 1) {
                    num2 += 1;
                } else if (classType == 4 && indexType == 1) {
                    num3 += 1;
                } else if (classType == 8 && indexType == 1) {
                    num4 += 1;
                }
            }

            if (num1 < 1) {
                return this.json.returnMsg("false", "请选择存贷联动类主指标!").toJson();
            } else if (num2 < 1) {
                return this.json.returnMsg("false", "请选择结构优化类主指标!").toJson();
            } else if (num3 < 1) {
                return this.json.returnMsg("false", "请选择市场竞争类主指标!").toJson();
            } else if (num4 < 1) {
                return this.json.returnMsg("false", "请选择价值提升类主指标!").toJson();
            }

            if (num1 > 1) {
                return this.json.returnMsg("false", "存贷联动类主指标唯一不可重复!").toJson();
            } else if (num2 > 1) {
                return this.json.returnMsg("false", "结构优化类主指标唯一不可重复!").toJson();
            } else if (num3 > 1) {
                return this.json.returnMsg("false", "市场竞争类主指标唯一不可重复!").toJson();
            } else if (num4 > 1) {
                return this.json.returnMsg("false", "价值提升类主指标唯一不可重复!").toJson();
            }


            tbCalculateOneProportionService.deleteAll();
            tbCalculateOneProportionService.insertBatch(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.json.returnMsg("true", "修改成功!").toJson();
    }

}