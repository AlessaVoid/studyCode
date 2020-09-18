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
     * 从本地文件读取图像的二进制流
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
     * 将图片流读出为图片
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
     * TODO 将图片裁剪为固定大小的字节流.
     *
     * @param data
     * @param nw
     * @param nh
     * @return
     *
     * <pre>
     * 修改日期        修改人    修改原因
     * 2015-8-30    	    杨滔    新建
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
            //原始颜色
            BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
            ato.filter(bis, bid);          
            //转换成byte字节
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bid, "jpeg", baos);
            newdata = baos.toByteArray();
        }catch(IOException e){
            e.printStackTrace();
        }
        return newdata;
    }
}
