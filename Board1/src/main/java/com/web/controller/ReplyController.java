package com.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.domain.Criteria;
import com.web.domain.PageMaker;
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
		System.out.println("reply register before ==>");
		try {
			System.out.println("reply register ==>");
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
			System.out.println("aaaa===>"+bno);
			entity = new ResponseEntity<>(service.listReply(bno), HttpStatus.OK);	// 200
			
		} catch (Exception e) {
			System.out.println("ReplyController error : "+e);
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);	// 400
		}
		
		return entity;
	}
	
	@RequestMapping(value = "/{rno}", method = {RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> update(@PathVariable("rno") Integer rno, @RequestBody ReplyVO vo) {
		ResponseEntity<String> entity = null;
		try {
			vo.setRno(rno);
			service.modifyReply(vo);
			entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);	// 200
			
		} catch (Exception e) {
			System.out.println("ReplyController error : "+e);
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);	// 400
		}
		
		return entity;
	}
	
	@RequestMapping(value = "/{rno}", method = {RequestMethod.DELETE})
	public ResponseEntity<String> remove(@PathVariable("rno") Integer rno) {
		ResponseEntity<String> entity = null;
		try {
			service.removeReply(rno);
			entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);	// 200
			
		} catch (Exception e) {
			System.out.println("ReplyController error : "+e);
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);	// 400
		}
		
		return entity;
	}
	
	@RequestMapping(value = "/{bno}/{page}", method = {RequestMethod.GET})
	public ResponseEntity<Map<String, Object>> listPage(@PathVariable("bno") Integer bno, 
														@PathVariable("page") Integer page) 
	{
		ResponseEntity<Map<String,Object>> entity = null;
		
		try {
			Criteria cri = new Criteria();
			cri.setPage(page);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			
			Map<String, Object> map = new HashMap<String, Object>();
			List<ReplyVO> list = service.listReplyPage(bno, cri);
			
			map.put("list", list);
			
			int replyCount = service.count(bno);
			pageMaker.setTotalCount(replyCount);
			
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);	// 200
			
		} catch (Exception e) {
			System.out.println("ReplyController error : "+e);
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);	// 400
		}
		return entity;
	}
	
}
