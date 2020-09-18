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
 *  ������Ϣ������.
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2016��4��27��    	    ����    �½�
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
	 * TODO ��̨��ʱ��ѯ��������Ϣ.
	 *
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��27��    	    ����    �½�
	 * </pre>
	 */
	@PostConstruct
	public void start(){
		try{
			//Double.parseDouble(DicCache.getKeyByName_("��̨��ѯ", "D_PROMPT_INTERVAL"));
			final double t =Double.parseDouble(DicCache.getKeyByName_("��̨��ѯ", "D_PROMPT_INTERVAL"));
			//��ѯ������
			Runnable runnable = new Runnable() {
			    public void run() {
		    	 msg = webPublicPromptTableMapper.selectPromptMsg();
				 log.info("==============================��ȡ������Ϣ����ѯƵ��["+t+"]��==================");
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
