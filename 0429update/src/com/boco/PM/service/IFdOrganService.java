package com.boco.PM.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOrgan;

import java.util.List;
import java.util.Map;

/**
 * FdOrgan业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IFdOrganService extends IGenericService<FdOrgan, java.lang.String> {
    /**
     * TODO 获取一分机构.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年3月4日    	  杜旭    新建
     * </pre>
     */
    public List<FdOrgan> selectProvOrgan();

    /**
     * TODO 查询某机构的直属下级机构
     *
     * @param thiscode
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年4月5日    	  李沐阳    新建
     * </pre>
     */
    public List<Map<String, String>> findNextOrganCodeList(String thiscode);

    /**
     * TODO 获取机构名称.
     *
     * @param organs
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年3月30日    	  杜旭    新建
     * </pre>
     */
    public List<Map<String, String>> selectProvName(String[] organs);

    /**
     * TODO 查询产品机构
     *
     * @param areaCodes
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年10月9日    	   谷立羊  新建
     * </pre>
     */
    public List<FdOrgan> selectByAreaCodes(String[] organs);

    /**
     * 通过名称联想
     *
     * @param fdOrgan 机构名称
     * @return
     */
    public List<String> selectByName(FdOrgan fdOrgan);
    public List<String> selectByNameZX(String thiscode);


    /**
     * 通过上级机构联想
     *
     * @param fdOrgan 上级机构
     * @return
     */
    public String selectUporganByThisCodeList(String thiscode);
    /**
     * 通过thiscode查询OrganName
     *
     * @param fdOrgan 上级机构
     * @return
     */
    public String selectOrganName(String thiscode);

    /**
     * 通过机构号联想
     *
     * @param fdOrgan 机构编码
     * @return
     */
    List<String> selectByThisCode(FdOrgan fdOrgan);

    List<String> selectByThisCodeZX(FdOrgan fdOrgan);

    List<FdOrgan> selectList(Map<String,Object> map);


    List<FdOrgan> selectByLevel(Map<String,Object> map);

    /**
     * 根据机构号，查询下级所有机构
     * @param uporgan
     * @return
     */
    List<Map<String, Object>> selectByUporgan(String uporgan);

    //查询当前机构
    List<Map<String, Object>> selectByOrganCode(String thiscode);

    /**
     * @Description 根据机构号查询要展示的下级机构
     * @Author liujinbo
     * @Date 2020/3/13
     * @param thiscode
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectOrgan(String thiscode);
}