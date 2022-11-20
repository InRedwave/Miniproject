package teamproject.comProject.View;

import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import teamproject.comProject.DTO.Free_Board;
import teamproject.comProject.DTO.Pager;
import teamproject.comProject.client.Board_Function;
import teamproject.comProject.client.Client;

public class FreeBoard_View implements Paging{
	Scanner sc = new Scanner(System.in);
	String selectNumber;
	Board_Function boardFunction = new Board_Function();
	Client client;
	JSONObject jsonObject;
	JSONArray jsonArray;
	Free_Board free_Board= new Free_Board();
	Pager pager = new Pager();
	Pager nextPage = new Pager();
	String selectFbNum = "";
	public FreeBoard_View(Client client) {
		this.client = client;
		
	}
	
	
	public void startFB() throws Exception {
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------");       
		System.out.println("[자유 게시판] | [1. 게시글 작성] | [2. 게시글 목록] | [3. 내 게시글 보기] | [4. 내 게시글 수정] | |[5. 메뉴로 돌아가기]");
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------");    
		selectNumber = sc.nextLine();
		
		if(selectNumber.equals("1")) {
			System.out.println("[1. 게시글 작성] | ");
			boardFunction.writeFb(client);
			JSONObject root = new JSONObject(client.receive());
			System.out.println(root.getString("message"));
			startFB();
		} else if(selectNumber.equals("2")) {
			readPager();
			nextPager();
		} else if(selectNumber.equals("3")) {
			readMyPost();
			nextMyPost();
		} else {
			Menu_View mv = new Menu_View(client);
			mv.LoginMenu();
		}
		
	}
	
	public void readPager() throws Exception {
		//현재페이지 정보 1로 먼저 줌.
		pager.setPageNo(1);
		boardFunction.readFbPager(client, pager);
		JSONObject root = new JSONObject(client.receive());
		JSONObject page = root.getJSONObject("page");
		nextPage.setRowsPerPage(page.getInt("rowsPerPage"));
		nextPage.setPagesPerGroup(page.getInt("pagesPerGroup"));
		nextPage.setTotalRows(page.getInt("totalRow"));
		nextPage.setPageNo(page.getInt("pageNo"));
		nextPage.calPage();
		ReadAndShow(root);
	}
	public void nextPager() throws Exception {
		boardFunction.readFbPager(client, nextPage);
		JSONObject root = new JSONObject(client.receive());
		ReadAndShow(root);
	}
	public void ReadAndShow(JSONObject jsonObject) throws Exception {
		JSONObject data = jsonObject.getJSONObject("data");
		JSONArray pl = data.getJSONArray("freeBoardList");
		System.out.println("[자유 게시판 게시글 목록]");
		System.out.println("-----------------------------------------------------------");
		System.out.printf("|No. | 선택 번호 | 게시물 제목 | 작성자 | 작성 일자| \n");
		System.out.println("-----------------------------------------------------------");
		
		ArrayList<Free_Board> list = new ArrayList<>();
		for(int i = 0; i<pl.length(); i++) {
			JSONObject limitJop = pl.getJSONObject(i);
			
			free_Board.setFree_Bno(limitJop.getString("free_Bno"));
			free_Board.setFree_Btitle(limitJop.getString("free_Btitle"));
			free_Board.setFree_Bcontent(limitJop.getString("free_Bcontent"));
			free_Board.setFree_Date(limitJop.getString("free_Date"));
			free_Board.setUser_Id(limitJop.getString("user_Id"));
			list.add(free_Board);
			
			System.out.print("[No] : ");
			System.out.printf("%-10s", i+1);
			System.out.print("[선택 번호] : ");
			System.out.printf("%-10s", free_Board.getFree_Bno());
			System.out.print("[게시물 제목] : ");
			System.out.printf("%-10s", free_Board.getFree_Btitle());
			System.out.print("[작성자] : ");
			System.out.printf("%-10s",free_Board.getUser_Id());
			System.out.print("[작성 일자] : ");
			System.out.printf("%-10s", free_Board.getFree_Date());
			System.out.println();
			
			
			
		}
		
		
		int pageNo = nextPage.getPageNo();
		if(pageNo < 1) {
			pageNo = 1;
			System.out.println("                         -------------1페이지 입니다.--------------                              ");
			nextPage.setPageNo(1);
			readPager();
		}
		printPaging(nextPage);
		selectPage();
	}
	
	
	
	
	public Pager selectPage() throws Exception {
		System.out.println("                      [목록으로 돌아가기] : menu 입력 || [[1~5] 페이지 선택] || [6. 게시글 선택]");
		System.out.print("                                        [<< |<|>|>>] or Page 입력 > : ");
		String select = sc.nextLine();
		try {
			if (Integer.parseInt(select) <= nextPage.getRowsPerPage()) {
				nextPage.setPageNo(Integer.parseInt(select));
				nextPager();
			} else {
				readFB();
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			switch (select) {
			case "<<":
				nextPage.setPageNo(1);
				nextPager();
				break;
			case "<":
				nextPage.setPageNo(nextPage.getPageNo()-1);
				nextPager();
				break;
			case ">":
				nextPage.setPageNo(nextPage.getPageNo()+1);
				nextPager();
				break;
			case ">>":
				nextPage.setPageNo(nextPage.getTotalPageNo());
				nextPager();
				selectPage();
				break;
			case "menu" :
				startFB();
				break;
		
			}
		}
		return pager;
	}
	
	
	//내 게시글 읽기 ------------------------------------------------------------------
	public void readMyPost() throws Exception {
		//현재페이지 정보 1로 먼저 줌.
		pager.setPageNo(1);
		boardFunction.readMyPost(client, pager);
		JSONObject root = new JSONObject(client.receive());
		JSONObject page = root.getJSONObject("page");
		nextPage.setRowsPerPage(page.getInt("rowsPerPage"));
		nextPage.setPagesPerGroup(page.getInt("pagesPerGroup"));
		nextPage.setTotalRows(page.getInt("totalRow"));
		nextPage.setPageNo(page.getInt("pageNo"));
		nextPage.calPage();
		myPostRead(root);
	}
	public void nextMyPost() throws Exception {
		boardFunction.readMyPost(client, nextPage);
		JSONObject root = new JSONObject(client.receive());
		myPostRead(root);
	}
	public void myPostRead(JSONObject jsonObject) throws Exception {
		JSONObject data = jsonObject.getJSONObject("data");
		JSONArray pl = data.getJSONArray("freeBoardList");
		System.out.println("[내 게시글 목록]");
		System.out.println("-----------------------------------------------------------");
		System.out.printf("|No. | 선택 번호 | 게시물 제목 | 작성자 | 작성 일자| \n");
		System.out.println("-----------------------------------------------------------");
		
		ArrayList<Free_Board> list = new ArrayList<>();
		for(int i = 0; i<pl.length(); i++) {
			JSONObject limitJop = pl.getJSONObject(i);
			
			free_Board.setFree_Bno(limitJop.getString("free_Bno"));
			free_Board.setFree_Btitle(limitJop.getString("free_Btitle"));
			free_Board.setFree_Bcontent(limitJop.getString("free_Bcontent"));
			free_Board.setFree_Date(limitJop.getString("free_Date"));
			free_Board.setUser_Id(limitJop.getString("user_Id"));
			list.add(free_Board);
			
			System.out.print("[No] : ");
			System.out.printf("%-10s", i+1);
			System.out.print("[선택 번호] : ");
			System.out.printf("%-10s", free_Board.getFree_Bno());
			System.out.print("[게시물 제목] : ");
			System.out.printf("%-10s", free_Board.getFree_Btitle());
			System.out.print("[작성자] : ");
			System.out.printf("%-10s",free_Board.getUser_Id());
			System.out.print("[작성 일자] : ");
			System.out.printf("%-10s", free_Board.getFree_Date());
			System.out.println();
			
		}
		
		
		int pageNo = nextPage.getPageNo();
		if(pageNo < 1) {
			pageNo = 1;
			System.out.println("                         -------------1페이지 입니다.--------------                              ");
			nextPage.setPageNo(1);
			readMyPost();
		}
		printPaging(nextPage);
		selectMyPost();
	}
	public Pager selectMyPost() throws Exception {
		System.out.println("                      [목록으로 돌아가기] : menu 입력 || [[1~5] 페이지 선택] || [6. 내 게시글 선택]");
		System.out.print("                                        [<< |<|>|>>] or Page 입력 > : ");
		String select = sc.nextLine();
		try {
			if (Integer.parseInt(select) <= nextPage.getRowsPerPage()) {
				nextPage.setPageNo(Integer.parseInt(select));
				nextMyPost();
			} else {
				readFB();
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			switch (select) {
			case "<<":
				nextPage.setPageNo(1);
				nextMyPost();
				break;
			case "<":
				nextPage.setPageNo(nextPage.getPageNo()-1);
				nextMyPost();
				break;
			case ">":
				nextPage.setPageNo(nextPage.getPageNo()+1);
				nextMyPost();
				break;
			case ">>":
				nextPage.setPageNo(nextPage.getTotalPageNo());
				nextMyPost();
				selectMyPost();
				break;
			case "menu" :
				startFB();
				break;
		
			}
		}
		return pager;
	}
	
	
	public void readFB() throws Exception {
		System.out.println("--------------------------------------------------");
		System.out.println("읽을 게시물 선택 : ");
		System.out.print("> : ");
		String selectFbNum =sc.nextLine();
		
		boardFunction.readFb(client, selectFbNum);
		JSONObject root1 = new JSONObject(client.receive());
		System.out.println("[자유 게시판 게시글 읽기]");
		System.out.println("-------------------------------------------------------");
		System.out.println("   [게시물 제목]   | [게시물 작성자] | [게시물 번호]");
		System.out.println(root1.getString("free_Btitle") + " | " + root1.getString("user_Id") + " | " + root1.getString("free_Bno"));
		System.out.println("--------------------------------------------------");
		System.out.println("                [게시물 내용]                        ");
		System.out.println(root1.getString("free_Bcontent"));
		
		System.out.println("--------------------------------------------------");
		System.out.println("[1. 목록으로 돌아가기]");
		selectNumber = sc.nextLine();
		if(selectNumber.equals("1")) {
			ReadAndShow(jsonObject);
		} else {
			
		}
	}

	
	
	//게시글 삭제 메소드 ------------------------------------------
	public void DeletePager() throws Exception {
		//현재페이지 정보 1로 먼저 줌.
		pager.setPageNo(1);
		boardFunction.readFbPager(client, pager);
		JSONObject root = new JSONObject(client.receive());
		JSONObject page = root.getJSONObject("page");
		nextPage.setRowsPerPage(page.getInt("rowsPerPage"));
		nextPage.setPagesPerGroup(page.getInt("pagesPerGroup"));
		nextPage.setTotalRows(page.getInt("totalRow"));
		nextPage.setPageNo(page.getInt("pageNo"));
		nextPage.calPage();
		DeletePager(root);
	}
	
	public void DeletePager(JSONObject jsonObject) throws Exception {
		JSONObject data = jsonObject.getJSONObject("data");
		JSONArray pl = data.getJSONArray("freeBoardList");
		System.out.println("[자유 게시판 게시글 목록]");
		System.out.println("-----------------------------------------------------------");
		System.out.printf("|No. | 선택 번호 | 게시물 제목 | 작성자 | 작성 일자| \n");
		System.out.println("-----------------------------------------------------------");
		
		ArrayList<Free_Board> list = new ArrayList<>();
		for(int i = 0; i<pl.length(); i++) {
			JSONObject limitJop = pl.getJSONObject(i);
			
			free_Board.setFree_Bno(limitJop.getString("free_Bno"));
			free_Board.setFree_Btitle(limitJop.getString("free_Btitle"));
			free_Board.setFree_Bcontent(limitJop.getString("free_Bcontent"));
			free_Board.setFree_Date(limitJop.getString("free_Date"));
			free_Board.setUser_Id(limitJop.getString("user_Id"));
			list.add(free_Board);
			
			System.out.print("[No] : ");
			System.out.printf("%-10s", i+1);
			System.out.print("[선택 번호] : ");
			System.out.printf("%-10s", free_Board.getFree_Bno());
			System.out.print("[게시물 제목] : ");
			System.out.printf("%-10s", free_Board.getFree_Btitle());
			System.out.print("[작성자] : ");
			System.out.printf("%-10s",free_Board.getUser_Id());
			System.out.print("[작성 일자] : ");
			System.out.printf("%-10s", free_Board.getFree_Date());
			System.out.println();
			
		}
		
		
		int pageNo = nextPage.getPageNo();
		if(pageNo < 1) {
			pageNo = 1;
			System.out.println("                         -------------1페이지 입니다.--------------                              ");
			nextPage.setPageNo(1);
			DeletePager();
		}
		printPaging(nextPage);
		DeletePageSelect();
	}
	public void DeleteNextPost() throws Exception {
		boardFunction.readFbPager(client, nextPage);
		JSONObject root = new JSONObject(client.receive());
		DeletePager(root);
	}
	
	
	public Pager DeletePageSelect() throws Exception {
		System.out.println("                      [목록으로 돌아가기] : menu 입력 || [1~5. 페이지 선택] | [6. 선택 게시글 삭제] |");
		System.out.print("                                        [<< |<|>|>>] or Page 입력 > : ");
		String select = sc.nextLine();
		try {
			if (Integer.parseInt(select) <= nextPage.getRowsPerPage()) {
				nextPage.setPageNo(Integer.parseInt(select));
				DeleteNextPost();
			} else if(select.equals("6")){
				DeletePage();
			} else {
				DeletePager();
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			switch (select) {
			case "<<":
				nextPage.setPageNo(1);
				DeleteNextPost();
				break;
			case "<":
				nextPage.setPageNo(nextPage.getPageNo()-1);
				DeleteNextPost();
				break;
			case ">":
				nextPage.setPageNo(nextPage.getPageNo()+1);
				DeleteNextPost();
				break;
			case ">>":
				nextPage.setPageNo(nextPage.getTotalPageNo());
				DeleteNextPost();
				DeletePageSelect();
				break;
			case "menu" :
				Menu_View mv= new Menu_View(client);
				mv.ManagerMenu();
				break;
		
			}
		}
		return pager;
	}
	
	
	
	public void DeletePage() throws Exception {
		System.out.println("--------------------------------------------------");
		System.out.println("삭제할 게시물 선택 : ");
		System.out.print("> : ");
		String selectFbNum =sc.nextLine();
		
		boardFunction.readFb(client, selectFbNum);
		JSONObject root1 = new JSONObject(client.receive());
		System.out.println("[자유 게시판 게시글 읽기]");
		System.out.println("--------------------------------------------------");
		System.out.println("   [게시물 제목]   | [게시물 작성자] | [게시물 번호]");
		System.out.println(root1.getString("free_Btitle") + " | " + root1.getString("user_Id") + " | " + root1.getString("free_Bno"));
		System.out.println("--------------------------------------------------");
		System.out.println("                [게시물 내용]                        ");
		System.out.println(root1.getString("free_Bcontent"));
		
		System.out.println("--------------------------------------------------");
		System.out.println("[1. 삭제 확인] | [2. 삭제 취소]");
		selectNumber = sc.nextLine();
		if(selectNumber.equals("1")) {
			boardFunction.DeleteFb(client, selectFbNum, root1.getString("user_Id"));
			jsonObject= new JSONObject(client.receive());
			String message = jsonObject.getString("message");
			System.out.println(message);
			Menu_View mv = new Menu_View(client);
			mv.ManagerMenu();
			
			
		} else {
			DeletePager();
			
		}
		
		
	}
	
	
	
}
