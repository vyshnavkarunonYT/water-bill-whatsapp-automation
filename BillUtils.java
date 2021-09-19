/*	SLABS 
 *  1-8 - 7
 *  8-25 - 11
 *  25-50 - 26
 *  50+
 */



package utils;

import java.io.IOException;


public class BillUtils {
	public static String getMonthBillByHouse(ExcelUtils eu, String month, String house) throws IOException {
		
		System.out.println("\n\nWater bill for "+house + " for the month "+ month +"\n");
		
		//Calculating Water Charge
		double consumption = eu.getCorrectedConsumptionHistory(month, house);
		double totalConsumption = eu.getTotalConsumptionByMonth(month);
		double totalBill = calculateBill(totalConsumption);
		double houseWaterCharge = (consumption/totalConsumption)*totalBill;
		System.out.print("Water charge \t\t\t\t");
		System.out.print( houseWaterCharge +"\n");
		
		
		//Calculating Pumping Charge
		double pumpingCharge = eu.getPumpingChargeByMonth(month);
		double housePumpingCharge = (consumption/totalConsumption)*pumpingCharge;
		System.out.print("Electrical charge \t\t\t");
		System.out.println(housePumpingCharge+"\n");
		
		//Printing the total Bill
		System.out.println("Total Bill\t\t\t\t" + Math.round(houseWaterCharge+housePumpingCharge));
		return "Your monthly bill for " + month + " is " + Math.round(houseWaterCharge+housePumpingCharge);
		
	}
	
	
	
	public static String getMonthBillByHouseWithActualWaterBill(ExcelUtils eu, String month, String house, double actualBill) throws IOException {
		//Calculating Water Charge
		double consumption = eu.getCorrectedConsumptionHistory(month, house);
		double totalConsumption = eu.getTotalConsumptionByMonth(month);
		double houseWaterCharge = (consumption/totalConsumption)*actualBill;
		System.out.print("Water charge \t\t\t\t");
		System.out.print( houseWaterCharge +"\n");
		
		
		//Calculating Pumping Charge
		double pumpingCharge = eu.getPumpingChargeByMonth(month);
		double housePumpingCharge = (consumption/totalConsumption)*pumpingCharge;
		System.out.print("Electrical charge \t\t\t");
		System.out.println(housePumpingCharge+"\n");
		
		//Printing the total Bill
		System.out.println("Total Bill\t\t\t\t" + Math.round(houseWaterCharge+housePumpingCharge));
		return "Water bill for " + month + " is Rs. " + Math.round(houseWaterCharge+housePumpingCharge);
	}
	
	
	public static void getTotalBillByMonth(ExcelUtils eu, String month) throws IOException {
		double totalConsumption = eu.getTotalConsumptionByMonth(month);
		calculateBill(totalConsumption);
	}
	
	
	public static double calculateBill(double consumption) {

		double u = consumption/1000;
		double waterCharge = Math.min(8.0, u) * 7 + Math.max( (Math.min(u, 25.0) -8.0)*11,0)
		+ Math.max( (Math.min(u, 50.0) -25.0)*26.0,0);
		double meterCharge = 0;
		if(u<=8) {
			meterCharge = 30;
		} else if(u<=25) {
			meterCharge = 50;
		} else if(meterCharge<=50) {
			meterCharge = 75;
		} else {
			meterCharge = 100;
		}
		double sanitaryCharge = 0.25*waterCharge;
//		System.out.println("Total charge\t\t\t\t" + (waterCharge+sanitaryCharge+meterCharge)+"\n\n");
		return waterCharge+sanitaryCharge+meterCharge;
	}
	
	public static double printBill(double consumption) {
		System.out.println("Water Bill");
		System.out.println("Consumption in litres\t\t\t"+consumption);
		double u = consumption/1000;
		double waterCharge = Math.min(8.0, u) * 7 + Math.max( (Math.min(u, 25.0) -8.0)*11,0)
		+ Math.max( (Math.min(u, 50.0) -25.0)*26.0,0);
		double meterCharge = 0;
		System.out.println("Water Charge \t\t\t\t"+ waterCharge);
		
		if(u<=8) {
			meterCharge = 30;
		} else if(u<=25) {
			meterCharge = 50;
		} else if(meterCharge<=50) {
			meterCharge = 75;
		} else {
			meterCharge = 100;
		}
		System.out.println("Meter charge\t\t\t\t" + meterCharge);
		
		double sanitaryCharge = 0.25*waterCharge;
		
		System.out.println("Sanitary charge\t\t\t\t" + sanitaryCharge);
		
		System.out.println("Total charge\t\t\t\t" + (waterCharge+sanitaryCharge+meterCharge)+"\n\n");
		return waterCharge+sanitaryCharge+meterCharge;
	}
}
