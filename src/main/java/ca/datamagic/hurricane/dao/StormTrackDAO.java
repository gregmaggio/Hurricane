/**
 * 
 */
package ca.datamagic.hurricane.dao;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.datamagic.hurricane.dto.StormDTO;
import ca.datamagic.hurricane.dto.StormTrackDTO;
import ca.datamagic.hurricane.inject.MemoryCache;

/**
 * @author Greg
 *
 */
public class StormTrackDAO extends BaseDAO {
	private static Logger _logger = LogManager.getLogger(StormTrackDAO.class);
	private String _basin = null;
	private Integer _year = null;
	
	public StormTrackDAO() {
	}

	public String getBasin() {
		return _basin;
	}
	
	public Integer getYear() {
		return _year;
	}
	
	public String getConnectionString() throws Exception {
		String basin = getBasin();
		Integer year = getYear();
		if ((basin == null) || (basin.length() < 1))
			throw new Exception("Basin not set of StormTrackDAO");
		if (year == null)
			throw new Exception("year not set of StormTrackDAO");
		String dataPath = getDataPath();
		String templateDatabase = MessageFormat.format("{0}/stormtrack.db", dataPath);
		String database = MessageFormat.format("{0}/{1}{2}.db", dataPath, basin, Integer.toString(year.intValue()));
		if (!(new File(database).exists())) {
			Files.copy((new File(templateDatabase)).toPath(), (new File(database)).toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		return MessageFormat.format("jdbc:sqlite:{0}", database);
	}
	
	public void setBasin(String newVal) {
		_basin = newVal;
	}
	
	public void setYear(Integer newVal) {
		_year = newVal;
	}
	
	@MemoryCache
	public List<StormDTO> getStorms() throws Exception {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			List<StormDTO> storms = new ArrayList<StormDTO>();
			String connectionString = getConnectionString();
			connection = DriverManager.getConnection(connectionString);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT storm_no, storm_name, COUNT(track_no) AS tracks FROM stormtrack GROUP BY storm_no, storm_name ORDER BY storm_no, storm_name");
			while (resultSet.next()) {
				StormDTO dto = new StormDTO();
				dto.setStormNo(resultSet.getInt("storm_no"));
				dto.setStormName(resultSet.getString("storm_name"));
				dto.setTracks(resultSet.getInt("tracks"));
				storms.add(dto);
			}
			return storms;
		} finally {
			if (resultSet != null) {
				close(resultSet);
			}
			if (statement != null) {
				close(statement);
			}
			if (connection != null) {
				close(connection);
			}
		}
	}
	
	@MemoryCache
	public List<StormTrackDTO> getStormTracks(Integer stormNo) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			List<StormTrackDTO> tracks = new ArrayList<StormTrackDTO>();
			String connectionString = getConnectionString();
			connection = DriverManager.getConnection(connectionString);
			if (stormNo > 0) {
				statement = connection.prepareStatement("SELECT * FROM stormtrack WHERE storm_no = ? ORDER BY track_no");
				statement.setInt(1, stormNo);
			} else {
				statement = connection.prepareStatement("SELECT * FROM stormtrack ORDER BY storm_no, track_no");
			}
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				StormTrackDTO dto = new StormTrackDTO();
				dto.setStormNo(resultSet.getInt("storm_no"));
				dto.setStormName(resultSet.getString("storm_name"));
				dto.setTrackNo(resultSet.getInt("track_no"));
				dto.setYear(resultSet.getInt("year"));
				dto.setMonth(resultSet.getInt("month"));
				dto.setDay(resultSet.getInt("day"));
				dto.setHours(resultSet.getInt("hours"));
				dto.setMinutes(resultSet.getInt("minutes"));
				dto.setRecordIdentifier(resultSet.getString("record_identifier"));
				dto.setStatus(resultSet.getString("status"));
				dto.setLatitude(resultSet.getDouble("latitude"));
				dto.setLatitudeHemisphere(resultSet.getString("latitude_hemisphere"));
				dto.setLongitude(resultSet.getDouble("longitude"));
				dto.setLongitudeHemisphere(resultSet.getString("longitude_hemisphere"));
				dto.setMaxWindSpeed(resultSet.getDouble("max_wind_speed"));
				dto.setMinPressure(resultSet.getDouble("min_pressure"));
				dto.setX(resultSet.getDouble("x"));
				dto.setY(resultSet.getDouble("y"));
				tracks.add(dto);
			}
			return tracks;
		} finally {
			if (resultSet != null) {
				close(resultSet);
			}
			if (statement != null) {
				close(statement);
			}
			if (connection != null) {
				close(connection);
			}
		}
	}
	
	@MemoryCache
	public StormTrackDTO getStormTrack(Integer stormNo, Integer trackNo) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String connectionString = getConnectionString();
			connection = DriverManager.getConnection(connectionString);
			statement = connection.prepareStatement("SELECT * FROM stormtrack WHERE storm_no = ? AND track_no = ?");
			statement.setInt(1, stormNo);
			statement.setInt(2, trackNo);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				StormTrackDTO dto = new StormTrackDTO();
				dto.setStormNo(resultSet.getInt("storm_no"));
				dto.setStormName(resultSet.getString("storm_name"));
				dto.setTrackNo(resultSet.getInt("track_no"));
				dto.setYear(resultSet.getInt("year"));
				dto.setMonth(resultSet.getInt("month"));
				dto.setDay(resultSet.getInt("day"));
				dto.setHours(resultSet.getInt("hours"));
				dto.setMinutes(resultSet.getInt("minutes"));
				dto.setRecordIdentifier(resultSet.getString("record_identifier"));
				dto.setStatus(resultSet.getString("status"));
				dto.setLatitude(resultSet.getDouble("latitude"));
				dto.setLatitudeHemisphere(resultSet.getString("latitude_hemisphere"));
				dto.setLongitude(resultSet.getDouble("longitude"));
				dto.setLongitudeHemisphere(resultSet.getString("longitude_hemisphere"));
				dto.setMaxWindSpeed(resultSet.getDouble("max_wind_speed"));
				dto.setMinPressure(resultSet.getDouble("min_pressure"));
				dto.setX(resultSet.getDouble("x"));
				dto.setY(resultSet.getDouble("y"));
				return dto;
			}
			return null;
		} finally {
			if (resultSet != null) {
				close(resultSet);
			}
			if (statement != null) {
				close(statement);
			}
			if (connection != null) {
				close(connection);
			}
		}
	}
	
	@MemoryCache
	public List<StormTrackDTO> getStormTracks(Integer startYear, Integer startMonth, Integer startDay, Integer endYear, Integer endMonth, Integer endDay) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			List<StormTrackDTO> tracks = new ArrayList<StormTrackDTO>();
			String connectionString = getConnectionString();
			connection = DriverManager.getConnection(connectionString);
			statement = connection.prepareStatement("SELECT * FROM stormtrack WHERE ((year >= ?) AND (month >= ?) AND (day >= ?)) AND ((year <= ?) AND (month <= ?) AND (day <= ?)) ORDER BY storm_no, track_no");
			statement.setInt(1, startYear);
			statement.setInt(2, startMonth);
			statement.setInt(3, startDay);
			statement.setInt(4, endYear);
			statement.setInt(5, endMonth);
			statement.setInt(6, endDay);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				StormTrackDTO dto = new StormTrackDTO();
				dto.setStormNo(resultSet.getInt("storm_no"));
				dto.setStormName(resultSet.getString("storm_name"));
				dto.setTrackNo(resultSet.getInt("track_no"));
				dto.setYear(resultSet.getInt("year"));
				dto.setMonth(resultSet.getInt("month"));
				dto.setDay(resultSet.getInt("day"));
				dto.setHours(resultSet.getInt("hours"));
				dto.setMinutes(resultSet.getInt("minutes"));
				dto.setRecordIdentifier(resultSet.getString("record_identifier"));
				dto.setStatus(resultSet.getString("status"));
				dto.setLatitude(resultSet.getDouble("latitude"));
				dto.setLatitudeHemisphere(resultSet.getString("latitude_hemisphere"));
				dto.setLongitude(resultSet.getDouble("longitude"));
				dto.setLongitudeHemisphere(resultSet.getString("longitude_hemisphere"));
				dto.setMaxWindSpeed(resultSet.getDouble("max_wind_speed"));
				dto.setMinPressure(resultSet.getDouble("min_pressure"));
				dto.setX(resultSet.getDouble("x"));
				dto.setY(resultSet.getDouble("y"));
				tracks.add(dto);
			}
			return tracks;
		} finally {
			if (resultSet != null) {
				close(resultSet);
			}
			if (statement != null) {
				close(statement);
			}
			if (connection != null) {
				close(connection);
			}
		}
	}
	
	@MemoryCache
	public List<StormDTO> getStorms(Integer startYear, Integer startMonth, Integer startDay, Integer endYear, Integer endMonth, Integer endDay) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			List<StormDTO> storms = new ArrayList<StormDTO>();
			String connectionString = getConnectionString();
			connection = DriverManager.getConnection(connectionString);
			statement = connection.prepareStatement("SELECT storm_no, storm_name, COUNT(track_no) AS tracks FROM stormtrack WHERE ((year >= ?) AND (month >= ?) AND (day >= ?)) AND ((year <= ?) AND (month <= ?) AND (day <= ?)) GROUP BY storm_no, storm_name ORDER BY storm_no, storm_name");
			statement.setInt(1, startYear);
			statement.setInt(2, startMonth);
			statement.setInt(3, startDay);
			statement.setInt(4, endYear);
			statement.setInt(5, endMonth);
			statement.setInt(6, endDay);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				StormDTO dto = new StormDTO();
				dto.setStormNo(resultSet.getInt("storm_no"));
				dto.setStormName(resultSet.getString("storm_name"));
				dto.setTracks(resultSet.getInt("tracks"));
				storms.add(dto);
			}
			return storms;
		} finally {
			if (resultSet != null) {
				close(resultSet);
			}
			if (statement != null) {
				close(statement);
			}
			if (connection != null) {
				close(connection);
			}
		}
	}
	
	public void deleteStormTracks(Integer stormNo) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			String connectionString = getConnectionString();
			connection = DriverManager.getConnection(connectionString);
			statement = connection.prepareStatement("DELETE FROM stormtrack WHERE storm_no = ?");
			statement.setInt(1, stormNo);
			statement.executeUpdate();
		} finally {
			if (statement != null) {
				close(statement);
			}
			if (connection != null) {
				close(connection);
			}
		}
	}
	
	public void clear() throws Exception {
		Connection connection = null;
		Statement statement = null;
		try {
			String connectionString = getConnectionString();
			connection = DriverManager.getConnection(connectionString);
			statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM stormtrack");
		} finally {
			if (statement != null) {
				close(statement);
			}
			if (connection != null) {
				close(connection);
			}
		}
	}
	
	public void save(StormTrackDTO stormTrack) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			String connectionString = getConnectionString();
			connection = DriverManager.getConnection(connectionString);
			statement = connection.prepareStatement("INSERT INTO stormtrack(storm_no,storm_name,track_no,year,month,day,hours,minutes,record_identifier,status,latitude,latitude_hemisphere,longitude,longitude_hemisphere,max_wind_speed,min_pressure,x,y) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			statement.setInt(1, stormTrack.getStormNo());
			statement.setString(2, stormTrack.getStormName());
			statement.setInt(3, stormTrack.getTrackNo());
			statement.setInt(4, stormTrack.getYear());
			statement.setInt(5, stormTrack.getMonth());
			statement.setInt(6, stormTrack.getDay());
			statement.setInt(7, stormTrack.getHours());
			statement.setInt(8, stormTrack.getMinutes());
			statement.setString(9, stormTrack.getRecordIdentifier());
			statement.setString(10, stormTrack.getStatus());
			statement.setDouble(11, stormTrack.getLatitude());
			statement.setString(12, stormTrack.getLatitudeHemisphere());
			statement.setDouble(13, stormTrack.getLongitude());
			statement.setString(14, stormTrack.getLongitudeHemisphere());
			statement.setDouble(15, stormTrack.getMaxWindSpeed());
			statement.setDouble(16, stormTrack.getMinPressure());
			statement.setDouble(17, stormTrack.getX());
			statement.setDouble(18, stormTrack.getY());
			int affectedRecords = statement.executeUpdate();
			_logger.debug("affectedRecords: " + affectedRecords);
		} finally {
			if (statement != null) {
				close(statement);
			}
			if (connection != null) {
				close(connection);
			}
		}
	}
}
