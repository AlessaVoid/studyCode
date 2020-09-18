package com.boco.PUB.service.tbCalculateTwo;

import java.util.HashMap;
import java.util.Map;

import com.boco.SYS.entity.TbCalculateTwoImportData;
import com.boco.SYS.base.IGenericService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 
 * �����  ��ʷ���ݵ����ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface ITbCalculateTwoImportDataService extends IGenericService<TbCalculateTwoImportData, String>{
    Map<String, String> enterDataByMonth(MultipartFile file, String operCode, HttpServletRequest request);
}