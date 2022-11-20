package teamproject.comProject.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import teamproject.comProject.DTO.Basket;
import teamproject.comProject.DTO.Basket_Detail;
import teamproject.comProject.server.ConnectionProvider;

public class BasketDao {
	String Output;

	public String Create (Basket basket) {
		String sql = "insert into basket (users_user_id)  values  ( ? ) ";
		Connection conn = ConnectionProvider.getConnection();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, basket.getUser_Id());
			pstmt.executeUpdate();
			pstmt.close();
			Output = "success";
			
		} catch (SQLException e) {
			Output = "fail";
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Output;
	}
	public Basket updateBasket(String user_id, Basket_Detail bd) {
		PreparedStatement pstmt;
		Connection conn = ConnectionProvider.getConnection();
		String sql="update basket set total_price=total_price+? ";
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bd.getPrice());
			if(pstmt.executeUpdate()==1) {
				return selectBasket(user_id);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionProvider.exit(conn);
		}
		return null;
	}

	public Basket selectBasket(String basket_id) {
		Basket basket = new Basket();
		PreparedStatement pstmt;
		Connection conn = ConnectionProvider.getConnection();
		String sql="select users_user_id, total_price from basket where users_user_id=?";
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, basket_id);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				basket.setUser_Id(rs.getString("users_user_id"));
				basket.setTotal_price(rs.getInt("total_price"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionProvider.exit(conn);
		}
		return basket;
	}

}
