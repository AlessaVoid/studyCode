package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 文件监控实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbMonitorFile extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 文件接收日期 yyyymmdd */
		@EntityParaAnno(zhName="文件接收日期 yyyymmdd")
	private String fileDate;
	/** 文件来自哪个系统 */
		@EntityParaAnno(zhName="文件来自哪个系统")
	private String fileSystemid;
	/** 文件类型 prodSync产品同步、check对账、organ机构、oper柜员、a3701报表 */
		@EntityParaAnno(zhName="文件类型 prodSync产品同步、check对账、organ机构、oper柜员、a3701报表")
	private String fileType;
	/** 文件路径 */
		@EntityParaAnno(zhName="文件路径")
	private String filePath;
	/**  文件接收时间 yyyyMMddHHmmss */
		@EntityParaAnno(zhName=" 文件接收时间 yyyyMMddHHmmss")
	private String fileRecvTime;
	/** 文件接收状态 0成功1失败 */
		@EntityParaAnno(zhName="文件接收状态 0成功1失败")
	private Integer fileRecvStatus;
	/** 文件开始处理时间 */
		@EntityParaAnno(zhName="文件开始处理时间")
	private String fileProcessStart;
	/** 文件处理结束时间 */
		@EntityParaAnno(zhName="文件处理结束时间")
	private String fileProcessFinish;
	/** 文件处理（内容）状态 0成功 1失败 */
		@EntityParaAnno(zhName="文件处理（内容）状态 0成功 1失败")
	private Integer fileProcessStatus;
	
	/** setter\getter方法 */
	/** 文件接收日期 yyyymmdd */
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate == null ? null : fileDate.trim();
	}
	public String getFileDate() {
		return this.fileDate;
	}
	/** 文件来自哪个系统 */
	public void setFileSystemid(String fileSystemid) {
		this.fileSystemid = fileSystemid == null ? null : fileSystemid.trim();
	}
	public String getFileSystemid() {
		return this.fileSystemid;
	}
	/** 文件类型 prodSync产品同步、check对账、organ机构、oper柜员、a3701报表 */
	public void setFileType(String fileType) {
		this.fileType = fileType == null ? null : fileType.trim();
	}
	public String getFileType() {
		return this.fileType;
	}
	/** 文件路径 */
	public void setFilePath(String filePath) {
		this.filePath = filePath == null ? null : filePath.trim();
	}
	public String getFilePath() {
		return this.filePath;
	}
	/**  文件接收时间 yyyyMMddHHmmss */
	public void setFileRecvTime(String fileRecvTime) {
		this.fileRecvTime = fileRecvTime == null ? null : fileRecvTime.trim();
	}
	public String getFileRecvTime() {
		return this.fileRecvTime;
	}
	/** 文件接收状态 0成功1失败 */
	public void setFileRecvStatus(Integer fileRecvStatus) {
		this.fileRecvStatus = fileRecvStatus;
	}
	public Integer getFileRecvStatus() {
		return this.fileRecvStatus;
	}
	/** 文件开始处理时间 */
	public void setFileProcessStart(String fileProcessStart) {
		this.fileProcessStart = fileProcessStart == null ? null : fileProcessStart.trim();
	}
	public String getFileProcessStart() {
		return this.fileProcessStart;
	}
	/** 文件处理结束时间 */
	public void setFileProcessFinish(String fileProcessFinish) {
		this.fileProcessFinish = fileProcessFinish == null ? null : fileProcessFinish.trim();
	}
	public String getFileProcessFinish() {
		return this.fileProcessFinish;
	}
	/** 文件处理（内容）状态 0成功 1失败 */
	public void setFileProcessStatus(Integer fileProcessStatus) {
		this.fileProcessStatus = fileProcessStatus;
	}
	public Integer getFileProcessStatus() {
		return this.fileProcessStatus;
	}
}