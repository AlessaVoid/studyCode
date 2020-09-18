package com.boco.SYS.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.boco.SYS.entity.MsgDetail;
import com.boco.SYS.entity.TbBatchTask;
import com.boco.SYS.entity.TbBatchTaskStatus;
import com.boco.SYS.mapper.MsgDetailMapper;
import com.boco.SYS.mapper.TbBatchParamMapper;
import com.boco.SYS.mapper.TbBatchTaskMapper;
import com.boco.SYS.mapper.TbBatchTaskStatusMapper;
import com.boco.SYS.service.BatchDayMessageService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.HttpRequestClient;
import com.boco.TONY.utils.IDGeneratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BatchDayMessageServiceImpl implements BatchDayMessageService {

    private static final Logger log = LoggerFactory.getLogger(BatchDayMessageServiceImpl.class);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    //日终状态监控Map
    private static Map<String,Map<String, String>> batchMap = new HashMap<>();

    @Autowired
    private TbBatchParamMapper tbBatchParamMapper;
    @Autowired
    private TbBatchTaskStatusMapper tbBatchTaskStatusMapper;
    @Autowired
    private TbBatchTaskMapper tbBatchTaskMapper;
    @Autowired
    private MsgDetailMapper msgDetailMapper;
    @Value("${msg.sendMsg.phone}")
    private String phoneList;
    @Value("${msg.sendMsg.template}")
    private String msgText;
    /*发送短信的环境地址*/
    @Value("${sendMsg.url}")
    private String sendMsgUrl;


    /**
     * 监控日终任务状态并且入短信库
     */
    @Override
    public void monitorBatchAndInsertMsg() {
        /*获取日终日期*/
        String batchDay = tbBatchParamMapper.selectSysDate();

        Date batchDate = null;
        try {
            batchDate = sdf.parse(batchDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(batchDate);
        c.add(Calendar.DATE,-1);
        //前一天
        Date beforeOneDay = c.getTime();
        c.add(Calendar.DATE, -1);
        //前二天
        Date beforeTwoDay = c.getTime();

        /*查询处理当天日终任务信息*/
        doMonitorBatchAndInsertMsg(batchDay);

        /*查询处理上一天日终任务信息*/
        doMonitorBatchAndInsertMsg(sdf.format(beforeOneDay));

        /*删除前天的日终任务信息*/
        batchMap.remove(sdf.format(beforeTwoDay));
    }


    /**
     * 发送短信库中的短信
     */
    @Override
    public void sendShortMsg() {
        /*查询需要发送的短信*/
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("date", BocoUtils.getTime());
        List<MsgDetail> msgDetailList = msgDetailMapper.selectSendShortMsg(paramMap);
        /*发送短信，更新短信状态*/
        for (MsgDetail msgDetail : msgDetailList) {
            JSONObject json = HttpRequestClient.sendMsg(msgDetail.getPhoneNumber(), sendMsgUrl, msgDetail.getSendMessage());
            msgDetail.setReturnMessage(json.toJSONString());
            msgDetail.setStatus("1");
            msgDetailMapper.updateByPK(msgDetail);
        }
    }

    private void doMonitorBatchAndInsertMsg(String date) {
        log.info("处理【{}】日终任务监控发送短信", date);
        //需要发送的短信List
        ArrayList<MsgDetail> msgDetailList = new ArrayList<>();

        TbBatchTaskStatus tbBatchTaskStatusParam = new TbBatchTaskStatus();
        tbBatchTaskStatusParam.setBatchDay(date);
        tbBatchTaskStatusParam.setTaskStatus(0);
        List<TbBatchTaskStatus> tbBatchTaskStatuseList = tbBatchTaskStatusMapper.selectByAttr(tbBatchTaskStatusParam);

        //判断是否有新完成的日终任务
        Map<String, String> map = batchMap.get(date);
        if (map == null) {
            map = new HashMap<>();
        }

        for (TbBatchTaskStatus tbBatchTaskStatus : tbBatchTaskStatuseList) {
            String key = tbBatchTaskStatus.getBatchDay() + "_" + tbBatchTaskStatus.getTaskCode();
            String value = map.get(key);
            if (value == null) {
                //更新日终状态监控Map
                TbBatchTask tbBatchTask = tbBatchTaskMapper.selectByPK(tbBatchTaskStatus.getTaskCode());
                if (tbBatchTask == null) {
                    tbBatchTask = new TbBatchTask();
                }
                map.put(key, tbBatchTaskStatus.getTaskCode());

                //插入短信库List
                String[] split = phoneList.split(",");
                for (String phone : split) {
                    MsgDetail msgDetail = new MsgDetail();
                    msgDetail.setId(IDGeneratorUtils.getSequence());
                    msgDetail.setPhoneNumber(phone);
                    String sendMessage = new String(msgText);
                    sendMessage = sendMessage.replace("$date", date);
                    sendMessage = sendMessage.replace("$batchCode", tbBatchTaskStatus.getTaskCode());
                    sendMessage = sendMessage.replace("$batchName", tbBatchTask.getTaskName()+"");
                    sendMessage = sendMessage.replace("$startTime", tbBatchTaskStatus.getStartTime());
                    sendMessage = sendMessage.replace("$endTime", tbBatchTaskStatus.getEndTime());

                    msgDetail.setSendMessage(sendMessage);
                    msgDetail.setSendDate(BocoUtils.getTime());
                    msgDetail.setStatus("2");
                    msgDetailList.add(msgDetail);
                }
            }
        }
        batchMap.put(date, map);
        //入短信库
        if (msgDetailList != null && msgDetailList.size() > 0) {
            msgDetailMapper.insertBatch(msgDetailList);
        }
    }
}
