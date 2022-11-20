package teamproject.comProject.DTO;

import lombok.Data;

@Data
public class Order_Detail {
	   private String order_Detail_Id;
	   private String product_Detail_Id;
	   private String order_Id;
	   private String subproduct_Id;
	   private int price;
	   private int product_Qnt;
	   private int subproduct_Qnt;
}
