package com.boco.SYS.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.transaction.SystemException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boco.SYS.entity.ShortMessage;
import com.boco.SYS.monitor.netty.handler.MonitorClientHandler;
import com.boco.TONY.utils.IDGeneratorUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.EchoXML;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.boco.SYS.controller.LoginController;
import sun.net.www.http.HttpClient;

public class HttpRequestClient {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MonitorClientHandler.class);


    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        String urlNameString = url + "?" + param;
        try {
            URL realUrl = new URL(urlNameString);
            //�򿪺�url֮�������
            URLConnection connection = realUrl.openConnection();
            //����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-ahent", "Mozilla/4.0(compatible;MSIE 6.0;Windows NT 5.1;SV1)");
            //����ʵ������
            connection.connect();
            //��ȡȥ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "-" + map.get(key));
            }
            //���� bufferedReader����������URL����Ӧ
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.info("�޷����ӵ�Զ������");
            HashMap resultMap = new HashMap<String, Object>();
            resultMap.put("success", "true");
            resultMap.put("msg", "ע���ɹ�");
            resultMap.put("localIp", IPUtil.getLocalHostIpAddr());
            Json json = Json.getInstance();
            result = json.setBean(resultMap).toJson();
            logger.info("�޷����ӵ�Զ������");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            //�򿪺�url֮�������
            URLConnection conn = realUrl.openConnection();
            //����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("user-ahent", "Mozilla/4.0(compatible;MSIE 5.0;Windows NT;DigExt)");
            //����post�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //��ȡURLconnection�����Ӧ�������
            out = new PrintWriter(conn.getOutputStream());
            //�����������
            out.print(param);
            //flush������Ļ���
            out.flush();
            //����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("����POST��������쳣" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }


    public static JSONObject newDoPost(String url, JSONObject json) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/json;charset=utf-8");
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString(), "utf-8");
            // s.setContentEncoding("UTF-8");
            // s.setContentType("application/json");
            httpPost.setEntity(s);
            HttpResponse res = httpClient.execute(httpPost);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                String result = EntityUtils.toString(res.getEntity());
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            logger.error("����POST��������쳣" + e);
            e.printStackTrace();
        }
        return response;
    }

    /**
     *  ���÷�����ƽ̨���Ͷ���
     * @param phoneNumber �ֻ���
     * @param url ����ƽ̨url
     * @param msg ��������
     * @return
     */
    public static JSONObject sendMsg(String phoneNumber,String url,String msg) {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String month=sdf.format(new Date());

        ShortMessage shortMessage = new ShortMessage();
        shortMessage.setStrDesMsisdn(phoneNumber);
        shortMessage.setStrFeeMsisdn(phoneNumber);
        shortMessage.setStrContent(msg);
        shortMessage.setCltSeqNo(getCltSeqNo(17));
        shortMessage.setSYS_TRACE_NO(shortMessage.getTransInst() + month + shortMessage.getCltSeqNo().substring(0, 15));

        logger.info("��ʼ���Ͷ���,����ƽ̨��{}��,�ֻ��š�{}��,�������ݡ�{}��", url, phoneNumber, msg);
        JSONObject json = null;
        try {
            json = HttpRequestClient.newDoPost(url, JSONObject.parseObject(JSON.toJSONString(shortMessage)));
            if (json == null) {
                json = new JSONObject();
            }
            logger.info("���Ͷ������,����ֵ��{}��",json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("���Ͷ���ʧ��",e);
        }
        return json;

    }

    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        System.out.println(json.toJSONString());
    }

    //��ȡ�����
    private synchronized static String getCltSeqNo(int num) {
        int machineId = 1;

        int hashCodeV = UUID.randomUUID().toString().hashCode();

        if(hashCodeV < 0) {//�п����Ǹ���

            hashCodeV = - hashCodeV;

        }
        // 0 ����ǰ�油��0
        // 4 ������Ϊ4
        // d �������Ϊ������
        String format = "%0" + (num - 1) + "d";
        return machineId+String.format(format, hashCodeV);
    }


//     public static void main(String[] args) {
// //        String sg = HttpRequestClient.sendGet("http://20.5.166.152:8080/web/qiantui.htm", "operCode=20132364541");
// //        System.out.println(sg);
//         String param = "{\n" +
//                 "\"SYS_TRACE_NO\":\"9971072000120191031234500819090757\",\n" +
//                 "\"SENDINST\":\"99710720001\",\n" +
//                 "\"destInst\":\"99100100000\",\n" +
//                 "\"transInst\":\"99710720001\",     \n" +
//                 "\"cltSeqNo\":\"12345678912345678\",\n" +
//                 "\"bTpUdhi\":\"0\",\n" +
//                 "\"bTpPid\":\"0\",\n" +
//                 "\"strDesMsisdn\":\"15002941705\",\n" +
//                 "\"bPKNumber\":\"1\",\n" +
//                 "\"bContentType\":\"15\",\n" +
//                 "\"bLongSMS\":\"1\",\n" +
//                 "\"bPKTotal\":\"1\",\n" +
//                 "\"strBusinessKind\":\"250_XEGLXT_001\",\n" +
//                 "\"strSendTime\":\"\",\n" +
//                 "\"bMediaType\":\"1\",\n" +
//                 "\"strContent\":\"testSendMessage\",\n" +
//                 "\"strFeeMsisdn\":\"18233136948\"\n" +
//                 "}";
//
//         SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
//         String month=sdf.format(new Date());
//
//         ShortMessage shortMessage = new ShortMessage();
//
//         shortMessage.setCltSeqNo(getCltSeqNo(17));
//         shortMessage.setSYS_TRACE_NO(shortMessage.getTransInst() + month + shortMessage.getCltSeqNo().substring(0, 15));
//
//         shortMessage.setStrDesMsisdn("18822526020");
//         shortMessage.setStrFeeMsisdn("18822526020");
//         shortMessage.setStrContent("test���Զ���");
//
//         param = JSON.toJSONString(shortMessage);
//         System.out.println(param);
//
//
//         // ���滷��
//        // JSONObject resJson = HttpRequestClient.newDoPost("http://20.200.21.68:8013/direct/TMSSendShortMessage", JSONObject.parseObject(param));
//        // System.out.println(resJson.toJSONString());
//
//         //Ԥ�ݻ���
// 		JSONObject resJson1 = HttpRequestClient.newDoPost("http://20.200.21.84:9007/direct/TMSSendShortMessage", JSONObject.parseObject(param));
//         System.out.println(resJson1.toJSONString());
//
//     }
}
