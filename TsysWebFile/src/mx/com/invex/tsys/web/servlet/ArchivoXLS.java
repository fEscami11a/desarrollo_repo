package mx.com.invex.tsys.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Servlet implementation class MiServlet
 */
@WebServlet("/ArchivoXLS")
public class ArchivoXLS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArchivoXLS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=filename.xls");
		HSSFWorkbook workbook = new HSSFWorkbook();
		// ...
		// Now populate workbook the usual way.
		HSSFSheet worksheet = workbook.createSheet("POI Worksheet");

		// index from 0,0... cell A1 is cell(0,0)
		HSSFRow row1 = worksheet.createRow((short) 0);

		HSSFCell cellA1 = row1.createCell((short) 0);
		cellA1.setCellValue("Hello");
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellA1.setCellStyle(cellStyle);

		HSSFCell cellB1 = row1.createCell((short) 1);
		cellB1.setCellValue("Goodbye");
		cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellB1.setCellStyle(cellStyle);

		HSSFCell cellC1 = row1.createCell((short) 2);
		cellC1.setCellValue(true);

		HSSFCell cellD1 = row1.createCell((short) 3);
		cellD1.setCellValue(new Date());
		cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat
				.getBuiltinFormat("m/d/yy h:mm"));
		cellD1.setCellStyle(cellStyle);

		// ...
		workbook.write(response.getOutputStream()); // Write workbook to response.
		workbook.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("dflñigjfldhg");
	}

}
