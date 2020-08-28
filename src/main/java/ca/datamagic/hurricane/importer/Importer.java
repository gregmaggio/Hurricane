/**
 * 
 */
package ca.datamagic.hurricane.importer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import ca.datamagic.hurricane.dao.BasinDAO;
import ca.datamagic.hurricane.dao.StormTrackDAO;
import ca.datamagic.hurricane.dao.YearDAO;
import ca.datamagic.hurricane.dto.BasinDTO;
import ca.datamagic.hurricane.dto.StormKeyDTO;
import ca.datamagic.hurricane.dto.StormTrackDTO;
import ca.datamagic.hurricane.dto.YearDTO;

/**
 * @author Greg
 *
 */
public class Importer {
	private static Logger _logger = LogManager.getLogger(Importer.class);
	
	public static List<BasinDTO> parse(InputStream inputStream) throws IOException {
		Scanner inputScanner = null;
		try {
			Hashtable<String, BasinDTO> basins = new Hashtable<String, BasinDTO>();
			
			BasinDTO basin = null;
			YearDTO year = null;
			
			inputScanner = new Scanner(inputStream);
			while (inputScanner.hasNextLine()) {
				String currentLine = inputScanner.nextLine();
				Header header = Header.getHeader(currentLine);
				if (header != null) {
					_logger.debug(header.toString());
					
					for (int ii = 0; ii < header.getTracks().intValue(); ii++) {
						currentLine = inputScanner.nextLine();
						Track track = Track.getTrack(currentLine);
						_logger.debug(track.toString());
						
						if (basin == null) {
							if (!basins.containsKey(header.getBasin().toUpperCase())) {
								basin = Basins.getBasin(header.getBasin());
								basins.put(header.getBasin().toUpperCase(), basin);
							}
							basin = basins.get(header.getBasin().toUpperCase());
						} else {
							if (basin.getName().compareToIgnoreCase(header.getBasin()) != 0) {
								if (!basins.containsKey(header.getBasin().toUpperCase())) {
									basin = Basins.getBasin(header.getBasin());
									basins.put(header.getBasin().toUpperCase(), basin);
									if (year != null) {
										basin.getYears().add(year);
										year = null;
									}
								} else {
									if (year != null) {
										basin.getYears().add(year);
										year = null;
									}
								}
								basin = basins.get(header.getBasin().toUpperCase());
							}
						}
						
						if (year != null) {
							if (year.getYear().intValue() != track.getYear().intValue()) {
								basin.getYears().add(year);
								year = new YearDTO();
								year.setBasin(basin.getName());
								year.setYear(track.getYear());
							}
						} else {
							year = new YearDTO();
							year.setBasin(basin.getName());
							year.setYear(track.getYear());
						}
						
						StormTrackDTO stormTrack = new StormTrackDTO();
						stormTrack.setStormNo(header.getCycloneNumber());
						stormTrack.setStormName(header.getName());
						stormTrack.setTrackNo(new Integer(ii + 1));
						stormTrack.setStatus(track.getStatus());
						stormTrack.setRecordIdentifier(track.getRecordIdentifier());
						stormTrack.setYear(track.getYear());
						stormTrack.setMonth(track.getMonth());
						stormTrack.setDay(track.getDay());
						stormTrack.setHours(track.getHours());
						stormTrack.setMinutes(track.getMinutes());
						stormTrack.setLatitude(track.getLatitude());
						stormTrack.setLatitudeHemisphere(track.getLatitudeHemisphere());
						stormTrack.setLongitude(track.getLongitude());
						stormTrack.setLongitudeHemisphere(track.getLongitudeHemisphere());
						stormTrack.setMaxWindSpeed(track.getMaxWindSpeed());
						stormTrack.setMinPressure(track.getMinPressure());
						Double x = null;
						Double y = null;
						if (stormTrack.getLongitudeHemisphere().compareToIgnoreCase("W") == 0) {
							x = new Double(-1.0 * stormTrack.getLongitude().doubleValue());
						} else {
							x = new Double(stormTrack.getLongitude().doubleValue());
						}
						if (stormTrack.getLongitudeHemisphere().compareToIgnoreCase("S") == 0) {
							y = new Double(-1.0 * stormTrack.getLatitude().doubleValue());
						} else {
							y = new Double(stormTrack.getLatitude().doubleValue());
						}
						stormTrack.setX(x);
						stormTrack.setY(y);
						year.getTracks().add(stormTrack);
					}
				}
			}
			if (year != null) {
				basin.getYears().add(year);
			}
			return new ArrayList<BasinDTO>(basins.values());
		} finally {
			if (inputScanner != null) {
				inputScanner.close();
			}
		}
	}
	
	public static List<BasinDTO> parse(String fileName) throws IOException {
		FileInputStream inputStream = null;		
		try {
			inputStream = new FileInputStream(fileName);
			return parse(inputStream);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			DOMConfigurator.configure("C:/Dev/Applications/Hurricane/src/main/resources/log4j.importer.cfg.xml");
			String fileName = "C:\\Data\\HurricaneTracks\\hurdat2-nepac-1949-2019-042320.txt";
			
			BasinDAO basinDAO = new BasinDAO();	
			List<BasinDTO> basins = parse(fileName);
			for (BasinDTO basin : basins) {
				_logger.debug("basin: " + basin);
				BasinDTO basinLookup = Basins.getBasin(basin.getName());
				if (basinLookup != null) {					
					if (!basinDAO.exists(basinLookup)) {
						basinDAO.save(basinLookup);
					}
					List<YearDTO> years = basin.getYears();
					for (YearDTO year : years) {
						_logger.debug("year: " + year);
						YearDAO yearDAO = new YearDAO();
						if (!yearDAO.exists(year)) {
							yearDAO.save(year);
						}
						StormTrackDAO stormTrackDAO = new StormTrackDAO();
						stormTrackDAO.setBasin(basin.getName());
						stormTrackDAO.setYear(year.getYear());
						for (StormTrackDTO stormTrack : year.getTracks()) {
							_logger.debug("stormTrack: " + stormTrack);
							StormKeyDTO stormKey = new StormKeyDTO();
							stormKey.setStormKey(basin.getName(), year.getYear(), stormTrack.getStormNo());
							stormKey.setBasin(basin.getName());
							stormKey.setYear(year.getYear());
							stormKey.setStormNo(stormTrack.getStormNo());
							stormKey.setStormName(stormTrack.getStormName());
							if (!basinDAO.exists(stormKey)) {
								basinDAO.save(stormKey);								
							}
							if (!stormTrackDAO.exists(stormTrack)) {
								stormTrackDAO.save(stormTrack);
							}
						}
					}
				}
			}
		} catch (Throwable t) {
			System.out.println("Exception: " + t.getMessage());
			t.printStackTrace();
		}
	}
}
