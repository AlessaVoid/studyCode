package com.boco.TONY.biz.loanplan.POJO.DO;

import com.boco.TONY.biz.loanplan.POJO.DTO.TbPlanDetailDTO;
import com.boco.TONY.biz.planadjust.POJO.DO.TbPlanAdjustDetailDO;

import java.io.Serializable;

/**
 * �Ŵ�������Ϣչʾ�б�
 *
 * @author tony
 * @describe FdOrganPlanInfo
 * @date 2019-10-09
 */
public class FdOrganPlanInfo implements Serializable {
    private static final long serialVersionUID = 3409791497878035948L;
    //������
    private String organCode;
    //��������
    private String organName;
    //�ƻ�������Ϣ
    private TbPlanDetailDTO tbPlanDetailDTO;
    //�ƻ�����������Ϣ
    private TbPlanAdjustDetailDO tbPlanAdjustDetail;

    public String getOrganCode() {
        return organCode;
    }

    public FdOrganPlanInfo setOrganCode(String organCode) {
        this.organCode = organCode;
        return this;
    }

    public String getOrganName() {
        return organName;
    }

    public FdOrganPlanInfo setOrganName(String organName) {
        this.organName = organName;
        return this;
    }

    public TbPlanDetailDTO getTbPlanDetailDTO() {
        return tbPlanDetailDTO;
    }

    public FdOrganPlanInfo setTbPlanDetailDTO(TbPlanDetailDTO tbPlanDetailDTO) {
        this.tbPlanDetailDTO = tbPlanDetailDTO;
        return this;
    }

    public TbPlanAdjustDetailDO getTbPlanAdjustDetail() {
        return tbPlanAdjustDetail;
    }

    public FdOrganPlanInfo setTbPlanAdjustDetail(TbPlanAdjustDetailDO tbPlanAdjustDetail) {
        this.tbPlanAdjustDetail = tbPlanAdjustDetail;
        return this;
    }

    @Override
    public String toString() {
        return "FdOrganPlanInfo{" +
                "organCode='" + organCode + '\'' +
                ", organName='" + organName + '\'' +
                ", tbPlanDetailDTO=" + tbPlanDetailDTO +
                '}';
    }
}
