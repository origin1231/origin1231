package com.web.domain;

// 객체를 JSON으로 반환하는 경우
public class SampleVO {
	private Integer mno;
	private String firstName;
	private String lastName;
	
	public Integer getMno() {
		return mno;
	}

	public void setMno(Integer mno) {
		this.mno = mno;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		
		return "mno:"+ mno + "firstName:" + firstName + "lastName:" + lastName;
	}
}
