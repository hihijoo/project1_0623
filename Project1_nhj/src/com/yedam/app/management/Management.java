package com.yedam.app.management;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Management {

	
	public String prisonName;
	public String prisonLocation;
	public int prisonNum;
	public String name;
	public String gender;
	public Date birth;
	public String crime;
	public Date imprison;
	public long sentence;
	public Date released;
	public String freedom;
	
	
	@Override
	public String toString() {
		long year = getSentence()/12;
		long month = getSentence()%12;
		String info = "";
		
		if(year == 0){
			//0개월일 경우
			info =prisonName +" 지역:"+prisonLocation+ " 죄수번호:" + prisonNum + " 이름:" + name + " 성별:" + gender + " 생년월일:" + birth 
				+ " 죄목:" + crime +" 수감일:" +imprison + " 형량:"+month+"개월"+ " 석방일:" +released + " " +freedom;
		}else {
			info =prisonName +" 지역:"+prisonLocation+ " 죄수번호:" + prisonNum + " 이름:" + name + " 성별:" + gender + " 생년월일:" + birth 
					+ " 죄목:" + crime +" 수감일:" +imprison + " 형량:"+year+"년 "+month+"개월"+ " 석방일:" +released + " " +freedom;
		}
		
		return info;
	}
	
	public String allView (){
	return prisonName + " 지역:"+prisonLocation+ " 죄수번호:" + prisonNum + " 이름:" + name + " 성별:" + gender + " 생년월일:" + birth 
			+ " 죄목:" + crime  +" "+freedom;
	
	}
	
}
	

	

