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
 * 任务监控管理
 * @Author zhuhongjiang
 * @Date 2020/3/20 下午2:31
 **/
@Controller
@RequestMapping(value = "/webTaskMonitor")
public class WebTaskMonitorController {

    @Autowired
    private TbBatchParamMapper tbBatchParamMapper;
    @Autowired
    private TbBatchTaskMapper tbBatchTaskMapper;

    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

    /**
     * 加载监控页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/toIndex")
    @SystemLog(tradeName = "任务监控功能", funCode = "SYS-07", funName = "加载监控页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toIndex(HttpServletRequest request, HttpServletResponse response){
        return "/system/SYS/monitor/taskMonitorIndex";
    }

    /**
     * 实时加载任务监控信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "/selectAll")
    @SystemLog(tradeName = "任务监控功能", funCode = "SYS-07", funName = "实时加载任务监控信息", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String select(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlainResult<Map<String, Object>> result = new PlainResult<>();
        try {
            //查询日期
            String date = tbBatchParamMapper.selectSysDate();
            if(StringUtils.isEmpty(date)){
                return JSON.toJSONString(result);
            }

            //查询列表
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
