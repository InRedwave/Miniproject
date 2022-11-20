package teamproject.comProject.Controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import teamproject.comProject.DTO.Pager;
import teamproject.comProject.DTO.Users;
import teamproject.comProject.Service.Service_User_Function;

public class User_Controller {
	JSONObject jsonObject;
	Users users = new Users();
	Service_User_Function suf = new Service_User_Function();
	String Output;
	String json;
	ArrayList<String> list;
	public String InputJson (JSONObject jsonObject) {
		
		String command =  jsonObject.getString("Command");
				
		switch(command) {
			case "IdConfirm" :
				users.setUser_Id(jsonObject.getString("id"));
				Output = suf.User_ID_Confirm(users);
				
				if(Output.equals("success")) {
					jsonObject.put("Command", "user_function");
					jsonObject.put("message", "존재하는 ID입니다.");
					jsonObject.put("step", "retry");
					json = jsonObject.toString();
					
				} else if( Output.equals("fail")) {
					jsonObject.put("Command", "user_function");
					jsonObject.put("message", "중복되지 않는 ID입니다.");
					jsonObject.put("step", "next");
					json = jsonObject.toString();
					
				} else {
					jsonObject.put("message", "error");
					json = jsonObject.toString();
				}
				break;
				
			case "login" : 
				users.setUser_Id(jsonObject.getString("id"));
				users.setUser_Pwd(jsonObject.getString("pwd"));
				Output = suf.User_LogIn(users);
				
				if(Output.equals("manager")) {
					jsonObject.put("Command", "user_function");
					jsonObject.put("message", "로그인 되었습니다");
					jsonObject.put("step", "manager");
					json = jsonObject.toString();
					
				} else if( Output.equals("normal")) {
					jsonObject.put("Command", "user_function");
					jsonObject.put("message", "로그인 되었습니다");
					jsonObject.put("step", "normal");
					json = jsonObject.toString();
					
				} else {
					jsonObject.put("Command", "user_function");
					jsonObject.put("message", "아이디나 비밀번호가 틀렸습니다.");
					jsonObject.put("step", "fail");
					json = jsonObject.toString();
				} 
				break;
				
			case "check" :
				users.setUser_Id(jsonObject.getString("id"));
				users.setUser_Pwd(jsonObject.getString("pwd"));
				Output = suf.User_LogIn(users);
				if(Output.equals("normal")) {
					jsonObject.put("Command", "user_function");
					jsonObject.put("message", "로그인 상태 확인 완료");
					jsonObject.put("step", "true");
					json = jsonObject.toString();
					
				} else if( Output.equals("fail")) {
					jsonObject.put("Command", "user_function");
					jsonObject.put("message", "로그인 상태 끊김");
					jsonObject.put("step", "fail");
					json = jsonObject.toString();
				}
				break;
				
			case "join" : 
				users.setUser_Id(jsonObject.getString("id"));
				users.setUser_Pwd(jsonObject.getString("pwd"));
				users.setUser_Name(jsonObject.getString("name"));
				users.setUser_Phone(jsonObject.getString("phone"));
				users.setUser_Email(jsonObject.getString("email"));
				users.setUser_Nickname(jsonObject.getString("nickname"));
				users.setUser_Address(jsonObject.getString("address"));
				Output = suf.User_Join(users);
				
				if(Output.equals("success")) {
					jsonObject.put("Command", "user_function");
					jsonObject.put("message", "회원가입 성공");
					jsonObject.put("step", "true");
					json = jsonObject.toString();
				} else {
					jsonObject.put("Command", "user_function");
					jsonObject.put("message", "회원가입 실패");
					json = jsonObject.toString();
				}
				
				break;
			case "userDelete" :	
				users.setUser_Id(jsonObject.getString("id"));
				users.setUser_Pwd(jsonObject.getString("pwd"));
				Output = suf.User_Delete(users);
				
				if(Output.equals("success")) {
					jsonObject.put("Command", "user_function");
					jsonObject.put("message", "회원 탈퇴 완료");
					jsonObject.put("step", "true");
					json = jsonObject.toString();
				} else if (Output.equals("fail")) {
					jsonObject.put("Command", "user_function");
					jsonObject.put("message", "잘못된 정보입니다. ");
					jsonObject.put("step", "true");
					json = jsonObject.toString();
				} else {
					jsonObject.put("Command", "user_function");
					jsonObject.put("message",  "ID 가 존재하지 않습니다");
					json = jsonObject.toString();
				}
				
				break;
			case "userModify" : 
				users.setUser_Id(jsonObject.getString("id"));
				users.setUser_Pwd(jsonObject.getString("pwd"));
				users.setUser_Phone(jsonObject.getString("phone"));
				users.setUser_Email(jsonObject.getString("email"));
				users.setUser_Nickname(jsonObject.getString("nickname"));
				users.setUser_Address(jsonObject.getString("address"));
				Output = suf.User_Update(users);
				
				if(Output.equals("success")) {
					
					jsonObject.put("Command", "user_function");
					jsonObject.put("message", "정보 수정을 완료했습니다.");
					jsonObject.put("step", "true");
					
					json = jsonObject.toString();
					
				} else {
					jsonObject.put("Command", "user_function");
					jsonObject.put("message", "정보 수정에 실패했습니다.");
					jsonObject.put("step", "fail");
					json = jsonObject.toString();
				}
				break;
				
			case "userInfo" :
				users.setUser_Id(jsonObject.getString("id"));
				Users data = suf.User_Info(users); //user 객체로 return 됨.
					jsonObject.put("Command", "user_function");
					jsonObject.put("message", "유저 정보 전송 완료");
					jsonObject.put("step", "true");
					jsonObject.put("id", data.getUser_Id());
					jsonObject.put("name", data.getUser_Name());
					jsonObject.put("phone", data.getUser_Phone());
					jsonObject.put("email", data.getUser_Email());
					jsonObject.put("nickname", data.getUser_Nickname());
					jsonObject.put("address", data.getUser_Address());
					json = jsonObject.toString();
				
				break;
			
			case "readUserList" :
				ArrayList<Users> list = new ArrayList<>();
				users.setUser_Id(jsonObject.getString("id"));
				int getUserCount = suf.getTotalUserRow();
				Pager pager = new Pager(5, 5, getUserCount, Integer.parseInt(jsonObject.getString("pageNo")));
				
				list = suf.readAllUser(users, pager);
				 
					JSONObject sendRoot = new JSONObject();
					//pager 정보 넣기.
					JSONObject page = new JSONObject();
					page.put("rowsPerPage", pager.getRowsPerPage());
					page.put("pagesPerGroup", pager.getPagesPerGroup());
					page.put("totalRow", pager.getTotalRows());
					page.put("pageNo", pager.getPageNo());
					
					sendRoot.put("page", page);
					
					
					JSONObject data1= new JSONObject();
					JSONArray pl = new JSONArray();
					
					for( Users F : list ) {
						JSONObject limitJop = new JSONObject();
						limitJop.put("user_Id", F.getUser_Id());
						limitJop.put("user_Pwd", F.getUser_Pwd());
						limitJop.put("user_Name", F.getUser_Name());
						limitJop.put("user_Phone", F.getUser_Phone());
						limitJop.put("user_Email", F.getUser_Email());
						limitJop.put("user_Nickname", F.getUser_Nickname());
						limitJop.put("user_Insertdate", F.getUser_Insertdate());
						limitJop.put("user_Address", F.getUser_Address());
						
						pl.put(limitJop);
					}
					data1.put("readUserList", pl);
					sendRoot.put("data",data1);
					json = sendRoot.toString();
					break;
				
			case "Manager_userDelete" :
				users.setUser_Id(jsonObject.getString("id"));
				users.setUser_Pwd(jsonObject.getString("pwd"));
				
				String Output = suf.Manager_UserDelete(users);
				System.out.println(Output);
				
				if(Output.equals("success")) {
					jsonObject.put("Command", "Manager_userDelete");
					jsonObject.put("message", "삭제가 완료되었습니다.");
					json = jsonObject.toString();
					
				} else if( Output.equals("fail")) {
					jsonObject.put("Command", "Manager_userDelete");
					jsonObject.put("message", "삭제가 완료되지 못했습니다.");
					json = jsonObject.toString();
				} 
			break;
				
				
					
					
		}
		return json;
		
	}
	
	
	
	
}
