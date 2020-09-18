package com.boco.SYS.monitor.timer;

import com.boco.AL.service.impl.PunishInterestService;
import com.boco.SYS.entity.TbTradeParam;
import com.boco.SYS.service.IWebMonitorService;
import com.boco.SYS.service.MsgMonitorService;
import com.boco.SYS.service.impl.MsgMonitorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Monitor��ʱ����
 *
 * @Author zhuhongjiang
 * @Date 2020/1/6 ����1:25
 **/
@Component
@EnableScheduling
public class MonitorTimer {

    private static final Logger log = LoggerFactory.getLogger(MonitorTimer.class);

    @Autowired
    private IWebMonitorService webMonitorService;
    @Autowired
    private PunishInterestService punishInterestService;
    @Autowired
    private MsgMonitorService msgMonitorService;
    /*�Ƿ�����ʱ����*/
    @Value("${timer.scheduled.open}")
    private boolean scheduledCtrl;


    /**
     * ���Channel��Ծ״̬
     *
     * @Author zhuhongjiang
     * @Date 2020/1/6 ����1:25
     **/
    @Scheduled(fixedRate = 5000)
    public void checkChannelActive() {
        webMonitorService.checkChannelActive();
    }

    /**
     * ʵʱ��ȡ�����Ϣ
     *
     * @Author zhuhongjiang
     * @Date 2020/1/6 ����1:25
     **/
    @Scheduled(fixedRate = 1000)
    public void reqMonitorData() {
        webMonitorService.reqMonitorData();
    }


    /**
     * �³����㷣Ϣ
     */

    @Scheduled(cron = "${timer.punish.cron}")
    public void punishInterest() throws Exception {
        if (scheduledCtrl) {
            punishInterestService.punish(new TbTradeParam());
        }

    }

    /**
     * ��ʱ����--���¶��ű�
     * ������״̬�ĸı䣬���µ����ű���
     */
   @Scheduled(cron = "${msg.send}")
    public void updateMsgStatus() throws Exception {
        if (scheduledCtrl) {
            try {
                msgMonitorService.updateMsgStatus();
            } catch (Exception e) {
                log.error("��ʱ����--���¶��ű�ʧ�ܣ�������Ϣ", e);
//                e.printStackTrace();
            }
        }
    }

    /**
     * ��ʱ����--���ݶ��ű�״̬���Ͷ���
     */
   @Scheduled(cron = "${msg.send}")
    public void sendMsg() throws Exception {
        if (scheduledCtrl) {
            try {
                msgMonitorService.sendMsg();
            } catch (Exception e) {
                log.error("��ʱ����--���ݶ��ű�״̬���Ͷ���ʧ�ܣ�������Ϣ", e);
//                e.printStackTrace();
            }
        }
    }

    /**
     * ��ʱ����--���ý��շ����Ŵ���
     */
   @Scheduled(cron = "${msg.clear}")
    public void clearTodayMsgCount() throws Exception {
        if (scheduledCtrl) {
            try {
                msgMonitorService.clearTodayMsgCount();
            } catch (Exception e) {
                log.error("��ʱ����--���ý��շ����Ŵ���ʧ�ܣ�������Ϣ", e);
//                e.printStackTrace();
            }
        }
    }
}
