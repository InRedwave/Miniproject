package teamproject.comProject.Service;

import org.json.JSONArray;
import org.json.JSONObject;

import teamproject.comProject.DAO.BasketDetailDao;
import teamproject.comProject.DAO.OrderDao;
import teamproject.comProject.DAO.OrderDatailDao;

public class OrderService {

	public String takeOrder(JSONArray insertData, String user_id, JSONObject receiveData,int total_price) {
		
		OrderDao orderDao=new OrderDao();
		OrderDatailDao orderDetailDao = new OrderDatailDao();
		String order_id=orderDao.insertOrder(user_id, total_price,receiveData);
		boolean check=true;
		for(int i=0;i<insertData.length();i++) {
			JSONObject detailData =insertData.getJSONObject(i);
			check=orderDetailDao.insertOrderDetail(order_id,detailData.getInt("product_qnt"),detailData.getInt("price"),detailData.getString("product_detail_id"));
			if(check==false) {
				System.out.println("insert 실패 "+i+"번째");
				return "fail";
			}
		}
		BasketDetailDao basketDetailDao = new BasketDetailDao();
		check=basketDetailDao.deleteBasketDetail(user_id);
		if(check==false) {
			System.out.println("바구니 삭제 실패");
			return"fail";
		}
		return "success";
	}

}
