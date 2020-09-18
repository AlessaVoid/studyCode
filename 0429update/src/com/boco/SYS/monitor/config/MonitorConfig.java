package com.boco.SYS.monitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Monitor≈‰÷√
 * @Author zhuhongjiang
 * @Date 2020/1/6 œ¬ŒÁ3:09
 **/
@Component
public class MonitorConfig {

    @Value("${bufferSize}")
    private int bufferSize;
    @Value("${totalMaster}")
    private int totalMaster;
    @Value("${ips}")
    private String ips;
    @Value("${ports}")
    private String ports;
    @Value("${types}")
    private String types;
    @Value("${names}")
    private String names;
    @Value("${numberWeb}")
    private String numberWeb;

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public int getTotalMaster() {
        return totalMaster;
    }

    public void setTotalMaster(int totalMaster) {
        this.totalMaster = totalMaster;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getNumberWeb() {
        return numberWeb;
    }

    public void setNumberWeb(String numberWeb) {
        this.numberWeb = numberWeb;
    }
}
