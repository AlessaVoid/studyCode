package com.boco.SYS.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: liujinbo
 * @Description: BigDecimal工具类
 * @Date: 2020/1/16
 * @Version: 1.0
 */
public class BigDecimalUtil {
    private BigDecimalUtil() {
    }

    //默认除法的计算精度
    private static final int DEFAULT_DIV_SCALE = 6;

    /**
     * @param v1
     * @param v2
     * @Description 加法
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
     * @Description 精确减法
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
     * @Description 精确乘法
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
     * @param scale      如果除不尽保留的小数点后位数
     * @param round_mode 保留方式
     *                   round_mode：
     *                   BigDecimal.ROUND_CEILING    像正无穷方向舍入
     *                   BigDecimal.ROUND_FLOOR      像负无穷方向舍入
     *                   BigDecimal.ROUND_DOWN       像零方向舍入
     *                   BigDecimal.ROUND_UP         像远离零的方向舍入
     *                   BigDecimal.ROUND_HALF_DOWN  像距离最近的方向舍入，如果两边相等，像下舍入  1.55-->1.5
     *                   BigDecimal.ROUND_HALF_UP    像距离最近的方向舍入，如果两边相等，像上舍入  1.55-->1.6  (四舍五入）
     * @Description 精确除法
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
     * @param scale 如果除不尽保留的小数点后位数
     * @Description 精确除法 四舍五入
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
     * @Description 精确除法 除不尽保留小数点后5位，四舍五入
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
     * @param bigDecimal 被格式化的数
     * @return
     * @Description 去掉无用的小数点和小数点后面的0
     */
    public static BigDecimal formatNum(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(bigDecimal.stripTrailingZeros().toPlainString());
    }



    /*-------------------最大最小平均中位众数-----------------------*/

    /**
     * 求最大值
     *
     * @param list 数据list,不能为空，并且list里面的值不能为空
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
     * 求最小值
     *
     * @param list 数据list,不能为空，并且list里面的值不能为空
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
     * 求中位数
     *
     * @param list 数据list,不能为空，并且list里面的值不能为空
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
                // 奇数
                avg = list.get(list.size() / 2);
            } else {
                // 偶数
                avg = add(list.get(list.size() / 2), (list.get((list.size() / 2) - 1))).divide(new BigDecimal("2"));
            }
        }
        return avg;
    }

    /**
     * 求众数
     *
     * @param list 数据list,不能为空，并且list里面的值不能为空
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
     * 求平均数
     *
     * @param list 数据list,不能为空，并且list里面的值不能为空
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
        System.out.println("--------四舍五入-----------");
        //四舍五入
        System.out.println(divide("1", "3", 3));
        System.out.println(divide("2", "3", 3));
        System.out.println(divide("1", "3"));
        System.out.println(divide("2", "3"));
        System.out.println(divide("1", "3", 5, BigDecimal.ROUND_HALF_DOWN));
        System.out.println(divide("2", "3", 5, BigDecimal.ROUND_HALF_DOWN));
        System.out.println(divide("1.55", "2.55"));
        //远离零的方向
        System.out.println("--------远离零的方向-----------");
        System.out.println(divide("2", "3", 5, BigDecimal.ROUND_UP));
        System.out.println(divide("1", "3", 5, BigDecimal.ROUND_UP));
        System.out.println(divide("-2", "3", 5, BigDecimal.ROUND_UP));
        System.out.println(divide("-1", "3", 5, BigDecimal.ROUND_UP));
        //接近零的方向
        System.out.println("--------接近零的方向-----------");
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
