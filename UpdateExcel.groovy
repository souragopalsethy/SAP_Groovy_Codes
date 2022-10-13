import java.util.Locale;
import java.io.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message processData(Message message) {

    def body = message.getBody();
    InputStream ExcelFileToRead = body;
    try {
        XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
        wb.setSheetName(0, "Sheet1");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        wb.write(out);
        byte[] bytes = out.toByteArray();
        message.setBody(bytes);

    } catch (Exception e) {
        e.printStackTrace();
    }
    return message;
}