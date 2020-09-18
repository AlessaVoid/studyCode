package com.boco.TONY.biz.loancomb.controller;

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
 * @author tony
 * @describe WebLoanCombController
 * @date 2019-09-17
 */
@RequestMapping("/webLoanDetail")
@Controller
public class WebLoanCombDetailController extends BaseController {
    @Autowired
    IWebLoanCombDetailService webLoanDetailService;

    @RequestMapping(value = "/listUI", method = RequestMethod.GET)
    @SystemLog(tradeName = "贷种组合明细", funCode = "PM-27", funName = "通过贷种编号查询贷种明细", accessType = Dic.AccessType.READ, level = Dic.Debug.DEBUG)
    public String getLoanComposeDetail(String combCode) throws Exception {
        authButtons();
        List<String> childCombinationCodeList = webLoanDetailService.getLoanCombDetailInfoByCombCode(combCode);
        setAttribute("childNodes", childCombinationCodeList);
        return basePath + "/PM/webLoanInfo/webLoanInfoList";
    }

}
