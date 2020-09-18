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
 * ������Ϣ������.
 *
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2016��4��27��    	    ����    �½�
 * </pre>
 */
@Component
public class WebMsgCache {
    private Logger log = Logger.getLogger(WebMsgCache.class);
    @Autowired
    private WebMsgMapper webMsgMapper;

    public static List<Map<String, String>> repMap = null;

    /**
     * TODO ��̨��ʱ��ѯ��������Ϣ.
     *
     *
     * <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��4��27��    	    ����    �½�
     * </pre>
     */
    // @PostConstruct
    // public void start() {
    //     try {
    //         //Double.parseDouble(DicCache.getKeyByName_("��̨��ѯ", "D_REFRESH_INTERVAL"));
    //             final double interval = Double.parseDouble(DicCache.getKeyByName_("��̨��ѯ", "D_REFRESH_INTERVAL"));
    //         //��ѯ������
    //         Runnable runnable = new Runnable() {
    //             public void run() {
    //                 log.info("==================��̨��ʱ��ѯ��������Ϣ����ѯƵ��[" + interval + "]��==================");
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
