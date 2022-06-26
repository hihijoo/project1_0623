package com.yedam.app.member;

import com.yedam.app.common.DAO;

public class LoginDAO extends DAO {

	
	//싱글톤
	private static LoginDAO lDAO = null;
	private LoginDAO() {}
	public static LoginDAO getInstance() {
		if(lDAO == null) {
			lDAO = new LoginDAO();
		}
		return lDAO;
	}
	
	
	
	
	
	
	
}
