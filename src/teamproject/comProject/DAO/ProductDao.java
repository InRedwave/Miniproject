package teamproject.comProject.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import teamproject.comProject.DTO.Pager;
import teamproject.comProject.DTO.Product;
import teamproject.comProject.server.ConnectionProvider;

public class ProductDao {

	public List<Product> selectProducts(Pager page) {
		PreparedStatement pstmt;
		Connection conn = ConnectionProvider.getConnection();
		String sql = "select rnum ,  product_id, product_name, product_price from ("
				+ "select rownum as rnum, product_id, product_name, product_price from product where rownum <=? "
				+ ")where rnum >=? ";
		List<Product> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, String.valueOf(page.getEndRowNo()));
			pstmt.setString(2, String.valueOf(page.getStartRowNo()));
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Product pro = new Product();

				pro.setProduct_Id(rs.getString("product_id"));
				pro.setProduct_Name(rs.getString("product_name"));
				pro.setProduct_Price(rs.getInt("product_price"));
				list.add(pro);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionProvider.exit(conn);
		}

		return list;
	}
public List<Product> selectProducts(Pager page, String searchOption) {
	PreparedStatement pstmt;
	Connection conn = ConnectionProvider.getConnection();
	String sql = "select rnum ,  product_id, product_name, product_price from ("
			+ "select rownum as rnum, product_id, product_name, product_price from product where rownum <=? and product_name like '%'||?||'%'"
			+ ")where rnum >=? ";
	List<Product> list = new ArrayList<>();
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, String.valueOf(page.getEndRowNo()));
		pstmt.setString(2, searchOption);
		pstmt.setString(3, String.valueOf(page.getStartRowNo()));
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Product pro = new Product();

			pro.setProduct_Id(rs.getString("product_id"));
			pro.setProduct_Name(rs.getString("product_name"));
			pro.setProduct_Price(rs.getInt("product_price"));
			list.add(pro);

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		ConnectionProvider.exit(conn);
	}

	return list;
	}
	public int selectCountProduct() {
		PreparedStatement pstmt;
		Connection conn = ConnectionProvider.getConnection();
		String sql = "select count(product_id)as count from product";

		try {
			pstmt = conn.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				return rs.getInt("count");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionProvider.exit(conn);
		}

		return 0;
	}

	public Product selectProduct(String product_Id) {
		PreparedStatement pstmt;
		Connection conn = ConnectionProvider.getConnection();
		Product product = new Product();
		try {
			String sql = "select product_id, product_name,product_price,product_content,"
					+ "product_graphic_card,product_cpu,product_mainboard,product_os,product_memory "
					+ "from product where product_id= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product_Id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				product.setProduct_Id(rs.getString("product_id"));
				product.setProduct_Name(rs.getString("product_name"));
				product.setProduct_Price(rs.getInt("product_price"));
				product.setProduct_Content(rs.getString("product_content"));
				product.setProduct_Graphic_Card(rs.getString("product_graphic_card"));
				product.setCPU(rs.getString("product_cpu"));
				product.setMainboard(rs.getString("product_mainboard"));
				product.setOS(rs.getString("product_os"));
				product.setMemory(rs.getString("product_memory"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.exit(conn);
		}

		return product;
	}

	public Product selectProductSubQuery(String productDetail_id) {
		PreparedStatement pstmt;
		Connection conn = ConnectionProvider.getConnection();
		Product product = new Product();
		try {
			String sql = "select product_id, product_name,product_price,product_content,"
					+ "product_graphic_card,product_cpu,product_mainboard,product_os,product_memory "
					+ "from product where product_id= (select product_product_id from product_detail where detail_id= ? )";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productDetail_id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				product.setProduct_Id(rs.getString("product_id"));
				product.setProduct_Name(rs.getString("product_name"));
				product.setProduct_Price(rs.getInt("product_price"));
				product.setProduct_Content(rs.getString("product_content"));
				product.setProduct_Graphic_Card(rs.getString("product_graphic_card"));
				product.setCPU(rs.getString("product_cpu"));
				product.setMainboard(rs.getString("product_mainboard"));
				product.setOS(rs.getString("product_os"));
				product.setMemory(rs.getString("product_memory"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.exit(conn);
		}

		return product;
	}

	

}
