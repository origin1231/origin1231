package com.web.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadFileUtils {

	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);
	
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) {
		
		return null;
	}
	
	// 경로 계산
	public static String calcPath(String uploadPath) {
		
		Calendar cal = Calendar.getInstance();
		
		String yearPath = File.separator + cal.get(Calendar.YEAR);
		
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
		
		String datePath = monthPath + File.separator +  new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		makeDir(uploadPath, yearPath, monthPath, datePath);
		
		logger.info(datePath);
		
		return datePath;
	}
	
	// 폴더 생성
	public static void makeDir(String uploadPath, String... paths) {
		
		if(new File(paths[paths.length-1]).exists()) {
			return;
		}
		
		for(String path : paths) {
			File dirPath = new File(uploadPath + path);
			
			if(! dirPath.exists()) {
				dirPath.mkdir();
			}
		}
	}
	
	// 썸네일생성
	public static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception {
		
		BufferedImage sourceImg = 
		        ImageIO.read(new File(uploadPath + path, fileName));
		    
		    BufferedImage destImg = 
		        Scalr.resize(sourceImg, 
		            Scalr.Method.AUTOMATIC, 
		            Scalr.Mode.FIT_TO_HEIGHT,100);
		    
		    String thumbnailName = 
		        uploadPath + path + File.separator +"s_"+ fileName;
		    
		    File newFile = new File(thumbnailName);
		    String formatName = 
		        fileName.substring(fileName.lastIndexOf(".")+1);
		    
		    
		    ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		    return thumbnailName.substring(
		        uploadPath.length()).replace(File.separatorChar, '/');
	}
	
}
