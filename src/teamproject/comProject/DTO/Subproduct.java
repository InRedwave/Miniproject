package teamproject.comProject.DTO;

import java.util.Date;

import lombok.Data;

@Data	
public class Subproduct {
	private String subproduct_Id;
	private String subproduct_Name;
	private String subproduct_Type;
	private int subproduct_Price;
	private String subproduct_Stock;
	private Date subproduct_Date;
	private String subproduct_Content;
}
