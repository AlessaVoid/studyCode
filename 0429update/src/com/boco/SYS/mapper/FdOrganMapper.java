package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.FdOrgan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * FdOrgan数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface FdOrganMapper extends GenericMapper<FdOrgan, java.lang.String> {
    /**
     * TODO 获取一分机构.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年3月4日    	  杜旭    新建
     * </pre>
     */
    public List<FdOrgan> selectProvOrgan();

    String selectOrganName(String thiscode);

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
     * TODO 获取产品发售机构
     *
     * @param areaCodes
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年10月9日    	   谷立羊  新建
     * </pre>
     */
    public List<FdOrgan> selectByAreaCodes(String[] organs);

    /**
     * 联想,机构名称
     *
     * @param fdOrgan 名称
     * @return
     */
    public List<String> selectByName(FdOrgan fdOrgan);

    /**
     * 联想,机构编码
     *
     * @param fdOrgan 名称
     * @return
     */
    public List<String> selectByThisCode(FdOrgan fdOrgan);

    /**
     * 根据机构号，查询下级所有机构
     * @param uporgan
     * @return
     */
    List<Map<String, Object>> selectByUporgan(String uporgan);

    List<FdOrgan> selectList(Map<String, Object> map);


    //查询当前机构
    List<Map<String, Object>> selectByOrganCode(String thiscode);

    List<String> selectByLikeThisCode(FdOrgan fdOrgan);

    List<String> selectByLikeThisCodeZX(FdOrgan fdOrgan);

    List<String> selectByLikeName(FdOrgan fdOrgan);
    List<String> selectByLikeNameZX(String thiscode);

    String selectUporganByThisCodeList(String thiscode);//根据thiscodelist查询uporgan

    String selectOrganNameBycode(String thiscode);

    List<String> selectthiscodeByLikeOrganname(String uporgan);//通过organname模糊查询对应的thiscode

    List<Map<String, Object>> selectByLikeOrganname(@Param("organname") String organname);

    //去除了总行，总行机构，邮储
    List<FdOrgan> selectByAttr2(FdOrgan fdOrgan);
}