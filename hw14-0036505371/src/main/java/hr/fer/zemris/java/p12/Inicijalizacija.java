package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;
import hr.fer.zemris.java.p12.model.PollEntry;

/**
 * Razred za inicijalizaciju podataka u bazi podataka prilikom pokretanja aplikacije
 * za glasovanje.
 * 
 * @author Maja Radočaj
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 * Broj linija koji se treba nalaziti u properties datoteci.
	 */
	private static final int NUMBER_OF_PROPERTIES = 5;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		Properties properties = new Properties();
		String connectionURL;
		
		try {
			properties.load(sce.getServletContext()
					.getResourceAsStream("/WEB-INF/dbsettings.properties"));
			HashMap<String, String> propertiesMap = new HashMap<>();
			
			for(String key : properties.stringPropertyNames()) {
				String value = properties.getProperty(key);
				propertiesMap.put(key, value);
			}
			
			if(propertiesMap.size() != NUMBER_OF_PROPERTIES) {
				throw new RuntimeException("File dbsettings.properties "
						+ "is not formated correctly.");
			}
			if(!propertiesMap.containsKey("host") || !propertiesMap.containsKey("port") 
					|| !propertiesMap.containsKey("name") || !propertiesMap.containsKey("user")
					|| !propertiesMap.containsKey("password")) {
				
				throw new RuntimeException("File dbsettings.properties "
						+ "is not formated correctly.");
			}
			
			connectionURL = "jdbc:derby://" + propertiesMap.get("host") + ":" 
					+ propertiesMap.get("port") + "/" + propertiesMap.get("name") 
					+ ";user=" + propertiesMap.get("user") +";password=" 
					+ propertiesMap.get("password");
			
		} catch (IOException | NullPointerException e) { 
			throw new RuntimeException("File dbsettings.properties is not present.");
		}

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		try {
			Connection connection = cpds.getConnection();
			prepareTables(connection, sce);
			SQLConnectionProvider.setConnection(connection);	
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Pomoćna metoda za pripremanje tablica koje treba spremiti u bazu podataka.
	 * 
	 * @param connection veza
	 * @param sce kontekst
	 * @throws SQLException u slučaju greške
	 */
	private void prepareTables(Connection connection, ServletContextEvent sce) 
			throws SQLException {

		getTable("Polls", connection, sce);
		getTable("PollOptions", connection, sce);
		
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM POLLS");
		ResultSet res = ps.executeQuery();
		
		if(!res.next()) {
			fillTables(connection, sce, "Glasanje za omiljeni bend:", 
					"Od sljedećih bendova, koji Vam je bend najdraži? "
				    + "Kliknite na link kako biste glasali!", "glasanje-definicija.txt",
				    "glasanje-rezultati.txt");
			fillTables(connection, sce, "Glasanje za omiljenu skladbu:", 
					"Od sljedećih skladbi, koja Vam je najdraža? "
				    + "Kliknite na link kako biste glasali!", "glasanje-definicija-vivaldi.txt",
				    "glasanje-rezultati-vivaldi.txt");
		}
	}

	/**
	 * Pomoćna metoda koja puni tablice sa podacima definiranima u tekstualnoj datoteci.
	 * 
	 * @param con veza
	 * @param sce kontekst
	 * @param title naslov
	 * @param message poruka
	 * @param fileDef naziv dokumenta za definiciju
	 * @param fileRes naziv dokumenta za rezultate
	 * @throws SQLException u slučaju greške
	 */
	private void fillTables(Connection con, ServletContextEvent sce,
			String title, String message, String fileDef, String fileRes) throws SQLException {
		
		PreparedStatement ps = con.prepareStatement("INSERT INTO POLLS "
				+ "(title, message) values (?,?)", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, title);
	    ps.setString(2, message);
	    ps.executeUpdate();
	    
	    ResultSet rs = ps.getGeneratedKeys();
	    rs.next();
	    Long id = rs.getLong(1);
	    
	    ps = con.prepareStatement("INSERT INTO POLLOPTIONS (optionTitle, optionLink, "
	    		+ "pollID, votesCount) values (?,?,?,?)");
	    try{
	    	List<PollEntry> voteResults = getCurrentVoteResults(sce, fileDef, fileRes);
	    	for(PollEntry result : voteResults) {
	    		ps.setString(1, result.getTitle());
	    		ps.setString(2, result.getLink());
	    		ps.setLong(3, id);
	    		ps.setLong(4, result.getVotes());
	    		ps.executeUpdate();
	    	}
	    } catch(IOException ex) {
	    	throw new SQLException(ex.getMessage());
	    }
	}

	/**
	 * Pomoćna metoda koja stvara tablicu ako ona već ne postoji.
	 * 
	 * @param tableName maziv tablice
	 * @param con veza
	 * @param sce kontekst
	 * @throws SQLException u slučaju greške
	 */
	private void getTable(String tableName, Connection con, ServletContextEvent sce) 
			throws SQLException {
		
		ResultSet res = con.getMetaData().getTables(null, null, tableName.toUpperCase(), null);
		if(res.next())
		{
		    //ako već postoji, sve je u redu i ne treba ništa raditi;
		} else {
			PreparedStatement pst = null;
			
			if(tableName.toUpperCase().equals("POLLS")) {
				pst = con.prepareStatement("CREATE TABLE " + tableName +"\n" + 
						" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" + 
						" title VARCHAR(150) NOT NULL,\n" + 
						" message CLOB(2048) NOT NULL\n" + 
						")");
				
			} else {
				pst = con.prepareStatement("CREATE TABLE " + tableName + "\n" + 
						" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" + 
						" optionTitle VARCHAR(100) NOT NULL,\n" + 
						" optionLink VARCHAR(150) NOT NULL,\n" + 
						" pollID BIGINT,\n" + 
						" votesCount BIGINT,\n" + 
						" FOREIGN KEY (pollID) REFERENCES Polls(id)\n" + 
						")");
			}
			
			pst.execute();
		}
	}

	/**
	 * Pomoćna metoda koja iz datoteka čita definiciju anketa i njihovo stanje te 
	 * vraća listu elemenata koji će se dodati u tablicu baze podataka.
	 * 
	 * @param sce kontekst
	 * @param fileDef naziv dokumenta za definiciju
	 * @param fileRes naziv dokumenta za rezultat
	 * @return lista elemenata tablice
	 * @throws IOException u slučaju greške
	 */
	private List<PollEntry> getCurrentVoteResults(ServletContextEvent sce,
			String fileDef, String fileRes) throws IOException {
		
		List<PollEntry> result = new ArrayList<>();
		String fileNameDef = sce.getServletContext()
				.getRealPath("/WEB-INF/" + fileDef);
		List<String> defLines = Files.readAllLines(Paths.get(fileNameDef));
		
		String fileNameRes = sce.getServletContext()
				.getRealPath("/WEB-INF/" + fileRes);
		Path filePath = Paths.get(fileNameRes);
		
		if(!Files.exists(filePath)) {
			Files.createFile(filePath);
		}
		
		List<String> resLines = Files.readAllLines(filePath);
		
		for(String voteResult : resLines) {
			String[] voteParts = voteResult.split("\t");
			
			for(String band : defLines) {
				String[] resultParts = band.split("\t");
				if(voteParts[0].equals(resultParts[0])) {	//ako imaju isti id
					result.add(new PollEntry(Long.parseLong(voteParts[0]), resultParts[1], 
							resultParts[2], 0, Long.parseLong(voteParts[1])));
					break;
				}
			}
		}
		
		for(String definitionLine : defLines) {
			boolean hasVotes = false;
			String[] bandParts = definitionLine.split("\t");
			
			for(int i = 0; i < result.size(); i++) {
				if(bandParts[0].equals(Long.toString(result.get(i).getId()))) {
					hasVotes = true;
					break;
				}
			}
			if(!hasVotes) {
				result.add(new PollEntry(Long.parseLong(bandParts[0]), bandParts[1], 
						bandParts[2], 0, 0));
			}
		}
		
		result.sort((o, b) -> Long.compare(b.getVotes(), o.getVotes()));
		return result;
	}

}