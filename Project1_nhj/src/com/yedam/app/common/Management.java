package com.yedam.app.common;

import java.util.Scanner;


import com.yedam.app.member.MemberDAO;
import com.yedam.app.prison.PrisonDAO;


public class Management {
 //멤버가 굴러가는데

	//필드
	protected Scanner sc = new Scanner(System.in);
	protected MemberDAO mDAO = MemberDAO.getInstance();
	protected PrisonDAO pDAO = PrisonDAO.getInstance();
	
	//생성자
	public void run() {
		
		boolean role = selectRole();
		
		while(true) {
			menuPrint();
			
			int menuNo = menuSelect();
			
			if(menuNo ==1) {
				//제품정보 관리
			
			}else if(menuNo ==2) {
				//제품관리
				
			}else if(menuNo ==9) {
				//프로그램 종료
				exit();
				break;
			}else {
				//입력오류
				showInputError();
			}
		}
	}
	
	//메소드
	protected void menuPrint() {
		System.out.println("================================");
		System.out.println("1.범죄자 조회 2.제품재고 관리 9.로그아웃");
		System.out.println("================================");
	}
	
	protected int menuSelect() {
		int menuNo = 0;
		try {
			menuNo = Integer.parseInt(sc.nextLine());
			
		}catch(NumberFormatException e){
			System.out.println("숫자를 입력해주시기 바랍니다.");
		}
		return menuNo;
	}
	
	protected void exit() {
		System.out.println("프로그램을 종료합니다.");
	}

	protected void showInputError () {
		System.out.println("메뉴에서 입력해주시기 바랍니다.");
	}
	

	
	protected boolean selectRole() {
		//이게 무슨 뜻일까?
		int memberRole = LoginControl.getLoginInfo().getMemberRole();
		if(memberRole == 0) {
			return true; //회원이면 돌아감
		} else {
			return false;
		}
	}
	
}
