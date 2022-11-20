package teamproject.comProject.client;

import java.io.IOException;

import teamproject.comProject.View.Menu_View;


public class Mainprogram {


	
	public static void main(String[] args) throws Exception {
		try {
			Client client = new Client();
			client.connect();
			boolean flag = true;
			// chatClient.receive();
			while (flag) {
				System.out.println("메인 메뉴 출력");
				Menu_View menu = new Menu_View(client);
				menu.MainMenu();
				flag=false;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
