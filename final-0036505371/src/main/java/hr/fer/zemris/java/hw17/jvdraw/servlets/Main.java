package hr.fer.zemris.java.hw17.jvdraw.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class Main extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/images");
		Path filePath = Paths.get(fileName);
		
		if(!Files.exists(filePath)) {
			Files.createDirectory(filePath);
		}
		
		File folder = filePath.toFile();
		File[] listOfFiles = folder.listFiles();

		List<String> fileNames = new ArrayList<>();
		for(File file : listOfFiles) {
			fileNames.add(file.getName());
		}
		fileNames.sort(String::compareTo);
		
		req.setAttribute("files", fileNames);
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}
}
