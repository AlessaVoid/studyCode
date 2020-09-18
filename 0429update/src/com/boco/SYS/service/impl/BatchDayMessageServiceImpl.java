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
    //����״̬���Map
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
    /*���Ͷ��ŵĻ�����ַ*/
    @Value("${sendMsg.url}")
    private String sendMsgUrl;


    /**
     * �����������״̬��������ſ�
     */
    @Override
    public void monitorBatchAndInsertMsg() {
        /*��ȡ��������*/
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
        //ǰһ��
        Date beforeOneDay = c.getTime();
        c.add(Calendar.DATE, -1);
        //ǰ����
        Date beforeTwoDay = c.getTime();

        /*��ѯ����������������Ϣ*/
        doMonitorBatchAndInsertMsg(batchDay);

        /*��ѯ������һ������������Ϣ*/
        doMonitorBatchAndInsertMsg(sdf.format(beforeOneDay));

        /*ɾ��ǰ�������������Ϣ*/
        batchMap.remove(sdf.format(beforeTwoDay));
    }


    /**
     * ���Ͷ��ſ��еĶ���
     */
    @Override
    public void sendShortMsg() {
        /*��ѯ��Ҫ���͵Ķ���*/
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("date", BocoUtils.getTime());
        List<MsgDetail> msgDetailList = msgDetailMapper.selectSendShortMsg(paramMap);
        /*���Ͷ��ţ����¶���״̬*/
        for (MsgDetail msgDetail : msgDetailList) {
            JSONObject json = HttpRequestClient.sendMsg(msgDetail.getPhoneNumber(), sendMsgUrl, msgDetail.getSendMessage());
            msgDetail.setReturnMessage(json.toJSONString());
            msgDetail.setStatus("1");
            msgDetailMapper.updateByPK(msgDetail);
        }
    }

    private void doMonitorBatchAndInsertMsg(String date) {
        log.info("����{}�����������ط��Ͷ���", date);
        //��Ҫ���͵Ķ���List
        ArrayList<MsgDetail> msgDetailList = new ArrayList<>();

        TbBatchTaskStatus tbBatchTaskStatusParam = new TbBatchTaskStatus();
        tbBatchTaskStatusParam.setBatchDay(date);
        tbBatchTaskStatusParam.setTaskStatus(0);
        List<TbBatchTaskStatus> tbBatchTaskStatuseList = tbBatchTaskStatusMapper.selectByAttr(tbBatchTaskStatusParam);

        //�ж��Ƿ�������ɵ���������
        Map<String, String> map = batchMap.get(date);
        if (map == null) {
            map = new HashMap<>();
        }

        for (TbBatchTaskStatus tbBatchTaskStatus : tbBatchTaskStatuseList) {
            String key = tbBatchTaskStatus.getBatchDay() + "_" + tbBatchTaskStatus.getTaskCode();
            String value = map.get(key);
            if (value == null) {
                //��������״̬���Map
                TbBatchTask tbBatchTask = tbBatchTaskMapper.selectByPK(tbBatchTaskStatus.getTaskCode());
                if (tbBatchTask == null) {
                    tbBatchTask = new TbBatchTask();
                }
                map.put(key, tbBatchTaskStatus.getTaskCode());

                //������ſ�List
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
        //����ſ�
        if (msgDetailList != null && msgDetailList.size() > 0) {
            msgDetailMapper.insertBatch(msgDetailList);
        }
    }
}
