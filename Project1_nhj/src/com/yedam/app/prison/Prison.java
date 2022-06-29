package com.yedam.app.prison;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Prison {

	private String prisonName;
	private String prisonLocation;
	private int prisonOccupy;
	private int prisonAccommodate;
	
	
	
	@Override
	public String toString() {
		return prisonName + " - 위치:" + prisonLocation + " 최대수용인원:"
				+ prisonAccommodate + " 수용가능인원:" + prisonOccupy + "\n";
	}



	

	
}
