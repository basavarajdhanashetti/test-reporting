package com.mkyong;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class ReadWriteExcelFile {

	
	@SuppressWarnings("rawtypes")
	public String readXLSXFileString() 
	{
		InputStream ExcelFileToRead = null;
		XSSFWorkbook  wb = null;
		Map<String, Double> productWiseSale = new HashMap<String, Double>();
		String str = null;
		try{
			
	    File currDir = new File(".");
		String path = currDir.getAbsolutePath();
	    String fileLocation = path.substring(0, path.length() - 1) + "src/main/resources/static/file/";
		ExcelFileToRead = new FileInputStream(fileLocation + "/Store_Sale.xlsx");
		wb = new XSSFWorkbook(ExcelFileToRead);
		
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();
		Double totalSale = 0.0;
		while (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			String productCategory = null;
			double saleValue = 0.0;
			while (cells.hasNext())
			{
				cell=(XSSFCell) cells.next();
				boolean isDataExist = false;
						
				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING && cell.getColumnIndex() == 10 && cell.getRowIndex() >0)
				{
					productCategory = cell.getStringCellValue();
					isDataExist = true;
					//System.out.print(cell.getStringCellValue()+" ");
				}
				else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC  && cell.getColumnIndex() == 5  && cell.getRowIndex() >0)
				{
					saleValue = cell.getNumericCellValue();
					totalSale +=saleValue;
				}
				
				if(isDataExist){
					if(productWiseSale.containsKey(productCategory)){
						productWiseSale.put(productCategory, productWiseSale.get(productCategory) + saleValue);
					}else{
		
						productWiseSale.put(productCategory, saleValue);
					}
				}
				
			}
		}
		
		StringBuffer buffer = new StringBuffer("[");
		
		for(Map.Entry<String, Double> value : productWiseSale.entrySet()){
			
			buffer = buffer.append("{y:" + ((value.getValue()/totalSale)*100) +", label: \""+value.getKey()+ "\"},");
			
		}
		 str = buffer.substring(0, buffer.lastIndexOf(",")) + "]";
		
		
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(wb != null){
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return str;
	}
		
	@SuppressWarnings("rawtypes")
	public Map<String, Double> readXLSXFile() 
	{
		InputStream ExcelFileToRead = null;
		XSSFWorkbook  wb = null;
		Map<String, Double> productWiseSale = new HashMap<String, Double>();
		
		try{
			
	    File currDir = new File(".");
		String path = currDir.getAbsolutePath();
	    String fileLocation = path.substring(0, path.length() - 1) + "src/main/resources/static/file/";
		ExcelFileToRead = new FileInputStream(fileLocation + "/Store_Sale.xlsx");
		wb = new XSSFWorkbook(ExcelFileToRead);
		
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			String productCategory = null;
			double saleValue = 0.0;
			while (cells.hasNext())
			{
				cell=(XSSFCell) cells.next();
				boolean isDataExist = false;
						
				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING && cell.getColumnIndex() == 10 && cell.getRowIndex() >0)
				{
					productCategory = cell.getStringCellValue();
					isDataExist = true;
					//System.out.print(cell.getStringCellValue()+" ");
				}
				else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC  && cell.getColumnIndex() == 5  && cell.getRowIndex() >0)
				{
					saleValue = cell.getNumericCellValue();
				}
				
				if(isDataExist){
					if(productWiseSale.containsKey(productCategory)){
						productWiseSale.put(productCategory, productWiseSale.get(productCategory) + saleValue);
					}else{
		
						productWiseSale.put(productCategory, saleValue);
					}
				}
				
			}
		}

		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(wb != null){
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return productWiseSale;
	}
	
}
