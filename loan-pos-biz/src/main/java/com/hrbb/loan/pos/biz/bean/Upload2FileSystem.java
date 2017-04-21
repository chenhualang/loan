/**
 * 
 */
package com.hrbb.loan.pos.biz.bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.hrbb.loan.pos.entity.SpringBeans;
import com.hrbb.loan.pos.util.StringUtil;
import com.hrbb.sh.framework.HServiceException;
import com.hrbb.sh.framework.ftp.FtpTransUpload;
import com.hrbb.sh.framework.ftp.ParamResBean;
import com.hrbb.sh.framework.ftpproxy.HFTPFile;

/**
 * <p>Title: Upload2FileSystem.java </p>
 * <p>Description: 上传文件到文档服务器 </p>
 * <p>Copyright: Copyright (C) 2015 Hrbb Ltd. Co.</p> 
 *     
 * @author linzhaolin@hrbb.com.cn
 * @version 
 * @date 2015年7月27日
 *
 * logs: 1. 
 */
public class Upload2FileSystem extends AbsInternalService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2656975585616903019L;
	/**
	 * 上传源文件
	 */
	private File uploadFile;
	
	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File sourceFile) {
		this.uploadFile = sourceFile;
	}

	@Override
	public boolean initService() throws HServiceException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean processService() throws HServiceException {
		if(getUploadFile()==null){
			logger.debug("上传文件未定义.");
			return false;
		}
		logger.debug("影像件["+getUploadFile().getName()+"]上传");
		
		FtpTransUpload ftpTransUpload = (FtpTransUpload)SpringBeans.getBean("ftpTransUpload");
		
        //上传影像件zip包到ftp
        Long step3BeginTime = System.currentTimeMillis();
        InputStream is = null;
        ParamResBean paramResBean = new ParamResBean();
		try {
			is = new FileInputStream(getUploadFile());
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        byte[] b= new byte[2048];
	        while((is.read(b)) != -1){
	            bos.write(b);
	        }
	        byte[] bytes = bos.toByteArray();
	        
	        bos.flush();
	        bos.close();
	        
	        //上传文件
	        HFTPFile file = new HFTPFile();
            file.setData(bytes);
            file.setName(getUploadFile().getName());
            paramResBean = ftpTransUpload.uploadFile("LC", file);
            Long step3EndTime = System.currentTimeMillis();
            logger.debug("影像件上传完成。耗时"+(step3EndTime-step3BeginTime)+"毫秒");
            
            String respCode = paramResBean.getRespCode();
            if(respCode!=null && respCode.equals("000")){
            	logger.debug("影像件["+getUploadFile().getName()+"]上传成功.");
            	return true;
            }else{
            	logger.error("影像件上传异常", paramResBean.getRespMsg());
            }
            
		} catch (FileNotFoundException e1) {
			logger.error("上传文件["+getUploadFile().getName()+"]不存在",e1);
			e1.printStackTrace();
//			throw e1;
		} catch (IOException e) {
			logger.error("上传文件["+getUploadFile().getName()+"]读取失败.",e);
			e.printStackTrace();
//			throw e;
		} catch (HServiceException e) {
			logger.error("影像件上传异常", paramResBean.getRespMsg());
			e.printStackTrace();
//			throw e;
		} finally{
        	try {
				is.close();
			} catch (IOException e) {
				logger.error("InputStream close error!",e);
				e.printStackTrace();
			}
        }
       
		return false;
	}
	
	public boolean processService(String fileName, byte[] bytes){
		if(StringUtil.isEmpty(fileName) || bytes == null){
			logger.debug("上传文件未定义.");
			return false;
		}
		logger.debug("影像件["+fileName+"]上传");
		
		
        //上传影像件zip包到ftp
       
		try {
			
	        
	        Long step3BeginTime = System.currentTimeMillis();
	        FtpTransUpload ftpTransUpload = (FtpTransUpload)SpringBeans.getBean("ftpTransUpload");
	        ParamResBean paramResBean = new ParamResBean();
	        //上传文件
	        HFTPFile file = new HFTPFile();
            file.setData(bytes);
            file.setName(fileName);
            paramResBean = ftpTransUpload.uploadFile("LC", file);
            Long step3EndTime = System.currentTimeMillis();
            logger.debug("影像件上传完成。耗时"+(step3EndTime-step3BeginTime)+"毫秒");
            
            String respCode = paramResBean.getRespCode();
            if(respCode!=null && respCode.equals("000")){
            	logger.debug("影像件["+fileName+"]上传成功.");
            	return true;
            }else{
            	logger.error("影像件上传异常", paramResBean.getRespMsg());
            }
           
		} catch (HServiceException e) {
			logger.error("影像件上传异常", fileName);
			e.printStackTrace();
//			throw e;
		}
       
		return false;
	}
	

}
