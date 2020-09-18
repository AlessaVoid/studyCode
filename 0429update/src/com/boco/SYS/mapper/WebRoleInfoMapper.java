package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebRoleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * WebRoleInfo数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface WebRoleInfoMapper extends GenericMapper<WebRoleInfo, String> {
    /**
     * TODO 角色名称模糊查询记录.
     *
     * @param webRoleInfo
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年3月8日    	  杜旭    新建
     * </pre>
     */
    List<WebRoleInfo> selectByLike(WebRoleInfo webRoleInfo);
    List<WebRoleInfo> selectByLikeOrder(WebRoleInfo webRoleInfo);
    List<WebRoleInfo> selectByOrganLevel(@Param(value = "organLevel") String organLevel, @Param(value = "operCode") String operCode);

    List<WebRoleInfo> selectBySuperAdmin(String organLevel);
    List<WebRoleInfo> selectByOrganLevelZero(@Param(value = "organLevelZero") String organLevelZero);
    List<WebRoleInfo> selectByOrganLevelOne(@Param(value = "organLevelOne") String organLevelOne);
    List<WebRoleInfo> selectByOrganLevelTwo(@Param(value = "organLevelTwo") String organLevelTwo);
    /**
     * TODO 联想输入角色编号.
     *
     * @param webRoleInfo
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年4月11日    	    秦海舟   新建
     * </pre>
     */
    public List<Map<String, String>> selectRoleCode(WebRoleInfo webRoleInfo);

    /**
     * TODO 联想输入角色名称.
     *
     * @param webRoleInfo
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年4月11日    	    秦海舟   新建
     * </pre>
     */
    public List<Map<String, String>> selectRoleName(WebRoleInfo webRoleInfo);

    /**
     * TODO 根据角色编码集查询数据.
     *
     * @param map
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年5月26日    	  杜旭    新建
     * </pre>
     */
    public List<WebRoleInfo> selectByRoleCodes(Map map);

}