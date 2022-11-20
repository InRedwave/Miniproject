package teamproject.comProject.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import teamproject.comProject.DTO.Free_Board;
import teamproject.comProject.DTO.Pager;
import teamproject.comProject.DTO.Review_Board;
import teamproject.comProject.server.ConnectionProvider;

public class DAO_Board {
	String Output;
	int countRow;
	int totalRow;
	Pager pager = new Pager();
	
	public String Insert(Free_Board free_Board) {
		String sql = "insert into free_board (free_bno, free_btitle, free_bcontent, free_date, free_comment_num, users_User_Id) "
				+ " values (free_bno.nextval, ?, ?, sysdate, free_conum.nextval, ? ) ";
		Connection conn = ConnectionProvider.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//입력됨.
			pstmt.setString(1, free_Board.getFree_Btitle());
			pstmt.setString(2, free_Board.getFree_Bcontent());
			pstmt.setString(3, free_Board.getUser_Id());
			pstmt.executeUpdate();
			pstmt.close();
			Output = "success";
				
		} catch (Exception e) {
			Output = "fail";
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Output;	
	}
	public Free_Board ReadFb(Free_Board free_Board) {
		String sql = "select Free_Btitle, Free_Bcontent, Free_Date, users_user_id,  free_bno "
				+ "from free_board "
				+ "where free_bno = ? ";
		Free_Board free_Board2 = new Free_Board();
		Connection conn = ConnectionProvider.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, free_Board.getFree_Bno());
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				free_Board2.setFree_Btitle(rs.getString(1));
				free_Board2.setFree_Bcontent(rs.getString(2));
				free_Board2.setFree_Date(rs.getString(3));
				free_Board2.setUser_Id(rs.getString(4));
				free_Board2.setFree_Bno(rs.getString(5));
			}
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return free_Board2;
	}
	
	
	
	
	
	public ArrayList<Free_Board> ReadAll(String readFb) {
		
		ArrayList<Free_Board> list = new ArrayList<>();
		String sql = "select rnum, Free_Btitle, Free_Bcontent, Free_Date, users_user_id,  free_bno "
				+" from ( "
						+" select rownum as rnum, Free_Btitle, Free_Bcontent, Free_Date, users_user_id,  free_bno "
						+" from (select rownum, Free_Btitle, Free_Bcontent, Free_Date, users_user_id,  free_bno "
						+" from free_board " 
						+" order by free_date desc "
						+" ) where rownum <= 5 "
						+" )" 
						+" where rnum >= 1 " ;
		Connection conn = ConnectionProvider.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Free_Board free_Board = new Free_Board();
				free_Board.setFree_Btitle(rs.getString(2));
				free_Board.setFree_Bcontent(rs.getString(3));
				free_Board.setFree_Date(rs.getString(4));
				free_Board.setUser_Id(rs.getString(5));
				free_Board.setFree_Bno(rs.getString(6));
				list.add(free_Board);
			}
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public int getTotalRow() {
		String sql = "select count (*) as count from free_board";
		Connection conn = ConnectionProvider.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				countRow = rs.getInt("count");
			}
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return countRow;
	}
	
	
	public ArrayList<Free_Board> ReadFbpager(Pager pager) {
		
		ArrayList<Free_Board> list = new ArrayList<>();
		String sql = "select rnum, Free_Btitle, Free_Bcontent, Free_Date, users_user_id,  free_bno "
			+" from ( "
					+" select rownum as rnum, Free_Btitle, Free_Bcontent, Free_Date, users_user_id,  free_bno "
					+" from (select Free_Btitle, Free_Bcontent, Free_Date, users_user_id,  free_bno "
					+" from free_board " 
					+" order by free_date desc "
					+" ) where rownum <= ? "
					+" )" 
					+" where rnum >= ? " ;
		Connection conn = ConnectionProvider.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setInt(1, pager.getEndRowNo());
			pstmt.setInt(2, pager.getStartRowNo());

			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Free_Board free_Board = new Free_Board();
				free_Board.setFree_Btitle(rs.getString(2));
				free_Board.setFree_Bcontent(rs.getString(3));
				free_Board.setFree_Date(rs.getString(4));
				free_Board.setUser_Id(rs.getString(5));
				free_Board.setFree_Bno(rs.getString(6));
				
				
				list.add(free_Board);
			}
			
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public ArrayList<Free_Board> readMyPost(Free_Board free_Board, Pager pager) {
	ArrayList<Free_Board> list = new ArrayList<>();
	String sql = "select rnum, Free_Btitle, Free_Bcontent, Free_Date, users_user_id,  free_bno "
			+" from ( "
			+" select rownum as rnum, Free_Btitle, Free_Bcontent, Free_Date, users_user_id,  free_bno "
			+" from (select Free_Btitle, Free_Bcontent, Free_Date, users_user_id,  free_bno "
			+" from free_board " 
			+" order by free_date desc "
			+" ) where rownum <= ? and users_user_id = ? "
			+" )" 
			+" where rnum >= ? " ;
	
	Connection conn = ConnectionProvider.getConnection();
	try {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, pager.getEndRowNo());
		pstmt.setString(2, free_Board.getUser_Id());
		pstmt.setInt(3, pager.getStartRowNo());

		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			Free_Board free_Board1 = new Free_Board();
			free_Board1.setFree_Btitle(rs.getString(2));
			free_Board1.setFree_Bcontent(rs.getString(3));
			free_Board1.setFree_Date(rs.getString(4));
			free_Board1.setUser_Id(rs.getString(5));
			free_Board1.setFree_Bno(rs.getString(6));
			
			list.add(free_Board1);
		}
		
		pstmt.close();
		rs.close();
	} catch (Exception e) {
		e.getStackTrace();
	} finally {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return list;
}
	public int getUser_ID_Board_Count(Free_Board free_Board) {
		String sql = "select count (*) as count from free_board where users_user_id = ? ";
		Connection conn = ConnectionProvider.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, free_Board.getUser_Id());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				countRow = rs.getInt("count");
			}
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return countRow;
	}
	
	
	
	
	
	public String DeleteFb(Free_Board free_Board) {
		String answer = "";
		String sql = "delete from free_board where users_user_id = ? and free_bno = ? ";
		Connection conn = ConnectionProvider.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, free_Board.getUser_Id());
			pstmt.setString(2, free_Board.getFree_Bno());
			pstmt.executeUpdate();
			
			answer = "success";
			
			pstmt.close();
		} catch (Exception e) {
			e.getStackTrace();
			answer = "fail";
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return answer;
	}
		public String ordered(String user_Id) {  
			String sql="select ORDER_DETAIL_ID, PRODUCT_NAME from ORDER_DETAIL, PRODUCT "
					+ "where ORDERS_ORDER_ID in (select ORDER_ID from ORDERS where USER_USER_ID = ? ) "
					+ "and PRODUCT_ID=(select PRODUCT_PRODUCT_ID from PRODUCT_DETAIL where DETAIL_ID=PRODUCT_DETAIL_DETAIL_ID)"; //얘도 필드로
			Connection conn = ConnectionProvider.getConnection();
			JSONObject returnjson = new JSONObject().put("sf", "fail");
			JSONArray ja = new JSONArray();
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user_Id);
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					do {
						returnjson.put("sf", "success");
						JSONObject jo = new JSONObject(); 
						jo.put("ORDER_DETAIL_ID",rs.getString("ORDER_DETAIL_ID"));
						jo.put("PRODUCT_NAME", rs.getString("PRODUCT_NAME"));
						ja.put(jo);
					} while(rs.next());
					returnjson.put("json", ja);
				} 		
			} catch(Exception e) {
				e.printStackTrace();
			}	finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}	
			return returnjson.toString();
		}
		
		public String Insert(Review_Board rb) {
			String sql = "insert into REVIEW_BOARD (review_bno, Review_Btitle,Review_Bcontent, REVIEW_DATE, Review_Rate, USER_USER_ID, ORDER_DETAIL_ORDER_DETAIL_ID, PRODUCT_NAME )  values(REVIEW_BNO.nextval, ? , ? , SYSDATE, ? , ? , ?, ?)";
			Connection conn = ConnectionProvider.getConnection();
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, rb.getReview_Btitle());
				pstmt.setString(2, rb.getReview_Bcontent());
				pstmt.setInt(3, rb.getReview_Rate());
				pstmt.setString(4, rb.getUser_Id());
				pstmt.setString(5, rb.getOrder_Detail_Id());
				pstmt.setString(6, rb.getProduct_Name());
				pstmt.executeUpdate();
				pstmt.close();
				Output = "success";
					
			} catch (Exception e) {
				Output = "fail";
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return Output;	
		}
		
		public int getTRRb() {
			Connection conn = ConnectionProvider.getConnection();
			
			try {
				String sql = "select count(*) from REVIEW_BOARD ";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					totalRow = rs.getInt(1);
				} else totalRow = 0;			
			}catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return totalRow;
		}
		
		public ArrayList<Review_Board> readRb(int pageNo) {
			Connection conn = ConnectionProvider.getConnection();
			ArrayList<Review_Board> list = new ArrayList<>();
			try {
				getTRRb();			
				 String sql = "select REVIEW_BNO, REVIEW_BTITLE, REVIEW_BCONTENT, REVIEW_DATE, REVIEW_RATE, USER_USER_ID, PRODUCT_NAME from ("
					+ "	select rownum as rnum, REVIEW_BNO,  REVIEW_BTITLE, REVIEW_BCONTENT, REVIEW_DATE, REVIEW_RATE, USER_USER_ID, PRODUCT_NAME from REVIEW_BOARD where rownum <= ?"
					+ "	order by REVIEW_BNO) where rnum >= ? ";
				Pager page = new Pager(5, 5, totalRow, pageNo);
				//page.setCurrPage(pageNo);
			
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, page.getEndRowNo());
				pstmt.setInt(2, page.getStartRowNo());
				ResultSet rs = pstmt.executeQuery();
				
				while (rs.next()) {
					Review_Board rb = new Review_Board();
					rb.setReview_Bno(rs.getString(1));
					rb.setReview_Btitle(rs.getString(2));
					rb.setReview_Bcontent(rs.getString(3));
					rb.setReview_Date(rs.getString(4));
					rb.setReview_Rate(rs.getInt(5));
					rb.setUser_Id(rs.getString(6));
					rb.setProduct_Name(rs.getString(7));
					
					list.add(rb);
				}
				
				pstmt.close();
				rs.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return list;
		}
		
		public String detailRb(String selectBno) {
			JSONObject json = new JSONObject();
			String sql = "select REVIEW_BTITLE, REVIEW_BCONTENT, REVIEW_DATE, REVIEW_RATE, USER_USER_ID, PRODUCT_NAME from REVIEW_BOARD where REVIEW_BNO = ? ";
			Connection conn = ConnectionProvider.getConnection();
			try{
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, selectBno);
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					json.put("sf", "success");
					json.put("review_Btitle", rs.getString(1));
					json.put("review_Bcontent", rs.getString(2));
					json.put("review_Date", rs.getString(3));
					json.put("review_Rate", rs.getInt(4));
					json.put("user_Id", rs.getString(5));
					json.put("product_Name", rs.getString(6));				
				} else json.put("sf", "fail");
							
				pstmt.close();
				rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return json.toString();
		}
		
		public String deleteRb(String bno) {
			String sql = "delete from REVIEW_BOARD where REVIEW_BNO = ? ";
			Connection conn = ConnectionProvider.getConnection();
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bno);
				pstmt.executeUpdate();
				pstmt.close();
				Output = "success";
					
			} catch (Exception e) {
				Output = "fail";
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return Output;			
		}
		
		public String updateRb(Review_Board rb) {		
			String sql = "update REVIEW_BOARD set REVIEW_BTITLE = ?, REVIEW_BCONTENT = ?, REVIEW_DATE = sysdate, REVIEW_RATE = ? where REVIEW_BNO = ? ";
			Connection conn = ConnectionProvider.getConnection();
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, rb.getReview_Btitle());
				pstmt.setString(2, rb.getReview_Bcontent());
				pstmt.setInt(3, rb.getReview_Rate());
				pstmt.setString(4, rb.getReview_Bno());
				pstmt.executeUpdate();
				pstmt.close();
				Output = "success";
					
			} catch (Exception e) {
				Output = "fail";
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return Output;
	}
}
