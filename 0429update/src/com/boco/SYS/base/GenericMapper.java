package com.boco.SYS.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.FdOrgan;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

/**
 * 
 * 
 *  提供基本的增、删、改、查接口.
 *  T 具体的实体类型
 *  K 实体的主键
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月2日    	    杨滔    新建
 * </pre>
 */
public interface GenericMapper <T,PK extends Serializable>{
	/**
	 * 
	 *
	 * TODO 新增一条实体.
	 *
	 * @param t
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月2日    	    杨滔    新建
	 * </pre>
	 */
	public int insertEntity(T t) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 批量插入.
	 *
	 * @param list
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月2日    	    杨滔    新建
	 * </pre>
	 */
	public int insertBatch(List<T> list) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 根据主键删一条记录.
	 *
	 * @param k
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月2日    	    杨滔    新建
	 * </pre>
	 */
	public int deleteByPK(PK pk) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 根据主键批量删除.
	 *
	 * @param list
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月30日    	    杨滔    新建
	 * </pre>
	 */
	public int deleteBatchByPKList(List<PK> list) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 自定义where条件删除，参数为where后的条件字符串，如"id='1' and name='a'".
	 *
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月4日    	    杨滔    新建
	 * </pre>
	 */
	public int deleteByWhere(@Param(value = "whereStr") String whereStr) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 根据主键修改一条记录.
	 *
	 * @param k
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月2日    	    杨滔    新建
	 * </pre>
	 */
	public int updateByPK(T t) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 批量修改,参数map:列名=列值，keyList={1,2,3...}.
	 *
	 * @param map
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月30日    	    杨滔    新建
	 * </pre>
	 */
	public int updateBatch(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 自定义where条件修改，参数为where后的条件字符串，如"id='1' and name='a'".
	 *
	 * @param whereStr
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月4日    	    杨滔    新建
	 * </pre>
	 */
	public int updateByWhere(@Param(value = "whereStr") String whereStr) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 根据主键查询一条记录.
	 *
	 * @param k
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月2日    	    杨滔    新建
	 * </pre>
	 */
	public T selectByPK(PK pk) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 根据非主键唯一约束查询记录,没有唯一约束时查询所有记录.
	 *
	 * @param k
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月5日    	    杨滔    新建
	 * </pre>
	 */
	public List<T> selectByUQ(T t) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 根据条件查询记录.
	 *
	 * @param t
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月2日    	    杨滔    新建
	 * </pre>
	 */
	public List<T> selectByAttr(T t) throws DataAccessException;

	//根据机构ID查询机构名称
	String selectOrganNameZX (String thiscode);

	public List<T> selectByType(T t) throws DataAccessException;

	public List<T> selectDistinctTbwarn(T t) throws DataAccessException;

	public List<T> select(T t) throws DataAccessException;

	/**
	 * 
	 *
	 * TODO 根据模糊条件查询记录.
	 *
	 * @param t
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月2日    	    杨滔    新建
	 * </pre>
	 */
	public List<T> selectByLikeAttr(T t) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 自定义where条件查询，参数为where后的条件字符串，如"id='1' and name='a'".
	 *
	 * @param whereStr
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月5日    	    杨滔    新建
	 * </pre>
	 */
	public List<T> selectByWhere(@Param(value = "whereStr") String whereStr) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 根据条件查询记录数.
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月5日    	    杨滔    新建
	 * </pre>
	 */
	public int countByAttr(T t) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 根据模糊条件查询记录数.
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月5日    	    杨滔    新建
	 * </pre>
	 */
	public int countByLikeAttr(T t) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO 自定义where条件查询记录数，参数为where后的条件字符串，如"id='1' and name='a'".
	 *
	 * @param whereStr
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月7日    	    杨滔    新建
	 * </pre>
	 */
	public int countByWhere(@Param(value = "whereStr") String whereStr) throws DataAccessException;

    List<FdOrgan> selectByLevel(Map<String, Object> map);


	T selectByPKOper(PK pk);
}
