package com.yedam.app.member;

import java.sql.SQLException;


import com.yedam.app.common.DAO;

public class MemberDAO extends DAO {

	
	//싱글톤
	private static MemberDAO mDAO = null;
	private MemberDAO() {}
	public static MemberDAO getInstance() {
		if(mDAO == null) {
			mDAO = new MemberDAO();
		}
		return mDAO;

	}
	
	
	//회원가입 
		public void register(Member member) {	
			try {
				connect();
				String sql = "INSERT INTO MEMBERS (member_id,member_password)VALUES (?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,member.getMemberId());
				pstmt.setString(2, member.getMemberPassword());
				
				int result = pstmt.executeUpdate();
				
				if (result > 0) {
					System.out.println("정상적으로 가입되었습니다.");
				} else {
					System.out.println("정상적으로 가입되지 않았습니다.");
				}
		
			} catch (SQLException e) {
				e.printStackTrace();	
			}finally {
				disconnect();
			}
		}
		
	//아이디 존재여부
		public Member selectId(String memberId) {
			Member id = null;
			try {
				connect();
				String sql = "select * from members "+ "where member_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, memberId);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					id = new Member();
					id.setMemberId(rs.getString("member_id"));
					id.setMemberPassword(rs.getNString("member_password"));
					id.setMemberRole(rs.getInt("member_role"));
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				disconnect();
			}	
			return id;
			
		}
	
	// 2.로그인
	public Member selectOne(Member member) {
		Member loginInfo = null;
		try {
			connect();		//아이디때문에 로그인이 안되는지 비밀번호 때문에 로그인이 안되는지 구분하기 위해서 나눔
			String sql = "select * from members where member_id = '" + member.getMemberId()+"'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) { //실행되면 아이디는 존재함	
								
				if(rs.getString("member_password").equals(member.getMemberPassword())) {
					//비밀번호도 같은지 확인해야함
					// 로그인 성공
					loginInfo = new Member();
					loginInfo.setMemberId(rs.getString("member_id"));
					loginInfo.setMemberPassword(rs.getString("member_password"));
					loginInfo.setMemberRole(rs.getInt("member_role"));
					loginInfo.setMemberName(rs.getString("member_name"));
				}else {
					System.out.println("비밀번호가 일치하지 않습니다.");
				}
			}else {
				//아이디가 존재하지 않음
				System.out.println("아이디가 존재하지 않습니다.");
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
		return loginInfo;
	}
	
	
		
	
}
