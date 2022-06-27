package com.yedam.app.management;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.app.common.DAO;

public class ManagementDAO extends DAO{

	//싱글톤
	private static ManagementDAO dao = null;
	private ManagementDAO() {}
	public static ManagementDAO getInstance() {
		if(dao == null) {
			dao = new ManagementDAO();
		}
		return dao;
	}
	
	
	//조회 - 죄수번호 -> 만약 동명이인이 있을 경우 죄수번호 확인 위해서 (무조건 한명이 나옴)
	public Management selectPrisonNum(int PrisonNum) {
		Management prisonNum = null;
		try {
			connect();
			String sql = "select * from v_offenderInfo where Prison_Num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, PrisonNum);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				prisonNum = new Management();
				prisonNum.setPrisonName(rs.getString("prison_name"));
				prisonNum.setPrisonLocation(rs.getString("prison_location"));
				prisonNum.setPrisonNum(rs.getInt("prison_num"));
				prisonNum.setName(rs.getString("name"));
				prisonNum.setGender(rs.getString("gender"));
				prisonNum.setBirth(rs.getDate("birth"));
				prisonNum.setCrime(rs.getString("crime"));
				prisonNum.setImprison(rs.getDate("imprison"));
				prisonNum.setSentence(rs.getLong("sentence"));
				prisonNum.setReleased(rs.getDate("released"));
				prisonNum.setFreedom(rs.getString("freedom"));
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		return prisonNum;
	}
	
	//조회 - 이름 -> 만약 동명이인이 있을 경우 죄수번호 확인 위해서
	public List<Management> selectName(String name) {
		List<Management> list = new ArrayList<>();
		
		try {
			connect();
			String sql = "select * from v_offenderInfo where name =" + "'"+name+"'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				Management info = new Management();
				info.setPrisonName(rs.getString("prison_name"));
				info.setPrisonLocation(rs.getString("prison_location"));
				info.setPrisonNum(rs.getInt("prison_num"));
				info.setName(rs.getString("name"));
				info.setGender(rs.getString("gender"));
				info.setBirth(rs.getDate("birth"));
				info.setCrime(rs.getString("crime"));
				info.setImprison(rs.getDate("imprison"));
				info.setSentence(rs.getLong("sentence"));
				info.setReleased(rs.getDate("released"));
				info.setFreedom(rs.getString("freedom"));
				
				list.add(info);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		return list;
	}
	
	
	//전체 조회 - 투옥중인 사람
		public List<Management> selectImprison(String freedom){
			List<Management> list = new ArrayList<>();
			
			try {
				connect();
				String sql ="SELECT * FROM v_offenderInfo where freedom = "+ "'"+freedom+"'";
				stmt = conn.createStatement();
				rs=stmt.executeQuery(sql);
				
				while (rs.next()){
					Management info = new Management();
					info.setPrisonName(rs.getString("prison_name"));
					info.setPrisonLocation(rs.getString("Prison_location"));
					info.setPrisonNum(rs.getInt("prison_num"));
					info.setName(rs.getString("name"));
					info.setGender(rs.getString("gender"));
					info.setBirth(rs.getDate("birth"));
					info.setCrime(rs.getString("crime"));
					info.setImprison(rs.getDate("imprison"));
					info.setSentence(rs.getLong("sentence"));
					info.setReleased(rs.getDate("released"));
					info.setFreedom(rs.getString("freedom"));
					
					list.add(info);
				}
				
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				disconnect();
			}

			return list;
		}
	
	
	
	
	// 전체조회 - 출소한사람
		public List<Management> selectFreedom(String freedom){
			List<Management> list = new ArrayList<>();
			
			try {
				connect();
				String sql ="SELECT * FROM v_offenderInfo where freedom =" + "'"+freedom+"'";
				stmt = conn.createStatement();
				rs=stmt.executeQuery(sql);
				
				while (rs.next()){
					Management info = new Management();
					info.setPrisonName(rs.getString("prison_name"));
					info.setPrisonLocation(rs.getString("Prison_location"));
					info.setPrisonNum(rs.getInt("prison_num"));
					info.setName(rs.getString("name"));
					info.setGender(rs.getString("gender"));
					info.setBirth(rs.getDate("birth"));
					info.setCrime(rs.getString("crime"));
					info.setImprison(rs.getDate("imprison"));
					info.setSentence(rs.getLong("sentence"));
					info.setReleased(rs.getDate("released"));
					info.setFreedom(rs.getString("freedom"));
					
					list.add(info);
				}
				
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				disconnect();
			}

			return list;
		}
	
	
	
	
	//전체조회 - 지역별 죄수들(유저들이 보는것)
	public List<Management> selectLocation(String location) {
		List<Management> list = new ArrayList<>();
	
		try {
			connect();
			String sql = "select * "+ "from v_crime "+ "where prison_location =" + "'"+location+"'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Management info = new Management();
				info.setPrisonNum(rs.getInt("prison_num"));
				info.setName(rs.getString("name"));
				info.setGender(rs.getString("gender"));
				info.setBirth(rs.getDate("birth"));
				info.setCrime(rs.getString("crime"));
				info.setPrisonName(rs.getString("prison_name"));
				info.setPrisonLocation(rs.getString("location"));
				info.setFreedom(rs.getString("freedom"));	
			
				list.add(info);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
		return list;
	}
	//전체 조회(distinct) - 유저
	public List<Management> distinctUser() {
		List<Management> list = new ArrayList<>();
	
		try {
			connect();
			String sql = "SELECT distinct prison_location FROM v_crime group by prison_location";
					
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Management info = new Management();
				info.setPrisonNum(rs.getInt("prison_num"));
				info.setName(rs.getString("name"));
				info.setGender(rs.getString("gender"));
				info.setBirth(rs.getDate("birth"));
				info.setCrime(rs.getString("crime"));
				info.setPrisonName(rs.getString("prison_name"));
				info.setPrisonLocation(rs.getString("prison_location"));
				info.setFreedom(rs.getString("freedom"));	
			
				list.add(info);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
		return list;
	}
	
	//전체 조회 - 지역별(관리자가 보는것)
	public List<Management> selectPrisonLocation(String location) {
		List<Management> list = new ArrayList<>();
	
		try {
			connect();
			String sql = "select * "+ "from v_offenderInfo "+ "where prison_location =" + "'"+location+"'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()){
				Management info = new Management();
				info.setPrisonName(rs.getString("prison_name"));
				info.setPrisonLocation(rs.getString("Prison_location"));
				info.setPrisonNum(rs.getInt("prison_num"));
				info.setName(rs.getString("name"));
				info.setGender(rs.getString("gender"));
				info.setBirth(rs.getDate("birth"));
				info.setCrime(rs.getString("crime"));
				info.setImprison(rs.getDate("imprison"));
				info.setSentence(rs.getLong("sentence"));
				info.setReleased(rs.getDate("released"));
				info.setFreedom(rs.getString("freedom"));
				
				list.add(info);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
		return list;
	}
	
	//전체 조회 
	public List<Management> selectAll(){
		List<Management> list = new ArrayList<>();
		
		try {
			connect();
			String sql ="SELECT * FROM v_offenderInfo";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()){
				Management info = new Management();
				info.setPrisonName(rs.getString("prison_name"));
				info.setPrisonLocation(rs.getString("Prison_location"));
				info.setPrisonNum(rs.getInt("prison_num"));
				info.setName(rs.getString("name"));
				info.setGender(rs.getString("gender"));
				info.setBirth(rs.getDate("birth"));
				info.setCrime(rs.getString("crime"));
				info.setImprison(rs.getDate("imprison"));
				info.setSentence(rs.getLong("sentence"));
				info.setReleased(rs.getDate("released"));
				info.setFreedom(rs.getString("freedom"));
				
				list.add(info);
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}

		return list;
	}
	
	//전체 조회 - DISTINCT를 위해서 필요함
	public List<Management> distinctLocation(){
		List<Management> list = new ArrayList<>();
		
		try {
			connect();
			String sql ="SELECT prison_name, distinct Prison_location, "
					+ "prison_num, name, gender, birth, crime, imprison,sentence,"
					+ "released,freedom FROM v_offenderInfo";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()){
				Management info = new Management();
				info.setPrisonName(rs.getString("prison_name"));
				info.setPrisonLocation(rs.getString("Prison_location"));
				info.setPrisonNum(rs.getInt("prison_num"));
				info.setName(rs.getString("name"));
				info.setGender(rs.getString("gender"));
				info.setBirth(rs.getDate("birth"));
				info.setCrime(rs.getString("crime"));
				info.setImprison(rs.getDate("imprison"));
				info.setSentence(rs.getLong("sentence"));
				info.setReleased(rs.getDate("released"));
				info.setFreedom(rs.getString("freedom"));
				
				list.add(info);
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}

		return list;
	}

	//수정 freedom
	public void updateFreedom(Management management) {
		try {
			connect();				//문장 끝날때 마다 공백 신경쓰기
			String sql = "UPDATE OFFENDERS SET freedom = ? WHERE prison_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, management.getFreedom());
			pstmt.setInt(2, management.getPrisonNum());
			
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
	}
	
	
	
}
