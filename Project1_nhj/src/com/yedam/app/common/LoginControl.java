package com.yedam.app.common;

import java.util.Scanner;

import com.yedam.app.member.Member;
import com.yedam.app.member.MemberDAO;



public class LoginControl {

	private Scanner sc = new Scanner(System.in);
	private static Member loginInfo = null;
	
	public static Member getLoginInfo() {
		return loginInfo;
	}
	
	public LoginControl() {
		while (true) {
			menuPrint();
			
			int menuNo = menuSelect();
			
			if(menuNo == 1) {
				//회원가입
				register();
			}else if(menuNo == 2) {
				//로그인
				login();
			}else if(menuNo == 3) {
				//종료
				exit();
				break;
			}else  {
				showInputError();
			}
		}
	}
	
	private void menuPrint() {
		System.out.println("===================");
		System.out.println("1.회원가입 2.로그인 3.종료");
		System.out.println("===================");
	}
	
	
	private int menuSelect() {
	int menuNo = 0;
	
	try {
		menuNo = Integer.parseInt(sc.nextLine());
	}catch(NumberFormatException e) {
		//숫자가 아닌 값이 입력됐을때 오류문을 보여줌
		System.out.println("숫자형식으로 입력해주세요.");
	}
	 return menuNo;
	}
	
	private void exit() {
		System.out.println("프로그램을 종료합니다.");
	}
	
	private void showInputError() {
		System.out.println("메뉴를 확인해주시기 바랍니다.");
	}
	
	//회원가입
	private void register() {
		Member information = PersonalInformation();
		
		//중복 아이디 여부 만들고 싶다....
		Member member = MemberDAO.register(information.getMemberId());
		
		
	}
	
	private Member PersonalInformation(){
		Member personal = new Member();
		System.out.print("아이디 > ");
		personal.setMemberId(sc.nextLine());
		System.out.print("비밀번호 > ");
		personal.setMemberPassword(sc.nextLine());
		
		return personal;
		
	}
	
	//로그인
	private void login() {
		//아이디와 비밀번호 입력
		Member inputInfo = inputMember();
		
		//로그인 시도
		loginInfo = MemberDAO.getInstance().selectOne(inputInfo);
		
		//실패할 경우 그대로 메소드 종료
		if(loginInfo == null)
			return;
		
		//성공할 결우 프로그램 실행
		new Management().run();
	}
	
	private Member inputMember() {
		Member info = new Member();
		System.out.print("아이디 > ");
		info.setMemberId(sc.nextLine());
		System.out.print("비밀번호 > ");
		info.setMemberPassword(sc.nextLine());
		
		return info;
	}
	
	
	
}
