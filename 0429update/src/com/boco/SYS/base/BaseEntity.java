package com.boco.SYS.base;
/**
 * 
 * 
 *  ʵ�������.
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2015��11��30��    	    ����    �½�
 * </pre>
 */
public abstract class BaseEntity{
	/** �����ֶ� */
	protected java.lang.String sortColumn;
	
	public java.lang.String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(java.lang.String sortColumn) {
		this.sortColumn = sortColumn == null ? null : sortColumn.trim();
	}
	
	
}
