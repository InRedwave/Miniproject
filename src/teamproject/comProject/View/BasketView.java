package teamproject.comProject.View;

import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import teamproject.comProject.DTO.Basket_Detail;
import teamproject.comProject.DTO.Product_Detail;
import teamproject.comProject.client.Client;

public class BasketView {
	Client client;
	public BasketView(Client client) {
		this.client=client;
	}
	public void addBasket(Product_Detail choiceDetail, String choiceQnt) {
		JSONObject root = new JSONObject();
		root.put("Command", "AddBasketProduct");
		root.put("controller", "AddBasketProduct");
		root.put("detail_id", choiceDetail.getDetail_Id());
		root.put("qnt", choiceQnt);
		if(client.getUser().getUser_Id() == null) {
			System.out.println("로그인이 필요한 기능입니다.");
			Menu_View mv=new Menu_View(client);
			try {
				mv.User();
				return ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		root.put("user_id",client.getUser().getUser_Id());
		String json = root.toString();
		try {
			client.send(json);
			json=client.receive();
			root=new JSONObject(json);
			System.out.println(root.getString("result"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void printBasket() {
		JSONObject root = new JSONObject();
		root.put("Command", "PrintBasket");
		root.put("controller", "PrintBasket");
		if(client.getUser().getUser_Id() == null) {
			System.out.println("로그인이 필요한 기능입니다.");
			Menu_View mv=new Menu_View(client);
			try {
				mv.User();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		root.put("user_id",client.getUser().getUser_Id());
		String json = root.toString();
		int total_price=0;
		try {
			client.send(json);
			json=client.receive();
			root=new JSONObject(json);
			System.out.println(root.getString("user_id")+"님의 장바구니");
			
			JSONArray bl = root.getJSONArray("data");
			
			System.out.println("-------------------------------------------------------------------------------------------------");
			System.out.printf("%-6s", "번호");
			System.out.printf("%-12s%-12s%-12s%-12s%-12s%-12s \n","상품명","색상","용량","가격","수량","합계");

			System.out.println("-------------------------------------------------------------------------------------------------");
			
			for (int i = 0; i < bl.length(); i++) {

				JSONObject bd = bl.getJSONObject(i);
				Basket_Detail basket_detail=new Basket_Detail();
				basket_detail.setBasket_Detail_Id(bd.getString("basket_detail_id"));
				basket_detail.setUser_Id(root.getString("user_id"));
				basket_detail.setPrice(bd.getInt("price"));
				
				basket_detail.setProduct_Qnt(bd.getInt("product_qnt"));
				
				

				System.out.println("-------------------------------------------------------------------------------------------------");
				System.out.printf("%-6s", (i + 1) + " ");
				System.out.printf("%-12s%-12s%-12s%-12d%-12d%-12d \n",bd.getString("product_name"),bd.getString("detail_color"),bd.getString("detail_capacity"),basket_detail.getPrice(),basket_detail.getProduct_Qnt(),bd.getInt("sum_price"));

				System.out.println("-------------------------------------------------------------------------------------------------");
				total_price+=bd.getInt("sum_price");
			}
			System.out.printf("%68s%8d%2s\n","총 금액:",total_price,"원");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("-------------------------------------------------------------------------------------------------");
		System.out.println("1. 결제하기 | 2. 삭제하기 | 3.뒤로가기");
		Scanner sc = new Scanner(System.in);
		String choice=sc.nextLine();
		switch(choice) {
		case "1":
			OrderView ov=new OrderView(client);
			ov.takeOrder(root,total_price);
			break;
		case"2":
			JSONObject deleteBasket=new JSONObject();
			deleteBasket.put("Command", "deleteBasketAll");
			deleteBasket.put("controller", "basket_Function");
			deleteBasket.put("basket_id", root.getString("user_id"));
			try {
				client.send(deleteBasket.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String result=client.receive();
			JSONObject resultJson=new JSONObject(result);
			System.out.println(resultJson.getString("message"));
			break;
			
		}
		Menu_View mv = new Menu_View(client);
		try {
			mv.LoginMenu();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
	
