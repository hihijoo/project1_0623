package com.yedam.app.offender;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.yedam.app.common.DAO;
import com.yedam.app.management.Management;
import com.yedam.app.prison.Prison;


public class OffenderDAO extends DAO {

	
	//싱글톤
	private static OffenderDAO dao = null;
	private OffenderDAO() {}
	public static OffenderDAO getInstance() {
		if(dao == null) {
			dao = new OffenderDAO();
		}
		return dao;
	}
	
	// 등록
	public void insert(Offender offender) {
		
		try {
			connect();
		
			String sql = "INSERT INTO OFFENDERS VALUES (OFFENDERS_SEQ.NEXTVAL,?,?,?,?,?,?,?,add_months(?,?),default)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, offender.getName());
			pstmt.setString(2, offender.getGender());
			pstmt.setDate(3, offender.getBirth());
			pstmt.setString(4, offender.getLocation());
			pstmt.setString(5, offender.getCrime());
			pstmt.setDate(6, offender.getImprison());//투옥일
			pstmt.setLong(7, offender.getSentence());//형량
			pstmt.setDate(8, offender.getImprison());//투옥일
			pstmt.setLong(9, offender.getSentence()); //석방일을 투옥일에서 형량 더한값으로 나오게 하는 방법
		
			int result = pstmt.executeUpdate();
			
			if(result >0) {
				//여기에 석방일 값을 변경하는 로직을 만들고 싶음
				System.out.println("정상적으로 등록되었습니다.");
			} else {
				System.out.println("정상적으로 등록되지 않았습니다.");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
	}

	// 수정 - 형량
	public void updateSentence(Management management) {
		
		try {
			connect();				//문장 끝날때 마다 공백 신경쓰기
			String sql = "UPDATE OFFENDERS SET SENTENCE = ?, RELEASED = ADD_MONTHS(?,?) WHERE prison_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, management.getSentence());
			pstmt.setDate(2, management.getImprison());
			pstmt.setLong(3, management.getSentence());
			pstmt.setInt(4, management.getPrisonNum());
			
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
	}
	
	
	// 수정 - 주소
	public void updateLocation(Management management) {
		
		try {
			connect();				//문장 끝날때 마다 공백 신경쓰기
			String sql = "UPDATE OFFENDERS SET Location = ? WHERE PRISON_NUM = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, management.getPrisonLocation());
			pstmt.setInt(2, management.getPrisonNum());
			
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
	}
	 
	

	
	//삭제 -죄수번호
	public void delete(int prisonNum) {
		
		try {
			connect();
			String sql = "DELETE FROM OFFENDERS WHERE PRISON_NUM = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prisonNum);
					
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("정상적으로 삭제되었습니다.");
			} else {
				System.out.println("정상적으로 삭제되지 않았습니다.");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
	}
	
	
	
	// 단건조회 - 죄수번호
	/*public Offender selectOne (int prisonNum) {
		Offender off = null;
		
		try {
			connect();
			String sql = "select * from offenders where prison_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prisonNum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				off = new Offender();
				
				off.setPrisonNum(rs.getInt("prison_num"));
				off.setName(rs.getString("name"));
				off.setGender(rs.getString("gender"));
				off.setBirth(rs.getDate("birth"));
				off.setCrime(rs.getString("location"));
				off.setCrime(rs.getString("crime"));
				off.setImprison(rs.getDate("imprison"));
				off.setSentence(rs.getLong("sentence"));
				off.setReleased(rs.getDate("released"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		return off;
		
	}*/

}
