package Resoureces;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class ReadExcelData {

	private static final String FILE_PATH = "C:\\Users\\ankushkumar.singh\\Downloads\\Excel.xlsx";

	// Method to read data from a specified sheet
	private static Object[][] getTestDataFromExcel(String sheetName) throws IOException {
		FileInputStream fileInputStream = null;
		XSSFWorkbook workbook = null;

		try {
			fileInputStream = new FileInputStream(FILE_PATH);
			workbook = new XSSFWorkbook(fileInputStream);

			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new IllegalArgumentException("Sheet not found: " + sheetName);
			}

			int rowCount = sheet.getLastRowNum() + 1; // Get the number of rows (including header)
			int colCount = sheet.getRow(0).getLastCellNum(); // Get the number of columns from the first row

			Object[][] data = new Object[rowCount - 1][colCount]; // Create the data array (excluding header row)

			for (int i = 1; i < rowCount; i++) { // Loop through rows (skip header)
				Row row = sheet.getRow(i);
				if (row == null)
					continue; // Skip if the row is null

				for (int j = 0; j < colCount; j++) { // Loop through columns
					Cell cell = row.getCell(j);
					Object cellValue = null;

					if (cell != null) {
						switch (cell.getCellType()) {
						case STRING:
							cellValue = cell.getStringCellValue(); // Handle string cells
							break;
						case NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								cellValue = cell.getDateCellValue(); // Handle date cells
							} else {
								cellValue = cell.getNumericCellValue(); // Handle numeric cells
							}
							break;
						case BOOLEAN:
							cellValue = cell.getBooleanCellValue(); // Handle boolean cells
							break;
						case FORMULA:
							DataFormatter dataFormatter = new DataFormatter();
							cellValue = dataFormatter.formatCellValue(cell); // Handle formula cells
							break;
						default:
							cellValue = cell.toString(); // Handle other types (e.g., errors)
							break;
						}
					}
					data[i - 1][j] = cellValue; // Store data in the array
				}
			}
			return data;

		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace(); // Handle potential IOExceptions during closing
				}
			}
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace(); // Handle potential IOExceptions during closing
				}
			}
		}
	}
	
	@DataProvider(name = "multiData")
	public static List<Map<String,String>> getTestDetails(String sheetname) throws Exception{
		List<Map<String,String>> list = null;
		
		String value = null;
		final String FILE_PATH = "C:\\Users\\vchafle\\OneDrive - Planit Test Management Solutions Pty Ltd\\Documents\\Excel.xlsx";
		FileInputStream fs=new FileInputStream(FILE_PATH);
		
		
		
		try {
			
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(fs);
			XSSFSheet sheet = workbook.getSheet(sheetname);
 
			int lastrownum = sheet.getLastRowNum();
			int lastcolnum = sheet.getRow(0).getLastCellNum();
			
 
			Map<String,String> map =null;
			list = new ArrayList<>();
 
			for(int i=1; i<=lastrownum;i++) {
				map = new HashMap<>();
				for(int j=0;j<lastcolnum;j++) {
					String key= sheet.getRow(0).getCell(j).getStringCellValue();
					//String value = sheet.getRow(i).getCell(j).getStringCellValue();
					Cell cell= sheet.getRow(i).getCell(j,MissingCellPolicy.CREATE_NULL_AS_BLANK);
					switch (cell.getCellType()) {
					case STRING:
						value=sheet.getRow(i).getCell(j).getStringCellValue();
						break;
					case NUMERIC:
						value=String.valueOf((long) sheet.getRow(i).getCell(j).getNumericCellValue());
						break;
					case BOOLEAN:
						value= Boolean.toString(sheet.getRow(i).getCell(j).getBooleanCellValue());
						break;
					case BLANK:
						value="";
						break;
 
					default:
						break;
					}
					map.put(key, value);
				}
				list.add(map);
			}
 
	}catch (FileNotFoundException e) {
			throw new Exception("Excel File you trying to read is not found");
		} catch (IOException e) {
			throw new Exception("Some io exception happened  while reading excel file");
		}
		finally {
			try {
				if(Objects.nonNull(fs)) {
					fs.close();
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	

	@DataProvider(name = "getLoginData")
	public static Object[][] getLoginData() throws IOException {
		return getTestDataFromExcel("Login"); // Pass the sheet name directly
	}

	@DataProvider(name = "getLeadData")
	public static Object[][] getLeadData() throws IOException {
		return getTestDataFromExcel("Leads"); // Pass the sheet name directly
	}

	@DataProvider(name = "ApplicationData1")
	public static Object[][] ApplicationData1() throws IOException {
		return getTestDataFromExcel("CAP1"); // Pass the sheet name directly
	}

	@DataProvider(name = "ApplicationData2")
	public static Object[][] ApplicationData2() throws IOException {
		return getTestDataFromExcel("CAP2"); // Pass the sheet name directly
	}

	@DataProvider(name = "ApplicationData4")
	public static Object[][] ApplicationData4() throws IOException {
		return getTestDataFromExcel("CAP4"); // Pass the sheet name directly
	}

	@DataProvider(name = "KYCaddress")
	public static Object[][] KYCaddress() throws IOException {
		return getTestDataFromExcel("KYC"); // Pass the sheet name directly
	}

	@DataProvider(name = "ecoDetails")
	public static Object[][] ecoDetails() throws IOException {
		return getTestDataFromExcel("SED"); // Pass the sheet name directly
	}

	@DataProvider(name = "AssetDetails")
	public static Object[][] AssetDetails() throws IOException {
		return getTestDataFromExcel("Asset"); // Pass the sheet name directly
	}
}
