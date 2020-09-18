package com.boco.SYS.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * 
 *  IP������.
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2015��12��2��    	    ����    �½�
 * </pre>
 */
public class IPUtil {
	/**
	 * 
	 *
	 * TODO ��ȡ����IP.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��2��    	    ����    �½�
	 * </pre>
	 */
	public static String getReqestIpAddr() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * 
	 *
	 * TODO ��ȡ��������Ӧip
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��11��1��    	   ������  �½�
	 * </pre>
	 */
	public static String getLocalHostIpAddr(){
		String localIp=null;
		try {
			if (isWindowsOS()) {
				localIp=InetAddress.getLocalHost().getHostAddress();
			}else {
			Enumeration<NetworkInterface> networkInterfaces=NetworkInterface.getNetworkInterfaces();
			InetAddress inetAddress=null;
			while(networkInterfaces.hasMoreElements()){
				  NetworkInterface ni =(NetworkInterface) networkInterfaces.nextElement();
				  System.out.println(ni.getName());
				  inetAddress=ni.getInetAddresses().nextElement();
				   if (!inetAddress.isLoopbackAddress() && !inetAddress.isSiteLocalAddress()&&inetAddress.getHostAddress().indexOf(":")==-1) {  
					   localIp=inetAddress.getHostAddress();
				   }  
				  
			}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		  return localIp;  
	}
	public static boolean isWindowsOS(){
		boolean isWindowsOS=false;
		String osName=System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows")>-1) {
			isWindowsOS=true;
			
		}
		return isWindowsOS;
		
	}
}
