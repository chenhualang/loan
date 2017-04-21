package com.hrbb.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.hrbb.sh.framework.ftp.FtpTransUpload;
import com.hrbb.sh.framework.ftp.ParamResBean;
import com.hrbb.sh.framework.ftpproxy.HFTPFile;


public class UploadFileToFtpTest {
    private static Logger logger = LoggerFactory.getLogger(UploadFileToFtpTest.class);
//    @Autowired
//    private FtpTransUpload ftpTransUpload;

    @Test
    public void uploadFile(){
        HFTPFile file = new HFTPFile();
        File f = new File("C:\\Users\\yu\\Desktop\\gengyu.zip");
        try {
            InputStream is = new FileInputStream(f);
            byte[] bytes = input2byte(is);
            file.setData(bytes);
            file.setName(f.getName());
            ApplicationContext context = new FileSystemXmlApplicationContext("D:/哈尔滨银行/loan/loan-pos-web/src/main/resources/config/context/ftp-hessian.xml");
            logger.debug(context.toString());
            FtpTransUpload ftpTransUpload = (FtpTransUpload) context.getBean("ftpTransUpload");
            ParamResBean paramResBean = ftpTransUpload.uploadFile("LC", file);
            String respCode = paramResBean.getRespCode();
            System.out.println(respCode);
            is.close();
        } catch (Exception e) {
            logger.error("", e);
        }
        
    }
    
    public static final byte[] input2byte(InputStream inStream)  
            throws IOException {  
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buff = new byte[100];  
        int rc = 0;  
        while ((rc = inStream.read(buff, 0, 100)) > 0) {  
            swapStream.write(buff, 0, rc);  
        }  
        byte[] in2b = swapStream.toByteArray();  
        return in2b;  
    }
}
