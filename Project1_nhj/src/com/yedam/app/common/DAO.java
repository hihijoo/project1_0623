package com.yedam.app.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DAO {
	
	//ORACLE DB 정보
		private String jdbcDriver="oracle.jdbc.driver.OracleDriver";
		private String oracleUrl="jdbc:oracle:thin:@localhost:1521:xe";
		private String connectedId ="crime";
		private String connectedPwd="crime";
		
		//공통으로 사용되는 필드 - 
		protected Connection conn;
		protected Statement stmt;
		protected PreparedStatement pstmt;
		protected ResultSet rs;
	
		
		//한번만 dbconfig를 실행시키고자 한다면 빼내야함
		public DAO() {
			//dbConfig();
		}
		
		//db에 접속하는 메소드
		public void connect() {
			
			try {
				Class.forName(jdbcDriver);
				conn = DriverManager.getConnection(oracleUrl,connectedId, connectedPwd);
				
			}catch(ClassNotFoundException e) {
				System.out.println("jdbc driver 로딩 실패");
			}catch(SQLException e) {
				System.out.println("DB 연결 실패");
			}
			
		}
		
		//db 정보를 가져오는 메소드
/*
		private void dbConfig() { // 파일에 저장된것을 가져옴, 파일값만 바꾸면 되니까 편리함
			String resource = "config/db.properties";
			Properties properties = new Properties();
			
			try {                //class 정보를 가져오는것, 지금실행되는 클래스의 정보, 접근위치 넘겨 자원찾기, 경로를 가져옴
				String filePath = ClassLoader.getSystemClassLoader().getResource(resource).getPath();
				properties.load(new FileInputStream(filePath)); //파일 읽어들이는 방식
				
			}catch(IOException e) {
				e.printStackTrace();
			}
			//map에 key과 value 의 형태
			jdbcDriver = properties.getProperty("driver");
			oracleUrl = properties.getProperty("url");
			connectedId = properties.getProperty("id");
			connectedPwd = properties.getProperty("password");
		}

		*/
		//db 접속을 해제하는 메소드
		public void disconnect() {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(stmt != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		
		}
}
