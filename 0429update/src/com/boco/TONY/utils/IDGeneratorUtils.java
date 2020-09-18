package com.boco.TONY.utils;

import java.util.UUID;

/**
 * @author tony
 * @describe IDGeneratorUtils
 * @date 2019-07-30
 */
public class IDGeneratorUtils {
    public synchronized static String getSequence() {
        // SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        // String formatDate = format.format(new Date());
        // return formatDate + ThreadLocalRandom.current().nextLong(100, 999);

        int machineId = 1;//最大支持1-9个集群机器部署

        int hashCodeV = UUID.randomUUID().toString().hashCode();

        if(hashCodeV < 0) {//有可能是负数

            hashCodeV = - hashCodeV;

        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return machineId+String.format("%015d", hashCodeV);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {

            System.out.println(getSequence());
        }
    }
}
