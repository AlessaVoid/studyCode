package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.FdOper;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FdOper数据访问层(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface FdOperMapper extends GenericMapper<FdOper, HashMap<String, Object>> {
    /**
     * TODO 通过角色查找柜员列表.
     *
     * @param roleCode
     * @return
     * @throws DataAccessException <pre>
     *                                                         修改日期        修改人    修改原因
     *                                                         2016年2月26日    	    杨滔    新建
     *                                                         </pre>
     */
    public List<FdOper> selectOpersByRole(String roleCode) throws DataAccessException;

    String selectOperName(String opercode) throws Exception;

    String selectOpersOrgan(String opercode)throws Exception;

    /**
     * TODO 与角色功能表关联查询授权用户下拉列表数据.
     *
     * @param query
     * @return
     * @throws Exception <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2016年3月7日    	  杜旭    新建
     *                                     </pre>
     */
    public List<Map<String, String>> getPowerList(Map<String, String> query) throws Exception;

    /**
     * TODO 复杂流程： 与角色功能表关联查询授权下拉列表数据.
     *
     * @param query
     * @return
     * @throws Exception <pre>
     *                                     修改日期        修改人    修改原因
     *                                     2016年4月5日    	  杜旭    新建
     *                                     </pre>
     */
    public List<Map<String, String>> getPowerListByRole(Map<String, Object> query) throws Exception;

    /**
     * 修改用户密码
     */
    void updateOperPassword(FdOper fdOper);

    void resetOperPassword(FdOper fdOper);

    void updateOperOrgan(FdOper fdOper);

    List<FdOper> selectByLikeStartAttr(FdOper fdOper);
}