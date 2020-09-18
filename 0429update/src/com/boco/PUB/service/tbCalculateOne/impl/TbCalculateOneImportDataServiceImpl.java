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
 * TbCalculateOneImportData业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class TbCalculateOneImportDataServiceImpl extends GenericService<TbCalculateOneImportData, String> implements TbCalculateOneImportDataService {

    @Autowired
    private TbCalculateOneImportDataMapper tbCalculateOneImportDataMapper;
    @Autowired
    private TbOrganRateScoreImportDataMapper tbOrganRateScoreImportDataMapper;
    /*上传地址*/
    @Value("${upload.path}")
    private String uploadPath;


    /*导入测算数据*/
    @Override
    public Map<String, String> enterDataByMonth(MultipartFile file, String operCode, HttpServletRequest request) throws Exception {
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("code", "true");
        resultMap.put("msg", "录入成功！");
        InputStream is = null;
        try {
            //-------处理表格-------
            String originalFilename = file.getOriginalFilename();
            originalFilename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + BocoUtils.getNowDate() + BocoUtils.getNowTime() + "_" + originalFilename;
            File fileExcel = new File(uploadPath, originalFilename);
            is = file.getInputStream();
            FileUtils.copyInputStreamToFile(is, fileExcel);

            //判断录入的计划文件是否正确
            List<String> excelTitleList = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 1);
            if (excelTitleList == null || excelTitleList.size() == 0) {
                resultMap.put("code", "false");
                resultMap.put("msg", "请录入正确的文件！");
                return resultMap;
            }
            String excelTitle = excelTitleList.get(0);
            String title = "测算模式一历史数据导入表样";
            if (!title.equals(excelTitle)) {
                resultMap.put("code", "false");
                resultMap.put("msg", "请录入正确的文件！");
                return resultMap;
            }

            //获取表格录入项（横行）
            List<String> cellValues = ExcelImport.excelImpOneRow(uploadPath + originalFilename, 1, 3);

            String[] cells = new String[cellValues.size()];
            for (int i = 0; i < cellValues.size(); i++) {
                cells[i] = "code" + i;
            }

            //获取表格所有内容
            List<Map<String, Object>> maps = ExcelImport.excelList(cells, uploadPath + originalFilename, 4, 1, cells.length);

            //-------数据入库-------
            //获取上个月月份
            SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.MONTH, -1);
            Date m1 = c.getTime();
            String month = format.format(m1);
            BigDecimal type = CalculateOneParam.CALCULATE_MONTH;

            // 测算如果存在已导入数据，就覆盖
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

            // 评分如果存在已导入数据，就覆盖
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
            // 构建导入对象
            for (Map<String, Object> map : maps) {
                if (!"".equals(map.get("code0"))) {

                    //测算数据
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
                    //多余字段，备用
                    importData.setCode29(getSafeCount("0"));
                    importData.setCode30(getSafeCount("0"));
                    importData.setCode31(getSafeCount("0"));
                    importData.setCode32(getSafeCount("0"));
                    importData.setCode33(getSafeCount("0"));
                    importData.setCode34(getSafeCount("0"));
                    importData.setCode35(getSafeCount("0"));

                    calculateOneImportDataList.add(importData);

                    //评分数据
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
            //插入评分数据
            tbOrganRateScoreImportDataMapper.insertBatch(rateScoreImportDataList);
            //插入测算数据
            tbCalculateOneImportDataMapper.insertBatch(calculateOneImportDataList);

            //最后删除文件
            delFile(fileExcel);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("录入失败");
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


    //获取机构号
    private String getOrganCode(String code0) {
        HashMap<String, String> codeMap = new HashMap<>();
        codeMap.put("北京", "11000013");
        codeMap.put("天津", "12004390");
        codeMap.put("河北", "13000015");
        codeMap.put("山西", "14012817");
        codeMap.put("内蒙古", "15010852");
        codeMap.put("辽宁", "21000015");
        codeMap.put("吉林", "22000030");
        codeMap.put("黑龙江", "23016187");
        codeMap.put("上海", "31000017");
        codeMap.put("江苏", "32000018");
        codeMap.put("浙江", "33016227");
        codeMap.put("安徽", "34000010");
        codeMap.put("福建", "35000011");
        codeMap.put("江西", "36014148");
        codeMap.put("山东", "37000025");
        codeMap.put("河南", "41022445");
        codeMap.put("湖北", "42014856");
        codeMap.put("湖南", "43020900");
        codeMap.put("广东", "44000012");
        codeMap.put("广西", "45009366");
        codeMap.put("海南", "46000014");
        codeMap.put("重庆", "50016751");
        codeMap.put("四川", "51000012");
        codeMap.put("贵州", "52007323");
        codeMap.put("云南", "53008846");
        codeMap.put("西藏", "54000736");
        codeMap.put("陕西", "61000089");
        codeMap.put("甘肃", "62006424");
        codeMap.put("青海", "63002179");
        codeMap.put("宁夏", "64002353");
        codeMap.put("新疆", "65006860");
        codeMap.put("大连", "21014952");
        codeMap.put("宁波", "33000072");
        codeMap.put("厦门", "35008816");
        codeMap.put("青岛", "37000013");
        codeMap.put("深圳", "44008995");

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

    /*删除文件*/
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