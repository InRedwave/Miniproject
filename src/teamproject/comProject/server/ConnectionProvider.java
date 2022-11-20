package teamproject.comProject.server;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionProvider {
	/*
	public static Connection getConnect() {
		// db연결
		Connection conn = null;
		try {

			Class.forName("oracle.jdbc.OracleDriver");
			// 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@kosa402.iptime.org:50051/orcl", "team5", "oracle");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return conn;
	}

	public static void exit(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}
	*/
	private static BasicDataSource dataSource;
	static {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@kosa402.iptime.org:50051/orcl");
		dataSource.setUsername("team5");
		dataSource.setPassword("oracle");
		dataSource.setMaxTotal(5);
		dataSource.setInitialSize(1);
		dataSource.setMaxIdle(1);
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void main(String[] args) {
		Connection conn = null;
		try {
			//Connection 대여
			conn = ConnectionProvider.getConnection();
			System.out.println("대여 성공");
		} catch(Exception e) {
			
		} finally {
			try {
				//Connection 반납
				conn.close();
				System.out.println("반납 성공");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void exit(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}
	
	
}
