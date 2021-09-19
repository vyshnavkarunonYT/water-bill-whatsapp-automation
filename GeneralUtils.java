package utils;

import java.util.Arrays;
import java.util.List;

public class GeneralUtils<T extends Number> {
	public static void print2DList(List<String[]>list) {
		for(int i =0; i<list.size(); i++) {
			for(int j =0; j<list.get(i).length; j++) {
				System.out.printf("%-40s",list.get(i)[j]);
			}
			System.out.println();
		}
	}
	
	public static double sumDoubleArray(double[]array) {
		double total = 0;
		for(int i =0; i<array.length; i++) {
			total+=array[i];
		}
		return total;
		
	}
}
