package com.boco.SYS.controller;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.TbWarn;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.mapper.LoanCombMapper;
import com.boco.SYS.service.ITbWarnService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Action控制层
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-09      txn      批量新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbWarn/")
public class TbWarnController extends BaseController {
    @Autowired
    private ITbWarnService tbWarnService;
    @Autowired
    private LoanCombMapper loanCombMapper;

    //访问列表、详细信息、新增、修改页面
    @RequestMapping("listUI")
    @SystemLog(tradeName = "预警线管理", funCode = "SYS-02", funName = "加载列表页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PM/warnManger/tbWarnList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "预警线管理", funCode = "SYS-02-04", funName = "加载详细页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbWarn tbWarn) throws Exception {
        setAttribute("TbWarn", tbWarnService.selectByPK(tbWarn.getWarnId()));
        return basePath + "/PM/warnManger/tbWarnInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "预警线管理", funCode = "SYS-02-01", funName = "加载新增页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/PM/warnManger/tbWarnAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "预警线管理", funCode = "SYS-02-02", funName = "加载修改页面", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbWarn tbWarn) throws Exception {
        setAttribute("TbWarn", tbWarnService.selectByPK(tbWarn.getWarnId()));
        return basePath + "/PM/warnManger/tbWarnEdit";
    }

    /**
     * TODO 查询tb_warn分页数据
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "预警线管理", funCode = "SYS-02", funName = "加载列表数据", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbWarn tbWarn, HttpSession session, HttpServletRequest request) throws Exception {
        setPageParam();
        String warnId = null;
        if (request.getParameter("warnId1") != null) {
            try {
                if (request.getParameter("warnId1").replace("'", "").matches("\\d+")) {
                    warnId = getParameter("warnId1").replace("'", "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (warnId != null) {
                tbWarn.setWarnId(Integer.valueOf(warnId));
            }
//            else {
//                tbWarn.setWarnId(999999999);
//            }
        }
        if (request.getParameter("warnName") != null) {
            String warnName = getParameter("warnName").replace("'", "");
            if (warnName != null) {
                tbWarn.setWarnName(warnName);
            }
        }
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbWarn.setWarnOrgan(Integer.parseInt(fdOper.getOrgancode()));
//        List<TbWarn> list = tbWarnService.selectByAttr(tbWarn);
        List<TbWarn> list = null;
        try {
            list = tbWarnService.selectByLikeAttr(tbWarn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageData(list);
    }

    /**
     * TODO 新增tb_warn.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "预警线管理", funCode = "SYS-02", funName = "新增", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(TbWarn tbWarn, HttpSession session) throws Exception {

        //最后操作员
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        tbWarn.setWarnOrgan(Integer.parseInt(fdOper.getOrgancode()));
        tbWarn.setWarnCreateOper(fdOper.getOpercode());
        tbWarn.setWarnUpdateOper(fdOper.getOpercode());
        tbWarn.setWarnCreateTime(BocoUtils.getTime());
        tbWarn.setWarnUpdateTime(BocoUtils.getTime());

        TbWarn tbWarn1 = new TbWarn();
        tbWarn1.setWarnOrgan(Integer.parseInt(fdOper.getOrgancode()));
        tbWarn1.setWarnType(tbWarn.getWarnType());
        String warnCombStr = tbWarn.getWarnComb();
        String[] warnCombArr = warnCombStr.split(",");
        //单条新增，预警名称有效
        if (warnCombArr != null && warnCombArr.length == 1) {
            tbWarn1.setWarnComb(warnCombArr[0]);
            List<TbWarn> tbWarns = tbWarnService.selectByAttr(tbWarn1);
            if (tbWarns.size() == 0) {
                int count = tbWarnService.insertEntity(tbWarn);
                if (count == 1) {
                    //"新增成功!"
                    return this.json.returnMsg("true", getErrorInfo("w456")).toJson();
                }
                //新增失败
                return this.json.returnMsg("false", getErrorInfo("w446")).toJson();
            } else {
                return this.json.returnMsg("false", "新增失败，该机构下该预警贷种已有相同预警类型").toJson();
            }

        } else if (warnCombArr != null && warnCombArr.length > 1) {
            //批量新增
            List<LoanCombDO> loanCombDOS = loanCombMapper.getAllLoanCombInfoList(new LoanCombDO());

            for (String combCode : warnCombArr) {
                tbWarn1.setWarnComb(combCode);
                List<TbWarn> tbWarns = tbWarnService.selectByAttr(tbWarn1);
                if (tbWarns.size() > 0) {
                    return this.json.returnMsg("false", "新增失败，该机构下该预警贷种已有相同预警类型").toJson();
                }
            }
            List<TbWarn> insertList = new ArrayList<>();
            Map<String, TbWarn> tempMap = new HashMap<>();
            for (String combCode : warnCombArr) {
                for (LoanCombDO tempComb : loanCombDOS) {
                    if (combCode.equals(tempComb.getCombCode())) {
                        tbWarn.setWarnName(tempComb.getCombName());
                    }
                }
                tbWarn.setWarnComb(combCode);
                TbWarn tempTb = copyTbWarn(tbWarn);
                tempMap.put(combCode, tempTb);
            }
            for (String combCode : warnCombArr) {
                insertList.add(tempMap.get(combCode));
            }
            try {
                tbWarnService.insertBatch(insertList);
                return this.json.returnMsg("true", getErrorInfo("w456")).toJson();
            } catch (Exception e) {
                //新增失败
                logger.info(e.toString());
                return this.json.returnMsg("false", getErrorInfo("w446")).toJson();
            }
        }
        return this.json.returnMsg("false", "新增失败，该机构下该预警贷种已有相同预警类型").toJson();
    }

    /**
     * copy class
     *
     * @param tbWarn
     * @return
     */
    private TbWarn copyTbWarn(TbWarn tbWarn) {
        TbWarn tempTbWarn = new TbWarn();
        tempTbWarn.setWarnName(tbWarn.getWarnName());
        tempTbWarn.setWarnComb(tbWarn.getWarnComb());
        tempTbWarn.setWarnOrgan(tbWarn.getWarnOrgan());
        tempTbWarn.setWarnType(tbWarn.getWarnType());
        tempTbWarn.setWarnUpdateOper(tbWarn.getWarnUpdateOper());
        tempTbWarn.setWarnUpdateTime(tbWarn.getWarnUpdateTime());
        tempTbWarn.setWarnCreateOper(tbWarn.getWarnCreateOper());
        tempTbWarn.setWarnCreateTime(tbWarn.getWarnCreateTime());
        tempTbWarn.setWarnOneLine(tbWarn.getWarnOneLine());
        tempTbWarn.setWarnTwoLine(tbWarn.getWarnTwoLine());
        tempTbWarn.setWarnThreeLine(tbWarn.getWarnThreeLine());
        tempTbWarn.setWarnFourLine(tbWarn.getWarnFourLine());
        tempTbWarn.setWarnFiveLine(tbWarn.getWarnFiveLine());
        return tempTbWarn;
    }

    /**
     * TODO 修改tb_warn.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015年11月18日    	    杨滔    新建
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "预警线管理", funCode = "SYS-02", funName = "修改", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(TbWarn tbWarn, HttpSession session) throws Exception {

        //最后操作员
        FdOper fdOper = (FdOper) session.getAttribute("sessionUser");
        Json json = new Json();
        tbWarn.setWarnUpdateOper(fdOper.getOpercode());
        tbWarn.setWarnUpdateTime(BocoUtils.getTime());

        TbWarn tbWarn1 = new TbWarn();
        tbWarn1.setWarnOrgan(Integer.parseInt(fdOper.getOrgancode()));
        tbWarn1.setWarnComb(tbWarn.getWarnComb());
        tbWarn1.setWarnType(tbWarn.getWarnType());
        tbWarn1.setWarnId(tbWarn.getWarnId());

        List<TbWarn> tbWarns = tbWarnService.selectDistinctTbwarn(tbWarn1);

        if (tbWarns.size() == 0) {
            int count = tbWarnService.updateByPK(tbWarn);
            if (count == 1) {
                //"修改成功!"
                return this.json.returnMsg("true", getErrorInfo("w448")).toJson();
            }
            //修改失败
            return this.json.returnMsg("false", getErrorInfo("w447")).toJson();
        }
        return this.json.returnMsg("false", "修改失败，该机构下该预警贷种已有相同预警类型").toJson();
    }

    /**
     * TODO 删除tb_warn
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2014-10-29      杨滔      批量新建
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "预警线管理", funCode = "SYS-02-03", funName = "删除", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbWarn tbWarn, HttpSession session) throws Exception {

        int count = tbWarnService.deleteByPK(tbWarn.getWarnId());
        if (count == 1) {
            //"删除成功!"
            return this.json.returnMsg("true", "删除成功!").toJson();
        }
        return this.json.returnMsg("false", "删除失败!").toJson();

    }


    /**
     * 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-09-19     txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectWarnId")
    @SystemLog(tradeName = "预警线管理", funCode = "SYS-02", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectWarnId(TbWarn tbWarn, HttpServletRequest request) throws Exception {
        String warnId = null;
        if (request.getParameter("warnId1").replace("'", "").matches("\\d+")) {
            warnId = getParameter("warnId1").replace("'", "");
        }
        if (warnId != null) {
            tbWarn.setWarnId(Integer.valueOf(warnId));
        } else {
            tbWarn.setWarnId(999999999);
        }
        FdOper fdOper = (FdOper) request.getSession().getAttribute("sessionUser");
        tbWarn.setWarnOrgan(Integer.parseInt(fdOper.getOrgancode()));
        Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Set<String> set = new TreeSet<String>();
        List<Map<String, Integer>> tbWarnList = tbWarnService.selectWarnId(tbWarn);
        for (Map<String, Integer> deptInfo : tbWarnList) {
            String data = String.valueOf(deptInfo.get("warn_id"));
            set.add(data);
        }
        for (String data : set) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        String json = JsonUtils.toJson(resultMap);
        return json;
    }

    /**
     * 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-09-19     txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectWarnName")
    @SystemLog(tradeName = "预警线管理", funCode = "SYS-02", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectWarnName(TbWarn tbWarn, HttpServletRequest request) throws Exception {
        String warnName = getParameter("warnName").replace("'", "");
        if (warnName != null) {
            tbWarn.setWarnName(warnName);
        }
        FdOper fdOper = (FdOper) request.getSession().getAttribute("sessionUser");
        tbWarn.setWarnOrgan(Integer.parseInt(fdOper.getOrgancode()));
        Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Set<String> set = new TreeSet<String>();
        List<Map<String, String>> tbWarnList = tbWarnService.selectWarnNameByOrgan(tbWarn);
        for (Map<String, String> deptInfo : tbWarnList) {
            String data = deptInfo.get("warn_name");
            set.add(data);
        }
        for (String data : set) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        String json = JsonUtils.toJson(resultMap);
        return json;
    }


    /**
     * 联想输入框
     *
     * @return <pre>
     * 修改日期            修改人      修改原因
     * 2019-09-19     txn      批量新建
     * </pre>
     */
    @RequestMapping(value = "selectWarnOrgan")
    @SystemLog(tradeName = "预警线管理", funCode = "SYS-02", funName = "联想输入", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String selectWarnOrgan(TbWarn tbWarn, HttpServletRequest request) throws Exception {
        String warnOrgan = request.getParameter("warnOrgan").replace("'", "");
        if (warnOrgan != null) {
            tbWarn.setWarnOrgan(Integer.valueOf(warnOrgan));
        }
        Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Set<String> set = new TreeSet<String>();
        List<Map<String, String>> tbWarnList = tbWarnService.selectWarnOrgan(tbWarn);
        for (Map<String, String> deptInfo : tbWarnList) {
            String data = deptInfo.get("warn_organ");
            set.add(data);
        }
        for (String data : set) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("key", data);
            map.put("value", data);
            list.add(map);
        }
        resultMap.put("list", list);
        String json = JsonUtils.toJson(resultMap);
        return json;
    }
}