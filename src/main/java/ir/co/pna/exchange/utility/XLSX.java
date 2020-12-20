package ir.co.pna.exchange.utility;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class XLSX {


    public static ArrayList<ArrayList<Object>> readFile(String filePath) {

        ArrayList<ArrayList<Object>> sheetObj = null;

        try (InputStream inp = new FileInputStream(filePath)) {

            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            sheetObj = new ArrayList<>();
            ArrayList<Object> rowObj;

            for (Row row : sheet) {

                rowObj = new ArrayList<>();

                Iterator<Cell> cellIterator = row.cellIterator();

                boolean rowIsNotEmpty = false;

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
//                            System.out.print(cell.getStringCellValue() + "\t\t\t");
                            String cellObj = cell.getStringCellValue();
                            rowObj.add(cellObj);
                            rowIsNotEmpty = true;
                            break;



                        case NUMERIC:
//                            System.out.print(cell.getNumericCellValue() + "\t\t\t");
                            Integer cellObj2 = (int) cell.getNumericCellValue();
                            rowObj.add(cellObj2);
                            rowIsNotEmpty = true;
                            break;

                    }
                }

                if (rowIsNotEmpty) {
                    sheetObj.add(rowObj);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return sheetObj;
    }

        public static void writeFile(String filePath, ArrayList<ArrayList<Object>> content) {


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("transactions-sheet");

        int rowCount = 0;

        for (int i = 0; i< content.size(); i++){

            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;

            for (Object field : content.get(i)) {
                Cell cell = row.createCell(columnCount++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                } else if (field instanceof Long) {
                    cell.setCellValue((Long) field);
                }
            }

        }


        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
//        writeFile();
    }

}