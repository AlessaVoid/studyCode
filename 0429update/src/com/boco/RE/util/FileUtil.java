package com.boco.RE.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件工具类
 * @Author zhuhongjiang
 * @Date 2020/2/9 下午2:44
 **/
public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 保存文件
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
                        log.error("【文件工具类-保存文件异常】创建目录失败");
                        return;
                    }
                }

                os = new FileOutputStream(fileDirPath + File.separator + fileName);
                is = file.getInputStream();

                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }

            }catch(Exception e){
                log.error("【文件工具类-保存文件异常】", e);
            }finally {
                try {
                    if(os != null){
                        os.close();
                    }
                    if(is != null){
                        is.close();
                    }
                } catch (Exception e) {
                    log.error("【文件工具类-保存文件异常】", e);
                }
            }
        }).start();
    }
}
