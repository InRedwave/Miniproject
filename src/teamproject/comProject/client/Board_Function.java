package teamproject.comProject.client;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import teamproject.comProject.DTO.Free_Board;
import teamproject.comProject.DTO.Pager;
import teamproject.comProject.DTO.Review_Board;
import teamproject.comProject.View.BoardView;
import teamproject.comProject.View.Menu_View;
import teamproject.comProject.View.ReviewBoard_View;

public class Board_Function {
	Socket socket;
	Scanner sc = new Scanner(System.in);
	JSONObject JsonObject = new JSONObject();
	Free_Board fb = new Free_Board();
	Review_Board rb = new Review_Board();
	String json;
	
	public void createFbTitle() {
		System.out.println("########[자유 게시판]########");
		System.out.println("[새 게시글]");
		while(true) {
			System.out.print("제목: ");  
			String valid = sc.nextLine();
			if(valid.length()<=10) {
				fb.setFree_Btitle(valid); 
				break;
			} else {
				System.out.println("글자수 10자 이하로 작성해주세요.");
				System.out.print("제목: ");  
			}
		}
	}
	
	public void createFbContent() {
		while(true) {
			System.out.print("내용: "); 
			String valid = sc.nextLine();
			if(valid.length()<=333) {
				fb.setFree_Bcontent(valid); 
				break;
			} else {
				System.out.println("글자수 333자 이하로 작성해주세요.");
				System.out.print("내용: "); 
			}
		}
	}
	
	public void createFbNickname() {
		while(true) {
			System.out.print("닉네임 : "); 
			String valid = sc.nextLine();
			if(valid.length()<=10) {
				fb.setUser_Id(valid); 
				break;
			} else {
				System.out.println("글자수 10자 이하로 작성해주세요.");
				System.out.print("닉네임 : "); 
			}
		}
	}
	
	
	
	public void writeFb(Client client) {
		try {
		createFbTitle();
		createFbContent();
		fb.setUser_Id(client.user.getUser_Id());
		JsonObject.put("Command", "writeFb");
		JsonObject.put("controller", "Board_Function");
		JsonObject.put("free_Btitle", fb.getFree_Btitle());
		JsonObject.put("free_Bcontent", fb.getFree_Bcontent());
		JsonObject.put("user_Id", fb.getUser_Id());
		
		String json = JsonObject.toString();
		client.send(json);
		//System.out.println("전송완료");
		
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("게시글 작성 요청 오류");
		}
		
	}	
	
	//게시글 목록
	public void readFb(Client client) {
		System.out.println("게시글 전체 출력");
		try {
		JsonObject.put("Command","readFb");
		JsonObject.put("controller", "Board_Function");
		String json = JsonObject.toString();
		client.send(json);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("게시글 읽기 요청 오류");
		}
		
	}
	//게시글 선택
	public void readFb(Client client, String selectFbNum) {
		System.out.println("게시글 선택");
		try {
			JsonObject.put("Command","readFb2");
			JsonObject.put("controller", "Board_Function");
			JsonObject.put("selectFbNum", selectFbNum);
			String json = JsonObject.toString();
			client.send(json);
			//System.out.println("전송완료");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("게시글 선택 요청 오류");
			}
	}
		//Pager 적용한 게시글 읽기.
		public void readFbPager(Client client, Pager pager) {
			System.out.println("게시글 읽기");
			try {
				
			JsonObject.put("Command","readFbPager");
			JsonObject.put("controller", "Board_Function");
			JsonObject.put("pageNo", String.valueOf(pager.getPageNo())	);
			
			String json = JsonObject.toString();
			client.send(json);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("게시글 읽기 요청 오류");
			}
			
		}
		
		public void readMyPost(Client client, Pager pager) {
			System.out.println("내 게시글 읽기");
			try {
				JsonObject.put("Command","readMyPost");
				JsonObject.put("controller", "Board_Function");
				JsonObject.put("id", client.user.getUser_Id());
				JsonObject.put("pageNo", String.valueOf(pager.getPageNo())	);
				
				String json = JsonObject.toString();
				client.send(json);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("내 게시글 읽기 요청 오류");
				}
		}	
		public void DeleteFb(Client client, String selectFbNum, String id) {
			System.out.println("게시글 선택");
			try {
				JsonObject.put("Command","deleteFb");
				JsonObject.put("controller", "Board_Function");
				JsonObject.put("id", id);
				JsonObject.put("selectFbNum", selectFbNum);
				String json = JsonObject.toString();
				client.send(json);
				//System.out.println("전송완료");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("게시글 선택 요청 오류");
				}
			}
		
	
		
		
		
		
		
		
		
		
			//리뷰 게시판
		public void createRbTitle() {
			while(true) {
				System.out.print("제목: ");  
				String valid = sc.nextLine();
				if(valid.length()<=10) {
					rb.setReview_Btitle(valid); 
					break;
				} else {
					System.out.println("글자수 10자 이하로 작성해주세요.");
				}
			}
		}
		
		public void createRbContent() {
			while(true) {
				System.out.print("내용: "); 
				String valid = sc.nextLine();
				if(valid.length()<=333) {
					rb.setReview_Bcontent(valid); 
					break;
				} else System.out.println("글자수 333자 이하로 작성해주세요.");
			}
		}
		
		public void createRbPname(Map<String, String> ordermap) {
			while(true) {
				System.out.print("제품명: ");
				String valid = sc.nextLine();
				System.out.println();
				if(ordermap.containsKey(valid)) {
					rb.setProduct_Name(valid);
					rb.setOrder_Detail_Id(ordermap.get(valid));
					break;
				} else {
					System.out.println("제품명을 확인해주세요.");
				}			
			}		
		}
		
		public void createRbRate() {
			while(true) {
				try {
					System.out.print("별점: ");
					int valid = Integer.parseInt(sc.nextLine());
					if(valid<=5 && valid>=0) {
						rb.setReview_Rate(valid);
						break;
					} else System.out.println("0 이상 5 이하 숫자로 작성해주세요.");
				}catch(NumberFormatException e) {
					System.out.println("숫자로 작성해주세요.");
				}
			}
		}
		
		public void orderedRb(String user_Id, Client client) {
			System.out.println("상품 구매 여부");
			
			try {
			JsonObject.put("Command","orderedRb");
			JsonObject.put("controller", "Board_Function");
			JsonObject.put("user_Id", user_Id);
			
			String json = JsonObject.toString();
			client.send(json);
			System.out.println("전송완료");
			
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("상품 구매 여부 오류");
			}		
		}
		
		public void writeRb(Client client) {  //아이디 주고 제품 이름을 리턴 받고 작성 시 제한
			try {
				orderedRb(client.user.getUser_Id(), client);
				JSONObject root = new JSONObject(client.receive());
				if(root.getString("sf").equals("fail")) {
					System.out.println("상품 구매 여부를 확인하세요.");
					Menu_View menu = new Menu_View(client);
					menu.LoginMenu();
				} else {
					Map<String, String> ordermap = new HashMap<>();
					JSONArray ja = root.getJSONArray("json");
					for(int i =0; i<ja.length(); i++) {
						JSONObject jo = ja.getJSONObject(i);
						ordermap.put(jo.getString("PRODUCT_NAME"), jo.getString("ORDER_DETAIL_ID"));
					}
					System.out.println("<구매내역>");
					Set<String> keySet = ordermap.keySet();
					Iterator<String> keyIterator = keySet.iterator();
					while(keyIterator.hasNext()) {
						String k = keyIterator.next();
						System.out.println(k+":"+ordermap.get(k));	
					}
					System.out.println("########[리뷰 게시판]########");
					System.out.println("[새 게시글]");		
					createRbTitle();
					createRbContent();
					createRbPname(ordermap);
					createRbRate();
					JsonObject.put("controller", "Board_Function");
					JsonObject.put("Command", "writeRb");
					JsonObject.put("review_Btitle", rb.getReview_Btitle());
					JsonObject.put("review_Bcontent", rb.getReview_Bcontent());
					JsonObject.put("review_Product_Name", rb.getProduct_Name());
					JsonObject.put("review_Rate", rb.getReview_Rate());
					JsonObject.put("user_Id", client.user.getUser_Id());
					JsonObject.put("order_Detail_Id", rb.getOrder_Detail_Id());
				
					BoardView.savePrint();
					if(sc.nextLine().equals("1")) {
						String json = JsonObject.toString();
						client.send(json);
						System.out.println("전송완료");
					} else System.out.println("전송취소");		
				}
			} catch (Exception e) {
					e.printStackTrace();
					System.out.println("게시글 작성 요청 오류");
			}		
		}
		
		public void readRb(Client client, int pageNo) {
			try {
			JsonObject.put("Command","readRb");
			JsonObject.put("controller", "Board_Function");
			JsonObject.put("pageNo", pageNo);
			
			String json = JsonObject.toString();
			client.send(json);
			System.out.println("전송완료");
			
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("게시글 읽기 요청 오류");
			}
		}
		public void getTRRb(Client client) {
			try {
				JsonObject.put("Command","getTRRb");
				JsonObject.put("controller", "Board_Function");	
				String json = JsonObject.toString();
				client.send(json);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
			
		public void detailRb(Client client, String selectBno) {
			System.out.println("[게시글 상세 보기]");
			try {
				JsonObject.put("Command","detailRb");
				JsonObject.put("controller", "Board_Function");
				JsonObject.put("selectBno", selectBno);
				
				String json = JsonObject.toString();
				client.send(json);
				System.out.println("전송완료");
				
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("게시글 보기 요청 오류");
				}
		}
		
		public void ud(Client client, String user_Id, String bno) {
			if(client.user.getUser_Id().equals(user_Id)) {
				System.out.println("[1. 리뷰 게시판 돌아가기] | [2. 게시글 삭제] | [3. 게시글 수정] | [4. 메뉴 선택으로 돌아가기]");
				System.out.print("선택> ");
				String sel = sc.nextLine();
				if(sel.equals("1")) {
					ReviewBoard_View rb = new ReviewBoard_View(client);
					rb.startRb();
				} else if(sel.equals("2")) {
					deleteRb(client, bno);
					JSONObject root = new JSONObject(client.receive());
					ReviewBoard_View rb = new ReviewBoard_View(client);
					rb.startRb();
				} else if(sel.equals("3")) {
					updateRb(client, bno);
					JSONObject root = new JSONObject(client.receive());
					ReviewBoard_View rb = new ReviewBoard_View(client);
					rb.startRb();
				}
			} else {
				System.out.println("[1. 리뷰 게시판 돌아가기] | [2. 메뉴 선택으로 돌아가기]");
				System.out.print("선택> ");
				if(sc.nextLine().equals("1")){
					ReviewBoard_View rb = new ReviewBoard_View(client);
					rb.startRb();
				}
			} 
			try {
				Menu_View mv = new Menu_View(client);
				mv.LoginMenu();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public void deleteRb(Client client, String bno) {
			System.out.println("[게시글 삭제하기]");
			BoardView.savePrint();
				if(sc.nextLine().equals("1")) {
					try {
						JsonObject.put("Command","deleteRb");
						JsonObject.put("controller", "Board_Function");
						JsonObject.put("bno", bno);					
						String json = JsonObject.toString();
						client.send(json);
						System.out.println("전송완료");
						
						} catch (IOException e) {
							e.printStackTrace();
							System.out.println("게시글 삭제 요청 오류");
						}				
				} else System.out.println("전송취소");			
				 ReviewBoard_View rb = new ReviewBoard_View(client);
	               rb.startRb();
		}
		
		public void updateRb(Client client, String bno) {
			try {
				System.out.println("[게시글 수정하기]");
				createRbTitle();
				createRbContent();
				createRbRate();
				JsonObject.put("controller", "Board_Function");
				JsonObject.put("Command", "updateRb");
				JsonObject.put("review_Btitle", rb.getReview_Btitle());
				JsonObject.put("review_Bcontent", rb.getReview_Bcontent());
				JsonObject.put("review_Rate", rb.getReview_Rate());
				JsonObject.put("user_Id", client.user.getUser_Id());
				JsonObject.put("review_Bno", bno);
			
				BoardView.savePrint();
				if(sc.nextLine().equals("1")) {
					String json = JsonObject.toString();
					client.send(json);
					System.out.println("전송완료");
				} else System.out.println("전송취소");		
					ReviewBoard_View rb = new ReviewBoard_View(client);
	               rb.startRb();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	
	
	
	
	
	
	}
	
		
		
			
		
	
