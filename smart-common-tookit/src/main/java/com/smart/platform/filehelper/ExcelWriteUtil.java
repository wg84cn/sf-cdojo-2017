package com.smart.platform.filehelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * ClassName: ExcelWriteUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年7月25日 下午3:59:17 <br/>
 *
 * @author 01135912
 * @version
 * @since JDK 1.6
 */
public class ExcelWriteUtil
{
    private static final Logger LOG = LoggerFactory.getLogger(ExcelWriteUtil.class);
    
    private static final int COLUMNS_SIZE = 15;
    
    private static final int EXCEL_CACHE_ROWS = 2000;
    
    /**
     * 
     * @Title: writeExcel
     * @Description: TODO(导出Excel表)
     * @param pathname:导出Excel表的文件路径
     * @param map：封装需要导出的数据(HEADERINFO封装表头信息，DATAINFON：封装要导出的数据信息,此处需要使用TreeMap) 例如：
     *            map.put(ExcelUtil.HEADERINFO,List<String> headList);
     *            map.put(ExcelUtil.DATAINFON,List<TreeMap<String,Object>> dataList);
     * @param wb
     * @throws IOException
     */
    public static byte[] createExcelByteArray(List<String> headList, List<List<String>> dataList)
    {
        if (headList == null || headList.isEmpty())
        {
            throw new IllegalArgumentException("The head list is empty!");
        }
        int[] cloumnSize = new int[] {10, 15, 15, 20, 18, 10, 10, 10, 10, 15, 25};
        Workbook wbook = new XSSFWorkbook();
        Sheet sheet = wbook.createSheet();
        setHeader(wbook, sheet, headList, cloumnSize);
        for (int i = 0; i < dataList.size(); i++)
        {
            Row rowdata = sheet.createRow(i + 1);// 创建数据行
            List<String> rowDatas = dataList.get(i);
            for (int j = 0; j < rowDatas.size(); j++)
            {
                Cell celldata = rowdata.createCell(j);
                celldata.setCellType(Cell.CELL_TYPE_STRING);
                celldata.setCellValue(rowDatas.get(j));
            }
        }
        return wookBookToBytes(wbook);
    }
    
    public static byte[] createExcelByteArray(String[] heads, String[] names, int[] formats, int[] sizes,
        String jsonStr)
    {
        if (heads == null || heads.length == 0 || names == null || 
            names.length == 0 || heads.length != names.length)
        {
            throw new IllegalArgumentException("The head or names length is invalid !");
        }
        
        if (sizes == null || sizes.length != heads.length)
        {
            sizes = initCoulumnSize(heads);
        }
        formats = ExcelCellFormat.getDataFormat(formats, heads.length);
        
        Workbook wb = new SXSSFWorkbook(EXCEL_CACHE_ROWS);
        Sheet sheet = wb.createSheet();
        setHeader(wb, sheet, Arrays.asList(heads), sizes);
        JSONArray jsonArray = JSONArray.parseArray(jsonStr);
        CellStyle style = ExcelCellFormat.getDataCellStyle(wb);
        
        for (int i = 0; i < jsonArray.size(); i++)
        {
            Row rowdata = sheet.createRow(i + 1);// 创建数据行
            JSONObject rowDatas = (JSONObject)jsonArray.get(i);
            for (int j = 0; j < heads.length; j++)
            {
                Cell celldata = rowdata.createCell(j);
                celldata.setCellType(Cell.CELL_TYPE_STRING);
                celldata.setCellStyle(style);
                String valueStr = rowDatas.getString(names[j]);
                if (formats[j] != Cell.CELL_TYPE_STRING)
                {
                    valueStr = ExcelCellFormat.getCellFormatValue(valueStr, formats[j]);
                }
                celldata.setCellValue(valueStr);
            }
        }
        return wookBookToBytes(wb);
    }
    
    private static byte[] wookBookToBytes(Workbook wbook)
    {
        ByteArrayOutputStream outByteArray = null;
        try
        {
            outByteArray = new ByteArrayOutputStream();
            wbook.write(outByteArray);
        }
        catch (IOException e)
        {
            LOG.error("Close the excel byte array out put stream error:{}", e.toString());
        }
        finally
        {
            IOUtils.closeQuietly(outByteArray);
        }
        return outByteArray.toByteArray();
    }
    
    /** set document header depend on given parameters */
    private static void setHeader(Workbook wb, Sheet sheet, List<String> headList, int size[])
    {
        
        if (headList.size() != size.length)
        {
            LOG.error("The head size is not match,please check it!");
            return;
        }
        /** set charater font */
        CellStyle style = ExcelCellFormat.getHeadCellStyle(wb);
        Row row = sheet.createRow(0);
        for (int i = 0; i < headList.size(); i++)
        {
            sheet.setColumnWidth(i, 256 * size[i]);
            createCell(row, i, style, headList.get(i));
        }
    }
    
    /** create a cell depend on given parameters */
    private static void createCell(Row row, int cellNum, CellStyle style, String text)
    {
        Cell cell = row.createCell(cellNum);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        if (style != null)
        {
            cell.setCellStyle(style);
        }
        cell.setCellValue(text);
    }
    
    private static int[] initCoulumnSize(String[] heads)
    {
        int[] clolumnSize = new int[heads.length];
        for (int ci = 0; ci < heads.length; ci++)
        {
            clolumnSize[ci] = COLUMNS_SIZE;
        }
        return clolumnSize;
    }
}
