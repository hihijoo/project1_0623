package com.yedam.app.offender;



import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

	public class Offender {
		private int prisonNum;
		private String name;
		private String gender;
		private Date birth;
		private String location;
		private String crime;
		private Date imprison;
		private long sentence;
		private Date released;
		private String freedom;
		
		//1-수용중인자 0-석방된자
		private int offenderRole;

		@Override
		public String toString() {
			
			long year = getSentence()/12;
			long month = getSentence()%12;
			
			String info = "";
			if(freedom == "수감중") {
				info = "죄수번호:" + prisonNum + " 이름:" + name + " 성별:" + gender + 
						" 죄목:" + crime + " \n수감일:"+imprison+" 형량:"+year+"년 "+month+"개월"+" 석방일:"+released+" 구속여부:"+freedom;
			}else {
				info = "죄수번호:" + prisonNum + " 이름:" + name + " 성별:" + gender + 
						" 죄목:" + crime + " \n수감일:"+imprison+" 형량:"+year+"년 "+month+"개월"+" 석방일:"+released+" 구속여부:"+freedom;
			}
			return info;
		}
		
}
