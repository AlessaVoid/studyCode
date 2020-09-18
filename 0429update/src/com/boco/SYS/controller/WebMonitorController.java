package com.boco.SYS.controller;

import com.alibaba.fastjson.JSON;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.global.Dic;
import com.boco.SYS.monitor.model.TradeReport;
import com.boco.SYS.service.IWebMonitorService;
import com.boco.TONY.common.PlainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * web监控
 * @Author zhuhongjiang
 * @Date 2020/1/6 下午1:03
 **/
@Controller
@RequestMapping(value = "/webMonitor")
public class WebMonitorController extends BaseController {

    @Autowired
    private IWebMonitorService webMonitorService;

    /**
     * 加载监控页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/toIndex")
    @SystemLog(tradeName = "监控功能", funCode = "SYS-04", funName = "加载监控页面", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public String toIndex(HttpServletRequest request, HttpServletResponse response){
        return "/system/SYS/monitor/monitorIndex";
    }

    /**
     * 实时加载监控信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "/selectAll")
    @SystemLog(tradeName = "监控功能", funCode = "SYS-04", funName = "实时加载监控信息", accessType = Dic.AccessType.READ, level = Dic.Debug.INFO)
    public @ResponseBody String select(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlainResult<Map<String, Object>> result = new PlainResult<>();
        try {
            List<TradeReport> list = webMonitorService.select();

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put( "total", list.size() );
            resultMap.put( "rows", list );
            result.success(resultMap, "selectMonitor success.");
        } catch (Exception e) {
            result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "selectMonitor error.");
        }
        return JSON.toJSONString(result);
    }
}
