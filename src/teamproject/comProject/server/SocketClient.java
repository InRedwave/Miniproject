package teamproject.comProject.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.json.JSONObject;

import teamproject.comProject.Controller.BasketController;
import teamproject.comProject.Controller.Board_Controller;
import teamproject.comProject.Controller.OrderController;
import teamproject.comProject.Controller.ProductController;
import teamproject.comProject.Controller.User_Controller;
import teamproject.comProject.DTO.Users;

public class SocketClient {
	Server server;
	Socket socket;
	
	DataInputStream dis;
	DataOutputStream dos;
	String Output;
	String clientIp;
	Users users = new Users();
	
	
	public SocketClient(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
		try {
			this.dis = new DataInputStream(socket.getInputStream());
			this.dos = new DataOutputStream(socket.getOutputStream());
			InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
			this.clientIp = isa.getHostName();
			receive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void receive() {
		
		server.threadPool.execute(() -> {
			try {
				while (true) {
					String receiveJson = dis.readUTF();
					JSONObject jsonObject = new JSONObject(receiveJson);
					
					
					String controller = jsonObject.getString("controller");
					switch(controller) {
					
					case  "user_function" : 
						User_Controller user_controller = new User_Controller();
						Output = user_controller.InputJson(jsonObject);
						break;
						
					case "basket_Function" :
						BasketController basket_controller = new BasketController();
						Output = basket_controller.InputJson(jsonObject);
						break;
						
					case "Board_Function" :
						Board_Controller board_controller = new Board_Controller();
						Output = board_controller.InputJson(jsonObject);
						
						break;
						
					case "ProductList":
					case "ProductContent":
					case "ProductOption":	
						//ProductService.getList(this,jsonObject.getString("startRow"),jsonObject.getString("endRow"));
						ProductController productController=new ProductController();
						Output =productController.handleJson(jsonObject);
						break;
					case "AddBasketProduct":
					case "PrintBasket":
						BasketController basketController = new BasketController();
						Output = basketController.handleJson(jsonObject);
						break;	
					case "orderFunction":
						OrderController orderController = new OrderController();
						Output=orderController.InputJson(jsonObject);
						break;
						
					}
					
					
					
					
					
					
					send(Output);
				}
			} catch (IOException e) {
				// 연결이 끊겼을때
				// chatServer.sendToAll(this, "나가셨습니다.");
				server.removeSocketClient(this);
			}
		});

	}

	public static void main(String[] args) {
	}

	public void send(String json) {
		try {

			dos.writeUTF(json);
			dos.flush();
		} catch (IOException e) {

		}
	}

	public void close() {
		try {
			socket.close();
		} catch (IOException e) {

		}
	}

}
