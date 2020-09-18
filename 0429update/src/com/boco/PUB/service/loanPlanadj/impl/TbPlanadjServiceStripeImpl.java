package com.boco.PUB.service.loanPlanadj.impl;

import com.boco.GF.service.IWebTaskRoleInfoNewService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PM.service.IWebMsgService;
import com.boco.PUB.service.ITbReqresetListService;
import com.boco.PUB.service.ITbTradeParamService;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjDetailService;
import com.boco.PUB.service.loanPlanadj.TbPlanadjServiceStripe;
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
 * 
 * 
 * �Ŵ��ƻ��������α�ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbPlanadjServiceStripeImpl extends GenericService<TbPlanadj, String> implements TbPlanadjServiceStripe {
    //���Զ��巽��ʱʹ��
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");


    public static Logger logger = Logger.getLogger(TbPlanadjServiceStripeImpl. class);


    @Autowired
    private FdOrganMapper fdOrganMapper;
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
    private ILoanPlanService iLoanPlanService;
    @Autowired
    private ITbTradeParamService tbTradeParamService;
    @Autowired
    private IWebLoanCombService loanCombService;
    @Autowired
    private LoanCombMapper loanCombMapper;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;
    @Autowired
    private TbPlanadjDetailService planadjDetailService;
    @Autowired
    private TbQuotaAllocateService tbQuotaAllocateService;
    @Autowired
    private TbPlanService tbPlanService;
    @Autowired
    private ITbReqresetListService tbReqresetListService;

    /*�ƻ��ϴ���ַ*/
    @Value("${upload.path}")
    private String uploadPath;





    @Override
    public Map<String, Object> selectTbplanadjByMonth(String sort, String month, int pageNo, int pageSize) {
        Map<String, Object> results = new Hashtable<>();
        try {
            HashMap<String, Object> queryMap = new HashMap<>();
            queryMap.put("planadjStatus", 0);
            queryMap.put("planadjMonth", month);
            queryMap.put("planadjType", TbPlan.STRIPE);
            queryMap.put("planadjOrgan", getSessionOrgan().getThiscode());
            queryMap.put("sort", sort);

            PageHelper.startPage(pageNo, pageSize, true, true, true);
            List<Map<String,Object>> resultList = planadjMapper.selectPlanadj(queryMap);
            //��Ԫת��Ԫ
            for (Map<String, Object> map : resultList) {
                map.put("planadjnetincrement", new BigDecimal(map.get("planadjnetincrement").toString()).divide(new BigDecimal("10000")));
                map.put("planadjadjamount", new BigDecimal(map.get("planadjadjamount").toString()).divide(new BigDecimal("10000")));
                map.put("planadjrealincrement", new BigDecimal(map.get("planadjrealincrement").toString()).divide(new BigDecimal("10000")));
            }

            //����ҳ��ķ�ҳ����
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
            //��ȡ������
            List<Map<String, Object>> organList = fdOrganMapper.selectByOrganCode(getSessionOrgan().getThiscode());

            //��ȡ��¼�����ȼ�
            String organlevel = getSessionOrgan().getOrganlevel();

            //��ȡ������ϼ��� ���������
            int combLevel = 2;
            //��ѯ�������
            Map<String, Object> combMap = new HashMap<String, Object>();
            combMap.put("combLevel", combLevel);
            List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);

            String planMonth=request.getParameter("planMonth");
            int planUnit=Integer.parseInt(request.getParameter("planUnit"));
            String increment = request.getParameter("increment");

            //�����Ŵ��ƻ���������
            String planadjId = request.getParameter("planadjId");
            TbPlanadj tbPlanadj=new TbPlanadj();
            tbPlanadj.setPlanadjMonth(planMonth);
            tbPlanadj.setPlanadjUnit(planUnit);
            tbPlanadj.setPlanadjId(planadjId);
            BigDecimal planIncrement = new BigDecimal(StringUtils.isEmpty(increment) ? "0" : increment);
            planIncrement = planIncrement.multiply(new BigDecimal("10000"));
            tbPlanadj.setPlanadjNetIncrement(planIncrement);;
            tbPlanadj.setPlanadjUpdateTime(BocoUtils.getTime());

            ArrayList<TbPlanadjDetail> tbPlanadjDetailList = new ArrayList<>();
            BigDecimal adjAmountCount = new BigDecimal("0");
            BigDecimal amountFinalCount = new BigDecimal("0");
            //�����Ŵ��ƻ�������ϸ
            for(Map<String,Object> map:organList){
                String organcode1=(String)map.get("organcode");
                for(Map<String,Object> comb:combList){
                    String combcode=(String)comb.get("combcode");
                    String planadjDetailId = request.getParameter(organcode1+"_"+combcode+"_id");
                    TbPlanadjDetail tbPlanadjDetail=new TbPlanadjDetail();
                    tbPlanadjDetail.setPlanadjdId(planadjDetailId);
                    tbPlanadjDetail.setPlanadjdRefId(planadjId);
                    tbPlanadjDetail.setPlanadjdOrgan(organcode1);
                    tbPlanadjDetail.setPlanadjdLoanType(combcode);
                    BigDecimal initAmount=new BigDecimal(request.getParameter(organcode1+"_"+combcode+"_init"));
                    BigDecimal adjAmount=new BigDecimal(request.getParameter(organcode1+"_"+combcode+"_adj"));
                    BigDecimal finalAmount=new BigDecimal(request.getParameter(organcode1+"_"+combcode+"_final"));
                    BigDecimal reqAmount=new BigDecimal(request.getParameter(organcode1+"_"+combcode+"_req")); //����������
                    //��Ԫת��Ԫ
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
                    //�����ܵĵ�����
                    adjAmountCount = adjAmountCount.add(adjAmount);
                    //���㱾�µ���������
                    amountFinalCount = amountFinalCount.add(finalAmount);
                    tbPlanadjDetailList.add(tbPlanadjDetail);
                }
            }

            //һ�ָ������ƶ��ƻ����ƶ��������ڼƻ������������ƶ�
            if ("1".equals(organlevel)) {
                // һ�ִ��ּ���
                int combLevelOne = combLevel;
                //���д��ּ���
                TbPlan planParam = new TbPlan();
                planParam.setPlanMonth(planMonth);
                planParam.setPlanOrgan(uporgan);
                planParam.setPlanType(TbPlan.PLAN);
                TbPlan upPlan = tbPlanMapper.selectByAttr(planParam).get(0);
                int combLevelTotal = upPlan.getCombLevel();

                // ��ȡ��ǰ����map  ��ͬ����ֱ�ӱȽϣ���ͬ����ת��Ϊһ�����ֱȽ�
                Map<String, BigDecimal> realUpcombIncrementMap = null;
                //���и��û���ָ���ļƻ���   ��ͬ����ֱ�ӱȽϣ���ͬ����ת��Ϊһ�����ֱȽ�
                Map<String, BigDecimal> upcombIncrementMap = null;

                if (combLevelTotal == 1 && combLevelOne ==1) {
                    // ����->һ������  һ��->һ������
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organcode);

                } else if (combLevelTotal == 1 && combLevelOne ==2) {
                    // ����->һ������  һ��->��������
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMapAndTransCombLevel(tbPlanadjDetailList, combLevelOne);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organcode);

                } else if (combLevelTotal == 2 && combLevelOne ==1) {
                    // ����->��������  һ��->һ������
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMapAndTransCombLevel(planMonth, uporgan, organcode,combLevelTotal);

                } else if (combLevelTotal == 2 && combLevelOne ==2) {
                    // ����->��������  һ��->��������
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organcode);

                }

                //�Ƚϼƻ����ƶ��ľ�����
                for (String upcombCode : upcombIncrementMap.keySet()) {
                    //���ʵ���ƶ��������ڼƻ�������������ʧ��
                    if (getSafeCount(realUpcombIncrementMap.get(upcombCode)).compareTo(getSafeCount(upcombIncrementMap.get(upcombCode))) != 0) {
                        HashMap<String, Object> querymap = new HashMap<>();
                        querymap.put("combcode", upcombCode);
                        List<Map<String, Object>> maps = loanCombService.selectCombBycombcode(querymap);
                        String combname = maps.get(0).get("combname").toString();
                        return result.error(HttpServletResponse.SC_REQUEST_TIMEOUT,
                                combname + "�ƶ�������Ϊ" + realUpcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "��Ԫ���ƻ�������Ϊ"
                                        + upcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "��Ԫ�������");
                    }
                }
            }

            //��������
            tbPlanadj.setPlanadjAdjAmount(adjAmountCount);
            tbPlanadj.setPlanadjRealIncrement(amountFinalCount);
            planadjMapper.updateByPK(tbPlanadj);

            //����������ϸ
            planadjMapper.deleteTbPlanadjDetailBYRefId(planadjId);
            planadjDetailMapper.insertBatch(tbPlanadjDetailList);

            return result.success("update", "update loan planadj detail success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update loan planadj detail fail");
    }

    @Override
    public PlainResult<String> savePlanadj(HttpServletRequest request, String operCode, String organcode, String uporgan) throws Exception {

        PlainResult<String> result = new PlainResult<>();
        try {

            String planMonth=request.getParameter("planMonth");
            String planUnit = request.getParameter("planUnit");
            String increment = request.getParameter("increment");

            if (StringUtils.isEmpty(planMonth)||StringUtils.isEmpty(planUnit)||StringUtils.isEmpty(increment)) {
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "��������Ϊ�գ�");
            }

            //У��Ψһ��
            TbPlanadj queryPlanadj = new TbPlanadj();
            queryPlanadj.setPlanadjMonth(planMonth);
            queryPlanadj.setPlanadjType(TbPlan.STRIPE);
            queryPlanadj.setPlanadjOrgan(getSessionOrgan().getThiscode());
            List<TbPlanadj> tbPlanadjs = planadjMapper.selectByAttr(queryPlanadj);
            for (TbPlanadj tbPlanadj : tbPlanadjs) {
                if (TbReqDetail.STATE_APPROVED != tbPlanadj.getPlanadjStatus()) {
                    return result.error(HttpServletResponse.SC_FORBIDDEN, "��ǰ�·ݵļƻ������Ѵ��ڣ�");
                }
            }

            //��ȡ������
            List<Map<String, Object>> organList = fdOrganMapper.selectByOrganCode(getSessionOrgan().getThiscode());

            //��ȡ��¼�����ȼ�
            String organlevel = getSessionOrgan().getOrganlevel();

            //��ȡ������ϼ��� ���������
            int combLevel = 2;
            //��ѯ�������
            Map<String, Object> combMap = new HashMap<String, Object>();
            combMap.put("combLevel", combLevel);
            List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);

            //�����Ŵ��ƻ���������
            String planadjId = IDGeneratorUtils.getSequence();
            TbPlanadj tbPlanadj=new TbPlanadj();
            tbPlanadj.setPlanadjMonth(planMonth);
            tbPlanadj.setPlanadjUnit(Integer.parseInt(planUnit));
            tbPlanadj.setPlanadjCreateTime(BocoUtils.getTime());
            tbPlanadj.setPlanadjUpdateTime(BocoUtils.getTime());
            tbPlanadj.setPlanadjCreateOpercode(operCode);
            tbPlanadj.setPlanadjId(planadjId);
            tbPlanadj.setPlanadjOrgan(organcode);
            BigDecimal planIncrement = new BigDecimal(StringUtils.isEmpty(increment) ? "0" : increment);
            planIncrement = planIncrement.multiply(new BigDecimal("10000"));
            tbPlanadj.setPlanadjNetIncrement(planIncrement);
            tbPlanadj.setPlanadjType(TbPlan.STRIPE);
            tbPlanadj.setCombLevel(TbPlan.COMB_TWO);

            ArrayList<TbPlanadjDetail> tbPlanadjDetailList = new ArrayList<>();
            BigDecimal adjAmountCount = new BigDecimal("0");
            BigDecimal amountFinalCount = new BigDecimal("0");
            //�����Ŵ��ƻ�������ϸ
            for(Map<String,Object> map:organList){
                String organcode1=(String)map.get("organcode");
                for(Map<String,Object> comb:combList){
                    String combcode=(String)comb.get("combcode");
                    String planadjDetailId = IDGeneratorUtils.getSequence();
                    TbPlanadjDetail tbPlanadjDetail=new TbPlanadjDetail();
                    tbPlanadjDetail.setPlanadjdId(planadjDetailId);
                    tbPlanadjDetail.setPlanadjdRefId(planadjId);
                    tbPlanadjDetail.setPlanadjdOrgan(organcode1);
                    tbPlanadjDetail.setPlanadjdLoanType(combcode);
                    BigDecimal initAmount=new BigDecimal(request.getParameter(organcode1+"_"+combcode+"_init"));
                    BigDecimal adjAmount=new BigDecimal(request.getParameter(organcode1+"_"+combcode+"_adj"));
                    BigDecimal finalAmount=new BigDecimal(request.getParameter(organcode1+"_"+combcode+"_final"));
                    BigDecimal reqAmount=new BigDecimal(request.getParameter(organcode1+"_"+combcode+"_req")); //����������
                    //��Ԫת��Ԫ
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
                    //�����ܵĵ�����
                    adjAmountCount = adjAmountCount.add(adjAmount);
                    //���㱾�µ���������
                    amountFinalCount = amountFinalCount.add(finalAmount);
                    tbPlanadjDetailList.add(tbPlanadjDetail);
                }
            }


            //һ�ָ������ƶ��ƻ����ƶ��������ڼƻ������������ƶ�
            if ("1".equals(organlevel)) {
                // һ�ִ��ּ���
                int combLevelOne = tbPlanadj.getCombLevel();
                //���д��ּ���
                TbPlan planParam = new TbPlan();
                planParam.setPlanMonth(planMonth);
                planParam.setPlanOrgan(uporgan);
                planParam.setPlanType(TbPlan.PLAN);
                TbPlan upPlan = tbPlanMapper.selectByAttr(planParam).get(0);
                int combLevelTotal = upPlan.getCombLevel();

                // ��ȡ��ǰ����map  ��ͬ����ֱ�ӱȽϣ���ͬ����ת��Ϊһ�����ֱȽ�
                Map<String, BigDecimal> realUpcombIncrementMap = null;
                //���и��û���ָ���ļƻ���   ��ͬ����ֱ�ӱȽϣ���ͬ����ת��Ϊһ�����ֱȽ�
                Map<String, BigDecimal> upcombIncrementMap = null;

                if (combLevelTotal == 1 && combLevelOne ==1) {
                    // ����->һ������  һ��->һ������
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organcode);

                } else if (combLevelTotal == 1 && combLevelOne ==2) {
                    // ����->һ������  һ��->��������
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMapAndTransCombLevel(tbPlanadjDetailList, combLevelOne);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organcode);

                } else if (combLevelTotal == 2 && combLevelOne ==1) {
                    // ����->��������  һ��->һ������
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMapAndTransCombLevel(planMonth, uporgan, organcode,combLevelTotal);

                } else if (combLevelTotal == 2 && combLevelOne ==2) {
                    // ����->��������  һ��->��������
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organcode);

                }

                //�Ƚϼƻ����ƶ��ľ�����
                for (String upcombCode : upcombIncrementMap.keySet()) {
                    //���ʵ���ƶ��������ڼƻ�������������ʧ��
                    if (getSafeCount(realUpcombIncrementMap.get(upcombCode)).compareTo(getSafeCount(upcombIncrementMap.get(upcombCode))) != 0) {
                        HashMap<String, Object> querymap = new HashMap<>();
                        querymap.put("combcode", upcombCode);
                        List<Map<String, Object>> maps = loanCombService.selectCombBycombcode(querymap);
                        String combname = maps.get(0).get("combname").toString();
                        return result.error(HttpServletResponse.SC_REQUEST_TIMEOUT,
                                combname + "�ƶ�������Ϊ" + realUpcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "��Ԫ���ƻ�������Ϊ"
                                        + upcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "��Ԫ�������");
                    }
                }

            }

            //��������
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


    /*�����Ŵ��ƻ�������*/
    @Override
    public PlainResult<String> startLoanReqAuditProcess(String planadjId, String organCode, String operCode, String operName, String assignee, String comment) throws Exception {

        /*����key*/
        String processKey = "";
        /*��������*/
        String organLevel = getSessionOrgan().getOrganlevel();
        if ("0".equals(organLevel)) {
            processKey = AuditMix.PLAN_RESET_TOTAL_LINE;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.PLAN_RESET_ONE_LINE;
        }

        String msgNo= BocoUtils.getUUID();
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> varMap = new HashMap<String, Object>();
        //ҵ�����
        varMap.put("businessKey", planadjId);
        //��һ������
        varMap.put("msgNo", msgNo);
        varMap.put("assignee", assignee);
        varMap.put("startUser", WebContextUtil.getSessionUser().getOpercode());
        varMap.put("organLevel", getSessionOrgan().getOrganlevel());
        //���������������̵���ԱID��������Զ����û�ID���浽activiti:initiator��
        identityService.setAuthenticatedUserId(WebContextUtil.getSessionUser().getOpercode());
        ProcessInstance pi = workFlowService.startProcess(processKey, varMap);
        String workFlowCode=pi.getProcessInstanceId();
        //�ύ��һ������
        Task task=workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(),operCode+AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
        workFlowService.completeTask(task.getId(),comment,null);
        //��ȡ���µ����񣬲�������ִ�ж�Ӧ������Ա
        task=workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(),assignee);

        logger.info("�������ߵ����ƻ��������̱��workFlowCode��"+workFlowCode+"��");

        //�����Ŵ��ƻ�������¼״̬
        TbPlanadj tbPlanadj = new TbPlanadj();
        tbPlanadj.setPlanadjId(planadjId);
        tbPlanadj.setPlanadjStatus(LoanStateEnums.STATE_APPROVING.status);
        tbPlanadj.setPlanadjUpdateTime(BocoUtils.getTime());
        planadjMapper.updateByPK(tbPlanadj);

        //��¼������Ϣ
        String url ="tbPlanadjStripePendingApp/listTbPlanDetailAuditUI.htm?planadjId="+planadjId+"&taskId="+task.getId();
        saveMsg(msgNo,operCode,assignee,url, planadjId);
        return result.success(workFlowCode, "�����������ߵ����ƻ��������������ɹ�");
    }

    /*��ѯ���ύ���Ŵ��ƻ�����*/
    @Override
    public List<Map<String, Object>> getAuditRecordHistRecord(String sort, String operCode, String auditStatus, String reqMonth, WebOperInfo sessionOperInfo) throws Exception {

        Map<String,Object> map=new HashMap<>();
        map.put("opercode",operCode);
        if(auditStatus!=null&&!"".equals(auditStatus)) {
            map.put("auditStatus", Integer.parseInt(auditStatus));
        }
        map.put("reqMonth",reqMonth);
        map.put("planadjType", TbPlan.STRIPE);
        map.put("sort", sort);

        List<Map<String,Object>> list=planadjMapper.getAuditRecordHist(map);

        for(Map<String,Object> map1 : list){
            String processInstanceId = (String)map1.get("procinstid");
            Task task = workFlowService.getTaskByPid(processInstanceId);
            if(task == null){
                map1.put("taskId","");
            }else{
                map1.put("taskId",task.getId());

            }
        }

        //��Ԫת��Ԫ
        for (Map<String, Object> map2 : list) {
            map2.put("planadjnetincrement", new BigDecimal(map2.get("planadjnetincrement").toString()).divide(new BigDecimal("10000")));
            map2.put("planadjadjamount", new BigDecimal(map2.get("planadjadjamount").toString()).divide(new BigDecimal("10000")));
            map2.put("planadjrealincrement", new BigDecimal(map2.get("planadjrealincrement").toString()).divide(new BigDecimal("10000")));
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getPendingAppReq(String sort, String operCode, String reqMonth, String auditStatus, Pager pager) throws Exception {
        /*����key*/
        String processKey = "";
        /*��������*/
        String organLevel = getSessionOrgan().getOrganlevel();

        if ("0".equals(organLevel)) {
            processKey = AuditMix.PLAN_RESET_TOTAL_LINE;
        } else if ("1".equals(organLevel)) {
            processKey = AuditMix.PLAN_RESET_ONE_LINE;
        }

        //��������б�
        List<Map<String,Object>> tastList = new ArrayList<Map<String,Object>>();
        //��ѯ��¼�û���������
        List<Map<String,Object>> rows =new ArrayList<Map<String,Object>>();
        rows = workFlowService.getPersonalTaskPage(processKey, pager);
        if(rows!=null&&rows.size()>0) {
            List<String> tmplist = new ArrayList<>();
            for (Map<String, Object> map : rows) {
                String processInstanceId = (String) map.get("processInstanceId");
                tmplist.add(processInstanceId);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("proIds", tmplist);
            map.put("assignee", operCode);
            map.put("reqMonth", reqMonth);
            if(auditStatus!=null&&!"".equals(auditStatus)) {
                map.put("auditStatus", Integer.parseInt(auditStatus));
            }
            map.put("planadjType", TbPlan.STRIPE);
            map.put("sort", sort);
            List<Map<String, Object>> list = planadjMapper.getPendingAppReq(map);
            //��Ԫת��Ԫ
            for (Map<String, Object> map2 : list) {
                map2.put("planadjnetincrement", new BigDecimal(map2.get("planadjnetincrement").toString()).divide(new BigDecimal("10000")));
                map2.put("planadjadjamount", new BigDecimal(map2.get("planadjadjamount").toString()).divide(new BigDecimal("10000")));
                map2.put("planadjrealincrement", new BigDecimal(map2.get("planadjrealincrement").toString()).divide(new BigDecimal("10000")));
            }
            return list;

        }else{
            return null;
        }
    }

    @Override
    public void completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap)  throws Exception {
        //��ȡ�Ƿ�ͬ��
        String isAgree = (String)varMap.get("isAgree");
        //��ȡ��ǰ�����Ӧ������ʵ��
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskId);
        if(processInstance != null){
            //��ȡ��ǰ����ʵ����Ӧ������ʵ��id
            //��װ�µ�����ʵ��id
            msgMap.put("nextProcessInstanceId", processInstance.getId());
        }else{
            msgMap.put("nextProcessInstanceId", "");
        }

        TbPlanadj tbPlanadj = new TbPlanadj();
        tbPlanadj.setPlanadjId(msgMap.get("planadjId").toString());
        //Ĭ��������ͨ���������ж��Ƿ����һ��������������Ϊ������
        tbPlanadj.setPlanadjStatus(TbReqDetail.STATE_APPROVED);

        if ("0".equals(isAgree)) {//����
            tbPlanadj.setPlanadjStatus(TbReqDetail.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String)msgMap.get("planadjId"), taskId, (String)msgMap.get("custType"));
        }else {
            ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
            List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
            if(pvmList!=null && pvmList.size()>0){
                for(PvmTransition pvm:pvmList){
                    PvmActivity act = pvm.getDestination();
                    //��������صĻ���ͨ�����ػ�ȡ��һ���ڵ������
                    if("Exclusive Gateway".equals(act.getProperty("name"))){
                        List<PvmTransition> actList = act.getOutgoingTransitions();
                        if(actList!=null && actList.size()>0){
                            for(PvmTransition gwt:actList){
                                PvmActivity gw = gwt.getDestination();
                                //������ӵ���һ���ڵ������ΪEnd���򷵻�true
                            }
                        }
                    }
                }
            }
            if (!"true".equals((String)msgMap.get("lastUserType"))) {
                tbPlanadj.setPlanadjStatus(TbReqDetail.STATE_APPROVALING);
            }
        }

        tbPlanadj.setPlanadjUpdateTime(BocoUtils.getTime());
        planadjMapper.updateByPK(tbPlanadj);

        //��ת����
        workFlowService.completeTask(taskId, comment, varMap);
        //��ȡ���µ����񣬲�������ִ�ж�Ӧ������Ա
        Task task=workFlowService.getTaskByPid(processInstance.getId());
        if ("0".equals(isAgree)) {
            //��ȡ���̷�����
            String opercode=tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
            workFlowService.claim(task.getId(), opercode);
        }else{
            if (!"true".equals((String)msgMap.get("lastUserType"))) {
                workFlowService.claim(task.getId(), (String) msgMap.get("assignee"));
            }
        }

        //��¼������Ϣ
        String msgNo= BocoUtils.getUUID();
        String operCode = String.valueOf(msgMap.get("operCode"));
        String assignee = String.valueOf(msgMap.get("assignee"));
        String planadjId = String.valueOf(msgMap.get("planadjId"));
        if("1".equals(isAgree)&&"true".equals((String) msgMap.get("lastUserType"))){
            deleteMsg(planadjId);
        } else {
            String url = "";
            if ("0".equals(isAgree)) {
                //��ȡ���̷�����
                assignee = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
                url = "tbPlanadjStripeReject/loanTbPlanadjResubmitAuditUI.htm?planadjId=" + planadjId + "&taskId=" + task.getId();
            } else if(!"true".equals((String) msgMap.get("lastUserType"))) {
                url = "tbPlanadjStripePendingApp/listTbPlanDetailAuditUI.htm?planadjId=" + planadjId + "&taskId=" + task.getId();
            }
            //��¼������Ϣ
            saveMsg(msgNo, operCode, assignee, url, planadjId);
        }

        //����ƻ�����������ɣ���ѵ�������ͬ�����ƻ�
        if ("1".equals(varMap.get("isAgree")) && ("true".equals((String)msgMap.get("lastUserType")))) {
            List<TbPlanadjDetail> tbPlanadjDetailList = planadjDetailService.selectByWhere("planadjd_ref_id = \'"+planadjId+"\'");
            // ���¶�ȱ�
            tbQuotaAllocateService.updatePlanTbQuota(tbPlanadjDetailList, TbPlan.STRIPE, planadjId);
            //ͬ���ƻ�
            updatePlanAndPlanadj(tbPlanadjDetailList,planadjId);
        }
    }

    @Override
    public List<Map<String, Object>> getApprovedRecord(String sort, String operCode, String auditStatus, String reqMonth) throws Exception {
        Map<String,Object> map=new HashMap<>();
        map.put("opercode",operCode);
        if(auditStatus!=null&&!"".equals(auditStatus)) {
            map.put("auditStatus", Integer.parseInt(auditStatus));
        }
        map.put("reqMonth",reqMonth);
        map.put("planadjType", TbPlan.STRIPE);
        map.put("sort", sort);

        List<Map<String,Object>> list=planadjMapper.getApprovedRecord(map);

        for(Map<String,Object> map1 : list){
            String processInstanceId = (String)map1.get("procinstid");
            Task task = workFlowService.getTaskByPid(processInstanceId);
            if(task == null){
                map1.put("taskId","");
            }else{
                map1.put("taskId",task.getId());
            }
        }
        //��Ԫת��Ԫ
        for (Map<String, Object> map2 : list) {
            map2.put("planadjnetincrement", new BigDecimal(map2.get("planadjnetincrement").toString()).divide(new BigDecimal("10000")));
            map2.put("planadjadjamount", new BigDecimal(map2.get("planadjadjamount").toString()).divide(new BigDecimal("10000")));
            map2.put("planadjrealincrement", new BigDecimal(map2.get("planadjrealincrement").toString()).divide(new BigDecimal("10000")));
        }

        return list;
    }


    /*ɾ���Ŵ��ƻ�����*/
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

        return planadjMapper.getCombList(level);
    }

    /**
     * @Author daice
     * @Description //��ȡ�����Ŵ��ƻ�
     * @Date ����2:25 2019/11/29
     * @Param [month]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public Map<String, Object> getCreditPlanDetail(String month)throws Exception {
        HashMap<String, Object> param = new HashMap<>();
        param.put("organ", getSessionOrgan().getThiscode());
        param.put("month", month);
        param.put("planType", TbPlan.STRIPE);
        List<Map<String,Object>> list=planadjMapper.getCreditPlanDetail(param);
        Map<String, Object> resultMap=new HashMap<>();
        for(Map<String,Object> map:list){
            //��Ԫת��Ԫ
            if ("2".equals(map.get("unit").toString())) {
                map.put("amount", new BigDecimal(map.get("amount").toString()).divide(new BigDecimal("10000")));
            }
            String key=map.get("organcode")+"_"+map.get("loantype");
            resultMap.put(key,map.get("amount"));
        }

        return resultMap;
    }

    /**
     * @Author daice
     * @Description //��ȡ���е����Ŵ�����
     * @Date ����2:25 2019/11/29
     * @Param [month]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public Map<String, Object> getReqDetail(String month) throws Exception{

        //��ȡ��������
        Map<String, BigDecimal> reqMap = tbReqresetListService.getLineReqResetDetailList(month, getSessionOrgan().getThiscode());

        //���㵥λ
        TbPlan planParam = new TbPlan();
        planParam.setPlanMonth(month);
        planParam.setPlanOrgan(getSessionOrgan().getThiscode());
        planParam.setPlanType(TbPlan.STRIPE);
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

    /*�ƻ��������������¼ƻ�*/
    @Override
    public void updatePlanAndPlanadj(List<TbPlanadjDetail> tbPlanadjDetailList, String planadjId) {
        TbPlanDetail tbPlanDetail = new TbPlanDetail();
        /*���¼ƻ��ƶ��ƻ�������*/
        BigDecimal realIncrement = new BigDecimal("0");

        //���¼ƻ�����
        TbPlanadj tbPlanadj = planadjMapper.selectByPK(planadjId);
        TbPlan tbPlanParam = new TbPlan();
        tbPlanParam.setPlanMonth(tbPlanadj.getPlanadjMonth());
        tbPlanParam.setPlanOrgan(tbPlanadj.getPlanadjOrgan());
        tbPlanParam.setPlanType(TbPlan.STRIPE);
        TbPlan tbPlan = tbPlanMapper.selectByAttr(tbPlanParam).get(0);

        for (TbPlanadjDetail tbPlanadjDetail : tbPlanadjDetailList) {
            realIncrement = realIncrement.add(tbPlanadjDetail.getPlanadjdAdjAmount());
            tbPlanDetail.setPdLoanType(tbPlanadjDetail.getPlanadjdLoanType());
            tbPlanDetail.setPdOrgan(tbPlanadjDetail.getPlanadjdOrgan());
            tbPlanDetail.setPdRefId(tbPlan.getPlanId());
            List<TbPlanDetail> tbPlanDetails = tbPlanDetailMapper.selectByAttr(tbPlanDetail);

            if (tbPlanDetails == null || tbPlanDetails.size() == 0) {
                //˵�����������֣���Ҫ�������ƻ�����
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

        //���¼ƻ�
        tbPlan.setPlanRealIncrement(realIncrement);
        tbPlan.setPlanUpdateTime(BocoUtils.getTime());
        tbPlanMapper.updateByPK(tbPlan);
    }

    @Override
    public Map<String, String> enterReportPlanadjByMonth(MultipartFile file, String operCode, String organCode, HttpServletRequest request, String organlevel, String uporgan) {
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("code", "true");
        resultMap.put("msg", "¼��ɹ���");
        InputStream is = null;
        try {
            //-------������-------
            String originalFilename = file.getOriginalFilename();
            originalFilename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + BocoUtils.getNowDate() +BocoUtils.getNowTime()+ "_" + originalFilename;
            File fileExcel = new File(uploadPath, originalFilename);
            is = file.getInputStream();
            FileUtils.copyInputStreamToFile(is, fileExcel);

            //�ж�¼��ļƻ��ļ��Ƿ���ȷ
            List<String> excelTitleList = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 1);
            if (excelTitleList == null || excelTitleList.size() == 0) {
                resultMap.put("code", "false");
                resultMap.put("msg", "��¼����ȷ���ļ���");
                return resultMap;
            }
            String excelTitle = excelTitleList.get(0);
            String title = getSessionOrgan().getOrganname()+"�ƶ��������ߵ����ƻ����������¼�������";
            if (!title.equals(excelTitle)) {
                resultMap.put("code", "false");
                resultMap.put("msg", "��¼����ȷ���ļ���");
                return resultMap;
            }

            //��ȡ���������
            List<String> cellValues = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 2);


            //��ѯ���ݿ�������
            List<Map<String, Object>> combResultList = loanCombMapper.selectComb(new HashMap<>());
            HashMap<String, String> combResultMap = new HashMap<>();
            for (Map<String, Object> map : combResultList) {
                combResultMap.put(map.get("combcode").toString(), "");
            }
            //�ѱ��Ĵ�����ϸ�ʽ���������жϱ���������Ƿ���������ݿ���
            String[] cells = new String[cellValues.size()];
            for (int i = 0; i < cellValues.size(); i++) {
                String combCode = findCode(cellValues.get(i));
                if (!combResultMap.containsKey(combCode)) {
                    if (!("organ".equals(combCode)||"".equals(combCode))) {
                        resultMap.put("code", "false");
                        resultMap.put("msg", "������ϡ�"+cellValues.get(i)+"�������ݿ��в����ڣ����飡");
                        return resultMap;
                    }
                }
                cells[i] =combCode ;
            }

            //-------�������-------
            String planMonth = request.getParameter("planMonth");
            String planUnit = request.getParameter("planUnit");
            String increment = request.getParameter("increment");
            String planadjUnifiedType = "2";

            //У�������ֶηǿ�
            if (StringUtils.isEmpty(planMonth) || StringUtils.isEmpty(planUnit)) {
                resultMap.put("code", "false");
                resultMap.put("msg", "��ѡ���·ݻ�λ��");
                return resultMap;
            }

            // У��Ψһ��
            TbPlanadj queryPlanadj = new TbPlanadj();
            queryPlanadj.setPlanadjMonth(planMonth);
            queryPlanadj.setPlanadjType(TbPlan.STRIPE);
            queryPlanadj.setPlanadjOrgan(getSessionOrgan().getThiscode());
            List<TbPlanadj> tbPlanadjListCheck = planadjMapper.selectByAttr(queryPlanadj);
            // ���ƻ���������ʱ������ δ�ύ �� ���� ״̬�����¼ƻ�
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
                        resultMap.put("msg", "���·ݼƻ������������������ܵ��룡");
                        return resultMap;
                    }
                }
            }


            //��ȡ�����������
            List<Map<String, Object>> maps = ExcelImport.excelList(cells, uploadPath + originalFilename, 3, 1, cells.length);

            //��ȡ�Ŵ��ƻ���ϸ
            Map<String, Object> planMap = getCreditPlanDetail2(planMonth);

            //��ȡ��������
            Map<String, Object> reqMap = getReqDetail2(planMonth);

            //��ȡ������ϼ��� ���� ����
            int combLevel = 2;

            //��ѯ�������
            Map<String, Object> combMap = new HashMap<>();
            combMap.put("combLevel", combLevel);
            List<Map<String, Object>> combList = null;
            if ("0".equals(organlevel)) {
                combList = loanCombMapper.selectComb(combMap);
            } else if ("1".equals(organlevel)) {
                combList = loanCombMapper.selectCombOfBind(combMap);
            }


            ArrayList<TbPlanadjDetail> tbPlanadjDetailList = new ArrayList<>();
            BigDecimal adjAmount = new BigDecimal("0");
            BigDecimal planRealIncrement = new BigDecimal("0");
            //�����ƻ���������
            for (Map<String, Object> map : maps) {
                String organ = findCode(map.get("organ").toString());
                if (!"".equals(organ)) {
                    for (Map<String, Object> comb : combList) {
                        String combCode = comb.get("combcode").toString();
                        String amount = map.get(combCode) == null ? "0" : map.get(combCode).toString();
                        BigDecimal planadjAdjAmount = null;
                        try {
                            planadjAdjAmount = new BigDecimal("".equals(amount)?"0":amount);
                        } catch (Exception e) {
                            resultMap.put("code", "false");
                            resultMap.put("msg", "����ƻ�����ʧ�ܣ���¼����ȷ����ֵ��");
                            return resultMap;
                        }
                        if ("2".equals(planUnit)) {
                            planadjAdjAmount = planadjAdjAmount.multiply(new BigDecimal("10000"));
                        }
                        //�����ƻ���������
                        TbPlanadjDetail tbPlanadjDetail = new TbPlanadjDetail();
                        tbPlanadjDetail.setPlanadjdId(IDGeneratorUtils.getSequence());
                        tbPlanadjDetail.setPlanadjdRefId(planadjId);
                        tbPlanadjDetail.setPlanadjdMonth(planMonth);
                        tbPlanadjDetail.setPlanadjdOrgan(organ);
                        tbPlanadjDetail.setPlanadjdLoanType(combCode);
                        tbPlanadjDetail.setPlanadjdInitAmount(getSafeCount(planMap.get(organ+"_"+combCode)));
                        tbPlanadjDetail.setPlanadjdReqAmount(getSafeCount(reqMap.get(organ+"_"+combCode)));
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

            //�����ƻ���������
            LoanCombDO loanComb = loanCombMapper.getLoanCombInfoByCombCode(cells[1]);
            TbPlanadj tbPlanadj = new TbPlanadj();
            tbPlanadj.setPlanadjId(planadjId);
            tbPlanadj.setPlanadjMonth(planMonth);
            BigDecimal planIncrement = new BigDecimal(StringUtils.isEmpty(increment) ? "0" : increment);
            planIncrement = planIncrement.multiply(new BigDecimal("10000"));
            tbPlanadj.setPlanadjNetIncrement(planIncrement);
            tbPlanadj.setPlanadjUnit(Integer.parseInt(planUnit));
            tbPlanadj.setPlanadjOrgan(organCode);
            tbPlanadj.setPlanadjType(TbPlan.STRIPE);
            tbPlanadj.setPlanadjUnifiedType(Integer.valueOf(planadjUnifiedType==""?"2":planadjUnifiedType));
            tbPlanadj.setPlanadjCreateTime(planadjCreaTime);
            tbPlanadj.setPlanadjUpdateTime(BocoUtils.getTime());
            tbPlanadj.setPlanadjCreateOpercode(operCode);
            tbPlanadj.setPlanadjStatus(planadjStatus);
            tbPlanadj.setPlanadjAdjAmount(adjAmount);
            tbPlanadj.setPlanadjRealIncrement(planRealIncrement);
            tbPlanadj.setCombLevel(loanComb.getCombLevel());

            //һ�����мƻ��ƶ���Ҫ���ڼƻ�������
            if ("1".equals(organlevel)) {
                // һ�ִ��ּ���
                int combLevelOne = tbPlanadj.getCombLevel();
                //���д��ּ���
                TbPlan planParam = new TbPlan();
                planParam.setPlanMonth(planMonth);
                planParam.setPlanOrgan(uporgan);
                planParam.setPlanType(TbPlan.PLAN);
                TbPlan upPlan = tbPlanMapper.selectByAttr(planParam).get(0);
                int combLevelTotal = upPlan.getCombLevel();

                // ��ȡ��ǰ����map  ��ͬ����ֱ�ӱȽϣ���ͬ����ת��Ϊһ�����ֱȽ�
                Map<String, BigDecimal> realUpcombIncrementMap = null;
                //���и��û���ָ���ļƻ���   ��ͬ����ֱ�ӱȽϣ���ͬ����ת��Ϊһ�����ֱȽ�
                Map<String, BigDecimal> upcombIncrementMap = null;

                if (combLevelTotal == 1 && combLevelOne ==1) {
                    // ����->һ������  һ��->һ������
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                } else if (combLevelTotal == 1 && combLevelOne ==2) {
                    // ����->һ������  һ��->��������
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMapAndTransCombLevel(tbPlanadjDetailList, combLevelOne);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                } else if (combLevelTotal == 2 && combLevelOne ==1) {
                    // ����->��������  һ��->һ������
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMapAndTransCombLevel(planMonth, uporgan, organCode,combLevelTotal);

                } else if (combLevelTotal == 2 && combLevelOne ==2) {
                    // ����->��������  һ��->��������
                    realUpcombIncrementMap = tbPlanService.getPlanadjCombMap(tbPlanadjDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                }

                //�Ƚϼƻ����ƶ��ľ�����
                for (String upcombCode : upcombIncrementMap.keySet()) {
                    //���ʵ���ƶ��������ڼƻ�������������ʧ��
                    if (getSafeCount(realUpcombIncrementMap.get(upcombCode)).compareTo(getSafeCount(upcombIncrementMap.get(upcombCode))) != 0) {
                        HashMap<String, Object> querymap = new HashMap<>();
                        querymap.put("combcode", upcombCode);
                        List<Map<String, Object>> combs = loanCombService.selectCombBycombcode(querymap);
                        String combname = combs.get(0).get("combname").toString();

                        resultMap.put("code", "false");
                        resultMap.put("msg", "¼��ƻ�����ʧ�ܣ�" +combname+"�ƶ�������Ϊ"+realUpcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000"))+"��Ԫ���ƻ�������Ϊ"
                                +upcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000"))+"��Ԫ���������");

                        return resultMap;
                    }
                }

            }

            planadjDetailMapper.deleteByWhere("planadjd_ref_id = \'" + planadjId + "\'");
            planadjDetailMapper.insertBatch(tbPlanadjDetailList);

            planadjMapper.deleteByPK(planadjId);
            planadjMapper.insertEntity(tbPlanadj);


            //���ɾ���ļ�
            delFile(fileExcel);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("¼��ʧ��");
        }finally {
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

    /*�����ƻ�����*/
    @Override
    public void downloadPlan(HttpServletResponse response, HttpServletRequest request, String organlevel) throws Exception{
        // ��ȡ�����
        OutputStream os = response.getOutputStream();

        //����������
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        //������ʽ
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


        /*��ѯ��������*/
        List<Map<String, Object>> organList = fdOrganService.selectByOrganCode(getSessionOrgan().getThiscode());

        /*��ѯ�������� ���������*/
        int combLevel = 2;
        //��ѯ�������
        Map<String, Object> combMapParam = new HashMap<String, Object>();
        combMapParam.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMapParam);

        /*��ѯ����������*/
        String planadjId = request.getParameter("planadjId");
        TbPlanadj tbPlanadjParam = new TbPlanadj();
        tbPlanadjParam.setPlanadjId(planadjId);
        TbPlanadj tbPlanadj = planadjMapper.selectByAttr(tbPlanadjParam).get(0);
        TbPlanadjDetail tbPlanadjDetailParam = new TbPlanadjDetail();
        tbPlanadjDetailParam.setPlanadjdRefId(planadjId);
        List<TbPlanadjDetail> tbPlanadjDetailList = planadjDetailMapper.selectByAttr(tbPlanadjDetailParam);


        /*��װ����*/
        //�ƻ�����
        Map<String,TbPlanadjDetail> planadjMap=new HashMap<>();
        for(TbPlanadjDetail tbPlanadjDetail:tbPlanadjDetailList){
            //��Ԫת��Ԫ
            if (tbPlanadj.getPlanadjUnit() == 2) {
                tbPlanadjDetail.setPlanadjdReqAmount(tbPlanadjDetail.getPlanadjdReqAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdInitAmount(tbPlanadjDetail.getPlanadjdInitAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjAmount(tbPlanadjDetail.getPlanadjdAdjAmount().divide(new BigDecimal("10000")));
                tbPlanadjDetail.setPlanadjdAdjedAmount(tbPlanadjDetail.getPlanadjdAdjedAmount().divide(new BigDecimal("10000")));
            }
            planadjMap.put(tbPlanadjDetail.getPlanadjdOrgan()+"_"+tbPlanadjDetail.getPlanadjdLoanType(),tbPlanadjDetail);
        }
        //����
        HashMap<String, Integer> organMap = new HashMap<>();
        for (int i = 0; i < organList.size(); i++) {
            Map<String, Object> map = organList.get(i);
            organMap.put(map.get("organcode").toString(), 4 + i);
        }
        //����
        HashMap<String, Integer> combMap = new HashMap<>();
        for (int i = 0; i <combList.size() ; i++) {
            Map<String, Object> map = combList.get(i);
            combMap.put(map.get("combcode").toString(), 1 + i);
        }

        /*д���ļ�*/

        String filename = getSessionOrgan().getOrganname() + "-" + tbPlanadj.getPlanadjMonth() + "-�������ߵ����ƻ�";
        //��ͷ
        POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, combList.size()*4);
        POIExportUtil.setCell(sheet, 0, 0, filename, cellStyle);
        //��λ
        POIExportUtil.setCell(sheet, 1, 0, "��λ:" , null);
        POIExportUtil.setCell(sheet, 1, 1, tbPlanadj.getPlanadjUnit()==1?"��Ԫ":"��Ԫ" , cellStyle);

        //����
        POIExportUtil.CellRangeAddress(sheet, 2, 3, 0,0);
        POIExportUtil.setCell(sheet, 2, 0, "����", cellStyle);
        for (Map<String, Object> map : organList) {
            Integer row = organMap.get(map.get("organcode"));
            if (row != null) {
                POIExportUtil.setCell(sheet, row, 0, map.get("organname").toString(), cellStyle);
            }
        }
        //����
        for (Map<String, Object> map : combList) {
            Integer column = combMap.get(map.get("combcode"));
            if (column != null) {
                POIExportUtil.CellRangeAddress(sheet, 2, 2, (column-1)*4+1, (column-1)*4+4);
                POIExportUtil.setCell(sheet, 2, (column-1)*4+1, map.get("combname").toString(), cellStyle);
                POIExportUtil.setCell(sheet, 3, (column-1)*4+1, "ԭ�ƻ�", cellStyle);
                POIExportUtil.setCell(sheet, 3, (column-1)*4+2, "��������", cellStyle);
                POIExportUtil.setCell(sheet, 3, (column-1)*4+3, "������", cellStyle);
                POIExportUtil.setCell(sheet, 3, (column-1)*4+4, "������ƻ�", cellStyle);
            }
        }
        //����
        for (String key : planadjMap.keySet()) {
            String[] keys = key.split("_");
            Integer row =organMap.get(keys[0]);
            Integer column = combMap.get(keys[1]);
            if (row != null && column != null) {
                POIExportUtil.setCell(sheet, row, (column-1)*4+1, planadjMap.get(key).getPlanadjdInitAmount(), cellStyle);
                POIExportUtil.setCell(sheet, row, (column-1)*4+2, planadjMap.get(key).getPlanadjdReqAmount(), cellStyle);
                POIExportUtil.setCell(sheet, row, (column-1)*4+3, planadjMap.get(key).getPlanadjdAdjAmount(), cellStyle);
                POIExportUtil.setCell(sheet, row, (column-1)*4+4, planadjMap.get(key).getPlanadjdAdjedAmount(), cellStyle);
            }
        }

        //�����п�
        for (int i = 0; i <combList.size()*4+1 ; i++) {
            POIExportUtil.setCellWidth(sheet,i);
        }

        //��������
        POIExportUtil.createFreezePane(sheet,1,4);

        //�ļ���
         filename = filename + ".xls";
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            //win10 edge ie ������ϵͳ��ie�����
            filename = URLEncoder.encode(filename, "UTF-8");
        } else {
            filename = new String(filename.getBytes("utf-8"), "iso-8859-1");

        }
//         ���response
        response.reset();
//        response.setHeader("Content-Type", "application/msexcel");
        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", filename));
        response.setContentType("application/octet-stream");

        wb.write(os);
        os.flush();
        os.close();
    }

    /*�ƻ�����--��ѯ�ƻ�ֵ*/
    private Map<String, Object> getCreditPlanDetail2(String month)throws Exception {
        HashMap<String, Object> param = new HashMap<>();
        param.put("organ", getSessionOrgan().getThiscode());
        param.put("month", month);
        param.put("planType", TbPlan.STRIPE);
        List<Map<String,Object>> list=planadjMapper.getCreditPlanDetail(param);
        Map<String, Object> resultMap=new HashMap<>();
        for(Map<String,Object> map:list){
            String key=map.get("organcode")+"_"+map.get("loantype");
            resultMap.put(key,map.get("amount"));
        }

        return resultMap;
    }

    /*�ƻ�����--��ѯ����ֵ*/
    private Map<String, Object> getReqDetail2(String month) throws Exception{

        //��ȡ��������
        Map<String, BigDecimal> reqMap = tbReqresetListService.getLineReqResetDetailList(month, getSessionOrgan().getThiscode());

        HashMap<String, Object> resultMap = new HashMap<>();
        for (String key : reqMap.keySet()) {
            resultMap.put(key, reqMap.get(key));
        }

        return resultMap;
    }


    private void deleteMsg( String planadjId) throws Exception {

        //ɾ����ǰС����
        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("�������ߵ����ƻ�����", "MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("�������ߵ����ƻ�������" + planadjId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }

    }

    private void saveMsg(String msgNo, String operCode, String assignee, String msgUrl, String planadjId) throws Exception {

        //ɾ����ǰС����
        WebMsg webMsg=new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("�������ߵ����ƻ�����","MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("�������ߵ����ƻ�������"+planadjId);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }


        WebMsg msg=new WebMsg();
        msg.setMsgNo(msgNo);
        msg.setAppDate(BocoUtils.getNowDate());
        msg.setAppUser(WebContextUtil.getSessionUser().getOpercode());
        // msg.setAppOperName(WebContextUtil.getSessionUser().getOpername());

        //��ȡ��ǰ����������������״̬
        List<Comment> comments = BocoUtils.translateComments(workFlowService.getTaskComments(msgUrl.substring(msgUrl.lastIndexOf("=")+1)), "");
        Comment comment1 = comments.get(comments.size() - 2);
        String operNameAndStatus = comment1.getUserId() + ":" + comment1.getType();
        msg.setAppOperName(operNameAndStatus);

        msg.setAppRoleName("");
        msg.setAppOrganCode(getSessionOrgan().getThiscode());
        msg.setAppOrganName(getSessionOrgan().getOrganname());
        msg.setAppTime(BocoUtils.getNowTime());
        msg.setMsgType(DicCache.getKeyByName_("�������ߵ����ƻ�����","MSG_TYPE"));

        TbPlanadj tbPlanadj = planadjMapper.selectByPK(planadjId);
        BigDecimal adjIncrement = tbPlanadj.getPlanadjAdjAmount();
        String unit = "��Ԫ";
        if (tbPlanadj.getPlanadjUnit().intValue() == 2) {
            unit = "��Ԫ";
            adjIncrement = adjIncrement.divide(new BigDecimal("10000"));
        }
        String operName = "�����·ݣ�" + tbPlanadj.getPlanadjMonth()
                + " �ƻ���������" + adjIncrement.toPlainString() + unit;

        msg.setOperName(operName);
        msg.setOperNo(operCode);
        msg.setRepUserCode(assignee);
        msg.setWebMsgStatus("1");
        msg.setMsgUrl(msgUrl);
        msg.setOperDescribe("�������ߵ����ƻ�������"+planadjId);
        webMsgService.insertEntity(msg);
    }

    //�����ϼ����ּ��ܵļƻ��ƶ���
    private Map<String, BigDecimal> comparePlanadjIncrement(String organlevel, ArrayList<TbPlanadjDetail> tbPlanadjDetails, String planMonth)throws Exception {
        //1.���ּ������ܽ��  <combcode,amount>
        HashMap<String, BigDecimal> combAmountMap = new HashMap<>();
        for (TbPlanadjDetail tbPlanadjDetail : tbPlanadjDetails) {
            String amount = combAmountMap.get(tbPlanadjDetail.getPlanadjdLoanType()) == null ? "0" : combAmountMap.get(tbPlanadjDetail.getPlanadjdLoanType()).toString();
            combAmountMap.put(tbPlanadjDetail.getPlanadjdLoanType(), new BigDecimal(amount).add(tbPlanadjDetail.getPlanadjdAdjedAmount()));
        }

        //2.���ֵ��ϼ����� <combcode,upcombcode>  ��ѯ��������
        HashMap<String, String> combAndUpcombMap = new HashMap<>();
        int combLevel = 0;
        try {
            combLevel = Integer.valueOf(organlevel).intValue() + 1;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);
        for (Map<String, Object> map : combList) {
            combAndUpcombMap.put(map.get("combcode").toString(), map.get("upcombcode").toString());
        }

        //3.�����ϼ����ּ��ܵļƻ��ƶ���
        HashMap<String, BigDecimal> realUpcombIncrementMap = new HashMap<>();
        for (String comb : combAmountMap.keySet()) {
            String upcombCode = combAndUpcombMap.get(comb);
            BigDecimal realAmountCount = realUpcombIncrementMap.get(upcombCode) == null ? new BigDecimal("0") : realUpcombIncrementMap.get(upcombCode);
            realUpcombIncrementMap.put(upcombCode, realAmountCount.add(combAmountMap.get(comb)));
        }
        return realUpcombIncrementMap;
    }

    /*ɾ���ļ�*/
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

    /*�ҳ��ַ���[]���������*/
    private  String findCode(String str) {
        String code = "";
        try {
            int begin = str.indexOf("[");
            int end = str.indexOf("]");
            code = str.substring(begin+1, end);
        } catch (Exception e) {
            e.printStackTrace();
            code = "";
        }
        return  code;
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

    //�����ϼ����ּ��ܵļƻ��ƶ���
    private Map<String, BigDecimal> comparePlanIncrement(ArrayList<TbPlanadjDetail> tbPlanadjDetailList, int combLevel) {
        //1.���ּ������ܽ��  <combcode,amount>
        HashMap<String, BigDecimal> combAmountMap = new HashMap<>();
        for (TbPlanadjDetail tbPlanadjDetail: tbPlanadjDetailList) {
            String amount = combAmountMap.get(tbPlanadjDetail.getPlanadjdLoanType()) == null ? "0" : combAmountMap.get(tbPlanadjDetail.getPlanadjdLoanType()).toString();
            combAmountMap.put(tbPlanadjDetail.getPlanadjdLoanType(), new BigDecimal(amount).add(tbPlanadjDetail.getPlanadjdAdjedAmount()));
        }

        //2.���ֵ��ϼ����� <combcode,upcombcode>
        HashMap<String, String> combAndUpcombMap = new HashMap<>();
        //��ѯ�������
        Map<String, Object> combMap = new HashMap<String, Object>();
        combMap.put("combLevel", combLevel);
        List<Map<String, Object>> combList = loanCombService.selectCombOfBind(combMap);
        for (Map<String, Object> map : combList) {
            combAndUpcombMap.put(map.get("combcode").toString(), map.get("upcombcode").toString());
        }

        //3.�����ϼ����ּ��ܵļƻ��ƶ���
        HashMap<String, BigDecimal> realUpcombIncrementMap = new HashMap<>();
        for (String comb : combAmountMap.keySet()) {
            String upcombCode = combAndUpcombMap.get(comb);
            BigDecimal realAmountCount = realUpcombIncrementMap.get(upcombCode) == null ? new BigDecimal("0") : realUpcombIncrementMap.get(upcombCode);
            realUpcombIncrementMap.put(upcombCode, realAmountCount.add(combAmountMap.get(comb)));
        }
        return realUpcombIncrementMap;
    }


}