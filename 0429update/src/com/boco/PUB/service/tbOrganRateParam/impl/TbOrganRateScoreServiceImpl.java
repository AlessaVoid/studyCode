package com.boco.PUB.service.tbOrganRateParam.impl;

import com.boco.GF.service.IWebTaskRoleInfoNewService;
import com.boco.PM.service.IFdOrganService;
import com.boco.PM.service.IWebMsgService;
import com.boco.PUB.POJO.DO.OrganRateParamElementType;
import com.boco.PUB.POJO.DO.OrganRateParamQueryDO;
import com.boco.PUB.service.ITbReqDetailService;
import com.boco.PUB.service.ITbReqListService;
import com.boco.PUB.service.tbOrganRateParam.OrganRateParamCalculateService;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateScoreService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.entity.*;
import com.boco.SYS.mapper.*;
import com.boco.SYS.service.IWorkFlowService;
import com.boco.SYS.util.BigDecimalUtil;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Pager;
import com.boco.SYS.util.WebContextUtil;
import com.boco.TONY.common.AuditMix;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.utils.IDGeneratorUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.boco.SYS.util.WebContextUtil.getSessionOrgan;

/**
 * 机构评分批次表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * <p>
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class TbOrganRateScoreServiceImpl extends GenericService<TbOrganRateScore, String> implements TbOrganRateScoreService {

    //有自定义方法时使用
    @Autowired
    private TbOrganRateScoreMapper tbOrganRateScoreMapper;
    @Autowired
    private TbOrganRateScoreMonthDetailMapper tbOrganRateScoreMonthDetailMapper;
    @Autowired
    private TbOrganRateScoreQuarterDetailMapper tbOrganRateScoreQuarterDetailMapper;
    @Autowired
    private OrganRateParamCalculateService organRateParamCalculateService;
    @Autowired
    private IFdOrganService fdOrganService;
    @Autowired
    private TbOrganRateParamMapper organRateParamMapper;
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
    private TbPunishDetailMapper tbPunishDetailMapper;
    @Autowired
    private ITbReqListService tbReqListService;
    @Autowired
    private ITbReqDetailService tbReqDetailService;
    @Autowired
    private TbRptBaseinfoMapper tbRptBaseinfoMapper;
    @Autowired
    private FdOrganMapper fdOrganMapper;
    @Autowired
    private TbPlanadjMapper tbPlanadjMapper;
    @Autowired
    private TbPlanadjDetailMapper tbPlanadjDetailMapper;
    @Autowired
    private TbPlanMapper tbPlanMapper;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;
    @Autowired
    private TbPlanDetailBackupMapper tbPlanDetailBackupMapper;
    @Autowired
    private TbOverMapper tbOverMapper;
    @Autowired
    private TbSingleMapper tbSingleMapper;
    @Autowired
    private TbOrganRateScoreMonthDetailBackupMapper tbOrganRateScoreMonthDetailBackupMapper;
    @Autowired
    private TbOrganRateScoreQuarterDetailBackupMapper   tbOrganRateScoreQuarterDetailBackupMapper;
    @Autowired
    private TbOrganRateScoreImportDataMapper tbOrganRateScoreImportDataMapper;


    /*生成月度评分*/
    @Override

    public void addMonthOragnRateScore() throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m1 = c.getTime();
        String month = format.format(m1);

        //判断当前月度评分存在不
        TbOrganRateScore tbOrganRateScoreQuery = new TbOrganRateScore();
        tbOrganRateScoreQuery.setRateMonth(month);
        tbOrganRateScoreQuery.setRateType(OrganRateParamElementType.RATE_MONTH);
        List<TbOrganRateScore> tbOrganRateScores = tbOrganRateScoreMapper.selectByAttr(tbOrganRateScoreQuery);

        if (tbOrganRateScores != null && tbOrganRateScores.size() != 0) {
            TbOrganRateScore tbOrganRateScore = tbOrganRateScores.get(0);
            //存在的情况下，如果是未提交或者驳回状态，进行覆盖，并且保留历史记录
            if (tbOrganRateScore.getRateStatus() == TbReqDetail.STATE_NEW || tbOrganRateScore.getRateStatus() == TbReqDetail.STATE_DISMISSED) {

                //查询所有明细
                TbOrganRateScoreMonthDetail tbOrganRateScoreMonthDetailQuery = new TbOrganRateScoreMonthDetail();
                tbOrganRateScoreMonthDetailQuery.setRefId(tbOrganRateScore.getId());
                List<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetails = tbOrganRateScoreMonthDetailMapper.selectByAttr(tbOrganRateScoreMonthDetailQuery);
                // 查询历史编号值
                List<Map<String,Object>> backupList = tbOrganRateScoreMonthDetailBackupMapper.selectMaxHistoryNumber(tbOrganRateScore.getRateMonth());
                BigDecimal number = BigDecimal.ONE;
                if (backupList.get(0) != null ) {
                    String numberStr = backupList.get(0).get("maxnumber").toString() == null ? "0" : backupList.get(0).get("maxnumber").toString();
                    BigDecimal maxnumber = new BigDecimal(numberStr);
                    number = number.add(maxnumber);
                }
                // 构建历史数据
                List<TbOrganRateScoreMonthDetailBackup> monthDetailBackupList = buildMonthHistory(tbOrganRateScoreMonthDetails, number);
                //插入历史数据表
                tbOrganRateScoreMonthDetailBackupMapper.insertBatch(monthDetailBackupList);


                //删除所有明细
                tbOrganRateScoreMonthDetailMapper.deleteByWhere("ref_id = \'" + tbOrganRateScore.getId() + "\'");
                //生成评分参数list
                ArrayList<OrganRateParamQueryDO> organRateParamQueryDOList = getOrganRateParamQueryDOList(month);
                //生成月度评分详情
                ArrayList<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetailList = getTbOrganRateScoreMonthDetailList(organRateParamQueryDOList, tbOrganRateScore.getId());

                //更新批次表
                tbOrganRateScore.setUpdateTime(BocoUtils.getTime());
                tbOrganRateScoreMapper.updateByPK(tbOrganRateScore);

                //插入月度详情表
                tbOrganRateScoreMonthDetailMapper.insertBatch(tbOrganRateScoreMonthDetailList);

            } else {
                return;
            }
        } else {

            //生成评分参数list
            ArrayList<OrganRateParamQueryDO> organRateParamQueryDOList = getOrganRateParamQueryDOList(month);

            OrganRateParamQueryDO organRateParamQueryDO = organRateParamQueryDOList.get(0);
            //生成评分批次
            String tbOrganRateScoreId = IDGeneratorUtils.getSequence();
            TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
            tbOrganRateScore.setId(tbOrganRateScoreId);
            tbOrganRateScore.setRateMonth(organRateParamQueryDO.getMonth());
            tbOrganRateScore.setRateOrgan(getSessionOrgan().getThiscode());
            tbOrganRateScore.setRateType(OrganRateParamElementType.RATE_MONTH);
            tbOrganRateScore.setRateStatus(TbReqDetail.STATE_NEW);
            tbOrganRateScore.setReportStatus(TbReqDetail.STATE_NEW);
            tbOrganRateScore.setCreateTime(BocoUtils.getTime());
            tbOrganRateScore.setUpdateTime(BocoUtils.getTime());

            //生成月度评分详情
            ArrayList<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetailList = getTbOrganRateScoreMonthDetailList(organRateParamQueryDOList, tbOrganRateScoreId);

            //插入批次表
            tbOrganRateScoreMapper.insertEntity(tbOrganRateScore);
            //插入月度详情表
            tbOrganRateScoreMonthDetailMapper.insertBatch(tbOrganRateScoreMonthDetailList);

        }


    }

    //构建月度历史数据
    private List<TbOrganRateScoreMonthDetailBackup> buildMonthHistory(List<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetails, BigDecimal number) {
        List<TbOrganRateScoreMonthDetailBackup> backupList = new ArrayList<>();
        for (TbOrganRateScoreMonthDetail detail : tbOrganRateScoreMonthDetails) {
            TbOrganRateScoreMonthDetailBackup backup = new TbOrganRateScoreMonthDetailBackup();
            backup.setId(IDGeneratorUtils.getSequence());
            backup.setRefId(detail.getRefId());
            backup.setRateMonth(detail.getRateMonth());
            backup.setRateOrgan(detail.getRateOrgan());
            backup.setRateScoreMonth(detail.getRateScoreMonth());

            backup.setBadConditionScore(detail.getBadConditionScore());
            backup.setBadConditionRatioMonthEnd(detail.getBadConditionRatioMonthEnd());
            backup.setBadConditionRatioYearBegin(detail.getBadConditionRatioYearBegin());
            backup.setBadConditionRatioChange(detail.getBadConditionRatioChange());
            backup.setDepositLoanRatioScore(detail.getDepositLoanRatioScore());
            backup.setLoanYearAdd(detail.getLoanYearAdd());
            backup.setPersonalDepositYearIncrement(detail.getPersonalDepositYearIncrement());
            backup.setCompanyDepositYearIncrement(detail.getCompanyDepositYearIncrement());
            backup.setDepositLoanRatioChange(detail.getDepositLoanRatioChange());
            backup.setNewLoanRatioScore(detail.getNewLoanRatioScore());
            backup.setNewLoanRatio(detail.getNewLoanRatio());
            backup.setBankAverageLoanRatio(detail.getBankAverageLoanRatio());
            backup.setNewLoanRatioChange(detail.getNewLoanRatioChange());
            backup.setLoanStructScore(detail.getLoanStructScore());
            backup.setLoanCount(detail.getLoanCount());
            backup.setMonthBeginEntityLoan(detail.getMonthBeginEntityLoan());
            backup.setMonthEndEntityLoan(detail.getMonthEndEntityLoan());
            backup.setMonthBeginEntityLoanChange(detail.getMonthBeginEntityLoanChange());
            backup.setMonthEndEntityLoanChange(detail.getMonthEndEntityLoanChange());
            backup.setMonthEndBeginEntityLoanChange(detail.getMonthEndBeginEntityLoanChange());
            backup.setScaleAmountScore(detail.getScaleAmountScore());
            backup.setOrganScaleAmount(detail.getOrganScaleAmount());
            backup.setBankScaleAmount(detail.getBankScaleAmount());
            backup.setScaleAmountChange(detail.getScaleAmountChange());
            backup.setPlanSubmitDeviationScore(detail.getPlanSubmitDeviationScore());
            backup.setMonthEndIncrement(detail.getMonthEndIncrement());
            backup.setMonthBeginReport(detail.getMonthBeginReport());
            backup.setPlanSubmitDeviationChange(detail.getPlanSubmitDeviationChange());
            backup.setPlanExecuteDeviationMonthMidScore(detail.getPlanExecuteDeviationMonthMidScore());
            backup.setMonthMidPlan(detail.getMonthMidPlan());
            backup.setMonthEndPlan(detail.getMonthEndPlan());
            backup.setPlanExecuteDeviationMonthMidChange(detail.getPlanExecuteDeviationMonthMidChange());
            backup.setPlanExecuteDeviationMonthInitScore(detail.getPlanExecuteDeviationMonthInitScore());
            backup.setMonthBeginPlan(detail.getMonthBeginPlan());
            backup.setPlanExecuteDeviationMonthInitChange(detail.getPlanExecuteDeviationMonthInitChange());
            backup.setAdjCountScore(detail.getAdjCountScore());
            backup.setAdjCount(detail.getAdjCount());
            backup.setCreateTime(detail.getCreateTime());
            backup.setUpdateTime(detail.getUpdateTime());
            backup.setUpdateOper(detail.getUpdateOper());
            backup.setAddScore(detail.getAddScore());
            backup.setRemark(detail.getRemark());
            backup.setHistoryNumber(number);

            backupList.add(backup);

        }
        return backupList;
    }

    /*生成季度评分*/
    @Override
    public void addQuarterOrganRateScore() throws Exception{
        //判断当前季度评分是否存在
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMM");
        String month = format1.format(new Date());
        String mon = month.substring(4, 6);
        String year = month.substring(0, 4);
        if ("01".equals(mon) || "02".equals(mon) || "03".equals(mon)) {

            Integer ye = Integer.valueOf(month.substring(0, 4))-1;
            month = ye + "12";
        } else if ("04".equals(mon) || "05".equals(mon) || "06".equals(mon)) {
            month = year + "03";
        }else if ("07".equals(mon) || "08".equals(mon) || "09".equals(mon)) {
            month = year + "06";
        }else if ("10".equals(mon) || "11".equals(mon) || "12".equals(mon)) {
            month = year + "09";
        }


        TbOrganRateScore tbOrganRateScoreQuery = new TbOrganRateScore();
        tbOrganRateScoreQuery.setRateMonth(month);
        tbOrganRateScoreQuery.setRateType(OrganRateParamElementType.RATE_QUARTER);
        List<TbOrganRateScore> tbOrganRateScores = tbOrganRateScoreMapper.selectByAttr(tbOrganRateScoreQuery);
        if (tbOrganRateScores != null && tbOrganRateScores.size() != 0) {
            TbOrganRateScore tbOrganRateScore = tbOrganRateScores.get(0);
            //存在的情况下，如果是未提交或者驳回状态，进行覆盖,并且记录历史
            if (tbOrganRateScore.getRateStatus() == TbReqDetail.STATE_NEW || tbOrganRateScore.getRateStatus() == TbReqDetail.STATE_DISMISSED) {
                //查询所有明细
                TbOrganRateScoreQuarterDetail tbOrganRateScoreQuarterDetailQuery = new TbOrganRateScoreQuarterDetail();
                tbOrganRateScoreQuarterDetailQuery.setRefId(tbOrganRateScore.getId());
                List<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetails = tbOrganRateScoreQuarterDetailMapper.selectByAttr(tbOrganRateScoreQuarterDetailQuery);
                // 查询历史编号值
                List<Map<String,Object>> backupList = tbOrganRateScoreQuarterDetailBackupMapper.selectMaxHistoryNumber(tbOrganRateScore.getRateMonth());
                BigDecimal number = BigDecimal.ONE;
                if (backupList.get(0) != null ) {
                    String numberStr = backupList.get(0).get("maxnumber").toString() == null ? "0" : backupList.get(0).get("maxnumber").toString();
                    BigDecimal maxnumber = new BigDecimal(numberStr);
                    number = number.add(maxnumber);
                }
                // 构建历史数据
                List<TbOrganRateScoreQuarterDetailBackup> quarterDetailBackupList = buildQuarterHistory(tbOrganRateScoreQuarterDetails, number);
                //插入历史数据表
                tbOrganRateScoreQuarterDetailBackupMapper.insertBatch(quarterDetailBackupList);

                //删除所有明细
                tbOrganRateScoreQuarterDetailMapper.deleteByWhere("ref_id = \'" + tbOrganRateScore.getId() + "\'");
                //获取评分参数 并且计算参数
                List<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetailList = getTbOrganRateScoreQuarterDetailList(tbOrganRateScore.getId(),month);
                //计算机构评级，全行排名，占全行比例
                BigDecimal organCount = new BigDecimal(tbOrganRateScoreQuarterDetailList.size() + "");
                BigDecimal grade = new BigDecimal("1");
                for (TbOrganRateScoreQuarterDetail organRateScoreQuarterDetail : tbOrganRateScoreQuarterDetailList) {
                    organRateScoreQuarterDetail.setRateRank(grade);
                    organRateScoreQuarterDetail.setRateRatio(BigDecimalUtil.divide(grade, organCount));
                    BigDecimal quarterLevel = organRateParamCalculateService.getQuarterLevel(grade, organCount);
                    organRateScoreQuarterDetail.setRateLevel(quarterLevel);
                    grade = grade.add(new BigDecimal("1"));

                }

                //更新批次表
                tbOrganRateScore.setUpdateTime(BocoUtils.getTime());
                tbOrganRateScoreMapper.updateByPK(tbOrganRateScore);

                //插入季度详情表
                tbOrganRateScoreQuarterDetailMapper.insertBatch(tbOrganRateScoreQuarterDetailList);

            } else {
                return;
            }
        } else {

            String tbOrganRateScoreId = IDGeneratorUtils.getSequence();

            //获取评分参数 并且计算参数
            List<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetailList = getTbOrganRateScoreQuarterDetailList(tbOrganRateScoreId, month);
            //计算机构评级，全行排名，占全行比例
            BigDecimal organCount = new BigDecimal(tbOrganRateScoreQuarterDetailList.size() + "");
            BigDecimal grade = new BigDecimal("1");
            for (TbOrganRateScoreQuarterDetail organRateScoreQuarterDetail : tbOrganRateScoreQuarterDetailList) {
                organRateScoreQuarterDetail.setRateRank(grade);
                organRateScoreQuarterDetail.setRateRatio(BigDecimalUtil.divide(grade, organCount));
                BigDecimal quarterLevel = organRateParamCalculateService.getQuarterLevel(grade, organCount);
                organRateScoreQuarterDetail.setRateLevel(quarterLevel);
                grade = grade.add(new BigDecimal("1"));

            }


            //生成批次
            TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
            tbOrganRateScore.setId(tbOrganRateScoreId);
            tbOrganRateScore.setRateMonth(tbOrganRateScoreQuarterDetailList.get(0).getRateMonth());
            tbOrganRateScore.setRateOrgan(getSessionOrgan().getThiscode());
            tbOrganRateScore.setRateType(OrganRateParamElementType.RATE_QUARTER);
            tbOrganRateScore.setRateStatus(TbReqDetail.STATE_NEW);
            tbOrganRateScore.setReportStatus(TbReqDetail.STATE_NEW);
            tbOrganRateScore.setCreateTime(BocoUtils.getTime());
            tbOrganRateScore.setUpdateTime(BocoUtils.getTime());

            //插入批次表
            tbOrganRateScoreMapper.insertEntity(tbOrganRateScore);

            //插入季度详情表
            tbOrganRateScoreQuarterDetailMapper.insertBatch(tbOrganRateScoreQuarterDetailList);

        }


    }

    //构建季度历史数据
    private List<TbOrganRateScoreQuarterDetailBackup> buildQuarterHistory(List<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetails, BigDecimal number) {
        ArrayList<TbOrganRateScoreQuarterDetailBackup> backups = new ArrayList<>();
        for (TbOrganRateScoreQuarterDetail detail : tbOrganRateScoreQuarterDetails) {
            TbOrganRateScoreQuarterDetailBackup backup = new TbOrganRateScoreQuarterDetailBackup();
            backup.setId(IDGeneratorUtils.getSequence());
            backup.setRefId(detail.getRefId());
            backup.setRateMonth(detail.getRateMonth());
            backup.setRateOrgan(detail.getRateOrgan());
            backup.setRateScoreQuarter(detail.getRateScoreQuarter());
            backup.setRateRank(detail.getRateRank());
            backup.setRateRatio(detail.getRateRatio());
            backup.setRateLevel(detail.getRateLevel());
            backup.setBadConditionScoreQuarter(detail.getBadConditionScoreQuarter());
            backup.setBadConditionScoreOne(detail.getBadConditionScoreOne());
            backup.setBadConditionScoreTwo(detail.getBadConditionScoreTwo());
            backup.setBadConditionScoreThree(detail.getBadConditionScoreThree());
            backup.setDepositLoanRatioScoreQuarter(detail.getDepositLoanRatioScoreQuarter());
            backup.setDepositLoanRatioScoreOne(detail.getDepositLoanRatioScoreOne());
            backup.setDepositLoanRatioScoreTwo(detail.getDepositLoanRatioScoreTwo());
            backup.setDepositLoanRatioScoreThree(detail.getDepositLoanRatioScoreThree());
            backup.setNewLoanRatioScoreQuarter(detail.getNewLoanRatioScoreQuarter());
            backup.setNewLoanRatioScoreOne(detail.getNewLoanRatioScoreOne());
            backup.setNewLoanRatioScoreTwo(detail.getNewLoanRatioScoreTwo());
            backup.setNewLoanRatioScoreThree(detail.getNewLoanRatioScoreThree());
            backup.setLoanStructScoreQuarter(detail.getLoanStructScoreQuarter());
            backup.setLoanStructScoreOne(detail.getLoanStructScoreOne());
            backup.setLoanStructScoreTwo(detail.getLoanStructScoreTwo());
            backup.setLoanStructScoreThree(detail.getLoanStructScoreThree());
            backup.setScaleAmountScoreQuarter(detail.getScaleAmountScoreQuarter());
            backup.setScaleAmountScoreOne(detail.getScaleAmountScoreOne());
            backup.setScaleAmountScoreTwo(detail.getScaleAmountScoreTwo());
            backup.setScaleAmountScoreThree(detail.getScaleAmountScoreThree());
            backup.setPlanSubmitDeviationScoreQuarter(detail.getPlanSubmitDeviationScoreQuarter());
            backup.setPlanSubmitDeviationScoreOne(detail.getPlanSubmitDeviationScoreOne());
            backup.setPlanSubmitDeviationScoreTwo(detail.getPlanSubmitDeviationScoreTwo());
            backup.setPlanSubmitDeviationScoreThree(detail.getPlanSubmitDeviationScoreThree());
            backup.setPlanExecuteDeviationMonthMidScoreQuarter(detail.getPlanExecuteDeviationMonthMidScoreQuarter());
            backup.setPlanExecuteDeviationMonthMidScoreOne(detail.getPlanExecuteDeviationMonthMidScoreOne());
            backup.setPlanExecuteDeviationMonthMidScoreTwo(detail.getPlanExecuteDeviationMonthMidScoreTwo());
            backup.setPlanExecuteDeviationMonthMidScoreThree(detail.getPlanExecuteDeviationMonthMidScoreThree());
            backup.setPlanExecuteDeviationMonthInitScoreQuarter(detail.getPlanExecuteDeviationMonthInitScoreQuarter());
            backup.setPlanExecuteDeviationMonthInitScoreOne(detail.getPlanExecuteDeviationMonthInitScoreOne());
            backup.setPlanExecuteDeviationMonthInitScoreTwo(detail.getPlanExecuteDeviationMonthInitScoreTwo());
            backup.setPlanExecuteDeviationMonthInitScoreThree(detail.getPlanExecuteDeviationMonthInitScoreThree());
            backup.setAdjCountScoreQuarter(detail.getAdjCountScoreQuarter());
            backup.setAdjCountScoreOne(detail.getAdjCountScoreOne());
            backup.setAdjCountScoreTwo(detail.getAdjCountScoreTwo());
            backup.setAdjCountScoreThree(detail.getAdjCountScoreThree());
            backup.setScaleAmountMonthCountScoreQuarter(detail.getScaleAmountMonthCountScoreQuarter());
            backup.setScaleAmountMonthCount(detail.getScaleAmountMonthCount());
            backup.setAdjCountMonthCountScoreQuarter(detail.getAdjCountMonthCountScoreQuarter());
            backup.setAdjCountMonthCount(detail.getAdjCountMonthCount());
            backup.setGoodIdeaScore(detail.getGoodIdeaScore());
            backup.setGoodJobScore(detail.getGoodJobScore());
            backup.setCreateTime(detail.getCreateTime());
            backup.setUpdateTime(detail.getUpdateTime());
            backup.setUpdateOper(detail.getUpdateOper());
            backup.setAddScore(detail.getAddScore());
            backup.setRemark(detail.getRemark());
            backup.setAddScoreQuarter(detail.getAddScoreQuarter());
            backup.setAddScoreOne(detail.getAddScoreOne());
            backup.setAddScoreTwo(detail.getAddScoreTwo());
            backup.setAddScoreThree(detail.getAddScoreThree());
            backup.setHistoryNumber(number);
            backups.add(backup);

        }
        return backups;
    }

    /*修改月度评分*/
    @Override
    public PlainResult<String> updateTbOrganRateScoreMonth(HttpServletRequest request, String operCode, String organCode) throws Exception {
        PlainResult<String> result = new PlainResult<>();

        try {
            String rateSorceDetailStr = request.getParameter("rateSorceDetail");
            String id = request.getParameter("id");

            //校验特殊字段非空
            if (StringUtils.isEmpty(id) || StringUtils.isEmpty(rateSorceDetailStr)) {
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update rateSorce param not to be null");
            }

            //构建评分
            TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
            tbOrganRateScore.setId(id);
            tbOrganRateScore.setUpdateOper(operCode);
            tbOrganRateScore.setUpdateTime(BocoUtils.getTime());

            //删除所有明细
            tbOrganRateScoreMonthDetailMapper.deleteByWhere("ref_id = \'" + id + "\'");
            //构建评分明细
            List<TbOrganRateScoreMonthDetail> organRateScoreMonthDetailList = buildTbOrganRateScoreMonthDetail(rateSorceDetailStr, id);

            //更新评分
            tbOrganRateScoreMapper.updateByPK(tbOrganRateScore);

            //插入评分明细
            tbOrganRateScoreMonthDetailMapper.insertBatch(organRateScoreMonthDetailList);

        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add rateSorce.");
        }
        return result.success("add", "add rateSorce success.");
    }

    /*修改季度评分*/
    @Override
    public PlainResult<String> updateTbOrganRateScoreQuarter(HttpServletRequest request, String operCode, String organCode) {
        PlainResult<String> result = new PlainResult<>();

        try {
            String rateSorceDetailStr = request.getParameter("rateSorceDetail");
            String id = request.getParameter("id");

            //校验特殊字段非空
            if (StringUtils.isEmpty(id) || StringUtils.isEmpty(rateSorceDetailStr)) {
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update rateSorce param not to be null");
            }

            //构建评分
            TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
            tbOrganRateScore.setId(id);
            tbOrganRateScore.setUpdateOper(operCode);
            tbOrganRateScore.setUpdateTime(BocoUtils.getTime());

            //删除所有明细
            tbOrganRateScoreQuarterDetailMapper.deleteByWhere("ref_id = \'" + id + "\'");
            //构建评分明细
            List<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetailList = buildTbOrganRateScoreQuarterDetail(rateSorceDetailStr, id);

            //更新评分
            tbOrganRateScoreMapper.updateByPK(tbOrganRateScore);

            //插入评分明细
            tbOrganRateScoreQuarterDetailMapper.insertBatch(tbOrganRateScoreQuarterDetailList);

        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add rateSorce.");
        }
        return result.success("add", "add rateSorce success.");
    }

    /*启动评分审批流程*/
    @Override
    public PlainResult<String> startRateScoreAuditProcess(String id, String organCode, String operCode, String operName, String assignee, String comment) throws Exception {
        /*流程key*/
        String processKey = AuditMix.RATE_SCORE;


        String msgNo = BocoUtils.getUUID();
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> varMap = new HashMap<String, Object>();
        //业务代码
        varMap.put("businessKey", id);
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

        logger.info("评分审批流程编号workFlowCode【" + workFlowCode + "】");

        //更新评分记录状态
        TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
        tbOrganRateScore.setId(id);
        tbOrganRateScore.setRateStatus(TbReqDetail.STATE_APPROVALING);
        tbOrganRateScore.setUpdateTime(BocoUtils.getTime());
        tbOrganRateScoreMapper.updateByPK(tbOrganRateScore);

        //记录审批信息
        String url = "tbOrganRateScoreMonthPendingApp/listRateScoreDetailAuditUI.htm?id=" + id + "&taskid=" + task.getId();
        saveMsg(msgNo, operCode, assignee, url, id);
        return result.success(workFlowCode, "启动评分审批流程启动成功");
    }

    /*启动季度评分审批流程*/
    @Override
    public PlainResult<String> startRateScoreAuditProcessQuarter(String id, String organCode, String operCode, String operName, String assignee, String comment) throws Exception {
        /*流程key*/
        String processKey = AuditMix.RATE_SCORE;


        String msgNo = BocoUtils.getUUID();
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> varMap = new HashMap<String, Object>();
        //业务代码
        varMap.put("businessKey", id);
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

        logger.info("评分审批流程编号workFlowCode【" + workFlowCode + "】");

        //更新评分记录状态
        TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
        tbOrganRateScore.setId(id);
        tbOrganRateScore.setRateStatus(TbReqDetail.STATE_APPROVALING);
        tbOrganRateScore.setUpdateTime(BocoUtils.getTime());
        tbOrganRateScoreMapper.updateByPK(tbOrganRateScore);

        //记录审批信息
        String url = "tbOrganRateScoreQuarterPendingApp/listRateScoreDetailAuditUI.htm?id=" + id + "&taskid=" + task.getId();
        saveMsg(msgNo, operCode, assignee, url, id);
        return result.success(workFlowCode, "启动评分审批流程启动成功");
    }


    /*查询已提交的评分*/
    @Override
    public List<Map<String, Object>> getAuditRecordHistRecord(String sort, String operCode, String auditStatus, String rateMonth, WebOperInfo sessionOperInfo, int rateType) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("opercode", operCode);
        if (auditStatus != null && !"".equals(auditStatus)) {
            map.put("auditStatus", Integer.parseInt(auditStatus));
        }
        map.put("rateMonth", rateMonth);
        map.put("rateType", rateType);
        map.put("sort", sort);

        List<Map<String, Object>> list = tbOrganRateScoreMapper.getAuditRecordHist(map);

        for (Map<String, Object> map1 : list) {
            String processInstanceId = (String) map1.get("procinstid");
            Task task = workFlowService.getTaskByPid(processInstanceId);
            if (task == null) {
                map1.put("taskid", "");
            } else {
                map1.put("taskid", task.getId());

            }
        }

        return list;

    }

    /*查询待审批的评分*/
    @Override
    public List<Map<String, Object>> getPendingAppReq(String sort, String operCode, String rateMonth, String auditStatus, Pager pager, int rateType) throws Exception {
        //设计任务列表
        List<Map<String, Object>> tastList = new ArrayList<Map<String, Object>>();
        //查询登录用户待办任务
        /*流程key*/
        String processKey = AuditMix.RATE_SCORE;

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
            map.put("rateMonth", rateMonth);
            if (auditStatus != null && !"".equals(auditStatus)) {
                map.put("auditStatus", Integer.parseInt(auditStatus));
            }
            map.put("rateType", rateType);
            map.put("sort", sort);
            List<Map<String, Object>> list = tbOrganRateScoreMapper.getPendingAppReq(map);

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

        TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
        tbOrganRateScore.setId(msgMap.get("id").toString());
        //默认先审批通过，后面判断是否最后一个审批人再设置为审批中
        tbOrganRateScore.setRateStatus(TbReqDetail.STATE_APPROVED);

        if ("0".equals(isAgree)) {//驳回
            tbOrganRateScore.setRateStatus(TbReqDetail.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String) msgMap.get("id"), taskId, (String) msgMap.get("custType"));
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
                tbOrganRateScore.setRateStatus(TbReqDetail.STATE_APPROVALING);
            }
        }
        tbOrganRateScore.setUpdateTime(BocoUtils.getTime());
        tbOrganRateScoreMapper.updateByPK(tbOrganRateScore);

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
        String id = String.valueOf(msgMap.get("id"));

        if ("1".equals(isAgree) && "true".equals((String) msgMap.get("lastUserType"))) {
            deleteMsg(id);
        } else {
            String url = "";
            if ("0".equals(isAgree)) {
                //获取流程发起人
                assignee = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());

                url = "tbOrganRateScoreMonthReject/rateScoreResubmitAuditUI.htm?id=" + id + "&taskid=" + task.getId();
            } else if (!"true".equals((String) msgMap.get("lastUserType"))) {
                url = "tbOrganRateScoreMonthPendingApp/listRateScoreDetailAuditUI.htm?id=" + id + "&taskid=" + task.getId();
            }
            saveMsg(msgNo, operCode, assignee, url, id + "");
        }

        //月度评分审批完成，生成季度评分
        if ("1".equals(varMap.get("isAgree")) && ("true".equals((String)msgMap.get("lastUserType")))) {
            addQuarterOrganRateScore();
        }
    }

    @Override
    public void completeTaskAuditQuarter(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception {
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

        TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
        tbOrganRateScore.setId(msgMap.get("id").toString());
        //默认先审批通过，后面判断是否最后一个审批人再设置为审批中
        tbOrganRateScore.setRateStatus(TbReqDetail.STATE_APPROVED);

        if ("0".equals(isAgree)) {//驳回
            tbOrganRateScore.setRateStatus(TbReqDetail.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String) msgMap.get("id"), taskId, (String) msgMap.get("custType"));
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
                tbOrganRateScore.setRateStatus(TbReqDetail.STATE_APPROVALING);
            }
        }
        tbOrganRateScore.setUpdateTime(BocoUtils.getTime());
        tbOrganRateScoreMapper.updateByPK(tbOrganRateScore);

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
        String id = String.valueOf(msgMap.get("id"));

        if ("1".equals(isAgree) && "true".equals((String) msgMap.get("lastUserType"))) {
            deleteMsg(id);
        } else {
            String url = "";
            if ("0".equals(isAgree)) {
                //获取流程发起人
                assignee = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());

                url = "tbOrganRateScoreQuarterReject/rateScoreResubmitAuditUI.htm?id=" + id + "&taskid=" + task.getId();
            } else if (!"true".equals((String) msgMap.get("lastUserType"))) {
                url = "tbOrganRateScoreQuarterPendingApp/listRateScoreDetailAuditUI.htm?id=" + id + "&taskid=" + task.getId();
            }
            saveMsg(msgNo, operCode, assignee, url, id + "");
        }
    }

    /*查询已审批的评分*/
    @Override
    public List<Map<String, Object>> getApprovedRecord(String sort, String operCode, String auditStatus, String rateMonth, int rateType) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("opercode", operCode);
        if (auditStatus != null && !"".equals(auditStatus)) {
            map.put("auditStatus", Integer.parseInt(auditStatus));
        }
        map.put("rateMonth", rateMonth);
        map.put("rateType", rateType);
        map.put("sort", sort);

        List<Map<String, Object>> list = tbOrganRateScoreMapper.getApprovedRecord(map);

        for (Map<String, Object> map1 : list) {
            String processInstanceId = (String) map1.get("procinstid");
            Task task = workFlowService.getTaskByPid(processInstanceId);
            if (task == null) {
                map1.put("taskid", "");
            } else {
                map1.put("taskid", task.getId());
            }
        }

        return list;
    }


    //构建月度评分详情
    private ArrayList<TbOrganRateScoreMonthDetail> getTbOrganRateScoreMonthDetailList(ArrayList<OrganRateParamQueryDO> organRateParamQueryDOList, String tbOrganRateScoreId) {
        ArrayList<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetailList = new ArrayList<>();
        //开始生成评分
        for (OrganRateParamQueryDO organRateparam : organRateParamQueryDOList) {

            String month = organRateparam.getMonth();
            String organCode = organRateparam.getOrganCode();
            BigDecimal monthEndRatio = organRateparam.getMonthEndRatio();
            BigDecimal yearBeginRatio = organRateparam.getYearBeginRatio();
            BigDecimal loanYearIncrement = organRateparam.getLoanYearIncrement();
            BigDecimal personDepositYearIncrement = organRateparam.getPersonDepositYearIncrement();
            BigDecimal companyDepositYearIncrement = organRateparam.getCompanyDepositYearIncrement();
            BigDecimal newLoanRatio = organRateparam.getNewLoanRatio();
            BigDecimal bankAverageLoanRatio = organRateparam.getBankAverageLoanRatio();
            BigDecimal monthBeginEntityLoan = organRateparam.getMonthBeginEntityLoan();
            BigDecimal monthEndEntityLoan = organRateparam.getMonthEndEntityLoan();
            BigDecimal loanCount = organRateparam.getLoanCount();
            BigDecimal organScaleAmount = organRateparam.getOrganScaleAmount();
            BigDecimal bankScaleAmount = organRateparam.getBankScaleAmount();
            BigDecimal monthEndIncrement = organRateparam.getMonthEndIncrement();
            BigDecimal monthBeginReport = organRateparam.getMonthBeginReport();
            BigDecimal monthMidPlan = organRateparam.getMonthMidPlan();
            BigDecimal monthEndPlan = organRateparam.getMonthEndPlan();
            BigDecimal monthBeginPlan = organRateparam.getMonthBeginPlan();
            BigDecimal adjCount = organRateparam.getAdjCount();


            TbOrganRateScoreMonthDetail monthDetail = new TbOrganRateScoreMonthDetail();
            monthDetail.setRateMonth(month);
            monthDetail.setRateOrgan(organCode);
            monthDetail.setId(IDGeneratorUtils.getSequence());
            monthDetail.setRefId(tbOrganRateScoreId);
            //不良情况得分 不良率 = 月末不良率 - 年初不良率
            monthDetail.setBadConditionRatioMonthEnd(monthEndRatio);
            monthDetail.setBadConditionRatioYearBegin(yearBeginRatio);
            monthDetail.setBadConditionRatioChange(BigDecimalUtil.subtract(monthEndRatio, yearBeginRatio));
            BigDecimal badConditionScore = organRateParamCalculateService.getBadCondition(monthEndRatio, yearBeginRatio);
            monthDetail.setBadConditionScore(badConditionScore);
            //自营新增存贷比得分 各项贷款年增量 /（个人自营存款年增量 + 公司存款年增量）
            monthDetail.setLoanYearAdd(loanYearIncrement);
            monthDetail.setPersonalDepositYearIncrement(personDepositYearIncrement);
            monthDetail.setCompanyDepositYearIncrement(companyDepositYearIncrement);
            monthDetail.setDepositLoanRatioChange(BigDecimalUtil.divide(loanYearIncrement, BigDecimalUtil.add(personDepositYearIncrement, companyDepositYearIncrement)));
            BigDecimal depositLoanRatioScore = organRateParamCalculateService.getDepositLoanRatio(loanYearIncrement, personDepositYearIncrement, companyDepositYearIncrement);
            monthDetail.setDepositLoanRatioScore(depositLoanRatioScore);
            //新发生贷款利率得分  贷款利率差 = 新发生贷款利率 - 全行平均贷款利率
            monthDetail.setNewLoanRatio(newLoanRatio);
            monthDetail.setBankAverageLoanRatio(bankAverageLoanRatio);
            monthDetail.setNewLoanRatioChange(newLoanRatio.subtract(bankAverageLoanRatio));
            BigDecimal newLoanRatioScore = organRateParamCalculateService.getNewLoanRatio(newLoanRatio, bankAverageLoanRatio);
            monthDetail.setNewLoanRatioScore(newLoanRatioScore);
            //贷款结构得分 月末实体贷款余额占比 = 月末实体贷款余额/总贷款 - 月初实体贷款余额/总贷款
            monthDetail.setLoanCount(loanCount);
            monthDetail.setMonthBeginEntityLoan(monthBeginEntityLoan);
            monthDetail.setMonthEndEntityLoan(monthEndEntityLoan);
            monthDetail.setMonthBeginEntityLoanChange(BigDecimalUtil.divide(monthBeginEntityLoan, loanCount));
            monthDetail.setMonthEndEntityLoanChange(BigDecimalUtil.divide(monthEndEntityLoan, loanCount));
            monthDetail.setMonthEndBeginEntityLoanChange(BigDecimalUtil.divide(monthEndEntityLoan, loanCount).subtract(BigDecimalUtil.divide(monthBeginEntityLoan, loanCount)));
            BigDecimal loanStructScore = organRateParamCalculateService.getLoanStruct(monthBeginEntityLoan, monthEndEntityLoan, loanCount);
            monthDetail.setLoanStructScore(loanStructScore);
            //超规模占用费和规模闲置费得分  分行罚息占比 =  分行罚息金额 / 全行罚息金额
            monthDetail.setOrganScaleAmount(organScaleAmount);
            monthDetail.setBankScaleAmount(bankScaleAmount);
            monthDetail.setScaleAmountChange(BigDecimalUtil.divide(organScaleAmount, bankScaleAmount));
            BigDecimal scaleAmountScore = organRateParamCalculateService.getScaleAmount(organScaleAmount, bankScaleAmount);
            monthDetail.setScaleAmountScore(scaleAmountScore);
            //计划报送偏离度得分  偏离幅度 = （月末实际月增量 - 月初报送需求） / 月末实际月增量
            monthDetail.setMonthEndIncrement(monthEndIncrement);
            monthDetail.setMonthBeginReport(monthBeginReport);
            monthDetail.setPlanSubmitDeviationChange(BigDecimalUtil.divide(monthEndIncrement.subtract(monthBeginReport), monthEndIncrement));
            BigDecimal planSubmitDeviationScore = organRateParamCalculateService.getPlanSubmitDeviation(monthEndIncrement, monthBeginReport);
            monthDetail.setPlanSubmitDeviationScore(planSubmitDeviationScore);
            //计划执行偏离度--月末月中偏离度得分   偏离幅度=（月末实际月增量-月中统一动态调整后计划）/月末统一动态调整后计划
            monthDetail.setMonthMidPlan(monthMidPlan);
            monthDetail.setMonthEndPlan(monthEndPlan);
            monthDetail.setPlanExecuteDeviationMonthMidChange(BigDecimalUtil.divide(monthEndIncrement.subtract(monthMidPlan), monthEndPlan));
            BigDecimal planExecuteDeviationMonthMidScore = organRateParamCalculateService.getPlanExecuteDeviationMonthMid(monthEndIncrement, monthMidPlan, monthEndPlan);
            monthDetail.setPlanExecuteDeviationMonthMidScore(planExecuteDeviationMonthMidScore);
            //计划执行偏离度--月末月初偏离度得分 偏离幅度=（月末实际月增量-月初总行下达计划）/月初总行下达计划
            monthDetail.setMonthBeginPlan(monthBeginPlan);
            monthDetail.setPlanExecuteDeviationMonthInitChange(BigDecimalUtil.divide(monthEndIncrement.subtract(monthBeginPlan), monthBeginPlan));
            BigDecimal planExecuteDeviationMonthInitScore = organRateParamCalculateService.getPlanExecuteDeviationMonthInit(monthEndIncrement, monthBeginPlan);
            monthDetail.setPlanExecuteDeviationMonthInitScore(planExecuteDeviationMonthInitScore);
            //调整频次得分
            monthDetail.setAdjCount(adjCount);
            BigDecimal adjCountScore = organRateParamCalculateService.getAdjCount(adjCount);
            monthDetail.setAdjCountScore(adjCountScore);
            //加分项
            monthDetail.setAddScore(BigDecimal.ZERO);

            //计算总分
            BigDecimal monthScore = badConditionScore.add(depositLoanRatioScore).add(newLoanRatioScore).add(loanStructScore)
                    .add(scaleAmountScore).add(planSubmitDeviationScore).add(planExecuteDeviationMonthMidScore)
                    .add(planExecuteDeviationMonthInitScore).add(adjCountScore);
            monthDetail.setRateScoreMonth(monthScore);

            monthDetail.setCreateTime(BocoUtils.getTime());
            monthDetail.setUpdateTime(BocoUtils.getTime());

            tbOrganRateScoreMonthDetailList.add(monthDetail);
        }
        return tbOrganRateScoreMonthDetailList;
    }

    //构建评分需要的数据
    private ArrayList<OrganRateParamQueryDO> getOrganRateParamQueryDOList(String month) throws Exception {
        ArrayList<OrganRateParamQueryDO> organRateParamQueryDOList = new ArrayList<>();

        Random r = new Random();

        //查询需要的评分数据

        // 月末不良率	 年初不良率  个人自营存款年增量  公司存款年增量 新发生贷款利率	全行平均贷款利率 ---外部导入的数据
        HashMap<String, TbOrganRateScoreImportData> importDataMap = buildImportDataMap(month);
        // 各项贷款年增量
        HashMap<String, BigDecimal> loanYearIncrementMap = buildLoanYearIncrementMap(month);
        // 月初实体贷款余额
        HashMap<String, Map<String, BigDecimal>> monthBeginMap = buildMonthEndEntityLoanMap(Integer.valueOf(month) - 1 + "");
        Map<String, BigDecimal> monthBeginEntityLoanMap = monthBeginMap.get("monthEndEntityLoanMap");
        // 月末实体贷款余额
        HashMap<String, Map<String, BigDecimal>> monthEndMap = buildMonthEndEntityLoanMap(month);
        Map<String, BigDecimal> monthEndEntityLoanMap = monthEndMap.get("monthEndEntityLoanMap");
        // 总贷款余额
        Map<String, BigDecimal> loanCountMap = monthEndMap.get("loanCountMap");
        //分行罚息金额
        HashMap<String, BigDecimal> organScaleAmountMap = buildOrganScaleAmountMap(month);
        //全行罚息金额
        BigDecimal bankScaleAmount = new BigDecimal("0");
        for (String key : organScaleAmountMap.keySet()) {
            bankScaleAmount = bankScaleAmount.add(organScaleAmountMap.get(key));
        }
        //月初报送需求
        Map<String, BigDecimal> monthBeginReportMap = buildMonthBeginReportMap(month);
        //月末实际月增量
        HashMap<String, BigDecimal> monthEndIncrementMap = buildMonthEndIncrementMap(month);
        //月中统一动态调整后计划
        HashMap<String, BigDecimal> monthMidPlanMap = buildMonthMidPlanMap(month);
        //月末统一动态调整后计划
        HashMap<String, BigDecimal> monthEndPlanMap = buildMonthEndPlanMap(month);
        //月初总行下达计划
        HashMap<String, BigDecimal> monthBeginPlanMap = buildMonthBeginPlanMap(month);
        // 月中统一动态调整计划通知分行后分行额外的调整次数  超限额--单笔专项
        Map<String, Integer> adjCountMap = getAdjCountMap(month);


        //查询机构
        List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());

        //组装评分数据
        for (Map<String, Object> organ : organList) {
            String organCode = organ.get("thiscode").toString();

            TbOrganRateScoreImportData importData = importDataMap.get(organCode);
            if (importData == null) {
                importData = new TbOrganRateScoreImportData();
            }

            OrganRateParamQueryDO organRateParamQueryDO = new OrganRateParamQueryDO();
            organRateParamQueryDO.setOrganCode(organCode);
            organRateParamQueryDO.setMonth(month);
            // 月末不良率 monthEndRatio;
            organRateParamQueryDO.setMonthEndRatio(getSafeCount(importData.getMonthEndRatio()));
            // 年初不良率 earBeginRatio;
            organRateParamQueryDO.setYearBeginRatio(getSafeCount(importData.getYearBeginRatio()));
            // 各项贷款年增量 loanYearIncrement;
            organRateParamQueryDO.setLoanYearIncrement(getSafeCount(loanYearIncrementMap.get(organCode)));
            // 个人自营存款年增量 personDepositYearIncrement;
            organRateParamQueryDO.setPersonDepositYearIncrement(getSafeCount(importData.getPersonDepositYearIncrement()));
            // 公司存款年增量 companyDepositYearIncrement;
            organRateParamQueryDO.setCompanyDepositYearIncrement(getSafeCount(importData.getCompanyDepositYearIncrement()));
            // 新发生贷款利率 newLoanRatio;
            organRateParamQueryDO.setNewLoanRatio(getSafeCount(importData.getNewLoanRatio()));
            // 全行平均贷款利率 BankAverageLoanRatio;
            organRateParamQueryDO.setBankAverageLoanRatio(getSafeCount(importData.getBankAverageLoanRatio()));
            // 月初实体贷款余额 monthBeginEntityLoan;
            organRateParamQueryDO.setMonthBeginEntityLoan(getSafeCount(monthBeginEntityLoanMap.get(organCode)));
            // 月末实体贷款余额 monthEndEntityLoan;
            organRateParamQueryDO.setMonthEndEntityLoan(getSafeCount(monthEndEntityLoanMap.get(organCode)));
            // 总贷款余额 loanCount;
            organRateParamQueryDO.setLoanCount(getSafeCount(loanCountMap.get(organCode)));
            // 分行罚息金额 organScaleAmount;
            organRateParamQueryDO.setOrganScaleAmount(getSafeCount(organScaleAmountMap.get(organCode)));
            // 全行罚息金额 bankScaleAmount;
            organRateParamQueryDO.setBankScaleAmount(getSafeCount(bankScaleAmount));
            // 月末实际月增量  monthEndIncrement;
            organRateParamQueryDO.setMonthEndIncrement(getSafeCount(monthEndIncrementMap.get(organCode)));
            // 月初报送需求 monthBeginReport;
            organRateParamQueryDO.setMonthBeginReport(getSafeCount(monthBeginReportMap.get(organCode)));
            // 月中统一动态调整后计划 monthMidPlan;
            organRateParamQueryDO.setMonthMidPlan(getSafeCount(monthMidPlanMap.get(organCode)));
            // 月末统一动态调整后计划 MonthEndPlan;
            organRateParamQueryDO.setMonthEndPlan(getSafeCount(monthEndPlanMap.get(organCode)));
            // 月初总行下达计划 monthBeginPlan;
            organRateParamQueryDO.setMonthBeginPlan(getSafeCount(monthBeginPlanMap.get(organCode)));
            // 月中统一动态调整计划通知分行后分行额外的调整次数 adjCount;
            organRateParamQueryDO.setAdjCount(getSafeCount(adjCountMap.get(organCode)));

            organRateParamQueryDOList.add(organRateParamQueryDO);
        }
        return organRateParamQueryDOList;
    }

    //导入的数据
    private HashMap<String, TbOrganRateScoreImportData> buildImportDataMap(String month) {
        TbOrganRateScoreImportData tbOrganRateScoreImportDataQuery = new TbOrganRateScoreImportData();
        tbOrganRateScoreImportDataQuery.setMonth(month);
        List<TbOrganRateScoreImportData> tbOrganRateScoreImportDataList = tbOrganRateScoreImportDataMapper.selectByAttr(tbOrganRateScoreImportDataQuery);
        HashMap<String, TbOrganRateScoreImportData> resultMap = new HashMap<>();
        for (TbOrganRateScoreImportData data : tbOrganRateScoreImportDataList) {
            resultMap.put(data.getOrgancode(), data);
        }
        return resultMap;
    }

    //各项贷款年增量
    private HashMap<String, BigDecimal> buildLoanYearIncrementMap(String month) {

        //二分机构-一分机构
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setOrganlevel("2");
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(fdOrgan);
        HashMap<String, String> organMap = new HashMap<>();
        for (FdOrgan organ : fdOrganList) {
            organMap.put(organ.getThiscode(), organ.getUporgan());
        }


        //机构--现在balance总值
        int year = 0;
        int mon = 0;
        try {
            year = Integer.valueOf(month.substring(0, 4));
            mon = Integer.valueOf(month.substring(4, 6));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String lastDayOfMonth = getLastDayOfMonth(year, mon);

        TbRptBaseinfo tbRptBaseinfo = new TbRptBaseinfo();
        tbRptBaseinfo.setRptDate(lastDayOfMonth);
        List<TbRptBaseinfo> tbRptBaseinfoList = tbRptBaseinfoMapper.selectByAttr(tbRptBaseinfo);

        //机构-贷款余额
        HashMap<String, BigDecimal> rptBaseInfoMapOfMonth = new HashMap<>();
        for (TbRptBaseinfo rptBaseinfo : tbRptBaseinfoList) {
            BigDecimal amountCount = rptBaseInfoMapOfMonth.get(rptBaseinfo.getOrgan());
            if (amountCount == null) {
                amountCount = BigDecimal.ZERO;
            }
            amountCount = amountCount.add(new BigDecimal(rptBaseinfo.getBalance() + ""));
            rptBaseInfoMapOfMonth.put(rptBaseinfo.getOrgan(), amountCount);
        }
        //一分机构-贷款余额
        HashMap<String, BigDecimal> monthBalanceMap = new HashMap<>();
        for (String key : rptBaseInfoMapOfMonth.keySet()) {
            String upOrgan = organMap.get(key);
            if (upOrgan == null) {
                upOrgan = key;
            }
            BigDecimal balance = monthBalanceMap.get(upOrgan);
            if (balance == null) {
                balance = BigDecimal.ZERO;
            }
            balance = balance.add(rptBaseInfoMapOfMonth.get(key));

            monthBalanceMap.put(upOrgan, balance);
        }


        //机构--去年年底balance总值
        int year2 = 0;
        int mon2 = 12;
        try {
            month = (Integer.valueOf(month) - 100) + "";
            year2 = Integer.valueOf(month.substring(0, 4));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String lastDayOfLastYear = getLastDayOfMonth(year2, mon2);

        TbRptBaseinfo tbRptBaseinfoLastYear = new TbRptBaseinfo();
        tbRptBaseinfoLastYear.setRptDate(lastDayOfLastYear);
        List<TbRptBaseinfo> tbRptBaseinfoListLastYear = tbRptBaseinfoMapper.selectByAttr(tbRptBaseinfoLastYear);

        //机构-贷款余额
        HashMap<String, BigDecimal> rptBaseInfoMapOfYear = new HashMap<>();
        for (TbRptBaseinfo rptBaseinfo : tbRptBaseinfoListLastYear) {
            BigDecimal amountCount = rptBaseInfoMapOfYear.get(rptBaseinfo.getOrgan());
            if (amountCount == null) {
                amountCount = BigDecimal.ZERO;
            }
            amountCount = amountCount.add(new BigDecimal(rptBaseinfo.getBalance() + ""));
            rptBaseInfoMapOfYear.put(rptBaseinfo.getOrgan(), amountCount);
        }
        //一分机构-贷款余额
        HashMap<String, BigDecimal> lastYearBalanceMap = new HashMap<>();
        for (String key : rptBaseInfoMapOfYear.keySet()) {
            String upOrgan = organMap.get(key);
            if (upOrgan == null) {
                upOrgan = key;
            }
            BigDecimal balance = lastYearBalanceMap.get(upOrgan);
            if (balance == null) {
                balance = BigDecimal.ZERO;
            }
            balance = balance.add(rptBaseInfoMapOfYear.get(key));

            lastYearBalanceMap.put(upOrgan, balance);
        }


        //机构--贷款年增量
        HashMap<String, BigDecimal> loanYearIncrementMap = new HashMap<>();
        for (String organ : monthBalanceMap.keySet()) {
            BigDecimal monthBalance = monthBalanceMap.get(organ);
            BigDecimal yearBalance = lastYearBalanceMap.get(organ) == null ? BigDecimal.ZERO : lastYearBalanceMap.get(organ);
            loanYearIncrementMap.put(organ, BigDecimalUtil.subtract(monthBalance, yearBalance));
        }

        return loanYearIncrementMap;
    }

    // 月末实体贷款余额    总贷款余额
    private HashMap<String, Map<String, BigDecimal>> buildMonthEndEntityLoanMap(String month) {
        int year = 0;
        int mon = 0;
        try {
            year = Integer.valueOf(month.substring(0, 4));
            mon = Integer.valueOf(month.substring(4, 6));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String lastDayOfMonth = getLastDayOfMonth(year, mon);

        TbRptBaseinfo tbRptBaseinfo = new TbRptBaseinfo();
        tbRptBaseinfo.setRptDate(lastDayOfMonth);
        List<TbRptBaseinfo> tbRptBaseinfoList = tbRptBaseinfoMapper.selectByAttr(tbRptBaseinfo);

        //机构-<贷种，余额>
        HashMap<String, Map<String, BigDecimal>> rptBaseInfoMap = new HashMap<>();
        for (TbRptBaseinfo rptBaseinfo : tbRptBaseinfoList) {

            Map<String, BigDecimal> loanKindAmountMap = rptBaseInfoMap.get(rptBaseinfo.getOrgan());
            if (loanKindAmountMap == null) {
                loanKindAmountMap = new HashMap<>();
            }
            loanKindAmountMap.put(rptBaseinfo.getLoanKind(), new BigDecimal(rptBaseinfo.getBalance() + ""));
            rptBaseInfoMap.put(rptBaseinfo.getOrgan(), loanKindAmountMap);
        }

        //查询所有的两小，其他 一级，二级，三级贷种
        List<Map<String, Object>> combList = tbOrganRateScoreMapper.selectLoanKindOfTwo();
        HashMap<String, String> combMap = new HashMap<>();
        for (Map<String, Object> map : combList) {
            combMap.put(map.get("combcode") + "", map.get("combname") + "");
        }

        //二分机构-一分机构
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setOrganlevel("2");
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(fdOrgan);
        Map<String, String> organMap = new HashMap<>();
        for (FdOrgan organ : fdOrganList) {
            organMap.put(organ.getThiscode(), organ.getUporgan());
        }

        //月末实体贷款余额 monthEndEntityLoan
        Map<String, BigDecimal> monthEndEntityLoanMap = new HashMap<>();
        //总贷款 loanCount
        Map<String, BigDecimal> loanCountMap = new HashMap<>();

        for (String organ : rptBaseInfoMap.keySet()) {
            String upOrgan = organMap.get(organ);
            if (upOrgan == null) {
                upOrgan = organ;
            }

            //实体贷款余额
            BigDecimal combAmountCount = monthEndEntityLoanMap.get(upOrgan);
            if (combAmountCount == null) {
                combAmountCount = BigDecimal.ZERO;
            }

            //总贷款余额
            BigDecimal amountCount = loanCountMap.get(upOrgan);
            if (amountCount == null) {
                amountCount = BigDecimal.ZERO;
            }

            Map<String, BigDecimal> organCombAmountMap = rptBaseInfoMap.get(organ);
            for (String comb : organCombAmountMap.keySet()) {
                //如果贷种是实体贷种，则加入
                if (combMap.get(comb) != null) {
                    combAmountCount = combAmountCount.add(organCombAmountMap.get(comb));
                }
                amountCount = amountCount.add(organCombAmountMap.get(comb));
            }

            monthEndEntityLoanMap.put(upOrgan, combAmountCount);
            loanCountMap.put(upOrgan, amountCount);

        }

        HashMap<String, Map<String, BigDecimal>> map = new HashMap<>();
        map.put("monthEndEntityLoanMap", monthEndEntityLoanMap);
        map.put("loanCountMap", loanCountMap);

        return map;
    }

    // 月中统一动态调整计划通知分行后分行额外的调整次数  超限额--单笔专项
    public Map<String, Integer> getAdjCountMap(String month)throws Exception {

        TbPlanadj tbPlanadj = new TbPlanadj();
        tbPlanadj.setPlanadjStatus(TbReqDetail.STATE_APPROVED);
        tbPlanadj.setPlanadjType(TbPlan.PLAN);
        tbPlanadj.setPlanadjUnifiedType(1);
        tbPlanadj.setPlanadjMonth(month);
        tbPlanadj.setPlanadjOrgan(getSessionOrgan().getThiscode());
        tbPlanadj.setSortColumn("planadj_create_time desc");
        List<TbPlanadj> tbPlanadjList = tbPlanadjMapper.selectByAttr(tbPlanadj);
        String dateTime = "";
        if (tbPlanadjList != null && tbPlanadjList.size() != 0) {
            dateTime = tbPlanadjList.get(0).getPlanadjUpdateTime();
        }

        TbOver searchTbOver = new TbOver();
        searchTbOver.setQaMonth(month);
        searchTbOver.setQaState(TbOver.STATE_APPROVED);
        List<TbOver> tbOverList = tbOverMapper.selectByAttr(searchTbOver);

        TbSingle searchTbSingle = new TbSingle();
        searchTbSingle.setQaMonth(month);
        searchTbSingle.setQaState(TbSingle.STATE_APPROVED);
        List<TbSingle> tbSingleList = tbSingleMapper.selectByAttr(searchTbSingle);

        Map<String, Integer> map = new HashMap<>();

        for (TbOver tbOver : tbOverList) {
            String organCode = tbOver.getQaOrgan();
            Integer num = map.get(organCode);
            if (num == null) {
                num = 0;
            }
            if (tbOver.getQaCreateTime().compareTo(dateTime) > 0) {
                num = num + 1;
            }
            map.put(organCode, num);
        }

        for (TbSingle tbSingle : tbSingleList) {
            String organCode = tbSingle.getQaOrgan();
            Integer num = map.get(organCode);
            if (num == null) {
                num = 0;
            }
            if (tbSingle.getQaCreateTime().compareTo(dateTime) > 0) {
                num = num + 1;
            }
            map.put(organCode, num);
        }

        return map;

    }

    //分行罚息金额
    private HashMap<String, BigDecimal> buildOrganScaleAmountMap(String month) {
        TbPunishDetail tbPunishDetail = new TbPunishDetail();
        tbPunishDetail.setMonth(month);
        tbPunishDetail.setLeftDay(new BigDecimal("0"));
        List<TbPunishDetail> punishDetailList = tbPunishDetailMapper.selectByAttr(tbPunishDetail);

        //分行罚息金额
        HashMap<String, BigDecimal> organScaleAmountMap = new HashMap<>();
        for (TbPunishDetail punishDetail : punishDetailList) {

            //除掉总行
            if (!"11005293".equals(punishDetail.getOrgan())) {
                BigDecimal amountCount = organScaleAmountMap.get(punishDetail.getOrgan());
                if (amountCount == null) {
                    amountCount = BigDecimal.ZERO;
                }
                amountCount = amountCount.add(punishDetail.getPunishMoney());
                organScaleAmountMap.put(punishDetail.getOrgan(), amountCount);
            }
        }
        return organScaleAmountMap;
    }

    //月初总行下达计划
    private HashMap<String, BigDecimal> buildMonthBeginPlanMap(String month) throws Exception{
        TbPlan tbPlan = new TbPlan();
        tbPlan.setPlanMonth(month);
        tbPlan.setPlanType(TbPlan.PLAN);
        tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
        tbPlan.setPlanOrgan(getSessionOrgan().getThiscode());
        List<TbPlan> tbPlanList = tbPlanMapper.selectByAttr(tbPlan);
        String planId = "";
        if (tbPlanList != null && tbPlanList.size() != 0) {
            planId = tbPlanList.get(0).getPlanId();
        }

        TbPlanDetailBackup planDetailBackupQuery = new TbPlanDetailBackup();
        planDetailBackupQuery.setPdRefId(planId);
        List<TbPlanDetailBackup> tbPlanDetailBackupList = tbPlanDetailBackupMapper.selectByAttr(planDetailBackupQuery);
        HashMap<String, BigDecimal> monthBeginPlanMap = new HashMap<>();
        for (TbPlanDetailBackup planDetailBackup : tbPlanDetailBackupList) {
            BigDecimal amountCount = monthBeginPlanMap.get(planDetailBackup.getPdOrgan());
            if (amountCount == null) {
                amountCount = BigDecimal.ZERO;
            }
            amountCount = amountCount.add(planDetailBackup.getPdAmount());
            monthBeginPlanMap.put(planDetailBackup.getPdOrgan(), amountCount);
        }
        return monthBeginPlanMap;
    }

    //月末统一动态调整后计划
    private HashMap<String, BigDecimal> buildMonthEndPlanMap(String month)throws  Exception {

        TbPlan tbPlan = new TbPlan();
        tbPlan.setPlanMonth(month);
        tbPlan.setPlanType(TbPlan.PLAN);
        tbPlan.setPlanStatus(TbReqDetail.STATE_APPROVED);
        tbPlan.setPlanOrgan(getSessionOrgan().getThiscode());
        List<TbPlan> tbPlanList = tbPlanMapper.selectByAttr(tbPlan);
        String planId = "";
        if (tbPlanList != null && tbPlanList.size() != 0) {
            planId = tbPlanList.get(0).getPlanId();
        }

        TbPlanDetail tbPlanDetailQuery = new TbPlanDetail();
        tbPlanDetailQuery.setPdRefId(planId);
        List<TbPlanDetail> tbPlanDetailList = tbPlanDetailMapper.selectByAttr(tbPlanDetailQuery);
        HashMap<String, BigDecimal> monthEndPlanMap = new HashMap<>();
        for (TbPlanDetail tbPlanDetail : tbPlanDetailList) {
            BigDecimal amountCount = monthEndPlanMap.get(tbPlanDetail.getPdOrgan());
            if (amountCount == null) {
                amountCount = BigDecimal.ZERO;
            }
            amountCount = amountCount.add(tbPlanDetail.getPdAmount());
            monthEndPlanMap.put(tbPlanDetail.getPdOrgan(), amountCount);
        }
        return monthEndPlanMap;
    }


    //月中统一动态调整后计划
    private HashMap<String, BigDecimal> buildMonthMidPlanMap(String month)throws Exception {
        TbPlanadj tbPlanadj = new TbPlanadj();
        tbPlanadj.setPlanadjStatus(TbReqDetail.STATE_APPROVED);
        tbPlanadj.setPlanadjType(TbPlan.PLAN);
        tbPlanadj.setPlanadjUnifiedType(1);
        tbPlanadj.setPlanadjMonth(month);
        tbPlanadj.setPlanadjOrgan(getSessionOrgan().getThiscode());
        tbPlanadj.setSortColumn("planadj_create_time desc");
        List<TbPlanadj> tbPlanadjList = tbPlanadjMapper.selectByAttr(tbPlanadj);
        String planadjId = "";
        if (tbPlanadjList != null && tbPlanadjList.size() != 0) {
            planadjId = tbPlanadjList.get(0).getPlanadjId();
        }

        TbPlanadjDetail tbPlanadjDetailQuery = new TbPlanadjDetail();
        tbPlanadjDetailQuery.setPlanadjdRefId(planadjId);
        List<TbPlanadjDetail> tbPlanadjDetailList = tbPlanadjDetailMapper.selectByAttr(tbPlanadjDetailQuery);
        HashMap<String, BigDecimal> monthMidPlanMap = new HashMap<>();
        for (TbPlanadjDetail tbPlanadjDetail : tbPlanadjDetailList) {
            BigDecimal amountCount = monthMidPlanMap.get(tbPlanadjDetail.getPlanadjdOrgan());
            if (amountCount == null) {
                amountCount = BigDecimal.ZERO;
            }
            amountCount = amountCount.add(tbPlanadjDetail.getPlanadjdAdjedAmount());
            monthMidPlanMap.put(tbPlanadjDetail.getPlanadjdOrgan(), amountCount);
        }
        return monthMidPlanMap;
    }


    //月末实际月增量
    private HashMap<String, BigDecimal> buildMonthEndIncrementMap(String month) {
        int year = 0;
        int mon = 0;
        try {
            year = Integer.valueOf(month.substring(0, 4));
            mon = Integer.valueOf(month.substring(4, 6));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String lastDayOfMonth = getLastDayOfMonth(year, mon);

        TbRptBaseinfo tbRptBaseinfo = new TbRptBaseinfo();
        tbRptBaseinfo.setRptDate(lastDayOfMonth);
        List<TbRptBaseinfo> tbRptBaseinfoList = tbRptBaseinfoMapper.selectByAttr(tbRptBaseinfo);

        //机构-金额
        HashMap<String, BigDecimal> rptBaseInfoMap = new HashMap<>();
        for (TbRptBaseinfo rptBaseinfo : tbRptBaseinfoList) {
            BigDecimal amountCount = rptBaseInfoMap.get(rptBaseinfo.getOrgan());
            if (amountCount == null) {
                amountCount = BigDecimal.ZERO;
            }
            BigDecimal amount = BigDecimalUtil.subtract(rptBaseinfo.getMonthOcc() + "", rptBaseinfo.getMonthLimit() + "");
            amountCount = amountCount.add(amount);
            rptBaseInfoMap.put(rptBaseinfo.getOrgan(), amountCount);
        }

        //二分机构-一分机构
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setOrganlevel("2");
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(fdOrgan);
        HashMap<String, String> organMap = new HashMap<>();
        for (FdOrgan organ : fdOrganList) {
            organMap.put(organ.getThiscode(), organ.getUporgan());
        }


        //一分机构-月末实际月增量
        HashMap<String, BigDecimal> monthEndIncrementMap = new HashMap<>();
        for (String key : rptBaseInfoMap.keySet()) {
            String upOrgan = organMap.get(key);
            if (upOrgan == null) {
                upOrgan = key;
            }
            BigDecimal increment = monthEndIncrementMap.get(upOrgan);
            if (increment == null) {
                increment = BigDecimal.ZERO;
            }
            increment = increment.add(rptBaseInfoMap.get(key));

            monthEndIncrementMap.put(upOrgan, increment);
        }
        return monthEndIncrementMap;
    }

    //月初报送需求
    private Map<String, BigDecimal> buildMonthBeginReportMap(String month) throws Exception {
        TbReqList searchTbReqList = new TbReqList();
        searchTbReqList.setReqMonth(month);//通过唯一月份 那tbreqList的 reqId
        List<TbReqList> lists = tbReqListService.selectByAttr(searchTbReqList);
        int reqId = 0;
        if (lists != null && lists.size() == 1) {
            reqId = lists.get(0).getReqId();
        }
        TbReqDetail searchTbReqDetail = new TbReqDetail();
        searchTbReqDetail.setReqdRefId(reqId);
        searchTbReqDetail.setReqdState(TbReqDetail.STATE_APPROVED);//reqid 拿到 该批次所有需求详情
        List<TbReqDetail> tbReqDetailList = tbReqDetailService.selectByAttr(searchTbReqDetail);

        Map<String, BigDecimal> monthBeginReportMap = new HashMap<>(36);
        for (TbReqDetail tbReqDetail : tbReqDetailList) {
            String organCode = tbReqDetail.getReqdOrgan(); //拿取得 每个需求详情的机构 做key
            BigDecimal totalReqNum = monthBeginReportMap.get(organCode);//map key:organCode;value 每机构需求累计总值
            if (null == totalReqNum) {
                totalReqNum = BigDecimal.ZERO;
            }
            totalReqNum = totalReqNum.add(tbReqDetail.getReqdReqnum());
            monthBeginReportMap.put(organCode, totalReqNum);
        }
        return monthBeginReportMap;
    }


    //构建季度评分参数表
    private List<TbOrganRateScoreQuarterDetail> getTbOrganRateScoreQuarterDetailList(String tbOrganRateScoreId, String month) throws Exception {

        ArrayList<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetailList = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar c = Calendar.getInstance();

        //查询该季度第一个月的数据
        String mon1 = Integer.valueOf(month)-2+"";
        HashMap<String, Object> queryMap1 = new HashMap<>();
        queryMap1.put("rateMonth", mon1);
        queryMap1.put("rateType", OrganRateParamElementType.RATE_MONTH);
        List<TbOrganRateScoreMonthDetail> organrateList1 = tbOrganRateScoreMonthDetailMapper.selectOrganRateScoreDetailByMonthAndType(queryMap1);
        HashMap<String, TbOrganRateScoreMonthDetail> organrateMap1 = new HashMap<>();
        for (TbOrganRateScoreMonthDetail tbOrganRateScoreMonthDetail : organrateList1) {
            organrateMap1.put(tbOrganRateScoreMonthDetail.getRateOrgan(), tbOrganRateScoreMonthDetail);
        }
        //查询该季度第二个月的数据
        String mon2 = Integer.valueOf(month)-1+"";
        HashMap<String, Object> queryMap2 = new HashMap<>();
        queryMap1.put("rateMonth", mon2);
        queryMap1.put("rateType", OrganRateParamElementType.RATE_MONTH);
        List<TbOrganRateScoreMonthDetail> organrateList2 = tbOrganRateScoreMonthDetailMapper.selectOrganRateScoreDetailByMonthAndType(queryMap1);
        HashMap<String, TbOrganRateScoreMonthDetail> organrateMap2 = new HashMap<>();
        for (TbOrganRateScoreMonthDetail tbOrganRateScoreMonthDetail : organrateList2) {
            organrateMap2.put(tbOrganRateScoreMonthDetail.getRateOrgan(), tbOrganRateScoreMonthDetail);
        }
        //查询该季度第三个月的数据
        String mon3 = month;
        HashMap<String, Object> queryMap3 = new HashMap<>();
        queryMap1.put("rateMonth", mon3);
        queryMap1.put("rateType", OrganRateParamElementType.RATE_MONTH);
        List<TbOrganRateScoreMonthDetail> organrateList3 = tbOrganRateScoreMonthDetailMapper.selectOrganRateScoreDetailByMonthAndType(queryMap1);
        HashMap<String, TbOrganRateScoreMonthDetail> organrateMap3 = new HashMap<>();
        for (TbOrganRateScoreMonthDetail tbOrganRateScoreMonthDetail : organrateList3) {
            organrateMap3.put(tbOrganRateScoreMonthDetail.getRateOrgan(), tbOrganRateScoreMonthDetail);
        }


        //查询机构
        String organCode = getSessionOrgan().getThiscode();
        List<Map<String, Object>> organList = fdOrganService.selectByUporgan(organCode);
        for (Map<String, Object> organ : organList) {
            TbOrganRateScoreQuarterDetail tbOrganRateScoreQuarterDetail = new TbOrganRateScoreQuarterDetail();
            tbOrganRateScoreQuarterDetail.setId(IDGeneratorUtils.getSequence());
            tbOrganRateScoreQuarterDetail.setRefId(tbOrganRateScoreId);
            tbOrganRateScoreQuarterDetail.setRateOrgan(organ.get("thiscode").toString());
            tbOrganRateScoreQuarterDetail.setRateMonth(mon3);
            tbOrganRateScoreQuarterDetail.setGoodIdeaScore(new BigDecimal("0"));
            tbOrganRateScoreQuarterDetail.setGoodJobScore(new BigDecimal("0"));
            tbOrganRateScoreQuarterDetail.setAddScore(BigDecimal.ZERO);
            tbOrganRateScoreQuarterDetail.setCreateTime(BocoUtils.getTime());
            tbOrganRateScoreQuarterDetail.setUpdateTime(BocoUtils.getTime());
            tbOrganRateScoreQuarterDetailList.add(tbOrganRateScoreQuarterDetail);
        }

        //组装数据
        for (TbOrganRateScoreQuarterDetail organDetail : tbOrganRateScoreQuarterDetailList) {
            String organ = organDetail.getRateOrgan();
            TbOrganRateScoreMonthDetail rateScore1 = organrateMap1.get(organ);
            TbOrganRateScoreMonthDetail rateScore2 = organrateMap2.get(organ);
            TbOrganRateScoreMonthDetail rateScore3 = organrateMap3.get(organ);
            if (rateScore1 == null) {
                rateScore1 = new TbOrganRateScoreMonthDetail();
            }
            if (rateScore2 == null) {
                rateScore2 = new TbOrganRateScoreMonthDetail();
            }
            if (rateScore3 == null) {
                rateScore3 = new TbOrganRateScoreMonthDetail();
            }

            //不良情况季度得分
            organDetail.setBadConditionScoreOne(getSafeCount(rateScore1.getBadConditionScore()));
            organDetail.setBadConditionScoreTwo(getSafeCount(rateScore2.getBadConditionScore()));
            organDetail.setBadConditionScoreThree(getSafeCount(rateScore3.getBadConditionScore()));
            organDetail.setBadConditionScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getBadConditionScore()),
                    getSafeCount(rateScore2.getBadConditionScore()), getSafeCount(rateScore3.getBadConditionScore())));
            //自营新增存贷比季度得分
            organDetail.setDepositLoanRatioScoreOne(getSafeCount(rateScore1.getDepositLoanRatioScore()));
            organDetail.setDepositLoanRatioScoreTwo(getSafeCount(rateScore2.getDepositLoanRatioScore()));
            organDetail.setDepositLoanRatioScoreThree(getSafeCount(rateScore3.getDepositLoanRatioScore()));
            organDetail.setDepositLoanRatioScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getDepositLoanRatioScore()),
                    getSafeCount(rateScore2.getDepositLoanRatioScore()), getSafeCount(rateScore3.getDepositLoanRatioScore())));
            //新发生贷款利率季度得分
            organDetail.setNewLoanRatioScoreOne(getSafeCount(rateScore1.getNewLoanRatioScore()));
            organDetail.setNewLoanRatioScoreTwo(getSafeCount(rateScore2.getNewLoanRatioScore()));
            organDetail.setNewLoanRatioScoreThree(getSafeCount(rateScore3.getNewLoanRatioScore()));
            organDetail.setNewLoanRatioScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getNewLoanRatioScore()),
                    getSafeCount(rateScore2.getNewLoanRatioScore()), getSafeCount(rateScore3.getNewLoanRatioScore())));
            //贷款结构季度得分
            organDetail.setLoanStructScoreOne(getSafeCount(rateScore1.getLoanStructScore()));
            organDetail.setLoanStructScoreTwo(getSafeCount(rateScore2.getLoanStructScore()));
            organDetail.setLoanStructScoreThree(getSafeCount(rateScore3.getLoanStructScore()));
            organDetail.setLoanStructScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getLoanStructScore()),
                    getSafeCount(rateScore2.getLoanStructScore()), getSafeCount(rateScore3.getLoanStructScore())));
            //超规模占用费和规模闲置费季度得分
            organDetail.setScaleAmountScoreOne(getSafeCount(rateScore1.getScaleAmountScore()));
            organDetail.setScaleAmountScoreTwo(getSafeCount(rateScore2.getScaleAmountScore()));
            organDetail.setScaleAmountScoreThree(getSafeCount(rateScore3.getScaleAmountScore()));
            organDetail.setScaleAmountScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getScaleAmountScore()),
                    getSafeCount(rateScore2.getScaleAmountScore()), getSafeCount(rateScore3.getScaleAmountScore())));
            //计划报送偏离度季度得分
            organDetail.setPlanSubmitDeviationScoreOne(getSafeCount(rateScore1.getPlanSubmitDeviationScore()));
            organDetail.setPlanSubmitDeviationScoreTwo(getSafeCount(rateScore2.getPlanSubmitDeviationScore()));
            organDetail.setPlanSubmitDeviationScoreThree(getSafeCount(rateScore3.getPlanSubmitDeviationScore()));
            organDetail.setPlanSubmitDeviationScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getPlanSubmitDeviationScore()),
                    getSafeCount(rateScore2.getPlanSubmitDeviationScore()), getSafeCount(rateScore3.getPlanSubmitDeviationScore())));
            //计划执行偏离度月末月中偏离度季度得分
            organDetail.setPlanExecuteDeviationMonthMidScoreOne(getSafeCount(rateScore1.getPlanExecuteDeviationMonthMidScore()));
            organDetail.setPlanExecuteDeviationMonthMidScoreTwo(getSafeCount(rateScore2.getPlanExecuteDeviationMonthMidScore()));
            organDetail.setPlanExecuteDeviationMonthMidScoreThree(getSafeCount(rateScore3.getPlanExecuteDeviationMonthMidScore()));
            organDetail.setPlanExecuteDeviationMonthMidScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getPlanExecuteDeviationMonthMidScore()),
                    getSafeCount(rateScore2.getPlanExecuteDeviationMonthMidScore()), getSafeCount(rateScore3.getPlanExecuteDeviationMonthMidScore())));
            //计划执行偏离度月末月初偏离度季度得分
            organDetail.setPlanExecuteDeviationMonthInitScoreOne(getSafeCount(rateScore1.getPlanExecuteDeviationMonthInitScore()));
            organDetail.setPlanExecuteDeviationMonthInitScoreTwo(getSafeCount(rateScore2.getPlanExecuteDeviationMonthInitScore()));
            organDetail.setPlanExecuteDeviationMonthInitScoreThree(getSafeCount(rateScore3.getPlanExecuteDeviationMonthInitScore()));
            organDetail.setPlanExecuteDeviationMonthInitScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getPlanExecuteDeviationMonthInitScore()),
                    getSafeCount(rateScore2.getPlanExecuteDeviationMonthInitScore()), getSafeCount(rateScore3.getPlanExecuteDeviationMonthInitScore())));
            //调整频次季度得分
            organDetail.setAdjCountScoreOne(getSafeCount(rateScore1.getAdjCountScore()));
            organDetail.setAdjCountScoreTwo(getSafeCount(rateScore2.getAdjCountScore()));
            organDetail.setAdjCountScoreThree(getSafeCount(rateScore3.getAdjCountScore()));
            organDetail.setAdjCountScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getAdjCountScore()),
                    getSafeCount(rateScore2.getAdjCountScore()), getSafeCount(rateScore3.getAdjCountScore())));
            //未产生超规模占用费季度得分
            BigDecimal scaleCount = new BigDecimal("0");
            TbOrganRateParam organRateParamScaleQuery = new TbOrganRateParam();
            organRateParamScaleQuery.setElementType(OrganRateParamElementType.SCALE_AMOUNT);
            List<TbOrganRateParam> rateScaleList = organRateParamMapper.selectByAttr(organRateParamScaleQuery);
            BigDecimal scaleMaxScore = rateScaleList.get(0).getMaxTargetScore();
            if (scaleMaxScore.compareTo(getSafeCount(rateScore1.getScaleAmountScore())) == 0) {
                scaleCount = scaleCount.add(new BigDecimal("1"));
            }
            if (scaleMaxScore.compareTo(getSafeCount(rateScore2.getScaleAmountScore())) == 0) {
                scaleCount = scaleCount.add(new BigDecimal("1"));
            }
            if (scaleMaxScore.compareTo(getSafeCount(rateScore3.getScaleAmountScore())) == 0) {
                scaleCount = scaleCount.add(new BigDecimal("1"));
            }
            BigDecimal scaleScore = organRateParamCalculateService.getScaleAmountQuarter(scaleCount);
            organDetail.setScaleAmountMonthCount(scaleCount);
            organDetail.setScaleAmountMonthCountScoreQuarter(scaleScore);
            //调整频次未扣分指数季度得分
            BigDecimal adjCount = new BigDecimal("0");
            TbOrganRateParam organRateParamAdjQuery = new TbOrganRateParam();
            organRateParamAdjQuery.setElementType(OrganRateParamElementType.ADJ_COUNT);
            List<TbOrganRateParam> rateAdjList = organRateParamMapper.selectByAttr(organRateParamAdjQuery);
            BigDecimal adjMaxScore = rateAdjList.get(0).getMaxTargetScore();
            if (adjMaxScore.compareTo(getSafeCount(rateScore1.getAdjCountScore())) == 0) {
                adjCount = adjCount.add(new BigDecimal("1"));
            }
            if (adjMaxScore.compareTo(getSafeCount(rateScore2.getAdjCountScore())) == 0) {
                adjCount = adjCount.add(new BigDecimal("1"));
            }
            if (adjMaxScore.compareTo(getSafeCount(rateScore3.getAdjCountScore())) == 0) {
                adjCount = adjCount.add(new BigDecimal("1"));
            }
            BigDecimal adjScore = organRateParamCalculateService.getAdjCountQuarter(adjCount);
            organDetail.setAdjCountMonthCount(adjCount);
            organDetail.setAdjCountMonthCountScoreQuarter(adjScore);
            //历史加分项
            organDetail.setAddScoreOne(getSafeCount(rateScore1.getAddScore()));
            organDetail.setAddScoreTwo(getSafeCount(rateScore2.getAddScore()));
            organDetail.setAddScoreThree(getSafeCount(rateScore3.getAddScore()));
            organDetail.setAddScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getAddScore()),
                    getSafeCount(rateScore2.getAddScore()), getSafeCount(rateScore3.getAddScore())));


            //计算总分
            BigDecimal quarterScore = organDetail.getAdjCountScoreQuarter()
                    .add(organDetail.getBadConditionScoreQuarter())
                    .add(organDetail.getLoanStructScoreQuarter())
                    .add(organDetail.getScaleAmountScoreQuarter())
                    .add(organDetail.getDepositLoanRatioScoreQuarter())
                    .add(organDetail.getNewLoanRatioScoreQuarter())
                    .add(organDetail.getPlanSubmitDeviationScoreQuarter())
                    .add(organDetail.getAdjCountMonthCountScoreQuarter())
                    .add(organDetail.getScaleAmountMonthCountScoreQuarter())
                    .add(organDetail.getPlanExecuteDeviationMonthInitScoreQuarter())
                    .add(organDetail.getPlanExecuteDeviationMonthMidScoreQuarter())
                    .add(organDetail.getGoodIdeaScore())
                    .add(organDetail.getGoodJobScore())
                    .add(organDetail.getAddScoreQuarter())
                    .add(organDetail.getAddScore());

            organDetail.setRateScoreQuarter(quarterScore);

        }

        //进行排序
        Collections.sort(tbOrganRateScoreQuarterDetailList, new Comparator<TbOrganRateScoreQuarterDetail>() {
            @Override
            public int compare(TbOrganRateScoreQuarterDetail o1, TbOrganRateScoreQuarterDetail o2) {
                if (o1.getRateScoreQuarter().compareTo(o2.getRateScoreQuarter()) == 1) {
                    return -1;
                } else if (o1.getRateScoreQuarter().compareTo(o2.getRateScoreQuarter()) == -1) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        return tbOrganRateScoreQuarterDetailList;
    }

    //计算季度得分
    private BigDecimal getQuarterScore(BigDecimal score1, BigDecimal score2, BigDecimal score3) {

        TbOrganRateParam organRateParamAdjQuery = new TbOrganRateParam();
        organRateParamAdjQuery.setElementType(OrganRateParamElementType.QUARTER_WEIGHT);
        List<TbOrganRateParam> rateQuarterWeightList = organRateParamMapper.selectByAttr(organRateParamAdjQuery);
        TbOrganRateParam tbOrganRateParam = rateQuarterWeightList.get(0);


        BigDecimal score = score1.multiply(tbOrganRateParam.getScoreOne())
                .add(score2.multiply(tbOrganRateParam.getScoreTwo()))
                .add(score3.multiply(tbOrganRateParam.getScoreThree()));
        return score;
    }


    /**
     * 构建月度评分明细对象
     *
     * @param rateSorceDetailStr
     * @param id
     * @return
     */
    public List<TbOrganRateScoreMonthDetail> buildTbOrganRateScoreMonthDetail(String rateSorceDetailStr, String id) throws Exception {

        List<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetailList = new ArrayList<>();
        String[] rateScoreDetailArr = rateSorceDetailStr.split("&");

        HashMap<String, Map<String, Object>> rateScoreMap = new HashMap<>();
        for (int i = 0; i < rateScoreDetailArr.length; i++) {
            String[] rateScoreDetailArr2 = rateScoreDetailArr[i].split("_");
            String[] rateScoreDetailArr3 = rateScoreDetailArr2[1].split("=");

            Map<String, Object> rateMap = rateScoreMap.get(rateScoreDetailArr2[0]);

            //如果页面为空值，则默认为0，备注除外
            String resultStr = "0";
            if ("remark".equals(rateScoreDetailArr3[0])) {
                resultStr = "";
            }

            try {
                resultStr = rateScoreDetailArr3[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (rateMap == null) {
                rateMap = new HashMap<String, Object>();
                rateMap.put(rateScoreDetailArr3[0], resultStr);
                rateScoreMap.put(rateScoreDetailArr2[0], rateMap);
            } else {
                rateMap.put(rateScoreDetailArr3[0], resultStr);
            }
        }

        for (String key : rateScoreMap.keySet()) {
            Map<String, Object> ratemap = rateScoreMap.get(key);

            TbOrganRateScoreMonthDetail monthDetail = new TbOrganRateScoreMonthDetail();
            monthDetail.setId(IDGeneratorUtils.getSequence());
            monthDetail.setRefId(id);
            monthDetail.setRateOrgan(key);
            monthDetail.setCreateTime(BocoUtils.getTime());
            monthDetail.setUpdateTime(BocoUtils.getTime());
            monthDetail.setRateMonth(ratemap.get("rateMonth").toString());
            monthDetail.setRateScoreMonth(new BigDecimal(ratemap.get("rateScoreMonth").toString()));
            //不良情况得分 不良率 = 月末不良率 - 年初不良率
            monthDetail.setBadConditionScore(new BigDecimal(ratemap.get("badConditionScore").toString()));
            monthDetail.setBadConditionRatioMonthEnd(new BigDecimal(ratemap.get("badConditionRatioMonthEnd").toString()));
            monthDetail.setBadConditionRatioYearBegin(new BigDecimal(ratemap.get("badConditionRatioYearBegin").toString()));
            monthDetail.setBadConditionRatioChange(new BigDecimal(ratemap.get("badConditionRatioChange").toString()));
            //自营新增存贷比得分 自营新增存贷比=各项贷款年增量 /（个人自营存款年增量 + 公司存款年增量）
            monthDetail.setDepositLoanRatioScore(new BigDecimal(ratemap.get("depositLoanRatioScore").toString()));
            monthDetail.setLoanYearAdd(new BigDecimal(ratemap.get("loanYearAdd").toString()));
            monthDetail.setPersonalDepositYearIncrement(new BigDecimal(ratemap.get("personalDepositYearIncrement").toString()));
            monthDetail.setCompanyDepositYearIncrement(new BigDecimal(ratemap.get("companyDepositYearIncrement").toString()));
            monthDetail.setDepositLoanRatioChange(new BigDecimal(ratemap.get("depositLoanRatioChange").toString()));
            //新发生贷款利率得分  贷款利率差 = 新发生贷款利率 - 全行平均贷款利率
            monthDetail.setNewLoanRatioScore(new BigDecimal(ratemap.get("newLoanRatioScore").toString()));
            monthDetail.setNewLoanRatio(new BigDecimal(ratemap.get("newLoanRatio").toString()));
            monthDetail.setBankAverageLoanRatio(new BigDecimal(ratemap.get("bankAverageLoanRatio").toString()));
            monthDetail.setNewLoanRatioChange(new BigDecimal(ratemap.get("newLoanRatioChange").toString()));
            //贷款结构得分 月末实体贷款余额占比 = 月末实体贷款余额/总贷款 - 月初实体贷款余额/总贷款
            monthDetail.setLoanStructScore(new BigDecimal(ratemap.get("loanStructScore").toString()));
            monthDetail.setLoanCount(new BigDecimal(ratemap.get("loanCount").toString()));
            monthDetail.setMonthBeginEntityLoan(new BigDecimal(ratemap.get("monthBeginEntityLoan").toString()));
            monthDetail.setMonthEndEntityLoan(new BigDecimal(ratemap.get("monthEndEntityLoan").toString()));
            monthDetail.setMonthBeginEntityLoanChange(new BigDecimal(ratemap.get("monthBeginEntityLoanChange").toString()));
            monthDetail.setMonthEndEntityLoanChange(new BigDecimal(ratemap.get("monthEndEntityLoanChange").toString()));
            monthDetail.setMonthEndBeginEntityLoanChange(new BigDecimal(ratemap.get("monthEndBeginEntityLoanChange").toString()));
            //超规模占用费和规模闲置费得分  分行罚息占比 =  分行罚息金额 / 全行罚息金额
            monthDetail.setScaleAmountScore(new BigDecimal(ratemap.get("scaleAmountScore").toString()));
            monthDetail.setOrganScaleAmount(new BigDecimal(ratemap.get("organScaleAmount").toString()));
            monthDetail.setBankScaleAmount(new BigDecimal(ratemap.get("bankScaleAmount").toString()));
            monthDetail.setScaleAmountChange(new BigDecimal(ratemap.get("scaleAmountChange").toString()));
            //计划报送偏离度得分  计划报送偏离幅度 = （月末实际月增量 - 月初报送需求） / 月末实际月增量
            monthDetail.setPlanSubmitDeviationScore(new BigDecimal(ratemap.get("planSubmitDeviationScore").toString()));
            monthDetail.setMonthEndIncrement(new BigDecimal(ratemap.get("monthEndIncrement").toString()));
            monthDetail.setMonthBeginReport(new BigDecimal(ratemap.get("monthBeginReport").toString()));
            monthDetail.setPlanSubmitDeviationChange(new BigDecimal(ratemap.get("planSubmitDeviationChange").toString()));
            //计划执行偏离度--月末月中偏离度得分   月末月中偏离幅度=（月末实际月增量-月中统一动态调整后计划）/月末统一动态调整后计划
            monthDetail.setPlanExecuteDeviationMonthMidScore(new BigDecimal(ratemap.get("planExecuteDeviationMonthMidScore").toString()));
            monthDetail.setMonthMidPlan(new BigDecimal(ratemap.get("monthMidPlan").toString()));
            monthDetail.setMonthEndPlan(new BigDecimal(ratemap.get("monthEndPlan").toString()));
            monthDetail.setPlanExecuteDeviationMonthMidChange(new BigDecimal(ratemap.get("planExecuteDeviationMonthMidChange").toString()));
            //计划执行偏离度--月末月初偏离度得分 月末月初偏离幅度=（月末实际月增量-月初总行下达计划）/月初总行下达计划
            monthDetail.setPlanExecuteDeviationMonthInitScore(new BigDecimal(ratemap.get("planExecuteDeviationMonthInitScore").toString()));
            monthDetail.setMonthBeginPlan(new BigDecimal(ratemap.get("monthBeginPlan").toString()));
            monthDetail.setPlanExecuteDeviationMonthInitChange(new BigDecimal(ratemap.get("planExecuteDeviationMonthInitChange").toString()));
            //调整频次得分
            monthDetail.setAdjCountScore(new BigDecimal(ratemap.get("adjCountScore").toString()));
            monthDetail.setAdjCount(new BigDecimal(ratemap.get("adjCount").toString()));
            //其他
            monthDetail.setAddScore(new BigDecimal(ratemap.get("addScore").toString()));
            monthDetail.setRemark(URLDecoder.decode(ratemap.get("remark").toString(), "UTF-8"));


            tbOrganRateScoreMonthDetailList.add(monthDetail);
        }
        return tbOrganRateScoreMonthDetailList;
    }


    /**
     * 构建季度评分明细对象
     *
     * @param rateSorceDetailStr
     * @param id
     * @return
     */
    public List<TbOrganRateScoreQuarterDetail> buildTbOrganRateScoreQuarterDetail(String rateSorceDetailStr, String id) throws Exception {

        List<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetailList = new ArrayList<>();
        String[] rateScoreDetailArr = rateSorceDetailStr.split("&");

        HashMap<String, Map<String, Object>> rateScoreMap = new HashMap<>();
        for (int i = 0; i < rateScoreDetailArr.length; i++) {
            String[] rateScoreDetailArr2 = rateScoreDetailArr[i].split("_");
            String[] rateScoreDetailArr3 = rateScoreDetailArr2[1].split("=");

            Map<String, Object> rateMap = rateScoreMap.get(rateScoreDetailArr2[0]);

            //如果页面为空值，则默认为0，备注除外
            String resultStr = "0";
            if ("remark".equals(rateScoreDetailArr3[0])) {
                resultStr = "";
            }
            try {
                resultStr = rateScoreDetailArr3[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (rateMap == null) {
                rateMap = new HashMap<String, Object>();
                rateMap.put(rateScoreDetailArr3[0], resultStr);
                rateScoreMap.put(rateScoreDetailArr2[0], rateMap);
            } else {
                rateMap.put(rateScoreDetailArr3[0], resultStr);
            }
        }

        for (String key : rateScoreMap.keySet()) {
            Map<String, Object> ratemap = rateScoreMap.get(key);

            TbOrganRateScoreQuarterDetail quarterDetail = new TbOrganRateScoreQuarterDetail();
            quarterDetail.setId(IDGeneratorUtils.getSequence());
            quarterDetail.setRefId(id);
            quarterDetail.setRateOrgan(key);
            quarterDetail.setCreateTime(BocoUtils.getTime());
            quarterDetail.setUpdateTime(BocoUtils.getTime());
            quarterDetail.setRateMonth(ratemap.get("rateMonth").toString());
            quarterDetail.setRateScoreQuarter(new BigDecimal(ratemap.get("rateScoreQuarter").toString()));
            quarterDetail.setRateRank(new BigDecimal(ratemap.get("rateRank").toString()));
            quarterDetail.setRateRatio(new BigDecimal(ratemap.get("rateRatio").toString()));
            //设置评分等级
            String rateLevelStr = ratemap.get("rateLevel").toString();
            int ratelevel = 4;
            if ("A".equals(rateLevelStr) || "a".equals(rateLevelStr)) {
                ratelevel = 1;
            } else if ("B".equals(rateLevelStr) || "b".equals(rateLevelStr)) {
                ratelevel = 2;
            } else if ("C".equals(rateLevelStr) || "c".equals(rateLevelStr)) {
                ratelevel = 3;
            } else if ("D".equals(rateLevelStr) || "d".equals(rateLevelStr)) {
                ratelevel = 4;
            }
            quarterDetail.setRateLevel(new BigDecimal(ratelevel + ""));

            //不良情况得分 不良率 = 月末不良率 - 年初不良率
            quarterDetail.setBadConditionScoreQuarter(new BigDecimal(ratemap.get("badConditionScoreQuarter").toString()));
            quarterDetail.setBadConditionScoreOne(new BigDecimal(ratemap.get("badConditionScoreOne").toString()));
            quarterDetail.setBadConditionScoreTwo(new BigDecimal(ratemap.get("badConditionScoreTwo").toString()));
            quarterDetail.setBadConditionScoreThree(new BigDecimal(ratemap.get("badConditionScoreThree").toString()));

            //自营新增存贷比得分 自营新增存贷比=各项贷款年增量 /（个人自营存款年增量 + 公司存款年增量）
            quarterDetail.setDepositLoanRatioScoreQuarter(new BigDecimal(ratemap.get("depositLoanRatioScoreQuarter").toString()));
            quarterDetail.setDepositLoanRatioScoreOne(new BigDecimal(ratemap.get("depositLoanRatioScoreOne").toString()));
            quarterDetail.setDepositLoanRatioScoreTwo(new BigDecimal(ratemap.get("depositLoanRatioScoreTwo").toString()));
            quarterDetail.setDepositLoanRatioScoreThree(new BigDecimal(ratemap.get("depositLoanRatioScoreThree").toString()));

            //新发生贷款利率得分  贷款利率差 = 新发生贷款利率 - 全行平均贷款利率

            quarterDetail.setNewLoanRatioScoreQuarter(new BigDecimal(ratemap.get("newLoanRatioScoreQuarter").toString()));
            quarterDetail.setNewLoanRatioScoreOne(new BigDecimal(ratemap.get("newLoanRatioScoreOne").toString()));
            quarterDetail.setNewLoanRatioScoreTwo(new BigDecimal(ratemap.get("newLoanRatioScoreTwo").toString()));
            quarterDetail.setNewLoanRatioScoreThree(new BigDecimal(ratemap.get("newLoanRatioScoreThree").toString()));
            //贷款结构得分 月末实体贷款余额占比 = 月末实体贷款余额/总贷款 - 月初实体贷款余额/总贷款

            quarterDetail.setLoanStructScoreQuarter(new BigDecimal(ratemap.get("loanStructScoreQuarter").toString()));
            quarterDetail.setLoanStructScoreOne(new BigDecimal(ratemap.get("loanStructScoreOne").toString()));
            quarterDetail.setLoanStructScoreTwo(new BigDecimal(ratemap.get("loanStructScoreTwo").toString()));
            quarterDetail.setLoanStructScoreThree(new BigDecimal(ratemap.get("loanStructScoreThree").toString()));
            //超规模占用费和规模闲置费得分  分行罚息占比 =  分行罚息金额 / 全行罚息金额

            quarterDetail.setScaleAmountScoreQuarter(new BigDecimal(ratemap.get("scaleAmountScoreQuarter").toString()));
            quarterDetail.setScaleAmountScoreOne(new BigDecimal(ratemap.get("scaleAmountScoreOne").toString()));
            quarterDetail.setScaleAmountScoreTwo(new BigDecimal(ratemap.get("scaleAmountScoreTwo").toString()));
            quarterDetail.setScaleAmountScoreThree(new BigDecimal(ratemap.get("scaleAmountScoreThree").toString()));
            //计划报送偏离度得分  计划报送偏离幅度 = （月末实际月增量 - 月初报送需求） / 月末实际月增量

            quarterDetail.setPlanSubmitDeviationScoreQuarter(new BigDecimal(ratemap.get("planSubmitDeviationScoreQuarter").toString()));
            quarterDetail.setPlanSubmitDeviationScoreOne(new BigDecimal(ratemap.get("planSubmitDeviationScoreOne").toString()));
            quarterDetail.setPlanSubmitDeviationScoreTwo(new BigDecimal(ratemap.get("planSubmitDeviationScoreTwo").toString()));
            quarterDetail.setPlanSubmitDeviationScoreThree(new BigDecimal(ratemap.get("planSubmitDeviationScoreThree").toString()));
            //计划执行偏离度--月末月中偏离度得分   月末月中偏离幅度=（月末实际月增量-月中统一动态调整后计划）/月末统一动态调整后计划

            quarterDetail.setPlanExecuteDeviationMonthMidScoreQuarter(new BigDecimal(ratemap.get("planExecuteDeviationMonthMidScoreQuarter").toString()));
            quarterDetail.setPlanExecuteDeviationMonthMidScoreOne(new BigDecimal(ratemap.get("planExecuteDeviationMonthMidScoreOne").toString()));
            quarterDetail.setPlanExecuteDeviationMonthMidScoreTwo(new BigDecimal(ratemap.get("planExecuteDeviationMonthMidScoreTwo").toString()));
            quarterDetail.setPlanExecuteDeviationMonthMidScoreThree(new BigDecimal(ratemap.get("planExecuteDeviationMonthMidScoreThree").toString()));
            //计划执行偏离度--月末月初偏离度得分 月末月初偏离幅度=（月末实际月增量-月初总行下达计划）/月初总行下达计划

            quarterDetail.setPlanExecuteDeviationMonthInitScoreQuarter(new BigDecimal(ratemap.get("planExecuteDeviationMonthInitScoreQuarter").toString()));
            quarterDetail.setPlanExecuteDeviationMonthInitScoreOne(new BigDecimal(ratemap.get("planExecuteDeviationMonthInitScoreOne").toString()));
            quarterDetail.setPlanExecuteDeviationMonthInitScoreTwo(new BigDecimal(ratemap.get("planExecuteDeviationMonthInitScoreTwo").toString()));
            quarterDetail.setPlanExecuteDeviationMonthInitScoreThree(new BigDecimal(ratemap.get("planExecuteDeviationMonthInitScoreThree").toString()));
            //调整频次得分

            quarterDetail.setAdjCountScoreQuarter(new BigDecimal(ratemap.get("adjCountScoreQuarter").toString()));
            quarterDetail.setAdjCountScoreOne(new BigDecimal(ratemap.get("adjCountScoreOne").toString()));
            quarterDetail.setAdjCountScoreTwo(new BigDecimal(ratemap.get("adjCountScoreTwo").toString()));
            quarterDetail.setAdjCountScoreThree(new BigDecimal(ratemap.get("adjCountScoreThree").toString()));
            //未产生超规模占用费得分
            quarterDetail.setScaleAmountMonthCountScoreQuarter(new BigDecimal(ratemap.get("scaleAmountMonthCountScoreQuarter").toString()));
            quarterDetail.setScaleAmountMonthCount(new BigDecimal(ratemap.get("scaleAmountMonthCount").toString()));
            //调整频次未扣分得分
            quarterDetail.setAdjCountMonthCountScoreQuarter(new BigDecimal(ratemap.get("adjCountMonthCountScoreQuarter").toString()));
            quarterDetail.setAdjCountMonthCount(new BigDecimal(ratemap.get("adjCountMonthCount").toString()));
            //历史加分项得分
            quarterDetail.setAddScoreQuarter(new BigDecimal(ratemap.get("addScoreQuarter").toString()));
            quarterDetail.setAddScoreOne(new BigDecimal(ratemap.get("addScoreOne").toString()));
            quarterDetail.setAddScoreTwo(new BigDecimal(ratemap.get("addScoreTwo").toString()));
            quarterDetail.setAddScoreThree(new BigDecimal(ratemap.get("addScoreThree").toString()));
            //加分项
            quarterDetail.setAddScore(new BigDecimal(ratemap.get("addScore").toString()));
            quarterDetail.setRemark(URLDecoder.decode(ratemap.get("remark").toString(), "UTF-8"));

            quarterDetail.setGoodIdeaScore(new BigDecimal("0"));
            quarterDetail.setGoodJobScore(new BigDecimal("0"));


            tbOrganRateScoreQuarterDetailList.add(quarterDetail);
        }
        return tbOrganRateScoreQuarterDetailList;
    }


    private void deleteMsg(String id) throws Exception {

        //删除当前小喇叭
        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("评分审批", "MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("评分审批：" + id);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }
    }

    private void saveMsg(String msgNo, String operCode, String assignee, String msgUrl, String id) throws Exception {

        //删除当前小喇叭
        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("评分审批", "MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("评分审批：" + id);
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
        List<Comment> comments = BocoUtils.translateComments(workFlowService.getTaskComments(msgUrl.substring(msgUrl.lastIndexOf("=")+1)), "");
        Comment comment1 = comments.get(comments.size() - 2);
        String operNameAndStatus = comment1.getUserId() + ":" + comment1.getType();
        msg.setAppOperName(operNameAndStatus);

        msg.setAppRoleName("");
        msg.setAppOrganCode(getSessionOrgan().getThiscode());
        msg.setAppOrganName(getSessionOrgan().getOrganname());
        msg.setAppTime(BocoUtils.getNowTime());
        msg.setMsgType(DicCache.getKeyByName_("评分审批", "MSG_TYPE"));
        TbOrganRateScore tbOrganRateScore = tbOrganRateScoreMapper.selectByPK(id);
        String type = tbOrganRateScore.getRateType().intValue() == 1 ? "月度" : "季度";
        msg.setOperName("评分审批" + "-" + tbOrganRateScore.getRateMonth() + "-" + type);
        msg.setOperNo(operCode);
        msg.setRepUserCode(assignee);
        msg.setWebMsgStatus("1");
        msg.setMsgUrl(msgUrl);
        msg.setOperDescribe("评分审批：" + id);
        webMsgService.insertEntity(msg);
    }


    //获取某年某月的最后一天
    public String getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        DateFormat format = new SimpleDateFormat("yyyyMMdd ");
        return format.format(calendar.getTime());
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

    public static void main(String[] args) {
        try {

            String month = "202004";
            String substring = month.substring(0, 4);
            System.out.println(Integer.valueOf(month.substring(0, 4))-1);

            TbOrganRateScoreServiceImpl t = new TbOrganRateScoreServiceImpl();
            String lastDayOfMonth = t.getLastDayOfMonth(2019, 12);
            System.out.println(lastDayOfMonth);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}