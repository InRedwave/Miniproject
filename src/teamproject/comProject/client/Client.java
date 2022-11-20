package teamproject.comProject.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import org.json.JSONObject;

import lombok.Data;
import teamproject.comProject.DTO.Basket_Detail;
import teamproject.comProject.DTO.Product;
import teamproject.comProject.DTO.Users;
public class Client {
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	public Users user = new Users();
	
	// 메소드
	public void connect() throws IOException {
		socket = new Socket("localhost", 50001);
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
		System.out.println("[클라이언트] 서버에 연결됨");
	}

	public Users getUser() {
		return user;
	}
	

	public void send(String json) throws IOException {
		dos.writeUTF(json);
		dos.flush();
	}

	public void unconnect() throws IOException {
		socket.close();
	}
	
	public String receive() {
		try {
			return dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	
}
