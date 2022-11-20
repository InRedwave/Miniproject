package teamproject.comProject.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.json.JSONObject;

import teamproject.comProject.DTO.Pager;
import teamproject.comProject.DTO.Users;


public class UserFunction {
	Socket socket;
	Scanner sc = new Scanner(System.in);
	JSONObject jsonObject = new JSONObject();
	boolean flag = true;
	
	public void idConfirm(Client client ) {
		System.out.println();
		System.out.println("[회원가입 할 아이디 중복 확인]");
		System.out.println("영어 or 숫자만 입력하세요");
		String pt = "^[a-zA-Z0-9]*$";
		
		boolean flag = true;
		while (flag) {
			String input = sc.nextLine();
			if (! input.equals("")) {
				boolean regex1 = Pattern.matches(pt, input);
				if (regex1) {
					client.user.setUser_Id(input);
					flag = false;
					
				} else {
					System.out.println("신규 ID는 영, 숫자만 가능합니다.");
					System.out.println("[회원가입 할 아이디 중복 확인]");
				}
				
			} else {
				System.out.println("공백- 재입력 필요");
				System.out.println("신규 ID는 영, 숫자만 가능합니다.");
				System.out.println("[회원가입 할 아이디 중복 확인]");
			}
			
		}
			
		jsonObject.put("Command","IdConfirm");
		jsonObject.put("controller", "user_function");
		jsonObject.put("id", client.user.getUser_Id());
		String json = jsonObject.toString();
		try {
			client.send(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//scanner 닫으면 다음 진행 안됨.
		//sc.close();
	}
	
	public void check(Client client ) {
		
		client.user.getUser_Id();
		client.user.getUser_Pwd();
		jsonObject.put("Command", "check");
		jsonObject.put("controller", "user_function");
		jsonObject.put("id", client.user.getUser_Id() );
		jsonObject.put("pwd", client.user.getUser_Pwd());
		
		String json = jsonObject.toString();
		try {
			client.send(json);
		} catch (IOException e) {
			System.out.println("접속 확인 오류");
			e.printStackTrace();
		}
	}
	
	public void login(Client client ) {

		try {
			
		System.out.println();
		System.out.print("[로그인 ID] : ");
		client.user.setUser_Id(sc.nextLine());
		System.out.print("[비밀번호 pwd] : ");
		client.user.setUser_Pwd(sc.nextLine());
		
		jsonObject.put("Command", "login");
		jsonObject.put("controller", "user_function");
		jsonObject.put("id", client.user.getUser_Id());
		jsonObject.put("pwd", client.user.getUser_Pwd());
		
		String json = jsonObject.toString();
		
		client.send(json);
		System.out.println("로그인 대기");
		
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void JoinID(Client client ) {
		try {
			//아이디 입력, 확인.
			System.out.println();
			System.out.println("[회원가입]");
			System.out.println("영어 or 숫자만 입력하세요");
			System.out.print("필수입력_[신규 ID] : ");
			String pt = "^[a-zA-Z0-9]*$";
			while (flag) {
				String input = sc.nextLine();
				if (! input.equals("")) {
					boolean regex1 = Pattern.matches(pt, input);
					if (regex1) {
						client.user.setUser_Id(input);
						flag = false;
					} else {
						System.out.println("[회원가입]");
						System.out.println("영어 or 숫자만 입력하세요.");
						System.out.print("필수입력_[신규 ID] : ");
					}
				} else {
					System.out.println("영어 or 숫자만 입력하세요.");
					System.out.print("필수입력_[신규 ID] : ");
				}
				
			}
			jsonObject.put("Command", "join");
			jsonObject.put("id", client.user.getUser_Id());
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void JoinPassword(Client client ) {
		try {
			System.out.println("6~15자 사이의 소문자와 숫자를 사용하세요.");
			System.out.print("필수입력_[패스워드] : ");
			String pt = "[a-z0-9]{6,15}";
			flag = true;
			while (flag) {
				String input = sc.nextLine();
				if (!input.equals("")) {
					boolean regex1 = Pattern.matches(pt, input);
					if (regex1) {
						client.user.setUser_Pwd(input);
						flag = false;
					} else {
						System.out.println("6~15자 사이의 소문자와 숫자를 사용하세요.");
						System.out.print("필수입력_[패스워드] : ");
					}
				} else {
					System.out.println("6~15자 사이의 소문자와 숫자를 사용하세요.");
					System.out.print("필수입력_[패스워드] : ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void JoinName(Client client ) {
		try {
			System.out.println("한글만 입력 가능합니다.(4자리 제한)");
			System.out.print("필수입력_[이름] : ");
			String pt= "^[가-힣]{0,4}$";
			flag = true;
			while (flag) {
				String input = sc.nextLine();
				if (!input.equals("")) {
					boolean regex1 = Pattern.matches(pt, input);
					if (regex1) {
						client.user.setUser_Name(input);
						flag = false;
					} else {
						System.out.println("한글만 입력 가능합니다.(4자리 제한)");
						System.out.print("필수입력_[이름] : ");
					}
				} else {
					System.out.println("한글만 입력 가능합니다.(4자리 제한)");
					System.out.print("필수입력_[이름] : ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void JoinPhone(Client client ) {
		try {
			System.out.println("000-0000-0000 타입으로 입력하세요.");
			System.out.print("[핸드폰 번호] : " );
			String pt="^01(?:0|1|[0-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
			flag = true;
			while (flag) {
				String input = sc.nextLine();
				boolean regex1 = Pattern.matches(pt, input);
				if (regex1) {
					client.user.setUser_Phone(input);
					flag = false;
				} else {
					System.out.println("000-0000-0000 타입으로 입력하세요.");
					System.out.print("[핸드폰 번호] : " );
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void JoinEmail(Client client ) {
		try {
			System.out.println("email@email.com 타입으로 입력하세요");
			System.out.print("[이메일] : ");
			String pt = "\\w+@\\w+\\.\\w+(\\.\\w+)?";
			flag = true;
			while (flag) {
				String input = sc.nextLine();
				boolean regex1 = Pattern.matches(pt, input);
				if (regex1) {
					client.user.setUser_Email(input);
					flag = false;
				} else {
					System.out.println("영문 아이디만 가능합니다.");
					System.out.println("email@email.com 타입으로 입력하세요");
					System.out.print("[이메일] : ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void JoinNickName(Client client ) {
		try {
			System.out.println("6~15자 사이의 영, 숫자, 한글만 사용 가능합니다.");
			System.out.print("필수입력_[닉네임] : ");
			String pt= "^[0-9a-zA-Zㄱ-ㅎ가-힣\s]{6,15}$";
			flag = true;
			while (flag) {
				String input = sc.nextLine();
				if (!input.equals("")) {
					boolean regex1 = Pattern.matches(pt, input);
					if (regex1) {
						client.user.setUser_Nickname(input);
						flag = false;
					} else {
						System.out.println("6~15자 사이의 영, 숫자, 한글만 사용 가능합니다.");
						System.out.print("필수입력_[닉네임] : ");
					}
				} else {
					System.out.println("6~15자 사이의 영, 숫자, 한글만 사용 가능합니다.");
					System.out.print("필수입력_[닉네임] : ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void JoinAddress(Client client ) {

		try {
			System.out.println("시, 군, 동과 상세주소를 입력하세요 ");
			System.out.print("필수입력_[주소] :");
			String pt= "^[0-9a-zA-Zㄱ-ㅎ가-힣\s]{6,15}$";
			flag = true;
			while (flag) {
				String input = sc.nextLine();
				if (!input.equals("")) {
					boolean regex1 = Pattern.matches(pt, input);
					if (regex1) {
						client.user.setUser_Address(input);
						flag = false;
					} else {
						System.out.println("시, 군, 동과 우편번호를 입력하세요 ");
						System.out.print("필수입력_[주소] :");
					}
				} else {
					System.out.println("시, 군, 동과 우편번호를 입력하세요 ");
					System.out.print("필수입력_[주소] :");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Join(Client client  ) {
		
		try {
			JoinID(client);
			JoinPassword(client);
			JoinName(client);
			JoinPhone(client);
			JoinEmail(client);
			JoinNickName(client);
			JoinAddress(client);
			
			jsonObject.put("Command", "join");
			jsonObject.put("controller", "user_function");
			jsonObject.put("id", client.user.getUser_Id());
			jsonObject.put("pwd", client.user.getUser_Pwd());
			jsonObject.put("name", client.user.getUser_Name());
			jsonObject.put("phone", client.user.getUser_Phone());
			jsonObject.put("email", client.user.getUser_Email());
			jsonObject.put("nickname", client.user.getUser_Nickname());
			jsonObject.put("address", client.user.getUser_Address());
			
			String json = jsonObject.toString();
			client.send(json);
		} catch (IOException e) {
			System.out.println("가입 정보 전송 오류");
			e.printStackTrace();
		}
		System.out.println("가입 정보 전송 완료");
	}
	
	public void Delte(Client client ) {
		
		System.out.println();
		System.out.println("[회원탈퇴]");
		System.out.print("[탈퇴할 ID] : ");
		client.user.setUser_Id(sc.nextLine());
		System.out.print("[패스워드 확인] : ");
		client.user.setUser_Pwd(sc.nextLine());
		
		jsonObject.put("Command", "userDelete");
		jsonObject.put("controller", "user_function");
		jsonObject.put("id", client.user.getUser_Id());
		jsonObject.put("pwd", client.user.getUser_Pwd());
		
		String json = jsonObject.toString();
		try {
			client.send(json);
			System.out.println("회원 탈퇴 요청 완료");
		} catch (IOException e) {
			System.out.println("보낼때 에러");
			e.printStackTrace();
		}
	}
	
	public void Modify(Client client ) {
		
		System.out.println("[개인정보 수정]");
		System.out.println("[1. 비밀번호 수정]");
		JoinPassword(client);
		System.out.println("[2. 핸드폰 번호 수정]");
		JoinPhone(client);
		System.out.println("[3. Email 수정]");
		JoinEmail(client);
		System.out.println("[4. NickName 수정]");
		JoinNickName(client);
		System.out.println("[5. 주소 수정]");
		JoinAddress(client);
		
		try {
			
			jsonObject.put("Command", "userModify");
			jsonObject.put("controller", "user_function");
			jsonObject.put("id", client.user.getUser_Id());
			jsonObject.put("pwd", client.user.getUser_Pwd());
			jsonObject.put("phone", client.user.getUser_Phone());
			jsonObject.put("email", client.user.getUser_Email());
			jsonObject.put("nickname", client.user.getUser_Nickname());
			jsonObject.put("address", client.user.getUser_Address());
			
			String json = jsonObject.toString();
			client.send(json);
		} catch (IOException e) {
			System.out.println("수정 정보 전송 오류");
			e.printStackTrace();
		}
		System.out.println("수정 정보 전송 완료");
	}
	
	public void UserInfo(Client client ) {
		System.out.println("개인정보 출력");
		try {

			jsonObject.put("Command", "userInfo");
			jsonObject.put("controller", "user_function");
			jsonObject.put("id", client.user.getUser_Id());
			jsonObject.put("pwd", client.user.getUser_Pwd());
			
			
			
			String json = jsonObject.toString();
			client.send(json);
		} catch (IOException e) {
			System.out.println("요청 오류");
			e.printStackTrace();
		}
		
	}
	
	public void UserLogOut(Client client ) {
		System.out.println("로그아웃");
		client.user.setUser_Id(null);
		client.user.setUser_Pwd(null);
		
	}
	public void UserList(Client client, Pager pager) {
			try {
			jsonObject.put("Command","readUserList");
			jsonObject.put("controller", "user_function");
			jsonObject.put("pageNo", String.valueOf(pager.getPageNo())	);
			jsonObject.put("id", client.getUser().getUser_Id());
			String json = jsonObject.toString();
			client.send(json);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("유저 List 읽기 요청 오류");
			}
			
		}
		
	public void DeleteUser(Client client) {
		System.out.println();
		System.out.println("[유저 정보 삭제]");
		System.out.print("[삭제할 ID] : ");
		client.user.setUser_Id(sc.nextLine());
		System.out.print("[패스워드 확인] : ");
		client.user.setUser_Pwd(sc.nextLine());
		
		jsonObject.put("Command", "Manager_userDelete");
		jsonObject.put("controller", "user_function");
		jsonObject.put("id", client.user.getUser_Id());
		jsonObject.put("pwd", client.user.getUser_Pwd());
		
		String json = jsonObject.toString();
		try {
			client.send(json);
			System.out.println("회원 삭제 요청 완료");
		} catch (IOException e) {
			System.out.println("보낼때 에러");
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
}
