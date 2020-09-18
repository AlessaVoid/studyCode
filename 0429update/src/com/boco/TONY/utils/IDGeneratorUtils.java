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

        int machineId = 1;//���֧��1-9����Ⱥ��������

        int hashCodeV = UUID.randomUUID().toString().hashCode();

        if(hashCodeV < 0) {//�п����Ǹ���

            hashCodeV = - hashCodeV;

        }
        // 0 ����ǰ�油��0
        // 4 ������Ϊ4
        // d �������Ϊ������
        return machineId+String.format("%015d", hashCodeV);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {

            System.out.println(getSequence());
        }
    }
}
