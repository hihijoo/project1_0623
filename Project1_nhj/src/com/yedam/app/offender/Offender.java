package com.yedam.app.offender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

	public class Offender {
		private int prisonNum;
		private String name;
		private String gender;
		private String birth;
		private String location;
		private String crime;
		private String imprison;
		private String sentence;
		private String released;
		private int freedom;
		
		//1-수용중인자 0-석방된자
		private int offenderRole;

		@Override
		public String toString() {
			String info = "";
			if(freedom == 1) {
				info = "죄수번호:" + prisonNum + " 이름:" + name + " 성별:" + gender + 
						" 범죄:" + crime + "구속여부: 구속중";
			}else {
				info = "죄수번호:" + prisonNum + " 이름:" + name + " 성별:" + gender + 
						" 범죄:" + crime + " 구속여부:석방";
			}
			return info;
		}
		
}
