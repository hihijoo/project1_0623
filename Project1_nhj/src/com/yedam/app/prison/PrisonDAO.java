package com.yedam.app.prison;

import java.sql.SQLException;

import com.yedam.app.common.DAO;
import com.yedam.app.offender.Offender;

public class PrisonDAO extends DAO {

	//싱글톤
	private static PrisonDAO dao = null;
	private PrisonDAO() {}
	public static PrisonDAO getInstance() {
		if(dao == null) {
			dao = new PrisonDAO();
		}
		return dao;
	}
	
	//등록
	public void insert (Prison prison) {
		try{
			connect();
			String sql = "INSERT INTO PRISON VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, prison.getPrisonName());
			pstmt.setString(2, prison.getPrisonLocation());
			//현재 교도소 최대 수용인원
			pstmt.setInt(3, prison.getPrisonOccupy()); 
			//현재 교도소 수용가능인원(최대 수용인원 - 수용중인 인원)
			pstmt.setInt(4, prison.getPrisonOccupy()-selectInfo(prison.getPrisonLocation()));
			
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
	
		}
	}
	
	//수용 중인 인원	
	public int selectInfo(String location) {
		int amount = 0;
		try {
			connect();
			String sql = "SELECT COUNT(*) "+ "FROM OFFENDERS " + "WHERE LOCAION = " + location;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				amount = rs.getInt("count");
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
			
		}
		
		return amount;
	}
	
	
	//수정 - 수용인원 조절
	public void updateOccupy(Prison prison) {
		
		try {
			connect();				//문장 끝날때 마다 공백 신경쓰기
			String sql = "UPDATE PRISON SET Prison_Occupy = ? WHERE PRISON_NAME = ?";
			pstmt.setInt(1, prison.getPrisonOccupy());
			pstmt.setString(2, prison.getPrisonName());
			
			int result = pstmt.executeUpdate();
			
			if (result>0) {
				System.out.println("인원이 수정되었습니다.");
			}else {
				System.out.println("인원이 정상적으로 수정되지 않았습니다.");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
	}
	

	
}
