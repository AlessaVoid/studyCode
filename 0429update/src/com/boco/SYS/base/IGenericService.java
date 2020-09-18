package com.boco.SYS.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * 
 * 
 *  包括了基本的增删改查操作.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月3日    	    杨滔    新建
 * </pre>
 */
public interface IGenericService<T, PK extends Serializable>{
	/**
	 * 
	 *
	 * TODO 新增一条实体.
	 *
	 * @param t
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月2日    	    杨滔    新建
	 * </pre>
	 */
	public int insertEntity(T t) throws Exception;
	
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
	public int insertBatch(List<T> list) throws Exception;
	
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
	public int deleteByPK(PK pk) throws Exception;
	
	/**
	 * 
	 *
	 * TODO 根据主键批量删除.
	 *
	 * @param list
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月30日    	    杨滔    新建
	 * </pre>
	 */
	public int deleteBatchByPKList(List<PK> list) throws Exception;
	
	/**
	 * 
	 *
	 * TODO 自定义where条件删除，参数为where后的条件字符串，如"id='1' and name='a'".
	 *
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月4日    	    杨滔    新建
	 * </pre>
	 */
	public int deleteByWhere(String whereStr) throws Exception;
	
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
	public int updateByPK(T t) throws Exception;
	
	/**
	 * 
	 *
	 * TODO 批量修改,参数map:列名=列值，idList={1,2,3...}.
	 *
	 * @param map
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月30日    	    杨滔    新建
	 * </pre>
	 */
	public int updateBatch(Map<String, Object> map) throws Exception;
	
	/**
	 * 
	 *
	 * TODO 自定义where条件修改，参数为where后的条件字符串，如"id='1' and name='a'".
	 *
	 * @param whereStr
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月4日    	    杨滔    新建
	 * </pre>
	 */
	public int updateByWhere(String whereStr) throws Exception;
	
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
	public T selectByPK(PK pk) throws Exception;
	public T selectByPKOper(PK pk) throws Exception;
	/**
	 * 
	 *
	 * TODO 根据非主键唯一约束查询记录,没有唯一约束时查询所有记录.
	 *
	 * @param k
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月5日    	    杨滔    新建
	 * </pre>
	 */
	public List<T> selectByUQ(T t) throws Exception;
	
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
	public List<T> selectByAttr(T t) throws Exception;
	String selectOrganNameZX(String thiscode);
	public List<T> selectab(T t) throws Exception;
	public List<T> selectDistinctTbwarn(T t) throws Exception;
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
	public List<T> selectByLikeAttr(T t) throws Exception;
	
	/**
	 * 
	 *
	 * TODO 自定义where条件查询，参数为where后的条件字符串，如"id='1' and name='a'".
	 *
	 * @param whereStr
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月5日    	    杨滔    新建
	 * </pre>
	 */
	public List<T> selectByWhere(String whereStr) throws Exception;
	
	/**
	 * 
	 *
	 * TODO 根据条件查询记录数.
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月5日    	    杨滔    新建
	 * </pre>
	 */
	public int countByAttr(T t) throws Exception;
	
	/**
	 * 
	 *
	 * TODO 根据模糊条件查询记录数.
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月5日    	    杨滔    新建
	 * </pre>
	 */
	public int countByLikeAttr(T t) throws Exception;
	
	/**
	 * 
	 *
	 * TODO 根据Where语句查询记录数.
	 *
	 * @param whereStr
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月7日    	    杨滔    新建
	 * </pre>
	 */
	public int countByWhere(String whereStr) throws Exception;
	
	/**
	 * 
	 *
	 * TODO 根据错误码查询错误信息.
	 *
	 * @param errorCode 错误码
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月11日    	     代策    新建
	 * </pre>
	 */
	public String getErrorInfo(String errorCode) throws Exception;
}
