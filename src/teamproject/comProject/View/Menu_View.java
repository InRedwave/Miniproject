package teamproject.comProject.View;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import teamproject.comProject.DTO.Basket;
import teamproject.comProject.DTO.Pager;
import teamproject.comProject.DTO.Users;
import teamproject.comProject.client.Basket_Function;
import teamproject.comProject.client.Board_Function;
import teamproject.comProject.client.Client;
import teamproject.comProject.client.UserFunction;

public class Menu_View implements Paging{
	Scanner sc = new Scanner(System.in);
	UserFunction userFunction = new UserFunction(); 
	Users users = new Users();
	Basket basket = new Basket();
	Basket_Function basket_Function = new Basket_Function();
	Board_Function boardFunction = new Board_Function();
	Pager pager = new Pager();
	Pager nextPage = new Pager();
	boolean flag;
	String selectNumber;
	Client client;
	JSONObject JsonObject;
	
	public Menu_View(Client client) {
		this.client = client;
	}
	
	public void MainMenu() throws Exception {
		
		System.out.println();
		System.out.println("==============[ 컴퓨터 판매 사이트]==============");
		System.out.println("[1. 상품목록] | [2. 회원 로그인 / 가입] | [3. 종료] ");
		System.out.println("----------------------------------------------------------------------");
		System.out.print("메뉴 선택 > :");
		selectNumber = sc.nextLine(	);
		flag = true;
		while(flag) {
		if(selectNumber.equals("1")) {
			System.out.println("1. 상품목록 2. 상품검색 3.장바구니 ");
			String message = sc.nextLine();
				if (message.equals("1")) {
					ProductView pl=new ProductView(client);
					pl.printEntireList();
					
				}else if(message.equals("2")) {
					ProductView pl=new ProductView(client);
					pl.searchProduct();
					
				}else if(message.equals("3")) {
					BasketView bv=new BasketView(client);
					bv.printBasket();
					
				}
		} else if (selectNumber.equals("2")) {
			User();
			
		} else if (selectNumber.equals("3")) {
			flag = false;
			client.unconnect();
		} else {
			MainMenu();
		}
		}
		
	}
		
		
		
		
	public void User() throws Exception {
		System.out.println();
		System.out.println("----------------------------------------------------");
		System.out.println("[1. 회원 로그인] | [2. 회원 가입] | [3. 메인 메뉴]");
		System.out.println("----------------------------------------------------");
		System.out.print("메뉴 선택 > :");
		selectNumber = sc.nextLine();
		
		if(selectNumber.equals("1")) {
			flag = true;
			while(flag) {
				userFunction.login(client);
				JsonObject= new JSONObject(client.receive());
				System.out.println(JsonObject.getString("message"));
				String step = JsonObject.getString("step");
				
				if(step.equals("manager")) {
					System.out.println("[Manager Menu]");
					ManagerMenu();
					flag = false;
				} else if(step.equals("normal")) {
					System.out.println("[Login Complete]");
					LoginMenu();
					flag = false;
				} else if(step.equals("fail")) {
					User();
					flag = false;
				}
			}
			
		} else if(selectNumber.equals("2")) {	
			flag = true;
			userFunction.idConfirm(client);
			JsonObject= new JSONObject(client.receive());
			String step = JsonObject.getString("step");
			
			while(flag) {
				if (step.equals("next")) {
					break;
				} else if (step.equals("retry")){
					
					System.out.println(JsonObject.getString("message"));
					userFunction.idConfirm(client);
					JsonObject= new JSONObject(client.receive());
					step = JsonObject.getString("step");
					
				}
			}
			userFunction.Join(client);
			JsonObject= new JSONObject(client.receive());
			JsonObject.getString("message");
			
			basket_Function.CreateBasket(client);
			JsonObject= new JSONObject(client.receive());
			JsonObject.getString("message");
			
			
			LoginMenu();
			
			//메뉴 선텍 오류
		} else if(selectNumber.equals("3")) {
			MainMenu();
		} else {
			System.out.println("메뉴 선택 오류");
			User();
		}
	}
	
	public void LoginMenu() throws Exception {
		System.out.println();
		
		System.out.println("[1. 마이 페이지] | [2. 로그아웃] | [3. 상품 페이지] | [4. 게시판] | [5. 종료]");
		System.out.print("선택 > : ");
		selectNumber = sc.nextLine();
		if( selectNumber.equals("1" )) {
			
			userFunction.check(client);
			JsonObject= new JSONObject(client.receive());
			String step = JsonObject.getString("step");
			if(step.equals("true")) {
				System.out.println(JsonObject.getString("message"));
			} else {
				System.out.println(JsonObject.getString("message"));
			}
			
			
			MyPage();
			
		} else if( selectNumber.equals("2")) {
			userFunction.UserLogOut(client);
			System.out.println("메인 페이지로 돌아갑니다.");
			MainMenu();
			
			
		} else if (selectNumber.equals("3")) {
			System.out.println("------------------------------------------------");
			System.out.println("[1. 상품목록] | [2. 상품검색] |  [3.장바구니] ");
			System.out.println("------------------------------------------------");
			String message = sc.nextLine(	);
					if (message.equals("1")) {
						ProductView pl=new ProductView(client);
						pl.printEntireList();
						
						
					}else if(message.equals("2")) {
						ProductView pl=new ProductView(client);
						pl.searchProduct();
						
						
					}else if(message.equals("3")) {
						BasketView bv=new BasketView(client);
						bv.printBasket();
			}
			
		} else if (selectNumber.equals("4")) {
			System.out.println("[게시판 선택] | [1. 자유 게시판] | [2. 리뷰게시판]");
			System.out.print("선택> ");
			switch(sc.nextLine()) {
			case "1" ->{
				FreeBoard_View freeboard_view = new FreeBoard_View(client);
				freeboard_view.startFB();
				//JsonObject= new JSONObject(client.receive());
				
							
			}
			case "2" -> {
				ReviewBoard_View rv = new ReviewBoard_View(client);
				rv.startRb();
				/*JsonObject= new JSONObject(client.receive());
				System.out.println(JsonObject.toString());
				System.out.println(JsonObject.getString("message"));*/
				
			}
			}
			
			
			
			
		} else if (selectNumber.equals("5")) {
			System.out.println("접속을 종료합니다.");
			System.exit(0);
			
		} else {
			LoginMenu();
		}
		
	}
	
	
	
	
	public void MyPage() throws IOException, Exception  {
		System.out.println();
		System.out.println("-------------------------------------------------------");
		System.out.println("[1. 회원정보 수정] | [2. 회원 탈퇴] | [3. 뒤로 가기]");
		System.out.println("-------------------------------------------------------");
		System.out.print("선택 > : ");
		selectNumber = sc.nextLine();
		if(selectNumber.equals("1")) {
			
			userFunction.UserInfo(client);
			JsonObject= new JSONObject(client.receive());
			//기존 회원정보 출력
				System.out.println("[ID] : " + JsonObject.getString("id"));
				System.out.println("[Name] : " + JsonObject.getString("name"));
				System.out.println("[Phone] : " +JsonObject.getString("phone"));
				System.out.println("[Email] : " + JsonObject.getString("email"));
				System.out.println("[닉네임] : " +JsonObject.getString("nickname"));
				System.out.println("[주소] : " +JsonObject.getString("address"));
				
				 userFunction.Modify(client);
				 JsonObject= new JSONObject(client.receive());
				 System.out.println(JsonObject.getString("message"));
				 LoginMenu();
				 
		} else if ( selectNumber.equals("2")) {
			userFunction.Delte(client);
			JsonObject= new JSONObject(client.receive());
			System.out.println(JsonObject.getString("message"));
			
			MainMenu();
			
		} else if (selectNumber.equals("3")) {
			LoginMenu();
		} else {
			System.out.println("메뉴 선택 오류");
			MyPage();
		}
		
		
	}
	
	public void ManagerMenu() throws Exception {
		System.out.println("--------------------------------------------------------------------");
		System.out.println("[1. 게시글 삭제] | [2. 회원 관리] | [3.로그아웃]");
		System.out.println("--------------------------------------------------------------------");
		System.out.print("선택 > : ");
		selectNumber = sc.nextLine();
		flag = true;
		while (flag) {
		if(selectNumber.equals("1")) {
			System.out.println("[게시글 삭제] ");
			FreeBoard_View freeBoardView = new FreeBoard_View(client);
			freeBoardView.DeletePager();
			
			
			flag = false;
		} else if (selectNumber.equals("2")) {
			readUserList();
			nextUserList();
			flag = false;
		} else if(selectNumber.equals("3")) {
			
			userFunction.UserLogOut(client);
			System.out.println("메인 페이지로 돌아갑니다.");
			MainMenu();
			flag = false;
		} else {
			
			ManagerMenu();
			flag = false;
	
		}
	
		}
	}
	
	//유저 리스트 조회, 받기
	public void readUserList() throws Exception {
		//현재페이지 정보 1로 먼저 줌.
		pager.setPageNo(1);
		userFunction.UserList(client, pager);
		JSONObject root = new JSONObject(client.receive());
		JSONObject page = root.getJSONObject("page");
		nextPage.setRowsPerPage(page.getInt("rowsPerPage"));
		nextPage.setPagesPerGroup(page.getInt("pagesPerGroup"));
		nextPage.setTotalRows(page.getInt("totalRow"));
		nextPage.setPageNo(page.getInt("pageNo"));
		nextPage.calPage();
		readUserListAndShow(root);
	}
	
	public void readUserListAndShow(JSONObject jsonObject) throws Exception {
		JSONObject data = jsonObject.getJSONObject("data");
		JSONArray pl = data.getJSONArray("readUserList");
		System.out.println("[유저 List]");
		System.out.println("----------------------------------------------------------------------------------------------------------------");
		System.out.printf("|    ID   |    Pwd   |    Name   |   Phone   |   Email   |   Nickname   |   InsertDate   |   Address   |  \n");
		System.out.println("----------------------------------------------------------------------------------------------------------------");
		
		for(int i = 0; i<pl.length(); i++) {
			JSONObject limitJop = pl.getJSONObject(i);
			
			users.setUser_Id(limitJop.getString("user_Id"));
			users.setUser_Pwd(limitJop.getString("user_Pwd"));
			users.setUser_Name(limitJop.getString("user_Name"));
			users.setUser_Phone(limitJop.getString("user_Phone"));
			users.setUser_Email(limitJop.getString("user_Email"));
			users.setUser_Nickname(limitJop.getString("user_Nickname"));
			users.setUser_Insertdate(limitJop.getString("user_Insertdate"));
			users.setUser_Address(limitJop.getString("user_Address"));
			
			System.out.print("[ID] : ");
			System.out.printf("%-10s", users.getUser_Id());
			System.out.print("    [Pwd] : ");
			System.out.printf("%-10s", users.getUser_Pwd());
			System.out.print("    [Name] : ");
			System.out.printf("%-10s", users.getUser_Name());
			System.out.print("    [Phone] : ");
			System.out.printf("%-10s", users.getUser_Phone());
			System.out.println();
			System.out.print("[Email] : ");
			System.out.printf("%-10s", users.getUser_Email());
			System.out.print("    [NickName] : ");
			System.out.printf("%-10s", users.getUser_Nickname());
			System.out.print("    [가입날짜] : ");
			System.out.printf("%-10s", users.getUser_Insertdate());
			System.out.print("    [주소] : ");
			System.out.printf("%-10s", users.getUser_Address());
			System.out.println();
			System.out.println();
		
		}
		
		int pageNo =  nextPage.getPageNo();
		if(pageNo < 1) {
			pageNo = 1;
			System.out.println("                         -------------1페이지 입니다.--------------                              ");
			nextPage.setPageNo(1);
			readUserList();
		}
		printPaging(nextPage);
		selectUsers();
	}
	
	public void nextUserList() throws Exception {
		userFunction.UserList(client, nextPage);
		JSONObject root = new JSONObject(client.receive());
		readUserListAndShow(root);
	}
	
	
	public Pager selectUsers() throws Exception {
		System.out.println("                      [목록으로 돌아가기] : menu 입력 || [[1~5] 페이지 선택] || [6. 유저 삭제]");
		System.out.print("                                        [<< |<|>|>>] or Page 입력 > : ");
		String select = sc.nextLine();
		try {
			if (Integer.parseInt(select) <= nextPage.getRowsPerPage()) {
				nextPage.setPageNo(Integer.parseInt(select));
				nextUserList();
			} else {
				userFunction.DeleteUser(client);
				JsonObject= new JSONObject(client.receive());
				System.out.println(JsonObject.getString("message"));
				readUserList();
				
			}
		} catch (Exception e) {
			switch (select) {
			case "<<":
				nextPage.setPageNo(1);
				nextUserList();
				break;
			case "<":
				nextPage.setPageNo(nextPage.getPageNo()-1);
				nextUserList();
				break;
			case ">":
				nextPage.setPageNo(nextPage.getPageNo()+1);
				nextUserList();
				break;
			case ">>":
				nextPage.setPageNo(nextPage.getTotalPageNo());
				nextUserList();
				selectUsers();
				break;
			case "menu" :
				ManagerMenu();
				break;
			}
		}
		return pager;
	}
	
	
	
	
	
}
