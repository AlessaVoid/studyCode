package com.boco.SYS.controller;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.global.Dic;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 贷种组合详情业务控制层
 *
 * @author txn
 * @describe ReportCombDetailController
 * @date 2020-05-13
 */
@RequestMapping("/webReportCombDetail")
@Controller
public class ReportCombDetailController extends BaseController {
    @Autowired
    IWebLoanCombDetailService webLoanDetailService;

    @RequestMapping(value = "/listUI", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合明细", funCode = "SYS-12", funName = "通过贷种编号查询贷种明细", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getLoanComposeDetail(String combCode) throws Exception {
        authButtons();
        List<String> childCombinationCodeList = webLoanDetailService.getLoanCombDetailInfoByCombCode(combCode);
        setAttribute("childNodes", childCombinationCodeList);
        return basePath + "/SYS/webReportComb/webReportCombInfoList";
    }

}
