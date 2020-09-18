package com.boco.SYS.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtil {
	 /**
     * �ӱ����ļ���ȡͼ��Ķ�������
     * 
     * @param infile
     * @return
     */
    public static FileInputStream getImageByte(File file) {
        FileInputStream imageByte = null;
        //file = new File(infile);
        try {
            imageByte = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return imageByte;
    }
    
    /**
     * ��ͼƬ������ΪͼƬ
     * 
     * @param inputStream
     * @param path
     */
    public static void readBlob(InputStream inputStream, String path) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
            inputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     *
     * TODO ��ͼƬ�ü�Ϊ�̶���С���ֽ���.
     *
     * @param data
     * @param nw
     * @param nh
     * @return
     *
     * <pre>
     * �޸�����        �޸���    �޸�ԭ��
     * 2015-8-30    	    ����    �½�
     * </pre>
     */
    public static byte[] ChangeImgSize(byte[] data, int nw, int nh){
        byte[] newdata = null;
        try{
        	BufferedImage bis = ImageIO.read(new ByteArrayInputStream(data));
            int w = bis.getWidth();
            int h = bis.getHeight();
            double sx = (double) nw / w;
            double sy = (double) nh / h;
            AffineTransform transform = new AffineTransform();
            transform.setToScale(sx, sy);
            AffineTransformOp ato = new AffineTransformOp(transform, null);
            //ԭʼ��ɫ
            BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
            ato.filter(bis, bid);          
            //ת����byte�ֽ�
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bid, "jpeg", baos);
            newdata = baos.toByteArray();
        }catch(IOException e){
            e.printStackTrace();
        }
        return newdata;
    }
}
