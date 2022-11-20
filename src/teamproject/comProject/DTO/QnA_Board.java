package teamproject.comProject.DTO;

import java.util.Date;

import lombok.Data;

@Data

public class QnA_Board {
	   private String QnA_Bno;
	   private String QnA_Btitle;
	   private String QnA_Bcontent;
	   private Date QnA_Bdate;
	   private String product_Id;
	   private String subproduct_Id;
	   private String user_Id;
	   private Boolean QnA_Content_Exist;
}
