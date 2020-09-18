package com.boco.PUB.service.tbCalculateOne;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbCalculateOneImportData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 
 * 
 * TbCalculateOneImportDataҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface TbCalculateOneImportDataService extends IGenericService<TbCalculateOneImportData, String>{
    /**
     * @Description �����������
     * @Author liujinbo
     * @Date 2020/3/10
     * @param file
     * @param operCode
     * @param request
     * @Return java.util.Map<java.lang.String,java.lang.String>
     */
    Map<String, String> enterDataByMonth(MultipartFile file, String operCode, HttpServletRequest request) throws Exception;

}