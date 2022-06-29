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

			// 메뉴 출력
			menuPrint();

			// 메뉴 선택하기
			int menuNo = menuSelect();

			if (menuNo == 1) {
				// 1.회원가입
				register();
			} else if (menuNo == 2) {
				// 2.로그인
				login();
			} else if (menuNo == 3) {
				// 3.종료
				exit();
				break;
			} else {
				// 오류났을때
				showInputError();
			}
		}
	}

	// 메뉴 출력
	private void menuPrint() {
		System.out.println("\n==========================");
		System.out.println("1.회원가입 2.로그인 3.종료");
		System.out.println("==========================");
	}

	// 메뉴 석택하기
	private int menuSelect() {

		int menuNo = 0;

		try {
			menuNo = Integer.parseInt(sc.nextLine());
			
		} catch (NumberFormatException e) {
			
			// 숫자가 아닌 값이 입력됐을때 오류문을 보여줌
			System.out.println("숫자형식으로 입력해주세요.");
		}
		return menuNo;
	}

	//3. 종료
	private void exit() {
		System.out.println("프로그램을 종료합니다.");
//		System.out.println("˙˚ ʚ ᕱ⑅ᕱ ɞ˚˙");
//		System.out.println("    (｡•◡•｡)");
//		System.out.println("   ┏━O━O━┓");
//		System.out.println("    TEXT");
//		System.out.println("   ┗━━━━━┛");
	}

	private void showInputError() {
		System.out.println("메뉴를 확인해주시기 바랍니다.");
	}

	// 1. 회원가입
	private void register() {
		// 1-1. 아이디와 비밀번호 입력
		Member information = inputregister();

		// 1-2. 아이디 중복여부
		Member member = mDAO.selectId(information.getMemberId());
		if (member != null) {
			System.out.println("중복된 아이디가 존재합니다.");
			return;
		}

		// 1-3. 등록되었을 경우 저장
		mDAO.register(information);

	}
	// 1-1. 아이디와 비밀번호 입력
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

	// 2. 로그인
	private void login() {
		// 2-1 아이디와 비밀번호 입력
		Member inputInfo = inputlogin();

		// 로그인 시도
		memberInfo = MemberDAO.getInstance().selectOne(inputInfo);

		// 실패할 경우 그대로 메소드 종료
		if (memberInfo == null)
			return;
		
		
		// 성공할 결우 프로그램 실행
		int loginInfo = memberInfo.getMemberRole();
		
		if(loginInfo ==0) {
			System.out.println("\n로그인 완료");
			//관리자 클래스가 실행됨
			new ManagementAdmin().run();

		} else {
			System.out.println("\n로그인 완료");
			//일반 유저 클래스가 실행됨
			new ManagementUser().run();
		}
	}
		
		
		/*
		Login loginMember = new Login();

		loginMember.setMemberId(inputInfo.getMemberId());
		loginMember.setMemberPassword(inputInfo.getMemberPassword());
		
		
		if (inputInfo.getMemberId().equals("admin")) {
			loginMember.setMemberRole(0);
			System.out.println("\n로그인이 완료");
			//관리자 클래스가 실행됨
			new ManagementAdmin().run();

		} else {
			loginMember.setMemberRole(1);
			System.out.println("\n로그인 완료");
			//일반 유저 클래스가 실행됨
			new ManagementUser().run();
		}
		*/

	// 2-1 아이디와 비밀번호 입력
	private Member inputlogin() {
		Member info = new Member();
		System.out.print("아이디 > ");
		info.setMemberId(sc.nextLine());
		System.out.print("비밀번호 > ");
		info.setMemberPassword(sc.nextLine());

		return info;
	}

}
