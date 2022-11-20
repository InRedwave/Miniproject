package teamproject.comProject.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import teamproject.comProject.DTO.Basket_Detail;
import teamproject.comProject.server.ConnectionProvider;

public class BasketDetailDao {

	public Basket_Detail insertBasketPro(String user_id, String product_id, int qnt,int price) {
		PreparedStatement pstmt;
		Connection conn = ConnectionProvider.getConnection();
		Basket_Detail basket_Detail=new Basket_Detail();
		try {
			
			String sql ="insert into basket_detail(basket_detail_id,PRODUCT_DETAIL_DETAIL_ID ,basket_users_user_id, price,PRODUCT_QNT ) "
					+ "values('basD'||basket_detail_id.nextval,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  product_id);
			pstmt.setString(2, user_id);
			pstmt.setInt(3,price);
			pstmt.setInt(4,qnt);
			if(pstmt.executeUpdate()==1) {
				sql="select rownum,basket_detail_id from (select * from basket_detail order by rownum desc) "
						+ "where rownum=1 and basket_users_user_id= ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user_id);
				
				ResultSet rs=pstmt.executeQuery();
				if(rs.next()) {
					basket_Detail.setBasket_Detail_Id(rs.getString("basket_detail_id"));
					
				}else {
					return basket_Detail;
				}
				basket_Detail.setProduct_Detail_Id(product_id);
				basket_Detail.setUser_Id(user_id);
				basket_Detail.setPrice(price);
				basket_Detail.setProduct_Qnt(qnt);
			}
				
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			ConnectionProvider.exit(conn);
		}
		return basket_Detail;
	}

	public JSONArray selectBasketDetails(String user_id) {
		PreparedStatement pstmt;
		Connection conn = ConnectionProvider.getConnection();
		JSONArray list = new JSONArray();
		String sql="select p.product_name, d.detail_color, d.detail_capacity, b.price, b.product_qnt, (b.price*b.product_qnt )as sum, b.basket_detail_id ,d.detail_id "
				+ "from product p, product_detail d, basket_detail b "
				+ "where p.product_id=d.product_product_id and b.product_detail_detail_id=d.detail_id and b.basket_users_user_id=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, user_id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				JSONObject bd=new JSONObject();
				bd.put("product_name", rs.getString("product_name"));
				bd.put("detail_color", rs.getString("detail_color"));
				bd.put("detail_capacity", rs.getString("detail_capacity"));
				bd.put("price", rs.getInt("price"));
				bd.put("product_qnt", rs.getInt("product_qnt"));
				bd.put("sum_price", rs.getInt("sum"));
				bd.put("basket_detail_id", rs.getString("basket_detail_id"));
				bd.put("product_detail_id", rs.getString("detail_id"));
				list.put(bd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionProvider.exit(conn);
		}
		return list;
	}

	public boolean deleteBasketDetail(String user_id) {
		PreparedStatement pstmt;
		Connection conn = ConnectionProvider.getConnection();
		boolean result=false;
		try {
			
			String sql ="delete from basket_detail where basket_users_user_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id);
			
			if(pstmt.executeUpdate()!=0) {
				
				result=true;
				
			}
				
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			ConnectionProvider.exit(conn);
		}
		return result;
		
	}
	
}
