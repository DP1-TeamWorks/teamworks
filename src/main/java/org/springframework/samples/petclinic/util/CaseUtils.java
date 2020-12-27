package org.springframework.samples.petclinic.util;

import java.util.stream.IntStream;

public class CaseUtils {
	public static String toPascalCase(String s){
		  String a=s.substring(0,1).toUpperCase();
		  String b=s.substring(1).toLowerCase();
		  return a+b;
		}

		

}
