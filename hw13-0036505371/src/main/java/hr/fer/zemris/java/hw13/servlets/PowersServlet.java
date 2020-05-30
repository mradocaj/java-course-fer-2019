package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet koji generira excel tablicu za zadane parametre.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/powers")
public class PowersServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String paramA = req.getParameter("a");
		String paramB = req.getParameter("b");
		String paramN = req.getParameter("n");
		
		int a;
		int n; 
		int b;
		try {
			a = Integer.parseInt(paramA);
			b = Integer.parseInt(paramB);
			n = Integer.parseInt(paramN);
		} catch(NumberFormatException | NullPointerException ex) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		if(a < -100 || a > 100 || b < -100 || b > 100 || n < 1 || n > 5) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
	
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
		
		try {
			HSSFWorkbook hwb = getWorkbook(a, b, n);
			OutputStream os = resp.getOutputStream();
			hwb.write(os);
			hwb.close();
		} catch(Exception ex) {
		}
	}

	/**
	 * Pomoćna metoda za generiranje excel workbooka.
	 * 
	 * @param a parametar a
	 * @param b parametar b
	 * @param n parametar n
	 * @return novi workbook
	 */
	private HSSFWorkbook getWorkbook(int a, int b, int n) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		if(a > b) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		
		for(int i = 0; i < n; i++) {
			HSSFSheet sheet = hwb.createSheet("Sheet " + (i + 1));
			HSSFRow rowHeader = sheet.createRow(0);
			rowHeader.createCell(0).setCellValue("x");
			rowHeader.createCell(1).setCellValue("pow(x," + (i + 1) + ")");
			
			int k = 1;
			for(int j = a; j <= b; j++) {
				HSSFRow row = sheet.createRow(k);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i + 1));
				k++;
			}
		}
		
		return hwb;
	}

}
