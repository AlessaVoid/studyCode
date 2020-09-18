package com.boco.PUB.controller.temp.timeTask;


import com.boco.SYS.entity.TbQuotaAllocate;
import com.boco.SYS.entity.TbTempResultInfo;
import com.boco.SYS.mapper.TbQuotaAllocateMapper;
import com.boco.SYS.mapper.TbTempResultInfoMapper;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class TempTimerTask {
    public static Logger logger = Logger.getLogger(TempTimerTask.class);
    @Autowired
    TbQuotaAllocateMapper tbQuotaAllocateMapper;
    @Autowired
    TbTempResultInfoMapper tbTempResultInfoMapper;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * 每日更新生效额度；移除失效额度
     * 暂定 每日 00:01:00执行
     */
    @Scheduled(cron = "0 1 0 * * ?")
    public void tempTask() {
        try {
            Date date = new Date();
            logger.info(sdf.format(date) + "【TEMP_NUM】临时额度开始更新");
            List<TbTempResultInfo> list = tbTempResultInfoMapper.selectByAttr(new TbTempResultInfo());
            //新增生效
            List<TbTempResultInfo> insertList = new ArrayList<>();
            Calendar c =Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH,-1);
            Date yesterday =c.getTime();
            BigDecimal unitNum = new BigDecimal(10000);
            //减去失效
            List<TbTempResultInfo> deleteList = new ArrayList<>();

            for (TbTempResultInfo tbTempResultInfo : list) {
                if (tbTempResultInfo.getStartDate().equals(sdf.format(date))) {
                    //今日生效的
                    insertList.add(tbTempResultInfo);
                }
                if (tbTempResultInfo.getEndDate().equals(sdf.format(yesterday))) {
                    //明日失效的--有效额度时间包含今日
                    deleteList.add(tbTempResultInfo);
                }
            }
            for (TbTempResultInfo addTb : insertList) {
                addTb.setState(TbTempResultInfo.TEMP_ING);
                TbQuotaAllocate searchTb = new TbQuotaAllocate();
                searchTb.setPaOrgan(addTb.getOrgancode());
                searchTb.setPaProdCode(addTb.getCombId());
                searchTb.setPaMonth(addTb.getMonth());
                List<TbQuotaAllocate> listResult = tbQuotaAllocateMapper.selectByAttr(searchTb);
                if (listResult != null && listResult.size() == 1) {
                    TbQuotaAllocate beforeTb = listResult.get(0);
                    BigDecimal beforeAmt = beforeTb.getPaQuotaAvail();
                    beforeTb.setPaQuotaAvail(beforeTb.getPaQuotaAvail().add(addTb.getTempamt().multiply(unitNum)));
                    tbQuotaAllocateMapper.updateByPK(beforeTb);
                    logger.info("beforeTb.getPaId():" + beforeTb.getPaId()
                            + "修改额度:之前额度【" + beforeAmt + "】修改后额度【" + beforeTb.getPaQuotaAvail() + "]");
                }
                tbTempResultInfoMapper.updateByPK(addTb);
            }

            for (TbTempResultInfo deleteTb : deleteList) {
                deleteTb.setState(TbTempResultInfo.TEMP_OLD);
                TbQuotaAllocate searchTb = new TbQuotaAllocate();
                searchTb.setPaOrgan(deleteTb.getOrgancode());
                searchTb.setPaProdCode(deleteTb.getCombId());
                searchTb.setPaMonth(deleteTb.getMonth());
                List<TbQuotaAllocate> listResult = tbQuotaAllocateMapper.selectByAttr(searchTb);
                if (listResult != null && listResult.size() == 1) {
                    TbQuotaAllocate beforeTb = listResult.get(0);
                    BigDecimal beforeAmt = beforeTb.getPaQuotaAvail();
                    beforeTb.setPaQuotaAvail(beforeTb.getPaQuotaAvail().subtract(deleteTb.getTempamt().multiply(unitNum)));
                    tbQuotaAllocateMapper.updateByPK(beforeTb);
                    logger.info("beforeTb.getPaId():" + beforeTb.getPaId()
                            + "修改额度:之前额度【" + beforeAmt + "】修改后额度【" + beforeTb.getPaQuotaAvail() + "]");
                }
                tbTempResultInfoMapper.updateByPK(deleteTb);
            }
            logger.info(sdf.format(date) + "【TEMP_NUM】临时额度更新完成");
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

    }


}
