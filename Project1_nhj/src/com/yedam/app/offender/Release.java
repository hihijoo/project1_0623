package com.yedam.app.offender;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Release {
	
	//기환이가 만들어준 것 - 해석해보기
	
	public static void main(String[] args) {
		
		System.out.printf("?년 ?개월\n");
	    Scanner sc = new Scanner(System.in);

	    StringTokenizer st = new StringTokenizer(sc.nextLine());
	    int month = 0;
	    while (st.hasMoreTokens()) {
	       String y = st.nextToken();
	       if (y.charAt(y.length() - 1) == '년') {
	          y = y.substring(0, y.length() - 1);
	          month += (Integer.parseInt(y) * 12);
	       } else if (y.charAt(y.length() - 1) == '월') {
	          y = y.substring(0, y.length() - 2);
	          month += Integer.parseInt(y);
	       }
	    }

	    System.out.println(month);
	}

}
