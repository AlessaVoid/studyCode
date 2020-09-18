package com.boco.RE.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * �ļ�������
 * @Author zhuhongjiang
 * @Date 2020/2/9 ����2:44
 **/
public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * �����ļ�
     * @param file
     * @param fileDirPath
     * @param fileName
     */
    public static void saveFile(MultipartFile file, String fileDirPath, String fileName){
        new Thread(() -> {
            OutputStream os = null;
            InputStream is = null;
            try{
                byte[] bs = new byte[1024];
                int len;

                File fileDir = new File(fileDirPath);
                if(!fileDir.exists()){
                    boolean isSuccess = fileDir.mkdirs();
                    if(!isSuccess){
                        log.error("���ļ�������-�����ļ��쳣������Ŀ¼ʧ��");
                        return;
                    }
                }

                os = new FileOutputStream(fileDirPath + File.separator + fileName);
                is = file.getInputStream();

                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }

            }catch(Exception e){
                log.error("���ļ�������-�����ļ��쳣��", e);
            }finally {
                try {
                    if(os != null){
                        os.close();
                    }
                    if(is != null){
                        is.close();
                    }
                } catch (Exception e) {
                    log.error("���ļ�������-�����ļ��쳣��", e);
                }
            }
        }).start();
    }
}
