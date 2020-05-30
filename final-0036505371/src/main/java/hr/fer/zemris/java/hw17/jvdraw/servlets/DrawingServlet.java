package hr.fer.zemris.java.hw17.jvdraw.servlets;

import java.awt.Color;
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
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledTriangle;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

@WebServlet("/drawing")
public class DrawingServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		
		
		try {
			String fileName = req.getParameter("file");
			Path filePath = Paths.get(
					req.getServletContext().getRealPath("/WEB-INF/images") + "/" + fileName);
			
			req.setAttribute("name", fileName);
			
			List<String> lines = Files.readAllLines(filePath);
			List<GeometricalObject> objects = new ArrayList<>();
			
			for(String line : lines) {
				String[] parts = line.split(" ");
				
				if(parts[0].equals("LINE")) {
					if(parts.length != 8) {
						throw new RuntimeException();
					}
					objects.add(new Line(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), 
							Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), 
							new Color(Integer.parseInt(parts[5]), Integer.parseInt(parts[6]), 
									Integer.parseInt(parts[7]))));
					
				} else if(parts[0].equals("CIRCLE")) {
					if(parts.length != 7) {
						throw new RuntimeException();
					}
					objects.add(new Circle(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), 
							Integer.parseInt(parts[3]), new Color(Integer.parseInt(parts[4]),
									Integer.parseInt(parts[5]), Integer.parseInt(parts[6]))));
					
				} else if(parts[0].equals("FCIRCLE")) {
					if(parts.length != 10) {
						throw new RuntimeException();
					}
					objects.add(new FilledCircle(Integer.parseInt(parts[1]), 
							Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), 
							new Color(Integer.parseInt(parts[4]),
									Integer.parseInt(parts[5]), Integer.parseInt(parts[6])),
							new Color(Integer.parseInt(parts[7]),
									Integer.parseInt(parts[8]), Integer.parseInt(parts[9]))));
				} else if(parts[0].equals("FTRIANGLE")) {
					if(parts.length != 13) {
						throw new RuntimeException();
					}
					
					objects.add(new FilledTriangle(Integer.parseInt(parts[1]), 
							Integer.parseInt(parts[2]), 
							Integer.parseInt(parts[3]), 
							Integer.parseInt(parts[4]), 
							Integer.parseInt(parts[5]), 
							Integer.parseInt(parts[6]), 
							new Color(Integer.parseInt(parts[7]),
									Integer.parseInt(parts[8]), Integer.parseInt(parts[9])), 
							new Color(Integer.parseInt(parts[10]),
										Integer.parseInt(parts[11]), Integer.parseInt(parts[12]))));
				}
			}
			
			int lineCount = 0;
			int circleCount = 0;
			int filledCircleCount = 0;
			int triangleCount = 0;
			
			for(GeometricalObject object : objects) {
				if(object instanceof FilledCircle) {
					circleCount++;
				} else if(object instanceof Circle) {
					filledCircleCount++;
				} else if(object instanceof Line) {
					lineCount++;
				} else if(object instanceof FilledTriangle) {
					triangleCount++;
				}
			}
			
			req.setAttribute("lines", lineCount);
			req.setAttribute("circles", circleCount);
			req.setAttribute("filledCircles", filledCircleCount);
			req.setAttribute("triangles", triangleCount);
			req.setAttribute("objects", objects);
			req.getSession().setAttribute("objects", objects);
			req.getRequestDispatcher("/WEB-INF/pages/show.jsp").forward(req, resp);
			
		} catch(Exception ex) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
	}
}
