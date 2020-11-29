package com.web.domain;

//@Alias("Criteria")
// 검색기준, 분류기준 클래스
public class Criteria {
	private int page;
	private int perPageNum;
	//private int startPage;
	
	
	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		
		if(page <= 0) {
			this.page = 1;
			return;
		}
		
		this.page = page;
	}

	public int getPerPageNum() {
		return this.perPageNum;
	}

	public int getPageStart() {
		return (this.page-1) * perPageNum;
	}
	
	public void setPerPageNum(int perPageNum) {
		
		if(perPageNum <=0 || perPageNum > 100) {
			this.perPageNum = 10;
			return;
		}
		this.perPageNum = perPageNum;
	}
	
	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum="+ perPageNum +", PageStart" + getPageStart()+"]" ;
	}
}
