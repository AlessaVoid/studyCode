package com.boco.PUB.controller.tbCalculateOne;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.boco.PUB.service.tbCalculateOne.ITbCalculateOneRankService;
import com.boco.SYS.util.BocoUtils;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import com.boco.SYS.entity.TbCalculateOneRank;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.base.BaseController;

/**
 * Action���Ʋ�
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-09      txn      �����½�
 * </pre>
 */
@Controller
@RequestMapping(value = "/tbCalculateOneRank/")
public class TbCalculateOneRankController extends BaseController {
    @Autowired
    private ITbCalculateOneRankService tbCalculateOneRankService;

    //�����б���ϸ��Ϣ���������޸�ҳ��
    @RequestMapping("listUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-31-02", funName = "�����б�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String listUI() throws Exception {
        authButtons();
        return basePath + "/PUB/tbCalculateOne/tbCalculateOneRank/tbCalculateOneRankList";
    }

    @RequestMapping("infoUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-31-02", funName = "������ϸҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String infoUI(TbCalculateOneRank tbCalculateOneRank) throws Exception {
        setAttribute("TbCalculateOneRank", tbCalculateOneRankService.selectByPK(tbCalculateOneRank.getId()));
        return basePath + "/PUB/tbCalculateOne/tbCalculateOneRank/tbCalculateOneRankInfo";
    }

    @RequestMapping("insertUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-31-02", funName = "��������ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String insertUI() throws Exception {
        return basePath + "/PUB/tbCalculateOne/tbCalculateOneRank/tbCalculateOneRankAdd";
    }

    @RequestMapping("updateUI")
    @SystemLog(tradeName = "��������", funCode = "PUB-31-02", funName = "�����޸�ҳ��", accessType = AccessType.READ, level = Debug.DEBUG)
    public String updateUI(TbCalculateOneRank tbCalculateOneRank) throws Exception {
        setAttribute("TbCalculateOneRank", tbCalculateOneRankService.selectByPK(tbCalculateOneRank.getId()));
        return basePath + "/PUB/tbCalculateOne/tbCalculateOneRank/tbCalculateOneRankEdit";
    }

    /**
     * TODO ��ѯtb_calculate_one_rank��ҳ����
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "findPage", method = RequestMethod.POST)
    @SystemLog(tradeName = "��������", funCode = "PUB-31-02", funName = "�����б�����", accessType = AccessType.READ, level = Debug.DEBUG)
    public @ResponseBody
    String findPage(TbCalculateOneRank tbCalculateOneRank) throws Exception {
        setPageParam();
        List<TbCalculateOneRank> list = new ArrayList<>();
        try {
            list = tbCalculateOneRankService.selectByAttr(tbCalculateOneRank);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return pageData(list);
    }

    /**
     * TODO ����tb_calculate_one_rank.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "insert")
    @SystemLog(tradeName = "��������", funCode = "PUB-31-02", funName = "����", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String insert(TbCalculateOneRank tbCalculateOneRank, HttpSession session) throws Exception {
        tbCalculateOneRank.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        tbCalculateOneRank.setCreateTime(BocoUtils.getTime());
        tbCalculateOneRank.setUpdateTime(BocoUtils.getTime());
        tbCalculateOneRank.setCreateOper(getSessionOperInfo().getOperCode());
        tbCalculateOneRank.setUpdateOper(getSessionOperInfo().getOperCode());

        int status = tbCalculateOneRank.getStatus();
        if (status == 1) {
            TbCalculateOneRank tb = new TbCalculateOneRank();
            tb.setStatus(status);
            tb.setType(tbCalculateOneRank.getType());
            List<TbCalculateOneRank> list = tbCalculateOneRankService.selectByAttr(tb);
            for (TbCalculateOneRank tempTb : list) {
                tempTb.setStatus(2);
                tbCalculateOneRankService.updateByPK(tempTb);
            }
        }
        tbCalculateOneRankService.insertEntity(tbCalculateOneRank);
        return this.json.returnMsg("true", "�����ɹ�!").toJson();
    }

    /**
     * TODO �޸�tb_calculate_one_rank.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015��11��18��    	    ����    �½�
     * </pre>
     */
    @RequestMapping(value = "update")
    @SystemLog(tradeName = "��������", funCode = "PUB-31-02", funName = "�޸�", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String update(TbCalculateOneRank tbCalculateOneRank, HttpSession session) throws Exception {
        tbCalculateOneRank.setUpdateTime(BocoUtils.getTime());
        tbCalculateOneRank.setUpdateOper(getSessionOperInfo().getOperCode());
        int status = tbCalculateOneRank.getStatus();
        if (status == 1) {
            TbCalculateOneRank tb = new TbCalculateOneRank();
            tb.setStatus(status);
            tb.setType(tbCalculateOneRank.getType());
            List<TbCalculateOneRank> list = tbCalculateOneRankService.selectByAttr(tb);
            for (TbCalculateOneRank tempTb : list) {
                tempTb.setStatus(2);
                tbCalculateOneRankService.updateByPK(tempTb);
            }
        }

        tbCalculateOneRankService.updateByPK(tbCalculateOneRank);
        return this.json.returnMsg("true", "�޸ĳɹ�!").toJson();
    }

    /**
     * TODO ɾ��tb_calculate_one_rank
     *
     * @return <pre>
     * �޸�����            �޸���      �޸�ԭ��
     * 2014-10-29      ����      �����½�
     * </pre>
     */
    @RequestMapping(value = "delete")
    @SystemLog(tradeName = "��������", funCode = "PUB-31-02", funName = "ɾ��", accessType = AccessType.WRITE, level = Debug.INFO)
    public @ResponseBody
    String delete(TbCalculateOneRank tbCalculateOneRank, HttpSession session) throws Exception {
        tbCalculateOneRankService.deleteByPK(tbCalculateOneRank.getId());
        return this.json.returnMsg("true", "ɾ���ɹ�!").toJson();
    }
}