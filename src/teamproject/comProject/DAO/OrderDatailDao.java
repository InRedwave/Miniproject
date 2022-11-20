package teamproject.comProject.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import teamproject.comProject.server.ConnectionProvider;

public class OrderDatailDao {

	public Boolean insertOrderDetail(String order_id, int qnt, int price, String product_detail_id) {
		Connection conn=ConnectionProvider.getConnection();
		String sql ="insert into order_detail (ORDER_DETAIL_ID,ORDERS_ORDER_ID,PRODUCT_QNT,PRICE,PRODUCT_DETAIL_DETAIL_ID)"
				+"values('OrdD'||order_detail_id.nextval,?,?,?,?)";
		Boolean result=true;
				try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, order_id);
			pstmt.setInt(2, qnt);
			pstmt.setInt(3, price);
			pstmt.setString(4, product_detail_id);
			if(pstmt.executeUpdate()!=1) {
				result=false;
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=false;
		}finally {
			ConnectionProvider.exit(conn);
		}
		return result;
		
	}

}
