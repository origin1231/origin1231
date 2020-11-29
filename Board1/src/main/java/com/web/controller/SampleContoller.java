package com.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.domain.SampleVO;

@RestController	// 뷰처리를 JSP의 경로로 하지 않음
@RequestMapping("/sample")
public class SampleContoller {
	
	@RequestMapping("/hello")
	public String sayHello() {
		return "Hello World";
	}
	
	@RequestMapping("/sendVO")	// 객체를 JSON으로 반환하는 경우
	public SampleVO sendVO() {
		SampleVO vo = new SampleVO();
		vo.setFirstName("프링");
		vo.setLastName("스");
		vo.setMno(1234);
		
		return vo;
	}
	
	@RequestMapping("/sendList") // 컬렉션 타입의 객체를 반환하는 경우
	public List<SampleVO> sendList(){
		List<SampleVO> list = new ArrayList<SampleVO>();
		for(int i=0; i<10; i++) {
			SampleVO vo = new SampleVO();
			vo.setFirstName("프링");
			vo.setLastName("스");
			vo.setMno(i);
			list.add(vo);
		}
		
		return list;
	}
	
	@RequestMapping("/sendMap")
	public Map<Integer,SampleVO> sendMap(){
		Map<Integer,SampleVO> map = new HashMap<>();
		for(int i=0; i<10; i++) {
			SampleVO vo = new SampleVO();
			vo.setFirstName("프링");
			vo.setLastName("스");
			vo.setMno(i);
			map.put(i,vo);
		}
		
		return map;
	}
	
	@RequestMapping("/sendErrorAuth")
	public ResponseEntity<Void> sendListAuth() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping("/sendErrorNot")
	public ResponseEntity<List<SampleVO>> sendListNot(){
		List<SampleVO> list = new ArrayList<>();
		for(int i=0; i<10; i++) {
			SampleVO vo = new SampleVO();
			vo.setFirstName("프링");
			vo.setLastName("스");
			vo.setMno(i);
			list.add(vo);
		}
		
		return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
	}
}
