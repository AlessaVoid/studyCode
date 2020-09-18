package com.boco.SYS.monitor.model;

import com.boco.SYS.monitor.util.ClassUtil;

import java.util.Date;

/**
 * ���ױ���
 * @Author zhuhongjiang
 * @Date 2020/1/3 ����10:21
 **/
public class TradeReport {

    private long totalDealNumber;								//�ܴ������� (�ɹ� or ʧ��)
    private long totalDealSuccessNumber;						//�ܴ������� (�ɹ�)
    private long todayDealNumber;								//���촦������ (�ɹ� or ʧ��)
    private long todayDealSuccessNumber;						//���촦������ (�ɹ�)
    private long totalDealTime;									//�ܴ���ʱ�䣨΢�룩
    private long totalTimeoutNumber;							//�ܳ�ʱ������
    private long maxDealTime;									//�����ʱ�䣨΢�룩
    private long date;											//��ǰʱ�䣨���룩
    private long sysStartTime;									//ϵͳ����ʱ�䣨���룩


    private double averageDealTime;								//ʱ�䷶Χ��ƽ������ʱ�䣨΢�룩
    private long timeoutNumber;									//ʱ�䷶Χ�ڳ�ʱ����
    private double runTime;										//ϵͳ����ʱ�䣨Сʱ��
    private double memory;										//ʹ���ڴ�
    private long tps;											//tps����/�룩
    private double successRate;                                 //ʱ�䷶Χ�ڽ��׳ɹ��ʣ�x100��
    private int reset;                                          //�Ƿ��1�� 2��Ĭ�ϣ�
    private String mergedTime;                                  //��غ�̨����ʱ��
    private String type;                                        //��������
    private String name;                                        //��������
    private String ip;                                          //����ip
    private String port;                                        //����port
    private Date time;                                          //ҳ������ʱ��


    /**
     * ��ʼ������ֵ
     * @Author zhuhongjiang
     * @Date 2020/1/3 ����10:21
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
