package utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class SupportFactory {
    private static HSSFSheet ExcelWSheet;
    private static HSSFWorkbook ExcelWBook;
    private static HSSFCell Cell;
    private static HSSFRow Row;

    private static final String Path_TestData = "src/main/java/utils/TestData.xls";
    private static final String File_TestData = "TestData.xls";

    private static String returnDateStamp(String fileExtension) {
        Date d = new Date();
        String date = d.toString().replace(":", "_").replace(" ", "_") + fileExtension;
        return date;
    }

    // This method is to set the File path and to open the Excel file, Pass Excel
    // Path and Sheetname as Arguments to this method
    public static void setExcelFile(String SheetName) throws Exception {
        try {
            // Open the Excel file
            FileInputStream ExcelFile = new FileInputStream(Path_TestData);
            // Access the required test data sheet
            ExcelWBook = new HSSFWorkbook(ExcelFile);
            ExcelWSheet = ExcelWBook.getSheet(SheetName);
        } catch (Exception e) {
            throw (e);
        }
    }

    // This method is to read the test data from the Excel cell, in this we are
    // passing parameters as Row num and Col num
    public static String getCellData(int RowNum, int ColNum) {
        try {
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            String CellData = Cell.getStringCellValue();
            return CellData;
        } catch (Exception e) {
            return "";
        }
    }

    public static int getCellDataI(int RowNum, int ColNum) {
        try {
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            double CellData = Cell.getNumericCellValue();
            int value = ((int) CellData);
            return value;
        } catch (Exception e) {
            return 0;
        }
    }

    // This method is to write in the Excel cell, Row num and Col num are the
    // parameters
    public static void setCellData(String Result, int RowNum, int ColNum) throws Exception {
        try {
            Row = ExcelWSheet.getRow(RowNum);
            Cell = Row.getCell(ColNum);
            if (Cell == null) {
                Cell = Row.createCell(ColNum);
                Cell.setCellValue(Result);
            } else {
                Cell.setCellValue(Result);
            }

            // Constant variables Test Data path and Test Data file name
            FileOutputStream fileOut = new FileOutputStream(Path_TestData + File_TestData);
            ExcelWBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            //throw (e);
        }
    }

    public static String ipaddress()  {
        String ip = null;
        try{
            InetAddress inetAddress = InetAddress.getLocalHost();
            ip = "http://"+inetAddress.getHostAddress()+"/wordpress/";
            /*System.out.println("IP: "+ inetAddress.getHostAddress());
            System.out.println("Host: "+ inetAddress.getHostName());
            System.out.println("URI: "+ ip);*/
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
        return ip;
    }
}
