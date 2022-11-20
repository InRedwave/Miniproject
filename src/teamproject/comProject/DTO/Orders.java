package teamproject.comProject.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class Orders {
	   private String order_Id;
	   private Date order_Date;
	   private String order_Status;
	   private int total_Price;
	   private String user_Id;
	   private String receiver_name;
	   private String receiver_address;
	   private String receiver_phone;
}
