package com.boco.AL.controller.punish;

import com.boco.AL.service.ITbPunishListService;
import com.boco.AL.service.ITbPunishResultService;
import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.entity.TbPunishList;
import com.boco.SYS.entity.TbPunishResult;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
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
@RequestMapping(value = "/tbTradeManger/tbPunishResult/")
public class TbPunishResultController extends BaseController {
    @Autowired
    private ITbPunishResultService tbPunishResultService;
    @Autowired
    private ITbPunishListService tbPunishListService;

    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("listUI")
    @SystemLog(tradeName = "��������", funCode = "AL", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/AL/tbPunishManage/tbPunishResult/List";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "��������", funCode = "AL", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbPunishResult tbPunishResult) throws Exception {
        tbPunishResult = tbPunishResultService.selectByPK(tbPunishResult.getPunishId());
        TbPunishList tbPunishList = tbPunishListService.selectByPK(tbPunishResult.getPunishListId());
        setAttribute("TbPunishResult", tbPunishResult);
        setAttribute("tbPunishList", tbPunishList);
        return basePath + "/AL/tbPunishManage/tbPunishResult/Info";
    }

    @RequestMapping("commitUI")
    @SystemLog(tradeName = "��������", funCode = "AL", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbPunishResult tbPunishResult) throws Exception {
        tbPunishResult = tbPunishResultService.selectByPK(tbPunishResult.getPunishId());
        TbPunishList tbPunishList = tbPunishListService.selectByPK(tbPunishResult.getPunishListId());
        setAttribute("TbPunishResult", tbPunishResult);
        setAttribute("tbPunishList", tbPunishList);
        return basePath + "/AL/tbPunishManage/tbPunishResult/Edit";
    }

    /**
     * TODO ��ѯtb_punish_result��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "AL", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbPunishResult tbPunishResult) throws Exception {
        setPageParam();
        tbPunishResult.setOrganCode(getSessionOrgan().getThiscode());
        List<TbPunishResult> list = tbPunishResultService.selectByAttr(tbPunishResult);
        return pageData(list);
    }


    /**
     * TODO �޸�tb_punish_result.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "��������", funCode = "AL", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(TbPunishResult tbPunishResult, HttpSession session) throws Exception {
        tbPunishResult.setState(TbPunishResult.STATE_APPROVALING);
        tbPunishResultService.updateByPK(tbPunishResult);
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }
}