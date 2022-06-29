package com.yedam.app.management;

import java.util.List;
import java.util.Scanner;
import com.yedam.app.member.MemberDAO;
import com.yedam.app.offender.OffenderDAO;



public class ManagementUser {
 //일반 사용자가 굴러가는데

	//필드
	protected Scanner sc = new Scanner(System.in);
	protected MemberDAO mDAO = MemberDAO.getInstance();
	protected OffenderDAO oDAO = OffenderDAO.getInstance();
	protected ManagementDAO gDAO = ManagementDAO.getInstance();		
	
	//생성자
	public void run() {
		
		while(true) {
			menuPrint();
			
			int menuNo = menuSelect();
			
			if(menuNo ==1) {
				//1.범죄자 조회
				searchOffender();
			
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
	
	//권한에 따른 메뉴
	protected void menuPrint() {
		System.out.println("\n==========================");
		System.out.println("1.범죄자 조회 9.로그아웃");
		System.out.println("==========================");
		
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
		System.out.println("\n프로그램을 종료합니다.");
	}

	protected void showInputError () {
		System.out.println("메뉴에서 입력해주시기 바랍니다.");
	}
	
	//1.범죄자 조회 - 지역별로 조회가능
	protected void searchOffender() {
		//1-1조회하고자 하는 지역을 입력
		String location = inputLocation();
		 
		List<Management> management = gDAO.selectLocation(location);
		 
		if(management.isEmpty()) { 
			
			System.out.println("검색 가능한 지역이 아닙니다.");
			return;
		}
		
		System.out.println("\n해당 지역에 범죄자 정보입니다.");
		//유저가 보는 전체화면
		for(Management allView : management) {
			System.out.println(allView.allView());
		}
		
	}

	
	// 1-1 지역검색
	private String inputLocation() {
		try {
			
		List<Management> list = gDAO.distinctOffenderLocation();
		
		System.out.print("검색 가능한 지역 - " + list.get(0).getPrisonLocation());
		for (int i = 1; i < list.size(); i++) {
			System.out.print("/" + list.get(i).getPrisonLocation());
		}
		System.out.print("\n지역 > ");

		} catch(IndexOutOfBoundsException i) {
		}
		return sc.nextLine();
	}
	
}
