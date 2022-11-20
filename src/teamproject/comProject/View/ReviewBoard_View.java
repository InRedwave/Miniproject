package teamproject.comProject.View;

import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import teamproject.comProject.DTO.Pager;
import teamproject.comProject.DTO.Review_Board;
import teamproject.comProject.client.Board_Function;
import teamproject.comProject.client.Client;

public class ReviewBoard_View implements Paging{
	Scanner sc = new Scanner(System.in);
	String select;
	Board_Function boardFunction = new Board_Function();
	Client client;
	
	Review_Board rb= new Review_Board();
	Pager page;
	
	public ReviewBoard_View(Client client) {
		this.client = client;
	}
	
	
	public void startRb() {
		System.out.println("[리뷰 게시판]");
		System.out.println("[1. 게시글 작성] | [2. 게시글 목록] | [3. 메뉴 선택] ");
		System.out.println("------------------------------------------------------");  
		System.out.print("선택> ");
		select = sc.nextLine();
		
		if(select.equals("1")) {
			boardFunction.writeRb(client);
			JSONObject trrb = new JSONObject(client.receive());
			startRb();
			
		} else if(select.equals("2")) {
			boardFunction.getTRRb(client);
			JSONObject trrb = new JSONObject(client.receive());
			page = new Pager(5, 5, trrb.getInt("trrb"), 1);			
			printRb();
						
		} else {
			System.out.println("메뉴 선택으로 돌아갑니다.");
			try {
				Menu_View mv = new Menu_View(client);
				mv.LoginMenu();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void printRb() {
		boardFunction.readRb(client, page.getPageNo());
		
		System.out.println("########[리뷰 게시판]########");
		System.out.println("[리뷰 게시판 게시글 목록]");
		System.out.println("--------------------------------------------------");
		System.out.printf("%-6s%-10s%-12s%-11s%-13s\t\t%-5s\n", "|게시글 번호|","| 제품 이름 |", "| 게시글 제목 |", "| 작성자 |","| 작성 일자 |", "| 별점 | ");
		
		JSONObject root = new JSONObject(client.receive());
		JSONArray rl = root.getJSONArray("RBList");
		
		for(int i = 0; i< rl.length(); i++) {
			JSONObject limitJop = rl.getJSONObject(i);
			rb.setReview_Bno(limitJop.getString("review_Bno"));
			rb.setProduct_Name(limitJop.getString("product_Name"));
			rb.setReview_Btitle(limitJop.getString("review_Btitle"));
			rb.setReview_Date(limitJop.getString("review_Date"));
			rb.setUser_Id(limitJop.getString("user_Id"));
			rb.setReview_Rate(limitJop.getInt("review_Rate"));
			
			System.out.printf(" %-10s%-12s%-15s%-10s%-5s\t\t",rb.getReview_Bno(), rb.getProduct_Name(),
					rb.getReview_Btitle(), rb.getUser_Id(), rb.getReview_Date()); 
			for(int j=0; j<rb.getReview_Rate(); j++) System.out.print("★");
			System.out.println();
		}
		page.calPage();
		printPaging(page);
		System.out.println("--------------------------------------------------"); 
		//**선택                                       
		System.out.println("[1. 페이지 선택] | [2. 게시글 보기] ");
		System.out.print("선택> : ");
		String key = sc.nextLine();
		try {
		 if(key.equals("1")) {
			
			System.out.println("[선택] ppp: 처음 | pp: 이전그룹 | p: 이전 | n: 다음 | nn: 다음그룹 | nnn: 맨끝 | q: 종료");
			System.out.print("선택>");
			String select = sc.nextLine();
									
			switch (select) {
				case "n":
					page.setPageNo(page.getPageNo() + 1);

					printRb();
					break;
				case "p":
					page.setPageNo(page.getPageNo() - 1);
					printRb();
					break;
				case "pp":
					page.setPageNo(page.getStartPageNo() - 1);
					printRb();
					break;
				case "nn":
					page.setPageNo(page.getEndPageNo() + 1);
					printRb();
					break;
				case "ppp":
					page.setPageNo(1);
					printRb();
					break;
				case "nnn":
					page.setPageNo(page.getTotalPageNo());
					printRb();
					break;
				case "q":
					break;				
			}
			
		 } else if(key.equals("2")){
			System.out.println("[읽을 게시글 번호]");
			System.out.print("선택> : ");
			String selectBno =sc.nextLine();
			boardFunction.detailRb(client, selectBno);
			JSONObject dj = new JSONObject(client.receive());
			System.out.println("[리뷰 게시판 게시글 읽기]");
			System.out.println("--------------------------------------------------");
			System.out.println("[게시글 번호] : " + selectBno);
			System.out.println("[제품 이름] : " + dj.getString("product_Name"));
			System.out.print("[별점] : ");
			for(int j=0; j<dj.getInt("review_Rate"); j++) System.out.print("★");
			System.out.println();
			System.out.println("[게시글 제목] : " + dj.getString("review_Btitle"));
			System.out.println("[게시글 내용] : " + dj.getString("review_Bcontent"));
			System.out.println("[작성자] : " + dj.getString("user_Id"));
			System.out.println("[작성 일자] : " + dj.getString("review_Date"));
			
			boardFunction.ud(client, dj.getString("user_Id"), selectBno);	
		 }
		 System.out.println("메뉴 선택으로 돌아갑니다.");		
			Menu_View mv = new Menu_View(client);
			mv.LoginMenu();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
}
