package com.yedam.app.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Member {

	
	public String memberId;
	public String memberPassword;
	
	// 0-관리자, 1-일반
	private int memberRole;
	
	
	@Override
	public String toString() {
		String info = "";
		if(memberRole == 0) {
			info = "관리자 : " + memberId;
		}else  {
			info = "일반 : " + memberId;
			
		}
		return info;
	}
	
}
