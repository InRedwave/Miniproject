package teamproject.comProject.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import teamproject.comProject.DTO.Pager;
import teamproject.comProject.DTO.Product;
import teamproject.comProject.DTO.Product_Detail;
import teamproject.comProject.client.Client;

public class ProductView implements Paging {
	public Pager page;
	public Client client;
	public String searchOption="";
	
	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}



	
	public ProductView(Client client) {
		this.client = client;

		page = new Pager();
		page.setPageNo(1);
	}

	public void printEntireList()  {
		Product[] product = null;
		// setPageInfo(page, 5, 5);
		// 상품목록출력
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Command", "ProductList");
		jsonObject.put("controller", "ProductList");
		JSONObject jd = new JSONObject();
		jd.put("pageNo", page.getPageNo());
		jd.put("searchOption",searchOption );
		jsonObject.put("data", jd);

		String json = jsonObject.toString();
		try {
			System.out.println("상품목록");
	         System.out.println("----------------------------------------------------------------------------------------------------");
	         System.out.printf("%-12s%-12s%-24s%-24s\n", "번호", "상품번호", "상품명", "가격");

	         String json2 = client.receive();
	         // 파싱필요
	         JSONObject root = new JSONObject(json2);
	         JSONObject data = root.getJSONObject("data");
	         JSONArray pl = data.getJSONArray("productList");
	         JSONObject pager = data.getJSONObject("page");
	         page.setTotalRows(pager.getInt("totalRow"));
	         page.setRowsPerPage(pager.getInt("rpp"));
	         page.setPagesPerGroup(pager.getInt("ppg"));

	         page.calPage();
	         product = new Product[page.getEndRowNo() - page.getStartRowNo() + 1];

	         for (int i = 0; i < pl.length(); i++) {

	            JSONObject pro = pl.getJSONObject(i);

	            product[i] = new Product();

	            product[i].setProduct_Id(pro.getString("product_id"));
	            product[i].setProduct_Name(pro.getString("product_name"));
	            product[i].setProduct_Price(pro.getInt("product_price"));

	            System.out.println("----------------------------------------------------------------------------------------------------");
	            System.out.printf("%-12s", (i + 1) + " ");
	            System.out.printf("%-16s%-24s%-24d \n", product[i].getProduct_Id(), product[i].getProduct_Name(),
	                  product[i].getProduct_Price());

	            System.out.println("----------------------------------------------------------------------------------------------------");

	         }

	         printPaging(page);
		} catch (Exception e) {
			System.out.println("[클라이언트] 서버 연결 안됨");
			e.printStackTrace();
		}
		
		Scanner sc = new Scanner(System.in);
		String select = sc.nextLine();
		try {
			if (Integer.parseInt(select) <= page.getRowsPerPage()) {
				// System.out.println(product[Integer.parseInt(select)-1].getProduct_Id());
				product_detail(product[Integer.parseInt(select) - 1].getProduct_Id());
			}
		} catch (Exception e) {
			// e.printStackTrace();
			switch (select) {
			case "n":
				page.setPageNo(page.getPageNo() + 1);

				printEntireList();
				break;
			case "p":
				page.setPageNo(page.getPageNo() - 1);
				printEntireList();
				break;
			case "pp":
				page.setPageNo(page.getStartPageNo() - 1);
				printEntireList();
				break;
			case "nn":
				page.setPageNo(page.getEndPageNo() + 1);
				printEntireList();
				break;
			case "ppp":
				page.setPageNo(1);
				printEntireList();
				break;
			case "nnn":
				page.setPageNo(page.getTotalPageNo());
				printEntireList();
				break;
			case "q":
				break;
			}
			Menu_View mv = new Menu_View(client);
			try {
				if(client.user.getUser_Id()==null) {
					mv.MainMenu();
				}else {
					mv.LoginMenu();
				}
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	

	public void product_detail(String product_Id) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Command", "ProductContent");
		jsonObject.put("controller", "ProductContent");
		jsonObject.put("Product_Id", product_Id);

		String json = jsonObject.toString();

		try {
			client.send(json);
			String receiveJson = client.receive();

			JSONObject root = new JSONObject(receiveJson);
			JSONObject data = root.getJSONObject("data");
			Product product = new Product();
			// 상품명, 가격, 설명, 그래픽카드, cpu, 메인보드,os,ram

			product.setProduct_Name(data.getString("Product_Name"));
			product.setProduct_Price(data.getInt("Product_Price"));
			product.setProduct_Content(data.getString("Product_Content"));
			product.setProduct_Graphic_Card(data.getString("Product_Graphic_Card"));
			product.setCPU(data.getString("CPU"));
			product.setMainboard(data.getString("Mainboard"));
			product.setOS(data.getString("OS"));
			product.setMemory(data.getString("Memory"));

			System.out.println("상품명 : " + product.getProduct_Name());
			System.out.println("가격 : " + product.getProduct_Price());
			System.out.println("설명 : " + product.getProduct_Content());
			System.out.println("그래픽 카드 :" + product.getProduct_Graphic_Card());
			System.out.println("cpu : " + product.getCPU());
			System.out.println("메인보드 : " + product.getMainboard());
			System.out.println("운영체제 : " + product.getOS());
			System.out.println("RAM : " + product.getMemory());
			// 옵션
			Scanner sc = new Scanner(System.in);
			System.out.println("1.장바구니에 담기 2.뒤로가기");
			System.out.print("입력) ");

			if (sc.nextLine().equals("1")) {
				jsonObject = new JSONObject();
				jsonObject.put("Command", "ProductOption");
				jsonObject.put("controller", "ProductOption");
				jsonObject.put("Product_Id", product_Id);
				json = jsonObject.toString();
				client.send(json);
				System.out.println();
				System.out.println("옵션 선택");
				System.out.println("---------------------------------------------------");
				System.out.print("1. 색상 : ");

				receiveJson = client.receive();
				root = new JSONObject(receiveJson);
				JSONArray pl = root.getJSONArray("data");
				List<Product_Detail> productDetailList = new ArrayList<>();
				Set<String> detailColor = new HashSet<>();
				for (int i = 0; i < pl.length(); i++) {
					Product_Detail pd = new Product_Detail();
					JSONObject jsonPD = pl.getJSONObject(i);
					pd.setDetail_Id(jsonPD.getString("detail_id"));
					pd.setDetail_Color(jsonPD.getString("detail_color"));
					pd.setDetail_Capacity(jsonPD.getString("detail_capacity"));
					pd.setDetail_Qnt(jsonPD.getInt("detail_qnt"));
					pd.setProduct_Id(jsonPD.getString("product_id"));
					productDetailList.add(pd);
					detailColor.add(pd.getDetail_Color());
				}

				for (String colorToken : detailColor) {
					System.out.print("[" + colorToken + "]");
				}

				System.out.println();
				System.out.print("색상입력 : ");
				String choiceColor = sc.nextLine();

				System.out.print("2. 용량 : ");
				for (Product_Detail pd : productDetailList) {
					if (pd.getDetail_Color().equals(choiceColor)) {
						System.out.print("[" + pd.getDetail_Capacity() + "]");

					}

				}
				System.out.println();
				System.out.print("용량입력 : ");
				String choiceCapacity = sc.nextLine();
				Product_Detail choiceDetail = null;
				for (Product_Detail pd : productDetailList) {
					if (pd.getDetail_Color().equals(choiceColor) && pd.getDetail_Capacity().equals(choiceCapacity)) {

						choiceDetail = pd;
					}
				}
				System.out.print("수량입력 : ");
				String choiceQnt = sc.nextLine();
				BasketView basketView = new BasketView(client);
				basketView.addBasket(choiceDetail, choiceQnt);
			}
			printEntireList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void searchProduct() {
		System.out.printf("%-30s%-5s","상품검색","검색어 입력");
		Scanner sc = new Scanner(System.in);
		String search=sc.nextLine();
		System.out.println("-----------------------------------------------");
		setSearchOption(search);
		printEntireList();
	}

	
}
