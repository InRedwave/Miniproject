package teamproject.comProject.DTO;

import java.util.Date;

import lombok.Data;

@Data

public class QnA_Comment {
	   private String QnA_Conum;
	   private String QnA_Cocontent;
	   private Date QnA_Codate;
	   
	   private String QnA_Bno;
	   private String user_Id;
}
