package com.mkyong;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mkyong.dto.BarChart;
import com.mkyong.dto.PieChart;
import com.mkyong.dto.SaleData;

public class ReadWriteExcelFile {

	/**
	 * 
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public static PieChart reportPieData(ReportType type) throws IOException {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1)
				+ "src/main/resources/static/file/Store_Sale.xlsx";
		Iterator<Row> rows = getRows(fileLocation);

		Map<String, SaleData> dataMap = new HashMap<String, SaleData>();

		// skipping headers
		if (rows.hasNext())
			rows.next();

		if (type == ReportType.PRODUCT) {
			productWiseData(dataMap, rows);
		} else if (type == ReportType.YEAR) {
			yearWiseData(dataMap, rows);
		}

		PieChart pieChart = new PieChart();
		List<String> labels = new ArrayList<>();
		List<Double> values = new ArrayList<>();
		for (String key : dataMap.keySet()) {
			labels.add(key +" ("+round(dataMap.get(key).getSaleValue(),2)+")");
			values.add(round(dataMap.get(key).getSaleValue(),2));
		}
		pieChart.setLabels(labels);
		pieChart.setSeries(values);
		return pieChart;
	}

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public static BarChart reportBarData(ReportType type) throws IOException {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1)
				+ "src/main/resources/static/file/Store_Sale.xlsx";
		Iterator<Row> rows = getRows(fileLocation);

		Map<String, SaleData> dataMap = new HashMap<String, SaleData>();

		// skipping headers
		if (rows.hasNext())
			rows.next();

		if (type == ReportType.PRODUCT) {
			productWiseData(dataMap, rows);
		} else if (type == ReportType.YEAR) {
			yearWiseData(dataMap, rows);
		}

		BarChart barChart = new BarChart();
		List<String> labels = new ArrayList<>();
		List<Double> values = new ArrayList<>();
		for (String key : dataMap.keySet()) {
			labels.add(key);
			values.add(dataMap.get(key).getSaleValue());
		}
		List<List<Double>> series = new ArrayList<>();
		series.add(values);
		barChart.setLabels(labels);
		barChart.setSeries(series);
		return barChart;
	}
	
	private static Iterator<Row> getRows(String fileLocation)
			throws FileNotFoundException, IOException {

		String inputFilename = new File(fileLocation).getName();

		switch (inputFilename.substring(inputFilename.lastIndexOf(".") + 1,
				inputFilename.length())) {
		case "xls":
			return readXLS(fileLocation);

		case "xlsx":
			return readXLSX(fileLocation);
		}
		return null;

	}

	private static Iterator<Row> readXLS(String inputFilename)
			throws IOException {

		InputStream ExcelFileToRead = new FileInputStream(inputFilename);

		@SuppressWarnings("resource")
		HSSFSheet sheet = new HSSFWorkbook(ExcelFileToRead).getSheetAt(0);

		Iterator<Row> rows = sheet.rowIterator();
		return rows;

	}

	private static Iterator<Row> readXLSX(String inputFilename)
			throws IOException {
		InputStream ExcelFileToRead = new FileInputStream(inputFilename);

		@SuppressWarnings("resource")
		XSSFSheet sheet = new XSSFWorkbook(ExcelFileToRead).getSheetAt(0);

		Iterator<Row> rows = sheet.rowIterator();
		return rows;
	}

	/**
	 * 
	 * @param dataMap
	 * @param cells
	 * @param productCategory
	 * @param saleValue
	 */
	private static void productWiseData(Map<String, SaleData> dataMap,
			Iterator<Row> rows) {

		double totalSaleValue = 0.0;
		while (rows.hasNext()) {
			Row row = (Row) rows.next();
			Iterator<Cell> cells = row.cellIterator();

			Cell cell;
			String productCategory = null;
			double saleValue = 0.0;
			while (cells.hasNext()) {
				cell = (Cell) cells.next();
				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING
						&& cell.getColumnIndex() == 15) {
					productCategory = cell.getStringCellValue();
					// System.out.print(cell.getStringCellValue()+" ");
				} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC
						&& cell.getColumnIndex() == 5) {
					saleValue = cell.getNumericCellValue();
				}
			}
			totalSaleValue+=saleValue;
			if (productCategory != null) {
				// System.out.println("Got product category:"+productCategory
				// +", sale price:"+saleValue);
				SaleData saleData = dataMap.get(productCategory);
				if (dataMap.get(productCategory) == null) {
					saleData = new SaleData();
				}
				saleData.setProductCategory(productCategory);
				saleData.addSaleValue(saleValue);
				dataMap.put(productCategory, saleData);
			} else {
				System.out.println("Didn't find any productCategory");
			}
		}
		
		for (String key : dataMap.keySet()) {
			SaleData saleData = dataMap.get(key);
			saleData.setSaleValue((saleData.getSaleValue()/totalSaleValue)*100);
			dataMap.put(key, saleData);
		}

	}

	/**
	 * 
	 * @param dataMap
	 * @param cells
	 * @param productCategory
	 * @param saleValue
	 */
	private static void yearWiseData(Map<String, SaleData> dataMap,
			Iterator<Row> rows) {

		while (rows.hasNext()) {
			Row row = (Row) rows.next();
			Iterator<Cell> cells = row.cellIterator();
			try {
				Cell cell;
				Date saleDate = null;
				Calendar cal = Calendar.getInstance();
				double saleValue = 0.0;
				while (cells.hasNext()) {
					cell = (Cell) cells.next();
					if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC
							&& cell.getColumnIndex() == 2) {
						saleDate = cell.getDateCellValue();
						cal.setTime(saleDate);
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC
							&& cell.getColumnIndex() == 5) {
						saleValue = cell.getNumericCellValue();
					}
				}

				if (saleDate != null) {
					// System.out.println("Got product category:"+productCategory
					// +", sale price:"+saleValue);
					String year = String.valueOf(cal.get(Calendar.YEAR));
					SaleData saleData = dataMap.get(year);
					if (dataMap.get(year) == null) {
						saleData = new SaleData();
					}
					saleData.setYear(year);
					saleData.addSaleValue(saleValue);
					dataMap.put(year, saleData);
				} else {
					System.out.println("Didn't find any date");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * public static void main(String[] args) { try {
	 * System.out.println(reportData(ReportType.YEAR)); } catch (IOException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); } }
	 */
}

enum ReportType {
	PRODUCT, YEAR
}
