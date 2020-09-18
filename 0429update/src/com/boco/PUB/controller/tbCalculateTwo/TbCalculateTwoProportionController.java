package com.boco.PUB.controller.tbCalculateTwo;

import com.boco.PUB.service.tbCalculateTwo.ITbCalculateTwoProportionService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbCalculateTwoProportion;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.TONY.utils.IDGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Action���Ʋ�
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbCalculateTwoProportion/")
public class TbCalculateTwoProportionController extends BaseController {
    @Autowired
    private ITbCalculateTwoProportionService tbCalculateTwoProportionService;


    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-31-04", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI() throws Exception {
        List<TbCalculateTwoProportion> list = tbCalculateTwoProportionService.selectByAttr(new TbCalculateTwoProportion());
        for (int i = 0; i < list.size(); i++) {
            setAttribute("TbCalculateTwoProportion_" + i, list.get(i));
        }

        return basePath + "/PUB/tbCalculateTwo/tbCalculateTwoProportion/tbCalculateTwoProportionEdit";
    }


    /**
     * TODO �޸�tb_calculate_one_proportion.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "��������", funCode = "PUB-31-04", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(HttpServletRequest request, HttpSession session) throws Exception {
        try {

            List<TbCalculateTwoProportion> list = new ArrayList<>(4);
            for (int i = 0; i < 4; i++) {
                TbCalculateTwoProportion tbCalculateOneProportion = new TbCalculateTwoProportion();
                String code = request.getParameter("code_" + i);
                String name = request.getParameter("name_" + i);
                BigDecimal sortType = new BigDecimal(request.getParameter("sortType_" + i));
                BigDecimal weight = new BigDecimal(request.getParameter("weight_" + i));
                tbCalculateOneProportion.setId(IDGeneratorUtils.getSequence());
                tbCalculateOneProportion.setCode(code);
                tbCalculateOneProportion.setName(name);
                tbCalculateOneProportion.setSortType(sortType);
                tbCalculateOneProportion.setWeight(weight);
                list.add(tbCalculateOneProportion);
            }
            tbCalculateTwoProportionService.deleteAll();
            tbCalculateTwoProportionService.insertBatch(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }

}