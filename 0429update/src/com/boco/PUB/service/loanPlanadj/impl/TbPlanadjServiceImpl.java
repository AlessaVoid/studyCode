package com.boco.PUB.service.loanPlanadj.impl;

import com.boco.GF.service.IWebTaskRoleInfoNewService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PM.service.IWebMsgService;
import com.boco.PUB.service.ITbReqresetDetailService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjDetailService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjService;
import com.boco.PUB.service.tbQuotaAllocate.TbQuotaAllocateService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.*;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.*;
import com.boco.TONY.biz.loancomb.POJO.DO.combbase.LoanCombDO;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import com.boco.TONY.biz.loanplan.service.ILoanPlanService;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.enums.LoanStateEnums;
import com.boco.TONY.utils.IDGeneratorUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.boco.SYS.util.WebContextUtil.getSessionOrgan;

/**
 * 信贷计划调整批次表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbPlanadjServiceImpl extends GenericService<TbPlanadj, String> implements TbPlanadjService {
    //有自定义方法时使用
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");


    public static Logger logger = Logger.getLogger(TbPlanadjServiceImpl.class);

    @Autowired
    private IWorkFlowService workFlowService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private IWebMsgService webMsgService;
    @Autowired
    private TbReqDetailMapper tbReqDetailMapper;
    @Autowired
    private IWebTaskRoleInfoNewService webTaskRoleInfoNewService;
    @Autowired
    private TbPlanadjMapper planadjMapper;
    @Autowired
    private TbPlanadjDetailMapper planadjDetailMapper;
    @Autowired
    private TbPlanMapper tbPlanMapper;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;
    @Autowired
    private ILoanPlanService iLoanPlanService;
    @Autowired
    private ITbTradeParamService tbTradeParamService;
    @Autowired
    private FdOrganMapper fdOrganMapper;
    @Autowired
    private IWebLoanCombService loanCombService;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    private TbPlanadjDetailService planadjDetailService;
    @Autowired
    private TbQuotaAllocateService tbQuotaAllocateService;
    @Autowired
    private ITbReqresetDetailService tbReqresetDetailService;

    /*计划上传地址*/
    @Value("${upload.path}")
    private String uploadPath;


    @Override
    public Map<String, Object> selectTbplanadjByMonth(String sort, String month, int pageNo, int pageSize) {
        Map<String, Object> results = new Hashtable<>();
        try {
            HashMap<String, Object> queryMap = new HashMap<>();
            queryMap.put("planadjStatus", 0);
            queryMap.put("planadjMonth", month);
            queryMap.put("planadjType", TbPlan.PLAN);
            queryMap.put("planadjOrgan", getSessionOrgan().getThiscode());
            queryMap.put("sort", sort);

            PageHelper.startPage(pageNo, pageSize, true, true, true);
            List<Map<String, Object>> resultList = planadjMapper.selectPlanadj(queryMap);

            //万元转亿元
            for (Map<String, Object> map : resultList) {
                map.put("planadjnetincrement", new BigDecimal(map.get("planadjnetincrement").toString()).divide(new BigDecimal("10000")));
                map.put("planadjadjamount", new BigDecimal(map.get("planadjadjamount").toString()).divide(new BigDecimal("10000")));
                map.put("planadjrealincrement", new BigDecimal(map.get("planadjrealincrement").toString()).divide(new BigDecimal("10000")));
            }

            //返回页面的分页数据
            if (CollectionUtils.isEmpty(resultList)) {
                results.put("pager.pageNo", 1);
                results.put("pager.totalRows", 0);
                results.put("rows", new ArrayList<>());
            }
            PageInfo<Map<String, Object>> mapPageInfo = new PageInfo<>(resultList);
            results.put("pager.pageNo", mapPageInfo.getPageNum());
            results.put("pager.totalRows", mapPageInfo.getTotal());

            results.put("rows", resultList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }


    @Override
    public PlainResult<String> updatePlanadj(HttpServletRequest request, String operCode, String organcode, String uporgan) throws Exception {
        PlainResult<String> result = new PlainResult<>();
        try {
            String planadjId = request.getParameter("planadjId");
            //获取机构号
            // List<Map<String, Object>> organList = fdOrganMapper.selectByUporgan(getSessionOrgan().getThiscode());
            List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

            //获取登录机构等级
            String organlevel = getSessionOrgan().getOrganlevel();

            //获取贷种组合
            TbPlanadj planadj = planadjMapper.selectByPK(planadjId);
            int combLevel = planadj.getCombLevel();
            List<Map<String, Object>> combList = this.getCombList(combLevel);

            String planMonth = request.getParameter("planMonth");
            int planUnit = Integer.parseInt(request.getParameter("planUnit"));
            String increment = request.getParameter("increment");
            String s = request.getParameter("planadjUnifiedType");
            int planadjUnifiedType = Integer.parseInt(request.getParameter("planadjUnifiedType"));

            //生成信贷计划调整批次
            TbPlanadj tbPlanadj = new TbPlanadj();
            tbPlanadj.setPlanadjMonth(planMonth);
            tbPlanadj.setPlanadjUnit(planUnit);
            tbPlanadj.setPlanadjId(planadjId);
            tbPlanadj.setPlanadjUnifiedType(planadjUnifiedType);
            BigDecimal planIncrement = new BigDecimal(StringUtils.isEmpty(increment) ? "0" : increment);
            planIncrement = planIncrement.multiply(new BigDecimal("10000"));
            tbPlanadj.setPlanadjNetIncrement(planIncrement);
            tbPlanadj.setPlanadjUpdateTime(BocoUtils.getTime());

            ArrayList<TbPlanadjDetail> tbPlanadjDetailList = new ArrayList<>();
            BigDecimal adjAmountCount = new BigDecimal("0");
            BigDecimal amountFinalCount = new BigDecimal("0");
            //生成信贷计划调整明细
            for (Map<String, Object> map : organList) {
                String organcode1 = (String) map.get("organcode");
                for (Map<String, Object> comb : combList) {
                    String combcode = (String) comb.get("combcode");
                    String planadjDetailId = request.getParameter(organcode1 + "_" + combcode + "_id");
                    TbPlanadjDetail tbPlanadjDetail = new TbPlanadjDetail();
                    tbPlanadjDetail.setPlanadjdId(planadjDetailId);
                    tbPlanadjDetail.setPlanadjdRefId(planadjId);
                    tbPlanadjDetail.setPlanadjdOrgan(organcode1);
                    tbPlanadjDetail.setPlanadjdLoanType(combcode);
                    BigDecimal initAmount = new BigDecimal(request.getParameter(organcode1 + "_" + combcode + "_init"));
                    BigDecimal adjAmount = new BigDecimal(request.getParameter(organcode1 + "_" + combcode + "_adj"));
                    BigDecimal finalAmount = new BigDecimal(request.getParameter(organcode1 + "_" + combcode + "_final"));
                    BigDecimal reqAmount = new BigDecimal(request.getParameter(organcode1 + "_" + combcode + "_req")); //增加需求金额
                    //亿元转万元
                    if (planUnit == 2) {
                        initAmount = initAmount.multiply(new BigDecimal("10000"));
                        adjAmount = adjAmount.multiply(new BigDecimal("10000"));
                        finalAmount = finalAmount.multiply(new BigDecimal("10000"));
                        reqAmount = reqAmount.multiply(new BigDecimal("10000"));
                    }
                    tbPlanadjDetail.setPlanadjdInitAmount(initAmount);
                    tbPlanadjDetail.setPlanadjdAdjAmount(adjAmount);
                    tbPlanadjDetail.setPlanadjdAdjedAmount(finalAmount);
                    tbPlanadjDetail.setPlanadjdReqAmount(reqAmount);
                    tbPlanadjDetail.setPlanadjdUnit(planUnit);
                    tbPlanadjDetail.setPlanadjdMonth(planMonth);
                    tbPlanadjDetail.setPlanadjdUpdateTime(BocoUtils.getTime());
                    tbPlanadjDetail.setPlanadjdCreateTime(BocoUtils.getTime());

                    //计算总的调整量
                    adjAmountCount = adjAmountCount.add(adjAmount);
                    //计算本月调整净增量
                    amountFinalCount = amountFinalCount.add(finalAmount);
                    tbPlanadjDetailList.add(tbPlanadjDetail);
                }
            }

            //一级分行 制定净增量要等于计划净增量--贷种细分计算
            if ("1".equals(organlevel)) {
                // 一分贷种级别
                int combLevelOne = combLevel;
                //总行贷种级别
                TbPlan planParam = new TbPlan();
                planParam.setPlanMonth(planMonth);
                planParam.setPlanOrgan(uporgan);
                planParam.setPlanType(TbPlan.PLAN);
                TbPlan upPlan = tbPlanMapper.selectByAttr(planParam).get(0);
                int combLevelTotal = upPlan.getCombLevel();

                // 获取当前贷种map  相同贷种直接比较，不同贷种转换为一级贷种比较
                Map<String, BigDecimal> realUpcombIncrementMap = null;
                //总行给该机构指定的计划量   相同贷种直接比较，不同贷种转换为一级贷种比较
                Map<String, BigDecimal> upcombIncrementMap = null;

                if (combLevelTotal == 1 && combLevelOne == 1) {
                    // 总行->一级贷种  一分->一级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organcode);

                } else if (combLevelTotal == 1 && combLevelOne == 2) {
                    // 总行->一级贷种  一分->二级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMapAndTransCombLevel(tbPlanadjDetailList, combLevelOne);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organcode);

                } else if (combLevelTotal == 2 && combLevelOne == 1) {
                    // 总行->二级贷种  一分->一级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMapAndTransCombLevel(planMonth, uporgan, organcode, combLevelTotal);

                } else if (combLevelTotal == 2 && combLevelOne == 2) {
                    // 总行->二级贷种  一分->二级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organcode);

                }

                //比较计划和制定的净增量
                for (String upcombCode : upcombIncrementMap.keySet()) {
                    //如果实际制定量不等于计划净增量，插入失败
                    if (getSafeCount(realUpcombIncrementMap.get(upcombCode)).compareTo(getSafeCount(upcombIncrementMap.get(upcombCode))) != 0) {
                        HashMap<String, Object> querymap = new HashMap<>();
                        querymap.put("combcode", upcombCode);
                        List<Map<String, Object>> maps = loanCombService.selectCombBycombcode(querymap);
                        String combname = maps.get(0).get("combname").toString();
                        return result.error(HttpServletResponse.SC_REQUEST_TIMEOUT,
                                combname + "制定净增量为" + realUpcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "亿元，计划净增量为"
                                        + upcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "亿元，请调整");
                    }
                }
            }

            //保存数据
            tbPlanadj.setPlanadjAdjAmount(adjAmountCount);
            tbPlanadj.setPlanadjRealIncrement(amountFinalCount);
            planadjMapper.updateByPK(tbPlanadj);

            //更新所有明细
            planadjMapper.deleteTbPlanadjDetailBYRefId(planadjId);
            planadjDetailMapper.insertBatch(tbPlanadjDetailList);

            return result.success("update", "update loan planadj detail success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update loan planadj detail fail");
    }

    /*计划调整审批完后更新计划*/
    @Override
    public void updatePlanAndPlanadj(List<TbPlanadjDetail> tbPlanadjDetailList, String planadjId) throws Exception {
        TbPlanDetail tbPlanDetail = new TbPlanDetail();
        /*本月计划制定改变的计划净增量*/
        BigDecimal realIncrement = new BigDecimal("0");

        //更新计划详情
        TbPlanadj tbPlanadj = planadjMapper.selectByPK(planadjId);
        TbPlan tbPlanParam = new TbPlan();
        tbPlanParam.setPlanMonth(tbPlanadj.getPlanadjMonth());
        tbPlanParam.setPlanOrgan(tbPlanadj.getPlanadjOrgan());
        tbPlanParam.setPlanType(TbPlan.PLAN);
        TbPlan tbPlan = tbPlanMapper.selectByAttr(tbPlanParam).get(0);

        for (TbPlanadjDetail tbPlanadjDetail : tbPlanadjDetailList) {
            realIncrement = realIncrement.add(tbPlanadjDetail.getPlanadjdAdjAmount());
            tbPlanDetail.setPdLoanType(tbPlanadjDetail.getPlanadjdLoanType());
            tbPlanDetail.setPdOrgan(tbPlanadjDetail.getPlanadjdOrgan());
            tbPlanDetail.setPdRefId(tbPlan.getPlanId());
            List<TbPlanDetail> tbPlanDetails = tbPlanDetailMapper.selectByAttr(tbPlanDetail);

            if (tbPlanDetails == null || tbPlanDetails.size() == 0) {
                //说明有新增贷种，需要新增到计划详情
                TbPlanDetail planDetail = new TbPlanDetail();
                planDetail.setPdId(IDGeneratorUtils.getSequence());
                planDetail.setPdRefId(tbPlan.getPlanId());
                planDetail.setPdMonth(tbPlan.getPlanMonth());
                planDetail.setPdUnit(tbPlan.getPlanUnit());
                planDetail.setPdOrgan(tbPlanadjDetail.getPlanadjdOrgan());
                planDetail.setPdLoanType(tbPlanadjDetail.getPlanadjdLoanType());
                planDetail.setPdAmount(tbPlanadjDetail.getPlanadjdAdjedAmount());
                planDetail.setPdCreateTime(BocoUtils.getTime());
                planDetail.setPdUpdateTime(BocoUtils.getTime());
                tbPlanDetailMapper.insertEntity(planDetail);
            } else {
                if (tbPlanadjDetail.getPlanadjdAdjAmount().compareTo(BigDecimal.ZERO) != 0) {
                    for (TbPlanDetail planDetail : tbPlanDetails) {
                        planDetail.setPdAmount(planDetail.getPdAmount().add(tbPlanadjDetail.getPlanadjdAdjAmount()));
                        planDetail.setPdUpdateTime(BocoUtils.getTime());
                        tbPlanDetailMapper.updateByPK(planDetail);
                    }
                }
            }
        }

        //更新计划
        tbPlan.setPlanRealIncrement(tbPlan.getPlanRealIncrement().add(realIncrement));
        tbPlan.setPlanUpdateTime(BocoUtils.getTime());
        tbPlanMapper.updateByPK(tbPlan);

    }

    /*导出计划调整模板*/
    @Override
    public void downPlanadjTemplate(HttpServletRequest request, String type, HttpServletResponse response, String organlevel) throws Exception {

        OutputStream os = response.getOutputStream();
        Workbook wb = null;
        String fileName = "";

        //1-计划模板  2-条线模板
        if ("1".equals(type)) {

            //查询贷种组合
            String combLevelStr = request.getParameter("combLevel") == null ? "1" : request.getParameter("combLevel");
            int combLevel = Integer.valueOf(combLevelStr);
            Map<String, Object> combMap = new HashMap<String, Object>();
            combMap.put("combLevel", combLevel);
            List<Map<String, Object>> combList = null;
            if (1 == combLevel) {
                combList = loanCombMapper.selectComb(combMap);
            } else if (2 == combLevel) {
                combList = loanCombMapper.selectCombOfBind(combMap);
            }

            fileName = getSessionOrgan().getOrganname() + "制定批量机构调整计划导入表样（录入调整金额）";

            //查询机构
            // List<Map<String, Object>> organList = fdOrganMapper.selectByUporgan(getSessionOrgan().getThiscode());
            List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

            wb = exportExcel(combList, organList, fileName);

        } else if ("2".equals(type)) {

            //获取贷种组合级别 查二级贷种
            int combLevel = 2;
            //查询贷种组合
            Map<String, Object> combMap = new HashMap<String, Object>();
            combMap.put("combLevel", combLevel);
            List<Map<String, Object>> combList = null;
            combList = loanCombService.selectCombOfBind(combMap);

            fileName = getSessionOrgan().getOrganname() + "制定批量条线调整计划导入表样（录入调整金额）";
            //查询机构
            List<Map<String, Object>> organList = fdOrganMapper.selectByOrganCode(getSessionOrgan().getThiscode());
            wb = exportExcel(combList, organList, fileName);

        }

        //文件名
        fileName = fileName + ".xls";
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            //win10 edge ie 和其他系统的ie浏览器
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");

        }
        // 清空response
        response.reset();
        //response.setHeader("Content-Type", "application/msexcel");
        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", fileName));
        response.setContentType("application/octet-stream");

        wb.write(os);
        os.flush();
        os.close();

    }

    @Override
    public Map<String, String> enterReportPlanadjByMonth(MultipartFile file, String operCode, String organCode, HttpServletRequest request, String organlevel, String uporgan) {
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("code", "true");
        resultMap.put("msg", "录入成功！");
        InputStream is = null;
        try {
            //-------处理表格-------
            String originalFilename = file.getOriginalFilename();
            originalFilename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + BocoUtils.getNowDate() + BocoUtils.getNowTime() + "_" + originalFilename;
            File fileExcel = new File(uploadPath, originalFilename);
            is = file.getInputStream();
            FileUtils.copyInputStreamToFile(is, fileExcel);

            //判断录入的计划文件是否正确
            List<String> excelTitleList = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 1);
            if (excelTitleList == null || excelTitleList.size() == 0) {
                resultMap.put("code", "false");
                resultMap.put("msg", "请录入正确的文件！");
                return resultMap;
            }
            String excelTitle = excelTitleList.get(0);
            String title = getSessionOrgan().getOrganname() + "制定批量机构调整计划导入表样（录入调整金额）";
            if (!title.equals(excelTitle)) {
                resultMap.put("code", "false");
                resultMap.put("msg", "请录入正确的文件！");
                return resultMap;
            }

            //获取表格贷种组合
            List<String> cellValues = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 2);


            //查询数据库贷种组合
            List<Map<String, Object>> combResultList = loanCombMapper.selectComb(new HashMap<>());
            HashMap<String, String> combResultMap = new HashMap<>();
            for (Map<String, Object> map : combResultList) {
                combResultMap.put(map.get("combcode").toString(), "");
            }
            //把表格的贷种组合格式化，并且判断表格贷种组合是否存在于数据库中
            String[] cells = new String[cellValues.size()];
            for (int i = 0; i < cellValues.size(); i++) {
                String combCode = findCode(cellValues.get(i));
                if (!combResultMap.containsKey(combCode)) {
                    if (!("organ".equals(combCode) || "".equals(combCode))) {
                        resultMap.put("code", "false");
                        resultMap.put("msg", "贷种组合【" + cellValues.get(i) + "】在数据库中不存在，请检查！");
                        return resultMap;
                    }
                }
                cells[i] = combCode;
            }


            //-------数据入库-------
            String planMonth = request.getParameter("planMonth");
            String planUnit = request.getParameter("planUnit");
            String increment = request.getParameter("increment");
            String planadjUnifiedType = request.getParameter("planadjUnifiedType");

            //校验特殊字段非空
            if (StringUtils.isEmpty(planMonth) || StringUtils.isEmpty(planUnit)) {
                resultMap.put("code", "false");
                resultMap.put("msg", "请选择月份或单位！");
                return resultMap;
            }

            //判断调整的贷种级别和制定的贷种级别一致性
            TbPlan tbPlanParam = new TbPlan();
            tbPlanParam.setPlanMonth(planMonth);
            tbPlanParam.setPlanType(TbPlan.PLAN);
            tbPlanParam.setPlanOrgan(organCode);
            TbPlan tbPlan = tbPlanMapper.selectByAttr(tbPlanParam).get(0);
            LoanCombDO loanComb = loanCombMapper.getLoanCombInfoByCombCode(cells[1]);
            if (tbPlan.getCombLevel().intValue() != loanComb.getCombLevel()) {
                resultMap.put("code", "false");
                resultMap.put("msg", "导入计划调整失败！导入的计划贷种级别和原计划贷种级别不一致！");
                return resultMap;
            }

            // 校验唯一性
            TbPlanadj queryPlanadj = new TbPlanadj();
            queryPlanadj.setPlanadjMonth(planMonth);
            queryPlanadj.setPlanadjType(TbPlan.PLAN);
            queryPlanadj.setPlanadjOrgan(getSessionOrgan().getThiscode());
            List<TbPlanadj> tbPlanadjListCheck = planadjMapper.selectByAttr(queryPlanadj);
            // 当计划调整存在时并且是 未提交 或 驳回 状态，更新计划
            String planadjId = IDGeneratorUtils.getSequence();
            Integer planadjStatus = new Integer(TbReqDetail.STATE_NEW);
            String planadjCreaTime = BocoUtils.getTime();
            if (CollectionUtils.isNotEmpty(tbPlanadjListCheck)) {
                for (TbPlanadj tbPlanadj : tbPlanadjListCheck) {
                    if (TbReqDetail.STATE_DISMISSED == tbPlanadj.getPlanadjStatus().intValue()) {
                        planadjId = tbPlanadj.getPlanadjId();
                        planadjStatus = tbPlanadj.getPlanadjStatus();
                        planadjCreaTime = tbPlanadj.getPlanadjCreateTime();
                    } else if (TbReqDetail.STATE_NEW == tbPlanadj.getPlanadjStatus().intValue()) {
                        planadjId = tbPlanadj.getPlanadjId();
                        planadjStatus = tbPlanadj.getPlanadjStatus();
                        planadjCreaTime = tbPlanadj.getPlanadjCreateTime();
                    } else if (TbReqDetail.STATE_APPROVALING == tbPlanadj.getPlanadjStatus().intValue()) {
                        resultMap.put("code", "false");
                        resultMap.put("msg", "该月份计划调整正在审批，不能导入！");
                        return resultMap;
                    }
                }
            }


            //获取表格所有内容
            List<Map<String, Object>> maps = ExcelImport.excelList(cells, uploadPath + originalFilename, 3, 1, cells.length);

            //获取信贷计划明细
            Map<String, Object> planMap = getCreditPlanDetail2(planMonth);

            //获取分行需求
            Map<String, Object> reqMap = getReqDetail2(planMonth);

            //获取贷种组合
            int combLevel = tbPlan.getCombLevel();
            List<Map<String, Object>> combList = getCombList(combLevel);

            ArrayList<TbPlanadjDetail> tbPlanadjDetailList = new ArrayList<>();
            BigDecimal adjAmount = new BigDecimal("0");
            BigDecimal planRealIncrement = new BigDecimal("0");
            //构建计划调整详情
            for (Map<String, Object> map : maps) {
                String organ = findCode(map.get("organ").toString());
                if (!"".equals(organ)) {
                    for (Map<String, Object> comb : combList) {
                        String combCode = comb.get("combcode").toString();
                        String amount = map.get(combCode) == null ? "0" : map.get(combCode).toString();
                        BigDecimal planadjAdjAmount = null;
                        try {
                            planadjAdjAmount = new BigDecimal("".equals(amount) ? "0" : amount);
                        } catch (Exception e) {
                            resultMap.put("code", "false");
                            resultMap.put("msg", "导入计划调整失败！请录入正确的数值！");
                            return resultMap;
                        }
                        if ("2".equals(planUnit)) {
                            planadjAdjAmount = planadjAdjAmount.multiply(new BigDecimal("10000"));
                        }
                        //构建计划调整详情
                        TbPlanadjDetail tbPlanadjDetail = new TbPlanadjDetail();
                        tbPlanadjDetail.setPlanadjdId(IDGeneratorUtils.getSequence());
                        tbPlanadjDetail.setPlanadjdRefId(planadjId);
                        tbPlanadjDetail.setPlanadjdMonth(planMonth);
                        tbPlanadjDetail.setPlanadjdOrgan(organ);
                        tbPlanadjDetail.setPlanadjdLoanType(combCode);
                        tbPlanadjDetail.setPlanadjdInitAmount(getSafeCount(planMap.get(organ + "_" + combCode)));
                        tbPlanadjDetail.setPlanadjdReqAmount(getSafeCount(reqMap.get(organ + "_" + combCode)));
                        tbPlanadjDetail.setPlanadjdAdjAmount(planadjAdjAmount);
                        tbPlanadjDetail.setPlanadjdAdjedAmount(tbPlanadjDetail.getPlanadjdInitAmount().add(tbPlanadjDetail.getPlanadjdAdjAmount()));
                        tbPlanadjDetail.setPlanadjdUnit(Integer.parseInt(planUnit));
                        tbPlanadjDetail.setPlanadjdCreateTime(BocoUtils.getTime());
                        tbPlanadjDetail.setPlanadjdUpdateTime(BocoUtils.getTime());

                        adjAmount = adjAmount.add(planadjAdjAmount);
                        planRealIncrement = planRealIncrement.add(tbPlanadjDetail.getPlanadjdAdjedAmount());

                        tbPlanadjDetailList.add(tbPlanadjDetail);
                    }
                }
            }

            //构建计划调整批次
            TbPlanadj tbPlanadj = new TbPlanadj();
            tbPlanadj.setPlanadjId(planadjId);
            tbPlanadj.setPlanadjMonth(planMonth);
            BigDecimal planIncrement = new BigDecimal(StringUtils.isEmpty(increment) ? "0" : increment);
            planIncrement = planIncrement.multiply(new BigDecimal("10000"));
            tbPlanadj.setPlanadjNetIncrement(planIncrement);
            tbPlanadj.setPlanadjUnit(Integer.parseInt(planUnit));
            tbPlanadj.setPlanadjOrgan(organCode);
            tbPlanadj.setPlanadjType(TbPlan.PLAN);
            tbPlanadj.setPlanadjUnifiedType(Integer.valueOf(planadjUnifiedType == "" ? "2" : planadjUnifiedType));
            tbPlanadj.setPlanadjCreateTime(planadjCreaTime);
            tbPlanadj.setPlanadjUpdateTime(BocoUtils.getTime());
            tbPlanadj.setPlanadjCreateOpercode(operCode);
            tbPlanadj.setPlanadjStatus(planadjStatus);
            tbPlanadj.setPlanadjAdjAmount(adjAmount);
            tbPlanadj.setPlanadjRealIncrement(planRealIncrement);
            tbPlanadj.setCombLevel(loanComb.getCombLevel());

            //一级分行计划制定量要等于计划净增量
            if ("1".equals(organlevel)) {

                // 一分贷种级别
                int combLevelOne = tbPlanadj.getCombLevel();
                //总行贷种级别
                TbPlan planParam = new TbPlan();
                planParam.setPlanMonth(planMonth);
                planParam.setPlanOrgan(uporgan);
                planParam.setPlanType(TbPlan.PLAN);
                TbPlan upPlan = tbPlanMapper.selectByAttr(planParam).get(0);
                int combLevelTotal = upPlan.getCombLevel();

                // 获取当前贷种map  相同贷种直接比较，不同贷种转换为一级贷种比较
                Map<String, BigDecimal> realUpcombIncrementMap = null;
                //总行给该机构指定的计划量   相同贷种直接比较，不同贷种转换为一级贷种比较
                Map<String, BigDecimal> upcombIncrementMap = null;

                if (combLevelTotal == 1 && combLevelOne == 1) {
                    // 总行->一级贷种  一分->一级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                } else if (combLevelTotal == 1 && combLevelOne == 2) {
                    // 总行->一级贷种  一分->二级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMapAndTransCombLevel(tbPlanadjDetailList, combLevelOne);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                } else if (combLevelTotal == 2 && combLevelOne == 1) {
                    // 总行->二级贷种  一分->一级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMapAndTransCombLevel(planMonth, uporgan, organCode, combLevelTotal);

                } else if (combLevelTotal == 2 && combLevelOne == 2) {
                    // 总行->二级贷种  一分->二级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                }

                //比较计划和制定的净增量
                for (String upcombCode : upcombIncrementMap.keySet()) {
                    //如果实际制定量不等于计划净增量，插入失败
                    if (getSafeCount(realUpcombIncrementMap.get(upcombCode)).compareTo(getSafeCount(upcombIncrementMap.get(upcombCode))) != 0) {
                        HashMap<String, Object> querymap = new HashMap<>();
                        querymap.put("combcode", upcombCode);
                        List<Map<String, Object>> combs = loanCombService.selectCombBycombcode(querymap);
                        String combname = combs.get(0).get("combname").toString();

                        resultMap.put("code", "false");
                        resultMap.put("msg", "录入计划调整失败！" + combname + "制定净增量为" + realUpcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "亿元，计划净增量为"
                                + upcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "亿元，请调整！");

                        return resultMap;
                    }
                }

            }

            planadjDetailMapper.deleteByWhere("planadjd_ref_id = \'" + planadjId + "\'");
            planadjDetailMapper.insertBatch(tbPlanadjDetailList);

            planadjMapper.deleteByPK(planadjId);
            planadjMapper.insertEntity(tbPlanadj);


            //最后删除文件
            delFile(fileExcel);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("录入失败");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    @Override
    public void downloadPlan(HttpServletResponse response, HttpServletRequest request, String organlevel) throws Exception {
        // 获取输出流
        OutputStream os = response.getOutputStream();

        //创建工作表
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        //居中样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        /*查询导出机构*/
        List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

        /*查询导出的数据*/
        String planadjId = request.getParameter("planadjId");
        TbPlanadj tbPlanadj = planadjMapper.selectByPK(planadjId);
        TbPlanadjDetail tbPlanadjDetailParam = new TbPlanadjDetail();
        tbPlanadjDetailParam.setPlanadjdRefId(planadjId);
        List<TbPlanadjDetail> tbPlanadjDetailList = planadjDetailMapper.selectByAttr(tbPlanadjDetailParam);

        //获取贷种组合
        int combLevel = tbPlanadj.getCombLevel();
        List<Map<String, Object>> combList = getCombList(combLevel);

        /*包装数据*/
        //计划详情
        Map<String, TbPlanadjDetail> planadjMap = new HashMap<>();
        for (TbPlanadjDetail tbPlanadjDetail : tbPlanadjDetailList) {
            //万元转亿元
            if (tbPlanadj.getPlanadjUnit() == 2) {
                tbPlanadjDetail.setPlanadjdReqAmount(tbPlanadjDetail.getPlanadjdReqAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdInitAmount(tbPlanadjDetail.getPlanadjdInitAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjAmount(tbPlanadjDetail.getPlanadjdAdjAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjedAmount(tbPlanadjDetail.getPlanadjdAdjedAmount().divide(new BigDecimal("10000")));
            }
            planadjMap.put(tbPlanadjDetail.getPlanadjdOrgan() + "_" + tbPlanadjDetail.getPlanadjdLoanType(), tbPlanadjDetail);
        }
        //机构
        HashMap<String, Integer> organMap = new HashMap<>();
        for (int i = 0; i < organList.size(); i++) {
            Map<String, Object> map = organList.get(i);
            organMap.put(map.get("organcode").toString(), 4 + i);
        }
        //贷种
        HashMap<String, Integer> combMap = new HashMap<>();
        for (int i = 0; i < combList.size(); i++) {
            Map<String, Object> map = combList.get(i);
            combMap.put(map.get("combcode").toString(), 1 + i);
        }

        /*写入文件*/

        String filename = getSessionOrgan().getOrganname() + "-" + tbPlanadj.getPlanadjMonth() + "-批量机构调整计划";
        //表头
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, combList.size() * 4);
        POIExportUtil.setCell(sheet, 0, 0, filename, cellStyle);
        //单位
        POIExportUtil.setCell(sheet, 1, 0, "单位:", null);
        POIExportUtil.setCell(sheet, 1, 1, tbPlanadj.getPlanadjUnit() == 1 ? "万元" : "亿元", cellStyle);

        //机构
        POIExportUtil.CellRangeAddress(sheet, 2, 3, 0, 0);
        POIExportUtil.setCell(sheet, 2, 0, "机构", cellStyle);
        for (Map<String, Object> map : organList) {
            Integer row = organMap.get(map.get("organcode"));
            if (row != null) {
                POIExportUtil.setCell(sheet, row, 0, map.get("organname").toString(), cellStyle);
            }
        }
        //贷种
        for (Map<String, Object> map : combList) {
            Integer column = combMap.get(map.get("combcode"));
            if (column != null) {
                POIExportUtil.CellRangeAddress(sheet, 2, 2, (column - 1) * 4 + 1, (column - 1) * 4 + 4);
                POIExportUtil.setCell(sheet, 2, (column - 1) * 4 + 1, map.get("combname").toString(), cellStyle);
                POIExportUtil.setCell(sheet, 3, (column - 1) * 4 + 1, "原计划金额", cellStyle);
                POIExportUtil.setCell(sheet, 3, (column - 1) * 4 + 2, "分行需求金额", cellStyle);
                POIExportUtil.setCell(sheet, 3, (column - 1) * 4 + 3, "调整金额", cellStyle);
                POIExportUtil.setCell(sheet, 3, (column - 1) * 4 + 4, "调整后金额", cellStyle);
            }
        }
        //数据
        for (String key : planadjMap.keySet()) {
            String[] keys = key.split("_");
            Integer row = organMap.get(keys[0]);
            Integer column = combMap.get(keys[1]);
            if (row != null && column != null) {
                POIExportUtil.setCell(sheet, row, (column - 1) * 4 + 1, planadjMap.get(key).getPlanadjdInitAmount(), cellStyle);
                POIExportUtil.setCell(sheet, row, (column - 1) * 4 + 2, planadjMap.get(key).getPlanadjdReqAmount(), cellStyle);
                POIExportUtil.setCell(sheet, row, (column - 1) * 4 + 3, planadjMap.get(key).getPlanadjdAdjAmount(), cellStyle);
                POIExportUtil.setCell(sheet, row, (column - 1) * 4 + 4, planadjMap.get(key).getPlanadjdAdjedAmount(), cellStyle);
            }
        }

        //设置列宽
        for (int i = 0; i < combList.size() * 4 + 1; i++) {
            POIExportUtil.setCellWidth(sheet, i);
        }

        //冻结行列
        POIExportUtil.createFreezePane(sheet, 1, 4);

        //文件名
        filename = filename + ".xls";
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            //win10 edge ie 和其他系统的ie浏览器
            filename = URLEncoder.encode(filename, "UTF-8");
        } else {
            filename = new String(filename.getBytes("utf-8"), "iso-8859-1");

        }
//         清空response
        response.reset();
//        response.setHeader("Content-Type", "application/msexcel");
        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", filename));
        response.setContentType("application/octet-stream");

        wb.write(os);
        os.flush();
        os.close();
    }


    /*计划调整--获取当前月的计划值*/
    private Map<String, Object> getCreditPlanDetail2(String month) throws Exception {
        HashMap<String, Object> param = new HashMap<>();
        param.put("organ", getSessionOrgan().getThiscode());
        param.put("month", month);
        param.put("planType", TbPlan.PLAN);
        List<Map<String, Object>> list = planadjMapper.getCreditPlanDetail(param);
        Map<String, Object> resultMap = new HashMap<>();
        for (Map<String, Object> map : list) {
            String key = map.get("organcode") + "_" + map.get("loantype");
            resultMap.put(key, map.get("amount"));
        }

        return resultMap;
    }

    /*计划调整--获取当前月需求值*/
    private Map<String, Object> getReqDetail2(String month) throws Exception {
        //获取机构需求
        Map<String, BigDecimal> reqMap = tbReqresetDetailService.getReqResetDetailList(month, getSessionOrgan().getThiscode());

        HashMap<String, Object> resultMap = new HashMap<>();
        for (String key : reqMap.keySet()) {
            resultMap.put(key, reqMap.get(key));
        }

        return resultMap;
    }


    @Override
    public PlainResult<String> savePlanadj(HttpServletRequest request, String operCode, String organcode, String uporgan) throws Exception {

        PlainResult<String> result = new PlainResult<>();
        try {

            String planMonth = request.getParameter("planMonth");
            String planUnit = request.getParameter("planUnit");
            String increment = request.getParameter("increment");
            String combLevelStr = request.getParameter("combLevel");
            String planadjUnifiedType = request.getParameter("planadjUnifiedType");


            if (StringUtils.isEmpty(planMonth) || StringUtils.isEmpty(planUnit) || StringUtils.isEmpty(increment) || StringUtils.isEmpty(planadjUnifiedType) || StringUtils.isEmpty(combLevelStr)) {
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "参数不能为空！");
            }

            //校验唯一性
            TbPlanadj queryPlanadj = new TbPlanadj();
            queryPlanadj.setPlanadjMonth(planMonth);
            queryPlanadj.setPlanadjType(TbPlan.PLAN);
            queryPlanadj.setPlanadjOrgan(getSessionOrgan().getThiscode());
            List<TbPlanadj> tbPlanadjs = planadjMapper.selectByAttr(queryPlanadj);
            for (TbPlanadj tbPlanadj : tbPlanadjs) {
                if (TbReqDetail.STATE_APPROVED != tbPlanadj.getPlanadjStatus()) {
                    return result.error(HttpServletResponse.SC_FORBIDDEN, "当前月份的计划调整已存在！");
                }
            }

            //获取机构号
            // List<Map<String, Object>> organList = fdOrganMapper.selectByUporgan(getSessionOrgan().getThiscode());
            List<Map<String, Object>> organList = fdOrganService.selectOrgan(getSessionOrgan().getThiscode());

            //获取登录机构等级
            String organlevel = getSessionOrgan().getOrganlevel();
            //获取贷种组合级别
            int combLevel = 0;
            try {
                combLevel = Integer.valueOf(combLevelStr).intValue();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            //获取贷种组合
            List<Map<String, Object>> combList = this.getCombList(combLevel);

            //生成信贷计划调整批次
            String planadjId = IDGeneratorUtils.getSequence();
            TbPlanadj tbPlanadj = new TbPlanadj();
            tbPlanadj.setPlanadjMonth(planMonth);
            tbPlanadj.setPlanadjUnit(Integer.parseInt(planUnit));
            tbPlanadj.setCombLevel(combLevel);
            tbPlanadj.setPlanadjCreateTime(BocoUtils.getTime());
            tbPlanadj.setPlanadjUpdateTime(BocoUtils.getTime());
            tbPlanadj.setPlanadjCreateOpercode(operCode);
            tbPlanadj.setPlanadjId(planadjId);
            tbPlanadj.setPlanadjOrgan(organcode);
            BigDecimal planIncrement = new BigDecimal(StringUtils.isEmpty(increment) ? "0" : increment);
            planIncrement = planIncrement.multiply(new BigDecimal("10000"));
            tbPlanadj.setPlanadjNetIncrement(planIncrement);
            tbPlanadj.setPlanadjType(TbPlan.PLAN);
            tbPlanadj.setPlanadjUnifiedType(Integer.valueOf(planadjUnifiedType));

            BigDecimal adjAmountCount = new BigDecimal("0");
            BigDecimal amountFinalCount = new BigDecimal("0");
            ArrayList<TbPlanadjDetail> tbPlanadjDetailList = new ArrayList<>();
            //生成信贷计划调整明细
            for (Map<String, Object> map : organList) {
                String organcode1 = (String) map.get("organcode");
                for (Map<String, Object> comb : combList) {
                    String combcode = (String) comb.get("combcode");
                    String planadjDetailId = IDGeneratorUtils.getSequence();
                    TbPlanadjDetail tbPlanadjDetail = new TbPlanadjDetail();
                    tbPlanadjDetail.setPlanadjdId(planadjDetailId);
                    tbPlanadjDetail.setPlanadjdRefId(planadjId);
                    tbPlanadjDetail.setPlanadjdOrgan(organcode1);
                    tbPlanadjDetail.setPlanadjdLoanType(combcode);
                    BigDecimal initAmount = new BigDecimal(request.getParameter(organcode1 + "_" + combcode + "_init"));
                    BigDecimal adjAmount = new BigDecimal(request.getParameter(organcode1 + "_" + combcode + "_adj"));
                    BigDecimal finalAmount = new BigDecimal(request.getParameter(organcode1 + "_" + combcode + "_final"));
                    BigDecimal reqAmount = new BigDecimal(request.getParameter(organcode1 + "_" + combcode + "_req")); //增加需求金额
                    //亿元转万元
                    if ("2".equals(planUnit)) {
                        initAmount = initAmount.multiply(new BigDecimal("10000"));
                        adjAmount = adjAmount.multiply(new BigDecimal("10000"));
                        finalAmount = finalAmount.multiply(new BigDecimal("10000"));
                        reqAmount = reqAmount.multiply(new BigDecimal("10000"));
                    }
                    tbPlanadjDetail.setPlanadjdInitAmount(initAmount);
                    tbPlanadjDetail.setPlanadjdAdjAmount(adjAmount);
                    tbPlanadjDetail.setPlanadjdAdjedAmount(finalAmount);
                    tbPlanadjDetail.setPlanadjdReqAmount(reqAmount);
                    tbPlanadjDetail.setPlanadjdUnit(Integer.parseInt(planUnit));
                    tbPlanadjDetail.setPlanadjdMonth(planMonth);
                    tbPlanadjDetail.setPlanadjdCreateTime(BocoUtils.getTime());
                    tbPlanadjDetail.setPlanadjdUpdateTime(BocoUtils.getTime());
                    tbPlanadjDetail.set__status("0");
                    //计算总的调整量
                    adjAmountCount = adjAmountCount.add(adjAmount);
                    //计算本月调整净增量
                    amountFinalCount = amountFinalCount.add(finalAmount);
                    tbPlanadjDetailList.add(tbPlanadjDetail);
                }
            }


            //一级分行 制定净增量要等于计划净增量--贷种细分计算
            if ("1".equals(organlevel)) {

                // 一分贷种级别
                int combLevelOne = tbPlanadj.getCombLevel();
                //总行贷种级别
                TbPlan planParam = new TbPlan();
                planParam.setPlanMonth(planMonth);
                planParam.setPlanOrgan(uporgan);
                planParam.setPlanType(TbPlan.PLAN);
                TbPlan upPlan = tbPlanMapper.selectByAttr(planParam).get(0);
                int combLevelTotal = upPlan.getCombLevel();

                // 获取当前贷种map  相同贷种直接比较，不同贷种转换为一级贷种比较
                Map<String, BigDecimal> realUpcombIncrementMap = null;
                //总行给该机构指定的计划量   相同贷种直接比较，不同贷种转换为一级贷种比较
                Map<String, BigDecimal> upcombIncrementMap = null;

                if (combLevelTotal == 1 && combLevelOne == 1) {
                    // 总行->一级贷种  一分->一级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organcode);

                } else if (combLevelTotal == 1 && combLevelOne == 2) {
                    // 总行->一级贷种  一分->二级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMapAndTransCombLevel(tbPlanadjDetailList, combLevelOne);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organcode);

                } else if (combLevelTotal == 2 && combLevelOne == 1) {
                    // 总行->二级贷种  一分->一级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMapAndTransCombLevel(planMonth, uporgan, organcode, combLevelTotal);

                } else if (combLevelTotal == 2 && combLevelOne == 2) {
                    // 总行->二级贷种  一分->二级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organcode);

                }

                //比较计划和制定的净增量
                for (String upcombCode : upcombIncrementMap.keySet()) {
                    //如果实际制定量不等于计划净增量，插入失败
                    if (getSafeCount(realUpcombIncrementMap.get(upcombCode)).compareTo(getSafeCount(upcombIncrementMap.get(upcombCode))) != 0) {
                        HashMap<String, Object> querymap = new HashMap<>();
                        querymap.put("combcode", upcombCode);
                        List<Map<String, Object>> maps = loanCombService.selectCombBycombcode(querymap);
                        String combname = maps.get(0).get("combname").toString();
                        return result.error(HttpServletResponse.SC_REQUEST_TIMEOUT,
                                combname + "制定净增量为" + realUpcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "亿元，计划净增量为"
                                        + upcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "亿元，请调整");
                    }
                }

            }


            //保存数据
            tbPlanadj.setPlanadjAdjAmount(adjAmountCount);
            tbPlanadj.setPlanadjRealIncrement(amountFinalCount);
            planadjMapper.insertEntity(tbPlanadj);
            planadjDetailMapper.insertBatch(tbPlanadjDetailList);

            return result.success("insert", "insert loan planadj detail success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "insert loan planadj detail fail");
    }


    /*启动信贷计划审批流*/
    @Override
    public PlainResult<String> startLoanReqAuditProcess(String planadjId, String organCode, String operCode, String operName, String assignee, String comment) throws Exception {

        /*流程key*/
        String processKey = "";
        /*机构级别*/
        String organLevel = getSessionOrgan().getOrganlevel();
        if ("0".equals(organLevel)) {
            processKey = AuditMix.PLAN_RESET_TOTAL_MECH;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.PLAN_RESET_ONE_MECH;
        }

        String msgNo = BocoUtils.getUUID();
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> varMap = new HashMap<String, Object>();
        //业务代码
        varMap.put("businessKey", planadjId);
        //下一审批人
        varMap.put("msgNo", msgNo);
        varMap.put("assignee", assignee);
        varMap.put("startUser", WebContextUtil.getSessionUser().getOpercode());
        varMap.put("organLevel", getSessionOrgan().getOrganlevel());
        //用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId(WebContextUtil.getSessionUser().getOpercode());
        ProcessInstance pi = workFlowService.startProcess(processKey, varMap);
        String workFlowCode = pi.getProcessInstanceId();
        //提交第一个任务
        Task task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), operCode + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
        workFlowService.completeTask(task.getId(), comment, null);
        //获取最新的任务，并将任务执行对应审批人员
        task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), assignee);

        logger.info("批量机构调整计划审批流程编号workFlowCode【" + workFlowCode + "】");

        //更新信贷计划调整记录状态
        TbPlanadj tbPlanadj = new TbPlanadj();
        tbPlanadj.setPlanadjId(planadjId);
        tbPlanadj.setPlanadjStatus(LoanStateEnums.STATE_APPROVING.status);
        tbPlanadj.setPlanadjUpdateTime(BocoUtils.getTime());
        planadjMapper.updateByPK(tbPlanadj);

        //记录审批信息
        String url = "tbPlanadjPendingApp/listTbPlanDetailAuditUI.htm?planadjId=" + planadjId + "&taskId=" + task.getId();
        saveMsg(msgNo, operCode, assignee, url, planadjId);
        return result.success(workFlowCode, "启动批量机构调整计划审批流程启动成功");
    }

    /*查询已提交的信贷计划调整*/
    @Override
    public List<Map<String, Object>> getAuditRecordHistRecord(String sort, String operCode, String auditStatus, String reqMonth, WebOperInfo sessionOperInfo) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("opercode", operCode);
        if (auditStatus != null && !"".equals(auditStatus)) {
            map.put("auditStatus", Integer.parseInt(auditStatus));
        }
        map.put("reqMonth", reqMonth);
        map.put("planadjType", TbPlan.PLAN);
        map.put("sort", sort);

        List<Map<String, Object>> list = planadjMapper.getAuditRecordHist(map);

        for (Map<String, Object> map1 : list) {
            String processInstanceId = (String) map1.get("procinstid");
            Task task = workFlowService.getTaskByPid(processInstanceId);
            if (task == null) {
                map1.put("taskId", "");
            } else {
                map1.put("taskId", task.getId());

            }
        }

        //万元转亿元
        for (Map<String, Object> map2 : list) {
            map2.put("planadjnetincrement", new BigDecimal(map2.get("planadjnetincrement").toString()).divide(new BigDecimal("10000")));
            map2.put("planadjadjamount", new BigDecimal(map2.get("planadjadjamount").toString()).divide(new BigDecimal("10000")));
            map2.put("planadjrealincrement", new BigDecimal(map2.get("planadjrealincrement").toString()).divide(new BigDecimal("10000")));
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getPendingAppReq(String sort, String operCode, String reqMonth, String auditStatus, Pager pager) throws Exception {
        /*流程key*/
        String processKey = "";
        /*机构级别*/
        String organLevel = getSessionOrgan().getOrganlevel();
        if ("0".equals(organLevel)) {
            processKey = AuditMix.PLAN_RESET_TOTAL_MECH;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.PLAN_RESET_ONE_MECH;
        }

        //设计任务列表
        List<Map<String, Object>> tastList = new ArrayList<Map<String, Object>>();
        //查询登录用户待办任务
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        rows = workFlowService.getPersonalTaskPage(processKey, pager);
        if (rows != null && rows.size() > 0) {
            List<String> tmplist = new ArrayList<>();
            for (Map<String, Object> map : rows) {
                String processInstanceId = (String) map.get("processInstanceId");
                tmplist.add(processInstanceId);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("proIds", tmplist);
            map.put("assignee", operCode);
            map.put("reqMonth", reqMonth);
            if (auditStatus != null && !"".equals(auditStatus)) {
                map.put("auditStatus", Integer.parseInt(auditStatus));
            }
            map.put("planadjType", TbPlan.PLAN);
            map.put("sort", sort);
            List<Map<String, Object>> list = planadjMapper.getPendingAppReq(map);
            //万元转亿元
            for (Map<String, Object> map2 : list) {
                map2.put("planadjnetincrement", new BigDecimal(map2.get("planadjnetincrement").toString()).divide(new BigDecimal("10000")));
                map2.put("planadjadjamount", new BigDecimal(map2.get("planadjadjamount").toString()).divide(new BigDecimal("10000")));
                map2.put("planadjrealincrement", new BigDecimal(map2.get("planadjrealincrement").toString()).divide(new BigDecimal("10000")));
            }
            return list;

        } else {
            return null;
        }
    }

    @Override
    public void completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception {
        //获取是否同意
        String isAgree = (String) varMap.get("isAgree");
        //获取当前任务对应的流程实例
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskId);
        if (processInstance != null) {
            //获取当前流程实例对应的流程实例id
            //封装新的流程实例id
            msgMap.put("nextProcessInstanceId", processInstance.getId());
        } else {
            msgMap.put("nextProcessInstanceId", "");
        }

        TbPlanadj tbPlanadj = new TbPlanadj();
        tbPlanadj.setPlanadjId(msgMap.get("planadjId").toString());
        //默认先审批通过，后面判断是否最后一个审批人再设置为审批中
        tbPlanadj.setPlanadjStatus(TbReqDetail.STATE_APPROVED);

        if ("0".equals(isAgree)) {//驳回
            tbPlanadj.setPlanadjStatus(TbReqDetail.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String) msgMap.get("planadjId"), taskId, (String) msgMap.get("custType"));
        } else {
            ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
            List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
            if (pvmList != null && pvmList.size() > 0) {
                for (PvmTransition pvm : pvmList) {
                    PvmActivity act = pvm.getDestination();
                    //如果是网关的话，通过网关获取下一个节点的名称
                    if ("Exclusive Gateway".equals(act.getProperty("name"))) {
                        List<PvmTransition> actList = act.getOutgoingTransitions();
                        if (actList != null && actList.size() > 0) {
                            for (PvmTransition gwt : actList) {
                                PvmActivity gw = gwt.getDestination();
                                //如果连接的下一个节点的名称为End，则返回true
                            }
                        }
                    }
                }
            }
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                tbPlanadj.setPlanadjStatus(TbReqDetail.STATE_APPROVALING);
            }
        }

        tbPlanadj.setPlanadjUpdateTime(BocoUtils.getTime());
        planadjMapper.updateByPK(tbPlanadj);

        //流转任务
        workFlowService.completeTask(taskId, comment, varMap);
        //获取最新的任务，并将任务执行对应审批人员
        Task task = workFlowService.getTaskByPid(processInstance.getId());
        if ("0".equals(isAgree)) {
            //获取流程发起人
            String opercode = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
            workFlowService.claim(task.getId(), opercode);
        } else {
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                workFlowService.claim(task.getId(), (String) msgMap.get("assignee"));
            }
        }

        //记录审批信息
        String msgNo = BocoUtils.getUUID();
        String operCode = String.valueOf(msgMap.get("operCode"));
        String assignee = String.valueOf(msgMap.get("assignee"));
        String planadjId = String.valueOf(msgMap.get("planadjId"));
        if ("1".equals(isAgree) && "true".equals((String) msgMap.get("lastUserType"))) {
            deleteMsg(planadjId);
        } else {
            String url = "";
            if ("0".equals(isAgree)) {
                //获取流程发起人
                assignee = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
                url = "tbPlanadjReject/loanTbPlanadjResubmitAuditUI.htm?planadjId=" + planadjId + "&taskId=" + task.getId();
            } else if (!"true".equals((String) msgMap.get("lastUserType"))) {
                url = "tbPlanadjPendingApp/listTbPlanDetailAuditUI.htm?planadjId=" + planadjId + "&taskId=" + task.getId();
            }
            //记录审批信息
            saveMsg(msgNo, operCode, assignee, url, planadjId);
        }

        //如果计划调整审批完成，则把调整内容同步到计划
        if ("1".equals(varMap.get("isAgree")) && ("true".equals((String) msgMap.get("lastUserType")))) {
            List<TbPlanadjDetail> tbPlanadjDetailList = planadjDetailService.selectByWhere("planadjd_ref_id = \'" + planadjId + "\'");
            // 更新额度表
            tbQuotaAllocateService.updatePlanTbQuota(tbPlanadjDetailList, TbPlan.PLAN, planadjId);
            //同步计划
            updatePlanAndPlanadj(tbPlanadjDetailList, planadjId);
        }


    }

    @Override
    public List<Map<String, Object>> getApprovedRecord(String sort, String operCode, String auditStatus, String reqMonth) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("opercode", operCode);
        if (auditStatus != null && !"".equals(auditStatus)) {
            map.put("auditStatus", Integer.parseInt(auditStatus));
        }
        map.put("reqMonth", reqMonth);
        map.put("planadjType", TbPlan.PLAN);
        map.put("sort", sort);
        List<Map<String, Object>> list = planadjMapper.getApprovedRecord(map);

        for (Map<String, Object> map1 : list) {
            String processInstanceId = (String) map1.get("procinstid");
            Task task = workFlowService.getTaskByPid(processInstanceId);
            if (task == null) {
                map1.put("taskId", "");
            } else {
                map1.put("taskId", task.getId());
            }
        }
        //万元转亿元
        for (Map<String, Object> map2 : list) {
            map2.put("planadjnetincrement", new BigDecimal(map2.get("planadjnetincrement").toString()).divide(new BigDecimal("10000")));
            map2.put("planadjadjamount", new BigDecimal(map2.get("planadjadjamount").toString()).divide(new BigDecimal("10000")));
            map2.put("planadjrealincrement", new BigDecimal(map2.get("planadjrealincrement").toString()).divide(new BigDecimal("10000")));
        }


        return list;
    }


    /*删除信贷计划调整*/
    @Override
    public PlainResult<String> deleteTbPlanadjDetail(String planadjId) {
        PlainResult<String> result = new PlainResult<>();
        try {
            planadjMapper.deleteByPK(planadjId);
            planadjMapper.deleteTbPlanadjDetailBYRefId(planadjId);
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "delete loan planadj.");
        }
        return result.success("delete", "delete loan planadj success.");
    }

    @Override
    public List<Map<String, Object>> getCombList(int level) {
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", level);
        List<Map<String, Object>> combList = null;
        if (1 == level) {
            combList = loanCombService.selectComb(combMap);
        } else if (2 == level) {
            combList = loanCombService.selectCombOfBind(combMap);
        }
        return combList;
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author daice
     * @Description //获取当月信贷计划
     * @Date 下午2:25 2019/11/29
     * @Param [month]
     **/
    @Override
    public Map<String, Object> getCreditPlanDetail(String month) throws Exception {
        HashMap<String, Object> param = new HashMap<>();
        param.put("organ", getSessionOrgan().getThiscode());
        param.put("month", month);
        param.put("planType", TbPlan.PLAN);
        List<Map<String, Object>> list = planadjMapper.getCreditPlanDetail(param);
        Map<String, Object> resultMap = new HashMap<>();
        for (Map<String, Object> map : list) {
            //万元转亿元
            if ("2".equals(map.get("unit").toString())) {
                map.put("amount", new BigDecimal(map.get("amount").toString()).divide(new BigDecimal("10000")));
            }
            String key = map.get("organcode") + "_" + map.get("loantype");
            resultMap.put(key, map.get("amount"));
        }

        return resultMap;
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author daice
     * @Description //获取分行调整信贷需求
     * @Date 下午2:25 2019/11/29
     * @Param [month]
     **/
    @Override
    public Map<String, Object> getReqDetail(String month) throws Exception {

        //获取机构需求
        Map<String, BigDecimal> reqMap = tbReqresetDetailService.getReqResetDetailList(month, getSessionOrgan().getThiscode());

        //换算单位
        TbPlan planParam = new TbPlan();
        planParam.setPlanMonth(month);
        planParam.setPlanOrgan(getSessionOrgan().getThiscode());
        planParam.setPlanType(TbPlan.PLAN);
        List<TbPlan> tbPlanList = tbPlanMapper.selectByAttr(planParam);
        if (tbPlanList != null && tbPlanList.size() > 0) {
            if (2 == (tbPlanList.get(0).getPlanUnit())) {
                for (String key : reqMap.keySet()) {
                    reqMap.put(key, BigDecimalUtil.divide(reqMap.get(key), new BigDecimal("10000")));
                }
            }
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        for (String key : reqMap.keySet()) {
            resultMap.put(key, reqMap.get(key));
        }

        return resultMap;
    }

    private void deleteMsg(String planadjId) throws Exception {

        //删除当前小喇叭
        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("批量机构调整计划审批", "MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("批量机构调整计划审批：" + planadjId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }
    }

    private void saveMsg(String msgNo, String operCode, String assignee, String msgUrl, String planadjId) throws Exception {

        //删除当前小喇叭
        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("批量机构调整计划审批", "MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("批量机构调整计划审批：" + planadjId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }


        WebMsg msg = new WebMsg();
        msg.setMsgNo(msgNo);
        msg.setAppDate(BocoUtils.getNowDate());
        msg.setAppUser(WebContextUtil.getSessionUser().getOpercode());
        // msg.setAppOperName(WebContextUtil.getSessionUser().getOpername());

        //获取当前操作人姓名及操作状态
        List<Comment> comments = BocoUtils.translateComments(workFlowService.getTaskComments(msgUrl.substring(msgUrl.lastIndexOf("=") + 1)), "");
        Comment comment1 = comments.get(comments.size() - 2);
        String operNameAndStatus = comment1.getUserId() + ":" + comment1.getType();
        msg.setAppOperName(operNameAndStatus);

        msg.setAppRoleName("");
        msg.setAppOrganCode(getSessionOrgan().getThiscode());
        msg.setAppOrganName(getSessionOrgan().getOrganname());
        msg.setAppTime(BocoUtils.getNowTime());
        msg.setMsgType(DicCache.getKeyByName_("批量机构调整计划审批", "MSG_TYPE"));

        TbPlanadj tbPlanadj = planadjMapper.selectByPK(planadjId);
        BigDecimal adjIncrement = tbPlanadj.getPlanadjAdjAmount();
        String unit = "万元";
        if (tbPlanadj.getPlanadjUnit().intValue() == 2) {
            unit = "亿元";
            adjIncrement = adjIncrement.divide(new BigDecimal("10000"));
        }
        String operName = "所属月份：" + tbPlanadj.getPlanadjMonth()
                + " 计划调整量：" + adjIncrement.toPlainString() + unit;

        msg.setOperName(operName);
        msg.setOperNo(operCode);
        msg.setRepUserCode(assignee);
        msg.setWebMsgStatus("1");
        msg.setMsgUrl(msgUrl);
        msg.setOperDescribe("批量机构调整计划审批：" + planadjId);
        webMsgService.insertEntity(msg);
    }


    //动态生成计划导入表样
    private Workbook exportExcel(List<Map<String, Object>> combList, List<Map<String, Object>> organList, String fileName) {

        Sheet sheet = null;

        // 创建一个excel文件并指定文件名
        String filepath = uploadPath + fileName + ".xls";
        File file = new File(filepath);

        @SuppressWarnings("resource")
        Workbook workbook = new HSSFWorkbook();

        // 创建sheet
        sheet = workbook.createSheet("计划导入");
        //冻结窗口
        sheet.createFreezePane(1, 2, 1, 2);

        // 合并单元格 s CellRangeAddress(起始行号, 结束行号, 其实列号, 结束列号)
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, combList.size()));

        // 标题行样式
        CellStyle titleStyle = workbook.createCellStyle();
        // 水平居中
        titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        //垂直居中
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //设置字体
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 15);
        titleFont.setBoldweight((short) 100);
        titleStyle.setFont(titleFont);

        /*设置单元格自动换行*/
        CellStyle cellStyle = workbook.createCellStyle();
        //设置自动换行
        cellStyle.setWrapText(true);
        // 水平居中
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);


        // 创建标题行
        Row titleRow = sheet.createRow(0);
        //设置行高
        titleRow.setHeight((short) 500);
        titleRow.createCell(0).setCellValue(fileName);
        titleRow.getCell(0).setCellStyle(titleStyle);

        //创建贷种
        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellStyle(cellStyle);
        row1.getCell(0).setCellValue("机构[organ]");

        for (int i = 0; i < combList.size(); i++) {
            //构建贷种名称
            Map<String, Object> map = combList.get(i);
            String combname = map.get("combname").toString();
            String combcode = map.get("combcode").toString();
            String comb = combname + "[" + combcode + "]";
            Cell cell = row1.createCell(i + 1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(comb);

            //设置贷种列宽
            int length = comb.toString().getBytes().length * 256 + 200;
            //这里把宽度最大限制到15000
            if (length > 15000) {
                length = 15000;
            }
            sheet.setColumnWidth(i + 1, length);
        }


// 创建机构
        int organLength = 200;
        Row row = null;
        for (int i = 0; i < organList.size(); i++) {
            row = sheet.createRow(i + 2);
            Cell cell = row.createCell(0);
            Map<String, Object> map = organList.get(i);
            String organname = map.get("organname").toString();
            String organcode = map.get("organcode").toString();
            String organ = organname + "[" + organcode + "]";
            organLength = Math.max(organLength, organ.getBytes().length * 256 + 200);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(organ);
        }

        //设置机构列宽 第一个参数代表列id(从0开始),第2个参数代表宽度值
        sheet.setColumnWidth(0, organLength);

        return workbook;

    }


    /*删除文件*/
    private boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }

    /*找出字符串[]里面的内容*/
    private String findCode(String str) {
        String code = "";
        try {
            int begin = str.indexOf("[");
            int end = str.indexOf("]");
            code = str.substring(begin + 1, end);
        } catch (Exception e) {
            e.printStackTrace();
            code = "";
        }
        return code;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    private BigDecimal getSafeCount(BigDecimal b1) {
        if (b1 == null) {
            return BigDecimal.ZERO;
        }
        return b1;
    }

    private BigDecimal getSafeCount(Object b1) {
        if (b1 == null || "".equals(b1.toString())) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(b1.toString());
    }


    public List<Map<String, Object>> getPlanDetail(String organ, String month, Integer planType) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("organ", organ);
        param.put("month", month);
        param.put("planType", planType);
        List<Map<String, Object>> list = planadjMapper.getCreditPlanDetail(param);
        return list;
    }


}