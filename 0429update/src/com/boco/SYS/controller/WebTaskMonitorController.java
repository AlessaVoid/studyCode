package com.boco.SYS.controller;

import com.alibaba.fastjson.JSON;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.global.Dic;
import com.boco.SYS.mapper.TbBatchParamMapper;
import com.boco.SYS.mapper.TbBatchTaskMapper;
import com.boco.TONY.common.PlainResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �����ع���
 * @Author zhuhongjiang
 * @Date 2020/3/20 ����2:31
 **/
@Controller
@RequestMapping(value = "/webTaskMonitor")
public class WebTaskMonitorController {

    @Autowired
    private TbBatchParamMapper tbBatchParamMapper;
    @Autowired
    private TbBatchTaskMapper tbBatchTaskMapper;

    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy��MM��dd��");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

    /**
     * ���ؼ��ҳ��
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/toIndex")
    @SystemLog(tradeName = "�����ع���", funCode = "SYS-07", funName = "���ؼ��ҳ��", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toIndex(HttpServletRequest request, HttpServletResponse response){
        return "/system/SYS/monitor/taskMonitorIndex";
    }

    /**
     * ʵʱ������������Ϣ
     * @param request
     * @param response
     */
    @RequestMapping(value = "/selectAll")
    @SystemLog(tradeName = "�����ع���", funCode = "SYS-07", funName = "ʵʱ������������Ϣ", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String select(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlainResult<Map<String, Object>> result = new PlainResult<>();
        try {
            //��ѯ����
            String date = tbBatchParamMapper.selectSysDate();
            if(StringUtils.isEmpty(date)){
                return JSON.toJSONString(result);
            }

            //��ѯ�б�
            List<Map<String, Object>> list = tbBatchTaskMapper.selectTaskList(date);

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put( "total", list.size() );
            dataMap.put( "rows", list );

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put( "list", dataMap);
            resultMap.put( "date", sdf1.format(sdf2.parse(date)));
            result.success(resultMap, "selectMonitor success.");
        } catch (Exception e) {
            result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "selectMonitor error.");
        }
        return JSON.toJSONString(result);
    }

}
