package com.yedam.app.offender;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.app.common.DAO;

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
			String sql = "INSERT INTO OFFENDERS VALUES (OFFENDERS_SEQ.NEXTVAL,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, offender.getName());
			pstmt.setString(2, offender.getGender());
			pstmt.setString(3, offender.getBirth());
			pstmt.setString(4, offender.getLocation());
			pstmt.setString(5, offender.getCrime());
			pstmt.setString(6, offender.getImprison());
			pstmt.setString(7, offender.getSentence());
			pstmt.setString(8, offender.getReleased()); //석방일을 투옥일에서 형량 더한값으로 나오게 하는 방법
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
	}
	
	
	// 수정 - 형량
	public void updateSentence(Offender offender) {
		
		try {
			connect();				//문장 끝날때 마다 공백 신경쓰기
			String sql = "UPDATE OFFENDERS SET SENTENCE = ? WHERE PRISON_NUM = ?";
			pstmt.setString(1, offender.getSentence());
			pstmt.setInt(2, offender.getPrisonNum());
			
			int result = pstmt.executeUpdate();
			if (result>0) {
				System.out.println("형량이 수정되었습니다.");
			}else {
				System.out.println("형량이 정상적으로 수정되지 않았습니다.");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
	}
	
	
	// 수정 - 주소
	public void updateLocation(Offender offender) {
		
		try {
			connect();				//문장 끝날때 마다 공백 신경쓰기
			String sql = "UPDATE OFFENDERS SET Location = ? WHERE PRISON_NUM = ?";
			pstmt.setString(1, offender.getLocation());
			pstmt.setInt(2, offender.getPrisonNum());
			
			int result = pstmt.executeUpdate();
			if (result>0) {
				System.out.println("주소가 수정되었습니다.");
			}else {
				System.out.println("주소가 정상적으로 수정되지 않았습니다.");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
	}
	 
	/*
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
	*/
	
	// 단건조회 - 죄수번호
	public Offender selectOne (int prisonNum) {
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
				off.setBirth(rs.getString("birth"));
				off.setCrime(rs.getString("location"));
				off.setCrime(rs.getString("crime"));
				off.setImprison(rs.getString("imprison"));
				off.setSentence(rs.getString("sentence"));
				off.setReleased(rs.getString("released"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		return off;
		
	}
	
	// 단건조회 - 투옥중인사람
	
	
	
	
	
	// 단건조회 - 출소한사람
	
	
	//전체조회 - 지역
	public List<Offender> selectAll(String location) {
		List<Offender> list = new ArrayList<>();
	
		try {
			connect();
			String sql = "select * "+ "from v_crime "+ "where location =" + "'"+location+"'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Offender offender = new Offender();
				offender.setPrisonNum(rs.getInt("prison_num"));
				offender.setName(rs.getString("name"));
				offender.setGender(rs.getString("gender"));
				offender.setBirth(rs.getNString("birth"));
				offender.setCrime(rs.getString("crime"));
				offender.setReleased(rs.getString("released"));	
			
			list.add(offender);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
		return list;
	}
}
