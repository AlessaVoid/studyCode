package com.boco.SYS.util;

import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.task.Comment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共工具类
 *
 * <pre>
 * 修改日期        修改人    修改原因
 * 2014-9-15    	     杨滔    新建
 * </pre>
 */
public class BocoUtils {
    static Log log = LogFactory.getLog(BocoUtils.class);
    private static boolean flag = true; //是否进行编码转换

    public static String getProjectPath() {
        String classpath = "";
        classpath = BocoUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return classpath;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static String MoneyFormat(String money) {
        double menney = (new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP)).doubleValue();
        return menney + "";
    }

    public static final String PLANTYPE = "plan";
    public static final String RATESCORE = "ratescore";
    public static final String OVER = "over";

    /**
     * 拆分审批备注-通用方法
     *
     * @param comments
     * @return
     */
    public static List<Comment> translateComments(List<Comment> comments, String type) {
        String str1 = "上报流程启动";
        String str2 = "审批驳回";
        String str3 = "审批通过";
        String str4 = "重新提交审批";
        String str5 = "待审批";
        String str6 = "待提交";
        String str7 = "审批通过已上报";

        List<Comment> commentList = new ArrayList<>();
        for (Comment comment : comments) {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setUserId(comment.getUserId());
            commentEntity.setTime(comment.getTime());
            String msg = comment.getFullMessage();
            if (msg.contains(str1)) {
                commentEntity.setType("提交审批");
                commentEntity.setFullMessage("无");
            } else if (msg.contains(str2)) {
                commentEntity.setType("审批驳回");
                commentEntity.setFullMessage(msg.substring(5));
            } else if (msg.contains(str7)) {
                if (PLANTYPE.equals(type)) {
                    //计划，条线模块
                    commentEntity.setType("审批通过,已下发");
                } else if (RATESCORE.equals(type)) {
                    //评分模块
                    commentEntity.setType("审批通过,已下发");
                } else if (OVER.equals(type)) {
                    //评分模块
                    commentEntity.setType("审批通过,已完结");
                } else {
                    commentEntity.setType("审批通过,已上报");
                }
                commentEntity.setFullMessage(msg.substring(7));
            } else if (msg.contains(str3)) {
                commentEntity.setType("审批通过");
                commentEntity.setFullMessage(msg.substring(5));
            } else if (msg.contains(str4)) {
                commentEntity.setType("驳回后重新提交审批");
                commentEntity.setFullMessage(msg.substring(7));
            } else if (msg.contains(str5)) {
                commentEntity.setType("待审批");
                commentEntity.setFullMessage("无");
            } else if (msg.contains(str6)) {
                commentEntity.setType("待提交");
                commentEntity.setFullMessage("无");
            } else {
                //提交添加了备注框，取消了默认提交字段
                commentEntity.setType("提交审批");
                commentEntity.setFullMessage(msg);
            }
            commentList.add(commentEntity);
        }
        return commentList;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs;
    }

    public static byte[] hex2byte(String str) {
        if (str == null)
            return null;
        str = str.trim();
        int len = str.length();
        if ((len == 0) || (len % 2 == 1))
            return null;
        byte[] b = new byte[len / 2];
        try {
            for (int i = 0; i < str.length(); i += 2) {
                b[(i / 2)] = (byte) Integer.decode("0x" + str.substring(i, i + 2)).intValue();
            }

            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byte2hexSum(String str) {
        int index = 0;
        String[] sumStr = str.split("|");

        for (int i = 1; i < sumStr.length; ++i)
            index = (int) (index + Double.parseDouble(StrToBinstr(sumStr[i])));

        index %= 256;
        return String.valueOf(index);
    }

    private static String StrToBinstr(String str) {
        char[] strChar = str.toCharArray();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < strChar.length; ++i)
            result.append(Integer.toBinaryString(strChar[i]));

        return result.toString();
    }

    public static String getNowDate() {
        return getForamt("yyyyMMdd").format(new Date());
    }

    public static String getNowDate1() {
        return getForamt("yyyy/MM/dd").format(new Date());
    }

    public static String getNowTime() {
        return getForamt("HHmmss").format(new Date());
    }


    public static String getMonth() {
        return getForamt("yyyyMM").format(new Date());
    }

    public static String getTime() {
        return getForamt("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getNowTime1() {
        return getForamt("HH:mm:ss").format(new Date());
    }

    public static SimpleDateFormat getForamt(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    public static String getPropertiesParam(String propertiesName, String ParamName) throws Exception {
        Properties properties = new Properties();
        properties.load(BocoUtils.class.getResourceAsStream("/" + propertiesName + ".properties"));

        if (properties.get(ParamName) == null)
            return null;

        return properties.get(ParamName).toString();
    }

    /**
     * 效验日期是否合法
     *
     * @param dateStr 日期字符串
     * @param spe     , 日期分隔符 如 :2014-01-01 , 分隔符为 -
     * @return 合法返回true, 反之false
     *
     * <pre>
     * 修改日期        修改人    修改原因
     * 2014-12-11    	    杨滔    新建
     * </pre>
     */
    public static boolean dateValidate(String dateStr, String spe) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy" + spe + "MM" + spe + "dd");
            df.setLenient(false);
            df.parse(dateStr);
            return true;
        } catch (Exception e) {
            e.getStackTrace();
            return false;
        }
    }

    /**
     * TODO 日期计算
     *
     * @param date yyyyMMdd
     * @param days +-n
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2017年6月15日    	   谷立羊  新建
     * </pre>
     */
    public static String DateDay(String date, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        GregorianCalendar gc = new GregorianCalendar();
        try {
            gc.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        gc.add(5, days);// 表示天减一.
        return new SimpleDateFormat("yyyyMMdd").format(gc.getTime());
    }


    /**
     * TODO 获取字符串长度，中文占cnbit位.
     *
     * @param str
     * @param cnbit中文占的位数
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015-4-15    	    杨滔    新建
     * </pre>
     */
    public static int getStrLength(String str, int cnbit) {
        int length = 0;
        String chinese = "[\u4e00-\u9fa5]";
        if (str != null && !"".equals(str)) {
            for (int i = 0; i < str.length(); i++) {
                String temp = str.substring(i, i + 1);
                if (temp.matches(chinese)) {
                    length += cnbit;
                } else {
                    length += 1;
                }
            }
        }
        return length;
    }

    /**
     * TODO 截取字符串，中文占cnbit位.
     *
     * @param str
     * @param len
     * @param cnbit中文占的位数
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2015-5-4    	    杨滔    新建
     * </pre>
     */
    // 截取字符串长度(中文cnbit个字节，半个中文显示一个)
    public static String subString(String str, int len, int cnbit) {
        if (cnbit <= 0) {
            throw new RuntimeException("参数有误,cnbit不能小于等于0");
        }
        if (str.length() < len / cnbit)
            return str;
        int count = 0;
        StringBuffer sb = new StringBuffer();
        String[] ss = str.split("");
        for (int i = 1; i < ss.length; i++) {
            count += ss[i].getBytes().length > 1 ? cnbit : 1;
            sb.append(ss[i]);
            if (count >= len)
                break;
        }
        return (sb.toString().length() < str.length()) ? sb.toString() : str;
    }

    public static String UTF8String(String para, String bm) throws UnsupportedEncodingException {
        String msg = "";
        if (flag) {
            String encode = getEncoding(para);
            if (encode.equals("ISO-8859-1")) {
                msg = new String(para.getBytes("ISO-8859-1"), bm);
            } else {
                msg = para;
            }
        } else {
            msg = para;
        }
        return msg;
    }

    public static String getEncoding(String str) {
        String encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        return "";
    }

    /**
     * TODO 获取向渠道发送报文中用到的流水号.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月24日    	  杜旭    新建
     * </pre>
     */
    public static String getSeqno() {
        String str = BocoUtils.getForamt("HHmmssS").format(new Date());
        str = str.substring(0, str.length() - 1);
        return str;
    }

    /**
     * TODO 获取6位随机数.
     *
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年2月24日    	  杜旭    新建
     * </pre>
     */
    public static String getLastSixNum() {
        int count = 0;
        char str[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < 6) {
            int i = Math.abs(r.nextInt(10));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    /**
     * TODO 动态拼接字符串.
     * zeroPrefix:向前补0
     * zeroSuffix:向后补0
     * BlankSuffix：向后补空格
     * BlankPrefix：向前补空格
     *
     * @param content       要拼接的字符串
     * @param type          zeroPrefix:向前补0、zeroSuffix:向后补0、BlankSuffix：向后补空格、BlankPrefix：向前补空格
     * @param contentLength 要拼接的字符串的自身的长度
     * @param length        拼接完成的字符串的长度
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年3月15日    	  杜旭    新建
     * </pre>
     */
    public static String getString(String content, String type, int contentLength, int length) {
        StringBuffer buffer = new StringBuffer();
        if ("zeroPrefix".equals(type)) {
            if (contentLength < length) {
                for (int i = 0; i < (length - contentLength); i++) {
                    buffer.append("0");
                }
                content = buffer.toString() + content;
            }
        }
        if ("zeroSuffix".equals(type)) {
            if (contentLength < length) {
                for (int i = 0; i < (length - contentLength); i++) {
                    buffer.append("0");
                }
                content = content + buffer.toString();
            }
        }
        if ("BlankSuffix".equals(type)) {
            if (contentLength < length) {
                for (int i = 0; i < (length - contentLength); i++) {
                    buffer.append(" ");
                }
                content = content + buffer.toString();
            }
        }
        if ("BlankPrefix".equals(type)) {
            if (contentLength < length) {
                for (int i = 0; i < (length - contentLength); i++) {
                    buffer.append(" ");
                }
                content = buffer.toString() + content;
            }
        }
        return content;
    }

    /**
     * TODO 验证是否是数字类型
     *
     * @param str
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年7月21日    	   谷立羊  新建
     * </pre>
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * TODO 获取日期的前/后 n天的日期
     *
     * @param nowDate
     * @param days
     * @return <pre>
     * 修改日期        修改人    修改原因
     * 2016年7月21日    	   谷立羊  新建
     * </pre>
     */
    public static String getLastDay(String nowDate, int n) {
        String newDate = "";
        try {
            Date dateNow = new SimpleDateFormat("yyyyMMdd").parse(nowDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateNow);
            calendar.add(Calendar.DATE, n);
            newDate = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    /**
     * TODO 比较日期大小
     *
     * @param date1
     * @param date2
     * @return 等于返0  大于返1，小于返-1
     *
     * <pre>
     * 修改日期        修改人    修改原因
     * 2017年6月15日    	   谷立羊  新建
     * </pre>
     */
    public static int compareDate(String date1, String date2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);
            if (d1.getTime() > d2.getTime()) {
                return 1;
            }
            if (d1.getTime() < d2.getTime()) {
                return -1;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 深度复制
     * @param orig
     * @return
     */
    public static Object deepCopy(Object orig) {
        Object obj = null;
        try {
            //写入流
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(orig);
            out.flush();
            out.close();

            //写出流
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            obj = in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }


}