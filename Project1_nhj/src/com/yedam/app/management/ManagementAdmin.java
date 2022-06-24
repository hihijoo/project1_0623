package com.yedam.app.management;

import java.util.Scanner;

import com.yedam.app.member.MemberDAO;
import com.yedam.app.offender.OffenderDAO;
import com.yedam.app.prison.PrisonDAO;

public class ManagementAdmin {
//관리자가 굴러가는데
	
	//필드
		protected Scanner sc = new Scanner(System.in);
		protected MemberDAO mDAO = MemberDAO.getInstance();
		protected OffenderDAO oDAO = OffenderDAO.getInstance();
		protected PrisonDAO pDAO = PrisonDAO.getInstance();

		
		//생성자
		public void run() {
			
			while(true) {
				menuPrint();
				
				int menuNo = menuSelect();
				
				if(menuNo ==1) {
					//1.범죄자 조회
					searchOffender();
				
				}else if (menuNo == 2){
					//2. 범죄자 등록
					
				}else if (menuNo == 3){
					//3. 범죄자 수정 - 형량
				}else if (menuNo == 4){
					//4. 범죄자 수정 - 지역
				}else if (menuNo == 5){
					//5. 범죄자 전체 조회
				}else if (menuNo == 6){
					//6. 단건조회(투옥중인 사람)
				}else if (menuNo == 7){
					//6. 단건조회(출소한 사람)
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
			System.out.println("1.범죄자 조회 2. 바보 9.로그아웃");
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
		

	
}
