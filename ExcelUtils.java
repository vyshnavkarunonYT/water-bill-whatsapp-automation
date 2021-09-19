package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOException;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@SuppressWarnings("deprecation")
public class ExcelUtils {
	
	//CONSTANTS
	
	int MONTH_COL_NUMBER = 0;
	int HOUSE_COL_NUMBER = 1;
	int CONSUMPTION_COL_NUMBER = 2;
	int METER_FACTOR_COL_NUMBER = 3;
	int CORRECTED_CONSUMPTION_COL_NUMBER = 5;
	
	static XSSFWorkbook workbook;
	static XSSFSheet waterSheet;
	static XSSFSheet electricalSheet;
	static XSSFSheet nameSheet;
	static XSSFSheet quickSendSheet;
	
	
	public ExcelUtils() throws IOException {
		
	}
	
	public ExcelUtils(String excelPath) throws IOException{
		try {
			workbook = new XSSFWorkbook(excelPath);
			waterSheet = workbook.getSheetAt(0);
			electricalSheet = workbook.getSheetAt(1);
			nameSheet = workbook.getSheetAt(2);
			quickSendSheet = workbook.getSheetAt(3);
		} catch(Exception ex) {
			System.out.println(ex.getStackTrace());
		}
	}
	
	
	public void getRowCount() {
		System.out.println("The number of rows in the waterSheet are " + waterSheet.getPhysicalNumberOfRows()+"\n");
	}
	
	
	public Cell getCell(int row, int column) throws IOException {
		Cell cell = waterSheet.getRow(row).getCell(column);
		return cell;
	}
	
	public void printCellData(int row, int column) throws IOException {
		String sheetName = "Water";
		printCellData(sheetName,row,column);
	}
	
	
	public void printCellData(String sheetName, int row, int column) throws IOException {
		XSSFSheet sheet = workbook.getSheet(sheetName);
		DataFormatter formatter = new DataFormatter();
		Object object = formatter.formatCellValue(sheet.getRow(row).getCell(column));
		System.out.println(object+"\n");
	}
	
	public void printSheetData(String sheetName) {
		XSSFSheet sheet = waterSheet;
		//Assigning sheet depending on sheet type
		if(sheetName.equals("Electrical")) {
			sheet = electricalSheet;
		}
		
		Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
		while (itr.hasNext())                 
		{  
		Row row = itr.next();  
		Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
		while (cellIterator.hasNext())   
		{  
		Cell cell = cellIterator.next();  
		switch (cell.getCellType())               
		{  
		case STRING:    //field that represents string cell type  
		String fullString = cell.getStringCellValue();
			if(fullString.length()>20)
				{
					String[]splitString = fullString.split(" ");
					String abbreviated = "";
					for(int i = 0; i<splitString.length; i++) {
						abbreviated+=splitString[i].charAt(0);
					}
					System.out.printf("%-40s", abbreviated);
				}
			else {
				System.out.printf("%-40s", fullString);
			}


		break;  
		case NUMERIC:    //field that represents number cell type  
		{
			if(HSSFDateUtil.isCellDateFormatted(cell))
			{
				System.out.printf("%-40s",
						(cell.getDateCellValue().toString().substring(4,7)
						+cell.getDateCellValue().toString().substring(cell.getDateCellValue().toString().length()-4))
						);
			} else {
			System.out.printf( "%-40s", (cell.getNumericCellValue()+""));  
			}
			break;
		}
		case FORMULA:
			System.out.printf( "%-40s", (cell.getNumericCellValue()+""));  
			
			
		default:  
		}  
		}  
		System.out.println("");  
	}
	}
	
	
	//Returns a List of Arrays which contain - Month CorrectedConsumption HouseName
	public List<String[]> getCorrectedConsumptionHistory() throws IOException{
		List<String[]> dataSet = new ArrayList<String[]>();
		Iterator<Row> rowIterator = waterSheet.iterator();
		int itr = 0;
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			DataFormatter formatter = new DataFormatter();
			Object object = formatter.formatCellValue(waterSheet.getRow(itr).getCell(HOUSE_COL_NUMBER));
			if(!object.equals("House") && !object.equals("")) {
				//System.out.println(waterSheet.getRow(itr).getCell(3).getNumericCellValue());
				String [] data = new String[3];
				data[0] = formatter.formatCellValue(waterSheet.getRow(itr).getCell(MONTH_COL_NUMBER));
				data[1] = this.getCell(itr, CORRECTED_CONSUMPTION_COL_NUMBER).getNumericCellValue()+"";
				data[2] = (String) object;
				dataSet.add(data);
			}
			itr++;
		}
		return dataSet;
	}
	
	
	//Returning a List of arrays of date and corrected consumption history of a particular house by name
	public List<String[]> getCorrectedConsumptionHistoryByHouse(String name){
		List<String[]> dataSet = new ArrayList<String[]>();
		Iterator<Row> rowIterator = waterSheet.iterator();
		int itr = 0;
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			DataFormatter formatter = new DataFormatter();
			Object object = formatter.formatCellValue(waterSheet.getRow(itr).getCell(HOUSE_COL_NUMBER));
			if(object.toString().equals(name)) {
				//System.out.println(waterSheet.getRow(itr).getCell(3).getNumericCellValue());
				String [] data = new String[3];
				data[0] = formatter.formatCellValue(waterSheet.getRow(itr).getCell(MONTH_COL_NUMBER));
				data[1] = waterSheet.getRow(itr).getCell(CORRECTED_CONSUMPTION_COL_NUMBER).getNumericCellValue()+"";
				data[2] = name;
				dataSet.add(data);
			}
			itr++;
		}
		
		return dataSet;
	}

	//Gets Corrected Consumption of A House for a specified Month
	public double getCorrectedConsumptionHistory(String month, String house) throws IOException {
		// TODO Auto-generated method stub
		Iterator<Row> rowIterator = waterSheet.iterator();
		int itr = 0;
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			DataFormatter formatter = new DataFormatter();
			Object houseObj = formatter.formatCellValue(waterSheet.getRow(itr).getCell(HOUSE_COL_NUMBER));
			Object monthObj = formatter.formatCellValue(waterSheet.getRow(itr).getCell(MONTH_COL_NUMBER));
			if(houseObj.equals(house) && !houseObj.equals("") && monthObj.equals(month)) {
				//System.out.println(waterSheet.getRow(itr).getCell(3).getNumericCellValue());
//				System.out.println(house+"	"+monthObj);
				System.out.println("Crctd consumption \t\t\t" +
				waterSheet.getRow(itr).getCell(CORRECTED_CONSUMPTION_COL_NUMBER).getNumericCellValue());
				
				return waterSheet.getRow(itr).getCell(CORRECTED_CONSUMPTION_COL_NUMBER).getNumericCellValue();
			}
			itr++;
		}
		System.out.println("No such entry found!");
		return -1.0;
	}
	
	//Get total corrected consumption for a month
	public double getTotalConsumptionByMonth(String month) throws IOException {
		Iterator<Row> rowIterator = waterSheet.iterator();
		int itr = 0;
		double totalConsumption = 0;
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			DataFormatter formatter = new DataFormatter();
			Object houseObj = formatter.formatCellValue(waterSheet.getRow(itr).getCell(HOUSE_COL_NUMBER));
			Object monthObj = formatter.formatCellValue(waterSheet.getRow(itr).getCell(MONTH_COL_NUMBER));

			if(monthObj.equals(month)) {
				//System.out.println(waterSheet.getRow(itr).getCell(3).getNumericCellValue());
//				System.out.println("Corrected consumption for the month is " +
//				waterSheet.getRow(itr).getCell(CORRECTED_CONSUMPTION_COL_NUMBER).getNumericCellValue());
				totalConsumption+= waterSheet.getRow(itr).getCell(CORRECTED_CONSUMPTION_COL_NUMBER).getNumericCellValue();
			}
			itr++;
		}
		
		return totalConsumption;
		
	}
	
	
	
	//Getting the total pumping charge for a month
	public double getPumpingChargeByMonth(String month) {
		Iterator<Row> rowIterator = waterSheet.iterator();
		int itr = 0;
		double pumpingCharge = 0.0;
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			DataFormatter formatter = new DataFormatter();
			Object monthObj = formatter.formatCellValue(electricalSheet.getRow(itr).getCell(0));

			if(monthObj.equals(month)) {
				//System.out.println(waterSheet.getRow(itr).getCell(3).getNumericCellValue());
//				System.out.println("Corrected consumption for the month is " +
//				waterSheet.getRow(itr).getCell(CORRECTED_CONSUMPTION_COL_NUMBER).getNumericCellValue());
				pumpingCharge= electricalSheet.getRow(itr).getCell(1).getNumericCellValue();
//				System.out.println("Extracted Pumping charge is " + pumpingCharge);
				return pumpingCharge;
			}
			itr++;
		}
		
		
		return pumpingCharge;
	}

	public String[] getHouseOccupantNames() {
		// TODO Auto-generated method stub
		String names[] = new String[5];
		Iterator<Row> rowIterator = nameSheet.iterator();
		int index = 0;
		
		//Getting rid of heading row
		rowIterator.next();
		
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			DataFormatter formatter = new DataFormatter();
			Object name = formatter.formatCellValue(nameSheet.getRow(index+1).getCell(1));
//			System.out.println(name);
			names[index] = name.toString();
			index++;
		}
		return names;
	}

	public int[] getQuickSendPrevConsumption() {
		// TODO Auto-generated method stub
		int consumption[] = new int[5];
		Iterator<Row> rowIterator = quickSendSheet.iterator();
		int index =0;
		

		//Getting rid of heading row
		rowIterator.next();
		
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			DataFormatter formatter = new DataFormatter();
			Object val = formatter.formatCellValue(quickSendSheet.getRow(index+1).getCell(2));
			if(index<5) {
				consumption[index] = Integer.parseInt(val.toString());
			}
			index++;
		}
		return consumption;
	}

	public double[] getQuickSendMeterFactor() {
		// TODO Auto-generated method stub
		double meterFactor[] = new double[5];
		Iterator<Row> rowIterator = quickSendSheet.iterator();
		int index =0;
		

		//Getting rid of heading row
		rowIterator.next();
		
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			DataFormatter formatter = new DataFormatter();
			Object val = formatter.formatCellValue(quickSendSheet.getRow(index+1).getCell(3));
			if(index<5) {
				meterFactor[index] = Double.parseDouble(val.toString());
			}
			index++;
		}
		return meterFactor;
	}

	public int getPumpingChargeQS() {
		// TODO Auto-generated method stub
		DataFormatter formatter = new DataFormatter();
		Object val = formatter.formatCellValue(quickSendSheet.getRow(1).getCell(5));
		return Integer.parseInt(val.toString());
	}


	
	
}
