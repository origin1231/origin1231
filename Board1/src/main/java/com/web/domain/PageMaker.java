package com.web.domain;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {
	private int totalCount;	// 총페이지수
	private int startPage;	// 시작페이지
	private int endPage;	// 끝페이지
	private boolean prev;	// 이전
	private boolean next;	// 다음
	
	private int displayPageNum = 10;  // 하단에 표시되는 페이지번호 목록수
	
	private Criteria cri;
	
	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		calcData();
	}
	
	// 페이지 계산
	private void calcData() {
		endPage = (int)(Math.ceil(cri.getPage() / (double)displayPageNum) * displayPageNum);
		startPage = (endPage - displayPageNum) + 1;
		
		int tempEndPage = (int)(Math.ceil(totalCount / (double)cri.getPerPageNum()));
		
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		System.out.println();
		prev = startPage == 1 ? false : true;
		
		next = endPage * cri.getPerPageNum() >= totalCount ? false : true;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public int getStartPage() {
		return startPage;
	}
	
	public int getEndPage() {
		return endPage;
	}
	
	public boolean isPrev() {
		return prev;
	}
	
	public boolean isNext() {
		return next;
	}
	
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	
	public Criteria getCri() {
		return cri;
	}
	
	public String makeQuery(int page) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance().queryParam("page", page)
						.queryParam("perPageNum", cri.getPerPageNum()).build();
		
		return uriComponents.toUriString();
	}
	
	public String makeSearch(int page) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance().queryParam("page", page)
						.queryParam("perPageNum", cri.getPerPageNum())
						.queryParam("searchType", ((SearchCriteria)cri).getSearchType())
						.queryParam("keyword", ((SearchCriteria)cri).getKeyword())		
						.build();
		
		return uriComponents.toUriString();
	}
	
	@Override
	public String toString() {
		return "Criteria [startPage=" +startPage + ", endPage=" +endPage+ ", prev=" +prev+ ", next=" +next+ "]" ;
	}
}
