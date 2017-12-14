/**
 * Project Name:iatp-basic-common
 * File Name:ExcelCellFormat.java
 * Package Name:com.smart.platform.filehelper
 * Date:2016年10月30日下午4:12:44
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.filehelper;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * ClassName:ExcelCellFormat <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年10月30日 下午4:12:44 <br/>
 * 
 * @author 01135912
 * @version
 * @since JDK 1.6
 * @see
 */
public class ExcelCellFormat
{
    
    private static String DEFAULT_DATE_FORMAT = "yyyy年m月d日";
    
    private static String DEFAULT_DOUBLE_FORMAT = "0.00";
    
    private static String DEFAULT_MONEY_FORMAT = "¥#,##0";
    
    private static String DEFAULT_PERCENT_FORMAT = "100%";
    
    private static String DEFAULT_UP_FORMAT = "[DbNum2][$-804]0";
    
    private static String DEFAULT_COMPUTER_FORMAT = "yyyy年m月d日";
    
    private static final int CELL_TYPE_PERCENT = 32;
    
/*    private static int CELL_TYPE_DATE = 33;
    
    private static int CELL_TYPE_MONEY = 34;
    
    private static int CELL_TYPE_COMPUTE = 35;*/
    
    public static CellStyle getCellFormat(Workbook wb, int formatIdx)
    {
        CellStyle style = ExcelCellFormat.getDataCellStyle(wb);
        switch (formatIdx)
        {
            case CELL_TYPE_PERCENT:
                style = dataPercentFormat(style, null);
                break;
            default:
                break;
        }
        return style;
    }
    
    public static String getCellFormatValue(String inputValue, int formatIdx)
    {
        switch (formatIdx)
        {
            case CELL_TYPE_PERCENT:
                inputValue = String.format("%.4f",Double.parseDouble(inputValue)*100) + "%";
                break;
            default:
                break;
        }
        return inputValue;
    }
    
    public static int[] getDataFormat(int[] inputFormat, int length)
    {
        if (inputFormat != null && inputFormat.length == length)
        {
            return inputFormat;
        }
        
        inputFormat = new int[length];
        
        for (int i = 0; i < length; i++)
        {
            inputFormat[i] = Cell.CELL_TYPE_STRING;
        }
        return inputFormat;
    }
    
    public static Cell dataFormat(Cell cell, Workbook wb, String dateFormate)
    {
        // set date format
        CellStyle cellStyle = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        if (dateFormate == null)
        {
            dateFormate = DEFAULT_DATE_FORMAT;
        }
        cellStyle.setDataFormat(format.getFormat(dateFormate));
        cell.setCellStyle(cellStyle);
        return cell;
    }
    
    // 第二种：保留两位小数格式
    public static Cell dataDoubleLenFormat(Cell cell, Workbook wb, String doubleFormat)
    {
        CellStyle cellStyle = wb.createCellStyle();
        if (doubleFormat == null)
        {
            doubleFormat = DEFAULT_DOUBLE_FORMAT;
        }
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(doubleFormat));
        cell.setCellStyle(cellStyle);
        return cell;
    }
    
    // 第三种：货币格式
    public static Cell dataMoneyFormat(Cell cell, Workbook wb, String moneyFormat)
    {
        CellStyle cellStyle = wb.createCellStyle();
        if (moneyFormat == null)
        {
            moneyFormat = DEFAULT_MONEY_FORMAT;
        }
        DataFormat format = wb.createDataFormat();
        cellStyle.setDataFormat(format.getFormat(moneyFormat));
        cell.setCellStyle(cellStyle);
        return cell;
    }
    
    public static CellStyle dataPercentFormat(CellStyle style, String percentFormat)
    {
        if (percentFormat == null)
        {
            percentFormat = DEFAULT_PERCENT_FORMAT;
        }
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat(percentFormat));
        return style;
    }
    
    // 第五种：中文大写格式
    public static Cell dataWordUpFormat(Cell cell, Workbook wb, String percentFormat)
    {
        CellStyle cellStyle = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        if (percentFormat == null)
        {
            percentFormat = DEFAULT_UP_FORMAT;
        }
        cellStyle.setDataFormat(format.getFormat(percentFormat));
        cell.setCellStyle(cellStyle);
        return cell;
    }
    
    // 第六种：科学计数法格式
    public static Cell computerFormat(Cell cell, Workbook wb, String computerFormat)
    {
        CellStyle cellStyle = wb.createCellStyle();
        if (computerFormat == null)
        {
            computerFormat = DEFAULT_COMPUTER_FORMAT;
        }
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(computerFormat));
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * 
     * @Title: getCellStyle
     * @Description: TODO（设置表头样式）
     * @param wb
     * @return
     */
    public static CellStyle getHeadCellStyle(Workbook wb)
    {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)10);// 设置字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
        style.setFont(font);
        
        style.setFillForegroundColor(HSSFColor.GREEN.index);// 设置背景色
        style.setFillBackgroundColor(HSSFColor.GREEN.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.SOLID_FOREGROUND);// 让单元格居中
        
        style.setBorderBottom(CellStyle.BORDER_THIN); // 底部边框
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
        style.setBorderRight(CellStyle.BORDER_THIN); // 右边边框
        style.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 右边边框颜色
        return style;
    }
    
    /**
     * 
     * @Title: getCellStyle
     * @Description: TODO（设置表头样式）
     * @param wb
     * @return
     */
    public static CellStyle getDataCellStyle(Workbook wb)
    {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)10);// 设置字体大小
        style.setFont(font);
        style.setWrapText(true);
        return style;
    }
}
