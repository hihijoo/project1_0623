package com.yedam.app.management;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.yedam.app.member.MemberDAO;
import com.yedam.app.offender.Offender;
import com.yedam.app.offender.OffenderDAO;
import com.yedam.app.prison.PrisonDAO;

public class ManagementOffender {

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
				//1.전체조회
				searchOffender();
			}else if (menuNo == 2){
				//2.등록
				insertOffender();
			}else if (menuNo == 3){
				//3.수정 - 형량
			}else if (menuNo == 4){
				//4.수정 - 지역
			}else if (menuNo == 5){
				//5. 지역별 전체 조회
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
		
		
		protected void menuPrint() {
			System.out.println("======================================================================");
			System.out.println("1.전체조회 2.등록 3.형량-수정 4.지역-수정 5.지역별 조회 6.수감중인 사람 7.출소한 사람 9.로그아웃");
			System.out.println("======================================================================");
			
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
			System.out.println("메인으로 돌아갑니다.");
		}

		protected void showInputError () {
			System.out.println("메뉴에서 입력해주시기 바랍니다.");
		}
		
		//1. 전체조회
		protected void searchOffender() {
		List<Management> list = oDAO.selectAll();
		
		for(Management management : list) {
			System.out.println(management);
		}
		}
		
		//2. 범죄자등록
		protected void insertOffender() {
			//정보 입력
			Offender offender = inputOffender();
			
			//디비에 저장
			oDAO.insert(offender);
		}
		
		protected Offender inputOffender() {
			Offender info = new Offender();
			System.out.println("이름>");
			info.setName(sc.nextLine());
			System.out.println("성별>");
			info.setGender(sc.nextLine());
			System.out.println("생년월일>(yyyy-mm-dd)");
			info.setBirth(Date.valueOf(sc.nextLine()));
			System.out.println("주소");
			info.setLocation(sc.nextLine());
			System.out.println("죄목");
			info.setCrime(sc.nextLine());
			System.out.println("투옥일(yyyy-mm-dd)");
			info.setImprison(Date.valueOf(sc.nextLine()));
			System.out.println("형량>(n년 n개월)");
			String str = sc.nextLine();
			long sentence = calcSentence(str);
			info.setSentence(sentence);
			
			
			return info;
		}
		
		//형량 달로 바꾸기
		private long calcSentence(String str) {
			StringTokenizer st = new StringTokenizer(str);
		    long month = 0;
		    while (st.hasMoreTokens()) {
		       String y = st.nextToken();
		       if (y.charAt(y.length() - 1) == '년') {
		          y = y.substring(0, y.length() - 1);
		          month += (Integer.parseInt(y) * 12);
		       } else if (y.charAt(y.length() - 1) == '월') {
		          y = y.substring(0, y.length() - 2);
		          month += Integer.parseInt(y);
		       }
		    }
	return month;
		}
}
