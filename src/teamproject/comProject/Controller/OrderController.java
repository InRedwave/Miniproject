package teamproject.comProject.Controller;

import org.json.JSONObject;

import teamproject.comProject.Service.OrderService;

public class OrderController {

	public String InputJson(JSONObject jsonObject) {
		String command = jsonObject.getString("Command");
		String result=null;
		
		JSONObject root=null;
		switch(command) {
		case "takeOrder":
			result=takeOrder(jsonObject);
			if(result.equals("success")) {
				root=new JSONObject();
				root.put("result", "결제가 완료 되었습니다.");
				
			}
		}
		return root.toString();
	}

	private String takeOrder(JSONObject jsonObject) {
		OrderService orderService = new OrderService();//jsonObject: data(jsonarray),user_id,receiveData
		
		return orderService.takeOrder(jsonObject.getJSONArray("data"),jsonObject.getString("user_id"),jsonObject.getJSONObject("receiveData"),jsonObject.getInt("total_price"));
	}

}
