package com.hrbb.loan.pos.util;


import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

public class ImageUtil {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtil.class);
	
	/**
	* @param p_Str Base64编码的字符窜
	* @return byte[]类型的照片信息
	* @throws IOException
	*/
	public static byte[] fromBase64(String p_Str) throws IOException {
		byte[] byteBuffer = new BASE64Decoder().decodeBuffer(p_Str);
		return byteBuffer;
	}
	
	
	/**
	* @param bytes byte[]类型的照片信息
	* @return Java Image对象。可以直接在java程序中绘制到UI界面
	*/
	public static Image getImage(byte[] bytes) {
		Image img = null;
		try {
			InputStream isPhoto = new ByteArrayInputStream(bytes);
			img = ImageIO.read(isPhoto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	
	public static void saveAsResize(File srcImage, String resizeFile) throws IOException{
		saveAsResize(srcImage, resizeFile, 1024.00, true);
	}
	
	public static void saveAsResize(File srcImage, String resizeFile, boolean keepOriginal) throws IOException{
		saveAsResize(srcImage, resizeFile, 1024.00, keepOriginal);
	}
	
	public static void saveAsResize(File srcImage, String resizeFile, double limit) throws IOException{
		saveAsResize(srcImage, resizeFile, limit, true);
	}
	
	/**
	 * 图片根据指定max limit大小resize后另存到指定路径
	 * 
	 * @param is
	 * @param originalFile
	 * @param resizeFile
	 * @param limit
	 * @param keepOriginal
	 * @throws IOException
	 */
	public static void saveAsResize(File srcImage, String resizeFile, double limit, boolean keepOriginal) throws IOException{
		EasyImage image = new EasyImage(srcImage);
		
		if(!image.canRead()) throw new IOException("读取图片文件["+srcImage.getName()+"]失败!");
		
		/*判断是否需要压缩*/
		int w = image.getWidth();
        int h = image.getHeight();
		double ratio = 1.0;
        ratio = (w > h ? w : h)/*长或宽，取最长那个。*/ > limit/*如果最长的大于限制长度*/ ? (limit/(w > h ? w : h))/*true 算出压缩比例*/ : ratio/*false 不压缩*/;// 
        if(ratio==1.0 || srcImage.length()<300000){		//低于limit要求的直接copy,不再resize; 300k以下直接复制
        	File dFile = new File(resizeFile);
        	if(!dFile.exists()) {
        		dFile.createNewFile();
//            	System.out.println("copy file though file channel");
            	FileUtil.fileChannelCopy(srcImage, dFile);		//copy file though file channel
        	}

        	return;
        }
        
		int newWidth = (int)Math.floor(w * ratio),newHeight = (int)Math.floor(h * ratio);//算出压缩后的长宽  
		
        image.resize(newWidth, newHeight);
        
        int start = (int) System.currentTimeMillis();   // 开始时间 
        image.saveAs(resizeFile);
        int end = (int) System.currentTimeMillis(); // 结束时间   
        int re = end-start; // 但图片生成处理时间   
        
        System.out.println("转换 ["+srcImage.getAbsolutePath()+"] 总共用了：" + re + "毫秒");
	}
	
	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * @param base64ImgStr 
	 * @param imgFilePath 图片全路径
	 * @return file
	 */
	public static File generateImage(String base64ImgStr, String imgFilePath) {
        if (null == base64ImgStr) // 图像数据为空
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        File imageFile = new File(imgFilePath);
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(base64ImgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imageFile);
            out.write(bytes);
            out.flush();
            out.close();
            LOGGER.debug("文件生成成功，文件路径为："+imageFile.getAbsolutePath());
            return imageFile;
        } catch (Exception e) {
            LOGGER.error("文件生成异常\n",e);
            return null;
        }
    }
}
