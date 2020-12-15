package com.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.web.util.MediaUtils;
import com.web.util.UploadFileUtils;

@Controller
public class UploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Resource(name = "uploadPath")
	private String uploadPath;
	
	@RequestMapping(value = "/uploadForm", method = RequestMethod.GET)
	public void uploadForm() throws Exception {
		System.out.println("uploadForm ===>");
	}
	
	@RequestMapping(value = "/uploadForm", method = RequestMethod.POST)
	public String uploadForm(MultipartFile file, Model model) throws Exception {
		
		logger.info("originalName: " +file.getOriginalFilename());
		logger.info("size: " +file.getSize());
		logger.info("contentType: " +file.getContentType());
		
		String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
				
		model.addAttribute("savedName", savedName);		
		
		return "uploadResult";
	}
	
	private String uploadFile(String originalName, byte[] fileData) throws Exception {
		
		UUID uid = UUID.randomUUID();
		
		String savedName = uid.toString() + "_" + originalName;
		
		File target = new File(uploadPath, savedName);
		
		// 실제 파일 처리
		FileCopyUtils.copy(fileData, target);
		
		return savedName;
	}
	
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.GET)
	public void uploadAjax() {
		System.out.println("uploadAjax");
	}
	
	// produces에서 한국어를 정상적으로 전송하기위한 설정
	@ResponseBody
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		
		logger.info("originalName: " +file.getOriginalFilename());
		logger.info("size: " +file.getSize());
		logger.info("contentType: " +file.getContentType());
		
		// 리소스가 정상적으로 생성되었다는 의미
		return new ResponseEntity<String>(UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.CREATED);
	}
	
	@ResponseBody
	@RequestMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		logger.info("File NAME:" + fileName);
		
		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			// 파일 이름에서 확장자를 추출하고 이미지 타입의 파일인 경우 적절한 MIME 타입 지정
			MediaType mType = MediaUtils.getMedaiType(formatName);
			
			HttpHeaders headers = new HttpHeaders();
			
			in = new FileInputStream(uploadPath + fileName);
			
			if(mType != null) {
				headers.setContentType(mType);
			}else {
				// 이미지 파일 아닌 경우 
				fileName = fileName.substring(fileName.indexOf("_")+1);
				// 다운로드시 사용자에게 보이는 파일의 이름을 한글인 경우 꺠지지 않도록 인코딩 처리 
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\""
				+ new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");
			}
			// 실제로 데이터 읽는 부분 commons 라이브러리 기능을 활용해서 대상 파일에서 데이터를 
			// 읽어내는 IOUtils.toByteArray()
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),
					headers, HttpStatus.CREATED);
		}catch(Exception e) {
			System.out.println("displayFile error =>"+e);
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			in.close();
		}
			return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName){
		
		logger.info("delete file: " + fileName);
		
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		MediaType mType = MediaUtils.getMedaiType(formatName);
		
		if(mType != null) { // 이미지 파일인 경우 썸네일 이름
			String front = fileName.substring(0,12);
			String end = fileName.substring(14);
			new File(uploadPath + (front + end).replace('/', File.separatorChar)).delete();
		}
		// 이미지 아닌 경우 실제 이름
		new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
		
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/deleteAllFiles", method = RequestMethod.POST)
	public ResponseEntity<String> deleteFile(@RequestParam("files[]") String[] files) {
		 logger.info("delete all files"+ files);
		 
		 if(files == null || files.length == 0) {
			 return new ResponseEntity<String>("deleted",HttpStatus.OK);
		 }
		 
		 for(String fileName : files) {
			 String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			 
			 MediaType mType = MediaUtils.getMedaiType(formatName);
			 
			 if(mType != null) {
				 String front = fileName.substring(0,12);
				 String end = fileName.substring(14);
				 
				 new File(uploadPath + (front+end).replace('/', File.separatorChar)).delete();
			 }
			 new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
		 }
		 
		 return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
}
