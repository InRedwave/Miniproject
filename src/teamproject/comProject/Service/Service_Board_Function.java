package teamproject.comProject.Service;

import java.util.ArrayList;

import teamproject.comProject.DAO.DAO_Board;
import teamproject.comProject.DTO.Free_Board;
import teamproject.comProject.DTO.Pager;
import teamproject.comProject.DTO.Review_Board;

public class Service_Board_Function {
	DAO_Board Db = new DAO_Board();
	Free_Board free_Board = new Free_Board();
	String Output;
	int totalRow;
	
	public int getTotalRow() {
		Db = new DAO_Board();
		totalRow = Db.getTotalRow();
		return totalRow;
	}
	
	
	public String writeFb(Free_Board free_Board) {
		Db = new DAO_Board();
		Output = Db.Insert(free_Board);
		return Output;
	}
	
	public ArrayList<Free_Board> readFb(String readFb) {
		ArrayList<Free_Board> list = new ArrayList<>();
		Db = new DAO_Board();
		list = Db.ReadAll(readFb);
		return list;
	}
	
	public Free_Board selectFb(Free_Board free_Board) {
		Db= new DAO_Board();
		Free_Board free_Board2 = new Free_Board();
		free_Board2 = Db.ReadFb(free_Board);
		return free_Board2;
	}
	
	public ArrayList<Free_Board> readFbPager(Pager pager) {
		ArrayList<Free_Board> list = new ArrayList<>();
		Db = new DAO_Board();
		list = Db.ReadFbpager(pager);
		return list;
	}
	
	public ArrayList<Free_Board> readMyPost(Free_Board free_Board, Pager pager) {
		ArrayList<Free_Board> list = new ArrayList<>();
		Db = new DAO_Board();
		list = Db.readMyPost(free_Board, pager);
		return list;
	}
	public int getUser_ID_Board_Count(Free_Board free_Board) {
		Db = new DAO_Board();
		int getUser_ID_Board_Count = Db.getUser_ID_Board_Count(free_Board);
		return getUser_ID_Board_Count;
	}
	
	public String DeleteFb(Free_Board free_Board) {
		Db = new DAO_Board();
		Output = Db.DeleteFb(free_Board);
		return Output;
	}
	public String Or(String user_Id) {
		return Db.ordered(user_Id);
	}
	
	public String writeRb(Review_Board rb) {
		return Db.Insert(rb);
	}
	
	public int getTRRb() {
		return Db.getTRRb();
	}
	
	public ArrayList<Review_Board> readRb(int pageNo) {
		ArrayList<Review_Board> list = new ArrayList<>();
		list = Db.readRb(pageNo);
		return list;
	}
	
	public String detailRb(String selectBno) {
		return Db.detailRb(selectBno);
	}
	
	public String deleteRb(String bno) {
		return Db.deleteRb(bno);
	}
	
	public String updateRb(Review_Board rb) {
		return Db.updateRb(rb);
	}
	
	
	
	
	
	
	
}
