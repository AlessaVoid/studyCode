package com.boco.SYS.monitor.timer;


import com.boco.SYS.service.BatchDayMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ���������������
 */
@Component
@EnableScheduling
public class ShortMsgMonitorTimer {
    private static final Logger log = LoggerFactory.getLogger(ShortMsgMonitorTimer.class);
    @Autowired
    private BatchDayMessageService batchDayMessageService;
    @Value("${msg.batch.isRun}")
    private boolean batchIsRun;
    @Value("${msg.sendMsg.isRun}")
    private boolean sendMsgIsRun;

    /**
     * ��ʱ����--�����������״̬��������ſ�
     */
    @Scheduled(cron = "${msg.batch.cron}")
    public void monitorBatchAndInsertMsg() throws Exception {
        if (batchIsRun) {
            try {
                batchDayMessageService.monitorBatchAndInsertMsg();
            } catch (Exception e) {
                log.error("��ʱ����--�����������״̬��������ſ���󣬴�����Ϣ", e);
            }
        }
    }

    /**
     * ��ʱ����--���Ͷ���
     */
    @Scheduled(cron = "${msg.sendMsg.cron}")
    public void sendShortMsg()  {
        if (sendMsgIsRun) {
            try {
                batchDayMessageService.sendShortMsg();
            } catch (Exception e) {
                log.error("��ʱ����--���Ͷ��Ŵ��󣬴�����Ϣ", e);
            }
        }
    }

}
