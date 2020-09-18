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
 * �����������α�ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * <p>
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class TbOrganRateScoreServiceImpl extends GenericService<TbOrganRateScore, String> implements TbOrganRateScoreService {

    //���Զ��巽��ʱʹ��
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


    /*�����¶�����*/
    @Override

    public void addMonthOragnRateScore() throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m1 = c.getTime();
        String month = format.format(m1);

        //�жϵ�ǰ�¶����ִ��ڲ�
        TbOrganRateScore tbOrganRateScoreQuery = new TbOrganRateScore();
        tbOrganRateScoreQuery.setRateMonth(month);
        tbOrganRateScoreQuery.setRateType(OrganRateParamElementType.RATE_MONTH);
        List<TbOrganRateScore> tbOrganRateScores = tbOrganRateScoreMapper.selectByAttr(tbOrganRateScoreQuery);

        if (tbOrganRateScores != null && tbOrganRateScores.size() != 0) {
            TbOrganRateScore tbOrganRateScore = tbOrganRateScores.get(0);
            //���ڵ�����£������δ�ύ���߲���״̬�����и��ǣ����ұ�����ʷ��¼
            if (tbOrganRateScore.getRateStatus() == TbReqDetail.STATE_NEW || tbOrganRateScore.getRateStatus() == TbReqDetail.STATE_DISMISSED) {

                //��ѯ������ϸ
                TbOrganRateScoreMonthDetail tbOrganRateScoreMonthDetailQuery = new TbOrganRateScoreMonthDetail();
                tbOrganRateScoreMonthDetailQuery.setRefId(tbOrganRateScore.getId());
                List<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetails = tbOrganRateScoreMonthDetailMapper.selectByAttr(tbOrganRateScoreMonthDetailQuery);
                // ��ѯ��ʷ���ֵ
                List<Map<String,Object>> backupList = tbOrganRateScoreMonthDetailBackupMapper.selectMaxHistoryNumber(tbOrganRateScore.getRateMonth());
                BigDecimal number = BigDecimal.ONE;
                if (backupList.get(0) != null ) {
                    String numberStr = backupList.get(0).get("maxnumber").toString() == null ? "0" : backupList.get(0).get("maxnumber").toString();
                    BigDecimal maxnumber = new BigDecimal(numberStr);
                    number = number.add(maxnumber);
                }
                // ������ʷ����
                List<TbOrganRateScoreMonthDetailBackup> monthDetailBackupList = buildMonthHistory(tbOrganRateScoreMonthDetails, number);
                //������ʷ���ݱ�
                tbOrganRateScoreMonthDetailBackupMapper.insertBatch(monthDetailBackupList);


                //ɾ��������ϸ
                tbOrganRateScoreMonthDetailMapper.deleteByWhere("ref_id = \'" + tbOrganRateScore.getId() + "\'");
                //�������ֲ���list
                ArrayList<OrganRateParamQueryDO> organRateParamQueryDOList = getOrganRateParamQueryDOList(month);
                //�����¶���������
                ArrayList<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetailList = getTbOrganRateScoreMonthDetailList(organRateParamQueryDOList, tbOrganRateScore.getId());

                //�������α�
                tbOrganRateScore.setUpdateTime(BocoUtils.getTime());
                tbOrganRateScoreMapper.updateByPK(tbOrganRateScore);

                //�����¶������
                tbOrganRateScoreMonthDetailMapper.insertBatch(tbOrganRateScoreMonthDetailList);

            } else {
                return;
            }
        } else {

            //�������ֲ���list
            ArrayList<OrganRateParamQueryDO> organRateParamQueryDOList = getOrganRateParamQueryDOList(month);

            OrganRateParamQueryDO organRateParamQueryDO = organRateParamQueryDOList.get(0);
            //������������
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

            //�����¶���������
            ArrayList<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetailList = getTbOrganRateScoreMonthDetailList(organRateParamQueryDOList, tbOrganRateScoreId);

            //�������α�
            tbOrganRateScoreMapper.insertEntity(tbOrganRateScore);
            //�����¶������
            tbOrganRateScoreMonthDetailMapper.insertBatch(tbOrganRateScoreMonthDetailList);

        }


    }

    //�����¶���ʷ����
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

    /*���ɼ�������*/
    @Override
    public void addQuarterOrganRateScore() throws Exception{
        //�жϵ�ǰ���������Ƿ����
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
            //���ڵ�����£������δ�ύ���߲���״̬�����и���,���Ҽ�¼��ʷ
            if (tbOrganRateScore.getRateStatus() == TbReqDetail.STATE_NEW || tbOrganRateScore.getRateStatus() == TbReqDetail.STATE_DISMISSED) {
                //��ѯ������ϸ
                TbOrganRateScoreQuarterDetail tbOrganRateScoreQuarterDetailQuery = new TbOrganRateScoreQuarterDetail();
                tbOrganRateScoreQuarterDetailQuery.setRefId(tbOrganRateScore.getId());
                List<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetails = tbOrganRateScoreQuarterDetailMapper.selectByAttr(tbOrganRateScoreQuarterDetailQuery);
                // ��ѯ��ʷ���ֵ
                List<Map<String,Object>> backupList = tbOrganRateScoreQuarterDetailBackupMapper.selectMaxHistoryNumber(tbOrganRateScore.getRateMonth());
                BigDecimal number = BigDecimal.ONE;
                if (backupList.get(0) != null ) {
                    String numberStr = backupList.get(0).get("maxnumber").toString() == null ? "0" : backupList.get(0).get("maxnumber").toString();
                    BigDecimal maxnumber = new BigDecimal(numberStr);
                    number = number.add(maxnumber);
                }
                // ������ʷ����
                List<TbOrganRateScoreQuarterDetailBackup> quarterDetailBackupList = buildQuarterHistory(tbOrganRateScoreQuarterDetails, number);
                //������ʷ���ݱ�
                tbOrganRateScoreQuarterDetailBackupMapper.insertBatch(quarterDetailBackupList);

                //ɾ��������ϸ
                tbOrganRateScoreQuarterDetailMapper.deleteByWhere("ref_id = \'" + tbOrganRateScore.getId() + "\'");
                //��ȡ���ֲ��� ���Ҽ������
                List<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetailList = getTbOrganRateScoreQuarterDetailList(tbOrganRateScore.getId(),month);
                //�������������ȫ��������ռȫ�б���
                BigDecimal organCount = new BigDecimal(tbOrganRateScoreQuarterDetailList.size() + "");
                BigDecimal grade = new BigDecimal("1");
                for (TbOrganRateScoreQuarterDetail organRateScoreQuarterDetail : tbOrganRateScoreQuarterDetailList) {
                    organRateScoreQuarterDetail.setRateRank(grade);
                    organRateScoreQuarterDetail.setRateRatio(BigDecimalUtil.divide(grade, organCount));
                    BigDecimal quarterLevel = organRateParamCalculateService.getQuarterLevel(grade, organCount);
                    organRateScoreQuarterDetail.setRateLevel(quarterLevel);
                    grade = grade.add(new BigDecimal("1"));

                }

                //�������α�
                tbOrganRateScore.setUpdateTime(BocoUtils.getTime());
                tbOrganRateScoreMapper.updateByPK(tbOrganRateScore);

                //���뼾�������
                tbOrganRateScoreQuarterDetailMapper.insertBatch(tbOrganRateScoreQuarterDetailList);

            } else {
                return;
            }
        } else {

            String tbOrganRateScoreId = IDGeneratorUtils.getSequence();

            //��ȡ���ֲ��� ���Ҽ������
            List<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetailList = getTbOrganRateScoreQuarterDetailList(tbOrganRateScoreId, month);
            //�������������ȫ��������ռȫ�б���
            BigDecimal organCount = new BigDecimal(tbOrganRateScoreQuarterDetailList.size() + "");
            BigDecimal grade = new BigDecimal("1");
            for (TbOrganRateScoreQuarterDetail organRateScoreQuarterDetail : tbOrganRateScoreQuarterDetailList) {
                organRateScoreQuarterDetail.setRateRank(grade);
                organRateScoreQuarterDetail.setRateRatio(BigDecimalUtil.divide(grade, organCount));
                BigDecimal quarterLevel = organRateParamCalculateService.getQuarterLevel(grade, organCount);
                organRateScoreQuarterDetail.setRateLevel(quarterLevel);
                grade = grade.add(new BigDecimal("1"));

            }


            //��������
            TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
            tbOrganRateScore.setId(tbOrganRateScoreId);
            tbOrganRateScore.setRateMonth(tbOrganRateScoreQuarterDetailList.get(0).getRateMonth());
            tbOrganRateScore.setRateOrgan(getSessionOrgan().getThiscode());
            tbOrganRateScore.setRateType(OrganRateParamElementType.RATE_QUARTER);
            tbOrganRateScore.setRateStatus(TbReqDetail.STATE_NEW);
            tbOrganRateScore.setReportStatus(TbReqDetail.STATE_NEW);
            tbOrganRateScore.setCreateTime(BocoUtils.getTime());
            tbOrganRateScore.setUpdateTime(BocoUtils.getTime());

            //�������α�
            tbOrganRateScoreMapper.insertEntity(tbOrganRateScore);

            //���뼾�������
            tbOrganRateScoreQuarterDetailMapper.insertBatch(tbOrganRateScoreQuarterDetailList);

        }


    }

    //����������ʷ����
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

    /*�޸��¶�����*/
    @Override
    public PlainResult<String> updateTbOrganRateScoreMonth(HttpServletRequest request, String operCode, String organCode) throws Exception {
        PlainResult<String> result = new PlainResult<>();

        try {
            String rateSorceDetailStr = request.getParameter("rateSorceDetail");
            String id = request.getParameter("id");

            //У�������ֶηǿ�
            if (StringUtils.isEmpty(id) || StringUtils.isEmpty(rateSorceDetailStr)) {
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update rateSorce param not to be null");
            }

            //��������
            TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
            tbOrganRateScore.setId(id);
            tbOrganRateScore.setUpdateOper(operCode);
            tbOrganRateScore.setUpdateTime(BocoUtils.getTime());

            //ɾ��������ϸ
            tbOrganRateScoreMonthDetailMapper.deleteByWhere("ref_id = \'" + id + "\'");
            //����������ϸ
            List<TbOrganRateScoreMonthDetail> organRateScoreMonthDetailList = buildTbOrganRateScoreMonthDetail(rateSorceDetailStr, id);

            //��������
            tbOrganRateScoreMapper.updateByPK(tbOrganRateScore);

            //����������ϸ
            tbOrganRateScoreMonthDetailMapper.insertBatch(organRateScoreMonthDetailList);

        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add rateSorce.");
        }
        return result.success("add", "add rateSorce success.");
    }

    /*�޸ļ�������*/
    @Override
    public PlainResult<String> updateTbOrganRateScoreQuarter(HttpServletRequest request, String operCode, String organCode) {
        PlainResult<String> result = new PlainResult<>();

        try {
            String rateSorceDetailStr = request.getParameter("rateSorceDetail");
            String id = request.getParameter("id");

            //У�������ֶηǿ�
            if (StringUtils.isEmpty(id) || StringUtils.isEmpty(rateSorceDetailStr)) {
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update rateSorce param not to be null");
            }

            //��������
            TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
            tbOrganRateScore.setId(id);
            tbOrganRateScore.setUpdateOper(operCode);
            tbOrganRateScore.setUpdateTime(BocoUtils.getTime());

            //ɾ��������ϸ
            tbOrganRateScoreQuarterDetailMapper.deleteByWhere("ref_id = \'" + id + "\'");
            //����������ϸ
            List<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetailList = buildTbOrganRateScoreQuarterDetail(rateSorceDetailStr, id);

            //��������
            tbOrganRateScoreMapper.updateByPK(tbOrganRateScore);

            //����������ϸ
            tbOrganRateScoreQuarterDetailMapper.insertBatch(tbOrganRateScoreQuarterDetailList);

        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add rateSorce.");
        }
        return result.success("add", "add rateSorce success.");
    }

    /*����������������*/
    @Override
    public PlainResult<String> startRateScoreAuditProcess(String id, String organCode, String operCode, String operName, String assignee, String comment) throws Exception {
        /*����key*/
        String processKey = AuditMix.RATE_SCORE;


        String msgNo = BocoUtils.getUUID();
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> varMap = new HashMap<String, Object>();
        //ҵ�����
        varMap.put("businessKey", id);
        //��һ������
        varMap.put("msgNo", msgNo);
        varMap.put("assignee", assignee);
        varMap.put("startUser", WebContextUtil.getSessionUser().getOpercode());
        varMap.put("organLevel", getSessionOrgan().getOrganlevel());
        //���������������̵���ԱID��������Զ����û�ID���浽activiti:initiator��
        identityService.setAuthenticatedUserId(WebContextUtil.getSessionUser().getOpercode());
        ProcessInstance pi = workFlowService.startProcess(processKey, varMap);
        String workFlowCode = pi.getProcessInstanceId();
        //�ύ��һ������
        Task task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), operCode + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
        workFlowService.completeTask(task.getId(), comment, null);
        //��ȡ���µ����񣬲�������ִ�ж�Ӧ������Ա
        task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), assignee);

        logger.info("�����������̱��workFlowCode��" + workFlowCode + "��");

        //�������ּ�¼״̬
        TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
        tbOrganRateScore.setId(id);
        tbOrganRateScore.setRateStatus(TbReqDetail.STATE_APPROVALING);
        tbOrganRateScore.setUpdateTime(BocoUtils.getTime());
        tbOrganRateScoreMapper.updateByPK(tbOrganRateScore);

        //��¼������Ϣ
        String url = "tbOrganRateScoreMonthPendingApp/listRateScoreDetailAuditUI.htm?id=" + id + "&taskid=" + task.getId();
        saveMsg(msgNo, operCode, assignee, url, id);
        return result.success(workFlowCode, "���������������������ɹ�");
    }

    /*��������������������*/
    @Override
    public PlainResult<String> startRateScoreAuditProcessQuarter(String id, String organCode, String operCode, String operName, String assignee, String comment) throws Exception {
        /*����key*/
        String processKey = AuditMix.RATE_SCORE;


        String msgNo = BocoUtils.getUUID();
        PlainResult<String> result = new PlainResult<>();
        Map<String, Object> varMap = new HashMap<String, Object>();
        //ҵ�����
        varMap.put("businessKey", id);
        //��һ������
        varMap.put("msgNo", msgNo);
        varMap.put("assignee", assignee);
        varMap.put("startUser", WebContextUtil.getSessionUser().getOpercode());
        varMap.put("organLevel", getSessionOrgan().getOrganlevel());
        //���������������̵���ԱID��������Զ����û�ID���浽activiti:initiator��
        identityService.setAuthenticatedUserId(WebContextUtil.getSessionUser().getOpercode());
        ProcessInstance pi = workFlowService.startProcess(processKey, varMap);
        String workFlowCode = pi.getProcessInstanceId();
        //�ύ��һ������
        Task task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), operCode + AuditMix.COLON + AuditMix.AUDIT_PASS_SUFFIX);
        workFlowService.completeTask(task.getId(), comment, null);
        //��ȡ���µ����񣬲�������ִ�ж�Ӧ������Ա
        task = workFlowService.getTaskByPid(pi.getId());
        workFlowService.claim(task.getId(), assignee);

        logger.info("�����������̱��workFlowCode��" + workFlowCode + "��");

        //�������ּ�¼״̬
        TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
        tbOrganRateScore.setId(id);
        tbOrganRateScore.setRateStatus(TbReqDetail.STATE_APPROVALING);
        tbOrganRateScore.setUpdateTime(BocoUtils.getTime());
        tbOrganRateScoreMapper.updateByPK(tbOrganRateScore);

        //��¼������Ϣ
        String url = "tbOrganRateScoreQuarterPendingApp/listRateScoreDetailAuditUI.htm?id=" + id + "&taskid=" + task.getId();
        saveMsg(msgNo, operCode, assignee, url, id);
        return result.success(workFlowCode, "���������������������ɹ�");
    }


    /*��ѯ���ύ������*/
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

    /*��ѯ������������*/
    @Override
    public List<Map<String, Object>> getPendingAppReq(String sort, String operCode, String rateMonth, String auditStatus, Pager pager, int rateType) throws Exception {
        //��������б�
        List<Map<String, Object>> tastList = new ArrayList<Map<String, Object>>();
        //��ѯ��¼�û���������
        /*����key*/
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
        //��ȡ�Ƿ�ͬ��
        String isAgree = (String) varMap.get("isAgree");
        //��ȡ��ǰ�����Ӧ������ʵ��
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskId);
        if (processInstance != null) {
            //��ȡ��ǰ����ʵ����Ӧ������ʵ��id
            //��װ�µ�����ʵ��id
            msgMap.put("nextProcessInstanceId", processInstance.getId());
        } else {
            msgMap.put("nextProcessInstanceId", "");
        }

        TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
        tbOrganRateScore.setId(msgMap.get("id").toString());
        //Ĭ��������ͨ���������ж��Ƿ����һ��������������Ϊ������
        tbOrganRateScore.setRateStatus(TbReqDetail.STATE_APPROVED);

        if ("0".equals(isAgree)) {//����
            tbOrganRateScore.setRateStatus(TbReqDetail.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String) msgMap.get("id"), taskId, (String) msgMap.get("custType"));
        } else {
            ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
            List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
            if (pvmList != null && pvmList.size() > 0) {
                for (PvmTransition pvm : pvmList) {
                    PvmActivity act = pvm.getDestination();
                    //��������صĻ���ͨ�����ػ�ȡ��һ���ڵ������
                    if ("Exclusive Gateway".equals(act.getProperty("name"))) {
                        List<PvmTransition> actList = act.getOutgoingTransitions();
                        if (actList != null && actList.size() > 0) {
                            for (PvmTransition gwt : actList) {
                                PvmActivity gw = gwt.getDestination();
                                //������ӵ���һ���ڵ������ΪEnd���򷵻�true
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

        //��ת����
        workFlowService.completeTask(taskId, comment, varMap);
        //��ȡ���µ����񣬲�������ִ�ж�Ӧ������Ա
        Task task = workFlowService.getTaskByPid(processInstance.getId());
        if ("0".equals(isAgree)) {
            //��ȡ���̷�����
            String opercode = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
            workFlowService.claim(task.getId(), opercode);
        } else {
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                workFlowService.claim(task.getId(), (String) msgMap.get("assignee"));
            }
        }
        //��¼������Ϣ
        String msgNo = BocoUtils.getUUID();
        String operCode = String.valueOf(msgMap.get("operCode"));
        String assignee = String.valueOf(msgMap.get("assignee"));
        String id = String.valueOf(msgMap.get("id"));

        if ("1".equals(isAgree) && "true".equals((String) msgMap.get("lastUserType"))) {
            deleteMsg(id);
        } else {
            String url = "";
            if ("0".equals(isAgree)) {
                //��ȡ���̷�����
                assignee = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());

                url = "tbOrganRateScoreMonthReject/rateScoreResubmitAuditUI.htm?id=" + id + "&taskid=" + task.getId();
            } else if (!"true".equals((String) msgMap.get("lastUserType"))) {
                url = "tbOrganRateScoreMonthPendingApp/listRateScoreDetailAuditUI.htm?id=" + id + "&taskid=" + task.getId();
            }
            saveMsg(msgNo, operCode, assignee, url, id + "");
        }

        //�¶�����������ɣ����ɼ�������
        if ("1".equals(varMap.get("isAgree")) && ("true".equals((String)msgMap.get("lastUserType")))) {
            addQuarterOrganRateScore();
        }
    }

    @Override
    public void completeTaskAuditQuarter(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception {
        //��ȡ�Ƿ�ͬ��
        String isAgree = (String) varMap.get("isAgree");
        //��ȡ��ǰ�����Ӧ������ʵ��
        ProcessInstance processInstance = workFlowService.getProcessInstanceByTaskId(taskId);
        if (processInstance != null) {
            //��ȡ��ǰ����ʵ����Ӧ������ʵ��id
            //��װ�µ�����ʵ��id
            msgMap.put("nextProcessInstanceId", processInstance.getId());
        } else {
            msgMap.put("nextProcessInstanceId", "");
        }

        TbOrganRateScore tbOrganRateScore = new TbOrganRateScore();
        tbOrganRateScore.setId(msgMap.get("id").toString());
        //Ĭ��������ͨ���������ж��Ƿ����һ��������������Ϊ������
        tbOrganRateScore.setRateStatus(TbReqDetail.STATE_APPROVED);

        if ("0".equals(isAgree)) {//����
            tbOrganRateScore.setRateStatus(TbReqDetail.STATE_DISMISSED);
            webTaskRoleInfoNewService.updateProdStatus((String) msgMap.get("id"), taskId, (String) msgMap.get("custType"));
        } else {
            ActivityImpl activityImpl = workFlowService.getActivityImplByTaskId(taskId);
            List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
            if (pvmList != null && pvmList.size() > 0) {
                for (PvmTransition pvm : pvmList) {
                    PvmActivity act = pvm.getDestination();
                    //��������صĻ���ͨ�����ػ�ȡ��һ���ڵ������
                    if ("Exclusive Gateway".equals(act.getProperty("name"))) {
                        List<PvmTransition> actList = act.getOutgoingTransitions();
                        if (actList != null && actList.size() > 0) {
                            for (PvmTransition gwt : actList) {
                                PvmActivity gw = gwt.getDestination();
                                //������ӵ���һ���ڵ������ΪEnd���򷵻�true
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

        //��ת����
        workFlowService.completeTask(taskId, comment, varMap);
        //��ȡ���µ����񣬲�������ִ�ж�Ӧ������Ա
        Task task = workFlowService.getTaskByPid(processInstance.getId());
        if ("0".equals(isAgree)) {
            //��ȡ���̷�����
            String opercode = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());
            workFlowService.claim(task.getId(), opercode);
        } else {
            if (!"true".equals((String) msgMap.get("lastUserType"))) {
                workFlowService.claim(task.getId(), (String) msgMap.get("assignee"));
            }
        }
        //��¼������Ϣ
        String msgNo = BocoUtils.getUUID();
        String operCode = String.valueOf(msgMap.get("operCode"));
        String assignee = String.valueOf(msgMap.get("assignee"));
        String id = String.valueOf(msgMap.get("id"));

        if ("1".equals(isAgree) && "true".equals((String) msgMap.get("lastUserType"))) {
            deleteMsg(id);
        } else {
            String url = "";
            if ("0".equals(isAgree)) {
                //��ȡ���̷�����
                assignee = tbReqDetailMapper.getStartWorkFlowPeople(processInstance.getId());

                url = "tbOrganRateScoreQuarterReject/rateScoreResubmitAuditUI.htm?id=" + id + "&taskid=" + task.getId();
            } else if (!"true".equals((String) msgMap.get("lastUserType"))) {
                url = "tbOrganRateScoreQuarterPendingApp/listRateScoreDetailAuditUI.htm?id=" + id + "&taskid=" + task.getId();
            }
            saveMsg(msgNo, operCode, assignee, url, id + "");
        }
    }

    /*��ѯ������������*/
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


    //�����¶���������
    private ArrayList<TbOrganRateScoreMonthDetail> getTbOrganRateScoreMonthDetailList(ArrayList<OrganRateParamQueryDO> organRateParamQueryDOList, String tbOrganRateScoreId) {
        ArrayList<TbOrganRateScoreMonthDetail> tbOrganRateScoreMonthDetailList = new ArrayList<>();
        //��ʼ��������
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
            //��������÷� ������ = ��ĩ������ - ���������
            monthDetail.setBadConditionRatioMonthEnd(monthEndRatio);
            monthDetail.setBadConditionRatioYearBegin(yearBeginRatio);
            monthDetail.setBadConditionRatioChange(BigDecimalUtil.subtract(monthEndRatio, yearBeginRatio));
            BigDecimal badConditionScore = organRateParamCalculateService.getBadCondition(monthEndRatio, yearBeginRatio);
            monthDetail.setBadConditionScore(badConditionScore);
            //��Ӫ��������ȵ÷� ������������� /��������Ӫ��������� + ��˾�����������
            monthDetail.setLoanYearAdd(loanYearIncrement);
            monthDetail.setPersonalDepositYearIncrement(personDepositYearIncrement);
            monthDetail.setCompanyDepositYearIncrement(companyDepositYearIncrement);
            monthDetail.setDepositLoanRatioChange(BigDecimalUtil.divide(loanYearIncrement, BigDecimalUtil.add(personDepositYearIncrement, companyDepositYearIncrement)));
            BigDecimal depositLoanRatioScore = organRateParamCalculateService.getDepositLoanRatio(loanYearIncrement, personDepositYearIncrement, companyDepositYearIncrement);
            monthDetail.setDepositLoanRatioScore(depositLoanRatioScore);
            //�·����������ʵ÷�  �������ʲ� = �·����������� - ȫ��ƽ����������
            monthDetail.setNewLoanRatio(newLoanRatio);
            monthDetail.setBankAverageLoanRatio(bankAverageLoanRatio);
            monthDetail.setNewLoanRatioChange(newLoanRatio.subtract(bankAverageLoanRatio));
            BigDecimal newLoanRatioScore = organRateParamCalculateService.getNewLoanRatio(newLoanRatio, bankAverageLoanRatio);
            monthDetail.setNewLoanRatioScore(newLoanRatioScore);
            //����ṹ�÷� ��ĩʵ��������ռ�� = ��ĩʵ��������/�ܴ��� - �³�ʵ��������/�ܴ���
            monthDetail.setLoanCount(loanCount);
            monthDetail.setMonthBeginEntityLoan(monthBeginEntityLoan);
            monthDetail.setMonthEndEntityLoan(monthEndEntityLoan);
            monthDetail.setMonthBeginEntityLoanChange(BigDecimalUtil.divide(monthBeginEntityLoan, loanCount));
            monthDetail.setMonthEndEntityLoanChange(BigDecimalUtil.divide(monthEndEntityLoan, loanCount));
            monthDetail.setMonthEndBeginEntityLoanChange(BigDecimalUtil.divide(monthEndEntityLoan, loanCount).subtract(BigDecimalUtil.divide(monthBeginEntityLoan, loanCount)));
            BigDecimal loanStructScore = organRateParamCalculateService.getLoanStruct(monthBeginEntityLoan, monthEndEntityLoan, loanCount);
            monthDetail.setLoanStructScore(loanStructScore);
            //����ģռ�÷Ѻ͹�ģ���÷ѵ÷�  ���з�Ϣռ�� =  ���з�Ϣ��� / ȫ�з�Ϣ���
            monthDetail.setOrganScaleAmount(organScaleAmount);
            monthDetail.setBankScaleAmount(bankScaleAmount);
            monthDetail.setScaleAmountChange(BigDecimalUtil.divide(organScaleAmount, bankScaleAmount));
            BigDecimal scaleAmountScore = organRateParamCalculateService.getScaleAmount(organScaleAmount, bankScaleAmount);
            monthDetail.setScaleAmountScore(scaleAmountScore);
            //�ƻ�����ƫ��ȵ÷�  ƫ����� = ����ĩʵ�������� - �³��������� / ��ĩʵ��������
            monthDetail.setMonthEndIncrement(monthEndIncrement);
            monthDetail.setMonthBeginReport(monthBeginReport);
            monthDetail.setPlanSubmitDeviationChange(BigDecimalUtil.divide(monthEndIncrement.subtract(monthBeginReport), monthEndIncrement));
            BigDecimal planSubmitDeviationScore = organRateParamCalculateService.getPlanSubmitDeviation(monthEndIncrement, monthBeginReport);
            monthDetail.setPlanSubmitDeviationScore(planSubmitDeviationScore);
            //�ƻ�ִ��ƫ���--��ĩ����ƫ��ȵ÷�   ƫ�����=����ĩʵ��������-����ͳһ��̬������ƻ���/��ĩͳһ��̬������ƻ�
            monthDetail.setMonthMidPlan(monthMidPlan);
            monthDetail.setMonthEndPlan(monthEndPlan);
            monthDetail.setPlanExecuteDeviationMonthMidChange(BigDecimalUtil.divide(monthEndIncrement.subtract(monthMidPlan), monthEndPlan));
            BigDecimal planExecuteDeviationMonthMidScore = organRateParamCalculateService.getPlanExecuteDeviationMonthMid(monthEndIncrement, monthMidPlan, monthEndPlan);
            monthDetail.setPlanExecuteDeviationMonthMidScore(planExecuteDeviationMonthMidScore);
            //�ƻ�ִ��ƫ���--��ĩ�³�ƫ��ȵ÷� ƫ�����=����ĩʵ��������-�³������´�ƻ���/�³������´�ƻ�
            monthDetail.setMonthBeginPlan(monthBeginPlan);
            monthDetail.setPlanExecuteDeviationMonthInitChange(BigDecimalUtil.divide(monthEndIncrement.subtract(monthBeginPlan), monthBeginPlan));
            BigDecimal planExecuteDeviationMonthInitScore = organRateParamCalculateService.getPlanExecuteDeviationMonthInit(monthEndIncrement, monthBeginPlan);
            monthDetail.setPlanExecuteDeviationMonthInitScore(planExecuteDeviationMonthInitScore);
            //����Ƶ�ε÷�
            monthDetail.setAdjCount(adjCount);
            BigDecimal adjCountScore = organRateParamCalculateService.getAdjCount(adjCount);
            monthDetail.setAdjCountScore(adjCountScore);
            //�ӷ���
            monthDetail.setAddScore(BigDecimal.ZERO);

            //�����ܷ�
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

    //����������Ҫ������
    private ArrayList<OrganRateParamQueryDO> getOrganRateParamQueryDOList(String month) throws Exception {
        ArrayList<OrganRateParamQueryDO> organRateParamQueryDOList = new ArrayList<>();

        Random r = new Random();

        //��ѯ��Ҫ����������

        // ��ĩ������	 ���������  ������Ӫ���������  ��˾��������� �·�����������	ȫ��ƽ���������� ---�ⲿ���������
        HashMap<String, TbOrganRateScoreImportData> importDataMap = buildImportDataMap(month);
        // �������������
        HashMap<String, BigDecimal> loanYearIncrementMap = buildLoanYearIncrementMap(month);
        // �³�ʵ��������
        HashMap<String, Map<String, BigDecimal>> monthBeginMap = buildMonthEndEntityLoanMap(Integer.valueOf(month) - 1 + "");
        Map<String, BigDecimal> monthBeginEntityLoanMap = monthBeginMap.get("monthEndEntityLoanMap");
        // ��ĩʵ��������
        HashMap<String, Map<String, BigDecimal>> monthEndMap = buildMonthEndEntityLoanMap(month);
        Map<String, BigDecimal> monthEndEntityLoanMap = monthEndMap.get("monthEndEntityLoanMap");
        // �ܴ������
        Map<String, BigDecimal> loanCountMap = monthEndMap.get("loanCountMap");
        //���з�Ϣ���
        HashMap<String, BigDecimal> organScaleAmountMap = buildOrganScaleAmountMap(month);
        //ȫ�з�Ϣ���
        BigDecimal bankScaleAmount = new BigDecimal("0");
        for (String key : organScaleAmountMap.keySet()) {
            bankScaleAmount = bankScaleAmount.add(organScaleAmountMap.get(key));
        }
        //�³���������
        Map<String, BigDecimal> monthBeginReportMap = buildMonthBeginReportMap(month);
        //��ĩʵ��������
        HashMap<String, BigDecimal> monthEndIncrementMap = buildMonthEndIncrementMap(month);
        //����ͳһ��̬������ƻ�
        HashMap<String, BigDecimal> monthMidPlanMap = buildMonthMidPlanMap(month);
        //��ĩͳһ��̬������ƻ�
        HashMap<String, BigDecimal> monthEndPlanMap = buildMonthEndPlanMap(month);
        //�³������´�ƻ�
        HashMap<String, BigDecimal> monthBeginPlanMap = buildMonthBeginPlanMap(month);
        // ����ͳһ��̬�����ƻ�֪ͨ���к���ж���ĵ�������  ���޶�--����ר��
        Map<String, Integer> adjCountMap = getAdjCountMap(month);


        //��ѯ����
        List<Map<String, Object>> organList = fdOrganService.selectByUporgan(getSessionOrgan().getThiscode());

        //��װ��������
        for (Map<String, Object> organ : organList) {
            String organCode = organ.get("thiscode").toString();

            TbOrganRateScoreImportData importData = importDataMap.get(organCode);
            if (importData == null) {
                importData = new TbOrganRateScoreImportData();
            }

            OrganRateParamQueryDO organRateParamQueryDO = new OrganRateParamQueryDO();
            organRateParamQueryDO.setOrganCode(organCode);
            organRateParamQueryDO.setMonth(month);
            // ��ĩ������ monthEndRatio;
            organRateParamQueryDO.setMonthEndRatio(getSafeCount(importData.getMonthEndRatio()));
            // ��������� earBeginRatio;
            organRateParamQueryDO.setYearBeginRatio(getSafeCount(importData.getYearBeginRatio()));
            // ������������� loanYearIncrement;
            organRateParamQueryDO.setLoanYearIncrement(getSafeCount(loanYearIncrementMap.get(organCode)));
            // ������Ӫ��������� personDepositYearIncrement;
            organRateParamQueryDO.setPersonDepositYearIncrement(getSafeCount(importData.getPersonDepositYearIncrement()));
            // ��˾��������� companyDepositYearIncrement;
            organRateParamQueryDO.setCompanyDepositYearIncrement(getSafeCount(importData.getCompanyDepositYearIncrement()));
            // �·����������� newLoanRatio;
            organRateParamQueryDO.setNewLoanRatio(getSafeCount(importData.getNewLoanRatio()));
            // ȫ��ƽ���������� BankAverageLoanRatio;
            organRateParamQueryDO.setBankAverageLoanRatio(getSafeCount(importData.getBankAverageLoanRatio()));
            // �³�ʵ�������� monthBeginEntityLoan;
            organRateParamQueryDO.setMonthBeginEntityLoan(getSafeCount(monthBeginEntityLoanMap.get(organCode)));
            // ��ĩʵ�������� monthEndEntityLoan;
            organRateParamQueryDO.setMonthEndEntityLoan(getSafeCount(monthEndEntityLoanMap.get(organCode)));
            // �ܴ������ loanCount;
            organRateParamQueryDO.setLoanCount(getSafeCount(loanCountMap.get(organCode)));
            // ���з�Ϣ��� organScaleAmount;
            organRateParamQueryDO.setOrganScaleAmount(getSafeCount(organScaleAmountMap.get(organCode)));
            // ȫ�з�Ϣ��� bankScaleAmount;
            organRateParamQueryDO.setBankScaleAmount(getSafeCount(bankScaleAmount));
            // ��ĩʵ��������  monthEndIncrement;
            organRateParamQueryDO.setMonthEndIncrement(getSafeCount(monthEndIncrementMap.get(organCode)));
            // �³��������� monthBeginReport;
            organRateParamQueryDO.setMonthBeginReport(getSafeCount(monthBeginReportMap.get(organCode)));
            // ����ͳһ��̬������ƻ� monthMidPlan;
            organRateParamQueryDO.setMonthMidPlan(getSafeCount(monthMidPlanMap.get(organCode)));
            // ��ĩͳһ��̬������ƻ� MonthEndPlan;
            organRateParamQueryDO.setMonthEndPlan(getSafeCount(monthEndPlanMap.get(organCode)));
            // �³������´�ƻ� monthBeginPlan;
            organRateParamQueryDO.setMonthBeginPlan(getSafeCount(monthBeginPlanMap.get(organCode)));
            // ����ͳһ��̬�����ƻ�֪ͨ���к���ж���ĵ������� adjCount;
            organRateParamQueryDO.setAdjCount(getSafeCount(adjCountMap.get(organCode)));

            organRateParamQueryDOList.add(organRateParamQueryDO);
        }
        return organRateParamQueryDOList;
    }

    //���������
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

    //�������������
    private HashMap<String, BigDecimal> buildLoanYearIncrementMap(String month) {

        //���ֻ���-һ�ֻ���
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setOrganlevel("2");
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(fdOrgan);
        HashMap<String, String> organMap = new HashMap<>();
        for (FdOrgan organ : fdOrganList) {
            organMap.put(organ.getThiscode(), organ.getUporgan());
        }


        //����--����balance��ֵ
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

        //����-�������
        HashMap<String, BigDecimal> rptBaseInfoMapOfMonth = new HashMap<>();
        for (TbRptBaseinfo rptBaseinfo : tbRptBaseinfoList) {
            BigDecimal amountCount = rptBaseInfoMapOfMonth.get(rptBaseinfo.getOrgan());
            if (amountCount == null) {
                amountCount = BigDecimal.ZERO;
            }
            amountCount = amountCount.add(new BigDecimal(rptBaseinfo.getBalance() + ""));
            rptBaseInfoMapOfMonth.put(rptBaseinfo.getOrgan(), amountCount);
        }
        //һ�ֻ���-�������
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


        //����--ȥ�����balance��ֵ
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

        //����-�������
        HashMap<String, BigDecimal> rptBaseInfoMapOfYear = new HashMap<>();
        for (TbRptBaseinfo rptBaseinfo : tbRptBaseinfoListLastYear) {
            BigDecimal amountCount = rptBaseInfoMapOfYear.get(rptBaseinfo.getOrgan());
            if (amountCount == null) {
                amountCount = BigDecimal.ZERO;
            }
            amountCount = amountCount.add(new BigDecimal(rptBaseinfo.getBalance() + ""));
            rptBaseInfoMapOfYear.put(rptBaseinfo.getOrgan(), amountCount);
        }
        //һ�ֻ���-�������
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


        //����--����������
        HashMap<String, BigDecimal> loanYearIncrementMap = new HashMap<>();
        for (String organ : monthBalanceMap.keySet()) {
            BigDecimal monthBalance = monthBalanceMap.get(organ);
            BigDecimal yearBalance = lastYearBalanceMap.get(organ) == null ? BigDecimal.ZERO : lastYearBalanceMap.get(organ);
            loanYearIncrementMap.put(organ, BigDecimalUtil.subtract(monthBalance, yearBalance));
        }

        return loanYearIncrementMap;
    }

    // ��ĩʵ��������    �ܴ������
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

        //����-<���֣����>
        HashMap<String, Map<String, BigDecimal>> rptBaseInfoMap = new HashMap<>();
        for (TbRptBaseinfo rptBaseinfo : tbRptBaseinfoList) {

            Map<String, BigDecimal> loanKindAmountMap = rptBaseInfoMap.get(rptBaseinfo.getOrgan());
            if (loanKindAmountMap == null) {
                loanKindAmountMap = new HashMap<>();
            }
            loanKindAmountMap.put(rptBaseinfo.getLoanKind(), new BigDecimal(rptBaseinfo.getBalance() + ""));
            rptBaseInfoMap.put(rptBaseinfo.getOrgan(), loanKindAmountMap);
        }

        //��ѯ���е���С������ һ������������������
        List<Map<String, Object>> combList = tbOrganRateScoreMapper.selectLoanKindOfTwo();
        HashMap<String, String> combMap = new HashMap<>();
        for (Map<String, Object> map : combList) {
            combMap.put(map.get("combcode") + "", map.get("combname") + "");
        }

        //���ֻ���-һ�ֻ���
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setOrganlevel("2");
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(fdOrgan);
        Map<String, String> organMap = new HashMap<>();
        for (FdOrgan organ : fdOrganList) {
            organMap.put(organ.getThiscode(), organ.getUporgan());
        }

        //��ĩʵ�������� monthEndEntityLoan
        Map<String, BigDecimal> monthEndEntityLoanMap = new HashMap<>();
        //�ܴ��� loanCount
        Map<String, BigDecimal> loanCountMap = new HashMap<>();

        for (String organ : rptBaseInfoMap.keySet()) {
            String upOrgan = organMap.get(organ);
            if (upOrgan == null) {
                upOrgan = organ;
            }

            //ʵ��������
            BigDecimal combAmountCount = monthEndEntityLoanMap.get(upOrgan);
            if (combAmountCount == null) {
                combAmountCount = BigDecimal.ZERO;
            }

            //�ܴ������
            BigDecimal amountCount = loanCountMap.get(upOrgan);
            if (amountCount == null) {
                amountCount = BigDecimal.ZERO;
            }

            Map<String, BigDecimal> organCombAmountMap = rptBaseInfoMap.get(organ);
            for (String comb : organCombAmountMap.keySet()) {
                //���������ʵ����֣������
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

    // ����ͳһ��̬�����ƻ�֪ͨ���к���ж���ĵ�������  ���޶�--����ר��
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

    //���з�Ϣ���
    private HashMap<String, BigDecimal> buildOrganScaleAmountMap(String month) {
        TbPunishDetail tbPunishDetail = new TbPunishDetail();
        tbPunishDetail.setMonth(month);
        tbPunishDetail.setLeftDay(new BigDecimal("0"));
        List<TbPunishDetail> punishDetailList = tbPunishDetailMapper.selectByAttr(tbPunishDetail);

        //���з�Ϣ���
        HashMap<String, BigDecimal> organScaleAmountMap = new HashMap<>();
        for (TbPunishDetail punishDetail : punishDetailList) {

            //��������
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

    //�³������´�ƻ�
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

    //��ĩͳһ��̬������ƻ�
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


    //����ͳһ��̬������ƻ�
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


    //��ĩʵ��������
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

        //����-���
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

        //���ֻ���-һ�ֻ���
        FdOrgan fdOrgan = new FdOrgan();
        fdOrgan.setOrganlevel("2");
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr(fdOrgan);
        HashMap<String, String> organMap = new HashMap<>();
        for (FdOrgan organ : fdOrganList) {
            organMap.put(organ.getThiscode(), organ.getUporgan());
        }


        //һ�ֻ���-��ĩʵ��������
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

    //�³���������
    private Map<String, BigDecimal> buildMonthBeginReportMap(String month) throws Exception {
        TbReqList searchTbReqList = new TbReqList();
        searchTbReqList.setReqMonth(month);//ͨ��Ψһ�·� ��tbreqList�� reqId
        List<TbReqList> lists = tbReqListService.selectByAttr(searchTbReqList);
        int reqId = 0;
        if (lists != null && lists.size() == 1) {
            reqId = lists.get(0).getReqId();
        }
        TbReqDetail searchTbReqDetail = new TbReqDetail();
        searchTbReqDetail.setReqdRefId(reqId);
        searchTbReqDetail.setReqdState(TbReqDetail.STATE_APPROVED);//reqid �õ� ������������������
        List<TbReqDetail> tbReqDetailList = tbReqDetailService.selectByAttr(searchTbReqDetail);

        Map<String, BigDecimal> monthBeginReportMap = new HashMap<>(36);
        for (TbReqDetail tbReqDetail : tbReqDetailList) {
            String organCode = tbReqDetail.getReqdOrgan(); //��ȡ�� ÿ����������Ļ��� ��key
            BigDecimal totalReqNum = monthBeginReportMap.get(organCode);//map key:organCode;value ÿ���������ۼ���ֵ
            if (null == totalReqNum) {
                totalReqNum = BigDecimal.ZERO;
            }
            totalReqNum = totalReqNum.add(tbReqDetail.getReqdReqnum());
            monthBeginReportMap.put(organCode, totalReqNum);
        }
        return monthBeginReportMap;
    }


    //�����������ֲ�����
    private List<TbOrganRateScoreQuarterDetail> getTbOrganRateScoreQuarterDetailList(String tbOrganRateScoreId, String month) throws Exception {

        ArrayList<TbOrganRateScoreQuarterDetail> tbOrganRateScoreQuarterDetailList = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar c = Calendar.getInstance();

        //��ѯ�ü��ȵ�һ���µ�����
        String mon1 = Integer.valueOf(month)-2+"";
        HashMap<String, Object> queryMap1 = new HashMap<>();
        queryMap1.put("rateMonth", mon1);
        queryMap1.put("rateType", OrganRateParamElementType.RATE_MONTH);
        List<TbOrganRateScoreMonthDetail> organrateList1 = tbOrganRateScoreMonthDetailMapper.selectOrganRateScoreDetailByMonthAndType(queryMap1);
        HashMap<String, TbOrganRateScoreMonthDetail> organrateMap1 = new HashMap<>();
        for (TbOrganRateScoreMonthDetail tbOrganRateScoreMonthDetail : organrateList1) {
            organrateMap1.put(tbOrganRateScoreMonthDetail.getRateOrgan(), tbOrganRateScoreMonthDetail);
        }
        //��ѯ�ü��ȵڶ����µ�����
        String mon2 = Integer.valueOf(month)-1+"";
        HashMap<String, Object> queryMap2 = new HashMap<>();
        queryMap1.put("rateMonth", mon2);
        queryMap1.put("rateType", OrganRateParamElementType.RATE_MONTH);
        List<TbOrganRateScoreMonthDetail> organrateList2 = tbOrganRateScoreMonthDetailMapper.selectOrganRateScoreDetailByMonthAndType(queryMap1);
        HashMap<String, TbOrganRateScoreMonthDetail> organrateMap2 = new HashMap<>();
        for (TbOrganRateScoreMonthDetail tbOrganRateScoreMonthDetail : organrateList2) {
            organrateMap2.put(tbOrganRateScoreMonthDetail.getRateOrgan(), tbOrganRateScoreMonthDetail);
        }
        //��ѯ�ü��ȵ������µ�����
        String mon3 = month;
        HashMap<String, Object> queryMap3 = new HashMap<>();
        queryMap1.put("rateMonth", mon3);
        queryMap1.put("rateType", OrganRateParamElementType.RATE_MONTH);
        List<TbOrganRateScoreMonthDetail> organrateList3 = tbOrganRateScoreMonthDetailMapper.selectOrganRateScoreDetailByMonthAndType(queryMap1);
        HashMap<String, TbOrganRateScoreMonthDetail> organrateMap3 = new HashMap<>();
        for (TbOrganRateScoreMonthDetail tbOrganRateScoreMonthDetail : organrateList3) {
            organrateMap3.put(tbOrganRateScoreMonthDetail.getRateOrgan(), tbOrganRateScoreMonthDetail);
        }


        //��ѯ����
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

        //��װ����
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

            //����������ȵ÷�
            organDetail.setBadConditionScoreOne(getSafeCount(rateScore1.getBadConditionScore()));
            organDetail.setBadConditionScoreTwo(getSafeCount(rateScore2.getBadConditionScore()));
            organDetail.setBadConditionScoreThree(getSafeCount(rateScore3.getBadConditionScore()));
            organDetail.setBadConditionScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getBadConditionScore()),
                    getSafeCount(rateScore2.getBadConditionScore()), getSafeCount(rateScore3.getBadConditionScore())));
            //��Ӫ��������ȼ��ȵ÷�
            organDetail.setDepositLoanRatioScoreOne(getSafeCount(rateScore1.getDepositLoanRatioScore()));
            organDetail.setDepositLoanRatioScoreTwo(getSafeCount(rateScore2.getDepositLoanRatioScore()));
            organDetail.setDepositLoanRatioScoreThree(getSafeCount(rateScore3.getDepositLoanRatioScore()));
            organDetail.setDepositLoanRatioScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getDepositLoanRatioScore()),
                    getSafeCount(rateScore2.getDepositLoanRatioScore()), getSafeCount(rateScore3.getDepositLoanRatioScore())));
            //�·����������ʼ��ȵ÷�
            organDetail.setNewLoanRatioScoreOne(getSafeCount(rateScore1.getNewLoanRatioScore()));
            organDetail.setNewLoanRatioScoreTwo(getSafeCount(rateScore2.getNewLoanRatioScore()));
            organDetail.setNewLoanRatioScoreThree(getSafeCount(rateScore3.getNewLoanRatioScore()));
            organDetail.setNewLoanRatioScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getNewLoanRatioScore()),
                    getSafeCount(rateScore2.getNewLoanRatioScore()), getSafeCount(rateScore3.getNewLoanRatioScore())));
            //����ṹ���ȵ÷�
            organDetail.setLoanStructScoreOne(getSafeCount(rateScore1.getLoanStructScore()));
            organDetail.setLoanStructScoreTwo(getSafeCount(rateScore2.getLoanStructScore()));
            organDetail.setLoanStructScoreThree(getSafeCount(rateScore3.getLoanStructScore()));
            organDetail.setLoanStructScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getLoanStructScore()),
                    getSafeCount(rateScore2.getLoanStructScore()), getSafeCount(rateScore3.getLoanStructScore())));
            //����ģռ�÷Ѻ͹�ģ���÷Ѽ��ȵ÷�
            organDetail.setScaleAmountScoreOne(getSafeCount(rateScore1.getScaleAmountScore()));
            organDetail.setScaleAmountScoreTwo(getSafeCount(rateScore2.getScaleAmountScore()));
            organDetail.setScaleAmountScoreThree(getSafeCount(rateScore3.getScaleAmountScore()));
            organDetail.setScaleAmountScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getScaleAmountScore()),
                    getSafeCount(rateScore2.getScaleAmountScore()), getSafeCount(rateScore3.getScaleAmountScore())));
            //�ƻ�����ƫ��ȼ��ȵ÷�
            organDetail.setPlanSubmitDeviationScoreOne(getSafeCount(rateScore1.getPlanSubmitDeviationScore()));
            organDetail.setPlanSubmitDeviationScoreTwo(getSafeCount(rateScore2.getPlanSubmitDeviationScore()));
            organDetail.setPlanSubmitDeviationScoreThree(getSafeCount(rateScore3.getPlanSubmitDeviationScore()));
            organDetail.setPlanSubmitDeviationScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getPlanSubmitDeviationScore()),
                    getSafeCount(rateScore2.getPlanSubmitDeviationScore()), getSafeCount(rateScore3.getPlanSubmitDeviationScore())));
            //�ƻ�ִ��ƫ�����ĩ����ƫ��ȼ��ȵ÷�
            organDetail.setPlanExecuteDeviationMonthMidScoreOne(getSafeCount(rateScore1.getPlanExecuteDeviationMonthMidScore()));
            organDetail.setPlanExecuteDeviationMonthMidScoreTwo(getSafeCount(rateScore2.getPlanExecuteDeviationMonthMidScore()));
            organDetail.setPlanExecuteDeviationMonthMidScoreThree(getSafeCount(rateScore3.getPlanExecuteDeviationMonthMidScore()));
            organDetail.setPlanExecuteDeviationMonthMidScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getPlanExecuteDeviationMonthMidScore()),
                    getSafeCount(rateScore2.getPlanExecuteDeviationMonthMidScore()), getSafeCount(rateScore3.getPlanExecuteDeviationMonthMidScore())));
            //�ƻ�ִ��ƫ�����ĩ�³�ƫ��ȼ��ȵ÷�
            organDetail.setPlanExecuteDeviationMonthInitScoreOne(getSafeCount(rateScore1.getPlanExecuteDeviationMonthInitScore()));
            organDetail.setPlanExecuteDeviationMonthInitScoreTwo(getSafeCount(rateScore2.getPlanExecuteDeviationMonthInitScore()));
            organDetail.setPlanExecuteDeviationMonthInitScoreThree(getSafeCount(rateScore3.getPlanExecuteDeviationMonthInitScore()));
            organDetail.setPlanExecuteDeviationMonthInitScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getPlanExecuteDeviationMonthInitScore()),
                    getSafeCount(rateScore2.getPlanExecuteDeviationMonthInitScore()), getSafeCount(rateScore3.getPlanExecuteDeviationMonthInitScore())));
            //����Ƶ�μ��ȵ÷�
            organDetail.setAdjCountScoreOne(getSafeCount(rateScore1.getAdjCountScore()));
            organDetail.setAdjCountScoreTwo(getSafeCount(rateScore2.getAdjCountScore()));
            organDetail.setAdjCountScoreThree(getSafeCount(rateScore3.getAdjCountScore()));
            organDetail.setAdjCountScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getAdjCountScore()),
                    getSafeCount(rateScore2.getAdjCountScore()), getSafeCount(rateScore3.getAdjCountScore())));
            //δ��������ģռ�÷Ѽ��ȵ÷�
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
            //����Ƶ��δ�۷�ָ�����ȵ÷�
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
            //��ʷ�ӷ���
            organDetail.setAddScoreOne(getSafeCount(rateScore1.getAddScore()));
            organDetail.setAddScoreTwo(getSafeCount(rateScore2.getAddScore()));
            organDetail.setAddScoreThree(getSafeCount(rateScore3.getAddScore()));
            organDetail.setAddScoreQuarter(getQuarterScore(getSafeCount(rateScore1.getAddScore()),
                    getSafeCount(rateScore2.getAddScore()), getSafeCount(rateScore3.getAddScore())));


            //�����ܷ�
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

        //��������
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

    //���㼾�ȵ÷�
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
     * �����¶�������ϸ����
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

            //���ҳ��Ϊ��ֵ����Ĭ��Ϊ0����ע����
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
            //��������÷� ������ = ��ĩ������ - ���������
            monthDetail.setBadConditionScore(new BigDecimal(ratemap.get("badConditionScore").toString()));
            monthDetail.setBadConditionRatioMonthEnd(new BigDecimal(ratemap.get("badConditionRatioMonthEnd").toString()));
            monthDetail.setBadConditionRatioYearBegin(new BigDecimal(ratemap.get("badConditionRatioYearBegin").toString()));
            monthDetail.setBadConditionRatioChange(new BigDecimal(ratemap.get("badConditionRatioChange").toString()));
            //��Ӫ��������ȵ÷� ��Ӫ���������=������������� /��������Ӫ��������� + ��˾�����������
            monthDetail.setDepositLoanRatioScore(new BigDecimal(ratemap.get("depositLoanRatioScore").toString()));
            monthDetail.setLoanYearAdd(new BigDecimal(ratemap.get("loanYearAdd").toString()));
            monthDetail.setPersonalDepositYearIncrement(new BigDecimal(ratemap.get("personalDepositYearIncrement").toString()));
            monthDetail.setCompanyDepositYearIncrement(new BigDecimal(ratemap.get("companyDepositYearIncrement").toString()));
            monthDetail.setDepositLoanRatioChange(new BigDecimal(ratemap.get("depositLoanRatioChange").toString()));
            //�·����������ʵ÷�  �������ʲ� = �·����������� - ȫ��ƽ����������
            monthDetail.setNewLoanRatioScore(new BigDecimal(ratemap.get("newLoanRatioScore").toString()));
            monthDetail.setNewLoanRatio(new BigDecimal(ratemap.get("newLoanRatio").toString()));
            monthDetail.setBankAverageLoanRatio(new BigDecimal(ratemap.get("bankAverageLoanRatio").toString()));
            monthDetail.setNewLoanRatioChange(new BigDecimal(ratemap.get("newLoanRatioChange").toString()));
            //����ṹ�÷� ��ĩʵ��������ռ�� = ��ĩʵ��������/�ܴ��� - �³�ʵ��������/�ܴ���
            monthDetail.setLoanStructScore(new BigDecimal(ratemap.get("loanStructScore").toString()));
            monthDetail.setLoanCount(new BigDecimal(ratemap.get("loanCount").toString()));
            monthDetail.setMonthBeginEntityLoan(new BigDecimal(ratemap.get("monthBeginEntityLoan").toString()));
            monthDetail.setMonthEndEntityLoan(new BigDecimal(ratemap.get("monthEndEntityLoan").toString()));
            monthDetail.setMonthBeginEntityLoanChange(new BigDecimal(ratemap.get("monthBeginEntityLoanChange").toString()));
            monthDetail.setMonthEndEntityLoanChange(new BigDecimal(ratemap.get("monthEndEntityLoanChange").toString()));
            monthDetail.setMonthEndBeginEntityLoanChange(new BigDecimal(ratemap.get("monthEndBeginEntityLoanChange").toString()));
            //����ģռ�÷Ѻ͹�ģ���÷ѵ÷�  ���з�Ϣռ�� =  ���з�Ϣ��� / ȫ�з�Ϣ���
            monthDetail.setScaleAmountScore(new BigDecimal(ratemap.get("scaleAmountScore").toString()));
            monthDetail.setOrganScaleAmount(new BigDecimal(ratemap.get("organScaleAmount").toString()));
            monthDetail.setBankScaleAmount(new BigDecimal(ratemap.get("bankScaleAmount").toString()));
            monthDetail.setScaleAmountChange(new BigDecimal(ratemap.get("scaleAmountChange").toString()));
            //�ƻ�����ƫ��ȵ÷�  �ƻ�����ƫ����� = ����ĩʵ�������� - �³��������� / ��ĩʵ��������
            monthDetail.setPlanSubmitDeviationScore(new BigDecimal(ratemap.get("planSubmitDeviationScore").toString()));
            monthDetail.setMonthEndIncrement(new BigDecimal(ratemap.get("monthEndIncrement").toString()));
            monthDetail.setMonthBeginReport(new BigDecimal(ratemap.get("monthBeginReport").toString()));
            monthDetail.setPlanSubmitDeviationChange(new BigDecimal(ratemap.get("planSubmitDeviationChange").toString()));
            //�ƻ�ִ��ƫ���--��ĩ����ƫ��ȵ÷�   ��ĩ����ƫ�����=����ĩʵ��������-����ͳһ��̬������ƻ���/��ĩͳһ��̬������ƻ�
            monthDetail.setPlanExecuteDeviationMonthMidScore(new BigDecimal(ratemap.get("planExecuteDeviationMonthMidScore").toString()));
            monthDetail.setMonthMidPlan(new BigDecimal(ratemap.get("monthMidPlan").toString()));
            monthDetail.setMonthEndPlan(new BigDecimal(ratemap.get("monthEndPlan").toString()));
            monthDetail.setPlanExecuteDeviationMonthMidChange(new BigDecimal(ratemap.get("planExecuteDeviationMonthMidChange").toString()));
            //�ƻ�ִ��ƫ���--��ĩ�³�ƫ��ȵ÷� ��ĩ�³�ƫ�����=����ĩʵ��������-�³������´�ƻ���/�³������´�ƻ�
            monthDetail.setPlanExecuteDeviationMonthInitScore(new BigDecimal(ratemap.get("planExecuteDeviationMonthInitScore").toString()));
            monthDetail.setMonthBeginPlan(new BigDecimal(ratemap.get("monthBeginPlan").toString()));
            monthDetail.setPlanExecuteDeviationMonthInitChange(new BigDecimal(ratemap.get("planExecuteDeviationMonthInitChange").toString()));
            //����Ƶ�ε÷�
            monthDetail.setAdjCountScore(new BigDecimal(ratemap.get("adjCountScore").toString()));
            monthDetail.setAdjCount(new BigDecimal(ratemap.get("adjCount").toString()));
            //����
            monthDetail.setAddScore(new BigDecimal(ratemap.get("addScore").toString()));
            monthDetail.setRemark(URLDecoder.decode(ratemap.get("remark").toString(), "UTF-8"));


            tbOrganRateScoreMonthDetailList.add(monthDetail);
        }
        return tbOrganRateScoreMonthDetailList;
    }


    /**
     * ��������������ϸ����
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

            //���ҳ��Ϊ��ֵ����Ĭ��Ϊ0����ע����
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
            //�������ֵȼ�
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

            //��������÷� ������ = ��ĩ������ - ���������
            quarterDetail.setBadConditionScoreQuarter(new BigDecimal(ratemap.get("badConditionScoreQuarter").toString()));
            quarterDetail.setBadConditionScoreOne(new BigDecimal(ratemap.get("badConditionScoreOne").toString()));
            quarterDetail.setBadConditionScoreTwo(new BigDecimal(ratemap.get("badConditionScoreTwo").toString()));
            quarterDetail.setBadConditionScoreThree(new BigDecimal(ratemap.get("badConditionScoreThree").toString()));

            //��Ӫ��������ȵ÷� ��Ӫ���������=������������� /��������Ӫ��������� + ��˾�����������
            quarterDetail.setDepositLoanRatioScoreQuarter(new BigDecimal(ratemap.get("depositLoanRatioScoreQuarter").toString()));
            quarterDetail.setDepositLoanRatioScoreOne(new BigDecimal(ratemap.get("depositLoanRatioScoreOne").toString()));
            quarterDetail.setDepositLoanRatioScoreTwo(new BigDecimal(ratemap.get("depositLoanRatioScoreTwo").toString()));
            quarterDetail.setDepositLoanRatioScoreThree(new BigDecimal(ratemap.get("depositLoanRatioScoreThree").toString()));

            //�·����������ʵ÷�  �������ʲ� = �·����������� - ȫ��ƽ����������

            quarterDetail.setNewLoanRatioScoreQuarter(new BigDecimal(ratemap.get("newLoanRatioScoreQuarter").toString()));
            quarterDetail.setNewLoanRatioScoreOne(new BigDecimal(ratemap.get("newLoanRatioScoreOne").toString()));
            quarterDetail.setNewLoanRatioScoreTwo(new BigDecimal(ratemap.get("newLoanRatioScoreTwo").toString()));
            quarterDetail.setNewLoanRatioScoreThree(new BigDecimal(ratemap.get("newLoanRatioScoreThree").toString()));
            //����ṹ�÷� ��ĩʵ��������ռ�� = ��ĩʵ��������/�ܴ��� - �³�ʵ��������/�ܴ���

            quarterDetail.setLoanStructScoreQuarter(new BigDecimal(ratemap.get("loanStructScoreQuarter").toString()));
            quarterDetail.setLoanStructScoreOne(new BigDecimal(ratemap.get("loanStructScoreOne").toString()));
            quarterDetail.setLoanStructScoreTwo(new BigDecimal(ratemap.get("loanStructScoreTwo").toString()));
            quarterDetail.setLoanStructScoreThree(new BigDecimal(ratemap.get("loanStructScoreThree").toString()));
            //����ģռ�÷Ѻ͹�ģ���÷ѵ÷�  ���з�Ϣռ�� =  ���з�Ϣ��� / ȫ�з�Ϣ���

            quarterDetail.setScaleAmountScoreQuarter(new BigDecimal(ratemap.get("scaleAmountScoreQuarter").toString()));
            quarterDetail.setScaleAmountScoreOne(new BigDecimal(ratemap.get("scaleAmountScoreOne").toString()));
            quarterDetail.setScaleAmountScoreTwo(new BigDecimal(ratemap.get("scaleAmountScoreTwo").toString()));
            quarterDetail.setScaleAmountScoreThree(new BigDecimal(ratemap.get("scaleAmountScoreThree").toString()));
            //�ƻ�����ƫ��ȵ÷�  �ƻ�����ƫ����� = ����ĩʵ�������� - �³��������� / ��ĩʵ��������

            quarterDetail.setPlanSubmitDeviationScoreQuarter(new BigDecimal(ratemap.get("planSubmitDeviationScoreQuarter").toString()));
            quarterDetail.setPlanSubmitDeviationScoreOne(new BigDecimal(ratemap.get("planSubmitDeviationScoreOne").toString()));
            quarterDetail.setPlanSubmitDeviationScoreTwo(new BigDecimal(ratemap.get("planSubmitDeviationScoreTwo").toString()));
            quarterDetail.setPlanSubmitDeviationScoreThree(new BigDecimal(ratemap.get("planSubmitDeviationScoreThree").toString()));
            //�ƻ�ִ��ƫ���--��ĩ����ƫ��ȵ÷�   ��ĩ����ƫ�����=����ĩʵ��������-����ͳһ��̬������ƻ���/��ĩͳһ��̬������ƻ�

            quarterDetail.setPlanExecuteDeviationMonthMidScoreQuarter(new BigDecimal(ratemap.get("planExecuteDeviationMonthMidScoreQuarter").toString()));
            quarterDetail.setPlanExecuteDeviationMonthMidScoreOne(new BigDecimal(ratemap.get("planExecuteDeviationMonthMidScoreOne").toString()));
            quarterDetail.setPlanExecuteDeviationMonthMidScoreTwo(new BigDecimal(ratemap.get("planExecuteDeviationMonthMidScoreTwo").toString()));
            quarterDetail.setPlanExecuteDeviationMonthMidScoreThree(new BigDecimal(ratemap.get("planExecuteDeviationMonthMidScoreThree").toString()));
            //�ƻ�ִ��ƫ���--��ĩ�³�ƫ��ȵ÷� ��ĩ�³�ƫ�����=����ĩʵ��������-�³������´�ƻ���/�³������´�ƻ�

            quarterDetail.setPlanExecuteDeviationMonthInitScoreQuarter(new BigDecimal(ratemap.get("planExecuteDeviationMonthInitScoreQuarter").toString()));
            quarterDetail.setPlanExecuteDeviationMonthInitScoreOne(new BigDecimal(ratemap.get("planExecuteDeviationMonthInitScoreOne").toString()));
            quarterDetail.setPlanExecuteDeviationMonthInitScoreTwo(new BigDecimal(ratemap.get("planExecuteDeviationMonthInitScoreTwo").toString()));
            quarterDetail.setPlanExecuteDeviationMonthInitScoreThree(new BigDecimal(ratemap.get("planExecuteDeviationMonthInitScoreThree").toString()));
            //����Ƶ�ε÷�

            quarterDetail.setAdjCountScoreQuarter(new BigDecimal(ratemap.get("adjCountScoreQuarter").toString()));
            quarterDetail.setAdjCountScoreOne(new BigDecimal(ratemap.get("adjCountScoreOne").toString()));
            quarterDetail.setAdjCountScoreTwo(new BigDecimal(ratemap.get("adjCountScoreTwo").toString()));
            quarterDetail.setAdjCountScoreThree(new BigDecimal(ratemap.get("adjCountScoreThree").toString()));
            //δ��������ģռ�÷ѵ÷�
            quarterDetail.setScaleAmountMonthCountScoreQuarter(new BigDecimal(ratemap.get("scaleAmountMonthCountScoreQuarter").toString()));
            quarterDetail.setScaleAmountMonthCount(new BigDecimal(ratemap.get("scaleAmountMonthCount").toString()));
            //����Ƶ��δ�۷ֵ÷�
            quarterDetail.setAdjCountMonthCountScoreQuarter(new BigDecimal(ratemap.get("adjCountMonthCountScoreQuarter").toString()));
            quarterDetail.setAdjCountMonthCount(new BigDecimal(ratemap.get("adjCountMonthCount").toString()));
            //��ʷ�ӷ���÷�
            quarterDetail.setAddScoreQuarter(new BigDecimal(ratemap.get("addScoreQuarter").toString()));
            quarterDetail.setAddScoreOne(new BigDecimal(ratemap.get("addScoreOne").toString()));
            quarterDetail.setAddScoreTwo(new BigDecimal(ratemap.get("addScoreTwo").toString()));
            quarterDetail.setAddScoreThree(new BigDecimal(ratemap.get("addScoreThree").toString()));
            //�ӷ���
            quarterDetail.setAddScore(new BigDecimal(ratemap.get("addScore").toString()));
            quarterDetail.setRemark(URLDecoder.decode(ratemap.get("remark").toString(), "UTF-8"));

            quarterDetail.setGoodIdeaScore(new BigDecimal("0"));
            quarterDetail.setGoodJobScore(new BigDecimal("0"));


            tbOrganRateScoreQuarterDetailList.add(quarterDetail);
        }
        return tbOrganRateScoreQuarterDetailList;
    }


    private void deleteMsg(String id) throws Exception {

        //ɾ����ǰС����
        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("��������", "MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("����������" + id);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }
    }

    private void saveMsg(String msgNo, String operCode, String assignee, String msgUrl, String id) throws Exception {

        //ɾ����ǰС����
        WebMsg webMsg = new WebMsg();
        webMsg.setMsgType(DicCache.getKeyByName_("��������", "MSG_TYPE"));
        webMsg.setWebMsgStatus("1");
        webMsg.setOperDescribe("����������" + id);
        List<WebMsg> webMsgs = webMsgService.selectByAttr(webMsg);
        if (webMsgs != null && webMsgs.size() != 0) {
            webMsgService.deleteByPK(webMsgs.get(0).getMsgNo());
        }

        WebMsg msg = new WebMsg();
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
        msg.setMsgType(DicCache.getKeyByName_("��������", "MSG_TYPE"));
        TbOrganRateScore tbOrganRateScore = tbOrganRateScoreMapper.selectByPK(id);
        String type = tbOrganRateScore.getRateType().intValue() == 1 ? "�¶�" : "����";
        msg.setOperName("��������" + "-" + tbOrganRateScore.getRateMonth() + "-" + type);
        msg.setOperNo(operCode);
        msg.setRepUserCode(assignee);
        msg.setWebMsgStatus("1");
        msg.setMsgUrl(msgUrl);
        msg.setOperDescribe("����������" + id);
        webMsgService.insertEntity(msg);
    }


    //��ȡĳ��ĳ�µ����һ��
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