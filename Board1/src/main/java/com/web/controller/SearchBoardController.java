package com.web.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.domain.BoardVO;
import com.web.domain.PageMaker;
import com.web.domain.SearchCriteria;
import com.web.service.BoardService;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	
	@Inject
	private BoardService service;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		logger.info("list..."+cri.toString());
		
		//model.addAttribute("list", service.listCriteria(cri));
		model.addAttribute("list", service.listSearchCriteria(cri));
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		//pageMaker.setTotalCount(service.listCountCriteria(cri));
		pageMaker.setTotalCount(service.listSearchCount(cri));
		
		model.addAttribute("pageMaker", pageMaker);
	}
	
	@RequestMapping(value="/read", method = RequestMethod.GET)
	public void read(@RequestParam("bno") int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		
		logger.info("read......................"+bno);
		// addAttribute에 이름 입력안하고 등록하면 클래스 이름을 소문자로 인식해서 사용하게됨 boardVO
		model.addAttribute(service.read(bno));
	}
	
	@RequestMapping(value="/remove", method = RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno, SearchCriteria cri, RedirectAttributes rttr) throws Exception {
		
		logger.info("remove......................"+bno);
		service.remove(bno);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addFlashAttribute("msg","SUCCESS");
		
		return "redirect:/sboard/list";
	}
	
	@RequestMapping(value="/modify", method = RequestMethod.GET)
	public void modifyPagingGET(@RequestParam("bno") int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		
		logger.info("modify GET......................"+bno);
		model.addAttribute(service.read(bno));
	}
	
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public String modifyPagingPOST(BoardVO board,  SearchCriteria cri, RedirectAttributes rttr) throws Exception {
		
		logger.info("modify POST......................"+cri.toString());
		service.modify(board);
		
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addFlashAttribute("msg","SUCCESS");
		logger.info("modify POST......................"+rttr.toString());
		
		
		return "redirect:/sboard/list";
	}
	
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public void registerGET(BoardVO board, Model model) throws Exception {
		logger.info("register get...");
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String registPOST(BoardVO board, RedirectAttributes rttr) throws Exception {
		logger.info("regist post....");
		logger.info(board.toString());
		
		service.regist(board);
		
		//model.addAttribute("msg", "success");
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		//return "/board/success";
		// 등록완료후 새로고침시 글이 계속 등록되는 현상 막기위해 리다이렉트 시킴
		return "redirect:/sboard/list";
	}
	
	@RequestMapping("/getAttach/{bno}")
	@ResponseBody
	public List<String> getAttach(@PathVariable("bno")Integer bno) throws Exception {
		return service.getAttach(bno);
	}
}
