package teamproject.comProject.View;

import java.util.Scanner;

public class BoardView {
	//필드
	private static Scanner scanner = new Scanner(System.in);
	
	//메소드
	//일단 메인메뉴에서 게시판을 선택했다면
			public static void board() {
				System.out.println("-------------------------------------------------------");
				System.out.println("1. 자유 게시판 | 2. 리뷰 게시판 | 3. 문의 게시판 | 4. 메인 페이지 ");
				System.out.println("-------------------------------------------------------");
				System.out.print("선택>");
				String key = scanner.nextLine();
				switch (key) {
				//case "1" -> 
				//case "2" -> 
				//case "3" -> 
				//default -> //메인페이지
				}	
			}
			
			public static void savePrint() {
				System.out.println("---------------------------------------");
				System.out.println("[저장하시겠습니까?] | [1. 예] | [2. 아니오]");
				System.out.println("---------------------------------------");
				System.out.print("선택> ");		
			}
			
			
			public static void subMenuPrint() {
				System.out.println("-----------------------------------------------------------------");
				System.out.println("1. 댓글 보기 | 2. 댓글 쓰기 | 3. 게시글 수정 | 4. 게시글 삭제 | 5. 메인 메뉴 ");
				System.out.println("-----------------------------------------------------------------");
				System.out.print("선택> ");		
			}
			
			//delete r+q 통합??
			
			
			
			
			
			

}
