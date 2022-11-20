package teamproject.comProject.client;

import java.io.IOException;

import org.json.JSONObject;


public class Basket_Function {
	String ID;
	String PWD;
	JSONObject jsonObject = new JSONObject();
	
	
	public void CreateBasket(Client client) throws IOException {
		System.out.println("장바구니 생성");
		jsonObject.put("Command", "createBasket");
		jsonObject.put("controller", "basket_Function");
		jsonObject.put("id", client.user.getUser_Id());
		String json = jsonObject.toString();
		try {
			client.send(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void BasketDelete(Client client) {
		System.out.println("장바구니 항목 삭제");
		jsonObject.put("Command", "deleteBasket");
		jsonObject.put("controller", "basket_Function");
		jsonObject.put("id", client.user.getUser_Id());
		jsonObject.put("pwd", client.user.getUser_Pwd());
		
		String json = jsonObject.toString();
		try {
			client.send(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
