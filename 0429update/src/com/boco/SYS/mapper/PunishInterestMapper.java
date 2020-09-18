package com.boco.SYS.mapper;

import com.boco.SYS.entity.PunishDetail;
import com.boco.SYS.entity.RptBaseinfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface PunishInterestMapper {

    String getcomb1(@Param("loanKind") String loanKind);

    List<Map<String, Integer>> selectOrgan1();

    List<RptBaseinfo> queryDetail(@Param("rptDate") String rptDate,@Param("organ") String organ);

    float getMonthOcc(RptBaseinfo rptBaseinfo1);

    float getMonthLimit(RptBaseinfo rptBaseinfo1);

    void insertPunishDetail(PunishDetail punishDetail);
}
