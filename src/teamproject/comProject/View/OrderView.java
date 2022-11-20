package teamproject.comProject.View;

import java.io.IOException;
import java.util.Scanner;

import org.json.JSONObject;

import teamproject.comProject.client.Client;

public class OrderView {

	public Client client;
	public OrderView(Client client) {
		this.client=client;
	}
	public void takeOrder(JSONObject root,int total_price) {
		Scanner sc = new Scanner(System.in);
		System.out.println("결제 정보 입력 ");
		System.out.print("받는 사람 :");
		String receiver=sc.nextLine();
		System.out.print("받는 사람 번호 :");
		String receiverPhone=sc.nextLine();
		System.out.print("배송지 : ");
		String receiverAddress=sc.nextLine();
		JSONObject receiveData=new JSONObject();
		receiveData.put("receiver", receiver);
		receiveData.put("receiverPhone", receiverPhone);
		receiveData.put("receiverAddress", receiverAddress);
		root.put("receiveData", receiveData);//root: data(jsonarray),user_id,receiveData,total_price
		root.put("total_price",total_price);
		System.out.println("결제하시겠습니까?(yes/no)");
		if(sc.nextLine().equals("yes")) {
			root.put("Command", "takeOrder");
			root.put("controller", "orderFunction");
			try {
				client.send(root.toString());
				String result=client.receive();
				JSONObject resultJson=new JSONObject(result);
				System.out.println(resultJson.getString("result"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("주문이 취소 되었습니다.");
		}
	}

}
