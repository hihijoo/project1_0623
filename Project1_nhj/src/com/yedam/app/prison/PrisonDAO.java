package com.yedam.app.prison;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.app.common.DAO;

public class PrisonDAO extends DAO {

	// 싱글톤
	private static PrisonDAO dao = null;

	private PrisonDAO() {
	}

	public static PrisonDAO getInstance() {
		if (dao == null) {
			dao = new PrisonDAO();
		}
		return dao;
	}

	// 등록
	public void insert(Prison prison) {
		try {
			connect();
			String sql = "INSERT INTO PRISON (prison_name, prison_location, prison_accommodate, prison_occupy) VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, prison.getPrisonName());
			pstmt.setString(2, prison.getPrisonLocation());
			// 현재 교도소 최대 수용인원
			pstmt.setInt(3, prison.getPrisonAccommodate()); // 30명
			// 현재 교도소 수용가능인원(최대 수용인원 - 수용중인 인원)
			pstmt.setInt(4, prison.getPrisonAccommodate()); // 30명
			// pstmt.setInt(4,
			// prison.getPrisonAccommodate()-selectInfo(prison.getPrisonLocation()));

			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("정상적으로 등록되었습니다.");
			} else {
				System.out.println("정상적으로 등록되지 않았습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();

		}
	}

	// 전체조회
	public List<Prison> selectAll() {
		List<Prison> list = new ArrayList<>();
		try {
			connect();
			String sql = "select * from prison order by prison_location";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Prison prison = new Prison();
				prison.setPrisonName(rs.getString("prison_name"));
				prison.setPrisonLocation(rs.getString("prison_location"));
				prison.setPrisonAccommodate(rs.getInt("prison_accommodate"));
				// int count = rs.getInt("prison_accommodate");
				// System.out.println(count);

				prison.setPrisonOccupy(rs.getInt("prison_occupy"));

				list.add(prison);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;

	}

	// 전체 조회 - 교도소 지역
	public Prison selectLocation(String prisonLocation) {
		Prison prison = null;
		try {
			connect();
			String sql = "SELECT * FROM PRISON WHERE PRISON_LOCATION = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, prisonLocation);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				prison = new Prison();
				prison.setPrisonName(rs.getString("prison_name"));
				prison.setPrisonLocation(rs.getString("prison_location"));
				prison.setPrisonAccommodate(rs.getInt("prison_accommodate"));
				prison.setPrisonOccupy(rs.getInt("prison_occupy"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return prison;
	}

	// 전체 조회 - 교도소 이름
	public Prison selectName(String prisonName) {
		Prison prison = null;
		try {
			connect();
			String sql = "SELECT * FROM PRISON WHERE PRISON_NAME = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, prisonName);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				prison = new Prison();
				prison.setPrisonName(rs.getString("prison_name"));
				prison.setPrisonLocation(rs.getString("prison_location"));
				prison.setPrisonAccommodate(rs.getInt("prison_accommodate"));
				prison.setPrisonOccupy(rs.getInt("prison_occupy"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return prison;
	}

	// 수용 중인 인원
	public int selectInfo(String location) {
		int amount = 0;
		try {
			connect();
			String sql = "SELECT COUNT(*) as count " + "FROM offenders " + "WHERE LOCATION = " + "'" + location + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				amount = rs.getInt("count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();

		}

		return amount;
	}
	
	// count로 where freedom = '가석방' and location = ? 가석방된 인간을 뽑아내서 where location넣어서
	// 그것을 managementprison의 selectAll에서 수용가능인원 계산하는 것에서 + 한다
	// 수정 - 최대수용인원 조절
	public int selectFreedom(String location) {
		int freedom = 0;
		try {
			connect();
			String sql = "SELECT COUNT(*) AS freedom from offenders where freedom='가석방' and location = "+"'"+location+"'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
					
			if(rs.next()) {
				freedom = rs.getInt("freedom");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		return freedom;
	}
	
	public void updatePrison(Prison prison) {

		try {
			connect(); // 문장 끝날때 마다 공백 신경쓰기
			String sql = "UPDATE PRISON SET Prison_accommodate = ?, prison_occupy = ? WHERE PRISON_location = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prison.getPrisonAccommodate()); // 15명 최대 수용가능인원
			pstmt.setInt(2, prison.getPrisonOccupy());
			pstmt.setString(3, prison.getPrisonLocation());

			int result = pstmt.executeUpdate();

			if (result > 0) {

			} else {
				System.out.println("정상적으로 인원이 수정되지 않았습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}

	// 수정 - 최대수용인원 조절
	public void updateAccommodate(Prison prison) {

		try {
			connect(); // 문장 끝날때 마다 공백 신경쓰기
			String sql = "UPDATE PRISON SET Prison_accommodate = ? WHERE PRISON_location = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prison.getPrisonAccommodate()); // 15명 최대 수용가능인원
			pstmt.setString(2, prison.getPrisonLocation());

			int result = pstmt.executeUpdate();

			if (result > 0) {

			} else {
				System.out.println("정상적으로 인원이 수정되지 않았습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}

	// 수정 - 수용중인 조절
	public void updateOccupy(Prison prison) {

		try {
			connect(); // 문장 끝날때 마다 공백 신경쓰기
			String sql = "UPDATE PRISON SET Prison_occupy = ? WHERE PRISON_location = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prison.getPrisonOccupy()); // 15명 최대 수용가능인원
			pstmt.setString(2, prison.getPrisonLocation());

			/*int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("2정상적으로 인원이 수정되었습니다.");

			} else {
				System.out.println("2정상적으로 인원이 수정되지 않았습니다.");
			}*/
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}

	// 수정 - 교도소 이름
	public void updateName(Prison prison) {

		try {
			connect(); // 문장 끝날때 마다 공백 신경쓰기
			String sql = "UPDATE PRISON SET Prison_name = ? where prison_location = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, prison.getPrisonName());
			pstmt.setString(2, prison.getPrisonLocation());

			int result = pstmt.executeUpdate();

			if (result > 0) {
				
			} else {
				System.out.println("정상적으로 이름이 수정되지 않았습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}

}
