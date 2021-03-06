package com.hrbb.loan.pos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpTool {
    
    private static Logger logger = LoggerFactory.getLogger(FtpTool.class);
    
    private FTPClient ftp;

    private String    romateDir = "";

    private String    userName  = "";

    private String    password  = "";

    private String    host      = "";

    private String    port      = "21";
    

    public FtpTool(){};
    public FtpTool(String url) throws IOException {
        //String url="ftp://user:password@ip:port/ftptest/psd"; 
        int len = url.indexOf("//");
        String strTemp = url.substring(len + 2);
        len = strTemp.indexOf(":");
        userName = strTemp.substring(0, len);
        strTemp = strTemp.substring(len + 1);

        len = strTemp.indexOf("@");
        password = strTemp.substring(0, len);
        strTemp = strTemp.substring(len + 1);
        host = "";
        len = strTemp.indexOf(":");
        if (len < 0)//没有设置端口 
        {
            port = "21";
            len = strTemp.indexOf("/");
            if (len > -1) {
                host = strTemp.substring(0, len);
                strTemp = strTemp.substring(len + 1);
            } else {
                strTemp = "";
            }
        } else {
            host = strTemp.substring(0, len);
            strTemp = strTemp.substring(len + 1);
            len = strTemp.indexOf("/");
            if (len > -1) {
                port = strTemp.substring(0, len);
                strTemp = strTemp.substring(len + 1);
            } else {
                port = "21";
                strTemp = "";
            }
        }
        romateDir = strTemp;
        ftp = new FTPClient();
        ftp.connect(host, FormatStringToInt(port));

    }

    public FtpTool(String host, int port) throws IOException {
        ftp = new FTPClient();
        ftp.connect(host, port);
    }

    public String login(String username, String password) throws IOException {
        this.ftp.login(username, password);
        return this.ftp.getReplyString();
    }

    /**
     * 
     * ftp登录
     * @return
     * @throws IOException
     */
    public String login() throws IOException {
        this.ftp.login(userName, password);
        logger.debug("ftp用户: " + userName);
        logger.debug("ftp密码: " + password);
        if (!romateDir.equals(""))
        logger.debug("cd " + romateDir);
        ftp.changeWorkingDirectory(romateDir);
        return this.ftp.getReplyString();
    }

    /**
     * 
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    public boolean upload(File file) throws IOException {
        if(!file.exists()){
            file.createNewFile();
        }
        int reply;
        String m_sfilename = null;
        String filename = "";
        filename = CheckNullString(file.getName());
        if (filename.equals(""))
            return false;
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            logger.debug("FTP server refused connection.");
            System.exit(1);
        }
        FileInputStream is = null;
        try {
            //产生随机数最大到99  
            m_sfilename = file.getName(); // 生成的文件名 
            is = new FileInputStream(file);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.storeFile(m_sfilename, is);
            ftp.logout();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        logger.debug("上传文件成功!");
        return true;
    }

    public boolean delete(String filename) throws IOException {

        FileInputStream is = null;
        boolean retValue = false;
        try {
            retValue = ftp.deleteFile(filename);
            ftp.logout();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return retValue;

    }

    public void close() {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int FormatStringToInt(String p_String) {
        int intRe = 0;
        if (p_String != null) {
            if (!p_String.trim().equals("")) {
                try {
                    intRe = Integer.parseInt(p_String);
                } catch (Exception ex) {

                }
            }
        }
        return intRe;
    }

    public static String CheckNullString(String p_String) {
        if (p_String == null)
            return "";
        else
            return p_String;
    }

    public boolean downfile(String pathname, String filename) {
        String outputFileName = null;
        boolean retValue = false;
        try {
            FTPFile files[] = ftp.listFiles();
            int reply = ftp.getReplyCode();

            //////////////////////////////////////////////// 
            if (!FTPReply.isPositiveCompletion(reply)) {
                try {
                    throw new Exception("Unable to get list of files to dowload.");
                } catch (Exception e) {
                    // TODO Auto-generated catch block 
                    e.printStackTrace();
                }
            }

            ///////////////////////////////////////////////////// 
            if (files.length == 0) {
                logger.debug("No files are available for download.");
            } else {
                for (int i = 0; i < files.length; i++) {
                    logger.debug("Downloading file " + files[i].getName() + "Size:"
                                       + files[i].getSize());
                    outputFileName = pathname + filename + ".pdf";
                    //return outputFileName; 
                    File f = new File(outputFileName);

                    ////////////////////////////////////////////////////// 
                    retValue = ftp.retrieveFile(outputFileName, new FileOutputStream(f));

                    if (!retValue) {
                        try {
                            throw new Exception("Downloading of remote file" + files[i].getName()
                                                + " failed. ftp.retrieveFile() returned false.");
                        } catch (Exception e) {
                            // TODO Auto-generated catch block 
                            e.printStackTrace();
                        }
                    }

                }
            }

            ///////////////////////////////////////////////////////////// 
        } catch (IOException e) {
            // TODO Auto-generated catch block 
            e.printStackTrace();
        }
        return retValue;
    }
    
    /**
     * 
     * 上传影像件
     * @param file
     * @param ftpUrl
     * @return
     */
    public boolean uploadImageData(File file,String ftpUrl){
        try {
            FtpTool fa = new FtpTool(ftpUrl);
            fa.login();
            boolean uploadSuccess = fa.upload(file);
            fa.close();
            return uploadSuccess;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}