package com.boco.SYS.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: liujinbo
 * @Description: BigDecimal������
 * @Date: 2020/1/16
 * @Version: 1.0
 */
public class BigDecimalUtil {
    private BigDecimalUtil() {
    }

    //Ĭ�ϳ����ļ��㾫��
    private static final int DEFAULT_DIV_SCALE = 6;

    /**
     * @param v1
     * @param v2
     * @Description �ӷ�
     * @Author liujinbo
     * @Date 2020/1/16
     * @Return java.math.BigDecimal
     */
    public static BigDecimal add(String v1, String v2) {
        if (v1 == null) {
            v1 = "0";
        }
        if (v2 == null) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2);
    }

    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }
        if (v2 == null) {
            v2 = BigDecimal.ZERO;
        }
        return v1.add(v2);
    }

    /**
     * @param v1
     * @param v2
     * @Description ��ȷ����
     * @Author liujinbo
     * @Date 2020/1/16
     * @Return java.math.BigDecimal
     */
    public static BigDecimal subtract(String v1, String v2) {
        if (v1 == null) {
            v1 = "0";
        }
        if (v2 == null) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2);
    }

    public static BigDecimal subtract(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }
        if (v2 == null) {
            v2 = BigDecimal.ZERO;
        }
        return v1.subtract(v2);
    }

    public static BigDecimal getSafeCount(Object b1) {
        if (b1 == null || "null".equals(b1) || "".equals(b1.toString())) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(b1.toString());
    }


    /**
     * @param v1
     * @param v2
     * @Description ��ȷ�˷�
     * @Author liujinbo
     * @Date 2020/1/16
     * @Return java.math.BigDecimal
     */
    public static BigDecimal multiply(String v1, String v2) {
        if (v1 == null) {
            v1 = "0";
        }
        if (v2 == null) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2);
    }

    public static BigDecimal multiply(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }
        if (v2 == null) {
            v2 = BigDecimal.ZERO;
        }
        return v1.multiply(v2);
    }


    /**
     * @param v1
     * @param v2
     * @param scale      ���������������С�����λ��
     * @param round_mode ������ʽ
     *                   round_mode��
     *                   BigDecimal.ROUND_CEILING    �������������
     *                   BigDecimal.ROUND_FLOOR      �����������
     *                   BigDecimal.ROUND_DOWN       ���㷽������
     *                   BigDecimal.ROUND_UP         ��Զ����ķ�������
     *                   BigDecimal.ROUND_HALF_DOWN  ���������ķ������룬���������ȣ���������  1.55-->1.5
     *                   BigDecimal.ROUND_HALF_UP    ���������ķ������룬���������ȣ���������  1.55-->1.6  (�������룩
     * @Description ��ȷ����
     * @Author liujinbo
     * @Date 2020/1/16
     * @Return java.math.BigDecimal
     */
    public static BigDecimal divide(String v1, String v2, int scale, int round_mode) {
        if (scale < 0) {
            scale = 0;
        }
        if (v1 == null) {
            v1 = "0";
        }
        if (v2 == null) {
            v2 = "0";
        }

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        if (b2.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.divide(BigDecimal.ONE, scale, round_mode);
        }
        return b1.divide(b2, scale, round_mode);
    }


    public static BigDecimal divide(BigDecimal v1, BigDecimal v2, int scale, int round_mode) {
        if (scale < 0) {
            scale = 0;
        }

        if (null == v1 || null == v2 || v2.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.divide(BigDecimal.ONE, scale, round_mode);
        }
        return v1.divide(v2, scale, round_mode);
    }

    /**
     * @param v1
     * @param v2
     * @param scale ���������������С�����λ��
     * @Description ��ȷ���� ��������
     * @Author liujinbo
     * @Date 2020/1/16
     * @Return java.math.BigDecimal
     */
    public static BigDecimal divide(String v1, String v2, int scale) {
        if (scale < 0) {
            scale = 0;
        }
        return divide(v1, v2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal divide(BigDecimal v1, BigDecimal v2, int scale) {
        if (scale < 0) {
            scale = 0;
        }
        return divide(v1, v2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * @param v1
     * @param v2
     * @Description ��ȷ���� ����������С�����5λ����������
     * @Author liujinbo
     * @Date 2020/1/16
     * @Return java.math.BigDecimal
     */
    public static BigDecimal divide(String v1, String v2) {

        return divide(v1, v2, DEFAULT_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal divide(BigDecimal v1, BigDecimal v2) {

        return divide(v1, v2, DEFAULT_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * @param bigDecimal ����ʽ������
     * @return
     * @Description ȥ�����õ�С�����С��������0
     */
    public static BigDecimal formatNum(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(bigDecimal.stripTrailingZeros().toPlainString());
    }



    /*-------------------�����Сƽ����λ����-----------------------*/

    /**
     * �����ֵ
     *
     * @param list ����list,����Ϊ�գ�����list�����ֵ����Ϊ��
     * @return
     */
    public static BigDecimal maxValue(List<BigDecimal> list) {
        BigDecimal max = null;

        if (list != null && list.size() > 0) {
            max = list.get(0);

            for (int i = 0; i < list.size(); i++) {
                if (max.compareTo(list.get(i)) == -1) {
                    max = list.get(i);
                }
            }
        }
        return max;
    }

    /**
     * ����Сֵ
     *
     * @param list ����list,����Ϊ�գ�����list�����ֵ����Ϊ��
     * @return
     */
    public static BigDecimal minValue(List<BigDecimal> list) {
        BigDecimal min = null;

        if (list != null && list.size() > 0) {
            min = list.get(0);

            for (int i = 0; i < list.size(); i++) {
                if (min.compareTo(list.get(i)) == 1) {
                    min = list.get(i);
                }
            }
        }
        return min;
    }

    /**
     * ����λ��
     *
     * @param list ����list,����Ϊ�գ�����list�����ֵ����Ϊ��
     * @return
     */
    public static BigDecimal midValue(List<BigDecimal> list) {
        BigDecimal avg = null;

        if (list != null && list.size() > 0) {

            Collections.sort(list, new Comparator<BigDecimal>() {
                @Override
                public int compare(BigDecimal o1, BigDecimal o2) {
                    return o1.compareTo(o2);
                }
            });

            if (list.size() % 2 == 1) {
                // ����
                avg = list.get(list.size() / 2);
            } else {
                // ż��
                avg = add(list.get(list.size() / 2), (list.get((list.size() / 2) - 1))).divide(new BigDecimal("2"));
            }
        }
        return avg;
    }

    /**
     * ������
     *
     * @param list ����list,����Ϊ�գ�����list�����ֵ����Ϊ��
     * @return
     */
    public static List<BigDecimal> modValue(List<BigDecimal> list) {

        List<BigDecimal> modList = new ArrayList<>();

        if (list != null && list.size() > 0) {
            int max = 0;
            HashMap<BigDecimal, Integer> map1 = new HashMap<>();
            for (BigDecimal bigDecimal : list) {
                Integer integer = map1.get(bigDecimal);
                if (integer == null) {
                    integer = 0;
                }
                max = max > (integer + 1) ? max : integer + 1;
                map1.put(bigDecimal, integer + 1);
            }

            HashMap<Integer, List<BigDecimal>> map2 = new HashMap<>();
            for (BigDecimal bigDecimal : map1.keySet()) {
                Integer num = map1.get(bigDecimal);
                List<BigDecimal> numList = map2.get(num);
                if (numList == null) {
                    numList = new ArrayList<>();
                }
                numList.add(bigDecimal);
                map2.put(num, numList);
            }

            if (max > 1) {
                modList = map2.get(max);
            }
        }
        return modList;
    }

    /**
     * ��ƽ����
     *
     * @param list ����list,����Ϊ�գ�����list�����ֵ����Ϊ��
     * @return
     */
    public static BigDecimal avgValue(List<BigDecimal> list) {
        BigDecimal count = BigDecimal.ZERO;
        BigDecimal avg = null;

        if (list != null && list.size() > 0) {
            for (BigDecimal num : list) {
                count = add(count, num);
            }
            avg = divide(count, new BigDecimal(list.size() + ""));
        }
        return avg;
    }


    public static void main(String[] args) {
        System.out.println("--------��������-----------");
        //��������
        System.out.println(divide("1", "3", 3));
        System.out.println(divide("2", "3", 3));
        System.out.println(divide("1", "3"));
        System.out.println(divide("2", "3"));
        System.out.println(divide("1", "3", 5, BigDecimal.ROUND_HALF_DOWN));
        System.out.println(divide("2", "3", 5, BigDecimal.ROUND_HALF_DOWN));
        System.out.println(divide("1.55", "2.55"));
        //Զ����ķ���
        System.out.println("--------Զ����ķ���-----------");
        System.out.println(divide("2", "3", 5, BigDecimal.ROUND_UP));
        System.out.println(divide("1", "3", 5, BigDecimal.ROUND_UP));
        System.out.println(divide("-2", "3", 5, BigDecimal.ROUND_UP));
        System.out.println(divide("-1", "3", 5, BigDecimal.ROUND_UP));
        //�ӽ���ķ���
        System.out.println("--------�ӽ���ķ���-----------");
        System.out.println(divide("2", "3", 5, BigDecimal.ROUND_DOWN));
        System.out.println(divide("1", "3", 5, BigDecimal.ROUND_DOWN));
        System.out.println(divide("-2", "3", 5, BigDecimal.ROUND_DOWN));
        System.out.println(divide("-1", "3", 5, BigDecimal.ROUND_DOWN));

        System.out.println(divide("12", "0"));
        System.out.println(divide(new BigDecimal("34"), new BigDecimal("0")));

        System.out.println("---------------------------");
        ArrayList<BigDecimal> list = new ArrayList<>();
        list.add(new BigDecimal("3"));
        list.add(new BigDecimal("6"));
        list.add(new BigDecimal("1"));
        list.add(new BigDecimal("5"));
        list.add(new BigDecimal("6.6"));
        list.add(new BigDecimal("4"));
        list.add(new BigDecimal("2"));

        System.out.println(maxValue(list));
        System.out.println(minValue(list));
        System.out.println(modValue(list));
        System.out.println(midValue(list));
        System.out.println(avgValue(list));


    }


}
