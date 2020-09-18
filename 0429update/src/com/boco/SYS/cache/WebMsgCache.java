package com.boco.SYS.cache;

import com.boco.SYS.mapper.WebMsgMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 待办消息缓存类.
 *
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年4月27日    	    杨滔    新建
 * </pre>
 */
@Component
public class WebMsgCache {
    private Logger log = Logger.getLogger(WebMsgCache.class);
    @Autowired
    private WebMsgMapper webMsgMapper;

    public static List<Map<String, String>> repMap = null;

    /**
     * TODO 后台定时查询待待办消息.
     *
     *
     * <pre>
     * 修改日期        修改人    修改原因
     * 2016年4月27日    	    杨滔    新建
     * </pre>
     */
    // @PostConstruct
    // public void start() {
    //     try {
    //         //Double.parseDouble(DicCache.getKeyByName_("后台查询", "D_REFRESH_INTERVAL"));
    //             final double interval = Double.parseDouble(DicCache.getKeyByName_("后台查询", "D_REFRESH_INTERVAL"));
    //         //查询待复核
    //         Runnable runnable = new Runnable() {
    //             public void run() {
    //                 log.info("==================后台定时查询待待办消息，查询频率[" + interval + "]秒==================");
    //                 repMap = webMsgMapper.countByRepuser();
    //             }
    //         };
    //         ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    //         service.scheduleAtFixedRate(runnable, 0, (long) interval, TimeUnit.SECONDS);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    public List<Map<String, String>> getRepMap() {
        return repMap = webMsgMapper.countByRepuser();
    }

    public static void setRepMap(List<Map<String, String>> repMap) {
        WebMsgCache.repMap = repMap;
    }
}
