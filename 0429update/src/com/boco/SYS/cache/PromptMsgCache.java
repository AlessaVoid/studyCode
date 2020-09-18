package com.boco.SYS.cache;


import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boco.SYS.entity.WebPublicPromptTable;
import com.boco.SYS.mapper.WebPublicPromptTableMapper;
/**
 * 
 * 
 *  待办消息缓存类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年4月27日    	    杨滔    新建
 * </pre>
 */
@Component
public class PromptMsgCache {
	private Logger log = Logger.getLogger(PromptMsgCache.class);
	@Autowired
	private WebPublicPromptTableMapper webPublicPromptTableMapper;
	public static List<WebPublicPromptTable> msg = null;
	/**
	 * 
	 *
	 * TODO 后台定时查询待待办消息.
	 *
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月27日    	    杨滔    新建
	 * </pre>
	 */
	@PostConstruct
	public void start(){
		try{
			//Double.parseDouble(DicCache.getKeyByName_("后台查询", "D_PROMPT_INTERVAL"));
			final double t =Double.parseDouble(DicCache.getKeyByName_("后台查询", "D_PROMPT_INTERVAL"));
			//查询待复核
			Runnable runnable = new Runnable() {
			    public void run() {
		    	 msg = webPublicPromptTableMapper.selectPromptMsg();
				 log.info("==============================获取公告信息，查询频率["+t+"]秒==================");
			    }
		    };
			    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
				service.scheduleAtFixedRate(runnable, 0, (long) t, TimeUnit.SECONDS);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static List<WebPublicPromptTable> getMsg() {
		return msg;
	}
	public static void setMsg(List<WebPublicPromptTable> msg) {
		PromptMsgCache.msg = msg;
	}
}
