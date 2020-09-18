package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * FdExternsys实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class FdExternsys extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** externsyscode */
	private java.lang.String externsyscode;
	/** othercode */
	private java.lang.String othercode;
	/** externsysname */
	private java.lang.String externsysname;
	/** externsysflag */
	private java.lang.String externsysflag;
	/** opendate */
	private java.lang.String opendate;
	/** lastexterndate */
	private java.lang.String lastexterndate;
	/** externdate */
	private java.lang.String externdate;
	/** serial */
	private java.lang.Long serial;
	/** openflag */
	private java.lang.String openflag;
	/** state */
	private java.lang.String state;
	/** maxmsgno */
	private java.lang.Long maxmsgno;
	/** stopdate */
	private java.lang.String stopdate;
	/** ipaddr */
	private java.lang.String ipaddr;
	/** port */
	private java.lang.String port;
	/** prikey */
	private java.lang.String prikey;
	/** trankey */
	private java.lang.String trankey;
	/** mackey */
	private java.lang.String mackey;
	/** maintainname */
	private java.lang.String maintainname;
	/** maintainphone */
	private java.lang.String maintainphone;
	/** ftpusername */
	private java.lang.String ftpusername;
	/** ftppassword */
	private java.lang.String ftppassword;
	/** ftpgetpath */
	private java.lang.String ftpgetpath;
	/** ftpputpath */
	private java.lang.String ftpputpath;
	/** spanproorgan */
	private java.lang.String spanproorgan;
	/** modifydate */
	private java.lang.String modifydate;
	/** modifyorgan */
	private java.lang.String modifyorgan;
	/** 第一本地端口 */
	private java.lang.String modifyoper;
	/** 第二外部端口 */
	private java.lang.String lport;
	/** 第二本地端口 */
	private java.lang.String port1;
	/** 第三外部端口 */
	private java.lang.String lport1;
	/** 第三本地端口 */
	private java.lang.String port2;
	/** 第四外部端口 */
	private java.lang.String lport2;
	/** 第四本地端口 */
	private java.lang.String port3;
	/** 虚拟机构号 */
	private java.lang.String lport3;
	/** 虚拟操作员 */
	private java.lang.String organcode;
	/** 3DES密钥 */
	private java.lang.String opercode;
	/** 最大并发子进程数 */
	private java.lang.String pin3key;
	/** 通信配置文件 */
	private java.lang.String maxchildproc;
	/** 发送队列键值路径 */
	private java.lang.String cfgfilename;
	/** sndqueuepath */
	private java.lang.String sndqueuepath;
	/** yysflag */
	private java.lang.String yysflag;
	
	/** setter\getter方法 */
	/** externsyscode */
	public void setExternsyscode(java.lang.String externsyscode) {
		this.externsyscode = externsyscode == null ? null : externsyscode.trim();
	}
	public java.lang.String getExternsyscode() {
		return this.externsyscode;
	}
	/** othercode */
	public void setOthercode(java.lang.String othercode) {
		this.othercode = othercode == null ? null : othercode.trim();
	}
	public java.lang.String getOthercode() {
		return this.othercode;
	}
	/** externsysname */
	public void setExternsysname(java.lang.String externsysname) {
		this.externsysname = externsysname == null ? null : externsysname.trim();
	}
	public java.lang.String getExternsysname() {
		return this.externsysname;
	}
	/** externsysflag */
	public void setExternsysflag(java.lang.String externsysflag) {
		this.externsysflag = externsysflag == null ? null : externsysflag.trim();
	}
	public java.lang.String getExternsysflag() {
		return this.externsysflag;
	}
	/** opendate */
	public void setOpendate(java.lang.String opendate) {
		this.opendate = opendate == null ? null : opendate.trim();
	}
	public java.lang.String getOpendate() {
		return this.opendate;
	}
	/** lastexterndate */
	public void setLastexterndate(java.lang.String lastexterndate) {
		this.lastexterndate = lastexterndate == null ? null : lastexterndate.trim();
	}
	public java.lang.String getLastexterndate() {
		return this.lastexterndate;
	}
	/** externdate */
	public void setExterndate(java.lang.String externdate) {
		this.externdate = externdate == null ? null : externdate.trim();
	}
	public java.lang.String getExterndate() {
		return this.externdate;
	}
	/** serial */
	public void setSerial(java.lang.Long serial) {
		this.serial = serial;
	}
	public java.lang.Long getSerial() {
		return this.serial;
	}
	/** openflag */
	public void setOpenflag(java.lang.String openflag) {
		this.openflag = openflag == null ? null : openflag.trim();
	}
	public java.lang.String getOpenflag() {
		return this.openflag;
	}
	/** state */
	public void setState(java.lang.String state) {
		this.state = state == null ? null : state.trim();
	}
	public java.lang.String getState() {
		return this.state;
	}
	/** maxmsgno */
	public void setMaxmsgno(java.lang.Long maxmsgno) {
		this.maxmsgno = maxmsgno;
	}
	public java.lang.Long getMaxmsgno() {
		return this.maxmsgno;
	}
	/** stopdate */
	public void setStopdate(java.lang.String stopdate) {
		this.stopdate = stopdate == null ? null : stopdate.trim();
	}
	public java.lang.String getStopdate() {
		return this.stopdate;
	}
	/** ipaddr */
	public void setIpaddr(java.lang.String ipaddr) {
		this.ipaddr = ipaddr == null ? null : ipaddr.trim();
	}
	public java.lang.String getIpaddr() {
		return this.ipaddr;
	}
	/** port */
	public void setPort(java.lang.String port) {
		this.port = port == null ? null : port.trim();
	}
	public java.lang.String getPort() {
		return this.port;
	}
	/** prikey */
	public void setPrikey(java.lang.String prikey) {
		this.prikey = prikey == null ? null : prikey.trim();
	}
	public java.lang.String getPrikey() {
		return this.prikey;
	}
	/** trankey */
	public void setTrankey(java.lang.String trankey) {
		this.trankey = trankey == null ? null : trankey.trim();
	}
	public java.lang.String getTrankey() {
		return this.trankey;
	}
	/** mackey */
	public void setMackey(java.lang.String mackey) {
		this.mackey = mackey == null ? null : mackey.trim();
	}
	public java.lang.String getMackey() {
		return this.mackey;
	}
	/** maintainname */
	public void setMaintainname(java.lang.String maintainname) {
		this.maintainname = maintainname == null ? null : maintainname.trim();
	}
	public java.lang.String getMaintainname() {
		return this.maintainname;
	}
	/** maintainphone */
	public void setMaintainphone(java.lang.String maintainphone) {
		this.maintainphone = maintainphone == null ? null : maintainphone.trim();
	}
	public java.lang.String getMaintainphone() {
		return this.maintainphone;
	}
	/** ftpusername */
	public void setFtpusername(java.lang.String ftpusername) {
		this.ftpusername = ftpusername == null ? null : ftpusername.trim();
	}
	public java.lang.String getFtpusername() {
		return this.ftpusername;
	}
	/** ftppassword */
	public void setFtppassword(java.lang.String ftppassword) {
		this.ftppassword = ftppassword == null ? null : ftppassword.trim();
	}
	public java.lang.String getFtppassword() {
		return this.ftppassword;
	}
	/** ftpgetpath */
	public void setFtpgetpath(java.lang.String ftpgetpath) {
		this.ftpgetpath = ftpgetpath == null ? null : ftpgetpath.trim();
	}
	public java.lang.String getFtpgetpath() {
		return this.ftpgetpath;
	}
	/** ftpputpath */
	public void setFtpputpath(java.lang.String ftpputpath) {
		this.ftpputpath = ftpputpath == null ? null : ftpputpath.trim();
	}
	public java.lang.String getFtpputpath() {
		return this.ftpputpath;
	}
	/** spanproorgan */
	public void setSpanproorgan(java.lang.String spanproorgan) {
		this.spanproorgan = spanproorgan == null ? null : spanproorgan.trim();
	}
	public java.lang.String getSpanproorgan() {
		return this.spanproorgan;
	}
	/** modifydate */
	public void setModifydate(java.lang.String modifydate) {
		this.modifydate = modifydate == null ? null : modifydate.trim();
	}
	public java.lang.String getModifydate() {
		return this.modifydate;
	}
	/** modifyorgan */
	public void setModifyorgan(java.lang.String modifyorgan) {
		this.modifyorgan = modifyorgan == null ? null : modifyorgan.trim();
	}
	public java.lang.String getModifyorgan() {
		return this.modifyorgan;
	}
	/** 第一本地端口 */
	public void setModifyoper(java.lang.String modifyoper) {
		this.modifyoper = modifyoper == null ? null : modifyoper.trim();
	}
	public java.lang.String getModifyoper() {
		return this.modifyoper;
	}
	/** 第二外部端口 */
	public void setLport(java.lang.String lport) {
		this.lport = lport == null ? null : lport.trim();
	}
	public java.lang.String getLport() {
		return this.lport;
	}
	/** 第二本地端口 */
	public void setPort1(java.lang.String port1) {
		this.port1 = port1 == null ? null : port1.trim();
	}
	public java.lang.String getPort1() {
		return this.port1;
	}
	/** 第三外部端口 */
	public void setLport1(java.lang.String lport1) {
		this.lport1 = lport1 == null ? null : lport1.trim();
	}
	public java.lang.String getLport1() {
		return this.lport1;
	}
	/** 第三本地端口 */
	public void setPort2(java.lang.String port2) {
		this.port2 = port2 == null ? null : port2.trim();
	}
	public java.lang.String getPort2() {
		return this.port2;
	}
	/** 第四外部端口 */
	public void setLport2(java.lang.String lport2) {
		this.lport2 = lport2 == null ? null : lport2.trim();
	}
	public java.lang.String getLport2() {
		return this.lport2;
	}
	/** 第四本地端口 */
	public void setPort3(java.lang.String port3) {
		this.port3 = port3 == null ? null : port3.trim();
	}
	public java.lang.String getPort3() {
		return this.port3;
	}
	/** 虚拟机构号 */
	public void setLport3(java.lang.String lport3) {
		this.lport3 = lport3 == null ? null : lport3.trim();
	}
	public java.lang.String getLport3() {
		return this.lport3;
	}
	/** 虚拟操作员 */
	public void setOrgancode(java.lang.String organcode) {
		this.organcode = organcode == null ? null : organcode.trim();
	}
	public java.lang.String getOrgancode() {
		return this.organcode;
	}
	/** 3DES密钥 */
	public void setOpercode(java.lang.String opercode) {
		this.opercode = opercode == null ? null : opercode.trim();
	}
	public java.lang.String getOpercode() {
		return this.opercode;
	}
	/** 最大并发子进程数 */
	public void setPin3key(java.lang.String pin3key) {
		this.pin3key = pin3key == null ? null : pin3key.trim();
	}
	public java.lang.String getPin3key() {
		return this.pin3key;
	}
	/** 通信配置文件 */
	public void setMaxchildproc(java.lang.String maxchildproc) {
		this.maxchildproc = maxchildproc == null ? null : maxchildproc.trim();
	}
	public java.lang.String getMaxchildproc() {
		return this.maxchildproc;
	}
	/** 发送队列键值路径 */
	public void setCfgfilename(java.lang.String cfgfilename) {
		this.cfgfilename = cfgfilename == null ? null : cfgfilename.trim();
	}
	public java.lang.String getCfgfilename() {
		return this.cfgfilename;
	}
	/** sndqueuepath */
	public void setSndqueuepath(java.lang.String sndqueuepath) {
		this.sndqueuepath = sndqueuepath == null ? null : sndqueuepath.trim();
	}
	public java.lang.String getSndqueuepath() {
		return this.sndqueuepath;
	}
	/** yysflag */
	public void setYysflag(java.lang.String yysflag) {
		this.yysflag = yysflag == null ? null : yysflag.trim();
	}
	public java.lang.String getYysflag() {
		return this.yysflag;
	}
}