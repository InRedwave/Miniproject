package teamproject.comProject.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import teamproject.comProject.DTO.Order_Detail;
import teamproject.comProject.DTO.Product_Detail;
import teamproject.comProject.server.ConnectionProvider;

public class ProductDetailDao {
	public List<Product_Detail> selectProductDetails(String product_Id) {
		PreparedStatement pstmt;
		Connection conn = ConnectionProvider.getConnection();
		
		String sql = "select d.detail_color ,d.detail_capacity,"
				+ "d.detail_qnt,d.detail_id,p.product_id from product p, product_detail d "
				+ "where p.product_id=d.product_product_id " + "and p.product_id= '" + product_Id + "' "
				+ "and d.detail_qnt!=0 " + "order by d.detail_color";

		List<Product_Detail> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Product_Detail pd = new Product_Detail();
				pd.setDetail_Id(rs.getString("detail_id"));
				pd.setDetail_Color(rs.getString("detail_color"));
				pd.setDetail_Capacity(rs.getString("detail_capacity"));
				pd.setDetail_Qnt(rs.getInt("detail_qnt"));
				pd.setProduct_Id(rs.getString("product_id"));
				list.add(pd);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionProvider.exit(conn);
		}

		return list;
	}

	public Product_Detail selectProductDetailOneCol(String detail_id) {
		PreparedStatement pstmt;
		Connection conn = ConnectionProvider.getConnection();
	
		String sql = "select detail_color ,detail_capacity,"
				+ "detail_qnt,detail_id, product_product_id from product_detail "
				+ "where detail_id= ? ";
			

		Product_Detail pd = new Product_Detail();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, detail_id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				pd.setDetail_Id(rs.getString("detail_id"));
				pd.setDetail_Color(rs.getString("detail_color"));
				pd.setDetail_Capacity(rs.getString("detail_capacity"));
				pd.setDetail_Qnt(rs.getInt("detail_qnt"));
				pd.setProduct_Id(rs.getString("product_product_id"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionProvider.exit(conn);
		}

		return pd;
	}

	public boolean updateDetail(Order_Detail od) {
		PreparedStatement pstmt;
		Connection conn = ConnectionProvider.getConnection();
		
		String sql = "update product_detail set detail_qnt=detail_qnt-? where detail_id=? ";

		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, od.getProduct_Qnt());
			pstmt.setString(2, od.getProduct_Detail_Id());
			
			

			return pstmt.execute() ;
				

			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionProvider.exit(conn);
		}

		return false;
	}
}
