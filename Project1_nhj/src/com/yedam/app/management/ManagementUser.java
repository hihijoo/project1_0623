package com.yedam.app.management;

import java.util.List;
import java.util.Scanner;


import com.yedam.app.member.MemberDAO;
import com.yedam.app.offender.Offender;
import com.yedam.app.offender.OffenderDAO;
import com.yedam.app.prison.PrisonDAO;


public class ManagementUser {
 //일반 사용자가 굴러가는데

	//필드
	protected Scanner sc = new Scanner(System.in);
	protected MemberDAO mDAO = MemberDAO.getInstance();
	protected OffenderDAO oDAO = OffenderDAO.getInstance();

	
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
		System.out.println("==================");
		System.out.println("1.범죄자 조회 9.로그아웃");
		System.out.println("==================");
		
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
	

	protected void searchOffender() {
		//조회하고자 하는 지역을 입력
		String location = inputLocation();
		 
		List<Offender> offenders = oDAO.selectAll(location);
		 
		if(offenders.size() == 0) {
			System.out.println("해당지역에 범죄자가 없습니다.");
			return;
		}
		
		for(Offender offender : offenders) {
			System.out.println(offender);
		}
		
	}
	
	protected String inputLocation(){
		System.out.println("검색 가능한 지역 - 서울/인천/부산/대구/광주/강릉/제주/대전/울산/천안");
		System.out.print("지역을 입력하세요> ");
		return sc.nextLine();
		
	}
	
}
