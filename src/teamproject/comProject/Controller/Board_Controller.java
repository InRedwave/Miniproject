package teamproject.comProject.Controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import teamproject.comProject.DTO.Free_Board;
import teamproject.comProject.DTO.Pager;
import teamproject.comProject.DTO.Review_Board;
import teamproject.comProject.Service.Service_Board_Function;

public class Board_Controller {
	JSONObject jsonObject;
	JSONArray jsonArray;
	Free_Board free_Board = new Free_Board();
	String Output;
	String json;
	Service_Board_Function sbf = new Service_Board_Function();
	Pager pager = new Pager();
	Review_Board rb = new Review_Board();
	
	public String InputJson (JSONObject jsonObject) {
		String command =  jsonObject.getString("Command");
		
		switch(command) {
			case "writeFb" :
				free_Board.setUser_Id(jsonObject.getString("user_Id"));
				free_Board.setFree_Btitle(jsonObject.getString("free_Btitle"));
				free_Board.setFree_Bcontent(jsonObject.getString("free_Bcontent"));
				
				Output = sbf.writeFb(free_Board);
				
				if(Output.equals("success")) {
					jsonObject.put("Command", "Board_Function");
					jsonObject.put("message", "게시글 입력 완료");
					jsonObject.put("step", "next");
					json = jsonObject.toString();
					
				} else if( Output.equals("fail")) {
					jsonObject.put("Command", "Board_Function");
					jsonObject.put("message", "게시글 입력 오류");
					jsonObject.put("step", "retry");
					json = jsonObject.toString();
					
				} else {
					jsonObject.put("Command", "Board_Function");
					jsonObject.put("message", "error");
					json = jsonObject.toString();
				}
				break;
				
			case "readFb" :
				ArrayList<Free_Board> list = new ArrayList<>();
				list = sbf.readFb("readFb");
				
				JSONObject sendRoot = new JSONObject();
				sendRoot.put("result", "product");
				JSONObject data = new JSONObject();
				JSONArray pl = new JSONArray();
				
				for( Free_Board F : list) {
					JSONObject limitJop = new JSONObject();
					limitJop.put("free_Bno", F.getFree_Bno());
					limitJop.put("free_Btitle", F.getFree_Btitle());
					limitJop.put("free_Bcontent", F.getFree_Bcontent());
					limitJop.put("free_Date", F.getFree_Date());
					limitJop.put("user_Id", F.getUser_Id());
					
					pl.put(limitJop);
				}
				data.put("freeBoardList", pl);
				sendRoot.put("data",data);
				json = sendRoot.toString();
				
				break;
				
			case "readFb2" : 
				free_Board.setFree_Bno(jsonObject.getString("selectFbNum"));
				Free_Board free_Board2 = sbf.selectFb(free_Board);
				
				JSONObject limitJop = new JSONObject();
				limitJop.put("free_Bno", free_Board2.getFree_Bno());
				limitJop.put("free_Btitle", free_Board2.getFree_Btitle());
				limitJop.put("free_Bcontent", free_Board2.getFree_Bcontent());
				limitJop.put("free_Date", free_Board2.getFree_Date());
				limitJop.put("user_Id", free_Board2.getUser_Id());
				json = limitJop.toString();
			break;
				
			case "readFbPager" :
				ArrayList<Free_Board> list3 = new ArrayList<>();
				//page 정보 추가해서 전송.
				int totalRow = sbf.getTotalRow();
				Pager pager = new Pager(5, 5, totalRow, Integer.parseInt(jsonObject.getString("pageNo")));
				
				list3 = sbf.readFbPager(pager);
				// return 된 list 받아서 JSON 변환 후 send
				JSONObject sendRoot3 = new JSONObject();
				//pager 정보 넣기.
				JSONObject page = new JSONObject();
				page.put("rowsPerPage", pager.getRowsPerPage());
				page.put("pagesPerGroup", pager.getPagesPerGroup());
				page.put("totalRow", pager.getTotalRows());
				page.put("pageNo", pager.getPageNo());
				
				sendRoot3.put("page", page);
				
				
				JSONObject data3 = new JSONObject();
				JSONArray pl3 = new JSONArray();
				
				for( Free_Board F : list3) {
					JSONObject limitJop3 = new JSONObject();
					limitJop3.put("free_Bno", F.getFree_Bno());
					limitJop3.put("free_Btitle", F.getFree_Btitle());
					limitJop3.put("free_Bcontent", F.getFree_Bcontent());
					limitJop3.put("free_Date", F.getFree_Date());
					limitJop3.put("user_Id", F.getUser_Id());
					
					pl3.put(limitJop3);
				}
				data3.put("freeBoardList", pl3);
				sendRoot3.put("data",data3);
				json = sendRoot3.toString();
				break;
				
			case "readMyPost" :
				ArrayList<Free_Board> list4 = new ArrayList<>();
				free_Board.setUser_Id(jsonObject.getString("id"));
				int getUser_ID_Board_Count = sbf.getUser_ID_Board_Count(free_Board);
				Pager pager1 = new Pager(5, 5, getUser_ID_Board_Count, Integer.parseInt(jsonObject.getString("pageNo")));
				
				list4 = sbf.readMyPost(free_Board, pager1);
					// return 된 list 받아서 JSON 변환 후 send
				 	
				 
					JSONObject sendRoot4 = new JSONObject();
					//pager 정보 넣기.
					JSONObject page1 = new JSONObject();
					page1.put("rowsPerPage", pager1.getRowsPerPage());
					page1.put("pagesPerGroup", pager1.getPagesPerGroup());
					page1.put("totalRow", pager1.getTotalRows());
					page1.put("pageNo", pager1.getPageNo());
					
					sendRoot4.put("page", page1);
					
					
					JSONObject data4 = new JSONObject();
					JSONArray pl4 = new JSONArray();
					
					for( Free_Board F : list4 ) {
						JSONObject limitJop3 = new JSONObject();
						limitJop3.put("free_Bno", F.getFree_Bno());
						limitJop3.put("free_Btitle", F.getFree_Btitle());
						limitJop3.put("free_Bcontent", F.getFree_Bcontent());
						limitJop3.put("free_Date", F.getFree_Date());
						limitJop3.put("user_Id", F.getUser_Id());
						
						pl4.put(limitJop3);
					}
					data4.put("freeBoardList", pl4);
					sendRoot4.put("data",data4);
					json = sendRoot4.toString();
					break;
					
					
			case "DeletePost" :
				ArrayList<Free_Board> list5 = new ArrayList<>();
				int totalRow1 = sbf.getTotalRow();
				Pager pager5 = new Pager(5, 5, totalRow1, Integer.parseInt(jsonObject.getString("pageNo")));
				
				list5 = sbf.readMyPost(free_Board, pager5);
					// return 된 list 받아서 JSON 변환 후 send
				 	
				 
					JSONObject sendRoot5 = new JSONObject();
					//pager 정보 넣기.
					JSONObject page5 = new JSONObject();
					page5.put("rowsPerPage", pager5.getRowsPerPage());
					page5.put("pagesPerGroup", pager5.getPagesPerGroup());
					page5.put("totalRow", pager5.getTotalRows());
					page5.put("pageNo", pager5.getPageNo());
					
					sendRoot5.put("page", pager5);
					
					
					JSONObject data5 = new JSONObject();
					JSONArray pl5 = new JSONArray();
					
					for( Free_Board F : list5 ) {
						JSONObject limitJop3 = new JSONObject();
						limitJop3.put("free_Bno", F.getFree_Bno());
						limitJop3.put("free_Btitle", F.getFree_Btitle());
						limitJop3.put("free_Bcontent", F.getFree_Bcontent());
						limitJop3.put("free_Date", F.getFree_Date());
						limitJop3.put("user_Id", F.getUser_Id());
						
						pl5.put(limitJop3);
					}
					data5.put("freeBoardList", pl5);
					sendRoot5.put("data",data5);
					json = sendRoot5.toString();
					break;		
					
			case "deleteFb" :
				free_Board.setFree_Bno(jsonObject.getString("selectFbNum"));
				free_Board.setUser_Id(jsonObject.getString("id"));
				String Output = sbf.DeleteFb(free_Board);
				if(Output.equals("success")) {
					jsonObject.put("Command", "Board_Function");
					jsonObject.put("message", "삭제가 완료되었습니다..");
					json = jsonObject.toString();
					
				} else if( Output.equals("fail")) {
					jsonObject.put("Command", "Board_Function");
					jsonObject.put("message", "삭제가 완료되지 못했습니다..");
					json = jsonObject.toString();
				} 
			break;
			
			
			
			
			
			case "writeRb" :
				rb.setUser_Id(jsonObject.getString("user_Id"));
				rb.setReview_Btitle(jsonObject.getString("review_Btitle"));
				rb.setReview_Bcontent(jsonObject.getString("review_Bcontent"));
				rb.setProduct_Name(jsonObject.getString("review_Product_Name"));
				rb.setReview_Rate(jsonObject.getInt("review_Rate"));
				rb.setOrder_Detail_Id(jsonObject.getString("order_Detail_Id"));
			
				Output = sbf.writeRb(rb);
				
				if(Output.equals("success")) {
					jsonObject.put("Command", "Board_Function");
					jsonObject.put("message", "게시글 입력 완료");
					jsonObject.put("step", "next");                     
					json = jsonObject.toString();
					
				} else if( Output.equals("fail")) {
					jsonObject.put("Command", "Board_Function");
					jsonObject.put("message", "게시글 입력 오류");
					jsonObject.put("step", "retry");
					json = jsonObject.toString();
					
				} else {
					jsonObject.put("Command", "Board_Function");
					jsonObject.put("message", "error");
					json = jsonObject.toString();
				}
				break;
				
			case "orderedRb" :
				json = sbf.Or(jsonObject.getString("user_Id"));		
				break;
				
			case "getTRRb" :
				json = jsonObject.put("trrb", sbf.getTRRb()).toString();
				break;
				
			case "readRb" :
				List<Review_Board> listR = new ArrayList<>();
				listR = sbf.readRb(jsonObject.getInt("pageNo"));
				
				JSONObject sendR = new JSONObject();
				//sendR.put("result", "product");         //<<???
				//JSONObject data = new JSONObject();
				JSONArray rl = new JSONArray();
				
				for(Review_Board R : listR) {
					JSONObject limitJop5 = new JSONObject();
					limitJop5.put("review_Bno", R.getReview_Bno());
					limitJop5.put("product_Name", R.getProduct_Name());
					limitJop5.put("review_Btitle", R.getReview_Btitle());
					limitJop5.put("review_Date", R.getReview_Date());
					limitJop5.put("user_Id", R.getUser_Id());
					limitJop5.put("review_Rate", R.getReview_Rate());
					limitJop5.put("product_Name", R.getProduct_Name());
					
					rl.put(limitJop5);
				}
				//data.put("freeBoardList", pl);
				sendR.put("RBList",rl);
				json = sendR.toString();
				break;
				
			case "detailRb" :
				json = sbf.detailRb(jsonObject.getString("selectBno"));				
				break;
				
			case "deleteRb" :			
				Output = sbf.deleteRb(jsonObject.getString("bno"));
				
				if(Output.equals("success")) {
					jsonObject.put("Command", "Board_Function");
					jsonObject.put("message", "게시글 입력 완료");
					jsonObject.put("step", "next");                      
					json = jsonObject.toString();
					
				} else if( Output.equals("fail")) {
					jsonObject.put("Command", "Board_Function");
					jsonObject.put("message", "게시글 입력 오류");
					jsonObject.put("step", "retry");
					json = jsonObject.toString();
					
				} else {
					jsonObject.put("Command", "Board_Function");
					jsonObject.put("message", "error");
					json = jsonObject.toString();
				}
				
				break;
				
			case "updateRb" :
				rb.setUser_Id(jsonObject.getString("user_Id"));
				rb.setReview_Btitle(jsonObject.getString("review_Btitle"));
				rb.setReview_Bcontent(jsonObject.getString("review_Bcontent"));
				rb.setReview_Rate(jsonObject.getInt("review_Rate"));
				rb.setReview_Bno(jsonObject.getString("review_Bno"));
				
				Output = sbf.updateRb(rb);
				
				if(Output.equals("success")) {
					jsonObject.put("Command", "Board_Function");
					jsonObject.put("message", "게시글 입력 완료");
					jsonObject.put("step", "next");                      
					json = jsonObject.toString();
					
				} else if( Output.equals("fail")) {
					jsonObject.put("Command", "Board_Function");
					jsonObject.put("message", "게시글 입력 오류");
					jsonObject.put("step", "retry");
					json = jsonObject.toString();
					
				} else {
					jsonObject.put("Command", "Board_Function");
					jsonObject.put("message", "error");
					json = jsonObject.toString();
				}
			
		}
		return json;
	}
	
	
	
	
	
	
}
