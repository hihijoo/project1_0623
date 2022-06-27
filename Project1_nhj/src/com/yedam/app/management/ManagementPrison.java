package com.yedam.app.management;

import java.util.List;
import java.util.Scanner;

import com.yedam.app.member.MemberDAO;
import com.yedam.app.offender.OffenderDAO;
import com.yedam.app.prison.Prison;
import com.yedam.app.prison.PrisonDAO;

public class ManagementPrison {

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
				//1.등록
				insertPrison();
			}else if (menuNo == 2){
				//2. 전체조회
				selectAll();
			}else if (menuNo == 3){
				//3. 단건 조회 - 지역별 //혹시 그 지역에 있는 죄수자들도 함께 나오게 할 수 있을까???
				selectLocation();
			}else if (menuNo == 4){
				//4. 교도소 이름 변경
				updatePrisonName();
			}else if (menuNo == 5){
				//3. 수용 가능한 인원 수정
				updateAccommodate();
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
			System.out.println("==============================================================");
			System.out.println("1.교도소 등록 2.전체조회 3.지역별 조회 4.교도소이름 변경 5.수용인원 변경 9.뒤로가기");
			System.out.println("==============================================================");
			
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
		
		//1.교도소 등록
		private void insertPrison() {
			//교도소 정보 입력
			Prison info = inputPrison();
			
			//해당 지역 교도소 등록 여부
			Prison prison = pDAO.selectLocation(info.getPrisonLocation());
			
			if(prison != null) {
				System.out.println("해당 지역에 교도소가 존재합니다.");
				return;
			}
			
			//db에 저장
			pDAO.insert(info);
		}
		
		private Prison inputPrison() {
			Prison info = new Prison();
			System.out.print("교도소 이름>");
			info.setPrisonName(sc.nextLine());
			System.out.print("교도소 지역>");
			info.setPrisonLocation(sc.nextLine());
			System.out.print("수용가능한 인원>");
			info.setPrisonAccommodate(Integer.parseInt(sc.nextLine()));			
			return info;
		}
		
		//2.전체조회
		private void selectAll() {
			List<Prison> list = pDAO.selectAll();
			
			for(Prison prison : list) {
			//System.out.println(prison);
				int people = pDAO.selectInfo(prison.getPrisonLocation());
				prison.setPrisonOccupy(prison.getPrisonAccommodate()-people);
				pDAO.updateOccupy(prison);
				System.out.println(prison);
			
			}
		}
		
		//3.지역별 조회
		private void selectLocation() {
			//지역 입력
			String prisonLocation = inputLocation();
			
			Prison prison = pDAO.selectLocation(prisonLocation);
			
			if(prison == null) {
				System.out.println("검색가능한 지역이 아닙니다.");
				return;
			}
			
			System.out.println("해당 지역에 교도소 정보입니다.");
			
			//수용중인인원
			System.out.println(prison);
		
			
			
			
		}
		
		//지역 검색
		private String inputLocation() {
			List<Prison> list = pDAO.selectAll();
			//리스트 0번 부터 시작하니까 예를들면 서울 정보가 들어가있음
			System.out.print("검색 가능한 지역 - "+list.get(0).getPrisonLocation());
			for(int i =1; i<list.size(); i++) {
				System.out.print("/"+list.get(i).getPrisonLocation());
			}
	
			return sc.nextLine();
		}
		
		//4. 교도소 이름 변경
		private void updatePrisonName() {
			//교도소 지역 검색
			String prisonLocation = inputLocation();
			
			//교도소 정보 검색
			Prison prison = pDAO.selectLocation(prisonLocation);
			
			if(prison == null) {
				System.out.println("등록된 교도소 정보가 없습니다.");
				return;
			}
			
			//수정할 정보 입력
			prison = inputUpdateName(prison);
			
			//db에 저장
			pDAO.updateName(prison);
		}

		
		//5.수용인원 수정
		private void updateAccommodate() {
			//교도소 지역 입력
			String prisonLocation = updateLocation();
			
			//교도소 정보 검색
			Prison prison = pDAO.selectLocation(prisonLocation);
			
			if(prison == null) {
				System.out.println("검색가능한 지역이 아닙니다.");
				return;
			}
			//수정할 정보 입력
			prison = inputUpdateInfo(prison);
			
			//db에 수정
			pDAO.updateAccommodate(prison);
		
		}
		
		//변경 지역 조회
		private String updateLocation() {
			List<Prison> list = pDAO.selectAll();
			//리스트 0번 부터 시작하니까 예를들면 서울 정보가 들어가있음
			System.out.print("변경 가능한 지역 - "+list.get(0).getPrisonLocation());
			System.out.print("지역 > ");
			for(int i =1; i<list.size(); i++) {
				System.out.print("/"+list.get(i).getPrisonLocation());
			}
			
			return sc.nextLine();
		}
		
		
		private Prison inputUpdateInfo(Prison prison) {
			System.out.println("기존 최대수용 인원> "+prison.getPrisonAccommodate());
			System.out.print("변경 인원(원치 않을 경우 0 입력)> ");
			int accommodate = Integer.parseInt(sc.nextLine());
			int occupy = pDAO.selectInfo(prison.getPrisonLocation());
			if(accommodate != 0) {
				prison.setPrisonAccommodate(accommodate);
				prison.setPrisonOccupy(accommodate-occupy);
			} else {
				System.out.println("수정이 되지 않았습니다.");
			}
			return prison;
		}
	
		private Prison inputUpdateName(Prison prison) {
			System.out.println("기존 교도소 이름>"+prison.getPrisonName());
			System.out.print("변경할 이름(원치 않을 경우 0 입력)> ");
			String name = sc.nextLine();
			if(!name.equals("0")) {	
				prison.setPrisonName(name);
			} else {
			System.out.println("수정이 되지 않았습니다.");
			}
			return prison;
		}
		
}
