package continuum.cucumber;




import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.runners.model.TestClass;
import org.testng.Reporter;

/*********
 * Utility to read values from properties file
 * @author sneha.chemburkar
 *
 */


public class Utilities {
	static HashMap<String,String> values=new HashMap<String,String>();
	static String sheetName="test";
	
	/**
	 * @param key
	 * @return value for specified key
	 */
	
	public static String  getMavenProperties(String key){

		String value=null;
		Properties mavenProps = new Properties();
		String prop = System.getProperty("jenkins");
		//  System.out.println("Jenkins value"+prop);
		if(prop==null)
		{
			//System.out.println("Executing on local machine");
		}
		else if(prop.equalsIgnoreCase("true"))
			value=System.getProperty(key);

		if(value==null)
		{ 	 
			InputStream inStream = TestClass.class.getResourceAsStream("/maven.properties");
			try {
				mavenProps.load(inStream);
			} catch (IOException e) {
				System.out.println("***Not able to load propeties from maven");
				e.printStackTrace();
			}
			value= mavenProps.getProperty(key);
		}

		return value;
	}
	
	
	
	public static HashMap<String,String> readValidationsFromExcelFile(String excelFileName,String TestCaseID)  {
		
		sheetName=TestCaseID;
		 String excelFilePath = new File("").getAbsolutePath()+"\\src\\test\\resources\\Data\\"+excelFileName;
		 System.out.println("Reading validation points from excel file "+excelFileName);
		try{
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	    XSSFRow row;
	    XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
	//    System.out.println("workbook created "+workbook);
	    XSSFSheet spreadsheet = workbook.getSheet(TestCaseID);
	      Iterator < Row > rowIterator = spreadsheet.iterator();
	      while (rowIterator.hasNext()) 
	      {
	         row = (XSSFRow) rowIterator.next();
	         Iterator < Cell > cellIterator = row.cellIterator();
	         while ( cellIterator.hasNext()) 
	         {
	            Cell cellKey = row.getCell(0);
	            Cell cellValue=row.getCell(1);
	            //System.out.println("Key :"+getCellValue(cellKey).toString()+"    Value:"+getCellValue(cellValue).toString());
	            values.put(getCellValue(cellKey).toString(), getCellValue(cellValue).toString());
	            cellKey = cellIterator.next();
	            cellValue = cellIterator.next();
	         } 
	        

	      }
	    
	   
	    
	    workbook.close();
	    inputStream.close();
		 }catch (Exception e)
	    {
	    	System.out.println("Not able to read data from Excelsheet at  "+excelFilePath);
	    	Reporter.log("Not able to read data from Excelsheet at  "+excelFilePath);
	    	e.printStackTrace();
	    }
	    return values;
	}
	
	public static HashMap<String,String> readValidationsFromExcelFile(String excelFileName)  {
		 
		String excelFilePath = new File("").getAbsolutePath()+"\\src\\test\\resources\\Data\\"+excelFileName;
		 System.out.println("Reading validation points from excel file "+excelFileName);
		try{
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	    XSSFRow row;
	    XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
	//    System.out.println("workbook created "+workbook);
	    XSSFSheet spreadsheet = workbook.getSheetAt(0);
	      Iterator < Row > rowIterator = spreadsheet.iterator();
	      while (rowIterator.hasNext()) 
	      {
	         row = (XSSFRow) rowIterator.next();
	         Iterator < Cell > cellIterator = row.cellIterator();
	         while ( cellIterator.hasNext()) 
	         {
	            Cell cellKey = row.getCell(0);
	            Cell cellValue=row.getCell(1);
	            //System.out.println("Key :"+getCellValue(cellKey).toString()+"    Value:"+getCellValue(cellValue).toString());
	            values.put(getCellValue(cellKey).toString(), getCellValue(cellValue).toString());
	            cellKey = cellIterator.next();
	            cellValue = cellIterator.next();
	         } 
	        

	      }
	    
	   
	    
	    workbook.close();
	    inputStream.close();
		 }catch (Exception e)
	    {
	    	System.out.println("Not able to read data from Excelsheet at  "+excelFilePath);
	    	Reporter.log("Not able to read data from Excelsheet at  "+excelFilePath);
	    	e.printStackTrace();
	    }
	    return values;
	}

	private static Object getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();
	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	    }
	 
	    return null;
	}
	
	
	/**
	 * @param excelFileName
	 * @param TestCaseID
	 * @param key
	 * @return vlaue
	 * return value against specified key in excel for specified sheet or test case id
	 */
	public static String getKeyValue( String excelFileName,String TestCaseID,String key){
		
		if(values.isEmpty() || !sheetName.equalsIgnoreCase(TestCaseID))
		   
		 {values=readValidationsFromExcelFile(excelFileName,TestCaseID);}
		
		String value= values.get(key);
		 System.out.println("Key :"+key+"    Value:"+value);
		return value;
		
	}
	
	/**
	 * @param excelFileName
	 * @param key
	 * @returns value against specified key in excel
	 */
	public static String getKeyValue( String excelFileName,String key){
		if(values.isEmpty())		
		 {values=readValidationsFromExcelFile(excelFileName);}
		
		String value= values.get(key);
		 System.out.println("Key :"+key+"    Value:"+value);
		return value;
		
	}
	
	/**
	 * @param str
	 * @return string array
	 * split String for ;
	 */
	public static String[] splitString(String str){
		String[] value= null;
	if(str.contains(";"))
		value=str.split(";");
	return value;
		
		
	}


}

