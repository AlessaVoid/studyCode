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
 * Monitor定时任务
 *
 * @Author zhuhongjiang
 * @Date 2020/1/6 下午1:25
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
    /*是否开启定时任务*/
    @Value("${timer.scheduled.open}")
    private boolean scheduledCtrl;


    /**
     * 检查Channel活跃状态
     *
     * @Author zhuhongjiang
     * @Date 2020/1/6 下午1:25
     **/
    @Scheduled(fixedRate = 5000)
    public void checkChannelActive() {
        webMonitorService.checkChannelActive();
    }

    /**
     * 实时获取监控信息
     *
     * @Author zhuhongjiang
     * @Date 2020/1/6 下午1:25
     **/
    @Scheduled(fixedRate = 1000)
    public void reqMonitorData() {
        webMonitorService.reqMonitorData();
    }


    /**
     * 月初计算罚息
     */

    @Scheduled(cron = "${timer.punish.cron}")
    public void punishInterest() throws Exception {
        if (scheduledCtrl) {
            punishInterestService.punish(new TbTradeParam());
        }

    }

    /**
     * 定时任务--更新短信表
     * 检测服务状态的改变，更新到短信表中
     */
   @Scheduled(cron = "${msg.send}")
    public void updateMsgStatus() throws Exception {
        if (scheduledCtrl) {
            try {
                msgMonitorService.updateMsgStatus();
            } catch (Exception e) {
                log.error("定时任务--更新短信表失败，错误信息", e);
//                e.printStackTrace();
            }
        }
    }

    /**
     * 定时任务--根据短信表状态发送短信
     */
   @Scheduled(cron = "${msg.send}")
    public void sendMsg() throws Exception {
        if (scheduledCtrl) {
            try {
                msgMonitorService.sendMsg();
            } catch (Exception e) {
                log.error("定时任务--根据短信表状态发送短信失败，错误信息", e);
//                e.printStackTrace();
            }
        }
    }

    /**
     * 定时任务--重置今日发短信次数
     */
   @Scheduled(cron = "${msg.clear}")
    public void clearTodayMsgCount() throws Exception {
        if (scheduledCtrl) {
            try {
                msgMonitorService.clearTodayMsgCount();
            } catch (Exception e) {
                log.error("定时任务--重置今日发短信次数失败，错误信息", e);
//                e.printStackTrace();
            }
        }
    }
}
