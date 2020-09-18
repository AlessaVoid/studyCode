package com.boco.PM.service.impl;

import com.boco.PM.service.IFdOrganService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.mapper.FdOrganMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FdOrgan业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class FdOrganService extends GenericService<FdOrgan, java.lang.String> implements IFdOrganService {
    @Autowired
    private FdOrganMapper fdOrganMapper;


    /**
     * TODO 获取一分机构.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年3月4日    	  杜旭    新建
     * </pre>
     */
    public List<FdOrgan> selectProvOrgan() {
        return fdOrganMapper.selectProvOrgan();
    }


    /**
     * TODO 查询某机构的直属下级机构
     *
     * @param thiscode
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年4月5日    	  李沐阳    新建
     * </pre>
     */
    public List<Map<String, String>> findNextOrganCodeList(String thiscode) {
        return fdOrganMapper.findNextOrganCodeList(thiscode);
    }

    /**
     * TODO 获取机构名称.
     *
     * @param organs
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年3月30日    	  杜旭    新建
     * </pre>
     */
    public List<Map<String, String>> selectProvName(String[] organs) {
        return fdOrganMapper.selectProvName(organs);
    }


    @Override
    public List<FdOrgan> selectByAreaCodes(String[] organs) {
        return fdOrganMapper.selectByAreaCodes(organs);
    }

    /**
     * 联想机构名称
     *
     * @param name 机构名称
     * @return
     */
    @Override
    public List<String> selectByName(FdOrgan fdOrgan) {
        List<String> nameList = fdOrganMapper.selectByLikeName(fdOrgan);
        return nameList;
    }
    @Override
    public List<String> selectByNameZX(String thiscode) {
        List<String> nameList = fdOrganMapper.selectByLikeNameZX(thiscode);
        return nameList;
    }


    /**
     * 联想上级机构
     *
     * @param uporgan  上级机构
     * @return
     */
    @Override
    public String selectUporganByThisCodeList(String thiscode) {
        String uporganList = fdOrganMapper.selectUporganByThisCodeList(thiscode);
        return uporganList;
    }
    /**
     * 查询OrganName
     *
     * @param thiscode 机构代码
     * @return
     */
    @Override
    public String selectOrganName(String thiscode) {
        String OrganName = fdOrganMapper.selectOrganNameBycode(thiscode);
        return OrganName;
    }




    /**
     * 联想机构编码
     *
     * @param thisCode 机构编码
     * @return
     */
    @Override
    public List<String> selectByThisCode(FdOrgan fdOrgan) {
        List<String> codeList = fdOrganMapper.selectByLikeThisCode(fdOrgan);
        return codeList;
    }

    @Override
    public List<String> selectByThisCodeZX(FdOrgan fdOrgan) {
        List<String> codeList = fdOrganMapper.selectByLikeThisCodeZX(fdOrgan);
        return codeList;
    }

    @Override
    public List<FdOrgan> selectList(Map<String, Object> map) {

        List<FdOrgan> list = fdOrganMapper.selectList(map);
        return list;
    }


    @Override
    public List<FdOrgan> selectByLevel(Map<String, Object> map) {
        List<FdOrgan> list = fdOrganMapper.selectByLevel(map);
        return list;
    }


    @Override
    public List<Map<String, Object>> selectByUporgan(String uporgan) {

        return fdOrganMapper.selectByUporgan(uporgan);
    }

    @Override
    public List<Map<String, Object>> selectByOrganCode(String thiscode) {
        List<Map<String, Object>> organList = fdOrganMapper.selectByOrganCode(thiscode);
        //简化总行
        for (Map<String, Object> map : organList) {
            if ("11005293".equals(map.get("thiscode"))) {
                map.put("organname", "总行");
                break;
            }
        }
        return organList;
    }

    /*根据机构号查询要展示的下级机构*/
    @Override
    public List<Map<String, Object>> selectOrgan(String thiscode) {

        FdOrgan thisOrgan = fdOrganMapper.selectByPK(thiscode);
        FdOrgan searchOrgan = new FdOrgan();
        searchOrgan.setUporgan(thisOrgan.getThiscode());
        List<FdOrgan> fdOrganList = fdOrganMapper.selectByAttr2(searchOrgan);


        //一分查询2分机构就加本部
        if ("1".equals(thisOrgan.getOrganlevel())) {
            FdOrgan searchOrgan_1 = new FdOrgan();
            searchOrgan_1.setProvincecode(thisOrgan.getThiscode());
            searchOrgan_1.setOrganlevel("1");
            List<FdOrgan> organList_1 = fdOrganMapper.selectByAttr(searchOrgan_1);
            FdOrgan fdOrgan = organList_1.get(0);
            fdOrgan.setOrganname(fdOrgan.getOrganname() + "本部");
            fdOrganList.add(fdOrgan);
        }

        //总行查询加上总行自己
        String organStr3 = "11005293";
        if (organStr3.contains(thisOrgan.getThiscode())) {
            FdOrgan searchOrgan_1 = new FdOrgan();
            searchOrgan_1.setThiscode(organStr3);
            List<FdOrgan> organList_3 = fdOrganMapper.selectByAttr(searchOrgan_1);
            for (FdOrgan fdOrgan1 : organList_3) {
                fdOrganList.add(fdOrgan1);
            }
        }

        //转换格式
        List<Map<String, Object>> organList = new ArrayList<>();
        for (FdOrgan fdOrgan : fdOrganList) {
            Map<String, Object> organMap = new HashMap<>(2);
            organMap.put("organcode", fdOrgan.getThiscode());
            organMap.put("thiscode", fdOrgan.getThiscode());
            organMap.put("organname", fdOrgan.getOrganname());
            organMap.put("uporgan", fdOrgan.getUporgan());
            organList.add(organMap);
        }

        //简化总行
        for (Map<String, Object> map : organList) {
            if ("11005293".equals(map.get("thiscode"))) {
                map.put("organname", "总行");
                break;
            }
        }
        return organList;
    }


}