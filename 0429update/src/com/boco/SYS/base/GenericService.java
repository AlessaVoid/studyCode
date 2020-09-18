package com.boco.SYS.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.boco.SYS.mapper.TbWarnMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.apache.tools.ant.types.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.boco.SYS.entity.GfErrInfo;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.GfErrInfoMapper;
import com.boco.SYS.util.Json;
import com.boco.SYS.util.MapUtil;
/**
 * 
 * 
 *  定义了基本的增删改查.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月3日    	    杨滔    新建
 * </pre>
 */
public abstract class GenericService<T extends BaseEntity, PK extends Serializable> implements IGenericService<T, PK>{
	public static Logger logger = Logger.getLogger(GenericService. class);
	/**
	 * 返回json对象
	 */
	protected Json json = Json.getInstance();
	@Autowired
	private GenericMapper<T, PK> baseMapper;
	@Autowired
	private GfErrInfoMapper gfErrInfoMapper;
	@Override
	public int insertEntity(T t) throws Exception {
		return baseMapper.insertEntity(t);
	}
	@Override
	public int insertBatch(List<T> list) throws Exception {
		int result = baseMapper.insertBatch(list);
		if(result != list.size()){
			//新增记录条数与新增结果不一致!
			throw new SystemException("w105");
		}
		return result;
	}
	@Override
	public int deleteByPK(PK pk) throws Exception {
		int result = baseMapper.deleteByPK(pk);

		if(result < 1){
			//没有与删除条件匹配的记录!
			throw new SystemException("w106");
		}else if(result > 1){
			//有多条与删除条件匹配的记录!
			throw new SystemException("w107");
		}
		return result;
	}
	@Override
	public int deleteBatchByPKList(List<PK> list) throws Exception {
		int	result = baseMapper.deleteBatchByPKList(list);
		if(result != list.size()){
			//删除条件与删除结果记录数不一致!
			throw new SystemException("w108");
		}
		return result;
	}
	@Override
	public int deleteByWhere(String whereStr) throws Exception {
		int	result = baseMapper.deleteByWhere(whereStr);
//		if(result < 1){
//			//没有与删除条件匹配的记录!
//			throw new SystemException("w106");
//		}
		return result;
	}
	@Override
	public int updateByPK(T t) throws Exception {
		int result = baseMapper.updateByPK(t);
		if(result < 1){
			//没有与修改条件匹配的记录!
			throw new SystemException("w109");
		}else if(result > 1){
			//有多条与修改条件匹配的记录!
			throw new SystemException("w110");
		}
		return result;
	}
	
	@Override
	public int updateBatch(Map<String, Object> map) throws Exception {
		int	result = baseMapper.updateBatch(map);
		if(result != ((List<?>)map.get("keyList")).size()){
			//修改条件与删除结果记录数不一致!
			throw new SystemException("w111");
		}
		return result;
	}
	@Override
	public int updateByWhere(String whereStr) throws Exception {
		int	result = baseMapper.updateByWhere(whereStr);
		if(result < 1){
			//没有与修改条件匹配的记录!
			throw new SystemException("w109");
		}
		return result;
	}
	@Override
	public T selectByPK(PK pk) throws Exception {
		return baseMapper.selectByPK(pk);
	}
	@Override
	public T selectByPKOper(PK pk) throws Exception {
		return baseMapper.selectByPKOper(pk);
	}
	@Override
	public List<T> selectByUQ(T t) throws Exception {
		return baseMapper.selectByUQ(t);
	}
	@Override
	public List<T> selectByAttr(T t) throws Exception {
		return baseMapper.selectByAttr(t);
	}
	@Override
	public String selectOrganNameZX(String thiscode){
		return baseMapper.selectOrganNameZX(thiscode);
	}
	@Override
	public List<T> selectab(T t) throws Exception {
		return baseMapper.select(t);
	}
	@Override
	public List<T> selectDistinctTbwarn(T t) throws Exception {
		return baseMapper.selectDistinctTbwarn(t);
	}
	@Override
	public List<T> selectByLikeAttr(T t) throws Exception {
		return baseMapper.selectByLikeAttr(t);
	}
	@Override
	public List<T> selectByWhere(String whereStr) throws Exception{
		return baseMapper.selectByWhere(whereStr);
	}
	@Override
	public int countByAttr(T t) throws Exception {
		return baseMapper.countByAttr(t);
	}
	@Override
	public int countByLikeAttr(T t) throws Exception {
		return baseMapper.countByLikeAttr(t);
	}
	@Override
	public int countByWhere(@Param(value = "whereStr") String whereStr) throws Exception{
		return baseMapper.countByWhere(whereStr);
	}
	
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
	public String getErrorInfo(String errorCode) throws Exception{
		GfErrInfo gfErrInfo=new GfErrInfo();
		gfErrInfo.setGfRetCode(errorCode);
		gfErrInfo.setGfSysCode("99370000000");
		gfErrInfo.setOtherSysCode("99370000000");
		gfErrInfo.setOtherRetCode(errorCode);
		GfErrInfo errInfo=gfErrInfoMapper.selectByPK(MapUtil.beanToMap(gfErrInfo));
		if(errInfo!=null){
			logger.info("***************操作失败：错误信息"+errInfo.getGfRetInfo()+"********************");
			return errInfo.getGfRetInfo();
		}else{
			return "没有找到对应错误信息，错误码："+errorCode;
		}
	}
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
	public String getErrorInfo(String errorCode,String code) throws Exception{
		GfErrInfo gfErrInfo=new GfErrInfo();
		gfErrInfo.setGfRetCode(errorCode);
		gfErrInfo.setGfSysCode("99370000000");
		gfErrInfo.setOtherSysCode("99370000000");
		gfErrInfo.setOtherRetCode(errorCode);
		GfErrInfo errInfo=gfErrInfoMapper.selectByPK(MapUtil.beanToMap(gfErrInfo));
		if(errInfo!=null){
			return errInfo.getGfRetInfo().replace("*", code);
		}else{
			return "没有找到对应错误信息，错误码："+errorCode;
		}
	}

}