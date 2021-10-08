package co.yedam.to_do_list;

import java.sql.SQLException;

public class To_do_listDAO extends DAO {
	
	private static To_do_listDAO singleton = new To_do_listDAO() ;
	
	private To_do_listDAO () {
		
	}
	
	public static To_do_listDAO getInstance () {
		return singleton ;
	}
	
	// 일단 void 하고 나중에 수정
	public To_do_list insertToDo(To_do_list to_to_list) {
		connect() ;
		int currId = 0 ;
		try {
			conn.setAutoCommit(false) ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt = conn.createStatement() ;
			rs = stmt.executeQuery("select value from num where name='todo'") ;
			if(rs.next()) {
				currId = rs.getInt("value") ;
			}
			currId++ ;
			psmt = conn.prepareStatement("insert into comments values(?,?)") ;
			psmt.setInt(1, currId) ;
			psmt.setString(2, to_to_list.getDotext()) ;
			int r = psmt.executeUpdate() ;
			System.out.println(r + "건 입력") ;
			
			psmt = conn.prepareStatement("update num set value=? where name='todo'") ;
			psmt.setInt(1, currId) ;
			r = psmt.executeUpdate() ;
			System.out.println("Num " + r + "건 변경") ;
			
			conn.commit() ;
			to_to_list.setId(String.valueOf(currId)) ;
			return to_to_list ;
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
			conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace() ;
			}
			return null ;
		} finally {
			disconnect() ;
		}
	}

}
