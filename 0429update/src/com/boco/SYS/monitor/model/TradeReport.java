package com.boco.SYS.monitor.model;

import com.boco.SYS.monitor.util.ClassUtil;

import java.util.Date;

/**
 * 交易报告
 * @Author zhuhongjiang
 * @Date 2020/1/3 上午10:21
 **/
public class TradeReport {

    private long totalDealNumber;								//总处理条数 (成功 or 失败)
    private long totalDealSuccessNumber;						//总处理条数 (成功)
    private long todayDealNumber;								//当天处理数量 (成功 or 失败)
    private long todayDealSuccessNumber;						//当天处理数量 (成功)
    private long totalDealTime;									//总处理时间（微秒）
    private long totalTimeoutNumber;							//总超时交易量
    private long maxDealTime;									//最大处理时间（微秒）
    private long date;											//当前时间（毫秒）
    private long sysStartTime;									//系统启动时间（毫秒）


    private double averageDealTime;								//时间范围内平均处理时间（微秒）
    private long timeoutNumber;									//时间范围内超时数量
    private double runTime;										//系统运行时间（小时）
    private double memory;										//使用内存
    private long tps;											//tps（次/秒）
    private double successRate;                                 //时间范围内交易成功率（x100）
    private int reset;                                          //是否存活，1是 2否（默认）
    private String mergedTime;                                  //监控后台请求时间
    private String type;                                        //服务类型
    private String name;                                        //服务名称
    private String ip;                                          //服务ip
    private String port;                                        //服务port
    private Date time;                                          //页面请求时间


    /**
     * 初始化属性值
     * @Author zhuhongjiang
     * @Date 2020/1/3 上午10:21
     **/
    public void clearData(String type, String name, String ip, String port) {
        try {
            ClassUtil.initObject(this);

            this.type = type;
            this.name = name;
            this.ip = ip;
            this.port = port;
            this.reset = 2;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public long getTotalDealNumber() {
        return totalDealNumber;
    }

    public void setTotalDealNumber(long totalDealNumber) {
        this.totalDealNumber = totalDealNumber;
    }

    public long getTodayDealNumber() {
        return todayDealNumber;
    }

    public void setTodayDealNumber(long todayDealNumber) {
        this.todayDealNumber = todayDealNumber;
    }

    public long getTotalDealTime() {
        return totalDealTime;
    }

    public void setTotalDealTime(long totalDealTime) {
        this.totalDealTime = totalDealTime;
    }

    public long getTotalTimeoutNumber() {
        return totalTimeoutNumber;
    }

    public void setTotalTimeoutNumber(long totalTimeoutNumber) {
        this.totalTimeoutNumber = totalTimeoutNumber;
    }

    public long getMaxDealTime() {
        return maxDealTime;
    }

    public void setMaxDealTime(long maxDealTime) {
        this.maxDealTime = maxDealTime;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getSysStartTime() {
        return sysStartTime;
    }

    public void setSysStartTime(long sysStartTime) {
        this.sysStartTime = sysStartTime;
    }

    public double getAverageDealTime() {
        return averageDealTime;
    }

    public void setAverageDealTime(double averageDealTime) {
        this.averageDealTime = averageDealTime;
    }

    public long getTimeoutNumber() {
        return timeoutNumber;
    }

    public void setTimeoutNumber(long timeoutNumber) {
        this.timeoutNumber = timeoutNumber;
    }

    public double getRunTime() {
        return runTime;
    }

    public void setRunTime(double runTime) {
        this.runTime = runTime;
    }

    public double getMemory() {
        return memory;
    }

    public void setMemory(double memory) {
        this.memory = memory;
    }

    public long getTps() {
        return tps;
    }

    public void setTps(long tps) {
        this.tps = tps;
    }

    public int getReset() {
        return reset;
    }

    public void setReset(int reset) {
        this.reset = reset;
    }

    public String getMergedTime() {
        return mergedTime;
    }

    public void setMergedTime(String mergedTime) {
        this.mergedTime = mergedTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public long getTotalDealSuccessNumber() {
        return totalDealSuccessNumber;
    }

    public void setTotalDealSuccessNumber(long totalDealSuccessNumber) {
        this.totalDealSuccessNumber = totalDealSuccessNumber;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public long getTodayDealSuccessNumber() {
        return todayDealSuccessNumber;
    }

    public void setTodayDealSuccessNumber(long todayDealSuccessNumber) {
        this.todayDealSuccessNumber = todayDealSuccessNumber;
    }
}
