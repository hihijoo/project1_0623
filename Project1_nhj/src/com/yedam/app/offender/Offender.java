package com.yedam.app.offender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

	public class Offender {
		private int prsionNum;
		private String name;
		private String gender;
		private String birth;
		private String location;
		private String crime;
		private String imprison;
		private String sentence;
		private String released;
		
		//1-수용중인자 0-석방된자
		private int offenderRole;
		
		
		@Override
		public String toString() {
			String crime = "";
			if(offenderRole == 0) {
				crime = "석방된자" ;
			}else  {
				crime = "수감중인자";
				
			}
			return crime;
		}
		
		
	
	
}
