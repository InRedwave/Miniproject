package teamproject.comProject.DTO;

import lombok.Data;

@Data
public class Basket_Detail {
	private String basket_Detail_Id;
	private String subproduct_Id;
	private String product_Detail_Id;
	private String user_Id;
	private int price;
	private int product_Qnt;
	private int subproduct_Qnt;
}
