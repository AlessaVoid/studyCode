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
 * ����������
 *
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2014-9-15    	     ����    �½�
 * </pre>
 */
public class BocoUtils {
    static Log log = LogFactory.getLog(BocoUtils.class);
    private static boolean flag = true; //�Ƿ���б���ת��

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
     * ���������ע-ͨ�÷���
     *
     * @param comments
     * @return
     */
    public static List<Comment> translateComments(List<Comment> comments, String type) {
        String str1 = "�ϱ���������";
        String str2 = "��������";
        String str3 = "����ͨ��";
        String str4 = "�����ύ����";
        String str5 = "������";
        String str6 = "���ύ";
        String str7 = "����ͨ�����ϱ�";

        List<Comment> commentList = new ArrayList<>();
        for (Comment comment : comments) {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setUserId(comment.getUserId());
            commentEntity.setTime(comment.getTime());
            String msg = comment.getFullMessage();
            if (msg.contains(str1)) {
                commentEntity.setType("�ύ����");
                commentEntity.setFullMessage("��");
            } else if (msg.contains(str2)) {
                commentEntity.setType("��������");
                commentEntity.setFullMessage(msg.substring(5));
            } else if (msg.contains(str7)) {
                if (PLANTYPE.equals(type)) {
                    //�ƻ�������ģ��
                    commentEntity.setType("����ͨ��,���·�");
                } else if (RATESCORE.equals(type)) {
                    //����ģ��
                    commentEntity.setType("����ͨ��,���·�");
                } else if (OVER.equals(type)) {
                    //����ģ��
                    commentEntity.setType("����ͨ��,�����");
                } else {
                    commentEntity.setType("����ͨ��,���ϱ�");
                }
                commentEntity.setFullMessage(msg.substring(7));
            } else if (msg.contains(str3)) {
                commentEntity.setType("����ͨ��");
                commentEntity.setFullMessage(msg.substring(5));
            } else if (msg.contains(str4)) {
                commentEntity.setType("���غ������ύ����");
                commentEntity.setFullMessage(msg.substring(7));
            } else if (msg.contains(str5)) {
                commentEntity.setType("������");
                commentEntity.setFullMessage("��");
            } else if (msg.contains(str6)) {
                commentEntity.setType("���ύ");
                commentEntity.setFullMessage("��");
            } else {
                //�ύ�����˱�ע��ȡ����Ĭ���ύ�ֶ�
                commentEntity.setType("�ύ����");
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
     * Ч�������Ƿ�Ϸ�
     *
     * @param dateStr �����ַ���
     * @param spe     , ���ڷָ��� �� :2014-01-01 , �ָ���Ϊ -
     * @return �Ϸ�����true, ��֮false
     *
     * <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2014-12-11    	    ����    �½�
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
     * TODO ���ڼ���
     *
     * @param date yyyyMMdd
     * @param days +-n
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2017��6��15��    	   ������  �½�
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
        gc.add(5, days);// ��ʾ���һ.
        return new SimpleDateFormat("yyyyMMdd").format(gc.getTime());
    }


    /**
     * TODO ��ȡ�ַ������ȣ�����ռcnbitλ.
     *
     * @param str
     * @param cnbit����ռ��λ��
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015-4-15    	    ����    �½�
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
     * TODO ��ȡ�ַ���������ռcnbitλ.
     *
     * @param str
     * @param len
     * @param cnbit����ռ��λ��
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015-5-4    	    ����    �½�
     * </pre>
     */
    // ��ȡ�ַ�������(����cnbit���ֽڣ����������ʾһ��)
    public static String subString(String str, int len, int cnbit) {
        if (cnbit <= 0) {
            throw new RuntimeException("��������,cnbit����С�ڵ���0");
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
     * TODO ��ȡ���������ͱ������õ�����ˮ��.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��24��    	  ����    �½�
     * </pre>
     */
    public static String getSeqno() {
        String str = BocoUtils.getForamt("HHmmssS").format(new Date());
        str = str.substring(0, str.length() - 1);
        return str;
    }

    /**
     * TODO ��ȡ6λ�����.
     *
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��2��24��    	  ����    �½�
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
     * TODO ��̬ƴ���ַ���.
     * zeroPrefix:��ǰ��0
     * zeroSuffix:���0
     * BlankSuffix����󲹿ո�
     * BlankPrefix����ǰ���ո�
     *
     * @param content       Ҫƴ�ӵ��ַ���
     * @param type          zeroPrefix:��ǰ��0��zeroSuffix:���0��BlankSuffix����󲹿ո�BlankPrefix����ǰ���ո�
     * @param contentLength Ҫƴ�ӵ��ַ����������ĳ���
     * @param length        ƴ����ɵ��ַ����ĳ���
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��3��15��    	  ����    �½�
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
     * TODO ��֤�Ƿ�����������
     *
     * @param str
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��7��21��    	   ������  �½�
     * </pre>
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * TODO ��ȡ���ڵ�ǰ/�� n�������
     *
     * @param nowDate
     * @param days
     * @return <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2016��7��21��    	   ������  �½�
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
     * TODO �Ƚ����ڴ�С
     *
     * @param date1
     * @param date2
     * @return ���ڷ�0  ���ڷ�1��С�ڷ�-1
     *
     * <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2017��6��15��    	   ������  �½�
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
     * ��ȸ���
     * @param orig
     * @return
     */
    public static Object deepCopy(Object orig) {
        Object obj = null;
        try {
            //д����
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(orig);
            out.flush();
            out.close();

            //д����
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