package teamproject.comProject.Controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import teamproject.comProject.DTO.Pager;
import teamproject.comProject.DTO.Product;
import teamproject.comProject.DTO.Product_Detail;
import teamproject.comProject.Service.ProductService;

public class ProductController {

	public String handleJson(JSONObject root) {
		String json = null;
		switch (root.getString("Command")) {
		case "ProductList":
			json = productList(root.getJSONObject("data"));
			break;
		case "ProductContent":
			json = productContent(root);
			break;
		case "ProductOption":
			json = productDetailList(root);
			break;
		}
	return json;

	}

	private String productDetailList(JSONObject jsonObject) {
		ProductService ps = new ProductService();
		List<Product_Detail> list = ps.getProductDetailList(jsonObject.getString("Product_Id"));
		JSONObject sendRoot = new JSONObject();
		sendRoot.put("result", "Product_Detail");
		
		JSONArray pl = new JSONArray();
		for (Product_Detail p : list) {
			JSONObject jop = new JSONObject();
			jop.put("product_id", p.getProduct_Id());
			jop.put("detail_id", p.getDetail_Id());
			jop.put("detail_color", p.getDetail_Color());
			jop.put("detail_capacity", p.getDetail_Capacity());
			jop.put("detail_qnt", p.getDetail_Qnt());
			pl.put(jop);
		}
		
		
		
		sendRoot.put("data", pl);
		return sendRoot.toString();
	}

	public String productList(JSONObject root) {
		ProductService ps = new ProductService();
		int pageNo = root.getInt("pageNo");
		Pager page = new Pager(5, 5, ps.getTotalRow(), pageNo);
		List<Product> list =null;
		if(root.getString("searchOption").equals("")) {
			list =	ps.getList(page);
		}else {
			list =	ps.getList(page,root.getString("searchOption"));
		}
	
		JSONObject sendRoot = new JSONObject();
		sendRoot.put("result", "product");
		JSONObject data = new JSONObject();
		JSONArray pl = new JSONArray();
		for (Product p : list) {
			JSONObject jop = new JSONObject();
			jop.put("product_id", p.getProduct_Id());
			jop.put("product_name", p.getProduct_Name());
			jop.put("product_price", p.getProduct_Price());
			pl.put(jop);
		}
		data.put("productList", pl);
		JSONObject pager = new JSONObject();
		pager.put("totalRow", page.getTotalRows());
		pager.put("rpp", page.getRowsPerPage());
		pager.put("ppg", page.getPagesPerGroup());
		data.put("page", pager);
		sendRoot.put("data", data);
		return sendRoot.toString();
	}

	public String productContent(JSONObject root) {
		ProductService ps = new ProductService();

		Product product = ps.getProductContent(root.getString("Product_Id"));

		JSONObject sendRoot = new JSONObject();
		sendRoot.put("result", "product");

		JSONObject jop = new JSONObject();
		// jop.put("product_id", product.getProduct_Id());
		jop.put("Product_Name", product.getProduct_Name());
		jop.put("Product_Price", product.getProduct_Price());
		jop.put("Product_Content", product.getProduct_Content());
		jop.put("Product_Graphic_Card", product.getProduct_Graphic_Card());
		jop.put("CPU", product.getCPU());
		jop.put("Mainboard", product.getMainboard());
		jop.put("OS", product.getOS());
		jop.put("Memory", product.getMemory());

		sendRoot.put("data", jop);
		return sendRoot.toString();
	}
}
