package teamproject.comProject.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

public class Server {
	// 필드
	ServerSocket serverSocket;
	ExecutorService threadPool = Executors.newFixedThreadPool(100);
	Map<String, SocketClient> clients = Collections.synchronizedMap(new HashMap<>());
	DataInputStream dis;
	DataOutputStream dos;
	JSONObject jsonObject = new JSONObject();
	String json;
	// private static Connection conn;
	
	
	// 메소드
	

	public void start() throws IOException {
		serverSocket = new ServerSocket(50001);
		System.out.println("[서버] 시작됨");
		
		Thread thread = new Thread(() -> {
			try {
				while (true) {
					Socket socket = serverSocket.accept();
					SocketClient sc = new SocketClient(this, socket); // this는 ChatServer

				}

			} catch (Exception e) {

			}
		});
		thread.start();
	}

	public void addSocketClient(SocketClient socketClient) {
		String key = socketClient.clientIp;
		clients.put(key, socketClient);
		System.out.println("접속: " + key);
		System.out.println("현재 접속자 수: " + clients.size() + "\n");
	}

	public void removeSocketClient(SocketClient socketClient) {
		String key = socketClient.clientIp;
		clients.remove(key);
		System.out.println("나감: " + key);
		System.out.println("현재 접속자 수: " + clients.size() + "\n");
	}

	

	public void stop() {
		try {
			serverSocket.close();
			threadPool.shutdownNow();
			clients.values().stream().forEach(sc -> sc.close());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("[서버] 종료됨");
	}

	public static void main(String[] args) {

		try {
			Server chatServer = new Server();
			chatServer.start();
			System.out.println("-------------------------------------------------");
			System.out.println("서버를 종료하려면 q 또는 Q를 입력하고 Enter키를 입력하세요.");
			System.out.println("-------------------------------------------------");

			// TCP서버 시작

			// 키보드 입력
			Scanner scanner = new Scanner(System.in);
			while (true) {
				String key = scanner.nextLine();
				if (key.toLowerCase().equals("q")) {
					break;
				}
			}
			scanner.close();
			// TCP 서버 종료
			chatServer.stop();
		} catch (IOException e) {
			System.out.println("[서버] " + e.getMessage());
		}
	}
	
	
	
	
	
}
