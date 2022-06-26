package com.yedam.app.member;

import java.util.Scanner;

import com.yedam.app.management.ManagementAdmin;
import com.yedam.app.management.ManagementUser;



public class LoginControl {

	private Scanner sc = new Scanner(System.in);
	private static Member memberInfo = null;
	private MemberDAO mDAO = MemberDAO.getInstance();
	
	public static Member getLoginInfo() {
		return memberInfo;
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
		System.out.println("====================");
		System.out.println("1.회원가입 2.로그인 3.종료");
		System.out.println("====================");
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
		//아이디와 비밀번호 입력
		Member information = inputregister();
		
		//아이디 중복여부
		Member member = mDAO.selectId(information.getMemberId());
		if(member != null) {
			System.out.println("중복된 아이디가 존재합니다.");
			return;
		}
		
		//등록되었을 경우 저장
		mDAO.register(information);
		
		
	}
	private Member inputregister() {
		Member info = new Member();
		System.out.print("아이디 > ");
		info.setMemberId(sc.nextLine());
		System.out.print("비밀번호 > ");
		info.setMemberPassword(sc.nextLine());
		System.out.print("이름 > ");
		info.setMemberName(sc.nextLine());
		return info;
	}
	
	
	//로그인
	private void login() {
		//아이디와 비밀번호 입력
		Member inputInfo = inputlogin();
		
		//로그인 시도
		memberInfo = MemberDAO.getInstance().selectOne(inputInfo);
		
		//실패할 경우 그대로 메소드 종료
		if(memberInfo == null)
			return;
		
		
		//성공할 결우 프로그램 실행
		
		Login loginMember = new Login();
		
		loginMember.setMemberId(inputInfo.getMemberId());
		loginMember.setMemberPassword(inputInfo.getMemberPassword());
		if (inputInfo.getMemberId().equals("admin")) {
			loginMember.setMemberRole(0);
			new ManagementAdmin().run();
		} else {
			loginMember.setMemberRole(1);
			new ManagementUser().run();
		}
	
	
		

	
	}

	

	private Member inputlogin() {
		Member info = new Member();
		System.out.print("아이디 > ");
		info.setMemberId(sc.nextLine());
		System.out.print("비밀번호 > ");
		info.setMemberPassword(sc.nextLine());
		
		return info;
	}
	
	
	
}
