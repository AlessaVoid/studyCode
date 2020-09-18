package com.boco.PM.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebRoleInfo;
import com.boco.SYS.util.Json;

import java.util.List;
import java.util.Map;

/**
 * WebRoleInfo业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebRoleInfoService extends IGenericService<WebRoleInfo, String> {
    /**
     * TODO 新增角色信息.
     *
     * @param webRoleInfo
     * @param fdOper
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月23日    	  杜旭    新建
     * </pre>
     * @throws Exception
     */
    public Json InsertWebRoleInfo(WebRoleInfo webRoleInfo, FdOper fdOper, String funCodes) throws Exception;

    /**
     * TODO 修改角色信息.
     *
     * @param webRoleInfo
     * @param fdOper
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月23日    	  杜旭    新建
     * </pre>
     * @throws Exception
     */
    public Json updateWebRoleInfo(WebRoleInfo webRoleInfo, FdOper fdOper, String funCodes) throws Exception;

    /**
     * TODO 删除角色信息.
     *
     * @param webRoleInfo
     * @param fdOper
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月23日    	  杜旭    新建
     * </pre>
     * @throws Exception
     */
    public Json deleteWebRoleInfo(WebRoleInfo webRoleInfo) throws Exception;

    /**
     * TODO 角色名称模糊查询记录.
     *
     * @param webRoleInfo
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年3月8日    	  杜旭    新建
     * </pre>
     */
    public List<WebRoleInfo> selectByLike(WebRoleInfo webRoleInfo);
    public List<WebRoleInfo> selectByLikeOrder(WebRoleInfo webRoleInfo);
    public List<WebRoleInfo> selectByOrganLevel(String organLevel, String operCode);

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
     * TODO 根据角色编码集查询角色名称.
     *
     * @param roleCode
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年5月26日    	  杜旭    新建
     * </pre>
     */
    public String findNameByProdCodes(String roleCode);

    /**
     * TODO 根据角色编码集查询角色名称.
     *
     * @param roleCode
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年8月17日    	   谷立羊  新建
     * </pre>
     */
    public String findNameByRoleCode(String roleCode);
}