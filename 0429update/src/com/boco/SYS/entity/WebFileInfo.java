package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * �ļ���Ϣ||�ñ�洢�ļ���Ϣʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class WebFileInfo extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** �ļ�ID||�ļ�ID */
		@EntityParaAnno(zhName="�ļ�ID")
	private java.lang.String fileId;
	/** �ļ�����||�ļ����� */
		@EntityParaAnno(zhName="�ļ�����")
	private java.lang.String fileType;
	/** �ļ�����||�ļ����� */
		@EntityParaAnno(zhName="�ļ�����")
	private java.lang.String fileName;
	/** �洢·��||�洢·�� */
		@EntityParaAnno(zhName="�洢·��")
	private java.lang.String filePath;
	/** ��С||��С */
		@EntityParaAnno(zhName="��С")
	private java.lang.String fileSize;
	/** ����||���� */
		@EntityParaAnno(zhName="����")
	private java.lang.String fileDesc;
	/** ����ʱ��||����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private java.lang.String createTime;
	/** ��������||�������� */
		@EntityParaAnno(zhName="��������")
	private java.lang.String createDate;
	/** ����Ա����||����Ա���� */
		@EntityParaAnno(zhName="����Ա����")
	private java.lang.String operCode;
	
	/** setter\getter���� */
	/** �ļ�ID||�ļ�ID */
	public void setFileId(java.lang.String fileId) {
		this.fileId = fileId == null ? null : fileId.trim();
	}
	public java.lang.String getFileId() {
		return this.fileId;
	}
	/** �ļ�����||�ļ����� */
	public void setFileType(java.lang.String fileType) {
		this.fileType = fileType == null ? null : fileType.trim();
	}
	public java.lang.String getFileType() {
		return this.fileType;
	}
	/** �ļ�����||�ļ����� */
	public void setFileName(java.lang.String fileName) {
		this.fileName = fileName == null ? null : fileName.trim();
	}
	public java.lang.String getFileName() {
		return this.fileName;
	}
	/** �洢·��||�洢·�� */
	public void setFilePath(java.lang.String filePath) {
		this.filePath = filePath == null ? null : filePath.trim();
	}
	public java.lang.String getFilePath() {
		return this.filePath;
	}
	/** ��С||��С */
	public void setFileSize(java.lang.String fileSize) {
		this.fileSize = fileSize == null ? null : fileSize.trim();
	}
	public java.lang.String getFileSize() {
		return this.fileSize;
	}
	/** ����||���� */
	public void setFileDesc(java.lang.String fileDesc) {
		this.fileDesc = fileDesc == null ? null : fileDesc.trim();
	}
	public java.lang.String getFileDesc() {
		return this.fileDesc;
	}
	/** ����ʱ��||����ʱ�� */
	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}
	public java.lang.String getCreateTime() {
		return this.createTime;
	}
	/** ��������||�������� */
	public void setCreateDate(java.lang.String createDate) {
		this.createDate = createDate == null ? null : createDate.trim();
	}
	public java.lang.String getCreateDate() {
		return this.createDate;
	}
	/** ����Ա����||����Ա���� */
	public void setOperCode(java.lang.String operCode) {
		this.operCode = operCode == null ? null : operCode.trim();
	}
	public java.lang.String getOperCode() {
		return this.operCode;
	}
}