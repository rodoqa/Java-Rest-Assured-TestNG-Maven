package dao;

import dto.ResponseCodesDTO;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ResponseCodesDAO {
    private static HSSFSheet ExcelWSheet;
    private static HSSFWorkbook ExcelWBook;
    private static HSSFCell Cell;
    private static HSSFRow Row;

    private static final String Path_TestData = "src/main/java/utils/codes.xls";
    private static final String File_TestData = "codes.xls";

    public ResponseCodesDAO(){
        try {
            this.setExcelFile("codes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // This method is to set the File path and to open the Excel file, Pass Excel
    // Path and Sheetname as Arguments to this method
    public void setExcelFile(String SheetName) throws Exception {
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
    public static String getStringCellValue(int RowNum, int ColNum) throws Exception {
        try {
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            String CellData = Cell.getStringCellValue();
            return CellData;
        } catch (Exception e) {
            return "";
        }
    }

    // This method is to read the test data from the Excel cell, in this we are
    // passing parameters as Row num and Col num
    public static int getNumericCellValue(int RowNum, int ColNum) throws Exception {
        try {
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            int CellData = (int) Cell.getNumericCellValue();
            return CellData;
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

    public static ResponseCodesDTO getCodeName(int cod){
        ResponseCodesDTO rescode = new ResponseCodesDTO();

        for(int i=0;i<62;i++){
            try {
                if(getNumericCellValue(i,1) == cod){
                    rescode.setType(getStringCellValue(i,0));
                    rescode.setNumber(getNumericCellValue(i,1));
                    rescode.setName(getStringCellValue(i,2));
                    rescode.setDescription(getStringCellValue(i,3));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rescode;
    }

	/*public static void main(String args[]) throws Exception {
		 ResponseCodesDAO rcDAO = new ResponseCodesDAO();
         ResponseCodesDTO rcDTO = new ResponseCodesDTO();

         rcDTO = rcDAO.getCodeName(403);

         System.out.println(rcDTO.getType() + " " +rcDTO.getNumber() + " " + rcDTO.getName());
	}*/
}
