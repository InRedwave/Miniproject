package teamproject.comProject.Controller;

import org.json.JSONObject;

import teamproject.comProject.DTO.Basket;
import teamproject.comProject.DTO.Basket_Detail;
import teamproject.comProject.Service.BasketService;

public class BasketController {
	JSONObject jsonObject;
	Basket basket = new Basket();
	Basket_Detail basket_detail = new Basket_Detail();
	BasketService sbf = new BasketService();
	String Output;
	String json;
	
	public String InputJson (JSONObject jsonObject) { 
		
		String command =  jsonObject.getString("Command");
		
		switch(command) {
		
		case "createBasket" :
			basket.setUser_Id(jsonObject.getString("id"));
			Output = sbf.Create_Basket(basket);
			
			if(Output.equals("success")) {
				jsonObject.put("controller", "basket_Function");
				jsonObject.put("message", "장바구니 생성 완료");
				json = jsonObject.toString();
			} else {
				jsonObject.put("controller", "basket_Function");
				jsonObject.put("message", "장바구니 생성 실패");
				json = jsonObject.toString();
			}
			break;
			
			
			
		case "deleteBasketAll" :
			basket.setUser_Id(jsonObject.getString("basket_id"));
			Output = sbf.deleteBasketDetailAll(basket);
			JSONObject result=new JSONObject();
			if(Output.equals("success")) {
				result.put("controller", "basket_Function");
				result.put("message", "장바구니 삭제 완료");
				json = result.toString();
			} else {
				result.put("controller", "basket_Function");
				result.put("message", "장바구니 삭제 실패");
				json = result.toString();
			}
			
			
			break;
			
			
		}
		
		return json;
	}
	public String handleJson(JSONObject jsonObject) {
		String json=null;
		switch(jsonObject.getString("Command")) {
		case "AddBasketProduct":
			json=addBasketPro(jsonObject);
			break;
		case "PrintBasket":
			json=printBasket(jsonObject);
			break;
		}
		
		return json;
	}

	private String printBasket(JSONObject jsonObject) {
		
		BasketService bs=new BasketService();
		JSONObject sendRoot = bs.printBasket(jsonObject.getString("user_id"));
		sendRoot.put("user_id", jsonObject.getString("user_id"));
		
		return sendRoot.toString();
	}

	private String addBasketPro(JSONObject root) {
		JSONObject sendRoot = new JSONObject();
		BasketService bs=new BasketService();
		Basket_Detail basket_Detail=bs.addBasketProduct(root.getString("user_id"),root.getString("detail_id"),Integer.parseInt(root.getString("qnt")));
		
		if(basket_Detail.getBasket_Detail_Id()!=null) {
			sendRoot.put("result", "장바구니 추가완료");
		}else {
			sendRoot.put("result", "장바구니 추가실패");
		}
		
		return sendRoot.toString();
	}

	
	
	
}
