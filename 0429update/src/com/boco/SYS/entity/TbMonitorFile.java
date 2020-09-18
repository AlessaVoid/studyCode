package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * �ļ����ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbMonitorFile extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** �ļ��������� yyyymmdd */
		@EntityParaAnno(zhName="�ļ��������� yyyymmdd")
	private String fileDate;
	/** �ļ������ĸ�ϵͳ */
		@EntityParaAnno(zhName="�ļ������ĸ�ϵͳ")
	private String fileSystemid;
	/** �ļ����� prodSync��Ʒͬ����check���ˡ�organ������oper��Ա��a3701���� */
		@EntityParaAnno(zhName="�ļ����� prodSync��Ʒͬ����check���ˡ�organ������oper��Ա��a3701����")
	private String fileType;
	/** �ļ�·�� */
		@EntityParaAnno(zhName="�ļ�·��")
	private String filePath;
	/**  �ļ�����ʱ�� yyyyMMddHHmmss */
		@EntityParaAnno(zhName=" �ļ�����ʱ�� yyyyMMddHHmmss")
	private String fileRecvTime;
	/** �ļ�����״̬ 0�ɹ�1ʧ�� */
		@EntityParaAnno(zhName="�ļ�����״̬ 0�ɹ�1ʧ��")
	private Integer fileRecvStatus;
	/** �ļ���ʼ����ʱ�� */
		@EntityParaAnno(zhName="�ļ���ʼ����ʱ��")
	private String fileProcessStart;
	/** �ļ��������ʱ�� */
		@EntityParaAnno(zhName="�ļ��������ʱ��")
	private String fileProcessFinish;
	/** �ļ��������ݣ�״̬ 0�ɹ� 1ʧ�� */
		@EntityParaAnno(zhName="�ļ��������ݣ�״̬ 0�ɹ� 1ʧ��")
	private Integer fileProcessStatus;
	
	/** setter\getter���� */
	/** �ļ��������� yyyymmdd */
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate == null ? null : fileDate.trim();
	}
	public String getFileDate() {
		return this.fileDate;
	}
	/** �ļ������ĸ�ϵͳ */
	public void setFileSystemid(String fileSystemid) {
		this.fileSystemid = fileSystemid == null ? null : fileSystemid.trim();
	}
	public String getFileSystemid() {
		return this.fileSystemid;
	}
	/** �ļ����� prodSync��Ʒͬ����check���ˡ�organ������oper��Ա��a3701���� */
	public void setFileType(String fileType) {
		this.fileType = fileType == null ? null : fileType.trim();
	}
	public String getFileType() {
		return this.fileType;
	}
	/** �ļ�·�� */
	public void setFilePath(String filePath) {
		this.filePath = filePath == null ? null : filePath.trim();
	}
	public String getFilePath() {
		return this.filePath;
	}
	/**  �ļ�����ʱ�� yyyyMMddHHmmss */
	public void setFileRecvTime(String fileRecvTime) {
		this.fileRecvTime = fileRecvTime == null ? null : fileRecvTime.trim();
	}
	public String getFileRecvTime() {
		return this.fileRecvTime;
	}
	/** �ļ�����״̬ 0�ɹ�1ʧ�� */
	public void setFileRecvStatus(Integer fileRecvStatus) {
		this.fileRecvStatus = fileRecvStatus;
	}
	public Integer getFileRecvStatus() {
		return this.fileRecvStatus;
	}
	/** �ļ���ʼ����ʱ�� */
	public void setFileProcessStart(String fileProcessStart) {
		this.fileProcessStart = fileProcessStart == null ? null : fileProcessStart.trim();
	}
	public String getFileProcessStart() {
		return this.fileProcessStart;
	}
	/** �ļ��������ʱ�� */
	public void setFileProcessFinish(String fileProcessFinish) {
		this.fileProcessFinish = fileProcessFinish == null ? null : fileProcessFinish.trim();
	}
	public String getFileProcessFinish() {
		return this.fileProcessFinish;
	}
	/** �ļ��������ݣ�״̬ 0�ɹ� 1ʧ�� */
	public void setFileProcessStatus(Integer fileProcessStatus) {
		this.fileProcessStatus = fileProcessStatus;
	}
	public Integer getFileProcessStatus() {
		return this.fileProcessStatus;
	}
}