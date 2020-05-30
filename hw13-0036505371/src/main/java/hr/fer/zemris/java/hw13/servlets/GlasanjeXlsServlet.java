package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet koji generira excel tablicu sa trenutnim rezultatima glasovanja.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXlsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");

		List<GlasanjeRezultatiServlet.VoteResult> results = 
				GlasanjeRezultatiServlet.getVoteResults(req);

		try {
			HSSFWorkbook hwb = getWorkbook(results);
			OutputStream os = resp.getOutputStream();
			hwb.write(os);
			hwb.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Pomoćna metoda koja generira workbook za rezultate glasanja.
	 * 
	 * @param results rezultati glasanja
	 * @return
	 */
	private HSSFWorkbook getWorkbook(List<GlasanjeRezultatiServlet.VoteResult> results) {
		HSSFWorkbook hwb = new HSSFWorkbook();

		HSSFSheet sheet = hwb.createSheet("rezultati");
		HSSFRow rowHeader = sheet.createRow(0);
		rowHeader.createCell(0).setCellValue("Bend");
		rowHeader.createCell(1).setCellValue("Broj glasova");

		for(int i = 0; i < results.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(results.get(i).getName());
			row.createCell(1).setCellValue(Integer.toString(results.get(i).getVote()));
		}

		return hwb;
	}
}
