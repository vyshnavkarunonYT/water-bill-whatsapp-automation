package utils;

import java.awt.Color;
import java.awt.FileDialog;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class UtilsTest {
	public static void main(String[]args) throws InterruptedException {
		try {
			
			//Default excelPath
			String excelPath = "./data/WaterData.xlsx";
			
			//Opening the File Explorer through a dialog bog box and getting the excel file.
			FileDialog fd = new FileDialog(new JFrame());
			fd.setFile("*.xlsx");
			fd.setVisible(true);
			File file[] = fd.getFiles();
			if(file.length>0) {
				//Setting excel path to value selected through dialog box
				excelPath = file[0].getAbsolutePath();
			}
			System.out.println("The path is "+excelPath);
			
			
			ExcelUtils eu = new ExcelUtils(excelPath);

			
			

			//Printing a bar chart of all the units of water consumed by first floor
			String houseName = "First Floor";
			List<String[]>  cc_hist_house = eu.getCorrectedConsumptionHistoryByHouse(houseName);
			List<String[]> cc_hist_all = eu.getCorrectedConsumptionHistory();  
			
			//Printing 2D List Details
//			CategoryDataset datasetAll = GraphUtils.createDataSet(cc_hist_all); 
//			GraphUtils.BarChart("Water Consumption History", "Consumption History of All Houses", datasetAll);
			
			
			//Drawing the bar graph
//			CategoryDataset datasetHouse = GraphUtils.createDataSet(cc_hist_house); 
//			GraphUtils.BarChart("Water Consumption History", "Consumption History of " +houseName, datasetHouse);
			

			//Bill Calculation with Actual Water Bill
//			String firstFloorTotal = BillUtils.getMonthBillByHouseWithActualWaterBill(eu, "Feb-21", "First Floor",299);
//			String secondFloorLastTotal = BillUtils.getMonthBillByHouseWithActualWaterBill(eu, "Feb-21", "Second Floor Last",299);
			
			
			//Bill Calculation With Generated Water Bill
			String firstFloorTotal = BillUtils.getMonthBillByHouse(eu, "Apr-21", "First Floor");
			String secondFloorLastTotal = BillUtils.getMonthBillByHouse(eu, "Apr-21", "Second Floor Last");
			String groundFloorTotal = BillUtils.getMonthBillByHouse(eu, "Apr-21", "Ground Floor");
			
			//Whatsapp
			WhatsappUtils.Authenticate();
			WhatsappUtils.sendMessage("Soma", firstFloorTotal);
			WhatsappUtils.sendMessage("Amit Kumar 2ndFloor Sw", secondFloorLastTotal);
			WhatsappUtils.sendMessage("Vinodkumar GF", groundFloorTotal);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

//Vinodkumar GF 
//Amit Kumar 2ndFloor SW
//Soma 
