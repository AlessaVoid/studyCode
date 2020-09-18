package com.boco.TONY.biz.weboper.service;

import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.biz.weboper.POJO.DTO.WebOperLineDTO;

/**
 * ��Ա����ҵ���߼���ӿ�
 * @author tony
 * @describe IWebOperLineService
 * @date 2019-09-24
 */
public interface IWebOperLineService {

    /**�����Ա�����ߵĹ�ϵ*/
    PlainResult<String> insertWebOperLine(String operCode, String lineIds);

    /**���¹�Ա�����ߵĹ�ϵ*/
    PlainResult<String> updateWebOperLine(String operCode, String lineIds);

    /**ɾ����Ա�����ߵĹ�ϵ*/
    PlainResult<String> deleteWebOperLine(String operCode, String lineIds);

    /**ɾ����Ա��ɫ�����е�����*/
    PlainResult<String> deleteAllWebOperLineByOperCode(String operCode);

    /**��ȡ��������,ͨ��������*/
   ListResult<WebOperLineDTO> getAllWebOperLineByOperCode(String operCode, int status);
}
