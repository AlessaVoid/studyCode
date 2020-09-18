package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 文件信息||该表存储文件信息实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class WebFileInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 文件ID||文件ID */
		@EntityParaAnno(zhName="文件ID")
	private java.lang.String fileId;
	/** 文件类型||文件类型 */
		@EntityParaAnno(zhName="文件类型")
	private java.lang.String fileType;
	/** 文件名称||文件名称 */
		@EntityParaAnno(zhName="文件名称")
	private java.lang.String fileName;
	/** 存储路径||存储路径 */
		@EntityParaAnno(zhName="存储路径")
	private java.lang.String filePath;
	/** 大小||大小 */
		@EntityParaAnno(zhName="大小")
	private java.lang.String fileSize;
	/** 描述||描述 */
		@EntityParaAnno(zhName="描述")
	private java.lang.String fileDesc;
	/** 创建时间||创建时间 */
		@EntityParaAnno(zhName="创建时间")
	private java.lang.String createTime;
	/** 创建日期||创建日期 */
		@EntityParaAnno(zhName="创建日期")
	private java.lang.String createDate;
	/** 操作员代码||操作员代码 */
		@EntityParaAnno(zhName="操作员代码")
	private java.lang.String operCode;
	
	/** setter\getter方法 */
	/** 文件ID||文件ID */
	public void setFileId(java.lang.String fileId) {
		this.fileId = fileId == null ? null : fileId.trim();
	}
	public java.lang.String getFileId() {
		return this.fileId;
	}
	/** 文件类型||文件类型 */
	public void setFileType(java.lang.String fileType) {
		this.fileType = fileType == null ? null : fileType.trim();
	}
	public java.lang.String getFileType() {
		return this.fileType;
	}
	/** 文件名称||文件名称 */
	public void setFileName(java.lang.String fileName) {
		this.fileName = fileName == null ? null : fileName.trim();
	}
	public java.lang.String getFileName() {
		return this.fileName;
	}
	/** 存储路径||存储路径 */
	public void setFilePath(java.lang.String filePath) {
		this.filePath = filePath == null ? null : filePath.trim();
	}
	public java.lang.String getFilePath() {
		return this.filePath;
	}
	/** 大小||大小 */
	public void setFileSize(java.lang.String fileSize) {
		this.fileSize = fileSize == null ? null : fileSize.trim();
	}
	public java.lang.String getFileSize() {
		return this.fileSize;
	}
	/** 描述||描述 */
	public void setFileDesc(java.lang.String fileDesc) {
		this.fileDesc = fileDesc == null ? null : fileDesc.trim();
	}
	public java.lang.String getFileDesc() {
		return this.fileDesc;
	}
	/** 创建时间||创建时间 */
	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}
	public java.lang.String getCreateTime() {
		return this.createTime;
	}
	/** 创建日期||创建日期 */
	public void setCreateDate(java.lang.String createDate) {
		this.createDate = createDate == null ? null : createDate.trim();
	}
	public java.lang.String getCreateDate() {
		return this.createDate;
	}
	/** 操作员代码||操作员代码 */
	public void setOperCode(java.lang.String operCode) {
		this.operCode = operCode == null ? null : operCode.trim();
	}
	public java.lang.String getOperCode() {
		return this.operCode;
	}
}