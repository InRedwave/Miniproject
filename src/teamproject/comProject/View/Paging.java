package teamproject.comProject.View;

import teamproject.comProject.DTO.Pager;

interface Paging {
	default void printPaging(Pager page) {
		
		
		System.out.print("                                         [처음]");
	
		if (page.getGroupNo() != 1) {
			System.out.print("[이전]");
		}
		for (int i = page.getStartPageNo(); i <= page.getEndPageNo(); i++) {
			if(page.getPageNo()==i) {
				System.out.print("("+i+")");
			}else {
				System.out.print(i);
			}
			
			if (i != page.getEndPageNo()) {
				System.out.print(",");
			}
		}

		if (page.getGroupNo() != (int) (Math.ceil((double) page.getTotalPageNo() / page.getPagesPerGroup()))) {
			System.out.print("[다음]");
		}
		System.out.println("[맨끝]");
		}
//호출하기전 전체 행수,현재 페이지 저장이 이루어진 상태여야한다.
	
	default void printPaging1(Pager page) {
		 String flag = page.getPageNo() + "";
		 String command = "";
		 if(flag.equals("1")) {
			 command = "start";
		 } else if(flag.equals(page.getEndPageNo() + "")){
			 command = "last";
		 }
		
		switch(command) {
		case  "start" :
			for (int i = page.getStartPageNo(); i <= page.getEndPageNo(); i++) {
				if(page.getPageNo()==i) {
					System.out.print("("+i+")");
				}else {
					System.out.print(i);
				}
				
				if (i != page.getEndPageNo()) {
					System.out.print(",");
				}
			}
			System.out.print("[다음]");
			System.out.println("[맨끝]");
			
			
		case "last" : 
			System.out.print("[처음]");
			for (int i = page.getStartPageNo(); i <= page.getEndPageNo(); i++) {
				if(page.getPageNo()==i) {
					System.out.print("("+i+")");
				}else {
					System.out.print(i);
				}
				
				if (i != page.getEndPageNo()) {
					System.out.print(",");
				}
			}
			
			
		}
		
		
		
	}
	
	
	
	
	
	
	
}
