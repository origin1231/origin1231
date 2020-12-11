package com.web.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {

	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);
	
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception {
		
		UUID uid = UUID.randomUUID();

		String savedName = uid.toString() + "_" + originalName;
		// 저장 될 경로를 계산
		String savedPath = calcPath(uploadPath);
		
		File target = new File(uploadPath + savedPath,savedName);
		// 원본 파일을 저장하는 부분
		FileCopyUtils.copy(fileData, target);
		// 원본 파일의 확장자
		String formatName = originalName.substring(originalName.lastIndexOf(".")+1);
		
		String uploadedFileName = null;
		// 이미지 파일인 경우 판별
		if(MediaUtils.getMedaiType(formatName) != null) {
			logger.info("img true =>");
			uploadedFileName = makeThumbnail(uploadPath,savedPath,savedName);
		}else {
			// 이미지 파일 아닌경우
			logger.info("img false =>");
			uploadedFileName = makeIcon(uploadPath, savedPath, savedName);
		}
		return uploadedFileName;
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
		
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path,fileName));
		
		// 썸네일 이미지 파일 가로/세로 비율 유지, 높이를 100px로 맞춤
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT,100);
		// s_로 시작하면 썸네일 이미지고 s_ 제외했을 때는 원본 파일의 이름이 되도록 하기 위한 설정
		String thumbnailName = uploadPath + path + File.separator + "s_" + fileName;
		
		// 썸네일 파일 생성
		File newFile = new File(thumbnailName);
		// 파일 이름에서 .를 찾아 그 뒤의 인덱스부터 읽어들임 .뒤의 파일 확장자 추출
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		// 윈도우 경로로 사용하는 \ 문자가 정상적인 경로로 인식되지 않기 때문에 /로 치환해 줌
		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar,'/');
	}
	
	// 경로처리 하는 문자열 치환용도
	private static String makeIcon(String uploadPath, String path, String fileName) throws Exception {
		
		String iconName = uploadPath + path + File.separator + fileName;
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
}
