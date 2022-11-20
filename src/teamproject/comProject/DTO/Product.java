package teamproject.comProject.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class Product {
	   private String product_Id;
	   private String product_Name;
	   private int product_Price;
	   
	   private String product_Content;
	   private Date product_Date;
	   private String product_Graphic_Card;
	   private String CPU;
	   private String mainboard;
	   private String OS;
	   private String memory;
}
