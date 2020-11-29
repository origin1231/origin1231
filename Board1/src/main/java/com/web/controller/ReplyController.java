package com.web.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.domain.ReplyVO;
import com.web.service.ReplyService;

@RestController
@RequestMapping("/replies")
public class ReplyController {
	@Inject
	private ReplyService service;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody ReplyVO vo) {
		ResponseEntity<String> entity = null;
		try {
			service.addReply(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);	// 200
		} catch (Exception e) {
			System.out.println("ReplyController error : "+e);
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);	// 400
		}
		
		return entity;
	}
	
	@RequestMapping(value = "/all/{bno}", method = RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") Integer bno) {
		ResponseEntity<List<ReplyVO>> entity = null;
		try {
			entity = new ResponseEntity<>(service.listReply(bno), HttpStatus.OK);	// 200
			
		} catch (Exception e) {
			System.out.println("ReplyController error : "+e);
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);	// 400
		}
		
		return entity;
	}
	
	@RequestMapping(value = "/all/{bno}", method = {RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> update(@PathVariable("rno") Integer rno, @RequestBody ReplyVO vo) {
		ResponseEntity<String> entity = null;
		try {
			vo.setRno(rno);
			service.modifyReply(vo);
			entity = new ResponseEntity<>("SUCCESS",HttpStatus.OK);	// 200
			
		} catch (Exception e) {
			System.out.println("ReplyController error : "+e);
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);	// 400
		}
		
		return entity;
	}
}
