package teamproject.comProject.Service;

import org.json.JSONArray;
import org.json.JSONObject;

import teamproject.comProject.DAO.BasketDao;
import teamproject.comProject.DAO.BasketDetailDao;
import teamproject.comProject.DAO.ProductDao;
import teamproject.comProject.DTO.Basket;
import teamproject.comProject.DTO.Basket_Detail;
import teamproject.comProject.DTO.Product;

public class BasketService {
	BasketDao Db = new BasketDao();
	String Output;

	public Basket_Detail addBasketProduct(String user_id, String product_detail_id, int qnt) {
		BasketDetailDao basketDetailDao = new BasketDetailDao();

		ProductDao productDao = new ProductDao();
		Basket_Detail bd = new Basket_Detail();
		if (product_detail_id.contains("proD")) {

			Product pro = productDao.selectProductSubQuery(product_detail_id);

			if (pro.getProduct_Id() != null) {
				bd = basketDetailDao.insertBasketPro(user_id, product_detail_id, qnt, pro.getProduct_Price());
				if (bd.getBasket_Detail_Id() != null) {
					BasketDao basketDao = new BasketDao();
					basketDao.updateBasket(user_id, bd);
				}
			}

		} else {

		}

		return bd;
	}

//상품명, 옵션1,옵션2, 수량,가격,주문가격
	public JSONObject printBasket(String user_id) {
		JSONObject root = new JSONObject();
		BasketDetailDao basketDao = new BasketDetailDao();
		JSONArray bdList = basketDao.selectBasketDetails(user_id);
		root.put("data", bdList);
		return root;
	}

	public String Create_Basket(Basket basket) {
		Db = new BasketDao();
		Output = Db.Create(basket);
		return Output;
	}

	public String deleteBasketDetailAll(Basket basket) {
		BasketDetailDao bdd = new BasketDetailDao();
		Output = (bdd.deleteBasketDetail(basket.getUser_Id())) ? "success" : "fail";
		return Output;
	}

}
