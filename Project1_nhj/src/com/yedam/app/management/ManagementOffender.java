package com.yedam.app.management;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.yedam.app.member.MemberDAO;
import com.yedam.app.offender.Offender;
import com.yedam.app.offender.OffenderDAO;
import com.yedam.app.prison.PrisonDAO;

public class ManagementOffender {

	// 필드
	protected Scanner sc = new Scanner(System.in);
	protected MemberDAO mDAO = MemberDAO.getInstance();
	protected OffenderDAO oDAO = OffenderDAO.getInstance();
	protected PrisonDAO pDAO = PrisonDAO.getInstance();
	protected ManagementDAO gDAO = ManagementDAO.getInstance();

	// 생성자
	public void run() {

		while (true) {
			menuPrint();

			int menuNo = menuSelect();

			if (menuNo == 1) {
				// 1.전체조회
				searchOffender();
			} else if (menuNo == 2) {
				// 2.등록
				insertOffender();
			} else if (menuNo == 3) {
				// 3.수정 - 형량
				updateSentence(); 
			} else if (menuNo == 4) {
				// 4.수정 - 지역
				updateLocation();
			} else if (menuNo == 5) {
				// 5. 지역별 전체 조회
				selectLocaiont();
			} else if (menuNo == 6) {
				// 6. 단건조회(투옥중인 사람)
				selectImprison();
			} else if (menuNo == 7) {
				// 6. 단건조회(출소한 사람)
				selectFreedom();
			} else if (menuNo == 9) {
				// 프로그램 종료
				exit();
				break;
			} else {
				// 입력오류
				showInputError();
			}
		}
	}

	protected void menuPrint() {
		System.out.println("=========================================================================");
		System.out.println("1.전체조회 2.등록 3.형량-수정 4.지역-수정 5.지역별 조회 6.수감중인 사람 7.출소한 사람 9.로그아웃");
		System.out.println("=========================================================================");

	}

	protected int menuSelect() {
		int menuNo = 0;
		try {
			menuNo = Integer.parseInt(sc.nextLine());

		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주시기 바랍니다.");
		}
		return menuNo;
	}

	protected void exit() {
		System.out.println("메인으로 돌아갑니다.");
	}

	protected void showInputError() {
		System.out.println("메뉴에서 입력해주시기 바랍니다.");
	}

	// 1. 전체조회
	protected void searchOffender() {
		List<Management> list = gDAO.selectAll();

		for (Management management : list) {
			System.out.println(management);
		}
	}

	// 2. 범죄자등록
	protected void insertOffender() {
		// 정보 입력
		Offender offender = inputOffender();

		// 디비에 저장
		oDAO.insert(offender);
	}

	
	protected Offender inputOffender() {
		Offender info = new Offender();
		try {
		
		System.out.print("이름>");
		info.setName(sc.nextLine());
		System.out.print("성별>");
		info.setGender(sc.nextLine());
		System.out.print("생년월일(yyyy-mm-dd)>");
		info.setBirth(Date.valueOf(sc.nextLine()));
		System.out.print("주소>");
		info.setLocation(sc.nextLine());
		System.out.print("범죄명>");
		info.setCrime(sc.nextLine());
		System.out.print("수감일(yyyy-mm-dd)>");
		info.setImprison(Date.valueOf(sc.nextLine()));
		System.out.print("형량(n년 n개월)>");
		String str = sc.nextLine();
		long sentence = calcSentence(str);
		info.setSentence(sentence);
		
	}catch(IllegalArgumentException e) {
		//왜 정상적으로 실행합니다가 뜰까?
		System.out.println("괄호의 형태대로 입력해주세요.");
	}
		return info;

	
	}

	// 형량 달로 바꾸기
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

	// 3.형량 수정
	private void updateSentence() {
		// 이름 검색
		String name = inputName();

		// 동명이인 존재여부 확인
		List<Management> list = gDAO.selectName(name);
		Management management;
		if (list.size() == 0) {
			System.out.println("범죄자가 존재하지 않습니다.");
			return;
		}
		if (list.size() > 1) {
			System.out.println("동명이인이 존재합니다. 죄수번호를 확인해주세요");
			for (Management m : list) {
				System.out.println(m);
			}
			int prisonNum = inputPrisonNum();

			// 죄수 번호로 검색
			management = gDAO.selectPrisonNum(prisonNum);
		} else {
			management = list.get(0);
		}

		// 죄수 번호 검색
		// 수정할 정보 입력(형량)
		management = inputUpdateSentence(management);

		// 디비에 연결
		oDAO.updateSentence(management);

		// new java.util.Date()); //오늘 객체를 생성한다.
		//현재 날짜를 yyyy-mm-dd형식으로 바꿈
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//이걸 다시 문자열로 바꾼것
		String ss = sdf.format(new java.util.Date());
		//sql.date타입으로 변환
		Date d = Date.valueOf(ss);

		if (d.after(management.getReleased())) {
			System.out.println("석방 ㅊㅋ");
			management.setFreedom("출소");
			gDAO.updateFreedom(management);
		}

	}

	// 이름 검색
	private String inputName() {
		System.out.print("이름을 입력하세요>");
		return sc.nextLine();
	}

	// 죄수 번호 검색
	private int inputPrisonNum() {
		System.out.println();
		System.out.print("죄수번호를 입력하세요>");
		return Integer.parseInt(sc.nextLine());
	}

	// 수정할 형량 입력
	private Management inputUpdateSentence(Management management) {
		String fromSentenceToString = management.getSentence() / 12 + "년 " + management.getSentence() % 12 + "개월";
		System.out.println("기존 형량> " + fromSentenceToString);
		System.out.print("변경할 형량(원치 않을 경우 0 입력)>");
		String sentenceString = sc.nextLine();
		long sentence = calcSentence(sentenceString);
		if (!sentenceString.equals("0")) {
			management.setSentence(sentence);
			System.out.println("정상적으로 수정되었습니다.");
		} else {
			System.out.println("수정이 되지 않았습니다.");
		}
		return management;
	}

	// 4. 지역 수정
	private void updateLocation() {

		// 이름 검색
		String name = inputName();

		// 동명이인 존재여부 확인
		List<Management> list = gDAO.selectName(name);
		Management management;
		if (list.size() == 0) {
			System.out.println("범죄자가 존재하지 않습니다.");
			return;
		}
		if (list.size() > 1) {
			System.out.println("동명이인이 존재합니다. 죄수번호를 확인해주세요");
			for (Management m : list) {
				System.out.println(m);
			}
			int prisonNum = inputPrisonNum();

			// 죄수 번호로 검색
			management = gDAO.selectPrisonNum(prisonNum);
		} else {
			management = list.get(0);
		}

		// 수정할 정보 입력(형량)
		management = inputUpdateLocation(management);

		// 디비에 연결
		oDAO.updateLocation(management);
	}

	// 수정할 지역 입력
	private Management inputUpdateLocation(Management management) {

		System.out.println("기존 지역> " + management.getPrisonLocation());
		System.out.print("변경할 지역(원치 않을 경우 0 입력)>");
		String location = sc.nextLine();
		if (!location.equals("0")) {
			management.setPrisonLocation(location);
		} else {
			System.out.println("수정이 되지 않았습니다.");
		}
		return management;
	}

	// 5. 지역별 조회
	private void selectLocaiont() {
		// 지역 입력
		String prisonLocation = inputLocation();

		List<Management> management = gDAO.selectPrisonLocation(prisonLocation);
		if (management == null) {
			System.out.println("검색가능한 지역이 아닙니다.");
		}
		System.out.println("해당 지역에 교도소 정보입니다.");

		// 수용중인 인원
		System.out.println(management);

	}

	// 지역 입력
	private String inputLocation() {
		List<Management> list = gDAO.distinctLocation();
		System.out.print("검색 가능한 지역 - " + list.get(0).getPrisonLocation());
		for (int i = 1; i < list.size(); i++) {
			System.out.print("/" + list.get(i).getPrisonLocation());
		}
		System.out.print("\n지역 > ");
		return sc.nextLine();
	}

	// 6. 수감중인 사람 조회
	private void selectImprison() {

		List<Management> list = gDAO.selectImprison("수감중");

		if (list.size() == 0) {
			System.out.println("수감중인 사람이 없습니다.");
			return;
		}

		System.out.println("수감자 정보입니다.");

		// 수감자출력
		for (Management management : list) {
			System.out.println(management);

		}
	}

	// 7. 출소한 사람 조회
	private void selectFreedom() {

		List<Management> list = gDAO.selectFreedom("출소");
		if (list.size() == 0) {
			System.out.println("출소한 사람이 없습니다.");
			return;
		}
		System.out.println("출소자 정보입니다.");

		// 출소자 출력
		for (Management management : list) {
			System.out.println(management);
		}
	}

}
