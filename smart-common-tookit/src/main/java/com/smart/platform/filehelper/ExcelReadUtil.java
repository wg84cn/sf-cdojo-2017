package com.smart.platform.filehelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.smart.platform.toolkit.DateStyle;

public class ExcelReadUtil
{
    private static final int SHEET_NUM = 0;
    
    private static final int INPUT_FILE_TYPE = 1;
    private static final int INPUT_PATH_TYPE = 0;
    
    public static List<List<String>> readXlsx(InputStream fileStream, String path) throws IOException
    {
        XSSFWorkbook xssfWorkbook = (XSSFWorkbook)getWorkBook(fileStream,path,INPUT_FILE_TYPE);
        return readXlsx(xssfWorkbook);
    }
    
    public static List<List<String>> readXlsx(String path) throws IOException
    {
        XSSFWorkbook xssfWorkbook = (XSSFWorkbook)getWorkBook(null,path,INPUT_PATH_TYPE);
        return readXlsx(xssfWorkbook);
    }
    
    /**
     * Read the Excel 2010
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    private static List<List<String>> readXlsx(XSSFWorkbook xssfWorkbook)
        throws IOException
    {
        List<List<String>> excelList = new ArrayList<List<String>>();
        // SHEET_NUM = xssfWorkbook.getNumberOfSheets()
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(SHEET_NUM);
        if (xssfSheet == null)
        {
            return null;
        }
        XSSFRow headRow = xssfSheet.getRow(0);
        int cloumnSize = headRow.getLastCellNum();
        for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++)
        {
            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow == null)
            {
                continue;
            }
            List<String> strArray = new ArrayList<String>();
            int currColumnSize = xssfRow.getLastCellNum();
            currColumnSize = (currColumnSize < cloumnSize) ? cloumnSize : currColumnSize;
            for (short cidx = 0; cidx < currColumnSize; cidx++)
            {
                strArray.add(getValue(xssfRow.getCell(cidx)));
            }
            excelList.add(strArray);
        }
        return excelList;
    }
    
    public List<List<String>> readXls(String path)
        throws IOException
    {
        HSSFWorkbook hssfWorkbook = (HSSFWorkbook)getWorkBook(null,path,INPUT_FILE_TYPE);;
        List<List<String>> excelList = new ArrayList<List<String>>();
        // SHEET_NUM = hssfWorkbook.getNumberOfSheets()
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(SHEET_NUM);
        if (hssfSheet == null)
        {
            return null;
        }
        // Read the Row
        for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++)
        {
            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            List<String> strArray = new ArrayList<String>();
            for (int cidx = 0; cidx < hssfRow.getLastCellNum(); cidx++)
            {
                strArray.add(getValue(hssfRow.getCell(cidx)));
            }
            excelList.add(strArray);
        }
        return excelList;
    }
    
    private static Workbook getWorkBook(InputStream fileStream, String fileName,int inputType)throws IOException
    {
        Workbook workbook = null;
        if (null == fileName || fileName.trim().isEmpty())
        {
            throw new IllegalArgumentException("The excel file path is null!");
        }
        
        try
        {
            String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            if(inputType == INPUT_PATH_TYPE)
            {
                fileStream = new FileInputStream(new File(fileName));
            }
            
            if (".xls".equals(fileType.trim().toLowerCase()))
            {
                workbook = new HSSFWorkbook(fileStream);// 创建 Excel 2003 工作簿对象
            }
            else if (".xlsx".equals(fileType.trim().toLowerCase()))
            {
                workbook = new XSSFWorkbook(fileStream);// 创建 Excel 2007 工作簿对象
            }
        }
        finally
        {
            IOUtils.closeQuietly(fileStream);
        }
        return workbook;
    }
    
    private static String getValue(Cell cell)
    {
        String cellValue = "";
        if(cell == null)
        {
            return cellValue;
        }
        switch (cell.getCellType())
        {
            case Cell.CELL_TYPE_STRING:// 字符串类型
                cellValue = cell.getStringCellValue();
                if (cellValue.trim().equals("") || cellValue.trim().length() <= 0)
                {
                    cellValue = " ";
                }
                break;
            case Cell.CELL_TYPE_NUMERIC: // 数值类型
                if (DateUtil.isCellDateFormatted(cell))
                { // 判断是日期类型
                    Date date = DateUtil.getJavaDate(cell.getNumericCellValue());// 获取成DATE类型
                    SimpleDateFormat dateformat = new SimpleDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());  
                    cellValue = dateformat.format(date);
                }
                else
                {
                    DecimalFormat df = new DecimalFormat("0");
                    cellValue = df.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                cellValue = " ";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                break;
            default:
                break;
        }
        return cellValue;
    }
}