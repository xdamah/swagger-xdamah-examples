package com.example.custom;
import java.util.ArrayList;
import java.util.List;
public class Checks {

	public static void main(String[] args) {
		List<String> lis=new ArrayList<String>();
		System.out.println(lis.getClass().getName());
		System.out.println(List.class.isAssignableFrom(ArrayList.class));

	}

}
