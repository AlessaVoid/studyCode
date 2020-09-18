package com.boco.PUB.service.tbCalculateOne.impl;

import com.boco.PUB.POJO.DO.CalculateOneParam;
import com.boco.PUB.service.tbCalculateOne.TbCalculateOneImportDataService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbCalculateOneImportData;
import com.boco.SYS.entity.TbOrganRateScoreImportData;
import com.boco.SYS.mapper.TbCalculateOneImportDataMapper;
import com.boco.SYS.mapper.TbOrganRateScoreImportDataMapper;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.ExcelImport;
import com.boco.TONY.utils.IDGeneratorUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TbCalculateOneImportDataҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class TbCalculateOneImportDataServiceImpl extends GenericService<TbCalculateOneImportData, String> implements TbCalculateOneImportDataService {

    @Autowired
    private TbCalculateOneImportDataMapper tbCalculateOneImportDataMapper;
    @Autowired
    private TbOrganRateScoreImportDataMapper tbOrganRateScoreImportDataMapper;
    /*�ϴ���ַ*/
    @Value("${upload.path}")
    private String uploadPath;


    /*�����������*/
    @Override
    public Map<String, String> enterDataByMonth(MultipartFile file, String operCode, HttpServletRequest request) throws Exception {
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("code", "true");
        resultMap.put("msg", "¼��ɹ���");
        InputStream is = null;
        try {
            //-------������-------
            String originalFilename = file.getOriginalFilename();
            originalFilename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + BocoUtils.getNowDate() + BocoUtils.getNowTime() + "_" + originalFilename;
            File fileExcel = new File(uploadPath, originalFilename);
            is = file.getInputStream();
            FileUtils.copyInputStreamToFile(is, fileExcel);

            //�ж�¼��ļƻ��ļ��Ƿ���ȷ
            List<String> excelTitleList = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 1);
            if (excelTitleList == null || excelTitleList.size() == 0) {
                resultMap.put("code", "false");
                resultMap.put("msg", "��¼����ȷ���ļ���");
                return resultMap;
            }
            String excelTitle = excelTitleList.get(0);
            String title = "����ģʽһ��ʷ���ݵ������";
            if (!title.equals(excelTitle)) {
                resultMap.put("code", "false");
                resultMap.put("msg", "��¼����ȷ���ļ���");
                return resultMap;
            }

            //��ȡ���¼������У�
            List<String> cellValues = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 3);

            String[] cells = new String[cellValues.size()];
            for (int i = 0; i < cellValues.size(); i++) {
                cells[i] = "code" + i;
            }

            //��ȡ�����������
            List<Map<String, Object>> maps = ExcelImport.excelList(cells, uploadPath + originalFilename, 4, 1, cells.length);

            //-------�������-------
            //��ȡ�ϸ����·�
            SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.MONTH, -1);
            Date m1 = c.getTime();
            String month = format.format(m1);
            BigDecimal type = CalculateOneParam.CALCULATE_MONTH;

            // ������������ѵ������ݣ��͸���
            TbCalculateOneImportData importDataQuery = new TbCalculateOneImportData();
            importDataQuery.setType(type);
            importDataQuery.setMonth(month);
            List<TbCalculateOneImportData> tbCalculateOneImportDataList = tbCalculateOneImportDataMapper.selectByAttr(importDataQuery);
            if (tbCalculateOneImportDataList != null && tbCalculateOneImportDataList.size() != 0) {
                HashMap<String, Object> queryMap = new HashMap<>();
                queryMap.put("type", type);
                queryMap.put("month", month);
                tbCalculateOneImportDataMapper.deleteByMonthAndType(queryMap);
            }

            // ������������ѵ������ݣ��͸���
            TbOrganRateScoreImportData tbOrganRateScoreImportDataQuery = new TbOrganRateScoreImportData();
            tbOrganRateScoreImportDataQuery.setMonth(month);
            List<TbOrganRateScoreImportData> tbOrganRateScoreImportDataList = tbOrganRateScoreImportDataMapper.selectByAttr(tbOrganRateScoreImportDataQuery);
            if (tbOrganRateScoreImportDataList != null && tbOrganRateScoreImportDataList.size() != 0) {
                HashMap<String, Object> queryMap = new HashMap<>();
                queryMap.put("month", month);
                tbOrganRateScoreImportDataMapper.deleteByMonth(queryMap);
            }


            ArrayList<TbCalculateOneImportData> calculateOneImportDataList = new ArrayList<>();
            ArrayList<TbOrganRateScoreImportData> rateScoreImportDataList = new ArrayList<>();
            // �����������
            for (Map<String, Object> map : maps) {
                if (!"".equals(map.get("code0"))) {

                    //��������
                    TbCalculateOneImportData importData = new TbCalculateOneImportData();
                    importData.setId(IDGeneratorUtils.getSequence());
                    importData.setMonth(month);
                    importData.setType(type);
                    importData.setOrgancode(getOrganCode(map.get("code0").toString()));

                    importData.setCode1(getSafeCount(map.get("code1")));
                    importData.setCode2(getSafeCount(map.get("code2")));
                    importData.setCode3(getSafeCount(map.get("code3")));
                    importData.setCode4(getSafeCount(map.get("code4")));
                    importData.setCode5(getSafeCount(map.get("code5")));
                    importData.setCode6(getSafeCount(map.get("code6")));
                    importData.setCode7(getSafeCount(map.get("code7")));
                    importData.setCode8(getSafeCount(map.get("code8")));
                    importData.setCode9(getSafeCount(map.get("code9")));
                    importData.setCode10(getSafeCount(map.get("code10")));
                    importData.setCode11(getSafeCount(map.get("code11")));
                    importData.setCode12(getSafeCount(map.get("code12")));
                    importData.setCode13(getSafeCount(map.get("code13")));
                    importData.setCode14(getSafeCount(map.get("code14")));
                    importData.setCode15(getSafeCount(map.get("code15")));
                    importData.setCode16(getSafeCount(map.get("code16")));
                    importData.setCode17(getSafeCount(map.get("code17")));
                    importData.setCode18(getSafeCount(map.get("code18")));
                    importData.setCode19(getSafeCount(map.get("code19")));
                    importData.setCode20(getSafeCount(map.get("code20")));
                    importData.setCode21(getSafeCount(map.get("code21")));
                    importData.setCode22(getSafeCount(map.get("code22")));
                    importData.setCode23(getSafeCount(map.get("code23")));
                    importData.setCode24(getSafeCount(map.get("code24")));
                    importData.setCode25(getSafeCount(map.get("code25")));
                    importData.setCode26(getSafeCount(map.get("code26")));
                    importData.setCode27(getSafeCount(map.get("code27")));
                    importData.setCode28(getSafeCount(map.get("code28")));
                    //�����ֶΣ�����
                    importData.setCode29(getSafeCount("0"));
                    importData.setCode30(getSafeCount("0"));
                    importData.setCode31(getSafeCount("0"));
                    importData.setCode32(getSafeCount("0"));
                    importData.setCode33(getSafeCount("0"));
                    importData.setCode34(getSafeCount("0"));
                    importData.setCode35(getSafeCount("0"));

                    calculateOneImportDataList.add(importData);

                    //��������
                    TbOrganRateScoreImportData rateScoreImportData = new TbOrganRateScoreImportData();
                    rateScoreImportData.setId(IDGeneratorUtils.getSequence());
                    rateScoreImportData.setMonth(month);
                    rateScoreImportData.setOrgancode(getOrganCode(map.get("code0").toString()));

                    rateScoreImportData.setMonthEndRatio(getSafeCount(map.get("code29")));
                    rateScoreImportData.setYearBeginRatio(getSafeCount(map.get("code30")));
                    rateScoreImportData.setPersonDepositYearIncrement(getSafeCount(map.get("code31")));
                    rateScoreImportData.setCompanyDepositYearIncrement(getSafeCount(map.get("code32")));
                    rateScoreImportData.setNewLoanRatio(getSafeCount(map.get("code33")));
                    rateScoreImportData.setBankAverageLoanRatio(getSafeCount(map.get("code34")));

                    rateScoreImportDataList.add(rateScoreImportData);
                }
            }
            //������������
            tbOrganRateScoreImportDataMapper.insertBatch(rateScoreImportDataList);
            //�����������
            tbCalculateOneImportDataMapper.insertBatch(calculateOneImportDataList);

            //���ɾ���ļ�
            delFile(fileExcel);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("¼��ʧ��");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }


    //��ȡ������
    private String getOrganCode(String code0) {
        HashMap<String, String> codeMap = new HashMap<>();
        codeMap.put("����", "11000013");
        codeMap.put("���", "12004390");
        codeMap.put("�ӱ�", "13000015");
        codeMap.put("ɽ��", "14012817");
        codeMap.put("���ɹ�", "15010852");
        codeMap.put("����", "21000015");
        codeMap.put("����", "22000030");
        codeMap.put("������", "23016187");
        codeMap.put("�Ϻ�", "31000017");
        codeMap.put("����", "32000018");
        codeMap.put("�㽭", "33016227");
        codeMap.put("����", "34000010");
        codeMap.put("����", "35000011");
        codeMap.put("����", "36014148");
        codeMap.put("ɽ��", "37000025");
        codeMap.put("����", "41022445");
        codeMap.put("����", "42014856");
        codeMap.put("����", "43020900");
        codeMap.put("�㶫", "44000012");
        codeMap.put("����", "45009366");
        codeMap.put("����", "46000014");
        codeMap.put("����", "50016751");
        codeMap.put("�Ĵ�", "51000012");
        codeMap.put("����", "52007323");
        codeMap.put("����", "53008846");
        codeMap.put("����", "54000736");
        codeMap.put("����", "61000089");
        codeMap.put("����", "62006424");
        codeMap.put("�ຣ", "63002179");
        codeMap.put("����", "64002353");
        codeMap.put("�½�", "65006860");
        codeMap.put("����", "21014952");
        codeMap.put("����", "33000072");
        codeMap.put("����", "35008816");
        codeMap.put("�ൺ", "37000013");
        codeMap.put("����", "44008995");

        return codeMap.get(code0);
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    private BigDecimal getSafeCount(BigDecimal b1) {
        if (b1 == null) {
            return BigDecimal.ZERO;
        }
        return b1;
    }

    private BigDecimal getSafeCount(Object b1) {
        if (b1 == null || "".equals(b1.toString())) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(b1.toString());
    }

    /*ɾ���ļ�*/
    private boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }

}