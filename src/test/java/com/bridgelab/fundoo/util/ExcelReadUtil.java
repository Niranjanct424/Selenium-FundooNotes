package com.bridgelab.fundoo.util;

import java.io.File;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReadUtil {
  public static String[][] readExcelInto2DArray(String excelFilePath, 
		  String sheetName, int totalCols) {

    File file = new File(excelFilePath);

    String[][] tabArray = null;

    try {
    	
//    OPCPackage which is helpful to read xl based data
      OPCPackage opcPackage = OPCPackage.open(file.getAbsolutePath());

//      workbook is a java object which contains data about all the sheets
      Workbook wb = WorkbookFactory.create(opcPackage);

//      we are getting specific sheet
      Sheet sheet = wb.getSheet(sheetName);

      
      int totalRows = sheet.getLastRowNum() + 1;

//      reading using two-dimensional array
      tabArray = new String[totalRows][totalCols];

      for (int i = 0; i < totalRows; i++) {
        for (int j = 0; j < totalCols; j++) {
          
//        	reading each cell individually
        	Cell cell = sheet.getRow(i).getCell(j);
          //System.out.println(cell + " " + i + " " + j);

          if (cell == null)
            continue;
          
//converting data of its type to string
          switch (cell.getCellType()) {
          case Cell.CELL_TYPE_BOOLEAN:
            tabArray[i][j] = String.valueOf(cell.getBooleanCellValue());
            break;
          case Cell.CELL_TYPE_NUMERIC:
            tabArray[i][j] = String.valueOf(cell.getNumericCellValue());
            break;
          case Cell.CELL_TYPE_STRING:
            tabArray[i][j] = cell.getStringCellValue();
            break;
          default:
            tabArray[i][j] = "";
            break;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }

    return tabArray;
  }
  
  

}
